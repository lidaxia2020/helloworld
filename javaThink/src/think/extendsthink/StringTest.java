package think.extendsthink;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther lidaxia
 * @Date 2020-05-01 22:40
 */
public class StringTest {

    // 正则表达式
    private static final Pattern pattern = Pattern.compile("\\w");

    public static void main(String[] args) {
        Matcher matcher = pattern.matcher("aaaa");

        HashMap map;
        TreeMap treeMap;
        System.out.println("args = " + "a".hashCode());
        System.out.println("args = " + "a".hashCode());
    }
}
