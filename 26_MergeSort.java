import java.util.*;

class Solution {
    public List<Integer> countSmaller(int[] nums) {
        int min = nums[0];
        int max = nums[0];

        // Find the minimum and maximum values.
        // We use them to convert negative values
        // into positive indices for the Fenwick Tree.
        for (int i = 0; i < nums.length; i++) {
            final int v = nums[i];

            if (v < min) {
                min = v;
            } else if (v > max) {
                max = v;
            }
        }

        // Shift all values so that they become
        // positive Fenwick Tree indices.
        final int delta = -min + 1;

        // Fenwick Tree.
        // arr[x] helps us store frequency information
        // and calculate how many smaller values exist.
        final int[] arr = new int[max + delta + 1];

        final int[] res = new int[nums.length];

        // Traverse from RIGHT to LEFT.
        for (int i = nums.length - 1; i >= 0; i--) {

            // Convert nums[i] into a positive index.
            final int v = nums[i] + delta;

            // Count how many previously seen values
            // are smaller than nums[i].
            res[i] = get(arr, v - 1);

            // Add the current value to the Fenwick Tree.
            add(arr, v);
        }

        // Return the result as a List.
        return new AbstractList<Integer>() {
            @Override
            public Integer get(int index) {
                return res[index];
            }

            @Override
            public int size() {
                return res.length;
            }
        };
    }

    // Returns the number of elements with index <= v.
    // In other words, it gives the frequency/prefix sum
    // of all values smaller than the current value.
    static int get(final int[] arr, int v) {
        int sum = 0;

        while (v > 0) {
            sum += arr[v];
            v -= v & -v;
        }

        return sum;
    }

    // Adds one occurrence of value v
    // into the Fenwick Tree.
    static void add(final int[] arr, int v) {
        while (v < arr.length) {
            arr[v]++;
            v += v & -v;
        }
    }
}

/*
==================================================
## Code Summary
==================================================

Problem:

For every nums[i], find how many elements
smaller than nums[i] are present on its RIGHT.

Example:

nums = [5, 2, 6, 1]

Answer:

[2, 1, 1, 0]


==================================================
## Main Idea
==================================================

Instead of checking every element on the right,
we process the array from:

RIGHT → LEFT


Why?

When we are at nums[i], all elements that were
originally to its right have already been processed.

We store their frequencies inside a:

Fenwick Tree
(Binary Indexed Tree)


Then we ask:

"How many already-seen values are smaller
than the current value?"


==================================================
## What Is a Fenwick Tree?
==================================================

Think of it as a special array that allows us
to efficiently:

1. Add a number
2. Count numbers up to a certain value

For example, suppose we have already seen:

[1, 2, 6]

If the current number is:

5

We want:

How many seen numbers are < 5?

Answer:

1 and 2

So:

2


The Fenwick Tree lets us find this quickly.


==================================================
## Why Do We Need delta?
==================================================

Fenwick Tree uses positive indices.

But nums can contain negative numbers.

Example:

nums = [-3, -1, 2]


We cannot directly use:

-3

as an array index.

So we shift every number.

delta = -min + 1


If:

min = -3

then:

delta = 4


Values become:

-3 + 4 = 1
-1 + 4 = 3
 2 + 4 = 6


Now all values can be used as indices.


==================================================
## What Does get() Do?
==================================================

get(arr, v)

returns the number of values whose
Fenwick Tree index is <= v.


For the current value:

v

we call:

get(arr, v - 1)


Why v - 1?

Because we want:

STRICTLY smaller


Example:

current value = 5

We want:

1, 2, 3, 4


NOT:

5


Therefore we query:

v - 1


==================================================
## What Does add() Do?
==================================================

add(arr, v)

means:

"One occurrence of value v has been seen."


So:

arr

is updated to remember that this value exists.


==================================================
## Iteration
==================================================

Input:

nums = [5, 2, 6, 1]


Initial:

res = [0, 0, 0, 0]


We start from the RIGHT.


--------------------------------------------------
### Iteration 1
--------------------------------------------------

i = 3

nums[i] = 1


Nothing is to the right of 1.


So:

res[3] = 0


Now add 1 to the Fenwick Tree.


Seen:

[1]


Result:

[0, 0, 0, 0]


--------------------------------------------------
### Iteration 2
--------------------------------------------------

i = 2

nums[i] = 6


Already seen:

[1]


Numbers smaller than 6:

[1]


So:

res[2] = 1


Now add 6.


Seen:

[1, 6]


Result:

[0, 0, 1, 0]


--------------------------------------------------
### Iteration 3
--------------------------------------------------

i = 1

nums[i] = 2


Already seen:

[1, 6]


Numbers smaller than 2:

[1]


So:

res[1] = 1


Now add 2.


Seen:

[1, 6, 2]


Result:

[0, 1, 1, 0]


--------------------------------------------------
### Iteration 4
--------------------------------------------------

i = 0

nums[i] = 5


Already seen:

[1, 6, 2]


Numbers smaller than 5:

[1, 2]


So:

res[0] = 2


Now add 5.


Seen:

[1, 2, 5, 6]


Final:

[2, 1, 1, 0]


==================================================
## Why Do We Traverse Right to Left?
==================================================

Suppose:

nums = [5, 2, 6, 1]


When we reach 5:

Everything already processed is:

[2, 6, 1]


These are exactly the elements
to the RIGHT of 5.


Therefore, we don't need to separately
search the right side.


The Fenwick Tree already contains them.


==================================================
## Complete Flow
==================================================

For every element from RIGHT to LEFT:

1. Convert value to a positive index.

2. Query Fenwick Tree:

   How many seen values are smaller?

3. Store that count in res[i].

4. Add the current value to the tree.


In short:

QUERY → STORE → ADD


==================================================
## Why get(v - 1)?
==================================================

Suppose current value is:

5


We want:

values < 5


So:

1
2
3
4


We do NOT want:

5


Therefore:

get(5 - 1)

which means:

get(4)


==================================================
## Why Does add() Come After get()?
==================================================

This is very important.

We first ask:

"How many smaller elements are already
to my right?"


Then we add the current element.


If we added it first, the current element
could incorrectly be included in its own count.


So the order is:

get()
↓
add()


==================================================
## Final Result
==================================================

Input:

[5, 2, 6, 1]


Output:

[2, 1, 1, 0]


Meaning:

5 → 2 smaller elements: 2, 1

2 → 1 smaller element: 1

6 → 1 smaller element: 1

1 → 0 smaller elements


==================================================
## Time Complexity
==================================================

Finding min/max:

O(N)


For every element:

get() → O(log N)
add() → O(log N)


For N elements:

O(N log N)


Overall:

O(N log N)


==================================================
## Space Complexity
==================================================

Fenwick Tree:

O(N)

Result array:

O(N)


Overall:

O(N)


==================================================
## Easy Way to Remember
==================================================

Go:

RIGHT → LEFT


For every number:

1. ASK:

   How many smaller values have I seen?


2. STORE:

   Put answer in res[i]


3. ADD:

   Add current value to Fenwick Tree


Remember:

QUERY → STORE → ADD

==================================================
*/
