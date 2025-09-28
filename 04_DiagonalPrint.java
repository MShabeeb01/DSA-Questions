import java.util.*;

public class ArraysCC {

    // Function to calculate sum of both diagonals of a square matrix
    public static int diagonalSum(int matrix[][]) {
        int sum = 0;

        for (int i = 0; i < matrix.length; i++) {
            /*
             * Primary Diagonal → matrix[i][i]
             * Here, both row index and column index are the same.
             * Example for a 4x4 matrix:
             *   i = 0 → matrix[0][0] = 1
             *   i = 1 → matrix[1][1] = 6
             *   i = 2 → matrix[2][2] = 11
             *   i = 3 → matrix[3][3] = 16
             * These elements form the diagonal from TOP-LEFT to BOTTOM-RIGHT.
             */
            sum += matrix[i][i];

            /*
             * Secondary Diagonal → matrix[i][matrix.length - i - 1]
             * Explanation:
             *   - matrix.length = number of rows/columns (since it’s a square matrix).
             *   - (matrix.length - i - 1) calculates the column index from the right side.
             *
             * Example for a 4x4 matrix (matrix.length = 4):
             *   i = 0 → matrix[0][4 - 0 - 1] = matrix[0][3] = 4
             *   i = 1 → matrix[1][4 - 1 - 1] = matrix[1][2] = 7
             *   i = 2 → matrix[2][4 - 2 - 1] = matrix[2][1] = 10
             *   i = 3 → matrix[3][4 - 3 - 1] = matrix[3][0] = 13
             * These elements form the diagonal from TOP-RIGHT to BOTTOM-LEFT.
             *
             * Note: When the matrix size is odd (like 3x3 or 5x5), the middle element
             * will be common to both diagonals. 
             * Condition (i != matrix.length - i - 1) prevents counting it twice.
             */
            if (i != matrix.length - i - 1) {
                sum += matrix[i][matrix.length - i - 1];
            }
        }
        return sum; // return the total sum of both diagonals
    }

    public static void main(String args[]) {
        // 4x4 square matrix
        int matrix[][] = {
            {1,  2,  3,  4},
            {5,  6,  7,  8},
            {9, 10, 11, 12},
            {13,14, 15, 16}
        };

        // Call the diagonalSum function and store the result
        int result = diagonalSum(matrix);

        // Print the sum of diagonals
        System.out.println("Sum of diagonals: " + result);
    }
}
