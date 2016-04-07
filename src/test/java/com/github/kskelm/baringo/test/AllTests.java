package com.github.kskelm.baringo.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	QuotaTest.class,
	AuthTest.class,
	AccountTest.class,
	AlbumTest.class,
	CommentTest.class,
	CustomGalleryTest.class,
	TopicTest.class,
	GalleryTest.class,
	ConversationTest.class,
	NotificationTest.class
})

public class AllTests {

}
