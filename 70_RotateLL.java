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
    // Operation: Rotate List to the Right by k Places (LeetCode 61)
    public ListNode rotateRight(ListNode head, int k) {
        // Base case: 0 nodes, 1 node, or no rotation requested
        if (head == null || head.next == null || k == 0) {
            return head;
        }

        // Step 1: Compute length of the linked list and locate the tail
        ListNode tail = head;
        int length = 1;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }

        // Step 2: Handle cases where k >= length
        k = k % length;
        if (k == 0) {
            return head; // Rotation brings it back to identical configuration
        }

        // Step 3: Connect tail to head to form a circular ring
        tail.next = head;

        // Step 4: Find new tail at (length - k) steps from head
        int stepsToNewTail = length - k;
        ListNode newTail = head;
        for (int i = 1; i < stepsToNewTail; i++) {
            newTail = newTail.next;
        }

        // Step 5: Break the circle and establish new head
        ListNode newHead = newTail.next;
        newTail.next = null;

        return newHead;
    }
}

/*
==================== SUMMARY ====================

Problem: LeetCode 61 - Rotate List

Objective:
Given the head of a linked list, rotate the list to the right by `k` places.

Core Algorithmic Technique (Circular Loop & Break):
1. Determine List Length ($n$):
   - Traverse the list to find the total count of nodes and the current `tail`.
2. Normalize $k$:
   - If $k \ge n$, rotating $n$ times returns the original list.
   - Effective rotations needed: $k = k \pmod n$. If $k == 0$, return `head` directly.
3. Form Circular Linked List:
   - Wire `tail.next = head`. The list is now a closed cycle.
4. Locate the Split Point:
   - When rotated right by $k$, the last $k$ nodes become the new front.
   - The new tail will be located at position $(n - k)$ (1-indexed from head).
   - Walk $(n - k - 1)$ steps from `head` to land on `newTail`.
5. Sever the Loop:
   - `newHead = newTail.next`
   - `newTail.next = null` (breaks the circular chain)

-------------------------------------------------

Pointer Flow & Reconnection Diagram

Input: [1 -> 2 -> 3 -> 4 -> 5], k = 2
Length n = 5, k % 5 = 2.
Steps to new tail: 5 - 2 = 3 (Node 3 is the new tail).

Step 1 & 3: Find length & make circular
         +---------------------------------------+
         v                                       |
       [ 1 ] ---> [ 2 ] ---> [ 3 ] ---> [ 4 ] ---> [ 5 ]
       head                 newTail               tail

Step 4 & 5: Break ring between Node 3 and Node 4
  - newHead = newTail.next -> Node 4
  - newTail.next = null

Result:
  [ 4 ] ---> [ 5 ] ---> [ 1 ] ---> [ 2 ] ---> [ 3 ] ---> null
    ^
  newHead

-------------------------------------------------

Step-by-Step Trace Table

Input: head = [1, 2, 3, 4, 5], k = 2

---------------------------------------------------------------------------------------------------------
Variable / Action    | Value / Node Pointer   | Evaluation / Details
---------------------------------------------------------------------------------------------------------
Initial Traversal    | tail = Node(5)         | length = 5
Normalize k          | k = 2 % 5 = 2          | k != 0, rotation needed
Make Circular        | tail.next = head       | Node(5).next = Node(1)
Find newTail steps   | length - k = 5 - 2 = 3 | Need 3rd node from head
Locate newTail       | newTail = Node(3)      | Traversed 2 steps from head (1 -> 2 -> 3)
Identify newHead     | newHead = Node(4)      | newTail.next
Break Link           | newTail.next = null    | Node(3).next = null
---------------------------------------------------------------------------------------------------------
Output: Return newHead -> [4, 5, 1, 2, 3]

Complexity Analysis:
- Time Complexity : O(n) — Traverses once to calculate length and find tail ($n$), then walks at most $n - k$ steps to find `newTail`. Total steps $< 2n \implies O(n)$.
- Space Complexity: O(1) — In-place pointer updates with no heap allocations or extra memory overhead.
=================================================
*/
