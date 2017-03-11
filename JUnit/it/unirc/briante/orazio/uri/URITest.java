package it.unirc.briante.orazio.uri;

import it.unirc.briante.orazio.ndn.URI;
import it.unirc.briante.orazio.ndn.URI.Type;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class URITest {

	private final static Logger LOG = Logger.getLogger(URITest.class);

	@Test
	public void test() {
		PropertyConfigurator.configure("log4j.properties");
		
		URI uri=new URI();
		LOG.info(uri.toString());
		LOG.info(uri.getURI());
		
		uri=new URI("Temperature","/kitchen");
		LOG.info(uri.toString());
		LOG.info("URI: "+uri.getURI());
		
		uri=new URI("Temperature","/kitchen", "19");
		LOG.info(uri.toString());
		LOG.info("URI: "+uri.getURI());
		
		uri=new URI("Temperature","/kitchen", "20", Type.REQUEST);
		LOG.info(uri.toString());
		LOG.info("URI: "+uri.getURI());
		
		uri=new URI(uri.getURI());
		LOG.info(uri.toString());
		LOG.info("URI: "+uri.getURI());
		
		uri=new URI("Humidity","/LivingRoom", Type.ANSWER);
		LOG.info(uri.toString());
		LOG.info("URI: "+uri.getURI());
		
		uri=new URI(uri.getURI());
		LOG.info(uri.toString());
		LOG.info("URI: "+uri.getURI());
		}

}
