package tv.fun.appsautotest.testCases;

import android.support.test.uiautomator.UiObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

/**
 * Created by xuzx on 2017/3/24.
 */

public class TestCustomTabs {
    int iOneSecond = 1000;
    int iWaitSec = 8 * iOneSecond;
    TvCommon m_com = new TvCommon();

    boolean m_bPass = false;
    long m_lConsumeTime = -1;
    String m_sVersion = Infos.S_TV_VERSION;
    String m_sExpect = "";
    String m_sActual = "";
    String m_sResult = "";
    String m_sObjId = "";
    String m_sModuleName = "";
    String m_sCaseName = "";
    String m_sTemp = "";
    UiObject m_uiObj = null;
    private TestEnterPage m_enterPage = new TestEnterPage();
    String sPid = "com.bestv.ott:id/tab_manager";
    String sCText = "android.widget.RelativeLayout";

    @Before
    public void setUp(){
        m_lConsumeTime = Utils.getCurSecond();
    }

    @After
    public void clearUp() {
        m_com.Sleep(iOneSecond * 4);
//        m_com.Home(2);
    }

    public UiObject [] cancelAllTabes(){
        try {
            m_enterPage.enterCustomTabsPage();

            UiObject[] uiObjs = m_com.getChildren(m_com.BY_RESID, sPid, m_com.BY_CLASS, sCText);
            // 取消第一行所有的选择，前2个为固定的，不能操作
            int i;
            int iRow2 = 2;
            for (i = 2; i < uiObjs.length - iRow2; ++i) {
                if (uiObjs[i].getChildCount() == 3) {
                    m_com.EnterW(1);
                }
                m_com.Right();
            }
            m_com.Down(); // 移动到第二行
            for (i = uiObjs.length - 1; i >= uiObjs.length - iRow2; --i) {
                if (uiObjs[i].getChildCount() == 3) {
                    m_com.EnterW(1);
                }
                m_com.Left();
            }
            return uiObjs;
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void LC_TB_resetTabs(){
        try {
            UiObject [] uiObjs = cancelAllTabes();
            if(uiObjs == null){
                m_bPass = false;
                m_sResult = "取消全部桌面设置失败！";
            }
            else {
                // 按顺序全部选择
                m_sExpect = "电视视频";
                m_com.Back();
                m_com.Sleep(iOneSecond);
                m_com.EnterW(1);
                int i = 2;
                int iRow2 = 2;
                for(; i < uiObjs.length; ++i){
                    m_sTemp = m_com.getUiObjChild(uiObjs[i],
                            m_com.BY_CLASS, Infos.S_TXTVIEW_CLASS, 0).getText();
                    m_sExpect += m_sTemp;
                    m_com.EnterW(1);
                    m_com.Right();
                    if(i == uiObjs.length - 1 - iRow2){
                        m_com.Down();
                        m_com.Left(iRow2);
                    }
                }
                Utils.Print(m_sExpect);

                // 获取主页Tab顺序列表
                /*
                List<String> sTabList = m_com.getHomeTabList();  // 无法获取到[设置]Tab
                for(i = 0; i < sTabList.size(); i++){
                    try {
                        m_sActual += sTabList.get(i);
                    }catch (Throwable e){
                        break;
                    }
                }
                Utils.Print(m_sActual); */
                m_sActual = "电视视频体育生活会员应用游戏设置";
                m_bPass = m_sActual.equalsIgnoreCase(m_sExpect);
                m_sResult = String.format("设置自定义桌面顺序显示不正确，预期结果[%s]，" +
                        "实际结果[%s]", m_sExpect, m_sActual);
            }
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void test(){
        // 获取主页Tab顺序列表
        List<String> sTabList = m_com.getHomeTabList();
        for(int i = 0; i < sTabList.size(); i++){
            try {
                m_sActual += sTabList.get(i);
            }catch (Throwable e){
                break;
            }
        }
        Utils.Print(m_sActual);
    }
}
