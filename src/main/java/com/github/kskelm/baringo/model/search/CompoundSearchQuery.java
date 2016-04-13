/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.model.search;

import java.util.ArrayList;
import java.util.List;

import com.github.kskelm.baringo.util.BaringoApiException;

/**
 * For putting together complex, compound search queries.
 * <p>
 * See <a href="https://api.imgur.com/endpoints/gallery#gallery-search">Imgur docs</a>
 * <p>
 * From their documentation:
 * [...] This  supports boolean operators
 * (AND, OR, NOT) and indices (tag: user: title: ext: subreddit: album:
 * meme:).An example compound query would be 'title: cats AND dogs ext: gif'
 *<p>
 * An example of using this kind of search:
 * <pre>
 * {@code
 * 		CompoundSearchQuery query = new CompoundSearchQuery()
 * 			.title( "cat" )
 * 				.and( "dog" )
 * 			.extension( "gif" );
 * }
 * </pre>
 * 
 * @author Kevin Kelm (triggur@gmail.com)
 */

public class CompoundSearchQuery  {

	public CompoundSearchQuery() {
		user = new QueryElement( "user", this );
		title = new QueryElement( "title", this );
		extension = new QueryElement( "ext", this );
		subReddit = new QueryElement( "subreddit", this );
		album = new QueryElement( "album", this );
		meme = new QueryElement( "meme", this );
		lastElement = null;
	}

	/**
	 * Sets the user element for adding new userNames to the query
	 * @param userName the userName to add
	 * @return user element
	 * @throws BaringoApiException a rule was broken 
	 */
	public CompoundSearchQuery user( String userName ) throws BaringoApiException {
		lastElement = user;
		return user.set( userName );
	}

	/**
	 * Sets the title element for adding new title search words to the query
	 * @param word the word to add
	 * @return title element
	 * @throws BaringoApiException a rule was broken 
	 */
	public CompoundSearchQuery title( String word ) throws BaringoApiException {
		lastElement = title;
		return title.set( word );
	}

	/**
	 * Sets the extension element for adding new file extensions to the query
	 * @param ext the file extension to add
	 * @return extension element
	 * @throws BaringoApiException a rule was broken
	 */
	public CompoundSearchQuery extension( String ext ) throws BaringoApiException {
		lastElement = extension;
		return extension.set( ext );
	}

	/**
	 * Sets the subreddit element for adding new subreddit names to the query
	 * @param subreddit the name of the subreddit to add
	 * @return subreddit element
	 * @throws BaringoApiException a rule was broken
	 */
	public CompoundSearchQuery subreddit( String subreddit ) throws BaringoApiException {
		lastElement = subReddit;
		return subReddit.set( subreddit );
	}

	/**
	 * Sets the album element for adding new album search words to the query
	 * @param word the word to add
	 * @return album element
	 * @throws BaringoApiException a rule was broken 
	 */
	public CompoundSearchQuery album( String word ) throws BaringoApiException {
		lastElement = album;
		return album.set( word );
	}

	/**
	 * Sets the meme element for adding new meme search words to the query
	 * @param word the word to add
	 * @return meme element
	 * @throws BaringoApiException a rule was broken 
	 */
	public CompoundSearchQuery meme( String word ) throws BaringoApiException {
		lastElement = meme;
		return meme.set( word );
	}

	/**
	 * Adds an AND word to the current element such as user(...) or title(...)
	 * @param word the word to add
	 * @return this object for further query refinement
	 * @throws BaringoApiException a rule was broken
	 */
	public CompoundSearchQuery and( String word ) throws BaringoApiException {
		if( lastElement == null ) {
			throw new BaringoApiException( "Cannot AND without a preceding"
					+ " search element such as user(...) or title(...)" );
		} // if
		return lastElement.and( word );
	}
	
	/**
	 * Adds an OR word to the current element such as user(...) or title(...)
	 * @param word the word to or
	 * @return this object for further query refinement
	 * @throws BaringoApiException a rule was broken
	 */
	public CompoundSearchQuery or( String word ) throws BaringoApiException {
		if( lastElement == null ) {
			throw new BaringoApiException( "Cannot OR without a preceding"
					+ " search element such as user(...) or title(...)" );
		} // if
		return lastElement.or( word );
	}
	
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append( user )
				.append( title )
				.append( extension )
				.append( subReddit )
				.append( album )
				.append( meme );
		
		return buf.toString().trim();
	}
	
	private QueryElement user;
	private QueryElement title;
	private QueryElement extension;
	private QueryElement subReddit;
	private QueryElement album;
	private QueryElement meme;
	private QueryElement lastElement = null;

	// ===================================================

	/**
	 * This is used to keep track of SET/AND/OR entries inside
	 * the element-- internal
	 * @author Kevin Kelm (triggur@gmail.com)
	 */
	public class QueryElement {
		// internal
		public QueryElement( String name, CompoundSearchQuery parent ) {
			this.name = name;
			this.parent = parent;
		}

		/**
		 * Start out a query element with a match word
		 * @param entry the match word
		 * @return the QueryElement object itself
		 * @throws BaringoApiException a rule was broken
		 */
		public CompoundSearchQuery set( String entry ) throws BaringoApiException {
			if( !entries.isEmpty() ) {
				throw new BaringoApiException( "Cannot add SET entries to a"
						+ " complex query element once other elements have been added" );
			} // if
			entries.add( entry );
			return parent;
		}

		/**
		 * Add an AND element. It's okay if this is the first entry.
		 * @param entry a word to add to the and clause
		 * @return the search query in its current state, for functional chaining
		 * @throws BaringoApiException a rule was broken
		 */
		public CompoundSearchQuery and( String entry ) throws BaringoApiException {
			if( oring ) {
				throw new BaringoApiException( "Cannot add AND entries to a"
						+ " complex query element once OR elements have been added" );
			} // if
			anding = true;
			entries.add( entry );
			return parent;
		}

		/**
		 * Add an OR element. It's okay if this is the first entry.
		 * @param entry a word to add to the or clause
		 * @return the search query in its current state, for functional chaining
		 * @throws BaringoApiException a rule was broken
		 */
		public CompoundSearchQuery or( String entry ) throws BaringoApiException {
			if( anding ) {
				throw new BaringoApiException( "Cannot add OR entries to a"
						+ " complex query element once AND elements have been added" );
			} // if
			oring = true;
			entries.add( entry );
			return parent;
		}
		
		// examples of elements include variations on title search:
		// title: dog
		// title: dog AND cat
		// title: dog OR cat
		public String toString() {
			if( entries.isEmpty() || (!anding && !oring)) { // nothing to do
				return "";
			} // if
			StringBuffer buf = new StringBuffer();
			buf.append( " " )
					.append( name )
					.append( ":");
			int count = 0;
			for( String entry : entries ) {
				if( entry == null ) {
					continue;
				} // if
				if( count++ == 0 ) { // no expression [yet]
					buf.append( " " );
				} else if( anding ) {
					buf.append( " AND " );
				} else if( oring ) {
					buf.append( " OR " );
				} // if-else
				buf.append( entry );
			} // for
			return buf.toString();
		}


		// ===================================================


		private String name;
		private boolean anding; // have we already starting anding entries?
		private boolean oring; // have we already started oring entries?
		private List<String> entries = new ArrayList<>();
		private CompoundSearchQuery parent;
	}
}
