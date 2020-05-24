package oom;

import java.util.Arrays;

/**
 * @Auther lidaxia
 * @Date 2020-05-24 21:50
 */
public class RunTimeConstantPoolOom2 {

    public static void main(String[] args) {
        String str1 = new StringBuffer("计算器").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuffer("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}
