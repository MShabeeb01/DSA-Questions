/*
==========================================
Approach 1 : Brute Force
==========================================

Idea:
- Pick one element at a time.
- Count how many times it appears in the entire array.
- If its frequency is greater than n/2, return it.
- Otherwise, move to the next element.

Algorithm:
1. Traverse the array using the outer loop.
2. For each element, traverse the array again using the inner loop.
3. Count its frequency.
4. If frequency > n/2, return the element.
5. Repeat until the majority element is found.

Time Complexity  : O(n²)
Reason           : For every element, the entire array is traversed again.

Space Complexity : O(1)
Reason           : Only a few extra variables are used.
*/

import java.util.*;

public class MajorityElementBruteForce {

    // Function to find the majority element
    public static int majorityElement(int[] nums) {

        int n = nums.length;

        // Pick one element at a time
        for (int val : nums) {

            int freq = 0;

            // Count frequency of the current element
            for (int el : nums) {
                if (el == val) {
                    freq++;
                }
            }

            // Return the element if it appears more than n/2 times
            if (freq > n / 2) {
                return val;
            }
        }

        // Problem guarantees a majority element
        return -1;
    }

    public static void main(String[] args) {

        int[] nums = {2, 2, 1, 1, 1, 2, 2};

        int result = majorityElement(nums);

        System.out.println("Majority Element = " + result);
    }
}

/*
==========================================
Dry Run (Iteration)
==========================================

Input:
nums = [2, 2, 1, 1, 1, 2, 2]
n = 7
n/2 = 3

Outer Loop - Iteration 1
val = 2
freq = 0

Inner Loop
Iteration 1 : el = 2 -> 2 == 2 ✓ -> freq = 1
Iteration 2 : el = 2 -> 2 == 2 ✓ -> freq = 2
Iteration 3 : el = 1 -> 1 == 2 ✗ -> freq = 2
Iteration 4 : el = 1 -> 1 == 2 ✗ -> freq = 2
Iteration 5 : el = 1 -> 1 == 2 ✗ -> freq = 2
Iteration 6 : el = 2 -> 2 == 2 ✓ -> freq = 3
Iteration 7 : el = 2 -> 2 == 2 ✓ -> freq = 4

After Inner Loop
freq = 4
Check: freq > n/2
4 > 3 ✓
return 2

Program Ends.

Summary:
1. Outer loop picks one element.
2. Inner loop counts its frequency.
3. If frequency > n/2, return that element.
4. Otherwise, the outer loop picks the next element.
5. Repeat until the majority element is found.

Time Complexity  : O(n²)
Space Complexity : O(1)
*/









/*
==========================================
Approach 2 : Better (Sorting)
==========================================

Idea:
- Sort the array so that equal elements become adjacent.
- Traverse the sorted array and count consecutive occurrences.
- If the count of any element becomes greater than n/2, return it.

Algorithm:
1. Sort the array.
2. Initialize freq = 1 and ans = first element.
3. Traverse from index 1 to n-1.
4. If current element is same as previous, increment freq.
5. Otherwise, reset freq = 1 and update ans.
6. If freq > n/2, return ans.

Time Complexity  : O(n log n)
Reason           : Sorting takes O(n log n). Traversing the array takes O(n).

Space Complexity : O(1)
Reason           : No extra data structure is used (ignoring the sorting algorithm's internal space).
*/

import java.util.*;

public class MajorityElementSorting {

    // Function to find the majority element using sorting
    public static int majorityElement(int[] nums) {

        int n = nums.length;

        // Sort the array
        Arrays.sort(nums);

        // Frequency of current element
        int freq = 1;

        // Current candidate
        int ans = nums[0];

        // Traverse the sorted array
        for (int i = 1; i < n; i++) {

            // Same element found
            if (nums[i] == nums[i - 1]) {
                freq++;
            }
            // New element found
            else {
                freq = 1;
                ans = nums[i];
            }

            // Majority element found
            if (freq > n / 2) {
                return ans;
            }
        }

        // Problem guarantees a majority element
        return ans;
    }

    public static void main(String[] args) {

        int[] nums = {2, 2, 1, 1, 1, 2, 2};

        int result = majorityElement(nums);

        System.out.println("Majority Element = " + result);
    }
}

