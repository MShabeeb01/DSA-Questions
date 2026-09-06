class Solution { // LeetCode 2596: Check Knight Tour Configuration

    public boolean isValid(int[][] grid, int r, int c, int n, int expVal) {
        // Base Case 1: Out of bounds check or current cell does not match expected value
        if (r < 0 || c < 0 || r >= n || c >= n || grid[r][c] != expVal) {
            return false;
        }

        // Base Case 2: Successfully reached the last move (n * n - 1)
        if (expVal == n * n - 1) {
            return true;
        }

        // 8 possible L-shaped moves of a knight
        boolean ans1 = isValid(grid, r - 2, c + 1, n, expVal + 1);
        boolean ans2 = isValid(grid, r - 1, c + 2, n, expVal + 1);
        boolean ans3 = isValid(grid, r + 1, c + 2, n, expVal + 1);
        boolean ans4 = isValid(grid, r + 2, c + 1, n, expVal + 1);
        boolean ans5 = isValid(grid, r + 2, c - 1, n, expVal + 1);
        boolean ans6 = isValid(grid, r + 1, c - 2, n, expVal + 1);
        boolean ans7 = isValid(grid, r - 1, c - 2, n, expVal + 1);
        boolean ans8 = isValid(grid, r - 2, c - 1, n, expVal + 1);

        return ans1 || ans2 || ans3 || ans4 || ans5 || ans6 || ans7 || ans8;
    }

    public boolean checkValidGrid(int[][] grid) {
        // Knight must always begin at the top-left cell (0, 0) with value 0
        if (grid[0][0] != 0) {
            return false;
        }
        return isValid(grid, 0, 0, grid.length, 0);
    }

    public static void main(String[] args) { // Driver function
        Solution solver = new Solution();

        int[][] validGrid = {
            {0, 11, 16, 5, 20},
            {17, 4, 19, 10, 15},
            {12, 1, 8, 21, 6},
            {3, 18, 23, 14, 9},
            {24, 13, 2, 7, 22}
        };

        System.out.println("Is valid knight tour: " + solver.checkValidGrid(validGrid));
    }
}

/*
==================== SUMMARY ====================

Approach:
1. Verify prerequisite: The tour MUST begin at (0, 0) with step value 0. If `grid[0][0] != 0`, return `false`.
2. Recursively trace the step number using `expVal` starting at 0:
   - Check boundary constraints: `0 <= r < n` and `0 <= c < n`.
   - Check value equality: `grid[r][c] == expVal`.
   - If bounds or value mismatch occurs, return `false`.
3. If `expVal == n * n - 1`, every cell has been visited in a valid knight sequence; return `true`.
4. Branch into the 8 valid knight jump offsets:
   - (r - 2, c + 1), (r - 1, c + 2), (r + 1, c + 2), (r + 2, c + 1)
   - (r + 2, c - 1), (r + 1, c - 2), (r - 1, c - 2), (r - 2, c - 1)
5. Return the logical OR (`||`) across all 8 paths. Because all step numbers `0` to `n^2 - 1` are unique in a valid tour, at most ONE move will match `expVal + 1`.

-------------------------------------------------

Recursion / Path Trace

Sample 3x3 Grid Segment:
[0, 3, 6]
[5, 8, 1]
[2, 7, 4]

Starting Call: isValid(grid, 0, 0, n=3, expVal=0)

---------------------------------------------------------------------------------------------------------------
Call Depth | Position (r, c) | Value in Grid | Expected Value (expVal) | Valid Knight Jump? | Next Call
---------------------------------------------------------------------------------------------------------------
1          | (0, 0)          | 0             | 0                       | Start cell (0, 0)  | Move to (1, 2)
2          | (1, 2)          | 1             | 1                       | (r+1, c+2) -> [1]  | Move to (2, 0)
3          | (2, 0)          | 2             | 2                       | (r+1, c-2) -> [2]  | Move to (0, 1)
4          | (0, 1)          | 3             | 3                       | (r-2, c+1) -> [3]  | Move to (2, 2)
5          | (2, 2)          | 4             | 4                       | (r+2, c+1) -> [4]  | Move to (1, 0)
6          | (1, 0)          | 5             | 5                       | (r-1, c-2) -> [5]  | Move to (0, 2)
7          | (0, 2)          | 6             | 6                       | (r-1, c+2) -> [6]  | Move to (2, 1)
8          | (2, 1)          | 7             | 7                       | (r+2, c-1) -> [7]  | Move to (1, 1)
9          | (1, 1)          | 8             | 8                       | expVal == 3*3 - 1  | RETURN TRUE
---------------------------------------------------------------------------------------------------------------

Output:
Is valid knight tour: true

Complexity:
- Time Complexity : O(n^2) — Each value from 0 to n^2 - 1 appears at most once; the recursion evaluates each valid step sequentially.
- Space Complexity: O(n^2) — Maximum call stack depth equals n^2 in a valid complete tour.

=================================================
*/
