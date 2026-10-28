/*
 * Problem: Trapping Rain Water (LeetCode #42)
 * Approach: Two-Pointer Technique (Optimal)
 *
 * Time Complexity  : O(n) -> Single pass with converging pointers
 * Space Complexity : O(1) -> No auxiliary arrays required
 */

class Solution {
    public int trap(int[] height) {
        // Base Condition: At least 3 bars are required to form a basin/trap water.
        if (height == null || height.length <= 2) {
            return 0;
        }

        int n = height.length;
        int ans = 0;

        // Initialize boundary maximums from the outer edges
        int lmax = height[0];
        int rmax = height[n - 1];

        // Pointers start inward because outer edges (index 0 and n-1) cannot hold water
        int low = 1;
        int high = n - 2;

        // Converge inward until all inner bars have been processed
        while (low <= high) {
            // Update the running peak heights seen so far from both ends
            lmax = Math.max(lmax, height[low]);
            rmax = Math.max(rmax, height[high]);

            // Bottleneck Principle:
            // Water capacity is strictly constrained by min(lmax, rmax).
            // Whichever side is strictly lower decides that side's water level immediately.
            if (lmax < rmax) {
                // lmax is the true limiting boundary for column `low`.
                ans += lmax - height[low];
                low++;
            } else {
                // rmax is the true limiting boundary for column `high`.
                ans += rmax - height[high];
                high--;
            }
        }

        return ans;
    }
}

/*
================================================================================
                                APPROACH SUMMARY
================================================================================

1. Core Bottleneck Intuition:
   The volume of water held directly above index `i` is determined by:
       Water[i] = min(max_left, max_right) - height[i]

2. Why O(1) Space Works Without Full Arrays:
   - When `lmax < rmax`, we do not need to know the exact future heights to the
     right of `low`. We already know at least one bar on the right (`rmax`) is
     taller than `lmax`.
   - Thus, `min(lmax, rightMax[low])` is guaranteed to be `lmax`.
   - This lets us resolve water at `low` immediately and move inward.
   - Symmetrically, when `rmax <= lmax`, the limiting wall for `high` is guaranteed
     to be `rmax`.

3. Pointer Convergence:
   - Left pointer (`low`) moves right (`low++`) when `lmax < rmax`.
   - Right pointer (`high`) moves left (`high--`) when `rmax <= lmax`.
   - Terminate once `low > high` (all inner bars calculated).


================================================================================
                        TRACE & STEP-BY-STEP ITERATION
================================================================================

Input Array: height = [4, 2, 0, 6, 3, 2, 5]
Total elements (n)  = 7

Initial State:
  - lmax = height[0] = 4
  - rmax = height[6] = 5
  - low  = 1
  - high = 5
  - ans  = 0

ASCII Map Reference:
  Height
    6 |             █
    5 |             █             █
    4 |   █  ~   ~  █             █
    3 |   █  ~   ~  █   █   ~     █
    2 |   █  █   ~  █   █   █     █
    1 |   █  █   ~  █   █   █     █
    0 +---+---+---+---+---+---+---+---
  Idx:    0   1   2   3   4   5   6
  Ht :    4   2   0   6   3   2   5

--------------------------------------------------------------------------------
Iteration 1:
  Pointers : low = 1, high = 5
  Values   : height[low] = 2, height[high] = 2
  Peaks    : lmax = max(4, 2) = 4
             rmax = max(5, 2) = 5
  Compare  : lmax (4) < rmax (5) -> TRUE
  Action   : Process `low` (index 1)
             Water added = lmax - height[low] = 4 - 2 = 2
             ans = 0 + 2 = 2
             low++ (low becomes 2)

  Visual State of Array:
    Indices :   0    [1]   2    3    4    5    6
    Height  :   4     2    0    6    3    2    5
    Water   :   -    [2]   .    .    .    .    -
    Pointers:        low                      high

--------------------------------------------------------------------------------
Iteration 2:
  Pointers : low = 2, high = 5
  Values   : height[low] = 0, height[high] = 2
  Peaks    : lmax = max(4, 0) = 4
             rmax = max(5, 2) = 5
  Compare  : lmax (4) < rmax (5) -> TRUE
  Action   : Process `low` (index 2)
             Water added = lmax - height[low] = 4 - 0 = 4
             ans = 2 + 4 = 6
             low++ (low becomes 3)

  Visual State of Array:
    Indices :   0     1   [2]   3    4    5    6
    Height  :   4     2    0    6    3    2    5
    Water   :   -     2   [4]   .    .    .    -
    Pointers:             low                 high

--------------------------------------------------------------------------------
Iteration 3:
  Pointers : low = 3, high = 5
  Values   : height[low] = 6, height[high] = 2
  Peaks    : lmax = max(4, 6) = 6
             rmax = max(5, 2) = 5
  Compare  : lmax (6) < rmax (5) -> FALSE (rmax <= lmax)
  Action   : Process `high` (index 5)
             Water added = rmax - height[high] = 5 - 2 = 3
             ans = 6 + 3 = 9
             high-- (high becomes 4)

  Visual State of Array:
    Indices :   0     1    2    3    4   [5]   6
    Height  :   4     2    0    6    3    2    5
    Water   :   -     2    4    .    .   [3]   -
    Pointers:                  low       high

--------------------------------------------------------------------------------
Iteration 4:
  Pointers : low = 3, high = 4
  Values   : height[low] = 6, height[high] = 3
  Peaks    : lmax = max(6, 6) = 6
             rmax = max(5, 3) = 5
  Compare  : lmax (6) < rmax (5) -> FALSE (rmax <= lmax)
  Action   : Process `high` (index 4)
             Water added = rmax - height[high] = 5 - 3 = 2
             ans = 9 + 2 = 11
             high-- (high becomes 3)

  Visual State of Array:
    Indices :   0     1    2    3   [4]   5    6
    Height  :   4     2    0    6    3    2    5
    Water   :   -     2    4    .   [2]   3    -
    Pointers:                  low/high

--------------------------------------------------------------------------------
Iteration 5:
  Pointers : low = 3, high = 3 (both pointers at peak index 3)
  Values   : height[low] = 6, height[high] = 6
  Peaks    : lmax = max(6, 6) = 6
             rmax = max(5, 6) = 6
  Compare  : lmax (6) < rmax (6) -> FALSE (rmax <= lmax)
  Action   : Process `high` (index 3)
             Water added = rmax - height[high] = 6 - 6 = 0
             ans = 11 + 0 = 11
             high-- (high becomes 2)

  Visual State of Array:
    Indices :   0     1    2   [3]   4    5    6
    Height  :   4     2    0    6    3    2    5
    Water   :   -     2    4   [0]   2    3    -
    Pointers:             high low (crossed)

--------------------------------------------------------------------------------
Loop Termination:
  Condition (low <= high) -> (3 <= 2) is FALSE. Loop exits.
  Final Returned Answer: 11
================================================================================
*/
