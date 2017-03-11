package it.unirc.briante.orazio.suite;

import it.unirc.briante.orazio.uri.URITest;
import it.unirc.briante.orazio.uri.URITest2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ URITest.class, URITest2.class})
public class URITestsSuite {

}
