class Solution {
    public String removeOccurrences(String s, String part) {

        // Keep removing 'part' as long as it exists in s.
        while (s.length() > 0 && s.contains(part)) {

            // Find the first occurrence of part.
            int index = s.indexOf(part);

            // Remove 'part' from the string.
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

We are given:

s    = original string
part = substring we need to remove


We repeatedly:

1. Find part inside s.
2. Remove that occurrence.
3. Repeat until part no longer exists.


For example:

s = "daabcbaabcbc"
part = "abc"


Remove first "abc":

"daabcbaabcbc"
    ↓
"dabaabcbc"


Remove "abc" again:

"dabaabcbc"
      ↓
"dab"


Final answer:

"dab"


==================================================
## Main Idea
==================================================

The important methods are:

contains()
indexOf()
substring()


--------------------------------------------------
### contains()
--------------------------------------------------

Checks whether part exists inside s.

Example:

s = "helloabc"

s.contains("abc")

→ true


If:

s = "hello"

s.contains("abc")

→ false


--------------------------------------------------
### indexOf()
--------------------------------------------------

Finds the starting index of part.

Example:

s = "daabcbaabcbc"
part = "abc"


The first "abc" starts at index:

2


So:

s.indexOf(part)

→ 2


--------------------------------------------------
### substring()
--------------------------------------------------

We use substring() to keep everything
BEFORE and AFTER the occurrence.


If:

s = "helloabcworld"

part = "abc"


index = 5


Before:

s.substring(0, 5)

→ "hello"


After:

s.substring(5 + 3)

→ "world"


Combine:

"hello" + "world"

→ "helloworld"


So "abc" is removed.


==================================================
## Iteration
==================================================

Let's take:

s = "daabcbaabcbc"

part = "abc"


--------------------------------------------------
### Iteration 1
--------------------------------------------------

Current:

daabcbaabcbc


Find:

"abc"


First occurrence starts at:

index = 2


Remove it:


da + baabcbc

↓

"dabaabcbc"


Current string:

dabaabcbc


--------------------------------------------------
### Iteration 2
--------------------------------------------------

Current:

dabaabcbc


Find:

"abc"


Its index is:

5


Remove it:


daba + bc

Wait — let's carefully track the string.

Current:

dabaabcbc

Indexes:

0 1 2 3 4 5 6 7 8
d a b a a b c b c


"abc" starts at index 4:

d a b a [a b c] b c

Remove it:

"dabab c"

↓

"dababc"


Now:

s = "dababc"


--------------------------------------------------
### Iteration 3
--------------------------------------------------

Current:

dababc


"abc" starts at index 3.


Remove:

dab + ""

↓

"dab"


Now:

"abc" no longer exists.


Stop.


Final answer:

"dab"


==================================================
## Why Do We Use a while Loop?
==================================================

We cannot remove only one occurrence.

After removing one occurrence, two parts of the
string can join together and create a NEW
occurrence of part.


Example:

s = "aabbcc"
part = "abc"


The string can change after every removal.


Therefore we keep checking:

while part exists


until there is nothing left to remove.


==================================================
## Easy Way to Remember
==================================================

Think:

FIND → REMOVE → REPEAT


1. Find part using:

   indexOf()


2. Remove it using:

   substring()


3. Repeat using:

   while()


4. Return the remaining string.


==================================================
## Time Complexity
==================================================

Finding and creating strings can take O(N).

Since we may remove the substring multiple times,
the worst-case complexity can be:

O(N²)


where N is the length of s.


==================================================
## Space Complexity
==================================================

substring concatenation creates new strings.

Therefore, the extra space can be:

O(N)


==================================================
## Pattern to Remember
==================================================

while (substring exists) {

    find it

    remove it
}


FIND
  ↓
REMOVE
  ↓
CHECK AGAIN
  ↓
REPEAT

==================================================
*/
