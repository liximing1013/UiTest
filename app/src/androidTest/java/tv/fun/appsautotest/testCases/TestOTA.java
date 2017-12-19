package tv.fun.appsautotest.testCases;

import android.support.test.uiautomator.UiObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

/**
 * Created by xuzx on 2017/6/19.
 */
public class TestOTA {
    private int m_iOneSecond = 1000;
    private TvCommon m_com = new TvCommon();

    private String m_sExpect = "";
    private String m_sActual = "";
    private String m_sResult = "";
    private String m_sObjId = "";
    private UiObject m_uiObj = null;

    private long m_lConsumeTime = -1;
    private boolean m_bPass = true;

    private TestEnterPage m_enterPage = new TestEnterPage();

    @Before
    public void setUp(){
        m_sResult = "自动化脚本运行失败！";
        m_lConsumeTime = Utils.getCurSecond();
    }
    @After
    public void clearUp() {
        m_com.Sleep(m_iOneSecond * 4);
//        m_com.Home(2);
    }

    @Test
    public void enterOTADetailPage(){
        try{
            m_enterPage.enterOTAPage();
            m_com.Sleep(5 * m_iOneSecond);

            m_sObjId = "tv.fun.tvupgrade:id/upgrade_detail_btn";
            String sLatestUiId = "tv.fun.tvupgrade:id/latest_ui";
            UiObject uiLatestUiObj = m_com.getUiObjByResId(sLatestUiId);

            m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId);
            if(m_uiObj.exists()) {
                m_sExpect = "更新详情";
                m_sActual = m_com.getUiObjText(m_com.getUiObjByResId(m_sObjId));
                m_sResult = "没有【更新详情】按钮";
                m_bPass = m_sActual.equalsIgnoreCase(m_sExpect);
            }
            else if(uiLatestUiObj.exists()){
                m_sExpect = "恭喜你,你的系统已经是最新版本";
                m_uiObj = m_com.getUiObjChild(uiLatestUiObj, m_com.BY_CLASS, Infos.S_TXTVIEW_CLASS, 0);
                m_sActual = m_uiObj.getText();
                m_sResult = String.format("当前的升级信息为：%s", m_sActual);
                m_bPass = m_sActual.equalsIgnoreCase(m_sExpect);
            }
            else{
                m_bPass = false;
            }
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }


    public void clickUpgradeBtn(){
        try {
            m_sObjId = "tv.fun.tvupgrade:id/do_upgrade_btn";
            m_uiObj = m_com.getUiObjByResId(m_sObjId);
            if (m_uiObj.exists()) {
                m_sExpect = "立即升级";
                m_sActual = m_com.getUiObjText(m_com.getUiObjByResId(m_sObjId));
                m_sResult = String.format("按钮上的文字不是【%s】", m_sExpect);
                m_bPass = m_sActual.equalsIgnoreCase(m_sExpect);
                m_com.Down(2);
                m_com.Enter();
            }
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }
}
