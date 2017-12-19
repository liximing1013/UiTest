package tv.fun.appsautotest;

public class Test {
    public static void main(String[] args) {
            String[] LOL1 = new String[]{"生", "旦", "净", "末", "丑"};
            String[] LOL2 = new String[LOL1.length];
            for (int i = 0; i < LOL1.length; i++) {
                LOL2[i] = LOL1[i];
                LOL2[3] = "洒家";
            }
        System.out.print("济南首富" + LOL2[3]);
        }
    }


