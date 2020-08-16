package LeetCode.Array.arrayMedian;

public class Test {
    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] nums1 = {2,3};
        int[] nums2 = {1};
        System.out.println(sol.findMedianSortedArrays(nums1,nums2));
    }
}
//nums1 = [1, 3]
  //      nums2 = [2]

    //    则中位数是 2.0

      //  示例 2:

        //nums1 = [1, 2]
        //nums2 = [3, 4]

        //则中位数是 (2 + 3)/2 = 2.5
