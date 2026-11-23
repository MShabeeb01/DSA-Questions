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
================================================================================
PART 2: SUMMARY OF THE APPROACH & LINE-BY-LINE EXPLANATION
================================================================================

Core Approach Summary:
----------------------
This solution reverses a singly-linked list in place using the **Three-Pointer
Iterative** technique (`prev`, `curr`, and `next`).

Imagine walking along a one-way street where every house points an arrow to
the next house, and your job is to turn every arrow around so it points
backward:
- `prev` points to the node behind `curr` (the front of the reversed list built
  so far).
- `curr` is the active node whose `next` arrow we are flipping backward.
- `next` temporarily saves the remaining list ahead of `curr` before we
  overwrite `curr.next`—otherwise we would lose sight of the rest of the list.

When `curr` moves past the last node and becomes `null`, `prev` is sitting on
the old tail, which is now the **new head** of the reversed list.

--------------------------------------------------------------------------------
Line-by-Line Explanation:
--------------------------------------------------------------------------------

Line: class Solution {
What it does:
  - Opens the `Solution` class, which is the standard wrapper expected by
    LeetCode for your solver method.

--------------------------------------------------------------------------------

Line: public ListNode reverseList(ListNode head) {
What it does:
  - Defines the main method that receives `head` (the first node of the
    singly-linked list), reverses all links in place, and returns the new head
    of the reversed list.

--------------------------------------------------------------------------------

Line: ListNode prev = null;
What it does:
  - Creates the `prev` pointer and starts it at `null`.
  - The original `head` will become the new tail of the reversed list, so its
    `next` pointer needs to point to `null` (`prev`) to mark the end.

--------------------------------------------------------------------------------

Line: ListNode curr = head;
What it does:
  - Creates the `curr` pointer and starts it at `head` so we begin reversing
    links from the very first node.

--------------------------------------------------------------------------------

Line: ListNode next = null;
What it does:
  - Creates the `next` pointer (initially `null`) to temporarily hold
    `curr.next` inside each loop iteration before we change `curr.next`.

--------------------------------------------------------------------------------

Line: while (curr != null) {
What it does:
  - Keeps looping as long as `curr` points to a real node.
  - Stops once `curr` becomes `null`, which means every node in the original
    list has had its link reversed.

--------------------------------------------------------------------------------

Line: next = curr.next;
What it does:
  - Saves a reference to the next node in the original list so we do not lose
    the rest of the list when we flip `curr.next` on the next line.

--------------------------------------------------------------------------------

Line: curr.next = prev;
What it does:
  - Flips `curr`'s arrow backward so it points to `prev` instead of the next
    node in the original list.

--------------------------------------------------------------------------------

Line: prev = curr;
What it does:
  - Moves `prev` forward to `curr` so `prev` now points to the latest node in
    our reversed chain.

--------------------------------------------------------------------------------

Line: curr = next;
What it does:
  - Moves `curr` forward to the saved `next` node so the next loop cycle can
    reverse its link.

--------------------------------------------------------------------------------

Line: return prev;
What it does:
  - Returns `prev` after the loop finishes.
  - Because `curr` is `null` when the loop exits, `prev` points to the last
    visited node—which is the new head of the reversed linked list.

================================================================================
PART 3: STEP-BY-STEP ITERATION WITH VISUAL REPRESENTATION
================================================================================

Example Walkthrough: List [1 -> 2 -> 3 -> null]
------------------------------------------------
Note on additions/deletions:
  No list nodes are added to or deleted from memory, and no extra array, list,
  or stack is used—we only rewire `.next` pointers and shift `prev`, `curr`,
  and `next`.
  If we were tracking reversed nodes in a helper list or stack, each step would
  add/push the current node to the front of the reversed chain and pop/delete
  nothing until all nodes are processed.

Initial State (Before Loop):
  - Tested: `curr != null` -> `Node(1) != null` is `true`.
  - Added / Deleted: None (pointers initialized: `prev = null`, `curr = Node(1)`,
    `next = null`).

  null        [1] -> [2] -> [3] -> null
   ^           ^
  prev        curr

--------------------------------------------------------------------------------

Iteration 1:
  - Tested: `curr != null` (`Node(1) != null` -> `true`).
  - Added / Deleted:
    - Saved `next = Node(2)` (no node deleted; forward link is temporarily
      bypassed).
    - Reversed `Node(1).next` from `Node(2)` to `null` (`prev`).
    - Shifted `prev` to `Node(1)` and `curr` to `Node(2)`.

  null <- [1]    [2] -> [3] -> null
           ^      ^      ^
          prev   curr   next

--------------------------------------------------------------------------------

Iteration 2:
  - Tested: `curr != null` (`Node(2) != null` -> `true`).
  - Added / Deleted:
    - Saved `next = Node(3)`.
    - Reversed `Node(2).next` from `Node(3)` to `Node(1)` (`prev`).
    - Shifted `prev` to `Node(2)` and `curr` to `Node(3)`.

  null <- [1] <- [2]    [3] -> null
                  ^      ^      ^
                 prev   curr   next

--------------------------------------------------------------------------------

Iteration 3:
  - Tested: `curr != null` (`Node(3) != null` -> `true`).
  - Added / Deleted:
    - Saved `next = null`.
    - Reversed `Node(3).next` from `null` to `Node(2)` (`prev`).
    - Shifted `prev` to `Node(3)` and `curr` to `null`.

  null <- [1] <- [2] <- [3]    curr = null, next = null
                         ^
                        prev

--------------------------------------------------------------------------------

Loop Exit & Return:
  - Tested: `curr != null` (`null != null` -> `false`) -> Loop ends.
  - Added / Deleted: None.
  - Returns `prev` = `Node(3)`, giving the reversed list `3 -> 2 -> 1 -> null`.

================================================================================
PART 4: TIME & SPACE COMPLEXITY
================================================================================

Time Complexity: O(n)
  - Variable definition: `n` is the total number of nodes in the linked list.
  - Plain-English explanation: The `while` loop visits each of the `n` nodes
    once and performs a fixed number of pointer assignments per node, giving
    linear time `O(n)`.

Space Complexity: O(1)
  - Variable definition: `n` is the total number of nodes in the linked list.
  - Plain-English explanation: We only use three pointer variables (`prev`,
    `curr`, and `next`) and reverse the list in place without allocating extra
    arrays, lists, or hash maps, so extra space is constant `O(1)`.

================================================================================
PART 5: MOST PROBABLE INTERVIEW QUESTIONS
================================================================================

Q1: Why do we save `next = curr.next` before doing `curr.next = prev`?
Answer:
  Because `curr.next = prev` overwrites `curr`'s pointer to the next node in
  the original list. Saving `curr.next` in `next` first ensures we don't lose
  the remaining nodes before moving `curr` forward.

Q2: What happens if `head` is `null` (an empty list) or has only 1 node?
Answer:
  If `head` is `null`, `curr` starts as `null`, the `while` loop does not run,
  and the method returns `prev` (`null`). If `head` has 1 node (`[1]`), the
  loop runs once (`1.next` becomes `null`, `prev` becomes `Node(1)`, `curr`
  becomes `null`) and returns `Node(1)`.

Q3: Why do we return `prev` instead of `curr` at the end?
Answer:
  The loop only exits when `curr` becomes `null`, so returning `curr` would
  return `null`. By that moment, `prev` has moved onto the final node of the
  original list, which is the new head of the reversed list.
================================================================================
*/
