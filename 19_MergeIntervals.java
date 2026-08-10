import java.util.*;

class Solution {
    public int[][] merge(int[][] intervals) {

        // If there is only one interval,
        // no merging is required.
        if (intervals.length <= 1) {
            return intervals;
        }

        // Sort intervals based on starting point
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // Store the merged intervals
        List<int[]> result = new ArrayList<>();

        // Start with the first interval
        int[] newInterval = intervals[0];
        result.add(newInterval);

        // Check each interval
        for (int[] interval : intervals) {

            // Overlapping intervals
            if (interval[0] <= newInterval[1]) {

                // Update the end point
                newInterval[1] =
                    Math.max(newInterval[1], interval[1]);

            } else {

                // Non-overlapping interval
                // Add it as a new interval
                newInterval = interval;
                result.add(newInterval);
            }
        }

        // Convert List<int[]> into int[][]
        return result.toArray(new int[result.size()][]);
    }
}

/*

## Code Summary

1. Check if there is only one interval.
2. Sort all intervals based on their starting point.
3. Create a list to store the merged intervals.
4. Take the first interval as the current interval.
5. Compare each interval with the current interval.
6. If they overlap, merge them by updating the ending point.
7. If they do not overlap, add the new interval to the result.
8. Return the result as a 2D array.

---

## Iteration

Input

[[1,3],[8,10],[2,6],[15,18]]

## Step 1: Sort the intervals

Before sorting:

[1,3] [8,10] [2,6] [15,18]

After sorting by starting point:

[1,3] [2,6] [8,10] [15,18]

---

## Iteration 1

newInterval = [1,3]

result = [[1,3]]

Current interval:

[1,3]

---

## Iteration 2

Current interval:

[2,6]

Check:

interval[0] <= newInterval[1]

2 <= 3 → True

Intervals overlap.

Update the end:

max(3,6) = 6

newInterval becomes:

[1,6]

result:

[[1,6]]

---

## Iteration 3

Current interval:

[8,10]

Check:

8 <= 6 → False

Intervals do not overlap.

Add the new interval.

newInterval = [8,10]

result:

[[1,6],[8,10]]

---

## Iteration 4

Current interval:

[15,18]

Check:

15 <= 10 → False

Intervals do not overlap.

Add the new interval.

newInterval = [15,18]

result:

[[1,6],[8,10],[15,18]]

---

## Final Answer

[[1,6],[8,10],[15,18]]

---

## Time Complexity

Sorting             : O(n log n)

Traversing intervals : O(n)

Overall              : O(n log n)

---

## Space Complexity

Result List : O(n)

Auxiliary Space : O(n)

*/
