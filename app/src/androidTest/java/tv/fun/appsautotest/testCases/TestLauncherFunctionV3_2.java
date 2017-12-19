package tv.fun.appsautotest.testCases;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.Random;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

import static android.content.ContentValues.TAG;
import static android.support.test.uiautomator.By.text;
import static android.support.test.uiautomator.Until.findObject;

/**
 * Created by lxm on 2017/6/15
 * testCase:57
 **/

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLauncherFunctionV3_2 extends TestCase{
    private Instrumentation instrument;
    private UiDevice uiDevice;
    //设定等待时间
    private static final int SHORT_WAIT = 1;
    private static final int WAIT = 5;
    private static final int LONG_WAIT = 15;
    //设定播放视频时间
    private static final int PlayVideoTime = 66;
    //初始化
    private String m_ObjId = "";
    private long m_Time;
    UiObject2 m_uiObj = null;
    private String m_Expect = "";
    private String m_Actual = "";
    private String resultStr = "";
    private boolean resultFlag = true;
    private boolean m_Pass = false;
    //七巧板
    private String cmd = "am start -n com.bestv.ott/com.bestv.ott.piano.PianoActivity";

    @Before
    public void setUp() {
        instrument = InstrumentationRegistry.getInstrumentation();
        uiDevice = UiDevice.getInstance(instrument);
        m_Time = Utils.getCurSecond();   //耗时

        backToLauncherHome(uiDevice);
    }

    @After //运行脚本之后回到launcher
    public void clearUp() {
        BackPageMethod();
        SystemClock.sleep(6000);
    }

    @Test  //获取用例名
    public void test() {

        TvCommon.printAllMethods(this.getClass().getName());
    }

    @Test  //首页搜索选项
    public void LC_Tab_01_LauncherSearchButton() {
        try {
            UpMoveMethod(1);
            uiDevice.pressDPadLeft();
            systemWait(WAIT);
            UiObject2 tabName = uiDevice.findObject(By.text("电视"));
            if (tabName.isSelected()) {
                LeftMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 searchPage = uiDevice.findObject(By.text("用手机搜片"));
                m_Actual = searchPage.getText();
                m_Expect = "用手机搜片";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转搜索页面错误", m_Pass, m_Time);
            } else {
                Assert.assertTrue("Tab未被选中",false);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test  //中文搜索
    public void LC_Tab_02_ChineseSearch() {
        try {
            uiDevice.pressMenu();
            systemWait(WAIT);
            UiObject2 Menu = uiDevice.findObject(By.text("搜索"));
            if (Menu != null) {
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 searchPage = uiDevice.findObject(By.text("中文"));
                m_Actual = searchPage.getText();
                m_Expect = "中文";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("中文搜索显示正确", m_Pass, m_Time);
            } else {
                Assert.assertTrue("搜索不存在",false);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //搜索页面
    public void LC_Tab_03_SearchPage() {
        try {
            UpMoveMethod(1);
            uiDevice.pressDPadLeft();
            systemWait(WAIT);
            UiObject2 tabName = uiDevice.findObject(By.text("电视"));
            if (tabName.isSelected()) {
                LeftMoveMethod(1);
                SystemClock.sleep(2000);
                DownMoveMethod(1);
                systemWait(WAIT);
                UiObject2 searchPage = uiDevice.findObject(By.text("搜索片名、演员、导演"));
                m_Actual = searchPage.getText();
                m_Expect = "搜索片名、演员、导演";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("搜索页面显示错误", m_Pass, m_Time);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 searchPage1 = uiDevice.findObject(By.text("用手机搜片"));
                m_Actual = searchPage1.getText();
                m_Expect = "用手机搜片";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转搜索页面错误", m_Pass, m_Time);
            } else {
                Assert.assertTrue(false);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //首页“+”选项
    public void LC_Tab_04_LauncherAddButton() {
        try {
            UpMoveMethod(1);
            systemWait(WAIT);
            UiObject2 tabNum = uiDevice.findObject(By.res("com.bestv.ott:id/indicator")
                    .clazz("android.widget.HorizontalScrollView"));
            List<UiObject2> tabCount = tabNum.findObjects(By.clazz("android.widget.RelativeLayout"));
//            Log.d(TAG, "lxm "+tabCount.size());
            RightMoveMethod(tabCount.size() - 1);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 searchPage = uiDevice.findObject(By.text("设置桌面频道"));
            m_Actual = searchPage.getText();
            m_Expect = "设置桌面频道";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("跳转正确", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //信源界面
    public void LC_Source_01_SourcePageDisplay() throws NullPointerException{
        try {
            uiDevice.pressKeyCode(260);  //信源键KeyCode
            systemWait(WAIT);
            UiObject2 sourcePage = uiDevice.findObject(By.text("当前选择").res("tv.fun.settings:id/title_tips"));
            m_Actual = sourcePage.getText();
            m_Expect = "当前选择";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("信源页面显示正确", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //电视Tab看看新闻直播
    public void LC_TV_01_News24InTVTab() {
        try {
            this.EnterTVTabPage();
            DownMoveMethod(1);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("看看新闻播放失败", m_uiObj != null, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //看看新闻切换清晰度
    public void LC_TV_02_News24ChooseDefinitionInTVTab() {
        try {
            EnterTVTabPage();
            DownMoveMethod(1);
            SystemClock.sleep(20000);
            UiObject2 textView = uiDevice.findObject(By.text("全球新闻24小时直播").res("com.bestv.ott:id/home_live_subtitle"));
            if (textView == null) {
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                uiDevice.pressMenu();
                systemWait(SHORT_WAIT);
                LeftMoveMethod(1);
                UpMoveMethod(1); //切换1080P清晰度
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("看看新闻播放失败", m_uiObj != null, m_Time);
            } else {
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                uiDevice.pressMenu();
                systemWait(SHORT_WAIT);
                LeftMoveMethod(1);
                UpMoveMethod(1); //切换1080P清晰度
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("看看新闻播放失败", m_uiObj != null, m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //看看新闻切换画面比例
    public void LC_TV_03_News24ChooseScaleInTVTab() {
        try {
            EnterTVTabPage();
            DownMoveMethod(1);
            SystemClock.sleep(20000);
            UiObject2 textView = uiDevice.findObject(By.text("全球新闻24小时直播").res("com.bestv.ott:id/home_live_subtitle"));
            if (textView == null) {
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                uiDevice.pressMenu();
                systemWait(SHORT_WAIT);
                DownMoveMethod(1);
                LeftMoveMethod(1);
                DownMoveMethod(1); //切换画面比例
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("看看新闻播放失败", m_uiObj != null, m_Time);
            } else {
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                uiDevice.pressMenu();
                systemWait(SHORT_WAIT);
                DownMoveMethod(1);
                LeftMoveMethod(1);
                DownMoveMethod(1); //切换画面比例16:9
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("看看新闻播放失败", m_uiObj != null, m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //轮播列表显示
    public void LC_Carousel_01_CarouselListDisplay() {
        try {
            EnterTVTabPage();
            RightMoveMethod(1);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            uiDevice.pressDPadUp();
            SystemClock.sleep(2000);
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/carsousel_fragment").clazz("android.widget.LinearLayout"));
            m_ObjId = Infos.S_Carsousel_List;
            Utils.writeCaseResult("轮播列表显示错误", m_uiObj != null, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //轮播选择一级列表节目播放
    public void LC_Carousel_02_CarouselAListPlayVideo() {
        try {
            EnterTVTabPage();
            RightMoveMethod(1);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressDPadUp();
            randomPlayFilm(4);
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("轮播一级列表播放失败", m_uiObj != null, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //轮播选择二级列表节目播放
    public void LC_Carousel_03_CarouselBListPlayVideo() {
        try {
            EnterTVTabPage();
            RightMoveMethod(1);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressDPadUp();
            randomPlayFilm(9);
            uiDevice.pressDPadRight();
            randomPlayFilm(66);
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("轮播二级列表播放失败", m_uiObj != null, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //节目暂停后快进快退
    public void LC_Carousel_04_PlayingVideoAdjustSpeed() {
        try {
            EnterTVTabPage();
            RightMoveMethod(1);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            uiDevice.pressDPadCenter();
            UiObject2 InitialTime = uiDevice.findObject(By.res("com.bestv.ott:id/time_current"));//起始时间
            String[] items = InitialTime.getText().split(":");
            String min = items[0];
            if (min.startsWith("0")) {
                min = min.substring(1);
            }
            int InitialT = Integer.parseInt(min);
            Log.d(TAG, "lxm: " + InitialT);
            systemWait(WAIT);
            RightSpeedMove(6);
            systemWait(WAIT);
            UiObject2 EndTime = uiDevice.findObject(By.res("com.bestv.ott:id/time_current")); //结束时间
            String[] items1 = EndTime.getText().split(":");
            String min1 = items1[0];
            if (min1.startsWith("0")) {
                min1 = min1.substring(1);
            }
            int EndT = Integer.parseInt(min1);
            Log.d(TAG, "lxm: " + EndT);
            if (EndT > InitialT) {
                Assert.assertTrue(true);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //节目键+使用
    public void LC_Carousel_05_UseProgramKey() {
        try {
            EnterTVTabPage();
            RightMoveMethod(1);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressKeyCode(166);  //频道+
            systemWait(PlayVideoTime);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("轮播一级列表播放失败", m_uiObj != null, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //节目键-使用
    public void LC_Carousel_06_UseProgramKey1() {
        try {
            EnterTVTabPage();
            RightMoveMethod(1);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressKeyCode(167);  //频道-
            systemWait(PlayVideoTime);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("轮播一级列表播放失败", m_uiObj != null, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test  //桌面设置
    public void LC_Table_01_TableSettings() {
        try {
            EnterTableSettings();
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //儿童桌面设置显示
    public void LC_Table_02_ChildrenTableSettings() {
        try {
            this.EnterTableSettings();
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 ChildrenPage = uiDevice.findObject(By.text("护眼模式"));
            m_Actual = ChildrenPage.getText();
            m_Expect = "护眼模式";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("少儿桌面设置显示错误", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //标准设置显示
    public void LC_Table_03_StandardTableSettings() {
        try {
            EnterTableSettings();
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 ChildrenPage = uiDevice.findObject(By.text("设置桌面频道"));
            m_Actual = ChildrenPage.getText();
            m_Expect = "设置桌面频道";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("标准桌面设置显示错误", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //桌面名称更改
    public void LC_Table_04_ChangeTableName() throws UiObjectNotFoundException{
        try {
            this.EnterTableSettings();
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 childrenPage = uiDevice.findObject(By.text("设置桌面频道"));
            Assert.assertEquals("设置桌面频道", childrenPage.getText());
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject editText = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/standard_desktop_name_edit")
                    .className("android.widget.EditText"));
            editText.clearTextField();
            Configurator configurator = Configurator.getInstance();
            configurator.setKeyInjectionDelay(1000);
            editText.setText("LauncherTV");
            systemWait(WAIT);
            uiDevice.pressBack();
            systemWait(SHORT_WAIT);
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.pressKeyCode(260);
            systemWait(WAIT);
            UiObject sourcePage = uiDevice.findObject(new UiSelector().resourceId("tv.fun.settings:id/title"));
            String standName = sourcePage.getText();
            if (standName.equals("LauncherTV")) {
                Assert.assertTrue(true);
            } else {
                Utils.writeCaseResult("标准桌面更改错误", false, m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test  //桌面选择
    public void LC_Table_05_tableChoose() throws NullPointerException{
        try {
            this.EnterTableSettings();
            DownMoveMethod(2);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 ChildrenPage = uiDevice.findObject(By.text("儿童桌面"));
            m_Actual = ChildrenPage.getText();
            m_Expect = "儿童桌面";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("桌面选择显示错误", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //小视频收藏
    public void LC_SV_10_SmallVideoCollectRecord() {
        try {
            UpMoveMethod(1);
            uiDevice.pressDPadRight();
            systemWait(WAIT);
            UiObject2 TabName = uiDevice.findObject(By.text("体育"));
            if (TabName.isSelected()) {
                uiDevice.pressDPadDown();
                systemWait(WAIT);
                RightMoveMethod(3);
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                UiObject2 ColButton1 = uiDevice.findObject(By.res("com.bestv.ott:id/special_player_favorite"));
                if (ColButton1.getText().equals("已收藏")) {
                    Utils.writeCaseResult("小视频已被收藏", false, m_Time);
                } else {
                    UpMoveMethod(1);
                    uiDevice.pressDPadCenter();
                    systemWait(SHORT_WAIT);
                    uiDevice.pressBack();
                    systemWait(SHORT_WAIT);
                    uiDevice.pressDPadCenter();
                    systemWait(WAIT);
                    UiObject ColButton2 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/special_player_favorite")
                            .text("已收藏"));
                    m_Expect = "已收藏";
                    m_Actual = ColButton2.getText();
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("小视频收藏失败", m_Pass, m_Time);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test  //视频分类页直播大厅
    public void LC_BL_01_EnterBesTVLivePage() {
        try {
            this.EnterVideoClassifyPage();
            systemWait(WAIT);
//            UiObject2 Live = uiDevice.findObject(text("直播"));
//            this.openTabFromLauncherHomeByTextView(uiDevice, Live); //视频分类页面改成瀑布流
            DownMoveMethod(2);
            RightMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/live_hall_title"));
            m_ObjId = "com.bestv.ott:id/live_hall_title";
            Utils.writeCaseResult("进入直播大厅失败", m_uiObj != null, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //NBA首页全屏播放小窗口
    public void LC_NBA_01_EnterNBAHomePage() {
        try {
            this.EnterNBAHomePage();
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("NBA小窗口播放小视频专题失败", m_uiObj != null, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //NBA播放窗口切换相关视频
    public void LC_NBA_02_SwitchRelatedVideo() {
        try {
            this.EnterNBAHomePage();
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            DownMoveMethod(1);
            this.randomPlaySmallVideo(9);
            uiDevice.pressDPadCenter();
            systemWait(PlayVideoTime);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("NBA小窗口切换播放小视频专题失败", m_uiObj != null, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //NBA赛程表页面
    public void LC_NBA_03_EnterNBARaceCard() {
        try {
            this.EnterNBAHomePage();
            RightMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 AllRace = uiDevice.findObject(By.text("NBA全部赛程"));
            m_Actual = AllRace.getText();
            m_Expect = "NBA全部赛程";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入NBA全部赛程页面失败", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //球队赛程页面
    public void LC_NBA_04_EnterNBATeamGameCard() {
        try {
            this.EnterNBAHomePage();
            RightMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 AllRace = uiDevice.findObject(By.text("NBA全部赛程"));
            Assert.assertEquals("NBA全部赛程", AllRace.getText());
            UpMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 TeamRace = uiDevice.findObject(By.text("NBA球队赛程"));
            m_Actual = TeamRace.getText();
            m_Expect = "NBA球队赛程";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入NBA球队赛程页面失败", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //NBA一键预约页面
    public void LC_NBA_05_EnterNBAKeyAppointment() {
        try {
            this.EnterNBAHomePage();
            UpMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 AllTeam = uiDevice.findObject(By.text("全部球队"));
            m_Actual = AllTeam.getText();
            m_Expect = "全部球队";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入NBA一键预约页面失败", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //NBA一键预约后播放记录进入
    public void LC_NBA_06_EnterKeyAppointmentFromGameOrder() {
        try {
            this.EnterNBAHomePage();
            UpMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 AllTeam = uiDevice.findObject(By.text("全部球队"));
            Assert.assertEquals("全部球队", AllTeam.getText());
            UiObject2 Order = uiDevice.findObject(By.clazz("android.widget.TextView").res("com.bestv.ott:id/subscribe"));
            Log.d("LXM", "LC_NBA_06_EnterKeyAppointmentFromGameOrder: " + Order.getText());
            if (Order.getText().equals("一键预约")) {
                DownMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(SHORT_WAIT);
                backToLauncherHome(uiDevice);
                uiDevice.waitForIdle();
                EnterMyCollectPage();
                systemWait(SHORT_WAIT);
                RightMoveMethod(1);
                UiObject2 Text1 = uiDevice.findObject(By.text("76人").res("com.bestv.ott:id/title"));
                this.openTabFromLauncherHomeByTextView(uiDevice, Text1);
                UiObject2 TeamRace = uiDevice.findObject(By.text("NBA球队赛程"));
                m_Actual = TeamRace.getText();
                m_Expect = "NBA球队赛程";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("进入NBA球队赛程页面失败", m_Pass, m_Time);
            } else {
                backToLauncherHome(uiDevice);
                uiDevice.waitForIdle();
                EnterMyCollectPage();
                systemWait(SHORT_WAIT);
                RightMoveMethod(1);
                UiObject2 Text1 = uiDevice.findObject(By.text("76人").res("com.bestv.ott:id/title"));
                this.openTabFromLauncherHomeByTextView(uiDevice, Text1);
                UiObject2 TeamRace = uiDevice.findObject(By.text("NBA球队赛程"));
                m_Actual = TeamRace.getText();
                m_Expect = "NBA球队赛程";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("进入NBA球队赛程页面失败", m_Pass, m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test  //购买NBA包
    public void LC_NBA_07_PayNBAPage() {
        try {
            this.EnterNBAHomePage();
            UiObject2 NBAPay = uiDevice.findObject(By.res("com.bestv.ott:id/pay_nba_txt"));
            if (NBAPay.getText().equals("购买体育尊享包(NBA)")) {
                UpMoveMethod(1);
                RightMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 TeamRace = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                m_Actual = TeamRace.getText();
                m_Expect = "请选择要购买的媒体或服务";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("进入NBA购买页面失败", m_Pass, m_Time);
            } else {
                Utils.writeCaseResult(NBAPay.getText(), true, m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //NBA固定位专题类型
    public void LC_NBA_08_EnterNBAFixedLocation() {
        try {
            EnterNBAHomePage();
            RightMoveMethod(2);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressDPadCenter();
            UiObject ImageView = uiDevice.findObject(new UiSelector().className("android.widget.ImageView")
                    .resourceId("com.bestv.ott:id/special_player_list_item_status_icon"));
            if (ImageView.exists()) {
                System.out.println("小窗口视频播放暂停");
                LeftMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                m_uiObj = uiDevice.findObject(By.clazz(Infos.S_CLASS_VIDEO_PLAYER));
                Utils.writeCaseResult("视频正常播放", m_uiObj != null, m_Time);
            } else {
                Utils.writeCaseResult("False", false, m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //新闻大厅全屏播放新闻
    public void LC_NEWS_01_FullScreenPlayRandomNews() {
        try {
            RightMoveMethod(2);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            randomPlayFilm(11);  //随机选择一新闻播放
            RightMoveMethod(1);
            uiDevice.pressEnter();//选择二级标题播放
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight(); //焦点移至小窗口
            systemWait(WAIT);
            uiDevice.pressEnter();  //全屏播放
            systemWait(PlayVideoTime);
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            Utils.writeCaseResult("新闻大厅播放新闻失败", m_uiObj != null, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //看看新闻直播
    public void LC_NEWS_02_KanKanNewsNormalPlay() {
        try {
            this.EnterVideoClassifyPage();
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.pressEnter();
            systemWait(PlayVideoTime);
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            Utils.writeCaseResult("看看新闻无法播放", m_uiObj != null, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //排行榜任选影片播放
    public void LC_PHB_01_RankListPlayVideo() {
        try {
            this.EnterVideoClassifyPage();  //排行榜数据每天更新一次，每天运行脚本时，播放影片不同
            DownMoveMethod(3);
            RightMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            this.randomPlayFilm(6);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(18000);//等待18s,如果界面还没有打开则超时异常
            systemWait(LONG_WAIT);
            UiObject2 TextView = uiDevice.findObject(text("全屏"));
            m_Expect = "全屏";
            m_Actual = TextView.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("进入详情页错误", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //视频分类页排行榜
    public void LC_PHB_02_EnterRankListPage() {
        try {
            this.EnterVideoClassifyPage();
            DownMoveMethod(3);
            RightMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 TextViewer = uiDevice.findObject(By.text("TOP1"));
            m_Actual = "TOP1";
            m_Expect = TextViewer.getText();
            m_Pass = m_Expect.equalsIgnoreCase(m_Actual);
            Utils.writeCaseResult("排行榜页面显示错误", m_Pass, m_Time);
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject2 TextView = uiDevice.findObject(By.text("全屏"));
            Assert.assertEquals("成功进入详情页", "全屏", TextView.getText());
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //顶部快捷栏--天气
    public void LC_TB_01_EnterWeatherTopBars() {
        try {
            UpMoveMethod(2);
            systemWait(WAIT);
            UiObject2 Weather = uiDevice.findObject(By.res("com.bestv.ott:id/weather"));
            this.openTopBarFromLauncherHomeByresId(uiDevice, Weather);
            systemWait(WAIT);
            UiObject2 TextViewer1 = uiDevice.findObject(text("按 \uE693 菜单键管理城市"));
            m_Expect = "按 \uE693 菜单键管理城市";
            m_Actual = TextViewer1.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入顶部快捷栏--天气：失败", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //顶部快捷栏--金卡会员
    public void LC_TB_02_EnterVipTopBars() {
        try {
            UpMoveMethod(2);
            systemWait(WAIT);
            UiObject2 vip = uiDevice.findObject(By.res("com.bestv.ott:id/vip_icon"));
            this.openTabFromLauncherHomeByresId(uiDevice, vip);
            systemWait(WAIT);
            UiObject2 TextViewer2 = uiDevice.findObject(text("金卡会员30天"));
            m_Expect = "金卡会员30天";
            m_Actual = TextViewer2.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入顶部快捷栏--金卡会员：失败", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //顶部快捷栏--我的应用
    public void LC_TB_03_EnterAppTopBars() {
        try {
            UpMoveMethod(2);
            systemWait(WAIT);
            UiObject2 App = uiDevice.findObject(By.res("com.bestv.ott:id/app"));
            this.openTabFromLauncherHomeByresId(uiDevice, App);
            systemWait(WAIT);
            UiObject2 TextViewer2 = uiDevice.findObject(text("我的应用"));
            m_Expect = "我的应用";
            m_Actual = TextViewer2.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入顶部快捷栏--我的应用：失败", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //顶部快捷栏--智能桌面
    public void LC_TB_04_EnterTableTopBars() {
        try {
            UpMoveMethod(2);
            systemWait(WAIT);
            UiObject2 Table = uiDevice.findObject(By.res("com.bestv.ott:id/launcher"));
            this.openTabFromLauncherHomeByresId(uiDevice, Table);
            systemWait(WAIT);
            UiObject2 TextViewer3 = uiDevice.findObject(text("当前选择"));
            m_Expect = "当前选择";
            m_Actual = TextViewer3.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入顶部快捷栏--智能桌面：失败", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //顶部快捷栏--U盘
    public void LC_TB_05_EnterStorageTopBars() {
        try {
            UpMoveMethod(2);
            systemWait(WAIT);
            UiObject2 U = uiDevice.findObject(By.res("com.bestv.ott:id/storage"));
            if (U == null) {
                System.out.println("当前电视未插有U盘，请插入U盘在进行测试...");
            } else {
                this.openTabFromLauncherHomeByresId(uiDevice, U);
                systemWait(WAIT);
                DownMoveMethod(2);
                uiDevice.pressDPadCenter();    //进入音乐模块
                systemWait(WAIT);
                UiObject2 TextViewer = uiDevice.findObject(text("未发现可播放的音乐"));
                m_Actual = TextViewer.getText();
                m_Expect = "未发现可播放的音乐";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("进入U盘页面失败", m_Pass, m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //顶部快捷栏--消息通知
    public void LC_TB_06_EnterMessageTopBars() {
        try {
            UpMoveMethod(2);
            systemWait(WAIT);
            UiObject2 Message = uiDevice.findObject(By.res("com.bestv.ott:id/message"));
            this.openTabFromLauncherHomeByresId(uiDevice, Message);
            systemWait(WAIT);
            UiObject2 TextViewer3 = uiDevice.findObject(text("消息中心"));
            m_Expect = "消息中心";
            m_Actual = TextViewer3.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入顶部快捷栏--消息中心：失败", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //顶部快捷栏--设置
    public void LC_TB_07_EnterSettingsTopBars() {
        try {
            UpMoveMethod(2);
            systemWait(WAIT);
            UiObject2 Setting = uiDevice.findObject(By.res("com.bestv.ott:id/setting"));
            this.openTabFromLauncherHomeByresId(uiDevice, Setting);
            systemWait(WAIT);
            UiObject2 TextViewer3 = uiDevice.findObject(text("通用设置"));
            m_Expect = "通用设置";
            m_Actual = TextViewer3.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入顶部快捷栏--设置：失败", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //顶部快捷栏--小助手
    public void LC_TB_08_EnterHelperTopBars() {
        try {
            UpMoveMethod(2);
            systemWait(WAIT);
            UiObject2 Helper = uiDevice.findObject(By.res("com.bestv.ott:id/helper"));
            this.openTabFromLauncherHomeByresId(uiDevice, Helper);
            systemWait(WAIT);
            m_uiObj = uiDevice.findObject(By.clazz("android.view.View").res("tv.fun.master:id/one_key_opt"));
            m_ObjId = "tv.fun.master:id/one_key_opt";
            Utils.writeCaseResult("新闻大厅播放新闻失败", m_uiObj != null, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //进入英超主页
    public void LC_PL_01_EnterPremierLeaguePage() {
        try {
            EnterPLHomePage();
            UiObject2 TextViewer = uiDevice.findObject(By.text("赛程表"));
            m_Expect = "赛程表";
            m_Actual = TextViewer.getText();
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入英超主页成功", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //英超播放小视频专题
    public void LC_PL_02_PremierLeaguePlaySmallVideo() {
        try {
            this.EnterPLHomePage();
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            Utils.writeCaseResult("英超首页小视频播放失败", m_uiObj != null, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //购买英超包页面跳转
    public void LC_PL_03_PayPremierLeague() {
        try {
            this.EnterPLHomePage();
            uiDevice.pressDPadUp();
            systemWait(WAIT);
            UiObject2 plPay = uiDevice.findObject(By.res("com.bestv.ott:id/pay_la_txt").clazz("android.widget.TextView"));
            if (!plPay.isChecked()) {
                System.out.println("电视已开通英超付费包");
            } else {
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 plMonth = uiDevice.findObject(By.text("英超包月"));
                m_Expect = "英超包月";
                m_Actual = plMonth.getText();
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("付费包页面跳转成功", m_Pass, m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //英超首页全部轮次新增选项--查看历史赛季
    public void LC_PL_04_EnterPremierLeagueAllRound() {
        try {
            this.EnterPLHomePage();
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 TextView = uiDevice.findObject(By.text("查看历史赛季"));
            m_Actual = TextView.getText();
            m_Expect = "查看历史赛季";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("页面未发现查看历史赛季选项", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //英超--查看历史赛季切换对比
    public void LC_PL_05_PremierLeagueAllRoundContrast() {
        try {
            this.EnterPLHomePage();
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject season1 = uiDevice.findObject(new UiSelector().text("2016-2017赛季")
                    .resourceId("com.bestv.ott:id/fa_round_title"));
            UiObject2 history = uiDevice.findObject(By.text("查看历史赛季").res("com.bestv.ott:id/round_history"));
            if (history != null) {
                UpMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                DownMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                UiObject season2 = uiDevice.findObject(new UiSelector().text("2015-2016赛季")
                        .resourceId("com.bestv.ott:id/fa_round_title"));
                if (season1 == season2) {
                    Assert.assertTrue(false);
                    Utils.writeLogs("修改成功后历史赛季显示应不相同", "历史赛季更改成功");
                } else {
                    Utils.Print("历史赛季未更改成功");
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test  //英超节目列表页
    public void LC_PL_06_PremierLeagueProgram() {
        try {
            this.EnterPLHomePage();
            DownMoveMethod(1);
            RightMoveMethod(1);
            uiDevice.pressDPadCenter();
            UiObject TabTitle = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/tab_title").text("节目"));
            m_Actual = TabTitle.getText();
            m_Expect = "节目";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("进入英超节目页面失败", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //英超主页预约比赛
    public void LC_PL_07_PremierLeaguePageOrderMatch() {
        try {
            EnterPLHomePage();
            UiObject orderIcon = uiDevice.findObject(new UiSelector().textContains("\uE68F  预约"));
            if (orderIcon.exists()) {
                orderIcon.clickAndWaitForNewWindow(3000);
                orderIcon.click();
                SystemClock.sleep(3000);
                uiDevice.pressDPadCenter();
                SystemClock.sleep(3000);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 orderText = uiDevice.findObject(By.text("取消预约"));
                m_Actual = "取消预约";
                m_Expect = orderText.getText();
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("页面选项显示正确", m_Pass, m_Time);
            } else {
                System.out.println("无可预约英超比赛");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //视频分类一周更新视频
    public void LC_VC_01_EnterNewVideo() {
        try {
            this.EnterVideoClassifyPage();
            uiDevice.pressDPadCenter();
            SystemClock.sleep(6000);
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            uiDevice.pressEnter();
            systemWait(PlayVideoTime);
            m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
            m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
            Utils.writeCaseResult("视频播放失败or初次进入页面时重试", m_uiObj != null, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr = e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //免费大片--清晰度引导开通金卡会员
    public void LC_VIP_22_VipViaDefinition() {
        try {
            UiObject2 vipTip = uiDevice.findObject(By.res("com.bestv.ott:id/vip_tip"));
            if (vipTip.getText().equals("开通会员")) {
                RightMoveMethod(2);
                DownMoveMethod(1);
                uiDevice.pressDPadCenter();
                uiDevice.waitForIdle(15000);
                systemWait(LONG_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                uiDevice.pressMenu();
                systemWait(SHORT_WAIT);
                LeftMoveMethod(1);
                UpMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                UiObject2 TextView = uiDevice.findObject(By.text("开通金卡会员"));
                if (TextView != null) {
                    uiDevice.pressDPadCenter();
                    systemWait(WAIT);
                    UiObject2 TextView1 = uiDevice.findObject(By.text("金卡会员30天"));
                    m_Actual = TextView1.getText();
                    m_Expect = "金卡会员30天";
                    m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                    Utils.writeCaseResult("进入会员开通失败", m_Pass, m_Time);
                } else {
                    Assert.assertTrue(false);
                }
            } else if (vipTip.getText().equals("开通会员")) {
                Utils.writeCaseResult("vip用户", true, m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //列表页筛选页面
    public void LC_SX_01_FilterPageChoose() {
        try {
            EnterFilmFilterPage();
            DownMoveMethod(1);
            systemWait(WAIT);
            UiObject FilterT = uiDevice.findObject(new UiSelector().text("地区").resourceId("com.bestv.ott:id/filter_page_title"));
            Assert.assertEquals("地区", FilterT.getText());
            List<UiObject2> list = uiDevice.findObjects(By.res("com.bestv.ott:id/filter_page_title"));
            if (list.size() >= 5) {
                Utils.writeCaseResult("True", true, m_Time);
            } else {
                Assert.assertTrue(false);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //详情页--明星详情页跳转
    public void LC_DETAIL_01_DetailsPagePerformerJump() {
        try {
            RightMoveMethod(2);
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(15000);
            systemWait(LONG_WAIT);
            UiObject2 groomList = uiDevice.findObject(By.text("全屏"));
            if (groomList != null) {
                DownMoveMethod(5);
                uiDevice.wait(Until.findObject(By.text("明星").res("com.bestv.ott:id/title")), 18000);
                RightMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 TextView1 = uiDevice.findObject(By.text("收 藏").res("com.bestv.ott:id/actor_favorite"));
                m_Actual = TextView1.getText();
                m_Expect = "收 藏";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("收藏", m_Pass, m_Time);
            } else {
                Utils.writeCaseResult("进入媒体详情页超时", m_Pass, m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //详情页--付费选项跳转
    public void LC_DETAIL_02_DetailsPagePayPackPageJump() {
        try {
            RightMoveMethod(2);
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject VideoName = uiDevice.findObject(new UiSelector().className("android.widget.TextView")
                    .resourceId("com.bestv.ott:id/detail_title"));
            UiObject2 PayVip = uiDevice.findObject(By.text("付费"));
            if (PayVip != null) {
                uiDevice.pressDPadRight();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 PayTitle = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                Assert.assertEquals("请选择要购买的媒体或服务", PayTitle.getText());
                uiDevice.pressDPadDown();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject PayVip1 = uiDevice.findObject(new UiSelector().text("付费开通"));
                m_Actual = PayVip1.getText();
                m_Expect = "付费开通";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("跳转至付费包页面失败", m_Pass, m_Time);
            } else {
                System.out.println("您可以直接观看：" + VideoName.getText());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //详情页--片花选项
    public void LC_DETAIL_03_DetailsPageTrailers() {
        try {
            RightMoveMethod(2);
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            DownMoveMethod(4);
            systemWait(WAIT);
            UiObject2 Trail = uiDevice.findObject(By.text("片花"));
            if (Trail != null) {
                RightMoveMethod(3);
                UiObject2 TrailText = uiDevice.findObject(By.text("精彩片花"));
                Assert.assertEquals("精彩片花", TrailText.getText());
                systemWait(WAIT);
                uiDevice.pressDPadDown();
                m_uiObj = uiDevice.findObject(By.res("com.bestv.ott:id/detail_related_vedio_tag"));
                m_ObjId = "com.bestv.ott:id/detail_related_vedio_tag";
                Utils.writeCaseResult("片花显示错误", m_uiObj != null, m_Time);
            } else {
                System.out.println("本片无精彩片花选项");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //详情页--更多选项
    public void LC_DETAIL_04_DetailsPageMoreButton() {
        try {
            RightMoveMethod(2);
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject More = uiDevice.findObject(new UiSelector().text("更多>").resourceId("com.bestv.ott:id/detail_more_text"));
            if (More.exists()) {
                uiDevice.pressDPadUp();
                systemWait(SHORT_WAIT);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 Brief = uiDevice.findObject(By.text("简 介"));
                m_Actual = Brief.getText();
                m_Expect = "简 介";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("简介页面跳转失败", m_Pass, m_Time);
            } else {
                System.out.println("本片无更多选项");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }

    }

    @Test  //详情页--跟播剧收藏提醒
    public void LC_DETAIL_05_DetailsPageCorner() {
        try {
            RightMoveMethod(2);
            DownMoveMethod(1);
            systemWait(WAIT);
            UiObject2 UpdateVideo = uiDevice.findObject(By.text("跟播"));
            if (UpdateVideo != null) {
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                RightMoveMethod(1); //等待时间不能超过5s
                UiObject ColTip = uiDevice.findObject(new UiSelector().text("加入收藏，有更新会提醒哦")
                        .resourceId("com.bestv.ott:id/subscibe_tip_button"));
                m_Expect = ColTip.getText();
                m_Actual = "加入收藏，有更新会提醒哦";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("Tips提示5s后消失", m_Pass, m_Time);
            } else {
                System.out.println("固定位未配置跟播剧");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test  //付费Button进入退出小窗口继续播放
    public void LC_DETAIL_07_PayButtonBackWindowPlay() {
        try {
            UiObject2 VipArea = this.getTabFromLauncherHomeByText(uiDevice, "金卡会员");
            this.openTabFromLauncherHomeByTextView(uiDevice, VipArea);
            systemWait(WAIT);
            UiObject2 VipVideo = uiDevice.findObject(By.text("金卡-电视剧").res("com.bestv.ott:id/title"));
            this.openTabFromLauncherHomeByTextView(uiDevice, VipVideo);
            this.randomPlayFilm(3);
            systemWait(WAIT);
            uiDevice.pressDPadDown();
            SystemClock.sleep(1500);
            uiDevice.pressDPadCenter();
            uiDevice.waitForIdle(18000);//等待18s,如果界面还没有打开则超时异常
            systemWait(LONG_WAIT);
            UiObject payButton = uiDevice.findObject(new UiSelector().text("付费"));
            UiObject renewButton = uiDevice.findObject(new UiSelector().text("续费"));
            uiDevice.wait(Until.findObject(By.text("试看第1集").res("com.bestv.ott:id/watch_try_text")), 18000);
            if (payButton.exists() || renewButton.exists()) {
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                UiObject2 payMedia = uiDevice.findObject(By.text("请选择要购买的媒体或服务"));
                Assert.assertEquals("请选择要购买的媒体或服务", payMedia.getText());
                uiDevice.pressBack();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡电影视频播放失败", m_uiObj != null, m_Time);
            } else {
                Utils.writeCaseResult("vip用户", true, m_Time);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test  //媒体详情页全屏播放
    public void LC_DETAIL_08_FullScreenButton() {
        try {
            RightMoveMethod(2);
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject fullScreen = uiDevice.findObject(new UiSelector().text("全屏"));
            if (fullScreen.exists()) {
                uiDevice.pressDPadCenter();
                systemWait(PlayVideoTime);
                m_uiObj = uiDevice.findObject(By.clazz("com.funshion.player.play.funshionplayer.VideoViewPlayer"));
                m_ObjId = Infos.S_CLASS_VIDEO_PLAYER;
                Utils.writeCaseResult("金卡电影视频播放失败", m_uiObj != null, m_Time);
                BackPageMethod();
            } else {
                Assert.assertTrue(false);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test  //媒体详情页收藏与播放器收藏
    @Ignore
    public void LC_DETAIL_09_DetailPageCollect() {
        try {
            RightMoveMethod(2);
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(LONG_WAIT);
            UiObject2 collect = uiDevice.findObject(By.res("com.bestv.ott:id/detail_fav_button")); //先取RelativeLayout再取TextView
            UiObject2 collectButton = collect.findObject(By.res("com.bestv.ott:id/discripse"));
            Log.d(TAG, "lxm: " + collectButton.getText());
            if (collectButton.getText().equals("收藏")) {
                RightMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(WAIT);
                LeftMoveMethod(2);
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                uiDevice.pressMenu();
                SystemClock.sleep(2000);
                UiObject2 collectEd = uiDevice.findObject(By.text("已收藏"));
                m_Expect = collectEd.getText();
                m_Actual = "已收藏";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("播放器menu显示正常", m_Pass, m_Time);
                BackPageMethod();
            } else {
                LeftMoveMethod(1);
                uiDevice.pressDPadCenter();
                systemWait(LONG_WAIT);
                uiDevice.pressMenu();
                SystemClock.sleep(3000);
                UiObject2 collectEd = uiDevice.findObject(By.text("已收藏"));
                m_Expect = collectEd.getText();
                Log.d(TAG, "lxm1: " + collectEd.getText());
                m_Actual = "已收藏";
                m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
                Utils.writeCaseResult("播放器menu显示正常", m_Pass, m_Time);
                BackPageMethod();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr += e.toString();
        } finally {
            Utils.writeCaseResult(resultStr, resultFlag, m_Time);
        }
    }

    @Test  //呼出七巧板展示页面
    @Ignore
    /**
     * 需要root权限：在AndroidManifest.xml中添加android:sharedUserId="android.uid.system"
     **/
    public void LC_Puzzle_01_ExhalePuzzle() {
        try {
            Utils.execCommand(cmd, false, false);
            systemWait(WAIT);
            UiObject2 add = uiDevice.findObject(By.text("添加"));
            m_Actual = add.getText();
            m_Expect = "添加";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("显示错误", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //七巧板添加页面
    @Ignore
    /**
     * 需要root权限：在AndroidManifest.xml中添加android:sharedUserId="android.uid.system"
     **/
    public void LC_Puzzle_02_AddPuzzlePage() {
        try {
            Utils.execCommand(cmd, false, false);
            systemWait(WAIT);
            LeftMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 add = uiDevice.findObject(By.text("选择快捷入口"));
            m_Actual = add.getText();
            m_Expect = "选择快捷入口";
            m_Pass = m_Actual.equalsIgnoreCase(m_Expect);
            Utils.writeCaseResult("显示错误", m_Pass, m_Time);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //新闻模板页播放窗口增加收藏
    public void LC_NEWS_03_DeleteVideoRecordInPlayRecord() throws NullPointerException {
        try {
            RightMoveMethod(2);
            systemWait(WAIT);
            UiObject title1 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/subtitle"));
            uiDevice.pressDPadCenter();
            sleep(10000);
            RightMoveMethod(2);
            DownMoveMethod(1);
            UiObject2 collect = uiDevice.findObject(By.text("收藏").focused(true));
            if (collect.getText().equals("收藏")) {
                uiDevice.pressDPadCenter();
                systemWait(SHORT_WAIT);
                uiDevice.pressBack();
                UiObject2 PlayRecord = this.getTabFromLauncherHomeByText(uiDevice, "播放记录");
                this.openTabFromLauncherHomeByTextView(uiDevice, PlayRecord);
                RightMoveMethod(1);
                DownMoveMethod(1);
                UiObject title2 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/title"));
                if (title2.getText().equals(title1.getText())) {
                    Assert.assertTrue(true);
                } else {
                    Assert.assertTrue("false",false);
                }
            } else if (collect.getText().equals("已收藏")) {
                uiDevice.pressBack();
                UiObject2 PlayRecord = this.getTabFromLauncherHomeByText(uiDevice, "播放记录");
                this.openTabFromLauncherHomeByTextView(uiDevice, PlayRecord);
                RightMoveMethod(1);
                DownMoveMethod(1);
                UiObject title2 = uiDevice.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").focused(true));
                if (title2.getText().equals(title1.getText())) {
                    Assert.assertTrue(true);
                } else {
                    Assert.assertTrue("false",false);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
    }

    @Test  //返回键回到顶部
    public void LC_Back_01_BackToTop() throws NullPointerException{
        try {
            enterPlayRecordPage();
            DownMoveMethod(9);
            UiObject2 backKey = uiDevice.findObject(By.res("com.bestv.ott:id/back_to_top").clazz("android.widget.ImageView"));
            if (backKey != null) {
                systemWait(WAIT);
                uiDevice.pressBack();
                sleep(3000);
                UiObject2 recordTab = uiDevice.findObject(By.text("播放记录").res("com.bestv.ott:id/tab_title"));
                if (recordTab.isSelected()) {
                    Assert.assertTrue(true);
                } else {
                    Assert.assertTrue("未返回到顶部",false);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultStr += e.toString();
            resultFlag = false;
        } finally {
            if (resultStr != null) {
                Utils.writeCaseResult(resultStr, resultFlag, m_Time);
            }
        }
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

    private void openTopBarFromLauncherHomeByresId(UiDevice device, UiObject2 resourceId) {
        resourceId.click();
        systemWait(WAIT);
        device.pressDPadCenter();
        device.waitForIdle();
        systemWait(WAIT);

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

    private void RightSpeedMove(int SwipeSpeed) throws UiObjectNotFoundException {
        uiDevice.pressDPadRight();
        UiObject Swipe = uiDevice.findObject(new UiSelector().className("android.widget.SeekBar")
                .resourceId("com.bestv.ott:id/media_progress"));
        Swipe.swipeRight(SwipeSpeed);
    } //快速滑动右移

    private void EnterFilmFilterPage() {
        DownMoveMethod(1);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        uiDevice.waitForIdle();
        LeftMoveMethod(1);
        systemWait(WAIT);
        uiDevice.pressDPadLeft();
        systemWait(SHORT_WAIT);
    } //进入电影筛选页面

    private void EnterNBAHomePage() {
//        RightMoveMethod(1);
//        DownMoveMethod(2);
//        RightMoveMethod(1);
//        uiDevice.pressDPadCenter();
//        systemWait(WAIT);
//        UiObject2 Classify = uiDevice.findObject(By.text("赛程表 >"));
//        Assert.assertEquals("赛程表 >",Classify.getText());
        UpMoveMethod(1);
        RightMoveMethod(2);
        systemWait(WAIT);
        UiObject2 TabName = uiDevice.findObject(By.text("体育"));
        if (TabName.isSelected()) {
            DownMoveMethod(1);
            RightMoveMethod(1);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.wait(findObject(By.text("赛程表 >")), 15000);
            systemWait(LONG_WAIT);
        } else {
            Assert.assertTrue("Tab位置错误",false);
        }
    } //进入NBA首页

    private void EnterPLHomePage() {
        UpMoveMethod(1);
        RightMoveMethod(2);
        systemWait(WAIT);
        UiObject2 TabName = uiDevice.findObject(By.text("体育"));
        if (TabName.isSelected()) {
            DownMoveMethod(2);
            RightMoveMethod(1);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            uiDevice.wait(findObject(By.text("赛程表")), 15000);
            systemWait(LONG_WAIT);
        } else {
            try {
                Assert.assertTrue(false);
            } finally {
                Utils.writeCaseResult("Launcher首页Tab位置不符合", false, m_Time);
            }
        }
    } //进入英超首页

    private void EnterTVTabPage() {
        UpMoveMethod(1);
        LeftMoveMethod(1);
        systemWait(WAIT);
        UiObject2 TabName = uiDevice.findObject(By.text("电视"));
        if (TabName.isSelected()) {
            DownMoveMethod(1);
            systemWait(WAIT);
        } else {
            Assert.assertTrue(false);
        }
    } //进入电视Tab页面

    private void EnterMyCollectPage() {
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        uiDevice.waitForIdle();
        RightMoveMethod(1);
        UiObject2 Collect = uiDevice.findObject(By.text("我的收藏"));
        if (Collect.isSelected()) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }
    }  //进入我的收藏页

    private void EnterTableSettings() {
        try {
            uiDevice.pressMenu();
            systemWait(WAIT);
            RightMoveMethod(3);
            systemWait(WAIT);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
            UiObject2 Text2 = uiDevice.findObject(text("桌面设置"));
            Assert.assertEquals("桌面设置", Text2.getText());
            RightMoveMethod(2);
            DownMoveMethod(1);
            uiDevice.pressDPadCenter();
            systemWait(WAIT);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }  //进入设置页面

    private void EnterVideoClassifyPage() {
        try {
            uiDevice.pressDPadRight();
            SystemClock.sleep(1000);
            DownMoveMethod(2);
            uiDevice.pressEnter();
            systemWait(WAIT);
            UiObject2 Classify = uiDevice.findObject(By.text("视频分类"));
            Assert.assertEquals("视频分类", Classify.getText());
            systemWait(WAIT);
        }catch (Throwable e){
            e.printStackTrace();
        }
    } //进入视频分类

    private void enterPlayRecordPage() {
        uiDevice.pressDPadRight();
        systemWait(SHORT_WAIT);
        uiDevice.pressDPadCenter();
        systemWait(WAIT);
        uiDevice.waitForIdle();
        UiObject2 Record = uiDevice.findObject(By.text("播放记录"));
        Assert.assertEquals("播放记录", Record.getText());
    } //进入播放记录页面

    private void systemWait(int seconds) {

        SystemClock.sleep(seconds * 1000);
    } //等待时间

    private void backToLauncherHome(UiDevice device) {
        device.pressHome();
        device.waitForIdle();
        systemWait(WAIT);
    }

    private void randomPlayFilm(int RandomMove) {
        Random moveTimes = new Random();
        int i;
        i = moveTimes.nextInt(RandomMove);
        for (int j = 0; j <= i; j++) {
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
            if (i > 8) {
                break;
            }
        }
    } //随机播放轮播

    private void randomPlaySmallVideo(int RandomMove) {
        Random moveTimes = new Random();
        int i;
        i = moveTimes.nextInt(RandomMove);
        for (int j = 0; j <= i; j++) {
            systemWait(SHORT_WAIT);
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
            if (i > 18) {
                break;
            }
        }
    } //随机播放相关视频

    private void RightMoveMethod(int RightMove) {
        int i = 1;
        while (i <= RightMove) {
            i++;
            uiDevice.pressDPadRight();
            systemWait(SHORT_WAIT);
        }
    } //Right*

    private void LeftMoveMethod(int LeftMove) {
        int i = 1;
        while (i <= LeftMove) {
            i++;
            uiDevice.pressDPadLeft();
            systemWait(SHORT_WAIT);
        }
    } //Left*

    private void DownMoveMethod(int DownMove) {
        int i = 1;
        while (i <= DownMove) {
            i++;
            uiDevice.pressDPadDown();
            systemWait(SHORT_WAIT);
        }
    } //Down*

    private void UpMoveMethod(int UpMove) {
        int i = 1;
        while (i <= UpMove) {
            i++;
            uiDevice.pressDPadUp();
            systemWait(SHORT_WAIT);
        }
    } //Up*

    private void BackPageMethod() {
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

