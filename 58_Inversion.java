import java.util.ArrayList; // Import ArrayList class
import java.util.List;      // Import List interface

class Solution { // Count Inversions Problem (Divide & Conquer / Enhanced Merge Sort)

    // Merge function that counts split inversions while merging two sorted halves
    private static long merge(long[] arr, int st, int mid, int end) {
        List<Long> temp = new ArrayList<>();
        int left = st;
        int right = mid + 1;
        long invCount = 0;

        // Traverse both halves
        while (left <= mid && right <= end) {
            if (arr[left] <= arr[right]) {
                temp.add(arr[left++]);
            } else {
                // Key Inversion Logic: arr[left] > arr[right]
                // Since left half is sorted, all elements from left to mid form inversions with arr[right]
                invCount += (mid - left + 1);
                temp.add(arr[right++]);
            }
        }

        // Copy remaining elements from the left half
        while (left <= mid) {
            temp.add(arr[left++]);
        }

        // Copy remaining elements from the right half
        while (right <= end) {
            temp.add(arr[right++]);
        }

        // Copy sorted elements back into original array
        for (int i = st; i <= end; i++) {
            arr[i] = temp.get(i - st);
        }

        return invCount;
    }

    // Recursive helper to divide array and accumulate inversion counts
    public static long mergeSort(long[] arr, int st, int end) {
        long invCount = 0;

        if (st < end) {
            int mid = st + (end - st) / 2;

            // Inversions in left half
            invCount += mergeSort(arr, st, mid);

            // Inversions in right half
            invCount += mergeSort(arr, mid + 1, end);

            // Inversions across left and right halves (split inversions)
            invCount += merge(arr, st, mid, end);
        }

        return invCount;
    }

    public static long inversionCount(long[] arr, int n) {
        return mergeSort(arr, 0, n - 1);
    }

    public static void main(String[] args) { // Driver function
        long[] arr = {6, 3, 5, 2, 7};
        int n = arr.length;

        long ans = inversionCount(arr, n);

        System.out.println("Total Inversions: " + ans);
    }
}

/*
==================== SUMMARY ====================

Core Definition:
An inversion occurs when `i < j` and `arr[i] > arr[j]`. It measures how far an array is from being sorted.

Divide & Conquer Approach:
1. Divide the array into two halves at `mid`.
2. Total Inversions = (Inversions in Left Half) + (Inversions in Right Half) + (Split Inversions between Left and Right).
3. Counting during Merge:
   - Left half `arr[st..mid]` is sorted.
   - Right half `arr[mid+1..end]` is sorted.
   - When `arr[left] > arr[right]`, then every element remaining in the left subarray from `left` through `mid` is also strictly greater than `arr[right]`.
   - Therefore, increment: `invCount += (mid - left + 1)`.

-------------------------------------------------

Recursion & Inversion Count Tree Breakdown

Input: arr = [6, 3, 5, 2, 7]

                                mergeSort(0, 4) -> Total: 5
                               /               \
                 mergeSort(0, 2) [6, 3, 5]     mergeSort(3, 4) [2, 7]
                  (inv: 1 + 1 = 2)              (inv: 0)
                 /             \               /        \
         mergeSort(0, 1)    mergeSort(2, 2)  ms(3, 3)  ms(4, 4)
           [6, 3] (inv: 1)     [5] (inv: 0)   [2] (0)   [7] (0)
          /      \
      ms(0,0)  ms(1,1)
       [6]      [3]

Split Inversion Merging:
1. Merge [6] & [3] -> 6 > 3 -> count += (0 - 0 + 1) = 1  => [3, 6]
2. Merge [3, 6] & [5] -> 6 > 5 -> count += (1 - 1 + 1) = 1 => [3, 5, 6]
3. Merge [2] & [7] -> no inversion => [2, 7]
4. Merge [3, 5, 6] & [2, 7]:
   - 3 > 2 -> count += (2 - 0 + 1) = 3 (pairs: (3,2), (5,2), (6,2))
   - Result = 2 (from subproblems) + 3 (split) = 5

-------------------------------------------------

Step-by-Step Merge Trace (Final Merge Step)

Left Subarray:  [3, 5, 6] (st = 0, mid = 2)
Right Subarray: [2, 7]    (mid + 1 = 3, end = 4)

---------------------------------------------------------------------------------------------------------
Step | left | right | arr[left] | arr[right] | Comparison | Inversions Added        | Current Temp List
---------------------------------------------------------------------------------------------------------
1    | 0    | 3     | 3         | 2          | 3 > 2      | mid - left + 1 = 2-0+1=3| [2]
2    | 0    | 4     | 3         | 7          | 3 <= 7     | 0                       | [2, 3]
3    | 1    | 4     | 5         | 7          | 5 <= 7     | 0                       | [2, 3, 5]
4    | 2    | 4     | 6         | 7          | 6 <= 7     | 0                       | [2, 3, 5, 6]
5    | -    | 4     | -         | 7          | Copy rest  | 0                       | [2, 3, 5, 6, 7]
---------------------------------------------------------------------------------------------------------

Inversion Pairs for [6, 3, 5, 2, 7]:
(6, 3), (6, 5), (6, 2), (3, 2), (5, 2) -> Total = 5

Complexity:
- Time Complexity : O(n log n) — Identical recurrence to Merge Sort: T(n) = 2T(n/2) + O(n).
- Space Complexity: O(n) — Auxiliary memory for merging subarrays + O(log n) call stack depth.

=================================================
*/
