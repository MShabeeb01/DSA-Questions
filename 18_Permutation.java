class Solution {
    public void nextPermutation(int[] nums) {

        int n = nums.length;

        // STEP 1: Find the pivot
        // Find the first element from the right
        // which is smaller than the next element.
        int pivot = -1;

        for (int i = n - 2; i >= 0; i--) {

            if (nums[i] < nums[i + 1]) {
                pivot = i;
                break;
            }
        }

        // If no pivot is found, the array is in descending order.
        // It is the last permutation.
        // Reverse the entire array to get the first permutation.
        if (pivot == -1) {
            reverse(nums, 0, n - 1);
            return;
        }

        // STEP 2: Find the first element from the right
        // which is greater than the pivot.
        for (int i = n - 1; i > pivot; i--) {

            if (nums[i] > nums[pivot]) {

                // Swap pivot and the greater element
                int temp = nums[i];
                nums[i] = nums[pivot];
                nums[pivot] = temp;

                break;
            }
        }

        // STEP 3: Reverse the elements after the pivot
        reverse(nums, pivot + 1, n - 1);
    }

    // Function to reverse the array
    public void reverse(int[] nums, int left, int right) {

        while (left <= right) {

            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;

            left++;
            right--;
        }
    }
}

/*

## Code Summary

1. Start from the right side of the array.
2. Find the first element which is smaller than the element next to it.
3. Store its index as the pivot.
4. If no pivot is found, the array is in descending order.
5. Reverse the entire array and return.
6. Find the first element from the right which is greater than the pivot.
7. Swap the pivot with that element.
8. Reverse all elements after the pivot.
9. The resulting array is the next permutation.

---

## Iteration

Array

1   2   5   4   3

Next Permutation

## Iteration 1

Find the pivot from right to left.

i = 3

nums[3] = 4
nums[4] = 3

4 < 3 → False

Continue.

## Iteration 2

i = 2

nums[2] = 5
nums[3] = 4

5 < 4 → False

Continue.

## Iteration 3

i = 1

nums[1] = 2
nums[2] = 5

2 < 5 → True

pivot = 1

Array:

1   2   5   4   3
    ↑
  pivot

## Iteration 4

Find an element greater than the pivot
from right to left.

i = 4

nums[4] = 3
nums[pivot] = 2

3 > 2 → True

Swap nums[4] and nums[1].

Before:

1   2   5   4   3

After:

1   3   5   4   2

## Iteration 5

Reverse all elements after the pivot.

Before:

1   3   5   4   2
    ↑   └───────┘
  pivot

Reverse:

5   4   2

After:

2   4   5

Final Array:

1   3   2   4   5

Next Permutation = [1, 3, 2, 4, 5]

---

## Special Case

Array

3   2   1

No pivot is found because:

3 < 2 → False
2 < 1 → False

pivot = -1

Therefore, reverse the entire array.

Before:

3   2   1

After:

1   2   3

Next Permutation = [1, 2, 3]

---

## Time Complexity

Finding Pivot       : O(n)

Finding Greater Element : O(n)

Reversing Suffix    : O(n)

Overall              : O(n)

---

## Space Complexity

Auxiliary Space : O(1)

*/
