class Solution { 
    public boolean checkInclusion(String s1, String s2) { 
 
        if (s1.length() > s2.length()) { 
            return false; 
        } 
 
        int freq[] = new int[26]; 
 
        // Store frequency of characters in s1.
        for (char ch : s1.toCharArray()) { 
            freq[ch - 'a']++; 
        } 
 
        int left = 0; 
 
        // Sliding window over s2.
        for (int right = 0; right < s2.length(); right++) { 
 
            // Remove the current character from frequency.
            freq[s2.charAt(right) - 'a']--; 
 
            // If window becomes bigger than s1,
            // remove the leftmost character.
            if (right - left + 1 > s1.length()) { 
                freq[s2.charAt(left) - 'a']++; 
                left++; 
            } 
 
            // If all frequencies are zero,
            // current window is a permutation of s1.
            if (right - left + 1 == s1.length() && allZero(freq)) { 
                return true; 
            } 
        } 
 
        return false; 
    } 
 
    // Check whether all frequency values are zero.
    private boolean allZero(int[] freq) { 
 
        for (int value : freq) { 
            if (value != 0) { 
                return false; 
            } 
        } 
 
        return true; 
    } 
} 
 
/* 
================================================== 
## Code Summary 
================================================== 
 
1. Store the frequency of every character in s1 
   using a frequency array of size 26. 
 
2. Use a sliding window on s2. 
 
3. The window size is always equal to s1.length(). 
 
4. When a character enters the window, decrease 
   its frequency. 
 
5. When a character leaves the window, increase 
   its frequency again. 
 
6. If all values in freq[] become 0, the current 
   window contains exactly the same characters as s1. 
 
7. Therefore, the current window is a permutation 
   of s1. 
 
================================================== 
## Iteration 
================================================== 
 
Input: 
 
s1 = "ab" 
s2 = "eidbaooo" 
 
Required window size: 
 
s1.length() = 2 
 
Initial frequency: 
 
a = 1 
b = 1 
 
-------------------------------------------------- 
### right = 0 
-------------------------------------------------- 
 
Character: 
 
'e' 
 
Window: 
 
"e" 
 
Window size = 1 
 
Not enough characters yet. 
 
-------------------------------------------------- 
### right = 1 
-------------------------------------------------- 
 
Character: 
 
'i' 
 
Window: 
 
"ei" 
 
Window size = 2 
 
Compare frequencies. 
 
"ei" does not contain the same characters 
as "ab". 
 
So: 
 
"ei" ❌ 
 
-------------------------------------------------- 
### right = 2 
-------------------------------------------------- 
 
Character: 
 
'd' 
 
Window temporarily becomes: 
 
"eid" 
 
Window size = 3. 
 
But required size is 2. 
 
So remove the leftmost character 'e'. 
 
Window becomes: 
 
"id" 
 
So: 
 
"id" ❌ 
 
-------------------------------------------------- 
### right = 3 
-------------------------------------------------- 
 
Character: 
 
'b' 
 
Window temporarily becomes: 
 
"idb" 
 
Remove the leftmost character 'i'. 
 
Window becomes: 
 
"db" 
 
So: 
 
"db" ❌ 
 
-------------------------------------------------- 
### right = 4 
-------------------------------------------------- 
 
Character: 
 
'a' 
 
Window temporarily becomes: 
 
"dba" 
 
Remove the leftmost character 'd'. 
 
Window becomes: 
 
"ba" 
 
Now compare: 
 
s1     = "ab" 
window = "ba" 
 
Both contain: 
 
a = 1 
b = 1 
 
All values in freq[] are now 0. 
 
Therefore: 
 
"ba" is a permutation of "ab". 
 
Return: 
 
true 
 
================================================== 
## Why This Works 
================================================== 
 
A permutation contains exactly the same characters 
with exactly the same frequencies. 
 
For example: 
 
"ab" 
"ba" 
 
Both contain: 
 
a -> 1 
b -> 1 
 
The order is different, but the frequencies are 
the same. 
 
The frequency array helps us check this efficiently. 
 
If every value in freq[] is 0, it means the current 
window and s1 contain exactly the same characters. 
 
================================================== 
## Why Sliding Window? 
================================================== 
 
We only need to check substrings whose length is 
equal to s1.length(). 
 
Instead of creating every substring separately, 
we maintain one window. 
 
When right moves: 
 
A new character enters. 
 
When the window becomes too large: 
 
The leftmost character leaves. 
 
This allows us to process s2 efficiently. 
 
================================================== 
## Time Complexity 
================================================== 
 
O(26 × N) 
 
Since there are only 26 lowercase letters, 
this becomes: 
 
O(N) 
 
where N is the length of s2. 
 
================================================== 
## Space Complexity 
================================================== 
 
O(26) 
 
The frequency array contains only 26 positions. 
 
Therefore: 
 
O(1) 
 
================================================== 
*/
