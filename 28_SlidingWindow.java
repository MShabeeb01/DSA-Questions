import java.util.*;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();

        // Unified Single Loop from 0 to n - 1
        for (int i = 0; i < n; i++) {

            // 1. Remove elements that have fallen outside the window
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) {
                dq.pollFirst();
            }

            // 2. Remove smaller elements from the back
            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) {
                dq.pollLast();
            }

            // 3. Add current index to the back
            dq.offerLast(i);

            // 4. Record the maximum once the first valid window of size k is formed
            if (i >= k - 1) {
                res[i - k + 1] = nums[dq.peekFirst()];
            }
        }

        return res;
    }
}

/*
==================================================
## Code Summary
==================================================

Find the maximum value inside every sliding window
of size k moving across an array of length n.

Example:

nums = [1, 3, -1, -3, 5, 3, 6, 7]
k = 3

Output:

[3, 3, 5, 5, 6, 7]


==================================================
## Main Idea
==================================================

Use a Monotonic Deque inside a UNIFIED SINGLE LOOP
from index 0 to n - 1.

The deque stores INDICES, and maintains their values
in decreasing order:

nums[front] >= ... >= nums[back]

Because of this order:

FRONT = Maximum element of the current window


==================================================
## Why One Single Loop?
==================================================

Previously, we used two loops:
- Loop 1 (0 to k - 1): Pre-fill the first window.
- Loop 2 (k to n - 1): Slide and record answers.

In a single loop:
- `dq.peekFirst() <= i - k` safely does nothing when
  i < k because (i - k) is negative.
- `if (i >= k - 1)` ensures we only write to `res[]`
  once the window has accumulated at least k elements.

This eliminates duplicated logic while keeping the
exact same O(n) performance.


==================================================
## Iteration
==================================================

Input:

nums = [1, 3, -1, -3, 5, 3, 6, 7]
k = 3


--------------------------------------------------
### Step i = 0 (val = 1)
--------------------------------------------------

1. Out of window check:
   dq.peekFirst() <= 0 - 3 (-3) -> False.

2. Remove smaller from back:
   Deque is empty.

3. Add index 0:
   Deque: [0] (vals: [1])

4. Record answer:
   i >= 3 - 1 (0 >= 2) -> False.
   (Window not full yet)


--------------------------------------------------
### Step i = 1 (val = 3)
--------------------------------------------------

1. Out of window check:
   dq.peekFirst() <= 1 - 3 (-2) -> False.

2. Remove smaller from back:
   nums[0] <= nums[1] (1 <= 3) -> Pop index 0.
   Deque: []

3. Add index 1:
   Deque: [1] (vals: [3])

4. Record answer:
   i >= 2 (1 >= 2) -> False.
   (Window not full yet)


--------------------------------------------------
### Step i = 2 (val = -1)  --> First Window Complete!
--------------------------------------------------

1. Out of window check:
   dq.peekFirst() <= 2 - 3 (-1) -> False.

2. Remove smaller from back:
   nums[1] <= nums[2] (3 <= -1) -> False.

3. Add index 2:
   Deque: [1, 2] (vals: [3, -1])

4. Record answer:
   i >= 2 (2 >= 2) -> True!
   Index in res = i - k + 1 = 2 - 3 + 1 = 0.
   res[0] = nums[dq.peekFirst()] = nums[1] = 3.

Result so far:
[3]


--------------------------------------------------
### Step i = 3 (val = -3)
--------------------------------------------------

1. Out of window check:
   dq.peekFirst() <= 3 - 3 (1 <= 0) -> False.

2. Remove smaller from back:
   nums[2] <= nums[3] (-1 <= -3) -> False.

3. Add index 3:
   Deque: [1, 2, 3] (vals: [3, -1, -3])

4. Record answer:
   i >= 2 (3 >= 2) -> True.
   res[1] = nums[dq.peekFirst()] = nums[1] = 3.

Result so far:
[3, 3]


--------------------------------------------------
### Step i = 4 (val = 5)
--------------------------------------------------

1. Out of window check:
   dq.peekFirst() <= 4 - 3 (1 <= 1) -> True!
   Index 1 expired. Poll first.
   Deque: [2, 3]

2. Remove smaller from back:
   nums[3] <= nums[4] (-3 <= 5) -> Pop index 3.
   nums[2] <= nums[4] (-1 <= 5) -> Pop index 2.
   Deque: []

3. Add index 4:
   Deque: [4] (vals: [5])

4. Record answer:
   i >= 2 (4 >= 2) -> True.
   res[2] = nums[dq.peekFirst()] = nums[4] = 5.

Result so far:
[3, 3, 5]


--------------------------------------------------
### Step i = 5 (val = 3)
--------------------------------------------------

1. Out of window check:
   dq.peekFirst() <= 5 - 3 (4 <= 2) -> False.

2. Remove smaller from back:
   nums[4] <= nums[5] (5 <= 3) -> False.

3. Add index 5:
   Deque: [4, 5] (vals: [5, 3])

4. Record answer:
   i >= 2 (5 >= 2) -> True.
   res[3] = nums[dq.peekFirst()] = nums[4] = 5.

Result so far:
[3, 3, 5, 5]


--------------------------------------------------
### Step i = 6 (val = 6)
--------------------------------------------------

1. Out of window check:
   dq.peekFirst() <= 6 - 3 (4 <= 3) -> False.

2. Remove smaller from back:
   nums[5] <= nums[6] (3 <= 6) -> Pop index 5.
   nums[4] <= nums[6] (5 <= 6) -> Pop index 4.
   Deque: []

3. Add index 6:
   Deque: [6] (vals: [6])

4. Record answer:
   i >= 2 (6 >= 2) -> True.
   res[4] = nums[dq.peekFirst()] = nums[6] = 6.

Result so far:
[3, 3, 5, 5, 6]


--------------------------------------------------
### Step i = 7 (val = 7)  --> Last Element (n - 1)
--------------------------------------------------

1. Out of window check:
   dq.peekFirst() <= 7 - 3 (6 <= 4) -> False.

2. Remove smaller from back:
   nums[6] <= nums[7] (6 <= 7) -> Pop index 6.
   Deque: []

3. Add index 7:
   Deque: [7] (vals: [7])

4. Record answer:
   i >= 2 (7 >= 2) -> True.
   res[5] = nums[dq.peekFirst()] = nums[7] = 7.

Final Result:
[3, 3, 5, 5, 6, 7]


==================================================
## Why Remove Smaller Values?
==================================================

When a new element enters at index i:
Any existing element in the deque that is smaller
than nums[i] is useless because:
1. It is smaller than nums[i].
2. It appeared earlier, so it will expire sooner.

It can never be the maximum again.


==================================================
## Why Store Indexes?
==================================================

Storing indices enables checking window expiry in O(1):

dq.peekFirst() <= i - k

If true, the index is outside the current window
boundary [i - k + 1, i].


==================================================
## Why Is the Front the Maximum?
==================================================

Because every time an element is pushed:
All smaller elements behind it are removed.

This preserves a monotonically decreasing order:

nums[front] > nums[second] > nums[third]...

Hence:
FRONT is always the largest element in the window.


==================================================
## Time Complexity
==================================================

- Every index from 0 to n - 1 is added to the deque
  once: O(n)
- Every index is removed from the deque at most once: O(n)

Total Time Complexity:

O(n)


==================================================
## Space Complexity
==================================================

- Deque holds at most k indices at any moment: O(k)
- Result array holds (n - k + 1) values: O(n - k + 1)

Total Auxiliary Space Complexity:

O(k)  (excluding output array)


==================================================
## Easy Way to Remember
==================================================

Inside ONE loop (0 to n - 1):

1. EXPIRED : Pop front if index <= i - k
2. SMALLER : Pop back if nums[back] <= nums[i]
3. ADD     : Push index i to back
4. RECORD  : If i >= k - 1, res[i - k + 1] = nums[front]

Remember:

EXPIRED → SMALLER → ADD → RECORD (when i >= k - 1)
==================================================
*/
