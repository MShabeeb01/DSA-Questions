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
    // Operation: Merge Two Sorted Lists (LeetCode 21 - Recursive Approach)
    public ListNode mergeTwoLists(ListNode head1, ListNode head2) {
        // Base Case: If either list runs out, return the non-empty list
        if (head1 == null || head2 == null) {
            return head1 == null ? head2 : head1;
        }

        // Case 1: head1 has smaller or equal value
        if (head1.val <= head2.val) {
            head1.next = mergeTwoLists(head1.next, head2);
            return head1;
        } 
        // Case 2: head2 has smaller value
        else {
            head2.next = mergeTwoLists(head1, head2.next);
            return head2;
        }
    }
}

/*
==================== SUMMARY ====================

Problem: LeetCode 21 - Merge Two Sorted Lists (Recursive)

Core Logic:
1. Base Case:
   - If `head1 == null`, append whatever remains of `head2`.
   - If `head2 == null`, append whatever remains of `head1`.
   - Elegantly written as: `return head1 == null ? head2 : head1;`

2. Recursive Selection:
   - Pick the smaller node between `head1` and `head2`.
   - Case 1 (`head1.val <= head2.val`):
     - `head1` comes first in order.
     - Connect `head1.next` to the result of merging the remaining nodes: `mergeTwoLists(head1.next, head2)`.
     - Return `head1`.
   - Case 2 (`head2.val < head1.val`):
     - `head2` comes first in order.
     - Connect `head2.next` to the result of merging: `mergeTwoLists(head1, head2.next)`.
     - Return `head2`.

-------------------------------------------------

Call Stack & Pointer Rewiring Visualization

List 1: [1 -> 3]
List 2: [2 -> 4]

--- Downward Call Phase (Pointers Picked) ---
mergeTwoLists(1, 2)  : 1 <= 2 -> picks Node(1), calls mergeTwoLists(3, 2)
  └── mergeTwoLists(3, 2)  : 3 > 2  -> picks Node(2), calls mergeTwoLists(3, 4)
        └── mergeTwoLists(3, 4)  : 3 <= 4 -> picks Node(3), calls mergeTwoLists(null, 4)
              └── mergeTwoLists(null, 4) : Base case hit -> returns Node(4)

--- Upward Backtracking Phase (Links Wired) ---
Level 3: Node(3).next = Node(4)       ===> returns [3 -> 4]
Level 2: Node(2).next = [3 -> 4]      ===> returns [2 -> 3 -> 4]
Level 1: Node(1).next = [2 -> 3 -> 4] ====> returns [1 -> 2 -> 3 -> 4]

Resulting Chain:
  [ 1 ] ---> [ 2 ] ---> [ 3 ] ---> [ 4 ] ---> null

-------------------------------------------------

Step-by-Step Trace Table

Input: head1 = [1, 3], head2 = [2, 4]

---------------------------------------------------------------------------------------------------------
Call Depth | Comparison         | Selected Head | Recursive Call Triggered           | Returned Node
---------------------------------------------------------------------------------------------------------
1          | 1 <= 2 (true)      | head1: Node(1)| mergeTwoLists(head1.next, head2)   | Returns Node(1)
2          | 3 <= 2 (false)     | head2: Node(2)| mergeTwoLists(head1, head2.next)   | Returns Node(2)
3          | 3 <= 4 (true)      | head1: Node(3)| mergeTwoLists(head1.next, head2)   | Returns Node(3)
4 (Base)   | head1 == null      | -             | Base hit -> returns head2 (Node 4) | Returns Node(4)
---------------------------------------------------------------------------------------------------------
Final Sorted Head: Node(1) -> 1 -> 2 -> 3 -> 4 -> null

Complexity Analysis:
- Time Complexity : O(n + m) — Where n and m are lengths of the lists; each recursive call processes one node.
- Space Complexity: O(n + m) — Call stack depth proportional to the total number of nodes merged.
=================================================
*/
