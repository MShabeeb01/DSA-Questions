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
    // Operation: Add Two Numbers Represented as Reversed Linked Lists (LeetCode 2)
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0); // Anchor for result list
        ListNode curr = dummyHead;
        int carry = 0;

        // Loop as long as there is an unprocessed node or a remaining carry
        while (l1 != null || l2 != null || carry != 0) {
            int val1 = (l1 != null) ? l1.val : 0;
            int val2 = (l2 != null) ? l2.val : 0;

            // Calculate sum and new carry
            int sum = val1 + val2 + carry;
            carry = sum / 10;

            // Attach new digit node
            curr.next = new ListNode(sum % 10);
            curr = curr.next;

            // Advance pointers if available
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        return dummyHead.next; // Head of resultant linked list
    }
}

/*
==================== SUMMARY ====================

Problem: LeetCode 2 - Add Two Numbers

Objective:
Add two non-empty linked lists representing two non-negative integers. 
Digits are stored in reverse order (head = least significant digit).
Return the sum as a reversed linked list.

Core Logic (Elementary Addition with Carry):
1. Dummy Head Sentinel:
   - Uses `dummyHead = new ListNode(0)` to build the resulting list cleanly without edge-case branching.
2. Unified While Condition (`l1 != null || l2 != null || carry != 0`):
   - Keeps running even if one list is longer than the other.
   - Catches any final leftover carry (e.g., $99 + 1 = 100$, where an extra node '1' must be spawned).
3. Arithmetic Step:
   - `sum = val1 + val2 + carry`
   - New digit to append: `sum % 10`
   - Carry forward to next position: `sum / 10`

-------------------------------------------------

Addition Walkthrough Visual

Input:
  l1: [2] -> [4] -> [3]    (Represents 342)
  l2: [5] -> [6] -> [4]    (Represents 465)

Column-wise Addition:
   Column 1: 2 + 5 + carry(0) = 7   -> digit: 7, carry: 0
   Column 2: 4 + 6 + carry(0) = 10  -> digit: 0, carry: 1
   Column 3: 3 + 4 + carry(1) = 8   -> digit: 8, carry: 0

Result Chain:
  [dummy] -> [ 7 ] -> [ 0 ] -> [ 8 ] -> null
              ^
        (dummyHead.next)

-------------------------------------------------

Step-by-Step Trace Table

Input: l1 = [2, 4, 3], l2 = [5, 6, 4]

---------------------------------------------------------------------------------------------------------
Iteration | l1 Node | l2 Node | In-Carry | sum (v1+v2+carry) | New Node (sum % 10) | Out-Carry | Result List
---------------------------------------------------------------------------------------------------------
Start     | -       | -       | 0        | -                 | dummy(0)            | 0         | [0]
1         | Node(2) | Node(5) | 0        | 2 + 5 + 0 = 7     | Node(7)             | 0         | 0 -> 7
2         | Node(4) | Node(6) | 0        | 4 + 6 + 0 = 10    | Node(0)             | 1         | 0 -> 7 -> 0
3         | Node(3) | Node(4) | 1        | 3 + 4 + 1 = 8     | Node(8)             | 0         | 0 -> 7 -> 0 -> 8
End       | null    | null    | 0        | Loop breaks       | -                   | -         | Return 7 -> 0 -> 8
---------------------------------------------------------------------------------------------------------

Complexity Analysis:
- Time Complexity : O(max(m, n)) — Single pass where m and n are the lengths of l1 and l2.
- Space Complexity: O(max(m, n)) — Generates a new linked list containing at most max(m, n) + 1 nodes.
=================================================
*/
