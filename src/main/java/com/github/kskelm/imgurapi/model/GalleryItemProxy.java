/**
 * INTERNAL ONLY - don't use this for anything
 */
package com.github.kskelm.imgurapi.model;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.github.kskelm.imgurapi.model.GalleryAlbum.Layout;
import com.github.kskelm.imgurapi.model.GalleryAlbum.Privacy;
import com.github.kskelm.imgurapi.util.Utils;
import com.google.gson.annotations.SerializedName;

// sometimes you do something that makes you feel dirty inside
@SuppressWarnings("unused")
public class GalleryItemProxy {

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
	public int accountId;
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
	

}
