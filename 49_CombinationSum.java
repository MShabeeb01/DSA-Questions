import java.util.ArrayList; // Import ArrayList class
import java.util.List;      // Import List interface

public class Main { // Main class

    // Recursive helper function to find all unique combinations
    public static void getAllCombinations(int[] arr, int idx, int tar, List<List<Integer>> ans, List<Integer> combin) {
        // Base Case 1: Target reached
        if (tar == 0) {
            ans.add(new ArrayList<>(combin)); // Save a copy of the valid combination
            return;
        }

        // Base Case 2: Out of bounds or target exceeded
        if (idx == arr.length || tar < 0) {
            return;
        }

        // --- CHOICE 1: INCLUDE CURRENT ELEMENT (arr[idx]) ---
        combin.add(arr[idx]); // 1. DO: Pick current element
        // Recurse with same index (idx) because elements can be reused indefinitely
        getAllCombinations(arr, idx, tar - arr[idx], ans, combin); // 2. RECURSE
        combin.remove(combin.size() - 1); // 3. UNDO (Backtrack): Remove last element to restore state

        // --- CHOICE 2: EXCLUDE CURRENT ELEMENT ---
        // Move to the next index without reducing the target
        getAllCombinations(arr, idx + 1, tar, ans, combin);
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>(); // Stores all valid combinations
        List<Integer> combin = new ArrayList<>();       // Tracks current combination path
        getAllCombinations(candidates, 0, target, ans, combin);
        return ans;
    }

    public static void main(String[] args) { // Driver function
        int[] candidates = {2, 3, 5};
        int target = 8;

        List<List<Integer>> result = combinationSum(candidates, target);

        System.out.println("Combinations that sum to " + target + ":");
        System.out.println(result);
    }
}

