package LeetCode.String.HuiWen.Solution2;

public class Solution {
    public String longestPalindrome(String s) {
        //判断所给字符串是否为空串或者只有一个字符，如果是直接返回该字符串
        if(s.equals("")||s.length()==1)
            return s;
        //创建s.length()*s.length()]的二维数组
        int[][] dp = new int[s.length()][s.length()];
        //创建字符数组，并将所给字符串转为字符数组存储在该数组中
        char[] num = s.toCharArray();
        int start = 0;
        int maxLen = 1;//最长回文串长度
        //i表示正在访问的字符
        for(int i=0;i<s.length();i++){
            dp[i][i] = 1;//将二位数组初始化，所有元素都为1
            //如果当前正在访问的字符不是字符串的最后一个字符，并且该字符和字符串的下一个字符相等
            //即该子串回文串只有两个相等的字符
            if(i+1<s.length() && num[i] == num[i+1]) {
                dp[i][i+1]  = 1;
                start = i;
                maxLen = 2;
            }
        }
        // 必须先把相同字母的回文串去除，然后把len从2开始计算
        for(int len=2;len<num.length;len++)
        {
            for(int i=0;i<num.length;i++)
            {
                if (i + len < num.length && num[i] == num[i + len]) {
                    dp[i][i + len] = dp[i + 1][i + len - 1];//验证dp[i][i+len]是回文串
                    if (dp[i][i + len] ==1 && len + 1 > maxLen) {//记录下最长回文串的起始位置和长度
                        maxLen = len + 1;
                        start = i;
                    }
                }

            }
        }
        return  new String(num,start,maxLen);
    }
}
