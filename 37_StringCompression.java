class Solution { 
    public int compress(char[] chars) { 
        int ans = 0; 
 
        for (int i = 0; i < chars.length;) { 
 
            // Store the current character
            final char letter = chars[i]; 
            int count = 0; 
 
            // Count consecutive occurrences
            while (i < chars.length && chars[i] == letter) { 
                ++count; 
                ++i; 
            } 
 
            // Store the character in compressed position
            chars[ans++] = letter; 
 
            // Store the count only if it is greater than 1
            if (count > 1) { 
                for (final char c : String.valueOf(count).toCharArray()) { 
                    chars[ans++] = c; 
                } 
            } 
        } 
 
        return ans; 
    } 
} 
 
/* 
================================================== 
## Code Summary 
================================================== 
 
1. Use two pointers: 
   - i   -> traverses the original array 
   - ans -> stores the compressed result 
 
2. Pick the current character as `letter`. 
 
3. Count how many times the same character appears 
   consecutively. 
 
4. Store the character at index `ans`. 
 
5. If count > 1, convert the count into characters 
   and store them after the letter. 
 
6. Return `ans`, which represents the length of the 
   compressed array. 
 
================================================== 
## Iteration 
================================================== 
 
Input: 
 
chars = ['a','a','b','b','c','c','c'] 
 
-------------------------------------------------- 
### Iteration 1 
-------------------------------------------------- 
 
letter = 'a' 
 
Count consecutive 'a': 
 
'a' → 2 times 
 
Store: 
 
['a','2'] 
 
ans = 2 
 
-------------------------------------------------- 
### Iteration 2 
-------------------------------------------------- 
 
letter = 'b' 
 
Count consecutive 'b': 
 
'b' → 2 times 
 
Store: 
 
['a','2','b','2'] 
 
ans = 4 
 
-------------------------------------------------- 
### Iteration 3 
-------------------------------------------------- 
 
letter = 'c' 
 
Count consecutive 'c': 
 
'c' → 3 times 
 
Store: 
 
['a','2','b','2','c','3'] 
 
ans = 6 
 
-------------------------------------------------- 
## Final Result 
-------------------------------------------------- 
 
Compressed array: 
 
['a','2','b','2','c','3'] 
 
Return: 
 
6 
 
The first 6 positions contain the compressed result. 
 
================================================== 
## Important Point 
================================================== 
 
If a character appears only once, we do not write 
the number 1. 
 
Example: 
 
"a" → "a" 
 
"aaa" → "a3" 
 
"ab" → "ab" 
 
================================================== 
## Why This Works 
================================================== 
 
The problem asks us to compress consecutive groups 
of the same character. 
 
The inner while loop counts each group, and the 
outer loop moves to the next group. 
 
The `ans` pointer overwrites the original array, 
so no separate result array is required. 
 
================================================== 
## Time Complexity 
================================================== 
 
O(N) 
 
Every character is processed once. 
 
================================================== 
## Space Complexity 
================================================== 
 
O(1) 
 
We modify the input array in-place and use only a 
few variables. 
 
================================================== 
*/
