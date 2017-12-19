package tv.fun.appsautotest.testCases;

import android.app.Instrumentation;
import android.app.Notification;
import android.app.UiAutomation;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.Tracer;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.Random;

import tv.fun.appsautotest.common.CommonMethod;
import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

import static android.content.ContentValues.TAG;
import static android.os.SystemClock.uptimeMillis;
import static android.support.test.uiautomator.By.text;

/***
 * Created lxm on 2016/6/10
 * TestCase:
 ***/

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestVipVideoV3_2 extends TestCase{
    public Instrumentation instrument;
    private UiDevice uiDevice;
    //设定等待时间
    private static final int SHORT_WAIT = 2;
    private static final int WAIT = 6;
    private static final int LONG_WAIT = 15;
    //设定一段播放时间
    private static final int PlayVideoTime = 60;
    //设定一段播放时间（中）
    private static final int PlayVideoMidTime = 300;
    //设定播放视频时间（长）
    private static final int PlayVideoLongTime = 660;
    //初始化
    private String m_ObjId = "";
    private UiObject2 m_uiObj = null;
    private long m_Time;
    private String m_Expect = "";
    private String m_Actual = "";
    private String resultStr = "";
    private boolean resultFlag = true;
    private boolean m_Pass = false;

    @BeforeClass
    public static void BeforeClass() {

        System.out.println("进入金卡会员页面，开启金卡专区的自动化测试了..");
    } //测试项目开始前运行（仅一次），如清除缓存数据、安装应用等

    @Before
    public void setUp() {
        instrument = InstrumentationRegistry.getInstrumentation();
        uiDevice = UiDevice.getInstance(instrument);
        m_Time=Utils.getCurSecond();   //耗时

        uiDevice.pressHome();
        uiDevice.waitForIdle(5000);
        UiObject2 VipArea = this.getTabFromLauncherHomeByText(uiDevice, "金卡会员");
        this.openTabFromLauncherHomeByTextView(uiDevice, VipArea);
        systemWait(WAIT);
    } //测试开始前运行（每个@Test运行前都会运行一次），如打开应用等

    @After
    public void clearUp() {
        backPageMethod();
        Configurator configurator = Configurator.getInstance();
        configurator.setActionAcknowledgmentTimeout(5000);
    } //测试结束后（每个@Test结束后都会运行一次），如退出应用等

    @AfterClass
    public static void AfterClass(){

        System.out.println("See You Later");
        System.gc();
    } //测试项目结束时运行（仅一次），如卸载应用等

    @Test  //获取用例名
    public void test(){

        TvCommon.printAllMethods(this.getClass().getName());
    }

    @Test //初进金卡会员页
    public void LC_VIP_01_EnterVipPage() {
        try {
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/vip_title"));
            m_ObjId = "com.bestv.ott:id/vip_title";
            Utils.writeCaseResult("进入金卡会员页面失败",m_uiObj != null,m_Time);
        }catch (Exception e){
            e.printStackTrace();
            resultFlag= false;
            resultStr += e.toString();
        }
        finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //全屏播放会员小窗口
    public void LC_VIP_02_HatchToFullScreenPlay() {
        try {
            uiDevice.pressEnter();
            CommonMethod.systemWait(PlayVideoTime);
            //加入断言判定是否全屏播放,via全屏播放时唯一的标示
            m_ObjId = Infos.S_LC_VIP_FULLSCREEN_BUTTON_ID;
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/player_view"));
            Utils.writeCaseResult("进入全屏播放时失败，无法抓取到控件", m_uiObj != null, m_Time);
        }catch(Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test //小窗口跳转观看完整影片
    public void LC_VIP_03_PlayCompleteVideo() {
        try {
            uiDevice.pressEnter();
            CommonMethod.systemWait(LONG_WAIT);
            UiObject2 detEnter = uiDevice.findObject(By.res("com.bestv.ott:id/player_view"));
            Assert.assertNotNull(detEnter);
            uiDevice.pressDPadCenter();
            this.waitForNewWindowIdle(17000);
            UiObject payButton = uiDevice.findObject(new UiSelector().text("付费"));
            if(payButton.exists()){
                systemWait(PlayVideoMidTime);
                UiObject2 payMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                m_Actual = payMedia.getText();
                m_Expect = "请选择要购买的媒体或服务";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("非金卡会员试看影片10min后，跳转开通会员页失败",m_Pass,m_Time);
            }else {
                UiObject2 fullScreen = uiDevice.findObject(By.text("全屏"));
                this.openTabFromLauncherHomeByVipText(uiDevice,fullScreen);
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡电影视频播放失败",m_uiObj !=null,m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test //会员特权页面
    public void LC_VIP_04_VipMemberPage() {
        try {
            rightMoveMethod(10);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            m_ObjId = "com.bestv.ott:id/detail_background";
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/detail_background"));
            Utils.writeCaseResult("进入金卡会员页面失败",m_uiObj !=null,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //开通金卡会员选项跳转
    public void LC_VIP_05_PersonalSkipVipPage() throws NullPointerException {
        try {
            upMoveMethod(1);
            UiObject vipText = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/vip"));
            if(vipText.exists()) {
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 tabView = uiDevice.findObject(text("金卡会员30天"));
                m_Expect = "金卡会员30天";
                m_Actual = tabView.getText();
                m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
                Utils.writeCaseResult("进入金卡会员页面时跳转失败：未抓到金卡会员跳转成功时关键字", m_Pass, m_Time);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //兑换页面
    public void LC_VIP_06_ExchangeButtonVipPage() {
        try {
            uiDevice.pressDPadUp();
            SystemClock.sleep(3000);
            rightMoveMethod(2);
            UiObject exchange = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/exchange"));
            if(exchange.exists()) {
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 tabView = uiDevice.findObject(text("兑换"));
                m_Expect = "兑换";
                m_Actual = tabView.getText();
                m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
                Utils.writeCaseResult("进入兑换页面时失败：未抓到页面关键字段", m_Pass, m_Time);
            }else {
                Assert.assertTrue(false);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //兑换测试--输入错误的兑换券
    public void LC_VIP_07_InputErrorCharacterOnExchangePage() {
        try {
            uiDevice.pressDPadUp();
            SystemClock.sleep(3000);
            rightMoveMethod(2);
            UiObject exchange = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/exchange"));
            if(exchange.exists()) {
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 tabView1 = uiDevice.findObject(text("兑换"));
                Assert.assertEquals("成功进入兑换页面", "兑换", tabView1.getText());
                UiObject2 editText = uiDevice.findObject(text("请输入兑换券编码"));
                Configurator configurator = Configurator.getInstance();
                configurator.setKeyInjectionDelay(1000);
                editText.setText("TestData");
                SystemClock.sleep(1000);
                uiDevice.pressBack();
                SystemClock.sleep(1000);
                downMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 textView = uiDevice.findObject(text("兑换码输入错误，请重试！"));
                m_Expect = "兑换码输入错误，请重试！";
                m_Actual = textView.getText();
                m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
                Utils.writeCaseResult("兑换系统出现问题：错误兑换码兑换成功", m_Pass, m_Time);
            }else {
                Assert.assertTrue("兑换",false);
            }
        }catch(Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //个人中心页面
    public void LC_VIP_08_ButtonSkipPersonalPage() {
        try {
            enterPersonalCenterPage();
        }catch (Exception e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //会员开通or会员续费页面
    public void LC_VIP_09_VipDisplayCardInPersonalPage(){
        try {
            enterPersonalCenterPage();
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 cardNum = uiDevice.findObject(By.res("com.bestv.ott:id/scollview"));
            List<UiObject2> cardCount = cardNum.findObjects(By.clazz("android.widget.RelativeLayout"));
            Log.d(TAG, "lxm: "+cardCount.size());  //==5
            if(cardCount.size() >= 4){
                UiObject2 tabView = uiDevice.findObject(text("金卡会员30天"));
                m_Expect = "金卡会员30天";
                m_Actual = tabView.getText();
                m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
                Utils.writeCaseResult("进入金卡会员页面时跳转失败：未抓到金卡会员跳转成功时关键字", m_Pass, m_Time);
            }else {
                Assert.assertTrue(false);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //支付代扣页面
    public void LC_VIP_10_PaymentWithHoldingInPersonalPage(){
        try {
            enterPersonalCenterPage();
            UiObject2 payCard = uiDevice.findObject(By.text("支付代扣"));
            this.openTabFromLauncherHomeByTextView(uiDevice,payCard);
            UiObject2 tabView = uiDevice.findObject(text("取消代扣"));
            m_Expect = "取消代扣";
            m_Actual = tabView.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入取消代扣页面时跳转失败：未抓到关键字", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //福利领取
    public void LC_VIP_11_WelfareCollectionInPersonalPage(){
        try {
            enterPersonalCenterPage();
            UiObject2 cardNum = uiDevice.findObject(By.res("com.bestv.ott:id/grid"));
            List<UiObject2> cardCount = cardNum.findObjects(By.clazz("android.widget.RelativeLayout"));
            Log.d(TAG, "lxm: "+cardCount.size());
            if(cardCount.size() == 7){
                UiObject2 WelCard = uiDevice.findObject(By.text("福利领取"));
                m_Expect = "福利领取";
                m_Actual = WelCard.getText();
                m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
                Utils.writeCaseResult("个人中心未抓到关键字", m_Pass, m_Time);
            }else {
                Assert.assertTrue(false);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //卡券兑换
    public void LC_VIP_12_CardExchangeInPersonalPage(){
        try {
            enterPersonalCenterPage();
            UiObject2 cardEx = uiDevice.findObject(By.text("卡券兑换"));
            this.openTabFromLauncherHomeByTextView(uiDevice,cardEx);
            UiObject2 tabView = uiDevice.findObject(text("兑换"));
            m_Expect = "兑换";
            m_Actual = tabView.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入卡券兑换页面时跳转失败：未抓到关键字", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //消费记录
    public void LC_VIP_13_PayRecordInPersonalPage(){
        try {
            enterPersonalCenterPage();
            UiObject2 record = uiDevice.findObject(By.text("消费记录"));
            this.openTabFromLauncherHomeByTextView(uiDevice,record);
            UiObject2 tabView = uiDevice.findObject(By.textContains("客服电话:"));
            Log.d(TAG, "lxm: "+tabView.getText());
            m_Expect = "客服电话: 400 600 6258";
            m_Actual = tabView.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult(tabView.getText()+" 显示错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //观影券
    public void LC_VIP_14_ViewingCouponInPersonalPage(){
        try {
            enterPersonalCenterPage();
            UiObject2 coupon = uiDevice.findObject(By.text("观影券"));
            this.openTabFromLauncherHomeByTextView(uiDevice,coupon);
            UiObject2 tabView = uiDevice.findObject(By.textContains("客服电话:"));
            m_Expect = "客服电话: 400 600 6258";
            m_Actual = tabView.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult(tabView.getText()+" 显示错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            if(resultStr != null ){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //会员状态页
    public void LC_VIP_15_MemberStatusInPersonalPage(){
        try {
            enterPersonalCenterPage();
            uiDevice.pressDPadLeft();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 tabView = uiDevice.findObject(text("会员头像设置"));
            m_Expect = "会员头像设置";
            m_Actual = tabView.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入会员头像设置页面时跳转失败：未抓到关键字", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test //即将上映--影片收藏
    public void LC_VIP_16_ComingMoviesCollectAndPlay() throws NullPointerException{
        try {
            rightMoveMethod(3);
            systemWait(WAIT);
            UiObject2 tabView = this.getTabFromLauncherHomeByText(uiDevice, "即将上映");
            this.openTabFromLauncherHomeByTextView(uiDevice, tabView); //进入即将上映页面
            UiObject2 cardNum = uiDevice.findObject(By.clazz("android.support.v7.widget.RecyclerView"));
            List<UiObject2> cardCount = cardNum.findObjects(By.clazz("android.widget.RelativeLayout"));
            sleep(3000);
            for(int i= 0; i<=cardCount.size(); i++){
                rightMoveMethod(1);
                UiObject2 resId = uiDevice.findObject(By.res("com.bestv.ott:id/fav_state"));//通过Res_id来获取Text值，判断当前button选项
                if (resId != null && resId.getText().equals("加入收藏")) {
                    downMoveMethod(1);
                    uiDevice.pressDPadCenter();   //点击确定加入收藏
                    systemWait(SHORT_WAIT);
                    upMoveMethod(1);
                    uiDevice.pressDPadCenter();
                    waitForNewWindowCollectButton(14000);
                    break;
                }
               Utils.Print("没有更多的上映新片");
            }
        }catch(Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //播放金卡电影
    public void LC_VIP_17_PlayVipVideo() {
        try {
            rightMoveMethod(2);
            uiDevice.pressDPadCenter();
            uiDevice.wait(Until.findObject(By.text("最新院线")),10000);
            SystemClock.sleep(8000);
            randomPlayFilm(18);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            waitForNewWindowIdle(18000);
            UiObject payButton = uiDevice.findObject(new UiSelector().text("付费"));
            if(payButton.exists()){
                systemWait(PlayVideoLongTime);
                UiObject2 payMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                m_Actual = payMedia.getText();
                m_Expect = "请选择要购买的媒体或服务";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("非金卡会员试看影片10min后，跳转开通会员页失败",m_Pass,m_Time);
            }else {
                leftMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡电影视频播放失败",m_uiObj !=null,m_Time);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        }
        finally{
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //播放金卡电视剧
    public void LC_VIP_18_PlayVipTVVideo(){
        try {
            UiObject2 VipVideo = uiDevice.findObject(By.text("金卡-电视剧").res("com.bestv.ott:id/title"));
            this.openTabFromLauncherHomeByTextView(uiDevice,VipVideo);
            this.randomPlayFilm(9);
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            SystemClock.sleep(1500);
            uiDevice.pressDPadCenter();
            waitForNewWindowIdle(18000);
            UiObject payButton = uiDevice.findObject(new UiSelector().text("付费"));
            if(payButton.exists()){
                sleep(3000);
                UiObject2 watchOne = uiDevice.findObject(By.text("试看第1集").res("com.bestv.ott:id/watch_try_text"));
                m_Actual = watchOne.getText();
                m_Expect = "试看第1集";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("试看",m_Pass,m_Time);
            }else {
                UiObject2 fullScreen = uiDevice.findObject(By.text("全屏"));
                this.openTabFromLauncherHomeByVipText(uiDevice,fullScreen);
                uiDevice.pressDPadCenter();   //+多语言
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡电影视频播放失败",m_uiObj !=null,m_Time);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        }
        finally{
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //4K专区
    public void LC_VIP_19_Play4KVideo() throws NullPointerException {
        try {
            rightMoveMethod(2);
            downMoveMethod(2);
            uiDevice.pressDPadCenter();
            uiDevice.wait(Until.findObject(By.text("4K")),18000);
            sleep(10000);
            randomPlayFilm(19);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            waitForNewWindowIdle(18000);
            UiObject payButton = uiDevice.findObject(new UiSelector().text("付费"));
            if(payButton.exists()){
                systemWait(PlayVideoLongTime);
                UiObject2 payMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                m_Actual = payMedia.getText();
                m_Expect = "请选择要购买的媒体或服务";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("非金卡会员试看影片10min后，跳转开通会员页失败",m_Pass,m_Time);
            }else {
                leftMoveMethod(2);
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡电影视频播放失败",m_uiObj !=null,m_Time);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //金卡纪实
    public void LC_VIP_20_PlayDocumentaryVideo() {
        try {
            UiObject2 vipVideo = uiDevice.findObject(By.text("金卡-纪实").res("com.bestv.ott:id/title"));
            this.openTabFromLauncherHomeByTextView(uiDevice,vipVideo);
            randomPlayFilm(6);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            waitForNewWindowIdle(18000);
            UiObject2 textView = uiDevice.findObject(By.text("金卡专享").res("com.bestv.ott:id/detail_vip_text"));
            m_Expect = "金卡专享";
            m_Actual = textView.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入详情页失败", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //金卡少儿
    public void LC_VIP_21_PlayKidVideo() throws NullPointerException{
        try {
            uiDevice.waitForIdle();
            rightMoveMethod(2);
            downMoveMethod(1);
            uiDevice.pressDPadCenter();
            uiDevice.wait(Until.findObject(By.text("动画电影")),18000);
            SystemClock.sleep(8000);
            randomPlayFilm(27);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            waitForNewWindowIdle(18000);
            UiObject2 payButton = uiDevice.findObject(By.text("付费"));
            if(payButton != null){
                systemWait(PlayVideoLongTime);
                UiObject2 payMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                m_Actual = payMedia.getText();
                m_Expect = "请选择要购买的媒体或服务";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("非金卡会员试看影片10min后，跳转开通会员页失败",m_Pass,m_Time);
            }else {
                leftMoveMethod(2);
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡电影视频播放失败",m_uiObj !=null,m_Time);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //金卡动漫
    public void LC_VIP_22_PlayComicVideo() {
        try {
            UiObject2 VipVideo = uiDevice.findObject(By.text("金卡-动漫").res("com.bestv.ott:id/title"));
            this.openTabFromLauncherHomeByTextView(uiDevice,VipVideo);
            this.randomPlayFilm(3);
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            waitForNewWindowIdle(18000);
            UiObject2 payButton = uiDevice.findObject(By.text("付费"));
            if(payButton != null){
                sleep(5000);
                UiObject2 watchOne = uiDevice.findObject(By.text("试看第1集").res("com.bestv.ott:id/watch_try_text"));
                m_Actual = watchOne.getText();
                m_Expect = "试看第1集";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("试看片花",m_Pass,m_Time);
            }else {
                leftMoveMethod(2);
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡电影视频播放失败",m_uiObj !=null,m_Time);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //视频分类页入口进入金卡列表页
    public void LC_VIP_23_VideoClassifySkipVip() throws NullPointerException{
        try {
            backToLauncherHome(uiDevice);
            this.enterVideoClassifyPage();
            downMoveMethod(2);
            uiDevice.pressDPadCenter();
            uiDevice.wait(Until.findObject(By.text("金卡电影")),18000);
            systemWait(LONG_WAIT);
            uiDevice.pressDPadDown();
            SystemClock.sleep(1500);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.waitForIdle(20000);
            UiObject2 payButton = uiDevice.findObject(By.text("付费"));
            if(payButton != null){
                systemWait(PlayVideoLongTime);
                UiObject2 payMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                m_Actual = payMedia.getText();
                m_Expect = "请选择要购买的媒体或服务";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("非金卡会员试看影片10min后，跳转开通会员页失败",m_Pass,m_Time);
            }else {
                leftMoveMethod(2);
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡电影视频播放失败",m_uiObj !=null,m_Time);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test //会员包月支付流程
    public void LC_VIP_24_PersonalPagePayVip() {
        try {
            upMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject2 textView = uiDevice.findObject(text("金卡会员30天").res("com.bestv.ott:id/commodity_type"));
            this.openTabFromLauncherHomeByTextView(uiDevice,textView);
            uiDevice.waitForIdle(6000);
            systemWait(WAIT);
            UiObject2 zhiFuBao = uiDevice.findObject(By.clazz("android.widget.ImageView")
            .res("com.bestv.mediapay:id/pay_image"));
            if(zhiFuBao != null){
                uiDevice.pressBack();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
            }else{
                Assert.assertTrue("BUG",false);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test //付费类剧集点击跳转
    public void LC_VIP_25_VipEpisodesListClickable(){
        try {
            UiObject2 VipVideo = uiDevice.findObject(By.text("金卡-电视剧").res("com.bestv.ott:id/title"));
            this.openTabFromLauncherHomeByTextView(uiDevice,VipVideo);
            this.randomPlayFilm(3);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            waitForNewWindowIdle(18000);
            UiObject payButton = uiDevice.findObject(new UiSelector().text("付费"));
            UiObject tvCell = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/tv_cell_mask")
            .className("android.widget.ImageView"));
            uiDevice.wait(Until.findObject(By.text("试看第1集").res("com.bestv.ott:id/watch_try_text")),18000);
            if(payButton.exists()) {
                if (tvCell.exists()) {
                    downMoveMethod(1);
                    rightMoveMethod(5);
                    uiDevice.pressDPadCenter();
                    systemWait(LONG_WAIT);
                    UiObject2 payMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                    m_Actual = payMedia.getText();
                    m_Expect = "请选择要购买的媒体或服务";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("跳转开通会员页失败", m_Pass, m_Time);
                } else {
                    downMoveMethod(1);
                    uiDevice.pressDPadCenter();
                    systemWait(PlayVideoLongTime);
                    UiObject2 payMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                    m_Actual = payMedia.getText();
                    m_Expect = "请选择要购买的媒体或服务";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("跳转开通会员页失败", m_Pass, m_Time);
                }
            }else {
                Assert.assertTrue("付费用户",true);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        }
        finally{
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    private UiObject2 getTabFromLauncherHomeByText(UiDevice device, String tabText) {
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

    private void openTabFromLauncherHomeByTextView(UiDevice device, UiObject2 TabView) {
        TabView.click();
        systemWait(SHORT_WAIT);
        device.pressEnter();
        device.waitForIdle();
        systemWait(WAIT);
    }

    private void openTabFromLauncherHomeByVipText(UiDevice device, UiObject2 TabView) {
        TabView.click();
        device.waitForIdle();
        systemWait(WAIT);
    }

    private void enterVideoClassifyPage(){
        try {
            uiDevice.pressDPadRight();
            SystemClock.sleep(1000);
            downMoveMethod(2);
            uiDevice.pressEnter();
            systemWait(WAIT);
            UiObject2 Classify = uiDevice.findObject(By.text("视频分类"));
            Assert.assertEquals("视频分类", Classify.getText());
            systemWait(WAIT);
        }catch (Throwable e){
            e.printStackTrace();
        }
    } //进入视频分类页面

    private void enterPersonalCenterPage(){
        uiDevice.pressDPadUp();
        systemWait(WAIT);
        rightMoveMethod(1);
        UiObject account = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/account"));
        if(account.exists()){
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 PerPage = uiDevice.findObject(By.text("个人中心"));
            Assert.assertEquals("个人中心",PerPage.getText());
        }else {
            Assert.assertTrue(false);
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

    private void backToLauncherHome(UiDevice device) {
        device.pressHome();
        device.waitForIdle();
        systemWait(WAIT);
    }

    private static void sleep(long ms) {
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

    private void systemWait(int seconds) {

        SystemClock.sleep(seconds * 1000);
    } //等待时间设定

    private void rightMoveMethod(int RightMove){
        try {
            int i = 1;
            while (i <= RightMove) {
                i++;
                uiDevice.pressDPadRight();
                SystemClock.sleep(1000);
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    } //Right*

    private void leftMoveMethod(int LeftMove){
        try {
            int i = 1;
            while (i <= LeftMove) {
                i++;
                uiDevice.pressDPadLeft();
                systemWait(SHORT_WAIT);
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    } //Left*

    private void downMoveMethod(int DownMove){
        try {
            int i = 1;
            while (i <= DownMove) {
                i++;
                uiDevice.pressDPadDown();
                SystemClock.sleep(1000);
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    } //Down*

    private void upMoveMethod(int UpMove){
        try {
            int i = 1;
            while (i <= UpMove) {
                i++;
                uiDevice.pressDPadUp();
                SystemClock.sleep(1000);
            }
        }catch (Throwable e){
           e.printStackTrace();
        }
    } //Up*

    private void backPageMethod(){
        uiDevice.pressBack();
        SystemClock.sleep(1000);
        uiDevice.pressBack();
    } //Back*

    private void sleep(int sleep){
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param id 根据ID查找对象，并返回对象UiObject
     */
    public UiObject findById(String id){
        UiObject obj = uiDevice.findObject(new UiSelector().resourceId(id));
        return obj;
    }

    private void waitForNewWindowIdle(long time){
        try {
            Tracer.trace(time);
            sleep(time);
            UiObject2 fullScreen = uiDevice.findObject(By.text("全屏").res("com.bestv.ott:id/discripse"));
            if (fullScreen != null) {
                Assert.assertTrue("进入详情页后未找到全屏选项",true);
            } else {
                Utils.funAssert("等待18s,如果界面还没有打开则超时异常", false);
            }
        }catch (Throwable e){
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
                Utils.writeCaseResult("menu选项收藏后应变为已收藏", m_Pass, m_Time);
            } else {
                sleep(2000);
                UiObject2 Text = uiDevice.findObject(text("已收藏"));  //加入断言判定详情页是否变为已收藏
                m_Expect = "已收藏";
                m_Actual = Text.getText();
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("详情页收藏显示错误：收藏后应变为已收藏", m_Pass, m_Time);
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    public void initToastListener(){
        instrument = InstrumentationRegistry.getInstrumentation();
        instrument.getUiAutomation().setOnAccessibilityEventListener(new UiAutomation.OnAccessibilityEventListener() {
            @Override
            public void onAccessibilityEvent(AccessibilityEvent event) {
                //判断是否是通知事件
                try {
                    if(event.getEventType() != AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED){
                        return;
                    }
                    //获取消息来源
                    String sourcePackageName = (String)event.getPackageName();
                    //获取时间具体信息
                    Parcelable parcelable = event.getParcelableData();
                    //如果不是下拉框信息，则为其它通知消息，包括Toast
                    if(!(parcelable instanceof Notification)){
                        String toastMessage = (String)event.getText().get(0);
                        long toastOccurTime = event.getEventTime();
                        Log.i("autotest","信息名称:"+toastMessage);
                        if(toastMessage.contains("")){
                            JSONObject.quote(""); //jsonObject,put("")
                        }else if(toastMessage.contains("")){
                            JSONObject.quote(""); //jsonObject,put("")
                        }else {
                        }
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        });

    } //Toast处理
}