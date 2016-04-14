/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.test;

import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

import com.github.kskelm.baringo.model.Comment;
import com.github.kskelm.baringo.model.ReportReason;
import com.github.kskelm.baringo.model.Vote;
import com.github.kskelm.baringo.util.BaringoApiException;


public class CommentTest extends TestCase {
	public CommentTest( String testName ) {
		super( testName );
	}

	@Test
	public void testGetComment() throws BaringoApiException {
		Setup setup = new Setup();

		Comment comm = setup.getClient().commentService().getComment( Setup.TEST_COMMENT_ID );

		assertEquals( "image id", comm.getImageId(), "G3xkE" );
		assertEquals( "author name", comm.getAuthorName(), "kskelmapitest" );
		assertEquals( "author id", comm.getAuthorId(), 33527752 );
		assertEquals( "parent id", comm.getParentId(), 617736106 );
		assertEquals( "create date", comm.getCreatedAt().getTime(), 1459694870000L );
		assertEquals( "no children", comm.getChildren().size(), 0 );
		assertEquals( "comment text", comm.getComment(), "test1" );
		assertEquals( "comment id", comm.getId(), 620866057 );
	}

	@Test
	public void testAddComment() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		
		String text = "bloopty!";
		long commentId = setup.getClient().commentService().addComment( Setup.TEST_GALLERY_ID, text );
		Comment comm = setup.getClient().commentService().getComment(commentId);
		setup.getClient().commentService().deleteComment( comm );
		
		assertEquals( "text came back", comm.getComment(), text );
	}

	@Test
	public void testDeleteComment() throws BaringoApiException {
		// this is implicit in testAddComment, which removes the mess
		// it just made. So yay-- passed!
	}

	@Test
	public void testGetReplies() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		
		List<Comment> list = setup.getClient().commentService().listReplies( Setup.TEST_COMMENT_ID_2 );
		assertTrue( "more than one came back", list.size() > 0 );
	}

	@Test
	public void testAddReply() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		
		Comment comm = setup.getClient().commentService().getComment( Setup.TEST_COMMENT_ID );
		long id = setup.getClient().commentService().addReply( comm, "blerpaderp" );
		List<Comment> list = setup.getClient().commentService().listReplies( Setup.TEST_COMMENT_ID );
		setup.getClient().commentService().deleteComment( id );
		assertTrue( "more than one came back", list.size() > 0 );
	}

	@Test
	public void testVote() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		// I'm happy with just being able to call without bombing out.
		Comment comm = setup.getClient().commentService().getComment( Setup.TEST_COMMENT_ID );
		setup.getClient().commentService().setVote( comm.getId(), Vote.Up );
	}

	@Test
	public void testReport() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		
		Comment comm = setup.getClient().commentService().getComment( Setup.TEST_COMMENT_ID );
		long id = setup.getClient().commentService().addReply( comm, "blerpaderp" );
		setup.getClient().commentService().report( id, ReportReason.ShouldBeMarkedNsfw );
		setup.getClient().commentService().deleteComment( id );

	}

}
