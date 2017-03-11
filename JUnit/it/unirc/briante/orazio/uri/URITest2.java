package it.unirc.briante.orazio.uri;

import it.unirc.briante.orazio.ndn.URI;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class URITest2 {

	private final static Logger LOG = Logger.getLogger(URITest2.class);

	@Test
	public void test() {
		PropertyConfigurator.configure("log4j.properties");
		
		/*
		 * NOT WELL-FORMED URI
		 */
				
		URI uri=new URI("/Temperature/LivingRoom");
		LOG.info(uri.toString());
		LOG.info("URI: "+uri.getURI());


		uri=new URI("a:Temperature/LivingRoom//");
		LOG.info(uri.toString());
		LOG.info("URI: "+uri.getURI());
		
		uri=new URI("r:/Temperature/LivingRoom");
		LOG.info(uri.toString());
		LOG.info("URI: "+uri.getURI());
		
		uri=new URI("c:/Temperature/LivingRoom");
		LOG.info(uri.toString());
		LOG.info("URI: "+uri.getURI());
	}

}
