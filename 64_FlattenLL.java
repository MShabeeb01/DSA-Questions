class Solution {
    // Operation: Flatten a Multilevel Doubly Linked List (LeetCode 430 - Recursive DFS Approach)
    public Node flatten(Node head) {
        Node curr = head; // Start walking from the head of the list

        while (curr != null) {
            // If the current node has a child list, splice it right after curr
            if (curr.child != null) {
                Node next = curr.next; // Save original next node before rewiring

                // Recursively flatten the child branch and link it to curr
                curr.next = flatten(curr.child);
                curr.next.prev = curr;
                curr.child = null; // Clear child pointer as required by problem

                // Walk to the tail of the newly flattened child list
                while (curr.next != null) {
                    curr = curr.next;
                }

                // Hook the tail of the child list back to the saved next node
                if (next != null) {
                    curr.next = next;
                    next.prev = curr;
                }
            }

            curr = curr.next; // Move to the next node in the flattened chain
        }

        return head;
    }
}

/*
================================================================================
PART 2: SUMMARY OF THE APPROACH & LINE-BY-LINE EXPLANATION
================================================================================

SUMMARY OF THE APPROACH:
This solution flattens a multilevel doubly linked list into a single-level
doubly linked list using recursive depth-first traversal (preorder: current
node -> child branch -> original next node).

As we walk forward with `curr`, whenever we hit a node with a non-null
`.child` pointer:
1. We save `curr.next` so we do not lose the rest of the current level.
2. We recursively flatten `curr.child`, connect it right after `curr` (updating
   both `.next` and `.prev` pointers), and set `curr.child = null`.
3. We walk `curr` forward to the end (tail) of that flattened child branch.
4. If there was an original `next` node, we connect the child branch's tail to
   `next` in both directions (`.next` and `.prev`).

LINE-BY-LINE EXPLANATION:

Line: class Solution {
What it does: Declares the `Solution` class required by LeetCode to execute
our `flatten` method.
--------------------------------------------------------------------------------

Line: public Node flatten(Node head) {
What it does: Defines the main method taking the starting `head` node of a
multilevel doubly linked list and returning the head of the flattened single-level
doubly linked list.
--------------------------------------------------------------------------------

Line: Node curr = head;
What it does: Initializes a pointer `curr` at `head` so we can walk through the
list from left to right.
--------------------------------------------------------------------------------

Line: while (curr != null) {
What it does: Keeps scanning through the list until `curr` moves past the last
node and becomes `null`.
--------------------------------------------------------------------------------

Line: if (curr.child != null) {
What it does: Checks whether `curr` has a child branch pointing downward that
needs to be flattened and inserted into the main chain.
--------------------------------------------------------------------------------

Line: Node next = curr.next;
What it does: Saves a reference to `curr.next` before we overwrite `curr.next`
with the flattened child list.
--------------------------------------------------------------------------------

Line: curr.next = flatten(curr.child);
What it does: Recursively flattens the entire child branch starting at
`curr.child` and sets `curr.next` to the head of that flattened child branch.
--------------------------------------------------------------------------------

Line: curr.next.prev = curr;
What it does: Sets the backward link (`prev`) of the flattened child list's head
back to `curr`, keeping the doubly linked list bidirectional.
--------------------------------------------------------------------------------

Line: curr.child = null;
What it does: Clears `curr.child` to `null` because in a flattened list, child
pointers must all be `null`.
--------------------------------------------------------------------------------

Line: while (curr.next != null) {
What it does: Moves `curr` forward step by step until it reaches the last node
(tail) of the newly spliced child branch.
--------------------------------------------------------------------------------

Line: curr = curr.next;
What it does: Advances `curr` one node forward toward the tail of the child
branch.
--------------------------------------------------------------------------------

Line: if (next != null) {
What it does: Checks if there was a node after `curr` originally (in case `curr`
was already the last node on its level).
--------------------------------------------------------------------------------

Line: curr.next = next;
What it does: Connects the tail of the flattened child branch forward to the
saved `next` node.
--------------------------------------------------------------------------------

Line: next.prev = curr;
What it does: Connects `next.prev` backward to `curr` (the child branch's
tail) so the reverse links are intact.
--------------------------------------------------------------------------------

Line: curr = curr.next;
What it does: Moves `curr` forward to continue traversing the flattened list.
--------------------------------------------------------------------------------

Line: return head;
What it does: Returns the original `head` of the list, which now points to the
start of the complete flattened doubly linked list.
--------------------------------------------------------------------------------


================================================================================
PART 3: STEP-BY-STEP ITERATION WITH VISUAL REPRESENTATION
================================================================================

EXAMPLE INPUT:
Main level:  [1] <---> [2] <---> [3] -> null
Child of [2]:          [4] <---> [5] -> null

Expected output:
[1] <---> [2] <---> [4] <---> [5] <---> [3] -> null

Note on additions/deletions:
We are NOT creating/deleting nodes from memory or pushing/popping from a stack
or array. We are rewiring `.next` and `.prev` pointers, setting `curr.child =
null`, and advancing `curr` along the list (plus recursive call stack frames).

--- START (Before Loop) ---
- Tested:
  `curr != null` -> `[1] != null` -> true (enter loop).
- Pointer position:
  `curr` = [1]

Visual:
  curr
   v
  [1] <---> [2] <---> [3] -> null
             | (child)
            [4] <---> [5] -> null

--- ITERATION 1 (At Node [1]) ---
- Tested:
  `curr.child != null` -> `[1].child == null` -> false (no child branch to splice).
- Updated:
  `curr = curr.next` -> `curr` moves from [1] to [2].
- Tested next loop check:
  `curr != null` -> `[2] != null` -> true.

Visual:
             curr
              v
  [1] <---> [2] <---> [3] -> null
             | (child)
            [4] <---> [5] -> null

--- ITERATION 2 (At Node [2] - Child Found!) ---
- Tested:
  `curr.child != null` -> `[2].child` is [4] (not null) -> true!
- Saved / Updated (Step 1 & 2):
  `next` = [3] (saved so we don't lose [3]).
  `flatten([4])` flattens [4] <---> [5] -> null and returns [4].
  `[2].next` = [4], `[4].prev` = [2], and `[2].child` = null.

Visual after splicing child head:
             curr
              v
  [1] <---> [2] <---> [4] <---> [5] -> null    [3] -> null
             (child is now null; saved `next` = [3])

- Updated (Step 3 - Walking to child tail):
  `curr.next != null` is true at [4] -> `curr` moves to [5].
  At [5], `curr.next` is null -> inner `while` stops with `curr` at [5].

Visual at child tail:
                                curr
                                 v
  [1] <---> [2] <---> [4] <---> [5] -> null    [3] -> null

- Tested & Updated (Step 4 - Reconnecting saved `next`):
  `next != null` -> `[3] != null` -> true.
  `[5].next` = [3] and `[3].prev` = [5].

Visual after reconnecting [3]:
                                curr
                                 v
  [1] <---> [2] <---> [4] <---> [5] <---> [3] -> null

- Updated at end of outer loop body:
  `curr = curr.next` -> `curr` moves from [5] to [3].

Visual:
                                           curr
                                            v
  [1] <---> [2] <---> [4] <---> [5] <---> [3] -> null

--- ITERATION 3 (At Node [3]) ---
- Tested:
  `curr.child != null` -> `[3].child == null` -> false.
- Updated:
  `curr = curr.next` -> `curr` moves from [3] to null.
- Tested next loop check:
  `curr != null` -> `null != null` -> false (exit `while` loop).

Visual:
                                                   curr = null
                                                       v
  [1] <---> [2] <---> [4] <---> [5] <---> [3] -> null

--- AFTER LOOP ---
- Returned value:
  `head` -> [1] <---> [2] <---> [4] <---> [5] <---> [3] -> null.


================================================================================
PART 4: TIME & SPACE COMPLEXITY
================================================================================

VARIABLES DEFINED:
- n = total number of nodes across all levels of the multilevel doubly linked list.
- d = maximum nesting depth of child levels.

TIME COMPLEXITY: O(n)
- Plain English explanation:
  - Every node in the list is visited by `flatten` once, and whenever a child
    branch is spliced in, `curr` walks forward through the nodes of that
    branch. Even though `curr` moves forward to find the tail, each node is
    crossed only a constant number of times overall, so total work is linear,
    O(n).

SPACE COMPLEXITY: O(d) (which is O(n) in the worst case)
- Plain English explanation:
  - Extra space comes from the recursion call stack when `flatten(curr.child)`
    is called on nested child levels. At most d recursive calls are active at
    once (where d is the deepest child level), giving O(d) space—or O(n) in the
    worst case if every single node has a child one level deeper. No extra
    data structures like lists or queues are allocated.


================================================================================
PART 5: MOST PROBABLE INTERVIEW QUESTIONS
================================================================================

Q1: Why do we need to update both `.next` and `.prev` pointers when splicing
    and reconnecting nodes?
A1: "Because this is a doubly linked list, every adjacent pair of nodes must
    point to each other in both directions. If we only set `.next` and forget
    `.prev`, walking backward from later nodes or checking `prev` pointers would
    be broken."

Q2: Why do we set `curr.child = null`?
A2: "The problem requires all `.child` pointers in the flattened list to be
    `null` so the structure becomes a standard single-level doubly linked list
    without leftover child branches."

Q3: How would you solve this iteratively instead of using recursion?
A3: "I can use a stack (or `ArrayDeque`). Whenever `curr.child != null`, if
    `curr.next != null` I push `curr.next` onto the stack, attach `curr.child`
    as `curr.next` (setting `.prev` and clearing `.child`), and keep moving
    `curr`. When `curr.next` becomes `null` and the stack is not empty, I pop
    the next saved node and link it to `curr`."
================================================================================
*/
