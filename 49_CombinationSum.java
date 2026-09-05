import java.util.ArrayList; // Import ArrayList class
import java.util.List;      // Import List interface

public class Main { // Main class

    // Recursive helper function to find all unique combinations
    public static void getAllCombinations(int[] arr, int idx, int tar, List<List<Integer>> ans, List<Integer> combin) {
        // Base Case 1: Target reached
        if (tar == 0) {
            ans.add(new ArrayList<>(combin)); // Create a deep copy of current combination and store
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
        combin.remove(combin.size() - 1); // 3. UNDO (Backtrack): Remove element to restore state

        // --- CHOICE 2: EXCLUDE CURRENT ELEMENT ---
        // Move to the next index without reducing the target
        getAllCombinations(arr, idx + 1, tar, ans, combin);
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> combin = new ArrayList<>();
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
==================== SUMMARY ====================

Approach: Pick / Not Pick (Inclusion / Exclusion)
1. At each recursion state (idx, tar), make two decisions:
   a. Include (Pick):
      - Add `arr[idx]` to `combin`.
      - Recurse keeping `idx` the same (allows unlimited reuse) and reduce `tar` by `arr[idx]`.
      - Backtrack by removing the last added element: `combin.remove(combin.size() - 1)`.
   b. Exclude (Not Pick):
      - Move directly to `idx + 1` with unchanged `tar`.
2. Base Conditions:
   - Success: `tar == 0` -> Valid combination found. Add a copy of `combin` to `ans`.
   - Failure: `idx == arr.length` OR `tar < 0` -> Invalid path, return immediately.

-------------------------------------------------

Recursion & Backtracking Tree Breakdown

Sample Input:
candidates = [2, 3], target = 5

Notation: f(idx, tar, currentList)

                       f(0, 5, [])
                      /           \
           Include 2 /             \ Exclude 2
                    v               v
             f(0, 3, [2])       f(1, 5, [])
             /          \             \
  Include 2 /   Exclude  \             \ Include 3
           v              v             v
     f(0, 1, [2,2])   f(1, 3, [2])   f(1, 2, [3])
      /           \         |              |
 Include 2     Exclude      | Include 3    | Exclude 3 -> [3] fails (tar > 0)
    v             v         v              v
f(0,-1,[2,2,2]) f(1,1,[2,2]) f(1,0,[2,3])    f(2, 2, [3]) -> idx == len (return)
 (tar < 0)       (idx=2 ret)  == TARGET 0 ==
 [BACKTRACK]     [BACKTRACK]  FOUND: [2, 3]
                              [BACKTRACK]

-------------------------------------------------

Step-by-Step Backtracking Trace

---------------------------------------------------------------------------------------------------------
Step | Action / Call       | idx | tar | combin     | Decision / Result
---------------------------------------------------------------------------------------------------------
1    | Call root           | 0   | 5   | []         | Try including arr[0] (2)
2    | add(2) -> f(0, 3)   | 0   | 3   | [2]        | Try including arr[0] (2)
3    | add(2) -> f(0, 1)   | 0   | 1   | [2, 2]     | Try including arr[0] (2)
4    | add(2) -> f(0, -1)  | 0   | -1  | [2, 2, 2]  | tar < 0 -> Prune / Base Case
5    | remove() [Backtrack]| 0   | 1   | [2, 2]     | State restored to [2, 2]
6    | Exclude -> f(1, 1)  | 1   | 1   | [2, 2]     | Move to idx=1 (element 3)
7    | add(3) -> f(1, -2)  | 1   | -2  | [2, 2, 3]  | tar < 0 -> Prune
8    | remove() [Backtrack]| 1   | 1   | [2, 2]     | State restored
9    | Exclude -> f(2, 1)  | 2   | 1   | [2, 2]     | idx == len -> Return
10   | remove() [Backtrack]| 0   | 3   | [2]        | State restored to [2]
11   | Exclude -> f(1, 3)  | 1   | 3   | [2]        | Move to idx=1 (element 3)
12   | add(3) -> f(1, 0)   | 1   | 0   | [2, 3]     | tar == 0 -> ADD [2, 3] TO ANS!
13   | remove() [Backtrack]| 1   | 3   | [2]        | State restored
14   | Exclude -> f(2, 3)  | 2   | 3   | [2]        | idx == len -> Return
15   | remove() [Backtrack]| 0   | 5   | []         | State restored to []
16   | Exclude -> f(1, 5)  | 1   | 5   | []         | Evaluate rest of combinations...
---------------------------------------------------------------------------------------------------------

Output for target = 8 with [2, 3, 5]:
[[2, 2, 2, 2], [2, 3, 3], [3, 5]]

Complexity:
- Time Complexity : O(2^t * k) where t = (target / min_element) and k is the average length of a combination.
- Space Complexity: O(t) auxiliary stack space for recursion depth + space for output list.

=================================================
*/
