import java.util.*;

public class ArraysCC {

    // Function to print matrix in spiral order (LeetCode 54)
    public static void printSpiral(int matrix[][]) {
        // Defining the boundaries of the spiral
        int startrow = 0;                     // Starting row index
        int startcol = 0;                     // Starting column index
        int endrow = matrix.length - 1;       // Ending row index
        int endcol = matrix[0].length - 1;    // Ending column index

        // Loop until all rows and columns are covered
        while (startrow <= endrow && startcol <= endcol) {
            
            // 1️⃣ Print the TOP boundary (left → right)
            for (int j = startcol; j <= endcol; j++) {
                System.out.print(matrix[startrow][j] + " ");
            }

            // 2️⃣ Print the RIGHT boundary (top → bottom)
            for (int i = startrow + 1; i <= endrow; i++) {
                System.out.print(matrix[i][endcol] + " ");
            }

            // 3️⃣ Print the BOTTOM boundary (right → left)
            for (int j = endcol - 1; j >= startcol; j--) {
                // If top row = bottom row, then it's already printed
                if (startrow == endrow) {
                    break;
                }
                System.out.print(matrix[endrow][j] + " ");
            }

            // 4️⃣ Print the LEFT boundary (bottom → top)
            for (int i = endrow - 1; i >= startrow + 1; i--) {
                // If left col = right col, then it's already printed
                if (startcol == endcol) {
                    break;
                }
                System.out.print(matrix[i][startcol] + " ");
            }

            // Move inward for the next spiral loop
            startcol++;
            startrow++;
            endcol--;
            endrow--;
        }
        System.out.println();
    }

    public static void main(String args[]) {
        // Example matrix
        int matrix[][] = {
            {1,  2,  3,  4},
            {5,  6,  7,  8},
            {9,  10, 11, 12},
            {13, 14, 15, 16}
        };

        // Function call
        printSpiral(matrix); // Output → 1 2 3 4 8 12 16 15 14 13 9 5 6 7 11 10
    }
}

/*
==================== SUMMARY ====================

Problem: LeetCode 54 - Spiral Matrix

Objective:
Given an m x n matrix, return all elements of the matrix in clockwise spiral order.

Core Logic:
- Uses 4 shifting boundary pointers:
  - `startrow`: Top boundary (moves down after layer complete).
  - `endrow`: Bottom boundary (moves up after layer complete).
  - `startcol`: Left boundary (moves right after layer complete).
  - `endcol`: Right boundary (moves left after layer complete).
- Traversal order per cycle:
  1. Top: Left -> Right along `startrow` from `startcol` to `endcol`.
  2. Right: Top -> Bottom along `endcol` from `startrow + 1` to `endrow`.
  3. Bottom: Right -> Left along `endrow` from `endcol - 1` to `startcol`.
  4. Left: Bottom -> Top along `startcol` from `endrow - 1` to `startrow + 1`.

Boundary Shrinking & Guards:
- Corner overlap prevention:
  - Right wall starts at `startrow + 1` (avoids reprinting top-right corner).
  - Bottom wall starts at `endcol - 1` (avoids reprinting bottom-right corner).
  - Left wall starts at `endrow - 1` and stops at `startrow + 1` (avoids bottom-left and top-left corners).
- Single-dimension edge cases:
  - `if (startrow == endrow) break;` prevents duplicate prints on single remaining rows (e.g., 1 x N).
  - `if (startcol == endcol) break;` prevents duplicate prints on single remaining columns (e.g., N x 1).

-------------------------------------------------

Pointer Walkthrough Visual

Matrix: 4 x 4

Cycle 1 (Outer Ring):
startrow = 0, endrow = 3, startcol = 0, endcol = 3

  startcol                                  endcol
     v                                         v
   [ 1  --->   2  --->   3  --->   4 ]  <- startrow (1. Top: 1, 2, 3, 4)
     ^                             |
     |                             v
   [ 5                             8 ]             (2. Right: 8, 12, 16)
     ^                             |
     |                             v
   [ 9                            12 ]
     ^                             |
     |                             v
   [ 13 <---  14  <---  15  <---  16 ]  <- endrow   (3. Bottom: 15, 14, 13)
                                                    (4. Left: 9, 5)

Cycle 2 (Inner Ring):
startrow = 1, endrow = 2, startcol = 1, endcol = 2

   [ 6  --->   7 ]  <- startrow = 1 (1. Top: 6, 7)
     ^         |
     |         v
   [ 10 <---  11 ]  <- endrow = 2   (2. Right: 11)
                                    (3. Bottom: 10)
                                    (4. Left: skipped, 1 >= 2 fails)

-------------------------------------------------

Trace Table

Input: 4 x 4 Matrix

-------------------------------------------------------------------------------------------------------------------------------------------------
Cycle | Boundary Variables (Start of Cycle)     | Wall   | Traversal Range              | Elements Printed              | Output So Far
-------------------------------------------------------------------------------------------------------------------------------------------------
1     | startrow=0, endrow=3, startcol=0, endcol=3 | Top    | row=0, col: 0 -> 3           | 1, 2, 3, 4                    | 1 2 3 4
      |                                            | Right  | col=3, row: 1 -> 3           | 8, 12, 16                     | 1 2 3 4 8 12 16
      |                                            | Bottom | row=3, col: 2 -> 0           | 15, 14, 13                    | 1 2 3 4 8 12 16 15 14 13
      |                                            | Left   | col=0, row: 2 -> 1           | 9, 5                          | 1 2 3 4 8 12 16 15 14 13 9 5
-------------------------------------------------------------------------------------------------------------------------------------------------
2     | startrow=1, endrow=2, startcol=1, endcol=2 | Top    | row=1, col: 1 -> 2           | 6, 7                          | ... 6 7
      |                                            | Right  | col=2, row: 2 -> 2           | 11                            | ... 6 7 11
      |                                            | Bottom | row=2, col: 1 -> 1           | 10                            | ... 6 7 11 10
      |                                            | Left   | col=1, row: 1 -> 2 (Fails)   | None                          | 1 2 3 4 8 12 16 15 14 13 9 5 6 7 11 10
-------------------------------------------------------------------------------------------------------------------------------------------------
End   | startrow=2, endrow=1, startcol=2, endcol=1 | Loop condition `startrow <= endrow` (2 <= 1) is false -> Terminates
-------------------------------------------------------------------------------------------------------------------------------------------------

Complexity Analysis:
- Time Complexity : O(m x n)
  - Every single cell in the m x n grid is visited and printed exactly once.
- Space Complexity: O(1)
  - Operates directly on the input array using only 4 boundary pointer variables.
=================================================
*/
