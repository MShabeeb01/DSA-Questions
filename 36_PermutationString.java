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
        int j = 0; // Left boundary pointer (tracks outgoing character)
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
========================================================================================
## Code Summary
========================================================================================

1. Boundary Check:
   If n1 > n2, return false immediately because s2 cannot contain a substring of length n1.

2. Frequency Arrays:
   Declare count1 and count2 of size 26 for letters 'a' through 'z'.
   Mapping formula: index = character - 'a'.

3. First Window Initialization:
   Simultaneously tally characters of s1 into count1 and the first window s2[0..n1-1] into count2.

4. Initial Window Verification:
   Check Arrays.equals(count1, count2). If true, return true without sliding.

5. Sliding Window Phase:
   Maintain a constant window size of n1 by incrementing right index i and left index j:
   - INCOMING: Increment count2 for character s2[i].
   - OUTGOING: Decrement count2 for character s2[j].
   - VALIDATE: Run Arrays.equals(count1, count2). Return true if arrays match.
   - ADVANCE : j++.

6. Default Return:
   Return false if the entire length of s2 is exhausted without a match.

========================================================================================
## Detailed Visual Step-by-Step Trace & Array State
========================================================================================

Input:
  s1 = "ab"        (n1 = 2)
  s2 = "eidbaooo"  (n2 = 8)

Alphabet Index Reference Table:
Index :   0   1   2   3   4   5   6   7   8   9 ...  25
Letter:  'a' 'b' 'c' 'd' 'e' 'f' 'g' 'h' 'i' 'j' ... 'z'

Target Frequency Array (count1 for s1 = "ab") - REMAINS CONSTANT THROUGHOUT:
Index :   0   1   2   3   4   5   6   7   8   9 ...  25
count1: [ 1,  1,  0,  0,  0,  0,  0,  0,  0,  0, ...  0 ]
         ('a')('b')

----------------------------------------------------------------------------------------
PART 1: Initial Window Setup (Loop i = 0 to 1)
----------------------------------------------------------------------------------------

[Iteration i = 0]
Variables:
  i = 0 | n1 = 2 | n2 = 8
  s1.charAt(0) = 'a' -> target index = 'a' - 'a' = 0
  s2.charAt(0) = 'e' -> target index = 'e' - 'a' = 4

Operations:
  count1[0]++  --> count1[0] changes from 0 to 1
  count2[4]++  --> count2[4] changes from 0 to 1

Array States:
Index :   0   1   2   3   4   5   6   7   8 ...  25
count1: [ 1,  0,  0,  0,  0,  0,  0,  0,  0, ...  0 ]
count2: [ 0,  0,  0,  0,  1,  0,  0,  0,  0, ...  0 ]
                         ('e')


[Iteration i = 1]
Variables:
  i = 1 | n1 = 2 | n2 = 8
  s1.charAt(1) = 'b' -> target index = 'b' - 'a' = 1
  s2.charAt(1) = 'i' -> target index = 'i' - 'a' = 8

Operations:
  count1[1]++  --> count1[1] changes from 0 to 1
  count2[8]++  --> count2[8] changes from 0 to 1

Array States:
Index :   0   1   2   3   4   5   6   7   8 ...  25
count1: [ 1,  1,  0,  0,  0,  0,  0,  0,  0, ...  0 ]
count2: [ 0,  0,  0,  0,  1,  0,  0,  0,  1, ...  0 ]
                         ('e')           ('i')

----------------------------------------------------------------------------------------
PART 2: Initial Match Check
----------------------------------------------------------------------------------------
Statement: Arrays.equals(count1, count2)
  count1 slots set : index 0 ('a') = 1, index 1 ('b') = 1
  count2 slots set : index 4 ('e') = 1, index 8 ('i') = 1
Result: false (Mismatch -> Proceed to sliding window)

----------------------------------------------------------------------------------------
PART 3: Sliding Window Loop (i starts at n1 = 2, j starts at 0)
----------------------------------------------------------------------------------------

========================================================================================
SLIDE 1: i = 2, j = 0
========================================================================================
Window in s2:
  Index :   0    1    2    3    4    5    6    7
  String: ['e', 'i', 'd', 'b', 'a', 'o', 'o', 'o']
            ^         ^
           OUT       IN
           (j)       (i)
  Current Substring Window: s2[1..2] = "id"

Variables:
  i = 2, j = 0
  Incoming character: s2.charAt(2) = 'd' (index = 'd' - 'a' = 3)
  Outgoing character: s2.charAt(0) = 'e' (index = 'e' - 'a' = 4)

