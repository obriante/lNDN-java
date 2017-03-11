/**
 * 
 */
package it.unirc.briante.orazio.comm;

import it.unirc.briante.orazio.ndn.JNDNComManager;
import it.unirc.briante.orazio.ndn.URI;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.rapplogic.xbee.api.XBeeException;

/**
 * @author Orazio Briante
 *
 */
@RunWith(JUnit4.class)
public class DiscoveryTest {

	private final static Logger LOG = Logger.getLogger(DiscoveryTest.class);

	private final static int DEFAULT_TIMEOUT_MS=5000;
	private final static int REPEATING_TIME_LENGTH=595000;
	// private final static int NTEST=30;


	@Test
	public void test() {
		PropertyConfigurator.configure("log4j.properties");

		JNDNComManager jxbee=new JNDNComManager();

		URI dRequest=new URI("r:/*/*//");
		LOG.info(dRequest);
		LOG.info("URI: "+dRequest.getURI());

		try {
			jxbee.open("/dev/ttyUSB0" ,115200);

			// for(int i=0; i<NTEST; i++){

			long stopTime=System.currentTimeMillis()+REPEATING_TIME_LENGTH;

			int pkt=0;
			while(System.currentTimeMillis()<stopTime){

				jxbee.send(dRequest);
				pkt=pkt+1;
				LOG.info("Packets: "+ pkt);

				ArrayList<URI> discovery=new ArrayList<URI>();
				String response;
				do{
					response=jxbee.receive(DEFAULT_TIMEOUT_MS);
					if(response!=null){
						discovery.add(new URI(response));
						LOG.info("Answer: "+response);
					}
				}while(response!=null);


				LOG.info("Discovered Node: " +discovery.size());

				for(URI answer :discovery){
					LOG.info(answer.toString());
				}

				//}
			}

			jxbee.close();
			LOG.info("[END]");

		} catch (XBeeException e) {
			LOG.fatal(e);
			jxbee.close();
		}

	}

}
