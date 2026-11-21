class Solution {
    public boolean isValid(int[][] grid, int r, int c, int n, int expVal) {
        if (r < 0 || c < 0 || r >= n || c >= n || grid[r][c] != expVal) { // Out of bounds or wrong move value
            return false;
        }

        if (expVal == n * n - 1) { // Reached the final move value (n^2 - 1)
            return true;
        }

        boolean ans1 = isValid(grid, r - 2, c + 1, n, expVal + 1); // Knight move 1 (up 2, right 1)
        boolean ans2 = isValid(grid, r - 1, c + 2, n, expVal + 1); // Knight move 2 (up 1, right 2)
        boolean ans3 = isValid(grid, r + 1, c + 2, n, expVal + 1); // Knight move 3 (down 1, right 2)
        boolean ans4 = isValid(grid, r + 2, c + 1, n, expVal + 1); // Knight move 4 (down 2, right 1)
        boolean ans5 = isValid(grid, r + 2, c - 1, n, expVal + 1); // Knight move 5 (down 2, left 1)
        boolean ans6 = isValid(grid, r + 1, c - 2, n, expVal + 1); // Knight move 6 (down 1, left 2)
        boolean ans7 = isValid(grid, r - 1, c - 2, n, expVal + 1); // Knight move 7 (up 1, left 2)
        boolean ans8 = isValid(grid, r - 2, c - 1, n, expVal + 1); // Knight move 8 (up 2, left 1)

        return ans1 || ans2 || ans3 || ans4 || ans5 || ans6 || ans7 || ans8; // True if any move completes path
    }

    public boolean checkValidGrid(int[][] grid) {
        if (grid[0][0] != 0) { // Knight tour must start at top-left cell with value 0
            return false;
        }
        return isValid(grid, 0, 0, grid.length, 0); // Start checking from (0, 0) expecting 0
    }
}

