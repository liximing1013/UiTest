package tv.fun.appassisttest.testCases;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MyTest {
	MonkeyTest mTest = new MonkeyTest();
    FirstTest mFirst = new FirstTest();

    @Before
    public void setUp() {
        mFirst.setUp();
    }

	@Test
	public void testCase() throws UiObjectNotFoundException {
        mFirst.connectTV();
        mTest.testRun();
	}
}