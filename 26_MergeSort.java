import java.util.*;

class Solution {

    // Stores the value and its original index.
    // originalIdx is needed because Merge Sort changes
    // the order of the elements.
    private class ArrayValWithOrigIdx {
        int val;
        int originalIdx;

        public ArrayValWithOrigIdx(int val, int originalIdx) {
            this.val = val;
            this.originalIdx = originalIdx;
        }
    }

    public List<Integer> countSmaller(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new LinkedList<>();
        }

        int n = nums.length;
        int[] result = new int[n];

        // Store every value with its original index.
        ArrayValWithOrigIdx[] newNums =
                new ArrayValWithOrigIdx[n];

        for (int i = 0; i < n; i++) {
            newNums[i] =
                    new ArrayValWithOrigIdx(nums[i], i);
        }

        // Merge Sort + Counting
        mergeSortAndCount(newNums, 0, n - 1, result);

        List<Integer> resultList = new LinkedList<>();
        for (int value : result) {
            resultList.add(value);
        }

        return resultList;
    }

    private void mergeSortAndCount(
            ArrayValWithOrigIdx[] nums,
            int start,
            int end,
            int[] result) {

        // One element is already sorted.
        if (start >= end) {
            return;
        }

        int mid = start + (end - start) / 2;

        // Sort left half.
        mergeSortAndCount(nums, start, mid, result);

        // Sort right half.
        mergeSortAndCount(nums, mid + 1, end, result);

        int leftPos = start;
        int rightPos = mid + 1;

        ArrayList<ArrayValWithOrigIdx> merged =
                new ArrayList<>();

        // Number of elements from the right half
        // that are smaller than the current left element.
        int rightSmallerCount = 0;

        while (leftPos <= mid && rightPos <= end) {

            if (nums[rightPos].val < nums[leftPos].val) {

                // Right element is smaller, so count it.
                rightSmallerCount++;

                merged.add(nums[rightPos]);
                rightPos++;

            } else {

                // All previously counted right elements
                // are smaller than this left element.
                result[nums[leftPos].originalIdx]
                        += rightSmallerCount;

                merged.add(nums[leftPos]);
                leftPos++;
            }
        }

        // Add remaining left elements.
        while (leftPos <= mid) {
            result[nums[leftPos].originalIdx]
                    += rightSmallerCount;

            merged.add(nums[leftPos]);
            leftPos++;
        }

        // Add remaining right elements.
        while (rightPos <= end) {
            merged.add(nums[rightPos]);
            rightPos++;
        }

        // Copy merged elements back.
        for (int i = 0; i < merged.size(); i++) {
            nums[start + i] = merged.get(i);
        }
    }
}

/*
==================================================
## Code Summary
==================================================

Problem:
For every nums[i], count how many elements
smaller than nums[i] are present on its right.

Example:
nums = [5, 2, 6, 1]

Answer:
[2, 1, 1, 0]

Brute Force:
Check every element against all elements
to its right.

Time = O(N²)

Better Approach:
Use Merge Sort.

During merging, both halves are sorted.
Whenever a RIGHT element is smaller than
a LEFT element, we increase:

rightSmallerCount

When a LEFT element is selected, all the
previously counted RIGHT elements are smaller
than it.

So:

result[originalIdx] += rightSmallerCount


==================================================
## Why originalIdx?
==================================================

Merge Sort changes the order.

Example:

Original:
[5, 2, 6, 1]

Objects:
(5,0), (2,1), (6,2), (1,3)

The second value is the original index.

After sorting, 5 may move to another position,
but we still know:

5 originally came from index 0.

Therefore its answer goes into:

result[0]


==================================================
## Iteration
==================================================

Input:
[5, 2, 6, 1]

Initial:
result = [0, 0, 0, 0]

### Step 1: Merge [5] and [2]

Compare:

5 > 2

So 2 is smaller than 5.

rightSmallerCount = 1

Move 2.

Now 5 remains.

5 originally had index 0.

Therefore:

result[0] += 1

result = [1, 0, 0, 0]

Merged:

[2, 5]


### Step 2: Merge [6] and [1]

Compare:

6 > 1

So 1 is smaller than 6.

rightSmallerCount = 1

6 originally had index 2.

Therefore:

result[2] += 1

result = [1, 0, 1, 0]

Merged:

[1, 6]


### Step 3: Final Merge

Left:
[2, 5]

Right:
[1, 6]

Compare 2 and 1:

1 < 2

So:

rightSmallerCount = 1

Move 1.

Now compare 2 and 6:

2 < 6

Move 2.

Since:

rightSmallerCount = 1

one right element is smaller than 2.

2 originally had index 1.

Therefore:

result[1] += 1

result = [1, 1, 1, 0]


Now compare 5 and 6:

5 < 6

Move 5.

The counter is still 1.

That means 1 is also smaller than 5.

5 originally had index 0.

Therefore:

result[0] += 1

result = [2, 1, 1, 0]


6 remains.

Final:

[2, 1, 1, 0]


==================================================
## Why Does rightSmallerCount Work?
==================================================

Suppose:

LEFT = [2, 5, 7]
RIGHT = [1, 6]

If:

1 < 2

then because the LEFT side is sorted:

1 < 2
1 < 5
1 < 7

So the same RIGHT element can be counted
for all remaining LEFT elements.

This is the main trick that makes the
solution O(N log N).


==================================================
## Important Condition
==================================================

We use:

nums[rightPos].val < nums[leftPos].val

NOT:

<=

Because we only want elements that are
STRICTLY smaller.

Equal values do not count.


==================================================
## Final Answer
==================================================

Input:
[5, 2, 6, 1]

Output:
[2, 1, 1, 0]

5 -> smaller on right: 2, 1
2 -> smaller on right: 1
6 -> smaller on right: 1
1 -> smaller on right: none


==================================================
## Time Complexity
==================================================

Merge Sort:

O(N log N)

Every merge processes each element once.

Total:

O(N log N)


==================================================
## Space Complexity
==================================================

result[] = O(N)
newNums[] = O(N)
merged = O(N)

Total:

O(N)


==================================================
## Easy Way to Remember
==================================================

Normal Merge Sort:

DIVIDE → SORT → MERGE

This problem:

DIVIDE → SORT → MERGE + COUNT

During merge:

RIGHT < LEFT
      ↓
counter++

LEFT is selected
      ↓
result[originalIdx] += counter

==================================================
*/
