package it.unirc.briante.orazio.suite;

import it.unirc.briante.orazio.comm.DiscoveryTest;
import it.unirc.briante.orazio.comm.SendInterestTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SendInterestTest.class, DiscoveryTest.class })
public class CommTestSuite {

}
