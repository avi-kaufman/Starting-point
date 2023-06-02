import java.util.HashMap;

/* This class provides the training stage of a random text generator.
 * The program reads text ("corpus") from a given file, and analyses
 * and records the character probabilitie in the given text. */
public class TextTrain {

    // Length of the moving window
    private static int windowLength;

    // A map for managing the (window, list) mappings
    private static HashMap<String, List> map;

    public static void main(String[] args) {
        int windowLength = Integer.parseInt(args[0]);
        String fileName = args[1];
        init(windowLength, fileName);

        // Creates the map (implemented as a global, class-level variable).
        train();

        // Prints a textual representation of the map (for debugging purposes only).
        System.out.println(mapString());
    }

    // Initializes the training process.

    public static void init(int length, String fileName) {
        windowLength = length;
        map = new HashMap<String, List>();
        StdIn.setInput(fileName);
    }

    /**
     * Trains the model, creating the map.
     */
    public static void train() {
        String window = "";
        char c = 'a';
        // Constructs the first window
        for (int i = 0; i < windowLength; i++) {
            c = StdIn.readChar();
            window += c;
        }


        // Processes the entire text, one character at a time
        while (!StdIn.isEmpty()) {
            c = StdIn.readChar();
            if (map.get(window) == null) {
                List newList = new List();
                newList.update(c);
                map.put(window, newList);
            } else {
                map.get(window).update(c);
            }

            window = window.substring(1) + c;

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

    /**
     * Generates a string representation of the map, for debugging purposes.
     */
    public static String mapString() {
        StringBuilder ans = new StringBuilder();
        for (String key : map.keySet()) {
            String output = key + ": " + map.get(key);
            ans.append(output + "\n");
        }
        return ans.toString();
    }

}