package tv.fun.appsettingstest.testCases;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import tv.fun.common.Utils;

/**
 * Created by zhengjin on 2017/6/29.
 * <p>
 * Test demos.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestDemos {

    private final static String TAG = TestDemos.class.getSimpleName();

    private Context mContext;

    @Before
    public void setUp() {
        Log.d(TAG, String.format("***** Test %s start.", TAG));
        mContext = InstrumentationRegistry.getContext();
    }

    @Test
    public void test01StartActivityByActionFromCommand() {
        final String cmd = "am start -a android.settings.WIFI_SETTINGS";
        Utils.CommandResult cr = Utils.execCommand(cmd, false, false);
        Assert.assertEquals("Demo, start activity by action from command line.", 0, cr.mResult);
    }

    @Test
    public void test02GetAppListFromContentProvider() {
        final String APP_LIST_URI = "content://tv.fun.tvupgrade.upgradeprovider";
        final String QUERY_ARG = "queryAppList";
        final String GET_KEY = "applist";

        Uri contentUri = Uri.parse(APP_LIST_URI);
        ContentResolver resolver = mContext.getContentResolver();
        Bundle bundle = resolver.call(contentUri, QUERY_ARG, null, null);

        if (bundle == null) {
            Assert.fail("Content is null from uri: " + APP_LIST_URI);
        }

        List<String> appList = bundle.getStringArrayList(GET_KEY);
        if (appList == null) {
            Assert.fail("Null for get array list from bundle by key: " + GET_KEY);
        }
        if (appList.size() == 0) {
            Assert.fail("Apps count is zero!");
        }

        Log.d(TAG, TAG + "=> app size: " + appList.size());
        for (String app : appList) {
            Log.d(TAG, TAG + "=> app: " + app);
        }
    }

}
