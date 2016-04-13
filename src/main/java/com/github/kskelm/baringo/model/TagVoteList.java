/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.model;

import java.util.List;

import com.github.kskelm.baringo.util.Utils;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * Just a list of tag items to accommodate a weird return in the standard
 * wrapper that has an object with a single key we need to extract. Ignore
 * this class.
 * @author Kevin Kelm (triggur@gmail.com)
 * 
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
