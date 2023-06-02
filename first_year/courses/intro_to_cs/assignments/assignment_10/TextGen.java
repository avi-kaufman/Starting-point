import java.util.HashMap;
import java.util.Random;

/* A random text generator. The program "trains" itself by reading and analysing
 * character probabilitie in a given text, and then generates random text that is
 * "similar" to the given text. */
public class TextGen {

	// Length of the moving window
	private static int windowLength; 
	
	// A map for managing the (window, list) mappings 
	private static HashMap<String, List> map;

	// Random number generator, used by the getRandomChar method. 
	private static Random randomGenerator;

	public static void main(String[] args) {
		int windowLength = Integer.parseInt(args[0]);
		String initialText = args[1];
		int generatedTextLength = Integer.parseInt(args[2]);
		boolean randomGeneration = args[3].equals("random");
		String fileName = args[4];
		init(windowLength, randomGeneration, fileName);

		// Creates the map (implemented as a global, class-level variable).
		train();

		// Uses the map for generating random text, and prints the text.
		String generatedText = generate(initialText, generatedTextLength);
		System.out.println(generatedText);
	}
	
	// Initializes the training and text generation processes
	private static void init(int length, boolean randomMode, String fileName) {
		windowLength = length;
		map = new HashMap<String, List>();
		StdIn.setInput(fileName);
		if (randomMode) {
			// Creates a random number generator with a random seed:
			// Each program run will produce a different random text.
		    randomGenerator = new Random();    
		} else {
			// Creates a random number generator with a fixed seed:
			// Each program run will produce the same random text.
			// Designed to support consistent testing and debugging.
            randomGenerator = new Random(20);
		}
	}

	/** Trains the model, creating the map. */
	public static void train() {
		/// Put here the code that you wrote in Stage II
		String window = "";
		char c;
		// Cosntructs the first window
		/// Put your code here
		for (int i = 0; i < windowLength; i++) {
			window += StdIn.readChar();
		}
		List list1 = new List();
		c = StdIn.readChar();
		list1.update(c);
		map.put(window,list1);
		//System.out.println("window: " + map.get(window));

		// Processes the entire text, one character at a time
		while (!StdIn.isEmpty()) {
			// Put your code here
			window = window.substring(1) + c ;
			c = StdIn.readChar();
			if(map.get(window) == null) {
				list1 = new List();
				list1.update(c);
				map.put(window, list1);
			}else {

				map.get(window).update(c);
			}

		}

		// Computes and sets the p and pp fields of all the
		// CharData objects in each list in the map.
		for (List list : map.values()) {
			calculateProbabilities(list);
		}
	}

	// Computes and sets the probabilities (p and pp fields) of all the
	// characters in the given list. */
	private static void calculateProbabilities(List list) {
		int totalCount = 0;
		for (int i = 0; i < list.getSize(); i++) {
			totalCount += list.get(i).count;
		}
		for (int i = 0; i < list.getSize(); i++) {
			list.get(i).p = (double) list.get(i).count / totalCount;
		}
		list.get(0).pp = list.get(0).p;
		for (int i = 1; i < list.getSize(); i++) {
			list.get(i).pp = list.get(i - 1).pp + list.get(i).p;
		}
	}

	/** Generates a string representation of the map, for debugging purposes. */
	public static String mapString() {
		StringBuilder ans = new StringBuilder();
		for (String key : map.keySet()) {
			String output = key + ": " + map.get(key);
			ans.append(output + "\n");
		}
		return ans.toString();
	}

	/**
	 * Generates random text, based on the map crated by the train() method. 
	 * @param initialText - the beginning of the generated text 
	 * @param textLength - the size of generated text
	 * @return the generated text
	 */
	public static String generate(String initialText, int textLength) {
		/// Replace the following statement with your code
		if(textLength <= windowLength) {
			return initialText;
		}
		String myText = initialText;
		String window = initialText.substring(initialText.length() - windowLength); //  לוודא שיש חלון באורך של הטקס
		while (myText.length() < textLength+windowLength && map.containsKey(window) ){
			char c = getRandomChar(map.get(window));
			myText += c;
			window = window.substring(1) + c;
		}
		return myText;
	}


	// Returns a random character from a given list of CharData objects,
    // using Monte Carlo.
	public static char getRandomChar(List list) {
		double r = Math.random();
		for (int i = 0; i < list.getSize(); i++) {
			if(r <= list.get(i).pp){
				return list.get(i).chr;
			}
		}

		return ' ';
	}
}