/*
================================================================================
PART 2: SUMMARY OF THE APPROACH & LINE-BY-LINE EXPLANATION
================================================================================

SUMMARY OF THE APPROACH
-----------------------
This code solves LeetCode's **Check Knight Tour Configuration** using **Depth-First
Search (DFS) / Recursion** along valid chess knight moves:

1. **Check Starting Cell:** A valid knight tour must begin at `(0, 0)` with move
   number `0`. If `grid[0][0] != 0`, return `false` right away.
2. **Match Expected Step (`expVal`):** At cell `(r, c)`, verify the cell is
   inside the `n x n` board and `grid[r][c] == expVal`.
3. **Reach the End or Try All 8 Knight Moves:** If `expVal` equals `n * n - 1`,
   every cell `0` through `n^2 - 1` was visited in valid knight order, so return
   `true`. Otherwise, recursively check all 8 L-shaped knight jumps for
   `expVal + 1` and return `true` if any branch succeeds.


LINE-BY-LINE EXPLANATION
-----------------------

Line: class Solution {
What it does:
  - Declares the `Solution` class containing the LeetCode `checkValidGrid`
    method and the recursive `isValid` helper method.

--------------------------------------------------------------------------------

Line: public boolean isValid(int[][] grid, int r, int c, int n, int expVal) {
What it does:
  - Recursive helper that checks whether `(r, c)` holds the expected move value
    `expVal` on an `n x n` `grid` and can continue a valid knight tour up to
    `n * n - 1`.

--------------------------------------------------------------------------------

Line: if (r < 0 || c < 0 || r >= n || c >= n || grid[r][c] != expVal) {
          return false;
      }
What it does:
  - **Invalid-path base case:** Checks if row `r` or column `c` is outside
    `0 .. n - 1`, or if `grid[r][c]` does not equal `expVal`.
  - If any condition is true, this branch cannot be part of a valid knight tour,
    so it returns `false` and pops this call frame off the call stack.

--------------------------------------------------------------------------------

Line: if (expVal == n * n - 1) {
          return true;
      }
What it does:
  - **Success base case:** Since `grid[r][c] == expVal` already passed above,
    reaching `n * n - 1` means the knight has stepped onto the final square of
    an `n x n` grid in valid order.
  - Returns `true` immediately.

--------------------------------------------------------------------------------

Line: boolean ans1 = isValid(grid, r - 2, c + 1, n, expVal + 1);
      boolean ans2 = isValid(grid, r - 1, c + 2, n, expVal + 1);
      boolean ans3 = isValid(grid, r + 1, c + 2, n, expVal + 1);
      boolean ans4 = isValid(grid, r + 2, c + 1, n, expVal + 1);
      boolean ans5 = isValid(grid, r + 2, c - 1, n, expVal + 1);
      boolean ans6 = isValid(grid, r + 1, c - 2, n, expVal + 1);
      boolean ans7 = isValid(grid, r - 1, c - 2, n, expVal + 1);
      boolean ans8 = isValid(grid, r - 2, c - 1, n, expVal + 1);
What it does:
  - Explores all 8 possible knight jumps (2 squares in one direction and 1
    square perpendicular) looking for the next step `expVal + 1`.
  - Each call pushes a new frame onto the call stack and returns `true` or
    `false` when finished.

--------------------------------------------------------------------------------

Line: return ans1 || ans2 || ans3 || ans4 || ans5 || ans6 || ans7 || ans8;
What it does:
  - Combines all 8 recursive results with logical OR (`||`).
  - Returns `true` if at least one knight jump leads to a complete valid tour;
    returns `false` if none of the 8 jumps work.

--------------------------------------------------------------------------------

Line: public boolean checkValidGrid(int[][] grid) {
          if (grid[0][0] != 0) {
              return false;
          }
          return isValid(grid, 0, 0, grid.length, 0);
      }
What it does:
  - Main LeetCode entry point.
  - Rejects any board that does not start with `0` at top-left `(0, 0)`.
  - Otherwise starts recursion at `(r = 0, c = 0)` with `n = grid.length` and
    `expVal = 0`.


================================================================================
PART 3: STEP-BY-STEP ITERATION WITH VISUAL REPRESENTATION
================================================================================

General example input (3x3 grid, `n = 3`, target final value `n * n - 1 = 8`):

  grid = [
    [0, 3, 6],
    [5, 8, 1],
    [2, 7, 4]
  ]

  Visual grid with coordinates (r, c):
         col 0   col 1   col 2
       +-------+-------+-------+
  row 0|   0   |   3   |   6   |
       +-------+-------+-------+
  row 1|   5   |   8   |   1   |
       +-------+-------+-------+
  row 2|   2   |   7   |   4   |
       +-------+-------+-------+

Note on how "added / tested / deleted" applies here:
  - Each recursive call **adds (pushes)** a stack frame onto the call stack and
    **tests** boundary/value conditions.
  - When a recursive call returns (`false` or `true`), that frame is
    **deleted (popped)** from the call stack. In this grid-lookup design, we do
    not mutate or remove cells from `grid`.

--------------------------------------------------------------------------------
STEP 1: Entry check in checkValidGrid(grid)
--------------------------------------------------------------------------------
- Tested: `grid[0][0] != 0` -> `0 != 0` -> false (valid start).
- Added / pushed: `isValid(grid, r=0, c=0, n=3, expVal=0)` pushed onto stack.

  Call stack:
    [ checkValidGrid ] -> [ isValid(0, 0, exp=0) ]

--------------------------------------------------------------------------------
STEP 2: Evaluate isValid(grid, r=0, c=0, n=3, expVal=0)
--------------------------------------------------------------------------------
- Tested: Out of bounds? `r=0, c=0` is inside `0..2` -> false.
- Tested: `grid[0][0] != 0`? `0 != 0` -> false.
- Tested: `expVal == 8`? `0 == 8` -> false.
- Action: Tests knight moves for `expVal + 1 = 1` from `(0, 0)`.
  - `ans1` -> `( -2, 1 )`: out of bounds (`r < 0`) -> returns `false` (pushed & popped).
  - `ans2` -> `( -1, 2 )`: out of bounds (`r < 0`) -> returns `false` (pushed & popped).
  - `ans3` -> `( 1, 2 )`: `grid[1][2]` is `1` (`1 == 1`) -> recurses into `isValid(1, 2, exp=1)`.

--------------------------------------------------------------------------------
STEP 3: Evaluate isValid(grid, r=1, c=2, n=3, expVal=1)
--------------------------------------------------------------------------------
- Added / pushed: `isValid(1, 2, exp=1)` on top of stack.
- Tested: `r=1, c=2` in bounds, `grid[1][2] == 1` -> valid. `expVal == 8` -> false.
- Action: From `(1, 2)`, knight moves look for `expVal = 2`:
  - `ans1` -> `( -1, 3 )`: out of bounds -> `false` (pushed & popped).
  - `ans2` -> `( 0, 4 )`: out of bounds -> `false` (pushed & popped).
  - `ans3` -> `( 2, 4 )`: out of bounds -> `false` (pushed & popped).
  - `ans4` -> `( 3, 3 )`: out of bounds -> `false` (pushed & popped).
  - `ans5` -> `( 3, 1 )`: out of bounds -> `false` (pushed & popped).
  - `ans6` -> `( 2, 0 )`: `grid[2][0]` is `2` (`2 == 2`) -> recurses into `isValid(2, 0, exp=2)`.

--------------------------------------------------------------------------------
STEP 4: Follow valid chain expVal = 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8
--------------------------------------------------------------------------------
- At `(2, 0)` (`expVal = 2`):
  - Tested/found move `ans1` -> `(0, 1)` where `grid[0][1] == 3` -> recurses `expVal = 3`.
- At `(0, 1)` (`expVal = 3`):
  - Tested/found move `ans3` -> `(1, 2)` has `1 != 4` (`false`), `ans4` -> `(2, 2)` where `grid[2][2] == 4` -> recurses `expVal = 4`.
- At `(2, 2)` (`expVal = 4`):
  - Tested/found move `ans7` -> `(1, 0)` where `grid[1][0] == 5` -> recurses `expVal = 5`.
- At `(1, 0)` (`expVal = 5`):
  - Tested/found move `ans1` -> `(-1, 1)` (`false`), `ans2` -> `(0, 2)` where `grid[0][2] == 6` -> recurses `expVal = 6`.
- At `(0, 2)` (`expVal = 6`):
  - Tested/found move `ans5` -> `(2, 1)` where `grid[2][1] == 7` -> recurses `expVal = 7`.
- At `(2, 1)` (`expVal = 7`):
  - Tested/found move `ans8` -> `(0, 0)` has `0 != 8` (`false`), `ans2` -> `(1, 3)` (`false`), `ans7` -> `(1, 1)` where `grid[1][1] == 8` -> recurses `expVal = 8`.

--------------------------------------------------------------------------------
STEP 5: Base case hit at isValid(grid, r=1, c=1, n=3, expVal=8)
--------------------------------------------------------------------------------
- Added / pushed: `isValid(1, 1, exp=8)` pushed onto stack.
- Tested: In bounds (`0 <= 1 < 3`, `0 <= 1 < 3`) and `grid[1][1] == 8` -> true.
- Tested: `expVal == n * n - 1` -> `8 == 3 * 3 - 1` (`8 == 8`) -> true!
- Deleted / popped: `isValid(1, 1, exp=8)` returns `true` and pops off stack.

--------------------------------------------------------------------------------
STEP 6: Unwinding / popping frames back to checkValidGrid
--------------------------------------------------------------------------------
- As each caller receives `true` from its matching knight move branch, `ans1 || ... || ans8`
  evaluates to `true` and its stack frame is deleted / popped:
  - `isValid(2, 1, exp=7)` -> returns `true` -> popped.
  - `isValid(0, 2, exp=6)` -> returns `true` -> popped.
  - `isValid(1, 0, exp=5)` -> returns `true` -> popped.
  - `isValid(2, 2, exp=4)` -> returns `true` -> popped.
  - `isValid(0, 1, exp=3)` -> returns `true` -> popped.
  - `isValid(2, 0, exp=2)` -> returns `true` -> popped.
  - `isValid(1, 2, exp=1)` -> returns `true` -> popped.
  - `isValid(0, 0, exp=0)` -> returns `true` -> popped.

  Final call stack state:
    (empty after checkValidGrid returns true)

  Final result returned: `true`.


================================================================================
PART 4: TIME & SPACE COMPLEXITY
================================================================================

Variable definitions:
  - `n`: Number of rows and columns in the square `grid` (`grid.length`).
  - `n * n`: Total number of cells (and total moves, `0` through `n * n - 1`)
    in `grid`.

TIME COMPLEXITY: O(n^2) (with this grid-value check; up to O(8^(n^2)) in pure unconstrained backtracking)
--------------------------------------------------------------------------------
  - Because `grid[r][c] != expVal` immediately rejects any cell that does not
    hold the exact next number `expVal`, at most **one** cell is accepted for
    each value from `0` to `n * n - 1`.
  - There are `n * n` values total, and each accepted cell checks at most `8`
    neighboring moves (`8 * n^2` operations), which simplifies to `O(n^2)`.
  - (Note: If an interviewer asks about general unguided 8-way branching on
    `n^2` steps without value matching, branching factor `8` across `n^2` steps
    is `O(8^(n^2))`, whereas here `grid[r][c] == expVal` restricts each step to
    the single cell holding `expVal`.)

SPACE COMPLEXITY: O(n^2)
------------------------
  - The only extra memory used is the recursion call stack.
  - In a valid tour, recursion goes `n * n` steps deep (from `expVal = 0` up to
    `n * n - 1`), so the maximum call stack depth is `O(n^2)`.


================================================================================
PART 5: MOST PROBABLE INTERVIEW QUESTIONS
================================================================================

Q1: Since this problem mentions a "knight tour," why is the time complexity
    O(n^2) instead of O(8^(n^2))?
--------------------------------------------------------------------------------
Spoken Answer:
  "We aren't searching for a tour from scratch—we're validating a given board
  where each step must match the exact next number `expVal`. At each step, at
  most one cell has `grid[r][c] == expVal`, so we visit at most `n^2` valid
  cells and check up to 8 neighbors per cell, giving `O(n^2)` time."

Q2: Can you make this cleaner and avoid repeating 8 `boolean` variables?
--------------------------------------------------------------------------------
Spoken Answer:
  "Yes. In an interview, I can mention storing two direction arrays like
  `int[] dr = {-2, -1, 1, 2, 2, 1, -1, -2};` and
  `int[] dc = {1, 2, 2, 1, -1, -2, -2, -1};` and looping `i` from `0` to `7`,
  returning `true` as soon as a recursive call returns `true`."

Q3: What edge cases should you check for this problem?
--------------------------------------------------------------------------------
Spoken Answer:
  "First, `grid[0][0] != 0` must return `false` immediately because the knight
  starts at `(0, 0)` at move `0`. Second, a `1 x 1` grid with `[[0]]` is
  valid because `expVal` starts at `0` and equals `1 * 1 - 1 = 0`. Third,
  if a number from `1` to `n^2 - 1` is missing or unreachable by a knight move
  from the previous number, recursion returns `false`."
 */
