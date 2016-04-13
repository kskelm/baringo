/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.model.search;

import java.util.logging.Logger;

import com.github.kskelm.baringo.BaringoClient;
import com.github.kskelm.baringo.util.Utils;

/**
 * 
 * For putting together simple search queries.
 * <p>
 * The simple search query cannot allow some combinations of
 * operations, such as putting AND and OR clauses into the same search.
 * The same is true for exact <code>thisPhrase()</code> matches.
 * <p>
 * Other modifiers such as NOT as well as size range and item type
 * may be used in conjunction with the above.
 * <p>
 * See <a href="https://api.imgur.com/endpoints/gallery#gallery-search">Imgur docs</a>
 * @author Kevin Kelm (triggur@gmail.com)
 */

public class SearchQuery {

	/**
	 * This allows specific kinds of items to come back
	 */
	public enum ItemType {
		/**
		 * No specific item type, allow any
		 */
		any,
		/**
		 * Return only image/jpeg
		 */
		jpg,
		/**
		 * Return only image/png
		 */
		png,
		/**
		 * Return only image/gif
		 */
		gif,
		/**
		 * Return only image/gif, but specifically animated gifs
		 */
		anigif,
		/**
		 * Return only albums
		 */
		album
	}
	
	/**
	 * This allows specific file size ranges to come back
	 */
	public enum SizeRange {
		/**
		 * No restriction, any size
		 */
		any,
		/**
		 * Small, 500px square or less
		 */
		small,
		/**
		 * Medium, 500-2000px square
		 */
		med,
		/**
		 * Big, 2000-5000px square
		 */
		big,
		/**
		 * Large, 5000-10000px square
		 */
		lrg,
		/**
		 * Huge, 10000px square and above
		 */
		huge
	}

	/**
	 * Returns the list of words that are part of an "or" search
	 * @return the anyWords
	 */
	public String getAnyWords() {
		return anyWords;
	}


	/**
	 * Set the "or" query string; If a result matches ANY of
	 * the words present, it will be returned. For more complex
	 * queries, see {@link CompoundSearchQuery}. Only one of the
	 * query string setters may be called for a given SearchQuery.
	 * @param anyWords the query to set
	 * @return The SearchQuery itself, for functional chaining
	 */
	public SearchQuery anyWords(String anyWords) {
		if( alreadySetQuery ) {
			log.warning( "SearchQuery string already set, overriding with compound query");
		} // if
		alreadySetQuery = true;

		this.anyWords = anyWords;
		return this;
	}


	/**
	 * Returns the list of words that are part of an "and" search
	 * @return the allWords
	 */
	public String getAllWords() {
		return allWords;
	}


	/**
	 * Set the "and" query string; If a result matches ALL of
	 * the words present, it will be returned. For more complex queries,
	 * see {@link CompoundSearchQuery}. Only one of the
	 * query string setters may be called for a given SearchQuery.
	 * @param allWords the query to set
	 * @return The SearchQuery itself, for functional chaining
	 */
	public SearchQuery allWords(String allWords) {
		if( alreadySetQuery ) {
			log.warning( "SearchQuery string already set, overriding with compound query");
		} // if
		alreadySetQuery = true;

		this.allWords = allWords;
		return this;
	}


	/**
	 * Returns the specific phrase to be searched.
	 * @return the thisPhrase
	 */
	public String getThisPhrase() {
		return thisPhrase;
	}


	/**
	 * Set the exact match query string; If a result matches the
	 * specific phrase given, it will be returned. Any item with
	 * this as a substring will be returned. Only one of the
	 * query string setters may be called for a given SearchQuery.
	 * For more complex queries, see {@link CompoundSearchQuery}.
	 * 
	 * @param thisPhrase a phrase to include in results
	 * @return The SearchQuery itself, for functional chaining
	 */
	public SearchQuery thisPhrase(String thisPhrase) {
		if( alreadySetQuery ) {
			log.warning( "SearchQuery string already set, overriding with compound query");
		} // if
		alreadySetQuery = true;

		this.thisPhrase = thisPhrase;
		return this;
	}


	/**
	 * Returns the phrase to be excluded from the search
	 * @return the notPhrase
	 */
	public String getNotPhrase() {
		return notThisPhrase;
	}


	/**
	 * Set the exclusion query string; If a result matches the
	 * specific phrase given, it will NOT be returned. For more
	 * complex queries, see {@link CompoundSearchQuery}. This MAY
	 * be used in conjunction with the other search strings.
	 * @param notThisPhrase a phrase to declare as unwanted
	 * @return The SearchQuery itself, for functional chaining
	 */
	public SearchQuery notThisPhrase(String notThisPhrase) {
		this.notThisPhrase = notThisPhrase;
		return this;
	}


	/**
	 * Returns the item type this search is to return
	 * @return the itemType
	 */
	public ItemType getItemType() {
		return itemType;
	}


	/**
	 * Sets the type of item this search must return.
	 * For more complex queries, see {@link CompoundSearchQuery}.
	 * @param itemType the itemType to set
	 * @return The SearchQuery itself, for functional chaining
	 */
	public SearchQuery itemType(ItemType itemType) {
		this.itemType = itemType;
		return this;
	}


	/**
	 * Returns the size range this search is to return
	 * @return the sizeRange
	 */
	public SizeRange getSizeRange() {
		return sizeRange;
	}


	/**
	 * Sets the range of image sizes this search must
	 * return. For more complex queries, see
	 * See {@link CompoundSearchQuery}.
	 * @param sizeRange the sizeRange to set
	 * @return The SearchQuery itself, for functional chaining
	 */
	public SearchQuery sizeRange(SizeRange sizeRange) {
		this.sizeRange = sizeRange;
		return this;
	}


	// ===================================================
	private String anyWords;  // or
	private String allWords;  // and
	private String thisPhrase; // exact match
	private String notThisPhrase; // not this phrase
	private ItemType itemType = ItemType.any;
	private SizeRange sizeRange = SizeRange.any;
	private boolean alreadySetQuery = false;
	
	private static final Logger log = Logger.getLogger( BaringoClient.LOG_NAME );
	
	@Override
	public String toString() {
		return Utils.toString( this );
	}

}
