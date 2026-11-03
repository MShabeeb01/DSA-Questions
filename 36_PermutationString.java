import java.util.*;

class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int n1 = s1.length();
        int n2 = s2.length();

        // If s1 is longer than s2, a permutation of s1 cannot exist in s2.
        if (n1 > n2) return false;

        // Frequency arrays for lowercase English letters ('a' through 'z').
        int[] count1 = new int[26];
        int[] count2 = new int[26];

        // Step 1: Initialize frequencies for s1 and the first window of size n1 in s2.
        for (int i = 0; i < n1; i++) {
            count1[s1.charAt(i) - 'a']++;
            count2[s2.charAt(i) - 'a']++;
        }

        // Check if the very first window matches.
        if (Arrays.equals(count1, count2)) return true;

        // Step 2: Slide the fixed-size window of length n1 across s2.
        int j = 0; // Left boundary pointer (outgoing character)
        for (int i = n1; i < n2; i++) {
            // Add the new incoming character on the right (index i)
            count2[s2.charAt(i) - 'a']++;

            // Remove the outgoing character on the left (index j)
            count2[s2.charAt(j) - 'a']--;

            // If frequencies match, a valid permutation is found.
            if (Arrays.equals(count1, count2)) {
                return true;
            }

            j++; // Advance the left boundary
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
   is too short to contain any permutation of s1.

2. Frequency Arrays:
   Create two integer arrays of size 26 (count1, count2) representing 
   frequencies for characters 'a' to 'z'.

3. First Window Initialization:
   Count the frequencies of all characters in s1 and the first n1 characters
   of s2 (indices 0 to n1 - 1) simultaneously.

4. Compare Initial Window:
   If Arrays.equals(count1, count2) is true, return true right away.

5. Fixed-Size Sliding Window:
   Slide across s2 from index n1 to n2 - 1:
   - Include incoming character at index i: count2[s2.charAt(i) - 'a']++
   - Exclude outgoing character at index j: count2[s2.charAt(j) - 'a']--
   - Check if Arrays.equals(count1, count2) is true -> return true.
   - Advance left pointer j by 1.

6. If the entire string s2 is scanned without a match, return false.

==================================================
## Detailed Tracing Example
==================================================

Input:
s1 = "ab"   (n1 = 2)
s2 = "baeo" (n2 = 4)

Initial Arrays Before Any Loop:
All 26 indices (0 to 25) start at 0.
count1 = [0, 0, 0, 0, ..., 0]
count2 = [0, 0, 0, 0, ..., 0]

--------------------------------------------------
PART 1: First Loop (Window Initialization)
Loop range: i = 0 to 1 (runs n1 = 2 times)
--------------------------------------------------

--- Iteration i = 0 ---
1. Processing s1.charAt(0) = 'a':
   - Index: 'a' - 'a' = 0
   - Action: count1[0]++ (0 -> 1)
   - What count1 stores:
     Index 0 ('a') = 1 | All other indices = 0

2. Processing s2.charAt(0) = 'b':
   - Index: 'b' - 'a' = 1
   - Action: count2[1]++ (0 -> 1)
   - What count2 stores:
     Index 1 ('b') = 1 | All other indices = 0

Array States after i = 0:
Index:      0    1    2    3    4  ...  25
Letter:    'a'  'b'  'c'  'd'  'e' ...  'z'
-------------------------------------------
count1:   [ 1,   0,   0,   0,   0, ...   0 ]
count2:   [ 0,   1,   0,   0,   0, ...   0 ]


--- Iteration i = 1 ---
1. Processing s1.charAt(1) = 'b':
   - Index: 'b' - 'a' = 1
   - Action: count1[1]++ (0 -> 1)
   - What count1 stores:
     Index 0 ('a') = 1 | Index 1 ('b') = 1 | Others = 0

2. Processing s2.charAt(1) = 'a':
   - Index: 'a' - 'a' = 0
   - Action: count2[0]++ (0 -> 1)
   - What count2 stores:
     Index 0 ('a') = 1 | Index 1 ('b') = 1 | Others = 0

Array States after i = 1:
Index:      0    1    2    3    4  ...  25
Letter:    'a'  'b'  'c'  'd'  'e' ...  'z'
-------------------------------------------
count1:   [ 1,   1,   0,   0,   0, ...   0 ]
count2:   [ 1,   1,   0,   0,   0, ...   0 ]


--------------------------------------------------
PART 2: Initial Match Check
--------------------------------------------------
Check: Arrays.equals(count1, count2)

- count1 has: 'a' = 1, 'b' = 1, all others = 0
- count2 has: 'a' = 1, 'b' = 1, all others = 0

Both arrays match identically at every index from 0 to 25.
Result: Returns true immediately.
(The sliding window loop does not even need to run!)

==================================================
## Why Does Fixed-Size Sliding Window Work?
==================================================

1. Every permutation of s1 has the exact same length (n1) and the 
   exact same character frequency counts as s1.

2. Therefore, any valid permutation must occupy a contiguous block 
   of exactly length n1 inside s2.

3. By maintaining frequency arrays of size 26 and updating only the 
   entering and exiting characters, each window slide runs in O(1) time.

==================================================
## Time Complexity
==================================================

O(n1 + 26 * (n2 - n1)) -> O(n2)

Where:
n1 = s1.length()
n2 = s2.length()

- Initial loop runs n1 times to build count1 and first window of count2: O(n1).
- Sliding window shifts (n2 - n1) times.
- Arrays.equals() compares 26 elements: O(26) = O(1) constant time per shift.
- Overall time complexity is linear: O(n2).

==================================================
## Space Complexity
==================================================

O(1) Auxiliary Space

The two frequency arrays count1 and count2 are always fixed at size 26, 
independent of the input lengths n1 and n2.
*/
