package tv.fun.appstoretest.common;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import tv.fun.common.Utils;


/**
 * Created by liuqing on 2016/8/15.
 */
public class Common {
    public UiDevice device;
    public int timeout = 60;
    public int nextPageTime = 3000;
    public int sleepInterval = 500;
    public String appTabName = "应用";
    public String addIconInLauncherTab = "addIcon";
    public String searchIconInLauncherTab = "searchIcon";
    public String appStoreIconName = "应用市场";
    public String tvMasterIconName = "电视助手";
    public String systemAppIconName = "系统应用";
    public String myAppIconName = "我的应用";
    public String myAppCountUnit = "个)";
    public String[] tabs = {"推荐", "游戏", "娱乐", "生活", "教育"};
    public String appstorePackage = "tv.fun.appstore";//{{add("推荐"); "游戏", "娱乐", "生活", "教育", "应用管理"};
    public String[] appStoreTabs = {"推荐", "游戏", "娱乐", "生活", "教育", "应用管理"};
    public String appTab = "应用";
    public String videoTab = "视频";
    public String launcherTabID = "com.bestv.ott:id/tab_title";
    public String timeIDInPopup = "com.bestv.ott:id/time";//launcher悬浮框上时间控件的resource id
    public String weatherIDInPopup = "com.bestv.ott:id/weather";//launcher悬浮框上天气按钮的resource id
    public static boolean resultFlag = true;
    public static String resultStr = "";
    public long execTime;
    public String runTool = "Auto";//Studio or Auto
    public String errorLog = "";
    public String[] keywordForAutoLApp = {"D", "S", "Y", "Y", "G", "J"};//有自启动管理权限的应用“电视应用管家”搜索关键字
    public ArrayList<String> launcherTabs = new ArrayList<>();
    public String[] launchTabStrs = new String[11];
    private static String COMMAND_EXIT = "exit\n";
    private static String COMMAND_LINE_END = "\n";
    public final static String CHILDREN_PKG_NAME = "tv.fun.children";
    public String[] childTabs = {"推荐", "少儿", "品牌专区"};
    public String childrenTabName = childTabs[1];
    public int maxCountOfTabs = 10;

    @Before
    public void setup() throws RemoteException, IOException, UiObjectNotFoundException {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        execTime = Utils.getCurSecond();
        resultStr = "";
        resultFlag = true;
        if (!device.isScreenOn()) {
            device.wakeUp();
        }
//        executeAdbShellCommond("am force-stop tv.fun.appstore");
        home();
//        launcherTabs = getTabsNameInLauncherPage();
    }

    @After
    public void tearDown() throws IOException {
//        device.pressHome();
        executeAdbShellCommond("am force-stop tv.fun.appstore");
    }

    public void longPressHomeByCommand() throws IOException {
//        String cmd = String.format("input keyevent --longpress 3");
        String cmd = String.format("am force-stop %s", "tv.fun.children");
        executeCommand(new String[]{cmd}, false);
    }

