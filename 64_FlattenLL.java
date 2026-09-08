/*
// Definition for a Node.
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
};
*/

class Solution {
    // Operation: Flatten a Multilevel Doubly Linked List (LeetCode 430 - Recursive DFS Approach)
    public Node flatten(Node head) {
        Node curr = head;

        while (curr != null) {
            // Check if current node has a child sublist
            if (curr.child != null) {
                // Step 1: Save the next node before splicing
                Node next = curr.next;

                // Step 2: Recursively flatten the child branch and wire it
                curr.next = flatten(curr.child);
                curr.next.prev = curr;
                curr.child = null; // Clean up child pointer

                // Step 3: Traverse to the tail of the newly flattened child list
                while (curr.next != null) {
                    curr = curr.next;
                }

                // Step 4: Reconnect the child list tail back to the original 'next' node
                if (next != null) {
                    curr.next = next;
                    next.prev = curr;
                }
            }

            // Move forward along the list
            curr = curr.next;
        }

        return head;
    }
}

/*
==================== SUMMARY ====================

Problem: LeetCode 430 - Flatten a Multilevel Doubly Linked List

Pattern: Recursive Depth-First Search (DFS) Pre-order Traversal

Core Concept:
A multilevel Doubly Linked List has nodes that can branch downward via `.child` pointers.
Flattening requires unwrapping branches in preorder sequence (Node -> Child branch -> Next node),
integrating them into a single-level bidirectional DLL.

Key Algorithm Steps (When `curr.child != null`):
1. Preserve Original Forward Path:
   - Store `Node next = curr.next;` to prevent losing reference to the remainder of the current level.
2. Flatten & Splice Child Subtree:
   - Recursively call `flatten(curr.child)`.
   - Wire bidirectional pointers: `curr.next = flatten(curr.child)` and `curr.next.prev = curr`.
   - Nullify child pointer: `curr.child = null;`.
3. Locate Tail of the Flattened Branch:
   - Walk forward until reaching the last node of the spliced child segment (`while (curr.next != null)`).
4. Reconnect to Original Continuation:
   - If `next != null`, hook the end of the child segment to `next`:
     - `curr.next = next;`
     - `next.prev = curr;`

-------------------------------------------------

Structural Transformation Visualization

Before Flattening:
  Level 1:  [ 1 ] <=======> [ 2 ] <=======> [ 3 ] ---> null
                             | (child)
  Level 2:                 [ 4 ] <=======> [ 5 ] ---> null

Transformation Steps at Node 2:
1. Save `next = Node(3)`.
2. Recursively flatten `curr.child` (Node 4), wire `2.next = 4` and `4.prev = 2`, set `2.child = null`.
3. Advance to the end of the child sublist (lands on Node 5).
4. Reconnect Node 5 to saved `next`: `5.next = 3` and `3.prev = 5`.

After Flattening:
  null <--- [ 1 ] <---> [ 2 ] <---> [ 4 ] <---> [ 5 ] <---> [ 3 ] ---> null

-------------------------------------------------

Step-by-Step Trace Table

Input: 1 - 2 - 3, where 2 has child 4 - 5

-------------------------------------------------------------------------------------------------------------------------
Active Node (`curr`) | Has Child? | Action Taken                                       | Resulting List Segment
-------------------------------------------------------------------------------------------------------------------------
Node(1)              | No         | Advance: `curr = curr.next`                        | 1
Node(2)              | Yes        | `next = Node(3)`                                   | 1 <-> 2
                     |            | `curr.next = flatten(4)`                           | 1 <-> 2 <-> 4 <-> 5
                     |            | Advance `curr` to tail -> lands on Node(5)         | tail = Node(5)
                     |            | Connect tail: `5.next = 3` & `3.prev = 5`          | 1 <-> 2 <-> 4 <-> 5 <-> 3
Node(5)              | -          | Loop continues: `curr = curr.next` -> lands on 3   | -
Node(3)              | No         | Advance: `curr = curr.next` -> lands on `null`     | 1 <-> 2 <-> 4 <-> 5 <-> 3
null                 | -          | Loop terminates, return `head` (Node 1)            | Complete Flat DLL
-------------------------------------------------------------------------------------------------------------------------

Complexity Analysis:
- Time Complexity : O(n) — Each node is processed and traversed a constant number of times across all recursive calls.
- Space Complexity: O(k) — Auxiliary call stack space proportional to the maximum depth $k$ of child levels ($O(n)$ in worst-case single-column nesting).
=================================================
*/
