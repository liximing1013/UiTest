package tv.fun.appstoretest.common;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.view.KeyEvent;

/**
 * Created by liuqing on 2016/10/24.
 */
public class MasterApp extends AppStorePage {
    public String[] masterCards = {"一键优化", "内存加速", "垃圾清理", "网络测速", "应用清理", "自启动管理", "网络诊断"};

    /**
     * 在Launcher应用tab页面，点击“电视助手”卡片
     */
    public void enterTVMasterPage() throws UiObjectNotFoundException, InterruptedException {
        //移动焦点到Launcher应用tab
        if(!findElementByID(launcherTabID).exists()){
            home();
        }
        moveToLauncherTargetTab(appTab);
        //点击“电视助手”卡片
        UiObject tvMasterCard = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(tvMasterIconName));
        moveDownForMultiple(2);
        moveRight();
        if (!tvMasterCard.isSelected()) {
            moveUpForMultiple(2);
            moveLeft();
            moveRight();
            moveDownForMultiple(2);
            moveRight();
        }
        enter();
        waitForElementPresentByID("tv.fun.master:id/iv_settings");
    }

    /**
     * Get the app size in app clean page
     */
    public float getAppSizeInAppClean(int index) throws UiObjectNotFoundException {
        UiObject listView = findElementByID("tv.fun.master:id/listView");
        UiObject firstApp = listView.getChild(new UiSelector().className("android.widget.RelativeLayout").index(index));
        UiObject sizeOfFirstApp = firstApp.getChild(new UiSelector().resourceId("tv.fun.master:id/totalSize"));
        String firstAppSizeStr = sizeOfFirstApp.getText().replace("MB", "").replace("总计 ", "");//总计 41.6MB
        float firstAppSize = stringToFloat(firstAppSizeStr);
        return firstAppSize;
    }

    /**
     * Goto App Clean page in TV Master
     */
    public void gotoAppCleanPageInMaster() throws UiObjectNotFoundException, InterruptedException {
        //点击"应用清理"卡片
        UiObject cleanCard = findElementByText(masterCards[4], "tv.fun.master:id/home_item_title");
        cleanCard.clickAndWaitForNewWindow();
        waitForElementPresentByIDAndText("tv.fun.master:id/title", masterCards[4]);
    }

    /**
     * Get the app number in App Clean page by top msg
     */
    public int getAppNumInAppCleanPage() throws UiObjectNotFoundException, InterruptedException {
        //在应用清理页面，通过头部的信息得到已安装的应用个数
        UiObject appNumObj = findElementByID("tv.fun.master:id/appNumber");
        String appNumTxt = appNumObj.getText().replace("应用数量 ", "").replace(" 个", "");
        int appNum = stringToInt(appNumTxt);
        return appNum;
    }

    /**
     * Get the app total size in App Clean page by top msg
     */
    public float getAppTotalSizeInAppCleanPage() throws UiObjectNotFoundException, InterruptedException {
        //在应用清理页面，通过头部的信息得到已安装的应用占用空间
        UiObject appSizeObj = findElementByID("tv.fun.master:id/spaceUsage");
        String appSizemTxt = appSizeObj.getText().replace("占用空间 ", "").replace(" MB", "");
        float appSize = stringToFloat(appSizemTxt);
        return appSize;
    }

    /**
     * Get the app name in App Clean list
     */
    public String getAppNameInAppCleanPage(int index) throws UiObjectNotFoundException {
        UiObject listView = findElementByID("tv.fun.master:id/listView");
        UiObject firstApp = listView.getChild(new UiSelector().className("android.widget.RelativeLayout").index(index));
        String firstAppName = firstApp.getChild(new UiSelector().resourceId("tv.fun.master:id/appNameView")).getText();
        return firstAppName;
    }

    /**
     * Click "网络诊断"卡片，进入页面
     */
    public void gotoAutoLaunchPage() throws UiObjectNotFoundException, InterruptedException {
        //Launcher应用tab页面，点击电视助手
        enterTVMasterPage();
        //点击“自启动管理”
        moveRightForMultiple(5);
        UiObject cardObj = findElementByText("自启动管理", "tv.fun.master:id/home_item_title");
        cardObj.clickAndWaitForNewWindow();
    }

    /**
     * Click "自启动管理"卡片，进入页面
     */
    public void gotoAutoLaunchPageFromMaster() throws UiObjectNotFoundException {
        moveRightForMultiple(5);
        UiObject cardObj = findElementByText("自启动管理", "tv.fun.master:id/home_item_title");
        cardObj.clickAndWaitForNewWindow();
    }

    /**
     * prepare one app with auto launch
     */
    public void prepareAppWithAutoLaunch() throws InterruptedException, UiObjectNotFoundException {
        //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面
        enterAppStorePage();
        menu();
        waitForElementPresentByID("android:id/tv_fun_menu_icon");
        UiObject searchIcon = device.findObject(new UiSelector().resourceId("android:id/tv_fun_menu_text").text("搜索"));
        searchIcon.clickAndWaitForNewWindow();
        waitForElementPresentByID("tv.fun.appstore:id/search_single_key");
        //Input search key and search
        //Input search key and search
        for(int i=0; i< keywordForAutoLApp.length; i++){
            device.findObject(new UiSelector().resourceId("tv.fun.appstore:id/search_single_key").text(keywordForAutoLApp[i])).click();
        }
        moveRightForMultiple(6);
        UiObject appObj = findElementByID("tv.fun.appstore:id/app_title");
        device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);
        waitForAppDetailPageDisplay();
        installAppInDetailPage();
    }

    /**
     * Click "网络诊断"卡片，进入页面
     */
    public void gotoNetworkCheckPageFromMaster() throws UiObjectNotFoundException {
        moveRightForMultiple(5);
        UiObject cardObj = findElementByText("网络诊断", "tv.fun.master:id/home_item_title");
        cardObj.clickAndWaitForNewWindow();
    }

    /**
     * Click 设置按钮，进入页面
     */
    public void gotoSettingPageFromMaster() throws UiObjectNotFoundException {
        UiObject setObj = findElementByID("tv.fun.master:id/iv_settings");
        setObj.clickAndWaitForNewWindow();
    }

}
