package tv.fun.appsautotest.common;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;

/**
 * Created by lixm on 2017/12/16.
 */

public class DeviceInfo {
    private String _currentPackageName;
    private int _displayWidth;
    private int _displayHeight;
    private int _displayRotation;
    private int _displaySizeDpX;
    private int _displaySizeDpY;
    private String _productName;
    private boolean _naturalOrientation;
    private boolean _screenOn;
    private int _sdkInt;

    public static DeviceInfo getDeviceInfo() {

        return new DeviceInfo();
    }

    private DeviceInfo() {
        this._sdkInt = android.os.Build.VERSION.SDK_INT;

        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        this._currentPackageName = uiDevice.getCurrentPackageName();
        this._displayWidth = uiDevice.getDisplayWidth();
        this._displayHeight = uiDevice.getDisplayHeight();
        this._displayRotation = uiDevice.getDisplayRotation();
        this._productName = uiDevice.getProductName();
        this._naturalOrientation = uiDevice.isNaturalOrientation();
        this._displaySizeDpX = uiDevice.getDisplaySizeDp().x;
        this._displaySizeDpY = uiDevice.getDisplaySizeDp().y;
        try {
            this._screenOn = uiDevice.isScreenOn();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String getCurrentPackageName() {

        return _currentPackageName;
    }

    public void setCurrentPackageName(String currentPackageName) {

        this._currentPackageName = currentPackageName;
    }

    public int getDisplayWidth() {

        return _displayWidth;
    }

    public void setDisplayWidth(int displayWidth) {
        this._displayWidth = displayWidth;
    }

    public int getDisplayHeight() {

        return _displayHeight;
    }

    public void setDisplayHeight(int displayHeight) {
        this._displayHeight = displayHeight;
    }

    public int getDisplayRotation() {
        return _displayRotation;
    }

    public void setDisplayRotation(int displayRotation) {
        this._displayRotation = displayRotation;
    }

    public int getDisplaySizeDpX() {
        return _displaySizeDpX;
    }

    public void setDisplaySizeDpX(int displaySizeDpX) {
        this._displaySizeDpX = displaySizeDpX;
    }

    public int getDisplaySizeDpY() {
        return _displaySizeDpY;
    }

    public void setDisplaySizeDpY(int displaySizeDpY) {
        this._displaySizeDpY = displaySizeDpY;
    }

    public String getProductName() {
        return _productName;
    }

    public void setProductName(String productName) {
        this._productName = productName;
    }

    public boolean isNaturalOrientation() {
        return _naturalOrientation;
    }

    public void setNaturalOrientation(boolean naturalOrientation) {
        this._naturalOrientation = naturalOrientation;
    }

    public int getSdkInt() {
        return _sdkInt;
    }

    public void setSdkInt(int sdkInt) {
        this._sdkInt = sdkInt;
    }

    public boolean getScreenOn() {
        return _screenOn;
    }

    public void setScreenOn(boolean screenOn) {

        this._screenOn = screenOn;
    }
}
