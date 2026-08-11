class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {

        int m = matrix.length;
        int n = matrix[0].length;

        // Start from the TOP-RIGHT corner.
        int row = 0;
        int col = n - 1;

        // Continue while we are inside the matrix.
        while (row < m && col >= 0) {

            // Target found.
            if (target == matrix[row][col]) {
                return true;

            // Current value is too large.
            // Move LEFT to find a smaller value.
            } else if (target < matrix[row][col]) {
                col--;

            // Current value is too small.
            // Move DOWN to find a larger value.
            } else {
                row++;
            }
        }

        return false;
    }
}

/*
==================================================
## Code Summary
==================================================

The matrix has two properties:

1. Every row is sorted from LEFT → RIGHT.
2. Every column is sorted from TOP → BOTTOM.

Instead of checking every element, we start from
the TOP-RIGHT corner.

Example:

1   4   7
2   5   8
3   6   9

Start at:

7


If target < 7:

Move LEFT.

If target > 7:

Move DOWN.

If target == 7:

Return true.


==================================================
## Why TOP-RIGHT?
==================================================

The TOP-RIGHT element gives us two choices:

        LEFT ←

          7
          ↓
         DOWN

If target is smaller:

LEFT is useful because values decrease.

If target is larger:

DOWN is useful because values increase.


So every step eliminates either:

- an entire column
OR
- an entire row.


==================================================
## Iteration
==================================================

Input:

matrix = [
    [1,  4,  7, 11],
    [2,  5,  8, 12],
    [3,  6,  9, 16],
    [10,13,14,17]
]

target = 5


Start:

row = 0
col = 3

Current:

matrix[0][3] = 11


Compare:

5 < 11


So 11 is too large.

Move LEFT:

col = 2


Current:

matrix[0][2] = 7


Compare:

5 < 7


Move LEFT:

col = 1


Current:

matrix[0][1] = 4


Compare:

5 > 4


So 4 is too small.

Move DOWN:

row = 1


Current:

matrix[1][1] = 5


Compare:

5 == 5


Return:

true


==================================================
## Another Simple Example
==================================================

target = 13


Start:

11


13 > 11

Move DOWN.


Current:

12


13 > 12

Move DOWN.


Current:

16


13 < 16

Move LEFT.


Current:

14


13 < 14

Move LEFT.


Current:

13


Found.

Return true.


==================================================
## Why Does This Work?
==================================================

Suppose current value is:

7


If:

target < 7


Because the row is sorted:

values to the RIGHT of 7 are:

8, 9, ...

They are all greater than target.

So we can safely eliminate the entire
RIGHT side.

Therefore:

col--


If:

target > 7


Because the column is sorted:

values ABOVE 7 are smaller.

So we can move DOWN.

Therefore:

row++


==================================================
## Time Complexity
==================================================

At every step we either:

Move LEFT
OR
Move DOWN.


We can move at most:

N columns + M rows


Therefore:

Time = O(M + N)


==================================================
## Space Complexity
==================================================

Only a few variables are used:

row
col
m
n


Therefore:

Space = O(1)


==================================================
## Easy Way to Remember
==================================================

Start:

TOP-RIGHT


Then:

target < current
        ↓
      LEFT

target > current
        ↓
      DOWN

target == current
        ↓
      FOUND


Pattern:

TOP-RIGHT → LEFT or DOWN

==================================================
*/
