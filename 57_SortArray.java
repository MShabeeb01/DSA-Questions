class Solution { // LeetCode 912: Sort an Array (Divide & Conquer / Merge Sort)

    // Merge two sorted halves back into nums[low..high]
    private void merge(int[] nums, int low, int mid, int high, int[] temp) {
        int left = low;
        int right = mid + 1;
        int k = low;

        // Compare elements from both halves and pick the smaller
        while (left <= mid && right <= high) {
            if (nums[left] <= nums[right]) {
                temp[k++] = nums[left++];
            } else {
                temp[k++] = nums[right++];
            }
        }

        // Copy remaining elements of left subarray (if any)
        while (left <= mid) {
            temp[k++] = nums[left++];
        }

        // Copy remaining elements of right subarray (if any)
        while (right <= high) {
            temp[k++] = nums[right++];
        }

        // Copy sorted elements back into the original array
        for (int i = low; i <= high; i++) {
            nums[i] = temp[i];
        }
    }

    // Recursive helper to split array and merge upon unwinding (Backtracking the split)
    private void mergeSort(int[] nums, int low, int high, int[] temp) {
        // Base Case: Single element or invalid boundary
        if (low >= high) {
            return;
        }

        int mid = low + (high - low) / 2;

        // 1. RECURSE: Divide the left half
        mergeSort(nums, low, mid, temp);

        // 2. RECURSE: Divide the right half
        mergeSort(nums, mid + 1, high, temp);

        // 3. COMBINE / BACKTRACK: Merge sorted subarrays into original array state
        merge(nums, low, mid, high, temp);
    }

    public int[] sortArray(int[] nums) {
        // Pre-allocate a single auxiliary buffer to avoid repeated O(N) allocations
        int[] temp = new int[nums.length];
        mergeSort(nums, 0, nums.length - 1, temp);
        return nums;
    }

    public static void main(String[] args) { // Driver function
        Solution solver = new Solution();
        int[] nums = {5, 2, 3, 1};

        solver.sortArray(nums);

        System.out.print("Sorted Array: ");
        for (int x : nums) {
            System.out.print(x + " ");
        }
        System.out.println();
    }
}

/*
==================== SUMMARY ====================

Why Merge Sort for LeetCode 912?
- LeetCode 912 strictly requires O(n log n) time without hitting TLE.
- Naive QuickSort hits O(n^2) worst-case on heavily sorted/duplicate test cases.
- Merge Sort guarantees deterministic O(n log n) across best, average, and worst cases.
- To prevent Memory Limit Exceeded (MLE), allocate a single `temp` buffer of size `n` upfront.

-------------------------------------------------

Recursion / Divide-and-Conquer Tree Breakdown

Input: nums = [5, 2, 3, 1]
Notation: ms(low, high)

                                  ms(0, 3) [5, 2, 3, 1]
                                 /                     \
                      ms(0, 1) [5, 2]               ms(2, 3) [3, 1]
                     /              \              /              \
               ms(0, 0) [5]   ms(1, 1) [2]   ms(2, 2) [3]   ms(3, 3) [1]
                     \              /              \              /
                      merge: [2, 5]                 merge: [1, 3]
                                 \                     /
                                  merge: [1, 2, 3, 5]

-------------------------------------------------

Step-by-Step Recursion & Merge Trace

Input: nums = [5, 2, 3, 1]

---------------------------------------------------------------------------------------------------------
Step | Operation       | Range [low, high] | Subarray State    | Action / Result
---------------------------------------------------------------------------------------------------------
1    | Call ms(0, 3)   | [0, 3]            | [5, 2, 3, 1]      | mid = 1, recurse left ms(0, 1)
2    | Call ms(0, 1)   | [0, 1]            | [5, 2]            | mid = 0, recurse left ms(0, 0)
3    | Call ms(0, 0)   | [0, 0]            | [5]               | low >= high -> return (Base Case)
4    | Call ms(1, 1)   | [1, 1]            | [2]               | low >= high -> return (Base Case)
5    | merge(0, 0, 1)  | [0, 1]            | [5] & [2]         | Compare: 2 < 5 -> Merged into [2, 5]
6    | Call ms(2, 3)   | [2, 3]            | [3, 1]            | mid = 2, recurse left ms(2, 2)
7    | Call ms(2, 2)   | [2, 2]            | [3]               | low >= high -> return (Base Case)
8    | Call ms(3, 3)   | [3, 3]            | [1]               | low >= high -> return (Base Case)
9    | merge(2, 2, 3)  | [2, 3]            | [3] & [1]         | Compare: 1 < 3 -> Merged into [1, 3]
10   | merge(0, 1, 3)  | [0, 3]            | [2, 5] & [1, 3]   | Interleave: 1, 2, 3, 5 -> [1, 2, 3, 5]
---------------------------------------------------------------------------------------------------------

Output:
[1, 2, 3, 5]

Complexity:
- Time Complexity : O(n log n) — The recursion tree depth is log2(n), and merging at each depth level takes O(n).
- Space Complexity: O(n) — O(n) for the single reused auxiliary array `temp` + O(log n) call stack frames.

=================================================
*/
