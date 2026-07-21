import java.util.*;

public class ArraysCC{
    public static int BuyAndSellStocks(int prices[]){
        int buyprice = Integer.MAX_VALUE;//+Infinity
        int maxProfit = 0;

        for(int i=0; i<prices.length ; i++){
            if(buyprice<prices[i]){ //If the prices[i] is greater than buyprice then there is profit 
                int profit = prices[i] - buyprice; //Todays Profit.
                maxProfit = Math.max(maxProfit,profit);//Overall Profit.
            } else {
                buyprice = prices[i];
            }
        }
        return maxProfit;
    }

    public static void main(String args[]){
        int prices[] = {7,1,5,3,6,4};
        System.out.println("The Profit Gained is : " + BuyAndSellStocks(prices));
    }

// Prices = {7,1,5,3,6,4}

buyPrice = Integer.MAX_VALUE;   // Start with the largest possible value
maxProfit = 0;
// i = 0, price = 7
// 7 is smaller than Infinity → Buy at 7
// buyPrice = 7

// i = 1, price = 1
// 1 is smaller than 7 → Better buying price
// buyPrice = 1

// i = 2, price = 5
// Sell at 5 → Profit = 5 - 1 = 4
// maxProfit = 4

// i = 3, price = 3
// Sell at 3 → Profit = 3 - 1 = 2
// maxProfit remains 4

// i = 4, price = 6
// Sell at 6 → Profit = 6 - 1 = 5
// maxProfit = 5

// i = 5, price = 4
// Sell at 4 → Profit = 4 - 1 = 3
// maxProfit remains 5

// Final Answer = 5