/*
================================================================================
PART 2: SUMMARY OF THE APPROACH
================================================================================
This problem (Combination Sum) is solved using **recursion and backtracking**
with a simple **"Pick / Not Pick" (Include / Exclude)** decision at every step.

Think of it like packing a shopping bag to hit an exact weight target (`tar`),
where you are allowed to put the same item in the bag as many times as you want.

At index `idx` with remaining target `tar`:
1. PICK (`Include`):
   - Add `arr[idx]` to the current list `combin`.
   - Keep `idx` the same (so you can pick `arr[idx]` again) and subtract `arr[idx]`
     from `tar`.
   - Once that branch finishes exploring, UNDO (backtrack) by removing the last
     element (`combin.remove(combin.size() - 1)`) so `combin` goes back to how it
     looked before that pick.
2. NOT PICK (`Exclude`):
   - Do not add `arr[idx]`, leave `tar` unchanged, and move to the next item
     (`idx + 1`).

When does a path stop?
- Success (`tar == 0`): We hit the exact target! Save a new copy of `combin` into `ans`.
- Stop / Dead end (`idx == arr.length` or `tar < 0`): Either we ran out of items or
  went past the target (`tar` became negative). Return immediately.

================================================================================
PART 3: STEP-BY-STEP ITERATION WITH VISUAL REPRESENTATION
================================================================================
Let's trace a small, clear example so you can see every single step and
backtrack clearly:

Input: candidates = [2, 3], target = 5
Expected output: [[2, 3]]

Notation:
- `combin` is a list acting like a stack where we ADD to the BACK and DELETE
  (backtrack) from the BACK (`combin.size() - 1`).
- `[ X ]` inside `combin` shows the exact element deleted from the BACK on that step.

--------------------------------------------------------------------------------
Step 1: Start at root call (idx = 0, tar = 5)
- Action: Testing current state; about to PICK `arr[0] = 2`.
- Added: nothing yet
- Tested: `tar == 5` (not 0), `idx == 0` (in bounds), `tar >= 0`
- Deleted: none
- `combin` now: []

--------------------------------------------------------------------------------
Step 2: PICK 2 -> call (idx = 0, tar = 3)
- Action: Added `2` to back of `combin`, subtracted `2` from `5` (`tar = 3`).
- Added: `2` (at back)
- Tested: `tar == 3` (not 0), `idx == 0`, `tar >= 0` -> try picking `2` again.
- Deleted: none
- `combin` now: [2]

--------------------------------------------------------------------------------
Step 3: PICK 2 -> call (idx = 0, tar = 1)
- Action: Added `2` to back of `combin`, subtracted `2` from `3` (`tar = 1`).
- Added: `2` (at back)
- Tested: `tar == 1` (not 0), `idx == 0`, `tar >= 0` -> try picking `2` again.
- Deleted: none
- `combin` now: [2, 2]

--------------------------------------------------------------------------------
Step 4: PICK 2 -> call (idx = 0, tar = -1)
- Action: Added `2` to back of `combin`, subtracted `2` from `1` (`tar = -1`).
- Added: `2` (at back)
- Tested: `tar < 0` (`-1 < 0`) -> dead end! Return from this call.
- Deleted: none yet (returns to Step 3 right after the recursive call)
- `combin` now: [2, 2, 2]

--------------------------------------------------------------------------------
Step 5: BACKTRACK from Step 4 (undo picking third `2`)
- Action: Remove LAST element (from the BACK of `combin`).
- Added: none
- Tested: restoring state before trying the NOT PICK path at `(idx = 0, tar = 1)`.
- Deleted: `2` from the BACK -> `[2, 2, (2)]` removed.
- `combin` now: [2, 2]

--------------------------------------------------------------------------------
Step 6: NOT PICK 2 -> call (idx = 1, tar = 1)
- Action: Move to `idx = 1` (`arr[1] = 3`) with `tar` still `1`.
- Added: none
- Tested: `tar == 1` (not 0), `idx == 1` (in bounds), `tar >= 0` -> try PICKING `3`.
- Deleted: none
- `combin` now: [2, 2]

--------------------------------------------------------------------------------
Step 7: PICK 3 -> call (idx = 1, tar = -2)
- Action: Added `3` to back of `combin`, subtracted `3` from `1` (`tar = -2`).
- Added: `3` (at back)
- Tested: `tar < 0` (`-2 < 0`) -> dead end! Return to Step 6.
- Deleted: none yet
- `combin` now: [2, 2, 3]

--------------------------------------------------------------------------------
Step 8: BACKTRACK from Step 7 (undo picking `3`)
- Action: Remove LAST element (from the BACK of `combin`).
- Added: none
- Tested: restoring state before trying NOT PICK at `(idx = 1, tar = 1)`.
- Deleted: `3` from the BACK -> `[2, 2, (3)]` removed.
- `combin` now: [2, 2]

--------------------------------------------------------------------------------
Step 9: NOT PICK 3 -> call (idx = 2, tar = 1)
- Action: Move to `idx = 2` with `tar = 1`.
- Added: none
- Tested: `idx == arr.length` (`2 == 2`) -> out of bounds! Return to Step 6, which finishes
  and returns to Step 3.
- Deleted: none
- `combin` now: [2, 2]

--------------------------------------------------------------------------------
Step 10: BACKTRACK from Step 3 (undo picking second `2`)
- Action: Remove LAST element (from the BACK of `combin`).
- Added: none
- Tested: restoring state to `[2]` so Step 3 can run its NOT PICK branch.
- Deleted: `2` from the BACK -> `[2, (2)]` removed.
- `combin` now: [2]

--------------------------------------------------------------------------------
Step 11: NOT PICK 2 -> call (idx = 1, tar = 3)
- Action: From `[2]` with `tar = 3`, move to `idx = 1` (`arr[1] = 3`), `tar` stays `3`.
- Added: none
- Tested: `tar == 3` (not 0), `idx == 1`, `tar >= 0` -> try PICKING `3`.
- Deleted: none
- `combin` now: [2]

--------------------------------------------------------------------------------
Step 12: PICK 3 -> call (idx = 1, tar = 0)
- Action: Added `3` to back of `combin`, subtracted `3` from `3` (`tar = 0`).
- Added: `3` (at back)
- Tested: `tar == 0` -> SUCCESS! Add a copy `[2, 3]` to `ans`, then return to Step 11.
- Deleted: none yet
- `combin` now: [2, 3]

--------------------------------------------------------------------------------
Step 13: BACKTRACK from Step 12 (undo picking `3`)
- Action: Remove LAST element (from the BACK of `combin`).
- Added: none
- Tested: restoring state to `[2]` for Step 11's NOT PICK branch.
- Deleted: `3` from the BACK -> `[2, (3)]` removed.
- `combin` now: [2]

--------------------------------------------------------------------------------
Step 14: NOT PICK 3 -> call (idx = 2, tar = 3)
- Action: Move to `idx = 2` with `tar = 3`.
- Added: none
- Tested: `idx == arr.length` (`2 == 2`) -> return to Step 11, which finishes and
  returns to Step 2.
- Deleted: none
- `combin` now: [2]

--------------------------------------------------------------------------------
Step 15: BACKTRACK from Step 2 (undo picking first `2`)
- Action: Remove LAST element (from the BACK of `combin`).
- Added: none
- Tested: restoring state to `[]` so the root call (Step 1) can run NOT PICK `2`.
- Deleted: `2` from the BACK -> `[(2)]` removed.
- `combin` now: []

--------------------------------------------------------------------------------
Step 16: NOT PICK 2 -> call (idx = 1, tar = 5)
- Action: From `[]` with `tar = 5`, move to `idx = 1` (`arr[1] = 3`), `tar` stays `5`.
- Added: none
- Tested:
  - PICK `3` -> `combin` becomes `[3]`, `tar = 2`:
    - PICK `3` -> `[3, 3]`, `tar = -1` -> dead end -> backtrack back to `[3]`
    - NOT PICK `3` -> `idx = 2`, `tar = 2` -> out of bounds -> backtrack to `[]`
  - NOT PICK `3` -> `idx = 2`, `tar = 5` -> out of bounds -> search complete!
- Deleted: `[3]` removed from back when backtracking out of the `3` branch.
- `combin` now: []

Final Answer (`ans`): [[2, 3]]
(For the `main` method input `[2, 3, 5]` with `target = 8`, the same process finds
`[[2, 2, 2, 2], [2, 3, 3], [3, 5]]`.)

================================================================================
PART 4: TIME & SPACE COMPLEXITY
================================================================================
Time Complexity:
- Roughly exponential — think of it as branching into two choices (Pick or Not Pick)
  repeatedly as `tar` shrinks toward `0`.
- The deepest you can go by picking the smallest number is roughly `target / smallest_number`
  steps, so the number of branches grows like `2` raised to that depth.
- Also, whenever we find a valid combination (`tar == 0`), we copy `combin` into `ans`,
  which takes time proportional to the length of that combination.

Space Complexity:
- Recursion call stack: At most proportional to `target / smallest_number` calls deep
  at any one time (since we finish one path before starting the next).
- Current combination list (`combin`): Also holds at most about `target / smallest_number`
  numbers at once.
- Output list (`ans`): Stores all valid combinations found, which is required for the answer.

================================================================================
PART 5: MOST PROBABLE INTERVIEW QUESTIONS
================================================================================
Q1: Why do we pass `idx` instead of `idx + 1` when we INCLUDE an element, and why
    do we do `combin.remove(combin.size() - 1)` afterward?
A1: "Because the problem lets us reuse the same number as many times as we want,
    staying at `idx` lets us pick `arr[idx]` again on the next step. Once that
    entire branch finishes, we remove the last element we added so `combin` is
    clean and accurate when we explore other choices."

Q2: Why do we write `ans.add(new ArrayList<>(combin))` instead of `ans.add(combin)`?
A2: "`combin` is a single list that gets modified in place—we keep adding to it and
    removing from it as we backtrack. If we added `combin` directly without copying
    it, later removals would wipe out or change the answer we already saved."

Q3: What if the input array has negative numbers or `0`? What would break in this code?
A3: "If there are negative numbers, `tar < 0` would stop a path early even though
    adding a negative number later could bring `tar` back up to `0`. If there is a `0`,
    once `tar` hits `0`, the recursion would keep picking `0` infinitely (`tar - 0`
    stays `0`), causing a stack overflow."
================================================================================
*/
