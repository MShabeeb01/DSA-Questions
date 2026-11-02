import java.util.*;

class Solution {
    public List<List<String>> groupAnagrams(String[] arr) {

        // Base check: If array is empty, return empty list.
        if (arr == null || arr.length == 0) {
            return new ArrayList<>();
        }

        // Map canonical sorted string -> list of matching original anagrams.
        Map<String, List<String>> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            String str = arr[i];

            // Convert string into character array and sort it.
            char[] chars = str.toCharArray();
            Arrays.sort(chars);

            // Reconstruct the sorted string to use as the map key.
            String sortedStr = new String(chars);

            // If key does not exist, initialize a new list.
            if (!map.containsKey(sortedStr)) {
                map.put(sortedStr, new ArrayList<>());
            }

            // Add original string to its anagram group.
            map.get(sortedStr).add(str);
        }

        // Return all grouped anagram lists.
        return new ArrayList<>(map.values());
    }
}

/*
==================================================
## Code Summary
==================================================

1. Initialize a HashMap to store sorted strings as keys and lists of original words as values.

2. Loop through each string in the array.

3. Convert each string to a character array and sort it to get its canonical anagram key.

4. Check if the sorted key exists in the HashMap:
   - If not, create a new list for it.

5. Add the original word to the list corresponding to that key.

6. Return all values from the HashMap as a list of lists.

==================================================
## Step-by-Step Iteration Trace
==================================================

Input: arr = ["eat", "tea", "tan", "ate", "nat", "bat"]
Initial Map: {}

--------------------------------------------------
Iteration 0: (i = 0)
--------------------------------------------------
str       = "eat"
chars     = ['e', 'a', 't']
Sorted    = ['a', 'e', 't']
sortedStr = "aet"

Check: Does map contain "aet"? -> NO
Action: map.put("aet", new ArrayList<>())
Action: map.get("aet").add("eat")

Map State:
{
  "aet": ["eat"]
}

--------------------------------------------------
Iteration 1: (i = 1)
--------------------------------------------------
str       = "tea"
chars     = ['t', 'e', 'a']
Sorted    = ['a', 'e', 't']
sortedStr = "aet"

Check: Does map contain "aet"? -> YES
Action: map.get("aet").add("tea")

Map State:
{
  "aet": ["eat", "tea"]
}

--------------------------------------------------
Iteration 2: (i = 2)
--------------------------------------------------
str       = "tan"
chars     = ['t', 'a', 'n']
Sorted    = ['a', 'n', 't']
sortedStr = "ant"

Check: Does map contain "ant"? -> NO
Action: map.put("ant", new ArrayList<>())
Action: map.get("ant").add("tan")

Map State:
{
  "aet": ["eat", "tea"],
  "ant": ["tan"]
}

--------------------------------------------------
Iteration 3: (i = 3)
--------------------------------------------------
str       = "ate"
chars     = ['a', 't', 'e']
Sorted    = ['a', 'e', 't']
sortedStr = "aet"

Check: Does map contain "aet"? -> YES
Action: map.get("aet").add("ate")

Map State:
{
  "aet": ["eat", "tea", "ate"],
  "ant": ["tan"]
}

--------------------------------------------------
Iteration 4: (i = 4)
--------------------------------------------------
str       = "nat"
chars     = ['n', 'a', 't']
Sorted    = ['a', 'n', 't']
sortedStr = "ant"

Check: Does map contain "ant"? -> YES
Action: map.get("ant").add("nat")

Map State:
{
  "aet": ["eat", "tea", "ate"],
  "ant": ["tan", "nat"]
}

--------------------------------------------------
Iteration 5: (i = 5)
--------------------------------------------------
str       = "bat"
chars     = ['b', 'a', 't']
Sorted    = ['a', 'b', 't']
sortedStr = "abt"

Check: Does map contain "abt"? -> NO
Action: map.put("abt", new ArrayList<>())
Action: map.get("abt").add("bat")

Map State:
{
  "aet": ["eat", "tea", "ate"],
  "ant": ["tan", "nat"],
  "abt": ["bat"]
}

--------------------------------------------------
Final Return: new ArrayList<>(map.values())
--------------------------------------------------
[
  ["eat", "tea", "ate"],
  ["tan", "nat"],
  ["bat"]
]

==================================================
## Why Does Sorting Work?
==================================================

All anagrams share the exact same characters with the
exact same frequencies.

When sorted alphabetically, all words of the same
anagram family produce the identical string:

"eat" -> "aet"
"tea" -> "aet"
"ate" -> "aet"

This unique sorted string acts as the common key
to group all original words together in the HashMap.

==================================================
## Time Complexity
==================================================

O(N * K log K)

Where:
N = number of strings in the array.
K = maximum length of a string.

For each of the N strings, sorting its characters
takes O(K log K) time.

==================================================
## Space Complexity
==================================================

O(N * K)

Because we store all original strings and their sorted keys
inside the HashMap.
*/
