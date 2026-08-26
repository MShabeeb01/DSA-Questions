import java.util.Arrays;

class Solution {
    // Helper function to check if we can place 'm' balls with at least 'minForce' distance
    private boolean canPlaceBalls(int[] position, int m, int minForce) {
        int count = 1; // Place the first ball in the first basket
        int lastPos = position[0];
        
        for (int i = 1; i < position.length; i++) {
            if (position[i] - lastPos >= minForce) {
                count++;
                lastPos = position[i];
            }
            if (count >= m) {
                return true;
            }
        }
        
        return false;
    }

    public int maxDistance(int[] position, int m) {
        Arrays.sort(position);
        
        int n = position.length;
        int st = 1; // Minimum possible magnetic force
        int end = position[n - 1] - position[0]; // Maximum possible magnetic force
        int ans = 0;
        
        while (st <= end) {
            int mid = st + (end - st) / 2;
            
            // If it's possible to place all balls with distance 'mid', try for a larger distance
            if (canPlaceBalls(position, m, mid)) {
                ans = mid;
                st = mid + 1; // Move right to maximize the minimum force
            } else {
                end = mid - 1; // Move left to search for a smaller feasible force
            }
        }
        
        return ans;
    }
}

/* 
==================================================
## Code Summary
==================================================

1. Apply the **Binary Search on Answer** pattern.

2. Sort the `position` array so baskets are checked in linear sequential order.

3. Define the search space for the answer (the minimum distance between balls):
   - `st = 1`: The smallest possible distance.
   - `end = position[n - 1] - position[0]`: The absolute maximum distance possible.

4. For each `mid` (candidate force/distance):
   - Use a greedy helper function `canPlaceBalls`:
     - Always place the 1st ball at `position[0]`.
     - Place each subsequent ball only when `current_position - last_position >= mid`.
   - If at least `m` balls can be placed:
     - Save `ans = mid` and move right (`st = mid + 1`) to check if a larger force is possible.
   - Otherwise:
     - Move left (`end = mid - 1`) to reduce the required distance.

==================================================
## Iteration
==================================================

Input:

position = [1, 2, 3, 4, 7]
m = 3
Sorted: [1, 2, 3, 4, 7]
Search Range: st = 1, end = 7 - 1 = 6

--------------------------------------------------
### Step-by-Step Binary Search
--------------------------------------------------

- Step 1:
  - st = 1, end = 6
  - mid = 1 + (6 - 1) / 2 = 3
  - Check `canPlaceBalls(minForce = 3)`:
    - Ball 1 at position[0] = 1
    - position[1]=2: 2 - 1 = 1 < 3 (skip)
    - position[2]=3: 3 - 1 = 2 < 3 (skip)
    - position[3]=4: 4 - 1 = 3 >= 3 -> Ball 2 placed at 4
    - position[4]=7: 7 - 4 = 3 >= 3 -> Ball 3 placed at 7
    - Total placed = 3 >= 3 -> TRUE
  - Action: ans = 3, st = mid + 1 = 4

- Step 2:
  - st = 4, end = 6
  - mid = 4 + (6 - 4) / 2 = 5
  - Check `canPlaceBalls(minForce = 5)`:
    - Ball 1 at position[0] = 1
    - Next possible is position[4] = 7 (7 - 1 = 6 >= 5) -> Ball 2 placed at 7
    - Total placed = 2 < 3 -> FALSE
  - Action: end = mid - 1 = 4

- Step 3:
  - st = 4, end = 4
  - mid = 4 + (4 - 4) / 2 = 4
  - Check `canPlaceBalls(minForce = 4)`:
    - Ball 1 at position[0] = 1
    - Next possible is position[4] = 7 (7 - 1 = 6 >= 4) -> Ball 2 placed at 7
    - Total placed = 2 < 3 -> FALSE
  - Action: end = mid - 1 = 3 (loop terminates since st > end)

--------------------------------------------------
## Final Result
--------------------------------------------------

Return:

3

==================================================
## Why This Works
==================================================

The problem has a monotonic feasibility property: 
If it is impossible to place $m$ balls with a distance $D$, it is also impossible for any distance $> D$. 
If it is possible with distance $D$, it is always possible for any distance $< D$.
This monotonicity allows binary searching directly over the possible answer range $[1, \max(pos) - \min(pos)]$.

==================================================
## Time Complexity
==================================================

O(N * log(max_pos - min_pos) + N * log N)

- Sorting takes $O(N \log N)$.
- Binary search takes $O(\log(\text{range}))$, and each check runs in $O(N)$ linear time.

==================================================
## Space Complexity
==================================================

O(1) (or O(log N) due to sorting recursion stack)

Requires only constant extra space for pointers and variables.

==================================================
*/
