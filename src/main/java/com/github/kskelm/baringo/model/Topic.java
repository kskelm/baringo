/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.model;

import com.github.kskelm.baringo.model.gallery.GalleryItem;
import com.github.kskelm.baringo.model.gallery.GalleryItemProxy;
import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a topic such as "funny" or "aww"
 * @author Kevin Kelm (triggur@gmail.com)
 *
 */

public class Topic {
	
	
	/**
	 * Returns the id of the topic
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Returns the name of the topic
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * This returns the topic's description
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * This is the CSS class used on the Imgur website to style the ephemeral topics
	 * @return the cssClass
	 */
	public String getCssClass() {
		return cssClass;
	}
	/** 
	 * Whether or not the topic is ephemeral, e.g. current events
	 * @return the ephemeral
	 */
	public boolean isEphemeral() {
		return ephemeral;
	}
	/**
	 * The current top image or album in this top
	 * @return the topItem
	 */
	public GalleryItem getTopItem() {
		return _convertedTopItem;
	}
	
	@Override
	public String toString() {
		return Utils.toString( this );
	}


	// ===================================================
	
	public Topic() { }
	
	
	private int id;
	private String name;
	private String description;
	@SerializedName("css")
	private String cssClass;
	private boolean ephemeral;
	@SerializedName("topPost")
	private GalleryItemProxy _internalTopItem;
	private GalleryItem _convertedTopItem;

	/**
	 * internal filth, don't use this.
	 * @return a proxy object
	 */
	public GalleryItemProxy getItemProxy() {
		return _internalTopItem;
	}
	
	/**
	 * internal filth, don't use this
	 * @param item - an item 
	 */
	public void setConvertedItem( GalleryItem item ) {
		_convertedTopItem = item;
	}

} 
