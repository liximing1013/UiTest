package tv.fun.appsautotest.testCases;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.HttpUtils;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

/**
 * Created by xuzx on 2016/11/8.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSearch {
    private int m_iOneSecond = 1000;
    private int m_iWaitSec = 8 * m_iOneSecond;
    private TvCommon m_com = new TvCommon();

    private String m_sExpect = "";
    private String m_sActual = "";
    private String m_sResult = "";
    private String m_sObjId = "";
    private UiObject m_uiObj = null;

    private TestEnterPage m_enterPage = new TestEnterPage();

    private long m_lConsumeTime = -1;
    private boolean m_bPass = true;

    private int m_iBtns = 30; // 左侧区域的按钮数量
    private int m_iNotLetter = 4; // 非字母的按钮数量
    // #表示123按键，*表示清空按键，<表示删除按键
    private String m_sLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#*<>";
    private String m_sClearId = "com.bestv.voiceAssist:id/clear_icon"; // 清空按钮
    private String m_sDeletId = "com.bestv.voiceAssist:id/delete_icon"; // 删除按钮
    private String m_sSchList = "com.bestv.voiceAssist:id/hot_search_list"; // 右侧热门搜索列表
    private String m_sHotSchCell = "com.bestv.voiceAssist:id/hot_search_cell"; // 热门搜索
    private String m_sSchPoster = "com.bestv.voiceAssist:id/poster_bg"; // 搜索结果
    private String m_sRecycleView = "com.bestv.voiceAssist:id/recycleview";
    private UiObject m_clearObj;
    private UiObject m_deletObj;

    @Before
    public void setUp(){
        m_sResult = "自动化脚本运行失败！";
        m_lConsumeTime = Utils.getCurSecond();
    }
//    @After
    public void clearUp() {
        m_com.Sleep(m_iOneSecond * 4);
        m_com.Home(2);
    }

    public boolean isInSearchPage(){
        m_sExpect = "大家都在搜："; // 如果没有"大家都在搜"的文字就表示不在搜索页
        m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));

        m_sObjId = Infos.S_VOICE_SCH_LETTER;
        m_uiObj = m_com.getUiObject(m_com.BY_RESID, m_sObjId);
        return m_sActual.equalsIgnoreCase(m_sExpect) || m_uiObj.exists();
    }
    @Test
    public void Voice_SCH_01_enterSearchPage(){
        try {
            m_sResult = "从主页搜索按钮进入搜索页失败！没有出现字母区域或“大家都在搜”字样";
            m_com.Navigation("hh990334"); // 进入搜索页
            m_bPass = isInSearchPage();
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    // iNums 菜单的按钮数量
    public boolean isMenuExists(int iNums){
        m_sExpect = "搜索";
        m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));
        m_bPass = m_sActual.equalsIgnoreCase(m_sExpect);

        if(iNums > 1) {
            m_sExpect = "视频分类";
            m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));
            return m_bPass && m_sActual.equalsIgnoreCase(m_sExpect);
        }
        return m_bPass;
    }

    // sPath 进入要点击menu按键的页面的路径
    // iNums 菜单的按钮数量
    public void openMenuTest(String sPath, int iNums){
        if(iNums > 1){
            m_sResult = "打开Menu菜单失败！页面中没有“视频分类”按钮";
        }
        else if(iNums == 1){
            m_sResult = "打开Menu菜单失败！页面中没有“搜索”按钮";
        }
        try {
            m_com.Navigation(sPath); // 进入sPath的指定页
            m_com.Menu();
            m_bPass = isMenuExists(iNums);
        } catch (Throwable e) {
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }
    }

    @Test
    public void Voice_SCH_02_enterSearchByMenu(){
        openMenuTest("hh99", 2); // 进入主页，按下menu键
        Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);

        try{
            m_sResult = "主页Menu菜单进入搜索页失败！没有出现字母区域或“大家都在搜”字样";
            m_com.Enter(); // 进入搜索页
            m_bPass = isInSearchPage();
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }
    @Test
    public void Voice_SCH_03_enterSearchByMenuFromSports(){
        openMenuTest("hh9901", 2); // 进入体育页
        Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);

        try{
            m_sResult = "体育页Menu菜单进入搜索页失败！没有出现字母区域或“大家都在搜”字样";
            m_com.Enter(); // 进入搜索页
            m_bPass = isInSearchPage();
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }
    @Test
    public void Voice_SCH_04_enterSearchByMenuFromChild(){
        openMenuTest("hh99011", 2); // 进入少儿页
        Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);

        try{
            m_sResult = "少儿页Menu菜单进入搜索页失败！没有出现字母区域或“大家都在搜”字样";
            m_com.Enter(); // 进入搜索页
            m_bPass = isInSearchPage();
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }
    @Test
    public void Voice_SCH_05_enterSearchByMenuFromApp(){
        openMenuTest("hh990111", 1); // 进入应用页
        Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);

        try{
            m_sResult = "应用页Menu菜单进入搜索页失败！没有出现字母区域或“大家都在搜”字样";
            m_com.Enter(); // 进入搜索页
            m_bPass = isInSearchPage();
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }
    @Test
    public void Voice_SCH_06_enterSearchByMenuFromTvTab(){
        openMenuTest("hh9903", 1); // 进入电视页
        Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);

        try{
            m_sResult = "电视页Menu菜单进入搜索页失败！没有出现字母区域或“大家都在搜”字样";
            m_com.Enter(); // 进入搜索页
            m_bPass = isInSearchPage();
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }
    @Test
    public void Voice_SCH_07_cannotOpenMenuAtSetting(){
        try {
            m_sResult = "此时不应该打开Menu菜单！出现了“搜索”按钮";
            m_enterPage.enterSettingPage();
            m_com.Menu();
            m_bPass = !isMenuExists(0);
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }
        Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
    }

    private String enterSearchPage(){
        String sResult = "从主页搜索按钮进入搜索页失败！没有出现字母区域或“大家都在搜”字样";
        try{
            m_enterPage.enterSearchPage();
            if(isInSearchPage()){
                m_sObjId = "com.bestv.voiceAssist:id/bubble_hint"; // 第一次进入搜索页
                if(m_com.isUiObjExists(m_sObjId)){
                    m_com.Enter();
                    Utils.Log("special_log", "刷机后第一次进入搜索页才会出现这个Log！");
                }
                return "OK";
            }
        }catch (Throwable e){
            return sResult + e.toString();
        }
        return sResult;
    }
    @Test
    public void Voice_SCH_08_focusOnLetterA(){
        try {
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if(!m_sResult.equalsIgnoreCase("OK")){
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            m_sResult = "进入搜索页后焦点不在字母A上！Focus状态为false";
            m_uiObj = m_com.getUiObjByText("A");
            m_bPass = m_uiObj.isFocused();
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }
    @Test
    public void Voice_SCH_10_checkLeftTopLayout() {
        // case 10
        try {
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            m_sResult = "case10运行失败！搜索页左侧上方没有输入框！";
            m_sExpect = "输入影片首字母或全拼";
            m_uiObj = m_com.getUiObjByText(m_sExpect);
            m_bPass = m_uiObj.exists();
        } catch (Throwable e) {
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        } finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

     @Test
     public void Voice_SCH_11_checkLeftLetterLayout() {
         // case 11
         UiObject uiObj;
         try {
             // 进入搜索页的功能函数
             m_sResult = enterSearchPage();
             if (!m_sResult.equalsIgnoreCase("OK")) {
                 Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
             }

             m_sResult = "case11运行失败！";
             m_uiObj = m_com.getUiObjByResId(Infos.S_VOICE_SCH_LETTER);
             int iChilds = m_uiObj.getChildCount();
             if (m_iBtns != iChilds) {
                 m_sResult += "搜索页左侧中部按键数量不对！";
                 m_bPass = false;
             } else {
                 m_sResult += "左侧按键阵列出现错误！";
                 for (int i = 0; i < m_iBtns - m_iNotLetter; ++i) {
                     uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS,
                             Infos.S_TXTVIEW_CLASS, i);
                     m_sActual = m_com.checkExpectResult(uiObj,
                             String.valueOf(m_sLetters.charAt(i)));
                     if (!m_sActual.equalsIgnoreCase("OK")) {
                         m_sResult += m_sActual;
                         m_bPass = false;
                         break;
                     }
                 }

                 uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS,
                         Infos.S_TXTVIEW_CLASS, m_iBtns - m_iNotLetter); // 123按钮
                 m_sActual = m_com.checkExpectResult(uiObj, "123");
                 if (!m_sActual.equalsIgnoreCase("OK")) {
                     m_sResult += m_sActual;
                     m_bPass = false;
                 }

                 m_clearObj = m_com.getUiObjByResId(m_sClearId);
                 m_deletObj = m_com.getUiObjByResId(m_sDeletId);
                 if (!m_clearObj.exists()) {
                     m_sResult += " 没有清空按钮！";
                     m_bPass = false;
                 }
                 if (!m_deletObj.exists()) {
                     m_sResult += " 没有删除按钮！";
                     m_bPass = false;
                 }

                 uiObj = m_com.getUiObjByText("中文"); // 中文按钮
                 Utils.Print(uiObj.getBounds());
                 if (!uiObj.exists()) {
                     m_sResult += " 没有“中文”按钮！";
                     m_bPass = false;
                 }
             }
         } catch (Throwable e) {
             e.printStackTrace();
             m_bPass = false;
             m_sResult += e.toString();
         } finally {
             Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
         }
     }
    @Test
    public void Voice_SCH_12_checkLeftBottomLayout() {
        UiObject uiObj;
        // case 12
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            m_sResult = "case12运行失败！";
            m_sResult += "搜索页左侧下方没有【用手机搜片】的按钮！";
            m_sExpect = "用手机搜片";
            m_sActual = m_com.getUiObjText(m_com.getUiObjByText(m_sExpect));
            m_bPass = m_sActual.equalsIgnoreCase(m_sExpect);

            m_sObjId = "com.bestv.voiceAssist:id/voice_search_hint";
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }

        try{
            m_sResult = "case12运行失败！";
            m_sObjId = "com.bestv.voiceAssist:id/voice_search_hint";
            m_uiObj = m_com.getUiObjByResId(m_sObjId);
            if(m_uiObj.getChildCount() != 3){
                m_sResult += "使用语音搜索的提示控件数量不是3个！";
                m_bPass = false;
            }
            else{
                m_sResult += "使用语音搜索的提示文字不正确：";
                uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS,
                        Infos.S_TXTVIEW_CLASS, 0);
                m_sExpect = "按住遥控器";
                m_sActual = m_com.checkExpectResult(uiObj, m_sExpect);
                if(!m_sActual.equalsIgnoreCase("OK")){
                    m_sResult += m_sActual;
                    m_bPass = false;
                }

                m_sResult += "使用语音搜索的提示文字不正确：";
                uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS,
                        Infos.S_TXTVIEW_CLASS, 1);
                m_sExpect = "启动语音搜索";
                m_sActual = m_com.checkExpectResult(uiObj, m_sExpect);
                if(!m_sActual.equalsIgnoreCase("OK")){
                    m_sResult += m_sActual;
                    m_bPass = false;
                }
            }
        }catch (Throwable e) {
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void Voice_SCH_13_selectOneHotSearchVideo() {
        try {
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            m_sResult = "case13运行失败！";
            m_com.Sleep(m_iOneSecond * 4);
            m_com.Right(5);
            m_uiObj = m_com.getUiObjByResId(m_sHotSchCell);
            if(!m_uiObj.exists()){
                m_bPass = false;
                m_sResult += "热门搜索数据为空！";
            }

            String sUrl = "http://es.fun.tv/js/media/hot"; // 热门搜索链接
            String sHotSearch = HttpUtils.sendGet(sUrl);
            JSONObject jsonObject = new JSONObject(sHotSearch);

            int iMax = jsonObject.getInt("num");
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            String [] aHotNames = new String[iMax];
            for(int i = 0; i < jsonArray.length(); ++i){
                JSONObject jsonObj = (JSONObject)jsonArray.get(i);
                aHotNames[i] = jsonObj.getString("name");
//                Utils.Print(i + " " + aHotNames[i]);
            }

            int iNum = Utils.randInt(iMax);
            m_sExpect = aHotNames[iNum];
            Utils.Print(iNum + " " +m_sExpect);
            for(int i = 0; i < iNum; ++i){
                m_com.Down();
                m_com.Sleep(500);
            }

            m_com.Enter(); // 进入节目详情页
            m_sActual = m_com.getDetailTitle();
            if(!m_sActual.equalsIgnoreCase(m_sExpect)){
                m_sResult += m_sActual;
                m_bPass = false;
            }
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void Voice_SCH_14_inputLetter(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            int iIndex = Utils.randInt(m_sLetters.length() - m_iNotLetter); // 随机输入一个字母
            m_uiObj = m_com.getUiObjByText("A");
            if(m_uiObj.isFocused()){ // 焦点在A字母上
                m_sResult = "输入的字母错误！";
                m_sExpect = enterLetter(iIndex);
                // 检查输入框中是否就是输入的这个字母
                m_uiObj = m_com.getUiObjByResId(Infos.S_VOICE_SCH_SEARCH_EDIT);
                m_sActual = m_com.checkExpectResult(m_uiObj, m_sExpect);
                if(!m_sActual.equalsIgnoreCase("OK")){
                    m_sResult += m_sActual;
                    m_bPass = false;
                }

                // 热门搜索列表应该消失
                m_uiObj = m_com.getUiObjByResId(m_sHotSchCell);
                if(m_uiObj.exists()){
                    m_bPass = false;
                    m_sResult = "输入字母后热门搜索列表没有消失！";
                }
            }else{
                m_bPass = false;
                m_sResult = "进入搜索页时焦点不在字母A上！";
            }
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    // 输入一串字母sLetters
    private void enterLetters(String sLetters){
        int iNumsPerLine = 5;
        int iHorizontal;
        int iVertical;
        int iX0, iX1, iY0, iY1;
        String sCurLetter = String.valueOf(sLetters.charAt(0));
        enterLetter(sCurLetter, false);
        for(int i = 1; i < sLetters.length(); ++i) {
            sCurLetter = String.valueOf(sLetters.charAt(i - 1));
            iY0 = m_sLetters.indexOf(sCurLetter) / iNumsPerLine;
            iX0 = m_sLetters.indexOf(sCurLetter) % iNumsPerLine;
            sCurLetter = String.valueOf(sLetters.charAt(i));
            iY1 = m_sLetters.indexOf(sCurLetter) / iNumsPerLine;
            iX1 = m_sLetters.indexOf(sCurLetter) % iNumsPerLine;
            iHorizontal = iX1 - iX0;
            iVertical = iY1 - iY0;
            m_com.MoveX(iHorizontal);
            m_com.MoveY(iVertical);
            m_com.Enter();
        }
    }

    // 输入一串字母sLetters，如果sRet不等于sLetters，则返回false
    private boolean enterLetter(String sLetters, boolean bBack){
        String sRet = "";
        for(int i = 0; i < sLetters.length(); ++i) {
            String sLetter = String.valueOf(sLetters.charAt(i)).toUpperCase();
            int iIndex = m_sLetters.indexOf(sLetter);
            sRet += enterLetter(iIndex, bBack);
        }
        return sRet.equalsIgnoreCase(sLetters);
    }

    private String enterLetter(int iIndex, boolean bBack){
        try {
            String sLetter = String.valueOf(m_sLetters.charAt(iIndex));
            int iNumsPerLine = 5;
            int iDown, iRight;
            iDown = iIndex / iNumsPerLine;
            iRight = iIndex % iNumsPerLine;
            m_com.Down(iDown);
            m_com.Right(iRight);
            m_uiObj = m_com.getUiObjByText(sLetter);
            if (!m_uiObj.isFocused()) {
                m_bPass = false;
                m_sResult = "字母按键没有焦点框！";
            } else {
                m_com.Enter();
                if(bBack) {
                    // 返回字母A
                    m_com.Up(iDown);
                    m_com.Left(iRight);
                }
                return sLetter;
            }
        }catch(Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult = e.toString();
        }
        return "";
    }
    private String enterLetter(int iIndex){
        return enterLetter(iIndex, true);
    }

    @Test
    public void Voice_SCH_15_focusAtLeftBottom(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            enterLetters("C");
            m_com.Down(10);
            m_uiObj = m_com.getUiObjByResId(Infos.S_VOICE_SCH_PHONE_BTN);
            m_bPass = m_uiObj.isFocused();
            if(!m_bPass){
                m_sResult = "左侧焦点没有一直处于手机搜片按钮上！";
            }

        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void Voice_SCH_16_enter123Button(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            m_com.Navigation("2222214"); // 【123】按钮
            m_uiObj = m_com.getUiObjByResId(Infos.S_VOICE_SCH_NUM_LIST);
            if(!m_uiObj.exists()){
                m_bPass = false;
                m_sResult = "点击【123】按钮，没有出现数字输入界面！";
            }

        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void Voice_SCH_17_18_clearAndDelete(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            m_uiObj = m_com.getUiObjByResId(Infos.S_VOICE_SCH_SEARCH_EDIT);
            m_com.Navigation("22222114"); // clear btn
            m_com.Navigation("14"); // delete btn
            if(!m_uiObj.getText().equalsIgnoreCase("输入影片首字母或全拼")){
                m_bPass = false;
                m_sResult = "输入框的内容发生了变化！";
            }

        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void Voice_SCH_19_clearInput(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            enterLetters("DH*"); // * 表示clear btn清空输入框
            m_uiObj = m_com.getUiObjByResId(Infos.S_VOICE_SCH_SEARCH_EDIT);
            if(!m_uiObj.getText().equalsIgnoreCase("输入影片首字母或全拼")){
                m_bPass = false;
                m_sResult = "输入框的内容没有被清空！";
            }

            m_com.Sleep(m_iOneSecond * 2);
            // 热门搜索列表应该出现
            m_uiObj = m_com.getUiObjByResId(m_sHotSchCell);
            if(!m_uiObj.exists()){
                m_bPass = false;
                m_sResult = "清空输入框之后，没有出现热门搜索列表！";
            }
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void Voice_SCH_20_deleteInput(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            String sSearchTxt = "XLJ";
            enterLetters(sSearchTxt);
            // 如果没有结果就记录一下
            if(m_com.getUiObjByResId("com.bestv.voiceAssist:id/nodata_hint").exists()){
                m_bPass = false;
                m_sResult = String.format("没有搜索到【%s】相关的内容！", sSearchTxt);
                Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
                return;
            }

            int iNumOfXLJ;
            int iNumOfXL;
            m_uiObj = m_com.getUiObjByResId(Infos.S_VOICE_SCH_SEARCH_EDIT);
            String sId = "com.bestv.voiceAssist:id/search_txt_hint";
            UiObject uiObj = m_com.getUiObjByResId(sId);
            String sSearchNum = m_com.getUiObjText(uiObj).split("：")[1];
            iNumOfXLJ = Integer.parseInt(sSearchNum);

            m_com.Navigation("322224"); // delete btn
            m_sExpect = sSearchTxt.substring(0, sSearchTxt.length() - 1);
            if(!m_uiObj.getText().equalsIgnoreCase(m_sExpect)){
                m_bPass = false;
                m_sResult = "点击删除按钮删除字母后与预期结果不符！";
            }else{ // 检查XL的结果数量和XLJ的结果数量
                uiObj = m_com.getUiObjByResId(sId);
                sSearchNum = m_com.getUiObjText(uiObj).split("：")[1];
                iNumOfXL = Integer.parseInt(sSearchNum);
                if(iNumOfXL == iNumOfXLJ){
                    m_bPass = false;
                    m_sResult = String.format("搜索【%s】和【%s】的结果数量相同！",
                            sSearchTxt, m_sExpect);
                }
            }

            m_com.Enter(sSearchTxt.length());
            m_com.Sleep(m_iOneSecond * 2);
            // 热门搜索列表应该出现
            m_uiObj = m_com.getUiObjByResId(m_sHotSchCell);
            if(!m_uiObj.exists()){
                m_bPass = false;
                m_sResult = "输入框内容全部删除之后，没有出现热门搜索列表！";
            }
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void Voice_SCH_21_phoneAppBtn(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }
            // 点击【用手机搜片】按钮
            m_com.Down(6);

            if(!m_com.getUiObjByResId("com.bestv.voiceAssist:id/phone_btn").isFocused()){
                m_bPass = false;
                m_sResult = "焦点没有移动到【用手机搜片】按钮上！";
            }

            m_com.Sleep(m_iOneSecond * 2);
            m_com.Enter();
            if(!m_com.getUiObjByResId("com.bestv.voiceAssist:id/qr").exists()){
                m_bPass = false;
                m_sResult = "点击【用手机搜片】按钮后没有弹出下载电视助手的二维码！";
            }else{
                String sTxt1 = "扫描安装风行电视助手";
                String sTxt2 = "搜片更方便，一键电视播放";
                m_bPass = m_com.isUiObjExists(m_com.BY_TEXT, sTxt1, "") &&
                        m_com.isUiObjExists(m_com.BY_TEXT, sTxt2, "");
                m_sResult = String.format("下载电视助手的提示文字不符合预期结果【%s】【%s】！",
                        sTxt1, sTxt2);
            }
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void Voice_SCH_22_focusMoveToVoiceDialog(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }
            // 焦点不能移动到语音浮层下方的按钮区域
            m_com.Enter(); // 输入字母A
            m_com.Up();
            m_uiObj = m_com.getUiObjByResId("com.bestv.voiceAssist:id/voice_btn");
            if(!m_uiObj.isFocused()){
                m_bPass = false;
                m_sResult = "焦点没有移动到语音按钮上！";
            }else{
                m_com.Enter(); // 调出语音输入界面，无法获取语音区域的Dialog对象
                String sIcon = "com.bestv.voiceAssist:id/unspread";
                String sHint = "com.bestv.voiceAssist:id/short_press_hint";
                String sRecList = "com.bestv.voiceAssist:id/short_press_list";
                UiObject uiObj1 = m_com.getUiObjByResId(sIcon);
                UiObject uiObj2 = m_com.getUiObjByResId(sHint);
                m_uiObj = m_com.getUiObjByResId(sRecList);
                m_bPass = m_uiObj.exists() && uiObj1.exists() && uiObj2.exists();
                m_sResult = "没有出现语音图标、操作提示以及相关推荐列表！";

                m_uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS, "android.widget.LinearLayout", 0);
                List<String> sNameList = new ArrayList<>();

                int iChild = m_uiObj.getChildCount();
                for(int i = 0; i < iChild; ++i) {
                    UiObject uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS, Infos.S_TXTVIEW_CLASS, i);
                    sNameList.add(i, uiObj.getText());
                }

                int iNum = Utils.randInt(iChild);
                m_com.Down(iNum);
                m_com.Enter(); // 进入节目详情页
                m_sExpect = sNameList.get(iNum);
                m_sActual = m_com.getDetailTitle();
                if(!m_sActual.equalsIgnoreCase(m_sExpect)){
                    m_sResult += m_sActual;
                    m_bPass = false;
                }
            }
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    // TODO 自动化脚本会导致程序代码的字符串判断有误，需要修改后再运行该脚本
    public void Voice_SCH_24_numberInputLayout(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            m_com.Navigation("2222214"); // 【123】按钮
            m_uiObj = m_com.getUiObjByResId(Infos.S_VOICE_SCH_NUM_LIST);
            if(!m_uiObj.exists()){
                m_bPass = false;
                m_sResult = "点击【123】按钮，没有出现数字输入界面！";
            }
            else{
                UiObject uiObj;
                String sNumbers = "0123456789";
                String sNumBtnsLeft = "com.bestv.voiceAssist:id/number";
                m_uiObj = m_com.getUiObjByResId(sNumBtnsLeft);
                // 检查数字布局顺序是否正确
                for(int i = 0; i < sNumbers.length(); ++i){
                    uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS,
                            Infos.S_TXTVIEW_CLASS, i);
                    m_sActual = m_com.checkExpectResult(uiObj,
                            String.valueOf(sNumbers.charAt(i)));
                    if (!m_sActual.equalsIgnoreCase("OK")) {
                        m_sResult += m_sActual;
                        m_bPass = false;
                        break;
                    }
                }

                // 输入517
                m_com.Up();
                m_com.EnterW(0);
                m_com.Right();
                m_com.Up();
                m_com.EnterW(0);
                m_com.Right();
                m_com.Down();
                m_com.EnterW(0);

                m_sExpect = "517";
                checkInput();

                m_com.Down();
                m_com.EnterW(0);
                m_sExpect = "51";
                checkInput();

                m_com.Left();
                m_com.EnterW(0);
                m_sExpect = "输入影片首字母或全拼";
                checkInput();
            }
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    // only use in search page
    private void checkInput(){
        try {
            m_uiObj = m_com.getUiObjByResId(Infos.S_VOICE_SCH_SEARCH_EDIT);
            m_sActual = m_uiObj.getText();
            if (!m_sActual.equalsIgnoreCase(m_sExpect)) {
                m_bPass = false;
                m_sResult += String.format("输入框的内容实际值[%s]与期望值[%s]不符！", m_sActual, m_sExpect);
            }
        }catch(Throwable e){
            m_bPass = false;
            m_sResult = "checkInput函数发生错误！" + e;
        }
    }

    @Test
    public void Voice_SCH_31_onlyStarResult(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }
            // 只有明星的搜索
            m_sExpect = "JKJN";
            enterLetters(m_sExpect);
            if(m_com.getUiObjByResId("com.bestv.voiceAssist:id/category_list").exists()){
                m_bPass = false;
                m_sResult = String.format("搜索【%s】时右侧出现了Tab栏！", m_sExpect);
            }
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void Voice_SCH_32_mixedSearchResult(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }
            // 既有视频又有明星的搜索
            m_sExpect = "GFC";
            enterLetters(m_sExpect);
            if(!m_com.getUiObjByResId("com.bestv.voiceAssist:id/category_list").exists()){
                m_bPass = false;
                m_sResult = String.format("搜索【%s】时右侧没有出现Tab栏！", m_sExpect);
            }else{
                // 第一级UI
                m_uiObj = m_com.getUiObjByResId("com.bestv.voiceAssist:id/category_list");
                // 第二级UI
                m_uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS, Infos.S_VIEW_CLASS, 0);

                int iChilds = m_uiObj.getChildCount();
                int iExpect = 3;
                if(iChilds != iExpect){
                    m_bPass = false;
                    m_sResult = String.format("搜索【%s】时右侧Tab栏数量为【%d】，" +
                            "预期结果为【%d】！", m_sExpect, iChilds, iExpect);
                }
            }
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    public String getSearchResultTxt(int iIndex){
        // 右侧搜索结果View
        m_uiObj = m_com.getUiObjByResId(m_sRecycleView);
        m_uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS,
                Infos.S_RELATELAYOUT_CLASS, iIndex); // 编号为index的结果
        m_sExpect = m_com.getUiObjText(m_com.getUiObjChild(m_uiObj, m_com.BY_RESID,
                "com.bestv.voiceAssist:id/name", 0));
        return m_sExpect;
    }

    @Test
    public void Voice_SCH_38_enterResultDetailPage() {
        try {
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }

            m_com.Enter(); // 输入A字母
            m_com.waitTillOccur(m_com.BY_RESID, m_sSchPoster, 0, 15);
            m_com.Right(6); // 移动4次到结果列表，再移动两次到第2个结果的卡片

            // 第二个结果，一般是《爱情包邮》，如果发生变化，这里需要修改
            m_sExpect = getSearchResultTxt(1);
            if(m_sExpect.equalsIgnoreCase("爱情包邮")){ // 别名
                m_sExpect = "那件疯狂的小事叫爱情"; // 正片名字
            }

            m_com.Enter(); // 进入节目详情页
            m_sActual = m_com.getDetailTitle();
            if(!m_sActual.equalsIgnoreCase(m_sExpect)){
                m_sResult += m_sActual;
                m_bPass = false;
            }
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

    @Test
    public void Voice_SCH_39_clickStarNameEnterVideoList(){
        try{
            // 进入搜索页的功能函数
            m_sResult = enterSearchPage();
            if (!m_sResult.equalsIgnoreCase("OK")) {
                Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
            }
            // 输入明星缩写
            enterLetters("LDH");
            m_com.Right(3); // 第3次才到结果页的第一项
            // 检查视频列表是否存在
            m_sExpect = getSearchResultTxt(0); // 第一个一般是刘德华
            m_com.Enter(); // 点击进入明星视频列表
            String sId = "com.bestv.ott:id/top_title";
            m_com.Sleep(m_iOneSecond * 4);
            m_uiObj = m_com.getUiObjByResId(sId);
            if(!m_uiObj.exists()){
                m_bPass = false;
                m_sResult = String.format("点击明星【%s】没有出现相关的视频列表！", m_sExpect);
            }else{
                m_sActual = m_uiObj.getText();
                if(!m_sActual.equalsIgnoreCase(m_sExpect)){
                    m_bPass = false;
                    m_sResult = String.format("【%s】的视频列表与预期结果【%s】不符！",
                            m_sActual, m_sExpect);
                }
            }
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }

//    @Test
    public void test() throws UiObjectNotFoundException {
//        enterLetters("CCC*LDK<HZ*A#");
//        m_com.Enter();
//        TvCommon.printAllMethods(this.getClass().getName());
        // 进入搜索页的功能函数
//        m_sResult = enterSearchPage();
//        if (!m_sResult.equalsIgnoreCase("OK")) {
//            Utils.writeCaseResult(m_sResult, false, m_lConsumeTime);
//        }
//
//        for(int i = 0; i < 26; i += 2) {
//
//        }
    }
}
