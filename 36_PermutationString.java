import java.util.*;

class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int n1 = s1.length();
        int n2 = s2.length();

        // If s1 is longer than s2, a permutation of s1 cannot be a substring of s2.
        if (n1 > n2) return false;

        // Frequency arrays for lowercase English letters ('a' through 'z').
        int[] count1 = new int[26];
        int[] count2 = new int[26];

        // Step 1: Initialize the frequencies for s1 and the first window of size n1 in s2.
        for (int i = 0; i < n1; i++) {
            count1[s1.charAt(i) - 'a']++;
            count2[s2.charAt(i) - 'a']++;
        }

        // Check if the very first window matches.
        if (Arrays.equals(count1, count2)) return true;

        // Step 2: Slide the window of fixed size n1 across s2.
        int j = 0; // Pointer to the character leaving the left side of the window
        for (int i = n1; i < n2; i++) {
            // Add the new incoming character on the right (index i)
            count2[s2.charAt(i) - 'a']++;

            // Remove the outgoing character on the left (index j)
            count2[s2.charAt(j) - 'a']--;

            // If frequencies match, an anagram/permutation is found in this window.
            if (Arrays.equals(count1, count2)) {
                return true;
            }

            j++; // Slide the left boundary forward
        }

        return false;
    }
}

/*
==================================================
## Code Summary
==================================================

1. Base Check:
   If s1.length() > s2.length(), return false immediately because s2
   cannot contain any permutation of s1.

2. Frequency Arrays:
   Create two integer arrays of size 26 (count1, count2) to store
   character counts for lowercase English letters ('a' to 'z').

3. Initial Window Setup:
   Count character frequencies of s1 and the first window of s2 (from index 0 to n1 - 1).

4. Compare Initial Window:
   If count1 equals count2 via Arrays.equals(), return true immediately.

5. Fixed-Size Sliding Window:
   Slide the window across s2 one character at a time from index n1 to n2 - 1:
   - Add the new rightmost character: count2[s2.charAt(i) - 'a']++
   - Remove the leftmost character: count2[s2.charAt(j) - 'a']--
   - Check if Arrays.equals(count1, count2) is true. If yes, return true.
   - Increment j to keep window length fixed at n1.

6. If the loop completes without finding a match, return false.

==================================================
## Step-by-Step Iteration Trace
==================================================

Input: 
s1 = "ab"  (n1 = 2)
s2 = "eidbaooo" (n2 = 8)

Initial Target Frequency for s1 ("ab"):
count1: ['a': 1, 'b': 1, all others: 0]

--------------------------------------------------
Initial Window: indices [0, 1] -> "ei"
--------------------------------------------------
count2: ['e': 1, 'i': 1]
Compare count1 == count2? -> NO

--------------------------------------------------
Sliding Window: (j = left, i = right)
--------------------------------------------------

Iteration 1: (i = 2, j = 0)
- In:  s2[2] = 'd' -> count2['d']++
- Out: s2[0] = 'e' -> count2['e']--
- Current Window substring [1..2]: "id"
- count2: ['i': 1, 'd': 1]
- Match? -> NO
- j becomes 1

Iteration 2: (i = 3, j = 1)
- In:  s2[3] = 'b' -> count2['b']++
- Out: s2[1] = 'i' -> count2['i']--
- Current Window substring [2..3]: "db"
- count2: ['d': 1, 'b': 1]
- Match? -> NO
- j becomes 2

Iteration 3: (i = 4, j = 2)
- In:  s2[4] = 'a' -> count2['a']++
- Out: s2[2] = 'd' -> count2['d']--
- Current Window substring [3..4]: "ba"
- count2: ['a': 1, 'b': 1]
- Match? Arrays.equals(count1, count2) -> YES!
- Return true immediately.

==================================================
## Why Does Fixed-Size Sliding Window Work?
==================================================

A permutation of string s1 has:
1. The exact same length as s1 (n1).
2. The exact same character frequencies as s1.

Because the required substring must have length n1, we only need to inspect
contiguous substrings of length n1 in s2. 

Instead of recomputing the character counts of every window from scratch in O(n1) time,
we update the counts in O(1) time by adding the incoming character on the right
and subtracting the outgoing character on the left.

==================================================
## Time Complexity
==================================================

O(n1 + 26 * (n2 - n1)) -> O(n2)

Where:
n1 = s1.length()
n2 = s2.length()

- Initial window creation takes O(n1) time.
- Sliding the window runs (n2 - n1) times.
- Arrays.equals() compares two fixed arrays of size 26, taking O(26) = O(1) operations per slide.
- Overall time complexity is linear: O(n2).

==================================================
## Space Complexity
==================================================

O(1) Auxiliary Space

Because both count1 and count2 are fixed-size arrays of size 26,
independent of the lengths of s1 and s2.
*/
