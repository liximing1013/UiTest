package tv.fun.appstoretest.common;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.view.KeyEvent;

/**
 * Created by liuqing on 2016/10/24.
 */
public class AppStorePage extends Common {
    public String appStoreBtn = "应用市场";
    public String appStoreTabID = "tv.fun.appstore:id/column_title";
    public String[] topicsUnderSuggest = {"热门排行", "最新上架", "装机必备"};
    public String currentTabName = "";
    public String launcherVideoTab = "视频";
    public String launcherAppTab = "应用";

    /**
     * Method for navigating to Launcher App tab page
     */
    public void navigateToLauncherAppTab() throws UiObjectNotFoundException, InterruptedException {
        home();
        moveToLauncherTargetTab(appTab);
    }

    /**
     * Method for entering AppStore page from Launcher
     *
     * @throws UiObjectNotFoundException
     */
    public void enterAppStorePage() throws UiObjectNotFoundException, InterruptedException {
        //移动焦点到Launcher应用tab
        UiObject appTabObj = device.findObject(new UiSelector().text("应用").resourceId("com.bestv.ott:id/tab_title"));
        if (!appTabObj.exists()||!appTabObj.isSelected()) {
            navigateToLauncherAppTab();
        }
        UiObject appStoreCard = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(appStoreIconName));
        moveDownForMultiple(1);
        if (!appStoreCard.isSelected()) {
            moveRight();
            appStoreCard = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(appStoreIconName));
            if(!appStoreCard.isSelected()){
                moveUp();
                moveLeft();
                moveRight();
                moveDown();
                moveRight();
            }
        }
        device.pressEnter();
        waitForElementPresentByIDAndText("tv.fun.appstore:id/column_title", "应用管理");
    }

    /**
     * Method for entering System App page from Launcher
     *
     * @throws UiObjectNotFoundException
     */
    public void enterSystemAppPage() throws UiObjectNotFoundException, InterruptedException {
        //移动焦点到Launcher应用tab
        UiObject appTabObj = device.findObject(new UiSelector().text("应用").resourceId("com.bestv.ott:id/tab_title"));
        if (!appTabObj.exists()||!appTabObj.isSelected()) {
            navigateToLauncherAppTab();
        }
        UiObject systemAppCard = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(systemAppIconName));
        moveDownForMultiple(2);
        if (!systemAppCard.isSelected()) {
            moveRightForMultiple(2);
            systemAppCard = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(systemAppIconName));
            if(!systemAppCard.isSelected()){
                moveUpForMultiple(2);
                moveLeft();
                moveRight();
                moveDownForMultiple(2);
                moveRightForMultiple(2);
            }
        }
        device.pressEnter();
        waitForElementPresentByIDAndText("tv.fun.appstore:id/title", "系统应用");
    }

    /**
     * Move to the target Method for entering System App page from Launcher
     *
     * @throws UiObjectNotFoundException
     */
    public void moveToTargetSystemApp(String targetApp) throws UiObjectNotFoundException, InterruptedException {
        moveLeftForMultiple(7);
        UiObject sysAppListObj = findElementByID("tv.fun.appstore:id/listview");
        int sysAppCount = sysAppListObj.getChildCount();
        UiObject currentSelectedTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("应用管理"));
        if (currentSelectedTab.isSelected()) {
            moveUpForMultiple(4);//Move to navBar to avoid that the current focus not in narBar
            if (targetApp.equalsIgnoreCase(appStoreTabs[0])) {
                moveLeftForMultiple(5);
            }
        }
    }

    /**
     * Sometimes, when entering appstore from Launcher, the default tab is not the first tab. This method is used to move the focus to the first tab
     *
     * @param targetTab
     * @throws UiObjectNotFoundException
     */
    public void moveToSuggestTabFromAppManageTab(String targetTab) throws UiObjectNotFoundException {
        UiObject currentSelectedTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("应用管理"));
        if (currentSelectedTab.isSelected()) {
            moveUpForMultiple(4);//Move to navBar to avoid that the current focus not in narBar
            if (targetTab.equalsIgnoreCase(appStoreTabs[0])) {
                moveLeftForMultiple(5);
            }
        }
    }

    /**
     * Sometimes, when entering appstore from Launcher, the default tab is not the first tab. This method is used to move the focus to the first tab
     *
     * @param targetTab
     * @throws UiObjectNotFoundException
     */
    public Boolean moveToAppStoreTargetTab(String targetTab) throws UiObjectNotFoundException {
        moveToTargetTab(appStoreTabs, targetTab, appStoreTabID, 6);
        moveUpForMultiple(4);//Move to navBar to avoid that the current focus not in narBar
        Boolean selectedFlag = findElementByText(targetTab, appStoreTabID).isSelected();
        return selectedFlag;
    }

    /**
     * Get the tabs in launcher page
     */
    public void getTabsInLauncherPage(String tabResouceID) throws UiObjectNotFoundException {
        home();
        moveUp();
        UiObject tabsListEle = device.findObject(new UiSelector().resourceId(tabResouceID));
        int tabsCount = tabsListEle.getChildCount();


    }

    /**
     * Sometimes, the default tab is not the APP tab. This method is used to move the focus to the target tab in launcher home page
     *
     * @param targetTab
     * @throws UiObjectNotFoundException
     */
    public void moveToLauncherTargetTab(String targetTab) throws UiObjectNotFoundException, InterruptedException {
        moveToLauncherTargetTab(targetTab, launcherTabID, 4);
    }

    /**
     * 在Appstore首页，移动到导航条
     * <p>
     * step  连续向右移的次数
     */
    public void moveToTopOnAppStoreHomePage() {
        for (int i = 1; i <= 6; i++) {
            device.pressDPadUp();
        }
    }

    /**
     * 连续按遥控器左键
     * <p>
     * step  连续向左移的次数
     */
    public void moveToTheMostLeftOfTabPage() {
        for (int i = 1; i <= 5; i++) {
            device.pressDPadLeft();
        }
    }

    /**
     * Enter topic page under suggest tab
     */
    public void enterTopicPage(String topicName) throws InterruptedException, UiObjectNotFoundException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        moveToFirstCardUnderSuggestTab();
        UiObject topicObj = device.findObject(new UiSelector().text(topicName).resourceId("tv.fun.appstore:id/title"));
        UiObject firstTopic = device.findObject(new UiSelector().text(topicsUnderSuggest[0]).resourceId("tv.fun.appstore:id/title"));
        if (!firstTopic.isSelected()) {
            moveToFirstCardUnderSuggestTab();
        }
        if (topicName.equalsIgnoreCase(topicsUnderSuggest[1])) {
            moveDown();
        } else if (topicName.equalsIgnoreCase(topicsUnderSuggest[2])) {
            moveDownForMultiple(2);
        }

        //点击推荐tab下topic
        topicObj.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/topic_app_list");
    }

    /**
     * Check whether no app installed in myApp or AppUninstall page except the app using for auto.Usually, there will be 1 app for auto displayed in MyApp page, 2 apps for auto displayed in AppUninstall page
     */
    public Boolean checkWhetherNoAppInstalled() {
        Boolean flag = false;
        try {
            if (device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/emptyView")).exists()) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * Check whether no app installed in myApp or AppUninstall page except the app using for auto.Usually, there will be 1 app for auto displayed in MyApp page, 2 apps for auto displayed in AppUninstall page
     *
     * @param limitAppCount
     * @return
     */
    public Boolean checkWhetherNoAppInstalledExceptAutoAPP(int limitAppCount) throws UiObjectNotFoundException {
        Boolean flag = false;
        try {
            flag = checkWhetherNoAppInstalled();
            if (!flag) {
                String app = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/subTitle")).getText().split("个应用")[0].replace("有", "");
                int appCount = stringToInt(app);
                if (appCount <= limitAppCount) {
                    flag = true;
                } else {
                    String fApp = findElementByID("tv.fun.appstore:id/appName").getText();
                    if (fApp.contains("auto") || fApp.contains("test")) {
                        flag = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * Check whether no app in MyApp page except 1 app for auto
     *
     * @return
     */
    public Boolean checkWhetherNoAppInMyApp() throws UiObjectNotFoundException {
        Boolean f = checkWhetherNoAppInstalledExceptAutoAPP(1);
        return f;
    }

    /**
     * Check whether no app in AppUninstall page except 2 app for auto
     *
     * @return
     */
    public Boolean checkWhetherNoAppInAppUninstall() throws UiObjectNotFoundException {
        Boolean f = checkWhetherNoAppInstalledExceptAutoAPP(2);
        return f;
    }

    /**
     * Check whether there are app which installed from app store in MyApp page
     *
     * @return
     */
    public Boolean checkWhetherHaveAppFromStoreInMyApp() throws UiObjectNotFoundException {
        Boolean flag = false;
        UiObject appList = findElementByID("tv.fun.appstore:id/listview");
        int appNum = appList.getChildCount();
        for(int i = 0; i<appNum; i++){
            UiObject eachAppObj = appList.getChild(new UiSelector().className("android.widget.RelativeLayout").index(i));
            UiObject currentAppObj = eachAppObj.getChild(new UiSelector().resourceId("tv.fun.appstore:id/app_item_poster"));
            if(!currentAppObj.exists()){
                flag = false;
                if(i<5) {
                    moveRight();
                }else if(i==5){
                    moveDown();
                    moveLeftForMultiple(5);
                }else {
                    moveLeft();
                }
            }else{
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Install App in detail page
     *
     * @throws InterruptedException
     */
    public void installAppInDetailPage() throws InterruptedException {
        device.pressDPadDown();
        device.pressDPadUp();
        device.pressEnter();
        Thread.sleep(30000);
        waitForElementNotPresentByID("tv.fun.appstore:id/progressState");
        waitForElementPresentByIDAndText("tv.fun.appstore:id/titleContainer", "打开");
    }

    /**
     * Install App in detail page and back
     *
     * @throws InterruptedException
     */
    public void installAppInDetailPageAndBack() throws InterruptedException {
        device.pressDPadDown();
        device.pressDPadUp();
        device.pressEnter();
        Thread.sleep(5000);
        waitForElementNotPresentByID("tv.fun.appstore:id/progressState");
        waitForElementPresentByIDAndText("tv.fun.appstore:id/titleContainer", "打开");
        device.pressBack();
    }

    /**
     * Navigate to search page by Menu popup in app store page
     *
     * @throws InterruptedException
     */
    public void navigateToSearchPageByMenuPopUp() throws InterruptedException, UiObjectNotFoundException {
        menu();
        waitForElementPresentByID("android:id/tv_fun_menu_icon");
        UiObject searchIcon = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("搜索"));
        searchIcon.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/search_single_key");

    }

    /**
     * Search App by keywords in app store page
     *
     * @throws InterruptedException
     */
    public void searchAppByKeyword(String[] keywords) throws InterruptedException, UiObjectNotFoundException {
        //Input search key and search
        for(int i=0; i< keywords.length; i++){
            device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").text(keywords[i])).click();
            Thread.sleep(50);
        }
        moveRightForMultiple(2);
    }

    /**
     * Navigate to All Category page by Menu popup in app store page
     *
     * @throws InterruptedException
     */
    public void navigateToAllCategoryPageByMenuPopUp() throws InterruptedException, UiObjectNotFoundException {
        menu();
        waitForElementPresentByID("android:id/tv_fun_menu_icon");
        UiObject categoryIcon = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("全部分类"));
        categoryIcon.clickAndWaitForNewWindow();
    }

    /**
     * Goto App Clean page
     *
     * @throws InterruptedException
     */
    public void gotoAppCleanPageFromAppUninstallPage(Boolean NeedToAppMTab) throws InterruptedException, UiObjectNotFoundException {
        if (NeedToAppMTab) {
            moveToAppStoreTargetTab(appStoreTabs[5]);
        }
        gotoAppUninstallPage();
        moveDown();
        menu();
        waitForElementPresentByID("android:id/tv_fun_menu_text");
        UiObject appCleanObj = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("清理数据"));
        appCleanObj.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.master:id/uninstall");
        waitForElementPresentByID("tv.fun.master:id/clearData");
    }

    /**
     * Uninstall App from App Clean page and back
     *
     * @throws InterruptedException
     */
    public void uninstallAppFromAppCleanPage(String targetAppName) throws InterruptedException, UiObjectNotFoundException {
        String appNumStr = findElementByID("tv.fun.master:id/appNumber").getText().replace("应用数量 ", "").replace(" 个", "");
        int appNum = stringToInt(appNumStr);
        int targetAppIndex = -1;
        UiObject eachAppObj = null;
        moveRightForMultiple(2);
        for (int loopTime = 0; loopTime < appNum; ) {
            UiObject appList = findElementByID("tv.fun.master:id/listView");
            int displayedAppCount = appList.getChildCount();
            for (int k = 0; k < displayedAppCount; k++) {
                loopTime++;
                UiObject appListObj = findElementByID("tv.fun.master:id/listView");
                eachAppObj = appListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(k));
                UiObject appObj = eachAppObj.getChild(new UiSelector().resourceId("tv.fun.master:id/appNameView"));
                String appName = appObj.getText();
                if (targetAppName.equalsIgnoreCase(appName)) {
                    targetAppIndex = k;
                    break;
                } else if (loopTime < appNum && k == displayedAppCount - 1) {
                    k--;
                }
                moveDown();
            }
            break;
        }
        if (targetAppIndex > 0) {
            UiObject uninstallBtnOfApp = eachAppObj.getChild(new UiSelector().resourceId("tv.fun.master:id/uninstall"));
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);//cannot use click method in here
            waitForElementPresentByID("com.android.packageinstaller:id/uninstall_confirm");
            //卸载弹框弹出后，点击“确定”
            UiObject confirmBtn = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/ok_button"));
            UiObject cancelBtn = device.findObject(new UiSelector().resourceId("com.android.packageinstaller:id/cancel_button"));
            confirmBtn.click();
            waitForElementNotPresentByID("com.android.packageinstaller:id/uninstall_confirm");
        }
        device.pressBack();
    }

    /**
     * Method for going to app uninstall page
     *
     * @throws UiObjectNotFoundException
     */
    public void gotoAppUninstallPage() throws UiObjectNotFoundException, InterruptedException {
        UiObject appUninstallCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall"));
        appUninstallCard.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/title");
        waitForElementPresentByID("tv.fun.appstore:id/appName");
    }

    /**
     * Uninstall App in App Uninstall page and back
     *
     * @throws InterruptedException
     */
    public void uninstallAppInAppUninstallPage(String targetApp) throws InterruptedException, UiObjectNotFoundException {
        gotoAppUninstallPage();
        uninstallAppFromAppCleanPage(targetApp);
    }

    /**
     * wait for app detail page display
     */
    public void waitForAppDetailPageDisplay() throws InterruptedException {
        waitForElementPresentByClassAndText("android.widget.TextView", "操控设备：");
    }

    /**
     * Method for going to
     *
     * @throws UiObjectNotFoundException
     */
    public void goToAppUninstallPage() throws UiObjectNotFoundException, InterruptedException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面---Need Update
        enterAppStorePage();
        UiObject tjTab = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/column_title").text("推荐"));
        if (!tjTab.isSelected()) {
            moveUpForMultiple(4);
            device.pressDPadUp();
        }
        moveRightForMultiple(5);
        device.pressDPadDown();
        device.pressDPadRight();
        UiObject appUninstallCard = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/tool_uninstall"));
        appUninstallCard.clickAndWaitForNewWindow();
    }

    /**
     * Move to the first card in App Store page
     */
    public void moveToFirstCardUnderSuggestTab() throws UiObjectNotFoundException {
        moveToTopOnAppStoreHomePage();
        moveToAppStoreTargetTab(appStoreTabs[0]);
        moveDown();
    }

    /**
     * Move to the first app in App Store page
     */
    public void moveToFirstAppUnderSuggestTab() throws UiObjectNotFoundException {
        moveToTopOnAppStoreHomePage();
        moveToAppStoreTargetTab(appStoreTabs[0]);
        moveDown();
        moveToTheMostLeftOfTabPage();
        moveRight();
    }

    /**
     * Enter App Store page and install multiple app in first child list under one tab
     */
    public void installMultipleAppsInFirstChildList(String targetTab, int needApps) throws InterruptedException, UiObjectNotFoundException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        moveToAppStoreTargetTab(targetTab);//appStoreTabs[4]
        UiObject firstApp = null;
        firstApp = device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/image").index(0));
        firstApp.clickAndWaitForNewWindow();
        UiObject childList = findElementByID("tv.fun.appstore:id/list");
        UiObject firstChild = childList.getChild(new UiSelector().resourceId("tv.fun.appstore:id/title"));
        firstChild.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/all_apps_view");
        //安装应用
        for (int i = 0; i < needApps; i++) {
            UiObject appList = findElementByID("tv.fun.appstore:id/all_apps_view");
            UiObject oneAppLine = appList.getChild(new UiSelector().className("android.widget.FrameLayout").index(i));
            UiObject oneApp = oneAppLine.getChild(new UiSelector().resourceId("tv.fun.appstore:id/all_app_title"));
            oneApp.clickAndWaitForNewWindow();
            waitForAppDetailPageDisplay();
            installAppInDetailPageAndBack();
        }
    }
}
