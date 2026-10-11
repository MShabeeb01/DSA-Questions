import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    // ==========================================================
    // LeetCode 18: 4Sum (Two Pointers with Duplicate Avoidance)
    // Time Complexity: O(n^3) | Space Complexity: O(1) auxiliary
    // ==========================================================
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums); // Step 1: Sort to enable two pointers and skip duplicates
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();

        // First pointer: i
        for (int i = 0; i < n; i++) {
            // Skip duplicates for the 1st element
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // Second pointer: j
            for (int j = i + 1; j < n; j++) {
                // Skip duplicates for the 2nd element
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                // Two pointers for the remaining two elements
                int p = j + 1;
                int l = n - 1;

                while (p < l) {
                    // Prevent 32-bit signed integer overflow during summation
                    long sum = (long) nums[i] + nums[j] + nums[p] + nums[l];

                    if (sum < target) {
                        p++;
                    } else if (sum > target) {
                        l--;
                    } else {
                        // Found a valid quadruplet
                        ans.add(Arrays.asList(nums[i], nums[j], nums[p], nums[l]));

                        p++;
                        l--;

                        // Skip duplicates for the 3rd element
                        while (p < l && nums[p] == nums[p - 1]) {
                            p++;
                        }

                        // Skip duplicates for the 4th element
                        while (p < l && nums[l] == nums[l + 1]) {
                            l--;
                        }
                    }
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        int[] nums1 = {1, 0, -1, 0, -2, 2};
        int target1 = 0;
        System.out.println("4Sum for [1, 0, -1, 0, -2, 2], target = 0:");
        System.out.println(solver.fourSum(nums1, target1));
        // Output: [[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]

        int[] nums2 = {2, 2, 2, 2, 2};
        int target2 = 8;
        System.out.println("\n4Sum for [2, 2, 2, 2, 2], target = 8:");
        System.out.println(solver.fourSum(nums2, target2));
        // Output: [[2, 2, 2, 2]]

        // Overflow test case:
        int[] nums3 = {1000000000, 1000000000, 1000000000, 1000000000};
        int target3 = -294967296;
        System.out.println("\n4Sum large values test:");
        System.out.println(solver.fourSum(nums3, target3));
        // Output: [] (Correctly handles without int overflow)
    }
}

/*
==================== SUMMARY ====================

Topic: LeetCode 18 — 4Sum (Two Pointers Pattern)

Core Strategy:
1. Sorting:
   - Sorting in $O(n \log n)$ places identical elements next to each other and enables two-pointer convergence.
2. Two Nested Fixed Loops ($i$ and $j$):
   - Fix the first number at index $i$, and the second number at index $j = i + 1$.
3. Two-Pointer Window ($p$ and $l$):
   - Set left pointer $p = j + 1$ and right pointer $l = n - 1$.
   - Adjust pointers based on `sum` relative to `target`.

Key Implementation Details:
- Integer Overflow Protection:
  - Adding four numbers can exceed the 32-bit signed integer range ($[-2^{31}, 2^{31}-1]$).
  - Casting the first term to `(long)` promotes the entire expression to 64-bit integer addition:
    `long sum = (long) nums[i] + nums[j] + nums[p] + nums[l];`
- Skipping Duplicates:
  - For $i$: `if (i > 0 && nums[i] == nums[i - 1]) continue;`
  - For $j$: `if (j > i + 1 && nums[j] == nums[j - 1]) continue;`
  - For $p$ and $l$: Once a matching quadruplet is captured and pointers step inward, skip identical consecutive values.

-------------------------------------------------

Two-Pointers State Progression

Input: [-2, -1, 0, 0, 1, 2], target = 0

  i      j      p               l
 [-2,   -1,     0,     0,   1,  2]  -> sum = -2 + -1 + 0 + 2 = -1 (< target) -> p++
 [-2,   -1,     0,     0,   1,  2]  -> sum = -2 + -1 + 1 + 2 =  0 (== target) -> Add [-2, -1, 1, 2]

Advance i and j, repeat two-pointer search on subarray.

-------------------------------------------------

Complexity Analysis:
- Time Complexity : O(n^3)
  - Sorting takes $O(n \log n)$.
  - Outer loop runs $O(n)$ times, middle loop runs $O(n)$ times, and the inner two-pointer loop runs $O(n)$ times.
  - Overall: $O(n^3)$.
- Space Complexity: O(1) auxiliary space (excluding the output list and internal sorting call stack).
=================================================
*/
