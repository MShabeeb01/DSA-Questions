class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array to optimize binary search range
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;
        int st = 0, end = m;

        while (st <= end) {
            // Partition index in nums1
            int px = st + (end - st) / 2;
            // Partition index in nums2 such that left half contains (m + n + 1) / 2 elements
            int py = (m + n + 1) / 2 - px;

            // Edge handling with infinity if partition is at boundaries
            int maxLeftX = (px == 0) ? Integer.MIN_VALUE : nums1[px - 1];
            int minRightX = (px == m) ? Integer.MAX_VALUE : nums1[px];

            int maxLeftY = (py == 0) ? Integer.MIN_VALUE : nums2[py - 1];
            int minRightY = (py == n) ? Integer.MAX_VALUE : nums2[py];

            // Valid partition condition
            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                // If total length is odd, median is the maximum of left side
                if ((m + n) % 2 == 1) {
                    return Math.max(maxLeftX, maxLeftY);
                } 
                // If total length is even, median is the average of max-left and min-right
                else {
                    return (Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2.0;
                }
            } else if (maxLeftX > minRightY) {
                // Too far right in nums1, move left
                end = px - 1;
            } else {
                // Too far left in nums1, move right
                st = px + 1;
            }
        }

        return 0.0;
    }
}

/* 
==================================================
## Code Summary
==================================================

1. Apply **Binary Search on Partitions** rather than searching array values directly.

2. Always ensure `nums1` is the shorter array ($m \le n$) so the search range is $O(\log(\min(m, n)))$.

3. Define left half size as `(m + n + 1) / 2`:
   - `px`: Number of elements taken from `nums1` into the left partition.
   - `py = (m + n + 1) / 2 - px`: Number of elements taken from `nums2` into the left partition.

4. Find elements adjacent to the cuts:
   - `maxLeftX = nums1[px - 1]`, `minRightX = nums1[px]`
   - `maxLeftY = nums2[py - 1]`, `minRightY = nums2[py]`
   *(Use `-∞` and `+∞` when a partition is at index `0` or boundary length)*.

5. Partition validity check:
   - If `maxLeftX <= minRightY` and `maxLeftY <= minRightX`:
     - If total elements is **odd**: Median = `Math.max(maxLeftX, maxLeftY)`.
     - If total elements is **even**: Median = `(Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2.0`.
   - If `maxLeftX > minRightY`: Shift left in `nums1` (`end = px - 1`).
   - If `maxLeftY > minRightX`: Shift right in `nums1` (`st = px + 1`).

==================================================
## Iteration
==================================================

Input:

nums1 = [1, 3] (m = 2)
nums2 = [2]    (n = 1)

Swap to make nums1 smaller:
nums1 = [2]    (m = 1)
nums2 = [1, 3] (n = 2)

Total = 3 (Odd)
Left half size = (1 + 2 + 1) / 2 = 2

--------------------------------------------------
### Step-by-Step Binary Search
--------------------------------------------------

- Step 1:
  - st = 0, end = 1
  - px = 0 + (1 - 0) / 2 = 0
  - py = 2 - 0 = 2
  - Partitions:
    - LeftX: [], RightX: [2] -> maxLeftX = -∞, minRightX = 2
    - LeftY: [1, 3], RightY: [] -> maxLeftY = 3, minRightY = +∞
  - Check:
    - maxLeftX (-∞) <= minRightY (+∞) -> TRUE
    - maxLeftY (3) <= minRightX (2) -> FALSE (maxLeftY > minRightX)
  - Action: Need more elements from nums1 -> st = px + 1 = 1

- Step 2:
  - st = 1, end = 1
  - px = 1 + (1 - 1) / 2 = 1
  - py = 2 - 1 = 1
  - Partitions:
    - LeftX: [2], RightX: [] -> maxLeftX = 2, minRightX = +∞
    - LeftY: [1], RightY: [3] -> maxLeftY = 1, minRightY = 3
  - Check:
    - maxLeftX (2) <= minRightY (3) -> TRUE
    - maxLeftY (1) <= minRightX (+∞) -> TRUE
  - Valid partition found!
  - Total length is odd (3 % 2 == 1):
    - Median = Math.max(maxLeftX, maxLeftY) = Math.max(2, 1) = 2.0

--------------------------------------------------
## Final Result
--------------------------------------------------

Return:

2.0

==================================================
## Why This Works
==================================================

The median splits the combined collection into two equal halves where every element in the left half is $\le$ every element in the right half.
By doing a binary search to find the cut line in the smaller array, the corresponding cut line in the larger array is strictly fixed by arithmetic `(m + n + 1) / 2 - px`, allowing instantaneous validation in constant time.

==================================================
## Time Complexity
==================================================

O(log(min(m, n)))

The binary search runs exclusively on the smaller array of length $\min(m, n)$.

==================================================
## Space Complexity
==================================================

O(1)

Only pointers and variable values are used with no extra data structures.

==================================================
*/
