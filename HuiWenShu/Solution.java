package LeetCode.HuiWenShu;

import java.util.ArrayList;

public class Solution {
    public boolean isPalindrome(int x) {
        if(x < 0)
            return false;
        else {
            int y = x;
            ArrayList<Integer> arry = new ArrayList<>();
            int i = 0,n=1;
            int result=0;
            while(x!=0){
                arry.add(x%10);
                x /= 10;
            }
            for(int j = arry.size()-1;j>=0;j--) {
                result += (arry.get(j))*n;
                n *= 10;
            }
            if(y == result)
                return true;
            else
                return false;
        }
    }
}
