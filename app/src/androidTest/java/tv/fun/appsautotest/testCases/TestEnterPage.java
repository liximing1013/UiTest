package tv.fun.appsautotest.testCases;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.HttpUtils;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

/**
 * Created by xuzx on 2016/8/17.
 * Include the test cases for enter page.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestEnterPage {
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
    UiObject m_uiObj = null;

    @Before
    public void setUp(){
        m_lConsumeTime = Utils.getCurSecond();
    }

    @After
    public void clearUp() {
        m_com.Sleep(iOneSecond * 4);
        m_com.Home(2);
    }

    @Test
    public void LC_TB_01_enterHomePage(){
        try {
            m_com.Home(2);

            m_sExpect = "播放记录";
            m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));
            m_bPass = m_sActual.equalsIgnoreCase(m_sExpect);
            m_sResult = "进入[视频]主页失败"; //m_bPass为false时，才会被记录到log中
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += " " + e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    public void enterTvTabPage(){
        m_com.Navigation("hh993");
    }
    @Test
    // 焦点选择电视Tab页
    public void LC_TB_02_enterTvTabPage(){
        try {
            enterTvTabPage();

            m_sExpect = "24小时热播";
            m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));
            m_bPass = m_sActual.equalsIgnoreCase(m_sExpect);
            m_sResult = String.format("进入[电视]页失败，没有[%s]字样！", m_sExpect);
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    public void enterSportsTabPage(){
        m_com.Navigation("hh9901");
    }

    @Test
    // 焦点选择体育Tab页
    public void LC_TB_03_enterSportsTabPage(){
        try {
            enterSportsTabPage();

            String sTitle1 = "NBA";
            String sTitle2 = "英超";
            m_bPass = m_com.isUiObjExists(m_com.BY_TEXT, sTitle1, "") ||
                    m_com.isUiObjExists(m_com.BY_TEXT, sTitle2, "");
            m_sResult = String.format("进入[体育]页失败，没有[%s]和[%s]栏目！",
                    sTitle1, sTitle2);
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    public void enterFilmPage(){
        m_com.Navigation("hh99249999");
    }
    @Test
    public void LC_TB_04_enterFilmPage(){
        try {
            enterFilmPage();

            String sUrl = HttpUtils.m_sFilmMainPageUrl + "&version=" + m_sVersion;;
            String sResult = HttpUtils.sendGet(sUrl);
            JSONObject jsonObject = new JSONObject(sResult);

            String sRetCode = jsonObject.getString("retCode");
            if(!sRetCode.equalsIgnoreCase("200")){
                m_bPass = false;
                m_sResult = String.format("电影页面TabUrl连接【%s】访问失败！", sUrl);
            }else{
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                int iCheck = 7; // 检查前7个Tab
                for(int i = 0; i < iCheck; ++i){
                    JSONObject jsonObj = (JSONObject)jsonArray.get(i);
                    m_sExpect = jsonObj.getString("name");
                    Utils.Print(m_sExpect);
                    m_bPass = m_com.isUiObjExists(m_com.BY_TEXT, m_sExpect, "");
                    if(!m_bPass) {
                        m_sResult += String.format("进入【视频】页面的电影页失败!" +
                                "不存在【%s】Tab", m_sExpect);
                        break;
                    }
                }
            }
            if(m_bPass) {
                String sTitle1 = "新片前瞻";
                String sTitle2 = "今夜看啥";
                m_bPass = m_com.isUiObjExists(m_com.BY_TEXT, sTitle1, "") ||
                        m_com.isUiObjExists(m_com.BY_TEXT, sTitle2, "");
                m_sResult = String.format("进入\"电影\"页失败，没有[%s]和[%s]栏目！",
                        sTitle1, sTitle2);
            }
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    public void enterTVPlayPage(){
        m_com.Navigation("hh992149999");
    }
    @Test
    public void LC_TB_05_enterTVPlayPage(){
        try{
            enterTVPlayPage();

            String sUrl = HttpUtils.m_sPlayMainPageUrl + "&version=" + m_sVersion;;
            String sResult = HttpUtils.sendGet(sUrl);
            JSONObject jsonObject = new JSONObject(sResult);

            String sRetCode = jsonObject.getString("retCode");
            if(!sRetCode.equalsIgnoreCase("200")){
                m_bPass = false;
                m_sResult = String.format("电视剧页面TabUrl连接【%s】访问失败！", sUrl);
            }else{
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                int iCheck = 7; // 检查前7个Tab
                for(int i = 0; i < iCheck; ++i){
                    JSONObject jsonObj = (JSONObject)jsonArray.get(i);
                    m_sExpect = jsonObj.getString("name");
                    Utils.Print(m_sExpect);
                    m_bPass = m_com.isUiObjExists(m_com.BY_TEXT, m_sExpect, "");
                    if(!m_bPass) {
                        m_sResult += String.format("进入【视频】页面的电视剧页失败!" +
                                "不存在【%s】Tab", m_sExpect);
                        break;
                    }
                }
            }
            if(m_bPass) {
                String sTitle1 = "香港TVB";
                String sTitle2 = "全民抗战";
                m_bPass = m_com.isUiObjExists(m_com.BY_TEXT, sTitle1, "") ||
                        m_com.isUiObjExists(m_com.BY_TEXT, sTitle2, "");
                m_sResult = String.format("进入\"电视剧\"页失败，没有[%s]和[%s]栏目！",
                        sTitle1, sTitle2);
            }
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    public void enterHistoryPage(){
        m_com.Navigation("hh9914"); //new int[]{-1, -1, 1, 4});
    }
    @Test
    public void LC_TB_06_enterHistoryPage(){
        try {
            enterHistoryPage();

            String sTitle1 = "播放记录";
            String sTitle2 = "猜你喜欢";
            m_bPass = m_com.isUiObjExists(m_com.BY_TEXT, sTitle1, "") ||
                    m_com.isUiObjExists(m_com.BY_TEXT, sTitle2, "");

            m_sResult = String.format("进入\"播放记录\"页失败，没有[%s]和[%s]栏目！",
                    sTitle1, sTitle2);
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }

    }


    public void enterChildPage(){
        m_com.Navigation("hh992114");
    }
    @Test
    public void LC_TB_07_enterChildPage(){
        try{
            enterChildPage();
            m_sObjId = "tv.fun.children:id/tab_title_children"; // 少儿标签id
            m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId);
            String sId = "tv.fun.children:id/tab_title_brand"; // 品牌专区标签id
            UiObject uiObj = m_com.getUiObject(m_com.BY_RESID, sId);
            m_bPass = m_uiObj.exists() && uiObj.exists();
            m_sResult = "进入少儿页失败，缺少【少儿】或【品牌专区】标签！";
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    public void enterVIPPage(){
        m_com.Navigation("hh99124"); // 视频-金卡会员页小窗口自动播放
    }
    @Test
    public void LC_TB_08_enterVIPPage(){
        try {
            enterVIPPage();

            m_sExpect = Infos.S_LC_TEXT_VIP_TITLE;
            m_sObjId = Infos.S_LC_VIP_ID;
            m_sActual = m_com.getUiObjText(m_com.getUiObjByResId(m_sObjId));
            m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId);
            m_bPass = m_sActual.equalsIgnoreCase(m_sExpect) || m_uiObj.exists();

            m_sResult = String.format("进入金卡会员主页失败，没有[%s]文字和金卡会员图标！",
                    m_sExpect);
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    public void enterVideoClassificationPage(){
        m_com.Navigation("hh991224");
    }
    @Test
    public void LC_TB_09_enterVideoClassificationPage(){
        try {
            enterVideoClassificationPage();

            String sTitle1 = "最新";
            String sTitle2 = "排行榜";
            m_bPass = m_com.isUiObjExists(m_com.BY_TEXT, sTitle1, "") ||
                    m_com.isUiObjExists(m_com.BY_TEXT, sTitle2, "");

            m_sResult = String.format("进入\"视频分类\"页失败，没有[%s]和[%s]栏目！",
                    sTitle1, sTitle2);
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }

    }

    public void enterBestvLivePage(){
        m_com.Navigation("hh990122224");
    }
    @Test
    public void LC_TB_10_enterBestvLivePage(){ // 百视通直播大厅
        try {
            enterBestvLivePage();

            m_sExpect = "看看新闻";
            m_sObjId = Infos.S_LC_BEST_LIVE_HALL_ID;
            m_sActual = m_com.getUiObjText(m_com.getUiObjByResId(m_sObjId));
            m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId);
            m_bPass = m_sActual.equalsIgnoreCase(m_sExpect) || m_uiObj.exists();

            m_sResult = "进入百视通直播大厅失败";
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    public boolean enterCustomTabsPage(){
        m_com.Navigation("h0");
        m_com.longPressRight(2);
        m_com.Enter();
        m_sExpect = "设置桌面频道";
        m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));
        return m_sActual.equalsIgnoreCase(m_sExpect);
    }

    public void enterCustomTabsPage_Slow(){
        m_com.Home();
        m_com.Up();
        m_com.Left();
        String sId = "com.bestv.ott:id/home_root";
        String sTitileId = "com.bestv.ott:id/tab_title";

        m_uiObj = m_com.getUiObjByResId(sId);
        int iChild = 0;
        try {
            iChild = m_uiObj.getChildCount();
        } catch (UiObjectNotFoundException e) {
            iChild = 10;
        }
        UiObject uiChildObj;
        for(int i = 0; i < iChild; i++){
            try {
                uiChildObj = m_uiObj.getChild(new UiSelector().resourceId(sTitileId).instance(i));
                Utils.Print("child "+ i + " " + uiChildObj.getBounds() + " " + uiChildObj.getText());
            }catch (Throwable e){
                break;
            }
            m_com.Right();
        }
        m_com.Enter();
    }

    @Test
    public void LC_TB_11_enterCustomTabsPage(){
        try {
            m_bPass = enterCustomTabsPage();
            m_sResult = String.format("进入设置桌面频道页失败，没有[%s]字样！", m_sExpect);
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    public void enterSearchPage(){
        m_com.Navigation("hh99");
        m_com.Menu();
        m_com.Sleep(iOneSecond);
        m_com.Enter();
    }

    public void enterSettingPage(){
        m_com.Home(2);
        m_com.Menu();
        m_com.Right(2);
        m_com.Enter();
    }

    public void enterOTAPage(){
        enterSettingPage();
        m_com.Right(4);
        m_com.Down(2);
        m_com.Enter(); // enter about
        m_com.Up(10);
        m_com.Down(2);
        m_com.Enter();
    }

//    @Test
//    public void test(){
//        TvCommon.printAllMethods(this.getClass().getName());
//    }
}
