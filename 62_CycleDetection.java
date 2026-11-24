public class Solution {
    // Operation: Detect if Linked List has a Cycle (LeetCode 141)
    public boolean hasCycle(ListNode head) {
        ListNode slow = head; // Moves 1 step (+1)
        ListNode fast = head; // Moves 2 steps (+2)
        boolean isCycle = false; // Tracks whether a cycle is found

        // Traverse until fast hits the end (null) or cannot jump two steps
        while (fast != null && fast.next != null) {
            slow = slow.next; // Advance slow by 1 node
            fast = fast.next.next; // Advance fast by 2 nodes

            // If both pointers land on the same node reference, there is a cycle
            if (fast == slow) {
                isCycle = true;
                break;
            }
        }

        return isCycle;
    }
}

/*
================================================================================
PART 2: SUMMARY OF THE APPROACH & LINE-BY-LINE EXPLANATION
================================================================================

SUMMARY OF THE APPROACH:
This solution uses Floyd's Cycle-Finding Algorithm, often called the "Tortoise
and Hare" approach, with two pointers moving at different speeds.

Imagine two runners on a track starting at the same spot (`head`). One runner
(`slow`) takes 1 step at a time, and the other runner (`fast`) takes 2 steps at
a time.

- If the linked list has NO cycle, the `fast` runner will eventually run off
  the end and hit `null` (or `fast.next` will be `null`), proving there is no
  loop.
- If the linked list HAS a cycle, both runners get trapped inside the loop.
  Because `fast` moves 1 extra step ahead of `slow` every single round, the gap
  between them shrinks until `fast` catches up to and collides with `slow`.

LINE-BY-LINE EXPLANATION:

Line: public class Solution {
What it does: Declares the `Solution` class required by LeetCode so the
platform can call our cycle-detection method.
--------------------------------------------------------------------------------

Line: public boolean hasCycle(ListNode head) {
What it does: Defines the main method that takes the starting node (`head`) of a
singly-linked list and returns `true` if a cycle exists or `false` if it does
not.
--------------------------------------------------------------------------------

Line: ListNode slow = head;
What it does: Creates the `slow` pointer and starts it at the beginning of the
list (`head`). It will move forward 1 node at a time.
--------------------------------------------------------------------------------

Line: ListNode fast = head;
What it does: Creates the `fast` pointer and also starts it at `head`. It will
move forward 2 nodes at a time so it can either reach the end quickly or catch
up to `slow` inside a cycle.
--------------------------------------------------------------------------------

Line: boolean isCycle = false;
What it does: Initializes a flag variable `isCycle` to `false`. We assume there
is no cycle until proven otherwise, and flip it to `true` if `fast` and `slow`
meet.
--------------------------------------------------------------------------------

Line: while (fast != null && fast.next != null) {
What it does: Keeps the loop running as long as `fast` is on a valid node AND
there is at least one more node (`fast.next`) so `fast.next.next` will not
crash with a `NullPointerException`.
--------------------------------------------------------------------------------

Line: slow = slow.next;
What it does: Moves the `slow` pointer forward by 1 step to the next node in the
list.
--------------------------------------------------------------------------------

Line: fast = fast.next.next;
What it does: Moves the `fast` pointer forward by 2 steps (jumping over one
node) to stay ahead of `slow`.
--------------------------------------------------------------------------------

Line: if (fast == slow) {
What it does: Checks whether `fast` and `slow` now point to the exact same node
in memory (comparing object references, not just values). Meeting means they
are trapped in a loop.
--------------------------------------------------------------------------------

Line: isCycle = true;
What it does: Records that a cycle has been detected by setting `isCycle` to
`true`.
--------------------------------------------------------------------------------

Line: break;
What it does: Exits the `while` loop immediately because we already have our
answer and do not need to keep spinning around the cycle.
--------------------------------------------------------------------------------

Line: return isCycle;
What it does: Returns `true` if a collision happened inside the loop, or `false`
if `fast` reached the end of the list without ever meeting `slow`.
--------------------------------------------------------------------------------


================================================================================
PART 3: STEP-BY-STEP ITERATION WITH VISUAL REPRESENTATION
================================================================================

EXAMPLE INPUT:
head = [3 -> 2 -> 0 -> -4], where -4 connects back to 2 (cycle exists).

Visualizing the list structure:
[3] ---> [2] ---> [0] ---> [-4]
          ^                   |
          |                   |
          +-------------------+

Note on additions/deletions:
In this pointer-walk problem, we are NOT adding or deleting nodes from the
linked list or pushing/popping from a stack/list. Instead, we are advancing
two pointer references (`slow` and `fast`) and testing whether `fast != null &&
fast.next != null` and whether `fast == slow`.

--- START (Before Loop) ---
- Tested:
  `fast != null && fast.next != null` -> `3 != null && 2 != null` -> true (enter loop)
- Pointer positions:
  `slow` = [3]
  `fast` = [3]

Visual:
  slow, fast
      v
     [3] ---> [2] ---> [0] ---> [-4]
               ^                   |
               +-------------------+

--- ITERATION 1 ---
- Added / Updated:
  `slow` advances +1 -> moves from [3] to [2]
  `fast` advances +2 -> moves from [3] -> [2] -> [0] (now at [0])
- Tested:
  `fast == slow` -> is [0] == [2]? NO (false).
  Next loop check: `fast != null && fast.next != null` -> `[0] != null && [-4] != null` -> true.

Visual:
              slow     fast
                v        v
     [3] ---> [2] ---> [0] ---> [-4]
               ^                   |
               +-------------------+

--- ITERATION 2 ---
- Added / Updated:
  `slow` advances +1 -> moves from [2] to [0]
  `fast` advances +2 -> moves from [0] -> [-4] -> [2] (wraps around cycle to [2])
- Tested:
  `fast == slow` -> is [2] == [0]? NO (false).
  Next loop check: `fast != null && fast.next != null` -> `[2] != null && [0] != null` -> true.

Visual:
              fast     slow
                v        v
     [3] ---> [2] ---> [0] ---> [-4]
               ^                   |
               +-------------------+

--- ITERATION 3 ---
- Added / Updated:
  `slow` advances +1 -> moves from [0] to [-4]
  `fast` advances +2 -> moves from [2] -> [0] -> [-4] (now at [-4])
- Tested:
  `fast == slow` -> is [-4] == [-4]? YES (true)!
  Sets `isCycle = true` and executes `break` (exits loop immediately).

Visual:
                            slow, fast
                                v
     [3] ---> [2] ---> [0] ---> [-4]
               ^                   |
               +-------------------+

--- AFTER LOOP ---
- Returned value: `true` (cycle confirmed).


================================================================================
PART 4: TIME & SPACE COMPLEXITY
================================================================================

VARIABLES DEFINED:
- n = total number of nodes in the linked list.
- c = number of nodes inside the cycle (when a cycle is present).

TIME COMPLEXITY: O(n)
- Plain English explanation:
  - If there is NO cycle, `fast` moves 2 nodes per step and reaches the end of
    the list in roughly n / 2 steps. Dropping the constant factor gives O(n).
  - If there IS a cycle, once both pointers enter the cycle, `fast` closes the
    distance to `slow` by 1 step per iteration and catches up in at most c
    steps (where c is at most n). Total work stays linear, so it is O(n).

SPACE COMPLEXITY: O(1)
- Plain English explanation:
  - We only store two node pointers (`slow` and `fast`) and one boolean
    (`isCycle`). No hash set, array, or extra data structure is created, so
    memory usage stays constant regardless of how large the list gets.


================================================================================
PART 5: MOST PROBABLE INTERVIEW QUESTIONS
================================================================================

Q1: Why does `fast` move by 2 steps instead of 3 or more?
A1: "Moving by 2 steps is clean, simple, and guarantees `fast` can never jump
    over `slow` without landing on it once both are inside the cycle. If `fast`
    moves 1 step faster than `slow` relative to the cycle, the gap between them
    shrinks by 1 each turn until it hits 0—meaning they must collide."

Q2: Why do we check `fast != null && fast.next != null` in the `while` loop,
    and why is the order important?
A2: "If `fast` reaches `null`, we are at the end of the list. If `fast` is on
    the last node, `fast.next` is `null`, so we cannot safely call
    `fast.next.next`. Checking `fast != null` first is crucial because of
    short-circuiting—if `fast` is `null`, Java stops evaluating and never tries
    to read `fast.next`, avoiding a `NullPointerException`."

Q3: How would you find the exact node where the cycle begins (LeetCode 142)?
A3: "Once `fast` and `slow` meet inside the cycle, I keep one pointer at the
    meeting point and place a new pointer at `head`. Then I move both pointers
    1 step at a time—the node where they meet again is the start of the
    cycle."
================================================================================
*/
