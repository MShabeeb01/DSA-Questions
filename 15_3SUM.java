class Solution {
    public List<List<Integer>> threeSum(int[] nums) {

        int n = nums.length;

        List<List<Integer>> ans = new ArrayList<>();
        Set<List<Integer>> set = new HashSet<>();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {

                    if (nums[i] + nums[j] + nums[k] == 0) {

                        List<Integer> trip = Arrays.asList(nums[i], nums[j], nums[k]);
                        Collections.sort(trip);

                        if (!set.contains(trip)) {
                            set.add(trip);
                            ans.add(trip);
                        }
                    }
                }
            }
        }

        return ans;
    }
}

/*
==================================================
SUMMARY
==================================================
-> Checked every possible triplet using three nested loops.
-> If the sum of the triplet is 0, it is a valid answer.
-> Sorted each valid triplet to maintain a consistent order.
-> Used a HashSet to avoid storing duplicate triplets.
-> Stored only unique triplets in the final answer.

==================================================
ITERATION 1 : BRUTE FORCE
==================================================

Approach:
1. Traverse all possible triplets using three nested loops.
2. Check whether nums[i] + nums[j] + nums[k] == 0.
3. If yes:
   - Create a triplet.
   - Sort the triplet.
   - Check if it already exists in the HashSet.
   - If not present, add it to both the HashSet and answer list.
4. Return the answer.

Why sort the triplet?
- Different index combinations can generate the same values.
- Sorting ensures all identical triplets have the same order.

Example:
[-1, 0, 1]
[1, -1, 0]

Both become:

[-1, 0, 1]

Why HashSet?
- Prevents duplicate triplets from being added to the answer.

==================================================
FINAL ANSWER
==================================================
Return all unique triplets whose sum is equal to 0.

==================================================
TIME COMPLEXITY
==================================================
Three nested loops        : O(N³)
Sorting each triplet      : O(1)
HashSet lookup/insertion  : O(1) Average

Overall: O(N³)

==================================================
SPACE COMPLEXITY
==================================================
HashSet + Answer List : O(K)

K = Number of unique triplets.
*/


class Solution {
    public List<List<Integer>> threeSum(int[] nums) {

        int n = nums.length;

        List<List<Integer>> ans = new ArrayList<>();
        Set<List<Integer>> uniqueTriplets = new HashSet<>();

        for (int i = 0; i < n; i++) {

            int target = -nums[i];
            HashSet<Integer> set = new HashSet<>();

            for (int j = i + 1; j < n; j++) {

                int third = target - nums[j];

                if (set.contains(third)) {

                    List<Integer> trip = Arrays.asList(nums[i], nums[j], third);
                    Collections.sort(trip);

                    uniqueTriplets.add(trip);
                }

                set.add(nums[j]);
            }
        }

        ans.addAll(uniqueTriplets);
        return ans;
    }
}

/*
==================================================
SUMMARY
==================================================
-> Reduced one nested loop using Hashing.
-> Fixed one element and searched for the remaining two using a HashSet.
-> For every element, calculated the required third value.
-> If the third value was already present in the HashSet, a valid triplet was found.
-> Sorted each triplet and stored it in a HashSet to remove duplicates.

==================================================
ITERATION 2 : BETTER APPROACH (HASHING)
==================================================

Approach:
1. Fix one element nums[i].
2. Calculate the target = -nums[i].
3. Create an empty HashSet for the current iteration.
4. Traverse the remaining elements.
5. Compute:
      third = target - nums[j]
6. If third already exists in the HashSet:
      - Create the triplet.
      - Sort it.
      - Store it in the uniqueTriplets set.
7. Otherwise, insert nums[j] into the HashSet.
8. Repeat for every index i.
9. Convert the set into the answer list.

Why HashSet?
- Instead of checking every third element using another loop,
  HashSet allows searching in O(1) average time.

Why sort the triplet?
- The same triplet can be formed in different orders.
- Sorting ensures every duplicate has the same representation.

Example:
[-1, 0, 1]
[1, -1, 0]

Both become:

[-1, 0, 1]

Why another HashSet (uniqueTriplets)?
- Different iterations can still generate the same triplet.
- This HashSet ensures only unique triplets are stored.

==================================================
FINAL ANSWER
==================================================
Return all unique triplets whose sum is equal to 0.

==================================================
TIME COMPLEXITY
==================================================
Outer Loop            : O(N)
Inner Loop            : O(N)
HashSet Lookup        : O(1) Average
Sorting Triplet       : O(1)

Overall: O(N²)

==================================================
SPACE COMPLEXITY
==================================================
HashSet (search)      : O(N)
Unique Triplets Set   : O(K)
Answer List           : O(K)

Overall: O(N + K)

K = Number of unique triplets.
*/




class Solution {
    public List<List<Integer>> threeSum(int[] nums) {

        Arrays.sort(nums);

        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            // Skip duplicate first elements
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int j = i + 1;
            int k = n - 1;

            while (j < k) {

                int sum = nums[i] + nums[j] + nums[k];

                if (sum < 0) {
                    j++;
                }
                else if (sum > 0) {
                    k--;
                }
                else {

                    ans.add(Arrays.asList(nums[i], nums[j], nums[k]));

                    j++;
                    k--;

                    // Skip duplicate second elements
                    while (j < k && nums[j] == nums[j - 1]) {
                        j++;
                    }

                    // Skip duplicate third elements
                    while (j < k && nums[k] == nums[k + 1]) {
                        k--;
                    }
                }
            }
        }

        return ans;
    }
}

/*
==================================================
SUMMARY
==================================================
-> Sorted the array first.
-> Fixed one element and used the Two Pointer technique
   to find the remaining two elements.
-> Moved pointers based on the current sum.
-> Skipped duplicate elements to avoid repeated triplets.
-> No extra HashSet was required.

==================================================
ITERATION 3 : OPTIMIZED APPROACH (TWO POINTER)
==================================================

Approach:
1. Sort the array.
2. Fix one element nums[i].
3. Place two pointers:
      j = i + 1
      k = n - 1
4. Calculate:
      sum = nums[i] + nums[j] + nums[k]
5. If sum < 0:
      Move j forward.
6. If sum > 0:
      Move k backward.
7. If sum == 0:
      - Store the triplet.
      - Move both pointers.
      - Skip duplicate values of j and k.
8. Repeat for every i.

Why sort the array?
- Sorting enables the Two Pointer technique.
- Duplicate triplets can be skipped efficiently.

Why skip duplicates?
- Prevents storing the same triplet multiple times.
- Eliminates the need for an extra HashSet.

Example:
Input:
[-1, 0, 1, 2, -1, -4]

After Sorting:
[-4, -1, -1, 0, 1, 2]

Unique Triplets:
[-1, -1, 2]
[-1, 0, 1]

==================================================
FINAL ANSWER
==================================================
Return all unique triplets whose sum is equal to 0.

==================================================
TIME COMPLEXITY
==================================================
Sorting Array      : O(N log N)
Outer Loop         : O(N)
Two Pointer Search : O(N)

Overall: O(N²)

==================================================
SPACE COMPLEXITY
==================================================
Extra Space : O(1)
Answer List  : O(K)

Overall: O(1) Auxiliary Space

K = Number of unique triplets.
*/
