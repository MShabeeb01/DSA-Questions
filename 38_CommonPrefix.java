import java.util.Arrays;

class Solution {
    public String longestCommonPrefix(String[] strs) {
        // Base condition
        if (strs == null || strs.length == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        // Sort the array lexicographically
        Arrays.sort(strs);

        // Get the first and last strings
        char[] first = strs[0].toCharArray();
        char[] last = strs[strs.length - 1].toCharArray();

        // Start comparing
        for (int i = 0; i < first.length && i < last.length; i++) {
            if (first[i] != last[i]) {
                break;
            }
            result.append(first[i]);
        }

        return result.toString();
    }
}

/* 
==================================================
## Code Summary
==================================================

1. Check if the input array is empty or null. If so, return "".

2. Sort the array of strings lexicographically.

3. Extract the first and last strings in the sorted array.

4. Compare characters of the first and last strings index by index.

5. Append matching characters to `result`. Stop when characters differ.

6. Return `result` as the longest common prefix.

==================================================
## Iteration
==================================================

Input:

strs = ["club", "clap", "clove"]

--------------------------------------------------
### Step 1: Sorting
--------------------------------------------------

Sorted array:

strs = ["clap", "clove", "club"]

first = "clap"
last  = "club"

--------------------------------------------------
### Iteration 1
--------------------------------------------------

i = 0

first[0] = 'c'
last[0]  = 'c'

Characters match → append 'c'

result = "c"

--------------------------------------------------
### Iteration 2
--------------------------------------------------

i = 1

first[1] = 'l'
last[1]  = 'l'

Characters match → append 'l'

result = "cl"

--------------------------------------------------
### Iteration 3
--------------------------------------------------

i = 2

first[2] = 'a'
last[2]  = 'u'

Characters do not match → break loop

--------------------------------------------------
## Final Result
--------------------------------------------------

Common Prefix:

"cl"

Return:

"cl"

==================================================
## Important Point
==================================================

If there is no common prefix between the first and
last strings, the loop terminates immediately at index 0.

Example:

["dog", "racecar", "car"]

Sorted:

["car", "dog", "racecar"]

first = "car", last = "racecar"
'c' != 'r' → returns ""

==================================================
## Why This Works
==================================================

Lexicographical sorting places the two strings with
the maximum difference at the very ends of the array.

Any prefix common to both the first and last strings
is guaranteed to be common across all intermediate strings.

==================================================
## Time Complexity
==================================================

O(N * M * log N)

Where:
- N is the number of strings
- M is the maximum length of a string

Sorting takes O(N * log N * M) comparisons, and the
prefix scan takes O(M).

==================================================
## Space Complexity
==================================================

O(M)

We allocate character arrays and a StringBuilder to
store the characters of the prefix.

==================================================
*/
