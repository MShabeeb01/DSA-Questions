class Solution {
    public int findDuplicate(int[] nums) {

        int slow = nums[0];
        int fast = nums[0];

        // ==================================================
        // STEP 1: Find the meeting point inside the cycle
        // ==================================================

        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        // ==================================================
        // STEP 2: Find the starting point of the cycle
        // ==================================================

        slow = nums[0];

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }
}

/*
==================================================
## Code Summary
==================================================

This solution uses:

Floyd's Cycle Detection Algorithm

It uses two pointers:

slow -> moves 1 step
fast -> moves 2 steps


We treat the array like a linked list.

For:

nums = [3, 1, 3, 4, 2]

We can think of it as:

0 -> 3 -> 4 -> 2 -> 3
          ↑         ↓
          └─────────┘

There is a cycle.

The beginning of this cycle is:

3

And 3 is the duplicate number.


==================================================
## Why Can We Treat the Array Like a Linked List?
==================================================

Normally in a linked list:

node -> next node

Here we use:

index -> nums[index]

For example:

nums = [3, 1, 3, 4, 2]

Start at index 0:

nums[0] = 3

So:

0 -> 3

Now go to index 3:

nums[3] = 4

So:

3 -> 4

Then:

nums[4] = 2

So:

4 -> 2

Then:

nums[2] = 3

So:

2 -> 3

We are back at 3.

Therefore, a cycle exists.


==================================================
## Why Does the Duplicate Create a Cycle?
==================================================

Every value is between 1 and n.

So every value can be used as an index.

Because one number is repeated, two different
positions eventually point to the same number.

That creates a cycle.

The starting point of that cycle is the
duplicate number.


==================================================
## Iteration
==================================================

Example:

nums = [3, 1, 3, 4, 2]

Expected Output:

3


==================================================
## Initial State
==================================================

slow = nums[0]
slow = 3

fast = nums[0]
fast = 3


So:

slow = 3
fast = 3


==================================================
## STEP 1
## Find the Meeting Point
==================================================

We use:

slow = nums[slow]

fast = nums[nums[fast]]

slow moves 1 step.

fast moves 2 steps.


--------------------------------------------------
### Iteration 1
--------------------------------------------------

slow:

slow = nums[3]
slow = 4


fast:

First step:

fast = nums[3]
fast = 4

Second step:

fast = nums[4]
fast = 2


Now:

slow = 4
fast = 2


They are not equal.


--------------------------------------------------
### Iteration 2
--------------------------------------------------

slow:

slow = nums[4]
slow = 2


fast:

First step:

fast = nums[2]
fast = 3

Second step:

fast = nums[3]
fast = 4


Now:

slow = 2
fast = 4


They are not equal.


--------------------------------------------------
### Iteration 3
--------------------------------------------------

slow:

slow = nums[2]
slow = 3


fast:

First step:

fast = nums[4]
fast = 2

Second step:

fast = nums[2]
fast = 3


Now:

slow = 3
fast = 3


They meet!


IMPORTANT:

This is only the meeting point.

It is not necessarily the duplicate.

Now we move to STEP 2.


==================================================
## STEP 2
## Find the Duplicate
==================================================

Reset slow:

slow = nums[0]

slow = 3


fast is still:

fast = 3


They are already equal.

Therefore:

duplicate = 3


return 3;


==================================================
## Another Example
==================================================

nums = [1, 3, 4, 2, 2]

Expected:

2


The structure becomes:

0 -> 1 -> 3 -> 2 -> 4
               ↑       ↓
               └───────┘

The cycle starts at:

2

Therefore:

duplicate = 2


==================================================
## Why Do We Need TWO Steps?
==================================================

STEP 1:

Find a point where slow and fast meet
inside the cycle.

STEP 2:

Find the beginning of that cycle.

The beginning of the cycle is the duplicate.


So remember:

STEP 1:
Find meeting point.

STEP 2:
Find cycle entrance.


==================================================
## Why Do We Reset slow?
==================================================

After STEP 1:

slow and fast are somewhere inside
the cycle.

We don't know where the cycle starts.

So:

slow = nums[0]

Now:

slow starts from the beginning.

fast stays at the meeting point.

Then both move at the same speed:

slow = nums[slow]
fast = nums[fast]

They eventually meet at the beginning
of the cycle.

That value is the duplicate.


==================================================
## Why Not Use Sorting?
==================================================

We could do:

Arrays.sort(nums);

Then:

for (int i = 1; i < nums.length; i++) {
    if (nums[i] == nums[i - 1]) {
        return nums[i];
    }
}

This would work logically.

But LeetCode says:

"You must solve the problem without
modifying the array."

Arrays.sort(nums) modifies the original array.

So sorting is not allowed here.


==================================================
## Why Not Use HashSet?
==================================================

We could store every number in a HashSet.

If a number already exists:

return that number.

But a HashSet requires:

O(N) space.

The problem requires:

O(1) extra space.

Therefore, HashSet is not suitable
for the required solution.


==================================================
## Why Floyd's Algorithm?
==================================================

Floyd's algorithm uses only:

slow
fast

So we don't need:

HashSet
another array
sorting

It satisfies both important constraints:

Array is not modified.

Extra space = O(1).


==================================================
## Time Complexity
==================================================

STEP 1:

O(N)

STEP 2:

O(N)

Overall:

O(N)


==================================================
## Space Complexity
==================================================

We only use two variables:

slow
fast

Therefore:

O(1)


==================================================
## Easy Way to Remember
==================================================

Think:

slow = 1 step

fast = 2 steps


STEP 1:

Make them meet.

STEP 2:

Send slow back to the beginning.

Move both 1 step.

Where they meet again:

That is the duplicate number.


==================================================
*/
