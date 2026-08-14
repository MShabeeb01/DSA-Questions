class Solution {
    public String reverseWords(String s) {

        // Remove extra spaces and split the string into words.
        String[] words = s.trim().split("\\s+");

        // Build the answer from right to left.
        StringBuilder result = new StringBuilder();

        for (int i = words.length - 1; i >= 0; i--) {

            // Add a space between words.
            if (result.length() > 0) {
                result.append(" ");
            }

            result.append(words[i]);
        }

        return result.toString();
    }
}

/*
==================================================
## Code Summary
==================================================

1. trim() removes spaces from the beginning and end.

2. split("\\s+") separates the string into words.

   \\s+ means:
   "one or more spaces"

3. Traverse the words from RIGHT to LEFT.

4. Add each word to StringBuilder.

5. Add only one space between words.

==================================================
## Iteration
==================================================

Input:

s = "the sky is blue"

After split:

words = ["the", "sky", "is", "blue"]

We start from the last word.

--------------------------------------------------
### i = 3
--------------------------------------------------

words[3] = "blue"

result:

"blue"

--------------------------------------------------
### i = 2
--------------------------------------------------

words[2] = "is"

Add a space first:

"blue" + " " + "is"

result:

"blue is"

--------------------------------------------------
### i = 1
--------------------------------------------------

words[1] = "sky"

result:

"blue is sky"

--------------------------------------------------
### i = 0
--------------------------------------------------

words[0] = "the"

result:

"blue is sky the"

==================================================
## Example with Extra Spaces
==================================================

Input:

"  hello   world  "

trim():

"hello   world"

split("\\s+"):

["hello", "world"]

Traverse backwards:

"world"
"world hello"

Final:

"world hello"

==================================================
## Why StringBuilder?
==================================================

Strings are immutable in Java.

Repeatedly doing:

result = result + word

creates many new String objects.

StringBuilder is better for repeatedly adding
characters/strings.

==================================================
## Time Complexity
==================================================

O(N)

We process the characters/words of the string.

==================================================
## Space Complexity
==================================================

O(N)

We store the words and the final result.
*/
