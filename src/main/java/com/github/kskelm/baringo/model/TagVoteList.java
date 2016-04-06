/**
 * just a list of tag items to accommodate a weird return in the standard
 * wrapper that has an object with a single key we need to extract. ignore this.
 */
package com.github.kskelm.baringo.model;

import java.util.List;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * @author kskelm
 * just a list of tag items to accommodate a weird return in the standard
 * wrapper that has an object with a single key we need to extract. ignore this.
 */

public class TagVoteList {

	/**
	 * Returns the tag votes
	 * @return the tag
	 */
	public List<TagVote> getList() {
		return tagVotes;
	}

	@Override
	public String toString() {
		return Utils.toString( this );
	}

	// =============================================
	
	@SerializedName("tags")
	private List<TagVote> tagVotes;

}
