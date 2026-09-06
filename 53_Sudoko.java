class Solution { // LeetCode 37: Sudoku Solver

    // Helper method to validate if 'dig' can be placed at board[row][col]
    public boolean isSafe(char[][] board, int row, int col, char dig) {
        // 1. Check Row & Column
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == dig || board[i][col] == dig) {
                return false;
            }
        }

        // 2. Check 3x3 Grid Box
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == dig) {
                    return false;
                }
            }
        }

        return true;
    }

    // Recursive backtracking solver
    public boolean helper(char[][] board, int row, int col) {
        // Base Case: Reached beyond last row -> Sudoku completely solved
        if (row == 9) {
            return true;
        }

        // Calculate next cell coordinates
        int nextRow = row, nextCol = col + 1;
        if (nextCol == 9) {
            nextRow = row + 1;
            nextCol = 0;
        }

        // If current cell already contains a number, skip to next cell
        if (board[row][col] != '.') {
            return helper(board, nextRow, nextCol);
        }

        // Place the digit: Try placing numbers from '1' to '9'
        for (char dig = '1'; dig <= '9'; dig++) {
            if (isSafe(board, row, col, dig)) {
                board[row][col] = dig; // 1. DO: Place digit

                if (helper(board, nextRow, nextCol)) { // 2. RECURSE: Move to next cell
                    return true; // Found valid complete solution
                }

                board[row][col] = '.'; // 3. UNDO (Backtrack): Reset cell
            }
        }

        return false; // Trigger backtracking to previous cell if no digit works
    }

    public void solveSudoku(char[][] board) {
        helper(board, 0, 0); // Start solving from top-left cell (0, 0)
    }

    public static void main(String[] args) { // Driver function
        Solution solver = new Solution();
        char[][] board = {
            {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        solver.solveSudoku(board);

        // Print Solved Sudoku
        System.out.println("Solved Sudoku:");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}

/*
==================== SUMMARY ====================

Approach:
1. Advance cell-by-cell in row-major order:
   - `nextRow = row`, `nextCol = col + 1`.
   - If `nextCol == 9`, wrap to next line: `nextRow = row + 1`, `nextCol = 0`.
2. Base Condition:
   - When `row == 9`, all rows 0-8 are correctly filled, return `true`.
3. If current cell is already filled (`board[row][col] != '.'`), immediately call `helper(board, nextRow, nextCol)`.
4. If empty (`'.'`), try placing digits `'1'` through `'9'`:
   - Verify safety using `isSafe(board, row, col, dig)` checking row, column, and 3x3 subgrid.
   - If valid, assign `board[row][col] = dig` and recurse.
   - If recursive branch returns `true`, propagate `true` up the stack.
   - If all digits 1-9 fail, backtrack by resetting cell to `'.'`.

-------------------------------------------------

Recursion & Backtracking Tree Breakdown

Initial cell (row, col)
                   (row, col)
                 /     |     \
              '1'     '2'    ... '9'
              /        \
          Safe?        Safe?
         / (Yes)         \ (No -> Prune)
    (nextRow, nextCol)
       /          \
     ...         ...
   row == 9 ? -> TRUE (Complete Solution)

-------------------------------------------------

Step-by-Step Backtracking Trace (Excerpt)

Target cell: board[0][2] (Empty '.')

-------------------------------------------------------------------------------------------------------------------
Step | Current Cell | Try Digit | Safe Check                       | Result / Action Taken
-------------------------------------------------------------------------------------------------------------------
1    | (0, 0)       | '5'       | Pre-filled                       | Skip -> go to (0, 1)
2    | (0, 1)       | '3'       | Pre-filled                       | Skip -> go to (0, 2)
3    | (0, 2)       | '1'       | '1' present in column 3 / block  | False -> skip
4    | (0, 2)       | '2'       | Row, col & 3x3 block check       | True -> board[0][2] = '2', Recurse to (0, 3)
5    | (0, 3)       | '1'       | Check row, col, 3x3 block        | True -> board[0][3] = '1', Recurse to (0, 4)
...  | ...          | ...       | Dead end reached at deeper cell  | False -> Backtracks
k    | (0, 2)       | '2'       | Undone                           | board[0][2] = '.', Try next digit '3'
k+1  | (0, 2)       | '4'       | Check row, col, 3x3 block        | True -> board[0][2] = '4', Continue path...
-------------------------------------------------------------------------------------------------------------------

Complexity:
- Time Complexity : O(9^(empty_cells)) in the worst case; highly pruned in practice by validity constraints.
- Space Complexity: O(1) auxiliary board space (done in-place) + O(81) = O(1) recursion call stack depth.

=================================================
*/
