package cn.javat.javaLearn.experiment4.utils;

public class AppUtils {
    //    打印单行分割线
    public static void printLine() {
        System.out.println("———————————————————————————————————————————————————————————————————");

    }

    //    打印双行分割线
    public static void printDoubleLine() {
        System.out.println("===================================================================");

    }

    // 无参数版本 - 实现可选参数效果
    public static void print(String msg) {
        System.out.println(msg);
    }

    // 有参数版本
    public static void print(String msg, Object... args) {
        if (args == null || args.length == 0) {
            System.out.println(msg);
        } else {
            System.out.printf(msg, args);
            System.out.println();
        }
    }

}
