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
--------------- QUICK RECALL ---------------

1. Create leftMax[]
   -> Highest bar from LEFT.

2. Create rightMax[]
   -> Highest bar from RIGHT.

3. Water Level
   -> min(leftMax, rightMax)

4. Trapped Water
   -> Water Level - Current Height

5. Add all trapped water.

Time Complexity  : O(n)
Space Complexity : O(n)

--------------------------------------------
*/