    public void executeCommand(String[] commands, Boolean isRoot) throws IOException {
        Process lprocess = null;
        DataOutputStream dos = null;
        try {
            int result = -1;
            lprocess = Runtime.getRuntime().exec(isRoot ? "su" : "sh");
            dos = new DataOutputStream(lprocess.getOutputStream());
            for (String command : commands) {
                dos.write(command.getBytes());
                dos.writeBytes(COMMAND_LINE_END);
                dos.flush();
            }
            dos.writeBytes(COMMAND_EXIT);
            dos.flush();
            lprocess.waitFor();
            result = lprocess.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Find Element By Resource ID
     *
     * @param resouceID
     * @return obj
     */
    public UiObject findElementByID(String resouceID) {
        UiObject obj = device.findObject(new UiSelector().resourceId(resouceID));
        return obj;
    }

    /**
     * Find Element By Resource ID
     *
     * @param textStr
     * @param resouceID
     * @return obj
     */
    public UiObject findElementByText(String textStr, String resouceID) {
        UiObject obj = device.findObject(new UiSelector().text(textStr).resourceId(resouceID));
        return obj;
    }

    /**
     * Find Element By Resource ID
     *
     * @param type
     * @param locator
     * @param index
     * @return obj
     */
    public UiObject findElementByIndex(String type, String locator, int index) {
        UiObject obj = null;
        if (type.equalsIgnoreCase("id")) {
            obj = device.findObject(new UiSelector().resourceId(locator).index(index));
        } else if (type.equalsIgnoreCase("class")) {
            obj = device.findObject(new UiSelector().className(locator).index(index));
        }
        return obj;
    }

    /**
     * Find Element By Class Name
     *
     * @param className
     * @return obj
     */
    public UiObject findElementByClass(String className) {
        UiObject obj = device.findObject(new UiSelector().className(className));
        return obj;
    }

    /**
     * Find child node of Element By Resource ID
     *
     * @param resID
     * @param parentResID
     * @return obj
     */
    public UiObject findChildNodeOfElementByID(String parentResID, String resID) throws UiObjectNotFoundException {
        UiObject parentObj = findElementByID(parentResID);
        UiObject childObj = parentObj.getChild(new UiSelector().resourceId(resID));
        return childObj;
    }

    /**
     * Find child node of Element By Class Name
     *
     * @param value
     * @param parentClass
     * @return obj
     */
    public UiObject findChildNodeOfElementByClass(String parentClass, String value) throws UiObjectNotFoundException {
        UiObject parentObj = findElementByClass(parentClass);
        UiObject childObj = parentObj.getChild(new UiSelector().className(value));
        return childObj;
    }

    /**
     * Find child node of Lisy Element By Class Name and index
     *
     * @param parentClass
     * @param resID
     * @param indexf
     * @return obj
     */
    public UiObject findChildNodeOfListElementByClass(String parentClass, String resID, int indexf) throws UiObjectNotFoundException {
        UiObject parentObj = findElementByClass(parentClass);
        UiObject childObj = parentObj.getChild(new UiSelector().resourceId(resID).index(indexf));
        return childObj;
    }

    /**
     * Find Element By Resource ID
     *
     * @param parentObj
     * @param resID
     * @param elemIndex
     * @return obj
     */
    public UiObject findChildNodeOfListElementByIDAndIndex(UiObject parentObj, String resID, int elemIndex) throws UiObjectNotFoundException {
        UiObject childObj = parentObj.getChild(new UiSelector().resourceId(resID).index(elemIndex));
        return childObj;
    }

    /**
     * Judge whether the Element exists by Resource ID
     *
     * @param resouceID
     * @return obj
     */
    public Boolean isElementPresent(String resouceID) {
        Boolean flag = false;
        if (findElementByID(resouceID).exists()) {
            flag = true;
        }
        return flag;
    }

//    /**
//     * 初始化进程
//     */
//    private static void initProcess() {
//        if (process == null)
//            try {
//                process = Runtime.getRuntime().exec("su");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//    }

    /**
     * 适用于所有系统
     *
     * @param commondStr
     */
    public void executeAdbShellCommond(String commondStr) throws IOException {
        try {
            Runtime.getRuntime().exec("am force-stop tv.fun.appstore");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 仅适用于api21以上系统（5.0以上）
     *
     * @param commondStr
     */
    public void executeAdbCommond(String commondStr) throws IOException {
        device.executeShellCommand(commondStr);
    }

    /**
     * Change string type to the int type
     */
    public int stringToInt(String intstr) {
        Integer integer;
        integer = Integer.valueOf(intstr);
        return integer.intValue();
    }

    /**
     * Change string type to the float type
     */
    public Float stringToFloat(String intstr) {
        Float value;
        value = Float.valueOf(intstr);
        return value;
    }

    /**
     * Change float type to the string type
     */
    public String floatToString(Float intstr) {
        String value;
        value = String.valueOf(intstr);
        return value;
    }

    /**
     * Change int type to the string type
     */
    public static String intToString(int value) {
        Integer integer = new Integer(value);
        return integer.toString();
    }

    /**
     * Click and wait for some time
     */
    public void clickAndWait(UiObject Obj, int waitTime) throws UiObjectNotFoundException, InterruptedException {
        Obj.click();
        Thread.sleep(waitTime);
    }

    /**
     * 按遥控器向上键
     */
    public void moveUp() {
        device.pressDPadUp();
    }

    /**
     * 连续按遥控器向上键
     * <p>
     * step  连续向右移的次数
     */
    public void moveUpForMultiple(int step) {
        for (int i = 1; i <= step; i++) {
            device.pressDPadUp();
        }
    }

    /**
     * 按遥控器右键
     */
    public void moveRight() {
        device.pressDPadRight();
    }

    /**
     * 连续按遥控器右键
     * <p>
     * step  连续向右移的次数
     */
    public void moveRightForMultiple(int step) {
        if (step > 0) {
            for (int i = 1; i <= step; i++) {
                device.pressDPadRight();
            }
        }
    }

    /**
     * 按遥控器左键
     */
    public void moveLeft() {
        device.pressDPadLeft();
    }

    /**
     * 连续按遥控器左键
     * <p>
     * step  连续向左移的次数
     */
    public void moveLeftForMultiple(int step) {
        if (step < 0) {
            step = -step;
        }
        for (int i = 1; i <= step; i++) {
            device.pressDPadLeft();
        }
    }

    /**
     * 按遥控器向下键
     * <p>
     * 向下移
     */
    public void moveDown() {
        device.pressDPadDown();
    }

    /**
     * 连续按遥控器下键
     * <p>
     * step  连续向下移的次数
     */
    public void moveDownForMultiple(int step) {
        if (step < 0) {
            step = -step;
        }
        for (int i = 1; i <= step; i++) {
            device.pressDPadDown();
        }
    }

    /**
     * Input password in children key popup window
     */
    public void inputChildrenKeyPasswordIfPopupDisplay() throws UiObjectNotFoundException {
        if (device.findObject(new UiSelector().text("输入密码或扫码解锁")).exists() | findElementByID("tv.fun.children:id/keypad").exists()) {
            //input 1111
            UiObject keyObj = findElementByID("tv.fun.children:id/keypad").getChild(new UiSelector().className("android.widget.ImageView").index(0));
            moveDownForMultiple(3);
            moveUp();
            device.pressEnter();
            device.pressEnter();
            device.pressEnter();
            device.pressEnter();
            if (device.findObject(new UiSelector().text("输入密码或扫码解锁")).exists() | findElementByID("tv.fun.children:id/keypad").exists()) {
                back();
            }
        }
    }

    /**
     * 按遥控器Home键
     */
    public void home() throws UiObjectNotFoundException {
        device.pressHome();
        inputChildrenKeyPasswordIfPopupDisplay();
    }

    /**
     * 按遥控器确定键
     */
    public void enter() {
        device.pressEnter();
    }

    /**
     * 按遥控器Back键
     */
    public void back() {
        device.pressBack();
    }

    /**
     * 连续多次按遥控器Back键
     */
    public void backForMultiple(int times) {
        if (times < 0) {
            times = -times;
        }
        for (int i = 1; i <= times; i++) {
            device.pressBack();
        }
    }

    /**
     * 按遥控器Menu键
     */
    public void menu() {
        device.pressMenu();
    }

    /**
     * Wait for an element present. The element on the page does not exist in
     * the pre-page, waiting for the element exist.
     *
     * @param locator an element locator
     * @throws InterruptedException
     */
    public void waitForElementPresentByID(String locator)
            throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= timeout) {
                System.out.println("timeout: wait for element present <"
                        + locator + ">");
                break;
            }
            if (device.findObject(new UiSelector().resourceId(locator)).exists()) {
                break;
            }
            Thread.sleep(sleepInterval);
        }
    }

    /**
     * Wait for time
     * <p>
     * an element locator
     *
     * @throws InterruptedException
     */
    public void wait(int timeUnit)
            throws InterruptedException {
        Thread.sleep(timeUnit * 60 * 2 * sleepInterval);
    }

    /**
     * Wait for an element present. The element on the page does not exist in
     * the pre-page, waiting for the element exist.
     *
     * @param locator an element locator
     * @param textStr text of element
     * @throws InterruptedException
     */
    public void waitForElementPresentByIDAndText(String locator, String textStr)
            throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= timeout) {
                System.out.println("timeout: wait for element present <"
                        + locator + "> with text (" + textStr + ")");
                break;
            }
            if (device.findObject(new UiSelector().resourceId(locator).text(textStr)).exists()) {
                break;
            }
            Thread.sleep(sleepInterval);
        }
    }

    /**
     * Wait for an element present. The element on the page does not exist in
     * the pre-page, waiting for the element exist.
     *
     * @param className an element locator
     * @param textStr   text of element
     * @throws InterruptedException
     */
    public void waitForElementPresentByClassAndText(String className, String textStr)
            throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= timeout) {
                System.out.println("timeout: wait for element present <"
                        + className + "> with text (" + textStr + ")");
                break;
            }
            if (device.findObject(new UiSelector().text(textStr).className(className)).exists()) {
                break;
            }
            Thread.sleep(sleepInterval);
        }
    }

    /**
     * Wait for an element present. The element on the page does not exist in
     * the pre-page, waiting for the element exist.
     *
     * @param locator an element locator
     * @throws InterruptedException
     */
    public void waitForElementNotPresentByID(String locator)
            throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= timeout) {
                System.out.println("timeout: wait for element present <"
                        + locator + ">");
                break;
            }
            if (!device.findObject(new UiSelector().resourceId(locator)).exists()) {
                break;
            }
            Thread.sleep(sleepInterval);
        }
    }

    /**
     * Wait for an element not present. Wait for an element form "exist" to
     * "disappear" in page.
     *
     * @param locator an element locator
     * @throws InterruptedException
     */
    public void waitForElementNotPresent(String locator)
            throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= timeout) {
                System.out.println("timeout: wait for element not present <"
                        + locator + ">");
                break;
            }
            if (!device.findObject(new UiSelector().resourceId(locator)).exists()) {
                break;
            }
            Thread.sleep(sleepInterval);
        }
    }

    /**
     * Wait for an element not present. Wait for an element form "exist" to
     * "disappear" in page.
     *
     * @param ele an element
     * @throws InterruptedException
     */
    public void waitForElementNotPresent(UiObject ele)
            throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= timeout) {
                System.out.println("timeout: wait for element not present <"
                        + ele + ">");
                break;
            }
            if (!ele.exists()) {
                break;
            }
            Thread.sleep(sleepInterval);
        }
    }

    /**
     * Wait for an text note present. The element on the page exists in
     * the pre-page, waiting for the element not exist.
     *
     * @param textStr a text
     * @throws InterruptedException
     */
    public void waitForTextNotPresent(String textStr)
            throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= timeout) {
                System.out.println("timeout: wait for text not present <"
                        + textStr + ">");
                break;
            }
            if (!device.findObject(new UiSelector().text(textStr)).exists()) {
                break;
            }
            Thread.sleep(sleepInterval);
        }
    }

    /**
     * Use to get the step need to move from the first tab to target tab
     *
     * @param targetTab
     * @return
     */
    public int stepFromCurrentTabToTargetTab(String[] tablist, String currentTab, String targetTab) {
        HashMap<String, Integer> tabMap = new HashMap<String, Integer>();
        int tabCount = tablist.length;
        for (int i = 0; i < tabCount; i++) {
            tabMap.put((String) tablist[i], i + 1);
        }
        int startStep = tabMap.get(currentTab);
        int targetTabStep = tabMap.get(targetTab);
        int step = targetTabStep - startStep;
        return step;
    }

    /**
     * Use to get the step need to move from the first tab to target tab
     *
     * @param targetTab
     * @return
     */
    public int stepFromCurrentTabToTargetTab(ArrayList tablist, String currentTab, String targetTab) {
        HashMap<String, Integer> tabMap = new HashMap<String, Integer>();
        int tabCount = tablist.size();
        for (int i = 0; i < tabCount; i++) {
            tabMap.put((String) tablist.get(i), i + 1);
        }
        int startStep = tabMap.get(currentTab);
        int targetTabStep = tabMap.get(targetTab);
        int step = targetTabStep - startStep;
        return step;
    }

    /**
     * Use to get the step need to move from the current tab to add icon
     *
     * @param currentTab
     * @return
     */
    public int stepFromCurrentTabToAdd(ArrayList tablist, String currentTab) {
        HashMap<String, Integer> tabMap = new HashMap<String, Integer>();
        int tabCount = tablist.size();
        for (int i = 0; i < tabCount; i++) {
            tabMap.put((String) tablist.get(i), i + 1);
        }
        int startStep = tabMap.get(currentTab);
        int targetTabStep = tabCount + 1;
        int step = targetTabStep - startStep;
        return step;
    }

    /**
     * Get the launcher tabs list
     */
    public String[] getTabsNameInLauncherPage() {
        String[] launcherTabObj = new String[10];
        UiObject tabEle = null;
        UiObject tabObj = null;
        String tabName ="";
        try {
            UiObject launcherScrollObject = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/indicator"));
            UiObject tabListObj = launcherScrollObject.getChild(new UiSelector().className("android.widget.LinearLayout"));
            int tabCount = tabListObj.getChildCount();
            cancelLauncherPopupDisplay();
            for (int i = 0; i < tabCount; i++) {
                if (i == 0) {
                    tabEle = tabListObj.getChild(new UiSelector().resourceId("com.bestv.ott:id/search_entry").index(i));
                    launcherTabObj[i] = searchIconInLauncherTab;
                } else {
                    tabEle = tabListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(i));
                    tabObj = tabEle.getChild(new UiSelector().resourceId("com.bestv.ott:id/tab_title"));
                    if (!tabObj.exists()&&i!=tabCount - 1) {
                        tabObj = tabEle.getChild(new UiSelector().resourceId("com.bestv.ott:id/tab_image"));
                        launcherTabObj[i] = "imageTab";
                    } else {
                        if (i == tabCount - 1) {
                            launcherTabObj[i] = addIconInLauncherTab;
                        } else {
                            tabName = tabObj.getText();
                            launcherTabObj[i] = tabName;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return launcherTabObj;
        }
    }

    /**
     * Prepare data to make sure the app tab is displayed in Launcher page
     */
    public void displayAppTabInLauncher() throws UiObjectNotFoundException, InterruptedException {
        launchTabStrs = getTabsNameInLauncherPage();
        UiObject launcherScrollObject = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/indicator"));
        UiObject tabListObj = launcherScrollObject.getChild(new UiSelector().className("android.widget.LinearLayout"));
        int tabCount = tabListObj.getChildCount();
        UiObject lastObj = tabListObj.getChild(new UiSelector().className("android.widget.LinearLayout").index(tabCount - 1));
        UiObject currentTabObj = device.findObject(new UiSelector().resourceId("com.bestv.ott:id/tab_title").selected(true));
        String currentTabName = currentTabObj.getText();
        int stepFromCurrent = stepFromCurrentTabToTargetTab(launchTabStrs, currentTabName, addIconInLauncherTab);
        if (stepFromCurrent > 0) {
            moveRightForMultiple(stepFromCurrent);
        } else {
            moveLeftForMultiple(stepFromCurrent);
        }
        device.pressEnter();
        waitForElementPresentByIDAndText("com.bestv.ott:id/tab_manager_title", "设置桌面频道");
        UiObject appTabInList = findElementByText(appTabName, "com.bestv.ott:id/tab_title");
        appTabInList.click();
        appTabInList = findElementByText(appTabName, "com.bestv.ott:id/tab_title");
        if (!appTabInList.isSelected()) {
            device.pressEnter();
        }
        back();
        //此时焦点会消失，以下步骤为了重新获取焦点
        moveLeft();
        moveRight();
    }

    /**
     * Check if the expected tab is displaying
     */
    public Boolean checkWhetherTabDisplay(String targetTab, String tabResouceID) {
        Boolean tabFalg = false;
        UiObject tab = device.findObject(new UiSelector().resourceId(tabResouceID).text(targetTab));
        if (device.findObject(new UiSelector().resourceId(tabResouceID).text(targetTab)).exists()) {
            tabFalg = true;
        }
        return tabFalg;
    }

    /**
     * Sometimes, when entering appstore from Launcher, the default tab is not the first tab. This method is used to move the focus to the first tab
     *
     * @param targetTab
     * @throws UiObjectNotFoundException
     */
    public void moveToTargetTab(String[] tablist, String targetTab, String tabResouceID, int step) throws UiObjectNotFoundException {
        UiObject tab = device.findObject(new UiSelector().resourceId(tabResouceID).text(targetTab));
        if (!tab.isSelected()) {
            moveUpForMultiple(step);//Move to navBar to avoid that the current focusot in narBar
            if (tabResouceID.equalsIgnoreCase(launcherTabID)) {
                cancelLauncherPopupDisplay();
            }
            UiObject currentTabObj = device.findObject(new UiSelector().resourceId(tabResouceID).selected(true));
            String currentTab = currentTabObj.getText();
            int needStep = stepFromCurrentTabToTargetTab(tablist, currentTab, targetTab);
            if (needStep > 0) {
                moveRightForMultiple(needStep);
            } else {
                moveLeftForMultiple(needStep);
            }
        }
    }

    /**
     * Cancel launcher pop-up
     */
    public void cancelLauncherPopupDisplay(){
        if (isElementPresent(timeIDInPopup) || isElementPresent(weatherIDInPopup)) {
            moveDown();
        }
    }

    /**
     * Sometimes, when entering appstore from Launcher, the default tab is not the first tab. This method is used to move the focus to the first tab
     *
     * @param targetTab
     * @throws UiObjectNotFoundException
     */
    public void moveToLauncherTargetTab(String targetTab, String tabResouceID, int step) throws UiObjectNotFoundException, InterruptedException {
        moveUp();
        cancelLauncherPopupDisplay();
        UiObject tab = device.findObject(new UiSelector().resourceId(tabResouceID).text(targetTab));
        if (!tab.exists()) {
            displayAppTabInLauncher();
            home();
        }
        moveUp();
        if (!device.findObject(new UiSelector().resourceId(tabResouceID).selected(true)).exists()) {
            moveUpForMultiple(2);//Move to navBar to avoid that the current focusot in narBar
        }
        cancelLauncherPopupDisplay();
        UiObject currentTabObj = device.findObject(new UiSelector().resourceId(tabResouceID).selected(true));
        String currentTab = currentTabObj.getText();
        launchTabStrs = getTabsNameInLauncherPage();
        String value = launchTabStrs[1];
        if (value != null) {
            int needStep = stepFromCurrentTabToTargetTab(launchTabStrs, currentTab, targetTab);
            if (needStep > 0) {
                moveRightForMultiple(needStep);
            } else {
                moveLeftForMultiple(needStep);
            }
        } else {
            for (int j = 2; j < maxCountOfTabs; j++) {
                currentTabObj = device.findObject(new UiSelector().resourceId(tabResouceID).selected(true));
                currentTab = currentTabObj.getText();
                if (currentTab.equalsIgnoreCase(targetTab)) {
                    break;
                } else {
                    moveRight();
                }
            }
        }
    }

    /**
     * Verify not the string.
     *
     * @param actual
     * @param unexpected
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyNotString(String actual, String unexpected)
            throws IOException {
        if (!unexpected.equals(actual)) {
            return true;
        } else {
            resultFlag = false;
            resultStr += "Not expected [" + unexpected + "] is displayed. ";
            return false;
        }
    }

    /**
     * Verify the string.
     *
     * @param actual
     * @param expected
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyString(String failMsg, String actual, String expected)
            throws IOException {
        Boolean verifyFlag = true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertTrue(failMsg, expected.equalsIgnoreCase(actual));
        } else {
            if (expected.equalsIgnoreCase(actual)) {
                resultFlag = true;
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                if (failMsg != "") {
                    resultStr += failMsg + ";";
                } else {
                    resultStr += "Expected [" + expected + "] but actual [" + actual
                            + "]; ";
                }
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the actual string is not matched with the input.
     *
     * @param actual
     * @param expected
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyNotString(String failMsg, String actual, String expected)
            throws IOException {
        Boolean verifyFlag = true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertTrue(failMsg, !expected.equalsIgnoreCase(actual));
        } else {
            if (!expected.equalsIgnoreCase(actual)) {
                resultFlag = true;
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                if (failMsg != "") {
                    resultStr += failMsg + ";";
                } else {
                    resultStr += "Expected [" + actual + "] is not same as the string [" + expected
                            + "]; but actually they are same";
                }
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the string include another string.
     *
     * @param actual
     * @param expected
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyIncludeString(String failMsg, String expected, String actual)
            throws IOException {
        Boolean verifyFlag = true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertTrue(failMsg, expected.contains(actual));
        } else {
            if (expected.indexOf(actual) > -1) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                resultStr += "Expected [" + expected + "] contains actual [" + actual
                        + "], but actually not contain; ";
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the number.
     *
     * @param actual
     * @param expected
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyNumber(String failMsg, int actual, int expected) throws IOException {
        Boolean verifyFlag = true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertTrue(failMsg, actual == expected);
        } else {
            if (actual == expected) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                resultStr += "Expected [" + expected + "] but actual [" + actual
                        + "]; ";
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the number.
     *
     * @param actual
     * @param expected
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyNotNumber(String failMsg, int actual, int expected) throws IOException {
        Boolean verifyFlag = true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertTrue(failMsg, actual != expected);
        } else {
            if (actual != expected) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                resultStr += "Expected not [" + expected + "] but actual [" + actual
                        + "]; ";
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the actual number is larger than the input number.
     *
     * @param actualNum
     * @param smallNum
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyNumberLarger(String failMsg, int actualNum, int smallNum) throws IOException {
        Boolean verifyFlag = true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertTrue(failMsg, actualNum > smallNum);
        } else {
            if (actualNum > smallNum) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                resultStr += "Expected the [" + actualNum + "] is larger than [" + smallNum
                        + "]; ";
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the actual number is larger than the input number.
     *
     * @param actualNum
     * @param smallNum
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyNumberEqualORLarger(String failMsg, int actualNum, int smallNum) throws IOException {
        Boolean verifyFlag = true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertTrue(failMsg, actualNum >= smallNum);
        } else {
            if (actualNum >= smallNum) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                resultStr += "Expected the [" + actualNum + "] is larger than [" + smallNum
                        + "]; ";
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the input parmeter is true.
     *
     * @param failMsg
     * @param testFlag
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyTrue(String failMsg, Boolean testFlag) throws IOException {
        Boolean verifyFlag = true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertTrue(failMsg, testFlag);
        } else {
            if (testFlag) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                if (failMsg != "") {
                    resultStr += failMsg + ";";
                } else {
                    resultStr += "The return of [" + testFlag + "] is not True; ";
                }
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the input parmeter is false.
     *
     * @param failMsg
     * @param testFlag
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyFalse(String failMsg, Boolean testFlag) throws IOException {
        Boolean verifyFlag = true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertFalse(failMsg, testFlag);
        } else {
            if (!testFlag) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                if (failMsg != "") {
                    resultStr += failMsg + ";";
                } else {
                    resultStr += "The return of [" + testFlag + "] is not False; ";
                }
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the element present.
     *
     * @param obj
     * @param failMsg
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyElementPresent(String failMsg, UiObject obj) throws IOException {
        Boolean verifyFlag = true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertTrue(failMsg, obj.exists());
        } else {
            if (obj.exists()) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                if (failMsg != "") {
                    resultStr += failMsg + ";";
                } else {
                    resultStr += "Element [" + obj + "] is NOT present; ";
                }
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the element present.
     *
     * @param locator
     * @param failMsg
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyElementPresent(String failMsg, String locator) throws IOException {
        Boolean verifyFlag = true;
        UiObject actualObj = findElementByID(locator);
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertTrue(failMsg, actualObj.exists());
        } else {
            if (actualObj.exists()) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                if (failMsg != "") {
                    resultStr += failMsg + ";";
                } else {
                    resultStr += "Element [" + actualObj + "] is NOT present; ";
                }
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the element not present.
     *
     * @param obj
     * @param failMsg
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyElementNotPresent(String failMsg, UiObject obj) throws IOException {
        Boolean verifyFlag = true;
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertFalse(failMsg, obj.exists());
        } else {
            if (!obj.exists()) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                if (failMsg != "") {
                    resultStr += failMsg + ";";
                } else {
                    resultStr += "Element [" + obj + "] is expected Not present, but actually present; ";
                }
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

    /**
     * Verify the element not present.
     *
     * @param locator
     * @param failMsg
     * @return result of verification
     * @throws IOException
     */
    public Boolean verifyElementNotPresent(String failMsg, String locator) throws IOException {
        Boolean verifyFlag = true;
        UiObject obj = findElementByID(locator);
        if (runTool.equalsIgnoreCase("Studio")) {
            Assert.assertFalse(failMsg, obj.exists());
        } else {
            if (!obj.exists()) {
                verifyFlag = true;
                return verifyFlag;
            } else {
                resultFlag = false;
                if (failMsg != "") {
                    resultStr += failMsg + ";";
                } else {
                    resultStr += "Element [" + locator + "] is expected Not present, but actually present; ";
                }
                verifyFlag = false;
                return verifyFlag;
            }
        }
        return verifyFlag;
    }

//
//    public void evaluate() throws Throwable {
//        List<Throwable> errors = new ArrayList<Throwable>();
//        try {
//            next.evaluate();
//        } catch (Throwable e) {
//            errors.add(e);
//        }
//    /**
//     * 解析XML文件
//     *
//     */
//    public String parserXml() throws DocumentException {
//        String fileXpath = "//app/testCases.xml";
//        File inputXml = new File(fileXpath);
//        SAXReader saxReader = new SAXReader();
//        String testKind = "";
//        try {
//            Document document = saxReader.read(fileXpath);
//            Element testCases = document.getRootElement();
//            for (Iterator i = testCases.elementIterator(); i.hasNext();) {
//                Element testCase = (Element) i.next();
//                for (Iterator j = testCase.elementIterator(); j.hasNext();) { // 遍例节点
//                    Element node = (Element) j.next();
//                    System.out.println(node.getName() + ":" + node.getText());
//                    testKind=node.getText();
//                }
//
//            }
//        } catch (DocumentException e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println("dom4j parserXml");
//        return testKind;
//    }

}
