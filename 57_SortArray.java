import java.util.Arrays;

class Solution {
    private void merge(int[] nums, int low, int mid, int high, int[] temp) {
        int left = low; // Pointer for the start of the left sorted half
        int right = mid + 1; // Pointer for the start of the right sorted half
        int k = low; // Pointer for writing into the temp array

        while (left <= mid && right <= high) { // Compare elements from both halves
            if (nums[left] <= nums[right]) { // Pick smaller (or equal) element from left
                temp[k++] = nums[left++];
            } else { // Otherwise pick smaller element from right
                temp[k++] = nums[right++];
            }
        }

        while (left <= mid) { // Copy any leftover elements from the left half
            temp[k++] = nums[left++];
        }

        while (right <= high) { // Copy any leftover elements from the right half
            temp[k++] = nums[right++];
        }

        for (int i = low; i <= high; i++) { // Copy sorted range back into nums
            nums[i] = temp[i];
        }
    }

    private void mergeSort(int[] nums, int low, int high, int[] temp) {
        if (low >= high) { // Base case: 0 or 1 element is already sorted
            return;
        }

        int mid = low + (high - low) / 2; // Split range safely without integer overflow

        mergeSort(nums, low, mid, temp); // Recursively sort left half
        mergeSort(nums, mid + 1, high, temp); // Recursively sort right half
        merge(nums, low, mid, high, temp); // Merge the two sorted halves
    }

    public int[] sortArray(int[] nums) {
        int[] temp = new int[nums.length]; // Reusable scratchpad array
        mergeSort(nums, 0, nums.length - 1, temp); // Sort entire array in place
        return nums;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] test1 = {5, 2, 3, 1};
        System.out.println("Test 1: " + Arrays.toString(sol.sortArray(test1)));

        int[] test2 = {5, 1, 1, 2, 0, 0};
        System.out.println("Test 2: " + Arrays.toString(sol.sortArray(test2)));

        int[] test3 = {42};
        System.out.println("Test 3: " + Arrays.toString(sol.sortArray(test3)));

        int[] test4 = {};
        System.out.println("Test 4: " + Arrays.toString(sol.sortArray(test4)));
    }
}

