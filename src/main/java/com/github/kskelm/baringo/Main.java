package com.github.kskelm.baringo;

import com.github.kskelm.baringo.model.Account;
import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.util.ImgurApiException;

public class Main {

	public static void main(String[] args) {
	    // Optimally these would come from a property file or somesuch.
	    String clientId = args[0];  // PUT YOUR CLIENT ID HERE!
	    String clientSecret = args[1]; // PUT YOUR CLIENT SECRET HERE!
		ImgurClient svc = new ImgurClient(clientId, clientSecret);
		
		try {
			Image img = svc.imageService().getImageInfo( "PgZtz0j" );
			System.out.println( img );
		} catch (ImgurApiException e) {
			e.printStackTrace();
		}
		
		System.out.println( svc.getQuota() );

		try {
			Account acct = svc.accountService().getAccount( "kskelmapitest" );
			System.out.println( acct );
		} catch (ImgurApiException e) {
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
			
		} catch (ImgurApiException e) {
			e.printStackTrace();
		}
*/		
	}

}