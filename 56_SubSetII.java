class Solution {

    // Helper method to explore all unique subsets using backtracking
    public void backtrack(int index, int[] nums, List<List<Integer>> ans, List<Integer> ds) {
        // Every recursive state represents a valid subset, so save a copy now
        ans.add(new ArrayList<>(ds));

        for (int i = index; i < nums.length; i++) {
            // Skip duplicate numbers at the same decision level to avoid duplicate subsets
            if (i > index && nums[i] == nums[i - 1]) {
                continue;
            }

            // Pick the current number
            ds.add(nums[i]);

            // Explore further elements starting from the next index
            backtrack(i + 1, nums, ans, ds);

            // Undo the choice (backtrack) before trying the next number
            ds.remove(ds.size() - 1);
        }
    }

    // Main solver method to sort input and start backtracking
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums); // Group duplicates next to each other
        List<List<Integer>> ans = new ArrayList<>();
        backtrack(0, nums, ans, new ArrayList<>());
        return ans;
    }
}

/*
================================================================================
PART 2: SUMMARY OF THE APPROACH & LINE-BY-LINE EXPLANATION
================================================================================

CORE APPROACH SUMMARY (IN PLAIN ENGLISH):
--------------------------------------------------------------------------------
1. Problem Goal:
   Given an integer array that may contain duplicate numbers (for example, an
   array with repeated values like [1, 2, 2]), return all possible unique
   subsets without any duplicate subsets.

2. Technique Used — Sorting + Backtracking (Pick & Undo):
   - Step 1 (Sort): We sort the array first so all identical numbers sit right
     next to each other.
   - Step 2 (Build & Save): We keep a temporary list (`ds`, short for "data
     structure" or current subset). Every time we enter our recursive function,
     `ds` holds a valid subset, so we immediately save a snapshot of it.
   - Step 3 (Branch & Skip Duplicates): Using a `for` loop, we try adding each
     remaining number one by one. If we see the exact same number again at the
     SAME loop level (`i > index && nums[i] == nums[i - 1]`), we skip it so we
     never build the same subset twice.
   - Step 4 (Backtrack): After exploring everything that starts with `nums[i]`,
     we remove `nums[i]` from the end of `ds` so we can cleanly test the next
     candidate in the loop.


LINE-BY-LINE EXPLANATION:
--------------------------------------------------------------------------------
Line: import java.util.ArrayList;
      import java.util.Arrays;
      import java.util.List;
What it does:
  - Imports the built-in Java tools we need: `List` and `ArrayList` to store our
    subsets, and `Arrays` to sort the input array.

--------------------------------------------------------------------------------
Line: class Solution {
What it does:
  - Defines the main container class used by LeetCode to run the `subsetsWithDup`
    method.

--------------------------------------------------------------------------------
Line: public void backtrack(int index, int[] nums, List<List<Integer>> ans, List<Integer> ds) {
What it does:
  - Declares our recursive helper function that builds subsets step by step.
  - `index`: The starting position in `nums` we are currently allowed to pick from.
  - `nums`: The sorted input array.
  - `ans`: The master list storing all final unique subsets.
  - `ds`: Our temporary working list holding the subset currently being built.

--------------------------------------------------------------------------------
Line: ans.add(new ArrayList<>(ds));
What it does:
  - Saves a brand-new copy of `ds` into `ans`.
  - Why `new ArrayList<>(ds)` instead of `ans.add(ds)`? Because `ds` keeps changing
    as we add and remove numbers. If we didn't make a copy, every entry in `ans`
    would point to the exact same list and end up empty `[]` at the end.

--------------------------------------------------------------------------------
Line: for (int i = index; i < nums.length; i++) {
What it does:
  - Loops through all remaining candidates from `index` to the end of the array.
  - Each iteration decides which number should be placed into the current slot
    of our subset `ds`.

--------------------------------------------------------------------------------
Line: if (i > index && nums[i] == nums[i - 1]) {
          continue;
      }
What it does:
  - The duplicate-prevention check.
  - `i == index` means this is the first time we are picking a number for this
    specific slot in `ds`—so we allow it (this lets us include repeated values
    across deeper levels, such as `[2, 2]`).
  - `i > index` means we already tried a number in this slot, backtracked, and
    moved on to the next number in the loop. If `nums[i] == nums[i - 1]`, it's
    the exact same value we just finished exploring, so `continue` skips it.

--------------------------------------------------------------------------------
Line: ds.add(nums[i]);
What it does:
  - "Make a choice": Appends `nums[i]` to the back of our temporary subset `ds`.

--------------------------------------------------------------------------------
Line: backtrack(i + 1, nums, ans, ds);
What it does:
  - "Explore deeper": Calls `backtrack` again with `i + 1` so the next recursive
    call can only pick numbers that come after index `i` (we never reuse the
    exact same array element twice).

--------------------------------------------------------------------------------
Line: ds.remove(ds.size() - 1);
What it does:
  - "Undo the choice (Backtrack)": Deletes the last element from the back of `ds`
    right after the recursive call finishes.
  - This restores `ds` to its previous state before the `for` loop moves to the
    next candidate.

--------------------------------------------------------------------------------
Line: public List<List<Integer>> subsetsWithDup(int[] nums) {
What it does:
  - The main entry-point method called by LeetCode that takes the input array
    and returns all unique subsets.

--------------------------------------------------------------------------------
Line: Arrays.sort(nums);
What it does:
  - Sorts `nums` in ascending order in-place.
  - Required so our duplicate check (`nums[i] == nums[i - 1]`) can spot adjacent
    duplicates.

--------------------------------------------------------------------------------
Line: List<List<Integer>> ans = new ArrayList<>();
      backtrack(0, nums, ans, new ArrayList<>());
      return ans;
What it does:
  - Creates the empty `ans` list, starts recursion at `index = 0` with an empty
    temporary list `new ArrayList<>()`, and returns `ans` once all branches are
    explored.


================================================================================
PART 3: STEP-BY-STEP ITERATION WITH VISUAL REPRESENTATION
================================================================================

Let's trace a general example with a duplicate value: `nums = [1, 2, 2]`
(already sorted).
Indices:  index 0 -> 1
          index 1 -> 2
          index 2 -> 2

--------------------------------------------------------------------------------
STEP 1: Initial call -> backtrack(index = 0, ds = [])
--------------------------------------------------------------------------------
- Action: Save copy of `ds` into `ans`.
- Current `ds`:  [ ]
- Current `ans`: [ [] ]
- Loop starts at `i = 0` (`nums[0] = 1`):
  - Check duplicate: `i > index` is `0 > 0` (False). Proceed!
  - ADD `1` to the BACK of `ds`:
    Before: [ ]
    After:  [ 1 ]  <-- added 1 at back

--------------------------------------------------------------------------------
STEP 2: Recursive call -> backtrack(index = 1, ds = [1])
--------------------------------------------------------------------------------
- Action: Save copy of `ds` into `ans`.
- Current `ds`:  [ 1 ]
- Current `ans`: [ [], [1] ]
- Loop starts at `i = 1` (`nums[1] = 2`):
  - Check duplicate: `i > index` is `1 > 1` (False). Proceed!
  - ADD `2` (from index 1) to the BACK of `ds`:
    Before: [ 1 ]
    After:  [ 1, 2 ]  <-- added 2 at back

--------------------------------------------------------------------------------
STEP 3: Recursive call -> backtrack(index = 2, ds = [1, 2])
--------------------------------------------------------------------------------
- Action: Save copy of `ds` into `ans`.
- Current `ds`:  [ 1, 2 ]
- Current `ans`: [ [], [1], [1, 2] ]
- Loop starts at `i = 2` (`nums[2] = 2`):
  - Check duplicate: `i > index` is `2 > 2` (False, because `i == index`). Proceed!
  - ADD `2` (from index 2) to the BACK of `ds`:
    Before: [ 1, 2 ]
    After:  [ 1, 2, 2 ]  <-- added 2 at back

--------------------------------------------------------------------------------
STEP 4: Recursive call -> backtrack(index = 3, ds = [1, 2, 2])
--------------------------------------------------------------------------------
- Action: Save copy of `ds` into `ans`.
- Current `ds`:  [ 1, 2, 2 ]
- Current `ans`: [ [], [1], [1, 2], [1, 2, 2] ]
- Loop check: `i = 3 < 3` is False. Loop ends, returns to Step 3!

--------------------------------------------------------------------------------
STEP 5: Back in Step 3 (index = 2) -> BACKTRACKING (DELETION!)
--------------------------------------------------------------------------------
- `backtrack(3, ...)` finished, so we run `ds.remove(ds.size() - 1)`.
- Visual Deletion (removing from the BACK of `ds`):
    Before deletion: [ 1, 2, 2 ]
                             ^
                             |--- remove last element (index 2)
    After deletion:  [ 1, 2 ]
- Loop in Step 3 increments `i` to `3` (`3 < 3` is False). Returns to Step 2!

--------------------------------------------------------------------------------
STEP 6: Back in Step 2 (index = 1) -> BACKTRACKING (DELETION!) & DUPLICATE SKIP
--------------------------------------------------------------------------------
- `backtrack(2, ...)` finished, so we run `ds.remove(ds.size() - 1)`.
- Visual Deletion (removing from the BACK of `ds`):
    Before deletion: [ 1, 2 ]
                          ^
                          |--- remove last element (index 1)
    After deletion:  [ 1 ]
- Loop in Step 2 moves to `i = 2` (`nums[2] = 2`):
  - Check duplicate: `i > index` (`2 > 1` is TRUE) AND `nums[2] == nums[1]` (`2 == 2` is TRUE)!
  - Action: `continue`! Skip `nums[2]` because `[1, 2]` was already explored.
- Loop in Step 2 ends (`i = 3 < 3` False). Returns to Step 1!

--------------------------------------------------------------------------------
STEP 7: Back in Step 1 (index = 0) -> BACKTRACKING (DELETION!) & NEXT BRANCH
--------------------------------------------------------------------------------
- `backtrack(1, ...)` finished, so we run `ds.remove(ds.size() - 1)`.
- Visual Deletion (removing from the BACK of `ds`):
    Before deletion: [ 1 ]
                       ^
                       |--- remove last element (index 0)
    After deletion:  [ ]
- Loop in Step 1 moves to `i = 1` (`nums[1] = 2`):
  - Check duplicate: `i > index` (`1 > 0` TRUE), `nums[1] == nums[0]` (`2 == 1` FALSE). Proceed!
  - ADD `2` (from index 1) to the BACK of `ds`:
    Before: [ ]
    After:  [ 2 ]  <-- added 2 at back

--------------------------------------------------------------------------------
STEP 8: Recursive call -> backtrack(index = 2, ds = [2])
--------------------------------------------------------------------------------
- Action: Save copy of `ds` into `ans`.
- Current `ds`:  [ 2 ]
- Current `ans`: [ [], [1], [1, 2], [1, 2, 2], [2] ]
- Loop starts at `i = 2` (`nums[2] = 2`):
  - Check duplicate: `i > index` is `2 > 2` (False). Proceed!
  - ADD `2` (from index 2) to the BACK of `ds`:
    Before: [ 2 ]
    After:  [ 2, 2 ]  <-- added 2 at back

--------------------------------------------------------------------------------
STEP 9: Recursive call -> backtrack(index = 3, ds = [2, 2])
--------------------------------------------------------------------------------
- Action: Save copy of `ds` into `ans`.
- Current `ds`:  [ 2, 2 ]
- Current `ans`: [ [], [1], [1, 2], [1, 2, 2], [2], [2, 2] ]
- Loop check: `i = 3 < 3` is False. Returns to Step 8!

--------------------------------------------------------------------------------
STEP 10: Unwinding Steps 8 & 1 -> FINAL DELETIONS & DUPLICATE SKIP
--------------------------------------------------------------------------------
- Back in Step 8 (`index = 2`):
  - Visual Deletion (removing from the BACK of `ds`):
      Before deletion: [ 2, 2 ]
                            ^
                            |--- remove last element (index 1)
      After deletion:  [ 2 ]
  - Loop in Step 8 ends (`i = 3`). Returns to Step 1!

- Back in Step 1 (`index = 0`):
  - Visual Deletion (removing from the BACK of `ds`):
      Before deletion: [ 2 ]
                         ^
                         |--- remove last element (index 0)
      After deletion:  [ ]
  - Loop in Step 1 moves to `i = 2` (`nums[2] = 2`):
    - Check duplicate: `i > index` (`2 > 0` TRUE) AND `nums[2] == nums[1]` (`2 == 2` TRUE)!
    - Action: `continue`! Skips `nums[2]` so we don't generate `[2]` or `[2, 2]` again.
  - Loop ends (`i = 3`).

FINAL OUTPUT `ans`:
[ [], [1], [1, 2], [1, 2, 2], [2], [2, 2] ]


================================================================================
PART 4: TIME & SPACE COMPLEXITY
================================================================================

Variables Used:
- `n` = total number of elements in the input array `nums`.

TIME COMPLEXITY: O(n * 2^n)
--------------------------------------------------------------------------------
1. Sorting (`Arrays.sort(nums)`): Takes `O(n log n)` time.
2. Generating Subsets (`2^n`): In the worst case (when every number in `nums` is
   unique), each number has 2 choices (include or exclude), creating up to `2^n`
   subsets.
3. Copying Each Subset (`O(n)`): Every time we execute `new ArrayList<>(ds)`,
   Java copies up to `n` elements from `ds` into a new list.
4. Total Time: `O(n log n) + O(n * 2^n)` -> simplifies to `O(n * 2^n)` because
   `n * 2^n` grows faster than `n log n`.

SPACE COMPLEXITY: O(n) Auxiliary Space  (or O(n * 2^n) including output)
--------------------------------------------------------------------------------
1. Auxiliary Space (Extra memory used while running): `O(n)`
   - Recursion Call Stack: Goes at most `n` levels deep.
   - Temporary List `ds`: Holds at most `n` elements at any time.
   - Together, `O(n) + O(n)` = `O(n)`.
2. Output Space (`ans`): `O(n * 2^n)`
   - Including the final answer list, there are up to `2^n` subsets, each of
     average length up to `n`, requiring `O(n * 2^n)` space.


================================================================================
PART 5: MOST PROBABLE INTERVIEW QUESTIONS
================================================================================

Q1: Why do we check `if (i > index && nums[i] == nums[i - 1])` instead of
    `if (i > 0 && nums[i] == nums[i - 1])`?
--------------------------------------------------------------------------------
Spoken Answer:
"If we checked `i > 0`, we would accidentally skip valid subsets like `[2, 2]`.
When `i == index`, we are picking the first element for a brand-new position in
our subset—meaning an earlier parent call picked a previous `2`, not a sibling
in this loop. Checking `i > index` ensures we only skip duplicates when choosing
between siblings at the exact same recursion level."

Q2: Why do we have to write `ans.add(new ArrayList<>(ds))` instead of simply
    `ans.add(ds)`?
--------------------------------------------------------------------------------
Spoken Answer:
"In Java, lists are passed by reference. We reuse the single `ds` list throughout
the recursion by adding and removing elements. If we added `ds` directly to
`ans`, every entry in `ans` would point to that same list, and once
backtracking finishes and `ds` becomes empty, `ans` would just hold empty lists.
Creating `new ArrayList<>(ds)` saves a copy of `ds` at that moment."

Q3: Could we solve this problem iteratively without recursion, and how would
    that work?
--------------------------------------------------------------------------------
Spoken Answer:
"Yes! We can sort the array first and start with `ans = [[]]`. Normally, for
each number, we append it to all existing subsets in `ans`. If the current
number equals the previous one (`nums[i] == nums[i - 1]`), we only append it to
the subsets newly added in the previous step, avoiding duplicates while keeping
`O(n * 2^n)` time complexity."
*/
