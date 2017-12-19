package tv.fun.appsettingstest.testCases;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import tv.fun.appsettingstest.common.TaskWeather;
import tv.fun.common.Constants;
import tv.fun.common.Utils;

import static tv.fun.common.Constants.LAUNCHER_PKG_NAME;
import static tv.fun.common.Constants.WEATHER_HOME_ACTIVITY;
import static tv.fun.common.Constants.WEATHER_PKG_NAME;

/**
 * Created by zhengjin on 2016/11/11.
 * <p>
 * Test cases for Weather app.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestWeather {
    // total 15

    private UiDevice mDevice;
    private TaskWeather mTask;
    private long mExecTime;
    private String mErrorStack = null;

    private final String INIT_PROVINCE = "湖北";
    private final String INIT_CITY = "武汉";
    private final String ADD_PROVINCE_1 = "山东";
    private final String ADD_CITY_1 = "青岛";
    private final String ADD_DEFAULT_PROVINCE_2 = "海南";
    private final String ADD_DEFAULT_CITY_2 = "三亚";
    private final String TAG = "lxm";

    @BeforeClass
    public static void classSetUp() {
        Utils.stopAndClearProcess(Constants.WEATHER_PKG_NAME);
    }

    @Before
    public void setUp() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mTask = TaskWeather.getInstance();
        mExecTime = Utils.getCurSecond();

        mDevice.pressHome();
        Utils.waitForPackageOpened(mDevice, LAUNCHER_PKG_NAME);
        Utils.startActivity(WEATHER_PKG_NAME, WEATHER_HOME_ACTIVITY);
        SystemClock.sleep(Constants.LONG_WAIT);
    }

    @After
    public void clearUp() {
        Utils.stopProcess(WEATHER_PKG_NAME);
    }

    @Test
    public void WEA_Home_01_01_testDefaultLocatedCity() {
        try {
            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Utils.writeCaseResult("Verify the default location on weather home.",
                    String.format("%s(默认)", INIT_CITY).equals(location.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void WEA_Home_01_02_testBottomTipOnWeatherHome() {
        try {
            UiObject2 tip = mDevice.findObject(By.res("tv.fun.weather:id/tv_tip"));
            Utils.writeCaseResult("Verify the tip at the bottom of weather home.",
                    tip.getText().contains("菜单键管理城市"), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void WEA_Home_12_01_testWeatherForecastDates() {
        try {
            List<UiObject2> dates =
                    mDevice.findObjects(By.res("tv.fun.weather:id/tv_weather_date"));
            final int size = dates.size();
            List<String> actualDates = new ArrayList<>(size);
            for (UiObject2 date : dates) {
                actualDates.add(mTask.getWeatherForecastDateFromUiText(date.getText()));
            }
            Collections.sort(actualDates, new dateComparator());

            List<String> expectedDates = mTask.getWeatherForecastDates();
            Collections.sort(expectedDates, new dateComparator());

            Utils.writeCaseResult("Verify the length of weather forecast dates is 5.",
                    actualDates.size() == expectedDates.size(), mExecTime);

            for (int i = 0; i < size; i++) {
                Utils.writeCaseResult(
                        String.format(Locale.getDefault(),
                                "Verify the forecast date at position %d", i),
                        expectedDates.get(i).equals(actualDates.get(i)), mExecTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void WEA_Home_20_01_testFunctionButtonsInMenu() {
        try {
            mTask.openBottomMenu();

            UiObject2 btnUpdate = mDevice.findObject(
                    By.text(mTask.WEATHER_MENU_BUTTON_TEXT_UPDATE)).getParent();
            Utils.writeCaseResult("Verify update button exist.",
                    btnUpdate.isClickable(), mExecTime);
            Utils.writeCaseResult("Verify update button is default focused.",
                    btnUpdate.isFocused(), mExecTime);

            UiObject2 btnModifyDefault = mDevice.findObject(
                    By.text(mTask.WEATHER_MENU_BUTTON_TEXT_MODIFY_DEFAULT)).getParent();
            Utils.writeCaseResult("Verify modify default city button exist.",
                    btnModifyDefault.isClickable(), mExecTime);

            UiObject2 btnAddCity = mDevice.findObject(
                    By.text(mTask.WEATHER_MENU_BUTTON_TEXT_ADD_CITY)).getParent();
            Utils.writeCaseResult("Verify add city button exist.",
                    btnAddCity.isClickable(), mExecTime);

            UiObject2 textDeleteCity =
                    mDevice.findObject(By.text(mTask.WEATHER_MENU_BUTTON_TEXT_DELETE_CITY));
            Utils.writeCaseResult("Verify the delete city button NOT exist.",
                    textDeleteCity == null, mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void WEA_Home_20_02_testUpdateAndRefreshWeatherData() {
        try {
            mTask.openBottomMenu();
            mTask.ClickOnSpecifiedMenuButtonByText(mTask.WEATHER_MENU_BUTTON_TEXT_UPDATE);

            UiObject2 refreshTime =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_refresh_time"));
            Utils.writeCaseResult(
                    "Verify the refresh time is updated after click Update menu button.",
                    "刚刚更新".equals(refreshTime.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void WEA_Menu_01_01_testAddCityAndCancel() {
        try {
            mTask.openBottomMenu();
            mTask.ClickOnSpecifiedMenuButtonByText(mTask.WEATHER_MENU_BUTTON_TEXT_ADD_CITY);

            // verification 1
            UiObject2 title = mDevice.findObject(By.res("tv.fun.weather:id/setting_title"));
            Utils.writeCaseResult("Verify the title of add city page.",
                    "添加城市".equals(title.getText()), mExecTime);
            Utils.writeCaseResult("Verify the default located province.",
                    INIT_PROVINCE.equals(mTask.getSelectedLocationProvince()), mExecTime);
            Utils.writeCaseResult("Verify the default located city.",
                    INIT_CITY.equals(mTask.getSelectedLocationCity()), mExecTime);

            // select 山东 青岛
            mTask.selectSpecifiedLocationProvince(ADD_PROVINCE_1, true);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mTask.selectSpecifiedLocationCity(ADD_CITY_1, false);
            mDevice.pressBack();
            SystemClock.sleep(Constants.WAIT);

            // verification 2
            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Utils.writeCaseResult("Verify the shown city is not changed after cancel add city.",
                    String.format("%s(默认)", INIT_CITY).equals(location.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void WEA_Menu_01_02_testAddNewCityWeather() {
        try {
            mTask.openBottomMenu();
            mTask.ClickOnSpecifiedMenuButtonByText(mTask.WEATHER_MENU_BUTTON_TEXT_ADD_CITY);

            // select 山东 青岛
            mTask.selectSpecifiedLocationProvince(ADD_PROVINCE_1, true);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mTask.selectSpecifiedLocationCity(ADD_CITY_1, false);
            mDevice.pressEnter();
            SystemClock.sleep(Constants.WAIT);

            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Utils.writeCaseResult("Verify the shown city after add new city weather.",
                    ADD_CITY_1.equals(location.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void WEA_Menu_05_01_testAddDefaultCityAndCancel() {
        try {
            mTask.openBottomMenu();
            mTask.ClickOnSpecifiedMenuButtonByText(mTask.WEATHER_MENU_BUTTON_TEXT_MODIFY_DEFAULT);

            // verification 1
            UiObject2 title = mDevice.findObject(By.res("tv.fun.weather:id/setting_title"));
            Utils.writeCaseResult("Verify the title of modify default city page.",
                    "修改默认城市".equals(title.getText()), mExecTime);
            Utils.writeCaseResult("Verify the default located province.",
                    INIT_PROVINCE.equals(mTask.getSelectedLocationProvince()), mExecTime);
            Utils.writeCaseResult("Verify the default located city.",
                    INIT_CITY.equals(mTask.getSelectedLocationCity()), mExecTime);

            // select 海南 三亚
            mTask.selectSpecifiedLocationProvince(ADD_DEFAULT_PROVINCE_2, false);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mTask.selectSpecifiedLocationCity(ADD_DEFAULT_CITY_2, false);
            mDevice.pressBack();
            SystemClock.sleep(Constants.WAIT);

            // verification 2
            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Utils.writeCaseResult(
                    "Verify the shown city is not changed after cancel add new default city.",
                    String.format("%s(默认)", INIT_CITY).equals(location.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void WEA_Menu_05_02_testAddDefaultCityWeather() {
        try {
            mTask.openBottomMenu();
            mTask.ClickOnSpecifiedMenuButtonByText(mTask.WEATHER_MENU_BUTTON_TEXT_MODIFY_DEFAULT);

            // select 海南 三亚
            mTask.selectSpecifiedLocationProvince(ADD_DEFAULT_PROVINCE_2, false);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mTask.selectSpecifiedLocationCity(ADD_DEFAULT_CITY_2, false);
            mDevice.pressEnter();
            SystemClock.sleep(Constants.WAIT);

            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Utils.writeCaseResult("Verify the shown city after add new default city.",
                    String.format("%s(默认)", ADD_DEFAULT_CITY_2).equals(location.getText()),
                    mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void WEA_Menu_05_03_testFirstCityAfterAddDefaultCity() {
        try {
            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Utils.writeCaseResult("Verify the 1st shown city after add new default city.",
                    String.format("%s(默认)", ADD_DEFAULT_CITY_2).equals(location.getText()),
                    mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void WEA_Menu_06_01_testModifyDefaultCity() {
        try {
            mTask.openBottomMenu();
            mTask.ClickOnSpecifiedMenuButtonByText(mTask.WEATHER_MENU_BUTTON_TEXT_MODIFY_DEFAULT);

            // select 湖北 武汉
            mTask.selectSpecifiedLocationProvince(INIT_PROVINCE, true);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mTask.selectSpecifiedLocationCity(INIT_CITY, false);
            mDevice.pressEnter();
            SystemClock.sleep(Constants.WAIT);

            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Utils.writeCaseResult("Verify the shown city after change default city.",
                    String.format("%s(默认)", INIT_CITY).equals(location.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void WEA_Menu_06_02_testFirstCityAfterModifyDefaultCity() {
        try {
            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Utils.writeCaseResult("Verify the 1st shown city after change default city.",
                    String.format("%s(默认)", INIT_CITY).equals(location.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void WEA_Menu_07_01_testReAddDefaultCity() {
        try {
            mTask.openBottomMenu();
            mTask.ClickOnSpecifiedMenuButtonByText(mTask.WEATHER_MENU_BUTTON_TEXT_MODIFY_DEFAULT);
            // select 湖北 武汉
            mTask.selectSpecifiedLocationProvince(INIT_PROVINCE, true);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mTask.selectSpecifiedLocationCity(INIT_CITY, false);
            mDevice.pressEnter();
            SystemClock.sleep(Constants.WAIT);

            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Utils.writeCaseResult("Verify the shown city after re-add same default city.",
                    String.format("%s(默认)", INIT_CITY).equals(location.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void WEA_Menu_07_02_testSequenceAfterAddedCities() {
        try {
            UiObject2 location =
                    mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Utils.writeCaseResult("Verify the 1st default city",
                    String.format("%s(默认)", INIT_CITY).equals(location.getText()), mExecTime);

            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.WAIT);
            location = mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Utils.writeCaseResult("Verify the 2nd added city.",
                    ADD_CITY_1.equals(location.getText()), mExecTime);

            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.WAIT);
            location = mDevice.findObject(By.res("tv.fun.weather:id/tv_weather_day_addr"));
            Utils.writeCaseResult("Verify the 3rd added city.",
                    ADD_DEFAULT_CITY_2.equals(location.getText()), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    @Test
    public void WEA_Menu_09_01_testDeleteMenuButton() {
        try {
            mTask.openBottomMenu();

            UiObject2 btnDeleteCity = mDevice.findObject(
                    By.text(mTask.WEATHER_MENU_BUTTON_TEXT_DELETE_CITY)).getParent();
            Utils.writeCaseResult("Verify delete button exist.",
                    btnDeleteCity.isClickable(), mExecTime);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    private class dateComparator implements Comparator<String> {
        @Override
        public int compare(String arg1, String arg2) {
            int tmpInt1 =
                    Integer.parseInt(arg1.substring((arg1.indexOf("月") + 1), (arg1.length() - 1)));
            int tmpInt2 =
                    Integer.parseInt(arg2.substring((arg2.indexOf("月") + 1), (arg2.length() - 1)));
            return tmpInt1 - tmpInt2;
        }
    }

//    @Test
//    public void test() {
//        TvCommon.printAllMethods(this.getClass().getName());
//    }

}
