class Solution {
    public String longestCommonPrefix(String[] strs) {
        // Base edge case: If the input array is empty, no common prefix exists.
        if (strs.length == 0)
            return "";

        // Assume the entire first word is the common prefix candidate.
        String prefix = strs[0];

        // Compare the candidate prefix against every subsequent word.
        for (int i = 1; i < strs.length; i++) {
            // Trim the prefix character-by-character from the back until strs[i] starts with it.
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);

                // Early exit: If the prefix shrinks down to nothing, no common prefix exists.
                if (prefix.isEmpty())
                    return "";
            }
        }

        return prefix;
    }
}

/*
========================================================================================
## Code Summary
========================================================================================

1. Base Edge Check:
   If `strs` has length 0, return `""` immediately.

2. Candidate Initialization:
   Set `prefix = strs[0]`. The longest common prefix of all words can never be longer 
   than the first word itself.

3. Horizontal Scanning Loop:
   Iterate from index `1` to `strs.length - 1`:
   - While the current string `strs[i]` does not start with `prefix`:
     - Shorten `prefix` by cutting off its last character: 
       `prefix = prefix.substring(0, prefix.length() - 1)`
     - If `prefix` becomes empty (`""`), return `""` immediately (early termination).

4. Return Result:
   Once all strings have been processed against the trimmed candidate, return `prefix`.

========================================================================================
## Detailed Visual Step-by-Step Trace
========================================================================================

Input:
  strs = ["flower", "flow", "flight"]
  strs.length = 3

Initial State:
  prefix = strs[0] = "flower"

----------------------------------------------------------------------------------------
ITERATION 1: i = 1 (Comparing against strs[1] = "flow")
----------------------------------------------------------------------------------------
Target word: "flow"
Current prefix: "flower"

- Check: "flow".startsWith("flower")?
  -> FALSE ("flower" is longer than "flow")
  Action: Trim last character ('r')
  prefix = "flower".substring(0, 5) => "flowe"
  prefix.isEmpty()? -> false

- Check: "flow".startsWith("flowe")?
  -> FALSE
  Action: Trim last character ('e')
  prefix = "flowe".substring(0, 4) => "flow"
  prefix.isEmpty()? -> false

- Check: "flow".startsWith("flow")?
  -> TRUE!
  Inner while-loop terminates.

End of Iteration 1:
  prefix is now: "flow"

----------------------------------------------------------------------------------------
ITERATION 2: i = 2 (Comparing against strs[2] = "flight")
----------------------------------------------------------------------------------------
Target word: "flight"
Current prefix: "flow"

- Check: "flight".startsWith("flow")?
  -> FALSE (characters at index 2 mismatch: 'o' vs 'i')
  Action: Trim last character ('w')
  prefix = "flow".substring(0, 3) => "flo"
  prefix.isEmpty()? -> false

- Check: "flight".startsWith("flo")?
  -> FALSE (characters at index 2 mismatch: 'o' vs 'i')
  Action: Trim last character ('o')
  prefix = "flo".substring(0, 2) => "fl"
  prefix.isEmpty()? -> false

- Check: "flight".startsWith("fl")?
  -> TRUE!
  Inner while-loop terminates.

End of Iteration 2:
  prefix is now: "fl"

----------------------------------------------------------------------------------------
LOOP TERMINATION & RETURN
----------------------------------------------------------------------------------------
Outer loop finishes (i reaches strs.length = 3).
Final return value: "fl"

========================================================================================
## Variable State Summary Table
========================================================================================

| Step / Action                     | i  | strs[i]   | prefix Before Action | startsWith? | prefix After Action |
|:----------------------------------|:---|:----------|:---------------------|:------------|:--------------------|
| Initialize                        | -  | -         | -                    | -           | "flower"            |
| i = 1 (Cut 'r')                   | 1  | "flow"    | "flower"             | false       | "flowe"             |
| i = 1 (Cut 'e')                   | 1  | "flow"    | "flowe"              | false       | "flow"              |
| i = 1 (Match)                     | 1  | "flow"    | "flow"               | true        | "flow"              |
| i = 2 (Cut 'w')                   | 2  | "flight"  | "flow"               | false       | "flo"               |
| i = 2 (Cut 'o')                   | 2  | "flight"  | "flo"                | false       | "fl"                |
| i = 2 (Match)                     | 2  | "flight"  | "fl"                 | true        | "fl"                |
| Return                            | -  | -         | -                    | -           | "fl"                |

========================================================================================
## Why Does Horizontal Scanning Work?
========================================================================================

Associative Property:
  LCP(S1, S2, S3) = LCP(LCP(S1, S2), S3)

Finding the common prefix of the entire array can be done incrementally:
1. Find the common prefix between the first two words: `P = LCP(S1, S2)`.
2. The global prefix can never be longer than `P`.
3. Compare `P` with the third word: `P = LCP(P, S3)`.
4. Continue until all words are checked or `P` becomes empty.

========================================================================================
## Time Complexity
========================================================================================

O(S)

Where:
  N = number of strings in strs
  M = length of the shortest/first string
  S = total sum of all characters across all strings

- Worst Case: All strings are identical or share a very long prefix. Every character
  comparison in `startsWith` runs through length M across all N words: O(N * M) = O(S).
- Best Case: strs[1] shares no characters with strs[0] (e.g., ["dog", "racecar"]).
  `prefix` rapidly trims down to "" in O(M) time, terminating on the first iteration.

========================================================================================
## Space Complexity
========================================================================================

O(1) Auxiliary Space (ignoring string creation overhead) / O(M) in Java heap

- The algorithm only uses a single pointer `i` and variable `prefix`.
- In Java, `substring()` allocates a new String on each trim step (taking up to O(M) 
  temporary heap memory), but no auxiliary data structures (like arrays or maps) are used.
*/
