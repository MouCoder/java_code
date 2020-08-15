package LeetCode.String.ZString;
public class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1)
            return s;
        else {
            char[][] str = new char[numRows][s.length()];
            int n = 0;
            for (int i = 0; n != s.length(); i++) {
                if (i == 0 || (i % (numRows - 1)) == 0) {
                    for (int j = 0; j < numRows && n != s.length(); j++) {
                        str[j][i] = s.charAt(n);
                        n++;
                    }
                } else {
                    str[numRows - (i % (numRows - 1)) - 1][i] = s.charAt(n);
                    n++;
                }
            }
            String str1 = new String();
            for (int i = 0; i < numRows; i++)
                for (int j = 0; j < s.length(); j++) {
                    if (Character.hashCode(str[i][j]) != 0000)
                        str1 += str[i][j];
                }
            return str1;
        }
    }
}
