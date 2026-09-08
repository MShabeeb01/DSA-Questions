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
    public ListNode reverseList(ListNode head) {
        // Step 1: Initialize 3 pointers
        ListNode prev = null;
        ListNode curr = head;
        ListNode next = null;

        // Step 2: Iterate and reverse links
        while (curr != null) {
            next = curr.next;     // 1. Store next node
            curr.next = prev;     // 2. Reverse pointer link backwards
            prev = curr;          // 3. Move prev forward
            curr = next;          // 4. Move curr forward
        }

        // Step 3: prev is now pointing to the new head
        return prev;
    }
}

/*
==================== SUMMARY ====================

Problem: LeetCode 206 - Reverse Linked List (Iterative Approach)

Core Logic (3 Pointers, 4 Steps):
- Pointers:
  1. `prev`: Anchors the reversed chain (starts at `null`).
  2. `curr`: Active node having its link flipped (starts at `head`).
  3. `next`: Preserves reference to the remaining chain before breaking `curr.next`.

- The 4-Step Iteration Loop (`while (curr != null)`):
  1. `next = curr.next;`  // Save remaining chain
  2. `curr.next = prev;`   // Reverse link backward
  3. `prev = curr;`        // Shift prev forward to current node
  4. `curr = next;`        // Shift curr forward to next node

- Final Return:
  - When `curr == null`, `prev` points to the final valid node (new head of reversed list).
  - Return `prev`.

-------------------------------------------------

Pointer Reversal Visual

Initial State:
  prev = null
  curr = [1] ---> [2] ---> [3] ---> null

Step 1 & 2 (Save next & Reverse link):
  null <--- [1]           [2] ---> [3] ---> null
   ^         ^             ^
  prev      curr          next

Step 3 & 4 (Shift pointers forward):
  null <--- [1]           [2] ---> [3] ---> null
             ^             ^
            prev          curr

Final State:
  null <--- [1] <--- [2] <--- [3]         curr = null
                               ^
                              prev (Return prev as new head)

-------------------------------------------------

Step-by-Step Trace Table

Input: head = [1, 2, 3]

---------------------------------------------------------------------------------------------------------
Iteration | curr Node | next = curr.next | curr.next = prev | prev = curr | curr = next | Chain State
---------------------------------------------------------------------------------------------------------
Start     | Node(1)   | null             | -                | null        | Node(1)     | 1 -> 2 -> 3 -> null
1         | Node(1)   | Node(2)          | null             | Node(1)     | Node(2)     | null <- 1  2 -> 3
2         | Node(2)   | Node(3)          | Node(1)          | Node(2)     | Node(3)     | null <- 1 <- 2  3
3         | Node(3)   | null             | Node(2)          | Node(3)     | null        | null <- 1 <- 2 <- 3
End       | null      | -                | Loop breaks      | Node(3)     | null        | Return Node(3)
---------------------------------------------------------------------------------------------------------

Complexity Analysis:
- Time Complexity : O(n) — Single pass traversal through all n nodes.
- Space Complexity: O(1) — In-place pointer updates with no extra space allocated.
=================================================
*/
