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
## Detailed Tracing Example (Demonstrating Sliding Window)
==================================================

Input:
s1 = "ab"        (n1 = 2)
s2 = "eidbaooo"  (n2 = 8)

Target to match (count1 for s1 = "ab"):
- Index 0 ('a') = 1
- Index 1 ('b') = 1
- All other 24 indices = 0

--------------------------------------------------
PART 1: First Loop (Initial Window Setup)
Loop range: i = 0 to 1 (n1 = 2)
Window covers s2[0..1] -> "ei"
--------------------------------------------------

--- Iteration i = 0 ---
s1.charAt(0) = 'a' -> count1['a' - 'a']++ -> count1[0] becomes 1
s2.charAt(0) = 'e' -> count2['e' - 'a']++ -> count2[4] becomes 1

State of non-zero counts:
count1: {'a': 1}
count2: {'e': 1}

--- Iteration i = 1 ---
s1.charAt(1) = 'b' -> count1['b' - 'a']++ -> count1[1] becomes 1
s2.charAt(1) = 'i' -> count2['i' - 'a']++ -> count2[8] becomes 1

State of non-zero counts:
count1: {'a': 1, 'b': 1}
count2: {'e': 1, 'i': 1}

--------------------------------------------------
PART 2: Initial Match Check
--------------------------------------------------
Arrays.equals(count1, count2)
- count1 has {'a': 1, 'b': 1}
- count2 has {'e': 1, 'i': 1}
Match? -> NO (Arrays.equals is false).
Proceed to Sliding Window loop!

--------------------------------------------------
PART 3: Sliding Window Loop (i = n1 to n2 - 1, j starts at 0)
Window size remains fixed at n1 = 2
--------------------------------------------------

--- Slide 1: i = 2, j = 0 ---
Incoming char on right : s2[2] = 'd' (index 'd' - 'a' = 3)
Outgoing char on left  : s2[0] = 'e' (index 'e' - 'a' = 4)

Actions:
- count2[3]++ ('d' count increases from 0 -> 1)
- count2[4]-- ('e' count decreases from 1 -> 0)

Current Window in s2 [indices 1..2]: "id"
What count1 stores: {'a': 1, 'b': 1}
What count2 stores: {'d': 1, 'i': 1}

Check Arrays.equals(count1, count2):
- count1 has 'a' and 'b'
- count2 has 'd' and 'i'
Match? -> NO

Update:
- j increments to 1

--- Slide 2: i = 3, j = 1 ---
Incoming char on right : s2[3] = 'b' (index 'b' - 'a' = 1)
Outgoing char on left  : s2[1] = 'i' (index 'i' - 'a' = 8)

Actions:
- count2[1]++ ('b' count increases from 0 -> 1)
- count2[8]-- ('i' count decreases from 1 -> 0)

Current Window in s2 [indices 2..3]: "db"
What count1 stores: {'a': 1, 'b': 1}
What count2 stores: {'b': 1, 'd': 1}

Check Arrays.equals(count1, count2):
- count1 has 'a' and 'b'
- count2 has 'b' and 'd'
Match? -> NO

Update:
- j increments to 2

--- Slide 3: i = 4, j = 2 ---
Incoming char on right : s2[4] = 'a' (index 'a' - 'a' = 0)
Outgoing char on left  : s2[2] = 'd' (index 'd' - 'a' = 3)

Actions:
- count2[0]++ ('a' count increases from 0 -> 1)
- count2[3]-- ('d' count decreases from 1 -> 0)

Current Window in s2 [indices 3..4]: "ba"
What count1 stores: {'a': 1, 'b': 1}
What count2 stores: {'a': 1, 'b': 1}

Check Arrays.equals(count1, count2):
- count1 index 0 ('a') = 1, index 1 ('b') = 1, all others 0
- count2 index 0 ('a') = 1, index 1 ('b') = 1, all others 0
Match? -> YES! Both arrays match identically.

Result:
Return true immediately.

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
