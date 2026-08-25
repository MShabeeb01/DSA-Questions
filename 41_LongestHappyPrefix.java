class Solution {
    public String longestPrefix(String s) {
        int n = s.length();
        int[] lps = new int[n];
        
        int len = 0; // Length of previous longest prefix suffix
        int i = 1;
        
        // Build the LPS (Longest Prefix Suffix) array
        while (i < n) {
            if (s.charAt(i) == s.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1]; // Fallback to previous longest prefix length
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        
        // lps[n - 1] holds the length of the longest prefix which is also a suffix
        int longestLen = lps[n - 1];
        return s.substring(0, longestLen);
    }
}

/* 
==================================================
## Code Summary
==================================================

1. Construct the KMP preprocessing table: the LPS (Longest Prefix Suffix) array.

2. `lps[i]` stores the length of the longest proper prefix of `s[0...i]` that is also a suffix of `s[0...i]`.

3. Use two pointers:
   - `len`: tracks the length of the current matched prefix.
   - `i`: iterates through the string from index 1 to `n - 1`.

4. The value at `lps[n - 1]` gives the exact length of the longest happy prefix for the whole string.

5. Return `s.substring(0, lps[n - 1])`.

==================================================
## Iteration
==================================================

Input:

s = "ababab"
n = 6

--------------------------------------------------
### Step-by-Step LPS Construction
--------------------------------------------------

- i = 1, len = 0: s[1] ('b') != s[0] ('a') -> lps[1] = 0, i = 2
- i = 2, len = 0: s[2] ('a') == s[0] ('a') -> len = 1, lps[2] = 1, i = 3
- i = 3, len = 1: s[3] ('b') == s[1] ('b') -> len = 2, lps[3] = 2, i = 4
- i = 4, len = 2: s[4] ('a') == s[2] ('a') -> len = 3, lps[4] = 3, i = 5
- i = 5, len = 3: s[5] ('b') == s[3] ('b') -> len = 4, lps[5] = 4, i = 6

LPS Array: [0, 0, 1, 2, 3, 4]

--------------------------------------------------
## Final Result
--------------------------------------------------

lps[n - 1] = lps[5] = 4

Substring s[0...4]:

"abab"

Return:

"abab"

==================================================
## Why This Works
==================================================

The definition of a "happy prefix" is identical to the 
definition of the Longest Proper Prefix which is also a Suffix (LPS).
Hence, the final entry `lps[n - 1]` directly gives the required length.

==================================================
## Time Complexity
==================================================

O(N)

The pointer `i` increases up to `N` times, and `len` decreases at most `N` times.

==================================================
## Space Complexity
==================================================

O(N)

Required to store the `lps` array of size `N`.

==================================================
*/
