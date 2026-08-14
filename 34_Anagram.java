import java.util.*;

class Solution {
    public boolean isAnagram(String s, String t) {

        // If lengths are different, they cannot be anagrams.
        if (s.length() != t.length()) {
            return false;
        }

        // Convert both strings into character arrays.
        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();

        // Sort both arrays.
        Arrays.sort(sArray);
        Arrays.sort(tArray);

        // If sorted arrays are equal, they are anagrams.
        return Arrays.equals(sArray, tArray);
    }
}

/*
==================================================
## Code Summary
==================================================

1. First check whether both strings have the same length.

2. Convert both strings into character arrays.

3. Sort both character arrays.

4. Compare the sorted arrays.

5. If they are equal → Anagram.
   If they are different → Not an Anagram.

==================================================
## Easy Example
==================================================

s = "anagram"
t = "nagaram"

After converting and sorting:

sArray = [a, a, a, g, m, n, r]
tArray = [a, a, a, g, m, n, r]

Both are equal.

Answer:

true

==================================================
## Why Does Sorting Work?
==================================================

Anagrams contain the same characters with the
same frequency.

For example:

"listen"
"silent"

After sorting:

"eilnst"
"eilnst"

So they are anagrams.

==================================================
## Time Complexity
==================================================

O(N log N)

Because we sort both character arrays.

==================================================
## Space Complexity
==================================================

O(N)

Because we create two character arrays.
*/
