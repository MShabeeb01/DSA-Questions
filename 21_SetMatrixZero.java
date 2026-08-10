class Solution {
    public void setZeroes(int[][] matrix) {

        // Check if matrix is empty
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;

        // col0 is used to remember whether
        // the first column needs to become zero.
        int col0 = 1;

        // STEP 1: Find all zeroes and use
        // first row and first column as markers.
        for (int i = 0; i < rows; i++) {

            // Check first column.
            // If matrix[i][0] is zero, the first
            // column must eventually become zero.
            if (matrix[i][0] == 0) {
                col0 = 0;
            }

            // Check remaining columns.
            for (int j = 1; j < cols; j++) {

                if (matrix[i][j] == 0) {

                    // Mark the current row.
                    // matrix[i][0] = 0 means:
                    // "Row i must become zero."
                    matrix[i][0] = 0;

                    // Mark the current column.
                    // matrix[0][j] = 0 means:
                    // "Column j must become zero."
                    matrix[0][j] = 0;
                }
            }
        }

        // STEP 2: Set marked rows and columns to zero.
        // Traverse from bottom-right to top-left
        // so that the markers are not destroyed early.
        for (int i = rows - 1; i >= 0; i--) {

            for (int j = cols - 1; j >= 1; j--) {

                // If the row is marked OR the column is marked,
                // make the current cell zero.
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }

            // Handle the first column separately.
            // col0 = 0 means the first column originally
            // contained a zero.
            if (col0 == 0) {
                matrix[i][0] = 0;
            }
        }
    }
}

/*

==================================================
## Code Summary
==================================================

1. Use the first row and first column as markers.

2. Find every zero in the matrix.

3. When a zero is found at matrix[i][j]:

   - matrix[i][0] = 0
     → Mark the entire row.

   - matrix[0][j] = 0
     → Mark the entire column.

4. Use col0 separately because the first column
   is also being used to store row markers.

5. Traverse from bottom-right to top-left.

6. If:
   
   matrix[i][0] == 0
   OR
   matrix[0][j] == 0

   then set matrix[i][j] = 0.

7. Finally, if col0 == 0, make the entire
   first column zero.

8. Extra Space: O(1)
   because no extra matrix, row array, or column
   array is used.


==================================================
## Iteration
==================================================

Input Matrix:

1   1   1
1   0   1
1   1   1


--------------------------------------------------
## Step 1: Find Zeroes and Mark
--------------------------------------------------

The zero is at:

row = 1
col = 1

So mark its row and column:

matrix[1][0] = 0
matrix[0][1] = 0

Matrix becomes:

1   0   1
0   0   1
1   1   1

Here:

matrix[1][0] = 0

means:

"Row 1 must become zero."

And:

matrix[0][1] = 0

means:

"Column 1 must become zero."


--------------------------------------------------
## Step 2: Traverse From Bottom-Right
--------------------------------------------------

Start:

i = 2
j = 2

Check:

matrix[2][0] = 1
matrix[0][2] = 1

No marker.

So:

matrix[2][2] = 1


--------------------------------------------------
## Iteration 2
--------------------------------------------------

i = 2
j = 1

Check:

matrix[2][0] = 1
matrix[0][1] = 0

Column 1 is marked.

So:

matrix[2][1] = 0

Matrix:

1   0   1
0   0   1
1   0   1


--------------------------------------------------
## Iteration 3
--------------------------------------------------

i = 2
j = 0

First column is handled separately.

col0 = 1

So the first column does not need to become zero.

Matrix remains:

1   0   1
0   0   1
1   0   1


--------------------------------------------------
## Iteration 4
--------------------------------------------------

i = 1
j = 2

Check:

matrix[1][0] = 0

This means Row 1 is marked.

So:

matrix[1][2] = 0

Matrix:

1   0   1
0   0   0
1   0   1


--------------------------------------------------
## Iteration 5
--------------------------------------------------

i = 1
j = 1

Check:

matrix[1][0] = 0

Row 1 is marked.

So:

matrix[1][1] = 0

It is already zero.

Matrix remains:

1   0   1
0   0   0
1   0   1


--------------------------------------------------
## Iteration 6
--------------------------------------------------

i = 1
j = 0

First column is handled separately.

col0 = 1

So the first column does not need to become zero.


--------------------------------------------------
## Iteration 7
--------------------------------------------------

i = 0
j = 2

Check:

matrix[0][0] = 1
matrix[0][2] = 1

No marker.

So:

matrix[0][2] = 1

Matrix remains:

1   0   1
0   0   0
1   0   1


--------------------------------------------------
## Iteration 8
--------------------------------------------------

i = 0
j = 1

Check:

matrix[0][0] = 1
matrix[0][1] = 0

Column 1 is marked.

So:

matrix[0][1] = 0

It is already zero.


--------------------------------------------------
## Final Matrix
--------------------------------------------------

1   0   1
0   0   0
1   0   1


==================================================
## Why Do We Traverse From Bottom-Right?
==================================================

The first row and first column are being used
as markers.

If we traverse from top-left to bottom-right,
we may overwrite these markers before using them.

Therefore:

Top-left → Bottom-right  ❌

Bottom-right → Top-left  ✅

This preserves the marker information.


==================================================
## Role of matrix[i][0]
==================================================

matrix[i][0] is used as a marker for ROW i.

For example:

matrix[1][0] = 0

means:

"Make the entire Row 1 zero."


==================================================
## Role of matrix[0][j]
==================================================

matrix[0][j] is used as a marker for COLUMN j.

For example:

matrix[0][1] = 0

means:

"Make the entire Column 1 zero."


==================================================
## Role of col0
==================================================

The first column needs separate handling.

matrix[i][0] is already being used as
a marker for each row.

Therefore, col0 remembers whether the
FIRST COLUMN itself needs to become zero.

col0 = 1 → First column does not need zeroing.

col0 = 0 → First column must become zero.

*/
