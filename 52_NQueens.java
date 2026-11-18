import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {

    public boolean isSafe(char[][] board, int row, int col, int n) {
        // Check column upward
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') return false;
        }
        // Check upper-left diagonal
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') return false;
        }
        // Check upper-right diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q') return false;
        }
        return true;
    }

    public void nQueens(char[][] board, int row, int n, List<List<String>> ans) {
        // Base case: all queens are safely placed
        if (row == n) {
            List<String> currentBoard = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                currentBoard.add(new String(board[i]));
            }
            ans.add(currentBoard);
            return;
        }

        // Try placing queen in each column of the current row
        for (int j = 0; j < n; j++) {
            if (isSafe(board, row, j, n)) {
                board[row][j] = 'Q';              // 1. ADD: Place queen choice
                nQueens(board, row + 1, n, ans);  // 2. RECURSE: Move to next row
                board[row][j] = '.';              // 3. DELETE / BACKTRACK: Reset cell back to empty
            }
        }
    }

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> ans = new ArrayList<>();
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        nQueens(board, 0, n, ans);
        return ans;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        int n = 4;
        List<List<String>> solutions = solver.solveNQueens(n);
        System.out.println("Total Solutions: " + solutions.size());
        for (List<String> board : solutions) {
            System.out.println("--- Board ---");
            for (String row : board) {
                System.out.println(row);
            }
        }
    }
}

/*
================================================================================
PART 2: SUMMARY OF THE APPROACH
================================================================================
We use a **Recursion and Backtracking** strategy row-by-row:
1. We place exactly one queen per row, which guarantees no two queens share the same row.
2. For the current row, we loop through every column and check if it's safe using `isSafe()`.
3. **If safe (Addition):** We place the queen (`'Q'`) and move recursively to the next row.
4. **If it leads to a dead end (Deletion / Backtracking):** We undo our choice by resetting the cell back to an empty space (`'.'`), allowing us to try the next column option.
5. When we successfully reach row `n`, we capture a picture of the board and save it to our answers list.

================================================================================
PART 3: STEP-BY-STEP ITERATION WITH VISUAL REPRESENTATION (Example: n = 4)
================================================================================

Let's trace the execution steps and how modifications happen on our 2D grid matrix:

--- INITIAL CALL: nQueens(board, row = 0, n = 4) ---

Step 1: Row = 0, Column = 0
- Check `isSafe(board, 0, 0)` -> YES.
- **ADDITION:** Place queen at `board[0][0] = 'Q'`.
- Board State:
  Q . . .
  . . . .
  . . . .
  . . . .
- Recurse: `nQueens(board, row = 1, n = 4)`

   --- Inside Row = 1 ---
   Step 2: Column = 0 and Column = 1
   - `isSafe(board, 1, 0)` -> NO (Same column as Q at [0,0]).
   - `isSafe(board, 1, 1)` -> NO (Diagonal attack from Q at [0,0]).
   
   Step 3: Column = 2
   - `isSafe(board, 1, 2)` -> YES.
   - **ADDITION:** Place queen at `board[1][2] = 'Q'`.
   - Board State:
     Q . . .
     . . Q .
     . . . .
     . . . .
   - Recurse: `nQueens(board, row = 2, n = 4)`

      --- Inside Row = 2 ---
      Step 4: Check columns 0, 1, 2, 3 for Row 2...
      - All columns are blocked by attacking threats from existing queens!
      - Loop finishes without any safe spot. Return back to Row 1.

   Step 5: Backtracking & Deletion from Row 1
   - Back in Row 1, we hit the line: `board[row][j] = '.'`.
   - **DELETION / RESET VISUAL:** We remove the queen from `board[1][2]` and restore it to `'.'`.
   - Board State after deletion:
     Q . . .
     . . . .
     . . . .
     . . . .
   - Loop in Row 1 continues to Column = 3.

   Step 6: Column = 3 in Row 1
   - `isSafe(board, 1, 3)` -> YES.
   - **ADDITION:** Place queen at `board[1][3] = 'Q'`.
   - Board State:
     Q . . .
     . . . Q
     . . . .
     . . . .
   - Recurse: `nQueens(board, row = 2, n = 4)`

      --- Inside Row = 2 ---
      Step 7: Column = 1
      - `isSafe(board, 2, 1)` -> YES.
      - **ADDITION:** Place queen at `board[2][1] = 'Q'`.
      - Board State:
        Q . . .
        . . . Q
        . Q . .
        . . . .
      - Recurse: `nQueens(board, row = 3, n = 4)`

         --- Inside Row = 3 ---
         Step 8: Check columns 0, 1, 2, 3 for Row 3...
         - All columns are blocked! No safe spot available. Return back.

      Step 9: Backtracking & Deletion from Row 2
      - **DELETION / RESET VISUAL:** We clear `board[2][1]` back to `'.'`.
      - Board State after deletion:
        Q . . .
        . . . Q
        . . . .
        . . . .
      - Loop ends for Row 2, return back to Row 1.

   (The backtracking process continues exploring other branches, cleaning up/deleting cells whenever a path fails, until valid configurations are fully discovered and saved!)

================================================================================
PART 4: TIME & SPACE COMPLEXITY
================================================================================
- **Time Complexity: O(N!)**
  - Why? For the first row, we have $N$ choices. For the second row, roughly $N-2$ choices, then $N-4$, and so on. This factorial growth pattern makes it $O(N!)$.
- **Space Complexity: O(N^2)**
  - Why? The chessboard takes $O(N^2)$ space to store characters, and the recursion call stack takes $O(N)$ depth since we go down row by row up to height $N$.

================================================================================
PART 5: MOST PROBABLE INTERVIEW QUESTIONS
================================================================================
1. **Q: Why don't we need to check rows below the current row in `isSafe`?**
   - **Answer:** Because we place queens row by row starting from row 0 downward. Rows below the current row are completely empty (`'.'`), so they cannot contain any conflicting queens yet.
2. **Q: Can we optimize the `isSafe` column and diagonal checks from O(N) down to O(1)?**
   - **Answer:** Yes! We can use hash sets or boolean tracking arrays (`cols`, `leftDiagonals`, `rightDiagonals`) to check attacks in constant time $O(1)$ instead of scanning the board matrix with loops.
3. **Q: What is the purpose of `board[row][j] = '.'` after the recursive call?**
   - **Answer:** That is our backtracking step. It acts as a cleanup deletion, resetting the cell back to empty so that when the loop moves to the next column option, the board is clean and unbiased.
*/
