import java.util.*;

class Solution {
    public int lengthOfLongestSubstring(String s) {

        // Set stores the characters currently inside the window
        Set<Character> charSet = new HashSet<>();

        // Stores the maximum length found so far
        int maxLength = 0;

        // Left pointer of the sliding window
        int left = 0;

        // Right pointer moves through the string
        for (int right = 0; right < s.length(); right++) {

            // If the current character already exists,
            // remove characters from the left until
            // the duplicate character is removed.
            while (charSet.contains(s.charAt(right))) {

                charSet.remove(s.charAt(left));
                left++;
            }

            // Add the current character to the set
            charSet.add(s.charAt(right));

            // Calculate the current window length
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}

/*

## Code Summary

1. Use a HashSet to store unique characters.
2. Use two pointers: left and right.
3. The right pointer moves through the string.
4. If the current character is already in the Set,
   move the left pointer forward.
5. Remove characters from the Set while moving left.
6. Add the current character to the Set.
7. Calculate the current window length.
8. Keep updating the maximum length.
9. Return maxLength.

---

## Iteration

String:

"pwwkew"

Index:

 0  1  2  3  4  5
 p  w  w  k  e  w

---

## Iteration 1

right = 0
left = 0

Current character = 'p'

Set does not contain 'p'.

Add 'p'.

Set = {p}

Window = "p"

Length = 0 - 0 + 1 = 1

maxLength = 1

---

## Iteration 2

right = 1
left = 0

Current character = 'w'

'w' is not in the Set.

Add 'w'.

Set = {p, w}

Window = "pw"

Length = 1 - 0 + 1 = 2

maxLength = 2

---

## Iteration 3

right = 2
left = 0

Current character = 'w'

'w' is already in the Set.

Remove s[left]:

s[0] = 'p'

Set = {w}

left = 1

'w' is still in the Set.

Remove s[left]:

s[1] = 'w'

Set = {}

left = 2

Now 'w' is not in the Set.

Add 'w'.

Set = {w}

Window = "w"

Length = 2 - 2 + 1 = 1

maxLength = 2

---

## Iteration 4

right = 3
left = 2

Current character = 'k'

'k' is not in the Set.

Add 'k'.

Set = {w, k}

Window = "wk"

Length = 3 - 2 + 1 = 2

maxLength = 2

---

## Iteration 5

right = 4
left = 2

Current character = 'e'

'e' is not in the Set.

Add 'e'.

Set = {w, k, e}

Window = "wke"

Length = 4 - 2 + 1 = 3

maxLength = 3

---

## Iteration 6

right = 5
left = 2

Current character = 'w'

'w' is already in the Set.

Remove s[left]:

s[2] = 'w'

Set = {k, e}

left = 3

Add 'w'.

Set = {k, e, w}

Window = "kew"

Length = 5 - 3 + 1 = 3

maxLength = 3

---

## Final Answer

Longest substring without repeating characters:

"kew"

Length = 3

Output:

3

---

## Time Complexity

Right pointer moves through the string : O(n)

Left pointer also moves through the string : O(n)

Overall : O(n)

---

## Space Complexity

HashSet stores at most the unique characters:

O(min(n, charset))

For typical ASCII characters:

O(1)

*/
