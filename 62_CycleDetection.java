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
    // Operation: Detect if Linked List has a Cycle (LeetCode 141)
    public boolean hasCycle(ListNode head) {
        ListNode slow = head; // Moves 1 step (+1)
        ListNode fast = head; // Moves 2 steps (+2)
        boolean isCycle = false;

        // Traverse until fast reaches the end (null) or advances past the end
        while (fast != null && fast.next != null) {
            slow = slow.next;        // +1
            fast = fast.next.next;   // +2

            // If fast and slow meet, a closed cycle exists
            if (fast == slow) {
                isCycle = true;
                break;
            }
        }

        return isCycle;
    }
}

/*
==================== SUMMARY ====================

Problem: LeetCode 141 - Linked List Cycle

Objective:
Determine whether the linked list has a cycle. Return `true` if there is a cycle, otherwise return `false`.

Core Logic:
- Uses Floyd's Cycle-Finding Algorithm (Tortoise and Hare).
- Pointers:
  - `slow`: Moves at $1\times$ speed (`slow = slow.next`).
  - `fast`: Moves at $2\times$ speed (`fast = fast.next.next`).
- Mechanics:
  - If the list has no loop, `fast` will inevitably encounter `null` or `fast.next == null`.
  - If the list contains a loop, both pointers will be trapped in the loop.
  - The relative distance between them shrinks by 1 node per iteration until `fast == slow`.

Loop Safety Guards:
- `while (fast != null && fast.next != null)` prevents throwing `NullPointerException`.

-------------------------------------------------

Pointer Walkthrough Visual

Example: [3 -> 2 -> 0 -> -4] where -4 connects back to 2 (pos = 1)

Initial:
  [3] -> [2] -> [0] -> [-4]
   ^                      |
   |                      v
 slow, fast              (loops back to 2)

Step 1:
  slow moves to [2]
  fast moves to [0]
  slow != fast

Step 2:
  slow moves to [0]
  fast moves from [0] -> [-4] -> [2]
  slow != fast

Step 3:
  slow moves to [-4]
  fast moves from [2] -> [0] -> [-4]
  slow == fast (Collision detected at Node -4!)
  Set isCycle = true -> break -> return true

-------------------------------------------------

Trace Table

Input: head = [3, 2, 0, -4], pos = 1

---------------------------------------------------------------------------------------------------------
Iteration | slow Position | fast Position | fast == slow | Condition Check (fast & fast.next != null)
---------------------------------------------------------------------------------------------------------
Start     | Node(3)       | Node(3)       | -            | Valid
1         | Node(2)       | Node(0)       | false        | Valid
2         | Node(0)       | Node(2)       | false        | Valid
3         | Node(-4)      | Node(-4)      | true         | Breaks loop, returns true
---------------------------------------------------------------------------------------------------------

Complexity Analysis:
- Time Complexity : O(n)
  - Non-cyclic list: fast traverses n/2 elements -> O(n).
  - Cyclic list: fast catches slow within C iterations (C = loop length <= n) -> O(n).
- Space Complexity: O(1) — Memory footprint is constant with only two pointers.
=================================================
*/
