package com.github.kskelm.baringo.test;

import java.util.List;

import org.junit.Test;
import junit.framework.TestCase;

import com.github.kskelm.baringo.model.Comment;
import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.model.ReportReason;
import com.github.kskelm.baringo.model.TagGallery;
import com.github.kskelm.baringo.model.TagVote;
import com.github.kskelm.baringo.model.Vote;
import com.github.kskelm.baringo.model.Votes;
import com.github.kskelm.baringo.model.gallery.GalleryImage;
import com.github.kskelm.baringo.model.gallery.GalleryItem;
import com.github.kskelm.baringo.model.search.CompoundSearchQuery;
import com.github.kskelm.baringo.model.search.SearchQuery;
import com.github.kskelm.baringo.util.BaringoApiException;


public class GalleryTest extends TestCase {
	public GalleryTest( String testName ) {
		super( testName );
	}
	
	@Test
	public void testListGallery() throws BaringoApiException {
		Setup setup = new Setup();
		
		List<GalleryItem> items = setup.getClient()
				.galleryService().listGallery(
						GalleryItem.Section.Top,
						GalleryItem.Sort.Time,
						GalleryItem.Window.Month,
						true,
						0 );
		
		assertFalse( "something came back", items.isEmpty() );

	}

	@Test
	public void testListMemeGallery() throws BaringoApiException {
		Setup setup = new Setup();
		
		List<GalleryItem> items = setup.getClient()
				.galleryService().listMemeGallery(
						GalleryItem.Sort.Time,
						GalleryItem.Window.Month,
						0 );
		assertFalse( "something came back", items.isEmpty() );
	}

	@Test
	public void testListSubredditGallery() throws BaringoApiException {
		Setup setup = new Setup();
		
		List<GalleryItem> items = setup.getClient()
				.galleryService().listSubredditGallery(
						"funny",
						GalleryItem.Sort.Time,
						GalleryItem.Window.Month,
						0 );
		assertFalse( "something came back", items.isEmpty() );

	}

// apparently this isn't supported anymore
//	@Test
//	public void testGetMemeImageInfo() throws BaringoApiException {
//		Setup setup = new Setup();
//		
//		GalleryImage image = setup.getClient()
//				.galleryService().getMemeImageInfo( Setup.TEST_MEME_IMAGE_ID );
//		
//		assertNotNull( "Image came back", image );
//	}

	@Test
	public void testGetSubredditImageInfo() throws BaringoApiException {
		Setup setup = new Setup();
		
		GalleryImage image = setup.getClient()
				.galleryService().getSubredditImageInfo(
						Setup.TEST_SUBREDDIT_NAME,
						Setup.TEST_SUBREDDIT_IMAGE_ID );
		
		assertNotNull( "Image came back", image );
	}

	@Test
	public void testGetTagGallery() throws BaringoApiException {
		Setup setup = new Setup();
		
		TagGallery gal = setup.getClient()
				.galleryService().getTagGallery(
						"astronomy",
						GalleryItem.Sort.Time,
						GalleryItem.Window.Month,
						0 );
		
		assertEquals( "name", gal.getName(), "astronomy" );
		assertTrue( "count not 0", gal.getItemCount() > 0 );
		assertTrue( "items loaded", !gal.getItems().isEmpty() );
	}

	@Test
	public void testGetImageInfo() throws BaringoApiException {
		Setup setup = new Setup();
		
		Image image = setup.getClient()
				.galleryService().getImageInfo( Setup.TEST_IMAGE_ID );
				
		assertNotNull( "Image came back", image );
	}

	@Test
	public void testGetGalleryItemTagVotes() throws BaringoApiException {
		Setup setup = new Setup();
		
		List<TagVote> list = setup.getClient()
				.galleryService().getGalleryItemTagVotes( Setup.TEST_IMAGE_ID );
		
		assertTrue( "no tag votes", list.isEmpty() );
		
		list = setup.getClient()
				.galleryService().getGalleryItemTagVotes( Setup.TEST_IMAGE_WITH_TAGS );

		assertFalse( "tag votes came back", list.isEmpty() );
		
		TagVote vote = list.get( 0 );
		assertNotNull( "tag has name", vote.getTag() );
		assertNotNull( "tag has author", vote.getUserName() );
	}

	@Test
	public void testVoteGalleryItemTag() throws BaringoApiException {
		Setup setup = new Setup()
				.switchToUserAuth();
		
		setup.getClient()
				.galleryService().voteGalleryItemTag(
						Setup.TEST_GALLERY_ID,
						"astronomy",
						Vote.Up );
		// got this far.
	}

