package LeetCode.Array.arrayMedian;

import java.util.ArrayList;

public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        //如果nums1数组为空，直接返回nums2的中位数
        if(nums1 == null || nums1.length == 0){
            //如果nums2的元素个数为奇数个，直接返回中间元素
            if(nums2.length % 2 == 1)
                return nums2[nums2.length / 2];
            else {//如果nums2的元素个数为偶数个，直接返回中间两个的平均值
                double median = ((double)(nums2[nums2.length / 2] + nums2[(nums2.length / 2) -1]))/2;
                return median;
            }
            //如果nums2为空，直接返回nums1的中位数
        }else if(nums2 == null || nums2.length == 0){
            if(nums1.length % 2 == 1)
                return nums1[nums1.length / 2];
            else {
                double median = ((double)(nums1[nums1.length / 2] + nums1[(nums1.length / 2) -1]))/2;
                return median;
            }
        }else{
            ArrayList<Integer> array = new ArrayList<>();
            int j = 0;
            for(int i = 0;i <= nums1.length;i++) {
                //如果nums2有剩余，将剩余元素直接放在集合末尾
                if (i == nums1.length && j != nums2.length) {
                    for(int m = j;m < nums2.length;m++)
                        array.add(nums2[m]);
                } //如果nums1有剩余，将剩余元素直接放在集合末尾
                else if(j == nums2.length && i != nums1.length){
                    for(int m = i;m < nums1.length;m++)
                        array.add(nums1[m]);
                    break;
                }//将nums1和nums2中元素按照从小到大顺序放在集合中
                    else if(i != nums1.length){
                    if (nums1[i] <= nums2[j]) {
                        array.add(nums1[i]);
                    } else {
                        array.add(nums2[j]);
                        i = i-1;
                        j++;
                    }
                }
                    else break;
            }
            if(array.size() % 2 == 1)
                return array.get(array.size()/2);
            else {
                double meidan = ((double)(array.get(array.size() / 2)+array.get(array.size() / 2 -1)))/2;
                return meidan;
            }

        }
    }
}
