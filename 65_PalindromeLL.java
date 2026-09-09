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
    // Operation: Check Palindrome Linked List (LeetCode 234)
    public boolean isPalindrome(ListNode head) {
        // Base case: Empty list or single node is intrinsically a palindrome
        if (head == null || head.next == null) {
            return true;
        }

        // Step 1: Find the middle of the linked list using slow and fast pointers
        ListNode slow = head; // 1x speed
        ListNode fast = head; // 2x speed
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Step 2: Reverse the second half of the list
        ListNode prev = null;
        ListNode curr = slow; // Start reversal from mid-point
        while (curr != null) {
            ListNode nextTemp = curr.next; // Cache remaining sublist
            curr.next = prev;             // Invert forward link
            prev = curr;                  // Advance prev pointer
            curr = nextTemp;              // Advance curr pointer
        }

        // Step 3: Compare the first half with the reversed second half
        ListNode p1 = head; // Scanner for first half
        ListNode p2 = prev; // Head of the newly reversed second half
        while (p2 != null) {
            if (p1.val != p2.val) {
                return false; // Mismatched value encountered
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        return true; // Symmetric equality verified across all nodes
    }
}

/*
==================== SUMMARY ====================

Problem: LeetCode 234 - Palindrome Linked List

Core Architecture: In-Place 3-Phase Pointer Transformation

Algorithmic Phases:
1. Midpoint Discovery (Floyd's Tortoise & Hare):
   - Fast advances at double speed ($+2$ nodes) while slow advances at single speed ($+1$ node).
   - Once fast exhausts list boundary, slow lands on:
     - Odd length ($2k + 1$): Exact center node $(k + 1)$.
     - Even length ($2k$): Second-half start node $(k + 1)$.

2. In-Place Second-Half Inversion:
   - Beginning at `curr = slow`, iteratively detach and redirect pointer links backwards.
   - Preserves remaining segments using temporary reference `nextTemp`.
   - Result: `prev` becomes the root reference to the inverted right-side chain.

3. Bi-Directional Symmetry Validation:
   - Synchronously advance `p1` (from head) and `p2` (from `prev`).
   - Terminate loop when `p2 == null` (handles odd-length center-node overlap naturally).

-------------------------------------------------

Structural Pointer Lifecycle

Input: [1] -> [2] -> [2] -> [1] -> null

Phase 1 (Finding Midpoint):
   [1] ---------> [2] ---------> [2] ---------> [1] ---> null
  head           slow           fast
                                  |
                              (moves out)
                 slow lands on 2nd [2]

Phase 2 (Reversing from Midpoint):
  Original First Half: [1] -> [2]
  Inverted Second Half: null <- [2] <- [1]
                                        ^
                                   prev (p2)

Phase 3 (Two-Pointer Comparison):
     p1
      |
      v
    [ 1 ] ---> [ 2 ]
    [ 1 ] ---> [ 2 ] ---> null
      ^
      |
     p2

  - Iteration 1: p1.val (1) == p2.val (1) -> Advance both
  - Iteration 2: p1.val (2) == p2.val (2) -> Advance both
  - p2 reaches null -> Symmetrical match confirmed -> returns true

-------------------------------------------------

Step-by-Step Trace Table

Test Instance: head = [1, 2, 2, 1]

-----------------------------------------------------------------------------------------------------------------
Phase      | Step / Action      | Pointer State                               | Evaluation Check
-----------------------------------------------------------------------------------------------------------------
Phase 1    | Find Mid           | fast = null, slow = Node(2) [index 2]       | Loop breaks (fast == null)
Phase 2    | Invert Second Half | prev = Node(1) [index 3], curr = null       | Splicing complete
Phase 3    | Iteration 1        | p1 = Node(1) [idx 0], p2 = Node(1) [idx 3]  | 1 == 1 (Valid, advance)
           | Iteration 2        | p1 = Node(2) [idx 1], p2 = Node(2) [idx 2]  | 2 == 2 (Valid, advance)
           | Termination        | p2 = null                                   | Comparison loop exits -> true
-----------------------------------------------------------------------------------------------------------------

Complexity Analysis:
- Time Complexity : O(n) — Midpoint search takes n/2, inversion takes n/2, comparison takes n/2 -> Total O(n).
- Space Complexity: O(1) — Direct pointer manipulation without auxiliary heap or recursive stack allocation.
=================================================
*/
