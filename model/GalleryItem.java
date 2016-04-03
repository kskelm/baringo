/**
 * Model object for items found in a gallery.
 * This is an abstract superclass for GalleryImage
 * and GalleryAlbum, which are both returned
 * by various Imgur APIs and they share a notable
 * number of characteristics.  Check the class
 * type of an instance and cast it to get the
 * appropriate version.
 * NOTE: Counter values tend to lag behind realtime.
 */
package com.github.kskelm.baringo.model;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

public abstract class GalleryItem {

	/**
	 * When requesting a gallery, this is the section
	 * 
	 */
	public enum Section {
		/**
		 * Used for returning galleries marked as hot, meaning they're trending.  This is the default in searches.
		 */
		@SerializedName("hot") Hot,  // default
		/**
		 * Used for returning galleries that are top rated
		 */
		@SerializedName("top") Top,
		/**
		 * Used for returning galleries associated with a specific user
		 */
		@SerializedName("user") User;

	}
	
	/**
	 * When requesting a gallery, this is the sort to use
	 */
	public enum Sort {
		/**
		 * Viral means lots of people are visiting this gallery
		 */
		@SerializedName("viral") Viral,  // default
		/**
		 * Top means lots of people like this gallery
		 */
		@SerializedName("top") Top,
		/**
		 * Time means galleries associated with current events
		 */
		@SerializedName("time") Time,
		/**
		 * Within the user section, galleries that are gaining popularity
		 */
		@SerializedName("rising") Rising;  // valid only with user section
	}
	
	/**
	 * When requesting the section type "top",
	 * this changes the date range of the request.
	 * The default is Day.
	 */
	public enum Window {
		/**
		 * Return items in the last day. Default
		 */
		@SerializedName("day") Day,  // default
		/**
		 * Return items in the last week
		 */
		@SerializedName("week") Week,
		/**
		 * Return items in the last month
		 */
		@SerializedName("month") Month,
		/**
		 * Return items in the last year
		 */
		@SerializedName("year") Year;
		
		@Override public String toString() {
			return this.toString().toLowerCase();
		}
	}

// TODO: COMMENT METHODS
	
	/**
	 * Returns the ID of the item
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the title of the item
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the description of the item
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the date/time on which the item was uploaded
	 * @return the uploadDate
	 */
	public Date getUploadDate() {
		return uploadDate;
	}

	/**
	 * Returns the number of times the item has been viewed.
	 * @return the views
	 */
	public int getViews() {
		return views;
	}

	/**
	 * Returns a link to directly view the item
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * If the account currently has a client logged in and the user has voted on this item, this will be the vote value.
	 * @return the vote
	 */
	public String getVote() {
		return vote;
	}

	/**
	 * Returns the number of upvotes this item has gotten
	 * @return the ups
	 */
	public int getUps() {
		return ups;
	}

	/**
	 * Returns the number of downvotes this item has gotten
	 * @return the downs
	 */
	public int getDowns() {
		return downs;
	}

	/**
	 * upvotes minus downvotes because math
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Popularity score
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Returns true if the account currently has a client logged in and the user favorited this item.
	 * @return the favorite
	 */
	public boolean isFavorite() {
		return favorite;
	}

	/**
	 * Returns whether this item has been marked as Not Safe For Work
	 * @return the nsfw
	 */
	public boolean isNsfw() {
		return nsfw;
	}

	/**
	 * Returns the total number of comments on the item
	 * @return the commentCount
	 */
	public int getCommentCount() {
		return commentCount;
	}

	/**
	 * Returns a preview of the first 10 comments on the item.  Not all fields are filled in.
	 * @return the commentPreview
	 */
	public List<Comment> getCommentPreview() {
		return commentPreview;
	}

	/**
	 * Returns the topic of the item
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * Returns the ID of the topic of the item
	 * @return the topicId
	 */
	public int getTopicId() {
		return topicId;
	}

	/**
	 * Returns the userName of the user that uploaded the item
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Returns the account id of the user that uploaded the item
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Returns whether or not this item is an album.  If true, it
	 * can be cast to GalleryAlbum.  If false, it can be cast to
	 * GalleryImage.
	 * @return the album
	 */
	public boolean isAlbum() {
		return album;
	}

	
	
	// =========================================================
	
	public GalleryItem() { }

	protected GalleryItem( GalleryItemProxy p ) {
		copyFrom( p, GalleryItem.class );
	} // lame copy constructor
	
	protected String id;
	protected String title;
	protected String description;
	@SerializedName("datetime")
	protected Date uploadDate;
	protected int views;
	protected String link;
	protected String vote;
	protected int ups;
	protected int downs;
	protected int points;
	protected int score;
	protected boolean favorite;
	protected boolean nsfw;
	@SerializedName("comment_count")
	protected int commentCount;
	@SerializedName("comment_preview")
	protected List<Comment> commentPreview;
	protected String topic;
	@SerializedName("topic_id")
	protected int topicId;
	@SerializedName("account_url")
	protected String userName;
	@SerializedName("account_id")
	protected int userId;
	@SerializedName("is_album")
	protected boolean album;
	
	public String toString() {
		return Utils.toString( this );
	} // toString

//	/**
//	 * (INTERNAL) Shallow copy the fields from one object to another, given
//	 * named fields in a specific level of the inheritance.
//	 * This makes me sad.
//	 * @param from - destination object
//	 * @param cls - the class to enumerate fields on
//	 */
	public void copyFrom( GalleryItemProxy from, @SuppressWarnings("rawtypes") Class cls ) {
		for( Field toField : cls.getDeclaredFields() ) {
			try {
				Field fromField = from.getClass().getField( toField.getName() );
				if( fromField == null ) { // doesn't exist in the source
					continue;
				} // if
				Object val = fromField.get( from );
				toField.set( this, val );
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		} // for
	} // copyFrom
}
