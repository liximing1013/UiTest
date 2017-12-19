package tv.fun.appsettingstest.common;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;

import junit.framework.Assert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tv.fun.common.Constants;

/**
 * Created by zhengjin on 2016/11/11.
 * <p>
 * TaskWeather
 */

public final class TaskWeather {

    private static TaskWeather instance;
    private UiDevice device;

    public final String WEATHER_MENU_BUTTON_TEXT_UPDATE = "更新";
    public final String WEATHER_MENU_BUTTON_TEXT_MODIFY_DEFAULT = "修改默认";
    public final String WEATHER_MENU_BUTTON_TEXT_ADD_CITY = "添加城市";
    public final String WEATHER_MENU_BUTTON_TEXT_DELETE_CITY = "删除当前";

    private TaskWeather() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    public static synchronized TaskWeather getInstance() {
        if (instance == null) {
            instance = new TaskWeather();
        }
        return instance;
    }

    public String getWeatherForecastDateFromUiText(String source) {
        int start = source.indexOf("(") + 1;
        return source.substring(start, (source.length() - 1));
    }

    public List<String> getWeatherForecastDates() {
        final int length = 5;
        List<String> dates = new ArrayList<>(length);

        for (int i = 0; i < length; i++) {
            dates.add(this.formatForecastDate(this.getNextFromCurrentDate(i)));
        }
        return dates;
    }

    // param date: yyyy-MM-dd
    private String formatForecastDate(String date) {
        String[] items = date.split("-");
        Assert.assertTrue("Error in formatForecastDate(), the date items length is not 3.",
                items.length == 3);

        String month = items[1];
        String day = items[2];
        // format "01" to 1
        return String.format(Locale.getDefault(),
                "%d月%d日", Integer.parseInt(month), Integer.parseInt(day));
    }

    private String getNextFromCurrentDate(int next) {
        final long DayTimeMillis = 24 * 60 * 60 * 1000;
        long extraTime = next * DayTimeMillis;
        return this.getSpecifiedDate(System.currentTimeMillis() + extraTime);
    }

    private String getSpecifiedDate(long timeMillis) {
        SimpleDateFormat formatter =
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date curDate = new Date(timeMillis);
        return formatter.format(curDate);
    }

    public void openBottomMenu() {
        device.pressMenu();
        SystemClock.sleep(Constants.SHORT_WAIT);
        Assert.assertTrue(device.findObject(By.text(WEATHER_MENU_BUTTON_TEXT_UPDATE)).isEnabled());
        device.pressDPadUp();
        SystemClock.sleep(Constants.SHORT_WAIT);
    }

    public void ClickOnSpecifiedMenuButtonByText(String btnText) {
        UiObject2 btn = device.findObject(By.text(btnText)).getParent();
        for (int i = 0, moveTimes = 4; i < moveTimes; i++) {
            if (btn.isFocused()) {
                device.pressEnter();
                SystemClock.sleep(Constants.WAIT);
                return;
            }
            device.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
        }

        Assert.assertTrue("Failed to focus on Specified menu button.", false);
    }

    public String getSelectedLocationProvince() {
        UiObject2 provinceList = device.findObject(By.res("tv.fun.weather:id/ws_province"));
        UiObject2 middleProvince =
                provinceList.findObject(By.res("tv.fun.weather:id/wheel_view_tx3"));
        return middleProvince.getText();
    }

    public String getSelectedLocationCity() {
        UiObject2 cityList = device.findObject(By.res("tv.fun.weather:id/ws_city"));
        UiObject2 middleCity =
                cityList.findObject(By.res("tv.fun.weather:id/wheel_view_tx3"));
        return middleCity.getText();
    }

    public void selectSpecifiedLocationProvince(String provinceText, boolean directionUp) {
        UiObject2 provinceList = device.findObject(By.res("tv.fun.weather:id/ws_province"));
        UiObject2 middleProvince =
                provinceList.findObject(By.res("tv.fun.weather:id/wheel_view_tx3"));
        for (int i = 0, maxMoveTimes = 20; i < maxMoveTimes; i++) {
            if (provinceText.equals(middleProvince.getText())) {
                SystemClock.sleep(Constants.SHORT_WAIT);
                return;
            }
            if (directionUp) {
                device.pressDPadUp();
                SystemClock.sleep(Constants.SHORT_WAIT);
            } else {
                device.pressDPadDown();
                SystemClock.sleep(Constants.SHORT_WAIT);
            }
            middleProvince = provinceList.findObject(By.res("tv.fun.weather:id/wheel_view_tx3"));
        }

        Assert.assertTrue("The specified province is NOT found on city manager!", false);
    }

    public void selectSpecifiedLocationCity(String cityText, boolean directionUp) {
        UiObject2 cityList = device.findObject(By.res("tv.fun.weather:id/ws_city"));
        UiObject2 middleCity =
                cityList.findObject(By.res("tv.fun.weather:id/wheel_view_tx3"));
        for (int i = 0, maxMoveTimes = 20; i < maxMoveTimes; i++) {
            if (cityText.equals(middleCity.getText())) {
                return;
            }
            if (directionUp) {
                device.pressDPadUp();
                SystemClock.sleep(Constants.SHORT_WAIT);
            } else {
                device.pressDPadDown();
                SystemClock.sleep(Constants.SHORT_WAIT);
            }
            middleCity = cityList.findObject(By.res("tv.fun.weather:id/wheel_view_tx3"));
        }

        Assert.assertTrue("The specified city is NOT found on city manager!", false);
    }

}
