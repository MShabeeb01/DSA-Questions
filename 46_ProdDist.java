class Solution {
    // Helper function to check if distribution is possible with max 'x' items per store
    private boolean canDistribute(int n, int[] quantities, int x) {
        int storesNeeded = 0;
        
        for (int q : quantities) {
            // Equivalent to ceil(q / (double) x) using integer arithmetic
            storesNeeded += (q + x - 1) / x;
            
            // If required stores exceed available stores 'n', it's impossible
            if (storesNeeded > n) {
                return false;
            }
        }
        
        return storesNeeded <= n;
    }

    public int minimizedMaximum(int n, int[] quantities) {
        int st = 1; // Minimum possible products a store can receive
        int end = 0;
        
        for (int q : quantities) {
            end = Math.max(end, q); // Maximum possible products in a single store
        }
        
        int ans = end;
        
        while (st <= end) {
            int mid = st + (end - st) / 2;
            
            // If it's possible with max 'mid' products per store, try a smaller maximum
            if (canDistribute(n, quantities, mid)) {
                ans = mid;
                end = mid - 1; // Move left to minimize the maximum
            } else {
                st = mid + 1;  // Move right to allow a larger capacity per store
            }
        }
        
        return ans;
    }
}

/* 
==================================================
## Code Summary
==================================================

1. Use **Binary Search on Answer** to find the minimized maximum value `x`.

2. Search range definition:
   - `st = 1`: The lowest non-zero maximum products given to any store.
   - `end = max(quantities)`: The maximum products any single store would ever need if given an entire product type.

3. For each candidate value `mid`:
   - Calculate total stores needed using `ceil(q / mid)`, computed as `(q + mid - 1) / mid`.
   - If total stores needed $\le n$:
     - It's feasible; record `ans = mid` and search the lower half (`end = mid - 1`).
   - If total stores needed $> n$:
     - Not feasible; search the upper half (`st = mid + 1`).

==================================================
## Iteration
==================================================

Input:

n = 6
quantities = [11, 6]
Search Range: st = 1, end = 11

--------------------------------------------------
### Step-by-Step Binary Search
--------------------------------------------------

- Step 1:
  - st = 1, end = 11
  - mid = 1 + (11 - 1) / 2 = 6
  - Check `canDistribute(x = 6)`:
    - For 11: ceil(11 / 6) = 2 stores
    - For 6: ceil(6 / 6) = 1 store
    - Total stores = 2 + 1 = 3 <= 6 -> TRUE
  - Action: ans = 6, end = mid - 1 = 5

- Step 2:
  - st = 1, end = 5
  - mid = 1 + (5 - 1) / 2 = 3
  - Check `canDistribute(x = 3)`:
    - For 11: ceil(11 / 3) = 4 stores
    - For 6: ceil(6 / 3) = 2 stores
    - Total stores = 4 + 2 = 6 <= 6 -> TRUE
  - Action: ans = 3, end = mid - 1 = 2

- Step 3:
  - st = 1, end = 2
  - mid = 1 + (2 - 1) / 2 = 1
  - Check `canDistribute(x = 1)`:
    - For 11: ceil(11 / 1) = 11 stores
    - Total stores = 11 > 6 -> FALSE
  - Action: st = mid + 1 = 2

- Step 4:
  - st = 2, end = 2
  - mid = 2 + (2 - 2) / 2 = 2
  - Check `canDistribute(x = 2)`:
    - For 11: ceil(11 / 2) = 6 stores
    - For 6: ceil(6 / 2) = 3 stores
    - Total stores = 6 + 3 = 9 > 6 -> FALSE
  - Action: st = mid + 1 = 3 (loop terminates since st > end)

--------------------------------------------------
## Final Result
--------------------------------------------------

Return:

3

==================================================
## Why This Works
==================================================

Feasibility is strictly monotonic:
If every product type can be distributed such that no store gets more than $x$ items, it is also feasible for any limit $> x$. 
If it fails for $x$, it will always fail for any limit $< x$.
Binary searching over the range $[1, \max(\text{quantities})]$ guarantees locating the exact minimal feasible $x$.

==================================================
## Time Complexity
==================================================

O(M * log(max(quantities)))

Where $M$ is `quantities.length`. In each of the $O(\log(\max(\text{quantities})))$ iterations, we iterate through the array of length $M$.

==================================================
## Space Complexity
==================================================

O(1)

Only a few auxiliary integer variables are used.

==================================================
*/
