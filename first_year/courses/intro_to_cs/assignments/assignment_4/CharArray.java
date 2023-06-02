
/** A library of functions that operate on arrays of characters.
 *  The function signatures are similar to the signatures of 
 *  equivalent String methods. 
 *  In this class documentation, we use the term "char array" to refer to 
 *  an array of char values. We also use the notation [R2-D2] to refer to
 *  the char array consisting of the characters 'R','2','-','D', and '2'
 *  (This is just one example of a possible char array).   
*/
public class CharArray {

	public static void main(String[] args) {
		char[] str = neww("Hello World");    // Equivalent to: String str = "Hello World"
		//System.out.println(indexOf(new char[]{'k', 'a', 'u', 'f', 'm', 'a', 'n'}, new char[]{'u'}));   // Equivalent to: System.out.println(str)
		System.out.println(length(str));     // Equivalent to: System.out.println(str.length())
		//System.out.println(charAt(str,12));   // Equivalent to: System.out.println(str.charAt(6))
		char[] s1 = neww("ox");              // Equivalent to: String s1 = "ox"
		char[] s2 = neww("ford");            // Equivalent to: String s2 = "ford"
		char[] s3 = concat(s1, s2);           // Equivalent to: s3 = s1 + s2
		System.out.println(toString(s3));    // Equivalent to: System.out.println(s3);
		char[] s4 = replace(s3, 'o', 'O');     // Equivalent to: String s4 = s3.replace('o','O')
		System.out.println(toString(s4));    // Equivalent to: System.out.println(s3);
		System.out.println(substring(new char[]{'0', '1', '2', '3', '4', '5', '6', '7'}, 2, 6));
		System.out.println(indexOf(new char[]{'A', 'V', 'I', 'K', 'A', 'U', 'F',
				'M', 'A', 'N'}, new char[]{'Z'}));
		// etc.
	}

	/**
	 * Returns a char array whose elements are the char values of the given
	 * string. For example, neww("R2-D2") returns the char array [R2-D2].
	 *
	 * @param s - the given string
	 * @return the new char array
	 */
	public static char[] neww(String s) {
		char[] result = new char[s.length()];
		for (int i = 0; i < s.length(); i++) {
			result[i] = s.charAt(i);
		}
		return result;
	}

	/**
	 * Returns a String representation of arr.
	 *
	 * @param arr - char array
	 * @return - the resulting String
	 */
	public static String toString(char[] arr) {
		String result = "";
		for (int i = 0; i < arr.length; i++) {
			result = result + arr[i];
		}
		return result;
	}

	/**
	 * Returns the length (number of elements) in the given char array.
	 * For example, if s is the char array [IDC], length(s) returns 3.
	 *
	 * @param arr - char array
	 * @return number of elements in arr
	 */
	public static int length(char[] arr) {
		int result = 0;
		for (int i = 0; i < arr.length; i++) {
			result++;
		}
		return result;
	}

	/**
	 * Returns the char value at the specified index of arr.
	 * The first char value is at index 0, the next char value is at index 1,
	 * and the last char value if at index (array's length - 1).
	 * If the index is invalid, returns '0'.
	 *
	 * @param arr   - char array
	 * @param index - nonnegative int between 0 and (arr's length - 1)
	 * @return the char value at the specified index***************************************************************
	 */
	public static char charAt(char[] arr, int index) {
		char c = '0';
		if (index < arr.length && index >= 0) {
			c = arr[index];
		}
		return c;
	}

	/**************************************************************************************************************
	 * Returns a char array that is the result of concatenating the
	 * char arrays a and b. For example, if a and b are the char arrays
	 * [R2-] and [D2], concat(a,b) returns the char array [R2-D2].
	 * @param a - the first char array
	 * @param b - the second char array
	 * @return concatenated char array
	 */
	public static char[] concat(char[] a, char[] b) {
		char[] result = new char[a.length + b.length];
		for (int i = 0; i < a.length; i++) {
			result[i] = a[i];
		}
		for (int i = 0; i < b.length; i++) {
			result[a.length + i] = b[i];
		}

		return result;
	}