Count Modifications:
  [ADDITION] : count2[3]++ --> count2[3] changes from 0 to 1 ('d' entered window)
  [DELETION] : count2[4]-- --> count2[4] changes from 1 to 0 ('e' left window)

Array States:
Index :   0   1   2   3   4   5   6   7   8 ...  25
count1: [ 1,  1,  0,  0,  0,  0,  0,  0,  0, ...  0 ]  ('a':1, 'b':1)
count2: [ 0,  0,  0,  1,  0,  0,  0,  0,  1, ...  0 ]  ('d':1, 'i':1)
                     ('d')               ('i')

Comparison:
  Arrays.equals(count1, count2) --> false

Pointer Advancement:
  j changes from 0 to 1


========================================================================================
SLIDE 2: i = 3, j = 1
========================================================================================
Window in s2:
  Index :   0    1    2    3    4    5    6    7
  String: ['e', 'i', 'd', 'b', 'a', 'o', 'o', 'o']
                 ^         ^
                OUT       IN
                (j)       (i)
  Current Substring Window: s2[2..3] = "db"

Variables:
  i = 3, j = 1
  Incoming character: s2.charAt(3) = 'b' (index = 'b' - 'a' = 1)
  Outgoing character: s2.charAt(1) = 'i' (index = 'i' - 'a' = 8)

Count Modifications:
  [ADDITION] : count2[1]++ --> count2[1] changes from 0 to 1 ('b' entered window)
  [DELETION] : count2[8]-- --> count2[8] changes from 1 to 0 ('i' left window)

Array States:
Index :   0   1   2   3   4   5   6   7   8 ...  25
count1: [ 1,  1,  0,  0,  0,  0,  0,  0,  0, ...  0 ]  ('a':1, 'b':1)
count2: [ 0,  1,  0,  1,  0,  0,  0,  0,  0, ...  0 ]  ('b':1, 'd':1)
             ('b')   ('d')

Comparison:
  Arrays.equals(count1, count2) --> false

Pointer Advancement:
  j changes from 1 to 2


========================================================================================
SLIDE 3: i = 4, j = 2
========================================================================================
Window in s2:
  Index :   0    1    2    3    4    5    6    7
  String: ['e', 'i', 'd', 'b', 'a', 'o', 'o', 'o']
                      ^         ^
                     OUT       IN
                     (j)       (i)
  Current Substring Window: s2[3..4] = "ba"

Variables:
  i = 4, j = 2
  Incoming character: s2.charAt(4) = 'a' (index = 'a' - 'a' = 0)
  Outgoing character: s2.charAt(2) = 'd' (index = 'd' - 'a' = 3)

Count Modifications:
  [ADDITION] : count2[0]++ --> count2[0] changes from 0 to 1 ('a' entered window)
  [DELETION] : count2[3]-- --> count2[3] changes from 1 to 0 ('d' left window)

Array States:
Index :   0   1   2   3   4   5   6   7   8 ...  25
count1: [ 1,  1,  0,  0,  0,  0,  0,  0,  0, ...  0 ]  ('a':1, 'b':1)
count2: [ 1,  1,  0,  0,  0,  0,  0,  0,  0, ...  0 ]  ('a':1, 'b':1)
         ('a')('b')

Comparison:
  Arrays.equals(count1, count2)
  - Index 0: count1[0] == 1, count2[0] == 1 (match)
  - Index 1: count1[1] == 1, count2[1] == 1 (match)
  - All other indices 2..25: both are 0 (match)
  Result: true!

Method Return:
  Terminates early and returns true.

========================================================================================
## Why Does Fixed-Size Sliding Window Work?
========================================================================================

1. Substring Length Invariant:
   Any valid permutation of s1 must have identical length n1. Thus, we only scan 
   windows in s2 that are exactly n1 characters wide.

2. State Updates in O(1):
   Instead of recalculating all character counts from scratch across the window (O(n1)), 
   shifting the window only alters two values in count2:
   - Increment incoming character at the head (i).
   - Decrement outgoing character at the tail (j).

========================================================================================
## Time Complexity
========================================================================================

O(n1 + 26 * (n2 - n1)) -> O(n2)

Where:
  n1 = length of s1
  n2 = length of s2

- Building the initial window takes O(n1).
- The window slides at most (n2 - n1) times.
- Arrays.equals() iterates over a fixed size of 26: 26 comparisons = O(1) per slide.
- Overall runtime is linear: O(n2).

========================================================================================
## Space Complexity
========================================================================================

O(1) Auxiliary Space

Both count1 and count2 are allocated with fixed lengths of 26 integers, regardless
of how large s1 or s2 grow.
*/
