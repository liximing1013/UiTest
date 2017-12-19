package tv.fun.appsautotest;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void Lol() { //复制
        String[] LOL1 = new String[]{"生", "旦", "净", "末", "丑"};
        String[] LOL2 = new String[LOL1.length];
        for (int i = 0; i < LOL1.length; i++) {
            LOL2[i] = LOL1[i];
            LOL2[3] = "BZ";
        }
        System.out.print("恕瑞玛" + LOL1[4] + LOL2[3]);
    }

    @Test
    public void Lol1() {
        String str = "你有故事ni有酒吗，SZ";
        int[] arr = countAll(str, 'S');
        int[] arr1 = countAll(str, 'Z');
        System.out.println(Arrays.toString(arr) + Arrays.toString(arr1));
    }

    private static int[] countAll(String str, char ch) {
        int[] arr2 = {};
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == ch) {
                arr2 = Arrays.copyOf(arr2, arr2.length + 1);
                arr2[arr2.length - 1] = i;
            }
        }
        return arr2;
    }

    @Test
    public void Lol2() {//数组倒序
        String[] test1 = {"z", "s", "l", "b", "s"};
        int[] test2 = {7, 2, 5, 9};
        System.out.println("\n");
        test1 = reverse2(test1);
        for (String i : test1) {
            System.out.print(i);
        }
        System.out.print("员工编号" + " ");
        test2 = reverse1(test2);
        for (int j : test2) {
            System.out.print(j);
        }
    }

    private static int[] reverse1(int[] arr) {
        int[] result = new int[arr.length];
        for (int i = 0, j = result.length - 1; i < arr.length; i++, j--) {
            result[j] = arr[i];
        }
        return result;
    }

    private static String[] reverse2(String[] arr) {
        String[] result = new String[arr.length];
        for (int i = 0, j = result.length - 1; i < arr.length; i++, j--) {
            result[j] = arr[i];
        }
        return result;
    }

    @Test
    public void testDemo() {
//    String s = "青春无悔无悔青春";
//        String result = s.substring(2,6);
//        System.out.print(result); //无悔无悔

        String s1 = new String("你好，");
        String name = new String("张三！");
        String sentence = s1.concat(name);
        System.out.print(sentence); //你好，张三！

//        char p[] = {'a','b','c'};
//        String q = "abc";
//        System.out.print(p.length);
//        System.out.print(q.length()); //33
//        int a =5;
//        int s =0;
//        switch (a){
//            case 5:
//                s=s+2;
//            case 3:
//                s=s+5;
//            case 8:
//                s=s+6;
//            default:
//                s=s+10;
//                break;
//        }
//        System.out.print(s);//23
//        for(int i = 0; i<6;i++){
//            int k = ++i;
//            while (k < 5){
//                System.out.print(i);
//                break;
//            }
//        } // 13
//        for(int i=0;i<6;i++){
//            System.out.print(i+",");
//            while (++i <7){
//                continue;
//            }
//            System.out.print(i);
//        } // 0,5

//        int count =0;
//        for (int i = 0; i < 10; i++) {
//             count = count++;
//        }
//        System.out.println(count);
    }
}







