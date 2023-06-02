/**
 * Tests the construction of a list of CharData objects.
 */
public class Test2 {

    public static void main(String args[]) {
        testBuildList();
        testBuildListWithProbabilities();
    }

    public static void testBuildList() {
        System.out.println("Testing the construction of a list of CharData objects " +
                "from a given string input.");
        System.out.println("The probability fields of the CharData objects will be initialized to 0.");
        String input = "committee ";
        System.out.println("Input = \"" + input + "\"");
        List q = buildList(input);
        System.out.println("List = " + q);
        System.out.println();
    }

    public static void testBuildListWithProbabilities() {
        System.out.println("Testing the construction of a list of CharData objects " +
                "from a given string input.");
        System.out.println("This time, the probability fields will be computed and set correctly.");
        String input = "committee ";
        System.out.println("Input = \"" + input + "\"");
        List q = buildList(input);
        // Calcualates the probalities
        calculateProbabilities(q);
        // Prints the list with the calculates probabilities
        System.out.println("List =  " + q);
        System.out.println();
    }

    // Builds a list of CharData objects from a given string.
    private static List buildList(String input) {
        // Replace the following statement with your code
        List listFromString = new List();

        for (int i = 0; i < input.length(); i++) {
            listFromString.update(input.charAt(i));

        }
        return listFromString;
    }

    // Computes and sets the probabilities (p and pp fields) in all the
    // CharData objects in the given list.
    public static void calculateProbabilities(List list) {
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
    // This function is designed to stress-test the getRandomChar function.
    // The function draws a random character from the given input, T times.
    // At the of the experiment (afetr T trials), the program prints
    // how many times each character was drawn, as a fraction of T.
    // For example, if T = 100 and character 'e' was drawn 23 times,
    // the program prints "Character e was generated 0.23 of the time".
    public static void testRandomCharGeneration(String input, int T) {
        System.out.println("Testing the generation of random characters from " +
                "the input " + "\"" + input + "\":");
        System.out.println("Total number of trials: " + T);

        // Builds a list of CharData elements from the given input
        List q = buildList(input);
        // Calcualates the probalities
        calculateProbabilities(q);

        // Builds an array of all the CharData elements in the list
        CharData[] cds = q.toArray();

        // Builds an array of int values of the same length as the cds array.
        // This array will be used to count how many times each character was generated.
        int n = cds.length;
        int[] count = new int[n];
        for (int i = 0; i < T; i++) {
            char c = getRandomChar(q);
            for (int j = 0 ; j < cds.length; j++) {
                if (cds[j].equals(c)) {
                    count[j]++;
                }
            }
        }
        // Runs a loop of T iterations. In each iteration generates a random character,
        // checks what is the index of the generated character in the cds array,
        // and increments the value of this index in the count array.
        /// Replace this line with your code. The code should implement what was just described.

        // Goes through the cds and count arrays, and prints the results of the experiment.
        /// Replace this line with your code. The code should implement what was just described.
        for (int i = 0; i < cds.length; i++) {
            System.out.println("Number of trials that generated " + cds[i].chr + ": " + count[i]);
        }
        System.out.println();
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

