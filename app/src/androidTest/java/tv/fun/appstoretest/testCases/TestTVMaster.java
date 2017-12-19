package tv.fun.appstoretest.testCases;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.view.KeyEvent;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import tv.fun.appstoretest.common.MasterApp;
import tv.fun.common.Utils;


/**
 * Created by liuqing on 2016/9/7.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestTVMaster extends MasterApp {
    public int optionNumOfNetworkCheck = 10;

    /**
     * 验证可以通过点击Launcher应用页面“电视助手”卡片，进入电视助手页面
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category({LevelP2Tests.class, LevelP1Tests.class})
    @Test
    public void Master_Home_01_testEnterTVMasterFromLauncher() throws IOException {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //Assert
            UiObject tvMasterPageTitleObj = device.findObject(new UiSelector().resourceId("tv.fun.master:id/tv_master_title"));
            String tvMasterPageTitle = tvMasterPageTitleObj.getText();
            UiObject settingBtn = device.findObject(new UiSelector().resourceId("tv.fun.master:id/iv_settings"));
            verifyString("电视助手页面中标题显示不正确", tvMasterPageTitle, tvMasterIconName);
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
     * 验证可以通过点击Launcher应用页面“电视助手”卡片，进入电视助手页面, 电视助手UI显示正确
     *
     * @throws UiObjectNotFoundException
     * @throws InterruptedException
     */
    @Category(LevelP1Tests.class)
    @Test
    public void Master_Home_01_testTVMasterUIDisplay() throws UiObjectNotFoundException {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //Assert
            UiObject tvMasterPageTitleObj = device.findObject(new UiSelector().resourceId("tv.fun.master:id/tv_master_title"));
            String tvMasterPageTitle = tvMasterPageTitleObj.getText();
            UiObject settingBtn = device.findObject(new UiSelector().resourceId("tv.fun.master:id/iv_settings"));
            verifyElementPresent("电视助手页面中设置按钮没有显示", settingBtn);
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
     * Test that no reponse when press Menu btn in TV Master page
     */
    @Test
    public void Master_Home_03_testNotMenuActionInMasterPage() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //按遥控器Menu键
            menu();
            Thread.sleep(200);
            //断言
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
     * Test that it can be back to Launcher home page by clicking Home btn on TV Master page
     */
    @Test
    public void Master_Home_04_testClickHomeBtnToLauncherHomePage() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            UiObject speedCard = findElementByText(masterCards[1], "tv.fun.master:id/home_item_title");
            verifyElementPresent("", speedCard);
            //按遥控器Back键，并验证回到Launcher App page
            device.pressHome();
            waitForElementNotPresentByID("tv.fun.master:id/home_item_title");
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
     * Test that it can be back from tv master page to Launcher app home page
     */
    @Test
    public void Master_Home_05_testBackFromMasterToLauncherApp() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            UiObject speedCard = findElementByText(masterCards[1], "tv.fun.master:id/home_item_title");
            verifyElementPresent("", speedCard);
            //按遥控器Back键，并验证回到Launcher App page
            back();
            waitForElementNotPresentByID("tv.fun.master:id/home_item_title");
            waitForElementPresentByIDAndText("com.bestv.ott:id/title", "电视助手");
            UiObject tvMasterCard = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/title").text(tvMasterIconName));
            UiObject appTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(launcherAppTab));
            verifyElementPresent("", tvMasterCard);
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
     * Test that the UI of Memory Speed page
     */
    @Test
    public void Master_Memory_01_testMemorySpeedPageUIDisplay() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"内存加速"卡片
            UiObject speedCardObj = findElementByText("内存加速", "tv.fun.master:id/home_item_title");
            UiObject statusObj = speedCardObj.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
            String statusText = statusObj.getText();
            verifyIncludeString("", statusText, "内存已占用");
            speedCardObj.clickAndWaitForNewWindow();
            Thread.sleep(50);
            if (findElementByID("tv.fun.master:id/btn_start_clear").exists()) {
                UiObject oneKeyClean = findElementByID("tv.fun.master:id/btn_start_clear");
                verifyString("", oneKeyClean.getText(), "一键清理");
                UiObject listView = findElementByID("tv.fun.master:id/firstscene");
                UiObject title = listView.getChild(new UiSelector().resourceId("tv.fun.master:id/title"));
                String textOfTitle = title.getText();//可清理内存5MB
                UiObject pageLine = listView.getChild(new UiSelector().resourceId("tv.fun.master:id/line"));
                UiObject appList = listView.getChild(new UiSelector().resourceId("tv.fun.master:id/listview"));
                UiObject firstApp = appList.getChild(new UiSelector().className("android.widget.LinearLayout").index(0));
                UiObject firstAppNameObj = firstApp.getChild(new UiSelector().resourceId("tv.fun.master:id/app_name"));
                UiObject firstAppMemoryObj = firstApp.getChild(new UiSelector().resourceId("tv.fun.master:id/app_memory"));
                verifyElementPresent("", pageLine);
                verifyIncludeString("", textOfTitle, "可清理内存");
                verifyElementPresent("", firstAppNameObj);
                verifyIncludeString("", firstAppMemoryObj.getText(), "MB");
            }else {
                UiObject memoryPic = findElementByID("tv.fun.master:id/dv_dial_memory");
                UiObject finishBtn = findElementByText("完成", "tv.fun.master:id/finish");
                verifyElementPresent("", memoryPic);
                verifyElementPresent("", finishBtn);
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
     * Test that no response when pressing Menu btn in memory speed page
     */
    @Test
    public void Master_Memory_06_testNoMenuActionInMemoryPage() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"内存加速"卡片
            UiObject speedCardObj = findElementByText("内存加速", "tv.fun.master:id/home_item_title");
            UiObject statusObj = speedCardObj.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
            speedCardObj.clickAndWaitForNewWindow();
            Thread.sleep(50);
            //按遥控器Menu键
            menu();
            Thread.sleep(500);
            //断言
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
     * Test that can back to Launcher home via pressing Home btn in memory speed page
     */
    @Test
    public void Master_Memory_07_testBackToLauncherHomeByHomeInMemoryPage() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"内存加速"卡片
            UiObject speedCardObj = findElementByText("内存加速", "tv.fun.master:id/home_item_title");
            UiObject statusObj = speedCardObj.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
            speedCardObj.clickAndWaitForNewWindow();
            Thread.sleep(50);
            //按遥控器Home键
            home();
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(launcherVideoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTab.isSelected());
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
     * Test that can back to TV Master home via pressing Back btn in memory speed page
     */
    @Test
    public void Master_Memory_08_testBackToMasterPageByBackInMemoryPage() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"内存加速"卡片
            UiObject speedCardObj = findElementByText("内存加速", "tv.fun.master:id/home_item_title");
            UiObject statusObj = speedCardObj.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
            speedCardObj.clickAndWaitForNewWindow();
            Thread.sleep(50);
            //按遥控器Home键
            back();
            //断言
            UiObject masterObj = device.findObject(new UiSelector().resourceId("tv.fun.master:id/tv_master_title").text("电视助手"));
            verifyElementPresent("", masterObj);
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
     * Test the UI of Garbage Clean page displays correctly
     */
    @Test
    public void Master_Refuse_01_testRefuseCleanpageUI() {
        String[] optionsInCleanList = {"缓存垃圾", "卸载残留", "无用安装包", "空文件夹", "系统缓存"};
        int percentOfApp = 0;
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"垃圾清理"卡片
            UiObject refuseCleanObj = findElementByText("垃圾清理", "tv.fun.master:id/home_item_title");
            UiObject statusObj = refuseCleanObj.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
            String statusText = statusObj.getText();
            percentOfApp = stringToInt(statusText.replace("空间已占用", "").replace("%", ""));
            verifyIncludeString("", statusText, "空间已占用");
            refuseCleanObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.master:id/bt_start_clean");
            if (findElementByID("tv.fun.master:id/bt_start_clean").exists()) {
                UiObject titleObj = findElementByID("tv.fun.master:id/tv_title");
                String pageTitle = titleObj.getText();
                verifyIncludeString("", pageTitle, "扫描出");
                verifyIncludeString("", pageTitle, "B可清理文件");
                UiObject resultList = findElementByID("tv.fun.master:id/lv_scan_result");
                int size = resultList.getChildCount();
                verifyTrue("", size == optionsInCleanList.length);
                for (int i = 0; i < size; i++) {
                    UiObject option = resultList.getChild(new UiSelector().className("android.widget.LinearLayout").index(i));
                    UiObject optionNameObj = option.getChild(new UiSelector().resourceId("tv.fun.master:id/app_name"));
                    String optionName = optionNameObj.getText();
                    verifyString("", optionName, optionsInCleanList[i]);
                }
                UiObject cleanBtn = findElementByID("tv.fun.master:id/bt_start_clean");
                verifyString("", cleanBtn.getText(), "一键清理");
                cleanBtn.click();
                Thread.sleep(500);
                waitForElementPresentByID("tv.fun.master:id/bt_done");
                UiObject finishBtn = findElementByID("tv.fun.master:id/bt_done");
                verifyString("", finishBtn.getText(), "完成");
                UiObject status = findElementByID("tv.fun.master:id/tv_cleaned_counter");
                UiObject info = findElementByID("tv.fun.master:id/tv_so_far_cleaned_size");
                verifyString("", status.getText(), "已清理");
                verifyString("", info.getText(), "累计清理");
                verifyString("", info.getText(), "B垃圾");
                finishBtn.click();
                waitForElementPresentByID("tv.fun.master:id/home_item_status");
                UiObject statusObjAfterClean = refuseCleanObj.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
                String statusTextAfterClean = statusObj.getText().replace("空间已占用", "").replace("%", "");
                int percentAfterClean = stringToInt(statusTextAfterClean);
                verifyTrue("", percentOfApp >= percentAfterClean);
                //再次点击"垃圾清理"卡片
                UiObject cleanObj = findElementByText("垃圾清理", "tv.fun.master:id/home_item_title");
                percentOfApp = percentAfterClean;
                cleanObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.master:id/bt_done");
            }
            if (findElementByID("tv.fun.master:id/dv_dial_storage").exists()) {
                UiObject ciricl = findElementByID("tv.fun.master:id/dv_dial_storage");
                int actualPercentInPage = stringToInt(findElementByID("tv.fun.master:id/tv_storage_occupy_percent").getText());
                verifyTrue("", actualPercentInPage == percentOfApp);
                UiObject finishBtnAfterClean = findElementByID("tv.fun.master:id/bt_done");
                verifyString("", finishBtnAfterClean.getText(), "完成");
                UiObject msg = findElementByClass("android.widget.RelativeLayout").getChild(new UiSelector().className("android.widget.TextView").index(5));
                verifyString("", msg.getText(), "非常干净 无需清理");
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
     * Test that can clean the refuse of TV correctly
     */
    @Test
    public void Master_Refuse_02_testCleanRefuse() {
        String[] optionsInCleanList = {"缓存垃圾", "卸载残留", "无用安装包", "空文件夹", "系统缓存"};
        int percentOfApp = 0;
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"垃圾清理"卡片
            UiObject refuseCleanObj = findElementByText("垃圾清理", "tv.fun.master:id/home_item_title");
            UiObject statusObj = refuseCleanObj.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
            String statusText = statusObj.getText();
            percentOfApp = stringToInt(statusText.replace("空间已占用", "").replace("%", ""));
            refuseCleanObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.master:id/bt_start_clean");
            if (findElementByID("tv.fun.master:id/bt_start_clean").exists()) {
                UiObject titleObj = findElementByID("tv.fun.master:id/tv_title");
                String pageTitle = titleObj.getText();
                verifyIncludeString("", pageTitle, "扫描出");
                verifyIncludeString("", pageTitle, "B可清理文件");
                UiObject resultList = findElementByID("tv.fun.master:id/lv_scan_result");
                int size = resultList.getChildCount();
                verifyTrue("", size == optionsInCleanList.length);
                for (int i = 0; i < size; i++) {
                    UiObject option = resultList.getChild(new UiSelector().className("android.widget.LinearLayout").index(i));
                    UiObject optionNameObj = option.getChild(new UiSelector().resourceId("tv.fun.master:id/app_name"));
                    String optionName = optionNameObj.getText();
                    verifyString("", optionName, optionsInCleanList[i]);
                }
                UiObject cleanBtn = findElementByID("tv.fun.master:id/bt_start_clean");
                verifyString("", cleanBtn.getText(), "一键清理");
                cleanBtn.click();
                waitForElementPresentByID("tv.fun.master:id/bt_done");
                UiObject finishBtn = findElementByID("tv.fun.master:id/bt_done");
                verifyString("", finishBtn.getText(), "完成");
                UiObject status = findElementByID("tv.fun.master:id/tv_cleaned_counter");
                UiObject info = findElementByID("tv.fun.master:id/tv_so_far_cleaned_size");
                verifyString("", status.getText(), "已清理");
                verifyString("", info.getText(), "累计清理");
                verifyString("", info.getText(), "B垃圾");
                finishBtn.click();
                waitForElementPresentByID("tv.fun.master:id/home_item_status");
                UiObject statusObjAfterClean = refuseCleanObj.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
                String statusTextAfterClean = statusObj.getText().replace("空间已占用", "").replace("%", "");
                int percentAfterClean = stringToInt(statusTextAfterClean);
                verifyTrue("", percentOfApp >= percentAfterClean);
                UiObject masterPageTitle = findElementByText("电视助手", "tv.fun.master:id/tv_master_title");
                verifyElementPresent("", masterPageTitle);
                //再次点击"垃圾清理"卡片
                UiObject cleanObj = findElementByText("垃圾清理", "tv.fun.master:id/home_item_title");
                percentOfApp = percentAfterClean;
                cleanObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.master:id/bt_done");
            }
            if (findElementByID("tv.fun.master:id/dv_dial_storage").exists()) {
                UiObject finishBtnClean = findElementByID("tv.fun.master:id/bt_done");
                verifyString("", finishBtnClean.getText(), "完成");
                finishBtnClean.click();
                waitForElementPresentByID("tv.fun.master:id/tv_master_title");
                UiObject pageTitle = findElementByText("电视助手", "tv.fun.master:id/tv_master_title");
                verifyElementPresent("", pageTitle);
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
     * Test that No repsonse when pressing menu in refuse clean page
     */
    @Test
    public void Master_Refuse_03_testNoMenuActionInRefuseCleanpage() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"垃圾清理"卡片
            UiObject refuseCleanObj = findElementByText("垃圾清理", "tv.fun.master:id/home_item_title");
            refuseCleanObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.master:id/bt_start_clean");
            //按遥控器Menu键
            menu();
            Thread.sleep(500);
            //断言
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
     * Test that can back to Launcher home page after pressing home btn in refuse clean page
     */
    @Test
    public void Master_Refuse_04_testBackToLauncherHomeFromRefuseCleanByHome() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"垃圾清理"卡片
            UiObject refuseCleanObj = findElementByText("垃圾清理", "tv.fun.master:id/home_item_title");
            refuseCleanObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.master:id/bt_start_clean");
            //按遥控器Home键
            home();
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(launcherVideoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTab.isSelected());
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
     * Test that can back to TV Master home page after pressing back btn in refuse clean page
     */
    @Test
    public void Master_Refuse_05_testBackToMasterHomeFromRefuseCleanByBack() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"垃圾清理"卡片
            UiObject refuseCleanObj = findElementByText("垃圾清理", "tv.fun.master:id/home_item_title");
            refuseCleanObj.clickAndWaitForNewWindow();
            waitForElementPresentByID("tv.fun.master:id/bt_start_clean");
            //按遥控器Back键
            back();
            //断言
            UiObject masterObj = device.findObject(new UiSelector().resourceId("tv.fun.master:id/tv_master_title").text("电视助手"));
            verifyElementPresent("", masterObj);
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
     * Test that the UI of NetWork Speed Check page displays correctly
     */
    @Test
    public void Master_Speed_01_testNetWorkSpeedCheckPageUI() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"网络测速"卡片
            UiObject speedCheckObj = findElementByText("网络测速", "tv.fun.master:id/home_item_title");
            speedCheckObj.clickAndWaitForNewWindow();
            Thread.sleep(2000);
            waitForElementPresentByID("tv.fun.master:id/retry_button");
            //断言
            UiObject speedCard = findElementByID("tv.fun.master:id/success_layout");
            UiObject speedMSG = speedCard.getChild(new UiSelector().className("android.widget.TextView"));
            verifyString("", speedCard.getText(), "平均下载速度");
            UiObject speedObj = findElementByID("tv.fun.master:id/speed_value");
            float speedValue = stringToFloat(speedObj.getText());
            verifyTrue("", speedValue > 1);
            UiObject unit = findElementByID("tv.fun.master:id/speed_uint");
            verifyString("", unit.getText(), "MB/s");
            UiObject result = findElementByID("tv.fun.master:id/bandwidth_text");
            verifyElementPresent("", result);
            UiObject retry = findElementByID("tv.fun.master:id/retry_button");
            verifyString("", retry.getText(), "重新测速");
            UiObject finish = findElementByID("tv.fun.master:id/finish_button");
            verifyString("", finish.getText(), "完 成");
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
     * Test that NetWork Speed Check function works correctly
     */
    @Test
    public void Master_Speed_03_testNetWorkSpeedCheckWork() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"网络测速"卡片
            UiObject speedCheckObj = findElementByText("网络测速", "tv.fun.master:id/home_item_title");
            speedCheckObj.clickAndWaitForNewWindow();
            Thread.sleep(2000);
            waitForElementPresentByID("tv.fun.master:id/retry_button");
            //断言
            UiObject speedCard = findElementByID("tv.fun.master:id/success_layout");
            UiObject speedMSG = speedCard.getChild(new UiSelector().className("android.widget.TextView").index(0));
            verifyString("", speedCard.getText(), "平均下载速度");
            UiObject speedObj = findElementByID("tv.fun.master:id/speed_value");
            float speedValue = stringToFloat(speedObj.getText());
            verifyTrue("", speedValue > 1);
            UiObject unit = findElementByID("tv.fun.master:id/speed_uint");
            verifyString("", unit.getText(), "MB/s");
            UiObject result = findElementByID("tv.fun.master:id/bandwidth_text");
            verifyElementPresent("", result);
            UiObject retry = findElementByID("tv.fun.master:id/retry_button");
            verifyString("", retry.getText(), "重新测速");
            UiObject finish = findElementByID("tv.fun.master:id/finish_button");
            verifyString("", finish.getText(), "完 成");
            finish.click();
            UiObject masterObj = device.findObject(new UiSelector().resourceId("tv.fun.master:id/tv_master_title").text("电视助手"));
            verifyElementPresent("", masterObj);
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
     * Test that No repsonse when pressing menu in NetWork Speed Check page
     */
    @Test
    public void Master_Speed_12_testNoMenuActionInNetWorkSpeedCheckpage() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"网络测速"卡片
            UiObject SpeedCheckObj = findElementByText("网络测速", "tv.fun.master:id/home_item_title");
            SpeedCheckObj.clickAndWaitForNewWindow();
            //按遥控器Menu键
            menu();
            Thread.sleep(500);
            //断言
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
     * Test that can back to Launcher home page after pressing home btn in NetWork Speed Check page
     */
    @Test
    public void Master_Speed_13_testBackToLauncherFromSpeedCheckByHome() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"网络测速"卡片
            UiObject SpeedCheckObj = findElementByText("网络测速", "tv.fun.master:id/home_item_title");
            SpeedCheckObj.clickAndWaitForNewWindow();
            Thread.sleep(2000);
            waitForElementPresentByID("tv.fun.master:id/retry_button");
            verifyElementPresent("", findElementByID("tv.fun.master:id/retry_button"));
            //按遥控器Home键
            home();
            waitForElementPresentByIDAndText("com.bestv.ott:id/tab_title", launcherVideoTab);
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(launcherVideoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTab.isSelected());
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
     * Test that can back to TV Master home page after pressing back btn in Network speed check page
     */
    @Test
    public void Master_Speed_14_testBackToMasterHomeFromSpeedCheckByBack() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"网络测速"卡片
            UiObject SpeedCheckObj = findElementByText("网络测速", "tv.fun.master:id/home_item_title");
            SpeedCheckObj.clickAndWaitForNewWindow();
            Thread.sleep(2000);
            waitForElementPresentByID("tv.fun.master:id/retry_button");
            verifyElementPresent("", findElementByID("tv.fun.master:id/retry_button"));
            //按遥控器Back键
            back();
            //断言
            UiObject masterObj = device.findObject(new UiSelector().resourceId("tv.fun.master:id/tv_master_title").text("电视助手"));
            verifyElementPresent("", masterObj);
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
     * Test that the UI of APP Clean page displays correctly
     */
    @Test
    public void Master_Clean_01_testAppCleanUI() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            UiObject cleanCard = findElementByText(masterCards[4], "tv.fun.master:id/home_item_title");
            UiObject statusObj = cleanCard.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
            String statusText = statusObj.getText();
            verifyIncludeString("", statusText, "已安装");
            verifyIncludeString("", statusText, "款应用");
            //点击“应用清理”卡片
            cleanCard.clickAndWaitForNewWindow();
            UiObject titleObj = findElementByID("tv.fun.master:id/title");
            verifyElementPresent("", titleObj);
            verifyString("", titleObj.getText(), masterCards[4]);
            if (findElementByID("tv.fun.master:id/appNameView").exists()) {
                UiObject appNumObj = findElementByID("tv.fun.master:id/appNumber");
                UiObject spaceObj = findElementByID("tv.fun.master:id/spaceUsage");
                verifyIncludeString("", appNumObj.getText(), "应用数量");
                verifyIncludeString("", spaceObj.getText(), "占用空间");
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
     * Test that can uninstall app successfully in app clean page
     */
    @Test
    public void Master_Clean_05_testUninstallAppInAppCleanPage() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"应用清理"卡片, 进入应用清理页面
            gotoAppCleanPageInMaster();
            //准备数据
            if (!findElementByID("tv.fun.master:id/appNumber").exists()) {
                backForMultiple(2);
                installMultipleAppsInFirstChildList(appStoreTabs[4], 1);
                home();
                //Launcher应用tab页面，点击电视助手
                enterTVMasterPage();
                //点击"应用清理"卡片, 进入应用清理页面
                gotoAppCleanPageInMaster();
            }
            String nameOfFirstApp = getAppNameInAppCleanPage(0);
            if (nameOfFirstApp.toLowerCase().contains("test")) {
                backForMultiple(2);
                installMultipleAppsInFirstChildList(appStoreTabs[4], 1);
                home();
                //Launcher应用tab页面，点击电视助手
                enterTVMasterPage();
                //点击"应用清理"卡片, 进入应用清理页面
                gotoAppCleanPageInMaster();
            }
            UiObject listView = findElementByID("tv.fun.master:id/listView");
            UiObject firstApp = listView.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
            String firstAppName = firstApp.getChild(new UiSelector().resourceId("tv.fun.master:id/appNameView")).getText();
            String secondAppName = getAppNameInAppCleanPage(1);
            UiObject uninstallBtn = firstApp.getChild(new UiSelector().resourceId("tv.fun.master:id/uninstall"));
            int appsNum = getAppNumInAppCleanPage();
//            float appsTotalSize = getAppTotalSizeInAppCleanPage();
//            float firstAppSize = getAppSizeInAppClean(0);
            moveRightForMultiple(3);
            //点击“卸载”按钮
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);//cannot use click method in here
            waitForElementPresentByID("com.android.packageinstaller:id/uninstall_confirm");
            UiObject appNameInPop = findElementByID("com.android.packageinstaller:id/app_name");
            String confirmMsg = findElementByID("com.android.packageinstaller:id/uninstall_confirm").getText();
            UiObject okBtn = findElementByID("com.android.packageinstaller:id/ok_button");
            String textInOkBtn = okBtn.getText();
            UiObject cancelBtn = findElementByID("com.android.packageinstaller:id/cancel_button");
            String textInCancelBtn = cancelBtn.getText();
            verifyString("", firstAppName, appNameInPop.getText());
            verifyString("", confirmMsg, "要卸载此应用吗？");
            verifyString("", textInOkBtn, "确定");
            verifyString("", textInCancelBtn, "取消");
            okBtn.click();
            waitForElementNotPresentByID("com.android.packageinstaller:id/uninstall_confirm");
            verifyElementNotPresent("", confirmMsg);
            waitForTextNotPresent("正在卸载");
            waitForTextNotPresent("卸载完成");
            String currentFirstAppName = getAppNameInAppCleanPage(0);
            verifyString("", currentFirstAppName, secondAppName);
            int currentAppNum = getAppNumInAppCleanPage();
            verifyTrue("", currentAppNum==appsNum-1);
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
     * Test that can cancel uninstalling app successfully in app clean page
     */
    @Test
    public void Master_Clean_06_testCancelUninstallAppInAppCleanPage() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"应用清理"卡片, 进入应用清理页面
            gotoAppCleanPageInMaster();
            //准备数据
            if (!findElementByID("tv.fun.master:id/appNumber").exists()) {
                backForMultiple(2);
                installMultipleAppsInFirstChildList(appStoreTabs[4], 1);
                home();
                //Launcher应用tab页面，点击电视助手
                enterTVMasterPage();
                //点击"应用清理"卡片, 进入应用清理页面
                gotoAppCleanPageInMaster();
            }
            UiObject listView = findElementByID("tv.fun.master:id/listView");
            UiObject firstApp = listView.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
            String firstAppName = firstApp.getChild(new UiSelector().resourceId("tv.fun.master:id/appNameView")).getText();
            UiObject uninstallBtn = firstApp.getChild(new UiSelector().resourceId("tv.fun.master:id/uninstall"));
            int appsNum = getAppNumInAppCleanPage();
            moveRightForMultiple(3);
            //点击“卸载”按钮
            device.pressKeyCode(KeyEvent.KEYCODE_DPAD_CENTER);//cannot use click method in here
            waitForElementPresentByID("com.android.packageinstaller:id/uninstall_confirm");
            UiObject appNameInPop = findElementByID("com.android.packageinstaller:id/app_name");
            String confirmMsg = findElementByID("com.android.packageinstaller:id/uninstall_confirm").getText();//
            UiObject okBtn = findElementByID("com.android.packageinstaller:id/ok_button");
            String textInOkBtn = okBtn.getText();
            UiObject cancelBtn = findElementByID("com.android.packageinstaller:id/cancel_button");
            String textInCancelBtn = cancelBtn.getText();
            verifyString("", firstAppName, appNameInPop.getText());
            verifyString("", confirmMsg, "要卸载此应用吗？");
            verifyString("", textInOkBtn, "确定");
            verifyString("", textInCancelBtn, "取消");
            cancelBtn.click();
            verifyElementNotPresent("", confirmMsg);
            String currentFirstAppName = getAppNameInAppCleanPage(0);
            verifyString("", currentFirstAppName, firstAppName);
            int currentAppNum = getAppNumInAppCleanPage();
            verifyTrue("", currentAppNum==appsNum);
        } catch (Throwable e) {
            e.printStackTrace();
            resultFlag = false;
            resultStr = e.toString();
        } finally {
            Utils.writeCaseResult(resultStr,
                    resultFlag, execTime);
        }
    }
//
//    /**
//     * Test that can clean app data successfully in app clean page
//     */
//    @Test
//    public void Master_Clean_07_testCleanAppDataInAppCleanPage() {
//        try {
//            //Launcher应用tab页面，点击电视助手
//            enterTVMasterPage();
//            //点击"应用清理"卡片
//            UiObject cleanCard = findElementByText(masterCards[4], "tv.fun.master:id/home_item_title");
//            cleanCard.clickAndWaitForNewWindow();
//            waitForElementPresentByIDAndText("tv.fun.master:id/title", masterCards[4]);
////TODO
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
     * Test that the apps in app clean page is ordered by its size in Desc
     */
    @Test
    public void Master_Clean_09_testCheckAppDisplayOrderInAppCleanPage() {
        int appNum;
        String[] appsSize;
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"应用清理"卡片, 进入应用清理页面
            gotoAppCleanPageInMaster();
            //准备数据
            if (!findElementByID("tv.fun.master:id/appNumber").exists()) {
                backForMultiple(2);
                installMultipleAppsInFirstChildList(appStoreTabs[4], 2);
                home();
                //Launcher应用tab页面，点击电视助手
                enterTVMasterPage();
                //点击"应用清理"卡片, 进入应用清理页面
                gotoAppCleanPageInMaster();
            }
            appNum = getAppNumInAppCleanPage();
            if (appNum < 2) {
                backForMultiple(2);
                installMultipleAppsInFirstChildList(appStoreTabs[4], 1);
                home();
                //Launcher应用tab页面，点击电视助手
                enterTVMasterPage();
                //点击"应用清理"卡片, 进入应用清理页面
                gotoAppCleanPageInMaster();
            }
            appNum = getAppNumInAppCleanPage();
            if (appNum > 4) {
                appNum = 4;
            }
            for (int i = 0; i < appNum - 1; i++) {
                moveRight();
                UiObject listView = findElementByID("tv.fun.master:id/listView");
                UiObject firstApp = listView.getChild(new UiSelector().className("android.widget.RelativeLayout").index(i));
                UiObject sizeOfFirstApp = firstApp.getChild(new UiSelector().resourceId("tv.fun.master:id/totalSize"));
                String firstAppSizeStr = sizeOfFirstApp.getText().replace("MB", "").replace("总计 ", "");//总计 41.6MB
                moveDown();
                float secondAppSize = getAppSizeInAppClean(i + 1);
                verifyTrue("", stringToFloat(firstAppSizeStr) >= secondAppSize);
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
     * Test that No repsonse when pressing menu in App Clean page
     */
    @Test
    public void Master_Clean_18_testNoMenuActionInAppCleanpage() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"应用清理"卡片
            gotoAppCleanPageInMaster();
            //按遥控器Menu键
            menu();
            Thread.sleep(500);
            //断言
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
     * Test that can back to Launcher home page after pressing home btn in App Clean page
     */
    @Test
    public void Master_Clean_19_testBackToLauncherFromAppCleanByHome() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"应用清理"卡片
            gotoAppCleanPageInMaster();
            //按遥控器Home键
            home();
            waitForElementPresentByIDAndText("com.bestv.ott:id/tab_title", launcherVideoTab);
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(launcherVideoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTab.isSelected());
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
     * Test that can back to TV Master home page after pressing back btn in App Clean page
     */
    @Test
    public void Master_Clean_20_testBackToMasterHomeFromAppCleanByBack() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"应用清理"卡片
            gotoAppCleanPageInMaster();
            //按遥控器Back键
            back();
            //断言
            UiObject masterObj = device.findObject(new UiSelector().resourceId("tv.fun.master:id/tv_master_title").text("电视助手"));
            verifyElementPresent("", masterObj);
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
     * Test that the UI of ap auto launch page displays correctly
     */
    @Test
    public void Master_AutoL_01_testAutoLaunchPageUI(){
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            moveRightForMultiple(5);
            //点击“自启动管理”卡片
            UiObject autoLObj = findElementByText("自启动管理", "tv.fun.master:id/home_item_title");
            UiObject statusObj = autoLObj.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
            String statusText = statusObj.getText();
            autoLObj.clickAndWaitForNewWindow();
            UiObject pageTitleObj = findElementByID("tv.fun.master:id/title");
            verifyString("", pageTitleObj.getText(), "自启动管理");
            if (findElementByID("tv.fun.master:id/toolbar").exists()) {
                UiObject appCountObj = findElementByID("tv.fun.master:id/app_count");
                int appCount = stringToInt(appCountObj.getText());
                String text = findElementByID("tv.fun.master:id/have").getText() + appCountObj.getFromParent(new UiSelector().className("android.widget.TextView").index(2)).getText();
                UiObject disableBtn = findElementByID("tv.fun.master:id/disable_all_btn");
                verifyElementPresent("", disableBtn);
                verifyString("", text, "有款应用自启动");
                verifyTrue("", appCount >= 0);
                back();
                if(appCount==0){
                    verifyIncludeString("", statusText, "无自启动应用");
                }else {
                    int autoLOptions = stringToInt(statusText.replace("款应用自启动", "").replace("有", ""));
                    verifyIncludeString("", statusText, "款应用自启动");
                    verifyTrue("", autoLOptions >= 1);
                }
            } else {
                UiObject msg = device.findObject(new UiSelector().className("android.widget.TextView").text("无自启动应用"));
                UiObject result = device.findObject(new UiSelector().className("android.widget.TextView").text("系统状态良好"));
                verifyElementPresent("", msg);
                verifyElementPresent("", result);
                back();
                verifyIncludeString("", statusText, "无自启动应用");
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
     * Test that the App option displays correctly in auto launch page
     */
    @Test
    public void Master_AutoL_02_testAppOptionDisplayInAutoLaunchPage(){
        try{
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            moveRightForMultiple(5);
            //点击“自启动管理”卡片
            UiObject autoLObj = findElementByText("自启动管理", "tv.fun.master:id/home_item_title");
            UiObject statusObj = autoLObj.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
            String statusText = statusObj.getText();
            autoLObj.clickAndWaitForNewWindow();
            if (!findElementByID("tv.fun.master:id/toolbar").exists()) {
                home();
                //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面,并安装有自启动管理的应用
                prepareAppWithAutoLaunch();
                home();
                //Launcher应用tab页面，点击电视助手，点击“自启动管理”卡片
                gotoAutoLaunchPage();
            }
            UiObject listView = findElementByID("tv.fun.master:id/listview");
            UiObject appObj = listView.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
            UiObject appImage = appObj.getChild(new UiSelector().resourceId("tv.fun.master:id/appImage"));
            UiObject appNameObj = appObj.getChild(new UiSelector().resourceId("tv.fun.master:id/appName"));
            UiObject curStatusObj = appObj.getChild(new UiSelector().resourceId("tv.fun.master:id/appStartState"));
            UiObject btnObj = appObj.getChild(new UiSelector().resourceId("tv.fun.master:id/switchButton"));
            UiObject disableAllBtn = findElementByID("tv.fun.master:id/disable_all_btn");
            verifyElementPresent("", appImage);
            verifyElementPresent("", appNameObj);
            verifyElementPresent("", curStatusObj);
            verifyElementPresent("", btnObj);
            verifyElementPresent("", disableAllBtn);
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
     * Test that can open the auto launch for one app
     */
    @Test
    public void Master_AutoL_03_testOpenAutoLaunchForApp(){
        UiObject curStatusObj=null;
        try{
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            moveRightForMultiple(5);
            //点击“自启动管理”卡片
            UiObject autoLObj = findElementByText("自启动管理", "tv.fun.master:id/home_item_title");
            UiObject statusObj = autoLObj.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
            autoLObj.clickAndWaitForNewWindow();
            if (!findElementByID("tv.fun.master:id/toolbar").exists()) {
                home();
                //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面,并安装有自启动管理的应用
                prepareAppWithAutoLaunch();
                home();
                //Launcher应用tab页面，点击电视助手，点击“自启动管理”卡片
                gotoAutoLaunchPage();
            }
            UiObject listView = findElementByID("tv.fun.master:id/listview");
            UiObject appObj = listView.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
            curStatusObj = appObj.getChild(new UiSelector().resourceId("tv.fun.master:id/appStartState"));
            UiObject btnObj = appObj.getChild(new UiSelector().resourceId("tv.fun.master:id/switchButton"));
            UiObject disableAllBtn = findElementByID("tv.fun.master:id/disable_all_btn");
            moveDown();
            moveUp();
            if(disableAllBtn.isFocused()){
                moveDown();
            }
            if("已允许".equalsIgnoreCase(curStatusObj.getText())){
                device.pressEnter();
                waitForElementNotPresent(findElementByText("已允许", "tv.fun.master:id/appStartState"));
            }
            curStatusObj = appObj.getChild(new UiSelector().resourceId("tv.fun.master:id/appStartState"));
            verifyString("", curStatusObj.getText(), "已禁止");
            device.pressEnter();
            waitForElementNotPresent(findElementByText("已禁止", "tv.fun.master:id/appStartState"));
            curStatusObj = appObj.getChild(new UiSelector().resourceId("tv.fun.master:id/appStartState"));
            verifyString("", curStatusObj.getText(), "已允许");
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
     * Test that can close the auto launch for one app
     */
    @Test
    public void Master_AutoL_04_testCloseAutoLaunchForApp(){
        UiObject curStatusObj=null;
        UiObject autoLObj = null;
        try{
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            moveRightForMultiple(5);
            //点击“自启动管理”卡片
            autoLObj = findElementByText("自启动管理", "tv.fun.master:id/home_item_title");
            UiObject statusObj = autoLObj.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
            autoLObj.clickAndWaitForNewWindow();
            if (!findElementByID("tv.fun.master:id/toolbar").exists()) {
                home();
                //在Launcher应用tab页面，点击应用市场卡片，进入应用市场页面,并安装有自启动管理的应用
                prepareAppWithAutoLaunch();
                home();
                //Launcher应用tab页面，点击电视助手，点击“自启动管理”卡片
                gotoAutoLaunchPage();
            }
            UiObject listView = findElementByID("tv.fun.master:id/listview");
            UiObject appObj = listView.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
            curStatusObj = appObj.getChild(new UiSelector().resourceId("tv.fun.master:id/appStartState"));
            UiObject btnObj = appObj.getChild(new UiSelector().resourceId("tv.fun.master:id/switchButton"));
            UiObject disableAllBtn = findElementByID("tv.fun.master:id/disable_all_btn");
            moveDown();
            moveUp();
            if(disableAllBtn.isFocused()){
                moveDown();
            }
            if("已禁止".equalsIgnoreCase(curStatusObj.getText())){
                device.pressEnter();
                waitForElementNotPresent(findElementByText("已禁止", "tv.fun.master:id/appStartState"));
            }
            curStatusObj = appObj.getChild(new UiSelector().resourceId("tv.fun.master:id/appStartState"));
            verifyString("", curStatusObj.getText(), "已允许");
            device.pressEnter();
            waitForElementNotPresent(findElementByText("已允许", "tv.fun.master:id/appStartState"));
            curStatusObj = appObj.getChild(new UiSelector().resourceId("tv.fun.master:id/appStartState"));
            verifyString("", curStatusObj.getText(), "已禁止");
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
     * Test that no response when pressing Menu btn in memory speed page
     */
    @Test
    public void Master_AutoL_07_testNoMenuActionInAutoLPage() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            moveRightForMultiple(5);
            //点击“自启动管理”卡片
            UiObject autoLObj = findElementByText("自启动管理", "tv.fun.master:id/home_item_title");
            autoLObj.clickAndWaitForNewWindow();
            //按遥控器Menu键
            menu();
            Thread.sleep(500);
            //断言
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
     * Test that can back to Launcher home via pressing Home btn in auto launch page
     */
    @Test
    public void Master_AutoL_08_testBackToLauncherHomeByHomeInAutoLPage() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            moveRightForMultiple(5);
            //点击“自启动管理”卡片
            UiObject autoLObj = findElementByText("自启动管理", "tv.fun.master:id/home_item_title");
            autoLObj.clickAndWaitForNewWindow();
            //按遥控器Home键
            home();
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(launcherVideoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTab.isSelected());
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
     * Test that can back to TV Master home via pressing Back btn in auto launch page
     */
    @Test
    public void Master_AutoL_09_testBackToMasterPageByBackIAutoLPage() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            moveRightForMultiple(5);
            //点击“自启动管理”卡片
            UiObject autoLObj = findElementByText("自启动管理", "tv.fun.master:id/home_item_title");
            autoLObj.clickAndWaitForNewWindow();
            //按遥控器Back键
            back();
            //断言
            UiObject masterObj = device.findObject(new UiSelector().resourceId("tv.fun.master:id/tv_master_title").text("电视助手"));
            verifyElementPresent("", masterObj);
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
     * Test the UI of network check page displays correctly when checking
     */
    @Test
    public void Master_WLZD_02_testNetworkCheckPageUIWhenChecking(){
        try{
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            moveRightForMultiple(5);
            //点击“网络诊断”卡片
            UiObject checkObj = findElementByText("网络诊断", "tv.fun.master:id/home_item_title");
            UiObject statusObj = checkObj.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
            String statusText = statusObj.getText();
            checkObj.clickAndWaitForNewWindow();
            //诊断中UI
            UiObject checkingObj = findElementByText("检测中...", "tv.fun.master:id/check_network_title");
            UiObject ipObj = findElementByID("tv.fun.master:id/check_network_info_ip");
            UiObject versionObj = findElementByID("tv.fun.master:id/check_network_info_version");
            UiObject macObj = findElementByID("tv.fun.master:id/check_network_info_mac");
            verifyElementPresent("", checkingObj);
            verifyIncludeString("", ipObj.getText(), "公网 IP：");
            verifyIncludeString("", versionObj.getText(), "版本号：");
            verifyIncludeString("", macObj.getText(), "MAC 地址：28:76:CD:");
            UiObject checkOptionList = findElementByID("tv.fun.master:id/check_network_result");
            int optionsCount = checkOptionList.getChildCount();
            verifyNumber("", optionsCount, optionNumOfNetworkCheck);
            UiObject firstOptionObj = checkOptionList.getChild(new UiSelector().className("android.widget.LinearLayout").index(0));
            UiObject firstOptionIcon = firstOptionObj.getChild(new UiSelector().resourceId("tv.fun.master:id/check_network_result_icon"));
            UiObject firstOptionText = firstOptionObj.getChild(new UiSelector().resourceId("tv.fun.master:id/check_network_result_text"));
            verifyElementPresent("", firstOptionIcon);
            verifyElementPresent("", firstOptionText);
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
     * Test the UI of network check page displays correctly after checking
     */
    @Test
    public void Master_WLZD_03_testNetworkCheckPageUIAfterCheck(){
        try{
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            moveRightForMultiple(5);
            //点击“网络诊断”卡片
            UiObject checkObj = findElementByText("网络诊断", "tv.fun.master:id/home_item_title");
            UiObject statusObj = checkObj.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
            String statusText = statusObj.getText();
            checkObj.clickAndWaitForNewWindow();
            //诊断后UI
            UiObject checkingObj = findElementByText("检测中...", "tv.fun.master:id/check_network_title");
            waitForElementNotPresent(checkingObj);
            waitForElementPresentByID("tv.fun.master:id/check_network_again");
            UiObject checkOptionList = findElementByID("tv.fun.master:id/check_network_result");
            int optionsCount = checkOptionList.getChildCount();
            verifyNumber("", optionsCount, 8);
            UiObject firstOptionObj = checkOptionList.getChild(new UiSelector().className("android.widget.LinearLayout").index(0));
            UiObject firstOptionIcon = firstOptionObj.getChild(new UiSelector().resourceId("tv.fun.master:id/check_network_result_icon"));
            UiObject firstOptionText = firstOptionObj.getChild(new UiSelector().resourceId("tv.fun.master:id/check_network_result_text"));
            verifyElementPresent("", firstOptionIcon);
            verifyElementPresent("", firstOptionText);
            UiObject retryObj = findElementByID("tv.fun.master:id/check_network_again");
            UiObject finishObj = findElementByID("tv.fun.master:id/check_network_finish");
            verifyString("", retryObj.getText(), "重新检测");
            verifyString("", finishObj.getText(), "完成");
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
     * Test that can retry network checking
     */
    @Test
    public void Master_WLZD_12_testRetryNetworkCheckingAfterCheck(){
        try{
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            moveRightForMultiple(5);
            //点击“网络诊断”卡片
            UiObject checkObj = findElementByText("网络诊断", "tv.fun.master:id/home_item_title");
            UiObject statusObj = checkObj.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
            String statusText = statusObj.getText();
            checkObj.clickAndWaitForNewWindow();
            //诊断后UI
            UiObject checkingObj = findElementByText("检测中...", "tv.fun.master:id/check_network_title");
            waitForElementNotPresent(checkingObj);
            waitForElementPresentByID("tv.fun.master:id/check_network_again");
            UiObject retryObj = findElementByID("tv.fun.master:id/check_network_again");
            verifyString("", retryObj.getText(), "重新检测");
            retryObj.click();
            UiObject retryChecking = findElementByText("检测中...", "tv.fun.master:id/check_network_title");
            verifyElementPresent("", retryChecking);
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
     * Test that can finish network checking
     */
    @Test
    public void Master_WLZD_13_testFinishNetworkCheckingAfterCheck(){
        try{
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            moveRightForMultiple(5);
            //点击“网络诊断”卡片
            UiObject checkObj = findElementByText("网络诊断", "tv.fun.master:id/home_item_title");
            UiObject statusObj = checkObj.getFromParent(new UiSelector().resourceId("tv.fun.master:id/home_item_status"));
            String statusText = statusObj.getText();
            checkObj.clickAndWaitForNewWindow();
            //诊断后UI
            UiObject checkingObj = findElementByText("检测中...", "tv.fun.master:id/check_network_title");
            waitForElementNotPresent(checkingObj);
            waitForElementPresentByID("tv.fun.master:id/check_network_again");
            UiObject finishObj = findElementByID("tv.fun.master:id/check_network_finish");
            verifyString("", finishObj.getText(), "完成");
            finishObj.click();
            verifyElementNotPresent("", findElementByID("tv.fun.master:id/check_network_info_version"));
            UiObject masterObj = device.findObject(new UiSelector().resourceId("tv.fun.master:id/tv_master_title").text("电视助手"));
            verifyElementPresent("", masterObj);
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
     * Test that No repsonse when pressing menu in NetWork Check page
     */
    @Test
    public void Master_WLZD_19_testNoMenuActionInNetWorkCheckpage() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"网络诊断"卡片
            gotoNetworkCheckPageFromMaster();
            //按遥控器Menu键
            menu();
            Thread.sleep(500);
            //断言
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
     * Test that can back to Launcher home page after pressing home btn in NetWork Check page
     */
    @Test
    public void Master_WLZD_20_testBackToLauncherFromNetworkCheckByHome() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"网络诊断"卡片
            waitForElementNotPresent("findElementByText(\"网络诊断\", \"tv.fun.master:id/home_item_title\")");
            gotoNetworkCheckPageFromMaster();
            UiObject checkingObj = findElementByText("检测中...", "tv.fun.master:id/check_network_title");
            verifyElementPresent("", checkingObj);
            //按遥控器Home键
            home();
            waitForElementPresentByIDAndText("com.bestv.ott:id/tab_title", launcherVideoTab);
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(launcherVideoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTab.isSelected());
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
     * Test that can back to TV Master home page after pressing back btn in Network check page
     */
    @Test
    public void Master_WLZD_21_testBackToMasterHomeFromNetworkCheckByBack() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击"网络诊断"卡片
            gotoNetworkCheckPageFromMaster();
            UiObject checkingObj = findElementByText("检测中...", "tv.fun.master:id/check_network_title");
            verifyElementPresent("", checkingObj);
            //按遥控器Back键
            back();
            //断言
            UiObject masterObj = device.findObject(new UiSelector().resourceId("tv.fun.master:id/tv_master_title").text("电视助手"));
            verifyElementPresent("", masterObj);
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
     * Test that the setting page UI displays correctly
     */
    @Test
    public void Master_SET_01_testMasterSettingPageUI() {
        String[] expectedOptionTitles = {"自动网络保护", "自动清理安装包", "自动清理卸载残留", "应用权限管理", "关  于"};
        String[] expectedOptionMsg = {"保护当前视频播放独享网络和资源", "应用安装之后自动清理安装包", "应用被卸载后自动清理其残留文件",
                "控制应用使用摄像头、录音等权限", "QQ群：259741088"};
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击设置按钮
            gotoSettingPageFromMaster();
            //断言
            UiObject optionListObj = findElementByID("tv.fun.master:id/list");
            int count = optionListObj.getChildCount();
            for (int i = 0; i < count; i++) {
                UiObject optionObj = optionListObj.getChild(new UiSelector().resourceId("tv.fun.master:id/rl_item_root").index(i));
                UiObject optionTitleObj = optionObj.getChild(new UiSelector().resourceId("tv.fun.master:id/title"));
                String optionTitle = optionTitleObj.getText();
                UiObject optionDescObj = optionObj.getChild(new UiSelector().resourceId("tv.fun.master:id/message"));
                String optionDescription = optionDescObj.getText();
                verifyString("", optionTitle, expectedOptionTitles[i]);
                verifyString("", optionDescription, expectedOptionMsg[i]);
                if (i <= 2) {
                    UiObject switchObj = optionObj.getChild(new UiSelector().resourceId("tv.fun.master:id/tv_switch"));
                    String switchStatus = switchObj.getText();
                    verifyElementPresent("", switchObj);
                    verifyString("", switchStatus, "开");
                } else {
                    UiObject arrowObj = optionObj.getChild(new UiSelector().resourceId("tv.fun.master:id/tv_arrow"));
                    verifyElementPresent("", arrowObj);
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
     * Test that No repsonse when pressing menu in Master setting page
     */
    @Test
    public void Master_SET_03_testNoMenuActionInMasterSettingpage() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击设置按钮
            gotoSettingPageFromMaster();
            //按遥控器Menu键
            menu();
            Thread.sleep(500);
            //断言
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
     * Test that can back to Launcher home page after pressing home btn in Master setting page
     */
    @Test
    public void Master_SET_04_testBackToLauncherFromMasterSettingpageByHome() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击设置按钮
            gotoSettingPageFromMaster();
            UiObject eleObj = findElementByID("tv.fun.master:id/rl_item_root");
            verifyElementPresent("", eleObj);
            //按遥控器Home键
            home();
            waitForElementPresentByIDAndText("com.bestv.ott:id/tab_title", launcherVideoTab);
            //断言
            UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
            UiObject videoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(launcherVideoTab));
            verifyElementPresent("", tvCard);
            verifyTrue("", videoTab.isSelected());
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
     * Test that can back to TV Master home page after pressing back btn in MasterSetting page
     */
    @Test
    public void Master_SET_05_testBackToMasterHomeFromMasterSettingByBack() {
        try {
            //Launcher应用tab页面，点击电视助手
            enterTVMasterPage();
            //点击设置按钮
            gotoSettingPageFromMaster();
            UiObject eleObj = findElementByID("tv.fun.master:id/rl_item_root");
            verifyElementPresent("", eleObj);
            //按遥控器Back键
            back();
            //断言
            UiObject masterObj = device.findObject(new UiSelector().resourceId("tv.fun.master:id/tv_master_title").text("电视助手"));
            verifyElementPresent("", masterObj);
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