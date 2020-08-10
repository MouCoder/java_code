package LeetCode.String.solution1;

public class Solution {
    int LastNum = 0;
    public int lengthOfLongestSubstring(String s) {
        if(s.length() != 0)
            LastNum = 1;
        for(int i=0;i<s.length()-1;i++)
            for(int j=i+1;j<s.length();j++)//求出字符串s的所有子串并判断是否含重复字符
                judgeString(s.substring(i,j+1),0,1);
        return LastNum;
    }
    public void judgeString(String ZiString,int m,int n){
        if(ZiString.charAt(m) != ZiString.charAt(n) && m!=ZiString.length()-2 ){
            if(n<ZiString.length()-1)
                judgeString(ZiString,m,n+1);
            else judgeString(ZiString,m+1,m+2);
        }
        else if(ZiString.charAt(m) != ZiString.charAt(n) && n==ZiString.length()-1)
            if (LastNum < ZiString.length())
                LastNum = ZiString.length();
    }
}
//if(((s.substring(i,j+1)).charAt(m) != (s.substring(i,j+1)).charAt(n)) && m == (s.substring(i,j+1)).length()-2 && n==(s.substring(i,j+1)).length()-1)
