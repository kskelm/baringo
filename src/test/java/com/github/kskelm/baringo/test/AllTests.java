package com.github.kskelm.baringo.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	QuotaTest.class,
	AuthTest.class,
	AccountTest.class,
	AlbumTest.class
})
public class AllTests {

}
