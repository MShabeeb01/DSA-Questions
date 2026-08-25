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
==================================================
## Code Summary
==================================================

1. Pre-populate an ASCII frequency array `targetMap` of size 128 for string `t`.

2. Use a two-pointer sliding window (`left`, `right`):
   - `right` expands the window to find a valid substring containing all of `t`.
   - `count` tracks how many characters of `t` are still missing.

3. Once all characters are covered (`count == 0`):
   - Update `minLen` and `startIndex` if the current window is smaller.
   - Shrink the window by moving `left` forward until it is no longer valid.

4. Return the minimum substring or `""` if no valid window is found.

==================================================
## Iteration
==================================================

Input:

s = "ADOBECODEBANC", t = "ABC"

targetMap initially:
{'A': 1, 'B': 1, 'C': 1}
count = 3

--------------------------------------------------
### Step 1: Expand Right Pointer
--------------------------------------------------

- Includes 'A', 'D', 'O', 'B', 'E', 'C'
- At index 5 ('C'), all required characters matched -> count = 0
- Valid window found: "ADOBEC" (length = 6)

--------------------------------------------------
### Step 2: Shrink Left Pointer
--------------------------------------------------

- Removes 'A' -> count becomes 1 (window invalid)
- Left stops at index 1

--------------------------------------------------
### Step 3: Continue Sliding
--------------------------------------------------

- Expand right to find next 'A' at index 10 -> "DOBECODEBA"
- Shrink left -> shrinks past 'D', 'O', 'B', 'E', 'C', 'O', 'D', 'E'
- New minimal window: "BANC" (length = 4)

--------------------------------------------------
## Final Result
--------------------------------------------------

Shortest valid substring:

"BANC"

Return:

"BANC"

==================================================
## Important Point
==================================================

Using a primitive array `int[128]` instead of `HashMap<Character, Integer>`
drastically reduces memory overhead and achieves optimal LeetCode runtime (~2-3 ms).

==================================================
## Why This Works
==================================================

- The `count` variable allows $O(1)$ validation of the window condition.
- Every character is visited at most twice (once by `right`, once by `left`).

==================================================
## Time Complexity
==================================================

O(M + N)

Where:
- M is the length of string `s`
- N is the length of string `t`

Both pointers traverse string `s` linearly.

==================================================
## Space Complexity
==================================================

O(1)

The frequency array is fixed at size 128 (ASCII set).

==================================================
*/
