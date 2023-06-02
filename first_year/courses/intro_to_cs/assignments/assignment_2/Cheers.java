// Prints a crowd cheering output.
public class Cheers {
	public static void main(String[] args) {

		String str = args[0];
		int times = Integer.parseInt(args[1]);
		String vowel = "aeiouAEIOU";


		for(int i = 0; i < str.length(); i++){
			boolean itIsVowel = false;
			for(int l = 0; l < vowel.length() && !itIsVowel ; l++){

				if(str.charAt(i) == vowel.charAt(l)){
					itIsVowel = true;
				}

			}

			if(itIsVowel ){
				System.out.println("Give me an " + str.charAt(i) + " : " + str.charAt(i) + "!");
			}else{
				System.out.println("Give me a " + str.charAt(i) + " : " + str.charAt(i) + "!");
			}

		}

		for (int i = 0; i < times; i++) {
			System.out.println(str + "!!!");
		}


	}
}
