class Solution {
    public int maxArea(int[] height) {
        int maxwater = 0;
        int lp = 0, rp = height.length - 1;

        while(lp < rp){
            int w = rp - lp;
            int ht = Math.min(height[lp], height[rp]);
            int currwater = w * ht;

            maxwater = Math.max(currwater, maxwater);

            if(height[lp] > height[rp]){
                rp--;
            }
            else{
                lp++;
            }
        }

        return maxwater;
    }
}


/*
SUMMARY:

- Use the Two Pointer approach to find the maximum amount of water.
- lp starts from the left and rp starts from the right.
- Width = rp - lp.
- Height = minimum of height[lp] and height[rp].
- Current water = width * height.
- Update maxwater with the maximum value.
- Move the pointer with the smaller height because moving the taller
  pointer cannot increase the container height.

ITERATION:

height = [1,8,6,2,5,4,8,3,7]

lp = 0, rp = 8
width = 8
height = min(1,7) = 1
water = 8 * 1 = 8
maxwater = 8
height[lp] < height[rp] → lp++

lp = 1, rp = 8
width = 7
height = min(8,7) = 7
water = 7 * 7 = 49
maxwater = 49
height[lp] > height[rp] → rp--

lp = 1, rp = 7
width = 6
height = min(8,3) = 3
water = 6 * 3 = 18
maxwater = 49
height[lp] > height[rp] → rp--

lp = 1, rp = 6
width = 5
height = min(8,8) = 8
water = 5 * 8 = 40
maxwater = 49
heights are equal → lp++

Continue moving the pointers until lp >= rp.

FINAL ANSWER:

Maximum Water = 49

TIME COMPLEXITY: O(n)
SPACE COMPLEXITY: O(1)
*/
