public class PalindromeCheck {

    // Function to check if a string is palindrome
    public static boolean isPalindrome(String str) {
        int n = str.length();  // Length of the string

        // Loop only till half the string (i < n/2)
        // because checking from both ends simultaneously
        // covers the whole string.
        for (int i = 0; i < n / 2; i++) {

            // str.charAt(i) → character from left
            // str.charAt(n-1-i) → character from right
            // Example: str = "racecar", n=7
            // i=0 → compare str[0]='r' with str[6]='r'
            // i=1 → compare str[1]='a' with str[5]='a'
            // i=2 → compare str[2]='c' with str[4]='c'
            // i=3 → compare str[3]='e' with str[3]='e'
            //
            // The formula (n-1-i) gives the "mirror index" from the right side.
            if (str.charAt(i) != str.charAt(n - 1 - i)) {
                // If mismatch is found → not a palindrome
                return false;
            }
        }

        // If loop finishes without mismatches → string is palindrome
        return true;
    }

    public static void main(String[] args) {
        // Test Case 1: Palindrome
        String str1 = "racecar";
        System.out.println(str1 + " → " + isPalindrome(str1)); // true

        // Test Case 2: Palindrome
        String str2 = "madam";
        System.out.println(str2 + " → " + isPalindrome(str2)); // true

        // Test Case 3: Not a Palindrome
        String str3 = "hello";
        System.out.println(str3 + " → " + isPalindrome(str3)); // false
    }
}
