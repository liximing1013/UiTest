package tv.fun.appstoretest.common;

import android.os.SystemClock;
import android.support.test.uiautomator.UiAutomatorBridge;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by liuqing on 2017/4/1.
 */
public class ChildrenPage extends Common {

    /**
     * Method for going to Children page
     */
    public void navigateToChildrenDesktopPage() throws UiObjectNotFoundException, InterruptedException {
        //点击launcher悬浮框上智能桌面卡片
        home();
        moveUpForMultiple(2);
        if (!findElementByID(timeIDInPopup).exists()) {
            moveUpForMultiple(2);
        }
        waitForElementPresentByID("com.bestv.ott:id/launcher_title");
        UiObject desktopIconObj = findElementByText("智能桌面", "com.bestv.ott:id/launcher_title");
        if (!desktopIconObj.isSelected()) {
            desktopIconObj.click();
        }
        desktopIconObj.click();
        //点击智能桌面弹框上儿童桌面
        waitForElementPresentByID("tv.fun.settings:id/title");
        UiObject childDesktopObj = findElementByText("儿童桌面", "tv.fun.settings:id/title");
        moveUp();
        moveRight();
        if (!findElementByText("儿童桌面", "tv.fun.settings:id/title").isSelected()) {
            moveUp();
            moveRight();
        }
        enter();
        waitForElementPresentByID("tv.fun.children:id/tab_title");
    }

    /**
     * Goto children page via children card under video tab
     */
    public void navigateToChildrenByChildrenCardUnderVideo() throws UiObjectNotFoundException, InterruptedException {
        navigateToChildrenDesktopPage();//少儿卡片点击进入不是到少儿桌面，故先用其他方法代替
//        //点击launcher悬浮框上智能桌面卡片
//        home();
//        UiObject videoTabObj = findElementByText("视频", "com.bestv.ott:id/tab_title");
//        if (!videoTabObj.isSelected()) {
//            home();
//        }
//        UiObject childrenCardObj = findElementByText("少儿", "com.bestv.ott:id/title");
//        if(!childrenCardObj.isSelected()){
//            childrenCardObj.click();
//        }
//        childrenCardObj.clickAndWaitForNewWindow();
//        waitForElementPresentByID("tv.fun.children:id/tab_title");
    }

    /**
     * Goto my playing record page from children home page
     */
    public void navigateToPlayRecordPage() throws UiObjectNotFoundException {
        UiObject childHistoryObj = findElementByID("tv.fun.children:id/children_history");
        childHistoryObj.clickAndWaitForNewWindow();
    }

    /**
     * Goto my collection page from children home page
     */
    public void navigateToMyCollectionPage() throws UiObjectNotFoundException {
        UiObject myFavoriteObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
        myFavoriteObj.clickAndWaitForNewWindow();
    }

    /**
     * Move to expected tab
     */
    public void moveToTargetTabInChildren(String targetTab) throws UiObjectNotFoundException {
        moveUpForMultiple(4);
        UiObject recommendTabObj = findElementByID("tv.fun.children:id/tab_title_recommend");
        UiObject childTabObj = findElementByID("tv.fun.children:id/tab_title_children");
        UiObject brandTabObj = findElementByID("tv.fun.children:id/tab_title_brand");
        int indexOfTargetTab = -1;
        for(int i=0; i< childTabs.length; i++){
            if(targetTab.equalsIgnoreCase(childTabs[i])){
                indexOfTargetTab=i;
            }
        }
        int moveStep =0;
        if(recommendTabObj.isFocused()){
            moveStep=indexOfTargetTab;
        }else if(childTabObj.isFocused()){
            moveStep=indexOfTargetTab-1;
        }else{
            moveStep=indexOfTargetTab-2;
        }
        if(moveStep>0){
            moveRightForMultiple(moveStep);
        }else{
            moveLeftForMultiple(-moveStep);
        }

    }

    /**
     * Long press Home
     */
    public void longPressHome() {
        final long eventTime = SystemClock.uptimeMillis();
        KeyEvent downEvent = new KeyEvent(5000, eventTime, KeyEvent.ACTION_DOWN,
                KeyEvent.KEYCODE_HOME, 5, 0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0, 0,
                InputDevice.SOURCE_KEYBOARD);
        KeyEvent upEvent = new KeyEvent(5000, eventTime, KeyEvent.ACTION_UP,
                KeyEvent.KEYCODE_HOME, 5, 0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0, 0,
                InputDevice.SOURCE_KEYBOARD);
    }

    private UiAutomatorBridge mUiAutomatorBridge;

    private boolean injectEventSync(InputEvent event) {
        return mUiAutomatorBridge.injectInputEvent(event, true);
    }

    public void sendKey(int keyCode, int metaState) throws InterruptedException {

        final long eventTime = SystemClock.uptimeMillis();
        KeyEvent downEvent = new KeyEvent(50000, eventTime, KeyEvent.ACTION_DOWN,
                keyCode, 10, metaState, KeyCharacterMap.VIRTUAL_KEYBOARD, 0, 0,
                InputDevice.SOURCE_KEYBOARD);
        Thread.sleep(3000);
        KeyEvent upEvent = new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_UP,
                keyCode, 0, metaState, KeyCharacterMap.VIRTUAL_KEYBOARD, 0, 0,
                InputDevice.SOURCE_KEYBOARD);
    }


    // 长按物理键
    public  boolean longPressKeyCode(int keyCode,int PressTime) throws InvocationTargetException {
        try {

            Field mUiAutomationBridge = Class.forName("android.support.test.uiautomator.UiDevice").getDeclaredField("mUiAutomationBridge");
            mUiAutomationBridge.setAccessible(true);

            Object bridgeObj = mUiAutomationBridge.get(device);
            Method injectInputEvent = Class.forName("android.support.test.uiautomator.UiAutomatorBridge")
                    .getDeclaredMethod("injectInputEvent",new Class[]{InputEvent.class,boolean.class});


            final long eventTime = SystemClock.uptimeMillis();
            KeyEvent downEvent = new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN,
                    keyCode, 2, 3, KeyCharacterMap.VIRTUAL_KEYBOARD, 0, 0,
                    InputDevice.SOURCE_KEYBOARD);


            if ((Boolean) injectInputEvent.invoke(bridgeObj, new Object[]{downEvent, true})) {

                SystemClock.sleep(PressTime);

                KeyEvent upEvent = new KeyEvent(eventTime, eventTime,
                        KeyEvent.ACTION_UP, keyCode, 0, 3,
                        KeyCharacterMap.VIRTUAL_KEYBOARD, 0, 0,
                        InputDevice.SOURCE_KEYBOARD);
                if ((Boolean) injectInputEvent.invoke(bridgeObj, new Object[]{upEvent, true})) {
                    return true;
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

}
