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
}

/*
================================================================================
PART 2: SUMMARY OF THE APPROACH & LINE-BY-LINE EXPLANATION
================================================================================

SUMMARY OF THE APPROACH
-----------------------
This code uses **Merge Sort**, a classic "divide and conquer" sorting algorithm:

1. **Divide:** Keep splitting the index range into two halves until each piece
   has only 1 element (or 0 elements). A single element is already sorted.
2. **Conquer & Merge:** Recursively sort the left half and right half, then use
   two pointers (`left` and `right`) inside `merge(...)` to walk through both
   sorted halves and pick the smaller front element each time.
3. **Reuse Temp Buffer:** Create one `temp` array of size `n` once in
   `sortArray(...)` and reuse it across all merges instead of allocating new
   arrays repeatedly.


LINE-BY-LINE EXPLANATION
-----------------------

Line: class Solution {
What it does:
  - Declares the `Solution` class that holds the LeetCode `sortArray` method
    and two private helper methods (`merge` and `mergeSort`).

--------------------------------------------------------------------------------

Line: private void merge(int[] nums, int low, int mid, int high, int[] temp) {
What it does:
  - Helper method that takes two adjacent already-sorted sections of `nums` --
    `low` through `mid` (left half) and `mid + 1` through `high` (right half) --
    and merges them into one sorted section from `low` to `high` using `temp`.

--------------------------------------------------------------------------------

Line: int left = low;
      int right = mid + 1;
      int k = low;
What it does:
  - Sets up three index pointers:
    - `left` starts at `low` (start of the left sorted half).
    - `right` starts at `mid + 1` (start of the right sorted half).
    - `k` starts at `low` (next position to write in `temp`).

--------------------------------------------------------------------------------

Line: while (left <= mid && right <= high) {
          if (nums[left] <= nums[right]) {
              temp[k++] = nums[left++];
          } else {
              temp[k++] = nums[right++];
          }
      }
What it does:
  - Loops while both the left half (`left <= mid`) and right half
    (`right <= high`) still have unmerged elements.
  - Compares `nums[left]` with `nums[right]`.
  - If `nums[left] <= nums[right]`, writes `nums[left]` into `temp[k]` and
    moves both `k` and `left` forward by 1.
  - Otherwise, writes `nums[right]` into `temp[k]` and moves both `k` and
    `right` forward by 1.
  - Using `<=` keeps equal values in their original order (making the sort
    stable).

--------------------------------------------------------------------------------

Line: while (left <= mid) {
          temp[k++] = nums[left++];
      }
What it does:
  - Runs when the right half runs out of elements first.
  - Copies all remaining elements from the left half into `temp`.

--------------------------------------------------------------------------------

Line: while (right <= high) {
          temp[k++] = nums[right++];
      }
What it does:
  - Runs when the left half runs out of elements first.
  - Copies all remaining elements from the right half into `temp`.

--------------------------------------------------------------------------------

Line: for (int i = low; i <= high; i++) {
          nums[i] = temp[i];
      }
What it does:
  - Copies the merged, sorted values from `temp[low..high]` back into
    `nums[low..high]` so `nums` is updated with the sorted slice.

--------------------------------------------------------------------------------

Line: private void mergeSort(int[] nums, int low, int high, int[] temp) {
What it does:
  - Recursive helper method that splits index range `low..high` in half, sorts
    both halves, and calls `merge(...)` to combine them.

--------------------------------------------------------------------------------

Line: if (low >= high) {
          return;
      }
What it does:
  - **Base case:** When `low >= high`, the slice has 1 element (`low == high`)
    or 0 elements (`low > high`).
  - Returns immediately to pop the current call frame off the call stack.

--------------------------------------------------------------------------------

Line: int mid = low + (high - low) / 2;
What it does:
  - Calculates the middle index of `low..high` safely without risking integer
    overflow from `(low + high)`.

--------------------------------------------------------------------------------

Line: mergeSort(nums, low, mid, temp);
      mergeSort(nums, mid + 1, high, temp);
      merge(nums, low, mid, high, temp);
What it does:
  - Recursively sorts the left half (`low` to `mid`).
  - Recursively sorts the right half (`mid + 1` to `high`).
  - Merges the two sorted halves into `low..high`.

--------------------------------------------------------------------------------

Line: public int[] sortArray(int[] nums) {
          int[] temp = new int[nums.length];
          mergeSort(nums, 0, nums.length - 1, temp);
          return nums;
      }
What it does:
  - Main LeetCode method: allocates a single `temp` array of size
    `nums.length`, runs `mergeSort` across `0` to `nums.length - 1`, and
    returns the sorted `nums` array.


================================================================================
PART 3: STEP-BY-STEP ITERATION WITH VISUAL REPRESENTATION
================================================================================

General example input:
  nums = [5, 2, 3, 1]   (indices: 0, 1, 2, 3)

Initial state:
  temp = [0, 0, 0, 0]
  Call: sortArray([5, 2, 3, 1]) -> calls mergeSort(nums, low=0, high=3, temp)

Note on how "added / tested / deleted" applies here:
  - Instead of deleting items from a list or stack with a `.remove()` or
    `.pop()` operation, recursion pushes a call frame onto the call stack when a
    call starts and pops/returns from it when finished.
  - During `merge(...)`, pairs are **tested** with `nums[left] <= nums[right]`,
    sorted values are **added (written)** into `temp[k]`, and then copied back
    into `nums[i]` (overwriting the previous unsorted values in that slice).

--------------------------------------------------------------------------------
STEP 1: Outer call mergeSort(nums, low=0, high=3, temp)
--------------------------------------------------------------------------------
- Added / pushed: Call frame `mergeSort(0, 3)` pushed onto call stack.
- Tested: Is `low >= high`? `0 >= 3` -> false.
- Added / computed: `mid = 0 + (3 - 0) / 2 = 1`.
- Action: Recurse into left half `low=0, high=1`.

  Visual state of nums:
    Index:  0    1    2    3
    nums: [ 5,   2,   3,   1 ]
            ^    ^         ^
           low  mid       high

--------------------------------------------------------------------------------
STEP 2: Left call mergeSort(nums, low=0, high=1, temp)
--------------------------------------------------------------------------------
- Added / pushed: Call frame `mergeSort(0, 1)` pushed onto call stack.
- Tested: Is `low >= high`? `0 >= 1` -> false.
- Added / computed: `mid = 0 + (1 - 0) / 2 = 0`.
- Action: Recurse into left half `low=0, high=0`.

  Visual state of nums:
    Index:  0    1    2    3
    nums: [ 5,   2,   3,   1 ]
           low/         high
           mid

--------------------------------------------------------------------------------
STEP 3: Base case mergeSort(nums, low=0, high=0, temp)
--------------------------------------------------------------------------------
- Added / pushed: Call frame `mergeSort(0, 0)` pushed onto call stack.
- Tested: Is `low >= high`? `0 >= 0` -> true.
- Deleted / popped: Call frame `mergeSort(0, 0)` returns and pops off call stack.

  Call stack after pop:
    [ sortArray ] -> [ mergeSort(0, 3) ] -> [ mergeSort(0, 1) ]

  nums after step: [5, 2, 3, 1]

--------------------------------------------------------------------------------
STEP 4: Right side of [0..1] -> mergeSort(nums, low=1, high=1, temp)
--------------------------------------------------------------------------------
- Added / pushed: Call frame `mergeSort(1, 1)` pushed onto call stack.
- Tested: Is `low >= high`? `1 >= 1` -> true.
- Deleted / popped: Call frame `mergeSort(1, 1)` returns and pops off call stack.

  Call stack after pop:
    [ sortArray ] -> [ mergeSort(0, 3) ] -> [ mergeSort(0, 1) ]

  nums after step: [5, 2, 3, 1]

--------------------------------------------------------------------------------
STEP 5: Merge ranges [0..0] and [1..1] -> merge(nums, low=0, mid=0, high=1, temp)
--------------------------------------------------------------------------------
- Setup: `left = 0` (value `5`), `right = 1` (value `2`), `k = 0`.

- Sub-step 5a:
  - Tested: `left <= mid && right <= high` (`0 <= 0 && 1 <= 1`) -> true.
  - Tested: `nums[0] <= nums[1]` -> `5 <= 2` -> false.
  - Added to `temp[0]`: `nums[1]` = `2`. Now `right = 2`, `k = 1`.
  - temp after write: [2, 0, 0, 0]

- Sub-step 5b:
  - Tested: `left <= mid && right <= high` (`0 <= 0 && 2 <= 1`) -> false (right half done).

- Sub-step 5c (leftover left elements):
  - Tested: `left <= mid` (`0 <= 0`) -> true.
  - Added to `temp[1]`: `nums[0]` = `5`. Now `left = 1`, `k = 2`.
  - temp after write: [2, 5, 0, 0]
  - Tested: `left <= mid` (`1 <= 0`) -> false.

- Sub-step 5d (copy `temp[0..1]` back to `nums[0..1]`):
  - Overwrites `nums[0]` with `2` and `nums[1]` with `5`.
  - Deleted / popped: `merge(0, 0, 1)` and `mergeSort(0, 1)` finish and pop off call stack.

  Visual state of nums after Step 5:
    Index:  0    1    2    3
    nums: [ 2,   5,   3,   1 ]
            ^^^^^^
            sorted

--------------------------------------------------------------------------------
STEP 6: Right half of main call -> mergeSort(nums, low=2, high=3, temp)
--------------------------------------------------------------------------------
- Added / pushed: Call frame `mergeSort(2, 3)` pushed onto call stack.
- Tested: Is `low >= high`? `2 >= 3` -> false.
- Added / computed: `mid = 2 + (3 - 2) / 2 = 2`.
- Action: Recurse into `[2..2]`, then `[3..3]`.

  Visual state of nums:
    Index:  0    1    2    3
    nums: [ 2,   5,   3,   1 ]
                      ^    ^
                   low/high
                    mid

--------------------------------------------------------------------------------
STEP 7: Base cases mergeSort(2, 2) and mergeSort(3, 3)
--------------------------------------------------------------------------------
- `mergeSort(2, 2)`: Tested `2 >= 2` -> true -> returns and pops off call stack.
- `mergeSort(3, 3)`: Tested `3 >= 3` -> true -> returns and pops off call stack.
- nums after step: [2, 5, 3, 1]

--------------------------------------------------------------------------------
STEP 8: Merge ranges [2..2] and [3..3] -> merge(nums, low=2, mid=2, high=3, temp)
--------------------------------------------------------------------------------
- Setup: `left = 2` (value `3`), `right = 3` (value `1`), `k = 2`.

- Sub-step 8a:
  - Tested: `nums[2] <= nums[3]` -> `3 <= 1` -> false.
  - Added to `temp[2]`: `nums[3]` = `1`. Now `right = 4`, `k = 3`.
  - temp after write: [2, 5, 1, 0]

- Sub-step 8b (leftover left elements):
  - Tested: `left <= mid` (`2 <= 2`) -> true.
  - Added to `temp[3]`: `nums[2]` = `3`. Now `left = 3`, `k = 4`.
  - temp after write: [2, 5, 1, 3]
  - Tested: `left <= mid` (`3 <= 2`) -> false.

- Sub-step 8c (copy `temp[2..3]` back to `nums[2..3]`):
  - Overwrites `nums[2]` with `1` and `nums[3]` with `3`.
  - Deleted / popped: `merge(2, 2, 3)` and `mergeSort(2, 3)` finish and pop off call stack.

  Visual state of nums after Step 8:
    Index:  0    1    2    3
    nums: [ 2,   5,   1,   3 ]
            ^^^^^^    ^^^^^^
            sorted    sorted

--------------------------------------------------------------------------------
STEP 9: Final merge -> merge(nums, low=0, mid=1, high=3, temp)
--------------------------------------------------------------------------------
- Setup: `left = 0` (`nums[0] = 2`), `right = 2` (`nums[2] = 1`), `k = 0`.

- Sub-step 9a:
  - Tested: `nums[0] <= nums[2]` -> `2 <= 1` -> false.
  - Added to `temp[0]`: `nums[2]` = `1`. Now `right = 3`, `k = 1`.
  - temp after write: [1, 5, 1, 3]

- Sub-step 9b:
  - Tested: `nums[0] <= nums[3]` -> `2 <= 3` -> true.
  - Added to `temp[1]`: `nums[0]` = `2`. Now `left = 1`, `k = 2`.
  - temp after write: [1, 2, 1, 3]

- Sub-step 9c:
  - Tested: `nums[1] <= nums[3]` -> `5 <= 3` -> false.
  - Added to `temp[2]`: `nums[3]` = `3`. Now `right = 4`, `k = 3`.
  - temp after write: [1, 2, 3, 3]
  - Loop `left <= mid && right <= high` (`1 <= 1 && 4 <= 3`) ends because right half
    is exhausted.

- Sub-step 9d (copy remaining left element):
  - Tested: `left <= mid` (`1 <= 1`) -> true.
  - Added to `temp[3]`: `nums[1]` = `5`. Now `left = 2`, `k = 4`.
  - temp after write: [1, 2, 3, 5]
  - Tested: `left <= mid` (`2 <= 1`) -> false.

- Sub-step 9e (copy `temp[0..3]` back to `nums[0..3]`):
  - Overwrites `nums` with `1, 2, 3, 5`.
  - Deleted / popped: `merge(0, 1, 3)` and `mergeSort(0, 3)` finish and pop off
    the call stack, returning `nums` to `sortArray`.

  Final visual state of nums:
    Index:  0    1    2    3
    nums: [ 1,   2,   3,   5 ]
            ^^^^^^^^^^^^^^^^
            fully sorted!


================================================================================
PART 4: TIME & SPACE COMPLEXITY
================================================================================

Variable definition:
  - `n`: Number of elements in `nums` (`nums.length`).

TIME COMPLEXITY: O(n log n)
----------------------------
  - **Why `log n` levels:** Each recursive split divides the range roughly in
    half, taking about `log2(n)` levels to reach single elements.
  - **Why `O(n)` work per level:** At any single recursion level, the sub-ranges
    do not overlap and together cover all `n` elements. Merging them scans and
    copies each element a constant number of times.
  - Combining `O(n)` work per level across `O(log n)` levels gives `O(n log n)`
    in the best, average, and worst cases.

SPACE COMPLEXITY: O(n)
----------------------
  - `temp` is an `int` array of size `n`, using `O(n)` extra space.
  - The recursion call stack holds at most `O(log n)` active frames at once.
  - Adding `O(n) + O(log n)` gives an overall auxiliary space complexity of
    `O(n)`.


================================================================================
PART 5: MOST PROBABLE INTERVIEW QUESTIONS
================================================================================

Q1: Why did you write `int mid = low + (high - low) / 2;` instead of
    `int mid = (low + high) / 2;`?
--------------------------------------------------------------------------------
Spoken Answer:
  "If `low` and `high` are both very large numbers near `Integer.MAX_VALUE`,
  adding `low + high` first can overflow a 32-bit `int` and turn negative.
  Subtracting `low` from `high` first keeps the value small and avoids
  overflow."

Q2: Is this Merge Sort implementation stable, and why?
--------------------------------------------------------------------------------
Spoken Answer:
  "Yes, it is stable. When comparing `nums[left]` and `nums[right]`, we use
  `nums[left] <= nums[right]`, which picks from the left half on ties. Since the
  left half comes before the right half in the original array, equal elements
  stay in their original relative order."

Q3: When would you pick Merge Sort over Quick Sort in practice or an interview?
--------------------------------------------------------------------------------
Spoken Answer:
  "I pick Merge Sort when I need a guaranteed `O(n log n)` worst-case time,
  stability, or when sorting linked lists or huge datasets that do not fit in
  RAM all at once. Quick Sort is often faster in memory on arrays because of
  better cache locality and `O(log n)` stack space, but it can hit `O(n^2)` in
  the worst case if bad pivots are chosen."
 */
