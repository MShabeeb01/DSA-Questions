public class Solution {

    // ==========================================================
    // LeetCode 74: Search a 2D Matrix (Flattened Binary Search)
    // Time Complexity: O(log(m * n)) | Space Complexity: O(1) auxiliary
    // ==========================================================
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int n = matrix.length;       // Number of rows
        int m = matrix[0].length;    // Number of columns

        int low = 0;
        int high = (n * m) - 1;      // Last index in virtual 1D array

        while (low <= high) {
            // Prevent integer overflow for large dimensions
            int mid = low + (high - low) / 2;

            // Map 1D index to 2D matrix coordinates: (row, col)
            int row = mid / m;
            int col = mid % m;

            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] < target) {
                low = mid + 1;       // Target lies in the right partition
            } else {
                high = mid - 1;      // Target lies in the left partition
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        int[][] matrix1 = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };

        int target1 = 3;
        System.out.println("Search " + target1 + " in matrix:");
        System.out.println("Found: " + solver.searchMatrix(matrix1, target1));
        // Output: true

        int target2 = 13;
        System.out.println("\nSearch " + target2 + " in matrix:");
        System.out.println("Found: " + solver.searchMatrix(matrix1, target2));
        // Output: false

        // Single element matrix test case:
        int[][] matrix2 = {{1}};
        int target3 = 1;
        System.out.println("\nSearch " + target3 + " in single-cell matrix:");
        System.out.println("Found: " + solver.searchMatrix(matrix2, target3));
        // Output: true
    }
}

/*
==================== SUMMARY ====================

Topic: LeetCode 74 — Search a 2D Matrix (Flattened Binary Search)

Core Strategy:
1. Virtual 1D Array Flattening:
   - The matrix rows are sorted left-to-right, and each row's first element strictly exceeds the prior row's last element.
   - Hence, treating the $n \times m$ grid as a continuous 1D sorted array of length $n \cdot m$ preserves total ascending order.
2. Direct 1D-to-2D Coordinate Projection:
   - Instead of allocating extra memory to flatten the matrix, project any virtual index `mid` directly using modular arithmetic:
     - `row = mid / m`
     - `col = mid % m`
3. Standard Binary Search:
   - Maintain search boundaries `low = 0` and `high = (n * m) - 1`.
   - Narrow the search window by half on every comparison until `target` is found or bounds cross.

Key Implementation Details:
- Mid Calculation:
  - Using `mid = low + (high - low) / 2` safeguards against signed 32-bit integer overflow when $(low + high) > 2^{31} - 1$.
- Boundary Guards:
  - Check for `null` or empty matrices (`matrix.length == 0 || matrix[0].length == 0`) before accessing grid lengths to avoid runtime exceptions.

-------------------------------------------------

Virtual 1D Mapping & Search Progression

Input Matrix (n = 3, m = 4), Target = 3:
  Row 0: [ 1,  3,  5,  7]
  Row 1: [10, 11, 16, 20]
  Row 2: [23, 30, 34, 60]

Virtual 1D Array Representation:
  Index:   0   1   2   3   4   5   6   7   8   9  10  11
  Value: [ 1,  3,  5,  7, 10, 11, 16, 20, 23, 30, 34, 60]

Trace:
---------------------------------------------------------------------------------------------------------
Iteration | low | high | mid | Row (mid / 4) | Col (mid % 4) | matrix[row][col] | Action Taken
---------------------------------------------------------------------------------------------------------
1         | 0   | 11   | 5   | 1             | 1             | 11               | 11 > 3 -> high = mid - 1 = 4
2         | 0   | 4    | 2   | 0             | 2             | 5                | 5 > 3  -> high = mid - 1 = 1
3         | 0   | 1    | 0   | 0             | 0             | 1                | 1 < 3  -> low = mid + 1 = 1
4         | 1   | 1    | 1   | 0             | 1             | 3                | 3 == 3 -> Found! (return true)
---------------------------------------------------------------------------------------------------------

-------------------------------------------------

Complexity Analysis:
- Time Complexity : O(log(m * n))
  - Halves the total search space of $m \cdot n$ elements at each step.
  - Equivalent to $O(\log m + \log n)$.
- Space Complexity: O(1) auxiliary space
  - Solved in-place using only scalar pointer variables (`low`, `high`, `mid`, `row`, `col`).
=================================================
*/
