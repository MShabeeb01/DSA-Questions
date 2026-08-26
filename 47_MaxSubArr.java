class Solution {
    // Helper function to check if array can be split into at most 'k' subarrays
    // with no subarray having a sum greater than 'maxAllowedSum'
    private boolean isPossible(int[] nums, int k, int maxAllowedSum) {
        int subarraysCount = 1;
        int currentSum = 0;
        
        for (int num : nums) {
            if (currentSum + num <= maxAllowedSum) {
                currentSum += num;
            } else {
                // Start a new subarray
                subarraysCount++;
                currentSum = num;
            }
            
            // Exceeded allowed splits
            if (subarraysCount > k) {
                return false;
            }
        }
        
        return subarraysCount <= k;
    }

    public int splitArray(int[] nums, int k) {
        int st = 0; // Minimum possible max subarray sum = max element
        int end = 0; // Maximum possible max subarray sum = total sum of all elements
        
        for (int num : nums) {
            st = Math.max(st, num);
            end += num;
        }
        
        int ans = end;
        
        // Binary Search on Answer
        while (st <= end) {
            int mid = st + (end - st) / 2;
            
            if (isPossible(nums, k, mid)) {
                ans = mid;
                end = mid - 1; // Try to find a smaller feasible maximum sum
            } else {
                st = mid + 1;  // Increase the allowed sum threshold
            }
        }
        
        return ans;
    }
}

/* 
==================================================
## Code Summary
==================================================

1. Apply **Binary Search on Answer** to find the minimized maximum subarray sum.

2. Define the search space:
   - `st = max(nums)`: The smallest possible subarray sum cannot be less than the largest single element.
   - `end = sum(nums)`: The largest possible subarray sum occurs when $k = 1$ (the entire array).

3. In each iteration:
   - Compute `mid = st + (end - st) / 2` as candidate maximum allowed subarray sum.
   - Use `isPossible` helper method to greedily allocate contiguous elements to subarrays without exceeding `mid`:
     - If elements can fit in $\le k$ subarrays:
       - Update `ans = mid` and move left (`end = mid - 1`) to search for a smaller feasible sum.
     - Otherwise:
       - Move right (`st = mid + 1`) to allow a larger subarray capacity.

==================================================
## Iteration
==================================================

Input:

nums = [7, 2, 5, 10, 8]
k = 2
Search Range: st = 10, end = 32

--------------------------------------------------
### Step-by-Step Binary Search
--------------------------------------------------

- Step 1:
  - st = 10, end = 32
  - mid = 10 + (32 - 10) / 2 = 21
  - Check `isPossible(maxAllowedSum = 21)`:
    - Subarray 1: [7, 2, 5] -> sum = 14 (adding 10 exceeds 21)
    - Subarray 2: [10, 8] -> sum = 18
    - Total subarrays = 2 <= 2 -> TRUE
  - Action: ans = 21, end = mid - 1 = 20

- Step 2:
  - st = 10, end = 20
  - mid = 10 + (20 - 10) / 2 = 15
  - Check `isPossible(maxAllowedSum = 15)`:
    - Subarray 1: [7, 2, 5] -> sum = 14
    - Subarray 2: [10] -> sum = 10 (adding 8 exceeds 15)
    - Subarray 3: [8] -> sum = 8
    - Total subarrays = 3 > 2 -> FALSE
  - Action: st = mid + 1 = 16

- Step 3:
  - st = 16, end = 20
  - mid = 16 + (20 - 16) / 2 = 18
  - Check `isPossible(maxAllowedSum = 18)`:
    - Subarray 1: [7, 2, 5] -> sum = 14
    - Subarray 2: [10, 8] -> sum = 18
    - Total subarrays = 2 <= 2 -> TRUE
  - Action: ans = 18, end = mid - 1 = 17

- Step 4:
  - st = 16, end = 17
  - mid = 16 + (17 - 16) / 2 = 16
  - Check `isPossible(maxAllowedSum = 16)`:
    - Subarray 1: [7, 2, 5] -> sum = 14
    - Subarray 2: [10] -> sum = 10
    - Subarray 3: [8] -> sum = 8
    - Total subarrays = 3 > 2 -> FALSE
  - Action: st = mid + 1 = 17

- Step 5:
  - st = 17, end = 17
  - mid = 17 + (17 - 17) / 2 = 17
  - Check `isPossible(maxAllowedSum = 17)`:
    - Subarray 1: [7, 2, 5] -> sum = 14
    - Subarray 2: [10] -> sum = 10
    - Subarray 3: [8] -> sum = 8
    - Total subarrays = 3 > 2 -> FALSE
  - Action: st = mid + 1 = 18 (loop terminates since st > end)

--------------------------------------------------
## Final Result
--------------------------------------------------

Return:

18

==================================================
## Why This Works
==================================================

The feasibility condition is strictly monotonic:
If it is possible to split the array into $k$ non-empty subarrays with maximum sum $\le S$, it is also possible for any sum $> S$.
If it is impossible for sum $S$, it is impossible for any sum $< S$.
Thus, binary searching the sum range $[\max(nums), \sum nums]$ guarantees finding the minimal valid maximum sum.

==================================================
## Time Complexity
==================================================

O(N * log(sum(nums) - max(nums)))

Where $N$ is `nums.length`. The binary search runs in $O(\log(\sum nums))$ steps, with each step running an $O(N)$ linear scan.

==================================================
## Space Complexity
==================================================

O(1)

Operates entirely in-place with a few primitive variables.

==================================================
*/
