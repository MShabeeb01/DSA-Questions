class Solution {
    // Operation: Merge Two Sorted Lists (LeetCode 21 - Recursive Approach)
    public ListNode mergeTwoLists(ListNode head1, ListNode head2) {
        // Base Case: If either list runs out, return the non-empty list
        if (head1 == null || head2 == null) {
            return head1 == null ? head2 : head1;
        }

        // Case 1: head1 has smaller or equal value
        if (head1.val <= head2.val) {
            head1.next = mergeTwoLists(head1.next, head2); // Attach merged rest after head1
            return head1;
        } 
        // Case 2: head2 has smaller value
        else {
            head2.next = mergeTwoLists(head1, head2.next); // Attach merged rest after head2
            return head2;
        }
    }
}

/*
================================================================================
PART 2: SUMMARY OF THE APPROACH & LINE-BY-LINE EXPLANATION
================================================================================

SUMMARY OF THE APPROACH:
This solution uses recursion to merge two already-sorted singly-linked lists
into one sorted list.

At each step, we compare the current front node of list 1 (`head1`) and the
current front node of list 2 (`head2`). Whichever node has the smaller value
becomes the next node in our merged list, and we ask recursion to merge the
remaining nodes and hook them onto that winner's `.next` pointer.

Once one list runs out (`null`), we do not need any more comparisons—we simply
return the rest of the other list, and as the recursive calls unwind back up,
every `.next` link gets connected in sorted order.

LINE-BY-LINE EXPLANATION:

Line: class Solution {
What it does: Declares the `Solution` class required by LeetCode to run our
merge method.
--------------------------------------------------------------------------------

Line: public ListNode mergeTwoLists(ListNode head1, ListNode head2) {
What it does: Defines the recursive method that takes the heads of two sorted
linked lists (`head1` and `head2`) and returns the head of the merged sorted
list.
--------------------------------------------------------------------------------

Line: if (head1 == null || head2 == null) {
What it does: Checks the base case—whether either list has reached the end
(`null`) or was empty to begin with.
--------------------------------------------------------------------------------

Line: return head1 == null ? head2 : head1;
What it does: If `head1` is `null`, returns whatever is left of `head2` (even
if `head2` is also `null`); otherwise returns whatever is left of `head1`.
--------------------------------------------------------------------------------

Line: if (head1.val <= head2.val) {
What it does: Compares the values at the front of both lists to see if `head1`
is smaller than or equal to `head2`. Using `<=` keeps ties stable in favor of
`head1`.
--------------------------------------------------------------------------------

Line: head1.next = mergeTwoLists(head1.next, head2);
What it does: Recursively merges the rest of list 1 (`head1.next`) with all of
list 2 (`head2`), and attaches the resulting sorted chain to `head1.next`.
--------------------------------------------------------------------------------

Line: return head1;
What it does: Returns `head1` as the head of this merged sub-list back to the
previous caller.
--------------------------------------------------------------------------------

Line: } else {
What it does: Handles the opposite case where `head2.val` is strictly smaller
than `head1.val`.
--------------------------------------------------------------------------------

Line: head2.next = mergeTwoLists(head1, head2.next);
What it does: Recursively merges all of list 1 (`head1`) with the rest of list
2 (`head2.next`), and attaches the resulting sorted chain to `head2.next`.
--------------------------------------------------------------------------------

Line: return head2;
What it does: Returns `head2` as the head of this merged sub-list back to the
previous caller.
--------------------------------------------------------------------------------


================================================================================
PART 3: STEP-BY-STEP ITERATION WITH VISUAL REPRESENTATION
================================================================================

EXAMPLE INPUT:
head1 = [1 -> 3 -> null]
head2 = [2 -> 4 -> null]

Note on additions/deletions:
We are not removing elements from a list/stack or deleting nodes from memory;
we are rewiring `.next` pointers on existing nodes as recursive calls return.
Below, each call shows what is tested/chosen going down, and what `.next` link
is attached/updated coming back up.

--- CALL 1: mergeTwoLists([1 -> 3], [2 -> 4]) ---
- Tested:
  Base case (`head1 == null || head2 == null`) -> false.
  Comparison (`1 <= 2`) -> true (`head1` wins).
- Chosen / Added to merge chain:
  Picks node [1].
- Next step:
  Calls `mergeTwoLists([3], [2 -> 4])` to find what goes after [1].

--- CALL 2: mergeTwoLists([3], [2 -> 4]) ---
- Tested:
  Base case -> false.
  Comparison (`3 <= 2`) -> false (`head2` wins).
- Chosen / Added to merge chain:
  Picks node [2].
- Next step:
  Calls `mergeTwoLists([3], [4])` to find what goes after [2].

--- CALL 3: mergeTwoLists([3], [4]) ---
- Tested:
  Base case -> false.
  Comparison (`3 <= 4`) -> true (`head1` wins).
- Chosen / Added to merge chain:
  Picks node [3].
- Next step:
  Calls `mergeTwoLists(null, [4])` to find what goes after [3].

--- CALL 4 (BASE CASE): mergeTwoLists(null, [4]) ---
- Tested:
  Base case (`head1 == null || head2 == null`) -> true (`head1` is null).
- Returned:
  Returns [4 -> null] immediately without making deeper calls.

--- UNWINDING CALL 3 (Rewiring [3].next) ---
- Attached / Updated:
  `head1.next` ([3].next) is set to [4 -> null].
- Chain at this point:
  [3] ---> [4] ---> null
- Returned:
  [3 -> 4 -> null].

--- UNWINDING CALL 2 (Rewiring [2].next) ---
- Attached / Updated:
  `head2.next` ([2].next) is set to [3 -> 4 -> null].
- Chain at this point:
  [2] ---> [3] ---> [4] ---> null
- Returned:
  [2 -> 3 -> 4 -> null].

--- UNWINDING CALL 1 (Rewiring [1].next) ---
- Attached / Updated:
  `head1.next` ([1].next) is set to [2 -> 3 -> 4 -> null].
- Final merged list:
  [1] ---> [2] ---> [3] ---> [4] ---> null
- Returned:
  [1 -> 2 -> 3 -> 4 -> null].


================================================================================
PART 4: TIME & SPACE COMPLEXITY
================================================================================

VARIABLES DEFINED:
- n = number of nodes in the first linked list (`head1`).
- m = number of nodes in the second linked list (`head2`).

TIME COMPLEXITY: O(n + m)
- Plain English explanation:
  - Each recursive call advances either `head1` or `head2` forward by one node
    until one of the lists becomes `null`. Since there are `n + m` nodes in
    total across both lists, we make at most `n + m + 1` calls, doing constant
    work per call. That gives O(n + m).

SPACE COMPLEXITY: O(n + m)
- Plain English explanation:
  - Every recursive call adds a frame onto the call stack until the base case
    is reached. In the worst case, we step through all `n + m` nodes before
    hitting `null`, so the call stack grows up to O(n + m) frames. (We do not
    allocate new `ListNode` objects for the data—we reuse existing nodes—but
    the recursion stack itself takes O(n + m) space.)


================================================================================
PART 5: MOST PROBABLE INTERVIEW QUESTIONS
================================================================================

Q1: What is the trade-off between this recursive approach and an iterative
    approach using a dummy node?
A1: "Both run in O(n + m) time, but the iterative approach uses O(1) space
    instead of O(n + m) call stack space. However, recursion is very clean and
    readable, though for a super long list it risks a `StackOverflowError`."

Q2: What happens if both input lists are empty (`null` and `null`)?
A2: "The base case triggers right away (`head1 == null`), and
    `head1 == null ? head2 : head1` evaluates to `head2`, which is `null`. So
    it safely returns `null`."

Q3: Could we write this without `else` after `if (head1.val <= head2.val)`?
A3: "Yes—because the `if` branch ends with `return head1;`, execution only
    reaches the next line when `head1.val > head2.val`. Keeping the `else`
    makes the two choices extra clear to read at a glance."
================================================================================
*/
