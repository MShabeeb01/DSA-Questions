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
            {1,   2,   3,   4},
            {5,   6,   7,   8},
            {9,  10,  11,  12},
            {13, 14,  15,  16}
        };

        // Function call
        printSpiral(matrix); // Output → 1 2 3 4 8 12 16 15 14 13 9 5 6 7 11 10
    }
}

/*
==================== DEEP DIVE SUMMARY & EXECUTION TRACE ====================

Problem: LeetCode 54 - Spiral Matrix Traversal

Core Concept: 
A matrix spiral is just 4 simple for-loops running in a loop, while 4 walls 
shrink inward like a closing room after every full cycle:
- Top Wall:    `startrow`
- Bottom Wall: `endrow`
- Left Wall:   `startcol`
- Right Wall:  `endcol`

=============================================================================
INPUT MATRIX (4 x 4):
Index:       c=0   c=1   c=2   c=3
r=0  -----> [  1,    2,    3,    4 ]
r=1  -----> [  5,    6,    7,    8 ]
r=2  -----> [  9,   10,   11,   12 ]
r=3  -----> [ 13,   14,   15,   16 ]

Initial Boundaries:
startrow = 0, endrow = 3
startcol = 0, endcol = 3
=============================================================================

=============================================================================
CYCLE 1: OUTER LAYER (Border walls: rows 0 & 3, cols 0 & 3)
Condition Check: while (0 <= 3 && 0 <= 3) -> TRUE! Enter loop.
=============================================================================

--- [Side 1: TOP WALL - Left to Right] ---
Code: for (int j = startcol; j <= endcol; j++)
State: row is fixed at startrow (0), j iterates from 0 to 3
- Iteration j=0: prints matrix[0][0] = 1
- Iteration j=1: prints matrix[0][1] = 2
- Iteration j=2: prints matrix[0][2] = 3
- Iteration j=3: prints matrix[0][3] = 4
Condition j <= 3 fails at j=4 -> Loop ends.
Output so far: 1 2 3 4

--- [Side 2: RIGHT WALL - Top to Bottom] ---
Code: for (int i = startrow + 1; i <= endrow; i++)
Why i = startrow + 1? Because (0, 3) which is '4' was just printed! We start at row 1.
State: col is fixed at endcol (3), i iterates from 1 to 3
- Iteration i=1: prints matrix[1][3] = 8
- Iteration i=2: prints matrix[2][3] = 12
- Iteration i=3: prints matrix[3][3] = 16
Condition i <= 3 fails at i=4 -> Loop ends.
Output so far: 1 2 3 4 8 12 16

--- [Side 3: BOTTOM WALL - Right to Left] ---
Code: for (int j = endcol - 1; j >= startcol; j--)
Why j = endcol - 1? Because (3, 3) which is '16' was just printed! We start at col 2.
Guard check: if (startrow == endrow) -> (0 == 3 is FALSE, don't break)
State: row is fixed at endrow (3), j iterates from 2 down to 0
- Iteration j=2: prints matrix[3][2] = 15
- Iteration j=1: prints matrix[3][1] = 14
- Iteration j=0: prints matrix[3][0] = 13
Condition j >= 0 fails at j=-1 -> Loop ends.
Output so far: 1 2 3 4 8 12 16 15 14 13

--- [Side 4: LEFT WALL - Bottom to Top] ---
Code: for (int i = endrow - 1; i >= startrow + 1; i--)
Why i = endrow - 1? Because (3, 0) which is '13' was just printed! We start at row 2.
Why i >= startrow + 1? Because (0, 0) which is '1' was printed at the very start! Stop at row 1.
Guard check: if (startcol == endcol) -> (0 == 3 is FALSE, don't break)
State: col is fixed at startcol (0), i iterates from 2 down to 1
- Iteration i=2: prints matrix[2][0] = 9
- Iteration i=1: prints matrix[1][0] = 5
Condition i >= 1 fails at i=0 -> Loop ends.
Output so far: 1 2 3 4 8 12 16 15 14 13 9 5

--- [SHRINK WALLS FOR NEXT CYCLE] ---
startcol++; -> startcol becomes 1 (left wall moves right)
startrow++; -> startrow becomes 1 (top wall moves down)
endcol--;   -> endcol becomes 2   (right wall moves left)
endrow--;   -> endrow becomes 2   (bottom wall moves up)


=============================================================================
CYCLE 2: INNER LAYER (Inner 2x2 square: elements 6, 7, 10, 11)
Condition Check: while (1 <= 2 && 1 <= 2) -> TRUE! Enter loop.
=============================================================================

--- [Side 1: TOP WALL - Left to Right] ---
Code: for (int j = startcol; j <= endcol; j++)
State: row is fixed at startrow (1), j iterates from 1 to 2
- Iteration j=1: prints matrix[1][1] = 6
- Iteration j=2: prints matrix[1][2] = 7
Condition j <= 2 fails at j=3 -> Loop ends.
Output so far: 1 2 3 4 8 12 16 15 14 13 9 5 6 7

--- [Side 2: RIGHT WALL - Top to Bottom] ---
Code: for (int i = startrow + 1; i <= endrow; i++)
Start at i = 1 + 1 = 2.
State: col is fixed at endcol (2), i iterates from 2 to 2
- Iteration i=2: prints matrix[2][2] = 11
Condition i <= 2 fails at i=3 -> Loop ends.
Output so far: 1 2 3 4 8 12 16 15 14 13 9 5 6 7 11

--- [Side 3: BOTTOM WALL - Right to Left] ---
Code: for (int j = endcol - 1; j >= startcol; j--)
Start at j = 2 - 1 = 1.
Guard check: if (startrow == endrow) -> (1 == 2 is FALSE, don't break)
State: row is fixed at endrow (2), j iterates from 1 down to 1
- Iteration j=1: prints matrix[2][1] = 10
Condition j >= 1 fails at j=0 -> Loop ends.
Output so far: 1 2 3 4 8 12 16 15 14 13 9 5 6 7 11 10

--- [Side 4: LEFT WALL - Bottom to Top] ---
Code: for (int i = endrow - 1; i >= startrow + 1; i--)
Start at i = 2 - 1 = 1.
Stop condition: i >= startrow + 1 => i >= (1 + 1) => i >= 2.
Check loop entry: i = 1 >= 2 is FALSE!
The loop does not run at all (0 iterations). No extra print!

--- [SHRINK WALLS FOR NEXT CYCLE] ---
startcol++; -> startcol becomes 2
startrow++; -> startrow becomes 2
endcol--;   -> endcol becomes 1
endrow--;   -> endrow becomes 1


=============================================================================
CYCLE 3: TERMINATION CHECK
=============================================================================
Condition Check: while (startrow <= endrow && startcol <= endcol)
Evaluates to:    while (2 <= 1 && 2 <= 1) -> FALSE!

The while loop exits.
All 16 cells visited cleanly!

FINAL OUTPUT:
1 2 3 4 8 12 16 15 14 13 9 5 6 7 11 10

=============================================================================
WHY ARE THE BREAK STATEMENTS CRITICAL? (Edge Case Example: 1 x 4 Matrix)
Matrix: [1, 2, 3, 4] -> startrow = 0, endrow = 0, startcol = 0, endcol = 3

1. TOP WALL prints: 1 2 3 4
2. RIGHT WALL: i starts at 1, but endrow=0, doesn't run.
3. BOTTOM WALL:
   WITHOUT `if (startrow == endrow) break;`:
   It would loop backwards and print: 3 2 1  <-- DUPLICATE BUG!
   WITH `break`:
   It sees 0 == 0, breaks immediately, and avoids the bug!
=============================================================================
*/
