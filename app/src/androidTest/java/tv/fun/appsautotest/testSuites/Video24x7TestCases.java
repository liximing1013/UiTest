package tv.fun.appsautotest.testSuites;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tv.fun.appsautotest.testCases.TestFunTvFilm;

/**
 * Created by zhengjin on 2016/8/30.
 *
 * Includes 24 x 7 test cases.
 */
@RunWith(Categories.class)
@Categories.IncludeCategory(Group24x7TestCases.class)
@Suite.SuiteClasses(TestFunTvFilm.class)
public class Video24x7TestCases {
}
