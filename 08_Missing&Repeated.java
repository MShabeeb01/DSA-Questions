import java.util.HashSet; // Import HashSet class

public class Main { // Main class

    public static int[] findMissingAndRepeatedValues(int[][] grid) { // Function to find repeated and missing numbers
        int n = grid.length; // Size of the grid (n x n)

        HashSet<Integer> set = new HashSet<>(); // Stores visited numbers
        int repeated = 0; // Stores the repeated number

        long actualSum = 0; // Sum of numbers present in the grid
        long expectedSum = (long) n * n * (n * n + 1) / 2; // Sum of numbers from 1 to n²

        for (int i = 0; i < n; i++) { // Traverse each row
            for (int j = 0; j < n; j++) { // Traverse each column
                actualSum += grid[i][j]; // Add current value to the sum

                if (set.contains(grid[i][j])) { // If number already exists
                    repeated = grid[i][j]; // Save it as repeated
                } else {
                    set.add(grid[i][j]); // Otherwise add it to the HashSet
                }
            }
        }

        int missing = (int) (expectedSum + repeated - actualSum); // Calculate missing number

        return new int[]{repeated, missing}; // Return {repeated, missing}
    }

    public static void main(String[] args) { // Driver function
        int[][] grid = {
            {1, 3},
            {2, 2}
        }; // Sample input

        int[] ans = findMissingAndRepeatedValues(grid); // Call function

        System.out.println("Repeated = " + ans[0]); // Print repeated number
        System.out.println("Missing = " + ans[1]); // Print missing number
    }
}

/*
==================== SUMMARY ====================

Approach:
1. Traverse the matrix.
2. Store each number in a HashSet.
3. If a number is already present, it is the repeated number.
4. Keep calculating the actual sum.
5. Calculate the expected sum from 1 to n².
6. Missing = Expected Sum + Repeated - Actual Sum.
7. Return {Repeated, Missing}.

-------------------------------------------------

Iteration

Input:
grid = [[1,3],
        [2,2]]

Expected Sum = 10

---------------------------------------------------
Step | Current | HashSet     | Actual Sum | Repeated
---------------------------------------------------
1    |    1    | {1}         |     1      |   -
2    |    3    | {1,3}       |     4      |   -
3    |    2    | {1,2,3}     |     6      |   -
4    |    2    | {1,2,3}     |     8      |   2
---------------------------------------------------

Missing = 10 + 2 - 8 = 4

Output:
Repeated = 2
Missing = 4

Time Complexity : O(n²)

Space Complexity : O(n²)

=================================================
*/
