package com.github.kskelm.baringo.test;

import java.util.List;

import org.junit.Test;
import junit.framework.TestCase;

import com.github.kskelm.baringo.model.Notification;
import com.github.kskelm.baringo.util.BaringoApiException;

/**
 * 
 * @author kskelm
 *
 */
public class NotificationTest extends TestCase {
	public NotificationTest( String testName ) {
		super( testName );
	}	


	@Test
	public void testListReplyNotifications() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		List<Notification> list = setup.getClient()
				.notificationService().listReplyNotifications( false );
	
		assertNotNull( "Notification list came back", list );
		for( Notification note : list ) {
			testNotificationObject( note );
		} // for
	}
	
	@Test
	public void testListMessageNotifications() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		List<Notification> list = setup.getClient()
				.notificationService().listMessageNotifications( false );
		
		assertNotNull( "Notification list came back", list );
		for( Notification note : list ) {
			testNotificationObject( note );
		} // for
	}
	
	@Test
	public void testGetNotification() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		List<Notification> list = setup.getClient()
				.notificationService().listMessageNotifications( false );
		
		assertNotNull( "Notification list came back", list );
		Notification note = list.get( 0 );
		
		Notification note2 = setup.getClient()
				.notificationService().getNotification( note.getId() );
		testNotificationObject( note2 );
	}
	
	@Test
	public void testMarkNotificationViewed() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();

		List<Notification> list = setup.getClient()
				.notificationService().listMessageNotifications( false );
		
		assertNotNull( "Notification list came back", list );
		Notification note = list.get( 0 );

		setup.getClient()
				.notificationService().markNotificiationViewed( note.getId() );
		
		// nothing to test on that one other than it didn't crash.
	}
	

	// ============================================================
	
	public static void testNotificationObject( Notification note ) {
		assertTrue( "id", note.getId() > 0 );
		assertTrue( "user id", note.getUserId() > 0 );
	}

}
