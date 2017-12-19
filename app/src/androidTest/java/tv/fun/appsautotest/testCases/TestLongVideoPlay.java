package tv.fun.appsautotest.testCases;

import android.app.Instrumentation;
import android.os.Environment;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.util.Random;

import tv.fun.common.HttpUtils;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

import static android.content.ContentValues.TAG;

/**
 *
 *----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━━━━━神兽出没━━━━━━━━━━━━━━by:Lixm
 **/

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLongVideoPlay {
    private Instrumentation instrument;
    private UiDevice uiDevice;
    //设定等待时间
    private static final int SHORT_WAIT = 1;
    private static final int WAIT = 5;
    private static final int LONG_WAIT = 15;
    //暂停时间
    private static final int PauseTime = 30;
    //设定播放视频时间（短）
    private static final int PlayVideoShortTime = 60;
    //设定播放视频时间（长）
    private static final int PlayVideoLongTime = 660;
    //初始化
    String m_ObjId = "";
    private long m_Time;
    private UiObject2 m_uiObj = null;
    private String m_Expect = "";
    private String m_Actual = "";
    private String resultStr = "";
    private boolean m_Pass = false;
    private boolean resultFlag = false;

    @Before
    public void setUp() {
        instrument = InstrumentationRegistry.getInstrumentation();
        uiDevice = UiDevice.getInstance(instrument);
        m_Time = Utils.getCurSecond();   //耗时

        uiDevice.pressHome();
        systemWait(SHORT_WAIT);
    }

    @Test //电视剧播放
    public void LC_PLAY_01_TestLongVideoPlay() throws InterruptedException {
        System.out.println("电视剧的播放开始了，哇咔咔...");
        EnterTVVideoListPage();//进入电视剧列表页全部
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        this.RandomPlayTVVideo();
        systemWait(SHORT_WAIT);
        for (int i = 0; i <= 15; i++) {
            uiDevice.pressDPadCenter();
            UiObject TVTitle = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/detail_title")
                    .className("android.widget.TextView"));
            UiObject2 PayButton = uiDevice.findObject(By.text("付费"));
            if(PayButton == null){
                System.out.println("本片为免费影片，请尽情欣赏，呵呵哒.....");
                systemWait(LONG_WAIT);
                uiDevice.waitForIdle();
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                uiDevice.pressDPadLeft(); //左键从头播放
                systemWait(PlayVideoShortTime); //播放10+min
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                m_uiObj = uiDevice.findObject
                        (By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                Utils.writeCaseResult("视频播放失败", m_uiObj != null, m_Time);
                uiDevice.pressDPadCenter();//暂停
                systemWait(PauseTime); //暂停时间
                m_ObjId = "com.bestv.ott:id/control_panel_pause_layout_btn";
                m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/control_panel_pause_layout_btn"));
                Utils.writeCaseResult("视频暂停后无暂停标示", m_uiObj != null, m_Time);
                try {
                    uiDevice.pressDPadCenter();
                    systemWait(WAIT);
                    uiDevice.waitForIdle();
                    this.RightRightSpeedSpeed();
                    this.ProgressBarExists(10000);
                    systemWait(PlayVideoShortTime);//播放10+min
                    uiDevice.pressBack();
                    systemWait(SHORT_WAIT);
                    uiDevice.pressBack();
                    systemWait(LONG_WAIT);
                    UiObject2 DetailText = uiDevice.findObject(By.text("相关推荐"));
                    m_Actual = DetailText.getText();
                    m_Expect = "相关推荐";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult(TVTitle.getText(), m_Pass, m_Time);
                    uiDevice.pressBack();
                    systemWait(WAIT);
                    uiDevice.waitForIdle();
                    uiDevice.pressDPadRight();
                }catch (Throwable e) {
                    System.out.println("视频快进失败");
                    e.printStackTrace();
                }
            }else {
                System.out.println("本片为Vip影片,请去购买金卡会员好吗,麻溜哒...");
                systemWait(LONG_WAIT);
                uiDevice.waitForIdle();
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                uiDevice.pressDPadLeft(); //左键从头播放
                systemWait(PlayVideoShortTime); //播放1min
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                m_uiObj = uiDevice.findObject
                        (By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                Utils.writeCaseResult("视频播放失败", m_uiObj != null, m_Time);
                uiDevice.pressDPadCenter();//暂停
                systemWait(PauseTime); //暂停时间
                m_ObjId = "com.bestv.ott:id/control_panel_pause_layout_btn";
                m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/control_panel_pause_layout_btn"));
                Utils.writeCaseResult("视频暂停后无暂停标示", m_uiObj != null, m_Time);
                try {
                    uiDevice.pressDPadCenter();
                    systemWait(WAIT);
                    uiDevice.waitForIdle();
                    this.RightRightSpeedSpeed();
                    this.ProgressBarExists(10000);
                    systemWait(PlayVideoShortTime);//播放1min
                    uiDevice.pressBack();
                    systemWait(SHORT_WAIT);
                    uiDevice.pressBack();
                    systemWait(LONG_WAIT);
                    UiObject2 DetailText = uiDevice.findObject(By.text("相关推荐"));
                    m_Actual = DetailText.getText();
                    m_Expect = "相关推荐";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult(TVTitle.getText(), m_Pass, m_Time);
                    uiDevice.pressBack();
                    systemWait(WAIT);
                    uiDevice.waitForIdle();
                    uiDevice.pressDPadRight();
                } catch (Throwable e) {
                    System.out.println("视频快进失败");
                    e.printStackTrace();
                }
            }
        }
            try {
                uiDevice.pressBack();
                systemWait(SHORT_WAIT);
                uiDevice.pressBack();
                systemWait(WAIT);
                uiDevice.pressDPadDown();
                systemWait(WAIT);
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                uiDevice.pressDPadRight();
                UiObject Cell = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/tv_cell")
                        .className("android.widget.TextView").text("3"));
                Cell.clickAndWaitForNewWindow(15000);
                systemWait(PlayVideoLongTime);
                uiDevice.pressDPadCenter();
                UiObject Time1 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/time_current")
                        .className("android.widget.TextView"));
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoLongTime);
                uiDevice.pressDPadCenter();
                UiObject Time2 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/time_current")
                        .className("android.widget.TextView"));
                if (Time1.getText().equals(Time2.getText())) {
                    uiDevice.pressHome();
                    System.out.println("时间戳无变化，播放器问题");
                } else {
                    uiDevice.pressDPadCenter();
                    this.LeftLeftSpeedSpeed();
                }
            } catch (Throwable e) {
                System.out.println("！！！！！");
                e.printStackTrace();
                resultStr += e.toString();
                resultFlag = false;
            } finally {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
    }

    @Test //电影播放
    public void LC_PLAY_02_Test100FilmPlay() throws InterruptedException {
        final UiObject2 Player = uiDevice.findObject(By.clazz(Infos.S_CLASS_VIDEO_PLAYER));
        //注册监听器
        uiDevice.registerWatcher("testWatcher", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                System.out.println("监听器检查函数开始运行--播放器是否在运行");
                if(Player == null){
                    Log.i("testWatcher", "监听器被触发了");
                    return true;
                }
                Log.i("testWatcher", "监听器未被触发");
                return false;
            }
        });
        System.out.println("Are you ready...Let's GO GO GO");
        try {
            this.EnterFilmCommendHall();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            this.RandomPlayFilm();
            systemWait(SHORT_WAIT);
            for (int j = 0; j <= 66; j++){ //66大顺
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                uiDevice.waitForIdle();
                UiObject TextView = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/detail_title")
                        .className("android.widget.TextView"));
                UiObject2 PayButton = uiDevice.findObject(By.text("付费"));
                if(PayButton != null){
                    System.out.println("本片为Vip影片,请去购买金卡会员好吗,麻溜哒...");
                    uiDevice.resetWatcherTriggers();
                    Log.i("testWatcher", "重置监听器成功");
                    uiDevice.pressDPadCenter();
                    systemWait(WAIT);
                    uiDevice.pressDPadLeft();
                    systemWait(PlayVideoLongTime); //播放10+min
                    UiObject2 PayMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                    m_Actual = PayMedia.getText();
                    m_Expect = "请选择要购买的媒体或服务";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("非金卡会员试看影片10min后，跳转开通会员页失败",m_Pass,m_Time);
                    uiDevice.pressBack();
                    systemWait(LONG_WAIT);
                    UiObject2 DetailText = uiDevice.findObject(By.text("相关推荐"));
                    m_Actual = DetailText.getText();
                    m_Expect = "相关推荐";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult(TextView.getText(),m_Pass,m_Time);
                    uiDevice.pressBack();
                    uiDevice.pressDPadRight();
                    uiDevice.removeWatcher("testWatcher");
                    systemWait(WAIT);
                }else {
                    System.out.println("本片为免费影片，请尽情欣赏，呵呵哒.....");
                    uiDevice.resetWatcherTriggers();
                    Log.i("testWatcher", "重置监听器成功");
                    uiDevice.pressDPadCenter();
                    systemWait(LONG_WAIT);
                    uiDevice.pressDPadCenter();//暂停
                    systemWait(PauseTime); //暂停时间
                    m_ObjId = "com.bestv.ott:id/control_panel_pause_layout_btn";
                    m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/control_panel_pause_layout_btn"));
                    Utils.writeCaseResult("视频暂停后无暂停标示", m_uiObj != null, m_Time);
                    uiDevice.pressDPadCenter();
                    systemWait(PlayVideoLongTime);
                    this.RightRightSpeedSpeed();
                    systemWait(LONG_WAIT);
                    this.LeftLeftSpeedSpeed();
                    systemWait(LONG_WAIT);
                    m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                    m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                    Utils.writeCaseResult("播放器正常播放",m_uiObj != null,m_Time);
                    systemWait(PlayVideoLongTime); //播放10+min
                    uiDevice.pressBack();
                    uiDevice.pressBack();
                    systemWait(WAIT);
                    UiObject2 DetailText = uiDevice.findObject(By.text("相关推荐"));
                    m_Actual = DetailText.getText();
                    m_Expect = "相关推荐";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult(TextView.getText(),m_Pass,m_Time);
                    uiDevice.pressBack();
                    systemWait(WAIT);
                    uiDevice.pressDPadRight();
                    uiDevice.removeWatcher("testWatcher");
                    systemWait(WAIT);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //更新剧集播放
    public void LC_PLAY_03_TestTVVideoSelectionPlay() throws InterruptedException {
        try {
            System.out.println("电视剧选集选择->播放");
            EnterTVVideoCommendHall();
            uiDevice.pressDPadDown();
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject2 TVCell = uiDevice.findObject(By.text("选集"));
            UiObject TVTitle = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/detail_title")
                    .className("android.widget.TextView"));
//            UiObject TipButton = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/detail_tip_button")
//                    .className("android.widget.TextView"));
            HttpUtils utils = new HttpUtils();
            int CellNum = utils.getLatestTvTotalNumByName(TVTitle.getText());
            System.out.println("本部电视剧更新集数为：" + CellNum + "集");
            if (TVCell != null) {
                uiDevice.pressDPadRight();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadDown();
                systemWait(WAIT);
                if (CellNum >= 1 && CellNum <= 30) {
                    UiObject Cell_1 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/tv_cell")
                            .className("android.widget.TextView").text("1"));
                    systemWait(WAIT);
                    Cell_1.clickAndWaitForNewWindow(10000);
                    ProgressBarExists(10000);
                    systemWait(PlayVideoShortTime);
                    uiDevice.pressDPadDown();
                    systemWait(SHORT_WAIT);
                    m_ObjId = "com.bestv.ott:id/control_panel_pause_layout_btn";
                    m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/control_panel_pause_layout_btn"));
                    Utils.writeCaseResult("暂停标示", m_uiObj != null, m_Time);
                } else {
                    LeftLeftLeft();
                    UiObject Cell_30 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/tv_cell")
                            .className("android.widget.TextView").text("30"));
                    systemWait(SHORT_WAIT);
                    Cell_30.clickAndWaitForNewWindow(10000);
                    systemWait(SHORT_WAIT);
                    uiDevice.pressDPadLeft();
                    ProgressBarExists(10000);
                    systemWait(PlayVideoShortTime);
                    uiDevice.pressDPadCenter();
                    systemWait(SHORT_WAIT);
                    m_ObjId = "com.bestv.ott:id/control_panel_pause_layout_btn";
                    m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/control_panel_pause_layout_btn"));
                    Utils.writeCaseResult("暂停标示", m_uiObj != null, m_Time);
                }
            } else {
                Log.d(TAG, "LC_PLAY_03_TestTVVideoSelectionPlay: 跑偏了");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

//    @Test
//    public void testDemo(){
//        uiDevice.pressDPadCenter();
//        new CommonMethod().clickByText("全屏");
//        sleep(1000);
//    }

    @Test //时间转换
    public void testDemo2(){
        sleep(10000);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        UiObject2 time = uiDevice.findObject(By.res("com.bestv.ott:id/time_total").clazz("android.widget.TextView"));
        timeTrans(time.getText());
        Log.d(TAG, "testDemo2: "+timeTrans(time.getText()));
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
    private void ProgressBarExists(int Second){
        UiObject ProBar = uiDevice.findObject(new UiSelector().resourceId(Infos.S_PROCESS_BAR_ID));
        ProBar.waitUntilGone(Second);//在一定时间内判断控件是否消失
        systemWait(SHORT_WAIT);
    }

    //快速滑动右移
    private void RightRightSpeedSpeed() throws UiObjectNotFoundException {
        uiDevice.pressDPadRight();
        UiObject Swipe = uiDevice.findObject(new UiSelector().className("android.widget.SeekBar")
                .resourceId("com.bestv.ott:id/media_progress"));
        Swipe.swipeRight(10);
    }

    //快速滑动左移
    private void LeftLeftSpeedSpeed() throws UiObjectNotFoundException {
        uiDevice.pressDPadRight();
        UiObject Swipe = uiDevice.findObject(new UiSelector().className("android.widget.SeekBar")
                .resourceId("com.bestv.ott:id/media_progress"));
        Swipe.swipeLeft(10);
    }

    //error截图功能
    public String TakeScreenShot(String VideoName){
        Environment.getExternalStoragePublicDirectory("/mnt/sdcard/autotest/image/");
        File ImageFile = new File("/mnt/sdcard/autotest/image/test.png");
        uiDevice.takeScreenshot(ImageFile);
        if (!ImageFile.exists()) {
            Assert.assertTrue(ImageFile.mkdirs());
        }if(m_uiObj == null){
            uiDevice.takeScreenshot(ImageFile);
        }
        return VideoName;
    }

    //等待时间
    public void sleep(int sleep){
        try {
            Thread.sleep(sleep*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void EnterTVVideoListPage() {
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
        uiDevice.pressDPadLeft();
        systemWait(LONG_WAIT);
    } //进入电视剧全部列表页

    private void EnterTVVideoCommendHall() {
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
    } //进入电视剧推荐大厅

    private void EnterFilmCommendHall() {
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadDown();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
        uiDevice.pressDPadLeft();
        systemWait(LONG_WAIT);
    } //进入电影全部列表页

    private void systemWait(int seconds) {

        SystemClock.sleep(seconds * 1000);
    } //等待时间

    private void RandomPlayTVVideo() {
        systemWait(WAIT);
        Random moveTimes = new Random(1);
        int i;
        i = moveTimes.nextInt(3);
        for (int j = 0; j <= i; j++) {
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    } //随机播放电视剧

    private void RandomPlayFilm(){
        systemWait(WAIT);
        Random moveTimes = new Random(1);
        int i;
        i = moveTimes.nextInt(4);
        for (int j = 0; j <= i; j++) {
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    } //随机播放电影

    private void LeftLeftLeft(){
        int i = 0;
        while( i <= 13){
            i++;
            uiDevice.pressDPadLeft();
            systemWait(SHORT_WAIT);
        }
    } //L*14

}
