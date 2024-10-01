package Demo;

import java.util.Scanner;

public class Tool {
    static Scanner sc = new Scanner(System.in);

    public static String getStr(String msg) {
        System.out.println(msg);
        String s = sc.next();
        return s;
    }
    public static int getInt(String msg) {
        System.out.println(msg);
        int x = sc.nextInt();
        return x;
    }
    public static double getDouble(String msg) {
        System.out.println(msg);
        double x = sc.nextDouble();
        return x;
    }
    public static String getString(String msg){
        System.out.println(msg);
        String x = sc.next();
        return x;
    }
}
