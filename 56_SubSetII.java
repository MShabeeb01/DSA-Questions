import java.util.ArrayList; // Import ArrayList class
import java.util.Arrays;    // Import Arrays utility class
import java.util.List;      // Import List interface

class Solution { // LeetCode 90: Subsets II (For-Loop Backtracking)

    // Helper method using the level-by-level decision tree pattern
    public void backtrack(int index, int[] nums, List<List<Integer>> ans, List<Integer> ds) {
        // Every state reached in this tree is a valid unique subset
        ans.add(new ArrayList<>(ds)); // Deep copy current subset into final answer list

        // Explore choices starting from the current index forward
        for (int i = index; i < nums.length; i++) {
            // Skip duplicates among siblings at the exact same recursive depth
            if (i > index && nums[i] == nums[i - 1]) {
                continue;
            }

            ds.add(nums[i]);              // 1. DO: Choose element
            backtrack(i + 1, nums, ans, ds); // 2. RECURSE: Move to next index
            ds.remove(ds.size() - 1);     // 3. UNDO (Backtrack): Restore state
        }
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        // Sort elements to position duplicates adjacently for duplicate pruning
        Arrays.sort(nums);

        List<List<Integer>> ans = new ArrayList<>();
        backtrack(0, nums, ans, new ArrayList<>());
        return ans;
    }

    public static void main(String[] args) { // Driver method
        Solution solver = new Solution();
        int[] nums = {1, 2, 2};

        List<List<Integer>> result = solver.subsetsWithDup(nums);

        System.out.println("Unique Subsets (Lexicographical Order):");
        System.out.println(result);
    }
}

/*
==================== SUMMARY ====================

Approach (For-Loop Based Backtracking):
1. Sort the input array `nums` upfront so duplicate values are contiguous.
2. At the start of each recursive call, immediately add a clone of `ds` to `ans`.
   - Captures `[]` at root level.
   - Captures every prefix subset as it expands without requiring a terminal leaf check.
3. Iterate from `index` to `nums.length - 1`:
   - Duplicate Guard: `if (i > index && nums[i] == nums[i - 1]) continue;`
     - Allows picking duplicate values across deeper levels (e.g., `[2, 2]`).
     - Prunes duplicate values initiated at the current horizontal level.
   - Append `nums[i]`, recurse with `i + 1`, and pop `nums[i]` on return.

-------------------------------------------------

Recursion & Decision Tree Breakdown

Input: nums = [1, 2, 2] (Sorted)
Notation: backtrack(index, ds)

                                 backtrack(0, [])
                                     Adds: []
                     /                  |                 \
            i=0 (1) /            i=1 (2)|                  \ i=2 (2)
                   v                    v                   SKIP (i > 0 && c[2] == c[1])
         backtrack(1, [1])       backtrack(2, [2])
             Adds: [1]               Adds: [2]
            /        \                   |
     i=1 (2)/         \ i=2 (2)   i=2 (2)|
           v           SKIP              v
    backtrack(2, [1, 2])          backtrack(3, [2, 2])
        Adds: [1, 2]                  Adds: [2, 2]
           |                             |
    i=2 (2)|                           (Loop ends)
           v
   backtrack(3, [1, 2, 2])
       Adds: [1, 2, 2]
           |
        (Loop ends)

-------------------------------------------------

Step-by-Step Execution Trace

Input: nums = [1, 2, 2]

---------------------------------------------------------------------------------------------------------
Step | Call Stack            | Loop (i) | Condition Check             | Action Taken      | Subset Added
---------------------------------------------------------------------------------------------------------
1    | backtrack(0, [])      | -        | Entry                       | Add ds copy       | []
2    | backtrack(0, [])      | i = 0    | i == index (0 == 0)         | Add 1, recurse    | -
3    | backtrack(1, [1])     | -        | Entry                       | Add ds copy       | [1]
4    | backtrack(1, [1])     | i = 1    | i == index (1 == 1)         | Add 2, recurse    | -
5    | backtrack(2, [1, 2])  | -        | Entry                       | Add ds copy       | [1, 2]
6    | backtrack(2, [1, 2])  | i = 2    | i == index (2 == 2)         | Add 2, recurse    | -
7    | backtrack(3, [1, 2, 2)| -        | Entry                       | Add ds copy       | [1, 2, 2]
8    | backtrack(3, [1, 2, 2)| -        | i < 3 false                 | Unwinds stack     | -
9    | backtrack(2, [1, 2])  | -        | Backtrack                   | Remove last (2)   | -
10   | backtrack(1, [1])     | i = 2    | i > index && nums[2]==nums[1]| Skip duplicate    | -
11   | backtrack(1, [1])     | -        | Backtrack                   | Remove last (1)   | -
12   | backtrack(0, [])      | i = 1    | i > index (1 > 0) -> false  | Add 2, recurse    | -
13   | backtrack(2, [2])     | -        | Entry                       | Add ds copy       | [2]
14   | backtrack(2, [2])     | i = 2    | i == index (2 == 2)         | Add 2, recurse    | -
15   | backtrack(3, [2, 2])  | -        | Entry                       | Add ds copy       | [2, 2]
16   | backtrack(3, [2, 2])  | -        | i < 3 false                 | Unwinds stack     | -
17   | backtrack(2, [2])     | -        | Backtrack                   | Remove last (2)   | -
18   | backtrack(0, [])      | i = 2    | i > index && nums[2]==nums[1]| Skip duplicate    | -
---------------------------------------------------------------------------------------------------------

Output:
[[], [1], [1, 2], [1, 2, 2], [2], [2, 2]]

Complexity:
- Time Complexity : O(n * 2^n) — Generates up to 2^n unique subsets, taking O(n) to deep-copy each list into `ans`.
- Space Complexity: O(n) recursion call stack depth + auxiliary list `ds` storage.

=================================================
*/
