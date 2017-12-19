package tv.fun.appassisttest.testCases;

import android.graphics.Rect;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import tv.fun.appassisttest.common.Funcs;
import tv.fun.appassisttest.common.PhoneCommon;
import tv.fun.appassisttest.common.UiIds;
import tv.fun.common.Utils;


//@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FirstTest{
	int iSwipe = 3; // the times of swiping to find the app
	int iDelay = 500;
	int iTabs = 3; // 下方的Tab栏数量
	int USER_CENTER_LIST_LEN = 4; // 个人中心的功能项数目
	String sTVName = "卧室的电视QQ群";
	boolean bFirstStartFlag = false;
    boolean bFlag = true;
	String[] lsPhoneNum = {"15926274299", "13545399905"};
	String[] lsPassword = {"wwwwww", "qqqqqq"};
	String sBuildMethod = "RELEASE";
    String sTemp;
	static PhoneCommon m_com = null;
	static UiObject tabsObj = null;
	static UiObject[] uiMainTabObjs = null;

    private long m_lConsumeTime = -1;
    private boolean m_bPass = true;
    private String m_sExpect = "";
    private String m_sActual = "";
    private String m_sResult = "";
    private String m_sObjId = "";
    private UiObject m_uiObj = null;

	@Before
	public void setUp(){
        m_lConsumeTime = Utils.getCurSecond();
        Funcs.WriteLogs("==========[风行电视助手]自动化测试初始化==========");
        m_com = new PhoneCommon();
        String sFolderName = "/data/local/tmp/log/";
        Funcs.MakeDir(sFolderName);

//        m_com.WakeUp();
//        enterApp();
//        waitingStart();
//        guidePage(); // guide page, first starting of app

        Funcs.WriteLogs("-----初始化主页的Tabs对象-----");
        tabsObj = m_com.getUiObjByResId(debugMethod(UiIds.SMAINTAB_STRING, sBuildMethod));
        Funcs.WriteLogs("-----初始化主页的Tabs对象列表-----");
        uiMainTabObjs = clickMainPageTabs(false);

        Funcs.WriteLogs("==========[风行电视助手]自动化测试初始化成功！==========");
        Funcs.WriteLogs("==========[风行电视助手]自动化测试开始==========");
	}
	
	// main test case
    @Test
	public void test_01_mainStart(){
		Funcs.WriteLogs("==============main case=================");
//        long iCurSec = Funcs.getCurSecond();
//        long iWaitSec = 10;
//        Funcs.WriteLogs("-----判断是否是DEBUG版-----");
//        while(!m_com.isUiObjExists(UiIds.SMAINTAB_STRING, "main tab is not exists")){
//        	if(Funcs.getCurSecond() >= iCurSec + iWaitSec){
//        		sBuildMethod = "DEBUG";
//        		break;
//        	}
//        	else if(Funcs.getCurSecond() >= iCurSec + (int)(iWaitSec/2)){
//        		m_com.Back();
//        	}
//        }
//        Funcs.WriteLogs(String.format("-----当前为[%s]版-----", sBuildMethod));

        Funcs.WriteLogs("-----点击主页上的Tabs-----");
        uiMainTabObjs = clickMainPageTabs(true);
        
        Funcs.WriteLogs("-----获取版本号-----");
        String verString = getVersion();
        Funcs.WriteLogs(String.format("-----获取版本号成功：[%s]-----", verString));
        
        enterAboutPage();
		enterAppPage();
		enterFeedback();
		enterForgotPwdPage();
		enterLoginPage();
		enterPersonalPage();
		getPersonalPageObjs(false);
		getPersonalPageObjs(true);
		login();
		getPersonalPageObjs(true);
		enterRegisterPage();
		enterToolsPage();
		enterProgrammeSearchPage();
		connectTV();
		enterControllerPage();
	}
	
	@Test
    public void test_02_checkHotSearch(){
        Funcs.Print("==============case: check hotsearch=================");
        caseCheckHotSearch(true);
    }

    @Test
	public void test_03_checkSearchResult(){
		Funcs.Print("==============case: check search result=================");
		caseCheckSearchResult();
	}

    @Test
	public void test_04_login() {
        login();
    }

    public void login(){
		enterLoginPage();
		UiObject uiUsrInput = m_com.getUiObjByResId(debugMethod(UiIds.SPHONEUSR_STRING, sBuildMethod));
        UiObject uiPwdInput = m_com.getUiObjByResId(debugMethod(UiIds.SPHONEPWD_STRING, sBuildMethod));
        try {
        	String sPhone = uiUsrInput.getText();
        	if(!sPhone.equalsIgnoreCase("手机号")){
        		uiUsrInput.setText("");
        		//m_com.Back();
        	}
            uiUsrInput.setText(lsPhoneNum[0]);
            Funcs.WriteLogs("-----输入[手机号]-----");
            uiPwdInput.setText(lsPassword[0]);
            Funcs.WriteLogs("-----输入[密码]-----");
            m_com.Sleep(2000);
            //m_com.Click(108, 1235, 1); // sogou input
            //m_com.Back(); // google input
            m_com.uiObjClickById(debugMethod(UiIds.SLOGIN_STRING, sBuildMethod));
		} catch (UiObjectNotFoundException e) {
			Funcs.WriteLogs("login error:" + e, m_com.ERR_CODE);
		}
	}

    @Test
	public void test_05_enterLoginPage(){
		enterLoginPage();
		Funcs.WriteLogs("-----进入[登录]页面-----");
	}

    public void enterLoginPage(){
        returnMainPage();
        m_com.uiObjClick(uiMainTabObjs[iTabs - 1]);
        clickLogout();
        clickLogin();
    }

    public void clickLogout(){
        String sText = "退出登录";
        if(m_com.isUiObjExists(m_com.BY_TEXT, sText, "")){
            m_com.uiObjClickByType(m_com.BY_TEXT, sText, 2);
            Funcs.WriteLogs("-----点击[退出登录]按钮-----");
        }
    }

    public void clickLogin(){
        String sText = "登录";
        if(m_com.isUiObjExists(m_com.BY_TEXT, sText, "")){
            m_com.uiObjClickByType(m_com.BY_TEXT, sText, 2);
            Funcs.WriteLogs("-----点击[登录]按钮-----");
        }
    }

    @Test
    public void test_06_enterForgotPwdPage(){
        enterForgotPwdPage();
    }

	public void enterForgotPwdPage(){
		enterLoginPage();
		m_com.uiObjClickByText("忘记密码");
		Funcs.WriteLogs("-----点击[忘记密码]-----");
	}

    @Test
    public void test_07_enterRegisterPage() {
        enterRegisterPage();
    }
	public void enterRegisterPage() {
		enterLoginPage();
		m_com.uiObjClickByText("注册");
		Funcs.WriteLogs("-----点击[注册]-----");
	}

	public boolean checkProgrammePage(){
		boolean bResult = true;
		String sSearchId = debugMethod(UiIds.SPRO_SEARCH_BAR_STRING, sBuildMethod);
		String sBannerId = debugMethod(UiIds.SPRO_BANNER_STRING, sBuildMethod);
		String sCategoryId = debugMethod(UiIds.SPRO_CATEGORY_STRING, sBuildMethod);
		String sHotPlayId = debugMethod(UiIds.SPRO_HOT_PLAY_STRING, sBuildMethod);
//		UiObject searchObject = m_com.m_com.getUiObjByResId(sSearchId);
//		UiObject bannerObject = m_com.m_com.getUiObjByResId(sBannerId);
//		UiObject categoryObject = m_com.m_com.getUiObjByResId(sCategoryId);
//		UiObject hotplayObject = m_com.m_com.getUiObjByResId(sHotPlayId);
		bResult &= m_com.isUiObjExists(sSearchId, "programme search bar is missing");
		bResult &= m_com.isUiObjExists(sBannerId, "programme banner is missing");
		bResult &= m_com.isUiObjExists(sCategoryId, "programme category bar is missing");
		bResult &= m_com.isUiObjExists(sHotPlayId, "programme hot play list is missing");
		if(bResult){
			// TODO check the secondary page
		}
		return bResult;
	}

    public void clickStatusBar(){
        m_sObjId = "com.funshion.remotecontrol:id/status_device_connect";
//        m_uiObj = m_com.getUiObjByResId(m_sObjId);
//        String sStatus = m_com.getUiObjText(m_uiObj);
//        Funcs.Print(sStatus);
        m_com.uiObjClickByType(m_com.BY_RESID, m_sObjId, 1); // 点击1次状态栏
    }

	public void clickFloatBtn(){
		m_com.uiObjClickByType(m_com.BY_DESC, "float button", 1); // 点击1次悬浮按钮
	}
	
	// press back button when obj_id occur
	public void needPressBack(String obj_id){
		if(m_com.isUiObjExists(debugMethod(obj_id, sBuildMethod))){
			Funcs.Print("Press back: " + obj_id);
			m_com.Back();
		}
	}
	
	public String debugMethod(String sString, String sMethod){
		if(sMethod.equalsIgnoreCase("DEBUG")){
			sString = sString.replace(":", ".debug:");
		}
		return sString;
	}

	public void enterApp(){
		if(m_com.isUiObjExists(UiIds.SMAINTAB_STRING)){
			return;
		}
		m_com.Back();
		Funcs.WriteLogs("-----在桌面寻找并启动App-----");
		// PressHome twice to confirm back to home page
        m_com.Home(2);
        m_com.Sleep(iDelay);

        // Get app uiobject and start app
        UiObject uiObjAppIcon = new UiObject(new UiSelector().text(UiIds.SAPPNAME_STRING));
        if(findUiObject(uiObjAppIcon, iSwipe)){
        	m_com.uiObjClick(uiObjAppIcon);
        }
        else{
        	Funcs.WriteLogs("error: app not found!", m_com.ERR_CODE);
        }
	}

    @Test
    public void test_08_returnMainPage(){
        returnMainPage();
    }
	public void returnMainPage(){
		while(!m_com.pressBackToFindObj(m_com.getUiObjByResId(UiIds.SMAINTAB_STRING), 5)){
        	m_com.Back();
        }
	}

	public UiObject[] clickMainPageTabs(boolean bClickFlag){
		returnMainPage();
		try {
			int iChilds = tabsObj.getChildCount();
	        Funcs.Print("Tabs num: " + iChilds);
	        UiObject[] uiChildObjs = new UiObject[iChilds];
	        for(int i = 0; i < iChilds; i++){
	        	uiChildObjs[i] = tabsObj.getChild(new UiSelector().className("android.widget.ImageView").instance(i));
	        	Funcs.Print("child "+ i + " " + uiChildObjs[i].getBounds());
	        	if(bClickFlag && uiChildObjs[i].exists()){
	        		uiChildObjs[i].click();
	        	}
	        }
	        return uiChildObjs;
		} catch (UiObjectNotFoundException e) {
			Utils.writeLogs("click mainpage tabs error: " + e, m_com.ERR_CODE);
			return null;
		}
	}

    @Test
    public void test_09_enterAppPage(){
        enterAppPage();
    }
	public void enterAppPage(){
		returnMainPage();
		m_com.uiObjClickByType(m_com.BY_TEXT, "应用", 1);
	}

    @Test
    public void test_10_enterToolsPage(){
        enterToolsPage();
    }
	public void enterToolsPage(){
		returnMainPage();
		m_com.uiObjClick(uiMainTabObjs[iTabs - 2]);
	}

	@Test
    public void test_11_enterAboutPage(){
        try {
            enterAboutPage();
            String sText1 = "意见反馈";
            String sText2 = "检查更新";

            m_sObjId = "com.funshion.remotecontrol:id/about_listlayout";
            m_uiObj = m_com.getUiObjByResId(m_sObjId);
            m_uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS,
                    "android.widget.LinearLayout", 0);
            m_uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS,
                    "android.widget.LinearLayout", 0);
            UiObject uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS,
                    "android.widget.RelativeLayout", 0);

            //m_sObjId = "com.funshion.remotecontrol:id/tv_item_name";
            uiObj = m_com.getUiObjChild(uiObj, m_com.BY_CLASS,
                    "android.widget.TextView", 1);
            Utils.Print(uiObj.getText());
            Utils.Print(uiObj.getBounds());
            m_bPass = m_com.isUiObjExists(m_com.BY_TEXT, sText1, "") &&
                    m_com.isUiObjExists(m_com.BY_TEXT, sText2, "");
            if(!m_bPass){
                m_sResult = "关于页没有【意见反馈】和【检查更新】！";
            }
        }catch (Throwable e){
            e.printStackTrace();
            m_bPass = false;
            m_sResult += e.toString();
        }finally {
            Utils.writeCaseResult(m_sResult, m_bPass, m_lConsumeTime);
        }
    }
	public void enterAboutPage(){
		enterPersonalPage();
		Funcs.WriteLogs("-----开始进入[关于]页-----");
		UiObject[] objs = getPersonalPageObjs(false);
		m_com.uiObjClick(objs[objs.length - 1]);
		Funcs.WriteLogs("-----成功进入[关于]页-----");
	}

	public String getVersion(){
		enterAboutPage();
		UiObject uiCheckUpdateObj = m_com.getUiObjByResId(debugMethod(UiIds.SVERSION_STRING, sBuildMethod));
		String sVer = m_com.getUiObjText(uiCheckUpdateObj);
		String[] lRes = sVer.split("-");
        sBuildMethod = lRes[lRes.length - 1]; // confirm build method
        if(sBuildMethod.equalsIgnoreCase(sVer)){
        	sBuildMethod = "RELEASE";
        }
		m_com.uiObjClick(uiCheckUpdateObj); // click check update
		if("" != sVer){
			return sVer;
		}
		Funcs.Print("error: cannot get the version!");
		return "0";
	}

    @Test
    public void test_12_enterFeedback(){
        enterFeedback();
    }
	public void enterFeedback(){
		Funcs.WriteLogs("-----准备进入[意见反馈]页-----");
		enterAboutPage();
		UiObject uiAboutList = m_com.getUiObjByResId(debugMethod(UiIds.SABTOULIST_STRING, sBuildMethod));
		Funcs.WriteLogs("-----准备点击[意见反馈]功能项-----");
		UiObject uiFeedbackObj;
		try {
			uiFeedbackObj = uiAboutList.getChild(new UiSelector().className("android.widget.RelativeLayout").instance(0));
			m_com.uiObjClick(uiFeedbackObj);
			Funcs.WriteLogs("-----成功点击[意见反馈]功能项-----");
		} catch (UiObjectNotFoundException e) {
			Funcs.WriteLogs("enter feedback error: " + e, m_com.ERR_CODE);
		}
	}

    @Test
    public void  test_13_enterPersonalPage() {
        enterPersonalPage();
    }
	public void  enterPersonalPage() {
		returnMainPage();
		m_com.uiObjClick(uiMainTabObjs[uiMainTabObjs.length - 1]);
		Funcs.WriteLogs("-----进入[个人中心]页-----");
	}
	
	public UiObject[] getPersonalPageObjs(boolean bClickFlag){
		enterPersonalPage();
        Funcs.WriteLogs("-----获取个人中心的UI对象-----");
		UiObject personnalObj = m_com.getUiObjByResId(debugMethod(UiIds.SUSERCENTERLIST_STRING,
                sBuildMethod));
		int iChilds = USER_CENTER_LIST_LEN;
        UiObject[] uiChildObjs = new UiObject[iChilds];
        try {
        	for(int i = 0; i < iChilds; i++){
            	uiChildObjs[i] = personnalObj.getChild(
                        new UiSelector().resourceId(
                                "com.funshion.remotecontrol:id/tv_item_name").instance(i));
            	Funcs.Print("child "+ uiChildObjs[i].getText() + " " + uiChildObjs[i].getBounds());
            	if(bClickFlag && uiChildObjs[i].exists()){
            		uiChildObjs[i].click();
            		returnMainPage();
            	}
            }
		} catch (UiObjectNotFoundException e) {
			Funcs.WriteLogs("enter Personal page error: " + e, m_com.ERR_CODE);
		}
		return uiChildObjs;
	}
	
	// click controller operation guiding images
	public void guideImageClick(){
		String sOperateTips = "com.funshion.remotecontrol:id/guide_imageview"; // when first connected, guiding image tips
		UiObject uiOperateTipsObj = m_com.getUiObjByResId(sOperateTips);
        // when first connected, guiding image tips show up
		while(uiOperateTipsObj.exists()){
        	Funcs.Print("info: the guide tips of operation is up");
			m_com.uiObjClick(uiOperateTipsObj);
		}
	}

	public void operationController(){
		int steps = 5;
		int x, y;
		Rect rect;
		guideImageClick();
		UiObject bodyObj = m_com.getUiObjByResId(debugMethod(UiIds.SCONTROLCONTENT_STRING, sBuildMethod));
		try {
			rect = bodyObj.getBounds();
			x = rect.centerX();
			y = rect.centerY();
			Funcs.Print("rect: " + rect + "x: " + x + "y: " + y);
			// enter video classification on TV
			m_com.uiObjClickById(debugMethod(UiIds.SHOME_BTN_STRING, sBuildMethod));
			m_com.SwipeL2R(x, y, 100, steps);
			m_com.SwipeT2B(x, y, 100, steps);
			m_com.SwipeT2B(x, y, 100, steps);
			m_com.Click(x, y, 1);
			m_com.Sleep(6 * iDelay);
			
			// direction operation
			m_com.SwipeL2R(x, y, 100, steps);
			m_com.Sleep(iDelay);
			m_com.SwipeR2L(x, y, 100, steps);
			m_com.Sleep(iDelay);
			m_com.SwipeT2B(x, y, 100, steps);
			m_com.Sleep(iDelay);
			m_com.SwipeB2T(x, y, 100, steps);
			m_com.Sleep(iDelay);

			// press menu button
			m_com.uiObjClickById(debugMethod(UiIds.SMENU_BTN_STRING, sBuildMethod));
			m_com.Sleep(4 * iDelay);
			m_com.uiObjClickById(debugMethod(UiIds.SBACK_BTN_STRING, sBuildMethod));
					
			m_com.Click(x, y, 1);
			m_com.Sleep(8 * iDelay);
			m_com.uiObjClickById(debugMethod(UiIds.SBACK_BTN_STRING, sBuildMethod));
			m_com.uiObjClickById(debugMethod(UiIds.SOPTIMIZE_BTN_STRING, sBuildMethod));
			m_com.Sleep(10 * iDelay);
			m_com.uiObjClickById(debugMethod(UiIds.SBACK_BTN_STRING, sBuildMethod));
			m_com.uiObjClickById(debugMethod(UiIds.SVOICE_BTN_STRING, sBuildMethod));
			m_com.Sleep(20 * iDelay);
			m_com.uiObjClickById(debugMethod(UiIds.SBACK_BTN_STRING, sBuildMethod));
		} catch (UiObjectNotFoundException e) {
			Funcs.WriteLogs("operate error: " + e, m_com.ERR_CODE);
		}
	}

    @Test
    public void test_14_enterControllerPage(){
        enterControllerPage();
    }
	public void enterControllerPage(){
        returnMainPage();
        m_com.uiObjClick(uiMainTabObjs[0]);
		boolean bConnect = false;
		clickFloatBtn();
		
		// connected
		if(m_com.isUiObjExists(debugMethod(UiIds.SCONNECT_STATU_STRING, sBuildMethod),
                "controller is not connected!")){
			checkControllerBody();
			m_com.uiObjClickById(debugMethod(UiIds.SCONTROLCLEAR_BTN_STRING, sBuildMethod));
			clickFloatBtn();
			m_com.uiObjClickById(debugMethod(UiIds.SCONNECT_STATU_STRING, sBuildMethod));
			bConnect = connectTVFromList(sTVName, false);
		}
		else{
			int i = 0;
			while(!bConnect && i < 3){
				Funcs.Print("i: " + i);
				bConnect = connectTVFromList(sTVName, true);
				++i;
			}
		}
		if(bConnect){
			operationController();
		}
	}

    @Test
    public void test_15_connectTV(){
        connectTV();
    }
	public boolean connectTV(){
		returnMainPage();
        m_com.uiObjClick(uiMainTabObjs[0]);
        return connectTV(sTVName);
	}

    public boolean connectTV(String sName){
        clickFloatBtn();
        if (m_com.isUiObjExists(UiIds.SWIFI_LOGO_STRING)) { // not connect tv yet
            bFlag = connectTVFromList(sName, true);
        }
        return bFlag;
    }
	
	public boolean connectTVFromList(String sTvName, boolean bClickFlag){
		String tvListString = debugMethod(UiIds.STVLIST_STRING, sBuildMethod);
		String tvLoadingBar = debugMethod(UiIds.SLOADINGBAR_STRING, sBuildMethod);
		Funcs.Print("---正在刷新TV列表---");
		while(m_com.isUiObjExists(tvLoadingBar)){
            if(m_com.getUiObjByText(sTvName).exists()){
                break;
            }
		}
		if(!m_com.isUiObjExists(tvListString)){
			Funcs.WriteLogs(String.format("TV[%s]和手机不在同一个WiFi下！", sTvName));
			m_com.uiObjClickById(debugMethod(UiIds.SREFRESH_STRING, sBuildMethod));
			return false;
		}
		UiObject uiTVObject = m_com.getUiObjByResId(tvListString);
		try {
			int iCnt = uiTVObject.getChildCount();
			UiObject[] uiChildObjs = new UiObject[iCnt];
			for(int i = 0; i < iCnt; i++){
				uiChildObjs[i] = uiTVObject.getChild(new UiSelector().resourceId(UiIds.STVLIST_ITEM_LAYOUT_STRING).instance(i));
				if(uiChildObjs[i].waitForExists(6 * iDelay)){
					String sDeviceNameString = uiChildObjs[i].getChild(new UiSelector().resourceId(UiIds.STVNAME_STRING).instance(0)).getText();
					Funcs.Print("tvlist "+ i + " " + uiChildObjs[i].getBounds() + "text: " + sDeviceNameString);
					if(bClickFlag && sDeviceNameString.equalsIgnoreCase(sTvName)){
						uiTVObject = uiChildObjs[i];
						uiChildObjs[i].click();
						Funcs.Print("connect to " + sDeviceNameString);
						break;
					}
				}
			}
			if(!bClickFlag){
				Funcs.WriteLogs("不连接遥控，直接返回");
				m_com.uiObjClickById(debugMethod(UiIds.SLISTBACK_BTN_STRING, sBuildMethod)); // back button in add devices page
                return false;
			}
			Funcs.Print("尝试连接电视！");
			for(int i = 0; i < 5 && !m_com.isUiObjExists(UiIds.SCONNECT_STATU_STRING); ++i){
				uiTVObject.click();
				if(m_com.isUiObjExists(UiIds.SCONNECT_STATU_STRING)){
					Funcs.Print("tv is connected!");
					return true;
				}
				else{
					Funcs.Print("tv connection failed!");
                    return false;
				}
			}
		} catch (Exception e) {
			Funcs.WriteLogs("get tv list error: " + e, m_com.ERR_CODE);
			return false;
		}
		return false;
	}

	public void connectTVByIP(String sIP){
        //clickFloatBtn();
        clickStatusBar();
        // 如果已连接，则点击“已连接”按钮
        if(m_com.isUiObjExists(UiIds.SCONNECT_STATU_STRING, "")){
            m_com.uiObjClickById(UiIds.SCONNECT_STATU_STRING);
        }
        // 如果未连接，则直接点击“+”按钮
        m_sObjId = "com.funshion.remotecontrol:id/top_bar_layout";
        m_uiObj = m_com.getUiObjByResId(m_sObjId);
        if(!m_uiObj.exists()){
            return;
        }
        m_uiObj = m_com.getUiObjChild(m_uiObj, m_com.BY_CLASS, "android.widget.TextView", 1);
        m_com.uiObjClick(m_uiObj);

        String[] aIP = sIP.split("\\.");
        m_sObjId = "com.funshion.remotecontrol:id/connect_tv_ip";
        // 输入四段IP
        for(int i = 1; i < 5; ++i){
            sTemp = m_sObjId + i; // 4个输入框的id 就是在后面的数字不同
            try {
                m_com.getUiObjByResId(sTemp).setText(aIP[i - 1]);
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
        // 连接电视按钮
        m_sObjId = "com.funshion.remotecontrol:id/connect_tv_btn";
        m_com.uiObjClickById(m_sObjId);
        m_com.Sleep(2000);

        // 如果存在×按钮，则点击一下
        m_sObjId = "com.funshion.remotecontrol:id/connect_tv_close";
        if(m_com.isUiObjExists(m_sObjId)){
            m_com.uiObjClickById(m_sObjId);
            m_com.Back();
        }
    }

    public void disconnectTV(){
        returnMainPage();
        m_com.uiObjClick(uiMainTabObjs[0]);
        clickFloatBtn();
        if(m_com.isUiObjExists(UiIds.SCONNECT_STATU_STRING)){
            m_com.uiObjClickById(UiIds.SCONNECT_STATU_STRING);
            while(!m_com.isUiObjExists(UiIds.SSELECT_WIFI_STRING)){
                m_com.SwipeB2T(0, 0, 100, 10);
            }
            m_com.uiObjClickById(UiIds.SSELECT_WIFI_STRING);
        }
    }

    @Test
    public void test_16_enterProgrammeSearchPage(){
        enterProgrammeSearchPage();
    }
	public void enterProgrammeSearchPage(){
		returnMainPage();
		m_com.uiObjClick(uiMainTabObjs[0]);
		Funcs.WriteLogs("点击节目首页的搜索栏");
		m_com.uiObjClickById(debugMethod(UiIds.SPRO_SEARCH_BAR_STRING, sBuildMethod));
	}
	
	// case：检查搜索jk后的结果
	public void caseCheckSearchResult(){
		searchProgramme("jk");	// 搜索jk
		UiObject uiObj = null;
				
		// 获取第2个搜索结果对象，点击后返回
		uiObj = m_com.getUiObject(m_com.BY_RESID, UiIds.SSEARCH_ITEM_STRING, 1); // 1是节目序号
		
		m_com.waitTillOccur(uiObj, 10);
		
		m_com.uiObjClick(uiObj);
		m_com.Back();

        // 连接电视后返回节目列表
		connectTV(sTVName);
        m_com.Back();

		// 获取第2个搜索结果的电视播放按钮对象，然后点击
		uiObj = m_com.getUiObject(m_com.BY_RESID, UiIds.STVPLAY_BTN_STRING, 1);
		m_com.uiObjClick(uiObj);
		
		// 向上滑动，直到有明星列表项出现
		while(!m_com.isUiObjExists(UiIds.SSEARCH_STAR_STRING)){
			m_com.SwipeB2T(0, 0, 100, 10);
		}
		
		// 点击第1个明星列表项
		uiObj = m_com.getUiObject(m_com.BY_RESID, UiIds.SSEARCH_STAR_STRING, 0);
		String sName = "";
		try {
			sName = m_com.getUiObjChild(uiObj, m_com.BY_CLASS, "android.widget.TextView", 0).getText();
		} catch (UiObjectNotFoundException e) {
			sName = "获取名字出错！";
		}
		m_com.uiObjClick(uiObj);
		
		m_com.Sleep(5 * iDelay);
		// 没有内容显示时，就点击下一个明星名字
		if(!m_com.isUiObjExists(m_com.BY_RESID, UiIds.SSEARCH_ITEM_STRING, String.format("[%s]没有搜索到结果！", sName))){
			m_com.Back();
			m_com.SwipeB2T(0, 0, 100, 20);
			uiObj = m_com.getUiObject(m_com.BY_RESID, UiIds.SSEARCH_STAR_STRING, 1);
			m_com.uiObjClick(uiObj);
		}
	}
	
	// case：检查热门搜索名称
	public void caseCheckHotSearch(boolean bClick){
		enterProgrammeSearchPage();
		String sHotSearch = "com.funshion.remotecontrol:id/tvprogram_fragment_flowlayout";
		UiObject uiHotSearch = m_com.getUiObjByResId(debugMethod(sHotSearch, sBuildMethod));
		try {
			int iCnt = uiHotSearch.getChildCount();
			for(int i = 0; i < iCnt; ++i){
				UiObject uiLink = uiHotSearch.getChild(
						new UiSelector().className("android.widget.TextView").instance(i));
				Funcs.Print("Name: " + uiLink.getText());
				if(bClick){
					uiLink.click();
					m_com.Sleep(2000);
					m_com.Back();
				}
			}
		} catch (UiObjectNotFoundException e) {
			Funcs.WriteLogs("check hotsearch error:" + e, m_com.ERR_CODE);
		}
	}
	
	public void searchProgramme(String sSearchText){
		enterProgrammeSearchPage();
		String sSearchEdit = "com.funshion.remotecontrol:id/tvprogram_search_edit";
		UiObject uiSearchEdit = m_com.getUiObjByResId(debugMethod(sSearchEdit, sBuildMethod));
		try {
			uiSearchEdit.clearTextField();
            m_com.uiObjClick(uiSearchEdit);
			uiSearchEdit.setText(sSearchText);
			m_com.clickEnter(0);
		} catch (UiObjectNotFoundException e) {
			Funcs.WriteLogs("search error: " + e, m_com.ERR_CODE);
		}
	}
	
	public boolean checkControllerBody(){
		boolean bResult = true;
		String sSwitchBtn = debugMethod(UiIds.SPOWER_BTN_STRING, sBuildMethod); // power button
		String sOptimizeBtn = debugMethod(UiIds.SOPTIMIZE_BTN_STRING, sBuildMethod); // optimize button
		String sVoiceBtn = debugMethod(UiIds.SVOICE_BTN_STRING, sBuildMethod); // voice button
		String sHomeBtn = debugMethod(UiIds.SHOME_BTN_STRING, sBuildMethod); // home button
		String sBackBtn = debugMethod(UiIds.SBACK_BTN_STRING, sBuildMethod); // back button
		String sMenuBtn = debugMethod(UiIds.SMENU_BTN_STRING, sBuildMethod); // menu button
//		String sLine1 = debugMethod(UiIds.STEXTLINE1_STRING, sBuildMethod);
//		String sLine2 = debugMethod(UiIds.STEXTLINE2_STRING, sBuildMethod);
//		String sLine3 = debugMethod(UiIds.STEXTLINE3_STRING, sBuildMethod);

		
		bResult &= m_com.isUiObjExists(sSwitchBtn, "error: the power button is missing");
		bResult &= m_com.isUiObjExists(sOptimizeBtn, "error: the optimize button is missing");
		bResult &= m_com.isUiObjExists(sHomeBtn, "error: the home button is missing");
		bResult &= m_com.isUiObjExists(sBackBtn, "error: the back button is missing");
		bResult &= m_com.isUiObjExists(sMenuBtn, "error: the menu button is missing");
		bResult &= m_com.isUiObjExists(sVoiceBtn, "error: the voice button is missing");
//		bResult &= m_com.isUiObjExists(sLine1, "error: the introduction text1 is missing");
//		bResult &= m_com.isUiObjExists(sLine2, "error: the introduction text2 is missing");
//		bResult &= m_com.isUiObjExists(sLine3, "error: the introduction text3 is missing");
		Funcs.Print("controller body checking result: " + bResult);
		return bResult;
	}
	
//	public boolean checkConnectPage(){
//		if(checkBody()){
//			return true;
//		}
//		return false;
//	}
	
//	public boolean checkTitleBar(){
//		boolean bResult = true;
//		bResult &= m_com.isUiObjExists("com.funshion.remotecontrol:id/btnMenu",
//				"error: the personal center button is missing");
//		bResult &= m_com.isUiObjExists("com.funshion.remotecontrol:id/btnShaoMa",
//				"error: the scanner button is missing");
//		return bResult;
//	}
	
//	public boolean checkBody(){
//		boolean bResult = true;
//		bResult &= m_com.isUiObjExists("com.funshion.remotecontrol:id/device_server",
//				"error: the pic of device in the body is missing");
//		bResult &= m_com.isUiObjExists("com.funshion.remotecontrol:id/device_connect_tip_textview",
//				"error: the text of connection tips in the body is missing");
//		return bResult;
//	}
	
	public void waitingStart(){
		Funcs.Print("---App启动中...---");
		while(m_com.isUiObjExists(UiIds.SSTART_IMG_STRING)){
			m_com.Sleep(1000);
		}
		Funcs.Print("---App启动成功！---");
	}
	
	public void guidePage(){
		// first start app, there are three guide pages
        String sStartBtn = "com.funshion.remotecontrol:id/startguide_btn";
        // first guide page
        bFirstStartFlag = m_com.isUiObjExists(UiIds.SGUIDE_IMG_STRING);
        if(bFirstStartFlag){
        	Funcs.WriteLogs("-----第一次启动App时，出现三张[指引页]-----");
        }
        while(!m_com.isUiObjExists(sStartBtn) && bFirstStartFlag){
            m_com.SwipeR2L(0, 0, 100, 10);
        }
        // third guide page
        if(m_com.isUiObjExists(sStartBtn)){
        	Funcs.WriteLogs("-----点击第三张[指引页]上的【立即体验】按钮-----");
        	m_com.uiObjClickById(sStartBtn);
        }
	}
		
	public boolean findUiObject(UiObject uiObj, int iFindTimes){
		int iCnt = 0;
        // find object from right
        while(!uiObj.exists() && iCnt != 3*iFindTimes){
            m_com.SwipeR2L(0, 0, 100, 10);
            m_com.Sleep(iDelay);
            if(uiObj.exists()){
                Funcs.Print("find obj: found in right of home");
                return true;
            }
            iCnt++;
        }
		// find object from left
		while(!uiObj.exists() && iCnt != iFindTimes){
			m_com.SwipeL2R(0, 0, 100, 10);
			m_com.Sleep(iDelay);
			if(uiObj.exists()){
				Funcs.Print("find obj: found in left of home");
				return true;
			}
			iCnt++;
		}
		return uiObj.exists();
	}
}
