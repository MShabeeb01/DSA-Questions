class Solution {
    public int peakIndexInMountainArray(int[] arr) {
        int st = 1, end = arr.length - 2;
        
        while (st <= end) {
            int mid = st + (end - st) / 2;
            
            // Peak condition: element is greater than both its left and right neighbors
            if (arr[mid - 1] < arr[mid] && arr[mid] > arr[mid + 1]) {
                return mid;
            } else if (arr[mid - 1] < arr[mid]) { 
                // Currently on the increasing slope -> peak lies to the right
                st = mid + 1;
            } else { 
                // Currently on the decreasing slope -> peak lies to the left
                end = mid - 1;
            }
        }
        
        return -1;
    }
}

/* 
==================================================
## Code Summary
==================================================

1. Use Binary Search to find the peak element in $O(\log N)$ time.

2. The search space is initialized from index `1` to `arr.length - 2` because the peak can never be the first or last element in a mountain array.

3. Calculate `mid = st + (end - st) / 2` to prevent potential integer overflow.

4. Check the local neighborhood at `mid`:
   - If `arr[mid - 1] < arr[mid] && arr[mid] > arr[mid + 1]`: `mid` is the peak element.
   - If `arr[mid - 1] < arr[mid]`: We are on the strictly increasing slope; move right (`st = mid + 1`).
   - Otherwise: We are on the strictly decreasing slope; move left (`end = mid - 1`).

==================================================
## Iteration
==================================================

Input:

arr = [0, 10, 5, 2]
n = 4
Search space: st = 1, end = 2

--------------------------------------------------
### Step-by-Step Binary Search
--------------------------------------------------

- Step 1:
  - st = 1, end = 2
  - mid = 1 + (2 - 1) / 2 = 1
  - Check:
    - arr[mid - 1] = arr[0] = 0
    - arr[mid] = arr[1] = 10
    - arr[mid + 1] = arr[2] = 5
  - Condition: arr[0] < arr[1] && arr[1] > arr[2] (0 < 10 && 10 > 5) -> TRUE
  - Peak index found at mid = 1.

--------------------------------------------------
## Final Result
--------------------------------------------------

Return:

1

==================================================
## Why This Works
==================================================

A mountain array increases strictly to a peak and then decreases strictly.
Comparing `arr[mid]` with its neighbors determines whether `mid` is on the ascending side, descending side, or at the peak, which allows halving the search space in each iteration without scanning the entire array.

==================================================
## Time Complexity
==================================================

O(log N)

The search space is halved in each step using Binary Search.

==================================================
## Space Complexity
==================================================

O(1)

Uses only a few pointers (`st`, `end`, `mid`), requiring constant extra space.

==================================================
*/
