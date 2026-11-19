import java.util.ArrayList; // Import ArrayList class
import java.util.Arrays;    // Import Arrays utility class for sorting
import java.util.List;      // Import List interface

public class Main { // Main class containing the solution and driver code

    // Recursive helper function using backtracking to find unique combinations
    private void backtrack(List<List<Integer>> result, List<Integer> current, int[] candidates, int target, int start) {
        if (target == 0) { // Base case: exact target sum reached
            result.add(new ArrayList<>(current)); // Save a copy of the valid combination
            return;
        }

        for (int i = start; i < candidates.length; i++) { // Try candidate elements starting from 'start'
            if (i > start && candidates[i] == candidates[i - 1]) { // Skip duplicate choices at the same recursion level
                continue;
            }

            if (target - candidates[i] < 0) { // Stop early because array is sorted and remaining numbers are too big
                break;
            }

            current.add(candidates[i]); // 1. DO: Pick current element
            backtrack(result, current, candidates, target - candidates[i], i + 1); // 2. RECURSE: Move to i + 1 (each element used at most once)
            current.remove(current.size() - 1); // 3. UNDO (Backtrack): Remove last element to restore state
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>(); // Stores all unique valid combinations
        Arrays.sort(candidates); // Sort so duplicates sit together and pruning with 'break' works
        backtrack(result, new ArrayList<>(), candidates, target, 0); // Start backtracking from index 0
        return result;
    }

    public static void main(String[] args) { // Driver method with sample test cases
        Main solver = new Main();

        int[] candidates1 = {10, 1, 2, 7, 6, 1, 5};
        int target1 = 8;
        System.out.println("Test 1 Output: " + solver.combinationSum2(candidates1, target1));

        int[] candidates2 = {2, 5, 2, 1, 2};
        int target2 = 5;
        System.out.println("Test 2 Output: " + solver.combinationSum2(candidates2, target2));
    }
}

/*
================================================================================
PART 2: SUMMARY OF THE APPROACH & LINE-BY-LINE EXPLANATION
================================================================================
This problem (Combination Sum II) is solved using **sorting + recursion and
backtracking** with a loop over choices.

Unlike Combination Sum I:
1. Each number in `candidates` can be used **at most once** per combination, so
   when we recurse deeper, we pass `i + 1` instead of `i`.
2. The input array can have **duplicate numbers**, and we want **unique
   combinations** without repeating the same combination twice.

How the approach works:
- First, we **sort** `candidates` in ascending order (`Arrays.sort(candidates)`).
  Sorting does two helpful things:
  a) Equal numbers end up right next to each other, making duplicate skipping easy.
  b) If `target - candidates[i] < 0`, every number after `i` is just as big or
     bigger, so we can `break` out of the loop immediately instead of testing
     the rest.
- At each step, we loop `i` from `start` to `candidates.length - 1`:
  - Skip duplicates at the same level (`if (i > start && candidates[i] == candidates[i - 1]) continue;`).
  - Stop the loop early if `target - candidates[i] < 0` (`break;`).
  - Otherwise:
    1) ADD `candidates[i]` to the back of `current`.
    2) RECURSE with `target - candidates[i]` and next index `i + 1`.
    3) REMOVE the last element from the back of `current` (`current.remove(current.size() - 1)`)
       to undo the choice and try the next candidate.
- Whenever `target == 0`, we found a valid combination! We add `new ArrayList<>(current)`
  to `result` so future changes to `current` do not affect saved answers.

--------------------------------------------------------------------------------
LINE-BY-LINE EXPLANATION
--------------------------------------------------------------------------------

Line: import java.util.ArrayList;
      import java.util.Arrays;
      import java.util.List;
What it does:
  Imports Java's `ArrayList` and `List` to store dynamic combinations and results,
  and `Arrays` so we can call `Arrays.sort(candidates)`.

--------------------------------------------------------------------------------

Line: public class Main {
What it does:
  Declares the main class so the file can be compiled and run directly in Java.

--------------------------------------------------------------------------------

Line: private void backtrack(List<List<Integer>> result, List<Integer> current, int[] candidates, int target, int start) {
What it does:
  Helper method that tries building combinations step by step:
  - `result`     : final list holding all unique valid combinations
  - `current`    : working list holding numbers picked along the current path
  - `candidates` : sorted array of available numbers
  - `target`     : remaining sum still needed to reach `0`
  - `start`      : earliest index in `candidates` allowed in the current loop

--------------------------------------------------------------------------------

Line: if (target == 0) {
          result.add(new ArrayList<>(current));
          return;
      }
What it does:
  Success base case—`target` has dropped to `0`, meaning the numbers currently in
  `current` add up to the desired sum. We save a fresh copy (`new ArrayList<>(current)`)
  into `result` and `return` immediately.

--------------------------------------------------------------------------------

Line: for (int i = start; i < candidates.length; i++) {
What it does:
  Loops through candidate indices starting at `start` so we only consider unused
  elements ahead in the array.

--------------------------------------------------------------------------------

Line: if (i > start && candidates[i] == candidates[i - 1]) {
          continue;
      }
What it does:
  Duplicate-skipping guard at the same decision level.
  - `i > start` means we are already past the first item tried for this slot/level.
  - If `candidates[i] == candidates[i - 1]`, picking `candidates[i]` here would
    create duplicate combinations that were already explored. `continue` skips it.

--------------------------------------------------------------------------------

Line: if (target - candidates[i] < 0) {
          break;
      }
What it does:
  Early stop (pruning). Because `candidates` is sorted, if `candidates[i]` is
  already bigger than `target`, every later element `candidates[i + 1]`, etc. is
  also too big. `break` exits the `for` loop right away.

--------------------------------------------------------------------------------

Line: current.add(candidates[i]);
What it does:
  DO step—appends `candidates[i]` to the BACK of `current`.

--------------------------------------------------------------------------------

Line: backtrack(result, current, candidates, target - candidates[i], i + 1);
What it does:
  RECURSE step—calls `backtrack` with the reduced target `target - candidates[i]`
  and `i + 1` so the same index `i` cannot be reused again in this path.

--------------------------------------------------------------------------------

Line: current.remove(current.size() - 1);
What it does:
  UNDO (backtrack) step—removes the last element from the BACK of `current` after
  the recursive call returns, restoring `current` before trying the next `i`.

--------------------------------------------------------------------------------

Line: public List<List<Integer>> combinationSum2(int[] candidates, int target) {
What it does:
  Main solver method that sets up the result list, sorts `candidates`, starts
  the recursion, and returns `result`.

--------------------------------------------------------------------------------

Line: List<List<Integer>> result = new ArrayList<>();
What it does:
  Creates an empty list of lists to collect all valid combinations.

--------------------------------------------------------------------------------

Line: Arrays.sort(candidates);
What it does:
  Sorts `candidates` in place in ascending order so duplicates are adjacent and
  `break` pruning works safely.

--------------------------------------------------------------------------------

Line: backtrack(result, new ArrayList<>(), candidates, target, 0);
What it does:
  Starts the recursive search with an empty `current` list, full `target`, and
  `start = 0`.

--------------------------------------------------------------------------------

Line: return result;
What it does:
  Returns all unique combinations found once backtracking finishes.

--------------------------------------------------------------------------------

Line: public static void main(String[] args) {
What it does:
  Program entry point where Java starts execution.

--------------------------------------------------------------------------------

Line: Main solver = new Main();
      int[] candidates1 = {10, 1, 2, 7, 6, 1, 5};
      int target1 = 8;
      System.out.println("Test 1 Output: " + solver.combinationSum2(candidates1, target1));
What it does:
  Creates a `Main` instance, runs Test 1 (`candidates = {10, 1, 2, 7, 6, 1, 5}`,
  `target = 8`), and prints the result.

--------------------------------------------------------------------------------

Line: int[] candidates2 = {2, 5, 2, 1, 2};
      int target2 = 5;
      System.out.println("Test 2 Output: " + solver.combinationSum2(candidates2, target2));
What it does:
  Runs a second test case (`candidates = {2, 5, 2, 1, 2}`, `target = 5`) and
  prints the output.

================================================================================
PART 3: STEP-BY-STEP ITERATION WITH VISUAL REPRESENTATION
================================================================================
Let's trace a compact, easy-to-follow example with duplicates and a valid match:

Original input: candidates = [2, 1, 2], target = 3
Step 0 (Sort): candidates becomes [1, 2, 2], target = 3
Expected output (`result`): [[1, 2]]

Notation:
- `current` acts like a stack/list where we ADD to the BACK and DELETE (backtrack)
  from the BACK (`current.size() - 1`).
- When deleting from the BACK, we show `[ ..., (removed_value) ] -> [ ... ]`.

--------------------------------------------------------------------------------
Step 1: Start backtrack(result, current=[], arr=[1, 2, 2], target=3, start=0)
- Added: nothing
- Tested: `target == 3` (not 0). Loop `i` from `0` to `2`:
  - `i = 0` (`arr[0] = 1`): `i > start` is `0 > 0` (false), `3 - 1 = 2 >= 0` (okay).
- Deleted: none
- `current` now: []
- `result` now: []

--------------------------------------------------------------------------------
Step 2: Pick `i = 0` (`1`) -> call backtrack(..., current=[1], target=2, start=1)
- Added: `1` to BACK of `current` (`[]` -> `[1]`)
- Tested: `target == 2` (not 0). In child call, loop `i` from `1` to `2`:
  - `i = 1` (`arr[1] = 2`): `i > start` is `1 > 1` (false), `2 - 2 = 0 >= 0` (okay).
- Deleted: none
- `current` now: [1]
- `result` now: []

--------------------------------------------------------------------------------
Step 3: Pick `i = 1` (`2`) -> call backtrack(..., current=[1, 2], target=0, start=2)
- Added: `2` to BACK of `current` (`[1]` -> `[1, 2]`)
- Tested: `target == 0` -> TRUE! Valid combination found.
  Save copy `[1, 2]` into `result` and `return` to Step 2.
- Deleted: none yet
- `current` now: [1, 2]
- `result` now: [[1, 2]]

--------------------------------------------------------------------------------
Step 4: Backtrack from Step 3 (undo picking `2` at `i = 1`)
- Action: Remove LAST element from the BACK of `current`.
- Added: none
- Tested: restoring state before trying `i = 2` inside the `target = 2, start = 1` call.
- Deleted from BACK:
  Before: [1, 2]
  Pop back:   (2)
  After : [1]
- `current` now: [1]
- `result` now: [[1, 2]]

--------------------------------------------------------------------------------
Step 5: Loop `i = 2` (`arr[2] = 2`) inside `target = 2, start = 1`
- Added: none
- Tested: `i > start` is `2 > 1` (true) AND `arr[2] == arr[1]` (`2 == 2`) -> DUPLICATE!
  `continue` skips `i = 2` so we do not add `[1, 2]` a second time.
  Loop finishes and returns to Step 1.
- Deleted: none
- `current` now: [1]
- `result` now: [[1, 2]]

--------------------------------------------------------------------------------
Step 6: Backtrack from Step 2 (undo picking `1` at `i = 0`)
- Action: Remove LAST element from the BACK of `current`.
- Added: none
- Tested: restoring `current` to `[]` so root loop can move to `i = 1`.
- Deleted from BACK:
  Before: [1]
  Pop back:   (1)
  After : []
- `current` now: []
- `result` now: [[1, 2]]

--------------------------------------------------------------------------------
Step 7: Root loop `i = 1` (`arr[1] = 2`) -> call backtrack(..., current=[2], target=1, start=2)
- Added: `2` to BACK of `current` (`[]` -> `[2]`)
- Tested: `target == 1` (not 0). In child call, loop `i = 2` (`arr[2] = 2`):
  `target - arr[2] = 1 - 2 = -1 < 0` -> `break` exits loop and returns to Step 7.
- Deleted: none yet
- `current` now: [2]
- `result` now: [[1, 2]]

--------------------------------------------------------------------------------
Step 8: Backtrack from Step 7 (undo picking `2` at `i = 1`)
- Action: Remove LAST element from the BACK of `current`.
- Added: none
- Tested: restoring `current` to `[]` before root loop checks `i = 2`.
- Deleted from BACK:
  Before: [2]
  Pop back:   (2)
  After : []
- `current` now: []
- `result` now: [[1, 2]]

--------------------------------------------------------------------------------
Step 9: Root loop `i = 2` (`arr[2] = 2`)
- Added: none
- Tested: `i > start` is `2 > 0` (true) AND `arr[2] == arr[1]` (`2 == 2`) -> DUPLICATE!
  `continue` skips `i = 2`. Root loop finishes and returns `result`.
- Deleted: none
- `current` now: []
- `result` now: [[1, 2]]

Final Answer (`result`): [[1, 2]]

================================================================================
PART 4: TIME & SPACE COMPLEXITY
================================================================================
Big-O Summary (where n = number of candidates, k = average combination length,
c = number of valid combinations):
- Time Complexity : O(2^n * k)  [includes O(n log n) sorting upfront]
- Space Complexity: O(n) auxiliary space [plus O(c * k) for the output list]

Plain-English Breakdown:
- Time Complexity:
  - Worst case is exponential — up to O(2^n) paths/subsets because each of the
    n elements can be included or skipped.
  - Sorting `candidates` at the start takes O(n log n) time, which is smaller
    than the worst-case branching search.
  - Whenever a valid combination is found, copying `current` into `result` takes
    O(k) time (proportional to the length of that combination).
- Space Complexity:
  - Recursion call stack: O(n) at most (since each recursive step advances to
    `i + 1`, depth is at most n).
  - Current combination list (`current`): O(n) at most along a single path.
  - Output list (`result`): O(c * k) to store all c valid combinations of average
    length k, required for the final answer.

================================================================================
PART 5: MOST PROBABLE INTERVIEW QUESTIONS
================================================================================
Q1: Why do we sort `candidates` first, and what do those two `if` checks inside
    the `for` loop do?
A1: "Sorting puts identical numbers right next to each other and lets us stop
    early. The `if (i > start && candidates[i] == candidates[i - 1]) continue;`
    line prevents duplicate combinations from being generated at the same step.
    The `if (target - candidates[i] < 0) break;` line stops the loop early
    because any later number is at least as big and would overshoot `target`."

Q2: Why do we pass `i + 1` to `backtrack` instead of `i` or `start + 1`?
A2: "Each element in `candidates` can only be used once per combination. Passing
    `i + 1` guarantees we only pick from elements strictly after the current `i`,
    so we never reuse the exact same element twice."

Q3: Why do we check `i > start` instead of `i > 0` when skipping duplicates?
A3: "Because `i > 0` would block us from picking a duplicate number on a *deeper*
    level (like `[1, 1]` when `candidates` has two `1`s). Checking `i > start`
    only skips duplicates at the *same* recursion level, so `[1, 1]` is still
    allowed when `start` advances."
================================================================================
*/