	/**
	 * Returns a new char array in which every occurrence of char c in arr
	 * is replaced with the char replace.
	 * If c does not appear in arr, returns a copy of arr.
	 * For example, if s is the char array [character], replace(s,'a','k')
	 * returns the char array [kharakter].
	 *
	 * @param arr     - char array
	 * @param c       - character to replace
	 * @param replace - character to replace with
	 * @return the resulting array
	 */
	public static char[] replace(char[] arr, char c, char replace) {
		char result[] = new char[arr.length];
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == c) {
				result[i] = replace;
			} else {
				result[i] = arr[i];
			}
		}
		return result;
	}

	/**
	 * Returns a char array consisting of all the characters in arr,
	 * starting at the given index. For example, if s is the char
	 * array [unhappy], substring(s,2) returns the char array [happy].
	 * If the index in invalid, returns null.
	 *
	 * @param arr   - char array
	 * @param index - index to start from
	 * @return the resulting substring (char array)
	 */
	public static char[] substring(char[] arr, int index) {
		if (index >= arr.length) {
			return null;
		}
		char[] result = new char[arr.length - index];
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i + index];
		}
		return result;
	}

	/**
	 * Returns a char array consisting of all the characters in arr,
	 * starting at index begin and ending at index end - 1.
	 * For example, if s is the char array [unhappy], substring(s,2,5)
	 * returns the char array [hap].
	 * If begin or end are invalid, or if begin >= end, returns null.
	 *
	 * @param arr   - char array
	 * @param begin - index to start the substring
	 * @param end   - index to end substring, + 1
	 * @return - resulting char array
	 */
	public static char[] substring(char[] arr, int begin, int end) {
		if (begin < 0 || end >= arr.length || begin >= end) {
			return null;
		}
		char[] result = new char[end - begin];
		for (int i = 0; i < end - begin; i++) {
			result[i] = arr[i + begin];
		}
		return result;
	}

	/**
	 * Compares two char arrays, lexicographically.
	 * The comparison is based on the ASCII values of corresponsing characters in the two arrays.
	 *
	 * @param a - char array
	 * @param b - char array
	 * @return 1  if array a is lexicographically greater than array b,
	 * -1 if array a is lexicographically less than than array b, and
	 * 0  if the two arrays have the same values.
	 */
	public static int compareTo(char[] a, char[] b) {
		for (int i = 0; i < a.length && i < b.length; i++) {
			int charOfa = (int) a[i];
			int charOfb = (int) b[i];
			if (charOfa > charOfb) {
				return 1;
			} else {
				if (charOfb > charOfa) {
					return -1;
				}
			}
		}
		if (a.length == b.length) {
			return 0;
		} else {
			if (a.length > b.length) {
				return 1;
			} else {
				return -1;
			}
		}

	}

	/**
	 * Returns a new char array in which all the uppercase letter characters
	 * in arr are converted to lowercase letters. Has no impact on non-letter
	 * characters. For example, 'A*(B-1)' becomes 'a*(b-1)'.
	 *
	 * @param arr - char array
	 * @return the resulting char array
	 */
	public static char[] toLowerCase(char[] arr) {
		char[] result = new char[arr.length];
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] >= 'A' && arr[i] <= 'Z') {
				int c = (int) (arr[i]) + 32;
				result[i] = (char) c;

			} else {
				result[i] = arr[i];
			}

		}
		return result;
	}

	/**
	 * Same as the compareTo function, ignoring letter casing.
	 * For example, the char arrays 'iDC' and 'IdC' are considered equal.
	 *
	 * @param a - char array
	 * @param b - char array
	 * @return 1  if array a is lexicographically greater than array b,
	 * -1 if array a is lexicographically less than than array b, and
	 * 0  if the two arrays have the same values.
	 */
	public static int compareToIgnoreCase(char[] a, char[] b) {
		char[] newA = a;
		char[] newB = b;
		newA = toLowerCase(newA);
		newB = toLowerCase(newB);

		return compareTo(newA, newB);

	}

	/**
	 * Returns the index of the first element in arr containing the character c.
	 * if none is found, returns -1. For example, if s is the char array [Abstraction],
	 * then indexOf(s,'A') returns 0, indexOf(s,'a') returns 5, indexOf(s,'t') returns 3,
	 * and indexOf(s,'e') returns -1.
	 *
	 * @param arr - char array
	 * @param c   - char to search
	 * @return the index of c in arr
	 */
	public static int indexOf(char[] arr, char c) {
		if (arr.length == 0) {
			return -1;
		}
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == c) {
				return i;
			}
		}
		return -1;
	}

	/***************************************************************************************
	 * If the char array sub (for "substring") appears in the char array arr
	 * starting at index i, returns i. Otherwise, returns -1.
	 * For example, if arr is [nonsense] and sub is [se], indexOf(arr,sub) returns 3.
	 * @param arr - char array
	 * @param sub - the substring to search in arr (also a char array)
	 * @return the index within arr of the first element of the first
	 *         occurrence of the specified substring, 
	 *         or -1 if no such substring exists.
	 */
	public static int indexOf(char[] arr, char[] sub) {
		if (arr.length == 0 || sub.length == 0) {
			return -1;
		}
		for (int i = 0; i < arr.length; i++) {
			if (sub[0] == arr[i]) {
				boolean a = true;
				if (arr.length - i >= sub.length) {
					for (int j = 0; a && j < sub.length; j++) {
						a = (arr[i + j] == sub[j]);
					}
					if (a) {
						return i;
					}
				}
			}

		}
		return -1;
	}
}
//**************************************************************************************************
