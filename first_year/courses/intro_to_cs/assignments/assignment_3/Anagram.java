
// A collection of functions for handling anagrams.
public class Anagram {
	public static void main(String args[]) {
	      // Tests the isAnagram function.
	      System.out.println(preProcess("aAbB1   23//99"));
		  System.out.println(isAnagram("cab","abc"));  // true
	      System.out.println(isAnagram("I am Lord Voldemort ","I am Lord Voldemord")); // true
	      System.out.println(isAnagram("Madam Curie","Radium came")); // true
	      // Tests the randomAnagram function.
	      System.out.println(randomAnagram("silent")); // Prints a random anagram
	      // Performs a stress test of randomAnagram
	      boolean pass = true;
	      String str = "this is a stress test";
	      for (int i = 0; i < 1000; i++) {
	          pass = pass && isAnagram(str, randomAnagram(str));	
	      }     
	      System.out.println(pass);	// true if all tests are positive   */
	   }  

	   // Returns true if the two given strings are anagrams, false otherwise.
	   public static boolean isAnagram(String str1, String str2) {
		   str1 = preProcess(str1);
		   str2 = preProcess(str2);

		   if (str1.length() == str2.length()) {
			   for (int i = 0; i < str1.length(); i++) {
			   	   boolean z = true;
				   for (int n = 0; z && n < str2.length(); n++) {
					   if (str1.charAt(i) == str2.charAt(n)) {
						   str2 = str2.substring(0, n) + str2.substring(n + 1, str2.length());
						   z = false;

					   }
				   }
			   }
			   if (str2.length() == 0) {
				   return true;
			   } else {
				   return false;
			   }
		   }else{
			   return false;
		   }

	   }
	   
	   // Returns a preprocessed version of the given string: all the letter characters
	   // are converted to lower-case, and all the other characters are deleted. For example, 
	   // the string "What? No way!" becomes "whatnoway"
	   public static String preProcess(String str) {
	       String preprocessed = "";
		   //for loop to recognize Which char are ('a', 'z') or ('A', 'Z')
		   for (int i = 0; i < str.length(); i++) {
		       char c = str.charAt(i);
		       if (c >= 'A' && c <= 'Z'){
		           int a = ((int)c) + 32;
		           preprocessed = preprocessed + ((char)a);
		           
		       }else{
		           if (c >= 'a' && c <= 'z'){
		               preprocessed = preprocessed + (c);
		       }
		       
		       }
		       
		   }
	       return preprocessed;
	   } 
	   
	   // Returns a random anagram of the given string. The random anagram consists of the same
	   // letter characters as the given string, arranged in a random order.
	   public static String randomAnagram(String str) {
	       String temp = "";
	       String str2 = str;
	       while (temp.length() < str.length()) {
		   int i = (int)(Math.random() * str2.length());
		   char c = str2.charAt(i);
		   temp = temp + c;
		   str2 = (str2.substring(0, i) + str2.substring(i+1 , str2.length() ));
		   
	       } 
	       return temp;
	   }
}
