package LeetCode.String.stringToInt;

public class Solution {
    public int myAtoi(String str) {
        double num = 0;
        for(int i = 0;i < str.length();i++) {
            //判断第一个非空格字符是否为数字或者'-'
            if (str.charAt(i) != ' ' && ((str.charAt(i) == '-' || str.charAt(i) == '+')|| (str.codePointAt(i) >= 48 && str.codePointAt(i) <= 57))) {
                //如果第一个非空格字符是'-'但是'-'后不是数字，直接返回0
                if (i<str.length()-1&&str.charAt(i) == '-' && (str.codePointAt(i+1) < 48 || str.codePointAt(i+1) > 57)) {
                    num = 0;
                    break;
                }
                else if (str.charAt(i) == '-') {
                    for (int j = i + 1; j < str.length() && (str.codePointAt(j) >= 48 && str.codePointAt(j) <= 57); j++) {
                        num = (num * 10 + Integer.parseInt(str.substring(j,j+1)));
                    }
                    num = (-1) * num;
                    break;
                }else if (i<str.length()-1&&str.charAt(i) == '+' && (str.codePointAt(i+1) < 48 || str.codePointAt(i+1) > 57)) {
                    num = 0;
                    break;
                }else if(str.charAt(i) == '+'){
                    for (int j = i+1;j < str.length() && (str.codePointAt(j) >= 48 && str.codePointAt(j) <= 57) ; j++) {
                        num = (num * 10 + Integer.parseInt(str.substring(j,j+1)));
                    }
                    break;
                }else{
                    for (int j = i;j < str.length() && (str.codePointAt(j) >= 48 && str.codePointAt(j) <= 57) ; j++) {
                        num = (num * 10 + Integer.parseInt(str.substring(j,j+1)));
                    }
                    break;
                }
            } else if (str.charAt(i) != ' '){
                num = 0;
                break;
            }
        }
        return (int)num;
    }
}
