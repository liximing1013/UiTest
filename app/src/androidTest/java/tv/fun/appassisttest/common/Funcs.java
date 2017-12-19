package tv.fun.appassisttest.common;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static tv.fun.common.Utils.makeDir;

public class Funcs{
	public static final String sTAG = "xuzx";
	public static String getCurDate(){
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
	
	public static String getCurTime(){
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(date);
	}
	
	public static long getCurSecond(){
		Date date = new Date();
		return date.getTime() / 1000;
	}
	
	public static void Print(Object text){
		String sHead = String.format("[%s][%s]", getCurDate(), getCurTime());
		Log.d(sTAG, sHead + text);
	}

	public static void Print(String sFormat, Object... args){
		String sHead = String.format("[%s][%s]", getCurDate(), getCurTime());
		Log.d(sTAG, sHead + String.format(sFormat, args));
	}
	
	/*if iSeed exists, the random is pseudo random
	everytime you run this code, you will get a same result
	so you should choose which is your purpose
		int iSeed = 10;
		Random ra = new Random(iSeed); */
	public static int RandInt(int iMax){
		Random ra = new Random();
		return ra.nextInt(iMax);
	}
	
	public static boolean MakeDir(String sFolderName) {
        File folder = new File(sFolderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }
	
	public static void WriteLine(String sFileName, String sContent, boolean appendFlag){
		FileWriter fw = null;
		try{
			fw = new FileWriter(sFileName, appendFlag);
			fw.write(sContent);  
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public static void Log(String sFileName, String sLogText) {
        String sPath = Environment.getExternalStorageDirectory().getPath();
        Log(sPath + "/autotest_phone/log/", sFileName, sLogText);
    }
    public static void Log(String sFolderName, String sFileName, String sLogText) {
        String sHead = String.format("[%s][%s]", getCurDate(), getCurTime());
        makeDir(sFolderName);
        writeLine(String.format("%s%s_%s.log", sFolderName, sFileName, getCurDate()), sHead + sLogText + "\r\n", true);
    }

    public static void writeLine(String sFileName, String sContent, boolean appendFlag){
        FileWriter fw = null;
        try{
            fw = new FileWriter(sFileName, appendFlag);
            fw.write(sContent);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static void WriteLogs(String sContent){
		Print(sContent);
		Log("runtime_log", sContent);
	}
	
	public static void WriteLogs(String sContent, int iLevel){
		Print(sContent);
		Log("runtime_log", sContent);
		switch (iLevel) {
		case 0:
			Log("runtime_log_error", sContent + "  ToT");
			break;
		default:
			break;
		}
	}
}