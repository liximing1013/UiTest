package tv.fun.appstoretest.testCases;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.view.KeyEvent;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import java.util.List;

import tv.fun.appstoretest.common.AppStorePage;
import tv.fun.common.Utils;


/**
 * Created by liuqing on 2016/8/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAppStore extends AppStorePage {
    private static final String AppStore_PACKAGE = "tv.fun.appstore";
    public String autoProjectKeyWord = "test";
    public int maxCountOfOneLine = 6;
    public String[] textInSearchWindow = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    public String systemAppPageTitle = "系统应用";
    public String[] systemAppList = {"应用市场", "文件管理", "电视助手", "系统升级","风行天气", "无线投屏", "玩转电视", "视频通话"};

    /**
     * Test Launcher App tab page UI displays correctly
     *
     * @throws UiObjectNotFoundException
     */
    @Category({LevelP1Tests.class})
    @Test
    public void App_Home_01_testLauncherAppUI() {
        String error = "";
        try {
            //移动焦点到Launcher应用tab
            navigateToLauncherAppTab();
            //得到Launcher应用页面元素
            UiObject appStoreIcon = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(appStoreIconName));
            UiObject tvMasterIcon = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(tvMasterIconName));
            UiObject myAppIcon = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/maintitle").text(myAppIconName));
            UiObject myAppSubTitle = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/subtitle"));//(22个)
            String appStoreStr = appStoreIcon.getText();

            //断言
            verifyElementPresent("应用市场卡片没有显示在Launcher应用页面", appStoreIcon);
            verifyElementPresent("电视助手卡片没有显示在Launcher应用页面", tvMasterIcon);
            verifyElementPresent("我的应用卡片没有显示在Launcher应用页面", myAppIcon);
            verifyString("在Launcher应用页面应用市场卡片显示不正确", appStoreIcon.getText(), appStoreIconName);
            verifyIncludeString("我的应用卡片下方的应用数量没有显示在Launcher应用页面", myAppSubTitle.getText(), myAppCountUnit);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);


        }
    }

