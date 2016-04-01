package com.github.kskelm.baringo.test;

import org.junit.Test;
import junit.framework.TestCase;

import com.github.kskelm.baringo.Quota;
import com.github.kskelm.baringo.util.BaringoApiException;


public class QuotaTest extends TestCase {
	public QuotaTest( String testName ) {
		super( testName );
	}
	
	@Test
	public void testQuotaIsSetOnCall() throws BaringoApiException {
		Setup setup = new Setup();
		
		Quota q1 = setup.getClient().getQuota();
		int oldAppCredsAvail = q1.getApplicationCreditsAvailable();
		setup.getClient().accountService().getAccount( Setup.TEST_USER_NAME );

		Quota q2 = setup.getClient().getQuota();

		assertNotNull(" Quota after API call is not null", q2 );
		assertNotSame( "App creds updated", oldAppCredsAvail, q2.getApplicationCreditsAvailable() );	
	}
}
