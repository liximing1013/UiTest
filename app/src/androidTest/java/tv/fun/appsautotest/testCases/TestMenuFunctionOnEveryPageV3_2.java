package tv.fun.appsautotest.testCases;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

import static android.support.test.uiautomator.By.text;

/**
 * Created by lxm on 2017/6/11
 * 测试menu键在各个页面的操作
 * TestCase:
 **/

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMenuFunctionOnEveryPageV3_2 extends TestCase{
    private Instrumentation instrument;
    private UiDevice uiDevice;
    //设定等待时间
    private static final int SHORT_WAIT = 1;
    private static final int WAIT = 5;
    private static final int LONG_WAIT = 12;
    //设定一段播放时间
    private static final int PlayVideoTime = 60;
    //初始化
    private String m_ObjId = "";
    private long m_Time;
    private UiObject2 m_uiObj = null;
    private boolean m_Pass = false;
    private boolean resultFlag = true;
    private String m_Expect = "";
    private String m_Actual = "";
    private String resultStr = "";
    private static final String TAG = "";

    @BeforeClass
    public static void BeforeClass(){

        System.out.println("各个页面的menu键的响应测试开始了...");
    }

    @Before
    public void setUp() {
        instrument = InstrumentationRegistry.getInstrumentation();
        uiDevice = UiDevice.getInstance(instrument);
        m_Time= Utils.getCurSecond();   //耗时

        backToLauncherHome(uiDevice);
        uiDevice.waitForIdle();
        systemWait(WAIT);
    }

    @After //运行脚本之后回到launcher
    public void clearUp() {

        backPageMethod();
        systemWait(WAIT);
    }

    @AfterClass
    public static void AfterClass(){
        System.gc();
        System.out.println("See You Later");
    }

    @Test  //获取用例名
    public void test(){

        TvCommon.printAllMethods(this.getClass().getName());
    }

    @Test  //搜索Tab页面menu操作
    public void LC_MENU_01_SearchTabMenuOperation(){
        try {
            upMoveMethod(1);
            leftMoveMethod(1);
            systemWait(WAIT);
            UiObject2 tabName = uiDevice.findObject(By.text("电视"));
            if(tabName.isSelected()){
                leftMoveMethod(1);
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Menu = uiDevice.findObject(By.text("搜索"));
                if(Menu != null) {
                    uiDevice.pressDPadCenter();
                    systemWait(WAIT);
                    UiObject2 SearchPage = uiDevice.findObject(By.text("用手机搜片"));
                    m_Actual = SearchPage.getText();
                    m_Expect = "用手机搜片";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("跳转搜索页面错误", m_Pass, m_Time);
                }
            }else {
                Assert.assertTrue("电视Tab未被选中",false);
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

    @Test  //电视Tab页面menu操作
    public void LC_MENU_02_TVTabMenuOperation() {
        try {
            upMoveMethod(1);
            uiDevice.pressDPadLeft();
            systemWait(WAIT);
            UiObject2 tabName = uiDevice.findObject(By.text("电视"));
            if(tabName.isSelected()){
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Menu = uiDevice.findObject(By.text("搜索"));
                if(Menu != null) {
                    uiDevice.pressDPadCenter();
                    systemWait(WAIT);
                    UiObject2 SearchPage = uiDevice.findObject(By.text("用手机搜片"));
                    m_Actual = SearchPage.getText();
                    m_Expect = "用手机搜片";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("跳转搜索页面错误", m_Pass, m_Time);
                }
            }else {
                Assert.assertTrue("电视Tab未被选中",false);
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

    @Test  //视频Tab页面menu操作
    public void LC_MENU_03_VideoTabMenuOperation() {
        try {
            upMoveMethod(1);
            systemWait(WAIT);
            UiObject2 tabName = uiDevice.findObject(By.text("视频"));
            if(tabName.isSelected()){
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Menu = uiDevice.findObject(By.text("视频分类"));
                if(Menu != null) {
                    rightMoveMethod(1);
                    systemWait(SHORT_WAIT);
                    uiDevice.pressDPadCenter();
                    systemWait(WAIT);
                    UiObject2 Classify = uiDevice.findObject(By.text("视频分类"));
                    m_Actual = Classify.getText();
                    m_Expect = "视频分类";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("跳转视频分类页面错误", m_Pass, m_Time);
                }
            }else {
                Assert.assertTrue("视频Tab未被选中",false);
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

    @Test  //会员Tab页面menu操作
    public void LC_MENU_04_VipTabMenuOperation(){
        try {
            upMoveMethod(1);
            String[] TabName = {"会员", "体育", "生活", "应用", "游戏", "设置"};
            for (int i = 0; i <= TabName.length - 1; i++) {
                if (TabName[i].equals("会员")) {
                    rightMoveMethod(i + 1);
                    UiObject2 tabName = uiDevice.findObject(By.text("会员"));
                    if (tabName.isSelected()) {
                        uiDevice.pressMenu();
                        systemWait(WAIT);
                        UiObject2 Menu = uiDevice.findObject(By.text("设置"));
                        Assert.assertEquals("设置", Menu.getText());
                        rightMoveMethod(2);
                        uiDevice.pressDPadCenter();
                        systemWait(WAIT);
                        UiObject2 classify = uiDevice.findObject(By.text("设 置"));
                        m_Actual = classify.getText();
                        m_Expect = "设 置";
                        m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                        Utils.writeCaseResult("跳转设置页面错误", m_Pass, m_Time);
                    } else {
                        Assert.assertTrue("首页Tab位置错误", false);
                    }
                    break;
                }
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

    @Test  //体育Tab页面menu操作
    public void LC_MENU_05_SportsTabMenuOperation(){
        try {
            upMoveMethod(1);
            String[] TabName = {"会员", "体育", "生活", "应用", "游戏", "设置"};
            int i = 0;
            for (; i <= TabName.length-1; i++) {
                if (TabName[i].equals("体育")) {
                    rightMoveMethod(i + 1);
                    UiObject2 tabName = uiDevice.findObject(By.text("体育"));
                    if (tabName.isSelected()) {
                        uiDevice.pressMenu();
                        systemWait(WAIT);
                        UiObject2 Menu = uiDevice.findObject(By.text("设置"));
                        Assert.assertEquals("设置", Menu.getText());
                        rightMoveMethod(2);
                        uiDevice.pressDPadCenter();
                        systemWait(WAIT);
                        UiObject2 Classify = uiDevice.findObject(By.text("设 置"));
                        m_Actual = Classify.getText();
                        m_Expect = "设 置";
                        m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                        Utils.writeCaseResult("跳转设置页面错误", m_Pass, m_Time);
                    } else {
                        Assert.assertTrue("首页Tab位置错误",false);
                    }
                    break;
                }
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

    @Test  //生活Tab页面menu操作
    public void LC_MENU_06_LifeTabMenuOperation(){
        try {
            upMoveMethod(1);
            String[] TabName = {"会员", "体育", "生活", "应用", "游戏", "设置"};
            for(int i=0; i<= TabName.length-1;i++) {
                if(TabName[i].equals("生活")) {
                    rightMoveMethod(i + 1);
                    UiObject2 tabName = uiDevice.findObject(By.text("生活"));
                    if (tabName.isSelected()) {
                        uiDevice.pressMenu();
                        systemWait(WAIT);
                        UiObject2 Menu = uiDevice.findObject(By.text("视频分类"));
                        Assert.assertEquals("视频分类", Menu.getText());
                        rightMoveMethod(1);
                        uiDevice.pressDPadCenter();
                        systemWait(WAIT);
                        UiObject2 Classify = uiDevice.findObject(By.text("视频分类"));
                        m_Actual = Classify.getText();
                        m_Expect = "视频分类";
                        m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                        Utils.writeCaseResult("跳转视频分类页面错误", m_Pass, m_Time);
                    } else {
                        Assert.assertTrue("首页Tab位置错误", false);
                    }
                    break;
                }
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

    @Test  //应用Tab页面menu操作
    public void LC_MENU_07_AppTabMenuOperation() {
        try {
            upMoveMethod(1);
            String[] TabName = {"会员", "体育", "生活", "应用", "游戏", "设置"};
            for (int i = 0; i <= TabName.length - 1; i++) {
                if(TabName[i].equals("应用")){
                    rightMoveMethod(i+1);
                    UiObject2 tabName = uiDevice.findObject(By.text("应用"));
                    if (tabName.isSelected()) {
                        uiDevice.pressMenu();
                        systemWait(WAIT);
                        UiObject2 Menu = uiDevice.findObject(By.text("搜索"));
                        Assert.assertEquals("搜索", Menu.getText());
                        uiDevice.pressDPadCenter();
                        systemWait(WAIT);
                        UiObject2 searchPage = uiDevice.findObject(By.text("用手机搜片"));
                        m_Actual = searchPage.getText();
                        m_Expect = "用手机搜片";
                        m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                        Utils.writeCaseResult("跳转搜索页面错误", m_Pass, m_Time);
                    } else {
                        Assert.assertTrue("首页Tab位置错误",false);
                    }
                    break;
                }
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

    @Test  //游戏Tab页面menu操作
    public void LC_MENU_08_GameTabMenuOperation(){
        try {
            upMoveMethod(1);
            String[] TabName = {"会员", "体育", "生活", "应用", "游戏", "设置"};
            for (int i = 0; i <= TabName.length - 1; i++) {
                if (TabName[i].equals("游戏")) {
                    rightMoveMethod(i + 1);
                    UiObject2 tabName = uiDevice.findObject(By.text("游戏"));
                    if (tabName.isSelected()) {
                        uiDevice.pressMenu();
                        systemWait(WAIT);
                        UiObject2 Menu = uiDevice.findObject(By.text("设置"));
                        Assert.assertEquals("设置", Menu.getText());
                        rightMoveMethod(2);
                        uiDevice.pressDPadCenter();
                        systemWait(WAIT);
                        UiObject2 classify = uiDevice.findObject(By.text("设 置"));
                        m_Actual = classify.getText();
                        m_Expect = "设 置";
                        m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                        Utils.writeCaseResult("跳转设置页面错误", m_Pass, m_Time);
                    } else {
                        Assert.assertTrue("首页Tab位置错误",false);
                    }
                    break;
                }
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

    @Test  //播放记录生成记录
    public void LC_MENU_11_DeleteVideoRecordInPlayRecord() {
        try {
            rightMoveMethod(2);
            systemWait(WAIT);
            UiObject title1 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/maintitle"));
            uiDevice.pressDPadCenter();
            sleep(15000);
            uiDevice.pressBack();
            UiObject2 PlayRecord = this.getTabFromLauncherHomeByText(uiDevice, "播放记录");
            this.openTabFromLauncherHomeByTextView(uiDevice, PlayRecord);
            UiObject title2 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/title"));
            if(title2.getText().equals("头条新闻")){
                Assert.assertTrue(true);
            }else {
                Assert.assertTrue(title1.getText(),false);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag =false;
        }
        finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test  //播放记录卡片menu-搜索
    public void LC_MENU_12_SearchInPlayRecordCard(){
        try {
            enterPlayRecordPage();
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 Menu = uiDevice.findObject(By.text("搜索"));
            if(Menu != null) {
                uiDevice.pressDPadLeft();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 searchPage = uiDevice.findObject(By.text("用手机搜片"));
                m_Actual = searchPage.getText();
                m_Expect = "用手机搜片";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转搜索页面错误", m_Pass, m_Time);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag =false;
        }
        finally {
            if(resultStr != null){
                Utils.writeCaseResult(resultStr,resultFlag,m_Time);
            }
        }
    }

    @Test  //播放记录卡片menu-播放
    public void LC_MENU_13_PlayingInPlayRecordCard(){
        try {
            enterPlayRecordPage();
            downMoveMethod(1);
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 Menu = uiDevice.findObject(By.text("播放"));
            if(Menu != null) {
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("视频播放失败", m_uiObj != null, m_Time);
            }else {
                Assert.assertTrue("BUG",false);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //播放记录卡片menu-删除单个(播放记录>=2)
    @Ignore
    public void LC_MENU_14_DelOnlyOneInPlayRecordCard(){
        try {
            enterPlayRecordPage();
            uiDevice.pressDPadDown();
            UiObject2 Recycle1 = uiDevice.findObject(By.res("com.bestv.ott:id/local_data"));
            List<UiObject2> cards1 = Recycle1.findObjects(By.clazz("android.widget.RelativeLayout"));
            int recordCount1 = cards1.size();//获取初次进入时页面卡片数量
            uiDevice.pressMenu();
            systemWait(WAIT);
            rightMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 recycle2 = uiDevice.findObject(By.res("com.bestv.ott:id/local_data"));
            List<UiObject2> cards2 = recycle2.findObjects(By.clazz("android.widget.RelativeLayout"));
            int recordCount2 = cards2.size();//获取删除单个后页面卡片数量
            if (recordCount2 != recordCount1) {
                Assert.assertTrue(true);
            } else {
                Assert.assertTrue(false);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //播放记录页tab menu-搜索
    public void LC_MENU_15_SearchInPlayRecordCardTab(){
        try {
            enterPlayRecordPage();
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 Menu = uiDevice.findObject(By.text("搜索"));
            if(Menu != null) {
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 searchPage = uiDevice.findObject(By.text("用手机搜片"));
                m_Actual = searchPage.getText();
                m_Expect = "用手机搜片";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转搜索页面错误", m_Pass, m_Time);
            }else {
                Assert.assertTrue("BUG",false);
            }
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //播放记录页Tab menu-清空全部
    public void LC_MENU_16_EmptyVideoRecordInPlayRecordTab() {
        try {
            enterPlayRecordPage();
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 MenuIcon = uiDevice.findObject(By.res("android:id/tv_fun_menu"));
            List<UiObject2> card = MenuIcon.findObjects(By.clazz("android.widget.LinearLayout"));
            UiObject delAll = uiDevice.findObject(new UiSelector().text("清空全部"));
            Log.d(TAG, "LC_MENU_16_EmptyVideoRecordInPlayRecordTab: "+card.size());
            if(card.size() == 3){
                rightMoveMethod(1);
                if(delAll.exists()){
                    uiDevice.pressDPadCenter();
                    sleep(1000);
                    uiDevice.pressDPadCenter();
                    systemWait(WAIT);
                    UiObject2 noDataPage = uiDevice.findObject(By.text("您还没有看过任何影片，去看几部片子再回来吧"));
                    m_Actual = noDataPage.getText();
                    m_Expect = "您还没有看过任何影片，去看几部片子再回来吧";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("无记录页面显示错误", m_Pass, m_Time);
                }else {
                    Utils.Print("播放记录页面无记录");
                }
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

    @Test  //播放记录页Tab menu-视频分类
    public void LC_MENU_17_EmptyRecordInPlayRecordTab() throws NullPointerException{
        try {
            enterPlayRecordPage();
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 MenuIcon = uiDevice.findObject(By.res("android:id/tv_fun_menu"));
            List<UiObject2> card = MenuIcon.findObjects(By.clazz("android.widget.LinearLayout"));
            UiObject delAll = uiDevice.findObject(new UiSelector().text("清空全部"));
            if(card.size() == 3) {    //menu选项隐藏一个，所有+1
                rightMoveMethod(1);
                if (!delAll.exists()) {
                    uiDevice.pressDPadCenter();
                    sleep(1000);
                    UiObject2 classify = uiDevice.findObject(By.text("视频分类"));
                    m_Actual = classify.getText();
                    m_Expect = "视频分类";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("跳转视频分类页面错误", m_Pass, m_Time);
                } else {
                    Assert.assertTrue("播放记录不为空,说明Case16是错误的", false);
                }
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

    @Test  //猜你喜欢内视频menu收藏
    @Ignore
    public void LC_MENU_18_WouldYouLikeCollectRecord() {
        try {
            enterPlayRecordPage();   //猜你喜欢进入时会重新刷新数据
            upMoveMethod(1);
            uiDevice.pressDPadLeft();
            systemWait(WAIT);
            downMoveMethod(1);
            uiDevice.pressMenu();
            systemWait(SHORT_WAIT);  //通过menu键收藏该影片
            uiDevice.pressDPadCenter();
            systemWait(SHORT_WAIT);
            downMoveMethod(1);
            uiDevice.pressMenu();
            UiObject2 textViewer1 = uiDevice.findObject(text("已收藏"));
            Assert.assertEquals("选择menu后应显示已收藏", "已收藏", textViewer1.getText());
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            uiDevice.pressEnter();
            uiDevice.waitForIdle(18000);
            systemWait(LONG_WAIT);
            UiObject2 textViewer2 = uiDevice.findObject(text("已收藏"));
            m_Expect = "已收藏";
            m_Actual = textViewer2.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("详情页收藏选项显示错误", m_Pass, m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag =false;
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //我的收藏卡片menu-已收藏
    public void LC_MENU_19_HadCollectInMyCollectPageCard(){
        try {
            enterMyCollectPage();
            UiObject2 Recycle1 = uiDevice.findObject(By.res("com.bestv.ott:id/inner_recyclerview"));
            List<UiObject2> Cards1 = Recycle1.findObjects(By.clazz("android.widget.RelativeLayout"));
            if(Cards1.size() >= 1) {
                downMoveMethod(1);
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 ColText = uiDevice.findObject(By.text("已收藏"));
                Assert.assertEquals("已收藏",ColText.getText());
                rightMoveMethod(1);
                uiDevice.pressDPadCenter();
            }else {
                Log.d("lxm", "LC_MENU_18_HadCollectInMyCollectPageCard: 我的收藏无记录");
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

    @Test  //我的收藏卡片menu-清空全部
    public void LC_MENU_20_EmptyAllCollectInMyCollectPageCard(){
        try {
            enterMyCollectPage();
            downMoveMethod(1);
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 menu = uiDevice.findObject(By.text("清空全部"));
            if(menu != null) {
                rightMoveMethod(2);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 col1 = uiDevice.findObject(By.text("亲，您确定要清空所有我的收藏吗？"));
                m_Actual = col1.getText();
                m_Expect = "亲，您确定要清空所有我的收藏吗？";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("弹框错误", m_Pass, m_Time);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 col2 = uiDevice.findObject(By.text("视频分类"));
                m_Actual = col2.getText();
                m_Expect = "视频分类";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("弹框错误", m_Pass, m_Time);
            }else {
                Assert.assertTrue(false);
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

    @Test  //赛事预约页面menu-取消预约
    public void LC_MENU_21_CancelAppointInMatchPageCard(){
        try{
            enterMyCollectPage();
            systemWait(WAIT);
            UiObject gameOrder = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/tab_title").text("赛事预约"));
            if(gameOrder.exists()){
                rightMoveMethod(1);
                downMoveMethod(1);
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Menu = uiDevice.findObject(By.text("取消预约"));
                if(Menu != null) {
                    UiObject2 cancelOrder = uiDevice.findObject(By.text("取消预约"));
                    m_Expect = "取消预约";
                    m_Actual = cancelOrder.getText();
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("menu显示错误", m_Pass, m_Time);
                }else {
                    Assert.assertTrue(false);
                }
            }else{
                Utils.writeCaseResult("未有预约赛事",true,m_Time);
            }
        }catch(Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //赛事预约Tab menu操作
    public void LC_MENU_22_LiveBookingPageMenuOperation(){
        try{
            enterMyCollectPage();
            systemWait(WAIT);
            UiObject zhiBoOrder = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/tab_title").text("赛事预约"));
            if(zhiBoOrder.exists()){
                rightMoveMethod(1);
                downMoveMethod(1);
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Menu = uiDevice.findObject(By.text("取消全部"));
                if(Menu != null) {
                    rightMoveMethod(2);
                    uiDevice.pressDPadCenter();
                    systemWait(WAIT);
                    UiObject2 col1 = uiDevice.findObject(By.text("亲，您确定要清空所有赛事预约吗？"));
                    m_Actual = col1.getText();
                    m_Expect = "亲，您确定要清空所有赛事预约吗？";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("弹框错误", m_Pass, m_Time);
                }else {
                    Assert.assertTrue(false);
                }
            }else{
                Utils.writeCaseResult("未预约赛事",true,m_Time);
            }
        }catch(Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //赛事预约页面清空全部比赛
    public void LC_MENU_23_EmptyMatchOrderInterface() {
        try {
            enterMyCollectPage();
            systemWait(WAIT);
            UiObject2 gameOrder= uiDevice.findObject(By.text("赛事预约"));
            if(gameOrder == null){
                Utils.writeCaseResult("未预约赛事",false,m_Time);
            }else {
                rightMoveMethod(1);
                systemWait(WAIT);
                uiDevice.pressMenu();
                systemWait(WAIT);
                rightMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 textView = uiDevice.findObject(text("赛事预约"));//赛事预约界面清空时会Tab消失&TextViewer为空值时不能使用get取值
                Assert.assertNull(textView);
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

    @Test  //列表页Tab Menu操作-搜索
    public void LC_MENU_31_ListTabMenuOperationTab1(){
        try {
            enterFilmListPage();
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 Menu = uiDevice.findObject(By.text("搜索"));
            if(Menu != null) {
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 searchPage = uiDevice.findObject(By.text("用手机搜片"));
                m_Actual = searchPage.getText();
                m_Expect = "用手机搜片";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转搜索页面错误", m_Pass, m_Time);
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

    @Test  //列表页Tab Menu操作-筛选
    public void LC_MENU_32_ListTabMenuOperationTab2(){
        try {
            enterFilmListPage();
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 Menu = uiDevice.findObject(By.text("搜索"));
            if(Menu != null) {
                rightMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 filterPage = uiDevice.findObject(By.text("地区").res("com.bestv.ott:id/filter_page_title"));
                Assert.assertEquals("地区", filterPage.getText());
            }else {
                Assert.assertTrue(false);
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

    @Test  //列表页卡片-搜索
    public void LC_MENU_33_ListTabMenuOperationCard1(){
        try {
            enterFilmListPage();
            rightMoveMethod(2);
            systemWait(WAIT);
            downMoveMethod(1);
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 Menu = uiDevice.findObject(By.text("搜索"));
            if (Menu != null) {
                leftMoveMethod(2);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 searchPage = uiDevice.findObject(By.text("用手机搜片"));
                m_Actual = searchPage.getText();
                m_Expect = "用手机搜片";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转搜索页面错误", m_Pass, m_Time);
            }else {
                Assert.assertTrue(false);
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

    @Test  //列表页卡片-筛选
    public void LC_MENU_34_ListTabMenuOperationCard2(){
        try {
            this.enterFilmListPage();
            downMoveMethod(3);
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 Menu = uiDevice.findObject(By.text("筛选"));
            if(Menu != null) {
                leftMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 filterPage = uiDevice.findObject(By.text("地区").res("com.bestv.ott:id/filter_page_title"));
                Assert.assertEquals("地区",filterPage.getText());
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

    @Test  //列表页卡片-收藏
    public void LC_MENU_35_ListTabMenuOperationCard3() throws UiObjectNotFoundException{
        try {
            enterFilmListPage();
            downMoveMethod(3);
            sleep(3000);
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject colText = uiDevice.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text")
                    .textContains("收藏").enabled(true));
            UiObject colIcon= uiDevice.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_icon").enabled(true));
            if(colText.exists()) {
                if (colText.getText().equals("收藏") && colIcon.isEnabled()) {
                    uiDevice.pressDPadCenter();
                    sleep(3000);
                    uiDevice.pressDPadCenter();
                    uiDevice.waitForIdle(18000);
                    systemWait(LONG_WAIT);
                    UiObject2 delCol = uiDevice.findObject(By.res("com.bestv.ott:id/discripse").text("已收藏"));
                    m_Actual = delCol.getText();
                    m_Expect = "已收藏";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("收藏成功", m_Pass, m_Time);
                }else {
                    uiDevice.pressBack();
                    systemWait(SHORT_WAIT);
                    uiDevice.pressDPadCenter();
                    uiDevice.waitForIdle(18000);
                    systemWait(LONG_WAIT);
                    UiObject2 delCol = uiDevice.findObject(By.res("com.bestv.ott:id/discripse").text("已收藏"));
                    m_Actual = delCol.getText();
                    m_Expect = "已收藏";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("收藏成功", m_Pass, m_Time);
                }
            }else {
                Assert.assertTrue(false);
            }
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

    @Test  //详情页menu-搜索
    @Ignore //3.2以后版本详情页不支持menu键
    public void LC_MENU_36_DetailsPageMenuOperation1(){
        try {
            rightMoveMethod(2);
            downMoveMethod(1);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(20000);
            systemWait(LONG_WAIT);
            UiObject2 Rel = uiDevice.findObject(By.text("相关推荐"));
            Assert.assertEquals("相关推荐",Rel.getText());
            uiDevice.pressMenu();
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 searchPage = uiDevice.findObject(By.text("用手机搜片"));
            m_Actual = searchPage.getText();
            m_Expect = "用手机搜片";
            m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转搜索页面错误",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //详情页menu-视频分类
    @Ignore //3.2以后版本详情页不支持menu键
    public void LC_MENU_37_DetailsPageMenuOperation2(){
        try {
            rightMoveMethod(2);
            downMoveMethod(1);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(20000);
            systemWait(LONG_WAIT);
            UiObject2 Rel = uiDevice.findObject(By.text("相关推荐"));
            Assert.assertEquals("相关推荐",Rel.getText());
            uiDevice.pressMenu();
            systemWait(WAIT);
            rightMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 classify = uiDevice.findObject(By.text("最新"));
            m_Actual = classify.getText();
            m_Expect = "最新";
            m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转视频分类页面错误",m_Pass,m_Time);
        }catch (Throwable e){
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        }
        finally {
            Utils.writeCaseResult(resultStr,resultFlag,m_Time);
        }
    }

    @Test  //轮播menu-查看详情
    public void LC_MENU_41_CarouselMenuOperation1(){
        try {
            enterTVTabPage();
            rightMoveMethod(1);
            systemWait(WAIT);
            UiObject2 pageTitle = uiDevice.findObject(By.res("com.bestv.ott:id/home_tv_feature_two_subtitle")
                    .clazz("android.widget.TextView"));
            String PT =pageTitle.getText().split("-")[0];  //截断字符
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            uiDevice.pressMenu();
            systemWait(SHORT_WAIT);
            upMoveMethod(1);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(20000);
            systemWait(LONG_WAIT);
            UiObject2 delPage = uiDevice.findObject(By.res("com.bestv.ott:id/detail_title")
                    .clazz("android.widget.TextView"));
            if(PT.equals(delPage.getText())){
                Assert.assertTrue(true);
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

    @Test  //轮播menu-清晰度
    public void LC_MENU_42_CarouselMenuOperation2(){
        try {
            enterTVTabPage();
            rightMoveMethod(1);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();   systemWait(PlayVideoTime);
            uiDevice.pressMenu();
            systemWait(SHORT_WAIT);
            leftMoveMethod(1);
            downMoveMethod(1);
            uiDevice.pressDPadCenter();   systemWait(PlayVideoTime);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("视频播放失败",m_uiObj !=null,m_Time);
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

    @Test  //轮播menu-画面比例
    public void LC_MENU_43_CarouselMenuOperation3(){
        try {
            enterTVTabPage();
            rightMoveMethod(1);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();   systemWait(PlayVideoTime);
            uiDevice.pressMenu();
            systemWait(SHORT_WAIT);
            downMoveMethod(1);
            leftMoveMethod(1);
            downMoveMethod(1);
            uiDevice.pressDPadCenter();   systemWait(PlayVideoTime);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("金卡电视剧视频播放失败",m_uiObj !=null,m_Time);
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

    @Test  //我的应用-清理数据
    public void LC_MENU_51_MyAppMenuOperation1InTopBar(){
        try {
            upMoveMethod(2);
            uiDevice.waitForIdle();
            UiObject2 myApp = uiDevice.findObject(By.res("com.bestv.ott:id/app"));
            this.openTabFromLauncherHomeByresId(uiDevice, myApp);
            systemWait(WAIT);
            UiObject2 appTitle = uiDevice.findObject(text("我的应用"));
            Assert.assertEquals("我的应用",appTitle.getText());
            UiObject2 Recycle = uiDevice.findObject(By.res("tv.fun.appstore:id/listview"));
            List<UiObject2> Cards = Recycle.findObjects(By.clazz("android.widget.RelativeLayout"));
            Log.d("lxm", "lxm: "+Cards.size());
            if(Cards.size()>=1){
                uiDevice.pressMenu();
                systemWait(WAIT);
                rightMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 uninstall = uiDevice.findObject(By.text("应用清理"));
                m_Actual = uninstall.getText();
                m_Expect = "应用清理";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转页面错误",m_Pass,m_Time);
            }else {
                Utils.writeCaseResult("无安装应用",false,m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test  //我的应用-卸载
    public void LC_MENU_52_MyAppMenuOperation2InTopBar(){
        try {
            upMoveMethod(2);
            uiDevice.waitForIdle();
            UiObject2 myApp = uiDevice.findObject(By.res("com.bestv.ott:id/app"));
            this.openTabFromLauncherHomeByresId(uiDevice, myApp);
            systemWait(WAIT);
            UiObject2 appTitle = uiDevice.findObject(text("我的应用"));
            Assert.assertEquals("我的应用",appTitle.getText());
            UiObject2 recycle = uiDevice.findObject(By.res("tv.fun.appstore:id/listview"));
            List<UiObject2> Cards = recycle.findObjects(By.clazz("android.widget.RelativeLayout"));
            if(Cards.size()>=1){
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 uninstall = uiDevice.findObject(By.text("卸载"));
                m_Actual = uninstall.getText();
                m_Expect = "卸载";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("menu选项错误",m_Pass,m_Time);
            }else {
                Utils.writeCaseResult("无安装应用",false,m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test  //消息中心-删除单条
    public void LC_MENU_53_MessageCenterMenuOperation1InTopBar() throws NullPointerException{
        try {
            upMoveMethod(2);
            uiDevice.waitForIdle();
            sleep(6000);
            UiObject2 Message = uiDevice.findObject(By.res("com.bestv.ott:id/message"));
            this.openTabFromLauncherHomeByresId1(uiDevice, Message);
            UiObject2 appTitle = uiDevice.findObject(text("消息中心"));
            Assert.assertEquals("消息中心",appTitle.getText());
            UiObject Title = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/title_tip"));
            systemWait(WAIT);
            if(!Title.exists()){
                UiObject2 pageText = uiDevice.findObject(By.text("暂未收到任何消息"));
                m_Actual = pageText.getText();
                m_Expect = "暂未收到任何消息";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("显示错误",m_Pass,m_Time);
            }else {
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Delete = uiDevice.findObject(By.text("删除单条"));
                m_Actual = Delete.getText();
                m_Expect = "删除单条";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("显示错误",m_Pass,m_Time);
                backPageMethod();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test  //消息中心-清空全部
    public void LC_MENU_54_MessageCenterMenuOperation2InTopBar(){
        try {
            upMoveMethod(2);
            uiDevice.waitForIdle();
            SystemClock.sleep(5000);
            UiObject2 Message = uiDevice.findObject(By.res("com.bestv.ott:id/message"));
            this.openTabFromLauncherHomeByresId1(uiDevice, Message);
            UiObject2 appTitle = uiDevice.findObject(text("消息中心"));
            Assert.assertEquals("消息中心",appTitle.getText());
            UiObject Title = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/title_tip"));
            systemWait(WAIT);
            if(!Title.exists()){
                UiObject2 pageText = uiDevice.findObject(By.text("暂未收到任何消息"));
                m_Actual = pageText.getText();
                m_Expect = "暂未收到任何消息";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("显示错误",m_Pass,m_Time);
            }else {
                uiDevice.pressMenu();
                systemWait(WAIT);
                UiObject2 Empty = uiDevice.findObject(By.text("清空全部"));
                m_Actual = Empty.getText();
                m_Expect = "清空全部";
                m_Pass =m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("显示错误",m_Pass,m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
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

    private void openTabFromLauncherHomeByresId(UiDevice device, UiObject2 resourceId) {
        resourceId.click();
        //resourceId.getParent().click();
        systemWait(SHORT_WAIT);
        device.pressDPadCenter();
        device.waitForIdle();
        systemWait(WAIT);
    }

    private void openTabFromLauncherHomeByresId1(UiDevice device, UiObject2 resourceId) {
        resourceId.click();
        //resourceId.getParent().click();
        systemWait(SHORT_WAIT);
        device.waitForIdle();
        systemWait(WAIT);
    }

    private void enterPlayRecordPage(){
        try{
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        uiDevice.waitForIdle();
        UiObject2 Record = uiDevice.findObject(By.text("播放记录"));
        Assert.assertEquals("播放记录",Record.getText());
        }catch (Throwable e){
            e.printStackTrace();
        }

    } //进入播放记录页面

    private void enterMyCollectPage(){
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.waitForIdle();
            rightMoveMethod(1);
            UiObject2 Collect = uiDevice.findObject(By.text("我的收藏"));
            if (Collect.isSelected()) {
                Assert.assertTrue(true);
            }else {
                try {
                    Assert.assertTrue(false);
                }finally {
                    Utils.writeCaseResult("my collect tab error",false,m_Time);
                }
            }
    }  //进入我的收藏页

    private void enterFilmListPage(){
        try{
        downMoveMethod(1);
        uiDevice.pressDPadCenter();
        systemWait(LONG_WAIT);
        uiDevice.waitForIdle();
        UiObject2 recommend = uiDevice.findObject(By.text("全部"));
        Assert.assertEquals("全部",recommend.getText());
    } catch (Throwable e){
            e.printStackTrace();
        }
    }//进入电影列表页

    private void enterTVTabPage(){
        upMoveMethod(1);
        leftMoveMethod(1);
        systemWait(WAIT);
        UiObject2 tabName = uiDevice.findObject(By.text("电视"));
        if(tabName.isSelected()) {
            downMoveMethod(1);
            systemWait(WAIT);
        }else {
            Assert.assertTrue(false);
        }
    } //进入电视Tab页面

    private void systemWait(int seconds) {

        SystemClock.sleep(seconds * 1000);
    } //等待时间

    private void backToLauncherHome(UiDevice device) {
        try {
            device.pressHome();
            device.waitForIdle();
            systemWait(WAIT);
        }catch (Throwable e){
         e.printStackTrace();
        }
    }

    private void rightMoveMethod(int RightMove){
        int i = 1;
        while (i <= RightMove){
            i++;
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    } //Right*

    private void leftMoveMethod(int LeftMove){
        int i = 1;
        while (i <= LeftMove){
            i++;
            uiDevice.pressDPadLeft();
            systemWait(SHORT_WAIT);
        }
    } //Left*

    private void downMoveMethod(int DownMove){
        int i = 1;
        while (i <= DownMove){
            i++;
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
        }
    } //Down*

    private void upMoveMethod(int UpMove){
        int i = 1;
        while (i <= UpMove){
            i++;
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
        }
    } //Up*

    private void backPageMethod(){
        uiDevice.pressBack();
        systemWait(SHORT_WAIT);
        uiDevice.pressBack();
    } //Back*

    private void sleep(int sleep){
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
