import java.util.*;

class Solution {
    public int subarraySum(int[] nums, int k) {

        int prefixSum = 0;
        int count = 0;

        HashMap<Integer, Integer> map = new HashMap<>();

        map.put(0, 1);

        for (int num : nums) {

            prefixSum += num;

            int required = prefixSum - k;

            if (map.containsKey(required)) {
                count += map.get(required);
            }

            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }
}

/*
==================================================
## Code Summary
==================================================

We need to find how many continuous subarrays
have a sum equal to k.

We use:

1. Prefix Sum
2. HashMap

Prefix Sum:

It keeps adding the elements as we move
through the array.

Example:

nums = [1, 2, 1]

Prefix sums:

1
1 + 2 = 3
1 + 2 + 1 = 4


==================================================
## What is a HashMap?
==================================================

Think of a HashMap like a small notebook.

We can write something in the notebook
and give it a name.

For example:

Name        Value
-----------------
Apple       5
Banana      10
Mango       20

Here:

"Apple" is called the KEY.

5 is called the VALUE.

So:

KEY -> VALUE


==================================================
## Why Do We Need HashMap Here?
==================================================

In this problem, instead of storing:

Fruit -> Number

we store:

Prefix Sum -> How many times we have seen it


For example:

0 -> 1
1 -> 1
3 -> 2
5 -> 1

This means:

Prefix sum 0 appeared 1 time.

Prefix sum 1 appeared 1 time.

Prefix sum 3 appeared 2 times.

Prefix sum 5 appeared 1 time.


==================================================
## Creating a HashMap
==================================================

This line:

HashMap<Integer, Integer> map = new HashMap<>();

creates our empty notebook.

The first Integer is the KEY.

The second Integer is the VALUE.

So here:

KEY   = Prefix Sum

VALUE = Frequency

Frequency simply means:

"How many times did we see it?"


==================================================
## Putting Something Into HashMap
==================================================

We use:

map.put(key, value);

For example:

map.put(5, 2);

means:

"Store 5 in the notebook and remember that
its value is 2."


If we write:

map.put(10, 3);

our HashMap becomes:

10 -> 3


==================================================
## Getting a Value
==================================================

Suppose:

map = {5 -> 2}

If we write:

map.get(5)

we get:

2

Because the value stored with key 5 is 2.


==================================================
## containsKey()
==================================================

This asks:

"Does this key exist in the HashMap?"


Example:

map = {5 -> 2, 10 -> 3}

map.containsKey(5)

Answer:

true


map.containsKey(7)

Answer:

false


So:

if (map.containsKey(required))

means:

"Have we seen this required prefix sum before?"


==================================================
## getOrDefault()
==================================================

This line may look confusing:

map.getOrDefault(prefixSum, 0)


It simply means:

"Give me the value stored for prefixSum.

If it doesn't exist, give me 0."


Example:

map = {5 -> 2}

map.getOrDefault(5, 0)

returns:

2


But:

map.getOrDefault(7, 0)

returns:

0

because 7 does not exist.


==================================================
## Our Three Important HashMap Operations
==================================================

### 1. put()

Store something.

map.put(5, 1)


### 2. get()

Get the value.

map.get(5)


### 3. containsKey()

Check whether something exists.

map.containsKey(5)


There is also:

### 4. getOrDefault()

Get the value if it exists.

Otherwise return a default value.

map.getOrDefault(5, 0)


==================================================
## Iteration
==================================================

Example:

nums = [1, 2, 1, 2]

k = 3

We want:

Subarray Sum = 3


Valid subarrays are:

[1, 2]
[2, 1]
[1, 2]

Answer = 3


==================================================
## Initial State
==================================================

prefixSum = 0

count = 0

HashMap:

{}

Then we do:

map.put(0, 1);


Now:

map = {0 -> 1}


This means:

"We have seen prefix sum 0 one time."

Why?

Because before the array starts,
the prefix sum is 0.


==================================================
## Iteration 1
==================================================

Current number:

1

prefixSum:

0 + 1 = 1


Now:

required = prefixSum - k

required = 1 - 3

required = -2


We ask:

Does -2 exist in our HashMap?

map.containsKey(-2)

No.

So:

count = 0


Now store prefix sum 1.

map.put(1, 1)


HashMap:

0 -> 1
1 -> 1


Meaning:

We have seen prefix sum 0 once.

We have seen prefix sum 1 once.


==================================================
## Iteration 2
==================================================

Current number:

2

prefixSum:

1 + 2 = 3


required:

3 - 3 = 0


Now ask:

Does 0 exist in the HashMap?

Yes!

HashMap:

0 -> 1


So:

count = count + 1

count = 1


This gives us:

[1, 2]

because:

1 + 2 = 3


Now store prefix sum 3:

map.put(3, 1)


HashMap:

0 -> 1
1 -> 1
3 -> 1


==================================================
## Iteration 3
==================================================

Current number:

1

prefixSum:

3 + 1 = 4


required:

4 - 3 = 1


Does 1 exist?

Yes!

HashMap:

1 -> 1


So:

count = 1 + 1

count = 2


The subarray is:

[2, 1]

because:

2 + 1 = 3


Now store prefix sum 4:

map.put(4, 1)


HashMap:

0 -> 1
1 -> 1
3 -> 1
4 -> 1


==================================================
## Iteration 4
==================================================

Current number:

2

prefixSum:

4 + 2 = 6


required:

6 - 3 = 3


Does 3 exist?

Yes!

HashMap:

3 -> 1


So:

count = 2 + 1

count = 3


The subarray is:

[1, 2]

because:

1 + 2 = 3


Now store prefix sum 6.

HashMap:

0 -> 1
1 -> 1
3 -> 1
4 -> 1
6 -> 1


==================================================
## Final Answer
==================================================

count = 3

Therefore:

Output = 3


==================================================
## The Main Idea in Simple Words
==================================================

Think of the HashMap as a MEMORY.

As we move through the array,
we keep remembering the prefix sums
we have already seen.

For every new prefix sum, we ask:

"Have I seen a prefix sum that is
exactly k smaller than my current sum?"

If yes:

That means the numbers between those
two prefix sums add up to k.

That's the entire idea.


==================================================
## HashMap Cheat Sheet
==================================================

Remember these four:

map.put(key, value)

-> Store something.


map.get(key)

-> Get the value.


map.containsKey(key)

-> Check if the key exists.


map.getOrDefault(key, 0)

-> Get the value if it exists,
   otherwise give 0.


==================================================
## Time Complexity
==================================================

O(N)

We go through the array only once.


==================================================
## Space Complexity
==================================================

O(N)

The HashMap can store up to N prefix sums.

==================================================
*/
