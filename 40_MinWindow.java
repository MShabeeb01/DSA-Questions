import java.util.*;

class Solution {
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }

        // Frequency array for ASCII characters in target string t
        int[] targetMap = new int[128];
        for (char c : t.toCharArray()) {
            targetMap[c]++;
        }

        int left = 0;
        int right = 0;
        int count = t.length(); // Total characters needed to satisfy window
        int minLen = Integer.MAX_VALUE;
        int startIndex = 0;

        char[] sArr = s.toCharArray();

        // Expand the right pointer to include characters in the window
        while (right < sArr.length) {
            char rChar = sArr[right];

            // If the character is needed, decrement the requirement count
            if (targetMap[rChar] > 0) {
                count--;
            }
            targetMap[rChar]--;
            right++;

            // When all characters are matched, shrink window from the left
            while (count == 0) {
                if (right - left < minLen) {
                    minLen = right - left;
                    startIndex = left;
                }

                char lChar = sArr[left];
                targetMap[lChar]++;

                // If removing left character violates the match, increment count
                if (targetMap[lChar] > 0) {
                    count++;
                }
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(startIndex, startIndex + minLen);
    }
}

/*
========================================================================================
## Code Summary
========================================================================================

1. Character Demand Array (`targetMap`):
   - A direct-mapped ASCII array of size 128.
   - Positive value (`> 0`): The character is still needed to form a valid window.
   - Zero (`0`): Exactly enough copies of this character exist inside the window.
   - Negative value (`< 0`): Surplus/unneeded characters present inside the window.

2. Deficit Counter (`count`):
   - Initialized to `t.length()`.
   - Decremented only when a character with `targetMap[c] > 0` is consumed.
   - When `count == 0`, all characters of `t` are present in the current window.

3. Two-Pointer Window Expansion & Contraction:
   - Expand (`right++`): Absorb `sArr[right]`, reduce its demand, update `count`.
   - Contract (`left++`): While `count == 0`, record the minimal window, eject `sArr[left]`,
     restore its demand in `targetMap`, and increment `count` if an essential character is lost.

4. Result Extraction:
   - If `minLen` remained untouched, return `""`.
   - Otherwise, extract `s.substring(startIndex, startIndex + minLen)`.

========================================================================================
## Core Invariants: What targetMap Values Mean
========================================================================================

Let's understand why `targetMap` values become negative:
- Target `t = "ABC"`.
- Initially: `targetMap['A'] = 1`, `targetMap['B'] = 1`, `targetMap['C'] = 1`, all others = 0.
- When an unrelated character like `'D'` enters:
  - `targetMap['D'] > 0` is false (it was 0), so `count` does NOT decrement.
  - `targetMap['D']--` turns it into `-1`.
  - Negative values signify: "This character is extra/garbage; we can discard it safely."
- When a needed character like `'A'` enters:
  - `targetMap['A'] > 0` is true (it was 1), so `count--` (one less character needed).
  - `targetMap['A']--` turns it into `0` (need is satisfied).
  - If a second `'A'` enters later, `targetMap['A']` becomes `-1` (duplicate/surplus).

========================================================================================
## Detailed Step-by-Step Visual Trace
========================================================================================

Input:
  s = "ADOBECODEBANC"
  t = "ABC"

Indices of s:
   0   1   2   3   4   5   6   7   8   9  10  11  12
 ['A','D','O','B','E','C','O','D','E','B','A','N','C']

Initial State:
  targetMap['A'] = 1
  targetMap['B'] = 1
  targetMap['C'] = 1
  All other 125 ASCII indices = 0
  count = 3
  left = 0, right = 0
  minLen = INF, startIndex = 0

----------------------------------------------------------------------------------------
EXPANSION PHASE 1: Searching for the first valid window (Indices 0 to 5)
----------------------------------------------------------------------------------------

--- STEP 1: right = 0, rChar = 'A' ---
Window: [A]
targetMap['A'] was 1 (> 0) -> MATCH! count becomes 3 - 1 = 2
targetMap['A'] becomes 0
right advances to 1
State: left=0, right=1 | count=2 | targetMap: {'A':0, 'B':1, 'C':1}
Pointer Trace:
  A  D  O  B  E  C  O  D  E  B  A  N  C
 [L]
    [R]

--- STEP 2: right = 1, rChar = 'D' ---
Window: [A D]
targetMap['D'] was 0 (not > 0) -> Unneeded. count remains 2
targetMap['D'] becomes -1
right advances to 2
State: left=0, right=2 | count=2 | targetMap: {'A':0, 'B':1, 'C':1, 'D':-1}
Pointer Trace:
  A  D  O  B  E  C  O  D  E  B  A  N  C
 [L]
       [R]

--- STEP 3: right = 2, rChar = 'O' ---
Window: [A D O]
targetMap['O'] was 0 (not > 0) -> Unneeded. count remains 2
targetMap['O'] becomes -1
right advances to 3
State: left=0, right=3 | count=2 | targetMap: {'A':0, 'B':1, 'C':1, 'D':-1, 'O':-1}
Pointer Trace:
  A  D  O  B  E  C  O  D  E  B  A  N  C
 [L]
          [R]

--- STEP 4: right = 3, rChar = 'B' ---
Window: [A D O B]
targetMap['B'] was 1 (> 0) -> MATCH! count becomes 2 - 1 = 1
targetMap['B'] becomes 0
right advances to 4
State: left=0, right=4 | count=1 | targetMap: {'A':0, 'B':0, 'C':1, 'D':-1, 'O':-1}
Pointer Trace:
  A  D  O  B  E  C  O  D  E  B  A  N  C
 [L]
             [R]

--- STEP 5: right = 4, rChar = 'E' ---
Window: [A D O B E]
targetMap['E'] was 0 (not > 0) -> Unneeded. count remains 1
targetMap['E'] becomes -1
right advances to 5
State: left=0, right=5 | count=1 | targetMap: {'A':0, 'B':0, 'C':1, 'D':-1, 'O':-1, 'E':-1}
Pointer Trace:
  A  D  O  B  E  C  O  D  E  B  A  N  C
 [L]
                [R]

--- STEP 6: right = 5, rChar = 'C' ---
Window: [A D O B E C]
targetMap['C'] was 1 (> 0) -> MATCH! count becomes 1 - 1 = 0
targetMap['C'] becomes 0
right advances to 6
State: left=0, right=6 | count=0 (VALID WINDOW FOUND!)
Pointer Trace:
  A  D  O  B  E  C  O  D  E  B  A  N  C
 [L]
                   [R]

----------------------------------------------------------------------------------------
CONTRACTION PHASE 1: count == 0 -> Shrink window from the left
----------------------------------------------------------------------------------------

Window is valid: s[0..5] = "ADOBEC", length = 6 - 0 = 6
minLen updated: 6, startIndex = 0

* Attempt to evict sArr[left=0] ('A'):
  lChar = 'A'
  targetMap['A']++ (becomes 0 + 1 = 1)
  targetMap['A'] is now > 0 -> Crucial character removed! count becomes 0 + 1 = 1
  left advances to 1
  count == 0 is now FALSE. Contraction halts!

State after ejection:
  Window: s[1..5] = "DOBEC" (missing 'A')
  left = 1, right = 6 | count = 1
  targetMap: {'A':1, 'B':0, 'C':0, 'D':-1, 'O':-1, 'E':-1}
Pointer Trace:
  A  D  O  B  E  C  O  D  E  B  A  N  C
    [L]            [R]

----------------------------------------------------------------------------------------
EXPANSION PHASE 2: Expanding right to find the next 'A' (Indices 6 to 10)
----------------------------------------------------------------------------------------

--- STEP 7: right = 6, rChar = 'O' ---
targetMap['O'] was -1 -> count remains 1
targetMap['O'] becomes -2, right advances to 7

--- STEP 8: right = 7, rChar = 'D' ---
targetMap['D'] was -1 -> count remains 1
targetMap['D'] becomes -2, right advances to 8

--- STEP 9: right = 8, rChar = 'E' ---
targetMap['E'] was -1 -> count remains 1
targetMap['E'] becomes -2, right advances to 9

--- STEP 10: right = 9, rChar = 'B' ---
targetMap['B'] was 0 (already satisfied) -> count remains 1
targetMap['B'] becomes -1 (surplus 'B' added), right advances to 10

Pointer Trace:
  A  D  O  B  E  C  O  D  E  B  A  N  C
    [L]                         [R]

--- STEP 11: right = 10, rChar = 'A' ---
targetMap['A'] was 1 (> 0) -> MATCH! count becomes 1 - 1 = 0
targetMap['A'] becomes 0
right advances to 11
State: left=1, right=11 | count=0 (VALID WINDOW FOUND: "DOBECODEBA", length = 10)
Pointer Trace:
  A  D  O  B  E  C  O  D  E  B  A  N  C
    [L]                            [R]

----------------------------------------------------------------------------------------
CONTRACTION PHASE 2: count == 0 -> Aggressive left shrinkage
----------------------------------------------------------------------------------------

Current Window length: 11 - 1 = 10 (not smaller than minLen=6, no update to minLen)

* Evict sArr[1] ('D'):
  targetMap['D'] was -2 -> targetMap['D']++ becomes -1 (still <= 0, count stays 0)
  left advances to 2: Window = "OBECODEBA"

* Evict sArr[2] ('O'):
  targetMap['O'] was -2 -> targetMap['O']++ becomes -1 (still <= 0, count stays 0)
  left advances to 3: Window = "BECODEBA"

* Evict sArr[3] ('B'):
  targetMap['B'] was -1 (surplus) -> targetMap['B']++ becomes 0 (still <= 0, count stays 0!)
  left advances to 4: Window = "ECODEBA"
  (Notice: We safely discarded the first 'B' because we captured another 'B' at index 9!)

* Evict sArr[4] ('E'):
  targetMap['E'] was -2 -> targetMap['E']++ becomes -1 (count stays 0)
  left advances to 5: Window = "CODEBA"

* Evict sArr[5] ('C'):
  targetMap['C'] was 0 -> targetMap['C']++ becomes 1 (> 0 -> Crucial character lost!)
  count becomes 0 + 1 = 1
  left advances to 6
  Contraction halts!

State after contraction:
  Window: s[6..10] = "ODEBA" (missing 'C')
  left = 6, right = 11 | count = 1
  targetMap: {'A':0, 'B':0, 'C':1, 'D':-1, 'O':-1, 'E':-1}
Pointer Trace:
  A  D  O  B  E  C  O  D  E  B  A  N  C
                   [L]             [R]

----------------------------------------------------------------------------------------
EXPANSION PHASE 3: Searching for 'C' (Indices 11 to 12)
----------------------------------------------------------------------------------------

--- STEP 12: right = 11, rChar = 'N' ---
targetMap['N'] was 0 -> count remains 1
targetMap['N'] becomes -1, right advances to 12

--- STEP 13: right = 12, rChar = 'C' ---
targetMap['C'] was 1 (> 0) -> MATCH! count becomes 1 - 1 = 0
targetMap['C'] becomes 0
right advances to 13 (array boundary reached)
State: left=6, right=13 | count=0 (VALID WINDOW FOUND: "ODEBANC", length = 7)
Pointer Trace:
  A  D  O  B  E  C  O  D  E  B  A  N  C
                   [L]                   [R]

----------------------------------------------------------------------------------------
CONTRACTION PHASE 3: Final shrinkage to uncover minimum window
----------------------------------------------------------------------------------------

* Evict sArr[6] ('O'):
  targetMap['O']++ becomes 0 (count stays 0), left advances to 7

* Evict sArr[7] ('D'):
  targetMap['D']++ becomes 0 (count stays 0), left advances to 8

* Evict sArr[8] ('E'):
  targetMap['E']++ becomes 0 (count stays 0), left advances to 9

Current Window: s[9..12] = "BANC"
  Length = right - left = 13 - 9 = 4
  4 < minLen (6) -> NEW MINIMUM FOUND!
  minLen = 4
  startIndex = 9

Pointer Trace of Optimal Substring:
  A  D  O  B  E  C  O  D  E  B  A  N  C
                            [L]          [R]
                             |-----------|
                                "BANC"

* Evict sArr[9] ('B'):
  targetMap['B'] was 0 -> targetMap['B']++ becomes 1 (> 0 -> count becomes 1)
  left advances to 10
  count == 0 is FALSE. Contraction halts!

Outer loop finishes because right == sArr.length (13).

----------------------------------------------------------------------------------------
FINAL EXTRACTION
----------------------------------------------------------------------------------------
startIndex = 9, minLen = 4
Substring: s.substring(9, 9 + 4) = s.substring(9, 13) = "BANC"
Returned Output: "BANC"

========================================================================================
## Complete State Matrix Across Key Iterations
========================================================================================

| Action / Event             | left | right | Current Window Substring | count | minLen | TargetMap Significant States         |
|:---------------------------|:-----|:------|:-------------------------|:------|:-------|:-------------------------------------|
| Initial                    | 0    | 0     | ""                       | 3     | INF    | A:1, B:1, C:1                        |
| First match reached        | 0    | 6     | "ADOBEC"                 | 0     | 6      | A:0, B:0, C:0, D:-1, O:-1, E:-1      |
| Evict 'A' (invalidates)    | 1    | 6     | "DOBEC"                  | 1     | 6      | A:1, B:0, C:0                        |
| Second match reached       | 1    | 11    | "DOBECODEBA"             | 0     | 6      | A:0, B:-1, C:0                       |
| Shrink to 'C' (ejects 'C') | 6    | 11    | "ODEBA"                  | 1     | 6      | A:0, B:0, C:1                        |
| Third match reached        | 6    | 13    | "ODEBANC"                | 0     | 6      | A:0, B:0, C:0, N:-1                  |
| Shrink garbage ('O','D','E')| 9   | 13    | "BANC"                   | 0     | 4      | A:0, B:0, C:0 (minLen updated to 4)  |
| Evict 'B' (terminates)     | 10   | 13    | "ANC"                    | 1     | 4      | B:1, A:0, C:0                        |

========================================================================================
## Time Complexity
========================================================================================

O(M + N)

Where:
  M = length of string s
  N = length of string t

- Creating targetMap from t takes O(N) time.
- The `right` pointer moves from 0 to M - 1 exactly once: O(M).
- The `left` pointer moves from 0 to M - 1 at most once across the entire algorithm: O(M).
- Total runtime is strictly linear: O(M + N).

========================================================================================
## Space Complexity
========================================================================================

O(1) Auxiliary Space

- `targetMap` is a fixed-size integer array of length 128, which consumes constant memory.
- `sArr` takes O(M) memory for character extraction (or O(1) auxiliary space if using 
  `s.charAt()` directly instead of `s.toCharArray()`).
*/
