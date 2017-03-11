package it.unirc.briante.orazio.comm;

import java.util.ArrayList;

import it.unirc.briante.orazio.ndn.JNDNComManager;
import it.unirc.briante.orazio.ndn.URI;
import it.unirc.briante.orazio.ndn.URI.Type;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.rapplogic.xbee.api.XBeeException;

@RunWith(JUnit4.class)
public class SendInterestTest {

	private final static Logger LOG = Logger.getLogger(SendInterestTest.class);

	private final static int DEFAULT_TIMEOUT_MS=2500;
	private final static int REPEATING_TIME_LENGTH=595000;
	private final static int ARDUINO_NUM=5;

	@Test
	public void test() {
		PropertyConfigurator.configure("log4j.properties");

		JNDNComManager jxbee=new JNDNComManager();
		try {
			jxbee.open("/dev/ttyUSB0" ,115200);

			long stopTime=System.currentTimeMillis()+REPEATING_TIME_LENGTH;

			int pkt=0;
			while(System.currentTimeMillis()<stopTime)
			{

				pkt=pkt+1;
				LOG.info("Packets: "+ pkt);
				
				ArrayList<URI> discovery=new ArrayList<URI>();

				
				for(int i=1; i<=ARDUINO_NUM; i++)
				{
					URI request=new URI("Dummy"+i,"/arduino", Type.REQUEST);
					LOG.info(request);
					jxbee.send(request);
					

					String answer=null;
					URI ans=null;
					do{
						answer=jxbee.receive(DEFAULT_TIMEOUT_MS);
						if(answer!=null){
							LOG.info("answer raw: "+answer);
							ans=new URI(answer);
							discovery.add(ans);
							if(ans.getObject().equals(request.getObject()) && ans.getPath().equals(request.getPath()) && ans.getType()==Type.ANSWER)
								break;
							else
								ans=null;					
						}	
					}while(answer!=null);				

					if(ans!=null)
						LOG.info("answer: "+ans);
					
					try {
						Thread.sleep(DEFAULT_TIMEOUT_MS);
					} catch (InterruptedException e) {
					}
				}
				
				LOG.info("Discovered Node: " +discovery.size());
				
			}
			jxbee.close();

		} catch (XBeeException e) {
			LOG.error(e);
			jxbee.close();
		}

	}
}