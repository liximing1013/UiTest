package tv.fun.appsautotest.common;

import android.os.SystemClock;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Tracer;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import junit.framework.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import tv.fun.common.Infos;

import static android.os.SystemClock.uptimeMillis;
import static android.support.test.uiautomator.By.text;

/**
 * Created by lixm on 2017/12/1.
 */

public final class CommonMethod {
    private UiDevice uiDevice;
    private UiActions uiActions = new UiActions();
    //设定等待时间
    private static final int SHORT_WAIT = 2;
    private static final int WAIT = 6;
    //初始化
    private long m_Time;
    private String m_Expect = "";
    private String m_Actual = "";
    private boolean m_Pass = false;

    //构造器
    public CommonMethod(UiDevice uiDevice){
        this.uiDevice = uiDevice;
    }

//    private CommonMethod() {
//        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
//    }

//    public CommonMethod(Instrumentation instrument,UiDevice uiDevice){
//        this.uiDevice = uiDevice;
//        this.instrument = instrument;
//    }

    private void sleep(int sleep){
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public UiObject2 getTabFromLauncherHomeByText(UiDevice device, String tabText) {
        List<UiObject2> tabTitles = device.findObjects(By.res("com.bestv.ott:id/title"));
        Assert.assertTrue("Verify tabs on launcher home.", tabTitles.size() > 0);
        UiObject2 retTitle = null;
        for (UiObject2 title : tabTitles) {
            if (tabText.equals(title.getText())) {
                retTitle = title;
            }
        }
        Assert.assertNotNull(retTitle);
        return retTitle;
    }

    public void openTabFromLauncherHomeByText(UiDevice device, UiObject2 tabText) {
        tabText.getParent().click();
        sleep(3000);
        device.pressEnter();
        device.waitForIdle();
        sleep(2000);
    }

    private void enterPersonalCenterPage(){
        uiDevice.pressDPadUp();
        systemWait(WAIT);
        uiActions.pressRight(1);
        UiObject account = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/account"));
        if(account.exists()){
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 PerPage = uiDevice.findObject(By.text("个人中心"));
            Assert.assertEquals("个人中心",PerPage.getText());
        }else {
            Assert.assertTrue(account.exists());
        }
    } //进入个人中心页面

    private void randomPlayFilm(int RandomMove){
        uiDevice.pressDPadDown();
        systemWait(WAIT);
        Random moveTimes = new Random();
        int i;
        i=moveTimes.nextInt(RandomMove);
        for(int j= 0;j<=i;j++){
            SystemClock.sleep(1000);
            uiDevice.pressDPadDown();
            SystemClock.sleep(1000);
            if(i > 18){
                break;
            }
        }
    } //随机播放电影

    public void sleep(long ms) {
        long start = uptimeMillis();
        long duration = ms;
        boolean interrupted = false;
        do {
            try {
                Thread.sleep(duration);
            }
            catch (InterruptedException e) {
                interrupted = true;
            }
            duration = start + ms - uptimeMillis();
        } while (duration > 0);

        if (interrupted) {
            // Important: we don't want to quietly eat an interrupt() event,
            // so we make sure to re-interrupt the thread so that the next
            // call to Thread.sleep() or Object.wait() will be interrupted.
            Thread.currentThread().interrupt();
        }
    }

    public static void systemWait(int seconds) {

        SystemClock.sleep(seconds * 1000);
    } //等待时间设定

    public void waitForNewWindowIdle(long time){
        try {
            Tracer.trace(time);
            uiDevice.wait(Until.findObject(By.text("全屏")),time);
            UiObject2 fullScreen = uiDevice.findObject(By.text("全屏").res("com.bestv.ott:id/discripse"));
            if (fullScreen != null) {
                Assert.assertTrue("进入详情页后未找到全屏选项",true);
            } else {
                tv.fun.common.Utils.funAssert("等待18s,如果界面还没有打开则超时异常", false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }  //等待18s,如果界面还没有打开则超时异常

    private void waitForNewWindowCollectButton(long time){
        try {
            Tracer.trace(time);
            sleep(time);
            UiObject2 fullScreen = uiDevice.findObject(By.text("全屏").res("com.bestv.ott:id/discripse"));
            if (fullScreen == null) {
                uiDevice.pressMenu();
                sleep(2000);
                UiObject2 Text = uiDevice.findObject(text("\uE6CF 已收藏")
                        .res("com.bestv.ott:id/fav_text"));  //加入断言判定详情页是否变为已收藏
                m_Expect = "\uE6CF 已收藏";
                m_Actual = Text.getText();
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                tv.fun.common.Utils.writeCaseResult("menu选项收藏后应变为已收藏", m_Pass, m_Time);
            } else {
                sleep(2000);
                UiObject2 Text = uiDevice.findObject(text("已收藏"));  //加入断言判定详情页是否变为已收藏
                m_Expect = "已收藏";
                m_Actual = Text.getText();
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                tv.fun.common.Utils.writeCaseResult("详情页收藏显示错误：收藏后应变为已收藏", m_Pass, m_Time);
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    //将xx:xx:xx格式的时间转化为xxx秒
    private int timeTrans(String sTime){
        int iTime;
        int iHour = 0;
        int iMins = 0;
        int iSecs;
        String[] lsTime = sTime.split(":");

        if( lsTime.length == 3){
            iHour = Integer.parseInt(lsTime[0]);
            iMins = Integer.parseInt(lsTime[1]);
            iSecs = Integer.parseInt(lsTime[2]);
        }
        else if(lsTime.length == 2) {
            iMins = Integer.parseInt(lsTime[0]);
            iSecs = Integer.parseInt(lsTime[1]);
        }
        else{
            iSecs = Integer.parseInt(lsTime[0]);
        }

        iTime = 3600 * iHour + 60 * iMins + iSecs;
        return iTime;
    }

    //等待loading消失
    private void progressBarExists(int Second){
        UiObject proBar = uiDevice.findObject(new UiSelector().resourceId(Infos.S_PROCESS_BAR_ID));
        proBar.waitUntilGone(Second);//在一定时间内判断控件是否消失
        if(!proBar.exists()){
            return;
        }
        systemWait(SHORT_WAIT);
    }

    //等待页面刷新结果
    private void waitPageRefresh(String text1,String text2) throws UiObjectNotFoundException{
        UiObject object = new UiObject(new UiSelector().text(text1));
        object.clickAndWaitForNewWindow(); //点击等待页面跳转
        UiObject object1 = new UiObject(new UiSelector().className(text2));//需要出现的页面元素
        while (!object1.exists()){
            UiObject object2 = new UiObject(new UiSelector().className(text2));
            if(!object2.exists()){
                systemWait(1);
            }
        }
        UiActions.takeScreenToFile(uiDevice,"object");
    }

    //获取当前时间
    private String getNow() {//获取当前时间
        Date time = new Date();
        SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow = now.format(time);
        return timeNow;
    }

    //播放时间
    public void playVideo(int playtime){
        try {
            UiObject2 funshionPlayer = uiDevice.findObject(By.clazz
                    ("com.funshion.player.play.funshionplayer.VideoViewPlayer").pkg("com.bestv.ott"));
            if(funshionPlayer != null){
                systemWait(playtime*1000);
            }else
                Assert.assertTrue("未找到播放器",false);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    //判断电视是否是会员
    public String checkVip() {
        systemWait(1);
        UiObject2 vipTip = uiDevice.findObject(By.res("com.bestv.ott:id/vip_tip")
                .clazz("android.widget.TextView").pkg("com.bestv.ott"));
        Log.d("LXM", "checkVip: "+vipTip.getText());
        return vipTip.getText();
    }

    //获取页面某种控件的个数
    public int getCountByResourceId(String id,int instance) {
        int num = 0;
        for(int i=0;i<100;i++){
            try {
                new UiObject(new UiSelector().resourceId(id).instance(instance)).getText();
//                getUiObjectByResourceIdAndIntance("com.gaotu100.superclass:id/assignmentitemview_upload_text", i).getText();
            } catch (UiObjectNotFoundException e) {
               e.printStackTrace();
                num = i;
                break;
            }
        }
        return num;
    }

    public void selectLocation(String locat) throws UiObjectNotFoundException{
        UiCollection collection = new UiCollection(new UiSelector().resourceId("com.bestv.ott:id/title"));
        UiObject hotLocat = collection.getChild(new UiSelector().text(locat));
        hotLocat.clickAndWaitForNewWindow();
    }

    public boolean assertLocation(String locat) throws UiObjectNotFoundException{
        UiObject hotLocat  = new UiObject(new UiSelector().text(locat));
        int x = hotLocat.getBounds().centerX();
        Log.d("lxm", "LC_VIP_01_EnterVipPage: "+x);
        int H = UiDevice.getInstance().getDisplayHeight();
        Log.d("lxm", "LC_VIP_01_EnterVipPage: "+H);
        int W = UiDevice.getInstance().getDisplayWidth(); //1920
        Log.d("lxm", "LC_VIP_01_EnterVipPage: "+W);
        int max = (int)(W+W*0.05);
        Log.d("lxm", "LC_VIP_01_EnterVipPage: "+max);
        int min = (int)(W-W*0.05);
        Log.d("lxm", "LC_VIP_01_EnterVipPage: "+min);
        return (max>x & x>min);
    }

    public void tabChoose(){
        List<UiObject2> tab = uiDevice.findObjects(By.clazz("android.widget.RelativeLayout"));

    }
}
