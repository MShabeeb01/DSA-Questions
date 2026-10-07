import java.util.Arrays;

public class Solution {

    // ==========================================================
    // LeetCode 75: Sort Colors (Dutch National Flag Algorithm)
    // Time Complexity: O(n) | Space Complexity: O(1)
    // ==========================================================
    public void sortColors(int[] nums) {
        int n = nums.length;
        int low = 0, mid = 0, high = n - 1;

        // Partitioning the array into three segments:
        // [0 ... low - 1]   -> All 0s
        // [low ... mid - 1]  -> All 1s
        // [mid ... high]     -> Unsorted / Unknown
        // [high + 1 ... n-1] -> All 2s
        while (mid <= high) {
            if (nums[mid] == 0) {
                // Swap nums[mid] and nums[low]
                int temp = nums[mid];
                nums[mid] = nums[low];
                nums[low] = temp;
                low++;
                mid++;
            } else if (nums[mid] == 1) {
                // 1 is already in its expected mid-range
                mid++;
            } else {
                // nums[mid] == 2: Swap nums[mid] and nums[high]
                int temp = nums[mid];
                nums[mid] = nums[high];
                nums[high] = temp;
                high--;
                // Note: Do NOT increment mid here; the element swapped from 'high' is uninspected!
            }
        }
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        int[] nums1 = {2, 0, 2, 1, 1, 0};
        System.out.println("Original Array: " + Arrays.toString(nums1));

        solver.sortColors(nums1);
        System.out.println("Sorted Colors:  " + Arrays.toString(nums1));
        // Output: [0, 0, 1, 1, 2, 2]

        int[] nums2 = {2, 0, 1};
        solver.sortColors(nums2);
        System.out.println("Sorted Colors:  " + Arrays.toString(nums2));
        // Output: [0, 1, 2]
    }
}

/*
==================== SUMMARY ====================

Topic: LeetCode 75 — Sort Colors (Dutch National Flag Algorithm by Edsger Dijkstra)

Problem Statement:
- Given an array `nums` with $n$ objects colored red, white, or blue (represented by integers 0, 1, and 2 respectively).
- Sort them in-place so that objects of the same color are adjacent, in the order of 0s, 1s, and then 2s.
- Constraint: Solve it in one-pass with $O(1)$ constant extra memory without using standard library sort functions.

Three-Pointer Partition Invariant:
At every step of the while loop (`mid <= high`), the array maintains 4 distinct regions:
1. `nums[0 ... low - 1]`: Fully sorted region containing only `0`s.
2. `nums[low ... mid - 1]`: Fully sorted region containing only `1`s.
3. `nums[mid ... high]`: Unexplored/unprocessed elements awaiting inspection.
4. `nums[high + 1 ... n - 1]`: Fully sorted region containing only `2`s.

Pointer Transition Rules:
- Case `nums[mid] == 0`:
  - belogns to the 0s region. Swap with `nums[low]`.
  - Increment both `low++` and `mid++`.
  - (Safe to advance `mid` because whatever came from `low` was already known to be a `1`).
- Case `nums[mid] == 1`:
  - In correct middle zone. Simply advance `mid++`.
- Case `nums[mid] == 2`:
  - Belongs to the 2s region. Swap with `nums[high]` and decrement `high--`.
  - **Crucial Rule**: Do NOT increment `mid`! The element brought over from index `high` has never been examined yet and could be a `0`, `1`, or `2`. It must be inspected in the next iteration.

-------------------------------------------------

Step-by-Step Dry Run Example

Array: [2, 0, 2, 1, 1, 0]
Pointers: low = 0, mid = 0, high = 5

---------------------------------------------------------------------------------------------------------
Step | Array State               | low | mid | high | Current `nums[mid]` | Action Taken
---------------------------------------------------------------------------------------------------------
1    | [2, 0, 2, 1, 1, 0]        | 0   | 0   | 5    | nums[0] = 2         | Swap(mid, high), high--
2    | [0, 0, 2, 1, 1, 2]        | 0   | 0   | 4    | nums[0] = 0         | Swap(mid, low), low++, mid++
3    | [0, 0, 2, 1, 1, 2]        | 1   | 1   | 4    | nums[1] = 0         | Swap(mid, low), low++, mid++
4    | [0, 0, 2, 1, 1, 2]        | 2   | 2   | 4    | nums[2] = 2         | Swap(mid, high), high--
5    | [0, 0, 1, 1, 2, 2]        | 2   | 2   | 3    | nums[2] = 1         | mid++
6    | [0, 0, 1, 1, 2, 2]        | 2   | 3   | 3    | nums[3] = 1         | mid++
7    | [0, 0, 1, 1, 2, 2]        | 2   | 4   | 3    | Loop terminates (`mid > high`)
---------------------------------------------------------------------------------------------------------

-------------------------------------------------

Comparison of Approaches

---------------------------------------------------------------------------------------------------------
Approach              | Passes    | Time Complexity | Space Complexity | Remarks
---------------------------------------------------------------------------------------------------------
Built-in Dual-Pivot   | N/A       | O(n log n)      | O(log n)         | Inefficient for 3 distinct values
Counting Sort         | 2 Passes  | O(n)            | O(1)             | Count 0s, 1s, 2s then overwrite array
Dutch National Flag   | 1 Pass    | O(n)            | O(1)             | Optimal single-pass in-place partitioning
---------------------------------------------------------------------------------------------------------

Complexity Analysis:
- Time Complexity : O(n) — Single pass over the array; in every iteration, either `mid` increments or `high` decrements, shrinking the uninspected interval `[mid, high]` until empty.
- Space Complexity: O(1) — In-place rearrangement using three pointer variables.
=================================================
*/
