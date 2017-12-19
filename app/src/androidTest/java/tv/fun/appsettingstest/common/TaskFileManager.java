package tv.fun.appsettingstest.common;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

import tv.fun.common.Constants;
import tv.fun.common.Utils;

/**
 * Created by zhengjin on 2016/11/4.
 * TaskFileManager
 */

public final class TaskFileManager {

    private static TaskFileManager instance;
    private UiDevice device;

    private TaskFileManager() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    public static synchronized TaskFileManager getInstance() {
        if (instance == null) {
            instance = new TaskFileManager();
        }
        return instance;
    }

    public void openLocalFilesCard() {
        final int positionX = 1350;
        final int positionY = 450;
        device.click(positionX, positionY);
        SystemClock.sleep(Constants.WAIT);
    }

    public void openCategoryVideoCard() {
        final int positionX = 450;
        final int positionY = 450;
        openCardFromFileManagerHomePage(positionX, positionY);
    }

    public void openCategoryAppCard() {
        final int positionX = 650;
        final int positionY = 450;
        openCardFromFileManagerHomePage(positionX, positionY);
    }

    public void openCategoryMusicCard() {
        final int positionX = 450;
        final int positionY = 750;
        openCardFromFileManagerHomePage(positionX, positionY);
    }

    public void openCategoryPictureCard() {
        final int positionX = 750;
        final int positionY = 750;
        openCardFromFileManagerHomePage(positionX, positionY);
    }

    private void openCardFromFileManagerHomePage(int positionX, int positionY) {
        device.click(positionX, positionY);
        SystemClock.sleep(Constants.SHORT_WAIT);
        device.pressEnter();
        SystemClock.sleep(Constants.WAIT);
    }

    public void moveUntilSpecifiedItemSelected(String itemText) {
        moveAndEnterOnSpecifiedItemFromCurrentDir(itemText, false);
    }

    private void moveAndEnterOnSpecifiedItemFromCurrentDir(String itemName, boolean isEnter) {
        device.pressDPadLeft();
        SystemClock.sleep(Constants.SHORT_WAIT);
        device.pressDPadLeft();
        SystemClock.sleep(Constants.SHORT_WAIT);

        UiObject2 item = device.findObject(By.text(itemName));
        for (int i = 0, tryTimes = 10; i < tryTimes; i++) {
            if (item != null && item.isEnabled()) {
                break;
            }
            device.pressDPadDown();
            SystemClock.sleep(Constants.SHORT_WAIT);
            item = device.findObject(By.text(itemName));
        }

        for (int i = 0, tryTimes = 50; i < tryTimes; i++) {
            if (item.isSelected()) {
                if (isEnter) {
                    device.pressEnter();
                    SystemClock.sleep(Constants.SHORT_WAIT);
                }
                return;
            }
            device.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
        }

        Assert.assertTrue("moveAndEnterOnSpecifiedItemFromCurrentDir, failed to select item "
                + itemName, false);
    }

    public void navigateToSpecifiedPath(String path) {
        for (String dir : parsePath(path)) {
            if (dir.toLowerCase().equals("mnt") || dir.toLowerCase().equals("sdcard")) {
                continue;
            }
            this.clickOnSpecifiedItemFromCurrentDir(dir);
        }
    }
    public void navigateAndOpenSpecifiedFile(String fileAbsPath) {
        this.navigateToSpecifiedPath(fileAbsPath);
    }

    private void clickOnSpecifiedItemFromCurrentDir(String dirName, boolean flag_bottom) {
        final int ScrollSteps = 5;

        String scrollViewId = "tv.fun.filemanager:id/activity_sub_grid";
        UiScrollable fileList = new UiScrollable(new UiSelector().resourceId(scrollViewId));
        fileList.setAsVerticalList();
        try {
            fileList.scrollTextIntoView(dirName);
            SystemClock.sleep(Constants.SHORT_WAIT);
            if (flag_bottom) {
                fileList.scrollForward(ScrollSteps);
                SystemClock.sleep(Constants.SHORT_WAIT);
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            String message = String.format(
                    "Error in clickOnSpecifiedItemFromCurrentDir(), scroll to UI object %s.",
                    dirName);
            Assert.assertTrue(message, false);
        }

        UiObject2 dir = device.findObject(By.text(dirName));
        dir.click();
        SystemClock.sleep(Constants.WAIT);
    }

    private void clickOnSpecifiedItemFromCurrentDir(String dirName) {
        this.clickOnSpecifiedItemFromCurrentDir(dirName, false);
    }

    // path like: android/data/tv.fun.filemanager
    // or: /android/data/tv.fun.filemanager/
    private List<String> parsePath(String path) {
        int levels = 20;
        List<String> dirs = new ArrayList<>(levels);

        String[] tempDirs = path.split("/");
        for (String dir : tempDirs) {
            if (!Utils.isEmpty(dir)) {
                dirs.add(dir);
            }
        }
        if (dirs.size() == 0) {
            String message = "Error in parsePath(), the dirs size is 0.";
            Assert.assertTrue(message, false);
        }

        return dirs;
    }

    public void showMenuAndRequestFocus() {
        device.pressMenu();
        SystemClock.sleep(Constants.SHORT_WAIT);
        device.pressDPadDown();
        SystemClock.sleep(Constants.SHORT_WAIT);
    }

    public void showMenuAndClickBtn(String btnText) {
        boolean isFocused = false;

        showMenuAndRequestFocus();

        UiObject2 btn = device.findObject(By.text(btnText));
        Assert.assertNotNull(btn);
        UiObject2 btnContainer = btn.getParent();

        for (int i = 0, maxMove = 5; i < maxMove; i++) {
            if (btnContainer.isFocused()) {
                isFocused = true;
                break;
            }
            device.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
        }
        Assert.assertTrue(isFocused);

        device.pressEnter();
        SystemClock.sleep(Constants.WAIT);
    }
}
