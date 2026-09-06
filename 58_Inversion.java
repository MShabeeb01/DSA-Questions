import java.util.Arrays; // Import Arrays utility class

class Solution { // LeetCode 3193: Count the Number of Inversions (Dynamic Programming + Prefix Sums)

    private static final int MOD = 1_000_000_007;

    public int numberOfPermutations(int n, int[][] requirements) {
        // Map requirements to an array indexed by prefix end index
        int[] req = new int[n];
        Arrays.fill(req, -1);
        int maxInv = 0;

        for (int[] r : requirements) {
            req[r[0]] = r[1];
            maxInv = Math.max(maxInv, r[1]);
        }

        // Base Check: A single element prefix perm[0..0] must have strictly 0 inversions
        if (req[0] > 0) {
            return 0;
        }

        // dp[inv] stores the number of valid permutations having 'inv' inversions
        int[] dp = new int[maxInv + 1];
        dp[0] = 1; // Only 1 permutation of length 1 exists with 0 inversions

        // Iteratively insert element with relative value 'i' into the permutation
        for (int i = 1; i < n; i++) {
            int[] nextDp = new int[maxInv + 1];

            // Build prefix sum array of dp table to evaluate transitions in O(1)
            int[] prefix = new int[maxInv + 2];
            for (int k = 0; k <= maxInv; k++) {
                prefix[k + 1] = (prefix[k] + dp[k]) % MOD;
            }

            for (int j = 0; j <= maxInv; j++) {
                // If a constraint is imposed at prefix end i, skip all non-matching inversion counts
                if (req[i] != -1 && j != req[i]) {
                    continue;
                }

                // Inserting element at step i can contribute between 0 and i new inversions
                // Transitions come from dp[j - k] where 0 <= k <= min(i, j)
                int minPrev = Math.max(0, j - i);
                int ways = (prefix[j + 1] - prefix[minPrev] + MOD) % MOD;
                nextDp[j] = ways;
            }

            dp = nextDp; // Advance DP table to next length
        }

        return dp[req[n - 1]];
    }

    public static void main(String[] args) { // Driver method
        Solution solver = new Solution();
        int n = 3;
        int[][] requirements = {{2, 2}, {0, 0}};

        int result = solver.numberOfPermutations(n, requirements);

        System.out.println("Valid Permutations Count: " + result);
    }
}

/*
==================== SUMMARY ====================

Problem Breakdown:
Given a length `n` and constraints `requirements[i] = [end_i, cnt_i]`, count the number of permutations 
of [0, 1, ..., n - 1] such that prefix `perm[0..end_i]` contains exactly `cnt_i` inversions.

State Transition (Relative Insertion DP):
1. When inserting the (i+1)-th element (index `i`), placing it at position `k` from the right introduces `k` new inversions (0 <= k <= i).
2. Transition Equation:
   dp[i][j] = sum(dp[i - 1][j - k]) for 0 <= k <= min(i, j)
            = dp[i - 1][j] + dp[i - 1][j - 1] + ... + dp[i - 1][j - i]
3. Optimization via Prefix Sums:
   - Calculating the window sum directly takes O(i), causing O(n * maxInv^2) time.
   - Precomputing `prefix[k]` reduces the range sum query to O(1):
     `ways = prefix[j + 1] - prefix[max(0, j - i)]`
4. Constraint Enforcement:
   - At step `i`, if `req[i] != -1`, enforce `dp[i][j] = 0` for all `j != req[i]`.

-------------------------------------------------

Trace Table: Example 1 (n = 3, requirements = [[2, 2], [0, 0]])

req mapping: req[0] = 0, req[1] = -1, req[2] = 2, maxInv = 2

---------------------------------------------------------------------------------------------------------
Step | Element Added (i) | Valid Range (j) | Formula Applied                          | dp state [0, 1, 2]
---------------------------------------------------------------------------------------------------------
0    | Init (len 1)      | j = 0           | Base condition (req[0] == 0)             | [1, 0, 0]
1    | i = 1 (len 2)     | j = 0..2        | k in [0, 1]:                             |
     |                   | j = 0           | sum(dp[0]) = 1                           |
     |                   | j = 1           | sum(dp[1] + dp[0]) = 1                   |
     |                   | j = 2           | sum(dp[2] + dp[1]) = 0                   | [1, 1, 0]
2    | i = 2 (len 3)     | j = 2 (req=2)   | Only j = req[2] evaluated:               |
     |                   | j = 2           | sum(dp[2..0]) = dp[2] + dp[1] + dp[0] = 2| [0, 0, 2]
---------------------------------------------------------------------------------------------------------

Result: dp[req[2]] = dp[2] = 2 (Permutations: [2, 0, 1] and [1, 2, 0])

Complexity:
- Time Complexity : O(n * maxInv) — With prefix sums, each inversion state transitions in O(1).
- Space Complexity: O(maxInv) — Maintained using 1D rolling array optimization.

=================================================
*/
