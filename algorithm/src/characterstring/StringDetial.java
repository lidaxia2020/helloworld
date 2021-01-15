package characterstring;

import java.util.Arrays;

/**
 * @author lidaxia
 * @version 1.0
 * @date 2021/1/14 15:36
 */
public class StringDetial {


    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("aaa"));
    }


    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * <p>
     *  
     * <p>
     * 示例 1:
     * <p>
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * <p>
     * 思路： 双指针滑动窗口
     *
     * @param s
     */
    public static int lengthOfLongestSubstring(String s) {

        // 记录字符上一次出现的位置
        int[] last = new int[128];
        int n = s.length();

        int res = 0;
        // 窗口开始位置
        int start = 0;
        for (int i = 0; i < n; i++) {
            int index = s.charAt(i);
            start = Math.max(start, last[index]);
            res = Math.max(res, i - start + 1);
            last[index] = i + 1;
        }

        return res;
    }
}
