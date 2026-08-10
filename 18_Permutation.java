class Solution {
    public void nextPermutation(int[] arr) {

        int n = arr.length;

        // STEP 1: Find the pivot
        // Find the first element from the right
        // which is smaller than the element next to it.
        int pivot = -1;

        for (int i = n - 2; i >= 0; i--) {

            if (arr[i] < arr[i + 1]) {
                pivot = i;
                break;
            }
        }

        // If no pivot is found, the array is in descending order.
        // It is the last permutation.
        // Reverse the entire array to get the first permutation.
        if (pivot == -1) {
            reverse(arr, 0, n - 1);
            return;
        }

        // STEP 2: Find the first element from the right
        // which is greater than the pivot.
        for (int i = n - 1; i > pivot; i--) {

            if (arr[i] > arr[pivot]) {

                // Swap pivot with the greater element
                int temp = arr[i];
                arr[i] = arr[pivot];
                arr[pivot] = temp;

                break;
            }
        }

        // STEP 3: Reverse the elements after the pivot
        reverse(arr, pivot + 1, n - 1);
    }

    // Function to reverse the array from left to right
    public void reverse(int[] arr, int left, int right) {

        while (left <= right) {

            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;

            left++;
            right--;
        }
    }
}

/*

## Code Summary

1. Find the first element from the right which is smaller than the next element.
2. Store its index as pivot.
3. If no pivot is found, reverse the entire array.
4. Find the first element from the right which is greater than arr[pivot].
5. Swap the pivot with that element.
6. Reverse all elements after the pivot.
7. The resulting array is the next permutation.

---

## Iteration

Array

1   2   5   4   3

Target = Next Permutation

## Iteration 1

Find the pivot from right to left.

i = 3

arr[3] = 4
arr[4] = 3

4 < 3 → False

Continue.

## Iteration 2

i = 2

arr[2] = 5
arr[3] = 4

5 < 4 → False

Continue.

## Iteration 3

i = 1

arr[1] = 2
arr[2] = 5

2 < 5 → True

pivot = 1

Array:

1   2   5   4   3
    ↑
  pivot

## Iteration 4

Find an element greater than arr[pivot]
from right to left.

i = 4

arr[4] = 3
arr[pivot] = 2

3 > 2 → True

Swap arr[4] and arr[1].

Before:

1   2   5   4   3

After:

1   3   5   4   2

## Iteration 5

Reverse all elements after the pivot.

Before:

1   3   5   4   2
    ↑   ↑───────↑
  pivot  reverse

Reverse:

5   4   2

After:

2   4   5

Final Array:

1   3   2   4   5

Next Permutation = [1, 3, 2, 4, 5]

---

## Time Complexity

Finding Pivot : O(n)

Finding Greater Element : O(n)

Reversing Suffix : O(n)

Overall : O(n)

---

## Space Complexity

Auxiliary Space : O(1)

*/
