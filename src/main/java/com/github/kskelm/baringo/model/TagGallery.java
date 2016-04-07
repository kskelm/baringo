/**
 * Model for a tag and the image gallery for it
 */
package com.github.kskelm.baringo.model;

import java.util.List;

import com.github.kskelm.baringo.model.gallery.GalleryItem;
import com.github.kskelm.baringo.model.gallery.GalleryItemProxy;
import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * @author kskelm
 *
 */

public class TagGallery {

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the followerCount
	 */
	public int getFollowerCount() {
		return followerCount;
	}
	/**
	 * @return the itemCount
	 */
	public int getItemCount() {
		return itemCount;
	}
	/**
	 * @return the following
	 */
	public boolean isFollowing() {
		return following;
	}
	/**
	 * @return the items
	 */
	public List<GalleryItem> getItems() {
		return _convertedItems;
	}
	@Override
	public String toString() {
		return Utils.toString( this );
	}


	// ==========================================
	
	// internal filth only
	public void setConvertedItems(List<GalleryItem> items ) {
		this._convertedItems = items;
	}
	
	public List<GalleryItemProxy> getInternalItems() {
		return _internalItems;
	}
	

	private String name;
	private int followerCount;
	@SerializedName("total_items")
	private int itemCount;
	private boolean following;
	private List<GalleryItem> _convertedItems;
	@SerializedName("items")
	private List<GalleryItemProxy> _internalItems;
	
}
