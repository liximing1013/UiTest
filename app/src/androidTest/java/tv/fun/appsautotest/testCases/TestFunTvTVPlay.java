package tv.fun.appsautotest.testCases;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import tv.fun.appsautotest.common.TvCommon;
import tv.fun.common.Infos;
import tv.fun.common.Utils;

/**
 * Created by xuzx on 2016/8/10.
 * Include the test cases for plays.
 */

@RunWith(AndroidJUnit4.class)
public class TestFunTvTVPlay {
    int iOneSecond = 1000;
    int iWaitSec = 8 * iOneSecond;
    TvCommon m_com = new TvCommon();
    TestEnterPage m_enterPage = new TestEnterPage();
    UiObject m_uiObj = null;

    String sPauseBtnID = Infos.S_LC_CONTROL_PAUSE_BTN; // 播放器上暂停按钮
    String sPlayedTimeID = Infos.S_LC_PLAYED_TIME_ID; // 当前播放时长
    String sPlayerClass = Infos.S_CLASS_VIDEO_PLAYER; // 播放器的类名
    String sDetailTitleID = Infos.S_LC_DETAIL_TITLE_ID; // 详情页标题
    String sEpisodeID = Infos.S_LC_EPISODES_CELL_ID; // 电视剧剧集控件

    @Before
    public void setUp() {

        m_com.setRefreshFailWatcher();
    }

    @Test
    public void collectOnePlay(){
        Utils.Print("==============case: collect one play=================");
        m_enterPage.enterTVPlayPage();
        m_com.Navigation(new int[]{2, 1, 1, 4});
        m_com.waitTillOccur(m_com.BY_TEXT, "收藏", 0, 10);
        if(!m_com.isUiObjExists(m_com.BY_TEXT, "已收藏", "")){
            m_com.Right(2);
            m_com.Enter();
        }
    }

    public boolean isPlayerWork(){

        return m_com.isUiObjExists(m_com.BY_CLASS, sPlayerClass, "");
    }

    public boolean isPausing(){
        if(m_com.isUiObjExists(sPauseBtnID)){
            Utils.Print("Player is pausing...");
            return true;
        }
        return false;
    }

    public String getPlayedTime(){
        String sPlayTime = "00:00";
        if(isPausing()) {
            sPlayTime = m_com.getUiObjText(m_com.getUiObject(m_com.BY_RESID, sPlayedTimeID, 0));
        }
        return sPlayTime;
    }

    // 将xx:xx:xx格式的时间转化为xxx秒
    public int timeTrans(String sTime){
        int iTime;
        int iHour = 0;
        int iMins = 0;
        int iSecs;
        String[] lsTime = sTime.split(":");

        if(3 == lsTime.length){
            iHour = Integer.parseInt(lsTime[0]);
            iMins = Integer.parseInt(lsTime[1]);
            iSecs = Integer.parseInt(lsTime[2]);
        }
        else if(2 == lsTime.length) {
            iMins = Integer.parseInt(lsTime[0]);
            iSecs = Integer.parseInt(lsTime[1]);
        }
        else{
            iSecs = Integer.parseInt(lsTime[0]);
        }

        iTime = 3600 * iHour + 60 * iMins + iSecs;
        return iTime;
    }

    @Test
    public void playOnePlay(){
        Utils.Print("case 播放一集电视剧 START");
        m_enterPage.enterTVPlayPage();
        m_com.Sleep(iWaitSec);
        m_com.Left();
        m_com.Sleep(iOneSecond * 10);
        m_com.Down();
        m_com.Navigation("2111499");

        m_com.waitTillOccur(m_com.BY_TEXT, "收藏", 0, 10);
        m_uiObj = m_com.getUiObject(m_com.BY_RESID, sDetailTitleID, 0);
        String sTitle = m_com.getUiObjText(m_uiObj);
        Utils.funAssert(String.format("播放电视剧[%s]失败", sTitle), "" != sTitle);
        m_com.Enter(); // 播放电视剧
        m_com.Sleep(18000); // 可能有15秒广告
        m_com.Enter();
        m_com.isUiObjExists(m_com.BY_RESID,  Infos.S_LC_CONTROL_PAUSE_BTN,
                String.format("没有开始播放电视剧[%s]",sTitle));
        Utils.Print("case 播放一集电视剧 END");
    }

