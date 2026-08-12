import java.util.*;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {

        int n = nums.length;
        int[] res = new int[n - k + 1];

        // Deque stores indices.
        // Values in the deque are kept in decreasing order.
        Deque<Integer> dq = new ArrayDeque<>();

        // --------------------------------------------------
        // STEP 1: Create the first window
        // --------------------------------------------------

        for (int i = 0; i < k; i++) {

            // Remove smaller values from the back.
            while (!dq.isEmpty() &&
                    nums[dq.peekLast()] <= nums[i]) {
                dq.pollLast();
            }

            dq.offerLast(i);
        }

        // Maximum of the first window.
        res[0] = nums[dq.peekFirst()];

        // --------------------------------------------------
        // STEP 2: Slide the window
        // --------------------------------------------------

        for (int i = k; i < n; i++) {

            // Remove elements that are no longer
            // part of the current window.
            while (!dq.isEmpty() &&
                    dq.peekFirst() <= i - k) {
                dq.pollFirst();
            }

            // Remove smaller values because they
            // can never become the maximum.
            while (!dq.isEmpty() &&
                    nums[dq.peekLast()] <= nums[i]) {
                dq.pollLast();
            }

            // Add current index.
            dq.offerLast(i);

            // Front always contains the maximum.
            res[i - k + 1] = nums[dq.peekFirst()];
        }

        return res;
    }
}

/*
==================================================
## Code Summary
==================================================

We need to find the maximum element in every
window of size k.

Example:

nums = [1,3,-1,-3,5,3,6,7]
k = 3

Output:

[3,3,5,5,6,7]


==================================================
## Main Idea
==================================================

We use a Deque.

The deque stores INDEXES, not values.

The values corresponding to these indexes
are always maintained in decreasing order.

Therefore:

FRONT = Maximum element


==================================================
## STEP 1: First Window
==================================================

We first create the window of size k.

For every element:

1. Remove smaller values from the back.
2. Add the current index.

After the first window is created:

The front of the deque contains
the maximum element.

So:

res[0] = nums[dq.peekFirst()]


==================================================
## STEP 2: Slide the Window
==================================================

For every new element:

1. Remove elements outside the window.
2. Remove smaller elements from the back.
3. Add the current index.
4. Front = maximum.


==================================================
## Iteration
==================================================

Input:

nums = [1,3,-1,-3,5,3,6,7]

k = 3


--------------------------------------------------
### First Window
--------------------------------------------------

Window:

[1, 3, -1]

Add 1:

Deque:

[1]


Add 3:

3 > 1

Remove 1.

Deque:

[3]


Add -1:

-1 < 3

Deque:

[3, -1]


Front = 3

Result:

[3]


--------------------------------------------------
### Window 2
--------------------------------------------------

New element:

-3

Current window:

[3, -1, -3]


-3 is smaller than -1.

Keep it.

Deque:

[3, -1, -3]

Front = 3

Result:

[3, 3]


--------------------------------------------------
### Window 3
--------------------------------------------------

New element:

5

Current window:

[-1, -3, 5]


5 > -3

Remove -3.


5 > -1

Remove -1.


Deque:

[5]

Front = 5

Result:

[3, 3, 5]


--------------------------------------------------
### Window 4
--------------------------------------------------

New element:

3

Current window:

[-3, 5, 3]


3 < 5

Keep 5.

Add 3.

Deque:

[5, 3]

Front = 5

Result:

[3, 3, 5, 5]


--------------------------------------------------
### Window 5
--------------------------------------------------

New element:

6

Current window:

[5, 3, 6]


6 > 3

Remove 3.


6 > 5

Remove 5.


Deque:

[6]

Front = 6

Result:

[3, 3, 5, 5, 6]


--------------------------------------------------
### Window 6
--------------------------------------------------

New element:

7

Current window:

[3, 6, 7]


7 > 6

Remove 6.

Deque:

[7]

Front = 7

Final:

[3, 3, 5, 5, 6, 7]


==================================================
## Why Remove Smaller Values?
==================================================

Suppose the deque contains:

[8, 5]

And the new value is:

10


Since:

10 > 5

5 can never be the maximum while 10
is inside the window.

Also:

10 > 8

So 8 can also be removed.

Deque becomes:

[10]


This keeps the deque useful for finding
the maximum.


==================================================
## Why Store Indexes?
==================================================

We need to know when an element leaves
the sliding window.

For example:

k = 3
i = 4

Current window:

indexes:

[2, 3, 4]

So index 1 is outside the window.

We can check:

dq.peekFirst() <= i - k


If true:

Remove it.


==================================================
## Why Is the Front the Maximum?
==================================================

The deque always keeps values in:

DECREASING ORDER


Example:

[9, 7, 5, 2]

The largest value is always at:

FRONT


Therefore:

nums[dq.peekFirst()]

gives the maximum instantly.


==================================================
## Time Complexity
==================================================

Every element is:

Added once.

Removed at most once.


Therefore:

O(N)


==================================================
## Space Complexity
==================================================

Deque can contain at most N elements.

Therefore:

O(N)


==================================================
## Easy Way to Remember
==================================================

For every new element:

1. Remove OUTSIDE elements.
2. Remove SMALLER elements.
3. ADD current index.
4. FRONT = MAXIMUM.

Remember:

OUTSIDE → SMALLER → ADD → FRONT = MAX
==================================================
*/
