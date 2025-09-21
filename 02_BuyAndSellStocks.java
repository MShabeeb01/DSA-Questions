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
