import java.util.LinkedList;
import java.util.Queue;

class Solution { // LeetCode 785: Is Graph Bipartite? (BFS)

    private boolean checkBipartiteBFS(int startNode, int[][] graph, int[] color) {
        Queue<Integer> queue = new LinkedList<>();
        
        queue.offer(startNode);
        color[startNode] = 0; // Color start node with 0

        while (!queue.isEmpty()) {
            int curr = queue.poll();

            for (int neighbor : graph[curr]) {
                // If neighbor is uncolored, assign opposite color and enqueue
                if (color[neighbor] == -1) {
                    color[neighbor] = 1 - color[curr]; // Toggle between 0 and 1
                    queue.offer(neighbor);
                } 
                // If neighbor shares the same color, graph is not bipartite
                else if (color[neighbor] == color[curr]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        
        // Initialize all nodes as unvisited (-1)
        for (int i = 0; i < n; i++) {
            color[i] = -1;
        }

        // Check every component (handles disconnected graphs)
        for (int i = 0; i < n; i++) {
            if (color[i] == -1) {
                if (!checkBipartiteBFS(i, graph, color)) {
                    return false;
                }
            }
        }
        return true;
    }
}

/*
==================== SUMMARY (BFS) ====================

Approach:
1. Represent two partitions using two colors: `0` and `1`. Initialize all nodes with `-1` (uncolored).
2. Loop through all nodes $0$ to $n - 1$ to handle disconnected components.
3. For each unvisited node, assign color `0` and start standard BFS using a queue.
4. For every adjacent neighbor:
   - If uncolored (`-1`): Color with opposite color `(1 - currColor)` and push to queue.
   - If already colored: Verify `color[neighbor] != color[curr]`. If equal, return `false`.
5. If queue exhausts with no color collision, the component is valid bipartite.

-------------------------------------------------

Step-by-Step BFS Trace

Input Graph:
0 -- 1
|    |
3 -- 2
graph = [[1, 3], [0, 2], [1, 3], [0, 2]]

-----------------------------------------------------------------------------------------
Step | Queue | Popped (curr) | Color Array [0, 1, 2, 3] | Neighbor | Action / Validation
-----------------------------------------------------------------------------------------
1    | [0]   | -             | [0, -1, -1, -1]          | -        | Start node 0 colored 0
2    | []    | 0             | [0, -1, -1, -1]          | 1        | Color 1 -> 1, offer(1)
3    | [1]   | 0             | [0,  1, -1, -1]          | 3        | Color 3 -> 1, offer(3)
4    | [3]   | 1             | [0,  1, -1,  1]          | 0        | color[0] == 0 (Valid)
5    | [3]   | 1             | [0,  1, -1,  1]          | 2        | Color 2 -> 0, offer(2)
6    | [2]   | 3             | [0,  1,  0,  1]          | 0, 2     | color[0]==0, color[2]==0 (Valid)
7    | []    | 2             | [0,  1,  0,  1]          | 1, 3     | color[1]==1, color[3]==1 (Valid)
-----------------------------------------------------------------------------------------
Queue empty -> No conflicts -> Result: true

Complexity:
- Time Complexity : O(V + E) — Each node and edge is visited once.
- Space Complexity: O(V) — For color array and queue.

=================================================
*/







class Solution { // LeetCode 785: Is Graph Bipartite? (DFS)

    private boolean checkBipartiteDFS(int curr, int currColor, int[][] graph, int[] color) {
        color[curr] = currColor; // Assign color to current node

        for (int neighbor : graph[curr]) {
            // If neighbor is uncolored, recurse with opposite color
            if (color[neighbor] == -1) {
                if (!checkBipartiteDFS(neighbor, 1 - currColor, graph, color)) {
                    return false;
                }
            } 
            // If neighbor has the same color, an odd-length cycle is detected
            else if (color[neighbor] == currColor) {
                return false;
            }
        }
        return true;
    }

    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];

        // Initialize all nodes as unvisited (-1)
        for (int i = 0; i < n; i++) {
            color[i] = -1;
        }

        // Check every component (handles disconnected graphs)
        for (int i = 0; i < n; i++) {
            if (color[i] == -1) {
                if (!checkBipartiteDFS(i, 0, graph, color)) {
                    return false;
                }
            }
        }
        return true;
    }
}

/*
==================== SUMMARY (DFS) ====================

Approach:
1. Initialize a `color` array with `-1`.
2. Run an outer loop over all nodes to cover disconnected components.
3. For any unvisited node, kick off recursive DFS with `currColor = 0`.
4. At each node:
   - Color the current node.
   - For every neighbor:
     - If unvisited (`-1`), recurse with flipped color `1 - currColor`. If recursive call returns `false`, propagate `false`.
     - If already visited and `color[neighbor] == currColor`, an odd cycle exists -> return `false`.
5. If DFS stack unwinds without conflicts, return `true`.

-------------------------------------------------

DFS Recursion Tree Breakdown

Input: graph = [[1, 2, 3], [0, 2], [0, 1, 3], [0, 2]] (Triangle between 0, 1, 2)

                DFS(Node 0, Color 0)
               /         \          \
              /           \          \
    DFS(Node 1, Color 1)  (Node 2)  (Node 3)
           |
      Neighbors: 0, 2
      - 0: color is 0 (Valid)
      - 2: uncolored -> DFS(Node 2, Color 0)
                          |
                     Neighbors: 0, 1, 3
                     - 0: color is 0 (CONFLICT: color[2] == color[0] == 0)
                     -> Return FALSE (Odd Cycle Detected)

-------------------------------------------------

Complexity:
- Time Complexity : O(V + E) — Visits each vertex and explores every incident edge once.
- Space Complexity: O(V) — Call stack depth up to V in the worst case + O(V) color array.

=================================================
*/
