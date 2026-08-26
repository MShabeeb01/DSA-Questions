class Solution {
    public int singleNonDuplicate(int[] nums) {
        int n = nums.length;
        
        // Base case: single element array
        if (n == 1) return nums[0];
        
        int st = 0, end = n - 1;
        
        while (st <= end) {
            int mid = st + (end - st) / 2;
            
            // Boundary checks
            if (mid == 0 && nums[0] != nums[1]) return nums[mid];
            if (mid == n - 1 && nums[n - 1] != nums[n - 2]) return nums[mid];
            
            // Peak/Single element condition: neither left nor right neighbor matches
            if (nums[mid - 1] != nums[mid] && nums[mid] != nums[mid + 1]) {
                return nums[mid];
            }
            
            // Even index
            if (mid % 2 == 0) {
                if (nums[mid - 1] == nums[mid]) { // Left side is disrupted
                    end = mid - 1;
                } else { // Right side contains the single element
                    st = mid + 1;
                }
            } else { // Odd index
                if (nums[mid - 1] == nums[mid]) { // Pattern is normal up to mid -> look right
                    st = mid + 1;
                } else { // Pattern is disrupted on the left -> look left
                    end = mid - 1;
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

1. Use Binary Search to find the single non-duplicate element in an otherwise pair-sorted array in $O(\log N)$ time.

2. Handle base/boundary cases:
   - If array length is 1, return `nums[0]`.
   - If `mid == 0` and doesn't match `nums[1]`, return `nums[mid]`.
   - If `mid == n - 1` and doesn't match `nums[n - 2]`, return `nums[mid]`.

3. Target condition:
   - If `nums[mid - 1] != nums[mid] && nums[mid] != nums[mid + 1]`, `nums[mid]` is the single element.

4. Index Parity Pattern:
   - Before the single element, pairs start at **even** indices and end at **odd** indices `(even, odd)`.
   - After the single element, the order flips to `(odd, even)`.
   - **If `mid` is even:**
     - If `nums[mid - 1] == nums[mid]`: the flip happened on the left -> search left (`end = mid - 1`).
     - Otherwise: search right (`st = mid + 1`).
   - **If `mid` is odd:**
     - If `nums[mid - 1] == nums[mid]`: pairs are intact up to `mid` -> search right (`st = mid + 1`).
     - Otherwise: search left (`end = mid - 1`).

==================================================
## Iteration
==================================================

Input:

nums = [1, 1, 2, 3, 3, 4, 4, 8, 8]
n = 9

--------------------------------------------------
### Step-by-Step Binary Search
--------------------------------------------------

- Step 1:
  - st = 0, end = 8
  - mid = 0 + (8 - 0) / 2 = 4 (even index)
  - nums[mid] = nums[4] = 3
  - Check single element: nums[3] (3) == nums[4] (3) -> FALSE
  - Parity check (mid is even):
    - nums[mid - 1] == nums[mid] -> nums[3] == nums[4] (3 == 3) -> TRUE
    - Action: Single element is on the left -> end = mid - 1 = 3

- Step 2:
  - st = 0, end = 3
  - mid = 0 + (3 - 0) / 2 = 1 (odd index)
  - nums[mid] = nums[1] = 1
  - Check single element: nums[0] (1) == nums[1] (1) -> FALSE
  - Parity check (mid is odd):
    - nums[mid - 1] == nums[mid] -> nums[0] == nums[1] (1 == 1) -> TRUE
    - Action: Single element is on the right -> st = mid + 1 = 2

- Step 3:
  - st = 2, end = 3
  - mid = 2 + (3 - 2) / 2 = 2 (even index)
  - nums[mid] = nums[2] = 2
  - Check single element:
    - nums[1] (1) != nums[2] (2) && nums[2] (2) != nums[3] (3) -> TRUE
    - Return nums[mid] = 2

--------------------------------------------------
## Final Result
--------------------------------------------------

Return:

2

==================================================
## Why This Works
==================================================

All duplicated elements normally appear as `[even_idx, odd_idx]`. 
The single element shifts all subsequent pairs into `[odd_idx, even_idx]`. 
Checking whether `mid` matches its left neighbor relative to its index parity determines which side of the partition `mid` lies on.

==================================================
## Time Complexity
==================================================

O(log N)

The search interval is cut in half at every iteration.

==================================================
## Space Complexity
==================================================

O(1)

Uses only a constant number of pointers and integer variables.

==================================================
*/
