class Solution {
    public void sortColors(int[] nums) {
        int n = nums.length;
        int low = 0, mid = 0, high = n - 1;

        while(mid <= high){
            if(nums[mid] == 0){
                int temp = nums[mid];
                nums[mid] = nums[low];
                nums[low] = temp;
                mid++;
                low++;
            }
            else if(nums[mid] == 1){
                mid++;
            }
            else{
                int temp = nums[mid];
                nums[mid] = nums[high];
                nums[high] = temp;
                high--;
            }
        }
    }
}


/*
SUMMARY:

- Use the Dutch National Flag algorithm with 3 pointers.
- low → position where the next 0 should be placed.
- mid → current element being checked.
- high → position where the next 2 should be placed.

- If nums[mid] == 0:
  Swap nums[mid] with nums[low].
  Move both low and mid forward.

- If nums[mid] == 1:
  1 is already in its correct section.
  Move mid forward.

- If nums[mid] == 2:
  Swap nums[mid] with nums[high].
  Move high backward.
  Do NOT move mid because the swapped element still needs to be checked.

ITERATION:

nums = [2,0,2,1,1,0]

low = 0, mid = 0, high = 5

mid = 0 → nums[mid] = 2
Swap nums[mid] and nums[high]
nums = [0,0,2,1,1,2]
high--
high = 4

mid = 0 → nums[mid] = 0
Swap nums[mid] and nums[low]
nums = [0,0,2,1,1,2]
low++
mid++

low = 1, mid = 1, high = 4

mid = 1 → nums[mid] = 0
Swap nums[mid] and nums[low]
nums = [0,0,2,1,1,2]
low++
mid++

low = 2, mid = 2, high = 4

mid = 2 → nums[mid] = 2
Swap nums[mid] and nums[high]
nums = [0,0,1,1,2,2]
high--

mid = 2 → nums[mid] = 1
Move mid++

mid = 3 → nums[mid] = 1
Move mid++

mid = 4, high = 3
mid > high → STOP

FINAL ANSWER:

[0,0,1,1,2,2]

TIME COMPLEXITY: O(n)
SPACE COMPLEXITY: O(1)
*/
