/**
 * Model object for albums found in a gallery,
 * extended with getRedditCommentUrl()
 */
package com.github.kskelm.baringo.model.gallery;

import java.util.HashMap;
import java.util.List;

import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author kskelm
// UPDATE: apparently Imgur doesn't really support this anymore
//  ... MARK THIS CLASS FOR DELETION
 */
public class GallerySubredditAlbum extends GalleryItem {

	/**
	 * Returns the URL to go see the related reddit comments
	 * @return the redditCommentsUrl
	 */
	public String getRedditCommentsUrl() {
		return redditCommentsUrl;
	}

	/**
	 * A GalleryAlbum has a privacy level. See @see <a href="https://help.imgur.com/hc/en-us/articles/201746817-Image-and-album-privacy-explained-">Imgur's documentation</a>.
	 */
	public enum Privacy {
		/**
		 * An album will be visible to public
		 */
		@SerializedName("public") Public,
		/**
		 * An album will be visible to public, but it won't show up in galleries
		 */
		@SerializedName("hidden") Hidden,
		/**
		 * An album will be invisible to everyone but its owner
		 */
		@SerializedName("secret") Secret
	}

	/**
	 * A GalleryAlbum has a layout. @see <a href="https://help.imgur.com/hc/en-us/articles/201746817-Image-and-album-privacy-explained-">Imgur's documentation</a>.
	 */
	public enum Layout {
		/**
		 * An album will be rendered in blog format
		 */
		@SerializedName("blog") Blog,
		/**
		 * An album will be rendered as a grid of images
		 */
		@SerializedName("grid") Grid,
		/**
		 * An album will be rendered with images side-by-side
		 */
		@SerializedName("horizontal") Horizontal,
		/**
		 * An album will be rendered vertically
		 */
		@SerializedName("vertical") Vertical
	}
	
	
	/**
	 * The image ID of the cover image for this album
	 * @return the cover
	 */
	public String getCoverId() {
		return coverId;
	}

	/**
	 * The width in pixels of the cover image
	 * @return the coverWidth
	 */
	public int getCoverWidth() {
		return coverWidth;
	}

	/**
	 * The height in pixels of the cover image
	 * @return the coverHeight
	 */
	public int getCoverHeight() {
		return coverHeight;
	}

	/**
	 * The privacy level of the album.  If an authenticated account
	 * is logged into the client, the public view cannot be accessed.
	 * Log out to see it.
	 * @return the privacy
	 */
	public Privacy getPrivacy() {
		return privacy;
	}

	/**
	 * The view layout of the album
	 * @return the layout
	 */
	public Layout getLayout() {
		return layout;
	}
	
	/**
	 * The total number of images in the album
	 * @return the imageCount
	 */
	public int getImageCount() {
		return imageCount;
	}

	/**
	 * Returns the list of Image objects
	 * in the GalleryAlbum.
	 * NOTE: This will only exist if the
	 * gallery album was requested
	 * directly.
	 * @return the images
	 */
	public List<Image> getImages() {
		return images;
	}
	
	public String toString() {
		HashMap<String,Object> fields = Utils.toHashMap( this, GallerySubredditAlbum.class );
		fields.putAll( Utils.toHashMap( this, GalleryAlbum.class ) );
		fields.putAll( Utils.toHashMap( this, GalleryItem.class ) );
		return Utils.toString( fields, GallerySubredditAlbum.class.getSimpleName() );
	} // toString
	
	// =========================================================

	// INTERNAL ONLY
	public GallerySubredditAlbum() { }

	// INTERNAL ONLY
	public GallerySubredditAlbum( GalleryItemProxy p ) {
		super( p );
		copyFrom( p, GallerySubredditAlbum.class );
	} // lame copy constructor
	
	protected String coverId;
	protected int coverWidth;
	protected int coverHeight;
	protected Privacy privacy;
	protected Layout layout;
	@SerializedName("images_count")
	protected int imageCount;
	protected List<Image> images;
int foo; // TODO CHECK THIS IS WHAT'S COMING BACK
	@SerializedName("reddit_comments")
	protected String redditCommentsUrl;
	
}
