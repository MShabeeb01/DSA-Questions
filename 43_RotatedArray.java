class Solution {
    public int search(int[] nums, int target) {
        int st = 0, end = nums.length - 1;
        
        while (st <= end) {
            int mid = st + (end - st) / 2;
            
            // Target found
            if (nums[mid] == target) {
                return mid;
            }
            
            // Check if the left half is sorted
            if (nums[st] <= nums[mid]) {
                if (nums[st] <= target && target <= nums[mid]) {
                    end = mid - 1; // Target lies in the left sorted portion
                } else {
                    st = mid + 1;  // Target lies in the right portion
                }
            } else { 
                // Right half is sorted
                if (nums[mid] <= target && target <= nums[end]) {
                    st = mid + 1;  // Target lies in the right sorted portion
                } else {
                    end = mid - 1; // Target lies in the left portion
                }
            }
        }
        
        return -1;
    }
}

/* 
==================================================
## Code Summary
==================================================

1. Perform a modified Binary Search on a rotated sorted array in $O(\log N)$ time.

2. Initialize pointers:
   - `st = 0`: Start of the search space.
   - `end = nums.length - 1`: End of the search space.

3. In every iteration, at least one half of the array (left or right) is guaranteed to be strictly sorted:
   - If `nums[st] <= nums[mid]`, the left half `[st...mid]` is sorted.
     - Check if `target` lies within `nums[st]` and `nums[mid]`:
       - If yes, move left (`end = mid - 1`).
       - Otherwise, search the right half (`st = mid + 1`).
   - Otherwise, the right half `[mid...end]` is sorted.
     - Check if `target` lies within `nums[mid]` and `nums[end]`:
       - If yes, move right (`st = mid + 1`).
       - Otherwise, search the left half (`end = mid - 1`).

4. If `nums[mid] == target`, return `mid`. If not found after exhausting the search space, return `-1`.

==================================================
## Iteration
==================================================

Input:

nums = [4, 5, 6, 7, 0, 1, 2]
target = 0
n = 7

--------------------------------------------------
### Step-by-Step Binary Search
--------------------------------------------------

- Step 1:
  - st = 0, end = 6
  - mid = 0 + (6 - 0) / 2 = 3
  - nums[mid] = nums[3] = 7 (7 != 0)
  - Left half sorted check: nums[0] (4) <= nums[3] (7) -> TRUE
  - Range check: nums[0] (4) <= target (0) && target (0) <= nums[3] (7) -> FALSE
  - Action: Search right half -> st = mid + 1 = 4

- Step 2:
  - st = 4, end = 6
  - mid = 4 + (6 - 4) / 2 = 5
  - nums[mid] = nums[5] = 1 (1 != 0)
  - Left half sorted check: nums[4] (0) <= nums[5] (1) -> TRUE
  - Range check: nums[4] (0) <= target (0) && target (0) <= nums[5] (1) -> TRUE
  - Action: Search left half -> end = mid - 1 = 4

- Step 3:
  - st = 4, end = 4
  - mid = 4 + (4 - 4) / 2 = 4
  - nums[mid] = nums[4] = 0
  - Target match: nums[4] == 0 -> Return 4

--------------------------------------------------
## Final Result
--------------------------------------------------

Return:

4

==================================================
## Why This Works
==================================================

Even after rotation, dividing a rotated sorted array at `mid` always leaves at least one half fully sorted. 
By identifying which half is sorted, we can check in $O(1)$ time if the target falls within that sorted segment's boundary values, allowing standard binary search pruning to achieve $O(\log N)$ complexity.

==================================================
## Time Complexity
==================================================

O(log N)

The search range is halved during each iteration.

==================================================
## Space Complexity
==================================================

O(1)

Only constant extra space is used for index pointers (`st`, `end`, `mid`).

==================================================
*/
