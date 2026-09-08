/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode middleNode(ListNode head) {
        ListNode slow = head; // Moves 1 step at a time (+1)
        ListNode fast = head; // Moves 2 steps at a time (+2)

        // Traverse until fast hits the end (odd) or passes it (even)
        while (fast != null && fast.next != null) {
            slow = slow.next;        // +1
            fast = fast.next.next;   // +2
        }

        return slow; // Points to the middle node (second middle if even)
    }
}

/*
==================== SUMMARY ====================

Problem: LeetCode 876 - Middle of the Linked List

Core Approach:
- Fast & Slow Pointer (Tortoise and Hare Technique)
- `slow` moves at $1\times$ speed (`slow = slow.next`)
- `fast` moves at $2\times$ speed (`fast = fast.next.next`)
- By the time `fast` covers the full list, `slow` is exactly halfway through.

Loop Termination Conditions:
- `fast != null`      : Guards against stepping past the tail in even-length lists.
- `fast.next != null` : Guards against stepping beyond null in odd-length lists.

-------------------------------------------------

Pointer Walkthrough Visualizations

Example 1: Odd Length [1 -> 2 -> 3 -> 4 -> 5 -> null]

Initial:
  [1] -> [2] -> [3] -> [4] -> [5] -> null
  s, f

Step 1:
  [1] -> [2] -> [3] -> [4] -> [5] -> null
          s             f

Step 2:
  [1] -> [2] -> [3] -> [4] -> [5] -> null
                 s                   f
  (fast.next == null -> loop ends -> returns slow: Node 3)


Example 2: Even Length [1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null]

Step 2:
  [1] -> [2] -> [3] -> [4] -> [5] -> [6] -> null
                 s                   f

Step 3:
  [1] -> [2] -> [3] -> [4] -> [5] -> [6] -> null
                        s                     f (null)
  (fast == null -> loop ends -> returns slow: Node 4 [second middle])

-------------------------------------------------

Trace Table

-------------------------------------------------------------------------------------------------
Iteration | Current `slow` | Current `fast` | Condition (`fast != null && fast.next != null`)
-------------------------------------------------------------------------------------------------
Start     | Node(1)        | Node(1)        | true
1         | Node(2)        | Node(3)        | true
2         | Node(3)        | Node(5)        | false (fast.next is null) -> Terminate
-------------------------------------------------------------------------------------------------
Final Output: Node with val = 3

Complexity Analysis:
- Time Complexity : O(n) — Traverses the list in a single pass of n/2 steps.
- Space Complexity: O(1) — Uses only two pointer references (`slow` and `fast`).
=================================================
*/
