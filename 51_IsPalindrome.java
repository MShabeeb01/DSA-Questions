import java.util.ArrayList; // Import ArrayList class
import java.util.List;      // Import List interface

class Solution { // LeetCode 131: Palindrome Partitioning

    // Helper method to check if a string slice is a palindrome
    public boolean isPalin(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // Recursive helper function to find all valid palindrome partitions
    public void getAllParts(String s, List<String> partitions, List<List<String>> ans) {
        // Base Case: When the remaining string is empty, we found a valid partition
        if (s.length() == 0) {
            ans.add(new ArrayList<>(partitions)); // Save a deep copy of current partition
            return;
        }

        // Try partitioning after every index i
        for (int i = 0; i < s.length(); i++) {
            String part = s.substring(0, i + 1); // Prefix substring from index 0 to i

            // Check if the current prefix is a palindrome
            if (isPalin(part)) {
                partitions.add(part);                        // 1. DO: Pick current prefix
                getAllParts(s.substring(i + 1), partitions, ans); // 2. RECURSE: Solve for remaining suffix
                partitions.remove(partitions.size() - 1);    // 3. UNDO (Backtrack): Remove prefix
            }
        }
    }

    public List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<>();
        List<String> partitions = new ArrayList<>();
        getAllParts(s, partitions, ans);
        return ans;
    }

    public static void main(String[] args) { // Driver function
        Solution solver = new Solution();
        String s = "aab";

        List<List<String>> result = solver.partition(s);

        System.out.println("Palindrome Partitions for \"" + s + "\":");
        System.out.println(result);
    }
}

/*
==================== SUMMARY ====================

Approach:
1. At each recursive call, generate all prefix substrings `part = s.substring(0, i + 1)` for `i` from 0 to length - 1.
2. Check whether `part` is a palindrome using the two-pointer method (`isPalin`).
3. If it IS a palindrome:
   - Add `part` to the current path (`partitions`).
   - Recurse on the remaining substring: `s.substring(i + 1)`.
   - Backtrack by removing `part` from `partitions` so other splits can be explored.
4. Base Condition:
   - If `s.length() == 0`, every cut made so far has yielded a palindrome. Add a clone of `partitions` to `ans`.

-------------------------------------------------

Recursion & Backtracking Tree Breakdown

Input: s = "aab"
Notation: f(remaining_s, currentList)

                                    f("aab", [])
                      /                                  \
        i=0: part="a" /                                    \ i=1: part="aa"  [i=2: part="aab" (not palin)]
                     v                                      v
               f("ab", ["a"])                         f("b", ["aa"])
              /              \                              |
i=0: part="a"/                \ i=1: part="ab"              | i=0: part="b"
            v                  (not palin)                  v
      f("b", ["a", "a"])                              f("", ["aa", "b"])
            |                                           == TARGET 0 ==
i=0: part="b"|                                         FOUND: ["aa", "b"]
            v                                           [BACKTRACK]
      f("", ["a", "a", "b"])
       == TARGET 0 ==
     FOUND: ["a", "a", "b"]
      [BACKTRACK]

-------------------------------------------------

Step-by-Step Backtracking Trace

Input: s = "aab"

-----------------------------------------------------------------------------------------------------------------
Step | Remaining `s` | i | Prefix `part` | isPalin | Action / Recursion State             | Current Partitions
-----------------------------------------------------------------------------------------------------------------
1    | "aab"         | 0 | "a"           | true    | Add "a", call f("ab")               | ["a"]
2    | "ab"          | 0 | "a"           | true    | Add "a", call f("b")                | ["a", "a"]
3    | "b"           | 0 | "b"           | true    | Add "b", call f("")                 | ["a", "a", "b"]
4    | ""            | - | -             | -       | Base case reached -> ADD TO ANS     | ["a", "a", "b"]
5    | "b"           | - | -             | -       | Backtrack -> remove "b"             | ["a", "a"]
6    | "ab"          | 1 | "ab"          | false   | Not a palindrome -> skip            | ["a"]
7    | "ab"          | - | -             | -       | Backtrack -> remove "a"             | []
8    | "aab"         | 1 | "aa"          | true    | Add "aa", call f("b")               | ["aa"]
9    | "b"           | 0 | "b"           | true    | Add "b", call f("")                 | ["aa", "b"]
10   | ""            | - | -             | -       | Base case reached -> ADD TO ANS     | ["aa", "b"]
11   | "b"           | - | -             | -       | Backtrack -> remove "b"             | ["aa"]
12   | "aab"         | 2 | "aab"         | false   | Not a palindrome -> skip            | []
-----------------------------------------------------------------------------------------------------------------

Output:
[["a", "a", "b"], ["aa", "b"]]

Complexity:
- Time Complexity : O(n * 2^n) in the worst case (e.g., all identical characters like "aaaa"), where there are 2^(n-1) potential splits and each substring slice + palindrome check takes O(n).
- Space Complexity: O(n) recursion depth on the call stack, plus auxiliary space for the current partition list.

=================================================
*/
