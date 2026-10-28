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
                       VISUAL EXPLANATION (AT END OF CODE)
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
3. TOTAL TRAPPED WATER:

   Total = 0 + 2 + 4 + 0 + 2 + 3 + 0 = 11 units
   
   Time Complexity  : O(n) -> 3 linear passes through the array
   Space Complexity : O(n) -> 2 auxiliary arrays of size n
=============================================================================
*/
