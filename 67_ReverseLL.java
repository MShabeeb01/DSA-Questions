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
    // Operation: Reverse Sublist between 'left' and 'right' (LeetCode 92)
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // Base case: No reversal needed if range is a single node or head is null
        if (head == null || left == right) {
            return head;
        }

        // Step 1: Create a dummy node to handle edge case where left == 1
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // Step 2: Reach node right before 'left' position (leftPre)
        ListNode leftPre = dummy;
        ListNode curr = head;
        for (int i = 0; i < left - 1; i++) {
            leftPre = leftPre.next;
            curr = curr.next;
        }

        // Step 3: Reverse the sublist from 'left' to 'right'
        ListNode subListHead = curr; // Will become the tail of the reversed sublist
        ListNode prev = null;
        for (int i = 0; i <= right - left; i++) {
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }

        // Step 4: Reconnect reversed portion back to the main list
        leftPre.next = prev;         // Connect prefix to new sublist head
        subListHead.next = curr;     // Connect new sublist tail to remainder suffix

        return dummy.next;
    }
}

/*
==================== SUMMARY ====================

Problem: LeetCode 92 - Reverse Linked List II

Objective:
Reverse the nodes of the singly-linked list from 1-indexed position `left` to `right` in-place.

Core Architecture (In-Place Segment Reversal):
1. Dummy Node Sentinel:
   - Prepends `dummy` with `dummy.next = head`.
   - Handles edge cases seamlessly where `left = 1` (reversal begins at the original head).
2. Locate Predecessor (`leftPre`):
   - Traverse `left - 1` steps to locate the node immediately preceding position `left`.
3. Standard 3-Pointer Reversal of Segment:
   - From `i = 0` to `right - left` (inclusive):
     - Reverse pointers of the target segment.
     - `prev` stops at the new head of the reversed sublist (originally node at index `right`).
     - `curr` stops at the head of the unreversed right suffix (originally node at index `right + 1`).
4. Reconnection:
   - `leftPre.next = prev` links the left unreversed part to the new sublist head.
   - `subListHead.next = curr` links the tail of the reversed sublist to the remaining right suffix.

-------------------------------------------------

Pointer Reconnection Visual

Input: [1 -> 2 -> 3 -> 4 -> 5], left = 2, right = 4

Initial state after locating leftPre:
  [dummy] -> [ 1 ] -> [ 2 ] -> [ 3 ] -> [ 4 ] -> [ 5 ] -> null
              ^        ^
           leftPre    curr (subListHead)

During Reversal (nodes 2, 3, 4 reversed):
  null <- [ 2 ] <- [ 3 ] <- [ 4 ]        [ 5 ] -> null
           ^                 ^            ^
       subListHead          prev         curr

Reconnection:
  1. leftPre.next = prev     (Node 1 connects to Node 4)
  2. subListHead.next = curr (Node 2 connects to Node 5)

Final List:
  [dummy] -> [ 1 ] --------> [ 4 ] -> [ 3 ] -> [ 2 ] --------> [ 5 ] -> null
  Result: [ 1 -> 4 -> 3 -> 2 -> 5 ]

-------------------------------------------------

Step-by-Step Trace Table

Input: head = [1, 2, 3, 4, 5], left = 2, right = 4

---------------------------------------------------------------------------------------------------------
Step / Loop Index | curr Node | prev Node | Action Taken                          | Sublist State
---------------------------------------------------------------------------------------------------------
Setup             | Node(2)   | null      | leftPre = Node(1), subListHead = Node(2)| -
i = 0             | Node(2)   | null      | 2.next = null, prev = 2, curr = 3     | null <- 2
i = 1             | Node(3)   | Node(2)   | 3.next = 2, prev = 3, curr = 4        | null <- 2 <- 3
i = 2             | Node(4)   | Node(3)   | 4.next = 3, prev = 4, curr = 5        | null <- 2 <- 3 <- 4
Loop Termination  | Node(5)   | Node(4)   | Reversal done (curr = 5, prev = 4)   | Sublist: 4 -> 3 -> 2
Reconnection      | -         | -         | leftPre.next=4; subListHead.next=5    | 1 -> 4 -> 3 -> 2 -> 5
---------------------------------------------------------------------------------------------------------
Result: Return dummy.next -> [1, 4, 3, 2, 5]

Complexity Analysis:
- Time Complexity : O(n) — One single pass (iterates at most right steps, which is <= n).
- Space Complexity: O(1) — Operates strictly in-place with auxiliary reference pointers.
=================================================
*/
