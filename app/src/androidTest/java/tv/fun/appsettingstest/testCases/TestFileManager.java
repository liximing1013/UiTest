package tv.fun.appsettingstest.testCases;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import tv.fun.appsettingstest.common.TaskFileManager;
import tv.fun.common.Constants;
import tv.fun.common.Utils;

import static tv.fun.common.Constants.FILEMANAGER_HOME_ACTIVITY;
import static tv.fun.common.Constants.FILEMANAGER_PKG_NAME;
import static tv.fun.common.Constants.LAUNCHER_PKG_NAME;

/**
 * Created by zhengjin on 2016/11/4.
 * <p>
 * TestFileManager
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestFileManager {
    // total 21

    private UiDevice mDevice;
    private TaskFileManager mTask;
    private long mExecTime;
    private String mErrorStack = null;

    private static final String TEST_ROOT_DIR_NAME = "AutoTestFiles";
    private static final String TEST_ROOT_DIR_PATH;
    private static final String TEST_DIR_NAME = "TestNonMediaDir";
    private static final String TEST_DIR_PATH;
    private static final String TEST_MEDIA_DIR_NAME = "TestMediaDir";
    private static final String TEST_MEDIA_DIR_PATH;

    private static final String TEST1_FILE_NAME = "TestFile1.log";
    private static final String TEST1_FILE_PATH;
    private static final String TEST2_FILE_NAME = "TestFile2.log";
    private static final String TEST2_FILE_PATH;
    private static final String TEST1_VIDEO_FILE_NAME = "TestVideo1.mp4";
    private static final String TEST1_VIDEO_FILE_PATH;
    private static final String TEST2_VIDEO_FILE_NAME = "TestVideo2.mp4";
    private static final String TEST2_VIDEO_FILE_PATH;

    private static final String TEXT_REMOVE_BUTTON = "删除";
    private static final String TEXT_HIDDEN_BUTTON = "隐藏";
    private static final String TEXT_SHOWALL_BUTTON = "显示全部";

    private final String TEXT_NO_VIDEO_IN_CATEGORY = "未发现可播放的视频";

    static {
        TEST_ROOT_DIR_PATH =
                String.format("%s/%s", Utils.getExternalStoragePath(), TEST_ROOT_DIR_NAME);

        TEST_DIR_PATH = String.format("%s/%s", TEST_ROOT_DIR_PATH, TEST_DIR_NAME);
        TEST1_FILE_PATH = String.format("%s/%s", TEST_ROOT_DIR_PATH, TEST1_FILE_NAME);
        TEST2_FILE_PATH = String.format("%s/%s", TEST_DIR_PATH, TEST2_FILE_NAME);

        TEST_MEDIA_DIR_PATH = String.format("%s/%s", TEST_ROOT_DIR_PATH, TEST_MEDIA_DIR_NAME);
        TEST1_VIDEO_FILE_PATH = String.format("%s/%s", TEST_MEDIA_DIR_PATH, TEST1_VIDEO_FILE_NAME);
        TEST2_VIDEO_FILE_PATH = String.format("%s/%s", TEST_MEDIA_DIR_PATH, TEST2_VIDEO_FILE_NAME);
    }

    private static void prepareData() {
        String message = "Prepare files for file manager test.";
        String cmdCreateTestDir = String.format("mkdir -p %s", TEST_DIR_PATH);
        String cmdCreateTxtFile1 = String.format("touch %s", TEST1_FILE_PATH);
        String cmdCreateTxtFile2 = String.format("touch %s", TEST2_FILE_PATH);

        String cmdCreateMediaDir = String.format("mkdir -p %s", TEST_MEDIA_DIR_PATH);
        String cmdCreateVideoFile1 = String.format("touch %s", TEST1_VIDEO_FILE_PATH);
        String cmdCreateVideoFile2 = String.format("touch %s", TEST2_VIDEO_FILE_PATH);

        String commands[] = {cmdCreateTestDir, cmdCreateTxtFile1, cmdCreateTxtFile2,
                cmdCreateMediaDir, cmdCreateVideoFile1, cmdCreateVideoFile2};

        Utils.CommandResult result = Utils.execCommand(commands, false, false);
        Assert.assertTrue(message, (result.mResult == 0));
    }

    private static void removeData() {
        String message = "Clear files for file manager test.";
        String removeAllFiles = String.format("rm -rf %s", TEST_ROOT_DIR_PATH);

        Utils.CommandResult result = Utils.execCommand(removeAllFiles, false, false);
        Assert.assertTrue(message, (result.mResult == 0));
    }

    @BeforeClass
    public static void classSetup() {
        Utils.stopAndClearProcess(FILEMANAGER_PKG_NAME);
        prepareData();
    }

    @AfterClass
    public static void classClearUp() {
        removeData();
    }

    @Before
    public void setUp() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mTask = TaskFileManager.getInstance();
        mExecTime = Utils.getCurSecond();

        mDevice.pressHome();
        Utils.waitForPackageOpened(mDevice, LAUNCHER_PKG_NAME);
        Utils.startActivity(FILEMANAGER_PKG_NAME, FILEMANAGER_HOME_ACTIVITY);
        SystemClock.sleep(Constants.WAIT);
    }

    @Test
    public void FileM_Category_11_01_OpenAllFilesCardFromSdcardTab() {
        try {
            mTask.openLocalFilesCard();

            UiObject2 mainTitle =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/activity_sub_title_main"));
            Utils.writeCaseResult("Verify the text of main title from sdcard local files.",
                    "本地存储".equals(mainTitle.getText()), mExecTime);

            UiObject2 subTitle =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/activity_sub_title_sub"));
            Utils.writeCaseResult("Verify the text of sub title from sdcard local files.",
                    "全部文件".equals(subTitle.getText()), mExecTime);
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
    public void FileM_Category_12_01_NavigateToSpecifiedPath() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);

            UiObject2 subTitle =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/activity_sub_title_sub"));
            Utils.writeCaseResult("Verify navigate to the specified path.",
                    TEST_ROOT_DIR_NAME.equals(subTitle.getText()), mExecTime);
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
    public void FileM_Category_12_03_MessageWhenEmptyForAppCard() {
        try {
            mTask.openCategoryAppCard();
            UiObject2 tips = mDevice.findObject(By.res("tv.fun.filemanager:id/sub_blank_tips"));
            Utils.writeCaseResult("Verify the tips when no files in APP card.",
                    "未发现可安装的应用".equals(tips.getText()), mExecTime);

            mDevice.pressMenu();
            SystemClock.sleep(Constants.SHORT_WAIT);
            UiObject2 menu = mDevice.findObject(By.res("android:id/tv_fun_menu"));
            Assert.assertNull("Verify the menu is NOT shown when no files in App card.", menu);
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
    public void FileM_Category_12_04_MessageWhenEmptyForMusicCard() {
        try {
            mTask.openCategoryMusicCard();
            UiObject2 tips = mDevice.findObject(By.res("tv.fun.filemanager:id/sub_blank_tips"));
            Utils.writeCaseResult("Verify the tips when no files in music card.",
                    "未发现可播放的音乐".equals(tips.getText()), mExecTime);

            mDevice.pressMenu();
            SystemClock.sleep(Constants.SHORT_WAIT);
            UiObject2 menu = mDevice.findObject(By.res("android:id/tv_fun_menu"));
            Assert.assertNull("Verify the menu is NOT shown when no files in music card.", menu);
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
    public void FileM_Category_12_05_MessageWhenEmptyForPictureCard() {
        try {
            mTask.openCategoryPictureCard();
            UiObject2 tips = mDevice.findObject(By.res("tv.fun.filemanager:id/sub_blank_tips"));
            Utils.writeCaseResult("Verify the tips when no files in picture card.",
                    "未发现可显示的图片".equals(tips.getText()), mExecTime);

            mDevice.pressMenu();
            SystemClock.sleep(Constants.SHORT_WAIT);
            UiObject2 menu = mDevice.findObject(By.res("android:id/tv_fun_menu"));
            Assert.assertNull("Verify the menu is NOT shown when no files in Pic card.", menu);
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
    public void FileM_Category_18_01_OpenUnknownTypeFile() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateAndOpenSpecifiedFile(TEST1_FILE_PATH);
            SystemClock.sleep(Constants.WAIT);

            UiObject2 subTitle = mDevice.findObject(By.text(TEST1_FILE_NAME));
            Utils.writeCaseResult(
                    "Verify open unknown type file.", subTitle.isEnabled(), mExecTime);
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
    public void FileM_Menu_01_01_MenuHideBtnExistForDir() {
        try {
            mTask.openLocalFilesCard();
            UiObject2 menuTips =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/activity_sub_menu_tips"));
            Utils.writeCaseResult("Verify the menu tips is displayed.",
                    menuTips.getText().contains("查看更多操作"), mExecTime);

            mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mDevice.pressDPadLeft();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mTask.showMenuAndRequestFocus();

            UiObject2 menuHideBtnContainer =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/menu_item_hide_id"));
            Utils.writeCaseResult("Verify the hide button is focused in the bottom menu.",
                    menuHideBtnContainer.isFocused(), mExecTime);

            UiObject2 menuHideBtn =
                    menuHideBtnContainer.findObject(By.res("android:id/tv_fun_menu_text"));
            Utils.writeCaseResult("Verify the text of hide button in the bottom menu.",
                    TEXT_HIDDEN_BUTTON.equals(menuHideBtn.getText()), mExecTime);
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
    public void FileM_Menu_01_02_MenuRemoveAndHideBtnExistForFile() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mTask.showMenuAndRequestFocus();

            UiObject2 menuRemoveBtnContainer =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/menu_item_del_id"));
            Utils.writeCaseResult("Verify the remove button is focused in the bottom menu.",
                    menuRemoveBtnContainer.isFocused(), mExecTime);

            UiObject2 menuRemoveBtn =
                    menuRemoveBtnContainer.findObject(By.res("android:id/tv_fun_menu_text"));
            Utils.writeCaseResult("Verify the text of remove button in the bottom menu.",
                    TEXT_REMOVE_BUTTON.equals(menuRemoveBtn.getText()), mExecTime);

            UiObject2 menuHideBtnContainer =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/menu_item_hide_id"));
            UiObject2 menuHideBtn =
                    menuHideBtnContainer.findObject(By.res("android:id/tv_fun_menu_text"));
            Utils.writeCaseResult("Verify the text of hide button in the bottom menu.",
                    TEXT_HIDDEN_BUTTON.equals(menuHideBtn.getText()), mExecTime);
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
    public void FileM_Menu_01_03_HideAndShowDirectory() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);

            mDevice.pressDPadUp();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mDevice.pressDPadLeft();
            SystemClock.sleep(Constants.SHORT_WAIT);
            mDevice.pressDPadLeft();
            SystemClock.sleep(Constants.SHORT_WAIT);

            mTask.showMenuAndClickBtn(TEXT_HIDDEN_BUTTON);
            UiObject2 fileHidden = mDevice.findObject(By.text(TEST_DIR_NAME));
            Utils.writeCaseResult("Verify the directory is hidden.", fileHidden == null, mExecTime);

            mTask.showMenuAndClickBtn(TEXT_SHOWALL_BUTTON);
            UiObject2 fileShow = mDevice.findObject(By.text(TEST_DIR_NAME));
            Utils.writeCaseResult("Verify the directory is show.", fileShow != null, mExecTime);
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
    public void FileM_Menu_01_04_HideAndShowFile() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);

            mTask.showMenuAndClickBtn(TEXT_HIDDEN_BUTTON);
            UiObject2 fileHidden = mDevice.findObject(By.text(TEST1_FILE_NAME));
            Utils.writeCaseResult("Verify the file is hidden.", fileHidden == null, mExecTime);

            mTask.showMenuAndClickBtn(TEXT_SHOWALL_BUTTON);
            UiObject2 fileShow = mDevice.findObject(By.text(TEST1_FILE_NAME));
            Utils.writeCaseResult("Verify the file is show.", fileShow != null, mExecTime);
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
    public void FileM_Menu_01_05_RemoveFileAndCancel() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);

            mTask.showMenuAndClickBtn(TEXT_REMOVE_BUTTON);
            UiObject2 cancelBtn =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/confirm_dialog_btn_cancel"));
            Utils.writeCaseResult("Verify the Cancel button of confirm dialog.",
                    cancelBtn != null, mExecTime);

            cancelBtn.click();
            SystemClock.sleep(Constants.WAIT);
            UiObject2 fileDeleted = mDevice.findObject(By.text(TEST1_FILE_NAME));
            Utils.writeCaseResult("Verify click cancel and do not remove a file.",
                    fileDeleted != null, mExecTime);
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
    public void FileM_Menu_01_06_RemoveFileAndConfirm() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
            mDevice.pressDPadRight();
            SystemClock.sleep(Constants.SHORT_WAIT);

            mTask.showMenuAndClickBtn(TEXT_REMOVE_BUTTON);
            UiObject2 confirmBtn =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/confirm_dialog_btn_confirm"));
            Utils.writeCaseResult("Verify the Yes button of confirm dialog.",
                    confirmBtn != null, mExecTime);

            confirmBtn.click();
            SystemClock.sleep(Constants.WAIT);
            UiObject2 fileDeleted = mDevice.findObject(By.text(TEST1_FILE_NAME));
            Utils.writeCaseResult("Verify click yes and remove a file.",
                    fileDeleted == null, mExecTime);
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
    public void FileM_Menu_02_01_HideAndShowOnlyFileOfDirectory() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_DIR_PATH);
            mDevice.pressDPadUp();
            SystemClock.sleep(Constants.SHORT_WAIT);

            mTask.showMenuAndClickBtn(TEXT_HIDDEN_BUTTON);
            UiObject2 tips = mDevice.findObject(By.res("tv.fun.filemanager:id/sub_blank_tips"));
            Utils.writeCaseResult("Verify the tips of empty directory from all files card.",
                    "未发现可显示的文件".equals(tips.getText()), mExecTime);

            mTask.showMenuAndClickBtn(TEXT_SHOWALL_BUTTON);
            UiObject2 hiddenFile = mDevice.findObject(By.text(TEST2_FILE_NAME));
            Utils.writeCaseResult("Verify the file is show after click Show All.",
                    hiddenFile.isEnabled(), mExecTime);
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
    public void FileM_Menu_02_02_RemoveOnlyFileOfDirectory() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_DIR_PATH);
            mDevice.pressDPadUp();
            SystemClock.sleep(Constants.SHORT_WAIT);

            mTask.showMenuAndClickBtn(TEXT_REMOVE_BUTTON);
            UiObject2 confirmBtn =
                    mDevice.findObject(By.res("tv.fun.filemanager:id/confirm_dialog_btn_confirm"));
            confirmBtn.click();
            SystemClock.sleep(Constants.WAIT);

            UiObject2 tips = mDevice.findObject(By.res("tv.fun.filemanager:id/sub_blank_tips"));
            Utils.writeCaseResult("Verify the tips of empty directory from all files card.",
                    "未发现可显示的文件".equals(tips.getText()), mExecTime);
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
    public void FileM_Menu_03_01_HideVideoFile() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_MEDIA_DIR_PATH);
            mTask.moveUntilSpecifiedItemSelected(TEST1_VIDEO_FILE_NAME);  // request focus

            mTask.showMenuAndClickBtn(TEXT_HIDDEN_BUTTON);
            UiObject2 fileHiddenFromAll = mDevice.findObject(By.text(TEST1_VIDEO_FILE_NAME));
            Utils.writeCaseResult("Verify the video file is hidden after click Hide button.",
                    fileHiddenFromAll == null, mExecTime);

            this.backToFileManagerHome();
            mTask.openCategoryVideoCard();

            UiObject2 fileHiddenFromCate = mDevice.findObject(By.text(TEST1_VIDEO_FILE_NAME));
            Utils.writeCaseResult("Verify the video file is hidden from video category.",
                    fileHiddenFromCate == null, mExecTime);

            UiObject2 fileUnchanged = mDevice.findObject(By.text(TEST2_VIDEO_FILE_NAME));
            Utils.writeCaseResult("Verify the unchanged video file is shown from video category.",
                    fileUnchanged != null, mExecTime);
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
    public void FileM_Menu_03_02_ShowVideoFile() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_MEDIA_DIR_PATH);

            mTask.showMenuAndClickBtn(TEXT_SHOWALL_BUTTON);
            UiObject2 fileShownFromAll = mDevice.findObject(By.text(TEST1_VIDEO_FILE_NAME));
            Utils.writeCaseResult("Verify the video file is shown after click Show All button.",
                    fileShownFromAll != null, mExecTime);

            this.backToFileManagerHome();
            mTask.openCategoryVideoCard();

            UiObject2 fileShownFromCate = mDevice.findObject(By.text(TEST1_VIDEO_FILE_NAME));
            Utils.writeCaseResult("Verify the video file is shown from video category.",
                    fileShownFromCate != null, mExecTime);

            UiObject2 fileUnchanged = mDevice.findObject(By.text(TEST2_VIDEO_FILE_NAME));
            Utils.writeCaseResult("Verify the unchanged video file is shown from video category.",
                    fileUnchanged != null, mExecTime);
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
    public void FileM_Menu_03_03_HideVideoDirectory() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);
            mTask.moveUntilSpecifiedItemSelected(TEST_MEDIA_DIR_NAME);

            mTask.showMenuAndClickBtn(TEXT_HIDDEN_BUTTON);
            UiObject2 mediaDirHidden = mDevice.findObject(By.text(TEST_MEDIA_DIR_NAME));
            Utils.writeCaseResult("Verify the video directory is hidden from All Files category.",
                    mediaDirHidden == null, mExecTime);

            this.backToFileManagerHome();
            mTask.openCategoryVideoCard();

            UiObject2 tips = mDevice.findObject(By.res("tv.fun.filemanager:id/sub_blank_tips"));
            Utils.writeCaseResult(
                    "Verify all video files in directory is hidden from Video category.",
                    TEXT_NO_VIDEO_IN_CATEGORY.equals(tips.getText()), mExecTime);
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
    public void FileM_Menu_03_04_ShowVideoDirectory() {
        try {
            mTask.openLocalFilesCard();
            mTask.showMenuAndClickBtn(TEXT_SHOWALL_BUTTON);
            mTask.navigateToSpecifiedPath(TEST_ROOT_DIR_PATH);

            UiObject2 mediaDirShown = mDevice.findObject(By.text(TEST_MEDIA_DIR_NAME));
            Utils.writeCaseResult("Verify the video directory is shown from All Files category.",
                    mediaDirShown != null, mExecTime);

            this.backToFileManagerHome();
            mTask.openCategoryVideoCard();

            String message =
                    "Verify all video files(%s) in directory is shown from Video category.";
            UiObject2 videoFileTest1 = mDevice.findObject(By.text(TEST1_VIDEO_FILE_NAME));
            Utils.writeCaseResult(String.format(message, TEST1_VIDEO_FILE_NAME),
                    videoFileTest1 != null, mExecTime);

            UiObject2 videoFileTest2 = mDevice.findObject(By.text(TEST2_VIDEO_FILE_NAME));
            Utils.writeCaseResult(String.format(message, TEST2_VIDEO_FILE_NAME),
                    videoFileTest2 != null, mExecTime);
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
    public void FileM_Menu_03_05_RemoveVideoFileFromAllFilesCategory() {
        try {
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_MEDIA_DIR_PATH);
            mTask.moveUntilSpecifiedItemSelected(TEST1_VIDEO_FILE_NAME);

            this.removeFileAndConfirm();
            UiObject2 fileRemovedFromAll = mDevice.findObject(By.text(TEST1_VIDEO_FILE_NAME));
            Utils.writeCaseResult("Verify the video file is removed after click Show All button.",
                    fileRemovedFromAll == null, mExecTime);

            this.backToFileManagerHome();
            mTask.openCategoryVideoCard();

            UiObject2 fileRemovedFromCate = mDevice.findObject(By.text(TEST1_VIDEO_FILE_NAME));
            Utils.writeCaseResult("Verify the video file is removed from video category.",
                    fileRemovedFromCate == null, mExecTime);

            UiObject2 fileUnchanged = mDevice.findObject(By.text(TEST2_VIDEO_FILE_NAME));
            Utils.writeCaseResult("Verify the unchanged video file is shown from video category.",
                    fileUnchanged != null, mExecTime);
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
    public void FileM_Menu_03_06_RemoveVideoFileFromVideoCategory() {
        try {
            mTask.openCategoryVideoCard();
            mTask.moveUntilSpecifiedItemSelected(TEST2_VIDEO_FILE_NAME);

            this.removeFileAndConfirm();
            UiObject2 fileRemovedFromCate = mDevice.findObject(By.text(TEST2_VIDEO_FILE_NAME));
            Utils.writeCaseResult("Verify the video file is removed from video category.",
                    fileRemovedFromCate == null, mExecTime);

            this.backToFileManagerHome();
            mTask.openLocalFilesCard();
            mTask.navigateToSpecifiedPath(TEST_MEDIA_DIR_PATH);

            UiObject2 fileRemovedFromAll = mDevice.findObject(By.text(TEST2_VIDEO_FILE_NAME));
            Utils.writeCaseResult("Verify the video file is removed from all files category.",
                    fileRemovedFromAll == null, mExecTime);
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
    public void FileM_Menu_03_07_MessageWhenEmptyFoVideoCard() {
        try {
            mTask.openCategoryVideoCard();
            UiObject2 tips = mDevice.findObject(By.res("tv.fun.filemanager:id/sub_blank_tips"));
            Utils.writeCaseResult("Verify the tips when no files in APP card.",
                    TEXT_NO_VIDEO_IN_CATEGORY.equals(tips.getText()), mExecTime);

            mDevice.pressMenu();
            SystemClock.sleep(Constants.SHORT_WAIT);
            UiObject2 menu = mDevice.findObject(By.res("android:id/tv_fun_menu"));
            Assert.assertNull("Verify the menu is NOT shown when no files in video card.", menu);
        } catch (Exception e) {
            e.printStackTrace();
            mErrorStack = e.toString();
        } finally {
            if (mErrorStack != null) {
                Utils.writeCaseResult(mErrorStack, false, mExecTime);
            }
        }
    }

    private void backToFileManagerHome() {
        Utils.startActivity(FILEMANAGER_PKG_NAME, FILEMANAGER_HOME_ACTIVITY);
        SystemClock.sleep(Constants.WAIT);
    }

    private void removeFileAndConfirm() {
        mTask.showMenuAndClickBtn(TEXT_REMOVE_BUTTON);
        UiObject2 btnConfirm =
                mDevice.findObject(By.res("tv.fun.filemanager:id/confirm_dialog_btn_confirm"));
        SystemClock.sleep(Constants.WAIT);
        if (btnConfirm != null) {
            btnConfirm.click();
            SystemClock.sleep(Constants.WAIT);
        }
    }

//    @Test
//    public void test() {
//        TvCommon.printAllMethods(this.getClass().getName());
//    }

}
