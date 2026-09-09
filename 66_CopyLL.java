import java.util.HashMap;
import java.util.Map;

/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    // Operation: Copy List with Random Pointer (LeetCode 138 - HashMap Approach)
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }

        // Map to store mapping between original nodes and their cloned counterparts
        Map<Node, Node> m = new HashMap<>();

        // Initialize clone head
        Node newHead = new Node(head.val);
        Node oldTemp = head.next;
        Node newTemp = newHead;
        m.put(head, newHead);

        // Pass 1: Clone nodes and next pointers, map old nodes to new nodes
        while (oldTemp != null) {
            Node copyNode = new Node(oldTemp.val);
            m.put(oldTemp, copyNode);
            newTemp.next = copyNode;

            oldTemp = oldTemp.next;
            newTemp = newTemp.next;
        }

        // Pass 2: Assign random pointers for cloned nodes using the map
        oldTemp = head;
        newTemp = newHead;

        while (oldTemp != null) {
            newTemp.random = m.get(oldTemp.random);
            oldTemp = oldTemp.next;
            newTemp = newTemp.next;
        }

        return newHead;
    }
}

/*
==================== SUMMARY ====================

Problem: LeetCode 138 - Copy List with Random Pointer

Core Problem:
A deep copy of a linked list where each node has two pointers:
1. `next`: Standard pointer to the sequential node.
2. `random`: Pointer that can point to any node in the list or `null`.
Direct one-pass copying fails because a node's `random` pointer might target a node 
that hasn't been created yet.

Approach: Two-Pass Cloning with HashMap (`Map<Node, Node>`)

Algorithm Breakdown:
1. Pass 1 (Clone Nodes & Rebuild Next Chain):
   - Traverse the original list node-by-node.
   - For every original node, instantiate a clone node: `copyNode = new Node(oldTemp.val)`.
   - Store mapping in table: `m.put(oldTemp, copyNode)`.
   - Wire the standard forward traversal: `newTemp.next = copyNode`.

2. Pass 2 (Assign Random References):
   - Reset traversal pointers: `oldTemp = head`, `newTemp = newHead`.
   - For every cloned node, set its random pointer by querying the hash table:
     `newTemp.random = m.get(oldTemp.random)`.
   - If `oldTemp.random` is `null`, `m.get(null)` safely returns `null`.

-------------------------------------------------

Mapping & Structure Diagram

Original Chain:
  [A] ------------> [B] ------------> [C] ---> null
   |                 ^                 |
   '--- random ------'                 '--- random ---> [A]

HashMap Table (`m`):
  Key (Original)  | Value (Deep Copy)
  ----------------|-------------------
  [A]             | [A']
  [B]             | [B']
  [C]             | [C']

Cloned Chain after Pass 2:
  [A'] -----------> [B'] -----------> [C'] ---> null
   |                 ^                 |
   '--- random ------'                 '--- random ---> [A']

-------------------------------------------------

Step-by-Step Trace Table

Input: [7, null] -> [13, 0] -> [11, 4] -> null

---------------------------------------------------------------------------------------------------------
Pass    | Node Processed | Action Taken                                      | Map / Pointer State
---------------------------------------------------------------------------------------------------------
Pass 1  | head (7)       | Create Node(7'), put(7, 7')                       | m = {7: 7'}
        | Node(13)       | Create Node(13'), 7'.next = 13', put(13, 13')     | m = {7: 7', 13: 13'}
        | Node(11)       | Create Node(11'), 13'.next = 11', put(11, 11')    | m = {7: 7', 13: 13', 11: 11'}
Pass 2  | Node(7)        | 7'.random = m.get(7.random = null)                | 7'.random = null
        | Node(13)       | 13'.random = m.get(13.random = 7)                 | 13'.random = 7'
        | Node(11)       | 11'.random = m.get(11.random = null)              | 11'.random = null
---------------------------------------------------------------------------------------------------------
Output: Independent deep cloned list head pointing to Node(7').

Complexity Analysis:
- Time Complexity : O(n) — Two linear passes over n nodes; HashMap operations take O(1) average time.
- Space Complexity: O(n) — Hash table stores mappings for all n nodes.
=================================================
*/
