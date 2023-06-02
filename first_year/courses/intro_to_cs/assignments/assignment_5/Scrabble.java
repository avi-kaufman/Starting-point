import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.net.StandardSocketOptions;
import java.sql.SQLOutput;

/*
 * A version of the Scrabble game.
 */

public class Scrabble {

	// Note: "Class variables", like the following five class-level variables,
	// are global variables that can be accessed by all the functions in the class.
	// It is customary to name class variables using capital letters and underline
	// characters. If a variable is "final", it means that it is treated as a 
	// constant value which is declared once and cannot changed later.
	// Name of the dictionary file:
	static final String WORDS_FILE = "dictionary.txt";

	// Number of words in the dictionary file (assumed to be at most 100,000):
	static int NUM_OF_WORDS;

	// The dictionary array (will be read from the dictionary file)
	static String[] DICTIONARY = new String[100000];

	// The "Scrabble value" of each letter in the English alphabet
	static final int[] SCRABBLE_LETTER_VALUES = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3,
			1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

	// The hand size (number of random letters dealt at each round of the Scrabble game):
	static int HAND_SIZE = 10;

	/**
	 * Initializes the game by performing the following SIDE EFFECTS:
	 * 1. Populates the DICTIONARY array with all the words found in the WORDS_FILE.
	 * Each word is stored in its lowercase version.
	 * 2. Sets NUM_OF_WORDS to the number of words found in the file.
	 * 3. Sets standard input to the keyboard.
	 */
	public static void init() {
		// Sets the standard input stream to the given word file
		StdIn.setInput(WORDS_FILE);
		System.out.println("Loading word list from file...");
		// Reads all the words from the file into the DICTIONARY array
		int i = 0;
		while (!StdIn.isEmpty()) {
			DICTIONARY[i] = StdIn.readString().toLowerCase();
			i++;
			NUM_OF_WORDS = i;
		}

		NUM_OF_WORDS = i;
		System.out.println(NUM_OF_WORDS + " words loaded.");
		// Sets the standard input stream to the keyboard, to allow interaction with the user.
		StdIn.setInput("keyboard");
	}

	/**
	 * Returns the Scrabble score of a given word.
	 * The score of a word is the sum of the points of the letters in the word,
	 * multiplied by the length of the word, plus 50 points if the length of the word is n.
	 *
	 * @param word - a lowercase string of letters
	 * @param n    - a given integer
	 * @return - the Scrabble value of the word
	 */
	public static int getWordScore(String word, int n) {
		int score = 0;
		for (int i = 0; i < word.length(); i++) {
			score += SCRABBLE_LETTER_VALUES[word.charAt(i) - 'a'];
		}
		score *= word.length();
		if (word.length() == n) {
			score += 50;
		}
		return score;
	}

	/**
	 * Runs a single hand in a Scrabble game. The hand starts with n letters.
	 *
	 * @param hand - the hand
	 * @param n    - the initial size of the hand
	 */
	public static void playHand(String hand) {
		int lengthOfTheHand = hand.length();
		int score = 0;
		while (lengthOfTheHand > 0) {
			System.out.println("Current Hand: " + MyString.spacedString(hand));
			System.out.println("Enter a word, or '.' to finish this hand:");
			String input = StdIn.readString();
			if (input.equals(".")) {
				System.out.println("End of hand. Total score: " + score + " points.");
				break;
			}
			if (!MyString.subsetOf(input, hand)) {
				System.out.println("Invalid word. Try again");
				System.out.println();
			} else if (!isWordInDictionary(input, DICTIONARY)) {
				System.out.println("No such word in the dictionary. Try again");
				System.out.println();
			} else {
				int scoreWord = getWordScore(input, hand.length());
				score += scoreWord;
				System.out.println(input + " earned " + scoreWord + " points. Total score: " + score + " points." );

				hand = MyString.remove(hand, input);
				System.out.println();
			}

		}
		if (lengthOfTheHand == 0) {
			System.out.println("Ran out of letters . " +
					"Total score: " + score + " points.");
		} else {
			System.out.println("End of hand " + score);
		}
	}


	/**
	 * Initializes the game, and then allows the user to play an arbitrary number of hands.
	 * <p>
	 * 1) Asks the user to input 'n' or 'r' or 'e'.
	 * - If the user inputs 'n', lets the user play a new (random) hand.
	 * - If the user inputs 'r', lets the user play the last hand again
	 * (works only if this is not the first hand).
	 * - If the user inputs 'e', exits the game.
	 * - If the user inputs anything else, writes that the input is invalid.
	 * <p>
	 * 2) When the user is done playing the hand, repeats from step 1.
	 */
	public static void playGame() {
		int numOfRounds = 0;
		String newHand = "";
		String lastHand = "";
		System.out.println("Enter n to deal a new hand, r to replay the last hand, or e to end game:");
		System.out.println();
		String inputFromUser = StdIn.readString();
		while (true) {
			if (inputFromUser.equals("n")) {
				numOfRounds += 1;
				newHand = MyString.randomStringOfLetters(HAND_SIZE);
				lastHand = newHand;
				playHand(newHand);
			} else {
				if (inputFromUser.equals("r")) {
					if (numOfRounds == 0) {
						System.out.println("You have not played a hand yet. Please play a new hand first!");
						System.out.println();
						break;
					} else {
						playHand(lastHand);
					}
				} else {
					if (inputFromUser.equals("e")) {
						break;
					} else {
						System.out.println("Invalid command.");
						System.out.println();
						System.out.println("Enter n to deal a new hand," +
								" r to replay the last hand, or e to end game:");
					}
				}

			}
		}
	}


	// Checks is the given word is in the given dictionary.
	public static boolean isWordInDictionary(String word, String[] dictionary) {
		for (int i = 0; i < dictionary.length; i++) {
			if (word.equals(dictionary[i])) {
				return true;
			}
		}
		return false;
	}
		public static void main(String args[]) {
			init();
			 //testPlayHand();
			 playGame();
		}

		public static void testPlayHand() {
			System.out.println();
			playHand("pzuttto");
			playHand("aqwffip");
			playHand("aretiin");

		}
}

