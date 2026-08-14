class Solution {
    public String removeOccurrences(String s, String part) {

        // Keep removing 'part' as long as it exists in s.
        while (s.contains(part)) {

            // Find the starting index of 'part'.
            int index = s.indexOf(part);

            // Remove 'part' by joining:
            // 1. Everything BEFORE part
            // 2. Everything AFTER part
            s = s.substring(0, index)
              + s.substring(index + part.length());
        }

        return s;
    }
}

/*
==================================================
## Code Summary
==================================================

1. Find where 'part' occurs in the string.

2. Split the string into two parts:

   BEFORE part + AFTER part

3. Join them together.

4. This removes the occurrence of 'part'.

5. Repeat until 'part' no longer exists.

==================================================
## Important Line
==================================================

s = s.substring(0, index)
  + s.substring(index + part.length());

Suppose:

s    = "daabcbaabcbc"
part = "abc"

index = 2

The string looks like:

d a [a b c] b a a b c
0 1  2 3 4  5 6 7 8 9 10 11

Everything BEFORE "abc":

s.substring(0, 2)

= "da"

Everything AFTER "abc":

s.substring(2 + 3)

= s.substring(5)

= "baabcbc"

Join them:

"da" + "baabcbc"

= "dabaabcbc"

So:

daabcbaabcbc
   ↓ remove abc
dabaabcbc

==================================================
## Iteration
==================================================

Input:

s = "daabcbaabcbc"
part = "abc"

--------------------------------------------------
### Iteration 1
--------------------------------------------------

s = "daabcbaabcbc"

First "abc" starts at:

index = 2

BEFORE:

"da"

AFTER:

"baabcbc"

Remove:

"da" + "baabcbc"

New s:

"dabaabcbc"

--------------------------------------------------
### Iteration 2
--------------------------------------------------

s = "dabaabcbc"

"abc" starts at:

index = 4

BEFORE:

"daab"

AFTER:

"bc"

Remove:

"daab" + "bc"

New s:

"daabbc"

--------------------------------------------------
### Iteration 3
--------------------------------------------------

s = "daabbc"

"abc" does not exist.

Stop.

Final answer:

"daabbc"

==================================================
## How substring() Works
==================================================

substring(start, end)

takes characters from start to end - 1.

Example:

String:

"abcdef"

substring(0, 3)

= "abc"

substring(3)

= "def"

So:

substring(0, index)

means:

"Give me everything BEFORE index"

And:

substring(index + part.length())

means:

"Skip the entire part and give me everything AFTER it."

==================================================
## Why Do We Use index + part.length()?
==================================================

Suppose:

s = "abcdef"
part = "cd"

index = 2

We want to remove:

a b [c d] e f
      ↑ ↑

After "cd", the next character is index:

2 + 2 = 4

Therefore:

s.substring(4)

= "ef"

So:

"ab" + "ef"

= "abef"

==================================================
## Time Complexity
==================================================

Finding and removing substrings can take O(N)
per operation.

In the worst case, the operation can happen
many times.

Overall:

O(N²) approximately.

==================================================
## Space Complexity
==================================================

O(N)

Because String objects are immutable and new
strings are created when we use substring and +.
*/
