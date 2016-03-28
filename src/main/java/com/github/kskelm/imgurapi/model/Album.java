/**
 * Model object for (non-gallery) albums.
 */
package com.github.kskelm.imgurapi.model;

import java.util.Date;
import java.util.List;

import com.github.kskelm.imgurapi.util.Utils;
import com.google.gson.annotations.SerializedName;

public class Album {

	/**
	 * An Album has a privacy level. {@link https://help.imgur.com/hc/en-us/articles/201746817-Image-and-album-privacy-explained-}
	 */
	public enum Privacy {
		@SerializedName("public") Public,
		@SerializedName("hidden") Hidden,
		@SerializedName("secret") Secret
	}

	/**
	 * An Album has a layout. {@link https://help.imgur.com/hc/en-us/articles/201746817-Image-and-album-privacy-explained-}
	 */
	public enum Layout {
		@SerializedName("blog") Blog,
		@SerializedName("grid") Grid,
		@SerializedName("horizontal") Horizontal,
		@SerializedName("vertical") Vertical
	}

	
	
	// =========================================================

	/**
	 * The id for the album
	 * @return the id
	 */
	public int getId() {
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
	 * The width in pixels of the cover
	 * @return the coverWidth
	 */
	public int getCoverWidth() {
		return coverWidth;
	}


	/**
	 * The height in pixels of the cover
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
	 * The view layout of the album
	 * @return the layout
	 */
	public Layout getLayout() {
		return layout;
	}


	/**
	 * The number of times the album has been viewed
	 * @return the views
	 */
	public int getViews() {
		return views;
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
	 * Returns how many images are in the album total
	 * @return the imageCount
	 */
	public int getImageCount() {
		return imageCount;
	}


	/**
	 * An array of all of the images in the album (but only when the album
	 * is being requested directly via its ID)
	 * @return the images
	 */
	public List<Image> getImages() {
		return images;
	}


	// INTERNAL ONLY
	public Album() { }

	private int id;
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
	private Layout layout;
	private int views;
	private String link;
	private boolean favorite;
	private boolean nsfw;
	private String section;
	@SerializedName("deletehash")
	private String deleteHash;
	@SerializedName("images_count")
	private int imageCount;
	private List<Image> images;
	

	public String toString() {
		return Utils.toString( this );
	} // toString
	
} // Album