	@Test
	public void testSearchGallery() throws BaringoApiException {
		Setup setup = new Setup();
	
		// test getting something back by exact match
		SearchQuery query = new SearchQuery()
				.thisPhrase( "ignore this post" );
		List<GalleryItem> list = setup.getClient()
				.galleryService().searchGallery(
						query,
						GalleryItem.Sort.Time,
						GalleryItem.Window.All,
						0);
		assertFalse( "exact match came back", list.isEmpty() );

		// test exclusion
		query = new SearchQuery()
				.thisPhrase( "ignore this post" )
				.notThisPhrase( "ignore this post" );
		list = setup.getClient()
				.galleryService().searchGallery(
						query,
						GalleryItem.Sort.Time,
						GalleryItem.Window.All,
						0);
		assertTrue( "excluded didn't come back", list.isEmpty() );

		// test "and"
		query = new SearchQuery()
				.allWords( "dog,cat" );
		list = setup.getClient()
				.galleryService().searchGallery(
						query,
						GalleryItem.Sort.Time,
						GalleryItem.Window.All,
						0);
		assertFalse( "'and' word match came back", list.isEmpty() );

		// test "or"
		query = new SearchQuery()
				.anyWords( "dog,cat" );

		list = setup.getClient()
				.galleryService().searchGallery(
						query,
						GalleryItem.Sort.Time,
						GalleryItem.Window.All,
						0);
		assertFalse( "'or' word match came back", list.isEmpty() );

		// test image type
		query = new SearchQuery()
				.anyWords( "instant,cat" )
				.itemType( SearchQuery.ItemType.anigif );

		list = setup.getClient()
				.galleryService().searchGallery(
						query,
						GalleryItem.Sort.Time,
						GalleryItem.Window.All,
						0);
		assertFalse( "item type match came back", list.isEmpty() );

		// constrict down size now
		query = new SearchQuery()
				.anyWords( "instant,cat" )
				.itemType( SearchQuery.ItemType.anigif )
				.sizeRange( SearchQuery.SizeRange.small );

		list = setup.getClient()
				.galleryService().searchGallery(
						query,
						GalleryItem.Sort.Time,
						GalleryItem.Window.All,
						0);
		assertFalse( "item type match came back", list.isEmpty() );

		// let's try a compound query
		CompoundSearchQuery q2 = new CompoundSearchQuery()
				.title( "dog" )
					.or( "cat" );
		list = setup.getClient()
				.galleryService().searchGallery(
						q2,
						GalleryItem.Sort.Time,
						GalleryItem.Window.All,
						0);
		
		assertFalse( "item type match came back", list.isEmpty() );
	}

	@Test
	public void testCompoundQueryCreation() throws BaringoApiException {

		CompoundSearchQuery query = new CompoundSearchQuery()
 			.user( "kevinkelm" )
 				.or( "baringoapi" )
 			.title( "dog" )
 				.and( "cat" )
 			.extension( "jpg" )
 				.and( "png" )
 			.meme( "sad_alligator")
 				.or( "adviceowl" )
 			.album( "astronomy" )
 				.and( "diy" )
 				.and( "asspennies" );
		
		assertEquals( query.toString(), "user: kevinkelm OR baringoapi"
				+ " title: dog AND cat ext: jpg AND png album: astronomy"
				+ " AND diy AND asspennies meme: sad_alligator OR adviceowl" );
	}

	@Test
	public void testGetRandomGallery() throws BaringoApiException {
		Setup setup = new Setup();
		
		List<GalleryItem> list = setup.getClient()
				.galleryService().getRandomGallery( 0 );

		assertFalse( "something came back", list.isEmpty() );
	}


// commenting this out for now because it stirs trouble with
// imgur to try sharing more than once every 60 mins and they
// don't helpfully return a 429, they return their blanket 400
// and the reason has to be fished out of the error response and
// string matched for english text.  Which is a bummer.
//	@Test
//	public void testShareAndUnshareItem() throws BaringoApiException {
//		Setup setup = new Setup();
//		setup.switchToUserAuth();
//		
//		boolean success = setup.getClient()
//				.galleryService().shareItem(
//						Setup.TEST_IMAGE_ID_3,
//						"title for api testing, ignore me",
//						Setup.TEST_TOPIC_ID,
//						true,
//						false );
//
//		assertTrue( "share worked", success );
//		
//		success = setup.getClient()
//				.galleryService().unshareItem( Setup.TEST_IMAGE_ID_3 );
//
//		assertTrue( "unshare worked", success );
//	}


// don't actually want to call this all the time because
// who knows what havoc that would wreak
//	@Test
	public void testReportItem() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		
		setup.getClient()
				.galleryService().reportItem(
						Setup.TEST_IMAGE_ID_3,
						ReportReason.DoesntBelongOnImgur );

	}

	@Test
	public void testGetItemVotes() throws BaringoApiException {
		Setup setup = new Setup();
		
		Votes votes = setup.getClient()
				.galleryService().getItemVotes( Setup.TEST_GALLERY_ID );
		
		assertTrue( "downvotes", votes.getDownVotes() > 0 );
		assertTrue( "upvotes", votes.getUpVotes() > 0 );
	}

	@Test
	public void testGetItemComments() throws BaringoApiException {
		Setup setup = new Setup();
		
		List<Comment> list = setup.getClient()
				.galleryService().getItemComments(
						Setup.TEST_GALLERY_ID,
						GalleryItem.Sort.Best );

		assertFalse( "something came back", list.isEmpty() );

	}

	@Test
	public void testGetItemCommentIds() throws BaringoApiException {
		Setup setup = new Setup();
		
		List<Long> list = setup.getClient()
				.galleryService().getItemCommentIds(
						Setup.TEST_GALLERY_ID );

		assertFalse( "something came back", list.isEmpty() );
	}

	@Test
	public void testGetItemCommentCount() throws BaringoApiException {
		Setup setup = new Setup();
		
		long count = setup.getClient()
				.galleryService().getItemCommentCount(
						Setup.TEST_GALLERY_ID );

		assertTrue( "there are comments", count > 0 );
	}


}
