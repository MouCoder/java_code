package LeetCode.intContrary;
import java.util.ArrayList;
public class Solution {
    public int reverse(int x){
        ArrayList<Integer> arry = new ArrayList<>();
        int i = 0,n=1;
        double result=0;
        while(x!=0){
            arry.add(x%10);
            x /= 10;
        }
        for(int j = arry.size()-1;j>=0;j--) {
            result += (arry.get(j))*n;
            n *= 10;
        }
        if(result > 2147483647 || result < -2147483647)
            return 0;
        else return (int)result;
    }
}
