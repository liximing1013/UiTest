package tv.fun.appsautotest.testCases;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import tv.fun.appsettingstest.common.TaskCommonSettings;
import tv.fun.common.Utils;

import static tv.fun.appsautotest.common.Utils.systemWait;

/**
 * Created Lixm on 2016/10/10
 * Test Playing VIP Video
 * Test Case: 16
 **/

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestDemo {
    UiDevice uiDevice;
    public Instrumentation instrument;
    private TaskCommonSettings mTask;
    //设定等待时间
    private static final int SHORT_WAIT = 1;
    private static final int WAIT = 5;
    private static final int LONG_WAIT = 15;
    //设定一段播放时间
    private static final int PlayVideoTime = 60;
    //初始化
    String m_ObjId = "";
    private UiObject2 m_uiObj = null;
    private long m_Time;
    private String m_Expect = "";
    private String m_Actual = "";
    private String resultStr = "";
    private boolean resultFlag = true;
    private boolean m_Pass = false;

    @Before
    public void setUp() {
        instrument = InstrumentationRegistry.getInstrumentation();
        uiDevice = UiDevice.getInstance(instrument);
        m_Time=Utils.getCurSecond();   //耗时

        uiDevice.pressHome();
        uiDevice.waitForIdle();
    }

    @After
    public void clearUp() {

        systemWait(WAIT);
    }

//    @Before
//    public void setUp() {
//        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
//        mTask = TaskCommonSettings.getInstance();
//        m_Time = Utils.getCurSecond();
//
//        uiDevice.pressHome();
//        Utils.waitForPackageOpened(uiDevice, LAUNCHER_PKG_NAME);
//        Utils.startActivity(SETTINGS_PKG_NAME, SETTINGS_HOME_ACTIVITY);
//        Utils.waitForPackageOpened(uiDevice, SETTINGS_PKG_NAME);
//
//        uiDevice.pressDPadUp();
//        SystemClock.sleep(Constants.SHORT_WAIT);
//    }
//
//    @After
//    public void clearUp() {
//        Utils.stopProcess(SETTINGS_PKG_NAME);
//    }

    @Test //初进金卡会员页
    public void LC_VIP_01_EnterVipPage() {
        System.out.println("进入金卡会员页面，开启金卡专区的自动化测试了..");
        try {
            systemWait(LONG_WAIT);
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/vip_title"));
            m_ObjId = "com.bestv.ott:id/vip_title";
            Utils.writeCaseResult("进入金卡会员页面失败",m_uiObj != null ,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag= false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test
    public void testDemo(){
        uiDevice.pressDPadUp();
        sleep(100000);
        UiObject2 scroll = uiDevice.findObject(By.clazz("android.widget.LinearLayout"));
        List<UiObject2> scrollList = scroll.findObjects(By.clazz("android.widget.RelativeLayout"));
        int tabSize = scrollList.size();
        Log.d("lxm", "testDemo1: "+tabSize);
        int tabList = scroll.getChildCount();
        Log.d("lxm", "testDemo2: "+tabList);
        for(int i =0;i<=tabSize;i++){
            if(i == 2){
                System.out.print("电视"+"视频");
            }
            RightMoveMethod(i/2);
        }

    }
    private void RightMoveMethod(int RightMove) {
        int i = 1;
        while (i <= RightMove) {
            i++;
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    } //Right*

    private void sleep(int sleep){
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}