import java.util.*;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        // Base case
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }

        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            // Sort the characters to create the signature key
            char[] arr = s.toCharArray();
            Arrays.sort(arr);
            String signature = String.valueOf(arr);

            // Group the original string under its sorted signature
            map.computeIfAbsent(signature, k -> new ArrayList<>()).add(s);
        }

        return new ArrayList<>(map.values());
    }
}

/* 
==================================================
## Code Summary
==================================================

1. Validate the input array. If null or empty, return an empty list.

2. Create a HashMap `map` where:
   - Key   -> sorted character string (anagram signature)
   - Value -> list of strings matching that signature

3. Iterate through each string `s` in `strs`:
   - Convert `s` to a character array and sort it.
   - Convert the sorted character array back to a String (`signature`).
   - Use `computeIfAbsent` to retrieve or initialize the list for that key, then add `s`.

4. Return all grouped values as a `List<List<String>>`.

==================================================
## Iteration
==================================================

Input:

strs = ["eat", "tea", "tan", "ate", "nat", "bat"]

--------------------------------------------------
### Iteration 1
--------------------------------------------------

s = "eat"

Sorted characters:
['a', 'e', 't'] -> signature = "aet"

Map State:
{
  "aet": ["eat"]
}

--------------------------------------------------
### Iteration 2
--------------------------------------------------

s = "tea"

Sorted characters:
['a', 'e', 't'] -> signature = "aet"

Map State:
{
  "aet": ["eat", "tea"]
}

--------------------------------------------------
### Iteration 3
--------------------------------------------------

s = "tan"

Sorted characters:
['a', 'n', 't'] -> signature = "ant"

Map State:
{
  "aet": ["eat", "tea"],
  "ant": ["tan"]
}

--------------------------------------------------
### Iteration 4
--------------------------------------------------

s = "ate"

Sorted characters:
['a', 'e', 't'] -> signature = "aet"

Map State:
{
  "aet": ["eat", "tea", "ate"],
  "ant": ["tan"]
}

--------------------------------------------------
### Iteration 5
--------------------------------------------------

s = "nat"

Sorted characters:
['a', 'n', 't'] -> signature = "ant"

Map State:
{
  "aet": ["eat", "tea", "ate"],
  "ant": ["tan", "nat"]
}

--------------------------------------------------
### Iteration 6
--------------------------------------------------

s = "bat"

Sorted characters:
['a', 'b', 't'] -> signature = "abt"

Map State:
{
  "aet": ["eat", "tea", "ate"],
  "ant": ["tan", "nat"],
  "abt": ["bat"]
}

--------------------------------------------------
## Final Result
--------------------------------------------------

Grouped Anagrams:

[
  ["eat", "tea", "ate"],
  ["tan", "nat"],
  ["bat"]
]

==================================================
## Important Point
==================================================

`map.computeIfAbsent(signature, k -> new ArrayList<>()).add(s)`
replaces the traditional `if (!map.containsKey(...))` pattern,
making the insertion concise and idiomatic.

==================================================
## Why This Works
==================================================

All anagrams share the exact same characters in equal counts.
Sorting any two anagrams produces identical strings, making the
sorted string a unique and reliable hash map key.

==================================================
## Time Complexity
==================================================

O(N * K log K)

Where:
- N is the number of strings in `strs`
- K is the maximum length of a string

Sorting each string takes O(K log K), repeated for N strings.

==================================================
## Space Complexity
==================================================

O(N * K)

Required to store the map keys and the resulting lists containing
all original strings.

==================================================
*/
