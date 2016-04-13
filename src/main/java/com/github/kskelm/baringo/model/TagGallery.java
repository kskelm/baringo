/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.model;

import java.util.List;

import com.github.kskelm.baringo.model.gallery.GalleryItem;
import com.github.kskelm.baringo.model.gallery.GalleryItemProxy;
import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * Model for a tag and the image gallery for it
 * @author Kevin Kelm (triggur@gmail.com)
 *
 */

public class TagGallery {

	
	/**
	 * Returns the name of the tag gallery (this will be the tag itself)
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * This is the number of users following this tag
	 * @return the followerCount
	 */
	public int getFollowerCount() {
		return followerCount;
	}
	/**
	 * This is the number of items tagged with this word
	 * @return the itemCount
	 */
	public int getItemCount() {
		return itemCount;
	}
	/**
	 * If there currently an authenticated user, this will indicate
	 * whether they are following this tag or not.  Instances of
	 * this object created before authenticating a user or before
	 * following the tag in CustomGallery will not match current
	 * server state.
	 * @return the following
	 */
	public boolean isFollowing() {
		return following;
	}
	/**
	 * Returns the current page of results for the gallery.
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
	
	/**
	 * INTERNAL ONLY
	 * @param items converts proxy objects to real GalleryItem objects
	 */
	public void setConvertedItems(List<GalleryItem> items ) {
		this._convertedItems = items;
	}
	
	/**
	 * INTERNAL ONLY
	 * @return list of proxy objects that you shouldn't use for anything
	 */
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