//    /**
//     * Test that can enter App detail page from launcher app tab
//     *
//     * @throws RemoteException
//     * @throws UiObjectNotFoundException
//     * @throws InterruptedException
//     */
//    @Category(LevelP2Tests.class)
//    @Test
//    public void App_Home_07_testEnterAppDetailFromLauncher() {
//        try {
//            //移动焦点到Launcher应用tab
//            navigateToLauncherAppTab();
//            moveDown();
//            UiObject firstApp = findElementByIndex("id", "com.bestv.ott:id/poster", 0);
//            firstApp.clickAndWaitForNewWindow();
//            waitForAppDetailPageDisplay();
//            //断言
//            UiObject pic = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_poster_image"));
//            UiObject controlledDevice = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/downloadCount"));
//            verifyElementPresent("详情页中海报图没有显示", pic);
//            verifyIncludeString("详情页中下载次数没有显示", controlledDevice.getText(), "下载：");
//        } catch (Throwable e) {
//            e.printStackTrace();
//            resultFlag = false;
//            resultStr = e.toString();
//        } finally {
//            Utils.writeCaseResult(resultStr,
//                    resultFlag, execTime);
//        }
//    }

    /**
     * test install app under Launcher App tab
     *
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP2Tests.class)
//    @Test
    public void App_Home_07_testInstallAppFromLauncherAndBack() {
        try {
            //移动焦点到Launcher应用tab
            navigateToLauncherAppTab();
            moveDown();
            UiObject firstApp = findElementByIndex("id", "com.bestv.ott:id/poster", 0);
            int installedAppNum = stringToInt(findElementByID("com.bestv.ott:id/subtitle").getText().replace("个)", "").replace("(", ""));
            firstApp.clickAndWaitForNewWindow();
            waitForAppDetailPageDisplay();

            //如果第一个应用已安装，回到首页再选择另一应用安装
            if (findElementByClass("android.widget.Button").exists()) {
                if ("打开".equalsIgnoreCase(findElementByClass("android.widget.Button").getText())) {
                    String appName = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText();
                    back();
                    moveRight();
                    device.pressEnter();
                }
            }
            //installBtn.click();不能实现点击安装按钮操作，故使用pressEnter
            moveDown();
            moveUp();
            //点击安装按钮，安装应用
            device.pressEnter();
            waitForElementNotPresentByID("tv.fun.appstore:id/progressState");
            waitForElementPresentByClassAndText("android.widget.Button", "打开");
            UiObject openBtn = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/emptyButton")).getChild(new UiSelector().resourceId("tv.fun.appstore:id/titleContainer"));

            //断言
            Assert.assertTrue("Failed to install app from Launcher App page", openBtn.getText().equalsIgnoreCase("打开"));
            back();
            waitForElementPresentByID("com.bestv.ott:id/tab_title");
            UiObject appTab = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/tab_title").text("应用"));
            verifyElementPresent("Doesn't back to Launcher App page", appTab);
            int currAppNum = stringToInt(findElementByID("com.bestv.ott:id/subtitle").getText().replace("个)", "").replace("(", ""));
            verifyNumber("The install app num is not increased after install one app", currAppNum, installedAppNum + 1);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can goto My App page from Launcher
     *
     */
    @Test
    public void App_Home_12_testEnterMyAppPageFromLauncher(){
        try{
            //移动焦点到Launcher应用tab
            moveToLauncherTargetTab(appTab, launcherTabID, 1);
            moveDown();
            moveRightForMultiple(2);
            UiObject myAppObj = findElementByText("我的应用", "com.bestv.ott:id/maintitle");
            myAppObj.clickAndWaitForNewWindow();
            //断言
            UiObject titleObj = findElementByID("tv.fun.appstore:id/title");
            verifyString("", titleObj.getText(), "我的应用");
            UiObject subTitle = findElementByID("tv.fun.appstore:id/subTitle");
            String subTitleText = subTitle.getText();
            verifyIncludeString("", subTitleText, "个应用");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can Back to Launcher home page from My App page By home
     */
    @Test
    public void App_Home_16_testBackToLauncherHomeFromMyApp(){
        try{
            //移动焦点到Launcher应用tab
            moveToLauncherTargetTab(appTab, launcherTabID, 4);
            moveDown();
            moveRightForMultiple(2);
            UiObject myAppObj = findElementByText("我的应用", "com.bestv.ott:id/maintitle");
            myAppObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            device.pressHome();
            waitForElementNotPresentByID("tv.fun.appstore:id/title");
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTabObj = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTabObj.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can Back to Launcher App home page from My App page By back btn
     */
    @Test
    public void App_Home_17_testBackToAppHomeFromMyApp(){
        try{
            //移动焦点到Launcher应用tab
            moveToLauncherTargetTab(appTab, launcherTabID, 4);
            moveDown();
            moveRightForMultiple(2);
            UiObject myAppObj = findElementByText("我的应用", "com.bestv.ott:id/maintitle");
            myAppObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            back();
            waitForElementNotPresentByID("tv.fun.appstore:id/title");
            //断言
            UiObject appStoreIcon = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(appStoreIconName));
            UiObject tvMasterIcon = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(tvMasterIconName));
            String appStoreStr = appStoreIcon.getText();
            verifyElementPresent("应用市场卡片没有显示在Launcher应用页面", appStoreIcon);
            verifyElementPresent("电视助手卡片没有显示在Launcher应用页面", tvMasterIcon);
            UiObject appTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(launcherAppTab));
            verifyTrue("", appTab.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can Back to Launcher App home page from My App page By back btn
     */
    @Test
    public void App_Home_19_testMenuPopUpInMyApp(){
        try{
            //移动焦点到Launcher应用tab
            moveToLauncherTargetTab(appTab, launcherTabID, 4);
            moveDown();
            moveRightForMultiple(2);
            UiObject myAppObj = findElementByText("我的应用", "com.bestv.ott:id/maintitle");
            myAppObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            moveRight();
            menu();

            //断言
            UiObject menuIconObj = findElementByID("android:id/tv_fun_menu_icon");
            UiObject uninstallBtn = findElementByText("卸载", "android:id/tv_fun_menu_text");
            UiObject cleanBtn = findElementByText("清理数据", "android:id/tv_fun_menu_text");
            UiObject menuPop = findElementByID("android:id/tv_fun_menu");
            verifyElementPresent("", menuIconObj);
            verifyElementPresent("", uninstallBtn);
            verifyElementPresent("", cleanBtn);
            verifyTrue("", menuPop.isEnabled());
            back();
            UiObject menuDisappear = findElementByID("android:id/tv_fun_menu");
            verifyElementNotPresent("", menuDisappear);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can uninstall one app in my app page
     */
    @Test
    public void App_Home_22_testUninstallAppInMyApp(){
        try{
            //移动焦点到Launcher应用tab
            moveToLauncherTargetTab(appTab, launcherTabID, 4);
            moveDown();
            moveRightForMultiple(2);
            UiObject myAppObj = findElementByText("我的应用", "com.bestv.ott:id/maintitle");
            myAppObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            moveRight();
            UiObject subTitle = findElementByID("tv.fun.appstore:id/subTitle");//有18个应用
            int appCount = stringToInt(subTitle.getText().replace("有", "").replace("个应用","").replace(" ", ""));
            menu();
            waitForElementPresentByID("android:id/tv_fun_menu");

            //断言
            UiObject uninstallBtn = findElementByText("卸载", "android:id/tv_fun_menu_text");
            uninstallBtn.click();
            waitForElementPresentByID("com.android.packageinstaller:id/uninstall_activity_snippet");
            UiObject okBtn = findElementByID("com.android.packageinstaller:id/ok_button");
            UiObject appNameObj = findElementByID("com.android.packageinstaller:id/app_name");
            String appName = appNameObj.getText();

            //在卸载弹框上点击“确定”按钮
            okBtn.click();
            waitForTextNotPresent("卸载完成");
            UiObject subTitleAfterUninstall = findElementByID("tv.fun.appstore:id/subTitle");//有17个应用
            int appCountAfterUninstall = stringToInt(subTitleAfterUninstall.getText().replace("有", "").replace("个应用","").replace(" ", ""));
            verifyTrue("", appCountAfterUninstall==appCount-1);

        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can cancel to uninstall one app in my app page
     */
    @Test
    public void App_Home_23_testCancelUninstallAppInMyApp(){
        try{
            //移动焦点到Launcher应用tab
            moveToLauncherTargetTab(appTab, launcherTabID, 4);
            moveDown();
            moveRightForMultiple(2);
            UiObject myAppObj = findElementByText("我的应用", "com.bestv.ott:id/maintitle");
            myAppObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            moveRight();
            menu();
            waitForElementPresentByID("android:id/tv_fun_menu");
            waitForElementPresentByID("android:id/tv_fun_menu_text");

            //断言
            UiObject uninstallBtn = findElementByText("卸载", "android:id/tv_fun_menu_text");
            uninstallBtn.click();
            waitForElementPresentByID("com.android.packageinstaller:id/uninstall_activity_snippet");
            UiObject cancelBtn = findElementByID("com.android.packageinstaller:id/cancel_button");
            UiObject okBtn = findElementByID("com.android.packageinstaller:id/ok_button");
            String confirmMsg = findElementByID("com.android.packageinstaller:id/uninstall_confirm").getText();
            UiObject appNameObj = findElementByID("com.android.packageinstaller:id/app_name");
            String appName = appNameObj.getText();
            verifyElementPresent("", cancelBtn);
            verifyElementPresent("", okBtn);
            verifyString("", confirmMsg, "要卸载此应用吗？");
            //在卸载弹框上点击“取消”按钮
            cancelBtn.click();
            UiObject menuPopAfterCancel = findElementByID("com.android.packageinstaller:id/uninstall_confirm");
            waitForElementNotPresentByID("com.android.packageinstaller:id/uninstall_confirm");
            verifyElementNotPresent("", menuPopAfterCancel);
            UiObject appList = findElementByID("tv.fun.appstore:id/listview");
            int childCount = appList.getChildCount();
            Boolean flag = false;
            for(int i=0; i<childCount;i++){
                UiObject appCard = device.findObject(new UiSelector().className("android.widget.RelativeLayout").index(i));
                UiObject appNaObj = appCard.getChild(new UiSelector().resourceId("tv.fun.appstore:id/appName"));
                String appNameInList = appNaObj.getText();
                if(appNameInList.equalsIgnoreCase(appName)){
                    flag=true;
                    return;
                }
            }
            verifyTrue("", flag);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can goto App Clean page from Launcher > my app page
     */
    @Test
    public void App_Home_24_testGotoAppCleanPageFromMyApp(){
        try{
            //移动焦点到Launcher应用tab
            moveToLauncherTargetTab(appTab, launcherTabID, 4);
            moveDown();
            moveRightForMultiple(2);
            UiObject myAppObj = findElementByText("我的应用", "com.bestv.ott:id/maintitle");
            myAppObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            moveRight();
            menu();
            waitForElementPresentByID("android:id/tv_fun_menu");

            //断言
            UiObject cleanBtn = findElementByText("清理数据", "android:id/tv_fun_menu_text");
            cleanBtn.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.master:id/clearData");
            UiObject appCleanPageTitle = device.findObject(new UiSelector().resourceId("tv.fun.master:id/title"));
            UiObject uninstallBtn = device.findObject(new UiSelector().resourceId("tv.fun.master:id/uninstall"));
            UiObject cleanDataBtn = device.findObject(new UiSelector().resourceId("tv.fun.master:id/clearData"));
            verifyString("app clean page title is incorrect", appCleanPageTitle.getText(), "应用清理");
            verifyElementPresent(" The uninstall button is not displayed", uninstallBtn);
            verifyString(" The name of uninstall button is displayed incorrectly", uninstallBtn.getText(), "卸载");
            verifyElementPresent(" The clean date button is not displayed", cleanDataBtn);
            verifyString(" The name of clean date is displayed incorrectly", cleanDataBtn.getText(), "清理数据");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can goto My app page from the popup in Launcher Home page
     */
    @Test
    public void App_Home_41_testGotoMyAppFromPopupInLauncher(){
        try{
            //移动焦点到导航条上任一tab
            moveToLauncherTargetTab(videoTab, launcherTabID, 4);
            moveUpForMultiple(2);
            //选中我的应用卡片，并点击
            waitForElementPresentByID("com.bestv.ott:id/app");
            UiObject myAppCardObj = findElementByID("com.bestv.ott:id/app_icon");
            UiObject titleObj = findElementByID("com.bestv.ott:id/app_title");
            String nameOfCard = titleObj.getText();
            verifyString("", nameOfCard, "我的应用");
            myAppCardObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/subTitle");

            //断言
            UiObject myAppTitleObj = findElementByID("tv.fun.appstore:id/title");
            verifyString("", myAppTitleObj.getText(), "我的应用");
            UiObject subTitle = findElementByID("tv.fun.appstore:id/subTitle");
            String subTitleText = subTitle.getText();
            verifyIncludeString("", subTitleText, "个应用");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test App Store UI Displays correctly
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_Home_43_testAppStoreUIDisplay() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //断言
            UiObject tjTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[0]));
            UiObject yxTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[1]));
            UiObject ylTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[2]));
            UiObject shTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[3]));
            UiObject jyTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[4]));
            UiObject appManageTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[5]));
            UiObject searchObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/activity_search_btn"));
            verifyElementPresent("The tuijian tab in AppStore page is not displayed", tjTab);
            verifyElementPresent("The game tab in AppStore page is not displayed", yxTab);
            verifyElementPresent("The yule tab in AppStore page is not displayed", ylTab);
            verifyElementPresent("The shenghuo tab in AppStore page is not displayed", shTab);
            verifyElementPresent("The jiaoyu tab in AppStore page is not displayed", jyTab);
            verifyElementPresent("The search icon in AppStore page is not displayed", searchObj);
            verifyElementPresent("The appManage tab in AppStore page is not displayed", appManageTab);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test the menu popup in appstore page displays correctly
     */
    @Test
    public void App_Home_48_testMenuPopupInAppStoreHome(){
    try{
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        //按遥控器Menu键
        menu();
        //断言
        UiObject searchObj = findElementByText("搜索", "android:id/tv_fun_menu_text");
        verifyElementPresent("", searchObj);
        UiObject allCateObj = findElementByText("全部分类", "android:id/tv_fun_menu_text");
        verifyElementPresent("", allCateObj);
    } catch (Throwable e) {
        e.printStackTrace();
        resultFlag = false;
        resultStr = e.toString();
    } finally {
        Utils.writeCaseResult(resultStr,
                resultFlag, execTime);
    }
    }

    /**
     * Test that can goto search page via menu popup in appstore page
     *
     */
    @Test
    public void App_Home_49_testGotoSearchPageFromAppStoreHome(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //按遥控器Menu键,并点击弹框上搜索按钮
            menu();
            UiObject searchObj = findElementByText("搜索", "android:id/tv_fun_menu_text");
            searchObj.clickAndWaitForNewWindow();
            //断言
            UiObject inputBox = findElementByID("tv.fun.appstore:id/search_input");
            UiObject deletBtn = findElementByID("tv.fun.appstore:id/search_del_key");
            UiObject keyObj = findElementByText("A", "tv.fun.appstore:id/search_single_key");
            UiObject hotSearch = findElementByID("tv.fun.appstore:id/search_result_title");
            UiObject hotResult = findElementByID("tv.fun.appstore:id/search_result_hot");
            verifyElementPresent("", inputBox);
            verifyString("", "请输入应用拼音首字母", inputBox.getText());
            verifyElementPresent("", deletBtn);
            verifyElementPresent("", hotSearch);
            verifyString("", "热门搜索：", hotSearch.getText());
            verifyNumberLarger("", hotResult.getChildCount(), 1);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can goto All category page via menu popup in appstore page
     *
     */
    @Test
    public void App_Home_50_testGotoAllCategoriesPageFromAppStoreHome(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //按遥控器Menu键,并点击弹框上全部分类按钮
            menu();
            UiObject allcateObj = findElementByText("全部分类", "android:id/tv_fun_menu_text");
            allcateObj.clickAndWaitForNewWindow();
            //断言
            UiObject pageTitleObj = findElementByID("android:id/content").getChild(new UiSelector().className("android.widget.TextView"));
            verifyString("", "全部分类", pageTitleObj.getText());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can goto Launcher home page from appstore page via Home
     *
     */
    @Test
    public void App_Home_52_testNavigateLauncherFromAppStoreByHome(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //按遥控器Home键
            home();
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTabObj = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTabObj.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can back to App home page from appstore page via Back
     *
     */
    @Test
    public void App_Home_53_testBackToAppHomeFromAppStore(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //按遥控器Back键
            back();
            waitForElementPresentByID("com.bestv.ott:id/maintitle");
            //断言
            if(!device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(appStoreIconName)).exists()){
                back();
            }
            UiObject appStoreIcon = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(appStoreIconName));
            UiObject tvMasterIcon = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(tvMasterIconName));
            UiObject myAppIcon = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/maintitle").text(myAppIconName));
            verifyElementPresent("应用市场卡片没有显示在Launcher应用页面", appStoreIcon);
            verifyElementPresent("电视助手卡片没有显示在Launcher应用页面", tvMasterIcon);
            verifyElementPresent("我的应用卡片没有显示在Launcher应用页面", myAppIcon);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test the UI of AppStore page displays correctly
     */
    @Test
    public void App_TJ_01_testAppStoreUIDisplay(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToAppStoreTargetTab(appStoreTabs[0]);
            //断言
            UiObject firstTab = findElementByText("推荐", "tv.fun.appstore:id/column_title");
            UiObject hotTopic = findElementByText("热门排行", "tv.fun.appstore:id/title");
            UiObject newestTopic = findElementByText("最新上架", "tv.fun.appstore:id/title");
            UiObject requiredTopic = findElementByText("装机必备", "tv.fun.appstore:id/title");
            UiObject cardListObj = findElementByID("tv.fun.appstore:id/app_pager").getChild(new UiSelector().className("android.widget.FrameLayout"));
            int cardNum = cardListObj.getChildCount();
            UiObject appPaiHObj = findElementByText("应用周排行榜", "tv.fun.appstore:id/cardTitle");
            UiObject gamePaiHObj = findElementByText("游戏周排行榜", "tv.fun.appstore:id/cardTitle");
            UiObject paihbj = cardListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(4));
            UiObject appPaiHTitle = paihbj.getChild(new UiSelector().resourceId("tv.fun.appstore:id/cardTitle"));
            UiObject appPaiHRank = paihbj.getChild(new UiSelector().resourceId("tv.fun.appstore:id/rank"));
            UiObject appTitleInPaiH = paihbj.getChild(new UiSelector().resourceId("tv.fun.appstore:id/title"));
            UiObject appDownloadInPaiH = paihbj.getChild(new UiSelector().resourceId("tv.fun.appstore:id/download_cnt"));
            verifyElementPresent("", firstTab);
            verifyTrue("", firstTab.isSelected());
            verifyElementPresent("", hotTopic);
            verifyElementPresent("", newestTopic);
            verifyElementPresent("", requiredTopic);
            verifyTrue("", cardNum==13);
            verifyElementPresent("", appPaiHObj);
            verifyElementPresent("", gamePaiHObj);
            verifyString("", "应用周排行榜", appPaiHTitle.getText());
            verifyElementPresent("", appPaiHRank);
            verifyElementPresent("", appTitleInPaiH);
            verifyElementPresent("", appDownloadInPaiH);
            verifyTrue("", appDownloadInPaiH.getText().contains("下载量: "));
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test go to hot topic page under suggest tab
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_TJ_07_testGotoHotTopicPage() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToFirstCardUnderSuggestTab();
            UiObject hotTopic = device.findObject(new UiSelector().text("热门排行").resourceId("tv.fun.appstore:id/title"));
            if (!hotTopic.isSelected()) {
                moveToFirstCardUnderSuggestTab();
            }

            //点击推荐tab下热门排行topic
            hotTopic.clickAndWaitForNewWindow();
            Thread.sleep(200);
            UiObject hotTopicImage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/topic_poster"));
            UiObject appListObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/topic_app_list"));
            List<UiObject2> appList = device.findObjects(By.clazz("android.widget.RelativeLayout"));
            int num = appList.size();
            //断言
            verifyElementPresent("The image of hot topic page is not displayed", hotTopicImage);
            verifyElementPresent("The app list in hot topic page is not displayed", appListObj);
            verifyTrue("The count of app listed in hot topic page is not more than 1", num > 1);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test that the hot topic page is not responsed for Menu btn
     */
    @Test
    public void App_TJ_12_testNoMenuActionInHotTopicPage(){
        try{
            //进入应用市场》推荐tab>热门排行Topic
            enterTopicPage(topicsUnderSuggest[0]);
            //按遥控器Menu键，并等待一段时间
            menu();
            Thread.sleep(500);
            verifyElementNotPresent("", "android:id/tv_fun_menu");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test back to Launcher app tab page from hot topic page by Home
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_TJ_14_testBackToLauncherFromHotTopicPage() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToFirstCardUnderSuggestTab();
            UiObject hotTopic = device.findObject(new UiSelector().text("热门排行").resourceId("tv.fun.appstore:id/title"));
            if (!hotTopic.isSelected()) {
                moveToFirstCardUnderSuggestTab();
            }

            //点击推荐tab下热门排行topic
            hotTopic.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/topic_app_list");
            device.pressHome();
            waitForElementNotPresent("tv.fun.appstore:id/topic_app_list");
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTabObj = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTabObj.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test back to App Store app tab page from hot topic page by back btn
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_TJ_15_testBackToAppStoreFromHotTopicPage() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToFirstCardUnderSuggestTab();
            UiObject hotTopic = device.findObject(new UiSelector().text("热门排行").resourceId("tv.fun.appstore:id/title"));
            if (!hotTopic.isSelected()) {
                moveToFirstCardUnderSuggestTab();
            }

            //点击推荐tab下热门排行topic
            hotTopic.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/topic_app_list");
            back();
            waitForElementNotPresent("tv.fun.appstore:id/topic_app_list");
            //断言
            UiObject appManageTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[5]));
            UiObject searchObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/activity_search_btn"));
            verifyElementPresent("The search icon in AppStore page is not displayed", searchObj);
            verifyElementPresent("The appManage tab in AppStore page is not displayed", appManageTab);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test enter back to App detail page from hot topic page
     */
    @Test
    public void App_TJ_16_testEnterAppDetailPageFromHotTopicPage() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToFirstCardUnderSuggestTab();
            UiObject hotTopic = device.findObject(new UiSelector().text("热门排行").resourceId("tv.fun.appstore:id/title"));
            if (!hotTopic.isSelected()) {
                moveToFirstCardUnderSuggestTab();
            }
            //点击推荐tab下热门排行topic
            hotTopic.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/topic_app_list");
            //点击任一应用
            UiObject appListView = findElementByID("tv.fun.appstore:id/topic_app_list");
            UiObject firstAppCard = appListView.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
            String appName = firstAppCard.getChild(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText();
            moveDown();
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
//            firstAppCard.clickAndWaitForNewWindow();//Not support
            waitForAppDetailPageDisplay();
            //Assert
            UiObject pic = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_poster_image"));
            UiObject controlledDevice = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/downloadCount"));
            verifyElementPresent("详情页中海报图没有显示", pic);
            verifyTrue("详情页中下载次数没有显示", controlledDevice.getText().contains("下载："));
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test check app brief info after clicking brief in app detail page from hot topic page
     */
    @Test
    public void App_TJ_18_testCheckAppBriefInfoFromHotTopicPage() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToFirstCardUnderSuggestTab();
            UiObject hotTopic = device.findObject(new UiSelector().text("热门排行").resourceId("tv.fun.appstore:id/title"));
            if (!hotTopic.isSelected()) {
                moveToFirstCardUnderSuggestTab();
            }
            //点击推荐tab下热门排行topic
            hotTopic.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/topic_app_list");
            //点击任一应用
            UiObject appListView = findElementByID("tv.fun.appstore:id/topic_app_list");
            UiObject firstAppCard = appListView.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
            String appName = firstAppCard.getChild(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText();
            moveDown();
            moveLeftForMultiple(2);
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            //firstAppCard.clickAndWaitForNewWindow();//Not support
            waitForAppDetailPageDisplay();
            //Assert
            String appTitleInDetail = findElementByID("tv.fun.appstore:id/title").getText();
            verifyString("", appTitleInDetail, appName);
            UiObject briefObj = findElementByID("tv.fun.appstore:id/brief");
            String textOfBrief = briefObj.getText();
            UiObject posterObj = findElementByID("tv.fun.appstore:id/app_detail_poster_image");
            moveDownForMultiple(4);
            moveUp();
            //点击海报
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            waitForElementPresentByID("tv.fun.appstore:id/snapshot_hd");
            //posterObj.clickAndWaitForNewWindow();//Not Support
            UiObject posterFull = findElementByID("tv.fun.appstore:id/snapshot_hd");
            verifyElementPresent("", posterFull);
            verifyTrue("", posterFull.isEnabled());
            back();
            //点击简介
            briefObj.clickAndWaitForNewWindow();
            UiObject fullPageTitle = findElementByClass("android.widget.TextView");
            String titleOfFullPage = fullPageTitle.getText();
            UiObject detailInfoInFull = findElementByID("tv.fun.appstore:id/app_detail_more_content");
            String textOfInfo = detailInfoInFull.getText();
            verifyIncludeString("", textOfInfo.replace("\n", ""), textOfBrief.replace("\n", ""));
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test that the newest topic page is not responsed for Menu btn
     */
    @Test
    public void App_TJ_21_testNoMenuActionInAppDetailFromNewestTopic(){
        try{
            //进入应用市场》推荐tab>最新上架Topic
            enterTopicPage(topicsUnderSuggest[1]);
            moveRight();
            UiObject appList = findElementByID("tv.fun.appstore:id/topic_app_list");
            UiObject appCard = appList.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
//            appCard.click();
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            waitForAppDetailPageDisplay();
            UiObject pic = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_poster_image"));
            verifyElementPresent("详情页中海报图没有显示", pic);
            //按遥控器Menu键，并等待一段时间
            menu();
            Thread.sleep(500);
            verifyElementNotPresent("", "android:id/tv_fun_menu");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test back to Launcher from app detail in hot topic page
     */
    @Test
    public void App_TJ_23_testBackToLauncherFromAppDetailInHotTopicPage() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToFirstCardUnderSuggestTab();
            UiObject hotTopic = device.findObject(new UiSelector().text("热门排行").resourceId("tv.fun.appstore:id/title"));
            if (!hotTopic.isSelected()) {
                moveToFirstCardUnderSuggestTab();
            }
            //点击推荐tab下热门排行topic
            hotTopic.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/topic_app_list");
            //点击任一应用
            UiObject appListView = findElementByID("tv.fun.appstore:id/topic_app_list");
            UiObject firstAppCard = appListView.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
            String appName = firstAppCard.getChild(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText();
            moveDown();
            moveLeftForMultiple(2);
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            //firstAppCard.clickAndWaitForNewWindow();//not support clicking in here
            waitForAppDetailPageDisplay();
            //按遥控器Home键
            home();
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTabObj = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTabObj.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test back to hot topic page from app detail
     */
    @Test
    public void App_TJ_24_testBackToHotTopicFromAppDetail() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            UiObject hotTopic = device.findObject(new UiSelector().text("热门排行").resourceId("tv.fun.appstore:id/title"));
            if (!device.findObject(new UiSelector().text("热门排行").resourceId("tv.fun.appstore:id/title").selected(true)).exists()) {
                moveToFirstCardUnderSuggestTab();
            }
            //点击推荐tab下热门排行topic
            hotTopic.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/topic_app_list");
            //点击任一应用
            UiObject appListView = findElementByID("tv.fun.appstore:id/topic_app_list");
            UiObject firstAppCard = appListView.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
            String appName = firstAppCard.getChild(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText();
            moveDown();
            moveLeftForMultiple(2);
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            waitForAppDetailPageDisplay();
            //按遥控器Back键
            back();
            UiObject hotTopicImage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/topic_poster"));
            UiObject appListObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/topic_app_list"));
            //断言
            verifyElementPresent("The image of hot topic page is not displayed", hotTopicImage);
            verifyElementPresent("The app list in hot topic page is not displayed", appListObj);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test that the newest topic page is not responsed for Menu btn
     */
    @Test
    public void App_TJ_30_testNoMenuActionInNewestTopicPage(){
        try{
            //进入应用市场》推荐tab>最新上架Topic
            enterTopicPage(topicsUnderSuggest[1]);
            //按遥控器Menu键，并等待一段时间
            menu();
            Thread.sleep(500);
            verifyElementNotPresent("", "android:id/tv_fun_menu");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test Menu pop-up in machine required page
     *
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP2Tests.class)
    @Test
    public void App_TJ_48_testMenuPopUpInMachineRequired() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToTargetTab(appStoreTabs, appStoreTabs[0], appStoreTabID, 0);
            moveDownForMultiple(3);
            UiObject targetTopic = findElementByText("装机必备", "tv.fun.appstore:id/title");
            if (!targetTopic.isSelected()) {
                moveToTopOnAppStoreHomePage();
                moveDownForMultiple(3);
            }
            //点击装机必备卡片
            targetTopic.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/topic_poster");
            moveRight();
            menu();
            waitForElementPresentByID("android:id/tv_fun_menu_icon");
            //Assert
            UiObject btnInPopup = findElementByID("android:id/tv_fun_menu_text");
            String btnName = btnInPopup.getText();
            verifyString("", btnName, "安装全部");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test enter app detail from app store page
     *
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP2Tests.class)
    @Test
    public void App_TJ_64_testEnterAppDetailFromAppStore() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToFirstAppUnderSuggestTab();
            UiObject firstApp = findElementByID("tv.fun.appstore:id/app_pager").getChild(new UiSelector().className("android.widget.RelativeLayout").index(2));
            if (!firstApp.isSelected()) {
                moveToFirstAppUnderSuggestTab();
            }
            //点击应用卡片
            firstApp.clickAndWaitForNewWindow();
            waitForAppDetailPageDisplay();
            //Assert
            UiObject pic = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_poster_image"));
            UiObject controlledDevice = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/downloadCount"));
            verifyElementPresent("详情页中海报图没有显示", pic);
            verifyTrue("详情页中下载次数没有显示", controlledDevice.getText().contains("下载："));
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that install app successfully
     *
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP1Tests.class)
    @Test
    public void App_Game_15_testInstallAppFromAppStore() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToAppStoreTargetTab(appStoreTabs[1]);
            UiObject firstApp = null;
            firstApp = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/image").index(0));
            firstApp.clickAndWaitForNewWindow();
            waitForAppDetailPageDisplay();
            //Assert
            String currentAppName = findElementByID("tv.fun.appstore:id/title").getText();
            if (!device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/titleContainer").text("安装")).exists()) {
                back();
                gotoAppCleanPageFromAppUninstallPage(true);
                uninstallAppFromAppCleanPage(currentAppName);
                back();
                moveToAppStoreTargetTab(appStoreTabs[1]);
                firstApp = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/image").index(0));
                firstApp.clickAndWaitForNewWindow();
                waitForAppDetailPageDisplay();
            }
            //installBtn.click();不能实现点击安装按钮操作，故使用pressEnter
            installAppInDetailPage();
            UiObject openBtn = device.findObject(new UiSelector().className("android.widget.Button"));
            verifyString("详情页中应用未安装成功", openBtn.getText(), "打开");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test can enter all game page by clicking game tab in appstore page
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_Game_23_testEnterAllGameAppPageByTab() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //Move to 游戏 Tab
            moveToAppStoreTargetTab(appStoreTabs[1]);
            UiObject yxTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[1]));
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_view");
            //全部游戏页面显示
            UiObject allyxPageTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_title"));
            String allyxPageTitleStr = allyxPageTitle.getText();
            Assert.assertTrue("The title of all youxi app page is expected" + "【全部游戏】" + ", but actual 【" + allyxPageTitleStr + "】", allyxPageTitleStr.equalsIgnoreCase("全部游戏"));
            moveDown();
            UiObject allPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
            UiObject iconOfallPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_app_icon"));
            verifyElementPresent("The card of youxi list page is not displayed", iconOfallPage);
            UiObject currentApp = allPage.getChild(new UiSelector().resourceId("tv.fun.appstore:id/all_app_title").selected(true));
            String currentAppName = currentApp.getText();
            UiObject t = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_status_prefix"));
            String appInfo = t.getText();
            verifyTrue("The app info is not displayed at the bottom", appInfo.contains(currentAppName));
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test the child App List of game can be filtered by All successfully
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_Game_43_testEnterAllCatogriesPageFromAllGamePage() {
        try {
           //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //Move to 游戏 Tab
            moveToAppStoreTargetTab(appStoreTabs[1]);
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_view");
            //点击Menu
            menu();
            waitForElementPresentByID("android:id/tv_fun_menu_icon");
            UiObject searchBtn = findElementByText("搜索", "android:id/tv_fun_menu_text");
            UiObject allCateBtn = findElementByText("全部分类", "android:id/tv_fun_menu_text");
            verifyElementPresent("", searchBtn);
            verifyElementPresent("", allCateBtn);
            allCateBtn.clickAndWaitForNewWindow();
            UiObject allCategoryTitle = device.findObject(new UiSelector().className("android.widget.TextView").index(0));
            //断言
            verifyString("All Category page title is displayed incorrectly", allCategoryTitle.getText(), "全部分类");
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }



    /**
     * Test the child App List of game can be filtered by All successfully
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_Game_54_testFilterGameChildListByAllAndBack() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToAppStoreTargetTab(appStoreTabs[1]);
            device.pressDPadDown();
            UiObject firstGameChildEnter = findElementByText("动作", "tv.fun.appstore:id/title");
            if (!firstGameChildEnter.isSelected()) {
                moveUpForMultiple(6);
                device.pressDPadRight();
                device.pressDPadLeft();
                device.pressDPadDown();
            }
            //点击游戏tab下第一个子入口，进入列表页
            firstGameChildEnter.clickAndWaitForNewWindow();
            String totalApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
            String startApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
            int startAppNum = stringToInt(startApp);
            int totalAppNum = stringToInt(totalApp);
            verifyTrue("App list is not default to start with NO. 1", startAppNum == 1);

            //点击"手柄"筛选按钮
            UiObject handlel = device.findObject(new UiSelector().text("手柄").className("android.widget.TextView"));
            handlel.clickAndWaitForNewWindow();
            //筛选后,再次获取数据
            String totalAppAfterHandlel = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
            int totalAppNumAfterHandlel = stringToInt(totalAppAfterHandlel);
            Assert.assertTrue("The total app num is not less than default after filtering by Handlel", totalAppNumAfterHandlel <= stringToInt(totalApp));
            UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("android:id/tv_fun_scrollbar"));
            if (scroll.exists()) {
                moveDownForMultiple(3);
                moveRight();
            }
            String startAppAfterScroll = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
            Assert.assertTrue("The start app is not changed after scrolling", stringToInt(startAppAfterScroll) > 1);

            //点击"全部"筛选按钮
            UiObject allFilter = device.findObject(new UiSelector().text("全部").className("android.widget.TextView"));
            allFilter.clickAndWaitForNewWindow();
            //筛选后,再次获取数据
            String totalAppAfterAll = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
            String startAppAfterAll = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
            int totalAppNumAfterAll = stringToInt(totalAppAfterAll);
            int startAppNumAfterAll = stringToInt(startAppAfterAll);
            verifyTrue("The total app num is not more than default after filtering by All", totalAppNumAfterAll == totalAppNum);
            verifyTrue("The start app is not default to 1 after filtering", startAppNumAfterAll == 1);
//        //Check all apps in list have all operation after filter by RC
//        UiObject allAppList = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
//        int allAppNumAfterFilter = allAppList.getChildCount();
//        Assert.assertTrue("The actaul app num is not same as the total num displayed in scrollbar after filtering by All", allAppNumAfterFilter == totalAppNumAfterAll);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test enter child list page of Game
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_Game_53_testEnterGameChildListPageAndBack() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToAppStoreTargetTab(appStoreTabs[1]);
            device.pressDPadDown();
            UiObject firstGameChildEnter = findElementByText("动作", "tv.fun.appstore:id/title");
            if (!firstGameChildEnter.isSelected()) {
                moveUpForMultiple(6);
                device.pressDPadRight();
                device.pressDPadLeft();
                device.pressDPadDown();
            }
            //点击游戏tab下第一个子入口，进入列表页
            firstGameChildEnter.clickAndWaitForNewWindow();

            //断言
            UiObject listPageTitle = findElementByID("tv.fun.appstore:id/all_apps_title");
            UiObject appListView = findElementByID("tv.fun.appstore:id/all_apps_view");
            List<UiObject2> appsList = device.findObjects(By.clazz("android.widget.FrameLayout"));
            int numOfAppList = appsList.size();
            UiObject filterBar = device.findObject(new UiSelector().className("android.widget.LinearLayout").index(2));
            int childCount = filterBar.getChildCount();
            String firstFilter = filterBar.getChild(new UiSelector().className("android.widget.TextView").index(0)).getText();
            String secFilter = filterBar.getChild(new UiSelector().className("android.widget.TextView").index(1)).getText();
            String thirdFilter = filterBar.getChild(new UiSelector().className("android.widget.TextView").index(2)).getText();
            verifyString("Title of the first child enter is incorrect", listPageTitle.getText(), "动作");
            verifyElementPresent("App List page is not displayed", appListView);
            verifyTrue("App displayed in List page is not more than 1", numOfAppList > 1);
            verifyElementPresent("Filter bar is not displayed in list page", filterBar);
            verifyString("Filter option is displayed incorrectly in list page", firstFilter, "全部");
            verifyString("Filter option is displayed incorrectly in list page", secFilter, "遥控器");
            verifyString("Filter option is displayed incorrectly in list page", thirdFilter, "手柄");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test Filter Child list of Game by Remote Control
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_Game_54_testFilterGameChildListByRemoteControlAndBack() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToAppStoreTargetTab(appStoreTabs[1]);
            device.pressDPadDown();
            UiObject firstGameChildEnter = findElementByText("动作", "tv.fun.appstore:id/title");
            if (!firstGameChildEnter.isSelected()) {
                moveUpForMultiple(6);
                moveRight();
                moveLeft();
                moveDown();
            }
            //点击游戏tab下第一个子入口，进入列表页
            firstGameChildEnter.clickAndWaitForNewWindow();
            String str = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText();
            String totalApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
            String startApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
            int startAppNum = stringToInt(startApp);
            Assert.assertTrue("App list is not default to start with NO. 1", startAppNum == 1);
            moveDownForMultiple(3);
            moveRight();
            String startAppAfterScroll = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
            verifyTrue("The start app is not changed after scrolling", stringToInt(startAppAfterScroll) > 1);
            //点击"遥控器"筛选按钮
            UiObject remoteControl = device.findObject(new UiSelector().text("遥控器").className("android.widget.TextView"));
            remoteControl.clickAndWaitForNewWindow();
            //筛选后,再次获取数据
            String totalAppAfterRC = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
            String startAppAfterRC = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
            int totalAppNumAfterRC = stringToInt(totalAppAfterRC);
            int startAppNumAfterRC = stringToInt(startAppAfterRC);
            verifyTrue("The start app is not changed to default after filtering", startAppNumAfterRC == 1);
            verifyTrue("The total app num is less than default after filtering by RC", totalAppNumAfterRC <= stringToInt(totalApp));
//        //Check all apps in list have remote control operation after filter by RC
//        UiObject rcAppList = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
//        int rcAppNum = rcAppList.getChildCount();
//        Assert.assertTrue("The actaul app num is not same as the total num displayed in scrollbar after filtering by RC", rcAppNum == totalAppNumAfterRC);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test filter child list of game by handle
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_Game_54_testFilterGameChildListByHandleAndBack() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToAppStoreTargetTab(appStoreTabs[1]);
            moveDown();
            UiObject firstGameChildEnter = findElementByText("动作", "tv.fun.appstore:id/title");
            if (!firstGameChildEnter.isSelected()) {
                moveUpForMultiple(6);
                device.pressDPadRight();
                device.pressDPadLeft();
                device.pressDPadDown();
            }
            //点击游戏tab下第一个子入口，进入列表页
            firstGameChildEnter.clickAndWaitForNewWindow();
            String totalApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
            String startApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
            int startAppNum = stringToInt(startApp);
            verifyTrue("App list is not default to start with NO. 1", startAppNum == 1);
            moveDownForMultiple(3);
            moveRight();
            String startAppAfterScroll = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
            verifyTrue("The start app is not changed after scrolling", stringToInt(startAppAfterScroll) > 1);
            //点击"手柄"筛选按钮
            UiObject handlel = device.findObject(new UiSelector().text("手柄").className("android.widget.TextView"));
            handlel.clickAndWaitForNewWindow();
            //筛选后,再次获取数据
            String totalAppAfterHandlel = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
            String startAppAfterHandlel = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[0];
            int totalAppNumAfterHandlel = stringToInt(totalAppAfterHandlel);
            int startAppNumAfterHandlel = stringToInt(startAppAfterHandlel);
            verifyTrue("The start app is not changed to default after filtering", startAppNumAfterHandlel == 1);
            verifyTrue("The total app num is less than default after filtering by Handlel", totalAppNumAfterHandlel <= stringToInt(totalApp));
//        //Check all apps in list have remote control operation after filter by RC
//        UiObject handlelAppList = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
//        int handlelAppNum = handlelAppList.getChildCount();
//        Assert.assertTrue("The actaul app num is not same as the total num displayed in scrollbar after filtering by RC", handlelAppNum == totalAppNumAfterHandlel);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that the default filter is correct of Action list page
     *
     */
    @Test
    public void App_Game_55_testDefaultFilterInActionListPage() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToAppStoreTargetTab(appStoreTabs[1]);
            moveDown();
            UiObject firstGameChildEnter = findElementByText("动作", "tv.fun.appstore:id/title");
            if (!firstGameChildEnter.isSelected()) {
                moveUpForMultiple(6);
                device.pressDPadRight();
                device.pressDPadLeft();
                device.pressDPadDown();
            }
            //点击游戏tab下第一个子入口，进入列表页
            firstGameChildEnter.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_view");
            UiObject allFilter = device.findObject(new UiSelector().text("全部").className("android.widget.TextView"));
            UiObject handlel = device.findObject(new UiSelector().text("手柄").className("android.widget.TextView"));
            UiObject remoteControl = device.findObject(new UiSelector().text("遥控器").className("android.widget.TextView"));
            verifyTrue("", allFilter.isSelected());
            verifyFalse("", handlel.isSelected());
            verifyFalse("", remoteControl.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can enter all Entertainment Page By Tab.
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_YL_17_testEnterAllEntertainmentPageByTab() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //Move to Entertainment Tab
            moveToAppStoreTargetTab(appStoreTabs[2]);
            UiObject ylTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[2]));
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
//        ylTab.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
            waitForElementPresentByID("android:id/tv_fun_scrollbar");
            UiObject allylPageTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_title"));
            String allylPageTitleStr = allylPageTitle.getText();
            verifyString("The title of all yl app page is expected" + "【全部娱乐】" + ", but actual 【" + allylPageTitleStr + "】", allylPageTitleStr, "全部娱乐");
            UiObject allPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
            verifyElementPresent("The yule list page is not displayed", allPage);
            moveDown();
            UiObject currentApp = allPage.getChild(new UiSelector().resourceId("tv.fun.appstore:id/all_app_title").selected(true));
            String currentAppName = currentApp.getText();
            String appInfo = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_status_prefix")).getText();
            verifyTrue("The app info is not displayed at the bottom", appInfo.contains(currentAppName));
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can enter all Entertainment Page By Card
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_YL_17_testEnterAllEntertainmentPageByCard() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToAppStoreTargetTab(appStoreTabs[2]);
            //移动到全部娱乐 卡片
            moveDownForMultiple(2);
            UiObject allylCard = device.findObject(new UiSelector().className("android.widget.RelativeLayout").index(7));
            //点击“全部娱乐”卡片，进入全部娱乐列表页
            allylCard.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_view");
            //断言
            UiObject allylPageTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_title"));
            String allylPageTitleStr = allylPageTitle.getText();
            Assert.assertTrue("The title of all yl app page is expected" + "【全部娱乐】" + ", but actual 【" + allylPageTitleStr + "】", allylPageTitleStr.equalsIgnoreCase("全部娱乐"));
            UiObject allPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
            UiObject iconOfallPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_app_icon"));
            verifyElementPresent("The card of yule list page is not displayed", iconOfallPage);
            moveDown();
            if (!allPage.getChild(new UiSelector().resourceId("tv.fun.appstore:id/all_app_title").selected(true)).exists()) {
                moveRight();
                moveUpForMultiple(2);
                moveDown();
            }
            UiObject currentApp = allPage.getChild(new UiSelector().resourceId("tv.fun.appstore:id/all_app_title").selected(true));
            String currentAppName = currentApp.getText();
            String appInfo = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_status_prefix")).getText();
            verifyTrue("The app info is not displayed at the bottom", appInfo.contains(currentAppName));
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that all life page can be entered by clicking the tab
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_SH_19_testEnterAllLifePageByTab() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //Move to 生活 Tab
            moveToAppStoreTargetTab(appStoreTabs[3]);
            //点击"生活"tab，进入全部生活页面
            UiObject shTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[3]));
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_view");
//        shTab.clickAndWaitForNewWindow();//Not support click action
            //断言
            UiObject allshPageTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_title"));
            String allshPageTitleStr = allshPageTitle.getText();
            verifyString("The title of all shenghuo app page is expected" + "【全部生活】" + ", but actual 【" + allshPageTitleStr + "】", allshPageTitleStr, "全部生活");
            UiObject allPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
            UiObject iconOfallPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_app_icon"));
            verifyElementPresent("The card of shenghuo list page is not displayed", iconOfallPage);
            moveDown();
            UiObject currentApp = allPage.getChild(new UiSelector().resourceId("tv.fun.appstore:id/all_app_title").selected(true));
            String currentAppName = currentApp.getText();
            String appInfo = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_status_prefix")).getText();
            verifyTrue("The app info is not displayed at the bottom", appInfo.contains(currentAppName));
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can enter into All Education Page by clicking 教育 tab
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_JY_20_testEnterAllEducationPageByTab() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //Move to 教育 Tab
            moveToAppStoreTargetTab(appStoreTabs[4]);
            UiObject jyTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[4]));
            // jyTab.clickAndWaitForNewWindow();//Not support to click the tab
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_view");
            //全部教育页面显示
            UiObject alljyPageTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_title"));
            String alljyPageTitleStr = alljyPageTitle.getText();
            Assert.assertTrue("The title of all jiaoyu app page is expected" + "【全部教育】" + ", but actual 【" + alljyPageTitleStr + "】", alljyPageTitleStr.equalsIgnoreCase("全部教育"));
            moveDown();
            UiObject allPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
            UiObject iconOfallPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_app_icon"));
            verifyElementPresent("The card of jiaoyu list page is not displayed", iconOfallPage);
            UiObject currentApp = allPage.getChild(new UiSelector().resourceId("tv.fun.appstore:id/all_app_title").selected(true));
            String currentAppName = currentApp.getText();
            UiObject t = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_status_prefix"));
            String appInfo = t.getText();
            verifyTrue("The app info is not displayed at the bottom", appInfo.contains(currentAppName));
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     *Test that the short introduce info of app in JY child list page displays correctly,same as the info in detail page
     */
    @Test
    public void App_JY_28_testAppShortIntroduceInfo(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //Move to 教育 Tab
            moveToAppStoreTargetTab(appStoreTabs[4]);
            UiObject jyTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[4]));
            // jyTab.clickAndWaitForNewWindow();//Not support to click the tab
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_view");
            //全部教育页面,点击一应用进入详情页
            moveRight();
            UiObject appTitleObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_app_title").selected(true));
            String appName = appTitleObj.getText();
            UiObject appShortInfoOne = findElementByID("tv.fun.appstore:id/all_apps_status_prefix");//滑雪大冒险　|　18万次下载　|
            String appShortInfo = appShortInfoOne.getText();
            appTitleObj.clickAndWaitForNewWindow();
            waitForAppDetailPageDisplay();
            //断言
            UiObject actualAppNameObj = findElementByID("tv.fun.appstore:id/title");
            String actualAppName = actualAppNameObj.getText();
            UiObject downloadCountObj = findElementByID("tv.fun.appstore:id/downloadCount");
            String downloadCount = downloadCountObj.getText().replace("下载：", "");//下载：18万次
            verifyString("", appName, actualAppName);
            verifyString("", appShortInfo, actualAppName+"　|　" + downloadCount+"下载　|");
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     *Test that the Menu popup displays corrctly in all JY page
     */
    @Test
    public void App_JY_31_testMenuPopUpInAllJYPage(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //Move to 教育 Tab
            moveToAppStoreTargetTab(appStoreTabs[4]);
            UiObject jyTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[4]));
            // jyTab.clickAndWaitForNewWindow();//Not support to click the tab
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_view");
            //全部教育页面,按遥控器Menu键
            menu();
            waitForElementPresentByID("android:id/tv_fun_menu");
            //断言
            UiObject menuObj = findElementByID("android:id/tv_fun_menu");
            UiObject firstObj = menuObj.getChild(new UiSelector().className("android.widget.LinearLayout").index(0)).getChild(new UiSelector().resourceId("android:id/tv_fun_menu_text"));
            String firstObjText = firstObj.getText();
            UiObject secObj = menuObj.getChild(new UiSelector().className("android.widget.LinearLayout").index(1)).getChild(new UiSelector().resourceId("android:id/tv_fun_menu_text"));
            String secObjText = secObj.getText();
            verifyElementPresent("", firstObj);
            verifyElementPresent("", secObj);
            verifyString("", firstObjText, "搜索");
            verifyString("", secObjText, "全部分类");
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     *Test that can back to Launcher from all JY page by pressing "Home" btn
     */
    @Test
    public void App_JY_33_testBackToLauncherFromAllJYPageByHome(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //Move to 教育 Tab
            moveToAppStoreTargetTab(appStoreTabs[4]);
            UiObject jyTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[4]));
            // jyTab.clickAndWaitForNewWindow();//Not support to click the tab
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_view");
            //全部教育页面,按遥控器Home键
            home();
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTabObj = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTabObj.isSelected());
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     *Test that can back to App Store home page from all JY page by pressing "Back" btn
     */
    @Test
    public void App_JY_34_testBackToAppStorePageFromAllJYPageByBack(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //Move to 教育 Tab
            moveToAppStoreTargetTab(appStoreTabs[4]);
            UiObject jyTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[4]));
            // jyTab.clickAndWaitForNewWindow();//Not support to click the tab
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_view");
            //全部教育页面,按遥控器Back键
            back();
            //断言
            UiObject appManageTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[5]));
            UiObject searchObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/activity_search_btn"));
            verifyElementPresent("The search icon in AppStore page is not displayed", searchObj);
            verifyElementPresent("The appManage tab in AppStore page is not displayed", appManageTab);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     *Test that can go to app search page from all JY page by pressing "Menu" btn
     */
    @Test
    public void App_JY_35_testGotoSearchPageFromAllJYPageByMenu(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //Move to 教育 Tab
            moveToAppStoreTargetTab(appStoreTabs[4]);
            UiObject jyTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[4]));
            // jyTab.clickAndWaitForNewWindow();//Not support to click the tab
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_view");
            //全部教育页面,按遥控器Menu键
            menu();
            waitForElementPresentByID("android:id/tv_fun_menu");
            UiObject searchBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("搜索"));
            searchBtn.clickAndWaitForNewWindow();
            //断言
            UiObject inputBox = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_input"));
            UiObject delBtn = findElementByID("tv.fun.appstore:id/search_del_key");
            verifyElementPresent("搜索页面中输入框未显示", inputBox);
            verifyElementPresent("搜索页面中删除按钮未显示", delBtn);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     *Test that can go to All category page from all JY page by pressing "Menu" btn
     */
    @Test
    public void App_JY_36_testGotoAllCategoryPageFromAllJYPageByMenu(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //Move to 教育 Tab
            moveToAppStoreTargetTab(appStoreTabs[4]);
            UiObject jyTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[4]));
            // jyTab.clickAndWaitForNewWindow();//Not support to click the tab
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_view");
            //全部教育页面,按遥控器Menu键
            menu();
            waitForElementPresentByID("android:id/tv_fun_menu");
            UiObject allCategoryBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("全部分类"));
            allCategoryBtn.clickAndWaitForNewWindow();
            //断言
            UiObject pageTitleObj = findElementByID("android:id/content").getChild(new UiSelector().className("android.widget.TextView"));
            verifyString("", "全部分类", pageTitleObj.getText());
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test the UI of App Store search page displayed correctly
     *
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP1Tests.class)
    @Test
    public void App_Search_01_testSearchAppUIDisplay() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            menu();
            UiObject searchIcon = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("搜索"));
            searchIcon.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/search_single_key");
            UiObject inputBox = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_input"));
            UiObject delBtn = findElementByID("tv.fun.appstore:id/search_del_key");
            //Assert
            verifyElementPresent("搜索页面中输入框未显示", inputBox);
            verifyElementPresent("搜索页面中删除按钮未显示", delBtn);
            UiObject keyboardInSearch = findElementByID("tv.fun.appstore:id/keyboard");
            int eleCount = keyboardInSearch.getChildCount();
            for (int i = 2; i < eleCount; i++) {
                if (i == eleCount - 1) {
                    String msgText = keyboardInSearch.getChild(new UiSelector().className("android.widget.TextView").index(eleCount - 1)).getText();
                    verifyTrue("The indicate message behind search keyboard is incorrect", msgText.equalsIgnoreCase("首字母搜索，例如\"贝瓦儿歌\"输入BWEG"));
                } else {
                    UiObject key = keyboardInSearch.getChild(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").index(i));
                    String keyWord = key.getText();
                    verifyTrue("The key displayed in search keyboard is incorrect", keyWord.equalsIgnoreCase(textInSearchWindow[i - 2]));
                }
            }
            String resultInitTitle = findElementByID("tv.fun.appstore:id/search_result_title").getText();
            verifyTrue("The title of suggest area in search page is displayed incorrectly", resultInitTitle.equalsIgnoreCase("热门搜索："));
            UiObject suggestApp = findElementByID("tv.fun.appstore:id/app_search_reulst_cell").getChild(new UiSelector().resourceId("tv.fun.appstore:id/container").index(0));
            verifyElementPresent("The suggest app in search page is not displayed", suggestApp);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test search app by keyword in app store
     *
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP1Tests.class)
    @Test
    public void App_Search_02_testSearchAppInAppStore() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            menu();
            waitForElementPresentByID("android:id/tv_fun_menu_icon");
            UiObject searchIcon = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("搜索"));
            searchIcon.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/search_single_key");
            //Input search key and search
            device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").text("B")).click();
            UiObject inputBox = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_input"));
            String inputText = inputBox.getText();
            Assert.assertTrue("The entered keyword displayed in search input box is expected [" + "B" + "], but actual is [" + inputText + "]", inputText.equalsIgnoreCase("B"));
            String resultTitle = findElementByID("tv.fun.appstore:id/search_result_title").getText();
            int resultCount = stringToInt(findElementByID("tv.fun.appstore:id/search_result_count").getText());

            //Get the actual app num displayed in result list
            UiObject appInResult = findElementByID("tv.fun.appstore:id/search_result_noraml").getChild(new UiSelector().resourceId("tv.fun.appstore:id/app_search_reulst_cell").index(0));
            //Assert
            verifyTrue("搜索页面中输入关键字搜索无搜索结果显示", resultCount > 1);
            verifyElementPresent("搜索页面中无搜索结果显示", appInResult);
            verifyTrue("搜索页面中搜索结果title显示错误", resultTitle.equalsIgnoreCase("搜索结果："));
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test that the UI of no search result page displays correctly in app store
     *
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP1Tests.class)
    @Test
    public void App_Search_03_testNoSearchResultPageInAppStore() {
        String[] keywordForNoResult = {"H", "I"};
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            menu();
            waitForElementPresentByID("android:id/tv_fun_menu_icon");
            UiObject searchIcon = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("搜索"));
            searchIcon.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/search_single_key");
            //Input search key and search
            for(int i=0; i< keywordForNoResult.length; i++){
                device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").text(keywordForNoResult[i])).click();
                Thread.sleep(500);
            }
            waitForElementPresentByID("tv.fun.appstore:id/search_no_result");
            //Assert
            UiObject noResultObj = findElementByID("tv.fun.appstore:id/search_no_result");
            String noReusltStr = noResultObj.getText();
            verifyElementPresent("", noResultObj);
            verifyString("", noReusltStr, "暂无匹配结果！");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test that can install app successfully which from search result
     *
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP1Tests.class)
    @Test
    public void App_Search_06_testInstallAppInSearchResult() {
        String[] keywordForNoResult = {"H"};
        String firstAppName = "";
        UiObject searchIcon = null;
        UiObject appObj = null;
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            navigateToSearchPageByMenuPopUp();
            //Search app by keyword
            searchAppByKeyword(keywordForNoResult);
            appObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_title").selected(true));
            String appName = appObj.getText();
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            waitForAppDetailPageDisplay();
            if (findElementByClass("android.widget.Button").exists()) {
                if ("打开".equalsIgnoreCase(findElementByClass("android.widget.Button").getText())) {
                    backForMultiple(2);
                    moveToAppStoreTargetTab(appStoreTabs[5]);
                    moveDown();
                    UiObject myAppCard = device.findObject(new UiSelector().text("我的应用").resourceId("tv.fun.appstore:id/tool_local"));
                    myAppCard.clickAndWaitForNewWindow();
                    waitForElementPresentByID("tv.fun.appstore:id/title");
                    moveRight();
                    menu();
                    waitForElementPresentByID("android:id/tv_fun_menu");
                    //点击卸载按钮
                    UiObject uninstallBtn = findElementByText("卸载", "android:id/tv_fun_menu_text");
                    uninstallBtn.clickAndWaitForNewWindow();
                    waitForElementPresentByID("android:id/tv_fun_menu_text");
                    //在卸载弹框上，点击确定按钮
                    UiObject okBtn = findElementByID("com.android.packageinstaller:id/ok_button");
                    okBtn.click();
                    waitForTextNotPresent("卸载完成");
                    back();
                    navigateToSearchPageByMenuPopUp();
                    //Search app by keyword
                    searchAppByKeyword(keywordForNoResult);
                    appObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_title").selected(true));
                    device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
                    waitForAppDetailPageDisplay();
                }
            }
            installAppInDetailPage();
            //Assert
            String appStatusBtn = findElementByClass("android.widget.Button").getText();
            verifyString("", appStatusBtn, "打开");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test that can install app successfully which from search result
     *
     * @throws RemoteException
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP1Tests.class)
    @Test
    public void App_Search_07_testCannotSearchOutAppInBackList() {
        String[] keywordForAppInBlackList = {"K", "K"};
        String appName = "K客";
        String firstAppName = "";
        UiObject searchIcon = null;
        UiObject appObj = null;
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            navigateToSearchPageByMenuPopUp();
            //Search app by keyword
            searchAppByKeyword(keywordForAppInBlackList);
            waitForElementPresentByID("tv.fun.appstore:id/search_no_result");
            //Assert
            if(findElementByID("tv.fun.appstore:id/search_no_result").exists()) {
                UiObject noResultObj = findElementByID("tv.fun.appstore:id/search_no_result");
                String noReusltStr = noResultObj.getText();
                verifyElementPresent("", noResultObj);
                verifyString("", noReusltStr, "暂无匹配结果！");
            }else{
                UiObject appList = findElementByID("tv.fun.appstore:id/app_search_reulst_cell");
                int appNum = appList.getChildCount();
                int totalAppNum = stringToInt(findElementByID("tv.fun.appstore:id/search_result_count").getText());//40
                for(int k=0; k<totalAppNum; k++){
                    UiObject firstAppInLineObj = appList.getChild(new UiSelector().className("android.widget.RelativeLayout").index(k));
                    UiObject appNameInLineObj = firstAppInLineObj.getChild(new UiSelector().resourceId("tv.fun.appstore:id/app_title"));
                    String firstAppNameInLine = appNameInLineObj.getText();
                    verifyNotString("", firstAppNameInLine, appName);
                    if(totalAppNum>=2) {
                        UiObject secAppInLineObj = appList.getChild(new UiSelector().className("android.widget.RelativeLayout").index(k + 1));
                        UiObject secAppNameInLineObj = secAppInLineObj.getChild(new UiSelector().resourceId("tv.fun.appstore:id/app_title"));
                        String secAppNameInLine = secAppNameInLineObj.getText();
                        verifyNotString("", secAppNameInLine, appName);
                    }
                    k=k+1;
                    moveDown();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that the corn of the installed app displays correctly in search result list
     */
    @Test
    public void App_Search_08_testInstalledAppCornDisplay(){
        String[] keywordForResult = {"H"};
        UiObject firstAppInLineObj = null;
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            navigateToSearchPageByMenuPopUp();
            //Search app by keyword
            searchAppByKeyword(keywordForResult);
            UiObject appList = findElementByID("tv.fun.appstore:id/search_result_noraml").getChild(new UiSelector().resourceId("tv.fun.appstore:id/app_search_reulst_cell").index(0));
            firstAppInLineObj = appList.getChild(new UiSelector().className("android.widget.RelativeLayout"));
            UiObject appNameInLineObj = firstAppInLineObj.getChild(new UiSelector().resourceId("tv.fun.appstore:id/app_title"));
            if(!appNameInLineObj.isSelected()){
                moveUpForMultiple(6);
                if(findElementByID("tv.fun.appstore:id/search_del_key").isSelected()){
                    moveRight();
                }else{
                    moveLeft();
                }
            }
            String appName = appNameInLineObj.getText();
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
            waitForAppDetailPageDisplay();
            if (findElementByClass("android.widget.Button").exists()) {
                if (!"打开".equalsIgnoreCase(findElementByClass("android.widget.Button").getText())) {
                    installAppInDetailPage();
                }
                back();
            }
            //Assert
            firstAppInLineObj = appList.getChild(new UiSelector().className("android.widget.RelativeLayout"));
            int childCount = firstAppInLineObj.getChildCount();
            UiObject appCorn = firstAppInLineObj.getChild(new UiSelector().resourceId("tv.fun.appstore:id/badge"));
            verifyTrue("", appCorn.isEnabled());
            verifyNumber("", childCount, 4);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that no repsonse when pressing Menu btn in app search page
     */
    @Test
    public void App_Search_09_testNoResponseForMenuInSearchPage(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            navigateToSearchPageByMenuPopUp();
            //按遥控器Menu键
            menu();
            //断言
            verifyElementNotPresent("", "android:id/tv_fun_menu_text");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can Back to Launcher home page from App Store Search page By home
     */
    @Test
    public void App_Search_11_testBackToLauncherHomeFromSearch(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            navigateToSearchPageByMenuPopUp();
            //按遥控器Home键
            home();
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTabObj = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTabObj.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can Back to App Store home page from App Search page By back btn
     */
    @Test
    public void App_Search_12_testBackToAppStoreHomeFroSearchPage(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            navigateToSearchPageByMenuPopUp();
            //按遥控器Back键
            back();
            //断言
            UiObject appManageTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[5]));
            UiObject searchObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/activity_search_btn"));
            verifyElementPresent("The search icon in AppStore page is not displayed", searchObj);
            verifyElementPresent("The appManage tab in AppStore page is not displayed", appManageTab);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that all categories page displays correctly.
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_All_01_testAllCategoriesPageDisplay() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
            enterAppStorePage();
            //进入全部分类
            menu();
            waitForElementPresentByID("android:id/tv_fun_menu_icon");
            UiObject allCategoryIcon = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("全部分类"));
            allCategoryIcon.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            UiObject allCategoryTitle = device.findObject(new UiSelector().className("android.widget.TextView").index(0));
            //断言
            verifyString("All Category page title is displayed incorrectly", allCategoryTitle.getText(), "全部分类");
            UiObject gameTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title").text(appStoreTabs[1]));
            UiObject ylTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title").text(appStoreTabs[2]));
            UiObject shTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title").text(appStoreTabs[3]));
            UiObject jyTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title").text(appStoreTabs[4]));
            verifyElementPresent("Game title is not displayed in all category page", gameTitle);
            verifyElementPresent("YL title is not displayed in all category page", ylTitle);
            verifyElementPresent("Life title is not displayed in all category page", shTitle);
            verifyElementPresent("Education title is not displayed in all category page", jyTitle);
            UiObject gameChildArea = device.findObject(new UiSelector().className("android.widget.LinearLayout").index(0));
            int gameChildCount = gameChildArea.getChild(new UiSelector().resourceId("tv.fun.appstore:id/grid")).getChildCount();
            verifyTrue("The count of game child enter is not more than 1", gameChildCount > 1);
            UiObject ylChildArea = device.findObject(new UiSelector().className("android.widget.LinearLayout").index(1));
            int ylChildCount = gameChildArea.getChild(new UiSelector().resourceId("tv.fun.appstore:id/grid")).getChildCount();
            verifyTrue("The count of yule child enter is not more than 1", ylChildCount > 1);
            UiObject shChildArea = device.findObject(new UiSelector().className("android.widget.LinearLayout").index(2));
            int shChildCount = gameChildArea.getChild(new UiSelector().resourceId("tv.fun.appstore:id/grid")).getChildCount();
            verifyTrue("The count of shenghuo child enter is not more than 1", shChildCount > 1);
            UiObject jyChildArea = device.findObject(new UiSelector().className("android.widget.LinearLayout").index(3));
            int jyChildCount = gameChildArea.getChild(new UiSelector().resourceId("tv.fun.appstore:id/grid")).getChildCount();
            verifyTrue("The count of jiaoyu child enter is not more than 1", jyChildCount > 1);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can enter child list from all category page
     *
     * @throws UiObjectNotFoundException
     * @throws RemoteException
     * @throws InterruptedException
     */
    @Category(LevelP1Tests.class)
    @Test
    public void App_All_02_testEnterChildListFromAllCategories() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            menu();
            waitForElementPresentByID("android:id/tv_fun_menu_icon");
            UiObject allCategoryIcon = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("全部分类"));
            allCategoryIcon.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            //点击第一个子入口
            UiObject shChildEnter = findElementByID("tv.fun.appstore:id/grid").getChild(new UiSelector().resourceId("tv.fun.appstore:id/title"));
            String childEnterName = shChildEnter.getText();
            shChildEnter.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
            //获取子列表页面元素
            UiObject title = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_title"));
            String actualPageTitle = title.getText();
            UiObject appList = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_view"));
            UiObject scrollBar = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar"));
            String actualAppC = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
            int actualAppCount = stringToInt(actualAppC);
            //Assert
            verifyString("The title of Child List page is expected [" + childEnterName + "], but actual is [" + actualPageTitle + "]", actualPageTitle, childEnterName);
            verifyElementPresent("App List page is Not displayed.", appList);
            verifyElementPresent("ScrollBar is Not displayed.", scrollBar);
            verifyTrue("There are more than one app in list: " + actualAppCount, actualAppCount > 1);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test enter yule child list page after filtering game app by remote control
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_All_04_testPageAfterFilterGameByRemoteControl() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //移动焦点到导航条 > 应用管理tab
            moveToAppStoreTargetTab(appStoreTabs[2]);
            menu();
            waitForElementPresentByID("android:id/tv_fun_menu_icon");
            UiObject allCateBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("全部分类"));
            allCateBtn.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            //找到第二块 娱乐 区域的子入口
            UiObject obj = findElementByID("tv.fun.appstore:id/container").getChild(new UiSelector().className("android.widget.LinearLayout").index(1));
            UiObject childEnter = obj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0)).getChild(new UiSelector().resourceId("tv.fun.appstore:id/title"));
            //点击娱乐下第一个子入口
            String childEnterName = childEnter.getText();
            childEnter.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
            String totalYLChildApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
            int totalYLChildAppNum = stringToInt(totalYLChildApp);

            //通过全部分类页面，进入游戏子入口列表页
            menu();
            device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("全部分类")).clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            waitForElementPresentByID("tv.fun.appstore:id/container");
            UiObject yxObj = findElementByID("tv.fun.appstore:id/container").getChild(new UiSelector().className("android.widget.LinearLayout").index(0));
            //点击游戏下第一个子入口
            UiObject yxChildEnter = yxObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
            String yxChildEnterName = yxChildEnter.getChild(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText();
            yxChildEnter.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
            //点击"遥控器"筛选按钮
            UiObject reControl = device.findObject(new UiSelector().text("遥控器").className("android.widget.TextView"));
            reControl.clickAndWaitForNewWindow();
            //再次按Menu键，通过全部分类页面，进入娱乐子入口列表页
            menu();
            device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("全部分类")).clickAndWaitForNewWindow();
            UiObject ylCildEnterObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title").text(childEnterName));
            ylCildEnterObj.clickAndWaitForNewWindow();
            String actualTotalYLChildApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
            int actualTotalYLChildAppNum = stringToInt(actualTotalYLChildApp);
            //断言
            verifyTrue("Goto yule child list page After filtering by Handle, the app num is expected [" + totalYLChildAppNum + "], but actual is [" + actualTotalYLChildAppNum + "]", actualTotalYLChildAppNum == totalYLChildAppNum);
            String pageTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/all_apps_title")).getText();
            verifyString("List page title is expected [" + childEnterName + "], but actual is [" + pageTitle + "]", pageTitle, childEnterName);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that the child list of life is displayed correctly when entering child list page from all category page after filtering game by handle in one game child list page
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_All_05_testEnterLifeChildListPageAfterFilterGameByHandle() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            moveToAppStoreTargetTab(appStoreTabs[1]);
            moveDown();
            UiObject firstGameChildEnter = findElementByText("动作", "tv.fun.appstore:id/title");
            if (!firstGameChildEnter.isSelected()) {
                moveUpForMultiple(6);
                moveRight();
                moveLeft();
                moveDown();
            }
            //点击游戏tab下第一个子入口，进入列表页
            firstGameChildEnter.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/all_apps_title");
            String totalApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
            //点击"手柄"筛选按钮
            UiObject handlel = device.findObject(new UiSelector().text("手柄").className("android.widget.TextView"));
            handlel.clickAndWaitForNewWindow();
            //筛选后,再次获取数据
            String totalAppAfterHandlel = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
            int totalAppNumAfterHandlel = stringToInt(totalAppAfterHandlel);
            Boolean handlelStatus = device.findObject(new UiSelector().text("手柄").className("android.widget.TextView")).isSelected();
            Assert.assertTrue("The total app num is not less than default after filtering by Handlel", totalAppNumAfterHandlel <= stringToInt(totalApp));
            Assert.assertTrue("The handle button is not selected", handlelStatus);
            //按遥控器Menu键，点击"全部分类"
            menu();
            UiObject allCateBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("全部分类"));
            allCateBtn.clickAndWaitForNewWindow();

            //点击生活下第一个子入口
            UiObject obj = findElementByID("tv.fun.appstore:id/container").getChild(new UiSelector().className("android.widget.LinearLayout").index(2));
            UiObject shChildEnter = obj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
            String childEnterName = shChildEnter.getChild(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText();
            shChildEnter.clickAndWaitForNewWindow();
            String totalSHChildApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
            int totalSHChildAppNum = stringToInt(totalSHChildApp);

            //返回应用市场首页,并点击生活tab下
            back();
            
            moveToAppStoreTargetTab(appStoreTabs[3]);
            moveDown();
            UiObject childEnter = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title").text(childEnterName));
            childEnter.clickAndWaitForNewWindow();
            String expectedAppList = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
            int expectedAppNum = stringToInt(expectedAppList);
            verifyTrue("The app num in sh child enter page is expected [" + expectedAppNum + "], but actaul [" + totalSHChildAppNum + "]", totalSHChildAppNum == expectedAppNum);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that no repsonse when pressing Menu btn in All category page
     */
    @Test
    public void App_All_07_testNoResponseForMenuInAllCategoryPage(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            navigateToAllCategoryPageByMenuPopUp();
            //按遥控器Menu键
            menu();
            //断言
            verifyElementNotPresent("", "android:id/tv_fun_menu_text");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can Back to Launcher home page from App Store All Category page By home
     */
    @Test
    public void App_All_09_testBackToLauncherHomeFromAllCategory(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            navigateToAllCategoryPageByMenuPopUp();
            //按遥控器Home键
            home();
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTabObj = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTabObj.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can Back to App Store home page from App All category page By back btn
     */
    @Test
    public void App_All_10_testBackToAppStoreHomeFroAllCategoryPage(){
        try{
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            navigateToAllCategoryPageByMenuPopUp();
            //按遥控器Back键
            back();
            //断言
            UiObject appManageTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[5]));
            UiObject searchObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/activity_search_btn"));
            verifyElementPresent("The search icon in AppStore page is not displayed", searchObj);
            verifyElementPresent("The appManage tab in AppStore page is not displayed", appManageTab);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that the Menu pop up displays correctly in my app page
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_16_testMemuPopupInMyAppPage() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
            enterAppStorePage();
            //移动焦点到导航条 > 应用管理tab
            moveToAppStoreTargetTab(appStoreTabs[5]);
            moveDown();
            UiObject myAppCard = device.findObject(new UiSelector().text("我的应用").resourceId("tv.fun.appstore:id/tool_local"));
            myAppCard.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            moveDown();
            Boolean appFlag = checkWhetherHaveAppFromStoreInMyApp();
            if (!appFlag) {
                back();
                moveUp();
                moveRight();
                device.pressEnter();
                waitForElementPresentByIDAndText("tv.fun.appstore:id/search_result_title", "热门搜索：");
                //Input keyword in input textfield, to search app
                UiObject aKey = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").text("A"));
                aKey.click();
                UiObject resultCountObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_result_count"));
                int resultCount = Integer.parseInt(resultCountObj.getText());
                if (resultCount > 0) {
                    moveRightForMultiple(2);
                    device.pressEnter();
                    installAppInDetailPageAndBack();
                    back();
                    moveDown();
                    device.findObject(new UiSelector().text("我的应用").resourceId("tv.fun.appstore:id/tool_local")).clickAndWaitForNewWindow();
                    moveDown();
                }
            }
            //在我的应用页面，按遥控器Menu键
            menu();
            UiObject detailBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("详情"));
            UiObject uninstallBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("卸载"));
            UiObject cleanBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("清理数据"));
            verifyElementPresent("The app detail button is not displayed on the Menu pop-up", detailBtn);
            verifyElementPresent("The uninstall button is not displayed on the Menu pop-up", uninstallBtn);
            verifyElementPresent("The clean button is not displayed on the Menu pop-up", cleanBtn);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }


    /**
     * Test that enter app detail page from my app page
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_17_testEnterAppDetailPageFromMyAppPage() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
            enterAppStorePage();
            //移动焦点到导航条 > 应用管理tab
            moveToAppStoreTargetTab(appStoreTabs[5]);
            moveDown();
            moveRight();
            UiObject myAppCard = device.findObject(new UiSelector().text("我的应用").resourceId("tv.fun.appstore:id/tool_local"));
            myAppCard.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            Boolean appFlag = checkWhetherNoAppInMyApp();
            if (appFlag) {
                back();
                moveUp();
                moveRight();
                device.pressEnter();
                waitForElementPresentByIDAndText("tv.fun.appstore:id/search_result_title", "热门搜索：");
                //device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/activity_search_btn")).click(); Cannot use click
                //Input keyword in input textfield, to search app
                UiObject aKey = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").text("A"));
                aKey.click();
                UiObject resultCountObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_result_count"));
                int resultCount = Integer.parseInt(resultCountObj.getText());
                if (resultCount > 0) {
                    moveRightForMultiple(2);
                    device.pressEnter();
                    installAppInDetailPageAndBack();
                    back();
                    moveDown();
                    moveRight();
                    device.findObject(new UiSelector().text("我的应用").resourceId("tv.fun.appstore:id/tool_local")).clickAndWaitForNewWindow();
                }
            }
            //在应用卸载页面，按遥控器Menu键，并点击弹框上“详情”按钮
            UiObject firstApp = device.findObject(new UiSelector().className("android.widget.RelativeLayout").index(0));
            int appCountInList = findElementByID("tv.fun.appstore:id/listview").getChildCount();
            //得到第一个已安装应用名称
            String firstAppName = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/appName")).getText();
            moveDown();
            Assert.assertTrue("The first app is not selected", firstApp.isSelected());
            menu();
            int k = 0;
            if (appCountInList <= maxCountOfOneLine) {
                k = appCountInList;
            } else {
                k = maxCountOfOneLine;
            }
            UiObject selectedApp = null;
            UiObject selectApp = null;
            String selectAppName = "";
            for (int i = 0; i < k; i++) {
                menu();
                if (!device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("详情")).exists()) {
                    back();
                    moveRight();
                } else {
                    break;
                }
            }
            UiObject detailBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("详情"));
            verifyElementPresent("The app detail button is not displayed on the Menu pop-up", detailBtn);
            //点击“详情”按钮，进入应用详情页
            detailBtn.clickAndWaitForNewWindow();
            waitForAppDetailPageDisplay();
            //断言
            String appTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText();
//        Assert.assertTrue("The detail page is incorrect when entering detail page from app uninstall page", appTitle.equalsIgnoreCase(selectAppName));
            verifyElementPresent("App detail page is not displayed", device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_root")));
            verifyElementPresent("The brief of App detail page is not displayed", device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/brief")));
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test uninstall app on My App page
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_18_testUninstallAppFromMyAppPage() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
            enterAppStorePage();
            //移动焦点到导航条 > 应用管理tab
            moveToAppStoreTargetTab(appStoreTabs[5]);
            moveDown();
            UiObject myAppCard = device.findObject(new UiSelector().text("我的应用").resourceId("tv.fun.appstore:id/tool_local"));
            myAppCard.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            Boolean appFlag = checkWhetherNoAppInMyApp();
            if (appFlag) {
                back();
                moveUp();
                moveRight();
                device.pressEnter();
                waitForElementPresentByIDAndText("tv.fun.appstore:id/search_result_title", "热门搜索：");
                //device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/activity_search_btn")).click(); Cannot use click
                //Input keyword in input textfield, to search app
                UiObject AKey = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").text("A"));
                AKey.click();
                UiObject resultCountObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_result_count"));
                int resultCount = Integer.parseInt(resultCountObj.getText());
                if (resultCount > 0) {
                    moveRight();
                    device.pressEnter();
                    installAppInDetailPageAndBack();
                    back();
                    moveDown();
                    device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_local")).clickAndWaitForNewWindow();
                }
            }
            //得到已安装应用数量
            String installedAPP = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/subTitle")).getText().split("个应用")[0].replace("有", "");
            int installedAppCount = stringToInt(installedAPP);
            //在我的应用页面，按遥控Menu键
            UiObject firstApp = device.findObject(new UiSelector().className("android.widget.RelativeLayout").index(0));
            moveDown();
            String firstAPPName = firstApp.getChild(new UiSelector().resourceId("tv.fun.appstore:id/appName")).getText();
            if (firstAPPName.toLowerCase().contains(autoProjectKeyWord)) {
                moveRight();
            }
            menu();
            UiObject uninstallBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("卸载"));
            uninstallBtn.click();
            waitForElementPresentByID("com.android.packageinstaller:id/uninstall_confirm");
            //卸载弹框弹出后，点击“确定”
            UiObject confirmBtn = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/ok_button"));
            UiObject cancelBtn = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/cancel_button"));
            UiObject msgObj = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/uninstall_confirm"));
            verifyString("Uninstall pop-up window is not displayed", confirmBtn.getText(), "确定");
            verifyString("Uninstall pop-up window is not displayed", cancelBtn.getText(), "取消");
            verifyString("Uninstall pop-up window is not displayed", msgObj.getText(), "要卸载此应用吗？");
            confirmBtn.click();
            waitForElementNotPresentByID("com.android.packageinstaller:id/uninstall_confirm");
            if (installedAppCount > 1) {
                List<UiObject2> myAppList = device.findObjects(By.clazz("android.widget.RelativeLayout"));
                int appCardCount = myAppList.size();
                Assert.assertTrue("The left app num is not more than 1", appCardCount >= 1);
                String leftApp = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/subTitle")).getText().split("个应用")[0].replace("有", "");
                int leftAppCount = Integer.parseInt(leftApp);
                verifyTrue("The left app num in title is not correct after uninstalling one app", leftAppCount == installedAppCount - 1);
            } else {
                UiObject uninstallAppPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/emptyView"));
                verifyElementPresent("My App page dispalys incorrectly after uninstalling", uninstallAppPage);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test that it can navigate from MyAPP page to APPClean page by CleanData button
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_22_testGotoAppCleanPageFromMyAppInAppstore() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //移动焦点到导航条 > 应用管理tab
            moveToAppStoreTargetTab(appStoreTabs[5]);
            moveDown();
            UiObject myAppCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_local"));
            if (!myAppCard.isSelected()) {
                moveRightForMultiple(2);
                if (findElementByID("tv.fun.appstore:id/activity_search_btn").isSelected()) {
                    moveLeft();
                    moveDown();
                } else {
                    moveLeft();
                    if (!findElementByID("tv.fun.appstore:id/tool_local").isSelected()) {
                        moveLeft();
                    }
                }
            }
            myAppCard.clickAndWaitForNewWindow();
            //在我的应用页面，按遥控器Menu键
            moveDown();
            menu();
            UiObject appCleanBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("清理数据"));
            //断言
            Assert.assertTrue("The app clean button is not displayed on the Menu pop-up", appCleanBtn.exists());
            device.pressDPadRight();
            appCleanBtn.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.master:id/uninstall");
            waitForElementPresentByID("tv.fun.master:id/clearData");
            UiObject appCleanPageTitle = device.findObject(new UiSelector().resourceId("tv.fun.master:id/title"));
            UiObject uninstallBtn = device.findObject(new UiSelector().resourceId("tv.fun.master:id/uninstall"));
            UiObject cleanDataBtn = device.findObject(new UiSelector().resourceId("tv.fun.master:id/clearData"));
            verifyString("app clean page title is incorrect", appCleanPageTitle.getText(), "应用清理");
            verifyElementPresent(" The uninstall button is not displayed", uninstallBtn);
            verifyString(" The name of uninstall button is displayed incorrectly", uninstallBtn.getText(), "卸载");
            verifyElementPresent(" The clean date button is not displayed", cleanDataBtn);
            verifyString(" The name of clean date is displayed incorrectly", cleanDataBtn.getText(), "清理数据");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that the UI and detail info of App Uninstall page displays correctly.
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_27_testEnterAppUninstallPage() {
        int totalAppNum = 0;
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
            enterAppStorePage();
            //移动焦点到导航条 > 应用管理tab
            moveToAppStoreTargetTab(appStoreTabs[5]);
            moveDown();
            moveRight();
            UiObject appUninstallCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall"));
            appUninstallCard.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            moveDown();
            //断言
            Assert.assertTrue("Title of AppUninstall page is incorrect", device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText().equalsIgnoreCase("应用卸载"));
            String subTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/subTitle")).getText();
            if(!device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).exists()){
                UiObject lisViewObj = findElementByID("tv.fun.appstore:id/listview");
                totalAppNum = lisViewObj.getChildCount();
            }else {
                String str = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText();
                String totalApp = device.findObject(new UiSelector().resourceId("android:id/tv_fun_scrollbar_indicator")).getText().split(" / ")[1];
                totalAppNum = stringToInt(totalApp);
            }
            List<UiObject2> appList = device.findObjects(By.clazz("android.widget.RelativeLayout"));
            int appCardCount = appList.size();
            verifyTrue("The count of app displayed in current page is not more than 1", appCardCount > 1 && appCardCount <= totalAppNum);
            verifyTrue("Subtitle of AppUninstall page is incorrect", subTitle.contains("有" + totalAppNum + "个应用 占用空间:") && subTitle.endsWith("M"));
            //验证页面底部有当前应用的名称和大小信息
            String firstAppName = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/appName")).getText();
            UiObject appInfoBar = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/appState"));
            String appInfo = appInfoBar.getText();
            verifyTrue("App info displayed in AppUninstall page is incorrect", appInfo.contains(firstAppName) && appInfo.endsWith("M"));
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * test the app can be uninstalled succeccfully from AppUninstall page
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_34_testUninstallAppFromAppUnintallPage() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
            enterAppStorePage();
            //移动焦点到导航条 > 应用管理tab
            moveToAppStoreTargetTab(appStoreTabs[5]);
            moveDown();
            moveRight();
            UiObject appUninstallCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall"));
            appUninstallCard.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            Boolean appFlag = checkWhetherNoAppInAppUninstall();
            if (appFlag) {
                back();
                moveUp();
                moveRight();
                device.pressEnter();
                waitForElementPresentByIDAndText("tv.fun.appstore:id/search_result_title", "热门搜索：");
                //device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/activity_search_btn")).click(); Cannot use click
                //Input keyword in input textfield, to search app
                UiObject AKey = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").text("A"));
                AKey.click();
                UiObject resultCountObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_result_count"));
                int resultCount = Integer.parseInt(resultCountObj.getText());
                if (resultCount > 0) {
                    moveRightForMultiple(2);
                    device.pressEnter();
                    installAppInDetailPageAndBack();
                    back();
                    moveDown();
                    moveRight();
                    device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall")).clickAndWaitForNewWindow();
                    waitForElementPresentByID("tv.fun.appstore:id/title");
                }
            }
            //得到已安装应用数量
            String installedAPP = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/subTitle")).getText().split("个应用")[0].replace("有", "");
            int installedAppCount = stringToInt(installedAPP);
            //在应用卸载页面，点击应用卡片
            UiObject firstApp = device.findObject(new UiSelector().className("android.widget.RelativeLayout").index(0));
            firstApp.click();
            waitForElementPresentByID("com.android.packageinstaller:id/uninstall_confirm");
            //卸载弹框弹出后，点击“确定”
            UiObject confirmBtn = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/ok_button"));
            UiObject cancelBtn = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/cancel_button"));
            UiObject msgObj = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/uninstall_confirm"));
            verifyString("Uninstall pop-up window is not displayed", confirmBtn.getText(), "确定");
            verifyString("Uninstall pop-up window is not displayed", cancelBtn.getText(), "取消");
            verifyString("Uninstall pop-up window is not displayed", msgObj.getText(), "要卸载此应用吗？");
            confirmBtn.click();
            waitForElementNotPresentByID("com.android.packageinstaller:id/uninstall_confirm");
            if (installedAppCount > 1) {
                List<UiObject2> appLists = device.findObjects(By.clazz("android.widget.RelativeLayout"));
                int appCount = appLists.size();
                verifyTrue("The left app num is more than 1", appCount >= 1);
                String leftApp = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/subTitle")).getText().split("个应用")[0].replace("有", "");
                int leftAppCount = Integer.parseInt(leftApp);
                verifyTrue("The left app num in title is not correct after uninstalling one app", leftAppCount == installedAppCount - 1);
            } else {
                UiObject uninstallAppPage = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/emptyView"));
                verifyElementPresent("The App uninstall page dispalys incorrectly after uninstalling", uninstallAppPage);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can goto App detail page from app uninstall page
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_40_testEnterAppDetailPageFromAppUnintallPage() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
            enterAppStorePage();
            //移动焦点到导航条 > 应用管理tab
            moveToAppStoreTargetTab(appStoreTabs[5]);
            moveDown();
            moveRight();
            UiObject appUninstallCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall"));
            appUninstallCard.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            Boolean appFlag = checkWhetherNoAppInAppUninstall();
            if (appFlag) {
                back();
                moveUp();
                moveRight();
                device.pressEnter();
                waitForElementPresentByIDAndText("tv.fun.appstore:id/search_result_title", "热门搜索：");
                //device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/activity_search_btn")).click(); Cannot use click
                //Input keyword in input textfield, to search app
                UiObject AKey = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").text("A"));
                AKey.click();
                UiObject resultCountObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_result_count"));
                int resultCount = Integer.parseInt(resultCountObj.getText());
                if (resultCount > 0) {
                    moveRightForMultiple(2);
//                device.pressDPadDown();
//                device.pressDPadUp();
                    device.pressEnter();
//              UiObject result = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_search_reulst_cell").index(0));
//                result.clickAndWaitForNewWindow();
                    installAppInDetailPageAndBack();
                    back();
                    moveDown();
                    moveRight();
                    device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall")).clickAndWaitForNewWindow();
                }
            }
            //得到第一个已安装应用名称
            String firstAppName = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/appName")).getText();

            //在应用卸载页面，点击应用卡片
            moveDown();
            menu();
            waitForElementPresentByID("android:id/tv_fun_menu_text");
            UiObject appDetailBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("详情"));
            verifyElementPresent("The app detail button is not displayed on the Menu pop-up", appDetailBtn);
//        device.pressDPadRight();
            //点击“详情”按钮，进入应用详情页
            appDetailBtn.clickAndWaitForNewWindow();
            waitForAppDetailPageDisplay();
            //断言
            String appTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText();
            verifyString("The detail page is incorrect when entering detail page from app uninstall page", appTitle, firstAppName);
            verifyElementPresent("App detail page is not displayed", device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_root")));
            verifyElementPresent("The brief of App detail page is not displayed", device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_poster_list")));
            UiObject picObj = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_poster_list")).getChild(new UiSelector().resourceId("tv.fun.appstore:id/app_detail_poster_image").index(0));
            verifyElementPresent("The poster of App detail page is not displayed", picObj);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that it can navigate from AppUninstall page to APPClean page by CleanData button
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_42_testGotoAppCleanPageFromUninstallAppPage() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //移动焦点到导航条 > 应用管理tab
            moveToAppStoreTargetTab(appStoreTabs[5]);
            //向下选中应用卸载卡片
            moveDown();
            moveRight();
            UiObject appUninstallCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall"));
            appUninstallCard.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            waitForElementPresentByID("android:id/tv_fun_scrollbar");
            //在应用卸载页面，按遥控器Menu键
            moveDown();
            menu();
            UiObject appCleanBtn = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("清理数据"));
            //断言
            Assert.assertTrue("The app clean button is not displayed on the Menu pop-up", appCleanBtn.exists());
            moveRightForMultiple(2);
            appCleanBtn.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.master:id/uninstall");
            waitForElementPresentByID("tv.fun.master:id/clearData");
            UiObject appCleanPageTitle = findElementByID("tv.fun.master:id/title");
            UiObject uninstallBtn = device.findObject(new UiSelector().resourceId("tv.fun.master:id/uninstall"));
            UiObject cleanDataBtn = device.findObject(new UiSelector().resourceId("tv.fun.master:id/clearData"));
            verifyString("app clean page title is incorrect", appCleanPageTitle.getText(), "应用清理");
            verifyElementPresent(" The uninstall button is not displayed", uninstallBtn);
            verifyString(" The name of uninstall button is displayed incorrectly", uninstallBtn.getText(), "卸载");
            verifyElementPresent(" The clean date button is not displayed", cleanDataBtn);
            verifyString(" The name of clean date is displayed incorrectly", cleanDataBtn.getText(), "清理数据");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test that can enter app upgrade page successfully
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_47_testEnterAppUpgradePage() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
            enterAppStorePage();
            //移动焦点到导航条 > 应用管理tab
            moveToAppStoreTargetTab(appStoreTabs[5]);
            moveDown();
            UiObject appUpgradeCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_upgrade"));
            appUpgradeCard.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.appstore:id/title");
            //断言
            String pageTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/title")).getText();
            verifyString("Title of AppUpgrade page is incorrect", pageTitle, "应用更新");
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Test goto About page
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Test
    public void App_AppM_73_testEnterAppStoreAboutPage() {
        try {
            //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
            enterAppStorePage();
            //移动焦点到导航条 >应用管理tab
            moveToAppStoreTargetTab(appStoreTabs[5]);
            UiObject appManageTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text(appStoreTabs[5]));
            Boolean flag = verifyTrue("The focus is not on the appManage tab", appManageTab.isSelected());
            //点击应用管理tab下关于卡片
            UiObject aboutCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_about"));
            aboutCard.clickAndWaitForNewWindow();
            UiObject aboutPageTitle = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/main"));
            UiObject detailInfo = device.findObject(new UiSelector().text("应用市场交流反馈QQ群：110155617\n" + "扫一扫二维码加入该群"));
            verifyString("AppStore About page title is displayed incorrectly", aboutPageTitle.getText(), "应用市场");
            verifyElementPresent("AppStore About page detail info is displayed incorrectly", detailInfo);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }
    /*****************************************************New Design******************************************************/
    /**
     * Check System App page UI
     */
    @Test
    public void App_AppM_73_testCheckSystemAppPageUI()  {
        try {
            //点击应用tab下“系统应用”卡片，进入系统应用页面
            enterSystemAppPage();
            //验证系统应用页面UI显示
            UiObject pageTitleObj = findElementByID("tv.fun.appstore:id/title");
            UiObject subtitleObj = findElementByID("tv.fun.appstore:id/subTitle");
            String subTitleStr = subtitleObj.getText().split("有")[1].split("个应用")[0];
            int appNum = stringToInt(subTitleStr);
            verifyString("", pageTitleObj.getText(), systemAppPageTitle);
            verifyIncludeString("", subtitleObj.getText(), "个应用");
            UiObject sysAppListObj = findElementByID("tv.fun.appstore:id/listview");
            int sysAppCount = sysAppListObj.getChildCount();
            verifyNumber("", sysAppCount, appNum);
            verifyNumberLarger("", sysAppCount, 1);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Launch appstore app in system app page
     */
    @Test
    public void App_AppM_73_testLaunchAppStoreInSystemAppPage(){
        try {
            //点击应用tab下“系统应用”卡片，进入系统应用页面
            enterSystemAppPage();
            //点击应用市场卡片
            UiObject subtitleObj = findElementByID("tv.fun.appstore:id/subTitle");
            int appNum = stringToInt(subtitleObj.getText().split("有")[1].split("个应用")[0]);
            UiObject sysAppListObj = findElementByID("tv.fun.appstore:id/listview");
            for(int i=0; i<appNum; i++){
                UiObject appObj = sysAppListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(i));
                UiObject appNameObj = appObj.getChild(new UiSelector().resourceId("tv.fun.appstore:id/appName"));
                String sysAppName = appNameObj.getText();
                if(sysAppName.equalsIgnoreCase(systemAppList[0])){
                    break;
                }else{
                    moveRight();
                }
            }
            UiObject appstoreCard = findElementByText("应用市场", "tv.fun.appstore:id/appName");
            appstoreCard.clickAndWaitForNewWindow();
            //验证可以正常进入应用市场页面
            UiObject appstoreManageTab = findElementByText("应用管理", "tv.fun.appstore:id/column_title");
            UiObject appstoreSearchBtn = findElementByID("tv.fun.appstore:id/activity_search_btn");
            verifyElementPresent("", appstoreManageTab);
            verifyTrue("", appstoreSearchBtn.exists());
            //按Back键可以正常回到系统应用页面
            back();
            UiObject pageTitleObj = findElementByID("tv.fun.appstore:id/title");
            verifyString("", pageTitleObj.getText(), systemAppPageTitle);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Launch TV Master app in system app page
     */
    @Test
    public void App_AppM_73_testLaunchMasterInSystemAppPage(){
        try {
            //点击应用tab下“系统应用”卡片，进入系统应用页面
            enterSystemAppPage();
            //点击应用市场卡片
            UiObject subtitleObj = findElementByID("tv.fun.appstore:id/subTitle");
            int appNum = stringToInt(subtitleObj.getText().split("有")[1].split("个应用")[0]);
            UiObject sysAppListObj = findElementByID("tv.fun.appstore:id/listview");
            for(int i=0; i<appNum; i++) {
                UiObject appObj = sysAppListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(i));
                UiObject appNameObj = appObj.getChild(new UiSelector().resourceId("tv.fun.appstore:id/appName"));
                String sysAppName = appNameObj.getText();
                if (sysAppName.equalsIgnoreCase(systemAppList[2])) {
                    break;
                } else if (appNum > 6 && i < 6) {
                    moveRight();
                } else {
                    if (i == 6) {
                        moveDown();
                    } else {
                        moveLeft();
                    }
                }
            }
            UiObject masterCard = findElementByText(systemAppList[2], "tv.fun.appstore:id/appName");
            masterCard.clickAndWaitForNewWindow();
            //验证可以正常进入电视助手页面
            UiObject tvMasterTitle = findElementByID("tv.fun.master:id/tv_master_title");
            UiObject masterSettingBtn = findElementByID("tv.fun.master:id/iv_settings");
            verifyElementPresent("", masterSettingBtn);
            verifyTrue("", tvMasterTitle.getText().equalsIgnoreCase(systemAppList[2]));
            //按Back键可以正常回到系统应用页面
            back();
            UiObject pageTitleObj = findElementByID("tv.fun.appstore:id/title");
            verifyString("", pageTitleObj.getText(), systemAppPageTitle);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }

    }

    /**
     * Launch FileManager app in system app page
     */
    @Test
    public void App_AppM_73_testLaunchFileManagerInSystemAppPage(){
        try {
            //点击应用tab下“系统应用”卡片，进入系统应用页面
            enterSystemAppPage();
            //点击应用市场卡片
            UiObject subtitleObj = findElementByID("tv.fun.appstore:id/subTitle");
            int appNum = stringToInt(subtitleObj.getText().split("有")[1].split("个应用")[0]);
            UiObject sysAppListObj = findElementByID("tv.fun.appstore:id/listview");
            for(int i=0; i<appNum; i++){
                UiObject appObj = sysAppListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(i));
                UiObject appNameObj = appObj.getChild(new UiSelector().resourceId("tv.fun.appstore:id/appName"));
                String sysAppName = appNameObj.getText();
                if(sysAppName.equalsIgnoreCase(systemAppList[1])){
                    break;
                }else{
                    moveRight();
                }
            }
            UiObject fileCard = findElementByText(systemAppList[1], "tv.fun.appstore:id/appName");
            fileCard.clickAndWaitForNewWindow();
            //验证可以正常进入文件管理页面
            UiObject fileManage = findElementByText("全部文件", "tv.fun.filemanager:id/entity_name");
            UiObject allCategoryBtn = findElementByID("tv.fun.filemanager:id/category_all");
            verifyElementPresent("", allCategoryBtn);
            verifyTrue("", fileManage.exists());
            //按Back键正常回到系统应用页面
            back();
            UiObject pageTitleObj = findElementByID("tv.fun.appstore:id/title");
            verifyString("", pageTitleObj.getText(), systemAppPageTitle);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }

    }

    /**
     * Launch TV Weather app in system app page
     */
    @Test
    public void App_AppM_73_testLaunchWeatherInSystemAppPage(){
        try {
            //点击应用tab下“系统应用”卡片，进入系统应用页面
            enterSystemAppPage();
            //点击应用市场卡片
            UiObject subtitleObj = findElementByID("tv.fun.appstore:id/subTitle");
            int appNum = stringToInt(subtitleObj.getText().split("有")[1].split("个应用")[0]);
            UiObject sysAppListObj = findElementByID("tv.fun.appstore:id/listview");
            for(int i=0; i<appNum; i++){
                UiObject appObj = sysAppListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(i));
                UiObject appNameObj = appObj.getChild(new UiSelector().resourceId("tv.fun.appstore:id/appName"));
                String sysAppName = appNameObj.getText();
                if(sysAppName.equalsIgnoreCase(systemAppList[4])){
                    break;
                }else{
                    moveRight();
                }
            }
            UiObject weatherCard = findElementByText(systemAppList[4], "tv.fun.appstore:id/appName");
            weatherCard.clickAndWaitForNewWindow();
            //验证可以正常进入应用市场页面
            UiObject weatherRefreshTab = findElementByID("tv.fun.weather:id/tv_weather_refresh_time");
            UiObject weatherDayObj = findElementByID("tv.fun.weather:id/rl_weather_day");
            verifyElementPresent("", weatherDayObj);
            verifyTrue("", weatherRefreshTab.getText().contains("前更新"));
            //按Back键正常回到系统应用页面
            back();
            UiObject pageTitleObj = findElementByID("tv.fun.appstore:id/title");
            verifyString("", pageTitleObj.getText(), systemAppPageTitle);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }

    }

    /**
     * Launch TV Upgrade app in system app page
     */
    @Test
    public void App_AppM_73_testLaunchTVUpgradeInSystemAppPage(){
        try {
            //点击应用tab下“系统应用”卡片，进入系统应用页面
            enterSystemAppPage();
            //点击应用市场卡片
            UiObject subtitleObj = findElementByID("tv.fun.appstore:id/subTitle");
            int appNum = stringToInt(subtitleObj.getText().split("有")[1].split("个应用")[0]);
            UiObject sysAppListObj = findElementByID("tv.fun.appstore:id/listview");
            for(int i=0; i<appNum; i++){
                UiObject appObj = sysAppListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(i));
                UiObject appNameObj = appObj.getChild(new UiSelector().resourceId("tv.fun.appstore:id/appName"));
                String sysAppName = appNameObj.getText();
                if(sysAppName.equalsIgnoreCase(systemAppList[3])){
                    break;
                }else{
                    moveRight();
                }
            }
            UiObject upgradeCard = findElementByText(systemAppList[3], "tv.fun.appstore:id/appName");
            upgradeCard.clickAndWaitForNewWindow();
            //验证可以正常进入系统升级页面
            UiObject upgradePageObj = findElementByID("tv.fun.tvupgrade:id/latest_ui");
            verifyElementPresent("", upgradePageObj);
            //按Back键正常回到系统应用页面
            back();
            UiObject pageTitleObj = findElementByID("tv.fun.appstore:id/title");
            verifyString("", pageTitleObj.getText(), systemAppPageTitle);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }

    }

    /**
     * Launch TV WifiDisplay app in system app page
     */
    @Test
    public void App_AppM_73_testLaunchWifiDisplayInSystemAppPage(){
        try {
            //点击应用tab下“系统应用”卡片，进入系统应用页面
            enterSystemAppPage();
            //点击应用市场卡片
            UiObject subtitleObj = findElementByID("tv.fun.appstore:id/subTitle");
            int appNum = stringToInt(subtitleObj.getText().split("有")[1].split("个应用")[0]);
            UiObject sysAppListObj = findElementByID("tv.fun.appstore:id/listview");
            for(int i=0; i<appNum; i++){
                UiObject appObj = sysAppListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(i));
                UiObject appNameObj = appObj.getChild(new UiSelector().resourceId("tv.fun.appstore:id/appName"));
                String sysAppName = appNameObj.getText();
                if(sysAppName.equalsIgnoreCase(systemAppList[5])){
                    break;
                }else{
                    moveRight();
                }
            }
            UiObject wifiDisplayCard = findElementByText(systemAppList[5], "tv.fun.appstore:id/appName");
            wifiDisplayCard.clickAndWaitForNewWindow();
            //验证可以正常进入无线投屏页面
            UiObject wifiDisplayTitle = findElementByID("com.funshion.tvwifidisplay:id/title_text");
            UiObject wifiDisplayDesBtn = findElementByID("com.funshion.tvwifidisplay:id/title_description");
            verifyString("", wifiDisplayTitle.getText(), "无线投屏");
            verifyTrue("", wifiDisplayDesBtn.exists());
            //按Back键正常回到系统应用页面
            back();
            UiObject pageTitleObj = findElementByID("tv.fun.appstore:id/title");
            verifyString("", pageTitleObj.getText(), systemAppPageTitle);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }

    }

    /**
     * Launch TV introduction app in system app page
     */
    @Test
    public void App_AppM_73_testLaunchTVIntroduceInSystemAppPage(){

        try {
            //点击应用tab下“系统应用”卡片，进入系统应用页面
            enterSystemAppPage();
            //点击应用市场卡片
            UiObject subtitleObj = findElementByID("tv.fun.appstore:id/subTitle");
            int appNum = stringToInt(subtitleObj.getText().split("有")[1].split("个应用")[0]);
            UiObject sysAppListObj = findElementByID("tv.fun.appstore:id/listview");
            for(int i=0; i<appNum; i++){
                UiObject appObj = sysAppListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(i));
                UiObject appNameObj = appObj.getChild(new UiSelector().resourceId("tv.fun.appstore:id/appName"));
                String sysAppName = appNameObj.getText();
                if(sysAppName.equalsIgnoreCase(systemAppList[6])){
                    break;
                }else{
                    moveRight();
                }
            }
            UiObject instroduceCard = findElementByText(systemAppList[6], "tv.fun.appstore:id/appName");
            instroduceCard.clickAndWaitForNewWindow();
            //验证可以正常进入玩转电视页面
            UiObject pageTitle = findElementByText("安装说明", "tv.fun.instructions:id/tab_title");
            UiObject introduceView = findElementByID("tv.fun.instructions:id/gallery_view");
            verifyElementPresent("", pageTitle);
            verifyTrue("", introduceView.exists());
            //按Back键正常回到系统应用页面
            back();
            UiObject pageTitleObj = findElementByID("tv.fun.appstore:id/title");
            verifyString("", pageTitleObj.getText(), systemAppPageTitle);
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * No response when pressing menu btn in system app page
     */
    @Test
    public void App_AppM_73_testNoMenuResponceInSystemAppPage(){
        try {
            //点击应用tab下“系统应用”卡片，进入系统应用页面
            enterSystemAppPage();
            //按遥控器Menu键
            menu();
            //验证系统应用页面不会响应Menu键操作
            verifyElementNotPresent("", "android:id/tv_fun_menu_text");
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Can back to launcher home page when pressing home btn in system app page
     */
    @Test
    public void App_AppM_73_testBackToLauncherFromSystemAppPageByHome(){
        try {
            //点击应用tab下“系统应用”卡片，进入系统应用页面
            enterSystemAppPage();
            //点击Back键
            home();
            //验证可以正常回到Launcher Home页面
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTabObj = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTabObj.isSelected());
        }catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

    /**
     * Can back to the last page when pressing back btn in system app page
     */
    @Test
    public void App_AppM_73_testBackToTheLastPageFromSystemAppPageByBack() {
        try {
            //点击应用tab下“系统应用”卡片，进入系统应用页面
            enterSystemAppPage();
            //点击Back键
            back();
            //验证可以正常回到Launcher应用页面
            UiObject appStoreIcon = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(appStoreIconName));
            UiObject tvMasterIcon = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(tvMasterIconName));
            verifyElementPresent("应用市场卡片没有显示在Launcher应用页面", appStoreIcon);
            verifyElementPresent("电视助手卡片没有显示在Launcher应用页面", tvMasterIcon);
            UiObject appTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(launcherAppTab));
            verifyTrue("", appTab.isSelected());
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }

//            @Test
//    public void test(){
//        TvCommon.printAllMethods(this.getClass().getName());
//    }
}