    // 0: not finished; 1: finished; -1: error
    public int isPlayFinished(){
        String sEpisodesListID = Infos.S_LC_EPISODES_LIST_ID;  // 电视剧列表
        int iChilds = 0;
        String s1;
        String s2 = "0";
        try {
            iChilds = m_com.getUiObject(m_com.BY_RESID, sEpisodesListID, 0).getChildCount();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        if(iChilds > 0){
            s1 = m_com.getUiObjText(m_com.getUiObject(m_com.BY_RESID, sEpisodeID, 0));
        }
        else{
            Utils.writeLogs("剧集信息获取失败！", m_com.ERR_CODE);
            return -1;
        }
        if(iChilds > 1){
            s2 = m_com.getUiObjText(m_com.getUiObject(m_com.BY_RESID, sEpisodeID, 1));
        }
        if(Integer.parseInt(s1) - Integer.parseInt(s2) < 0){ // 已完结
            return 1;
        }
        else{
            return 0;
        }
    }

    // only use in detail page
    public void chooseNextEpisode(int iNum){
        for (int i = 0; i < iNum; ++i){
            if(1 == isPlayFinished()){
                m_com.Right();
            }
            else {
                m_com.Left();
            }
        }
    }

    // only use in detail page
    public void choosePrevEpisode(int iNum){
        for (int i = 0; i < iNum; ++i){
            if(1 == isPlayFinished()){
                m_com.Left();
            }
            else {
                m_com.Right();
            }
        }
    }

    @Test
    public void playNextEpisode(){
        Utils.Print("==============case: play next episode=================");
        String selectEpisodeID = Infos.S_LC_SELECT_EPISODE_ID;
        if(isPlayerWork()) {
            m_com.Sleep(iOneSecond * 2);
            m_com.Back(2);
        }
        if(!m_com.isUiObjExists(sDetailTitleID)){
            Utils.Print("not in detail page, play one play!");
            playOnePlay();
            m_com.Back(2);
        }
        m_com.uiObjClick(m_com.getUiObjByResId(selectEpisodeID));
        m_com.Down();
        Utils.Print("即将播放下一集！");
        chooseNextEpisode(1);
        m_com.Enter(); // 播放电视剧
    }

    public void exitPlaying(String playTime){
        if(m_com.waitTillOccur(m_com.BY_CLASS, sPlayerClass, 0, 10)) {
            if(!isPausing()) {
                m_com.Enter(); // 暂停播放
            }
        }
        else{
            m_com.Right(2);
        }

        int iDeltaTime = timeTrans(playTime) - timeTrans(getPlayedTime());
        if(iDeltaTime < 0){
            iDeltaTime = 1;
        }
        m_com.Enter();
        Utils.Print("[%s]秒后即将退出播放！", iDeltaTime);
        m_com.Sleep(iDeltaTime * 1000);
        m_com.Enter();
        m_com.Back(2);
    }

    @Test
    public void playOnePlayMinutes(){
        Utils.Print("==============case: play one play some minutes=================");
        playOnePlay();
        m_com.Sleep(iWaitSec);
        exitPlaying("1:00");
    }

    @Test
    public void playUntilFail(){
        playOnePlayMinutes();
        m_com.Sleep(iOneSecond * 2);
        m_com.Right();
        m_com.Down();
        while(true){
            chooseNextEpisode(1);
            m_com.Enter();
            m_com.Sleep(iOneSecond * 5);
            exitPlaying("5:00");
        }
    }

    @Test
    public void test(){
//        String[] cmd = {"/system/bin/sh", "-c", "/data/local/tmp/sh/right_long_press.sh 1"};
//        com.executeCommand(cmd);
//        Utils.Print("T: " + com.isUiObjExists(com.BY_TEXT, "缓冲失败，请检查网络设置后重新播放", ""));
        exitPlaying("1:00");
    }
}
