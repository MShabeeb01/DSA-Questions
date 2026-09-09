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
    // Operation: Reverse Nodes in k-Group (LeetCode 25 - Hard)
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k <= 1) {
            return head;
        }

        // Sentinel dummy node to handle head-switch edge cases seamlessly
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode groupPrev = dummy;

        while (true) {
            // Step 1: Check if there are at least k nodes left to reverse
            ListNode kthNode = getKthNode(groupPrev, k);
            if (kthNode == null) {
                break; // Fewer than k nodes remain; leave them as-is
            }

            ListNode groupNext = kthNode.next; // Node following the k-group
            ListNode curr = groupPrev.next;    // First node of the current k-group
            ListNode prev = groupNext;         // Reversal target: links tail directly to groupNext

            // Step 2: Reverse exactly k nodes
            while (curr != groupNext) {
                ListNode temp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = temp;
            }

            // Step 3: Reconnect preceding group to new group head & advance groupPrev
            ListNode temp = groupPrev.next;    // Original start of group (now the tail)
            groupPrev.next = kthNode;          // Link preceding tail to new group head
            groupPrev = temp;                  // Move groupPrev to current group's tail
        }

        return dummy.next;
    }

    // Helper: Find the k-th node from a starting reference pointer
    private ListNode getKthNode(ListNode start, int k) {
        while (start != null && k > 0) {
            start = start.next;
            k--;
        }
        return start;
    }
}

/*
==================== SUMMARY ====================

Problem: LeetCode 25 - Reverse Nodes in k-Group

Objective:
Reverse the nodes of a linked list `k` at a time. If the number of nodes at the end 
is not a multiple of `k`, leave the remaining nodes untouched in their original order.

Core Algorithmic Blueprint:
1. Dummy Sentinel Anchor:
   - Sets `dummy.next = head` so that reversing the first segment updates `dummy.next` automatically.
2. Lookahead Verification (`getKthNode`):
   - Probes forward by `k` steps.
   - If `kthNode == null`, remaining nodes are fewer than `k`; terminate the loop.
3. In-Place Segment Reversal:
   - Target reversal segment: `groupPrev.next` through `kthNode`.
   - Initialize `prev = groupNext` (connecting the first reversed node straight to the next section).
   - Invert all `k` links using standard pointer rotation.
4. Segment Reconnection:
   - `groupPrev.next = kthNode` connects the prior segment's tail to the new reversed head.
   - Advance `groupPrev` to the tail of the current reversed group (`temp`).

-------------------------------------------------

Pointer Flow & Reconnection Visual

Input: [1 -> 2 -> 3 -> 4 -> 5], k = 2

Initial state:
  [dummy] -> [ 1 ] -> [ 2 ] -> [ 3 ] -> [ 4 ] -> [ 5 ] -> null
     ^                   ^        ^
  groupPrev           kthNode  groupNext
  (curr = 1)

Step 1: Invert Group 1 ([1, 2]):
  - `curr` traverses and points backward toward `groupNext` (Node 3):
    [ 2 ] -> [ 1 ] -> [ 3 ] ...
  - Connect `groupPrev.next = kthNode` (dummy points to 2):
    [dummy] -> [ 2 ] -> [ 1 ] -> [ 3 ] -> [ 4 ] -> [ 5 ]
  - Update `groupPrev` to Node(1).

Step 2: Process Group 2 ([3, 4]):
  - `groupPrev` = Node(1), `kthNode` = Node(4), `groupNext` = Node(5)
  - Invert nodes 3 and 4:
    [dummy] -> [ 2 ] -> [ 1 ] -> [ 4 ] -> [ 3 ] -> [ 5 ] -> null
  - Update `groupPrev` to Node(3).

Step 3: Check Remaining Group ([5]):
  - `getKthNode(groupPrev, 2)` runs out of nodes and returns `null`.
  - Loop breaks; Node(5) remains untouched.

Result: [2 -> 1 -> 4 -> 3 -> 5]

-------------------------------------------------

Trace Table

Input: [1, 2, 3, 4, 5], k = 2

----------------------------------------------------------------------------------------------------------------------
Iteration | groupPrev Node | kthNode   | groupNext | Nodes Reversed | Reconnected Sublist
----------------------------------------------------------------------------------------------------------------------
Setup     | dummy(0)       | -         | -         | -              | 0 -> 1 -> 2 -> 3 -> 4 -> 5
Iter 1    | dummy(0)       | Node(2)   | Node(3)   | Node(1), (2)   | dummy -> 2 -> 1 -> 3 -> 4 -> 5
Iter 2    | Node(1)        | Node(4)   | Node(5)   | Node(3), (4)   | dummy -> 2 -> 1 -> 4 -> 3 -> 5
Iter 3    | Node(3)        | null (<k) | -         | None           | Loop exits (remaining left-out nodes intact)
----------------------------------------------------------------------------------------------------------------------
Result: Return dummy.next -> [2, 1, 4, 3, 5]

Complexity Analysis:
- Time Complexity : O(n) — Each node is visited twice (once by `getKthNode` and once during reversal).
- Space Complexity: O(1) — Strictly constant space; completely iterative in-place pointer manipulation.
=================================================
*/
