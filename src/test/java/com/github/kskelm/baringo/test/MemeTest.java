/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.test;

import java.util.List;

import org.junit.Test;
import junit.framework.TestCase;

import com.github.kskelm.baringo.model.Image;
import com.github.kskelm.baringo.util.BaringoApiException;

/**
 * 
 * @author Kevin Kelm (triggur@gmail.com)
 *
 */
public class MemeTest extends TestCase {
	public MemeTest( String testName ) {
		super( testName );
	}	

	@Test
	public void testListDefaultMemes() throws BaringoApiException {
		Setup setup = new Setup();

		List<Image> list = setup.getClient()
				.memeService().listDefaultMemes();
		
		assertFalse( "meme images returned", list.isEmpty() );
		for( Image img : list ) {
			ImageTest.testImageObject( img );
		} // for
	}
	
}
