package LeetCode.Array.water;

public class Solution {
    public int maxArea(int[] height) {
        int max = 0,min = 0;
        for(int i = 0;i < height.length - 1;i++)
            for(int j = i+1;j < height.length;j++){
                if(height[i] < height[j])
                    min = height[i];
                else min = height[j];
                if(max < (j - i)*min)
                    max = (j - i)*min;
            }
        return max;
    }
}
//{1,8,6,2,5,4,8,3,7}