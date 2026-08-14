class Solution {

    // Checks whether the character is a letter or a number.
    public boolean isAlphaNum(char ch) {

        // Check if character is a digit.
        if (ch >= '0' && ch <= '9') {
            return true;
        }

        // Convert character to lowercase and check
        // whether it is between 'a' and 'z'.
        if (Character.toLowerCase(ch) >= 'a' &&
            Character.toLowerCase(ch) <= 'z') {
            return true;
        }

        return false;
    }

    public boolean isPalindrome(String s) {

        int st = 0;
        int end = s.length() - 1;

        // Use two pointers:
        // st  → starts from the LEFT
        // end → starts from the RIGHT
        while (st < end) {

            // Ignore spaces, symbols and other
            // non-alphanumeric characters from the left.
            if (!isAlphaNum(s.charAt(st))) {
                st++;
                continue;
            }

            // Ignore spaces, symbols and other
            // non-alphanumeric characters from the right.
            if (!isAlphaNum(s.charAt(end))) {
                end--;
                continue;
            }

            // Compare both characters without considering case.
            if (Character.toLowerCase(s.charAt(st)) !=
                Character.toLowerCase(s.charAt(end))) {
                return false;
            }

            // Move both pointers towards the center.
            st++;
            end--;
        }

        return true;
    }
}

/*
==================================================
## Code Summary
==================================================

We need to check whether a string is a palindrome.

A palindrome reads the same from:

LEFT → RIGHT

and

RIGHT → LEFT


Example:

"racecar"

r a c e c a r
↑           ↑

The characters match from both sides.


==================================================
## Important Condition
==================================================

The problem ignores:

- Spaces
- Punctuation
- Symbols

And it is NOT case-sensitive.

Example:

"A man, a plan, a canal: Panama"

After removing non-alphanumeric characters:

"amanaplanacanalpanama"

This is a palindrome.


==================================================
## Main Idea
==================================================

We use TWO POINTERS.

st:

Starts from the LEFT.


end:

Starts from the RIGHT.


Then compare:

s[st]

with:

s[end]


If they match:

Move both towards the center.


If they don't match:

Return false.


==================================================
## Why isAlphaNum()?
==================================================

We don't want to compare characters like:

' '
','
':'
'!'


We only care about:

a-z
A-Z
0-9


So:

isAlphaNum(ch)

returns:

true  → letter or number
false → anything else


==================================================
## Iteration
==================================================

Example:

s = "A man, a plan, a canal: Panama"


We ignore spaces, commas and ':'.


The useful characters are:

A m a n a p l a n a c a n a l P a n a m a


--------------------------------------------------
### Iteration 1
--------------------------------------------------

st points to:

'A'

end points to:

'a'


Convert both to lowercase:

'a' == 'a'


Match.


Move:

st++
end--


--------------------------------------------------
### Iteration 2
--------------------------------------------------

Compare:

'm'

with:

'm'


Match.


Move both pointers.


--------------------------------------------------
### Iteration 3
--------------------------------------------------

Compare:

'a'

with:

'a'


Match.


Move both pointers.


--------------------------------------------------
### Continue
--------------------------------------------------

The same process continues.

Every valid character from the left
matches the corresponding character
from the right.


Eventually:

st >= end


So we stop.


Return:

true


==================================================
## Example With Symbols
==================================================

s = "race a car"


The pointers may encounter:

' '


Since space is not alphanumeric:

isAlphaNum(' ')

returns:

false


So we simply skip it.


We do NOT compare the space.


==================================================
## Example Where It Fails
==================================================

s = "hello"


Start:

h          o
↑          ↑

h != o


Therefore:

return false


The string is not a palindrome.


==================================================
## Why Character.toLowerCase()?
==================================================

The problem is case-insensitive.

So:

'A' and 'a'

should be considered equal.


Therefore we convert both characters
to lowercase before comparing.


Example:

Character.toLowerCase('A')

→ 'a'


Character.toLowerCase('a')

→ 'a'


So they match.


==================================================
## Easy Way to Remember
==================================================

TWO POINTERS:

LEFT → st
RIGHT → end


For every step:

1. Skip non-alphanumeric characters.

2. Compare both characters ignoring case.

3. If different:

   return false

4. If same:

   move both pointers.


Finally:

return true


==================================================
## Time Complexity
==================================================

Each character is visited at most once.

Therefore:

O(N)


==================================================
## Space Complexity
==================================================

We only use two pointers.

Therefore:

O(1)


==================================================
## Pattern to Remember
==================================================

LEFT →→→

      ←←← RIGHT

Skip invalid characters.

Compare valid characters.

Move inward.


TWO POINTERS + SKIP + COMPARE

==================================================
*/
