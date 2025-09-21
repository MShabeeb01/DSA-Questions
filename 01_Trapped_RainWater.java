import java.util.*;

public class ArraysCC{
    public static int Trappedrainwater(int height[]){
        int n = height.length;

        //Calculate Left Max Boundary --> This is an Auxillary Array.
        int leftMax[] = new int[n];
        leftMax[0] = height[0]; //Because at the beggining the first bar will have the Max height.
        for(int i=1; i<n; i++){
            leftMax[i] = Math.max(height[i], leftMax[i-1]);
        }

        //Calculate Right Max Boundary --> Auxillary Array
        int rightMax[] = new int [n];
        rightMax[n-1] = height[n-1]; //Because at the end the last Bar will have the Max height.
        for(int i=n-2; i>=0; i--){
            rightMax[i] = Math.max(height[i],rightMax[i+1]);
        }

        int trappedWater = 0;
        //Loop
        for(int i=0; i<n; i++){
            //Water level = min(leftMax , rightMax)
            int waterLevel = Math.min(leftMax[i],rightMax[i]);

            //Trapped water = waterlevel - height[i]
            trappedWater += waterLevel - height[i];
        }

        return trappedWater;
    }
    
    public static void main(String args[]){
        int height[] = {4,2,0,6,3,2,5};
        System.out.println("The Area of Trapped Rainwater is: " + Trappedrainwater(height));
    }

}
// The Time Complexity will be O(n).
