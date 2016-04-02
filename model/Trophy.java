/**
 * a trophy that can be earned by users
 */
package com.github.kskelm.baringo.model;

import java.util.Date;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * @author kskelm
 *
 */

public class Trophy {


	/**
	 * The ID of the trophy, unique to each instance
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * The human-readable name of the trophy
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * The machine-readable name of the trophy
	 * @return the trophyType
	 */
	public String getType() {
		return type;
	}

	/**
	 * A description of the trophy and how it was earned
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * The ID of the image or comment where the trophy was earned
	 * @return the earnedAtId
	 */
	public String getEarnedAtId() {
		return earnedAtId;
	}

	/**
	 * A link to where the trophy was earned
	 * @return the earnedAtLink
	 */
	public String getEarnedAtLink() {
		return earnedAtLink;
	}

	/**
	 * The date/time on which the trophy was earned
	 * @return the earnedDate
	 */
	public Date getEarnedDate() {
		return earnedDate;
	}

	/**
	 * A link to the image of the trophy
	 * @return the imageLink
	 */
	public String getImageLink() {
		return imageLink;
	}

	// ===================================================
	private int id;
	private String name;
	@SerializedName("name_clean")
	private String type;
	private String description;
	@SerializedName("data")
	private String earnedAtId;
	@SerializedName("data_link")
	private String earnedAtLink;
	@SerializedName("datetime")
	private Date earnedDate;
	@SerializedName("image")
	private String imageLink;
	
	@Override
	public String toString() {
		return Utils.toString( this );
	}

} // class AccountSettings
