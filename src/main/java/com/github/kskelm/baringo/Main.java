package com.github.kskelm.baringo;

import com.github.kskelm.baringo.model.Account;
import com.github.kskelm.baringo.model.Comment;
import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.util.BaringoApiException;

public class Main {

	public static void main(String[] args) {
        String clientId = System.getProperty( BaringoClient.PROPERTY_CLIENT_ID );
        String clientSecret = System.getProperty( BaringoClient.PROPERTY_CLIENT_SECRET );
		BaringoClient client = null;
		try {
			client = new BaringoClient(clientId, clientSecret);
		} catch (BaringoApiException e1) {
			e1.printStackTrace();
			return;
		}
		
		try {
			Image img = client.imageService().getImageInfo( "G3xkE" );
			System.out.println( img );
		} catch (BaringoApiException e) {
			e.printStackTrace();
		}
		
		System.out.println( client.getQuota() );

		try {
			Account acct = client.accountService().getAccount( "kskelmapitest" );
			System.out.println( acct );
			System.out.println( client.accountService().listCommentIds( "kskelmapitest",  Comment.Sort.Newest, 0) );
		} catch (BaringoApiException e) {
			e.printStackTrace();
		}
/*		
		List<GalleryItem> items;
		try {
			items = svc.galleryService().getGallery(
					GalleryImage.Section.Hot,
					GalleryImage.Sort.Viral,
					0 );
			
			
			for( GalleryItem item : items ) {
				if( item == null ) {
					continue;
				} // if
				System.out.println( item );
			} // for
			
		} catch (BaringoApiException e) {
			e.printStackTrace();
		}
*/		
	}

}
