/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    // Operation: Detect Cycle II - Find Starting Node of Loop (LeetCode 142)
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head; // 1x speed (+1)
        ListNode fast = head; // 2x speed (+2)
        boolean isCycle = false;

        // Step 1: Detect whether a cycle exists using Floyd's algorithm
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                isCycle = true; // Collision detected
                break;
            }
        }

        // If fast reached null, the list is linear
        if (!isCycle) {
            return null;
        }

        // Step 2: Locate the cycle entry node
        slow = head; // Reset slow to head
        while (slow != fast) {
            slow = slow.next; // +1
            fast = fast.next; // +1 (moves from collision point)
        }

        return slow; // Points to the exact start node of the cycle
    }
}

/*
==================== SUMMARY ====================

Problem: LeetCode 142 - Linked List Cycle II

Core Objective:
Return the exact node where the cycle begins. If no cycle exists, return `null`.

Mathematical Proof of Floyd's Cycle Algorithm:
- Let $L$ = Distance from `head` to the start of the cycle.
- Let $d$ = Distance from the start of the cycle to the collision point.
- Let $C$ = Total perimeter/length of the cycle.

1. At collision point:
   - Distance traversed by slow: $D_{slow} = L + d$
   - Distance traversed by fast: $D_{fast} = L + n \cdot C + d$ (where $n \ge 1$ laps)

2. Since fast moves at twice the speed of slow:
   $$D_{fast} = 2 \cdot D_{slow}$$
   $$L + n \cdot C + d = 2(L + d)$$
   $$L + n \cdot C + d = 2L + 2d$$
   $$L = n \cdot C - d$$
   $$L = (n - 1) \cdot C + (C - d)$$

Conclusion:
The distance from `head` to the cycle start ($L$) is mathematically identical to 
the distance from the collision point to the cycle start along the loop ($C - d$).
Therefore, resetting `slow = head` and advancing both `slow` and `fast` by +1 step 
guarantees they collide directly at the cycle's entrance.

-------------------------------------------------

Pointer Walkthrough Diagram

Example: [3 -> 2 -> 0 -> -4 -> (loops back to 2)]

1. Detection Phase:
   L = 1 (Node 3 -> Node 2)
   C = 3 (Nodes 2, 0, -4)
   
   Initial: slow = [3], fast = [3]
   Step 1 : slow = [2], fast = [0]
   Step 2 : slow = [0], fast = [2]
   Step 3 : slow = [-4], fast = [-4]  <-- Collision Point (slow == fast)

2. Finding Cycle Entry Phase:
   - Reset `slow = head` (Node 3)
   - Keep `fast` at Collision Point (Node -4)
   - Step 1 (+1 speed for both):
       slow moves from [3] to [2]
       fast moves from [-4] to [2]
   - slow == fast at Node(2)! 
   - Return Node(2).

          L=1               Cycle (C=3)
       +-----+         +-----+      +-----+
head ->|  3  | ------> |  2  | ---> |  0  |
       +-----+         +-----+      +-----+
                          ^            |
                          |            v
                       +-----+      +-----+
                       |     | <--- | -4  | <-- Collision Point
                       +-----+      +-----+

-------------------------------------------------

Trace Table

---------------------------------------------------------------------------------------------------------
Phase             | slow Pointer | fast Pointer | Check / Action               | Notes
---------------------------------------------------------------------------------------------------------
1. Detection      | Node(3)      | Node(3)      | Loop runs (+1 / +2)          | Detection starts
                  | Node(2)      | Node(0)      | slow != fast                 | Step 1
                  | Node(0)      | Node(2)      | slow != fast                 | Step 2
                  | Node(-4)     | Node(-4)     | slow == fast                 | Break (Cycle confirmed)
2. Reset          | Node(3)      | Node(-4)     | slow = head, fast remains    | Reset phase
3. Step-by-Step   | Node(2)      | Node(2)      | slow == fast (Loop ends)     | Met at entry node
---------------------------------------------------------------------------------------------------------
Result: Return Node(2)

Complexity Analysis:
- Time Complexity : O(n) — Phase 1 takes at most O(n) steps to find collision; Phase 2 takes L <= n steps. Total: O(n).
- Space Complexity: O(1) — Constant memory overhead using only reference variables (`slow`, `fast`).
=================================================
*/
