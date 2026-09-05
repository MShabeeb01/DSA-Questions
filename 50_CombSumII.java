import java.util.ArrayList; // Import ArrayList class
import java.util.Arrays;    // Import Arrays utility class
import java.util.List;      // Import List interface

class Solution { // LeetCode 40: Combination Sum II

    private void backtrack(List<List<Integer>> result, List<Integer> current, int[] candidates, int target, int start) {
        // Base Case: Valid combination found
        if (target == 0) {
            result.add(new ArrayList<>(current)); // Save deep copy of the valid subset
            return;
        }

        // Iterate over candidates starting from index 'start'
        for (int i = start; i < candidates.length; i++) {
            // 1. Skip duplicate elements at the same decision level
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }

            // 2. Early termination (pruning) if remainder becomes negative
            if (target - candidates[i] < 0) {
                break; // Since array is sorted, subsequent elements will also exceed target
            }

            // 3. Choice: Add element
            current.add(candidates[i]);

            // 4. Recurse: Move to next index (i + 1) because each element is used at most once
            backtrack(result, current, candidates, target - candidates[i], i + 1);

            // 5. Backtrack: Remove last element to explore other choices
            current.remove(current.size() - 1);
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();

        // Sort candidates first to group duplicates together and enable early break pruning
        Arrays.sort(candidates);

        backtrack(result, current, candidates, target, 0);
        return result;
    }

    public static void main(String[] args) { // Driver method for testing
        Solution solver = new Solution();
        int[] candidates = {10, 1, 2, 7, 6, 1, 5};
        int target = 8;

        List<List<Integer>> ans = solver.combinationSum2(candidates, target);

        System.out.println("Unique Combinations for sum " + target + ":");
        System.out.println(ans);
    }
}

/*
==================== SUMMARY ====================

Core Differences vs Combination Sum I:
1. Each element in candidates may only be used ONCE (pass `i + 1` instead of `i`).
2. Input can contain duplicate elements; output must contain NO duplicate combinations.
3. Requires `Arrays.sort(candidates)` upfront:
   - Enables `i > start && candidates[i] == candidates[i - 1]` to skip duplicate branches.
   - Enables `target - candidates[i] < 0` early break pruning.

-------------------------------------------------

Recursion & Backtracking Tree Breakdown

Input: candidates = [1, 1, 2, 5, 6, 7, 10] (sorted), target = 8
Notation: backtrack(target, start, currentList)

                             backtrack(8, 0, [])
                     /         |          \        \
         i=0 (val 1)/   i=1(1)/            \        \
                   v      SKIP (i>start && c[1]==c[0])
          backtrack(7, 1, [1])
          /        |          \
   i=1(1)/   i=2(2)/           \ i=3(5)
        v         v             v
   [1, 1]      [1, 2]         [1, 5]
    ...      (target=5)     (target=2)
                /   \             \
          i=3(5)/    \i=4(6)       \i=4(6) -> target - 6 < 0 (BREAK)
               v      v             v
            target=0  BREAK      target - 2 < 0
          FOUND: [1, 2, 5]

-------------------------------------------------

Dry Run Trace (Skipping Duplicates & Pruning)

Array: [1, 1, 2, 5, 6, 7, 10], Target: 8

-----------------------------------------------------------------------------------------------------------------
Step | Loop (i) | start | candidates[i] | target | Action / Condition Check              | Current List
-----------------------------------------------------------------------------------------------------------------
1    | 0        | 0     | 1             | 8      | Add 1, recurse -> backtrack(7, 1)     | [1]
2    | 1        | 1     | 1             | 7      | Add 1, recurse -> backtrack(6, 2)     | [1, 1]
3    | 2        | 2     | 2             | 6      | Add 2, recurse -> backtrack(4, 3)     | [1, 1, 2]
4    | 3        | 3     | 5             | 4      | 4 - 5 < 0 -> BREAK loop               | [1, 1, 2]
5    | -        | -     | -             | -      | Backtrack -> remove 2                 | [1, 1]
6    | 3        | 2     | 5             | 6      | Add 5, recurse -> backtrack(1, 4)     | [1, 1, 5]
7    | 4        | 4     | 6             | 1      | 1 - 6 < 0 -> BREAK loop               | [1, 1, 5]
8    | -        | -     | -             | -      | Backtrack -> remove 5                 | [1, 1]
9    | ...      | ...   | ...           | ...    | Continues exploration...              | ...
10   | 1        | 0     | 1             | 8      | i > start (1 > 0) && c[1] == c[0]     | SKIP DUPLICATE!
-----------------------------------------------------------------------------------------------------------------

Output:
[[1, 1, 6], [1, 2, 5], [1, 7], [2, 6]]

Complexity:
- Time Complexity : O(2^n * k) in the worst case (all subsets), heavily reduced in practice by duplicate skipping and early pruning.
- Space Complexity: O(k) for the recursion stack and temporary list, where k is the maximum depth of recursion (target or n).

=================================================
*/
