import java.util.*;

class Solution {
    public int largestRectangleArea(int[] heights) {

        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;

        // Traverse every bar.
        // We also go one extra step with height = 0
        // to process all remaining bars in the stack.
        for (int i = 0; i <= n; i++) {

            // After the last element, use height 0
            // to force all remaining bars to be popped.
            int currentHeight = (i == n) ? 0 : heights[i];

            // If the current bar is smaller than the
            // bar at the top of the stack, calculate
            // the rectangle using the popped bar.
            while (!stack.isEmpty() &&
                    currentHeight < heights[stack.peek()]) {

                int height = heights[stack.pop()];

                // If stack is empty, the popped bar can
                // extend from index 0 to i - 1.
                //
                // Otherwise, the new stack top is the
                // first smaller bar on the LEFT.
                int width = stack.isEmpty()
                        ? i
                        : i - stack.peek() - 1;

                int area = height * width;

                maxArea = Math.max(maxArea, area);
            }

            // Store the current index.
            stack.push(i);
        }

        return maxArea;
    }
}

/*
==================================================
## Code Summary
==================================================

Problem:

Find the largest rectangular area that can be
formed inside a histogram.

Example:

heights = [2,1,5,6,2,3]

Answer:

10


==================================================
## Main Idea
==================================================

We use a STACK.

The stack stores indexes of bars whose heights
are in increasing order.

Example:

heights = [2,5,6]

Stack:

[2,5,6]

If we find a smaller height:

2

then the taller bars can no longer extend
to the right.

So we pop them and calculate their areas.


==================================================
## What Happens When We Pop?
==================================================

Suppose:

heights = [2,5,6,2]

When we reach the final 2:

6 > 2

So 6 is popped.

The width of the rectangle formed by height 6
is calculated using:

width = currentIndex - stackTop - 1


The stack top tells us where the rectangle
can no longer extend on the LEFT.

The current index tells us where it can no
longer extend on the RIGHT.


==================================================
## Why Do We Use Height 0 at the End?
==================================================

Consider:

[2,4,6]

The stack may still contain:

[2,4,6]

after the loop finishes.

But we still need to calculate their areas.

So at:

i == n

we pretend:

currentHeight = 0

This forces every remaining bar to be popped.


==================================================
## Iteration
==================================================

Input:

heights = [2,1,5,6,2,3]


--------------------------------------------------
### i = 0
--------------------------------------------------

Current height:

2

Stack is empty.

Push index 0.

Stack:

[0]

Values:

[2]


--------------------------------------------------
### i = 1
--------------------------------------------------

Current height:

1

1 < 2

So pop index 0.

height = 2

Stack is now empty.

Therefore:

width = i

width = 1

Area:

2 × 1 = 2

maxArea = 2

Now push index 1.

Stack:

[1]


--------------------------------------------------
### i = 2
--------------------------------------------------

Current height:

5

5 > 1

Push index 2.

Stack:

[1,2]

Values:

[1,5]


--------------------------------------------------
### i = 3
--------------------------------------------------

Current height:

6

6 > 5

Push index 3.

Stack:

[1,2,3]

Values:

[1,5,6]


--------------------------------------------------
### i = 4
--------------------------------------------------

Current height:

2

2 < 6

Pop index 3.

height = 6

Stack top = 2

width:

4 - 2 - 1 = 1

Area:

6 × 1 = 6

maxArea = 6


Current 2 is still smaller than:

5

So pop index 2.

height = 5

Stack top = 1

width:

4 - 1 - 1 = 2

Area:

5 × 2 = 10

maxArea = 10


Now:

2 > 1

Stop popping.

Push index 4.

Stack:

[1,4]


--------------------------------------------------
### i = 5
--------------------------------------------------

Current height:

3

3 > 2

Push index 5.

Stack:

[1,4,5]


--------------------------------------------------
### i = 6
--------------------------------------------------

This is the extra iteration.

Current height:

0

This forces all remaining bars to be processed.


First pop index 5.

height = 3

Stack top = 4

width:

6 - 4 - 1 = 1

Area:

3 × 1 = 3


Next pop index 4.

height = 2

Stack top = 1

width:

6 - 1 - 1 = 4

Area:

2 × 4 = 8


Next pop index 1.

height = 1

Stack is empty.

width:

6

Area:

1 × 6 = 6


Final:

maxArea = 10


==================================================
## Important Formula
==================================================

When we pop a bar:

height = heights[stack.pop()]


Right boundary:

i


Left boundary:

stack.peek()


Therefore:

width = i - stack.peek() - 1


If stack is empty:

width = i


Finally:

area = height × width


==================================================
## Why Is The Stack Increasing?
==================================================

We only push the current index after removing
all taller bars.

Therefore the heights inside the stack remain:

INCREASING


Example:

[1, 5, 6]

If we see:

2

We remove:

6
5

Then push 2.

Stack becomes:

[1,2]


==================================================
## Time Complexity
==================================================

Every index is:

Pushed once.

Popped at most once.


Therefore:

O(N)


==================================================
## Space Complexity
==================================================

The stack can contain at most N indexes.

Therefore:

O(N)


==================================================
## Easy Way to Remember
==================================================

STACK = increasing heights


When:

current < stack top

↓

POP

↓

Calculate:

AREA = HEIGHT × WIDTH


At the end:

Add imaginary height 0

↓

Flush the stack.


==================================================
*/
