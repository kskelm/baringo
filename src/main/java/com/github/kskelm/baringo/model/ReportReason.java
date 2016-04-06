
package com.github.kskelm.baringo.model;

import com.google.gson.annotations.SerializedName;

/**
 * When reporting something as inappropriate, this is
 * the reason code to pass in.
 * 
 * @author kskelm
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
