package tv.fun.appsautotest.testCases;

import android.support.test.uiautomator.UiObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

/**
 * Created by lixm on 2017/2/12.
 */

public class TestAutoDataReport {
    private int m_iOneSecond = 1000;
    private int m_iWaitSec = 8 * m_iOneSecond;
    private TvCommon m_com = new TvCommon();

    private String m_sExpect = "";
    private String m_sActual = "";
    private String m_sResult = "";
    private String m_sObjId = "";
    private UiObject m_uiObj = null;

    private long m_lConsumeTime = -1;
    private boolean m_bPass = true;
    private TestCustomTabs customTabs = new TestCustomTabs();

    @Before
    public void setUp(){
        m_sResult = "自动化脚本运行失败！";
        m_lConsumeTime = Utils.getCurSecond();
    }
    @After
    public void clearUp() {
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Home(2);
    }

    @Test
    public void LC_Data_001_playVIPMedia(){
        try{
            m_sResult = "播放金卡小窗口视频失败！没有金卡会员字样或者标识图片";
            m_com.Navigation("hh99124"); // 视频-金卡会员页小窗口自动播放
            m_com.Sleep(m_iOneSecond * 5); // 等待5秒

            m_sExpect = Infos.S_LC_TEXT_VIP_TITLE;
            m_sObjId = Infos.S_LC_VIP_ID;
            m_sActual = m_com.getUiObjText(m_com.getUiObjByResId(m_sObjId));
            m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId);
            m_bPass = m_sActual.equalsIgnoreCase(m_sExpect) || m_uiObj.exists();

            m_com.EnterW(2); // 点击确定键，进入全屏播放
            m_com.EnterW(5); //点击确定键，进入节目详情页
            m_com.EnterW(8); // 点击确定键，进入节目播放缓冲页，缓冲结束后开始播放
            m_com.Sleep(m_iOneSecond * 10); // 播放10秒
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void LC_Data_002_playCarousel(){
//        Utils.Print("play case1 播放轮播视频 START");
        try {
            m_com.Navigation("hh99333199"); // 进入电视页，焦点框定位在24小时热播上
            // test point 1
            m_sExpect = "24小时热播"; // 如果没有"轮播"就表示不在电视页
            m_sResult = String.format("进入电视页失败！页面中没有“%s”字样", m_sExpect);
            m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));
            m_bPass = m_sActual.equalsIgnoreCase(m_sExpect);

        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }

        // test point 2
        try {
            m_sResult = "轮播视频打开失败！轮播视频里没有《24小时热播》";
            m_com.Enter(); // 打开轮播视频
            m_com.Sleep(m_iWaitSec);
            m_com.Up(); // 调出轮播菜单

            m_sObjId = Infos.S_LC_CAROUSEL_CHANNLE; // 轮播菜单里的控件
            m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId, 0); // 第一个节目: 24小时热播
            m_bPass = m_uiObj.exists();
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
//        Utils.Print("play case1 播放轮播视频 END");
    }

    @Test
    public void LC_Data_003_searchA(){
        try {
            m_com.Home(2);
            m_com.Menu();
            m_com.EnterW(1);

            m_sObjId = "com.bestv.voiceAssist:id/bubble_hint"; // 第一次进入搜索页会有Tips提示
            if (m_com.isUiObjExists(m_sObjId)) {
                m_com.Enter();
                Utils.Log("special_log", "刷机后第一次进入搜索页才会出现这个Log！");
            }

            m_com.EnterW(3);
            m_com.Back();
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void LC_Data_004_customDesktop(){
        try {
            customTabs.cancelAllTabes();
            m_com.Back();
            m_com.EnterW(4);
            m_com.Navigation("414141423414");
            m_com.Back();
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

}
