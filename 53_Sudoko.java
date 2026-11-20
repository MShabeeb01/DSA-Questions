class Solution { // LeetCode 37: Sudoku Solver

    public boolean isSafe(char[][] board, int row, int col, char dig) { // Check if digit is valid here
        for (int i = 0; i < 9; i++) { // Check entire row and entire column
            if (board[row][i] == dig || board[i][col] == dig) { // Duplicate found in row or col
                return false;
            }
        }

        int startRow = (row / 3) * 3; // Top row index of the 3x3 box
        int startCol = (col / 3) * 3; // Left col index of the 3x3 box

        for (int i = startRow; i < startRow + 3; i++) { // Scan 3 rows of the box
            for (int j = startCol; j < startCol + 3; j++) { // Scan 3 cols of the box
                if (board[i][j] == dig) { // Duplicate found in 3x3 box
                    return false;
                }
            }
        }

        return true; // Safe to place dig at (row, col)
    }

    public boolean helper(char[][] board, int row, int col) { // Backtracking solver from (row, col)
        if (row == 9) { // Passed row 8 -> all 81 cells are filled validly
            return true;
        }

        int nextRow = row, nextCol = col + 1; // Move one column to the right
        if (nextCol == 9) { // Wrapped past last column -> move to start of next row
            nextRow = row + 1;
            nextCol = 0;
        }

        if (board[row][col] != '.') { // Pre-filled cell -> skip without changing it
            return helper(board, nextRow, nextCol);
        }

        for (char dig = '1'; dig <= '9'; dig++) { // Try digits '1' through '9'
            if (isSafe(board, row, col, dig)) { // Only try if rules aren't broken
                board[row][col] = dig; // DO: place digit on board

                if (helper(board, nextRow, nextCol)) { // RECURSE: solve remaining board
                    return true; // Propagate success all the way up
                }

                board[row][col] = '.'; // UNDO (backtrack): erase digit back to '.'
            }
        }

        return false; // No digit '1'..'9' worked -> backtrack to earlier cell
    }

    public void solveSudoku(char[][] board) { // LeetCode entry point
        helper(board, 0, 0); // Begin at top-left cell (0, 0)
    }
}

