class Solution {
    public int maxSubArray(int[] nums) {
        int currsum = 0;
        int maxsum = Integer.MIN_VALUE;

        for(int i = 0; i < nums.length; i++){
            currsum += nums[i];
            maxsum = Math.max(maxsum, currsum);

            if(currsum < 0){
                currsum = 0;
            }
        }

        return maxsum;
    }
}

/*
SUMMARY:

- Use Kadane's Algorithm to find the maximum sum of a contiguous subarray.
- currsum stores the sum of the current subarray.
- maxsum stores the maximum sum found so far.
- Add each element to currsum.
- Update maxsum after every addition.
- If currsum becomes negative, reset it to 0 because a negative sum
  will decrease the sum of any future subarray.

ITERATION:

nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]

i = 0 → currsum = -2 → maxsum = -2 → reset currsum = 0
i = 1 → currsum =  1 → maxsum =  1
i = 2 → currsum = -2 → maxsum =  1 → reset currsum = 0
i = 3 → currsum =  4 → maxsum =  4
i = 4 → currsum =  3 → maxsum =  4
i = 5 → currsum =  5 → maxsum =  5
i = 6 → currsum =  6 → maxsum =  6
i = 7 → currsum =  1 → maxsum =  6
i = 8 → currsum =  5 → maxsum =  6

FINAL ANSWER:

Maximum Subarray = [4, -1, 2, 1]
Maximum Sum = 6

TIME COMPLEXITY: O(n)
SPACE COMPLEXITY: O(1)
*/
