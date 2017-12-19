package tv.fun.appstoretest.testCases;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.appstoretest.common.ChildrenPage;
import tv.fun.common.Utils;


/**
 * Created by liuqing on 2017/5/4.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestTVChildren extends ChildrenPage {

        /**
         * Navigate to TV Children page via Children card under launcher video tab
         */
        @Test
        public void Child_Home_02_testNavigateToChildrenFromChildUnderVideoTab() {
            try {
                //在launcher视频tab页面点击“少儿”卡片
                navigateToChildrenByChildrenCardUnderVideo();
                waitForElementPresentByID("tv.fun.children:id/tab_title_setting");
                //断言：少儿页面正确显示
                UiObject childrenTab = findElementByID("tv.fun.children:id/tab_title_children");
                UiObject childrenSetting = findElementByID("tv.fun.children:id/tab_title_setting");
                UiObject searchBtnObj = findElementByID("tv.fun.children:id/tab_title_search");
                verifyElementPresent("点击视频tab页面少儿卡片后没有正常进入少儿页面", childrenTab);
                verifyElementPresent("设置图标没有显示在少儿页面", childrenSetting);
                verifyElementPresent("", searchBtnObj);
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
         * Navigate to TV Children page via Children card in video category page
         */
        @Test
        public void Child_Home_03_testNavigateToChildrenFromVideoCategory() {
            try {
                //在launcher页面点击“视频分类”卡片
                home();
                UiObject videoCategoryObj = findElementByText("视频分类", "com.bestv.ott:id/title");
                if(!videoCategoryObj.isSelected()) {
                    videoCategoryObj.click();
                }
                videoCategoryObj.clickAndWaitForNewWindow();
                //点击“少儿”卡片
                UiObject childCardObj = findElementByText("少儿", "com.bestv.ott:id/maintitle");
                childCardObj.click();
                childCardObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/tab_title");
                //断言：少儿页面正确显示
                UiObject childrenTab = findElementByID("tv.fun.children:id/tab_title_children");
                UiObject childrenSetting = findElementByID("tv.fun.children:id/tab_title_setting");
                verifyElementPresent("点击视频tab页面少儿卡片后没有正常进入少儿页面", childrenTab);
                verifyElementPresent("设置图标没有显示在少儿页面", childrenSetting);
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
         *Test that can navigate to children page from 七巧板
         */
        // @Test
        public void Child_Home_04_testNavigateToChildrenFromSevenPiece(){
            try {
                //长按home键
//            home();
                //长按home键
//            longPressHome(KeyEvent.KEYCODE_HOME);
//            device.pressKeyCode( KeyEvent.KEYCODE_HOME, 5000);
//            device.pressHome();
//            Utils.stopProcess(CHILDREN_PKG_NAME);
//            longPressKeyCode(KeyEvent.KEYCODE_HOME, 5000);
//            longPressHome();


                //点击七巧板上少儿频道卡片
                UiObject childrenIconObj = findElementByText("少儿频道", "com.bestv.ott:id/maintitle");
                if(!childrenIconObj.isSelected()) {
                    childrenIconObj.click();
                }
                childrenIconObj.clickAndWaitForNewWindow();
                //断言：少儿页面正确显示
                waitForElementPresentByID("tv.fun.children:id/tab_title");
                UiObject childrenTab = findElementByID("tv.fun.children:id/tab_title_children");
                UiObject childrenSetting = findElementByID("tv.fun.children:id/tab_title_setting");
                verifyElementPresent("点击视频tab页面少儿卡片后没有正常进入少儿页面", childrenTab);
                verifyElementPresent("设置图标没有显示在少儿页面", childrenSetting);
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
         *Test that can navigate to children page after clicking 智能桌面 in launcher pop-up
         */
        @Test
        public void Child_Home_05_testNavigateToChildrenFromLauncherPopUp(){
            try {
                //点击launcher悬浮框上智能桌面卡片
                home();
                moveUpForMultiple(2);
                if(!findElementByID(timeIDInPopup).exists()){
                    moveUpForMultiple(2);
                }
                UiObject desktopIconObj = findElementByText("智能桌面", "com.bestv.ott:id/launcher_title");
                if(!desktopIconObj.isSelected()) {
                    desktopIconObj.click();
                }
                desktopIconObj.click();
                waitForElementPresentByID("tv.fun.settings:id/title");
                //点击智能桌面弹框上儿童桌面
                UiObject childDesktopObj = findElementByText("儿童桌面", "tv.fun.settings:id/title");
                moveRight();
                if(!childDesktopObj.isSelected()) {
                    moveUp();
                    moveRight();
                }
                enter();
                //断言：少儿页面正确显示
                waitForElementPresentByID("tv.fun.children:id/tab_title");
                UiObject childrenTab = findElementByID("tv.fun.children:id/tab_title_children");
                UiObject childrenSetting = findElementByID("tv.fun.children:id/tab_title_setting");
                verifyElementPresent("点击视频tab页面少儿卡片后没有正常进入少儿页面", childrenTab);
                verifyElementPresent("设置图标没有显示在少儿页面", childrenSetting);
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
         * Children Desktop UI displays correctly
         */
        @Test
        public void Child_Home_07_testChildrenDesktopUIDisplay(){
            try{
                navigateToChildrenDesktopPage();
                //断言
                UiObject suggestTabObj = findElementByID("tv.fun.children:id/tab_title_recommend");
                UiObject childTabObj = findElementByID("tv.fun.children:id/tab_title_children");
                UiObject brandTabObj = findElementByID("tv.fun.children:id/tab_title_brand");
                UiObject searchObj = findElementByID("tv.fun.children:id/tab_title_search");
                UiObject settingObj = findElementByID("tv.fun.children:id/tab_title_setting");
                verifyElementPresent("", suggestTabObj);
                verifyElementPresent("", childTabObj);
                verifyElementPresent("", brandTabObj);
                verifyElementPresent("", searchObj);
                verifyElementPresent("", settingObj);
                verifyTrue("", suggestTabObj.isFocused());
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
         * Children tab page UI displays correctly
         */
        @Test
        public void Child_Home_08_testChildrenTabUIDisplay(){
            try{
                navigateToChildrenDesktopPage();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //断言
                UiObject childHistoryObj = findElementByID("tv.fun.children:id/children_history");
                UiObject myListObj = findElementByID("tv.fun.children:id/children_menu");
                UiObject latestHistoryObj = findElementByID("tv.fun.children:id/children_history_icon");
                verifyElementPresent("", childHistoryObj);
                verifyElementPresent("", myListObj);
                verifyElementPresent("", latestHistoryObj);
                verifyString("", childHistoryObj.getText(), "播放记录");
                verifyString("", myListObj.getText(), "我的收藏");
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
         * Check the suggested video in children tab page when no playing record exists
         */
        @Test
        public void Child_Home_09_testSuggestVideoWhenNoPlayRecord(){
            try{
                //进入少儿桌面
                navigateToChildrenDesktopPage();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //点击播放记录卡片
                navigateToPlayRecordPage();
                UiObject recordList = findElementByID("tv.fun.children:id/listView");
                if(!findElementByID("tv.fun.children:id/empty").exists()){
                    moveDown();
                    menu();
                    UiObject clearObj = findElementByText("清除所有", "tv.fun.children:id/menu_text");
                    clearObj.click();
                    waitForElementPresentByID("tv.fun.children:id/empty");
                }
                back();
                //断言
                UiObject firstCardObj = findElementByID("tv.fun.children:id/children_view_history");
                int firstCardEleCount = firstCardObj.getChildCount();
                UiObject firstSuggestCardTitle = firstCardObj.getChild(new UiSelector().resourceId("tv.fun.children:id/children_history_title"));
                UiObject firstSuggestCardSubTitle = firstCardObj.getChild(new UiSelector().resourceId("tv.fun.children:id/children_history_sub_title"));
                verifyNumber("", firstCardEleCount, 3);
                verifyElementPresent("", firstSuggestCardTitle);
                verifyElementPresent("", firstSuggestCardSubTitle);
                verifyFalse("", firstSuggestCardSubTitle.getText().contains("观看第"));
                verifyFalse("", firstSuggestCardSubTitle.getText().contains("集至"));
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
         * Test the playing record page displaying when no playing record
         */
        @Test
        public void Child_Home_11_testPlayRecordPageWhenNoRecord(){
            try{
                //进入少儿桌面
                navigateToChildrenDesktopPage();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //点击播放记录卡片
                navigateToPlayRecordPage();
                UiObject recordList = findElementByID("tv.fun.children:id/listView");
                if(!findElementByID("tv.fun.children:id/empty").exists()){
                    menu();
                    UiObject clearObj = findElementByText("清除所有", "tv.fun.children:id/menu_text");
                    clearObj.click();
                    waitForElementPresentByID("tv.fun.children:id/empty");
                    back();
                    navigateToPlayRecordPage();
                }
                //断言
                UiObject historyObj = findElementByText("播放记录", "tv.fun.children:id/history");
                verifyTrue("", historyObj.isSelected()&&historyObj.isFocused());
                verifyString("", findElementByID("tv.fun.children:id/empty").getText(), "您还没有看过任何影片");
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
         * Test playing record page displaying when have playing records
         */
        @Test
        public void Child_Home_12_testPlayRecordPageWhenHaveRecord(){
            try{
                //进入少儿页面
                navigateToChildrenDesktopPage();
                waitForElementPresentByID("tv.fun.children:id/tab_title_children");
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //点击播放记录卡片
                navigateToPlayRecordPage();
                //准备测试条件：有播放记录
                    if(findElementByID("tv.fun.children:id/empty").exists()){
                        back();
                    UiObject historyObj = findElementByID("tv.fun.children:id/children_history_icon");
                    historyObj.clickAndWaitForNewWindow();
                    Thread.sleep(2000);
                    back();
                    back();
                    if(findElementByID("com.bestv.ott:id/playerflipper").exists()){
                        back();
                        back();
                    }
                    navigateToPlayRecordPage();
                }
                UiObject recordList = findElementByID("tv.fun.children:id/listView");
                int recordCount = recordList.getChildCount();
                verifyNumberEqualORLarger("", recordCount, 1);
                UiObject timeStrObj = findElementByID("tv.fun.children:id/timestamp");
                verifyTrue("", timeStrObj.getText().contains("观看第"));
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
         * Test my favorite page displaying when no restored record
         */
        @Test
        public void Child_Home_13_testMyFavoritePageWhenNoRecord(){
            try{
                //进入少儿页面
                navigateToChildrenDesktopPage();
                waitForElementPresentByID("tv.fun.children:id/tab_title_children");
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //点击“我的收藏”
                navigateToMyCollectionPage();
                //准备测试条件：无收藏记录
                if(!findElementByID("tv.fun.children:id/empty").exists()){
                    moveDown();
                    if(!findElementByText("我的收藏", "tv.fun.children:id/favorite").isSelected()){
                        moveRightForMultiple(2);
                        moveDown();
                    }
                    menu();
                    UiObject clearObj = findElementByText("清除所有", "tv.fun.children:id/menu_text");
                    clearObj.click();
                    moveRightForMultiple(2);
                    if(!findElementByID("tv.fun.children:id/empty").exists()){
                        moveDown();
                        menu();
                        clearObj = findElementByText("清除所有", "tv.fun.children:id/menu_text");
                        clearObj.click();
                    }
                    waitForElementPresentByID("tv.fun.children:id/empty");
                }
                back();
                if(!findElementByID("tv.fun.children:id/tab_title_children").isFocused()){
                    moveLeftForMultiple(2);
                    moveRight();
                }
                navigateToMyCollectionPage();
                //断言
                UiObject historyObj = findElementByText("我的收藏", "tv.fun.children:id/favorite");
                verifyTrue("", historyObj.isSelected()&&historyObj.isFocused());
                verifyString("", findElementByID("tv.fun.children:id/empty").getText(), "您还没收藏任何影片");
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
         * Test my favorite page displaying when have restored record
         */
        @Test
        public void Child_Home_14_testMyFavoritePageWhenHaveRecord(){
            try{
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //点击“我的收藏”
                UiObject myFavoriteObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
                myFavoriteObj.clickAndWaitForNewWindow();
                //准备测试条件：有收藏记录
                if(findElementByID("tv.fun.children:id/empty").exists()) {
                    back();
                    //点击任一列表卡片：例如动画大全
                    UiObject allVideoObj = findElementByID("tv.fun.children:id/children_view_item2");
                    allVideoObj.clickAndWaitForNewWindow();
                    waitForElementPresentByID("tv.fun.children:id/filter");
                    moveDown();
                    moveRightForMultiple(3);
                    if(device.findObject(new UiSelector().className("android.widget.Button").selected(true)).exists()){
                        moveUpForMultiple(3);
                        moveRightForMultiple(3);
                        Thread.sleep(3000);
                    }
                    moveDown();
                    menu();
                    UiObject collObj = findElementByText("收藏", "tv.fun.children:id/menu_text");
                    collObj.click();
                    waitForElementNotPresent("tv.fun.children:id/menu_text");
                    back();
                    //点击我的收藏卡片
                    myFavoriteObj.clickAndWaitForNewWindow();
                }
                UiObject recordList = findElementByID("tv.fun.children:id/listView");
                int recordCount = recordList.getChildCount();
                verifyNumberEqualORLarger("", recordCount, 1);
                UiObject timeStrObj = findElementByID("tv.fun.children:id/timestamp");
                verifyTrue("", timeStrObj.getText().equalsIgnoreCase("已收藏"));
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
         * Test go to search page via clicking search icon in Children Home page
         */
        @Test
        public void Child_Home_15_testSearchPageViaSearchIconInHome(){
            try{
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //点击少儿首页“搜索”icon
                UiObject searchObj = findElementByID("tv.fun.children:id/tab_title_search");
                searchObj.clickAndWaitForNewWindow();
                //断言
                UiObject searchResultObj = findElementByID("tv.fun.children:id/searchResult");
                UiObject searchTitleObj = findElementByID("tv.fun.children:id/resultTitle");//大家都在搜：
                UiObject searchInputObj = findElementByID("tv.fun.children:id/search_input");//输入首字母或全拼
                verifyElementPresent("", searchResultObj);
                verifyString("", searchTitleObj.getText(), "大家都在搜：");
                verifyString("", searchInputObj.getText(), "输入首字母或全拼");
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
         * Test go to parental setting page via setting icon in Children home page
         */
        @Test
        public void Child_Home_16_testParentalSettingPageViaSettingIconInHome(){
            try{
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //点击少儿首页“家长设置”icon
                UiObject settingObj = findElementByID("tv.fun.children:id/tab_title_setting");
                settingObj.clickAndWaitForNewWindow();
                inputChildrenKeyPasswordIfPopupDisplay();
                //断言
                UiObject settingTitleObj = findElementByID("tv.fun.children:id/settings_title");//家长设置
                UiObject sexObj = findElementByID("tv.fun.children:id/settings_item_sex_icon");//宝贝性别
                UiObject birthObj = findElementByID("tv.fun.children:id/settings_birth");//宝贝生日
                UiObject birthLableObj = birthObj.getChild(new UiSelector().className("android.widget.TextView").index(0));
                UiObject playObj = findElementByID("tv.fun.children:id/settings_play_time");//播放时长控制
                UiObject playLableObj = playObj.getChild(new UiSelector().className("android.widget.TextView").index(0));
                UiObject lockObj = findElementByID("tv.fun.children:id/settings_lock");//儿童锁
                UiObject lockLableObj = lockObj.getChild(new UiSelector().className("android.widget.TextView").index(0));
                UiObject eyeModeObj = findElementByID("tv.fun.children:id/settings_eye_mode");//护眼模式
                UiObject eyeModeLabelObj = eyeModeObj.getChild(new UiSelector().className("android.widget.TextView").index(0));
                verifyElementPresent("", settingTitleObj);
                verifyString("", settingTitleObj.getText(), "家长设置");
                verifyElementPresent("", sexObj);
                verifyString("", sexObj.getText(), "宝贝性别");
                verifyIncludeString("", birthLableObj.getText(), "宝贝生日");
                verifyIncludeString("", playLableObj.getText(), "播放时长控制");
                verifyIncludeString("", lockLableObj.getText(), "儿童锁");
                verifyIncludeString("", eyeModeLabelObj.getText(), "护眼模式");
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
         * Test navigate to Bilingual Area page via bilingual Area card in Children home page
         */
        @Test
        public void Child_Home_17_testBilingualAreaPageViaCardInHome(){
            try{
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //移动到品牌专区tab
                moveToTargetTabInChildren(childTabs[2]);
                //点击少儿首页“双语专区”卡片
                UiObject bilingualObj = findElementByID("tv.fun.children:id/children_brand_item9");
                bilingualObj.clickAndWaitForNewWindow();
                //断言
                UiObject listObj = findElementByID("tv.fun.children:id/listView");
                int videoCount = listObj.getChildCount();
                verifyNumberLarger("", videoCount, 1);
                moveRight();
                UiObject videoCardObj = listObj.getChild(new UiSelector().className("android.widget.LinearLayout").index(0));
                int videoCardCount = videoCardObj.getChildCount();
                verifyNumber("", videoCardCount,3);
                UiObject cardEle = videoCardObj.getChild(new UiSelector().resourceId("tv.fun.children:id/card"));
                UiObject chineseBtn = videoCardObj.getChild(new UiSelector().resourceId("tv.fun.children:id/chinese"));
                UiObject englishBtn = videoCardObj.getChild(new UiSelector().resourceId("tv.fun.children:id/english"));
                verifyElementPresent("", cardEle);
                verifyElementPresent("", chineseBtn);
                verifyElementPresent("", englishBtn);
                verifyString("", chineseBtn.getText(), "中文版");
                verifyString("", englishBtn.getText(), "英文版");
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
         * Test age list page via clicking age list card in Children home page
         */
        @Test
        public void Child_Home_19_testAgeListPageViaAgeCardInHome(){
            try{
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //点击少儿首页“按年龄”卡片
                UiObject ageListObj = findElementByID("tv.fun.children:id/children_view_item6");
                ageListObj.clickAndWaitForNewWindow();
                moveRightForMultiple(2);
                Thread.sleep(1000);
                //断言
                UiObject tabSectionObj = findElementByID("tv.fun.children:id/tabTitleSection");
                UiObject searchObj = tabSectionObj.getChild(new UiSelector().resourceId("tv.fun.children:id/search"));
                UiObject firstAgeTabObj = findElementByID("tv.fun.children:id/tabContainer").getChild(new UiSelector().className("android.widget.Button").text("0-3岁"));
                UiObject secondAgeTabObj = findElementByID("tv.fun.children:id/tabContainer").getChild(new UiSelector().className("android.widget.Button").text("4-6岁"));
                UiObject thirdAgeTabObj = findElementByID("tv.fun.children:id/tabContainer").getChild(new UiSelector().className("android.widget.Button").text("7岁以上"));
                UiObject listObj = findElementByID("tv.fun.children:id/listView");
                int videoCount = listObj.getChildCount();
                verifyNumberLarger("", videoCount, 1);
                verifyTrue("", firstAgeTabObj.exists()&&secondAgeTabObj.exists()&&thirdAgeTabObj.exists());
                UiObject videoCardObj = listObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                UiObject imageObj = videoCardObj.getChild(new UiSelector().resourceId("tv.fun.children:id/image"));
                UiObject videoTitleObj = videoCardObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                verifyElementPresent("", imageObj);
                verifyElementPresent("", videoTitleObj);
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
         * Test navigate to cartoon star page via clicking cartoon star card in Children home page
         */
        @Test
        public void Child_Home_20_testCartoonStarPageViaStarCardInHome(){
            try{
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //点击少儿首页“卡通明星”卡片
                UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
                cartoonStarObj.clickAndWaitForNewWindow();
                moveRightForMultiple(1);
                //断言
                UiObject cartoonStarTitleObj = findElementByID("tv.fun.children:id/listTitleImage");//卡通明星
                UiObject listObj = findElementByID("tv.fun.children:id/listView");
                int starCount = listObj.getChildCount();
                verifyNumberLarger("", starCount, 1);
                verifyString("", cartoonStarTitleObj.getText(), "卡通明星");
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
         * Test navigate to 动画大全 page via clicking 动画大全 card in Children home page
         */
        @Test
        public void Child_Home_21_testAllChildrenVideoPageViaCardInHome(){
            String[] tabs = {"全部", "推荐", "专题", "金卡VIP", "卡通明星", "安全教育", "爆笑乐园", "冒险世界", "动画电影"};
            try{
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //点击少儿首页“动画大全”卡片
                UiObject allVideoObj = findElementByID("tv.fun.children:id/children_view_item2");
                allVideoObj.clickAndWaitForNewWindow();
                moveRightForMultiple(3);
                Thread.sleep(1000);
                //断言
                UiObject suggestObj = device.findObject(new UiSelector().className("android.widget.Button").text("推荐"));
                UiObject listObj = findElementByID("tv.fun.children:id/listView");
                int videoCount = listObj.getChildCount();
                verifyNumberLarger("", videoCount, 1);
                UiObject sectionObj = findElementByID("tv.fun.children:id/tabTitleSection");
                UiObject firstLabelObj = sectionObj.getChild(new UiSelector().resourceId("tv.fun.children:id/search"));
                UiObject secLabelObj = sectionObj.getChild(new UiSelector().resourceId("tv.fun.children:id/filter"));
                verifyElementPresent("", firstLabelObj);
                verifyElementPresent("", secLabelObj);
                UiObject tabListObj = findElementByID("tv.fun.children:id/tabContainer");
                int tabCount = tabListObj.getChildCount();
                for (int i=0; i<tabCount; i++){
                    if(tabCount>9){
                        tabCount=9;
                    }
                    UiObject eachTabObj = tabListObj.getChild(new UiSelector().className("android.widget.Button").index(i));
                    String eachTabName = eachTabObj.getText();
                    verifyString("", eachTabObj.getText(), tabs[i]);
                }
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
         * Test navigate to topic page via clicking topic card in Children home page
         */
        @Test
        public void Child_Home_22_testGotoTopicPageViaCardInHome(){
            try{
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //点击少儿首页任一专题卡片
                UiObject earlyTeachObj = findElementByID("tv.fun.children:id/children_view_item5");
                earlyTeachObj.clickAndWaitForNewWindow();
                //断言
                UiObject listObj = findElementByID("tv.fun.children:id/container");
                UiObject languageObj = listObj.getChild(new UiSelector().resourceId("tv.fun.children:id/language_class"));
                UiObject afterObj = listObj.getChild(new UiSelector().resourceId("tv.fun.children:id/after_class"));
                UiObject earlyObj = listObj.getChild(new UiSelector().resourceId("tv.fun.children:id/early_class"));
                UiObject artObj = listObj.getChild(new UiSelector().resourceId("tv.fun.children:id/art_class"));
                verifyElementPresent("", languageObj);
                verifyElementPresent("", afterObj);
                verifyElementPresent("", earlyObj);
                verifyElementPresent("", artObj);
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
         * Test navigate to single tab list page via clicking card in Children home page
         */
        @Test
        public void Child_Home_23_testGotoSingleTabListPageViaCardInHome(){
            try{
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //点击少儿首页任一单tab列表页卡片: 例如讲故事
                UiObject singleTabListObj = findElementByID("tv.fun.children:id/children_view_item7");
                singleTabListObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/listTitleImage");
                moveRight();
                //断言
                verifyElementPresent("", findElementByID("tv.fun.children:id/listTitleImage"));
                UiObject listObj = findElementByID("tv.fun.children:id/listView");
                int starCount = listObj.getChildCount();
                verifyNumberLarger("", starCount, 1);
                verifyElementPresent("", findElementByID("tv.fun.children:id/briefContainer"));
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
         * Test that no response when pressing menu in Children home page
         */
        @Test
        public void Child_Home_33_testNoResponseWhenPressingMenuInHome() {
            try {
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //按遥控器Menu键
                menu();
                verifyElementNotPresent("", findElementByID("tv.fun.children:id/menu_text"));
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
         * Test that can back to correct page from children home page when pressing back in Children home page
         */
        @Test
        public void Child_Home_34_testBackFromChildrenPageWhenPressingBackInHome() {
            try {
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //按遥控器Back键
                back();
                inputChildrenKeyPasswordIfPopupDisplay();
                //断言
                UiObject launcherTabObj = findElementByText("电视", "com.bestv.ott:id/tab_title");
                UiObject launcherStatusBar = findElementByID("com.bestv.ott:id/status_bar");
                verifyElementPresent("", launcherTabObj);
                verifyElementPresent("", launcherStatusBar);
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
         * Test that can back to launcher home page from children home page when pressing home in Children home page
         */
        @Test
        public void Child_Home_35_testBackToLauncherHomeWhenPressingHomeInHome() {
            try {
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //按遥控器Home键
                home();
                //断言
                UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
                UiObject launcherVideoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
                verifyElementPresent("", tvCard);
                verifyTrue("", launcherVideoTab.isSelected());
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
         * Test that the people like tab page UI displaying correctly
         */
        @Test
        public void Child_Home_39_testPeopleLikeTabPageUI(){
            try {
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //Goto Children suggest tab page
                moveToTargetTabInChildren(childTabs[0]);
                UiObject likeTabObj = findElementByID("tv.fun.children:id/tab_title_recommend");
                UiObject likePageObj = findElementByID("tv.fun.children:id/tab_content").getChild(new UiSelector().resourceId("tv.fun.children:id/children_recommand_view"));
                int likePageCardCount = likePageObj.getChildCount();
                verifyNumberLarger("", likePageCardCount, 10);
                UiObject rankListObj = findElementByID("tv.fun.children:id/children_view_rank_list");
                int rankListCount = rankListObj.getChildCount();
                verifyNumberLarger("", rankListCount, 4);
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
         * Play correctly after clicking any video in rank lisy under 推荐 tab
         */
        @Test
        public void Child_Home_44_testVideoPlayOfRank() {
            try {
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //Goto Children suggest tab page
                moveToTargetTabInChildren(childTabs[0]);
                waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
                //点击排行榜list中任一单片
                UiObject rankListObj = findElementByID("tv.fun.children:id/children_view_rank_list");
                UiObject firstVideoObj = rankListObj.getChild(new UiSelector().className("android.widget.TextView").index(0));
                firstVideoObj.clickAndWaitForNewWindow();
                Thread.sleep(5000);
                //断言
                moveLeft();
                waitForElementPresentByID("com.bestv.ott:id/media_progress");
                UiObject videoPage = findElementByID("com.funshion.player.play.funshionplayer.VideoViewPlayer");
                UiObject tipObj = findElementByID("com.bestv.ott:id/control_tips");//按菜单键切换清晰度
                String tipText = tipObj.getText();
                verifyElementPresent("", findElementByID("com.bestv.ott:id/media_progress"));
                verifyElementPresent("", videoPage);
                verifyString("", tipText, "按\uE693菜单键切换清晰度");
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
         * Check the rank list page displays correctly
         */
        @Test
        public void Child_Home_45_testRankListPageUIDisplay() {
            try {
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //Goto Children suggest tab page
                moveToTargetTabInChildren(childTabs[0]);
                waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
                //点击排行榜list中任一单片
                UiObject rankListObj = findElementByID("tv.fun.children:id/children_view_rank_list");
                UiObject firstVideoObj = rankListObj.getChild(new UiSelector().className("android.widget.TextView").index(0));
                UiObject rankListUnderTab = findElementByID("tv.fun.children:id/children_view_rank_list");
                int videosCountInRank = rankListUnderTab.getChildCount();
                String[] videosNameInRank = new String[videosCountInRank];
                if(videosCountInRank>4){
                    videosCountInRank=4;
                }
                for(int i=0; i<videosCountInRank; i++){
                    UiObject eachVideoInRank = rankListUnderTab.getChild(new UiSelector().className("android.widget.TextView").index(i));
                    videosNameInRank[i] = eachVideoInRank.getText();
                }
                //点击热播榜卡片进入排行榜页面
                UiObject reboObj = findElementByID("tv.fun.children:id/children_view_recommand");
                reboObj.clickAndWaitForNewWindow();
                //断言
                UiObject videoList = findElementByID("tv.fun.children:id/listView");
                int videoCount = videoList.getChildCount();
                verifyNumberEqualORLarger("", videoCount, videosCountInRank);
                //向下选中第一个卡片
                moveDown();
                for(int j=0; j<videosCountInRank; j++){
                    UiObject singleVideoObj = videoList.getChild(new UiSelector().className("android.widget.FrameLayout").index(j));
                    UiObject singleVideo = singleVideoObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                    String singleVideoName = singleVideo.getText();
                    verifyString("", singleVideoName, videosNameInRank[j]);
                    if(j<3){
                        UiObject markObj = singleVideoObj.getChild(new UiSelector().resourceId("tv.fun.children:id/badge"));
                        verifyElementPresent("", markObj);
                    }
                    UiObject briefObj = findElementByID("tv.fun.children:id/briefContainer");
                    UiObject briefTopObj = briefObj.getChild(new UiSelector().resourceId("tv.fun.children:id/briefTop"));
                    UiObject briefTitleObj = briefObj.getChild(new UiSelector().resourceId("tv.fun.children:id/briefTitle"));
                    UiObject briefMetaObj = briefObj.getChild(new UiSelector().resourceId("tv.fun.children:id/briefMeta"));
                    String videoNameInBrief = briefTitleObj.getText();
                    String topInBrief = briefTopObj.getText();
                    verifyString("", briefTopObj.getText(), "TOP"+(j+1));
                    verifyString("",videoNameInBrief, singleVideoName);
                    verifyElementPresent("", briefMetaObj);
                    moveRight();
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
         * test that rank page can response the MENU
         */
        @Test
        public void Child_Home_48_testMenuResponseInRankPage(){
            try {
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //Goto Children suggest tab page
                moveToTargetTabInChildren(childTabs[0]);
                waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
                //点击热播榜卡片进入排行榜页面
                UiObject reboObj = findElementByID("tv.fun.children:id/children_view_recommand");
                reboObj.clickAndWaitForNewWindow();
                //选中任一卡片，按遥控器Menu键
                moveDown();
                menu();
                //断言
                UiObject menuPopupObj = findElementByID("tv.fun.children:id/menu_container");
                UiObject storeObj = menuPopupObj.getChild(new UiSelector().className("android.widget.LinearLayout").index(0)).getChild(new UiSelector().resourceId("tv.fun.children:id/menu_text"));
                UiObject searchObj = menuPopupObj.getChild(new UiSelector().className("android.widget.LinearLayout").index(1)).getChild(new UiSelector().resourceId("tv.fun.children:id/menu_text"));
                verifyString("", storeObj.getText(), "收藏");
                verifyString("", searchObj.getText(), "搜索");
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
         * test that can jump to search page after clicking search icon in menu pop-up
         */
        @Test
        public void Child_Home_49_testJumpToSearchPageAfterClickingSearchIconInMenu(){
            try {
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //Goto Children suggest tab page
                moveToTargetTabInChildren(childTabs[0]);
                waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
                //点击热播榜卡片进入排行榜页面
                UiObject reboObj = findElementByID("tv.fun.children:id/children_view_recommand");
                reboObj.clickAndWaitForNewWindow();
                //选中任一卡片，按遥控器Menu键
                moveDown();
                menu();
                UiObject searchIconObj = findElementByText("搜索", "tv.fun.children:id/menu_text");
                searchIconObj.clickAndWaitForNewWindow();
                //断言
                UiObject searchResultObj = findElementByID("tv.fun.children:id/searchResult");
                UiObject searchTitleObj = findElementByID("tv.fun.children:id/resultTitle");//大家都在搜：
                UiObject searchInputObj = findElementByID("tv.fun.children:id/search_input");//输入首字母或全拼
                verifyElementPresent("", searchResultObj);
                verifyString("", searchTitleObj.getText(), "大家都在搜：");
                verifyString("", searchInputObj.getText(), "输入首字母或全拼");
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
         * Test that can store video after clicking store icon in menu pop-up
         */
        @Test
        public void Child_Home_50_testStoreVideoViaStoreIcon(){
            try{
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //Goto Children suggest tab page
                moveToTargetTabInChildren(childTabs[0]);
                waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
                //点击热播榜卡片进入排行榜页面
                UiObject reboObj = findElementByID("tv.fun.children:id/children_view_recommand");
                reboObj.clickAndWaitForNewWindow();
                //选中任一卡片，按遥控器Menu键
                moveDown();
                UiObject nameInBriefObj = findElementByID("tv.fun.children:id/briefTitle");
                String nameInBrief = nameInBriefObj.getText();
                //收藏当前选中的影片
                menu();
                if(findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()){
                    UiObject cancelStoreIconObj = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                    cancelStoreIconObj.clickAndWaitForNewWindow();
                    moveRight();
                    moveLeft();
                    menu();
                }
                UiObject storeIconObj = findElementByText("收藏", "tv.fun.children:id/menu_text");
                storeIconObj.clickAndWaitForNewWindow();
                back();
                //移动焦点到少儿tab
                moveUp();
                moveRight();
                //点击 我的收藏，进入此页面查看收藏记录
                UiObject myFavoriteObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
                myFavoriteObj.clickAndWaitForNewWindow();
                UiObject myFavoriteListObj = findElementByID("tv.fun.children:id/listView");
                UiObject firstRecordObj = myFavoriteListObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                UiObject firstRecordNameObj = firstRecordObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                UiObject firstRecordStatusObj = firstRecordObj.getChild(new UiSelector().resourceId("tv.fun.children:id/timestamp"));
                String firstRecordName = firstRecordNameObj.getText();
                String firstRecordStatus = firstRecordStatusObj.getText();
                verifyString("", firstRecordName, nameInBrief);
                verifyString("", firstRecordStatus, "已收藏");
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
         * Test that can back the last page after clicking Back in rank list page
         */
        @Test
        public void Child_Home_51_testBackFromRankListPage(){
            try{
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //Goto Children suggest tab page
                moveToTargetTabInChildren(childTabs[0]);
                waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
                //点击热播榜卡片进入排行榜页面
                UiObject reboObj = findElementByID("tv.fun.children:id/children_view_recommand");
                reboObj.clickAndWaitForNewWindow();
                //按遥控器back键
                back();
                //断言
                UiObject likeTabObj = findElementByID("tv.fun.children:id/tab_title_recommend");
                UiObject settingObj = findElementByID("tv.fun.children:id/tab_title_setting");
                verifyElementPresent("", likeTabObj);
                verifyElementPresent("", settingObj);
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
         * Test that can back the launcher home page after pressing Home in rank list page
         */
        @Test
        public void Child_Home_52_testPressHomeInRankListPage(){
            try{
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //Goto Children suggest tab page
                moveToTargetTabInChildren(childTabs[0]);
                waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
                //点击热播榜卡片进入排行榜页面
                UiObject reboObj = findElementByID("tv.fun.children:id/children_view_recommand");
                reboObj.clickAndWaitForNewWindow();
                //按遥控器Home键
                home();
                //断言
                UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
                UiObject launcherVideoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
                verifyElementPresent("", tvCard);
                verifyTrue("", launcherVideoTab.isSelected());
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
         * Test that the video in ranking list page can play correctly
         */
        @Test
        public void Child_Home_54_testVideoPlayInRankListPage(){
            try{
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //Goto Children suggest tab page
                moveToTargetTabInChildren(childTabs[0]);
                waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
                //点击热播榜卡片进入排行榜页面
                UiObject reboObj = findElementByID("tv.fun.children:id/children_view_recommand");
                reboObj.clickAndWaitForNewWindow();
                //点击任一影片
                moveRight();
                enter();
                Thread.sleep(5000);
                //断言
                moveLeft();
                waitForElementPresentByID("com.bestv.ott:id/media_progress");
                UiObject videoPage = findElementByID("com.funshion.player.play.funshionplayer.VideoViewPlayer");
                UiObject tipObj = findElementByID("com.bestv.ott:id/control_tips");//按菜单键切换清晰度
                String tipText = tipObj.getText();
                verifyElementPresent("", findElementByID("com.bestv.ott:id/media_progress"));
                verifyElementPresent("", videoPage);
                verifyString("", tipText, "按\uE693菜单键切换清晰度");
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
         * Test that the suggested video under like tab can be played correctly
         */
        @Test
        public void Child_Home_63_testSuggestVideoPlayUnderLikeTab() {
            try {
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //Goto Children suggest tab page
                moveToTargetTabInChildren(childTabs[0]);
                waitForElementPresentByID("tv.fun.children:id/children_recommand_view");
                //点击大家都在看tab页面推荐影片
                UiObject suggestVideoObj = findElementByID("tv.fun.children:id/children_recommand_item3");//选中任一影片，点击播放
                suggestVideoObj.clickAndWaitForNewWindow();
                Thread.sleep(5000);
                if(findElementByID("tv.fun.children:id/addFav").exists()){
                    moveDownForMultiple(2);
                    enter();
                    Thread.sleep(6000);
                }
                //断言
                moveLeft();
                waitForElementPresentByID("com.bestv.ott:id/media_progress");
                UiObject videoPage = findElementByID("com.funshion.player.play.funshionplayer.VideoViewPlayer");
                moveLeft();
                UiObject tipObj = findElementByID("com.bestv.ott:id/control_tips");//按菜单键切换清晰度
                String tipText = tipObj.getText();
                verifyElementPresent("", findElementByID("com.bestv.ott:id/media_progress"));
                verifyElementPresent("", videoPage);
                verifyString("", tipText, "按\uE693菜单键切换清晰度");
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
         * Test that the brand tab page UI displays correctly
         */
        @Test
        public void Child_Home_69_testBrandTabPageUIDisplay() {
            try {
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //Goto Children Brand tab page
                moveToTargetTabInChildren(childTabs[2]);
                UiObject brandTab = findElementByID("tv.fun.children:id/tab_title_brand");
                verifyTrue("", brandTab.isFocused());
                //断言
                UiObject brandListObj = findElementByID("tv.fun.children:id/children_brand_view");
                int cardCount = brandListObj.getChildCount();
                verifyNumber("", cardCount, 9);
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
         * Test that can enter into topic page under brand tab
         */
        @Test
        public void Child_Home_74_testEnterTopicPageUnderBrandTab(){
            try {
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //Goto Children brand tab page
                moveToTargetTabInChildren(childTabs[2]);
                UiObject topicObj = findElementByID("tv.fun.children:id/children_brand_item4");//第四个专题卡片
                topicObj.clickAndWaitForNewWindow();
                //断言
                UiObject storeBtnObj = findElementByID("tv.fun.children:id/addFav");
                UiObject videoList = findElementByID("tv.fun.children:id/listView");
                int videoCount = videoList.getChildCount();
                moveDownForMultiple(2);
                UiObject videoShortInfo = findElementByID("tv.fun.children:id/briefContainer");
                verifyElementPresent("", storeBtnObj);
                verifyElementPresent("", videoList);
                verifyNumberLarger("", videoCount, 1);
                verifyElementPresent("", videoShortInfo);
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
         * Test that no response when pressing menu under Brand tab
         *
         * 品牌专区页面不响应Menu键操作
         */
        @Test
        public void Child_Home_77_testNoMenuResponseWhenPressMenuUnderBrandTab(){
            try {
                //进入少儿页面
                navigateToChildrenDesktopPage();
                //Goto Children brand tab page
                moveUp();
                moveRight();
                //Move down to select one brand topic card
                moveDown();
                //Press menu
                menu();
                //断言
                verifyElementNotPresent("", "tv.fun.children:id/menu_text");
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
         * Test that the UI of multiple tabs page displays correctly
         * <p>
         * 多tab列表页UI显示正确
         */
        @Test
        public void Child_List_01_testMultipleTabPageUIDisplay() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                //断言
                UiObject searchIconObj = findElementByID("tv.fun.children:id/search");
                UiObject filterIconObj = findElementByID("tv.fun.children:id/filter");
                UiObject tabsObj = findElementByID("tv.fun.children:id/tabContainer");
                UiObject firstTabObj = tabsObj.getChild(new UiSelector().className("android.widget.Button").index(0));
                int tabCount = tabsObj.getChildCount();
                verifyElementPresent("", searchIconObj);
                verifyElementPresent("", filterIconObj);
                verifyElementPresent("", tabsObj);
                verifyNumberLarger("", tabCount, 1);
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
         * Test that can clicking any tab without changing
         * <p>
         * 点击tab无异常
         */
        @Test
        public void Child_List_06_testMultipleTabPageUIDisplay() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveRightForMultiple(3);
                UiObject tabObj = findElementByID("tv.fun.children:id/tabContainer").getChild(new UiSelector().className("android.widget.Button").text("全部"));
                tabObj.click();
                //断言
                UiObject tabListObj = findElementByID("tv.fun.children:id/listView");
                UiObject scrollBarObj = findElementByID("android:id/tv_fun_scrollbar");
                int videoCountInList = tabListObj.getChildCount();
                verifyElementPresent("", tabListObj);
                verifyElementPresent("", scrollBarObj);
                verifyNumberLarger("", videoCountInList, 1);
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
         * Test that the video info displays correctly
         * <p>
         * 影片简介信息显示正确
         */
        @Test
        public void Child_List_20_testVideoInfo() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveRightForMultiple(3);
                //向下选中任一影片
                moveDown();
                if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                    moveUpForMultiple(3);
                    moveDown();
                }
                UiObject videoInfoObj = findElementByID("tv.fun.children:id/briefContainer");
                String infoStr = videoInfoObj.getText();
                do {
                    moveRight();
                    videoInfoObj = findElementByID("tv.fun.children:id/briefContainer");
                    infoStr = videoInfoObj.getText();
                } while (!infoStr.contains("集)"));
                //断言
                UiObject selectedVideo = device.findObject(new UiSelector().resourceId("tv.fun.children:id/title").selected(true));
                videoInfoObj = findElementByID("tv.fun.children:id/briefContainer");
                String currentVideoName = selectedVideo.getText();//宝宝巴士之神奇简笔画
                String videoInfo = videoInfoObj.getText();//宝宝巴士之神奇简笔画 (全30集)　8.0分　2016/内地/早教　"做一个天才的小画家吧"
                String videoNameInInfo = videoInfo.split(" ")[0];
                String otherInfo = videoInfo.split(" ")[1];
                String jujiInfo = otherInfo.split("　")[0];
                String gradeInfo = otherInfo.split("　")[1];
                String timeInfo = otherInfo.split("　")[2];
                String shortInfo = otherInfo.split("　")[3];
                int shortInfoSize = shortInfo.length();
                verifyString("", videoNameInInfo, currentVideoName);
                verifyIncludeString("", jujiInfo, "(");
                verifyIncludeString("", jujiInfo, ")");
                verifyIncludeString("", gradeInfo, "分");
                verifyIncludeString("", timeInfo, "/");
                verifyIncludeString("", shortInfo, "\"");
                verifyNumberLarger("", shortInfoSize, 3);
            } catch (Throwable e) {
                e.printStackTrace();
                resultFlag = false;
                resultStr = e.toString();
            } finally {
                Utils.writeCaseResult(resultStr,
                        resultFlag, execTime);
            }
        }

        /*
    * Test that the topic info displays correctly
    *
    * 专题的简介信息显示正确
    */
        @Test
        public void Child_List_21_testTopicInfo() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveRightForMultiple(3);
                UiObject topicTabObj = findElementByID("tv.fun.children:id/tabContainer").getChild(new UiSelector().className("android.widget.Button").text("专题"));
                do {
                    moveRight();
                } while (!topicTabObj.isSelected());
                //向下选中任一影片
                moveDown();
                if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                    moveUpForMultiple(3);
                    moveDown();
                }
                //断言
                UiObject selectedTopic = device.findObject(new UiSelector().resourceId("tv.fun.children:id/title").selected(true));
                UiObject topicInfoObj = findElementByID("tv.fun.children:id/briefContainer");
                String currentTopicName = selectedTopic.getText();
                String topicInfo = topicInfoObj.getText();//好习惯只需3分钟　"时间虽短 剧情上佳"
                String topicNameInInfo = topicInfo.split("　")[0];
                String shortInfo = topicInfo.split("　")[1];
                int infoLength = shortInfo.length();
                verifyString("", topicNameInInfo, currentTopicName);
                verifyIncludeString("", shortInfo, "\"");
                verifyNumberLarger("", infoLength, 3);
            } catch (Throwable e) {
                e.printStackTrace();
                resultFlag = false;
                resultStr = e.toString();
            } finally {
                Utils.writeCaseResult(resultStr,
                        resultFlag, execTime);
            }
        }

        /*
   * Test that the list page can response to Back button
   *
   * 列表页可以正常响应Back键操作
   */
        @Test
        public void Child_List_28_testPressBackInListPage() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                //按遥控器Back键
                back();
                //断言
                verifyElementNotPresent("", "tv.fun.children:id/filter");
                verifyElementPresent("", "tv.fun.children:id/tab_title_setting");
                verifyElementPresent("", "tv.fun.children:id/tab_title_brand");
            } catch (Throwable e) {
                e.printStackTrace();
                resultFlag = false;
                resultStr = e.toString();
            } finally {
                Utils.writeCaseResult(resultStr,
                        resultFlag, execTime);
            }
        }

        /*
 * Test that the list page can response to Home key
 *
 * 列表页可以正常响应Home键操作
 */
        @Test
        public void Child_List_29_testPressHomeInListPage() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                //按遥控器Home键
                home();
                //断言
                waitForElementPresentByID("com.bestv.ott:id/title");
                UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
                UiObject launcherVideoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
                verifyElementPresent("", tvCard);
                verifyTrue("", launcherVideoTab.isSelected());
            } catch (Throwable e) {
                e.printStackTrace();
                resultFlag = false;
                resultStr = e.toString();
            } finally {
                Utils.writeCaseResult(resultStr,
                        resultFlag, execTime);
            }
        }

        /*
  * Test that the list page can response to Menu key
  *
  * 列表页可以正常响应Menu键操作
  */
        @Test
        public void Child_List_30_testPressMenuInListPage() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                waitForElementPresentByID("tv.fun.children:id/tab_title_children");
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                //按遥控器Menu键
                menu();
                //断言
                UiObject iconInMenuPop = findElementByID("tv.fun.children:id/menu_icon");
                UiObject textInMenuPop = findElementByID("tv.fun.children:id/menu_text");
                String btnNameInMenu = textInMenuPop.getText();
                verifyElementPresent("", iconInMenuPop);
                verifyElementPresent("", textInMenuPop);
                verifyString("", btnNameInMenu, "搜索");
            } catch (Throwable e) {
                e.printStackTrace();
                resultFlag = false;
                resultStr = e.toString();
            } finally {
                Utils.writeCaseResult(resultStr,
                        resultFlag, execTime);
            }
        }

        /*
 * Test that can goto search page by pressing menu button in list page
 *
 * 列表页可以通过Menu键进入搜索页面
 */
        @Test
        public void Child_List_31_testGotoSearchPageByMenu() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                //按遥控器Menu键
                menu();
                UiObject searchBtnInMenu = findElementByText("搜索", "tv.fun.children:id/menu_text");
                searchBtnInMenu.clickAndWaitForNewWindow();
                //断言
                UiObject inputObj = findElementByID("tv.fun.children:id/search_input");
                String textInInput = inputObj.getText();//
                UiObject searchResultTitleObj = findElementByID("tv.fun.children:id/resultTitle");
                String suggestSearchTitle = searchResultTitleObj.getText();
                UiObject searchResultObj = findElementByID("tv.fun.children:id/searchResult");
                verifyElementPresent("", inputObj);
                verifyString("", textInInput, "输入首字母或全拼");
                verifyElementPresent("", searchResultTitleObj);
                verifyString("", suggestSearchTitle, "大家都在搜：");
                verifyElementPresent("", searchResultObj);
            } catch (Throwable e) {
                e.printStackTrace();
                resultFlag = false;
                resultStr = e.toString();
            } finally {
                Utils.writeCaseResult(resultStr,
                        resultFlag, execTime);
            }
        }

        /*
     * Test that can cancel collecting video or topic by Menu
     *
     * 在列表页中，可以取消收藏影片/专题
     */
        @Test
        public void Child_List_32_testCollectVideoByMenu() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveRightForMultiple(3);
                //向下选中任一影片
                moveDown();
                if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                    moveUpForMultiple(3);
                    moveDown();
                }
                //获取当前选中影片的名称
                UiObject videoInfoObj = findElementByID("tv.fun.children:id/briefContainer");
                String videoInfo = videoInfoObj.getText();
                String currentVideoName = videoInfo.split(" ")[0];
                //按遥控器Menu键
                menu();
                if (findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()) {
                    UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                    cancelCollectBtnInMenu.click();
                    menu();
                }
                UiObject collectBtnInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                collectBtnInMenu.click();
                back();
                //进入我的收藏页面
                UiObject collectBtnObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
                collectBtnObj.clickAndWaitForNewWindow();
                //断言
                UiObject collectTabObj = findElementByID("tv.fun.children:id/favorite");
                UiObject collectListObj = findElementByID("tv.fun.children:id/listView");
                UiObject firstCollectObj = collectListObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                UiObject firstCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                UiObject firstCollectStatusObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/timestamp"));
                String firstCollectName = firstCollectNameObj.getText();
                String firstCollectStatus = firstCollectStatusObj.getText();
                verifyTrue("", collectTabObj.isSelected());
                verifyString("", firstCollectName, currentVideoName);
                verifyString("", firstCollectStatus, "已收藏");
            } catch (Throwable e) {
                e.printStackTrace();
                resultFlag = false;
                resultStr = e.toString();
            } finally {
                Utils.writeCaseResult(resultStr,
                        resultFlag, execTime);
            }
        }

        /*
         * Test that can cancel collecting video or topic by Menu
         *
         * 在列表页中，可以取消收藏影片/专题
         */
        @Test
        public void Child_List_33_testCancelCollectVideoByMenu() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveRightForMultiple(3);
                //向下选中任一影片
                moveDown();
                if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                    moveUpForMultiple(3);
                    moveDown();
                }
                //获取当前选中影片的名称
                UiObject videoInfoObj = findElementByID("tv.fun.children:id/briefContainer");
                String videoInfo = videoInfoObj.getText();
                String currentVideoName = videoInfo.split(" \\(")[0];
                //按遥控器Menu键
                menu();
                if (findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()) {
                    UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                    cancelCollectBtnInMenu.click();
                    moveRight();
                    menu();
                }
                UiObject collectBtnInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                collectBtnInMenu.click();
                back();
                //进入我的收藏页面
                UiObject collectBtnObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
                collectBtnObj.clickAndWaitForNewWindow();
                UiObject collectListObj = findElementByID("tv.fun.children:id/listView");
                UiObject firstCollectObj = collectListObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                UiObject firstCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                String firstCollectName = firstCollectNameObj.getText();
                verifyString("", firstCollectName, currentVideoName);
                back();
                //再次进入列表页，取消收藏
                multipleTabObj.clickAndWaitForNewWindow();
                moveRightForMultiple(3);
                //向下选中任一影片
                moveDown();
                if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                    moveUpForMultiple(3);
                    moveDown();
                }
                //获取当前选中影片的名称
                UiObject selectedVideoInfoObj = findElementByID("tv.fun.children:id/briefContainer");
                String selectedVideoInfo = selectedVideoInfoObj.getText();
                String selectedVideoName = selectedVideoInfo.split(" \\(")[0];
                menu();
                if (findElementByText("收藏", "tv.fun.children:id/menu_text").exists()) {
                    UiObject collectBtnTwoInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                    firstCollectName = selectedVideoName;
                    collectBtnTwoInMenu.click();
                    moveDown();
                    menu();
                }
                UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                cancelCollectBtnInMenu.click();
                back();
                //断言
                findElementByText("我的收藏", "tv.fun.children:id/children_menu").clickAndWaitForNewWindow();
                if (findElementByID("tv.fun.children:id/empty").exists()) {
                    verifyElementNotPresent("", "tv.fun.children:id/listView");
                } else {
                    UiObject firstActualCollectObj = collectListObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                    UiObject firstActualCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                    String firstActualCollectName = firstCollectNameObj.getText();
                    verifyNotString("", firstActualCollectName, selectedVideoName);
                    verifyNotString("", firstActualCollectName, firstCollectName);
                    verifyString("", selectedVideoName, firstCollectName);
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

        /*
   * Test that can collect topic by Menu
   *
   * 可以正常收藏大背景专题
   */
        @Test
        public void Child_List_42_testCollectTopicByMenu() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveRightForMultiple(5);
                //向下选中任一专题
                moveDown();
                if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                    moveUpForMultiple(3);
                    moveDown();
                }
                //获取当前选中专题的名称
                UiObject topicInfoObj = findElementByID("tv.fun.children:id/briefContainer");
                String topicInfo = topicInfoObj.getText();
                String currentTopicName = topicInfo.split("　")[0];
                //按遥控器Menu键
                menu();
                if (findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()) {
                    UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                    cancelCollectBtnInMenu.click();
                    moveRight();
                    menu();
                }
                UiObject collectBtnInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                collectBtnInMenu.click();
                back();
                //进入我的收藏页面
                UiObject collectBtnObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
                collectBtnObj.clickAndWaitForNewWindow();
                UiObject firstCollectObj = findElementByID("tv.fun.children:id/listView").getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                UiObject firstCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                String firstCollectName = firstCollectNameObj.getText();
                verifyString("", firstCollectName, currentTopicName);
                back();
                //再次进入列表页，取消收藏
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveRightForMultiple(5);
                //向下选中任一影片
                moveDown();
                if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                    moveUpForMultiple(3);
                    moveDown();
                }
                //获取当前选中影片的名称
                UiObject selectedTopicInfoObj = findElementByID("tv.fun.children:id/briefContainer");
                String selectedTopicInfo = selectedTopicInfoObj.getText();
                String selectedTopicName = selectedTopicInfo.split(" ")[0];
                menu();
                if (findElementByText("收藏", "tv.fun.children:id/menu_text").exists()) {
                    UiObject collectBtnTwoInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                    collectBtnTwoInMenu.click();
                    moveDown();
                    menu();
                }
                UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                cancelCollectBtnInMenu.click();
                back();
                //断言
                findElementByText("我的收藏", "tv.fun.children:id/children_menu").clickAndWaitForNewWindow();
                UiObject firstActualCollectObj = findElementByID("tv.fun.children:id/listView").getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                UiObject firstActualCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                String firstActualCollectName = firstCollectNameObj.getText();
                verifyNotString("", firstActualCollectName, selectedTopicName);
                verifyNotString("", firstActualCollectName, firstCollectName);
            } catch (Throwable e) {
                e.printStackTrace();
                resultFlag = false;
                resultStr = e.toString();
            } finally {
                Utils.writeCaseResult(resultStr,
                        resultFlag, execTime);
            }
        }

        /*
* Test that can cancel collecting topic by Menu
*
* 可以取消收藏大背景专题
*/
        @Test
        public void Child_List_43_testCancelCollectTopicByMenu() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveRightForMultiple(5);
                UiObject currentSelectedTab = findElementByID("tv.fun.children:id/tabTitleSection").getChild(new UiSelector().className("android.widget.Button").text("专题"));
                verifyTrue("", currentSelectedTab.isSelected());
                //向下选中任一专题
                moveDown();
                if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                    moveUpForMultiple(5);
                    moveDown();
                }
                //获取当前选中专题的名称
                UiObject topicInfoObj = findElementByID("tv.fun.children:id/briefContainer");
                String topicInfo = topicInfoObj.getText();
                String currentTopicName = topicInfo.split(" ")[0];
                //按遥控器Menu键
                menu();
                if (findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()) {
                    UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                    cancelCollectBtnInMenu.click();
                    moveRight();
                    menu();
                }
                UiObject collectBtnInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                collectBtnInMenu.click();
                back();
                //进入我的收藏页面
                UiObject collectListObj = findElementByID("tv.fun.children:id/listView");
                UiObject collectBtnObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
                collectBtnObj.clickAndWaitForNewWindow();
                UiObject firstCollectObj = collectListObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                UiObject firstCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                String firstCollectName = firstCollectNameObj.getText();
                verifyString("", firstCollectName, currentTopicName);
                back();
                //再次进入列表页，取消收藏
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveRightForMultiple(5);
                //向下选中任一专题
                moveDown();
                if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                    moveUpForMultiple(5);
                    moveDown();
                }
                //获取当前选中专题的名称
                UiObject selectedTopicInfoObj = findElementByID("tv.fun.children:id/briefContainer");
                String selectedTopicInfo = selectedTopicInfoObj.getText();
                String selectedTopicName = selectedTopicInfo.split("　")[0];
                menu();
                if (findElementByText("收藏", "tv.fun.children:id/menu_text").exists()) {
                    UiObject collectBtnTwoInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                    collectBtnTwoInMenu.click();
                    moveDown();
                    menu();
                }
                UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                cancelCollectBtnInMenu.click();
                back();
                //断言
                findElementByText("我的收藏", "tv.fun.children:id/children_menu").clickAndWaitForNewWindow();
                UiObject firstActualCollectObj = collectListObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                UiObject firstActualCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                String firstActualCollectName = firstCollectNameObj.getText();
                verifyNotString("", firstActualCollectName, selectedTopicName);
                verifyNotString("", firstActualCollectName, firstCollectName);
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
         * 大背景专题页可以正确响应Menu键操作
         */
        @Test
        public void Child_List_44_testPressMenuInTopicPage() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveRightForMultiple(5);
                UiObject currentSelectedTab = findElementByID("tv.fun.children:id/tabTitleSection").getChild(new UiSelector().className("android.widget.Button").text("专题"));
                verifyTrue("", currentSelectedTab.isSelected());
                //向下选中任一专题
                moveDown();
                enter();
                UiObject collectBtnInTopic = findElementByID("tv.fun.children:id/addFav");
                verifyElementPresent("", collectBtnInTopic);
                moveRight();
                //按遥控器Menu键
                menu();
                //断言
                UiObject menuPopupObj = findElementByID("tv.fun.children:id/menu_container");
                UiObject collectBtnObj = findElementByText("收藏", "tv.fun.children:id/menu_text");
                UiObject searchBtnObj = findElementByText("搜索", "tv.fun.children:id/menu_text");
                verifyElementPresent("", menuPopupObj);
                verifyElementPresent("", searchBtnObj);
                verifyElementPresent("", menuPopupObj);
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
         * 大背景专题页可以正确响应Back键操作
         */
        @Test
        public void Child_List_45_testPressBackInTopicPage() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveRightForMultiple(5);
                UiObject currentSelectedTab = findElementByID("tv.fun.children:id/tabTitleSection").getChild(new UiSelector().className("android.widget.Button").text("专题"));
                Boolean f = verifyTrue("", currentSelectedTab.isSelected());
                //向下选中任一专题
                moveDown();
                enter();
                waitForElementPresentByID("com.bestv.ott:id/special_player_progress");
                if (findElementByID("com.bestv.ott:id/special_player_progress").exists()) {
                    UiObject videoListObj = findElementByID("com.bestv.ott:id/special_play_list");
                    boolean f11 = verifyElementPresent("", videoListObj);
                } else {
                    UiObject collectBtnInTopic = findElementByID("tv.fun.children:id/addFav");
                    verifyElementPresent("", collectBtnInTopic);
                }
                //按遥控器Back键
                back();
                //断言
                UiObject filterObj = findElementByID("tv.fun.children:id/filter");
                UiObject suggestTabObj = device.findObject(new UiSelector().className("android.widget.Button").text("推荐"));
                verifyElementPresent("", suggestTabObj);
                verifyElementPresent("", filterObj);
                verifyElementPresent("", "tv.fun.children:id/listView");
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
         * 大背景专题页可以正确响应Home键操作
         */
        @Test
        public void Child_List_46_testPressHomeInTopicPage() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveRightForMultiple(5);
                UiObject currentSelectedTab = findElementByID("tv.fun.children:id/tabTitleSection").getChild(new UiSelector().className("android.widget.Button").text("专题"));
                verifyTrue("", currentSelectedTab.isSelected());
                //向下选中任一专题
                moveDown();
                enter();
                UiObject collectBtnInTopic = findElementByID("tv.fun.children:id/addFav");
                verifyElementPresent("", collectBtnInTopic);
                //按遥控器Home键
                home();
                //断言
                waitForElementPresentByID("com.bestv.ott:id/title");
                UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
                UiObject launcherVideoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
                verifyElementPresent("", tvCard);
                verifyTrue("", launcherVideoTab.isSelected());
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
         * 在大背景专题页可以通过menu键进入搜索页面
         */
        @Test
        public void Child_List_47_testGotoSearchPageFromTopicPageByMenu() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到少儿tab
                moveToTargetTabInChildren(childrenTabName);
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveRightForMultiple(5);
                UiObject currentSelectedTab = findElementByID("tv.fun.children:id/tabTitleSection").getChild(new UiSelector().className("android.widget.Button").text("专题"));
                verifyTrue("", currentSelectedTab.isSelected());
                //向下选中任一专题
                moveDown();
                enter();
                UiObject collectBtnInTopic = findElementByID("tv.fun.children:id/addFav");
                verifyElementPresent("", collectBtnInTopic);
                moveRight();
                //按遥控器Menu键
                menu();
                UiObject searchBtnObj = findElementByText("搜索", "tv.fun.children:id/menu_text");
                searchBtnObj.clickAndWaitForNewWindow();
                //断言
                UiObject inputObj = findElementByID("tv.fun.children:id/search_input");
                String textInInput = inputObj.getText();
                UiObject searchResultTitleObj = findElementByID("tv.fun.children:id/resultTitle");
                String suggestSearchTitle = searchResultTitleObj.getText();
                UiObject searchResultObj = findElementByID("tv.fun.children:id/searchResult");
                verifyElementPresent("", inputObj);
                verifyString("", textInInput, "输入首字母或全拼");
                verifyElementPresent("", searchResultTitleObj);
                verifyString("", suggestSearchTitle, "大家都在搜：");
                verifyElementPresent("", searchResultObj);
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
         * 在大背景专题页可以通过menu键收藏影片
         */
        @Test
        public void Child_List_48_1_testCollectVideoInTopicPageByMenu() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren(childrenTabName);
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveRightForMultiple(5);
                UiObject currentSelectedTab = findElementByID("tv.fun.children:id/tabTitleSection").getChild(new UiSelector().className("android.widget.Button").text("专题"));
                verifyTrue("", currentSelectedTab.isSelected());
                //向下选中任一专题
                moveDown();
                enter();
                UiObject collectBtnInTopic = findElementByID("tv.fun.children:id/addFav");
                verifyElementPresent("", collectBtnInTopic);
                moveDown();
                UiObject briefInfo = findElementByID("tv.fun.children:id/briefContainer");
                String briefInfoStr = briefInfo.getText();
                String videoNameInBrief = briefInfoStr.split(" \\(")[0];//小蜜蜂 第一季 (全40集)　8.1分　2016/英国/早教　"萌萌哒的小蜜蜂们"
                //按遥控器Menu键
                menu();
                if (findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()) {
                    UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                    cancelCollectBtnInMenu.click();
                    back();
                    moveRightForMultiple(5);
                    moveDown();
                    enter();
                    menu();
                }
                UiObject collectBtnObj = findElementByText("收藏", "tv.fun.children:id/menu_text");
                collectBtnObj.clickAndWaitForNewWindow();
                back();
                back();
                //断言
                findElementByText("我的收藏", "tv.fun.children:id/children_menu").clickAndWaitForNewWindow();
                UiObject firstActualCollectObj = findElementByID("tv.fun.children:id/listView").getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                UiObject firstActualCollectNameObj = firstActualCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                String firstActualCollectName = firstActualCollectNameObj.getText();
                verifyString("", firstActualCollectName, videoNameInBrief);
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
         * 在大背景专题页可以通过menu键取消收藏影片
         */
        @Test
        public void Child_List_48_2_testCancelCollectVideoInTopicPageByMenu() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //Clicking multiple tab card
                UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveRightForMultiple(5);
                UiObject currentSelectedTab = findElementByID("tv.fun.children:id/tabTitleSection").getChild(new UiSelector().className("android.widget.Button").text("专题"));
                verifyTrue("", currentSelectedTab.isSelected());
                //向下选中任一专题
                moveDown();
                //获取当前选中专题的名称
                UiObject topicInfoObj = findElementByID("tv.fun.children:id/briefContainer");
                String topicInfo = topicInfoObj.getText();
                String currentTopicName = topicInfo.split("　")[0];//奇趣大自然　"好多毛蓬蓬的小动物啊"
                //按遥控器Menu键
                menu();
                if (findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()) {
                    UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                    cancelCollectBtnInMenu.click();
                    moveRight();
                    menu();
                }
                UiObject collectBtnInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                collectBtnInMenu.click();
                back();
                //进入我的收藏页面
                UiObject collectBtnObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
                collectBtnObj.clickAndWaitForNewWindow();
                UiObject collectListObj = findElementByID("tv.fun.children:id/listView");
                UiObject firstCollectObj = collectListObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                UiObject firstCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                String firstCollectName = firstCollectNameObj.getText();
                verifyString("", firstCollectName, currentTopicName);
                //退出我的收藏页面，回到少儿首页
                back();
                //再次进入列表页，取消收藏
                multipleTabObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/filter");
                moveRightForMultiple(5);
                //向下选中任一专题
                moveDown();
                if (!device.findObject(new UiSelector().resourceId("tv.fun.children:id/image").selected(true)).exists()) {
                    moveUpForMultiple(5);
                    moveDown();
                }
                //获取当前选中专题的名称
                UiObject selectedTopicInfoObj = findElementByID("tv.fun.children:id/briefContainer");
                String selectedTopicInfo = selectedTopicInfoObj.getText();
                String selectedTopicName = selectedTopicInfo.split("　")[0];
                menu();
                if (findElementByText("收藏", "tv.fun.children:id/menu_text").exists()) {
                    UiObject collectBtnTwoInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                    collectBtnTwoInMenu.click();
                    moveDown();
                    menu();
                }
                UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                cancelCollectBtnInMenu.click();
                back();
                //断言
                findElementByText("我的收藏", "tv.fun.children:id/children_menu").clickAndWaitForNewWindow();
                UiObject firstActualCollectObj = collectListObj.getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                UiObject firstActualCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                String firstActualCollectName = firstCollectNameObj.getText();
                verifyNotString("", firstActualCollectName, selectedTopicName);
                verifyNotString("", firstActualCollectName, firstCollectName);
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
         * 无tab列表页UI显示正确
         */
        @Test
        public void Child_List_58_testSingleTabPageUI() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //Clicking multiple tab card
                UiObject singleTabObj = findElementByID("tv.fun.children:id/children_view_item8");
                singleTabObj.clickAndWaitForNewWindow();

                //断言
                UiObject signleTabPageTitleObj = findElementByID("tv.fun.children:id/listTitleImage");
                UiObject videoListObj = findElementByID("tv.fun.children:id/listView");
                verifyElementPresent("", signleTabPageTitleObj);
                verifyElementPresent("", videoListObj);
                verifyElementNotPresent("", "tv.fun.children:id/tabScrollContainer");
                verifyElementNotPresent("", "tv.fun.children:id/tabScrollContainer");
                verifyElementNotPresent("", "tv.fun.children:id/filter");
                verifyElementNotPresent("", "tv.fun.children:id/tabTitleSection");
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
         * 早教学堂页面UI显示正确清晰
         */
        @Test
        public void Child_Early_01_testEarlySchoolPageUI() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //Clicking multiple tab card
                UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
                earlySchoolObj.clickAndWaitForNewWindow();
                //断言
                UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
                UiObject afterClassObj = findElementByID("tv.fun.children:id/after_class");
                UiObject artClassObj = findElementByID("tv.fun.children:id/art_class");
                UiObject earlyClassObj = findElementByID("tv.fun.children:id/early_class");
                verifyElementPresent("", languageClassObj);
                verifyElementPresent("", afterClassObj);
                verifyElementPresent("", artClassObj);
                verifyElementPresent("", earlyClassObj);

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
         * 早教学堂页面可以正确响应Back键操作
         */
        @Test
        public void Child_Early_03_testPressBackInEarlySchoolPage() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //Clicking multiple tab card
                UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
                earlySchoolObj.clickAndWaitForNewWindow();
                verifyElementPresent("", "tv.fun.children:id/language_class");
                //press back
                back();
                //断言
                UiObject childTabObj = findElementByID("tv.fun.children:id/tab_title_children");
                UiObject settingBtnObj = findElementByID("tv.fun.children:id/tab_title_setting");
                verifyElementPresent("", settingBtnObj);
                verifyElementPresent("", childTabObj);
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
         * 早教学堂页面可以正确响应Home键操作
         */
        @Test
        public void Child_Early_04_testPressHomeInEarlySchoolPage() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //Clicking multiple tab card
                UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
                earlySchoolObj.clickAndWaitForNewWindow();
                verifyElementPresent("", "tv.fun.children:id/language_class");
                //press home
                home();
                waitForElementNotPresent("tv.fun.children:id/language_class");
                //断言
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
         * 早教学堂页面不响应Menu键操作
         */
        @Test
        public void Child_Early_05_testNoMenuResponseInEarlySchoolPage() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //Clicking multiple tab card
                UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
                earlySchoolObj.clickAndWaitForNewWindow();
                verifyElementPresent("", "tv.fun.children:id/language_class");
                //press menu
                menu();
                //断言
                Thread.sleep(500);
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
         * 学堂页面UI显示正确
         */
        @Test
        public void Child_Early_09_testEarlyClassPageUI() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //Clicking multiple tab card
                UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
                earlySchoolObj.clickAndWaitForNewWindow();
                UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
                languageClassObj.clickAndWaitForNewWindow();
                //断言
                Thread.sleep(500);
                UiObject classMenuObj = findElementByID("tv.fun.children:id/video_menu");
                UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
                UiObject playAreaObj = findElementByID("tv.fun.children:id/video_television");
                UiObject playContainerObj = findElementByID("tv.fun.children:id/video_container");
                verifyElementPresent("", classMenuObj);
                verifyElementPresent("", classMenuTitleObj);
                verifyElementPresent("", playAreaObj);
                verifyElementPresent("", playContainerObj);
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
         * 学堂页面可正确响应Back键操作
         */
        @Test
        public void Child_Early_11_testPressBackInClassPage() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //Clicking multiple tab card
                UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
                earlySchoolObj.clickAndWaitForNewWindow();
                UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
                languageClassObj.clickAndWaitForNewWindow();
                UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
                verifyElementPresent("", classMenuTitleObj);
                //press back
                back();
                //断言
                Boolean flag = verifyElementPresent("", "tv.fun.children:id/art_class");
                Boolean flag1 = verifyElementPresent("", "tv.fun.children:id/language_class");
                Boolean flag2 = verifyElementNotPresent("", "tv.fun.children:id/video_television");

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
         * 学堂页面可正确响应Home键操作
         */
        @Test
        public void Child_Early_12_testPressHomeInClassPage() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //Clicking multiple tab card
                UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
                earlySchoolObj.clickAndWaitForNewWindow();
                UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
                languageClassObj.clickAndWaitForNewWindow();
                UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
                verifyElementPresent("", classMenuTitleObj);
                //press home
                home();
                waitForElementNotPresent("tv.fun.children:id/video_television");
                //断言
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
         * 学堂页面不响应Menu键操作
         */
        @Test
        public void Child_Early_03_testNoMenuResponseInClassPage() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //Clicking multiple tab card
                UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
                earlySchoolObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/language_class");
                UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
                languageClassObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/video_menu_title");
                //press menu
                menu();
                //断言
                Thread.sleep(500);
                verifyElementNotPresent("Menu popup window is expected NOT display, but actual displayed", "android:id/tv_fun_menu");
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
         * 在全屏播放页面可以通过向上或向下键呼出所有课程
         */
        @Test
        public void Child_Early_36_testAllClassDisplayInFullScreen() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //Clicking multiple tab card
                UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
                earlySchoolObj.clickAndWaitForNewWindow();
                UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
                languageClassObj.clickAndWaitForNewWindow();
                UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
                Boolean flag1 = classMenuTitleObj.exists();
                verifyElementPresent("", classMenuTitleObj);

                //点击小窗口播放
                UiObject smallPlayObj = findElementByID("tv.fun.children:id/video_view");
                smallPlayObj.clickAndWaitForNewWindow();
                moveLeft();
                moveRight();
                UiObject processObj = findElementByID("tv.fun.children:id/video_progress");
                verifyElementPresent("", processObj);
                waitForElementNotPresent(processObj);
                //按遥控器向下键
                moveDown();

                //断言
                UiObject classListObj = findElementByID("tv.fun.children:id/video_schedule_list");
                UiObject classTitleObj = findElementByID("tv.fun.children:id/video_schedule_title");
                UiObject scheduleObj = findElementByID("tv.fun.children:id/video_schedule_view");
                verifyElementPresent("The class list is expected displayed, but actual not", classListObj);
                verifyElementPresent("The class title is expected displayed, but actual not", classTitleObj);
                verifyElementPresent("The class schedule is expected displayed, but actual not", scheduleObj);
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
         * 影片播放时，全屏播放页面可以通过Back键正确切换到小窗口
         */
        @Test
        public void Child_Early_40_testBackFromFullScreenToSmall() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //Clicking multiple tab card
                UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
                earlySchoolObj.clickAndWaitForNewWindow();
                UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
                languageClassObj.clickAndWaitForNewWindow();
                UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
                verifyElementPresent("", classMenuTitleObj);
                //点击小窗口播放
                UiObject smallPlayObj = findElementByID("tv.fun.children:id/video_view");
                smallPlayObj.clickAndWaitForNewWindow();
                moveLeft();
                moveRight();
                UiObject processObj = findElementByID("tv.fun.children:id/video_progress");
                verifyElementPresent("", processObj);
                waitForElementNotPresent(processObj);
                //按遥控器Back键
                back();
                //断言
                UiObject classMenuObj = findElementByID("tv.fun.children:id/video_menu");
                UiObject playAreaObj = findElementByID("tv.fun.children:id/video_television");
                UiObject playContainerObj = findElementByID("tv.fun.children:id/video_container");
                verifyElementPresent("", classMenuObj);
                verifyElementPresent("", playAreaObj);
                verifyElementPresent("", playContainerObj);
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
         * 全屏播放页面可以正确响应Home键操作
         */
        @Test
        public void Child_Early_42_testPressHomeInFullScreen() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //Clicking multiple tab card
                UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
                earlySchoolObj.clickAndWaitForNewWindow();
                UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
                languageClassObj.clickAndWaitForNewWindow();
                UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
                verifyElementPresent("", classMenuTitleObj);
                //点击小窗口播放
                UiObject smallPlayObj = findElementByID("tv.fun.children:id/video_view");
                smallPlayObj.clickAndWaitForNewWindow();
                waitForElementNotPresent("tv.fun.children:id/loading_icon");
                Thread.sleep(1000);
                verifyElementNotPresent("", classMenuTitleObj);
                //按遥控器home键
                home();
                //断言
                waitForElementPresentByID("com.bestv.ott:id/title");
                UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
                verifyElementPresent("", tvCard);
                UiObject videoTabObj = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
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
         * 全屏播放页面可以正确响应Menu键操作
         */
        @Test
        public void Child_Early_44_testPressMenuInFullScreen() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //Clicking multiple tab card
                UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
                earlySchoolObj.clickAndWaitForNewWindow();
                UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
                languageClassObj.clickAndWaitForNewWindow();
                UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
                verifyElementPresent("", classMenuTitleObj);
                //点击小窗口播放
                UiObject smallPlayObj = findElementByID("tv.fun.children:id/video_view");
                smallPlayObj.clickAndWaitForNewWindow();
                moveLeft();
                moveRight();
                UiObject processObj = findElementByID("tv.fun.children:id/video_progress");
                verifyElementPresent("", processObj);
                waitForElementNotPresent(processObj);
                waitForElementNotPresent("tv.fun.children:id/loading_icon");
                Thread.sleep(1000);
                //按遥控器menu键
                menu();
                //Menu框
                for(int i=0; i<5; i++) {
                    if (!findElementByID("tv.fun.children:id/video_clarity_view").exists()) {
                        waitForElementNotPresent("tv.fun.children:id/loading_icon");
                        menu();
                    }else{
                        break;
                    }
                }

                //断言
                UiObject menuViewObj = findElementByID("tv.fun.children:id/video_clarity_view");
                UiObject menuListObj = findElementByID("tv.fun.children:id/video_clarity_list");
                verifyElementPresent("", menuViewObj);
                verifyElementPresent("", menuListObj);
                UiObject clarityObj = menuListObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_clarity"));
                UiObject playOrderObj = menuListObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_play_order"));
                UiObject playModeObj = menuListObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_video_mode"));
                verifyString("", clarityObj.getText(), "清晰度");
                verifyTrue("", clarityObj.isFocused());
                verifyString("", playOrderObj.getText(), "播放模式");
                verifyString("", playModeObj.getText(), "画面比例");
                moveUpForMultiple(3);
                UiObject clarityOptionList = findElementByID("tv.fun.children:id/video_clarity_clarity");
                int clarityListCount = clarityOptionList.getChildCount();
                verifyTrue("", clarityOptionList.isEnabled());
                verifyNumberLarger("", clarityListCount, 1);
                moveDown();
                UiObject playOrderOptionList = findElementByID("tv.fun.children:id/video_clarity_play_order");
                int playOrderOptionCount = playOrderOptionList.getChildCount();
                verifyNumber("", playOrderOptionCount, 2);
                UiObject firstOptionObj = playOrderOptionList.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
                UiObject secOptionObj = playOrderOptionList.getChild(new UiSelector().className("android.widget.RelativeLayout").index(1));
                String firstOptionStr = firstOptionObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_text")).getText();
                String secOptionStr = secOptionObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_text")).getText();
                verifyString("", firstOptionStr, "顺序播放");
                verifyString("", secOptionStr, "单集循环");

                //press menu
                menu();
                moveDownForMultiple(3);
                UiObject modeListObj = findElementByID("tv.fun.children:id/video_clarity_video_mode");
                verifyTrue("", modeListObj.isEnabled());
                int modeListCount = modeListObj.getChildCount();
                verifyNumber("", modeListCount, 3);
                UiObject firstModeObj = modeListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(0));
                UiObject secModeObj = modeListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(1));
                UiObject thirdModeObj = modeListObj.getChild(new UiSelector().className("android.widget.RelativeLayout").index(2));
                String firstModenStr = firstModeObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_text")).getText();
                String secModeStr = secModeObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_text")).getText();
                String thirdOptionStr = thirdModeObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_text")).getText();
                verifyString("", firstModenStr, "自适应");
                verifyString("", secModeStr, "16:9");
                verifyString("", thirdOptionStr, "4:3");
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
         * 全屏播放页面, Back键可取消Menu弹框
         */
        @Test
        public void Child_Early_45_testCancelMenuPopupByBack() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                waitForElementPresentByID("tv.fun.children:id/children_view_item5");
                //点击早教学堂
                UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
                earlySchoolObj.clickAndWaitForNewWindow();
                UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
                languageClassObj.clickAndWaitForNewWindow();
                UiObject classMenuTitleObj = findElementByID("tv.fun.children:id/video_menu_title");
                Boolean flag1 = verifyElementPresent("", classMenuTitleObj);
                //点击小窗口播放
                UiObject smallPlayObj = findElementByID("tv.fun.children:id/video_view");
                smallPlayObj.clickAndWaitForNewWindow();
                moveLeft();
                moveRight();
                UiObject processObj = findElementByID("tv.fun.children:id/video_progress");
                verifyElementPresent("", processObj);
                waitForElementNotPresent(processObj);
                waitForElementNotPresent("tv.fun.children:id/loading_icon");
                //按遥控器menu键
                menu();
                //Menu框
                if (!findElementByID("tv.fun.children:id/video_clarity_view").exists()) {
                    waitForElementNotPresent("tv.fun.children:id/loading_icon");
                    menu();
                }
                Thread.sleep(500);
                UiObject menuViewObj = findElementByID("tv.fun.children:id/video_clarity_view");
                UiObject menuListObj = menuViewObj.getChild(new UiSelector().resourceId("tv.fun.children:id/video_clarity_list"));
                verifyElementPresent("3 is not pass", menuViewObj);
                //按遥控器Back键
                back();
                //断言
                verifyElementNotPresent("5 is not pass", "tv.fun.children:id/video_clarity_view");
                verifyElementNotPresent("6 is not pass", "tv.fun.children:id/video_clarity_list");
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
         * Back键可取消今日课程表显示
         */
        @Test
        public void Child_Early_63_testCancelClassScheduleByBack() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //Clicking multiple tab card
                UiObject earlySchoolObj = findElementByID("tv.fun.children:id/children_view_item5");
                earlySchoolObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/language_class");
                UiObject languageClassObj = findElementByID("tv.fun.children:id/language_class");
                languageClassObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/video_menu_title");
                //点击小窗口播放
                UiObject smallPlayObj = findElementByID("tv.fun.children:id/video_view");
                smallPlayObj.clickAndWaitForNewWindow();
                moveLeft();
                moveRight();
                if(findElementByID("tv.fun.children:id/video_progress").exists()){
                    waitForElementNotPresent("tv.fun.children:id/video_progress");
                }
                waitForElementNotPresent("tv.fun.children:id/loading_icon");
                //按遥控器向下键
                moveDown();
                waitForElementPresentByID("tv.fun.children:id/video_schedule_list");
                if(!findElementByID("tv.fun.children:id/video_schedule_list").exists()){
                    moveDown();
                }
                UiObject classListObj = device.findObject(new UiSelector().className("android.widget.TextView").text("今日课程表"));
                UiObject classTitleObj = findElementByID("tv.fun.children:id/video_schedule_title");
                verifyElementPresent("", classListObj);
                verifyElementPresent("", classTitleObj);
                //按遥控器Back键
                back();
                //断言
                verifyElementNotPresent("", "tv.fun.children:id/video_schedule_list");
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
         * 卡通明星页面UI显示正确
         */
        @Test
        public void Child_Star_01_testCartoonStarPageUI() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //点击“卡通明星”
                UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
                cartoonStarObj.clickAndWaitForNewWindow();
                //断言
                UiObject startPageTitleObj = findElementByID("tv.fun.children:id/listTitleImage");
                verifyString("", startPageTitleObj.getText(), "卡通明星");
                UiObject starListObj = findElementByID("tv.fun.children:id/listView");
                int starCount = starListObj.getChildCount();
                verifyElementPresent("", starListObj);
                verifyNumberLarger("", starCount, 1);
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
         * 卡通明星页面可以正确响应Menu键操作
         */
        @Test
        public void Child_Star_17_testPressMenuInCartoonStarPage() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //点击“卡通明星”
                UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
                cartoonStarObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/listView");
                //选中任一明星，并按遥控器Menu键
                moveUpForMultiple(2);
                menu();
                //断言
                if (findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()) {
                    findElementByText("取消收藏", "tv.fun.children:id/menu_text").click();
                    moveUpForMultiple(2);
                    menu();
                }
                UiObject storeBtnInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                UiObject searchBtnInMenu = findElementByText("搜索", "tv.fun.children:id/menu_text");
                verifyElementPresent("", storeBtnInMenu);
                verifyElementPresent("", searchBtnInMenu);
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
         * 在卡通明星页面可以通过Menu键跳转到搜索页面
         */
        @Test
        public void Child_Star_18_testGotoSearchPageViaMenu() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //点击“卡通明星”
                UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
                cartoonStarObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/listView");
                //选中任一明星，并按遥控器Menu键
                moveUpForMultiple(2);
                menu();
                //点击menu弹框上搜索按钮
                UiObject searchBtnInMenu = findElementByText("搜索", "tv.fun.children:id/menu_text");
                searchBtnInMenu.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/search_input");
                //断言
                UiObject inputBox = findElementByID("tv.fun.children:id/search_input");
                UiObject phoneSearchBtn = findElementByID("tv.fun.children:id/phoneSearch");
                verifyElementPresent("搜索页面中输入框未显示", inputBox);
                verifyElementPresent("搜索页面中删除按钮未显示", phoneSearchBtn);
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
         * 在卡通明星页面可以通过Menu键收藏卡通明星
         */
        @Test
        public void Child_Star_19_testStoreCartoonStarViaMenu() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //点击“卡通明星”
                UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
                cartoonStarObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/listView");
                //选中任一明星，并按遥控器Menu键
                moveUpForMultiple(2);
                UiObject currentStarObj = findElementByID("tv.fun.children:id/title");
                String currentStarName = currentStarObj.getText();
                menu();
                //点击menu弹框上收藏按钮
                if (findElementByText("取消收藏", "tv.fun.children:id/menu_text").exists()) {
                    UiObject cancelCollectBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                    cancelCollectBtnInMenu.click();
                    moveUpForMultiple(2);
                    menu();
                }
                UiObject storeBtnInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                storeBtnInMenu.clickAndWaitForNewWindow();
                //断言
                back();
                UiObject collectBtnObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
                collectBtnObj.clickAndWaitForNewWindow();
                UiObject firstCollectObj = findElementByID("tv.fun.children:id/listView").getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                UiObject firstCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                String firstCollectName = firstCollectNameObj.getText();
                verifyString("", firstCollectName, currentStarName);
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
         * 在卡通明星页面可以通过Menu键取消收藏卡通明星
         */
        @Test
        public void Child_Star_20_testCancelStoreCartoonStarViaMenu() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //点击“卡通明星”
                UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
                cartoonStarObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/listView");
                //选中任一明星，并按遥控器Menu键
                moveUpForMultiple(2);
                moveLeftForMultiple(5);
                UiObject currentStarObj = findElementByID("tv.fun.children:id/title");
                String currentStarName = currentStarObj.getText();
                menu();
                String currentStarNameInSec = null;
                //点击menu弹框上取消收藏按钮
                if (findElementByText("收藏", "tv.fun.children:id/menu_text").exists()) {
                    UiObject storeBtnInMenu = findElementByText("收藏", "tv.fun.children:id/menu_text");
                    storeBtnInMenu.click();
                    back();
                    UiObject collectBtnObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
                    collectBtnObj.clickAndWaitForNewWindow();
                    UiObject firstStoreObj = findElementByID("tv.fun.children:id/listView").getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                    UiObject firstStoreNameObj = firstStoreObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                    String firstStoreName = firstStoreNameObj.getText();
                    back();
                    findElementByID("tv.fun.children:id/children_view_item4").clickAndWaitForNewWindow();
                    waitForElementPresentByID("tv.fun.children:id/listView");
                    moveUpForMultiple(2);
                    moveLeftForMultiple(5);
                    currentStarNameInSec = findElementByID("tv.fun.children:id/title").getText();
                    menu();
                }
                UiObject cancelStoreBtnInMenu = findElementByText("取消收藏", "tv.fun.children:id/menu_text");
                cancelStoreBtnInMenu.clickAndWaitForNewWindow();
                //断言
                back();
                UiObject collectBtnObj = findElementByText("我的收藏", "tv.fun.children:id/children_menu");
                collectBtnObj.clickAndWaitForNewWindow();
                UiObject firstCollectObj = findElementByID("tv.fun.children:id/listView").getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
                UiObject firstCollectNameObj = firstCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
                String firstCollectName = firstCollectNameObj.getText();
                verifyString("", firstCollectName, currentStarNameInSec);
                verifyString("", currentStarNameInSec, currentStarName);
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
         * 卡通明星页面可以正确响应Back键操作
         */
        @Test
        public void Child_Star_21_testPressBackInCartoonStarPage() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //点击“卡通明星”
                UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
                cartoonStarObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/listView");
                //按遥控器back键
                back();
                //断言
                verifyElementNotPresent("", "tv.fun.children:id/filter");
                verifyElementPresent("", "tv.fun.children:id/tab_title_setting");
                verifyElementPresent("", "tv.fun.children:id/tab_title_brand");
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
         * 卡通明星页面可以正确响应Home键操作
         */
        @Test
        public void Child_Star_22_testPressHomeInCartoonStarPage() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //点击“卡通明星”
                UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
                cartoonStarObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/listView");
                //按遥控器home键
                home();
                //断言
                waitForElementPresentByID("com.bestv.ott:id/title");
                UiObject tvCard = findElementByText("电视剧", "com.bestv.ott:id/title");
                UiObject launcherVideoTab = device.findObject(new UiSelector().resourceId(launcherTabID).text(videoTab));
                verifyElementPresent("", tvCard);
                verifyTrue("", launcherVideoTab.isSelected());
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
         * 卡通明星页面中点击大背景图专题可以进入大背景图专题页面
         */
        @Test
        public void Child_Star_24_testGotoTopicPageByClickingStar() {
            try {
                //进入少儿页面
                navigateToChildrenByChildrenCardUnderVideo();
                //移动到期望的tab
                moveToTargetTabInChildren("少儿");
                //点击“卡通明星”
                UiObject cartoonStarObj = findElementByID("tv.fun.children:id/children_view_item4");
                cartoonStarObj.clickAndWaitForNewWindow();
                waitForElementPresentByID("tv.fun.children:id/listView");
                //点击任一明星专题
                moveUpForMultiple(2);
                moveLeftForMultiple(5);
                enter();
                //断言
                waitForElementPresentByID("tv.fun.children:id/addFav");
                UiObject storeBtn = findElementByID("tv.fun.children:id/addFav");
                UiObject videoList = findElementByID("tv.fun.children:id/listView");
                int videoCount = videoList.getChildCount();
                UiObject videoInfo = findElementByID("tv.fun.children:id/briefContainer");
                verifyElementPresent("", storeBtn);
                verifyElementPresent("", videoList);
                verifyNumberLarger("", videoCount, 1);
                verifyElementPresent("", videoInfo);
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
//     * 筛选页面UI显示正确
//     */
//    @Test
//    public void Child_List_67_testFilterPageUI(){
//        try{
//            //进入少儿页面
//            navigateToChildrenByChildrenCardUnderVideo();
//            //移动到期望的tab
//            moveToTargetTabInChildren("少儿");
//            //Clicking multiple tab card
//            UiObject multipleTabObj = findElementByID("tv.fun.children:id/children_view_item2");
//            multipleTabObj.clickAndWaitForNewWindow();
//            if(!findElementByID("tv.fun.children:id/filter").exists()){
//                back();
//                multipleTabObj = findElementByID("tv.fun.children:id/children_view_item6");
//                multipleTabObj.clickAndWaitForNewWindow();
//            }
//            waitForElementPresentByID("tv.fun.children:id/filter");
//            UiObject filterBtn = findElementByID("tv.fun.children:id/filter");
//            filterBtn.clickAndWaitForNewWindow();
//            //向下选中任一专题
//
//            //断言
//            findElementByText("我的收藏", "tv.fun.children:id/children_menu").clickAndWaitForNewWindow();
//            UiObject firstActualCollectObj = findElementByID("tv.fun.children:id/listView").getChild(new UiSelector().className("android.widget.FrameLayout").index(0));
//            UiObject firstActualCollectNameObj = firstActualCollectObj.getChild(new UiSelector().resourceId("tv.fun.children:id/title"));
//            String firstActualCollectName = firstActualCollectNameObj.getText();
//            verifyString("", firstActualCollectName, videoNameInBrief);
//        } catch (Throwable e) {
//            e.printStackTrace();
//            resultFlag = false;
//            resultStr = e.toString();


//        } finally {
//            Utils.writeCaseResult(resultStr,
//                    resultFlag, execTime);
//        }
//    }

        @Test
        public void test(){
            TvCommon.printAllMethods(this.getClass().getName());
        }

    }
