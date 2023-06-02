/**
 * A library of string functions.
 */
public class MyString {
   public static void main(String args[]) {
       System.out.println(MyString.subsetOf("sap","space"));
       System.out.println(MyString.subsetOf("spa","space"));
       System.out.println(MyString.subsetOf("pass","space"));
       System.out.println(MyString.subsetOf("c","space"));
       System.out.println("..." + MyString.spacedString("foobar") + "...");
       System.out.println(MyString.randomStringOfLetters(3));

    }

    /**
     * Returns the number of times the given character appears in the given string+.
     *
     * @param str - a string
     * @param c   - a character
     * @return the number of times c appears in str
     */
    public static int countChar(String str, char c) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Returns true if str1 is a subset string str2, false otherwise.
     * For example, "spa" is a subset of "space", and "pass" is not
     * a subset of "space".
     *
     * @param str1 - a string
     * @param str2 - a string
     * @return true is str1 is a subset of str2, false otherwise
     */
    public static boolean subsetOf(String str1, String str2) {
        if (str1.length() > str2.length()) {
            return false;
        }
        for (int i = 0; i < str1.length(); i++) {
            char check = str1.charAt(i);
            if ((countChar(str1, check)) > (countChar(str2 , check))) {
                return false;
        }

        }
        return true;
    }

    /**
     * Returns a string which is the same as the given string, with a space
     * character inserted after each character in the given string, except
     * for last character of the string, that has no space after it.
     * Example: if str is "silent", returns "s i l e n t".
     *
     * @param str - a string
     * @return a string consisting of the characters of str, separated by spaces.
     */
    public static String spacedString(String str) {
        if (str.length() == 0) {
            return null;
        }
        String withSpace = "";
        for (int i = 0; i < (str.length() - 1); i++) {
            withSpace += str.charAt(i);
            withSpace += " ";
        }
        withSpace += str.charAt(str.length() - 1);
        return withSpace;
    }

    /**
     * Returns a string of n lowercase letters, selected randomly from
     * the English alphabet 'a', 'b', 'c', ..., 'z'. Note that the same
     * letter can be selected more than once.
     *
     * @param n - the number of letter to select
     * @return a randomly generated string, consisting of 'n' lowercase letters
     */
    public static String randomStringOfLetters(int n) {
        if (n <= 0) {
            return null;
        }
        String randomString = "";
        for (int i = 0; i < n; i++) {
            int randomASCIIvalue = (int) (Math.random() * 26 + 97);
            char newChar = (char) randomASCIIvalue;
            randomString += newChar;
        }
        return randomString;
    }

    /**
     * Returns a string consisting of the string str1, minus all the characters in the
     * string str2. Assumes (without checking) that str2 is a subset of str1.
     * Example: "committee" minus "meet" returns "comit".
     *
     * @param str1 - a string
     * @param str2 - a string
     * @return a string consisting of str1 minus all the characters of str2
     */
    public static String remove(String str1, String str2) {
        if (str2.length() == 0) {
            return str1;
        }
        String str1MinusStr2 = str1;
        for (int i = 0; i < str2.length(); i++) {
            boolean a = true;
            for (int j = 0; a && j < str1MinusStr2.length(); j++) {
                if (str2.charAt(i) == str1MinusStr2.charAt(j)) {
                    a = false;
                    if (j < str1MinusStr2.length() - 1) {
                        str1MinusStr2 = str1MinusStr2.substring(0, j) + str1MinusStr2.substring(j + 1);
                    } else {
                        str1MinusStr2 = str1MinusStr2.substring(0, j);
                    }
                }

            }
        }
        return str1MinusStr2;
    }

}