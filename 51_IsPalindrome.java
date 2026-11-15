import java.util.ArrayList; // Import ArrayList class
import java.util.List;      // Import List interface

public class Solution { // LeetCode 131: Palindrome Partitioning (DP + Backtracking)

    public List<List<String>> partition(String s) {
        int n = s.length();

        // Phase 1: Precompute whether s[start...end] is a palindrome using DP
        boolean[][] isPalindrome = new boolean[n][n];
        for (int end = 0; end < n; end++) {
            for (int start = 0; start <= end; start++) {
                if (s.charAt(start) == s.charAt(end) && (end - start <= 2 || isPalindrome[start + 1][end - 1])) {
                    isPalindrome[start][end] = true;
                }
            }
        }

        // Phase 2: Backtrack to find all valid partitions using the DP table
        List<List<String>> result = new ArrayList<>();
        backtrack(s, 0, isPalindrome, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(String s, int start, boolean[][] isPalindrome, List<String> currentPath, List<List<String>> result) {
        // Base case: reached the end of the string, save a copy of the current partition
        if (start == s.length()) {
            result.add(new ArrayList<>(currentPath));
            return;
        }

        // Try cutting the string at every possible `end` index
        for (int end = start; end < s.length(); end++) {
            if (isPalindrome[start][end]) {
                currentPath.add(s.substring(start, end + 1));             // 1. DO: Pick valid palindrome slice
                backtrack(s, end + 1, isPalindrome, currentPath, result); // 2. RECURSE: Move start to end + 1
                currentPath.remove(currentPath.size() - 1);               // 3. UNDO: Remove last slice (backtrack)
            }
        }
    }

    public static void main(String[] args) { // Driver function
        Solution solver = new Solution();
        String s = "aab";

        List<List<String>> result = solver.partition(s);

        System.out.println("Palindrome Partitions for \"" + s + "\":");
        System.out.println(result); // Output: [[a, a, b], [aa, b]]
    }
}

/*
================================================================================
PART 2: SIMPLE INTUITION & REAL-WORLD ANALOGY
================================================================================

1. THE "KNIFE & CHEAT SHEET" ANALOGY
--------------------------------------------------------------------------------
Imagine you have the word "aab" and a knife. You need to slice the word into pieces
so that EVERY piece is a palindrome (reads the same forwards and backwards, like "a" or "aa").

- Cut Way 1: "a" | "a" | "b" -> Are "a", "a", and "b" all palindromes? YES! Keep ["a", "a", "b"].
- Cut Way 2: "a" | "ab"      -> "a" is a palindrome, but "ab" is NOT. Stop and go back!
- Cut Way 3: "aa" | "b"      -> Are "aa" and "b" both palindromes? YES! Keep ["aa", "b"].
- Cut Way 4: "aab" (no cut)  -> Is "aab" a palindrome? NO!

2. WHY DO WE USE TWO PHASES (DP + BACKTRACKING)?
--------------------------------------------------------------------------------
- In the basic approach, every time we cut a piece, we run a `while` loop (`left < right`)
  to check if that piece is a palindrome. That repeats the exact same checks over and over.
- Instead, in **Phase 1 (DP)**, we build a 2D "Cheat Sheet" (`isPalindrome[start][end]`) first.
  Then in **Phase 2 (Backtracking)**, we just look at our Cheat Sheet to know instantly in 1 step
  (`O(1)` time) whether a piece is a palindrome!


================================================================================
PART 3: LINE-BY-LINE & FUNCTION-BY-FUNCTION EXPLANATION (PLAIN ENGLISH)
================================================================================

--------------------------------------------------------------------------------
FUNCTION 1: `public List<List<String>> partition(String s)`
--------------------------------------------------------------------------------
This is the main function that sets up our Cheat Sheet and starts the recursion.

* Line: `int n = s.length();`
  - What it does: Stores the length of string `s` in variable `n` (for `"aab"`, `n = 3`).

* Line: `boolean[][] isPalindrome = new boolean[n][n];`
  - What it does: Creates an `n x n` grid of `true`/`false` values (all start as `false`).
  - What `isPalindrome[start][end]` means: It answers the question: "Is the substring starting at
    index `start` and ending at index `end` a palindrome?"
    For example, `isPalindrome[0][1]` tells us if `s[0...1]` (`"aa"`) is a palindrome.

* Line: `for (int end = 0; end < n; end++) {`
* Line: `    for (int start = 0; start <= end; start++) {`
  - What it does: `end` marks the right end of a slice, and `start` marks the left start of a slice.
  - Why `start <= end`? Because a slice cannot start *after* it ends!
  - Why is `end` the outer loop? Because to check a long slice like `start=0, end=3` (`"abba"`),
    we need to check the middle piece `start=1, end=2` (`"bb"`). Having `end` on the outside
    makes sure `end = 2` is already finished before we ever reach `end = 3`!

* Line: `if (s.charAt(start) == s.charAt(end) && (end - start <= 2 || isPalindrome[start + 1][end - 1]))`
  - Let's break this `if` condition into 3 simple pieces:
    1. `s.charAt(start) == s.charAt(end)`:
       The first letter and last letter of the slice MUST be the same. If they don't match
       (like `'a'` and `'b'` in `"ab"`), it can never be a palindrome!
    2. `end - start <= 2`:
       How many letters are in our slice?
       - If `end - start == 0`: 1 letter (e.g., `"a"` at `0..0`). Always a palindrome!
       - If `end - start == 1`: 2 letters (e.g., `"aa"` at `0..1`). Since outer letters match, it's a palindrome!
       - If `end - start == 2`: 3 letters (e.g., `"aba"` at `0..2`). Since first and last letters match,
         the middle letter doesn't even matter—it's automatically a palindrome!
    3. `isPalindrome[start + 1][end - 1]`:
       If the slice is 4 or more letters long (so `end - start > 2`), we already know the outer letters
       match. We just look at our Cheat Sheet to see if the **inside substring** (moving `start` one step
       right to `start + 1`, and `end` one step left to `end - 1`) was also a palindrome!

* Line: `isPalindrome[start][end] = true;`
  - What it does: Marks that slice as `true` on our Cheat Sheet.

* Line: `List<List<String>> result = new ArrayList<>();`
  - What it does: Creates the final photo album (`result`) that will hold all valid answer lists.

* Line: `backtrack(s, 0, isPalindrome, new ArrayList<>(), result);`
  - What it does: Starts cutting the string at index `0`, passing an empty scratchpad list (`new ArrayList<>()`)
    as `currentPath`.


--------------------------------------------------------------------------------
FUNCTION 2: `private void backtrack(String s, int start, boolean[][] isPalindrome, List<String> currentPath, List<List<String>> result)`
--------------------------------------------------------------------------------
This function tries every possible cut starting from index `start`.

* What the parameters mean:
  - `s`: The original string `"aab"`.
  - `start`: Where our next cut must begin (starts at `0`).
  - `isPalindrome`: Our pre-filled Cheat Sheet.
  - `currentPath`: A single list acting like a **Whiteboard** that holds the pieces we've cut so far.
  - `result`: Our **Photo Album** that saves finished partitions.

* Line: `if (start == s.length()) {`
* Line: `    result.add(new ArrayList<>(currentPath));`
* Line: `    return;`
* Line: `}`
  - What it does: If `start` equals the length of the string (`3` for `"aab"`), it means our knife
    has passed the very last character—we successfully cut the whole word into palindromes!
  - Why `new ArrayList<>(currentPath)` instead of `result.add(currentPath)`?
    Because `currentPath` is a single whiteboard that gets erased as we backtrack!
    If we just add `currentPath` directly, `result` only saves a pointer to that whiteboard,
    which ends up empty `[]` at the end. `new ArrayList<>(currentPath)` takes a **fresh snapshot copy**
    of what is currently on the whiteboard.

    WRONG: result.add(currentPath)                 RIGHT: result.add(new ArrayList<>(currentPath))
    +--------+                                     +--------+     +-----------------+
    | result | ---> [ ref1, ref2 ]                 | result | --> | ["a", "a", "b"] | (Snapshot Copy 1)
    +--------+         |     |                     +--------+     +-----------------+
                       v     v                                    | ["aa", "b"]     | (Snapshot Copy 2)
                    +-------------+                               +-----------------+
                    | currentPath | (Ends up []!)
                    +-------------+

* Line: `for (int end = start; end < s.length(); end++) {`
  - What it does: Tries placing the right edge of our cut (`end`) at `start`, `start + 1`, all the way
    to the end of the string.

* Line: `if (isPalindrome[start][end]) {`
  - What it does: Checks our Cheat Sheet. If the slice from `start` to `end` is NOT a palindrome,
    we ignore it completely and try the next `end`.

* Line: `currentPath.add(s.substring(start, end + 1));`  (STEP 1: DO)
  - What it does: Cuts the substring from `start` to `end` and adds it to our `currentPath` list.
  - Why `end + 1`? In Java, `s.substring(start, end + 1)` stops BEFORE the second number. So to include
    the character at index `end`, we must pass `end + 1`.

* Line: `backtrack(s, end + 1, isPalindrome, currentPath, result);`  (STEP 2: RECURSE)
  - What it does: Since we just cut up to index `end`, the remaining uncut string starts at `end + 1`.
    We call `backtrack` again starting at `end + 1`.

* Line: `currentPath.remove(currentPath.size() - 1);`  (STEP 3: UNDO / BACKTRACK)
  - What it does: Once the recursive call finishes exploring everything after `end`, we erase the last
    piece we added to `currentPath` so the `for` loop can try a wider cut (`end + 1`).


================================================================================
PART 4: EVERY SINGLE LOOP ITERATION & RECURSION STEP (VISUAL TRACE)
================================================================================
Input: `s = "aab"` (`n = 3`)
Indices:
  Index:   0     1     2
  Char : [ 'a' | 'a' | 'b' ]

--------------------------------------------------------------------------------
SECTION 4A: EVERY ITERATION OF PHASE 1 (BUILDING THE DP TABLE)
--------------------------------------------------------------------------------
Initially, `isPalindrome[3][3]` is all `false` (`F`):
           end=0  end=1  end=2
  start=0 [  F  ,   F  ,   F  ]
  start=1 [  F  ,   F  ,   F  ]
  start=2 [  F  ,   F  ,   F  ]

=== DP Iteration 1: `end = 0`, `start = 0` ===
  String `s` : [ 'a' ]  'a'    'b'   (Slice s[0..0] = "a")
                  ^
                  *   (^ = start = 0, * = end = 0)
  Check `if` :
    - `s.charAt(0) == s.charAt(0)` -> `'a' == 'a'` (TRUE)
    - `end - start <= 2`           -> `0 - 0 = 0 <= 2` (TRUE)
  Action     : `isPalindrome[0][0] = true`
  Grid Now   :
           end=0  end=1  end=2
  start=0 [  T  ,   F  ,   F  ]
  start=1 [  F  ,   F  ,   F  ]
  start=2 [  F  ,   F  ,   F  ]

=== DP Iteration 2: `end = 1`, `start = 0` ===
  String `s` : [ 'a'    'a' ]  'b'   (Slice s[0..1] = "aa")
                  ^      *   (^ = start = 0, * = end = 1)
  Check `if` :
    - `s.charAt(0) == s.charAt(1)` -> `'a' == 'a'` (TRUE)
    - `end - start <= 2`           -> `1 - 0 = 1 <= 2` (TRUE)
  Action     : `isPalindrome[0][1] = true`
  Grid Now   :
           end=0  end=1  end=2
  start=0 [  T  ,   T  ,   F  ]
  start=1 [  F  ,   F  ,   F  ]
  start=2 [  F  ,   F  ,   F  ]

=== DP Iteration 3: `end = 1`, `start = 1` ===
  String `s` :   'a'  [ 'a' ]  'b'   (Slice s[1..1] = "a")
                         ^
                         *   (^ = start = 1, * = end = 1)
  Check `if` :
    - `s.charAt(1) == s.charAt(1)` -> `'a' == 'a'` (TRUE)
    - `end - start <= 2`           -> `1 - 1 = 0 <= 2` (TRUE)
  Action     : `isPalindrome[1][1] = true`
  Grid Now   :
           end=0  end=1  end=2
  start=0 [  T  ,   T  ,   F  ]
  start=1 [  F  ,   T  ,   F  ]
  start=2 [  F  ,   F  ,   F  ]

=== DP Iteration 4: `end = 2`, `start = 0` ===
  String `s` : [ 'a'    'a'    'b' ] (Slice s[0..2] = "aab")
                  ^             *   (^ = start = 0, * = end = 2)
  Check `if` :
    - `s.charAt(0) == s.charAt(2)` -> `'a' == 'b'` (FALSE!)
  Action     : Skip! `isPalindrome[0][2]` stays `false`.

=== DP Iteration 5: `end = 2`, `start = 1` ===
  String `s` :   'a'  [ 'a'    'b' ] (Slice s[1..2] = "ab")
                         ^      *   (^ = start = 1, * = end = 2)
  Check `if` :
    - `s.charAt(1) == s.charAt(2)` -> `'a' == 'b'` (FALSE!)
  Action     : Skip! `isPalindrome[1][2]` stays `false`.

=== DP Iteration 6: `end = 2`, `start = 2` ===
  String `s` :   'a'    'a'  [ 'b' ] (Slice s[2..2] = "b")
                                ^
                                *   (^ = start = 2, * = end = 2)
  Check `if` :
    - `s.charAt(2) == s.charAt(2)` -> `'b' == 'b'` (TRUE)
    - `end - start <= 2`           -> `2 - 2 = 0 <= 2` (TRUE)
  Action     : `isPalindrome[2][2] = true`
  FINAL DP GRID AFTER PHASE 1:
           end=0  end=1  end=2
  start=0 [  T  ,   T  ,   F  ]   ("a"=T, "aa"=T, "aab"=F)
  start=1 [  F  ,   T  ,   F  ]   (       "a"=T,  "ab"=F )
  start=2 [  F  ,   F  ,   T  ]   (               "b"=T  )


--------------------------------------------------------------------------------
SECTION 4B: FULL RECURSION TREE FOR PHASE 2 (`s = "aab"`)
--------------------------------------------------------------------------------
                                   backtrack(start=0, [])
                       /                     |                       \
    end=0: s[0..0]="a"/                      | end=1: s[0..1]="aa"    \ end=2: s[0..2]="aab"
    (isPalin[0][0]=T)/                       | (isPalin[0][1]=T)       \ (isPalin[0][2]=F)
                    v                        v                          v
          backtrack(1, ["a"])       backtrack(2, ["aa"])             [SKIP!]
            /             \                  |
     end=1 /               \ end=2           | end=2: s[2..2]="b"
  s[1..1]="a"           s[1..2]="ab"         | (isPalin[2][2]=T)
 (isPalin=T)            (isPalin=F)          v
          v                  v        backtrack(3, ["aa", "b"])
backtrack(2, ["a", "a"])  [SKIP!]       == BASE CASE (start==3) ==
          |                             SAVES: ["aa", "b"]
          | end=2: s[2..2]="b"
          | (isPalin[2][2]=T)
          v
backtrack(3, ["a", "a", "b"])
  == BASE CASE (start==3) ==
  SAVES: ["a", "a", "b"]


--------------------------------------------------------------------------------
SECTION 4C: EVERY SINGLE STEP & LOOP ITERATION OF PHASE 2 (BACKTRACKING)
--------------------------------------------------------------------------------

=== STEP 1: Inside `backtrack(start = 0)` | Loop `end = 0` ===
  String `s`  : [ 'a' ]  'a'    'b'   (Slice s.substring(0, 1) = "a")
                   ^
                   *   (^ = start = 0, * = end = 0)
  Check `if`  : `isPalindrome[0][0]` -> `true`
  What happens:
    1. DO     : `currentPath.add("a")`
    2. RECURSE: Call `backtrack(start = 1)`
  Lists Now   :
    `currentPath` = `[ "a" ]`
    `result`      = `[ ]`
    `Call Stack`  = `[ backtrack(0): end=0 ]`

=== STEP 2: Inside `backtrack(start = 1)` | Loop `end = 1` ===
  String `s`  :   'a'  [ 'a' ]  'b'   (Slice s.substring(1, 2) = "a")
                          ^
                          *   (^ = start = 1, * = end = 1)
  Check `if`  : `isPalindrome[1][1]` -> `true`
  What happens:
    1. DO     : `currentPath.add("a")`
    2. RECURSE: Call `backtrack(start = 2)`
  Lists Now   :
    `currentPath` = `[ "a", "a" ]`
    `result`      = `[ ]`
    `Call Stack`  = `[ backtrack(0): end=0 ] -> [ backtrack(1): end=1 ]`

=== STEP 3: Inside `backtrack(start = 2)` | Loop `end = 2` ===
  String `s`  :   'a'    'a'  [ 'b' ] (Slice s.substring(2, 3) = "b")
                                 ^
                                 *   (^ = start = 2, * = end = 2)
  Check `if`  : `isPalindrome[2][2]` -> `true`
  What happens:
    1. DO     : `currentPath.add("b")`
    2. RECURSE: Call `backtrack(start = 3)`
  Lists Now   :
    `currentPath` = `[ "a", "a", "b" ]`
    `result`      = `[ ]`
    `Call Stack`  = `[ backtrack(0): end=0 ] -> [ backtrack(1): end=1 ] -> [ backtrack(2): end=2 ]`

=== STEP 4: Inside `backtrack(start = 3)` | *** BASE CASE HIT! *** ===
  String `s`  :   'a'    'a'    'b'  | (start = 3 equals s.length() = 3)
                                     ^
  Check `if`  : `start == s.length()` -> `3 == 3` (TRUE!)
  What happens:
    - Copy `currentPath` into `result`: `result.add(new ArrayList<>(currentPath))`
    - `return` back to `backtrack(start = 2)`
  Lists Now   :
    `currentPath` = `[ "a", "a", "b" ]`  ---(SNAPSHOT COPIED)---+
    `result`      = `[ ["a", "a", "b"] ]` <---------------------+

=== STEP 5: Back in `backtrack(start = 2)` | UNDO after `end = 2` ===
  What happens:
    3. UNDO   : `currentPath.remove(2)` -> Removes `"b"`.
    - Loop tries `end = 3`, which stops because `end < 3` is false.
    - Returns back to `backtrack(start = 1)`.
  Lists Now   :
    `currentPath` = `[ "a", "a" ]`
    `result`      = `[ ["a", "a", "b"] ]`
    `Call Stack`  = `[ backtrack(0): end=0 ] -> [ backtrack(1): end=1 ]`

=== STEP 6: Back in `backtrack(start = 1)` | UNDO after `end = 1`, then Loop `end = 2` ===
  What happens:
    3. UNDO   : `currentPath.remove(1)` -> Removes the second `"a"`. (`currentPath` is now `["a"]`).
    - Next Loop Iteration: `end` moves from `1` to `2`!
  String `s`  :   'a'  [ 'a'    'b' ] (Slice s[1..2] = "ab")
                          ^      *   (^ = start = 1, * = end = 2)
  Check `if`  : `isPalindrome[1][2]` -> `false` ("ab" is NOT a palindrome)
  What happens:
    - Skips the `if` body!
    - Loop for `start = 1` finishes (`end` reaches 3). Returns back to `backtrack(start = 0)`.
  Lists Now   :
    `currentPath` = `[ "a" ]`
    `result`      = `[ ["a", "a", "b"] ]`
    `Call Stack`  = `[ backtrack(0): end=0 ]`

=== STEP 7: Back in `backtrack(start = 0)` | UNDO after `end = 0`, then Loop `end = 1` ===
  What happens:
    3. UNDO   : `currentPath.remove(0)` -> Removes the first `"a"`. (`currentPath` is now `[]`!).
    - Next Loop Iteration: `end` moves from `0` to `1`!
  String `s`  : [ 'a'    'a' ]  'b'   (Slice s.substring(0, 2) = "aa")
                   ^      *   (^ = start = 0, * = end = 1)
  Check `if`  : `isPalindrome[0][1]` -> `true`
  What happens:
    1. DO     : `currentPath.add("aa")`
    2. RECURSE: Call `backtrack(start = 2)`
  Lists Now   :
    `currentPath` = `[ "aa" ]`
    `result`      = `[ ["a", "a", "b"] ]`
    `Call Stack`  = `[ backtrack(0): end=1 ] -> [ backtrack(2) ]`

=== STEP 8: Inside `backtrack(start = 2)` | Loop `end = 2` ===
  String `s`  :   'a'    'a'  [ 'b' ] (Slice s.substring(2, 3) = "b")
                                 ^
                                 *   (^ = start = 2, * = end = 2)
  Check `if`  : `isPalindrome[2][2]` -> `true`
  What happens:
    1. DO     : `currentPath.add("b")`
    2. RECURSE: Call `backtrack(start = 3)`
  Lists Now   :
    `currentPath` = `[ "aa", "b" ]`
    `result`      = `[ ["a", "a", "b"] ]`
    `Call Stack`  = `[ backtrack(0): end=1 ] -> [ backtrack(2): end=2 ]`

=== STEP 9: Inside `backtrack(start = 3)` | *** BASE CASE HIT! *** ===
  String `s`  :   'a'    'a'    'b'  | (start = 3 equals s.length() = 3)
                                     ^
  Check `if`  : `start == s.length()` -> `3 == 3` (TRUE!)
  What happens:
    - Copy `currentPath` into `result`: `result.add(new ArrayList<>(currentPath))`
    - `return` back to `backtrack(start = 2)`
  Lists Now   :
    `currentPath` = `[ "aa", "b" ]`  ---(SNAPSHOT COPIED)-------+
    `result`      = `[ ["a", "a", "b"], ["aa", "b"] ]` <--------+

=== STEP 10: Back in `backtrack(start = 2)` | UNDO after `end = 2` ===
  What happens:
    3. UNDO   : `currentPath.remove(1)` -> Removes `"b"`.
    - Loop for `start = 2` finishes. Returns back to `backtrack(start = 0)`.
  Lists Now   :
    `currentPath` = `[ "aa" ]`
    `result`      = `[ ["a", "a", "b"], ["aa", "b"] ]`

=== STEP 11: Back in `backtrack(start = 0)` | UNDO after `end = 1`, then Loop `end = 2` ===
  What happens:
    3. UNDO   : `currentPath.remove(0)` -> Removes `"aa"`. (`currentPath` is now `[]`).
    - Next Loop Iteration: `end` moves from `1` to `2`!
  String `s`  : [ 'a'    'a'    'b' ] (Slice s[0..2] = "aab")
                   ^             *   (^ = start = 0, * = end = 2)
  Check `if`  : `isPalindrome[0][2]` -> `false` ("aab" is NOT a palindrome)
  What happens:
    - Skips the `if` body!
    - Loop for `start = 0` finishes (`end` reaches 3).
    - `backtrack` finishes completely and `partition` returns `result`!
  Final State :
    `currentPath` = `[ ]`
    `result`      = `[ ["a", "a", "b"], ["aa", "b"] ]`


================================================================================
PART 5: QUICK SUMMARY TABLE & VISUAL TIMELINE
================================================================================

1. BACKTRACKING SUMMARY TABLE (`s = "aab"`)
-----------------------------------------------------------------------------------------------------------------
Step | `start` | `end` | Slice `s[start..end]` | `isPalindrome` | What Happens                    | `currentPath`
-----------------------------------------------------------------------------------------------------------------
1    | 0       | 0     | "a"   (0..0)          | true           | Add "a", call backtrack(1)      | ["a"]
2    | 1       | 1     | "a"   (1..1)          | true           | Add "a", call backtrack(2)      | ["a", "a"]
3    | 2       | 2     | "b"   (2..2)          | true           | Add "b", call backtrack(3)      | ["a", "a", "b"]
4    | 3       | -     | -                     | -              | Base Case (start==3) -> SAVE    | ["a", "a", "b"]
5    | 2       | 2     | "b"                   | -              | Undo -> remove "b", return      | ["a", "a"]
6    | 1       | 2     | "ab"  (1..2)          | false          | Undo "a", try "ab" (skip)       | ["a"]
7    | 0       | 1     | "aa"  (0..1)          | true           | Undo "a", Add "aa", call(2)     | ["aa"]
8    | 2       | 2     | "b"   (2..2)          | true           | Add "b", call backtrack(3)      | ["aa", "b"]
9    | 3       | -     | -                     | -              | Base Case (start==3) -> SAVE    | ["aa", "b"]
10   | 2       | 2     | "b"                   | -              | Undo -> remove "b", return      | ["aa"]
11   | 0       | 2     | "aab" (0..2)          | false          | Undo "aa", try "aab" (skip)     | []
-----------------------------------------------------------------------------------------------------------------

2. VISUAL TIMELINE OF `currentPath` AND `result`
-----------------------------------------------------------------------------------------------------------------
Time ->   Step 1    Step 2    Step 3     Step 4(Save) Step 5    Step 6    Step 7    Step 8     Step 9(Save) End
          +-----+   +-----+   +-----+    +-----+      +-----+   +-----+   +-----+   +-----+    +-----+      +---+
          |     |   |     |   | "b" |    | "b" |      |     |   |     |   |     |   | "b" |    | "b" |      |   |
current   |     |   | "a" |   | "a" |    | "a" |      | "a" |   |     |   |     |   | "aa"|    | "aa"|      |   |
Path      | "a" |   | "a" |   | "a" |    | "a" |      | "a" |   | "a" |   | "aa"|   |     |    |     |      |   |
          +-----+   +-----+   +-----+    +-----+      +-----+   +-----+   +-----+   +-----+    +-----+      +---+
           ADD       ADD       ADD        COPY!        REMOVE    REMOVE   REMOVE+    ADD        COPY!      REMOVE
                                            |                              ADD                    |
                                            v                                                     v
result:                           [ ["a","a","b"] ]                                   [ ["a","a","b"], ["aa","b"] ]


================================================================================
PART 6: EASY-TO-MEMORIZE FAANG INTERVIEW Q&A
================================================================================

1. TIME & SPACE COMPLEXITY (PLAIN ENGLISH)
--------------------------------------------------------------------------------
- **Time Complexity: O(n * 2^n)**
  1. Building the `isPalindrome` grid takes `O(n^2)` time because we fill half of an `n x n` grid.
  2. In the worst case (like `s = "aaaa"`), every possible cut gives a palindrome. For `n` letters,
     there are `n - 1` places to put a knife, and at each place we have 2 choices (Cut or Don't Cut).
     That creates `2^(n-1)` (which is `O(2^n)`) valid partitions.
  3. For each valid partition, slicing the strings and copying `currentPath` into `result` takes `O(n)` time.
  4. Total time = `O(n^2 + n * 2^n)` = **`O(n * 2^n)`**.

- **Space Complexity:**
  - **Extra Working Space (Auxiliary Space): `O(n^2)`**
    - `isPalindrome` table takes `O(n^2)` space.
    - Recursion Call Stack and `currentPath` list go at most `n` levels deep, taking `O(n)` space.
  - **Total Space (Including Output List `result`): `O(n * 2^n)`**
    - Because `result` stores up to `2^(n-1)` lists, each of size up to `n`.


2. TOP 5 INTERVIEWER FOLLOW-UP QUESTIONS & SIMPLE ANSWERS
--------------------------------------------------------------------------------
Q1: "Why did you precompute the `isPalindrome` 2D array instead of using a two-pointer helper method?"
- **Speak this answer:**
  "Without the 2D array, the backtracking tree checks the exact same substrings repeatedly across
  different branches, taking `O(n)` per check. Precomputing the DP table takes `O(n^2)` time once up front,
  making every palindrome check inside the recursion instant (`O(1)`)."

Q2: "How could you reduce the extra working space from O(n^2) down to O(n)?"
- **Speak this answer:**
  "If memory is tight, we can remove the `isPalindrome[][]` array and use a simple two-pointer helper
  `isPalin(s, start, end)` inside the backtracking loop. That drops our extra space from `O(n^2)` down
  to `O(n)` (just the recursion stack and `currentPath`), while overall worst-case time complexity stays
  `O(n * 2^n)`."

Q3: "Why must we check `if (isPalindrome[start][end])` BEFORE calling `s.substring(start, end + 1)`?"
- **Speak this answer:**
  "In Java, `s.substring()` creates a brand-new String object in memory and copies the characters,
  which takes `O(n)` time and memory. Checking `isPalindrome[start][end]` first takes `O(1)` time and
  avoids creating useless String objects when the slice isn't a palindrome."

Q4: "What if I only want the MINIMUM number of cuts needed, not the actual lists (LeetCode 132)?"
- **Speak this answer:**
  "If we only need the minimum number of cuts, we shouldn't use backtracking because listing all `2^n`
  ways is too slow. Instead, we keep the `isPalindrome` 2D table and use a 1D DP array `minCuts[i]`
  to track the minimum cuts for the prefix ending at `i`, solving it in `O(n^2)` time."

Q5: "Why did you pass `start` as an integer index instead of slicing `s.substring(i + 1)` into the recursive call?"
- **Speak this answer:**
  "Passing an integer `start` (`end + 1`) doesn't create any new String objects during the recursive call,
  and it lets our `start` and `end` pointers match the exact coordinates of our `isPalindrome[start][end]`
  table!"


3. TOP 3 WHITEBOARD MISTAKES TO AVOID
--------------------------------------------------------------------------------
1. **Forgetting `new ArrayList<>(currentPath)` in the base case:**
   Writing `result.add(currentPath)` will make your output `[[], []]` because `currentPath` gets emptied
   when backtracking unwinds.
2. **Putting `start` as the outer loop from `0` to `n-1` in DP:**
   If you loop `start` from `0` to `n-1` on the outside, `isPalindrome[start + 1][end - 1]` looks at row
   `start + 1`, which hasn't been calculated yet! Always keep `end` as the outer loop (`0` to `n-1`).
3. **Writing `s.substring(start, end)` instead of `s.substring(start, end + 1)`:**
   Java's `substring` excludes the end index, so you must write `end + 1` to include the character at `end`.
================================================================================
*/
