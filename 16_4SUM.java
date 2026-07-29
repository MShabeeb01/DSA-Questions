import java.util.*;

class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            for (int j = i + 1; j < n; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                int p = j + 1;
                int q = n - 1;

                while (p < q) {
                    long sum = (long) nums[i] + nums[j] + nums[p] + nums[q];

                    if (sum < target) {
                        p++;
                    } else if (sum > target) {
                        q--;
                    } else {
                        ans.add(Arrays.asList(nums[i], nums[j], nums[p], nums[q]));

                        p++;
                        q--;

                        while (p < q && nums[p] == nums[p - 1]) {
                            p++;
                        }

                        while (p < q && nums[q] == nums[q + 1]) {
                            q--;
                        }
                    }
                }
            }
        }

        return ans;
    }
}

/*
-----------------------------------------
Code Summary
-----------------------------------------
1. Sort the array.
2. Fix the first element using index i.
3. Skip duplicate values of i.
4. Fix the second element using index j.
5. Skip duplicate values of j.
6. Initialize two pointers:
      - p = j + 1
      - q = n - 1
7. Calculate the sum of the four elements.
8. If sum < target, move p forward.
9. If sum > target, move q backward.
10. If sum == target:
      - Add the quadruplet.
      - Move both pointers.
      - Skip duplicate values.
11. Return all unique quadruplets.

-----------------------------------------
Iteration
-----------------------------------------

Sort Array
│
├── for(i = 0 → n-1)
│   │
│   ├── Skip duplicate i
│   │
│   ├── for(j = i+1 → n-1)
│   │   │
│   │   ├── Skip duplicate j
│   │   │
│   │   ├── p = j + 1
│   │   ├── q = n - 1
│   │   │
│   │   └── while(p < q)
│   │       │
│   │       ├── sum = nums[i] + nums[j] + nums[p] + nums[q]
│   │       │
│   │       ├── sum < target → p++
│   │       ├── sum > target → q--
│   │       └── sum == target
│   │           │
│   │           ├── Add quadruplet
│   │           ├── p++
│   │           ├── q--
│   │           ├── Skip duplicate p
│   │           └── Skip duplicate q
│   │
│   └── Continue
│
└── Return Answer

-----------------------------------------
Time Complexity
-----------------------------------------
Sorting               : O(n log n)
Outer Loop            : O(n)
Inner Loop            : O(n)
Two Pointer Traversal : O(n)

Overall               : O(n³)

-----------------------------------------
Space Complexity
-----------------------------------------
Auxiliary Space : O(1)
Output Space    : O(k)
*/
