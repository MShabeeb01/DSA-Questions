public class Solution {

    // ==========================================================
    // LeetCode 74: Search a 2D Matrix (Virtual 1D Binary Search)
    // Time Complexity: O(log(m * n)) | Space Complexity: O(1) auxiliary
    // ==========================================================
    public boolean searchMatrix(int[][] matrix, int target) {
        // Base edge case: null or empty matrix check
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int n = matrix.length;       // Number of rows
        int m = matrix[0].length;    // Number of columns

        // Virtual 1D array search boundaries
        int low = 0;
        int high = (n * m) - 1;

        while (low <= high) {
            // Avoid integer overflow for very large dimensions
            int mid = low + (high - low) / 2;

            // Mathematical projection from 1D index to 2D matrix coordinates
            int row = mid / m; // How many full rows of size 'm' fit before 'mid'
            int col = mid % m; // Remaining column offset inside that row

            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] < target) {
                low = mid + 1; // Target is strictly greater -> search right partition
            } else {
                high = mid - 1; // Target is strictly smaller -> search left partition
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1: Standard 3x4 Matrix
        int[][] matrix1 = {
            { 1,  3,  5,  7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };

        int target1 = 3;
        System.out.println("Test Case 1 (Target: " + target1 + "):");
        System.out.println("Result: " + solver.searchMatrix(matrix1, target1));
        // Expected Output: true

        int target2 = 13;
        System.out.println("\nTest Case 2 (Target: " + target2 + "):");
        System.out.println("Result: " + solver.searchMatrix(matrix1, target2));
        // Expected Output: false

        // Test Case 3: 1x1 Boundary Matrix
        int[][] matrix2 = {{5}};
        int target3 = 5;
        System.out.println("\nTest Case 3 (Single Element Match - Target: " + target3 + "):");
        System.out.println("Result: " + solver.searchMatrix(matrix2, target3));
        // Expected Output: true

        // Test Case 4: Element smaller than matrix min
        int target4 = 0;
        System.out.println("\nTest Case 4 (Target below minimum - Target: " + target4 + "):");
        System.out.println("Result: " + solver.searchMatrix(matrix1, target4));
        // Expected Output: false

        // Test Case 5: Element larger than matrix max
        int target5 = 100;
        System.out.println("\nTest Case 5 (Target above maximum - Target: " + target5 + "):");
        System.out.println("Result: " + solver.searchMatrix(matrix1, target5));
        // Expected Output: false
    }
}

/*
==================== SUMMARY ====================

Topic: LeetCode 74 — Search a 2D Matrix (Virtual 1D Array Binary Search)

Core Philosophy:
- An $n \times m$ matrix where every row is sorted in non-decreasing order and the first element
  of every row is strictly greater than the last element of the preceding row forms a continuous
  strictly ascending sequence across all $n \cdot m$ elements.
- Instead of allocating an auxiliary 1D array of size $n \cdot m$ (which would cost $O(n \cdot m)$ time
  and space to copy), we perform **Virtual Flattening** using standard modular arithmetic.

Mathematical Projection Invariant:
For any virtual 1D index `mid` in range $[0, (n \cdot m) - 1]$:
  1. `row = mid / m`
     - Integer division computes the row index by counting complete row blocks of size $m$.
  2. `col = mid % m`
     - Modulo computes the zero-indexed column offset inside that row block.

-------------------------------------------------

Step-by-Step Dry Run Example

Matrix ($n = 3, m = 4$):
  Row 0: [ 1,  3,  5,  7]
  Row 1: [10, 11, 16, 20]
  Row 2: [23, 30, 34, 60]

Target: 3
Total Range: [low = 0, high = 11]

---------------------------------------------------------------------------------------------------------
Iteration | low | high | mid = low+(high-low)/2 | row = mid/4 | col = mid%4 | matrix[row][col] | Action
---------------------------------------------------------------------------------------------------------
1         | 0   | 11   | 5                      | 1           | 1           | 11               | 11 > 3 -> high = 4
2         | 0   | 4    | 2                      | 0           | 2           | 5                | 5 > 3  -> high = 1
3         | 0   | 1    | 0                      | 0           | 0           | 1                | 1 < 3  -> low  = 1
4         | 1   | 1    | 1                      | 0           | 1           | 3                | 3 == 3 -> Found! (return true)
---------------------------------------------------------------------------------------------------------

-------------------------------------------------

Edge Cases Handled:
1. Matrix is null or dimensions are zero (`matrix.length == 0 || matrix[0].length == 0`).
2. Single-element matrix ($1 \times 1$).
3. Target is smaller than the smallest element (`matrix[0][0]`).
4. Target is larger than the largest element (`matrix[n-1][m-1]`).
5. Large dimensions: `mid = low + (high - low) / 2` protects against signed 32-bit integer overflow.

-------------------------------------------------

Complexity Analysis:
- Time Complexity : O(log(n * m))
  - At every step of binary search, the virtual search space of size $n \cdot m$ is halved.
  - Mathematically equivalent to $O(\log n + \log m)$.
- Space Complexity: O(1) auxiliary space
  - Operates completely in-place; only scalar primitive pointer variables (`n`, `m`, `low`, `high`, `mid`, `row`, `col`) are allocated.
=================================================
*/
