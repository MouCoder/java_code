package LeetCode.String.HuiWen;
import java.util.ArrayList;
public class Solution {
    public String longestPalindrome(String s) {
        int maxIndex = 0;
        ArrayList<String> str = new ArrayList<>();
        //求出所有子串（不去重）
        for(int i=0;i<s.length()-1;i++)
            for(int j=i+1;j<=s.length();j++) {
                str.add(s.substring(i, j));//将字符串s的所有子串保存在str集合中
            }
        //求出所有回文子串（不去重）
        for(int m=0;m<str.size();m++)
            for(int i=0;i<((str.get(m)).length())/2;i++){
                if((str.get(m)).charAt(i) != (str.get(m)).charAt(((str.get(m)).length())-i-1)) {
                    str.remove(m);//删除非回子串文串
                    m--;
                    break;
                }
            }
        for(int i=1;i<str.size();i++) {
            if ((str.get(i)).length() > (str.get(maxIndex)).length())
                maxIndex = i;
        }
            return str.get(maxIndex);
    }
}

