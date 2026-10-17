class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        // Base case: check for null or empty matrix
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int n = matrix.length;
        int m = matrix[0].length;

        // Treat the 2D matrix as a virtual 1D sorted array
        int low = 0;
        int high = n * m - 1;

        // Standard Binary Search
        while (low <= high) {
            int mid = low + (high - low) / 2;

            // Map 1D index back to 2D coordinates
            int row = mid / m;
            int col = mid % m;

            // Target found
            if (matrix[row][col] == target) {
                return true;

            // Current value is too small, search the right half
            } else if (matrix[row][col] < target) {
                low = mid + 1;

            // Current value is too large, search the left half
            } else {
                high = mid - 1;
            }
        }

        return false;
    }
}

/*
==================================================
## Code Summary
==================================================

The matrix has the following properties:

1. Integers in each row are sorted from LEFT → RIGHT.
2. The first integer of each row is greater than the
   last integer of the previous row.

This means if we unroll the entire matrix row by row,
it forms a single, strictly ascending 1D array.

Instead of running a 2D traversal, we perform a
standard BINARY SEARCH over the range:

0  →  (N * M - 1)

Example:

[ 1,  3,  5,  7]
[10, 11, 16, 20]
[23, 30, 34, 60]

Virtual 1D Array:

Index:  0   1   2   3   4   5   6   7   8   9  10  11
Value:  1   3   5   7  10  11  16  20  23  30  34  60


==================================================
## Index Mapping Formula
==================================================

Given:

Total columns = M
1D Index = mid

To find the (row, col) position in the 2D matrix:

row = mid / M
col = mid % M


Visual:

        col 0   col 1   col 2   col 3
row 0 [   0       1       2       3   ]
row 1 [   4       5       6       7   ]
row 2 [   8       9      10      11   ]

For mid = 6 and M = 4:

row = 6 / 4 = 1
col = 6 % 4 = 2

Element = matrix[1][2] = 16


==================================================
## Iteration
==================================================

Input:

matrix = [
    [1,   3,  5,  7],
    [10, 11, 16, 20],
    [23, 30, 34, 60]
]

target = 3

Dimensions:

n = 3 (rows)
m = 4 (cols)

low  = 0
high = 3 * 4 - 1 = 11


--- Step 1 ---

low  = 0
high = 11

mid = 0 + (11 - 0) / 2 = 5

row = 5 / 4 = 1
col = 5 % 4 = 1

Current:

matrix[1][1] = 11


Compare:

11 > 3 (target < current)


So 11 is too large.
Eliminate right half.

high = mid - 1 = 4


--- Step 2 ---

low  = 0
high = 4

mid = 0 + (4 - 0) / 2 = 2

row = 2 / 4 = 0
col = 2 % 4 = 2

Current:

matrix[0][2] = 5


Compare:

5 > 3 (target < current)


So 5 is too large.
Eliminate right half.

high = mid - 1 = 1


--- Step 3 ---

low  = 0
high = 1

mid = 0 + (1 - 0) / 2 = 0

row = 0 / 4 = 0
col = 0 % 4 = 0

Current:

matrix[0][0] = 1


Compare:

1 < 3 (target > current)


So 1 is too small.
Eliminate left half.

low = mid + 1 = 1


--- Step 4 ---

low  = 1
high = 1

mid = 1 + (1 - 1) / 2 = 1

row = 1 / 4 = 0
col = 1 % 4 = 1

Current:

matrix[0][1] = 3


Compare:

3 == 3


Return:

true


==================================================
## Why Does This Work?
==================================================

Because row `i` ends with a value smaller than the
start of row `i + 1`, the matrix behaves identically
to a flattened sorted array of length (N * M).

Standard binary search cuts the search space in half
at every step:

                [ mid ]
               /       \
      matrix < target   matrix > target
            ↓                 ↓
       Search RIGHT      Search LEFT
       (low = mid + 1)   (high = mid - 1)


The arithmetic mapping:

mid / M → determines how many full rows fit before mid
mid % M → determines the offset within the current row

This allows O(1) access to any virtual 1D index
without copying or flattening the matrix in memory.


==================================================
## Time Complexity
==================================================

Total elements in virtual array:

N * M


Binary search halves the search space each step:

Time = O(log(N * M))


Which is equivalent to:

Time = O(log N + log M)


==================================================
## Space Complexity
==================================================

Only pointer variables are allocated:

low
high
mid
row
col
n
m


No extra matrix or array is created:

Space = O(1)


==================================================
## Easy Way to Remember
==================================================

Search Space:

0  →  (N * M - 1)


Coordinate Transform:

row = mid / M
col = mid % M


Binary Search Decision:

target < matrix[row][col]
          ↓
     high = mid - 1

target > matrix[row][col]
          ↓
     low = mid + 1

target == matrix[row][col]
          ↓
        FOUND


Pattern:

Flattened Index → (mid / M, mid % M) → Binary Search

==================================================
*/
