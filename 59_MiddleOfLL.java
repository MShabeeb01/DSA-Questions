class Solution {
    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while(fast!=null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        } 
        return slow;
        
    }
}

/*
================================================================================
PART 2: SUMMARY OF THE APPROACH & LINE-BY-LINE EXPLANATION
================================================================================

Core Approach Summary:
----------------------
This solution uses the **Fast and Slow Pointers** pattern (also known as the
Tortoise and Hare technique) on a singly-linked list.

Think of two runners starting on the same track at `head`:
- `slow` walks at normal speed (1 node per step).
- `fast` jogs at double speed (2 nodes per step).

Because `fast` moves twice as fast as `slow`, by the time `fast` reaches the
finish line (the end of the list), `slow` has covered half the distance—landing
right on the middle node in a single pass. If there are two middle nodes (in an
even-length list), this approach naturally lands on the **second** middle node.

--------------------------------------------------------------------------------
Line-by-Line Explanation:
--------------------------------------------------------------------------------

Line: class Solution {
What it does:
  - Defines the `Solution` class that wraps our method.

--------------------------------------------------------------------------------

Line: public ListNode middleNode(ListNode head) {
What it does:
  - Defines the main method that takes the starting node (`head`) of a linked
    list and returns the `ListNode` at the middle of the list.

--------------------------------------------------------------------------------

Line: ListNode slow = head;
What it does:
  - Creates our "slow" pointer and starts it at the very first node (`head`).
  - It will advance by 1 node on each loop iteration.

--------------------------------------------------------------------------------

Line: ListNode fast = head;
What it does:
  - Creates our "fast" pointer and also starts it at `head`.
  - It will advance by 2 nodes on each loop iteration so it races ahead to find
    the end of the list.

--------------------------------------------------------------------------------

Line: while(fast!=null && fast.next != null){
What it does:
  - Keeps the loop running as long as `fast` can safely jump two steps forward.
  - `fast != null` prevents a `NullPointerException` when `fast` lands right on
    `null` (which happens at the end of even-length lists).
  - `fast.next != null` prevents a `NullPointerException` when `fast` is on the
    last node and tries to read `fast.next.next` (which happens at the end of
    odd-length lists).

--------------------------------------------------------------------------------

Line: slow = slow.next;
What it does:
  - Moves `slow` forward by exactly 1 node.

--------------------------------------------------------------------------------

Line: fast = fast.next.next;
What it does:
  - Moves `fast` forward by 2 nodes (`fast.next` then `.next` of that).

--------------------------------------------------------------------------------

Line: return slow;
What it does:
  - Once the `while` loop stops, `slow` is at the middle node, so we return it.
  - Returning `slow` returns that node AND the rest of the list attached after
    it, which matches the problem's expected return value.

================================================================================
PART 3: STEP-BY-STEP ITERATION WITH VISUAL REPRESENTATION
================================================================================

Example Walkthrough: Odd-Length List [1 -> 2 -> 3 -> 4 -> 5 -> null]
--------------------------------------------------------------------
Legend:
  S = slow pointer
  F = fast pointer

Note on additions/deletions:
  This pointer traversal does not add or delete list nodes, and it uses no
  extra list or stack—only pointer references (`slow` and `fast`) are updated
  each step.

Start (Before Loop):
  Pointers initialized: slow -> Node(1), fast -> Node(1)
  Updated state: slow = Node(1), fast = Node(1)

  [1] -> [2] -> [3] -> [4] -> [5] -> null
   S
   F

  Check condition: fast != null (Node 1 != null -> true) AND
                   fast.next != null (Node 2 != null -> true)
  Result: true -> Enter Iteration 1.

--------------------------------------------------------------------------------

Iteration 1:
  Action:
    - Move `slow` 1 step:  Node(1) -> Node(2)
    - Move `fast` 2 steps: Node(1) -> Node(2) -> Node(3)
  Updated state: slow = Node(2), fast = Node(3)

  [1] -> [2] -> [3] -> [4] -> [5] -> null
          S      F

  Check condition: fast != null (Node 3 != null -> true) AND
                   fast.next != null (Node 4 != null -> true)
  Result: true -> Enter Iteration 2.

--------------------------------------------------------------------------------

Iteration 2:
  Action:
    - Move `slow` 1 step:  Node(2) -> Node(3)
    - Move `fast` 2 steps: Node(3) -> Node(4) -> Node(5)
  Updated state: slow = Node(3), fast = Node(5)

  [1] -> [2] -> [3] -> [4] -> [5] -> null
                 S             F

  Check condition: fast != null (Node 5 != null -> true) AND
                   fast.next != null (null != null -> false)
  Result: false -> Exit loop!

--------------------------------------------------------------------------------

End of Loop:
  - Returns `slow`, which is Node(3).
  - Output list starting from Node(3): 3 -> 4 -> 5.

================================================================================

Bonus Even-Length Check: [1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null]
-------------------------------------------------------------
- Start: S at 1, F at 1 -> loop runs
- Iter 1: S at 2, F at 3 -> loop runs
- Iter 2: S at 3, F at 5 -> F.next is Node(6) (not null), so loop runs!
- Iter 3: S moves to Node(4), F moves to Node(6).next = null -> loop stops
  because `fast != null` is false.
- Returns `slow` = Node(4) (the second of the two middle nodes, 3 and 4).

================================================================================
PART 4: TIME & SPACE COMPLEXITY
================================================================================

Time Complexity: O(n)
  - Variable definition: `n` is the total number of nodes in the linked list.
  - Plain-English explanation: `fast` jumps forward by 2 nodes each step, so it
    takes roughly `n / 2` steps to reach the end of the list. In Big-O notation,
    we drop constant factors like `1/2`, giving linear time `O(n)`. We only
    scan the list once.

Space Complexity: O(1)
  - Variable definition: `n` is the total number of nodes in the linked list.
  - Plain-English explanation: We only create two pointer variables (`slow` and
    `fast`) and do not build any new arrays, lists, or hash maps. Whether the
    list has 5 nodes or 5 million nodes, the extra memory used stays constant
    at `O(1)`.

================================================================================
PART 5: MOST PROBABLE INTERVIEW QUESTIONS
================================================================================

Q1: Why do we check both `fast != null` and `fast.next != null` in the `while`
    loop condition, and does their order matter?
Answer:
  Yes, the order matters! We check `fast != null` first so we never call
  `.next` on a `null` reference (which would crash with a
  `NullPointerException`). In an even-length list, `fast` becomes `null` after
  the last jump, so `fast != null` catches it. In an odd-length list, `fast`
  lands on the final node where `fast.next` is `null`, so `fast.next != null`
  stops the loop before `fast.next.next` tries to read past `null`.

Q2: For an even-length list like `[1 -> 2 -> 3 -> 4]`, why does this return
    `3` instead of `2`? And how would you return the first middle (`2`) instead?
Answer:
  Because `fast` starts at `head` and moves 2 steps per iteration, `slow` lands
  on the second middle node (`3`) when `fast` hits `null`. To return the first
  middle node (`2`) instead, you can start `fast` at `head.next` (while
  keeping `slow` at `head`), or adjust the loop stop condition to
  `fast.next != null && fast.next.next != null`.

Q3: Can we solve this without two pointers, and why is two pointers better?
Answer:
  Yes—you could do a two-pass approach: first count the total length `count`
  of the list, then walk `count / 2` steps from `head` in a second pass. Both
  use `O(1)` extra space, but the fast-and-slow pointer approach only needs a
  single pass through the list, which is cleaner and avoids re-traversing
  nodes.
================================================================================
*/
