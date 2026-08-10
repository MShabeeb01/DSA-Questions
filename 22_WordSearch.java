class Solution {
    public boolean exist(char[][] board, String word) {

        int m = board.length;
        int n = board[0].length;

        // Try starting the word from every cell
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (dfs(board, word, i, j, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfs(char[][] board, String word, int i, int j, int index) {

        // All characters of the word are matched
        if (index == word.length()) {
            return true;
        }

        // Check boundaries and character match
        if (i < 0 || j < 0 ||
            i >= board.length || j >= board[0].length ||
            board[i][j] != word.charAt(index)) {

            return false;
        }

        // Mark the current cell as visited
        // so that it cannot be used again in this path
        char temp = board[i][j];
        board[i][j] = '#';

        // Explore all four directions
        boolean found =
                dfs(board, word, i + 1, j, index + 1) ||  // Down
                dfs(board, word, i - 1, j, index + 1) ||  // Up
                dfs(board, word, i, j + 1, index + 1) ||  // Right
                dfs(board, word, i, j - 1, index + 1);    // Left

        // Backtrack:
        // Restore the original character so this cell
        // can be used in another search path.
        board[i][j] = temp;

        return found;
    }
}

/*

==================================================
## Code Summary
==================================================

1. Start from every cell in the board.

2. For each cell, call DFS to check whether
   the word can be formed starting from there.

3. In DFS:

   - Check whether the entire word is matched.
   - Check boundaries.
   - Check whether the current character matches.
   - Mark the current cell as visited.
   - Search in all 4 directions.
   - Restore the cell using backtracking.

4. The four possible directions are:

   Down
   Up
   Right
   Left

5. A cell cannot be used more than once in the
   same path.

6. We temporarily change the current character
   to '#' to mark it as visited.

7. After exploring all directions, restore the
   original character.

8. If any path successfully matches the complete
   word, return true.

9. If no path works, return false.


==================================================
## Iteration
==================================================

Input:

Board:

A  B  C  E
S  F  C  S
A  D  E  E

Word:

ABCCED


--------------------------------------------------
## Step 1: Start Searching
--------------------------------------------------

The outer loops try every cell.

Start at:

i = 0
j = 0

board[0][0] = A

word[0] = A

Characters match.

Call:

dfs(0, 0, 0)


--------------------------------------------------
## Step 2: Match A
--------------------------------------------------

Current:

A

Mark A as visited:

#  B  C  E
S  F  C  S
A  D  E  E

index = 1

Next character:

B

Explore four directions.


--------------------------------------------------
## Step 3: Move Right
--------------------------------------------------

From A:

i = 0
j = 1

board[0][1] = B

word[1] = B

Match found.

Mark B as visited:

#  #  C  E
S  F  C  S
A  D  E  E

index = 2

Next character:

C


--------------------------------------------------
## Step 4: Move Right
--------------------------------------------------

From B:

i = 0
j = 2

board[0][2] = C

word[2] = C

Match found.

Mark C:

#  #  #  E
S  F  C  S
A  D  E  E

index = 3

Next character:

C


--------------------------------------------------
## Step 5: Move Down
--------------------------------------------------

From C:

i = 1
j = 2

board[1][2] = C

word[3] = C

Match found.

Mark C:

#  #  #  E
S  F  #  S
A  D  E  E

index = 4

Next character:

E


--------------------------------------------------
## Step 6: Move Down
--------------------------------------------------

From C:

i = 2
j = 2

board[2][2] = E

word[4] = E

Match found.

Mark E:

#  #  #  E
S  F  #  S
A  D  #  E

index = 5

Next character:

D


--------------------------------------------------
## Step 7: Move Left
--------------------------------------------------

From E:

i = 2
j = 1

board[2][1] = D

word[5] = D

Match found.

Mark D:

#  #  #  E
S  F  #  S
A  #  #  E

index = 6


--------------------------------------------------
## Step 8: Word Completely Matched
--------------------------------------------------

index == word.length()

6 == 6

Therefore:

return true


--------------------------------------------------
## Final Result
--------------------------------------------------

The word:

ABCCED

exists in the board.

Therefore:

return true


==================================================
## Why Do We Mark Cells With '#'? 
==================================================

Suppose we are currently using:

A → B → C

We cannot use the same cell again in
the same path.

So we temporarily change:

A → #

This tells DFS:

"This cell has already been used."


==================================================
## Why Do We Backtrack?
==================================================

After finishing one search path, we restore
the original character.

For example:

Before:

A

Mark:

#

After searching:

A

This allows the same cell to be used in
a different search path.


==================================================
## Four Directions
==================================================

From every cell we try:

i + 1, j    → Down

i - 1, j    → Up

i, j + 1    → Right

i, j - 1    → Left


==================================================
## Important DFS Variables
==================================================

i, j

→ Current position in the board.


index

→ Current character position in the word.

For example:

index = 0 → First character
index = 1 → Second character
index = 2 → Third character


temp

→ Stores the original character before
   replacing it with '#'.

found

→ Stores whether the word was successfully
   found from the current cell.


==================================================
## Time Complexity
==================================================

O(M × N × 4^L)

M × N:

Try every cell as a starting point.

4^L:

At each character, DFS can explore up to
4 directions.

L:

Length of the word.


==================================================
## Space Complexity
==================================================

O(L)

Because the DFS recursion can go as deep
as the length of the word.

No separate visited matrix is used because
the board itself is temporarily modified.

*/