/*
==========================================
Dry Run (Iteration)
==========================================

Input:
nums = [2, 2, 1, 1, 1, 2, 2]

After Sorting:
nums = [1, 1, 1, 2, 2, 2, 2]

n = 7
n/2 = 3

Initial:
freq = 1
ans = 1

Iteration 1 (i = 1)
nums[1] = 1
nums[0] = 1
Same element ✓
freq = 2

Iteration 2 (i = 2)
nums[2] = 1
nums[1] = 1
Same element ✓
freq = 3

Iteration 3 (i = 3)
nums[3] = 2
nums[2] = 1
Different element ✗
freq = 1
ans = 2

Iteration 4 (i = 4)
nums[4] = 2
nums[3] = 2
Same element ✓
freq = 2

Iteration 5 (i = 5)
nums[5] = 2
nums[4] = 2
Same element ✓
freq = 3

Iteration 6 (i = 6)
nums[6] = 2
nums[5] = 2
Same element ✓
freq = 4

Check:
4 > 3 ✓

return 2

Program Ends.

Summary:
1. Sort the array.
2. Count consecutive occurrences of each element.
3. Reset the count whenever a new element appears.
4. If count becomes greater than n/2, return that element.

Time Complexity  : O(n log n)
Space Complexity : O(1)
*/







/*
==========================================
Approach 3 : Optimal (Moore's Voting Algorithm)
==========================================

Idea:
- Maintain a candidate element and its frequency.
- If frequency becomes 0, choose the current element as the new candidate.
- If the current element is the candidate, increase the frequency.
- Otherwise, decrease the frequency.
- Since the problem guarantees a majority element exists, the final candidate is the answer.

Algorithm:
1. Initialize freq = 0 and ans = 0.
2. Traverse the array.
3. If freq becomes 0, select the current element as the new candidate.
4. If current element equals candidate, increment freq.
5. Otherwise, decrement freq.
6. After traversal, return the candidate.

Time Complexity  : O(n)
Reason           : The array is traversed only once.

Space Complexity : O(1)
Reason           : Only two extra variables are used.
*/

import java.util.*;

public class MajorityElementMooreVoting {

    // Function to find the majority element
    public static int majorityElement(int[] nums) {

        // Candidate element
        int ans = 0;

        // Frequency of candidate
        int freq = 0;

        // Traverse the array
        for (int i = 0; i < nums.length; i++) {

            // If frequency becomes 0,
            // choose a new candidate
            if (freq == 0) {
                ans = nums[i];
            }

            // Current element matches candidate
            if (nums[i] == ans) {
                freq++;
            }
            // Current element is different
            else {
                freq--;
            }
        }

        return ans;
    }

    public static void main(String[] args) {

        int[] nums = {2, 2, 1, 1, 1, 2, 2};

        int result = majorityElement(nums);

        System.out.println("Majority Element = " + result);
    }
}

/*
==========================================
Dry Run (Iteration)
==========================================

Input:
nums = [2, 2, 1, 1, 1, 2, 2]

Initial:
ans = 0
freq = 0

Iteration 1 (i = 0)
nums[0] = 2
freq == 0 ✓
ans = 2
2 == 2 ✓
freq = 1

Iteration 2 (i = 1)
nums[1] = 2
2 == 2 ✓
freq = 2

Iteration 3 (i = 2)
nums[2] = 1
1 != 2 ✗
freq = 1

Iteration 4 (i = 3)
nums[3] = 1
1 != 2 ✗
freq = 0

Iteration 5 (i = 4)
nums[4] = 1
freq == 0 ✓
ans = 1
1 == 1 ✓
freq = 1

Iteration 6 (i = 5)
nums[5] = 2
2 != 1 ✗
freq = 0

Iteration 7 (i = 6)
nums[6] = 2
freq == 0 ✓
ans = 2
2 == 2 ✓
freq = 1

Traversal Ends
return 2

Summary:
1. Keep a candidate and its frequency.
2. If frequency becomes 0, choose the current element as the new candidate.
3. Same element -> freq++.
4. Different element -> freq--.
5. After one traversal, the candidate is the majority element.

Time Complexity  : O(n)
Space Complexity : O(1)
*/ 
