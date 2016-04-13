/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.model;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * Information about a meme
 * @author Kevin Kelm (triggur@gmail.com)
 *
 */

public class MemeMetaData {

	/**
	 * Returns the name of the meme
	 * @return the memeName
	 */
	public String getMemeName() {
		return memeName;
	}

	/**
	 * Returns the text that's at the top of the meme image
	 * @return the topText
	 */
	public String getTopText() {
		return topText;
	}

	/**
	 * Returns the text that's at the bottom of the meme image
	 * @return the bottomText
	 */
	public String getBottomText() {
		return bottomText;
	}

	/**
	 * Returns the imageId of the background image
	 * @return the backgroundImageId
	 */
	public String getBackgroundImageId() {
		return backgroundImageId;
	}

	@Override
	public String toString() {
		return Utils.toString( this );
	}


	// ===================================================
	

	@SerializedName("meme_name")
	private String memeName;
	@SerializedName("top_text")
	private String topText;
	@SerializedName("bottom_text")
	private String bottomText;
	@SerializedName("bg_image")
	private String backgroundImageId;
	
}