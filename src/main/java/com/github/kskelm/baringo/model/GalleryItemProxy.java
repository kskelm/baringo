/**
 * INTERNAL ONLY - don't use this for anything
 */
package com.github.kskelm.baringo.model;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.github.kskelm.baringo.model.GalleryAlbum.Layout;
import com.github.kskelm.baringo.model.GalleryAlbum.Privacy;
import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

// sometimes you do something that makes you feel dirty inside
@SuppressWarnings("unused")
public class GalleryItemProxy {

// TODO: SWITCH THIS OVER TO THE JSONDESERIALIZER APPROACH USED IN COMMENTSERVICE
	public boolean isAlbum() {
		return album;
	}
	
	public String id;
	public String title;
	public String description;
	@SerializedName("datetime")
	public Date uploadDate;
	public int views;
	public String link;
	public String vote;
	public int ups;
	public int downs;
	public int points;
	public int score;
	public boolean favorite;
	public boolean nsfw;
	@SerializedName("comment_count")
	public int commentCount;
	@SerializedName("comment_preview")
	public List<Comment> commentPreview;
	public String topic;
	@SerializedName("topic_id")
	public int topicId;
	@SerializedName("account_url")
	public String userName;
	@SerializedName("account_id")
	public int userId;
	@SerializedName("type")
	public String mimeType;
	public boolean animated;
	public int width;
	public int height;
	public int size;
	public long bandwidth;
	@SerializedName("deletehash")
	public String deleteHash;
	public String gifvLink;
	public String mp4Link;
	public String webmLink;
	public boolean looping;
	public GalleryItem.Section section;
	public String coverId;
	public int coverWidth;
	public int coverHeight;
	public Privacy privacy;
	public Layout layout;
	@SerializedName("images_count")
	public int imageCount;
	public List<Image> images;
	@SerializedName("is_album")
	public boolean album;
	// only for meme subclasses
// UPDATE: apparently Imgur doesn't really support this anymore at least
// as of 6/24/15.  https://groups.google.com/forum/#!msg/imgur/BEyZryAhGi0/yfOFyixuPy4J
//	@SerializedName("is_album")
//	protected MemeMetaData memeMetaData;

	// only for subreddit classes
// UPDATE: apparently Imgur doesn't really support this anymore
//	@SerializedName("reddit_comments")
//	protected String redditCommentsUrl;
	
}
