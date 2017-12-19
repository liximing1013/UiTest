package tv.fun.common;

import android.os.Environment;
import android.os.SystemClock;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.util.Log;

import junit.framework.Assert;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by xuzx on 2016/10/14.
 * 通用函数，Java/Android中可以通用的函数就写在这里
 */

public class Utils {
	public static final String sTAG = "AutoTestUtils";

    public static String getCurDate(){
        return getCurDate("yyyy-MM-dd");
    }
	public static String getCurDate(String sFormat){
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(sFormat);
		return df.format(date);
	}

    public static String getCurTime(){
        return getCurTime("HH:mm:ss");
    }
	public static String getCurTime(String sFormat){
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(sFormat);
		return df.format(date);
	}

    // 获取时间戳
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
	
	/* random int range: [0, iMax)
	if iSeed exists, the random is pseudo random
	everytime you run this code, you will get a same result
	so you should choose which is your purpose
		int iSeed = 10;
		Random ra = new Random(iSeed);
	*/
	public static int randInt(int iMax){
		Random ra = new Random();
		return ra.nextInt(iMax);
	}
	
	public static boolean makeDir(String sFolderName) {
        File folder = new File(sFolderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
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

    public static void Log(String sFileName, String sLogText) {
        String sPath = Environment.getExternalStorageDirectory().getPath();
        Log(sPath + "/autotest/log/", sFileName, sLogText);
    }
	public static void Log(String sFolderName, String sFileName, String sLogText) {
		String sHead = String.format("[%s][%s]", getCurDate(), getCurTime());
		makeDir(sFolderName);
		writeLine(String.format("%s%s_%s.log", sFolderName, sFileName, getCurDate()), sHead + sLogText + "\r\n", true);
	}

    public static void writeLogs(String sContent){
        writeLogs("runtime_log", sContent);
    }
	public static void writeLogs(String sLogName, String sContent){
		Print(sContent);
		Log(sLogName, sContent);
	}
	
	public static void writeLogs(String sContent, int iLevel){
		Print(sContent);
		Log("runtime_log", sContent);
		switch (iLevel) {
		case 0:
			Log("runtime_log_error", "[ERROR]" + sContent + "  ToT");
			break;
		default:
			break;
		}
	}

    public static String getMethodName(int iLevel) {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[iLevel];
        String methodName = e.getMethodName();
        return methodName;
    }

//    public static void insertMainData(Object... args){
//        if(args.length < 6){
//            return;
//        }
//        String sSql = "insert into autotest_main(app_name, case_num, start_time, " +
//                "consume_time, pass_rate, other) values(\"%s\", %d, \"%s\", " +
//                "%d, %f, \"%s\");";
//        sSql = String.format(sSql, args);
////        m_sqlUtils.getDBConnection("qa");
////        m_sqlUtils.operateSQL2(m_sqlUtils.m_Conn, sSql);
//    }
//
//    public static void insertModuleData(Object... args){
//        if(args.length < 6){
//            return;
//        }
//        String sSql = "insert into autotest_module(module_name, case_name, start_time, " +
//                "consume_time, pass, other) values(\"%s\", \"%s\", \"%s\"," +
//                "%d, \"%s\", \"%s\");";
//        sSql = String.format(sSql, args);
//        MysqlUtils.insert();
////        m_sqlUtils.getDBConnection("qa");
////        m_sqlUtils.operateSQL2(m_sqlUtils.m_Conn, sSql);
//    }

    // 条件判断结果为false时记录脚本的error log
    // sErrorMsg  : error log
    // bCondition : 条件判断的结果
    public static void funAssert(String sErrorMsg, boolean bCondition){
        if(!bCondition){
            writeLogs(sErrorMsg, 0);
            Assert.assertTrue(sErrorMsg, false);
        }
    }

    // 写case运行的log，如果bCondition为真，则记录sOK内容，否则记录sError内容
    public static void writeCaseResult(String sModuleName, String sCaseName,
                                       String sOK, String sError, boolean bCondition,
                                       long lConsume, String sOther){
        // 统计时会根据[]来获取字段，所以需要将log中的[]替换为【】
		sOK = sOK.replace("[", "【").replace("]", "】");
		sError = sError.replace("[", "【").replace("]", "】");
		if(!bCondition){ // 条件假
            String sErrorText = String.format("[%s][%s] %d %s %s",
                    sModuleName, sCaseName, lConsume, sError, sOther);
			execCommand(String.format("logcat -v time -d > /mnt/sdcard/autotest/logcat_%s_%s.log",
                    sCaseName, getCurTime("yyyyMMddHHmmss")), false, false);
            Utils.funAssert(sErrorText, false);
        }
        else{
            String sOKText = String.format("[%s][%s] %d %s %s",
                    sModuleName, sCaseName, lConsume, sOK, sOther);
            Utils.writeLogs(sOKText);
        }
    }
    public static void writeCaseResult(String sError, boolean bPass, long lStartTime) {
        String sCaseName = getMethodName(4); // 第4层函数名才是用例名
        String sModuleName = sCaseName.split("_")[0];
        writeCaseResult(sModuleName, sCaseName, "Success", sError, bPass,
                getCurSecond() - lStartTime, "");
    }

	private static final String COMMAND_SU = "su";
	private static final String COMMAND_SH = "sh";
	private static final String COMMAND_EXIT = "exit\n";
	private static final String COMMAND_LINE_END = "\n";

	public static CommandResult execCommand(
			String command, boolean isRoot, boolean isNeedResultMsg) {
		return execCommand(new String[]{command}, isRoot, isNeedResultMsg);
	}

	public static CommandResult execCommand(
			String[] commands, boolean isRoot, boolean isNeedResultMsg) {
		int result = -1;
		if (commands == null || commands.length == 0) {
			return new CommandResult(result, null, null);
		}

		Process process = null;
		BufferedReader successResult = null;
		BufferedReader errorResult = null;
		StringBuilder successMsg = null;
		StringBuilder errorMsg = null;
		DataOutputStream os = null;

		try {
			process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
			os = new DataOutputStream(process.getOutputStream());
			for (String command : commands) {
				if (isEmpty(command)) {
					continue;
				}
				os.write(command.getBytes());
				os.writeBytes(COMMAND_LINE_END);
				os.flush();
			}
			os.writeBytes(COMMAND_EXIT);
			os.flush();

			result = process.waitFor();
			if (isNeedResultMsg) {
				successMsg = new StringBuilder();
				errorMsg = new StringBuilder();
				successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
				errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				String tmpStr;
				while ((tmpStr = successResult.readLine()) != null) {
					successMsg.append(tmpStr);
				}
				while ((tmpStr = errorResult.readLine()) != null) {
					errorMsg.append(tmpStr);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (successResult != null) {
					successResult.close();
				}
				if (errorResult != null) {
					errorResult.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (process != null) {
				process.destroy();
			}
		}

		return new CommandResult(result, (successMsg != null ? successMsg.toString() : null),
				(errorMsg != null ? errorMsg.toString() : null));
	}

	public static class CommandResult {
		public int mResult;
		public String mSuccessMsg;
		public String mErrorMsg;

		CommandResult(int results, String successMsg, String errorMsg) {
			mResult = results;
			mSuccessMsg = successMsg;
			mErrorMsg = errorMsg;
		}
	}

    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    public static int stopProcess(String packageName) {
		String cmd = String.format("am force-stop %s", packageName);
		CommandResult result = execCommand(cmd, false, false);
		return result.mResult;
	}

	public static int stopAndClearProcess(String packageName) {
		String cmd = String.format("pm clear %s", packageName);
		CommandResult result = execCommand(cmd, false, false);
		return result.mResult;
	}

    public static int startActivity(String packageName, String componentName) {
        String cmd = String.format("am start %s/%s", packageName, componentName);
        CommandResult result = execCommand(cmd, false, false);
        return result.mResult;
    }

    public static void waitForPackageOpened(UiDevice device, String pkgName, long wait) {
        for (int i = 0; i < wait; i++) {
            SystemClock.sleep(Constants.SHORT_WAIT);
            if (pkgName.equals(device.getCurrentPackageName())) {
                return;
            }
        }
    }

    public static void waitForPackageOpened(UiDevice device, String pkgName) {
        waitForPackageOpened(device, pkgName, 5);
    }

	public static void waitForLoadingComplete(UiDevice device) {
		UiObject2 loading = device.findObject(By.res(Infos.S_PROCESS_BAR_ID));

		int tryTimes = 3;
		for (int i = 0, waitTimes = 20; i < waitTimes; i++) {
			SystemClock.sleep(Constants.SHORT_WAIT);
			if (tryTimes == 0) {
				return;
			}
			if (loading == null) {
				tryTimes--;
			}
		}
	}

    public static boolean isExternalStorageAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    private static File getExternalStorageDir() {
        return isExternalStorageAvailable() ? Environment.getExternalStorageDirectory() : null;
    }

    public static String getExternalStoragePath() {
        File file = getExternalStorageDir();
        if (file == null) {
            Log.e("ZJ", "The external storage (sdcard) is not available!");
            return "";
        }
        return file.getAbsolutePath();
    }
}