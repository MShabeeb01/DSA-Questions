public class ShortestPath {

    // Function to calculate shortest path distance
    public static float getShortestPath(String path) {
        int x = 0, y = 0; // Start at origin (0,0)

        // Traverse each character in the path string
        for (int i = 0; i < path.length(); i++) {
            char dir = path.charAt(i); // Current direction

            // Move according to direction
            if (dir == 'S') {
                y--;  // South → decrease y
            } else if (dir == 'N') {
                y++;  // North → increase y
            } else if (dir == 'W') {
                x--;  // West → decrease x
            } else if (dir == 'E') {
                x++;  // East → increase x
            }
        }

        // After final coordinates (x,y), 
        // use distance formula from origin (0,0):
        // distance = √(x² + y²)
        return (float) Math.sqrt((x * x) + (y * y));
    }

    public static void main(String[] args) {
        String path = "WNEENESENNN";  // Example path
        System.out.println("Shortest path distance: " + getShortestPath(path));
    }
}
