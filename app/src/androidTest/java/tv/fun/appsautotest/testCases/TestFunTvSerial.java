package tv.fun.appsautotest.testCases;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Random;

import tv.fun.appsautotest.common.Utils;

/**
 * Created by zhengjin on 2016/8/30.
 *
 * Test playing tv serial video.
 */
@RunWith(AndroidJUnit4.class)
public final class TestFunTvSerial {

    private static final String TAG = TestFunTvFilm.class.getSimpleName();

    private UiDevice mDevice;

    @Before
    public void setUp() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
//        UiActions.backToLauncherHome(mDevice);
    }

    @After
    public void clearUp() {
//        UiActions.backToLauncherHome(mDevice);
    }

    @Test
    public void testPlayMultipleTvs() {
//        UiActions.verifyOnLauncherHome(mDevice);
//        UiObject2 tvTabText = UiActions.getTabFromLauncherHomeByText(mDevice, "电视剧");
//        UiActions.openTabFromLauncherHome(mDevice, tvTabText);
//
//        randomSelectTvAndOpenTvDetails(10);
        openTvAndVerifyPlayerOnTop();
        randomSelectNumberOfTvFromPlayer(10);
        openTvAndVerifyPlayerOnTop();
        playingTvAndVerifyPlayerOnTop(2 * 60);
    }

    private void playingTvAndVerifyPlayerOnTop(int playTime) {
        Utils.systemWait(playTime);
        UiObject2 player = mDevice.findObject(By.clazz(
                "com.funshion.player.play.funshionplayer.VideoViewPlayer"));
        boolean ret = (player != null) && player.isEnabled();
        assertAndCaptureForFailed(ret, "Verify player is playing and on the top.");
    }

    private void randomSelectNumberOfTvFromPlayer(int randomNumber) {
        mDevice.pressDPadDown();
//        Utils.systemWait(UiActions.SHORT_WAIT);

        List<UiObject2> tvCells = mDevice.findObjects(By.res("com.bestv.ott:id/tv_cell"));
        Assert.assertTrue("Tv cell is NOT found!", (tvCells.size() > 0));

        int tvNumber = new Random().nextInt(randomNumber);
        tvCells.get(tvNumber).getParent().click();
        mDevice.pressEnter();
    }

    private void openTvAndVerifyPlayerOnTop() {
        mDevice.pressEnter();
        Utils.systemWait(15);

        boolean ret = mDevice.wait(Until.hasObject(By.clazz(
                "com.funshion.player.play.funshionplayer.VideoViewPlayer")), 5);
        assertAndCaptureForFailed(ret, "Verify player is open and on the top.");
    }

//    private void randomSelectTvAndOpenTvDetails(int randomNumber) {
//        mDevice.pressDPadLeft();
//        mDevice.waitForIdle();
//        Utils.systemWait(UiActions.WAIT);
//
//        mDevice.pressDPadDown();
//        Utils.systemWait(UiActions.SHORT_WAIT);
//
//        int moveTimes = new Random().nextInt(randomNumber);
//        for (int i = 0; i <= moveTimes; i++) {
//            mDevice.pressDPadRight();
//            Utils.systemWait(UiActions.SHORT_WAIT);
//        }
//
//        mDevice.pressEnter();
//        mDevice.waitForIdle();
//        Utils.systemWait(UiActions.WAIT);
//
//        UiObject2 tvTitle = mDevice.findObject(By.res("com.bestv.ott:id/detail_title"));
//        assertAndCaptureForFailed((tvTitle != null), "Verify tv title of details page.");
//        Log.d(TAG, String.format("TV title: %s", tvTitle.getText()));
//    }

    private void assertAndCaptureForFailed(boolean ret, String message) {
        if (!ret) {
            Utils.takeScreenCapture(mDevice);
        }
        Assert.assertTrue(message, ret);
    }

}
