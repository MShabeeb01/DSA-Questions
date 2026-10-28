import java.util.*;

public class ArraysCC {

    // Function to calculate trapped rainwater
    public static int TrappedRainwater(int height[]) {

        int n = height.length;

        // ---------- Left Max Array ----------
        int leftMax[] = new int[n];
        leftMax[0] = height[0];

        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(height[i], leftMax[i - 1]);
        }

        // ---------- Right Max Array ----------
        int rightMax[] = new int[n];
        rightMax[n - 1] = height[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(height[i], rightMax[i + 1]);
        }

        // ---------- Calculate Trapped Water ----------
        int trappedWater = 0;

        for (int i = 0; i < n; i++) {
            int waterLevel = Math.min(leftMax[i], rightMax[i]);
            trappedWater += waterLevel - height[i];
        }

        return trappedWater;
    }

    public static void main(String args[]) {

        int height[] = {4, 2, 0, 6, 3, 2, 5};

        System.out.println("The Area of Trapped Rainwater is: "
                + TrappedRainwater(height));
    }
}

/*
=============================================================================
                       VISUAL EXPLANATION & SUMMARY
=============================================================================

Input Array: height[] = { 4, 2, 0, 6, 3, 2, 5 }

[###] = Solid elevation bar (height[i])
[ ~ ] = Trapped rainwater unit

Height
  6 |                   [###]
  5 |                   [###]                         [###]
  4 |    [###]  [ ~ ]   [ ~ ]   [###]                 [###]
  3 |    [###]  [ ~ ]   [ ~ ]   [###]   [###]  [ ~ ]  [###]
  2 |    [###]  [###]   [ ~ ]   [###]   [###]  [###]  [###]
  1 |    [###]  [###]   [ ~ ]   [###]   [###]  [###]  [###]
  0 +------+------+------+------+------+------+------+---
Idx:       0      1      2      3       4      5      6
Bar Ht:    4      2      0      6       3      2      5

-----------------------------------------------------------------------------
1. ARRAY VALUES AT EACH INDEX:

   Index i      :   0    1    2    3    4    5    6
   -------------------------------------------------
   height[i]    :   4    2    0    6    3    2    5
   leftMax[i]   :   4    4    4    6    6    6    6   (Scan left -> right)
   rightMax[i]  :   6    6    6    6    5    5    5   (Scan right -> left)

-----------------------------------------------------------------------------
2. WATER LEVEL & TRAPPED WATER CALCULATION:

   Formula:
     Water Level   = min(leftMax[i], rightMax[i])
     Trapped Water = Water Level - height[i]

   - Index 0: min(4, 6) = 4  -->  4 - 4 = 0 units (Edge boundary)
   - Index 1: min(4, 6) = 4  -->  4 - 2 = 2 units (Trapped by bars 0 and 3)
   - Index 2: min(4, 6) = 4  -->  4 - 0 = 4 units (Trapped by bars 0 and 3)
   - Index 3: min(6, 6) = 6  -->  6 - 6 = 0 units (Peak bar, cannot hold water)
   - Index 4: min(6, 5) = 5  -->  5 - 3 = 2 units (Trapped by bars 3 and 6)
   - Index 5: min(6, 5) = 5  -->  5 - 2 = 3 units (Trapped by bars 3 and 6)
   - Index 6: min(6, 5) = 5  -->  5 - 5 = 0 units (Edge boundary)

-----------------------------------------------------------------------------
3. FUNCTION-BY-FUNCTION & COMPONENT SUMMARY:

   A. ArraysCC (Class):
      - Encapsulates the entire program; serves as the namespace.

   B. TrappedRainwater(int height[]) (Core Logic Method):
      - Purpose: Computes total units of rainwater retained between the bars.
      - Core Mechanics:
        1. Allocates auxiliary arrays `leftMax` and `rightMax` of size n.
        2. Computes the maximum height to the left of each index using
           prefix maximums: leftMax[i] = max(height[i], leftMax[i - 1]).
        3. Computes the maximum height to the right of each index using
           suffix maximums: rightMax[i] = max(height[i], rightMax[i + 1]).
        4. Calculates bounded water over column i by taking the limiting
           boundary: min(leftMax[i], rightMax[i]) - height[i].
        5. Accumulates and returns total trapped water.

   C. main(String args[]) (Execution Entry Point):
      - Initializes sample input array: {4, 2, 0, 6, 3, 2, 5}.
      - Passes the input array to TrappedRainwater().
      - Prints the final calculated result (11) to the console.

-----------------------------------------------------------------------------
4. COMPLEXITY ANALYSIS:

   - Time Complexity: O(n)
     Iterates across the array three separate times:
     (1 pass for leftMax, 1 pass for rightMax, 1 pass for trappedWater).

   - Space Complexity: O(n)
     Requires 2 additional arrays (leftMax and rightMax), each of size n.
=============================================================================
*/
