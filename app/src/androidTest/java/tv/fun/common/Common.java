package tv.fun.common;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;

import java.lang.reflect.Method;

/**
 * Created by xuzx on 2016/10/14.
 * 通用函数，手机或者电视上都可以使用的函数就写在这里
 */
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

    public void Sleep(int imSeconds){
        SystemClock.sleep(imSeconds);
    }

    public void Up(){
        device.pressDPadUp();
    }

    public void Up(int iTimes){
        for(int i = 0; i < iTimes; ++i){
            Up();
        }
    }

    public void Down(){
        device.pressDPadDown();
    }

    public void Down(int iTimes){
        for(int i = 0; i < iTimes; ++i){
            Down();
        }
    }

    // 水平方向移动，正数向右，负数向左
    public void MoveX(int iTimes){
        if(iTimes > 0){
            Right(iTimes);
        }else{
            Left(-iTimes);
        }
    }

    // 垂直方向移动，正数向下，负数向上
    public void MoveY(int iTimes){
        if(iTimes > 0){
            Down(iTimes);
        }else{
            Up(-iTimes);
        }
    }

    public void Left(){
        device.pressDPadLeft();
    }

    public void Left(int iTimes){
        for(int i = 0; i < iTimes; ++i){
            Left();
        }
    }

    public void Right(){
        device.pressDPadRight();
    }

    public void Right(int iTimes){
        for(int i = 0; i < iTimes; ++i){
            Right();
        }
    }

    public void Delete(){
        device.pressDelete();
    }

    public void Enter(){
        device.pressDPadCenter();
        Sleep(iDelay * 4);
    }
    public void EnterW(int iSecs){
        device.pressDPadCenter();
        Sleep(iDelay * iSecs);
    }

    public void Enter(int iTimes){
        for(int i = 0; i < iTimes; ++i){
            Enter();
        }
    }

    public void Search(){
        device.pressSearch();
    }

    public void PressKey(int keyCode){
        device.pressKeyCode(keyCode);
    }

    public void Home(){
        device.pressHome();
    }

    public void Home(int iTimes){
        for(int i = 0; i < iTimes; ++i){
            Home();
        }
    }
    public void Menu(){
        device.pressMenu();
    }

    public void Back(){
        device.pressBack();
    }

    public void Back(int iTimes){
        for(int i = 0; i < iTimes; ++i){
            Back();
        }
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

    // 反射机制获取类的所有方法
    public static void printAllMethods(String sClassName){
        try {
            Class classType = Class.forName(sClassName);
            Method[] methods = classType.getDeclaredMethods();
            for (Method method : methods) {
                Utils.Print(method.getName());
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Utils.Print(e);
        }
    }
}