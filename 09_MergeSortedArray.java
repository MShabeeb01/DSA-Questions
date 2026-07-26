class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {

        int i = m - 1;          // Last valid element of nums1
        int j = n - 1;          // Last element of nums2
        int idx = m + n - 1;    // Last index of nums1

        while (i >= 0 && j >= 0) {

            // Place the larger element at the end of nums1
            if (nums1[i] >= nums2[j]) {
                nums1[idx] = nums1[i];
                i--;
            } else {
                nums1[idx] = nums2[j];
                j--;
            }

            idx--;
        }

        // Copy remaining elements of nums2 (if any)
        while (j >= 0) {
            nums1[idx] = nums2[j];
            idx--;
            j--;
        }
    }
}

/*
==================== SUMMARY ====================

Approach:
1. Since both arrays are already sorted, no need to sort again.
2. Start comparing from the end because nums1 has empty spaces there.
3. Use three pointers:
      i   -> Last valid element of nums1
      j   -> Last element of nums2
      idx -> Last position of nums1
4. Compare nums1[i] and nums2[j].
5. Place the larger element at nums1[idx].
6. Move the corresponding pointer and idx.
7. If nums2 still has elements left, copy them.
8. Return nums1.

-------------------------------------------------

Iteration

Input:

nums1 = [1,2,3,0,0,0]
m = 3

nums2 = [2,5,6]
n = 3

Initial

i = 2 (3)
j = 2 (6)
idx = 5

-------------------------------------------------------------
Step | nums1[i] | nums2[j] | Place | nums1 after placement
-------------------------------------------------------------
1    |     3    |     6    |   6   | [1,2,3,0,0,6]
2    |     3    |     5    |   5   | [1,2,3,0,5,6]
3    |     3    |     2    |   3   | [1,2,3,3,5,6]
4    |     2    |     2    |   2   | [1,2,2,3,5,6]
5    |     1    |     2    |   2   | [1,2,2,3,5,6]

Now j becomes -1.

No elements are left in nums2.

Final Output:

[1,2,2,3,5,6]

-------------------------------------------------

Why don't we copy remaining nums1 elements?

Because they are already in the correct position.

Example:

nums1 = [1,2,3,0,0,0]

If nums2 becomes empty,

nums1 is already correctly placed.

Only nums2 elements may need copying.

-------------------------------------------------

Time Complexity : O(m + n)

Space Complexity : O(1)

=================================================
*/
