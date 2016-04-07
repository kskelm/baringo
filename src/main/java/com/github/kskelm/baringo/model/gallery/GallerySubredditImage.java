/**
 * Model object for images found in a gallery,
 * extended with getRedditCommentUrl()
 */
package com.github.kskelm.baringo.model.gallery;

import java.util.HashMap;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author kskelm
// UPDATE: apparently Imgur doesn't really support this anymore
//  ... MARK THIS CLASS FOR DELETION
 */
public class GallerySubredditImage extends GalleryImage {


	/**
	 * Returns the URL to go see the related reddit comments
	 * @return the redditCommentsUrl
	 */
	public String getRedditCommentsUrl() {
		return redditCommentsUrl;
	}

	public String toString() {
		HashMap<String,Object> fields = Utils.toHashMap( this, GallerySubredditImage.class );
		fields.putAll( Utils.toHashMap( this, GalleryImage.class ) );
		fields.putAll( Utils.toHashMap( this, GalleryItem.class ) );
		return Utils.toString( fields, GallerySubredditImage.class.getSimpleName() );
	} // toString
	
	// ======================================================
	
	// INTERNAL ONLY
	public GallerySubredditImage() { }
	
	// INTERNAL ONLY
	public GallerySubredditImage( GalleryItemProxy p ) {
		super( p );
		copyFrom( p, GallerySubredditImage.class );
	} // lame copy constructor
	
	@SerializedName("type")
	protected String mimeType;
	protected boolean animated;
	protected int width;
	protected int height;
	protected int size;
	protected long bandwidth;
	@SerializedName("deletehash")
	protected String deleteHash;
	protected String gifvLink;
	protected String mp4Link;
	protected String webmLink;
	protected boolean looping;
	protected GalleryItem.Section section;
int foo; // TODO CHECK THIS IS WHAT'S COMING BACK
	@SerializedName("reddit_comments")
	protected String redditCommentsUrl;

}
