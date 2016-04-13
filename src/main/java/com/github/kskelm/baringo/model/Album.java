/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.model;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * Model object for (non-gallery) albums. GalleryAlbum objects are
 * different in the sense that they contain a subset of information
 * pertinent to listings of items in galleries.
 * @author Kevin Kelm (triggur@gmail.com)
 *
 */
public class Album {


	/**
	 * An Album has a privacy level. See <a href="https://help.imgur.com/hc/en-us/articles/201746817-Image-and-album-privacy-explained-">Imgur's documentation</a>.
	 */
	public enum Privacy {
		/**
		 * An album will be visible to public. Default.
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
	 * An Album has a layout. See <a href="https://help.imgur.com/hc/en-us/articles/201746817-Image-and-album-privacy-explained-">Imgur's documentation</a>.
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
	 * The id for the album
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * The title of the album
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * The description of the album
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * The date/time the album was created
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}


	/**
	 * The image ID of the album cover image
	 * @return the coverId
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
	 * The userName of the account that created the album
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * The userId of the account that created the album
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}


	/**
	 * The privacy setting of the album
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
	 * The number of times the album has been viewed
	 * @return the view count
	 */
	public int getViewCount() {
		return viewCount;
	}


	/**
	 * The URL of the album
	 * @return the link
	 */
	public String getLink() {
		return link;
	}


	/**
	 * If an authenticated account is logged into the client,
	 * returns true if the user favorited this album
	 * @return the favorite
	 */
	public boolean isFavorite() {
		return favorite;
	}


	/**
	 * Returns true if the album is marked as nsfw. Defaults to false.
	 * @return the nsfw
	 */
	public boolean isNsfw() {
		return nsfw;
	}


	/**
	 * If the image has been categorized by the back-end, this will
	 * name the section it belongs in. (funny, cats, adviceanimals, wtf, etc)
	 * @return the section
	 */
	public String getSection() {
		return section;
	}


	/**
	 * If an authenticated account is logged into the client, this
	 * will contain the deleteHash necessary to ask Imgur to delete the
	 * album.
	 * @return the deleteHash
	 */
	public String getDeleteHash() {
		return deleteHash;
	}

	/**
	 * An array of all of the images in the album (but only when the album
	 * is being requested directly via its ID)
	 * @return the images
	 */
	public List<Image> getImages() {
		return images;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Set the description for the album
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * Set the id of the cover image for the album
	 * @param coverId the coverId to set
	 */
	public void setCoverId(String coverId) {
		this.coverId = coverId;
	}


	/**
	 * Set the privacy level for the album
	 * @param privacy the privacy to set
	 */
	public void setPrivacy(Privacy privacy) {
		this.privacy = privacy;
	}


	/**
	 * Set the layout of the album
	 * @param layout the layout to set
	 */
	public void setLayout(Layout layout) {
		this.layout = layout;
	}


	/**
	 * Set whether the album is favorited by
	 * the currently-logged-in account.  Does
	 * nothing if not logged in.
	 * @param favorite the favorite to set
	 */
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}


	/**
	 * The list of images in the album.
	 * @param images the images to set
	 */
	public void setImages(List<Image> images) {
		this.images = images;
	}

	/**
	 * This is for determining the proper ID
	 * to send Imgur when referencing this
	 * album.  If the album is anonymous,
	 * return the deletehash instead of the
	 * album id.
	 * @return the id imgur will need to work on the album
	 */
	public String getAPIReferenceKey() {
		if( this.userId == 0 ) {
			return this.deleteHash;
		} else {
			return this.id;
		} // if-else
	} // getAPIReferenceKey
	
	public String toString() {
		return Utils.toString( this );
	} // toString


	// =========================================================

	// Internal only -- copy Image array to id array for save
	public void prepareForSave() {
		ids = null;
		if( images != null && images.size() > 0 ) {
			this.ids = new String[images.size()];
			int index = 0;
			for( Image image : images ) {
				this.ids[index++] = image.getId();
			} // for
		} // if
	} // prepareForSave
	
	
	private String id;
	private String title;
	private String description;
	@SerializedName("datetime")
	private Date createdAt;
	@SerializedName("cover")
	private String coverId;
	@SerializedName("cover_width")
	private int coverWidth;
	@SerializedName("cover_height")
	private int coverHeight;
	@SerializedName("account_url")
	private String userName;
	@SerializedName("account_id")
	private int userId;
	private Privacy privacy = Privacy.Public;
	private Layout layout = Layout.Vertical;
	@SerializedName("views")
	private int viewCount;
	private String link;
	private boolean favorite;
	private boolean nsfw;
	private String section;
	@SerializedName("deletehash")
	private String deleteHash;
	private String[] ids;
	private List<Image> images;
	
	/**
	 * internal only
	 * @param hash set the delete hash (result of saving to imgur)
	 */
	public void setDeleteHash( String hash ) {
		this.deleteHash = hash;
	}

	/**
	 * internal only
	 * @param src Album object to copy from
	 */
	public void copyFrom( Album src ) {
        Field[] fields = Album.class.getDeclaredFields();
        for (Field field : fields) {
        		field.setAccessible( true );
        	    try {
					field.set( this, field.get( src ) );
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
        } // for
	} // copyFrom
} // Album
