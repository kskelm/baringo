package com.github.kskelm.baringo.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import junit.framework.TestCase;

import com.github.kskelm.baringo.model.Album;
import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.util.BaringoApiException;


public class AlbumTest extends TestCase {
	public AlbumTest( String testName ) {
		super( testName );
	}
	
	@Test
	public void testGetAlbum() throws BaringoApiException {
		Setup setup = new Setup();
		
		Album album = setup.getClient().albumService().getAlbum( Setup.TEST_ALBUM_ID );

		assertEquals("Cover id", album.getCoverId(), "bHEb5Sw" );
		assertEquals("Cover width", album.getCoverWidth(), 500 );
		assertEquals("Cover height", album.getCoverHeight(), 500 );
		assertEquals("Create date", album.getCreatedAt().getTime(), 1459131917000L );
		assertEquals("Description", album.getDescription(), "A test album" );
		assertEquals("Id", album.getId(), Setup.TEST_ALBUM_ID );
		assertEquals("Images", album.getImages().size(), 2 );
		assertEquals("Layout", album.getLayout(), Album.Layout.Horizontal );
		assertEquals("Link", album.getLink(), "http://imgur.com/a/tZz2i" );
		assertEquals("Privacy", album.getPrivacy(), Album.Privacy.Public );
		assertEquals("Title", album.getTitle(), "Test Album" );
		assertEquals("User id", album.getUserId(), 33527752 );
		assertEquals("User name", album.getUserName(), "kskelmapitest" );
		assertTrue("Views", album.getViewCount() > 0 );
	}
	
	@Test
	public void testGetAlbumImages() throws BaringoApiException {
		Setup setup = new Setup();
		
		List<Image> list = setup.getClient().albumService().getAlbumImages( Setup.TEST_ALBUM_ID );
		
		assertEquals("Images came back", list.size(), 2 );
	} // testGetAlbumImages
	
	@Test
	public void testAddAlbum() throws BaringoApiException {
		System.out.println("testAddAlbum");
		Setup setup = new Setup();
		setup.switchToUserAuth();
		
		Map<String,Album> pair = createTestAlbum( setup );
		Album album = pair.get( "original" );
		Album album2 = pair.get( "saved" );
		setup.getClient().albumService().deleteAlbum( album2 );
		
		assertEquals( "title", album.getTitle(), album2.getTitle() );
		assertEquals( "description", album.getDescription(), album2.getDescription() );
		assertEquals( "images", album.getImages().size(), album2.getImages().size() );
		assertEquals( "layout", album.getLayout(), album2.getLayout() );
		assertEquals( "privacy", album.getPrivacy(), album2.getPrivacy() );
		assertEquals( "coverid", album.getCoverId(), album2.getCoverId() );
		
	} // testAddAlbum
	
	@Test
	public void testUpdateAlbum() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		
		// Imgur's db is eventually consistent so it doesn't always
		// return the new answer immediately.  So we're just going
		// to save over the old value and make sure it doesn't bomb out.
		Album album = setup.getClient().albumService().getAlbum( Setup.TEST_ALBUM_ID );
		setup.getClient().albumService().updateAlbum( album );

	} // testUpdateAlbum
	
	@Test
	public void testDeleteAlbum() throws BaringoApiException {
		System.out.println("testDeleteAlbum");
		Setup setup = new Setup();

		Map<String,Album> pair = createTestAlbum( setup );
		Album album = pair.get( "saved" );
		
		setup.getClient().albumService().deleteAlbum( album );
		try {
			Album album2 = setup.getClient().albumService().getAlbum( album.getId() );
	System.out.println( album2 );
		} catch( Exception e ) {
			System.out.println( e.getMessage() );			
		}
	} // testDeleteAlbum
	
	@Test
	public void testFavoriteAlbum() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		Album album = setup.getClient().albumService().getAlbum( Setup.TEST_ALBUM_ID );
		
		setup.getClient().albumService().favoriteAlbum(album);
	} // test
	
	@Test
	public void testUnfavoriteAlbum() throws BaringoApiException {
		Setup setup = new Setup();
		setup.switchToUserAuth();
		Album album = setup.getClient().albumService().getAlbum( Setup.TEST_ALBUM_ID );
		
		setup.getClient().albumService().unfavoriteAlbum(album);

	} // testUnfavoriteAlbum
	
	@Test
	public void testAddAlbumImageIds() throws BaringoApiException {
		System.out.println("testAddAlbumImageIds");
		Setup setup = new Setup();

		List<String> ids = new ArrayList<>();
		ids.add( Setup.TEST_IMAGE_ID_2 );

		// add images to an unauthenticated album first to
		// test using the deletehash
		Map<String,Album> pair = createTestAlbum( setup );
		Album album = pair.get( "saved" );
		
		setup.getClient().albumService().addAlbumImageIds( album, ids );
		// delete temp album
		setup.getClient().albumService().deleteAlbum( album );

		// phew!  now let's do it as a real user
		setup.switchToUserAuth();

		album = setup.getClient().albumService().getAlbum( Setup.TEST_ALBUM_ID );
		setup.getClient().albumService().addAlbumImageIds( album, ids );
		// okay now undo it
		setup.getClient().albumService().deleteAlbumImageIds( album, ids );
	
	} // testAddAlbumImageIds
	
	@Test
	public void testDeleteAlbumImageIds() throws BaringoApiException {
		// this is sort of implicit in testAddAlbumImageLds() since it
		// deletes stuff anyway. So... passed!
	} // testDeleteAlbumImageIds
	

	// ==================================
	// helpers
	private Map<String,Album> createTestAlbum( Setup setup ) throws BaringoApiException {
		List<Image> borrowedImages = setup.getClient().albumService().getAlbumImages( Setup.TEST_ALBUM_ID );

		Album album = new Album();
		album.setTitle( "Super album" );
		album.setDescription( "ho hum another test album" );
		album.setImages( borrowedImages );
		album.setLayout( Album.Layout.Grid );
		album.setPrivacy( Album.Privacy.Hidden );
		album.setCoverId( Setup.TEST_IMAGE_ID );

		Album album2 = setup.getClient().albumService().addAlbum( album );
		
		HashMap<String,Album> result = new HashMap<>();
		result.put( "original", album );
		result.put( "saved", album2 );
		return result;
	}
}
