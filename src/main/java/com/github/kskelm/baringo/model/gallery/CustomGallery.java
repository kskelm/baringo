/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.model.gallery;

import java.util.List;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * A user's custom gallery is specific tags they want to search on.
 * It also represents a list of blocked tags that the user never,
 * ever wants to see.
 * @author Kevin Kelm (triggur@gmail.com)
 *
 */
public class CustomGallery {


	/**
	 * Returns the name of the user that created the gallery
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Returns a link to the URL for the gallery
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Returns the list of tags associated with the custom gallery
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * Get the number of items in the gallery total
	 * @return the itemCount
	 */
	public int getItemCount() {
		return itemCount;
	}

	/**
	 * Return the list of items in the custom gallery.
	 * Each item can be cast to its more specific
	 * GalleryImage or GalleryAlbum, as necessary.
	 * @return the items list of GalleryItems
	 */
	public List<GalleryItem> getItems() {
		return _convertedItems;
	}

	// ================================================
	@SerializedName("account_url")
	private String userName;
	private String link;
	private List<String> tags;
	@SerializedName("item_count")
	private int itemCount;
	@SerializedName("items")
	private List<GalleryItemProxy> _internalItems;
	private List<GalleryItem> _convertedItems;

	// internal filth only
	public void setConvertedItems(List<GalleryItem> items ) {
		this._convertedItems = items;
	}
	
	/**
	 * Internal only, don't use this
	 * @return list of proxy objects
	 */
	public List<GalleryItemProxy> getInternalItems() {
		return _internalItems;
	}
	
	public String toString() {
		return Utils.toString( this );
	} // toString
}
