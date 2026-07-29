class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;

        int low = 0;
        int high = n * m - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            int row = mid / m;
            int col = mid % m;

            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return false;
    }
}

/*
-----------------------------------------
Code Summary
-----------------------------------------
1. Consider the entire matrix as a single sorted 1D array.
2. Apply Binary Search on indices from 0 to (rows × columns - 1).
3. Find the middle index.
4. Convert the middle index into row and column.
5. Compare the current element with the target.
6. If the target is greater, search the right half.
7. If the target is smaller, search the left half.
8. If the target is found, return true.
9. If the search ends, return false.

-----------------------------------------
Iteration
-----------------------------------------

Matrix

 1   3   5   7
10  11  16  20
23  30  34  60

Target = 16

Virtual 1D Array

Index :  0  1  2  3  4  5  6  7  8  9 10 11
Value :  1  3  5  7 10 11 16 20 23 30 34 60

Iteration 1
------------
low = 0
high = 11

mid = (0 + 11) / 2 = 5

row = 5 / 4 = 1
col = 5 % 4 = 1

matrix[1][1] = 11

11 < 16

low = 6

Iteration 2
------------
low = 6
high = 11

mid = (6 + 11) / 2 = 8

row = 8 / 4 = 2
col = 8 % 4 = 0

matrix[2][0] = 23

23 > 16

high = 7

Iteration 3
------------
low = 6
high = 7

mid = (6 + 7) / 2 = 6

row = 6 / 4 = 1
col = 6 % 4 = 2

matrix[1][2] = 16

Target Found ✓

-----------------------------------------
Time Complexity
-----------------------------------------
Binary Search : O(log(m × n))

Overall       : O(log(m × n))

-----------------------------------------
Space Complexity
-----------------------------------------
Auxiliary Space : O(1)
*/