/*
================================================================================
PART 2: SUMMARY OF THE APPROACH & LINE-BY-LINE EXPLANATION
================================================================================

SUMMARY OF THE APPROACH
-----------------------
This code implements **Merge Sort**, a classic "divide and conquer" sorting
algorithm:

1. **Divide:** Keep splitting the array into two halves until each piece has only
   1 element (or 0 elements). A single element is naturally sorted.
2. **Conquer & Merge:** Pair up sorted pieces and merge them into a bigger sorted
   piece using two pointers (`left` and `right`) to always pick the smaller front
   element first.
3. **Reuse Temp Buffer:** Instead of allocating brand-new arrays at every step,
   it creates one `temp` array of size `n` up front and reuses it across all
   merges.


LINE-BY-LINE EXPLANATION
-----------------------

Line: import java.util.Arrays;
What it does:
  - Imports Java's `Arrays` utility class so we can easily print arrays with
    `Arrays.toString(...)` inside our `main` test method.

--------------------------------------------------------------------------------

Line: class Solution {
What it does:
  - Declares the `Solution` class that holds our sorting logic and test runner.

--------------------------------------------------------------------------------

Line: private void merge(int[] nums, int low, int mid, int high, int[] temp) {
What it does:
  - Helper method that takes `nums` where `[low..mid]` is already sorted and
    `[mid+1..high]` is already sorted, and merges them into one sorted range
    `[low..high]` using `temp` as a temporary holding buffer.

--------------------------------------------------------------------------------

Line: int left = low;
      int right = mid + 1;
      int k = low;
What it does:
  - Sets up three index pointers:
    * `left` starts at the beginning of the left sorted half (`low`).
    * `right` starts at the beginning of the right sorted half (`mid + 1`).
    * `k` starts at `low` in `temp`, marking the next slot to fill.

--------------------------------------------------------------------------------

Line: while (left <= mid && right <= high) {
          if (nums[left] <= nums[right]) {
              temp[k++] = nums[left++];
          } else {
              temp[k++] = nums[right++];
          }
      }
What it does:
  - Compares the current unmerged element in the left half against the current
    unmerged element in the right half, as long as both halves still have
    elements left.
  - Whichever is smaller (or equal) is copied into `temp[k]`, and both `k` and
    that chosen pointer (`left` or `right`) move forward by 1.
  - Using `<=` keeps equal values in their original relative order (making the
    sort **stable**).

--------------------------------------------------------------------------------

Line: while (left <= mid) {
          temp[k++] = nums[left++];
      }
What it does:
  - If the right half runs out first, copies all remaining elements from the
    left half into `temp`.

--------------------------------------------------------------------------------

Line: while (right <= high) {
          temp[k++] = nums[right++];
      }
What it does:
  - If the left half runs out first, copies all remaining elements from the
    right half into `temp`.

--------------------------------------------------------------------------------

Line: for (int i = low; i <= high; i++) {
          nums[i] = temp[i];
      }
What it does:
  - Copies the freshly sorted values from `temp[low..high]` back into the
    original `nums[low..high]` slice so `nums` stays updated in place.

--------------------------------------------------------------------------------

Line: private void mergeSort(int[] nums, int low, int high, int[] temp) {
What it does:
  - Recursive helper method that splits the index range `[low..high]` in half,
    sorts each half, and then calls `merge(...)` to combine them.

--------------------------------------------------------------------------------

Line: if (low >= high) {
          return;
      }
What it does:
  - **Base case:** If `low` is equal to `high` (1 element) or greater than
    `high` (empty range, such as when `nums` has length 0 and `high` is -1),
    there is nothing left to split, so we immediately return.

--------------------------------------------------------------------------------

Line: int mid = low + (high - low) / 2;
What it does:
  - Finds the midpoint index between `low` and `high`.
  - Writing `low + (high - low) / 2` instead of `(low + high) / 2` prevents
    integer overflow when `low` and `high` are very large numbers.

--------------------------------------------------------------------------------

Line: mergeSort(nums, low, mid, temp);
      mergeSort(nums, mid + 1, high, temp);
      merge(nums, low, mid, high, temp);
What it does:
  - Recursively sorts the left half `[low..mid]`.
  - Recursively sorts the right half `[mid + 1..high]`.
  - Merges the two now-sorted halves into `[low..high]`.

--------------------------------------------------------------------------------

Line: public int[] sortArray(int[] nums) {
          int[] temp = new int[nums.length];
          mergeSort(nums, 0, nums.length - 1, temp);
          return nums;
      }
What it does:
  - Main public entry point: allocates a single `temp` array of size
    `nums.length`, kicks off `mergeSort` on the full range `0` to `nums.length - 1`,
    and returns the sorted `nums` array.

--------------------------------------------------------------------------------

Line: public static void main(String[] args) { ... }
What it does:
  - Runs four sample test cases (unsorted array, array with duplicates, single-
    element array, and empty array) and prints each result to the console.


================================================================================
PART 3: STEP-BY-STEP ITERATION WITH VISUAL REPRESENTATION
================================================================================

Let's trace `sortArray` on:
  nums = [5, 2, 3, 1]   (indices: 0, 1, 2, 3)

Initial setup:
  temp = [0, 0, 0, 0]
  Call: mergeSort(nums, low=0, high=3, temp)

Note on "added / tested / deleted" in this algorithm:
  - We are not deleting elements from a list/stack in a backtracking sense;
    instead, recursion pushes stack frames onto the call stack and pops/returns
    from them when finished, while `merge` tests pairs (`nums[left] <= nums[right]`)
    and overwrites `temp[k]` and `nums[i]` with sorted values.

--------------------------------------------------------------------------------
STEP 1: Outer call mergeSort(nums, low=0, high=3)
--------------------------------------------------------------------------------
  - Tested: Is `low >= high`? `0 >= 3` -> false.
  - Added / computed: `mid = 0 + (3 - 0) / 2 = 1`.
  - Action: Recurse on left half `[0..1]`.

  Visual state of nums:
    Index:   0    1    2    3
    nums:  [ 5,   2,   3,   1 ]
             ^         ^
            low       mid (high=3)

--------------------------------------------------------------------------------
STEP 2: Left call mergeSort(nums, low=0, high=1)
--------------------------------------------------------------------------------
  - Tested: Is `low >= high`? `0 >= 1` -> false.
  - Added / computed: `mid = 0 + (1 - 0) / 2 = 0`.
  - Action: Recurse on left half `[0..0]`.

  Visual state of nums:
    Index:   0    1    2    3
    nums:  [ 5,   2,   3,   1 ]
            low/mid high

--------------------------------------------------------------------------------
STEP 3: Base case mergeSort(nums, low=0, high=0)
--------------------------------------------------------------------------------
  - Tested: Is `low >= high`? `0 >= 0` -> true!
  - Action: Returns immediately (call frame pops off recursion stack).
  - nums remains: [5, 2, 3, 1].

--------------------------------------------------------------------------------
STEP 4: Right side of [0..1] -> mergeSort(nums, low=1, high=1)
--------------------------------------------------------------------------------
  - Tested: Is `low >= high`? `1 >= 1` -> true!
  - Action: Returns immediately (call frame pops off recursion stack).
  - nums remains: [5, 2, 3, 1].

--------------------------------------------------------------------------------
STEP 5: Merge [0..0] and [1..1] -> merge(nums, low=0, mid=0, high=1)
--------------------------------------------------------------------------------
  - Setup: `left = 0`, `right = 1`, `k = 0`. Left sub-array is [5], right is [2].
  - Sub-step 5a:
      - Tested: `left <= mid && right <= high` (`0 <= 0 && 1 <= 1`) -> true.
      - Tested: `nums[0] <= nums[1]` -> `5 <= 2` -> false.
      - Added to `temp[0]`: `nums[1]` which is `2` (`right` becomes 2, `k` becomes 1).
      - temp is now: [2, 0, 0, 0].
  - Sub-step 5b:
      - Tested: `left <= mid && right <= high` (`0 <= 0 && 2 <= 1`) -> false (right finished).
  - Sub-step 5c (leftover left elements):
      - Tested: `left <= mid` (`0 <= 0`) -> true.
      - Added to `temp[1]`: `nums[0]` which is `5` (`left` becomes 1, `k` becomes 2).
      - temp is now: [2, 5, 0, 0].
  - Sub-step 5d (copy back to nums[0..1]):
      - Overwrites `nums[0] = 2`, `nums[1] = 5`.

  Visual state of nums after Step 5:
    Index:   0    1    2    3
    nums:  [ 2,   5,   3,   1 ]
             ^^^^^^
             sorted

--------------------------------------------------------------------------------
STEP 6: Right half of main call -> mergeSort(nums, low=2, high=3)
--------------------------------------------------------------------------------
  - Tested: Is `low >= high`? `2 >= 3` -> false.
  - Added / computed: `mid = 2 + (3 - 2) / 2 = 2`.
  - Action: Recurse on `[2..2]`, then `[3..3]`.

--------------------------------------------------------------------------------
STEP 7: Base cases mergeSort(2, 2) and mergeSort(3, 3)
--------------------------------------------------------------------------------
  - Tested: `2 >= 2` -> true (returns). Then `3 >= 3` -> true (returns).
  - nums remains: [2, 5, 3, 1].

--------------------------------------------------------------------------------
STEP 8: Merge [2..2] and [3..3] -> merge(nums, low=2, mid=2, high=3)
--------------------------------------------------------------------------------
  - Setup: `left = 2`, `right = 3`, `k = 2`. Left sub-array is [3], right is [1].
  - Sub-step 8a:
      - Tested: `nums[2] <= nums[3]` -> `3 <= 1` -> false.
      - Added to `temp[2]`: `nums[3]` which is `1` (`right` becomes 4, `k` becomes 3).
      - temp is now: [2, 5, 1, 0].
  - Sub-step 8b (leftover left elements):
      - Tested: `left <= mid` (`2 <= 2`) -> true.
      - Added to `temp[3]`: `nums[2]` which is `3` (`left` becomes 3, `k` becomes 4).
      - temp is now: [2, 5, 1, 3].
  - Sub-step 8c (copy back to nums[2..3]):
      - Overwrites `nums[2] = 1`, `nums[3] = 3`.

  Visual state of nums after Step 8:
    Index:   0    1    2    3
    nums:  [ 2,   5,   1,   3 ]
             ^^^^^^    ^^^^^^
             sorted    sorted

--------------------------------------------------------------------------------
STEP 9: Final merge -> merge(nums, low=0, mid=1, high=3)
--------------------------------------------------------------------------------
  - Setup: `left = 0` (points to 2), `right = 2` (points to 1), `k = 0`.
  - Sub-step 9a:
      - Tested: `nums[0] <= nums[2]` -> `2 <= 1` -> false.
      - Added to `temp[0]`: `1` (`right` becomes 3, `k` becomes 1).
      - temp: [1, 5, 1, 3].
  - Sub-step 9b:
      - Tested: `nums[0] <= nums[3]` -> `2 <= 3` -> true.
      - Added to `temp[1]`: `2` (`left` becomes 1, `k` becomes 2).
      - temp: [1, 2, 1, 3].
  - Sub-step 9c:
      - Tested: `nums[1] <= nums[3]` -> `5 <= 3` -> false.
      - Added to `temp[2]`: `3` (`right` becomes 4, `k` becomes 3).
      - temp: [1, 2, 3, 3].
  - Sub-step 9d (right half exhausted, copy remaining left element):
      - Tested: `left <= mid` (`1 <= 1`) -> true.
      - Added to `temp[3]`: `5` (`left` becomes 2, `k` becomes 4).
      - temp: [1, 2, 3, 5].
  - Sub-step 9e (copy temp[0..3] back to nums[0..3]):
      - Overwrites `nums` with [1, 2, 3, 5].

  Final visual state of nums:
    Index:   0    1    2    3
    nums:  [ 1,   2,   3,   5 ]
             ^^^^^^^^^^^^^^^^
             fully sorted!


================================================================================
PART 4: TIME & SPACE COMPLEXITY
================================================================================

Let `n` be the number of elements in `nums` (`nums.length`).

TIME COMPLEXITY: O(n log n)
----------------------------
- **Why `log n` levels:** Every call to `mergeSort` cuts the range roughly in
  half, so it takes about `log2(n)` levels of splitting to reach single
  elements.
- **Why `n` work per level:** At any single level of recursion, the active ranges
  are disjoint and cover all `n` elements total. Merging them touches each
  element a constant number of times (`O(n)` per level).
- Multiplying `n` work per level by `log n` levels gives `O(n log n)` in the
  best, average, and worst cases.

SPACE COMPLEXITY: O(n)
----------------------
- `temp` is an `int` array of length `n`, which takes `O(n)` auxiliary space.
- The recursion call stack goes at most `O(log n)` frames deep.
- Combining both (`O(n) + O(log n)`), the overall auxiliary space complexity
  is `O(n)`.


================================================================================
PART 5: MOST PROBABLE INTERVIEW QUESTIONS
================================================================================

Q1: Why did you write `int mid = low + (high - low) / 2;` instead of
    `int mid = (low + high) / 2;`?
--------------------------------------------------------------------------------
Spoken Answer:
  "If `low` and `high` are both close to `Integer.MAX_VALUE`, adding `(low + high)`
  first can overflow a 32-bit `int` and wrap around to a negative number.
  Subtracting `low` from `high` first keeps the intermediate sum small and safe."

Q2: Is this implementation of Merge Sort stable, and why?
--------------------------------------------------------------------------------
Spoken Answer:
  "Yes, it is stable—meaning equal elements keep their original relative order.
  In `merge`, when `nums[left] <= nums[right]`, we pick from the left half
  first. Because `left` holds earlier elements than `right`, ties always favor
  the earlier element."

Q3: When would you prefer Merge Sort over Quick Sort in an interview or
    production system?
--------------------------------------------------------------------------------
Spoken Answer:
  "I'd prefer Merge Sort when I need a guaranteed `O(n log n)` worst-case time
  complexity, stability, or when sorting linked lists or external data that
  doesn't fit in memory. Quick Sort is often faster in practice on arrays due to
  better cache locality and `O(log n)` stack space, but its worst case is `O(n^2)`
  if pivots are poorly chosen."
 */
