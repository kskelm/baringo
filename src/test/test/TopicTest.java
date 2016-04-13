/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.test;

import java.util.List;

import org.junit.Test;
import junit.framework.TestCase;

import com.github.kskelm.baringo.model.Topic;
import com.github.kskelm.baringo.model.gallery.GalleryItem;
import com.github.kskelm.baringo.util.BaringoApiException;

/**
 * 
 * @author Kevin Kelm (triggur@gmail.com)
 *
 */
public class TopicTest extends TestCase {
	public TopicTest( String testName ) {
		super( testName );
	}	

	@Test
	public void testDefaultTopics() throws BaringoApiException {
		Setup setup = new Setup();

		List<Topic> list = setup.getClient()
				.topicService().listDefaultTopics();

		assertFalse( "topics returned", list.isEmpty() );
	}

	@Test
	public void testListTopic() throws BaringoApiException {
		Setup setup = new Setup();

		List<GalleryItem> list = setup.getClient()
				.topicService().listTopic(
						Setup.TEST_TOPIC_ID,
						GalleryItem.Sort.Viral,
						GalleryItem.Window.Month,
						0 );

		assertFalse( "gallery items returned", list.isEmpty() );

	}
}
