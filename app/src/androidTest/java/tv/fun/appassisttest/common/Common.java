package tv.fun.appassisttest.common;

import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

public class Common {
	UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
	int iHeight = Height();
	int iWidth = Width();
	int iDelay = 1000;
	public final int ERR_CODE = 0;
	public final int BY_CLASS = 0;
	public final int BY_TEXT = 1;
	public final int BY_DESC = 2;
	public final int BY_RESID = 3;
	
	public UiDevice getDevice(){
		return device;
	}

	public void Sleep(int iSeconds){
		SystemClock.sleep(iSeconds);
	}
	
	public void WakeUp(){
		try {
			if(!device.isScreenOn()){
				device.wakeUp();
				SwipeB2T(0, 0, 400, 10);
				SwipeL2R(iHeight-250, 0, 250, 5);
			}
		} catch (RemoteException e) {
			Funcs.WriteLogs("wakeup failure！", ERR_CODE);
		}
	}
	
	public void Up(){
		device.pressDPadUp();
	}
	
	public void Down(){
		device.pressDPadDown();
	}
	
	public void Left(){
		device.pressDPadLeft();
	}
	
	public void Right(){
		device.pressDPadRight();
	}
	
	public void Delete(){
		device.pressDelete();
	}
	
	public void Enter(){
		device.pressSearch();
	}

	public void PressKey(int keyCode){
		device.pressKeyCode(keyCode);
	}

	public void Home(){
		device.pressHome();	
	}

	public void Menu(){
		device.pressMenu();
	}

	public void Back(){
		device.pressBack();
	}

	public void Click(int x, int y){
		device.click(x, y);
	}

	public int Height(){
		return device.getDisplayHeight();
	}
	
	public int Width(){
		return device.getDisplayWidth();
	}
	
	// the smaller the steps, the faster the swiping speed
	public boolean SwipeR2L(int centerX, int centerY, int iSwipeLen, int steps){
		if(0 == centerX){
			centerX = iWidth/2;
		}
		if(0 == centerY){
			centerY = iHeight/2;
		}
		return device.swipe(centerX+iSwipeLen, centerY, centerX-iSwipeLen, centerY, steps);
	}
	
	public boolean SwipeL2R(int centerX, int centerY, int iSwipeLen, int steps){
		if(0 == centerX){
			centerX = iWidth/2;
		}
		if(0 == centerY){
			centerY = iHeight/2;
		}
		return device.swipe(centerX-iSwipeLen, centerY, centerX+iSwipeLen, centerY, steps);
	}
	
	public boolean SwipeT2B(int centerX, int centerY, int iSwipeLen, int steps){
		if(0 == centerX){
			centerX = iWidth/2;
		}
		if(0 == centerY){
			centerY = iHeight/2;
		}
		return device.swipe(centerX, centerY-iSwipeLen, centerX, centerY+iSwipeLen, steps);
	}

	public boolean SwipeB2T(int centerX, int centerY, int iSwipeLen, int steps){
		if(0 == centerX){
			centerX = iWidth/2;
		}
		if(0 == centerY){
			centerY = iHeight/2;
		}
		return device.swipe(centerX, centerY+iSwipeLen, centerX, centerY-iSwipeLen, steps);
	}

	public UiObject getUiObjByResId(String resId){
		UiObject uiObj = null;
		try {
			uiObj = new UiObject(new UiSelector().resourceId(resId));
		} catch (Exception e) {
			Funcs.WriteLogs(String.format("没有找到resource_id为[%s]的对象\r\n%s", resId, e), ERR_CODE);
		}
		return uiObj;
	}

	public UiObject getUiObjByDesc(String sDesc){
		UiObject uiObj = null;
		try {
			uiObj = new UiObject(new UiSelector().description(sDesc));
		} catch (Exception e) {
			Funcs.WriteLogs(String.format("没有找到content-desc为[%s]的对象\r\n%s", sDesc, e), ERR_CODE);
		}
		return uiObj;
	}

	public UiObject getUiObjByText(String sText){
		UiObject uiObj = null;
		try {
			uiObj = new UiObject(new UiSelector().text(sText));
		} catch (Exception e) {
			Funcs.WriteLogs(String.format("没有找到text为[%s]的对象\r\n%s", sText, e), ERR_CODE);
		}
		return uiObj;
	}
	
	public UiObject getUiObjByClass(String sClass){
		UiObject uiObj = null;
		try {
			uiObj = new UiObject(new UiSelector().className(sClass));
		} catch (Exception e) {
			Funcs.WriteLogs(String.format("没有找到class为[%s]的对象\r\n%s", sClass, e), ERR_CODE);
		}
		return uiObj;
	}
	
	public boolean isUiObjExists(String sResId){
		UiObject uiObj = null;
		uiObj = getUiObjByResId(sResId);  // 根据id查找obj
		return uiObj.exists();
	}
	
	public boolean isUiObjExists(String sResId, String sError){
		boolean exists = true;
		if(!isUiObjExists(sResId)){
			exists = false;
			if(!sError.equalsIgnoreCase("")){
				Funcs.WriteLogs(sError, 0);
			}
		}
		return exists;
	}
	
