import java.util.ArrayList; // Import ArrayList class
import java.util.Arrays;    // Import Arrays class
import java.util.List;      // Import List interface

class Solution { // LeetCode 51: N-Queens

    // Helper function to check if placing a queen at board[row][col] is safe
    public boolean isSafe(char[][] board, int row, int col, int n) {
        // 1. Check vertical column upward
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') {
                return false;
            }
        }

        // 2. Check upper-left diagonal
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }

        // 3. Check upper-right diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }

        return true;
    }

    // Recursive helper to place queens row by row
    public void nQueens(char[][] board, int row, int n, List<List<String>> ans) {
        // Base Case: All n queens are placed safely across rows 0 to n-1
        if (row == n) {
            List<String> currentBoard = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                currentBoard.add(new String(board[i])); // Convert char row to String
            }
            ans.add(currentBoard); // Add valid board configuration to answer
            return;
        }

        // Try placing the queen in each column of the current row
        for (int j = 0; j < n; j++) {
            if (isSafe(board, row, j, n)) {
                board[row][j] = 'Q';              // 1. DO: Place queen
                nQueens(board, row + 1, n, ans);  // 2. RECURSE: Move to next row
                board[row][j] = '.';              // 3. UNDO (Backtrack): Reset cell
            }
        }
    }

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> ans = new ArrayList<>();
        char[][] board = new char[n][n];

        // Initialize empty chessboard with '.'
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }

        nQueens(board, 0, n, ans); // Start from row 0
        return ans;
    }

    public static void main(String[] args) { // Driver function
        Solution solver = new Solution();
        int n = 4;

        List<List<String>> solutions = solver.solveNQueens(n);

        System.out.println("Total Distinct Solutions for " + n + "-Queens: " + solutions.size());
        for (List<String> board : solutions) {
            System.out.println("--- Solution ---");
            for (String row : board) {
                System.out.println(row);
            }
        }
    }
}

/*
==================== SUMMARY ====================

Approach:
1. Backtrack row-by-row: Place exactly one queen per row, guaranteeing no two queens share a row.
2. For the current row, try placing the queen in every column `j` from 0 to n - 1.
3. Use `isSafe(board, row, col, n)` to verify:
   - No queen exists directly above in the same column.
   - No queen exists in the top-left diagonal.
   - No queen exists in the top-right diagonal.
   (No need to check rows below, as they are not populated yet).
4. If safe:
   - Place queen (`board[row][j] = 'Q'`).
   - Recurse to place next queen at `row + 1`.
   - Backtrack by clearing the cell (`board[row][j] = '.'`).
5. Base Case: When `row == n`, a full valid board has been formed; format and store it.

-------------------------------------------------

Recursion & Backtracking Tree Breakdown (n = 4)

Notation: Q(row, col)

                         Row 0: Try placing Q
             /               \              \             \
         Q(0,0)             Q(0,1)         Q(0,2)        Q(0,3)
         /   \              /    \
     Q(1,2)  Q(1,3)     Q(1,3)   ...
       |       |          |
     Q(2,x)  Q(2,1)     Q(2,0)
    (Blocked)  |          |
             Q(3,x)     Q(3,2)
            (Blocked)     |
                        Row 4 == n (VALID SOLUTION FOUND!)

-------------------------------------------------

Step-by-Step Backtracking Trace (First Valid Solution for n = 4)

Board Dimension: 4x4

---------------------------------------------------------------------------------------------------------
Step | Row | Col | Safe? | Action / Transition                  | Board State Preview
---------------------------------------------------------------------------------------------------------
1    | 0   | 0   | Yes   | Place Q at (0,0), Recurse row 1      | Q . . .
2    | 1   | 0,1 | No    | Blocked (col / diagonal attack)      | Q . . .
3    | 1   | 2   | Yes   | Place Q at (1,2), Recurse row 2      | Q . . . / . . Q .
4    | 2   | 0-3 | No    | All cols blocked -> Backtrack row 1  | Q . . . / . . . .
5    | 1   | 3   | Yes   | Place Q at (1,3), Recurse row 2      | Q . . . / . . . Q
6    | 2   | 1   | Yes   | Place Q at (2,1), Recurse row 3      | Q . . . / . . . Q / . Q . .
7    | 3   | 0-3 | No    | All cols blocked -> Backtrack to (0,0)| . . . .
8    | 0   | 1   | Yes   | Place Q at (0,1), Recurse row 1      | . Q . .
9    | 1   | 3   | Yes   | Place Q at (1,3), Recurse row 2      | . Q . . / . . . Q
10   | 2   | 0   | Yes   | Place Q at (2,0), Recurse row 3      | . Q . . / . . . Q / Q . . .
11   | 3   | 2   | Yes   | Place Q at (3,2), Recurse row 4      | . Q . . / . . . Q / Q . . . / . . Q .
12   | 4   | -   | -     | Base Case: row == 4 -> SAVE SOLUTION | [Solution 1 Added]
---------------------------------------------------------------------------------------------------------

Output for n = 4:
Total Distinct Solutions for 4-Queens: 2
--- Solution ---
. Q . .
. . . Q
Q . . .
. . Q .
--- Solution ---
. . Q .
Q . . .
. . . Q
. Q . .

Complexity:
- Time Complexity : O(n!) — The first queen has n choices, the second at most n - 2, the third n - 4, etc.
- Space Complexity: O(n^2) for the chessboard matrix and O(n) for the recursion stack depth.

=================================================
*/
