class Solution {
    public int smallestNumber(int n) {
        int x = 1;
        while (x < n) {
            x = (x << 1) | 1;
        }
        return x;
    }
}

/*
==================================================
## Code Summary
==================================================

We need the smallest number >= n whose binary
representation contains only 1s.

Numbers with only 1s in binary are:

1
11
111
1111
11111
...

In decimal:

1
3
7
15
31
...


We start with:

x = 1


Then keep changing:

1 → 3 → 7 → 15 → 31 → ...


We stop when:

x >= n


==================================================
## Easy Explanation of This Line
==================================================

x = (x << 1) | 1;


This line simply means:

1. Move all bits LEFT by one position.
2. Put 1 at the end.


Example:

x = 3

Binary:

011


Step 1:

011 << 1

= 110


Step 2:

110 | 001

= 111


Therefore:

3 → 7


Another example:

7 = 111

111 << 1

= 1110


1110 | 0001

= 1111


Therefore:

7 → 15


So this one line creates:

1 → 3 → 7 → 15 → 31


==================================================
## Iteration
==================================================

Let's take:

n = 10


Start:

x = 1

Binary:

1


Is:

1 < 10?

Yes.

Generate the next number:

1 → 3


Now:

x = 3

Binary:

11


Is:

3 < 10?

Yes.

Generate the next number:

3 → 7


Now:

x = 7

Binary:

111


Is:

7 < 10?

Yes.

Generate the next number:

7 → 15


Now:

x = 15

Binary:

1111


Is:

15 < 10?

No.


Stop.


Answer:

15


==================================================
## Another Example
==================================================

n = 5


Start:

x = 1


1 < 5

↓

x = 3


3 < 5

↓

x = 7


7 >= 5

↓

STOP


Answer:

7


==================================================
## Why Does This Work?
==================================================

The question wants a number whose binary
representation contains ONLY 1s.

For example:

5 = 101 ❌

6 = 110 ❌

7 = 111 ✅

8 = 1000 ❌

15 = 1111 ✅


The code generates ONLY these valid numbers:

1
3
7
15
31
...


Because they are generated in increasing order,
the FIRST one that is >= n is automatically
the smallest possible answer.


==================================================
## Easy Way to Remember
==================================================

Start:

1


Then repeatedly:

LEFT SHIFT + 1


Which gives:

1 → 3 → 7 → 15 → 31 → ...


Stop when:

x >= n


==================================================
## Time Complexity
==================================================

The value roughly doubles every iteration.

Therefore:

O(log N)


==================================================
## Space Complexity
==================================================

Only one variable is used.

Therefore:

O(1)

==================================================
*/
