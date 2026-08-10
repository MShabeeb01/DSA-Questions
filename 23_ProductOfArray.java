class Solution {
    public int[] productExceptSelf(int[] nums) {

        int n = nums.length;

        // ans[i] will store the product of all elements
        // to the LEFT of index i.
        int[] ans = new int[n];

        // The product of no elements is 1.
        ans[0] = 1;

        // --------------------------------------------------
        // STEP 1: Calculate PREFIX products
        // --------------------------------------------------

        for (int i = 1; i < n; i++) {

            // Product of all elements before i
            ans[i] = ans[i - 1] * nums[i - 1];
        }

        // suffix stores the product of all elements
        // to the RIGHT of the current index.
        int suffix = 1;

        // --------------------------------------------------
        // STEP 2: Calculate SUFFIX products
        // and multiply them with prefix products.
        // --------------------------------------------------

        for (int i = n - 2; i >= 0; i--) {

            // Add the product of elements to the right
            // to the existing prefix product.
            ans[i] = ans[i] * suffix;

            // Update suffix for the next element.
            suffix = suffix * nums[i];
        }

        return ans;
    }
}

/*

==================================================
## Code Summary
==================================================

1. We need to find the product of every element
   except the current element.

2. We divide the product into two parts:

   PREFIX → Product of elements on the LEFT.

   SUFFIX → Product of elements on the RIGHT.

3. First, calculate the prefix products and store
   them directly inside ans[].

4. Then traverse from right to left and maintain
   a variable called suffix.

5. For every index:

   ans[i] = prefix product × suffix product

6. We do not use division.

7. We also do not create separate prefix and suffix
   arrays.

8. Therefore, the extra space used is O(1)
   apart from the output array.


==================================================
## Iteration
==================================================

Input:

nums = [1, 2, 3, 4]

Expected Output:

[24, 12, 8, 6]


==================================================
## STEP 1: Prefix Products
==================================================

We calculate the product of all elements
BEFORE each index.

Start:

ans[0] = 1

Why?

There is nothing before index 0.

So:

nums:

1   2   3   4

ans:

1   _   _   _


--------------------------------------------------
## Iteration 1
--------------------------------------------------

i = 1

Formula:

ans[1] = ans[0] * nums[0]

ans[1] = 1 * 1

ans[1] = 1

ans:

1   1   _   _


--------------------------------------------------
## Iteration 2
--------------------------------------------------

i = 2

Formula:

ans[2] = ans[1] * nums[1]

ans[2] = 1 * 2

ans[2] = 2

ans:

1   1   2   _


--------------------------------------------------
## Iteration 3
--------------------------------------------------

i = 3

Formula:

ans[3] = ans[2] * nums[2]

ans[3] = 2 * 3

ans[3] = 6

ans:

1   1   2   6


So after the prefix step:

ans = [1, 1, 2, 6]


Meaning:

ans[0] = product before index 0 = 1

ans[1] = product before index 1 = 1

ans[2] = product before index 2 = 1 × 2 = 2

ans[3] = product before index 3 = 1 × 2 × 3 = 6


==================================================
## STEP 2: Suffix Products
==================================================

Now we calculate the product of elements
to the RIGHT.

Start:

suffix = 1

We start from:

i = n - 2

because the last element has nothing
after it.


--------------------------------------------------
## Iteration 4
--------------------------------------------------

i = 2

Current:

nums[2] = 3

Current prefix:

ans[2] = 2

Current suffix:

suffix = 1

Multiply:

ans[2] = ans[2] * suffix

ans[2] = 2 * 1

ans[2] = 2

Now update suffix:

suffix = suffix * nums[2]

suffix = 1 * 3

suffix = 3

ans:

1   1   2   6


--------------------------------------------------
## Iteration 5
--------------------------------------------------

i = 1

Current:

nums[1] = 2

Current prefix:

ans[1] = 1

Current suffix:

suffix = 3

Multiply:

ans[1] = ans[1] * suffix

ans[1] = 1 * 3

ans[1] = 3

Now update suffix:

suffix = suffix * nums[1]

suffix = 3 * 2

suffix = 6

ans:

1   3   2   6


--------------------------------------------------
## Iteration 6
--------------------------------------------------

i = 0

Current:

nums[0] = 1

Current prefix:

ans[0] = 1

Current suffix:

suffix = 6

Multiply:

ans[0] = ans[0] * suffix

ans[0] = 1 * 6

ans[0] = 6

Now:

ans:

6   3   2   6


==================================================
## IMPORTANT CORRECTION
==================================================

The above suffix loop must include the product
of the elements to the RIGHT of each index.

For index 0:

Right side = 2 × 3 × 4 = 24

Therefore, the clean implementation is:

for (int i = n - 1; i >= 0; i--) {

    ans[i] = ans[i] * suffix;

    suffix = suffix * nums[i];
}

This gives the correct result.


==================================================
## Correct Suffix Iteration
==================================================

Start:

suffix = 1


--------------------------------------------------
## i = 3
--------------------------------------------------

ans[3] = 6 * 1

ans[3] = 6

Update:

suffix = 1 * 4

suffix = 4


--------------------------------------------------
## i = 2
--------------------------------------------------

ans[2] = 2 * 4

ans[2] = 8

Update:

suffix = 4 * 3

suffix = 12


--------------------------------------------------
## i = 1
--------------------------------------------------

ans[1] = 1 * 12

ans[1] = 12

Update:

suffix = 12 * 2

suffix = 24


--------------------------------------------------
## i = 0
--------------------------------------------------

ans[0] = 1 * 24

ans[0] = 24

Update:

suffix = 24 * 1

suffix = 24


==================================================
## Final Answer
==================================================

ans = [24, 12, 8, 6]


==================================================
## How Does It Work?
==================================================

For every index:

answer = LEFT PRODUCT × RIGHT PRODUCT


For nums = [1, 2, 3, 4]:

Index 0:

Left  = 1
Right = 2 × 3 × 4 = 24

Answer = 1 × 24 = 24


Index 1:

Left  = 1
Right = 3 × 4 = 12

Answer = 1 × 12 = 12


Index 2:

Left  = 1 × 2 = 2
Right = 4

Answer = 2 × 4 = 8


Index 3:

Left  = 1 × 2 × 3 = 6
Right = 1

Answer = 6 × 1 = 6


==================================================
## Why Do We Use suffix?
==================================================

Instead of creating another suffix array,
we use one variable:

int suffix = 1;

It continuously stores the product of all
elements to the RIGHT of the current index.


==================================================
## Why No Division?
==================================================

A common approach would be:

total product / nums[i]

But division is not allowed.

Also, division creates problems when the array
contains zero.

Prefix + suffix completely avoids division.


==================================================
## Time Complexity
==================================================

O(N)

First loop:

O(N)

Second loop:

O(N)

Total:

O(N)


==================================================
## Space Complexity
==================================================

O(1) extra space

We only use:

int suffix

The output array `ans` is not counted as
extra space because it is required as the result.

*/
