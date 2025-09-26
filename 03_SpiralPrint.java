import java.util.*;

public class ArraysCC{

    // Function to print matrix in spiral order
    public static void printSpiral(int matrix[][]){
        // Defining the boundaries of the spiral
        int startrow = 0;                     // Starting row index
        int startcol = 0;                     // Starting column index
        int endrow = matrix.length - 1;       // Ending row index
        int endcol = matrix[0].length - 1;    // Ending column index

        // Loop until all rows and columns are covered
        while(startrow <= endrow && startcol <= endcol){
            
            // 1️⃣ Print the TOP boundary (left → right)
            for(int j = startcol; j <= endcol; j++){
                System.out.print(matrix[startrow][j] + " ");
            }

            // 2️⃣ Print the RIGHT boundary (top → bottom)
            for(int i = startrow + 1; i <= endrow; i++){
                System.out.print(matrix[i][endcol] + " ");
            }

            // 3️⃣ Print the BOTTOM boundary (right → left)
            for(int j = endcol - 1; j >= startcol; j--){
                // If top row = bottom row, then it's already printed
                if(startrow == endrow){
                    break;
                }
                System.out.print(matrix[endrow][j] + " ");
            }

            // 4️⃣ Print the LEFT boundary (bottom → top)
            for(int i = endrow - 1; i >= startrow + 1; i--){
                // If left col = right col, then it's already printed
                if(startcol == endcol){
                    break;
                }
                System.out.print(matrix[i][startcol] + " ");
            }

            // Move inward for the next spiral loop
            startcol++;
            startrow++;
            endcol--;
            endrow--;
        }
        System.out.println();
    }

    public static void main(String args[]){
        // Example matrix
        int matrix[][] = {
                          {1,  2,  3,  4},
                          {5,  6,  7,  8},
                          {9,  10, 11, 12},
                          {13, 14, 15, 16}
                         };

        // Function call
        printSpiral(matrix);  // Output → 1 2 3 4 8 12 16 15 14 13 9 5 6 7 11 10
    }
}
