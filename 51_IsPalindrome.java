import java.util.ArrayList;
import java.util.List;

public class PalindromePartitioning {
    
    public static List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<>();
        getAllParts(s, new ArrayList<>(), ans); // Kick off backtracking
        return ans;
    }

    // Checks if a string is a palindrome using two pointers
    private static boolean isPalin(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) return false;
            left++; right--;
        }
        return true;
    }

    // Recursively finds all palindrome partitions
    private static void getAllParts(String s, List<String> partitions, List<List<String>> ans) {
        if (s.length() == 0) {
            ans.add(new ArrayList<>(partitions)); // Base case: store a copy of valid path
            return;
        }

        for (int i = 0; i < s.length(); i++) {
            String part = s.substring(0, i + 1); // Extract prefix substring
            if (isPalin(part)) {
                partitions.add(part); // Choose: add to current path list
                getAllParts(s.substring(i + 1), partitions, ans); // Explore remaining string
                partitions.remove(partitions.size() - 1); // Un-choose: Backtrack by deleting from the back
            }
        }
    }

    public static void main(String[] args) {
        String s = "aab";
        System.out.println(partition(s)); // Output: [[a, a, b], [aa, b]]
    }
}

/*
================================================================================
PART 2: SUMMARY OF THE APPROACH
================================================================================
We use a **Recursion and Backtracking** approach. 
1. We check every possible prefix of the string from left to right.
2. If a prefix is a palindrome, we **add** it to our current path list and recursively solve for the remaining substring.
3. Once we hit the end of the string, we save the current path to our final answer list.
4. When a recursive call finishes, we **backtrack** by removing the last element from our list (deleting from the back) to free up space and test alternative partitions.

================================================================================
PART 3: STEP-BY-STEP ITERATION WITH VISUAL REPRESENTATION (Example: s = "aab")
================================================================================

--- INITIAL CALL: getAllParts("aab", [], ans) ---

Step 1 (i = 0):
- Extract prefix `part = "aab".substring(0, 1)` -> `"a"`
- Is `"a"` a palindrome? YES.
- **ADD** `"a"` to `partitions` from the back.
- Visual state of `partitions`: `["a"]`
- Recurse: `getAllParts("ab", ["a"], ans)`

   --- Inside recursive call: getAllParts("ab", ["a"], ans) ---
   Step 2 (i = 0):
   - Extract prefix `part = "ab".substring(0, 1)` -> `"a"`
   - Is `"a"` a palindrome? YES.
   - **ADD** `"a"` to `partitions` from the back.
   - Visual state of `partitions`: `["a", "a"]`
   - Recurse: `getAllParts("b", ["a", "a"], ans)`

      --- Inside recursive call: getAllParts("b", ["a", "a"], ans) ---
      Step 3 (i = 0):
      - Extract prefix `part = "b".substring(0, 1)` -> `"b"`
      - Is `"b"` a palindrome? YES.
      - **ADD** `"b"` to `partitions` from the back.
      - Visual state of `partitions`: `["a", "a", "b"]`
      - Recurse: `getAllParts("", ["a", "a", "b"], ans)`
      
         --- Inside recursive call: getAllParts("", ...) ---
         - String length is 0 (Base case reached!).
         - Copy current list `["a", "a", "b"]` and add to `ans`.
         - Return back.

      Step 4 (Backtracking / Deletion):
      - We return from base case and execute `partitions.remove(partitions.size() - 1)`.
      - **DELETION VISUAL:** We remove the last element (`"b"`) from the back of the list.
      - Visual state of `partitions` after deletion: `["a", "a"]`
      - Loop for `"b"` ends. Return back.

   Step 5 (Backtracking / Deletion):
   - Back in `getAllParts("ab", ...)`, loop moves to `i = 1`.
   - Extract prefix `part = "ab".substring(0, 2)` -> `"ab"`
   - Is `"ab"` a palindrome? NO (`'a' != 'b'`). Skip.
   - Loop ends. Return back.

   Step 6 (Backtracking / Deletion):
   - Back in initial call, execute `partitions.remove(partitions.size() - 1)`.
   - **DELETION VISUAL:** We remove the last element (`"a"`) from the back of the list.
   - Visual state of `partitions` after deletion: `[]` (Empty list)

Step 7 (i = 1):
- Extract prefix `part = "aab".substring(0, 2)` -> `"aa"`
- Is `"aa"` a palindrome? YES.
- **ADD** `"aa"` to `partitions` from the back.
- Visual state of `partitions`: `["aa"]`
- Recurse: `getAllParts("b", ["aa"], ans)`

   --- Inside recursive call: getAllParts("b", ["aa"], ans) ---
   Step 8 (i = 0):
   - Extract prefix `part = "b".substring(0, 1)` -> `"b"`
   - Is `"b"` a palindrome? YES.
   - **ADD** `"b"` to `partitions` from the back.
   - Visual state of `partitions`: `["aa", "b"]`
   - Recurse: `getAllParts("", ["aa", "b"], ans)`
   
      --- Base case reached ---
      - Copy `["aa", "b"]` and add to `ans`.
      - Return back.

   Step 9 (Backtracking / Deletion):
   - Execute `partitions.remove(partitions.size() - 1)`.
   - **DELETION VISUAL:** Remove `"b"` from the back.
   - Visual state of `partitions`: `["aa"]`
   - Return back.

Step 10 (Backtracking / Deletion):
- Back in initial call, execute `partitions.remove(partitions.size() - 1)`.
- **DELETION VISUAL:** Remove `"aa"` from the back.
- Visual state of `partitions`: `[]`

Step 11 (i = 2):
- Extract prefix `part = "aab".substring(0, 3)` -> `"aab"`
- Is `"aab"` a palindrome? NO. Skip.
- Function finishes and returns final `ans` (`[[a, a, b], [aa, b]]`).

================================================================================
PART 4: TIME & SPACE COMPLEXITY
================================================================================
- **Time Complexity: O(N * 2^N)**
  - Why? There are 2^(N-1) possible ways to partition a string of length N. For 
    every partition path we explore, checking if the substrings are palindromes 
    takes O(N) time. Multiplying the number of paths by the check time gives 
    O(N * 2^N).
- **Space Complexity: O(N)**
  - Why? The maximum height of the recursion stack is N (when every character 
    is a separate partition), and our working list stores at most N elements 
    at any time.
================================================================================
PART 5: MOST PROBABLE INTERVIEW QUESTIONS
================================================================================
1. **Q: Why do we need `new ArrayList<>(partitions)` when adding to `ans`?**
   - **Answer:** Because `partitions` is a shared reference variable that changes during backtracking. If we add it directly without cloning, our final answer list will end up containing empty lists.
2. **Q: Can we optimize the palindrome check to run faster?**
   - **Answer:** Yes, we can use Dynamic Programming (precomputing a 2D boolean table) to check if a substring is a palindrome in $O(1)$ time instead of $O(N)$.
3. **Q: What is the primary role of the `partitions.remove(...)` line?**
   - **Answer:** It acts as the "backtrack" step, deleting the most recently added element from the back of the list so we can clear the path and test different partition lengths.
*/
