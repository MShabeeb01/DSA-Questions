class Solution {
    public int reversePairs(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        return reversePairsSub(nums, 0, nums.length - 1);
    }

    private int reversePairsSub(int[] nums, int l, int r) {

        // Base case: one element cannot form a pair.
        if (l >= r) {
            return 0;
        }

        // Find the middle index safely.
        int m = l + ((r - l) >> 1);

        // Count reverse pairs in the left and right halves.
        int res = reversePairsSub(nums, l, m)
                + reversePairsSub(nums, m + 1, r);

        int i = l;
        int j = m + 1;
        int k = 0;
        int p = m + 1;

        int[] merge = new int[r - l + 1];

        // Count reverse pairs and merge both halves.
        while (i <= m) {

            // Count elements in the right half that satisfy:
            //
            // nums[i] > 2 * nums[p]
            //
            // Use 2L to prevent integer overflow.
            while (p <= r &&
                    nums[i] > 2L * nums[p]) {
                p++;
            }

            res += p - (m + 1);

            // Normal merge step.
            while (j <= r &&
                    nums[i] >= nums[j]) {
                merge[k++] = nums[j++];
            }

            // Add current left element.
            merge[k++] = nums[i++];
        }

        // Add remaining elements from the right half.
        while (j <= r) {
            merge[k++] = nums[j++];
        }

        // Copy sorted elements back into nums.
        System.arraycopy(
                merge,
                0,
                nums,
                l,
                merge.length
        );

        return res;
    }
}

/*
==================================================
## Code Summary
==================================================

A reverse pair is a pair of indexes (i, j) where:

i < j

AND

nums[i] > 2 * nums[j]


Example:

nums = [1, 3, 2, 3, 1]

Reverse pairs:

(3,1)
(3,1)

Answer:

2


==================================================
## Main Idea
==================================================

We use:

MERGE SORT + TWO POINTERS


Merge Sort divides the array into smaller parts.

While merging two sorted halves, we efficiently
count the reverse pairs between them.


There are 3 types of pairs:

1. Both elements are in the LEFT half.

2. Both elements are in the RIGHT half.

3. First element is in LEFT and second element
   is in RIGHT.


The recursive calls handle:

1 and 2.

The merge step handles:

3.


==================================================
## Why Merge Sort?
==================================================

After dividing the array:

LEFT half  → sorted
RIGHT half → sorted


Because both halves are sorted, we can use
two pointers to count reverse pairs efficiently.

Instead of checking every pair:

O(N²)

we can count them in:

O(N log N)


==================================================
## Important Condition
==================================================

A reverse pair satisfies:

nums[i] > 2 * nums[j]


We use:

2L * nums[p]

instead of:

2 * nums[p]


because nums[p] can be very large.

Using long prevents integer overflow.


==================================================
## Iteration
==================================================

Example:

nums = [1, 3, 2, 3, 1]


The merge sort divides the array:

[1, 3, 2]    [3, 1]


Eventually we compare sorted halves.


--------------------------------------------------
### Example Merge
--------------------------------------------------

Suppose we have:

LEFT:

[2, 3]

RIGHT:

[1, 3]


We need to find:

nums[i] > 2 * nums[p]


Start:

i → 2
p → 1


Check:

2 > 2 × 1

2 > 2

FALSE


So no reverse pair for 2.


Move i to 3.

Check:

3 > 2 × 1

3 > 2

TRUE


Therefore:

3 forms a reverse pair with 1.


Count:

1


So:

res = 1


Then we continue merging the two
sorted arrays.


==================================================
## What Is p?
==================================================

We use:

int p = m + 1;


p points to the RIGHT half.


For every element nums[i] in the LEFT half,
we move p while:

nums[i] > 2 * nums[p]


Because the right half is sorted, once the
condition becomes false, we can stop.


The number of valid elements is:

p - (m + 1)


==================================================
## Why Don't We Reset p?
==================================================

This is an important optimization.


Suppose:

LEFT = [2, 5, 8]

RIGHT = [1, 2, 3]


For a larger left value, the valid range
in the right half can only move forward.


So p never needs to move backwards.


This makes the counting step:

O(N)


instead of:

O(N²)


==================================================
## Merge Step
==================================================

After counting reverse pairs, we still need
to perform a normal merge sort.


We compare:

nums[i]

and:

nums[j]


If:

nums[i] >= nums[j]


we put the right element first.


Otherwise:

we put the left element first.


This keeps the current section sorted.


==================================================
## Why Do We Need Sorting?
==================================================

Suppose:

LEFT:

[1, 3, 5]


RIGHT:

[1, 2, 4]


Because both sides are sorted, once we find
that a right-side element does not satisfy:

nums[i] > 2 * nums[j]


we can move efficiently instead of checking
every possible pair.


That's the main advantage of Merge Sort here.


==================================================
## Overall Process
==================================================

Divide:

        [1,3,2,3,1]
               ↓
       [1,3,2] [3,1]


Sort smaller parts.

Then:

COUNT reverse pairs
        ↓
MERGE sorted halves
        ↓
Return total count


==================================================
## Why System.arraycopy()?
==================================================

After merging:

merge = sorted version of nums[l...r]


We copy it back:

System.arraycopy(
    merge,
    0,
    nums,
    l,
    merge.length
);


This updates the original array so that
the next merge level receives sorted data.


==================================================
## Time Complexity
==================================================

Merge Sort has:

O(log N) levels.


At every level, we process:

O(N) elements.


Therefore:

Time = O(N log N)


==================================================
## Space Complexity
==================================================

Temporary merge array:

O(N)


Recursion stack:

O(log N)


Overall:

O(N)


==================================================
## Easy Way to Remember
==================================================

Reverse Pair:

i < j

AND

nums[i] > 2 * nums[j]


Pattern:

DIVIDE
   ↓
COUNT CROSS PAIRS
   ↓
MERGE
   ↓
RETURN COUNT


Remember:

MERGE SORT + TWO POINTERS

==================================================
*/
