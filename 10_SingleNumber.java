class Solution {
    public int singleNumber(int[] nums) {

        int ans = 0; // Stores the final unique number

        for (int val : nums) { // Traverse every element in the array
            ans = ans ^ val;   // XOR current element with ans
        }

        return ans; // Return the single number
    }
}

/*
==================== SUMMARY ====================

Approach:
1. Initialize ans = 0.
2. Traverse every element in the array.
3. XOR each element with ans.
4. Duplicate numbers cancel each other:
      x ^ x = 0
5. XOR with 0 keeps the number unchanged:
      x ^ 0 = x
6. After all duplicates are removed, only the unique number remains.
7. Return ans.

-------------------------------------------------

XOR Rules

x ^ x = 0
x ^ 0 = x

Example:

5 ^ 5 = 0
7 ^ 0 = 7

-------------------------------------------------

Iteration

Input:

nums = [4,1,2,1,2]

ans = 0

0 ^ 4 ^ 1 ^ 2 ^ 1 ^ 2

= 0 ^ 4 ^ (1 ^ 1) ^ (2 ^ 2)

= 0 ^ 4 ^ 0 ^ 0

= 4

Output:

4

-------------------------------------------------

Time Complexity : O(n)

Space Complexity : O(1)

=================================================
*/
