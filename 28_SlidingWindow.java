import java.util.*;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        List<Integer> result = new ArrayList<>();
        Deque<Integer> dq = new LinkedList<>();

        // Process first k elements
        dq.addLast(0);
        for (int i = 1; i < k; i++) {
            while (!dq.isEmpty() && nums[i] >= nums[dq.getLast()]) {
                dq.removeLast();
            }
            dq.addLast(i);
        }

        result.add(nums[dq.getFirst()]);

        // Process remaining elements
        for (int i = k; i < n; i++) {
            // Remove elements out of window
            if (i - dq.getFirst() >= k) {
                dq.removeFirst();
            }

            // Remove smaller elements
            while (!dq.isEmpty() && nums[i] >= nums[dq.getLast()]) {
                dq.removeLast();
            }

            dq.addLast(i);
            result.add(nums[dq.getFirst()]);
        }

        // Convert list to array
        int[] resArr = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            resArr[i] = result.get(i);
        }

        return resArr;
    }
}

/*
==================================================
## Code Summary
==================================================

We need to find the maximum element in every
contiguous sliding window of size k moving from
left to right across an array.

Example:

nums = [1, 3, -1, -3, 5, 3, 6, 7]
k = 3

Output:

[3, 3, 5, 5, 6, 7]


==================================================
## Main Idea
==================================================

We use a Monotonic Deque (Double-Ended Queue).

The deque stores INDEXES of elements, not their values.

The values at these indexes are maintained in strictly
non-increasing (decreasing) order from front to back:

nums[dq.peekFirst()] >= nums[...] >= nums[dq.peekLast()]

Therefore:

FRONT = Maximum element in the current window


==================================================
## STEP 1: First Window (Indices 0 to k - 1)
==================================================

1. Push index 0 into the deque initially:
   dq.addLast(0)

2. For every next index i from 1 to k - 1:
   - While the deque is not empty and nums[i] >= nums[dq.getLast()]:
     remove from the back (dq.removeLast()).
   - Add index i to the back (dq.addLast(i)).

3. After processing the first k elements:
   The front index has the largest value in [0 ... k - 1].
   result.add(nums[dq.getFirst()])


==================================================
## STEP 2: Slide the Window (Indices k to n - 1)
==================================================

For every new element at index i:

1. Remove elements out of the window:
   If (i - dq.getFirst() >= k), the element at
   the front has expired. Remove it with dq.removeFirst().

2. Remove smaller elements from the back:
   While nums[i] >= nums[dq.getLast()], pop from back.

3. Add current index:
   dq.addLast(i)

4. Record the window maximum:
   result.add(nums[dq.getFirst()])


==================================================
## Iteration
==================================================

Input:

nums = [1, 3, -1, -3, 5, 3, 6, 7]
k = 3


--------------------------------------------------
### First Window (Indices 0, 1, 2)
--------------------------------------------------

Window:
[1, 3, -1]

Initial:
Add index 0 (val = 1)
Deque: [0] (vals: [1])

Add index 1 (val = 3):
3 >= nums[0] (3 >= 1) -> removeLast() (removes index 0)
Deque: []
Add index 1 -> Deque: [1] (vals: [3])

Add index 2 (val = -1):
-1 < nums[1] (-1 < 3) -> no removal
Add index 2 -> Deque: [1, 2] (vals: [3, -1])

Front index = 1 -> nums[1] = 3

Result:
[3]


--------------------------------------------------
### Window 2 (Index 3, val = -3)
--------------------------------------------------

Current window elements:
[3, -1, -3]  (indices: 1, 2, 3)

1. Check expired:
   i - dq.getFirst() = 3 - 1 = 2 < 3 -> Not expired.

2. Remove smaller from back:
   -3 < nums[2] (-3 < -1) -> Keep all.

3. Add index 3:
   Deque: [1, 2, 3] (vals: [3, -1, -3])

Front index = 1 -> nums[1] = 3

Result:
[3, 3]


--------------------------------------------------
### Window 3 (Index 4, val = 5)
--------------------------------------------------

Current window elements:
[-1, -3, 5]  (indices: 2, 3, 4)

1. Check expired:
   i - dq.getFirst() = 4 - 1 = 3 >= 3 -> Index 1 expired!
   removeFirst() -> removes index 1.
   Deque: [2, 3]

2. Remove smaller from back:
   5 >= nums[3] (5 >= -3) -> removeLast() (removes index 3)
   5 >= nums[2] (5 >= -1) -> removeLast() (removes index 2)
   Deque: []

3. Add index 4:
   Deque: [4] (vals: [5])

Front index = 4 -> nums[4] = 5

Result:
[3, 3, 5]


--------------------------------------------------
### Window 4 (Index 5, val = 3)
--------------------------------------------------

Current window elements:
[-3, 5, 3]  (indices: 3, 4, 5)

1. Check expired:
   i - dq.getFirst() = 5 - 4 = 1 < 3 -> Not expired.

2. Remove smaller from back:
   3 < nums[4] (3 < 5) -> Keep 5.

3. Add index 5:
   Deque: [4, 5] (vals: [5, 3])

Front index = 4 -> nums[4] = 5

Result:
[3, 3, 5, 5]


--------------------------------------------------
### Window 5 (Index 6, val = 6)
--------------------------------------------------

Current window elements:
[5, 3, 6]  (indices: 4, 5, 6)

1. Check expired:
   i - dq.getFirst() = 6 - 4 = 2 < 3 -> Not expired.

2. Remove smaller from back:
   6 >= nums[5] (6 >= 3) -> removeLast() (removes index 5)
   6 >= nums[4] (6 >= 5) -> removeLast() (removes index 4)
   Deque: []

3. Add index 6:
   Deque: [6] (vals: [6])

Front index = 6 -> nums[6] = 6

Result:
[3, 3, 5, 5, 6]


--------------------------------------------------
### Window 6 (Index 7, val = 7)
--------------------------------------------------

Current window elements:
[3, 6, 7]  (indices: 5, 6, 7)

1. Check expired:
   i - dq.getFirst() = 7 - 6 = 1 < 3 -> Not expired.

2. Remove smaller from back:
   7 >= nums[6] (7 >= 6) -> removeLast() (removes index 6)
   Deque: []

3. Add index 7:
   Deque: [7] (vals: [7])

Front index = 7 -> nums[7] = 7

Result:
[3, 3, 5, 5, 6, 7]


Final Converted Array:
[3, 3, 5, 5, 6, 7]


==================================================
## Why Remove Smaller Values?
==================================================

If a new element entering the window is greater than
or equal to an older element inside the deque:

nums[i] >= nums[dq.getLast()]

The older element is:
1. Smaller than nums[i]
2. Older (will expire before nums[i])

Therefore, it can NEVER be the maximum for this window
or any future window. We safely discard it from the back.


==================================================
## Why Store Indexes?
==================================================

Storing values alone does not tell us when an element
falls outside the sliding window.

Storing indices allows checking expiration in O(1):

if (i - dq.getFirst() >= k) {
    dq.removeFirst();
}


==================================================
## Why Is the Front the Maximum?
==================================================

Because elements are maintained in strictly
non-increasing order:

Every time an element is added, all smaller elements
behind it are evicted.

Hence, the largest valid element of the current window
is always at:

FRONT (dq.getFirst())


==================================================
## Time Complexity
==================================================

- Each index from 0 to n - 1 is added to the deque
  exactly once: O(n)
- Each index is removed from the deque at most once: O(n)
- Converting List to array takes: O(n - k + 1)

Total Time Complexity:

O(n)


==================================================
## Space Complexity
==================================================

- Deque stores at most k elements at any point: O(k)
- Result list/array holds (n - k + 1) elements: O(n - k + 1)

Total Auxiliary Space Complexity:

O(k)  (excluding output storage)


==================================================
## Easy Way to Remember
==================================================

For every new element:

1. EXPIRED: Remove if index is out of window bounds.
2. SMALLER: Remove elements <= current from back.
3. ADD: Push current index to back.
4. RECORD: Front is the maximum.

Remember:

EXPIRED → SMALLER → ADD → FRONT = MAX
==================================================
*/
