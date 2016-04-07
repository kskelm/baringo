package com.github.kskelm.baringo.test;

import java.util.List;

import org.junit.Test;
import junit.framework.TestCase;

import com.github.kskelm.baringo.model.Conversation;
import com.github.kskelm.baringo.model.Message;
import com.github.kskelm.baringo.util.BaringoApiException;


public class ConversationTest extends TestCase {
	public ConversationTest( String testName ) {
		super( testName );
	}
	
	@Test
	public void testGetConversations() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		
		List<Conversation> list = setup.getClient()
				.conversationService().getConversations();

		assertFalse( "conversations came back", list.isEmpty() );

		for( Conversation conv : list ) {
			testConversationObject( conv );
		} // for
	}

	@Test
	public void testGetConversationWithMessages() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		
		List<Conversation> list = setup.getClient()
				.conversationService().getConversations();

		assertFalse( "conversations came back", list.isEmpty() );
		Conversation conv = list.get( 0 );
		conv = setup.getClient().conversationService()
				.getConversationWithMessages( conv.getId(), 0 );

		testConversationObject( conv );
	}

	@Test
	public void testSendandDeleteMessage() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		
		// send a message to myself
		boolean success = setup.getClient()
				.conversationService()
				.sendMessage( Setup.TEST_USER_NAME, "Well _met_, sirrah." );

		assertTrue( "successful send", success );
		
		List<Conversation> list = setup.getClient()
				.conversationService().getConversations();
		assertFalse( "conversations came back", list.isEmpty() );
		Conversation conv = list.get( 0 );
		
		success = setup.getClient()
				.conversationService()
				.deleteConversation( conv.getId() );
		
		assertTrue( "successful delete", success );

	}

// NOT going to call this routinely.  Who knows what it would stir up at Imgur
//	@Test
//	public void testReportSender() throws BaringoApiException {
//		Setup setup = new Setup();
//		setup.switchToUserAuth();
//
//		boolean success = setup.getClient()
//				.conversationService().reportSender( Setup.TEST_USER_NAME );
//
//		assertTrue( "successful report", success );
//	}
//
// NOT going to call this routinely.  Who knows what it would stir up at Imgur
//	@Test
//	public void testBlockSender() throws BaringoApiException {
//		Setup setup = new Setup();
//		setup.switchToUserAuth();
//
//		boolean success = setup.getClient()
//				.conversationService().blockSender( Setup.TEST_USER_NAME );
//
//		assertTrue( "successful block", success );
//	}

	// =======================================================
	// stuff so we can all test everything
	
	public static void testConversationObject( Conversation conv ) {
		assertTrue( "author id", conv.getAuthorId() > 0 );
		assertNotNull( "author name", conv.getAuthorName() );
		assertNotNull( "msg date", conv.getLastMessageDate() );
		assertNotNull( "msg preview", conv.getLastMessagePreview() );
		assertTrue( "has count", conv.getMessageCount() > 0 );
		for( Message msg : conv.getMessages() ) {
			testMessageObject( msg );
		} // for
	}

	public static void testMessageObject( Message msg ) {
		assertTrue( "id", msg.getId() > 0 );
		assertNotNull( "body", msg.getBody() );
		assertTrue( "conv id", msg.getConversationId() > 0 );
		assertTrue( "from user id", msg.getFromUserId() > 0 );
		assertTrue( "to user id", msg.getToUserId() > 0 );
		assertNotNull( "from user name", msg.getFromUserName() );
		assertTrue( "send date", msg.getSendDate().getTime() > 0 );
	}

}
