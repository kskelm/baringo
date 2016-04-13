/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.model;

import com.google.gson.annotations.SerializedName;

/**
 * When reporting something as inappropriate, this is
 * the reason code to pass in.
 * 
 * @author Kevin Kelm (triggur@gmail.com)
 *
 */
public enum ReportReason {

	/**
	 * Ambiguously rejected
	 */
	@SerializedName("1") DoesntBelongOnImgur,
	/**
	 * The image is advertising spam
	 */
	@SerializedName("2") Spam,
	/**
	 * The image includes abusive or hateful content
	 */
	@SerializedName("3") Abusive,
	/**
	 * Not safe for work, but not marked that way
	 */
	@SerializedName("4") ShouldBeMarkedNsfw,
	/**
	 * Ambiguously rejected
	 */
	@SerializedName("5") Pornography,
}
