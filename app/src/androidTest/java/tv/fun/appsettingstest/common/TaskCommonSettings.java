package tv.fun.appsettingstest.common;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;

import junit.framework.Assert;

import tv.fun.common.Constants;
import tv.fun.common.Utils;

import static tv.fun.common.Constants.CLASS_TEXT_VIEW;

/**
 * Created by zhengjin on 2016/10/25.
 *
 * TaskCommonSettings
 */

public final class TaskCommonSettings {

    private static TaskCommonSettings instance;
    private UiDevice device;

    private TaskCommonSettings() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    public static synchronized TaskCommonSettings getInstance() {
        if (instance == null) {
            instance = new TaskCommonSettings();
        }
        return instance;
    }

    public void moveToSpecifiedSettingsItem(BySelector selector) {
        UiObject2 item = device.findObject(selector);  // find the item from top
        if (item != null) {
            this.moveDownUntilSettingsItemFocused(item);
        } else {  // find the item from bottom
            this.moveToBottomOnCommonSettingsPage();
            item = device.findObject(selector);
            this.moveUpUntilSettingsItemFocused(item);
        }
    }

    private void moveToBottomOnCommonSettingsPage() {
        for (int i = 0; i < 10; i++) {
            device.pressDPadDown();
        }
    }

    private void moveDownUntilSettingsItemFocused(UiObject2 item) {
        this.moveUntilSettingsItemFocused(item, true);
    }

    private void moveUpUntilSettingsItemFocused(UiObject2 item) {
        this.moveUntilSettingsItemFocused(item, false);
    }

    private void moveUntilSettingsItemFocused(UiObject2 item, boolean flagDirectionDown) {
        for (int i = 0, maxMoveTimes = 15; i < maxMoveTimes; i++) {
            if (item.isFocused()) {
                return;
            }
            if (flagDirectionDown) {
                device.pressDPadDown();
            } else {
                device.pressDPadUp();
            }
            SystemClock.sleep(Constants.SHORT_WAIT);
        }
    }

    public UiObject2 getTextViewOfSwitcher(UiObject2 container) {
        UiObject2 switcher =
                container.findObject(By.res("tv.fun.settings:id/setting_item_value"));
        UiObject2 text = switcher.findObject(By.clazz(CLASS_TEXT_VIEW));
        return text;
    }

    public void selectSpecifiedSubWallpaper(String title) {
        UiObject2 wallpaper = device.findObject(By.text(title)).getParent();
        for (int i = 0, wallpaperSize = 4; i < wallpaperSize; i++) {
            if (wallpaper.isSelected()) {
                device.pressEnter();
                SystemClock.sleep(Constants.WAIT);
                return;
            }
            device.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
        }

        Assert.assertTrue("Failed to select the specified wallpaper.", false);
    }

    public void openSelfDefineDeviceNamePage() {
        device.pressEnter();
        SystemClock.sleep(Constants.SHORT_WAIT);
        UiObject2 itemSelfDefine = device.findObject(By.text("自定义"));
        itemSelfDefine.click();
        SystemClock.sleep(Constants.WAIT);
    }

    public void clearTextOfEditorView(int charCount) {
        for (int i = 0; i < charCount; i++) {
            device.pressDelete();
            SystemClock.sleep(200L);
        }
    }

    public int disableInpuntMethod() {
        Utils.CommandResult cr =
                Utils.execCommand("ime disable com.baidu.input_baidutv/.ImeService", false, false);
        return cr.mResult;
    }

    public int enableInputMethod() {
        Utils.CommandResult cr =
                Utils.execCommand("ime enable com.baidu.input_baidutv/.ImeService", false, false);
        return cr.mResult;
    }

}
