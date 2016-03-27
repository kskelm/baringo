package com.github.kskelm.imgurapi.test;

import org.junit.Test;
import junit.framework.TestCase;

import com.github.kskelm.imgurapi.Quota;
import com.github.kskelm.imgurapi.util.ImgurApiException;


public class QuotaTest extends TestCase {
	public QuotaTest( String testName ) {
		super( testName );
	}
	
	@Test
	public void testQuotaIsSetOnCall() throws ImgurApiException {
		Setup setup = new Setup();
		
		Quota q1 = setup.getClient().getQuota();
		int oldAppCredsAvail = q1.getApplicationCreditsAvailable();
		setup.getClient().accountService().getAccount( Setup.TEST_USER_NAME );

		Quota q2 = setup.getClient().getQuota();

		assertNotNull(" Quota after API call is not null", q2 );
		assertNotSame( "App creds updated", oldAppCredsAvail, q2.getApplicationCreditsAvailable() );	
	}
}
