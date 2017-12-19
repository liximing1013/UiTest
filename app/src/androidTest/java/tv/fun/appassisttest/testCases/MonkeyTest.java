package tv.fun.appassisttest.testCases;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import tv.fun.appassisttest.common.Funcs;
import tv.fun.appassisttest.common.PhoneCommon;

@RunWith(AndroidJUnit4.class)
public class MonkeyTest{
	boolean bRunFlag = true;
	int iHeight = 100;
	int iWidth = 100;

	Random ra = new Random();

    @Test
	public void testRun() throws UiObjectNotFoundException {
		PhoneCommon com = new PhoneCommon();
		MonkeyRun(com, 100);
	}
	
	public void MonkeyRun(PhoneCommon com, int Mins){
		int iTimes;
		while(bRunFlag){
			iTimes = ra.nextInt(5) + 1;
			switch (iTimes) {
			case 1:
				com.Back(iTimes);
				break;
			case 5:
				MonkeySwipe(com, 5);
				break;
			default:
				MonkeyClick(com, 50);
				break;
			}
		}
	}
	
	public void MonkeyClick(PhoneCommon com, int iAllTimes){
		int x;
		int y;
		int iClickTimes;
		iHeight = com.Height();
		iWidth = com.Width();
		for(int i = 0; i < iAllTimes; ++i){
			x = ra.nextInt(iWidth) + 1;
			y = ra.nextInt(iHeight) + 1;
			iClickTimes = ra.nextInt(4) + 1;
			Funcs.Print(String.format("x: %s, y: %s, iTimes: %s", x, y, iClickTimes));
			com.Click(x, y, iClickTimes);
		}
	}
	
	public void MonkeySwipe(PhoneCommon com, int iAllTimes) {
		iHeight = com.Height();
		iWidth = com.Width();
		for(int i = 0; i < iAllTimes; ++i){
			switch (ra.nextInt(4)) {
			case 0:
				com.SwipeB2T(iWidth/2, iHeight/2, 100, 10);
				break;
			case 1:
				com.SwipeT2B(iWidth/2, iHeight/2, 100, 10);
				break;
			case 2:
				com.SwipeR2L(iWidth/2, iHeight/2, 100, 10);
				break;
			case 3:
				com.SwipeL2R(iWidth/2, iHeight/2, 100, 10);
				break;
			default:
				break;
			}
		}
	}
}
