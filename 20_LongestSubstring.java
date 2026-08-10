class Solution {
    public int lengthOfLongestSubstring(String s) {

        // Stores the last position of each character
        int charIndex[] = new int[128];

        int maxLength = 0;
        int left = 0;

        // Right pointer moves through the string
        for (int right = 0; right < s.length(); right++) {

            char ch = s.charAt(right);

            // Move left if the character was already seen
            left = Math.max(left, charIndex[ch]);

            // Store current index + 1
            charIndex[ch] = right + 1;

            // Calculate current window length
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}

/*

## Code Summary

1. Create an array charIndex[] of size 128.
2. The array stores the last position of each character.
3. Use two pointers: left and right.
4. Move right through the string.
5. If the current character was already seen,
   move left to the position after its previous occurrence.
6. Update the character's position.
7. Calculate the current window length.
8. Keep the maximum length.
9. Return maxLength.

---

## Important Concept

We store:

charIndex[ch] = right + 1;

instead of:

charIndex[ch] = right;

Therefore:

0 means the character has never appeared.

Example:

String = "abc"

'a' at index 0

charIndex['a'] = 0 + 1 = 1

So when 'a' appears again:

left = Math.max(left, charIndex['a']);

left becomes 1.

This automatically moves left after the previous 'a'.

---

## Iteration

String:

"abcabcbb"

Index:

 0  1  2  3  4  5  6  7
 a  b  c  a  b  c  b  b

Initially:

left = 0
maxLength = 0

All charIndex[] values = 0

---

## Iteration 1

right = 0
ch = 'a'

charIndex['a'] = 0

left = Math.max(0, 0)
left = 0

Update:

charIndex['a'] = 1

Window:

"a"

Length:

0 - 0 + 1 = 1

maxLength = 1

---

## Iteration 2

right = 1
ch = 'b'

charIndex['b'] = 0

left = Math.max(0, 0)
left = 0

Update:

charIndex['b'] = 2

Window:

"ab"

Length:

1 - 0 + 1 = 2

maxLength = 2

---

## Iteration 3

right = 2
ch = 'c'

charIndex['c'] = 0

left = Math.max(0, 0)
left = 0

Update:

charIndex['c'] = 3

Window:

"abc"

Length:

2 - 0 + 1 = 3

maxLength = 3

---

## Iteration 4

right = 3
ch = 'a'

charIndex['a'] = 1

left = Math.max(0, 1)
left = 1

Update:

charIndex['a'] = 4

Window:

"bca"

Length:

3 - 1 + 1 = 3

maxLength = 3

---

## Iteration 5

right = 4
ch = 'b'

charIndex['b'] = 2

left = Math.max(1, 2)
left = 2

Update:

charIndex['b'] = 5

Window:

"cab"

Length:

4 - 2 + 1 = 3

maxLength = 3

---

## Iteration 6

right = 5
ch = 'c'

charIndex['c'] = 3

left = Math.max(2, 3)
left = 3

Update:

charIndex['c'] = 6

Window:

"abc"

Length:

5 - 3 + 1 = 3

maxLength = 3

---

## Iteration 7

right = 6
ch = 'b'

charIndex['b'] = 5

left = Math.max(3, 5)
left = 5

Update:

charIndex['b'] = 7

Window:

"cb"

Length:

6 - 5 + 1 = 2

maxLength = 3

---

## Iteration 8

right = 7
ch = 'b'

charIndex['b'] = 7

left = Math.max(5, 7)
left = 7

Update:

charIndex['b'] = 8

Window:

"b"

Length:

7 - 7 + 1 = 1

maxLength = 3

---

## Final Answer

Longest substring:

"abc"

Length:

3

Output:

3

---

## Time Complexity

The right pointer traverses the string once.

Time Complexity:

O(n)

---

## Space Complexity

The charIndex array has a fixed size of 128.

Space Complexity:

O(1)

*/
