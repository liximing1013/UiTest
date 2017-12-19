package tv.fun.appstoretest.testCases;

import android.os.RemoteException;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tv.fun.appstoretest.common.Common;

/**
 * Created by liuqing on 2016/8/15.
 */
//@RunWith(Suite.class)
//@Suite.SuiteClasses({TestLauncherAppPage.class})
@RunWith(Categories.class)
@Categories.IncludeCategory(LevelP1Tests.class)
@Categories.ExcludeCategory({LevelP2Tests.class, NotRun.class})
@Suite.SuiteClasses({TestAppStore.class})
public class TestSuites {
    TestAppStore testAppStore = new TestAppStore();
    Common common = new Common();

    public void testAppStore_Run() throws UiObjectNotFoundException, RemoteException {
        try{
              //None
        }catch(Exception e){
            e.printStackTrace();
        }finally {
        }
    }
}
