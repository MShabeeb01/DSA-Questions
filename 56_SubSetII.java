import java.util.ArrayList; // Import ArrayList class
import java.util.Arrays;    // Import Arrays utility class
import java.util.List;      // Import List interface

class Solution { // LeetCode 90: Subsets II

    // Recursive helper function to find all unique subsets
    public void getAllSubsets(int[] nums, List<Integer> ans, int i, List<List<Integer>> allSubsets) {
        // Base Case: When index reaches array length, record the current subset
        if (i == nums.length) {
            allSubsets.add(new ArrayList<>(ans)); // Make a deep copy of ans
            return;
        }

        // --- CHOICE 1: INCLUDE CURRENT ELEMENT ---
        ans.add(nums[i]);                                      // 1. DO: Include nums[i]
        getAllSubsets(nums, ans, i + 1, allSubsets);           // 2. RECURSE: Move to next index
        ans.remove(ans.size() - 1);                            // 3. UNDO (Backtrack): Remove nums[i]

        // --- CHOICE 2: EXCLUDE CURRENT ELEMENT & SKIP DUPLICATES ---
        int idx = i + 1;
        while (idx < nums.length && nums[idx] == nums[idx - 1]) {
            idx++; // Skip duplicate adjacent values to prevent duplicate subsets
        }

        getAllSubsets(nums, ans, idx, allSubsets);             // Recurse with next non-duplicate index
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        // Sort the array first so duplicates are placed adjacently
        Arrays.sort(nums);

        List<List<Integer>> allSubsets = new ArrayList<>();
        List<Integer> ans = new ArrayList<>();

        getAllSubsets(nums, ans, 0, allSubsets);
        return allSubsets;
    }

    public static void main(String[] args) { // Driver function
        Solution solver = new Solution();
        int[] nums = {1, 2, 2};

        List<List<Integer>> result = solver.subsetsWithDup(nums);

        System.out.println("Subsets with duplicates handled:");
        System.out.println(result);
    }
}

/*
==================== SUMMARY ====================

Approach (Include / Exclude with Duplicate Skipping):
1. Sort `nums` first using Arrays.sort(nums). This groups identical elements together.
2. At every index `i`:
   a. Include Choice:
      - Add `nums[i]` to `ans`.
      - Recurse for index `i + 1`.
      - Backtrack by removing `nums[i]` via `ans.remove(ans.size() - 1)`.
   b. Exclude Choice:
      - Advance past all elements that have the same value as `nums[i]` using a while loop.
      - Recurse starting at that newly advanced index `idx`.
3. Base Case:
   - When `i == nums.length`, add a copy of `ans` to `allSubsets`.

-------------------------------------------------

Recursion & Backtracking Tree Breakdown

Input: nums = [1, 2, 2] (sorted)
Notation: f(i, ans)

                                      f(0, [])
                          /                              \
            Include nums[0]=1 /                                \ Exclude 1
                            v                                  v
                      f(1, [1])                              f(1, [])
                  /              \                       /              \
    Include 2    /    Exclude 2*  \        Include 2    /    Exclude 2*  \
                v                  v                   v                  v
          f(2, [1, 2])          f(3, [1])          f(2, [2])            f(3, [])
          /          \              |              /        \              |
Include 2/   Exclude 2\             |    Include 2/ Exclude 2\             |
        v              v            |            v            v            |
f(3,[1,2,2])      f(3,[1,2])        |     f(3,[2,2])       f(3,[2])        |
     |                 |            |          |              |            |
 [1, 2, 2]          [1, 2]         [1]       [2, 2]          [2]          []

*Note: In the exclude branch, the while loop skips all matching 2s (from index 1 directly to 3).

-------------------------------------------------

Step-by-Step Recursion Trace

Input: nums = [1, 2, 2]

-----------------------------------------------------------------------------------------------------------------
Step | Call           | i   | ans       | Action / Decision                      | Added to allSubsets
-----------------------------------------------------------------------------------------------------------------
1    | f(0, [])       | 0   | []        | Include nums[0] (1) -> ans = [1]       | -
2    | f(1, [1])      | 1   | [1]       | Include nums[1] (2) -> ans = [1, 2]    | -
3    | f(2, [1, 2])   | 2   | [1, 2]    | Include nums[2] (2) -> ans = [1, 2, 2] | -
4    | f(3, [1, 2, 2])| 3   | [1, 2, 2] | Base Case: i == 3                      | [1, 2, 2]
5    | Backtrack      | 2   | [1, 2]    | Exclude nums[2] -> idx becomes 3       | -
6    | f(3, [1, 2])   | 3   | [1, 2]    | Base Case: i == 3                      | [1, 2]
7    | Backtrack      | 1   | [1]       | Exclude nums[1] -> skip all 2s (idx=3) | -
8    | f(3, [1])      | 3   | [1]       | Base Case: i == 3                      | [1]
9    | Backtrack      | 0   | []        | Exclude nums[0] -> idx becomes 1       | -
10   | f(1, [])       | 1   | []        | Include nums[1] (2) -> ans = [2]       | -
11   | f(2, [2])      | 2   | [2]       | Include nums[2] (2) -> ans = [2, 2]    | -
12   | f(3, [2, 2])   | 3   | [2, 2]    | Base Case: i == 3                      | [2, 2]
13   | Backtrack      | 2   | [2]       | Exclude nums[2] -> idx becomes 3       | -
14   | f(3, [2])      | 3   | [2]       | Base Case: i == 3                      | [2]
15   | Backtrack      | 1   | []        | Exclude nums[1] -> skip all 2s (idx=3) | -
16   | f(3, [])       | 3   | []        | Base Case: i == 3                      | []
-----------------------------------------------------------------------------------------------------------------

Output:
[[1, 2, 2], [1, 2], [1], [2, 2], [2], []]

Complexity:
- Time Complexity : O(n * 2^n) — There are 2^n total subsets in the worst case, and copying each subset takes O(n).
- Space Complexity: O(n) auxiliary space for the recursion call stack and temporary list `ans`.

=================================================
*/
