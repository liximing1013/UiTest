package tv.fun.appsautotest.common;

import android.os.SystemClock;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import junit.framework.Assert;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhengjin on 2016/7/28.
 *
 * Common used Utils.
 */
public final class Utils {

    private static final String TAG = Utils.class.getSimpleName();
    private static final String CAPTURES_PATH = "/data/local/tmp/captures";

    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd_hhmmss_SSS", Locale.getDefault());
        return simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }

    public static void takeScreenCapture(UiDevice device) {
        File localFile = new File(CAPTURES_PATH);
        if (!localFile.exists()) {
            Assert.assertTrue(localFile.mkdirs());
        }

        String path = String.format("%s/capture_%s.png", CAPTURES_PATH, getCurrentTime());

        if (device.takeScreenshot(new File(path))) {
            Log.d(TAG, String.format("Take capture and save at %s", path));
        } else {
            Log.e(TAG, String.format("Take capture failed!", path));
        }
    }

    public static void systemWait(int seconds) {
        SystemClock.sleep(seconds * 1000);
    }
}