	public boolean isUiObjExists(int iType, String sContent, String sError){
		UiObject uiObj = getUiObject(iType, sContent);
		boolean exists = true;
		if(!uiObj.waitForExists(iDelay)){
			exists = false;
			if(!sError.equalsIgnoreCase("")){
				Funcs.WriteLogs(sError, ERR_CODE);
			}
		}
		return exists;
	}

	// iType: 0-class 1-text 2-desc
	public UiObject getUiObject(int iType, String sContent){
		UiObject uiObj = null;
		switch (iType) {
		case BY_CLASS:
			uiObj = getUiObjByClass(sContent);  // 根据class查找obj
			break;
		case BY_TEXT:
			uiObj = getUiObjByText(sContent);  // 根据text查找obj
			break;
		case BY_DESC:
			uiObj = getUiObjByDesc(sContent);  // 根据desc查找obj
			break;
		default:
			uiObj = getUiObjByResId(sContent);  // 根据id查找obj
			break;
		}
		return uiObj;
	}
	
	public String getUiObjText(UiObject uiObj){
		try {
			return uiObj.getText();
		} catch (UiObjectNotFoundException e) {
			return "";
		}
	}
	
	public boolean uiObjClick(UiObject uiObj){
		if(uiObj.exists()){
			try {
				return uiObj.click();
			} catch (UiObjectNotFoundException e) {
				Funcs.WriteLogs("ui click error: " + e, ERR_CODE);
			}
		}
		return false;
	}
	
	public void uiObjClickByType(int iType, String sContent, int iTimes){
		UiObject uiObj = null;
		switch (iType) {
		case BY_CLASS:
			uiObj = getUiObjByClass(sContent);  // 根据class查找obj
			break;
		case BY_TEXT:
			uiObj = getUiObjByText(sContent);  // 根据text查找obj
			break;
		case BY_DESC:
			uiObj = getUiObjByDesc(sContent);  // 根据desc查找obj
			break;
		default:
			uiObj = getUiObjByResId(sContent);  // 根据id查找obj
			break;
		}
		if(isUiObjExists(iType, sContent, "")){
			for(int i = 0; i < iTimes; ++i){
				uiObjClick(uiObj);
			}
		}
	}
	
	public boolean uiObjClickById(String resId){
		UiObject uiObj = getUiObjByResId(resId);
		return uiObjClick(uiObj);
	}
	
	public boolean uiObjClickByDesc(String sDesc){
		UiObject uiObj = getUiObjByDesc(sDesc);
		return uiObjClick(uiObj);
	}
	
	public boolean uiObjClickByText(String sText){
		UiObject uiObj = getUiObjByText(sText);
		return uiObjClick(uiObj);
	}
	
	public UiObject getUiObject(int iType, String sContent, int index){
		UiObject uiObj = null;
		try {
			switch (iType) {
			case BY_CLASS:
				uiObj = new UiObject(new UiSelector().className(sContent).instance(index));
				break;
			case BY_TEXT:
				uiObj = new UiObject(new UiSelector().text(sContent).instance(index));
				break;
			case BY_DESC:
				uiObj = new UiObject(new UiSelector().description(sContent).instance(index));
				break;
			default:
				uiObj = new UiObject(new UiSelector().resourceId(sContent).instance(index));
				break;
			}
		} catch (Exception e) {
			Funcs.WriteLogs("get uiobject child error: " + e, ERR_CODE);
		}
		return uiObj;
	}
	
	public UiObject getUiObjChild(UiObject uiObj, int iChildType, String sChildContent, int index){
		UiObject uiChildObject = null;
		try {
			int iChilds = uiObj.getChildCount();
			if(index >= iChilds){
				index = iChilds - 1;
			}
			switch (iChildType) {
			case BY_CLASS:
				uiChildObject = uiObj.getChild(new UiSelector().className(sChildContent).instance(index));
				break;
			case BY_TEXT:
				uiChildObject = uiObj.getChild(new UiSelector().text(sChildContent).instance(index));
				break;
			case BY_DESC:
				uiChildObject = uiObj.getChild(new UiSelector().description(sChildContent).instance(index));
				break;
			default:
				uiChildObject = uiObj.getChild(new UiSelector().resourceId(sChildContent).instance(index));
				break;
			}
		} catch (Exception e) {
			Funcs.WriteLogs("get uiobject child error: " + e, ERR_CODE);
		}
		return uiChildObject;
	}
	
	public boolean pressBackToFindObj(UiObject obj, int iTimes){
		for(int i = 0; i < iTimes; ++i){
			if(obj.exists()){
				return true;
			}
			Back();
		}
		return false;
	}
	
	public void waitTillOccur(UiObject uiObj, int iWaitSec){
		long iCurSec = Funcs.getCurSecond();
		while(!uiObj.exists()) {
			if(Funcs.getCurSecond() >= iCurSec + 10){
				Funcs.Print("waiting time is up, obj not occur!");
				break;
			}
		}
	}
	
	public void waitTillGone(UiObject uiObj, int iWaitSec){
		long iCurSec = Funcs.getCurSecond();
		while(uiObj.exists()) {
			if(Funcs.getCurSecond() >= iCurSec + 10){
				Funcs.Print("waiting time is up, obj not gone!");
				break;
			}
		}
	}


	public void clickEnter(int iInputType){
        if (0 == iInputType){
            device.click(666, 1212); // sogou input
        }
        else{
            device.pressEnter();
        }
	}
}