/*
================================================================================
PART 2: SUMMARY OF THE APPROACH & LINE-BY-LINE EXPLANATION
================================================================================

SUMMARY OF THE APPROACH
-----------------------
This code solves Sudoku using **recursion + backtracking** (trial-and-error
with an "undo" step):

1. **Move Cell by Cell:** Start at `(0, 0)` and walk across each row from left
   to right, then down to the next row.
2. **Skip Given Numbers:** If a cell already has a digit (`'1'`–`'9'`), leave it
   alone and jump straight to the next cell.
3. **Try, Recurse, Undo:** For an empty cell `'.'`, test digits `'1'` to `'9'`.
   If a digit doesn't clash with the row, column, or 3x3 box, place it
   (**DO**) and solve the rest of the board (**RECURSE**). If a dead end happens
   later, erase the digit back to `'.'` (**UNDO / backtrack**) and try the next
   digit.


LINE-BY-LINE EXPLANATION
-----------------------

Line: class Solution { // LeetCode 37: Sudoku Solver
What it does:
  - Declares the `Solution` class containing the Sudoku solver methods.

--------------------------------------------------------------------------------

Line: public boolean isSafe(char[][] board, int row, int col, char dig) {
What it does:
  - Helper method that checks whether placing character `dig` at `(row, col)`
    follows all three Sudoku rules: no duplicate in `row`, no duplicate in
    `col`, and no duplicate in the 3x3 box containing `(row, col)`.

--------------------------------------------------------------------------------

Line: for (int i = 0; i < 9; i++) {
          if (board[row][i] == dig || board[i][col] == dig) {
              return false;
          }
      }
What it does:
  - Loops `i` from `0` to `8` to check every cell in `board[row][i]` (same
    row) and every cell in `board[i][col]` (same column).
  - If `dig` already appears in either place, returns `false` right away.

--------------------------------------------------------------------------------

Line: int startRow = (row / 3) * 3;
      int startCol = (col / 3) * 3;
What it does:
  - Finds the top-left corner index `(startRow, startCol)` of the 3x3 box that
    holds `(row, col)`.
  - Because `row / 3` uses integer division, rows `0, 1, 2` give `0`, rows
    `3, 4, 5` give `3`, and rows `6, 7, 8` give `6` (same for columns).

--------------------------------------------------------------------------------

Line: for (int i = startRow; i < startRow + 3; i++) {
          for (int j = startCol; j < startCol + 3; j++) {
              if (board[i][j] == dig) {
                  return false;
              }
          }
      }
What it does:
  - Scans all 9 cells inside that 3x3 subgrid (`startRow` to `startRow + 2`,
    and `startCol` to `startCol + 2`).
  - Returns `false` if `dig` is already present inside that 3x3 box.

--------------------------------------------------------------------------------

Line: return true;
What it does:
  - Reached only if `dig` passed the row check, column check, and 3x3 box check—
    meaning it is valid to place at `(row, col)`.

--------------------------------------------------------------------------------

Line: public boolean helper(char[][] board, int row, int col) {
What it does:
  - Recursive backtracking function that tries to fill `(row, col)` and every
    cell after it. Returns `true` if a full valid solution is found, or
    `false` if this path hits a dead end.

--------------------------------------------------------------------------------

Line: if (row == 9) {
          return true;
      }
What it does:
  - **Base case:** Once all rows `0` through `8` are completed, `nextRow`
    becomes `9`. That means every cell on the 9x9 board is filled validly, so
    we return `true`.

--------------------------------------------------------------------------------

Line: int nextRow = row, nextCol = col + 1;
      if (nextCol == 9) {
          nextRow = row + 1;
          nextCol = 0;
      }
What it does:
  - Computes the coordinates of the very next cell in row-major order:
    * Normally moves right (`col + 1`) on the same `row`.
    * If `col + 1` hits `9` (past index `8`), wraps to column `0` of the next
      row (`row + 1`).

--------------------------------------------------------------------------------

Line: if (board[row][col] != '.') {
          return helper(board, nextRow, nextCol);
      }
What it does:
  - If `board[row][col]` is not `'.'`, the cell already has a fixed number, so
    we don't overwrite it—we just move directly to `(nextRow, nextCol)`.

--------------------------------------------------------------------------------

Line: for (char dig = '1'; dig <= '9'; dig++) {
What it does:
  - Loops through candidate digits `'1'` to `'9'` to test in an empty `'.'`
    cell.

--------------------------------------------------------------------------------

Line: if (isSafe(board, row, col, dig)) {
          board[row][col] = dig; // 1. DO: Place digit

          if (helper(board, nextRow, nextCol)) { // 2. RECURSE: Move to next cell
              return true; // Found valid complete solution
          }

          board[row][col] = '.'; // 3. UNDO (Backtrack): Reset cell
      }
What it does:
  - **Check:** Calls `isSafe(...)` before touching the board.
  - **DO:** Writes `dig` into `board[row][col]`.
  - **RECURSE:** Calls `helper(board, nextRow, nextCol)` to solve the rest of
    the board. If that returns `true`, immediately returns `true` so we keep
    the solved board intact.
  - **UNDO (Backtrack):** If the recursive call returns `false` (meaning `dig`
    led to a dead end later), sets `board[row][col]` back to `'.'` so the loop
    can try the next digit cleanly.

--------------------------------------------------------------------------------

Line: return false;
What it does:
  - Reached if none of `'1'` through `'9'` worked at `(row, col)`. Returns
    `false` to tell the previous cell to backtrack and try a different digit.

--------------------------------------------------------------------------------

Line: public void solveSudoku(char[][] board) {
          helper(board, 0, 0); // Start solving from top-left cell (0, 0)
      }
What it does:
  - Main LeetCode method that kicks off backtracking at top-left `(0, 0)` and
    mutates `board` directly in place.


================================================================================
PART 3: STEP-BY-STEP ITERATION WITH VISUAL REPRESENTATION
================================================================================

Let's trace on a concrete **3x3 matrix** (using the same row/col/box logic,
with a 3x3 box of size 3 so we can see the full 3x3 grid every step!).

Suppose our 3x3 grid starts as:

  Initial 3x3 Grid:
        c0   c1   c2
    r0 [ '5', '.', '3' ]
    r1 [ '.', '5', '.' ]
    r2 [ '3', '.', '.' ]

Let's trace trying digits `'1'` to `'5'` into the empty cells (`'.'`) in
row-major order: `(0,1) -> (1,0) -> (1,2) -> (2,1) -> (2,2)`.

--------------------------------------------------------------------------------
STEP 1: At (0, 1) — Testing & Adding
--------------------------------------------------------------------------------
  - Cell `(0, 1)` is `'.'`.
  - Tested `'1'`: `isSafe` -> true.
  - ADDED (DO): `board[0][1] = '1'`.

  3x3 Grid after adding '1' at (0, 1):
        c0    c1    c2
    r0 [ '5', ('1'), '3' ]   <-- added '1' at (0, 1)
    r1 [ '.',  '5',  '.' ]
    r2 [ '3',  '.',  '.' ]

--------------------------------------------------------------------------------
STEP 2: Recurse to (1, 0) — Testing & Adding
--------------------------------------------------------------------------------
  - Cell `(1, 0)` is `'.'`.
  - Tested `'1'`: `isSafe` -> false (`'1'` already in col 1, and `'1'` is at `(0,1)`).
  - Tested `'2'`: `isSafe` -> true.
  - ADDED (DO): `board[1][0] = '2'`.

  3x3 Grid after adding '2' at (1, 0):
        c0    c1    c2
    r0 [ '5',  '1',  '3' ]
    r1 [('2'), '5',  '.' ]   <-- added '2' at (1, 0)
    r2 [ '3',  '.',  '.' ]

--------------------------------------------------------------------------------
STEP 3: Recurse to (1, 1) — Pre-filled cell -> Skip
--------------------------------------------------------------------------------
  - Cell `(1, 1)` is `'5'` (`board[1][1] != '.'`).
  - Action: Skips and moves to `(1, 2)`.

  3x3 Grid (unchanged):
        c0    c1    c2
    r0 [ '5',  '1',  '3' ]
    r1 [ '2', ['5'], '.' ]   <-- pre-filled '5', skipped
    r2 [ '3',  '.',  '.' ]

--------------------------------------------------------------------------------
STEP 4: At (1, 2) — Testing & Adding
--------------------------------------------------------------------------------
  - Cell `(1, 2)` is `'.'`.
  - Suppose `'1'`, `'2'`, `'3'` fail `isSafe` (conflicts in row 1 / col 2 / 3x3).
  - Tested `'4'`: `isSafe` -> true.
  - ADDED (DO): `board[1][2] = '4'`.

  3x3 Grid after adding '4' at (1, 2):
        c0    c1    c2
    r0 [ '5',  '1',  '3' ]
    r1 [ '2',  '5', ('4') ]  <-- added '4' at (1, 2)
    r2 [ '3',  '.',  '.' ]

--------------------------------------------------------------------------------
STEP 5: Recurse to (2, 1) — Dead End -> Trigger Backtrack (DELETE / UNDO)
--------------------------------------------------------------------------------
  - At `(2, 1)`, suppose every digit `'1'`..`'5'` fails `isSafe` (blocked by
    row 2 having `'3'`, col 1 having `'1'` and `'5'`, or grid conflicts).
  - `(2, 1)` returns `false` back to `(1, 2)`.
  - At `(1, 2)`, no other digit after `'4'` works, so `(1, 2)` returns `false`
    back to `(1, 0)`.
  - At `(1, 0)`, no other digit after `'2'` works, so `(1, 0)` returns `false`
    back to `(0, 1)`.

  Now we are back at `(0, 1)` where `'1'` was placed in Step 1:
  - DELETED (UNDO): `board[0][1]` is reset from `'1'` back to `'.'`.

  Visualizing the in-place deletion at (0, 1) in the 3x3 matrix:

    BEFORE DELETING at (0, 1):
          c0    c1    c2
      r0 [ '5', <'1'>, '3' ]   <-- '1' currently at (0, 1)
      r1 [ '2',  '5',  '4'  ]
      r2 [ '3',  '.',  '.'  ]

    ACTION: Overwrite board[0][1] = '.' (in-place cell reset, not list pop)

    AFTER DELETING at (0, 1):
          c0    c1    c2
      r0 [ '5', <'.'>, '3' ]   <-- '1' deleted, cell restored to '.'
      r1 [ '2',  '5',  '4'  ]
      r2 [ '3',  '.',  '.'  ]

--------------------------------------------------------------------------------
STEP 6: Try Next Digit at (0, 1) — Testing & Adding
--------------------------------------------------------------------------------
  - Loop at `(0, 1)` continues after `'1'`.
  - Suppose `'2'` is tested -> `isSafe` is true!
  - ADDED (DO): `board[0][1] = '2'`.

  3x3 Grid after adding '2' at (0, 1):
        c0    c1    c2
    r0 [ '5', ('2'), '3' ]   <-- added '2' at (0, 1)
    r1 [ '2',  '5',  '4'  ]
    r2 [ '3',  '.',  '.'  ]

--------------------------------------------------------------------------------
STEP 7: Continue Forward Until Solved
--------------------------------------------------------------------------------
  - From `(0, 1) = '2'`, recursion moves forward to fill `(1, 0)`, `(1, 2)`,
    `(2, 1)`, and `(2, 2)` with valid non-clashing digits, and once all rows
    are finished (`row == 9` in full 9x9 Sudoku), returns `true` and keeps
    the completed grid.


================================================================================
PART 4: TIME & SPACE COMPLEXITY
================================================================================

Let:
  - `m` be the number of empty cells (`'.'`) on the `9 x 9` board (at most `81`).
  - `9` be the number of digits (`'1'` to `'9'`) we try per empty cell.

TIME COMPLEXITY: O(9^m)  (bounded by O(9^81) in the worst case)
----------------------------------------------------------------
- **Why `9^m`:** In the worst case, each of the `m` empty cells has up to `9`
  digit choices before backtracking, forming a branching tree of up to `9^m`
  states.
- **Why `isSafe` is constant time:** Checking 9 cells in the row, 9 in the
  column, and 9 in the 3x3 box is a fixed small number of steps (`O(1)`).
- In practice, pruning invalid branches via `isSafe` makes valid Sudoku puzzles
  solve much faster than the worst-case bound.

SPACE COMPLEXITY: O(m)  (at most O(81) = O(1) for a fixed 9x9 board)
-------------------------------------------------------------------
- **Why `O(m)`:** The recursion call stack goes at most one level deep per empty
  cell (`m` levels, at most `81` calls deep).
- Because the board size is always fixed at `9 x 9` (`m <= 81`), `O(m)` is
  also bounded by a constant `O(81)` (often written as `O(1)` when treating
  the `9 x 9` grid as fixed size, or `O(m)` in terms of empty cells).


================================================================================
PART 5: MOST PROBABLE INTERVIEW QUESTIONS
================================================================================

Q1: How do you compute the starting index of the 3x3 box for `(row, col)`?
--------------------------------------------------------------------------------
Spoken Answer:
  "I use `startRow = (row / 3) * 3` and `startCol = (col / 3) * 3`. Integer
  division by `3` groups indices `0..2`, `3..5`, and `6..8` into box `0`, `1`,
  or `2`, and multiplying by `3` gives the exact top-left row or column of that
  3x3 box."

Q2: Why do we reset `board[row][col] = '.'` when `helper` returns `false`?
--------------------------------------------------------------------------------
Spoken Answer:
  "Because we modify `board` in place to save memory. If a chosen digit leads
  to a dead end later, we must erase it back to `'.'` before trying the next
  digit or returning `false`—otherwise a leftover invalid digit would pollute
  future checks."

Q3: How could you optimize `isSafe` if an interviewer asks for faster lookups?
--------------------------------------------------------------------------------
Spoken Answer:
  "Right now `isSafe` scans rows, columns, and the 3x3 box on every check. I can
  keep three boolean arrays (or bitmasks) for `rows[9][9]`, `cols[9][9]`, and
  `boxes[9][9]` so checking whether a digit is used takes `O(1)` instead of
  looping over cells, and toggle them during DO and UNDO."
 */
