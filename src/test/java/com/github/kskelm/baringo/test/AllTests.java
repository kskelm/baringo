/** This file is released under the Apache License 2.0. See the LICENSE file for details. **/
package com.github.kskelm.baringo.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	QuotaTest.class,
	AuthTest.class,
	ImageTest.class,
	AccountTest.class,
	AlbumTest.class,
	CommentTest.class,
	CustomGalleryTest.class,
	TopicTest.class,
	GalleryTest.class,
	ConversationTest.class,
	NotificationTest.class,
	MemeTest.class
})

public class AllTests {

}
