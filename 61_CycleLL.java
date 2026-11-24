public class Solution {
    // Operation: Detect Cycle II - Find Starting Node of Loop (LeetCode 142)
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head; // Moves 1 step at a time (+1)
        ListNode fast = head; // Moves 2 steps at a time (+2)
        boolean isCycle = false; // Tracks whether a cycle exists

        // Phase 1: Move slow by 1 and fast by 2 to see if they meet inside a loop
        while (fast != null && fast.next != null) {
            slow = slow.next; // Advance slow by 1 node
            fast = fast.next.next; // Advance fast by 2 nodes

            // Meeting means both pointers are trapped in a cycle
            if (slow == fast) {
                isCycle = true;
                break;
            }
        }

        // If no cycle was found, there is no cycle entry node
        if (!isCycle) {
            return null;
        }

        // Phase 2: Reset slow to head and move both 1 step at a time to find loop start
        slow = head; // Reset slow back to the start of the list
        while (slow != fast) {
            slow = slow.next; // Advance slow by 1 node
            fast = fast.next; // Advance fast by 1 node from meeting point
        }

        return slow; // Both meet at the exact node where the cycle starts
    }
}

/*
================================================================================
PART 2: SUMMARY OF THE APPROACH & LINE-BY-LINE EXPLANATION
================================================================================

SUMMARY OF THE APPROACH:
This solution uses Floyd's Cycle-Finding Algorithm (Tortoise and Hare) in two
phases to find the exact node where a cycle begins.

- Phase 1 (Detect the cycle): Start `slow` (1 step/move) and `fast` (2
  steps/moves) at `head`. If `fast` hits `null` or `fast.next` is `null`, there
  is no cycle, so return `null`. If they meet at some node inside the loop, a
  cycle exists.
- Phase 2 (Find the entry node): Move `slow` back to `head`, keep `fast` at
  the meeting point, and advance BOTH pointers 1 step at a time. Because of how
  the distances line up around the loop, they are guaranteed to meet right at
  the node where the cycle begins.

LINE-BY-LINE EXPLANATION:

Line: public class Solution {
What it does: Declares the `Solution` class required by LeetCode so it can run
our `detectCycle` method.
--------------------------------------------------------------------------------

Line: public ListNode detectCycle(ListNode head) {
What it does: Defines the main method taking `head` of a singly-linked list and
returning the `ListNode` where the cycle starts (or `null` if there is no
cycle).
--------------------------------------------------------------------------------

Line: ListNode slow = head;
What it does: Initializes the `slow` pointer at `head` so it can walk 1 node
per step.
--------------------------------------------------------------------------------

Line: ListNode fast = head;
What it does: Initializes the `fast` pointer at `head` so it can walk 2 nodes
per step during cycle detection.
--------------------------------------------------------------------------------

Line: boolean isCycle = false;
What it does: Creates a flag `isCycle` set to `false` initially, which will
become `true` only if `slow` and `fast` collide.
--------------------------------------------------------------------------------

Line: while (fast != null && fast.next != null) {
What it does: Keeps Phase 1 running while `fast` is on a valid node and has a
`fast.next` node so jumping two steps (`fast.next.next`) cannot cause a
`NullPointerException`.
--------------------------------------------------------------------------------

Line: slow = slow.next;
What it does: Moves `slow` forward by 1 node.
--------------------------------------------------------------------------------

Line: fast = fast.next.next;
What it does: Moves `fast` forward by 2 nodes.
--------------------------------------------------------------------------------

Line: if (slow == fast) {
What it does: Checks if `slow` and `fast` point to the exact same node in
memory, meaning they have met inside a cycle.
--------------------------------------------------------------------------------

Line: isCycle = true;
What it does: Marks `isCycle` as `true` to record that a cycle was found.
--------------------------------------------------------------------------------

Line: break;
What it does: Exits Phase 1's `while` loop right away since we already found a
meeting point inside the cycle.
--------------------------------------------------------------------------------

Line: if (!isCycle) {
What it does: Checks whether Phase 1 finished without ever finding a cycle.
--------------------------------------------------------------------------------

Line: return null;
What it does: Returns `null` immediately because a list with no cycle has no
cycle starting node.
--------------------------------------------------------------------------------

Line: slow = head;
What it does: Resets `slow` back to the beginning (`head`) while leaving `fast`
sitting at the Phase 1 collision node.
--------------------------------------------------------------------------------

Line: while (slow != fast) {
What it does: Keeps Phase 2 running at 1 step per pointer until `slow` and
`fast` land on the exact same node.
--------------------------------------------------------------------------------

Line: slow = slow.next;
What it does: Advances `slow` by 1 node toward the cycle entry.
--------------------------------------------------------------------------------

Line: fast = fast.next;
What it does: Advances `fast` by 1 node around the cycle toward the cycle entry.
--------------------------------------------------------------------------------

Line: return slow;
What it does: Returns `slow` (which equals `fast`), pointing to the exact node
where the cycle begins.
--------------------------------------------------------------------------------


================================================================================
PART 3: STEP-BY-STEP ITERATION WITH VISUAL REPRESENTATION
================================================================================

EXAMPLE INPUT:
head = [3 -> 2 -> 0 -> -4], where [-4] points back to [2].
Expected output: Node with value 2 (the cycle entry node).

Visualizing the list structure:
[3] ---> [2] ---> [0] ---> [-4]
          ^                   |
          |                   |
          +-------------------+

Note on additions/deletions:
We do NOT add or delete nodes from the linked list or push/pop from a stack or
list. Instead, we update pointer positions (`slow` and `fast`) and test loop
conditions (`fast != null && fast.next != null`, `slow == fast` in Phase 1, and
`slow != fast` in Phase 2).

--- PHASE 1: START (Before Loop) ---
- Tested:
  `fast != null && fast.next != null` -> `[3] != null && [2] != null` -> true (enter loop)
- Pointer positions:
  `slow` = [3], `fast` = [3], `isCycle` = false

Visual:
  slow, fast
      v
     [3] ---> [2] ---> [0] ---> [-4]
               ^                   |
               +-------------------+

--- PHASE 1: ITERATION 1 ---
- Updated:
  `slow` moves +1 -> [2]
  `fast` moves +2 -> [3] -> [2] -> [0] (now at [0])
- Tested:
  `slow == fast` -> is [2] == [0]? NO (false).
  Next loop check: `fast != null && fast.next != null` -> `[0] != null && [-4] != null` -> true.

Visual:
              slow     fast
                v        v
     [3] ---> [2] ---> [0] ---> [-4]
               ^                   |
               +-------------------+

--- PHASE 1: ITERATION 2 ---
- Updated:
  `slow` moves +1 -> [0]
  `fast` moves +2 -> [0] -> [-4] -> [2] (wraps around to [2])
- Tested:
  `slow == fast` -> is [0] == [2]? NO (false).
  Next loop check: `fast != null && fast.next != null` -> `[2] != null && [0] != null` -> true.

Visual:
              fast     slow
                v        v
     [3] ---> [2] ---> [0] ---> [-4]
               ^                   |
               +-------------------+

--- PHASE 1: ITERATION 3 ---
- Updated:
  `slow` moves +1 -> [-4]
  `fast` moves +2 -> [2] -> [0] -> [-4] (now at [-4])
- Tested:
  `slow == fast` -> is [-4] == [-4]? YES (true)!
  Sets `isCycle = true` and executes `break` to exit Phase 1.

Visual:
                            slow, fast
                                v
     [3] ---> [2] ---> [0] ---> [-4]
               ^                   |
               +-------------------+

--- BETWEEN PHASE 1 AND PHASE 2 ---
- Tested:
  `!isCycle` -> `!true` -> false (do not return `null`).
- Updated:
  `slow = head` -> `slow` resets to [3].
  `fast` stays at [-4] (the collision node).

Visual:
  slow                      fast
    v                         v
   [3] ---> [2] ---> [0] ---> [-4]
             ^                   |
             +-------------------+

--- PHASE 2: ITERATION 1 ---
- Tested before moving:
  `slow != fast` -> is [3] != [-4]? YES (true, enter loop).
- Updated (both move +1):
  `slow` moves +1 -> [3] to [2]
  `fast` moves +1 -> [-4] to [2] (wraps around loop to [2])
- Tested after moving:
  `slow != fast` -> is [2] != [2]? NO (false) -> loop ends!

Visual:
              slow, fast
                  v
     [3] ---> [2] ---> [0] ---> [-4]
               ^                   |
               +-------------------+

--- AFTER PHASE 2 ---
- Returned value: `slow` -> Node [2] (the exact start of the cycle).


================================================================================
PART 4: TIME & SPACE COMPLEXITY
================================================================================

VARIABLES DEFINED:
- n = total number of nodes in the linked list.
- L = number of nodes from `head` to the start of the cycle.
- c = number of nodes inside the cycle.

TIME COMPLEXITY: O(n)
- Plain English explanation:
  - In Phase 1, if there is no cycle, `fast` reaches the end in about n / 2
    steps. If there is a cycle, `fast` catches up to `slow` inside the loop in a
    number of steps bounded by n. That gives O(n).
  - In Phase 2, `slow` walks L steps from `head` to the cycle start while `fast`
    walks the matching remaining distance around the loop. Since L is at most n,
    Phase 2 also takes O(n) time.
  - Adding two O(n) phases still gives O(n) overall.

SPACE COMPLEXITY: O(1)
- Plain English explanation:
  - We only use a few fixed variables (`slow`, `fast`, and `isCycle`). We do not
    allocate a hash set, list, or extra nodes regardless of input size, so extra
    space is O(1).


================================================================================
PART 5: MOST PROBABLE INTERVIEW QUESTIONS
================================================================================

Q1: Why does moving `slow` from `head` and `fast` from the meeting point at 1
    step each land on the cycle start?
A1: "Because the distance from `head` to the cycle start equals the remaining
    distance from the meeting point around the cycle back to the cycle start
    (plus full loops around the cycle). Moving both 1 step at a time keeps them
    equidistant from the entry point, so they meet right at the start of the
    loop."

Q2: Why do we advance `fast` by 1 step in Phase 2 instead of 2 steps like in
    Phase 1?
A2: "In Phase 2, both pointers need to stay in sync so they arrive at the entry
    node at the exact same time. If `fast` kept moving by 2 steps, it could jump
    over or past the entry node instead of meeting `slow` right on it."

Q3: How would you solve this if you were allowed O(n) extra space, and why
    use two pointers instead?
A3: "With O(n) extra space, I could store visited `ListNode` references in a
    `HashSet` and return the first node I see twice. Two pointers is better in
    interviews because it uses O(1) space while keeping O(n) time."
================================================================================
*/
