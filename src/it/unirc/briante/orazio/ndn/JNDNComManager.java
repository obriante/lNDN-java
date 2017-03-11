package it.unirc.briante.orazio.ndn;

import org.apache.log4j.Logger;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.XBeeTimeoutException;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.util.ByteUtils;

public class JNDNComManager{

	private final static Logger LOG = Logger.getLogger(JNDNComManager.class);
	
	private final static int DEFAULT_PACKET_LENGTH=72;
	//private final static int DEFAULT_BROADCAST_RADIUS=1;
	
	private static URI lastURI;

	private XBee xbee;

	public JNDNComManager(){
		xbee=new XBee();
	}
	
	public void open(String port, int baudrate)  throws XBeeException{
		xbee.open(port, baudrate);
	}
	
	public void close(){
		xbee.close();
	}

	public XBee getXbee() {
		return xbee;
	}

	public void setXbee(XBee xbee) {
		this.xbee = xbee;
	}

	public void send(URI uri) throws XBeeException{
	
		String interest=uri.getURI();
		
		int payload[]=ByteUtils.stringToIntArray(interest);

		if(interest.length()>DEFAULT_PACKET_LENGTH){

			int lastPacketDim=interest.length()%DEFAULT_PACKET_LENGTH;
			int numberOfSegments=(interest.length()-lastPacketDim)/DEFAULT_PACKET_LENGTH;

			for(int i=0; i<numberOfSegments; i++){
				int[] _payload = new int[DEFAULT_PACKET_LENGTH];
				System.arraycopy(payload, i*DEFAULT_PACKET_LENGTH, _payload, 0, _payload.length);

				sendPacket(_payload);
			}

			if(lastPacketDim>0){
				int[] _payload = new int[lastPacketDim];
				System.arraycopy(payload, numberOfSegments*DEFAULT_PACKET_LENGTH, _payload, 0, lastPacketDim);

				sendPacket(_payload);
			}

		}else
			sendPacket(payload);
	}

	public void send(XBeeAddress64 addr, URI uri) throws XBeeException{
		
		lastURI=uri;
		
		String interest=uri.getURI();
		
		int payload[]=ByteUtils.stringToIntArray(interest);

		if(interest.length()>DEFAULT_PACKET_LENGTH){

			int lastPacketDim=interest.length()%DEFAULT_PACKET_LENGTH;
			int numberOfSegments=(interest.length()-lastPacketDim)/DEFAULT_PACKET_LENGTH;

			for(int i=0; i<numberOfSegments; i++){
				int[] _payload = new int[DEFAULT_PACKET_LENGTH];
				System.arraycopy(payload, i*DEFAULT_PACKET_LENGTH, _payload, 0, _payload.length);

				sendPacket(addr, _payload);
			}

			if(lastPacketDim>0){
				int[] _payload = new int[lastPacketDim];
				System.arraycopy(payload, numberOfSegments*DEFAULT_PACKET_LENGTH, _payload, 0, lastPacketDim);

				sendPacket(addr, _payload);
			}

		}else
			sendPacket(addr, payload);
	}
	
	private void sendPacket(int[] payload) throws XBeeException{
		ZNetTxRequest zrequest = new ZNetTxRequest(XBeeAddress64.BROADCAST, payload);
		zrequest.setOption(ZNetTxRequest.Option.BROADCAST);
		//zrequest.setBroadcastRadius(DEFAULT_BROADCAST_RADIUS);
		LOG.debug("Trying to send: "+new String(IntTochar(payload)));
		xbee.sendAsynchronous(zrequest);
	}
	
	private void sendPacket(XBeeAddress64 addr, int[] payload) throws XBeeException{
		ZNetTxRequest zrequest = new ZNetTxRequest(addr, payload);
		zrequest.setOption(ZNetTxRequest.Option.UNICAST);
		//zrequest.setBroadcastRadius(DEFAULT_BROADCAST_RADIUS);
		LOG.debug("Trying to send: "+new String(IntTochar(payload)));
		xbee.sendAsynchronous(zrequest);
	}

	
	private char[] IntTochar(int[] array) {

		char[] output = new char[array.length];

		for (int i = 0; i < array.length; i++)
			output[i] = (char) (array[i]);

		return output;
	}

	public String receive(int timeout) throws XBeeException{

		XBeeResponse response;
		try {
			response = xbee.getResponse(timeout);

			//LOG.debug(response.toString());
			if (response.getApiId() == ApiId.ZNET_RX_RESPONSE)									
				return new String(IntTochar(((ZNetRxResponse) response).getData()));
			else
				return receive(timeout);

		} catch (XBeeTimeoutException e) {
			LOG.debug(e.toString());
		}

		return null;
	}
}
