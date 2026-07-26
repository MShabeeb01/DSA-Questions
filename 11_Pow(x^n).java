class Solution {
    public double myPow(double x, int n) {

        long binForm = n; // Store n as long to avoid overflow

        if (n < 0) { // Handle negative powers
            x = 1 / x;
            binForm = -binForm;
        }

        double ans = 1; // Stores the final answer

        while (binForm > 0) {

            if (binForm % 2 == 1) { // If power is odd
                ans *= x;           // Include current power
            }

            x *= x;          // Square the base
            binForm /= 2;    // Move to the next binary bit
        }

        return ans; // Return xⁿ
    }
}

/*
==================== SUMMARY ====================

Approach:
1. Store n as long to avoid integer overflow.
2. If n is negative:
      - Convert x to 1/x.
      - Make n positive.
3. Initialize ans = 1.
4. While power > 0:
      - If power is odd, multiply ans by x.
      - Square x.
      - Divide power by 2.
5. Return ans.

-------------------------------------------------

Key Idea (Binary Exponentiation)

Instead of multiplying x, n times,

x × x × x × x × ...

We keep squaring the base.

Example:

2
↓

2² = 4
↓

4² = 16
↓

16² = 256

This reduces the time from O(n) to O(log n).

-------------------------------------------------

Iteration

Input:

x = 2
n = 13

Binary of 13 = 1101

Initial:

ans = 1
x = 2
power = 13

----------------------------------------------------------------
Power | Odd? | ans = ans*x | x = x*x | power = power/2
----------------------------------------------------------------
13    | Yes  | 1 × 2 = 2   |    4    |      6
6     | No   |      2      |   16    |      3
3     | Yes  | 2 ×16 = 32  |  256    |      1
1     | Yes  |32×256=8192  |65536    |      0
----------------------------------------------------------------

Output:

8192

-------------------------------------------------

Negative Power Example

Input:

x = 2
n = -3

Convert

x = 1/2 = 0.5
power = 3

0.5 × 0.5 × 0.5 = 0.125

Output:

0.125

-------------------------------------------------

Time Complexity : O(log n)

Space Complexity : O(1)

=================================================
*/
