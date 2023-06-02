public class MaxValPath {

    // This class variable is used for counting recursive steps 
    private static int steps = 0;

    public static void main(String[] args) {
        // Put your testing code here. For example:
        int[][] a = {{3, 4, 5, 2},
                     {2, 0, 1, 0},
                     {1, 2, 3, 4}};


        System.out.println(" Value of maximal path = " + maxVal(a));
        System.out.println(" Number of recursive steps = " + steps);
        steps = 0;
        System.out.println(" Value of maximal path = " + effMaxVal(a));
        System.out.println(" Number of recursive steps = " + steps);
    }

    // Returns the value of the maximal path in the given 2D array
    public static int maxVal(int[][] arr) {
        return maxVal(arr, 0, 0);

    }

    // Returns the value of the maximal path in the given 2D array, starting at location (i,j)
    private static int maxVal(int[][] arr, int i, int j) {
        steps++;
        if (i == arr.length || j == arr[0].length) {
            return 0;
        }
        if (i == arr.length - 1 && j == arr[0].length - 1) {
            return arr[i][j];
        }
        return arr[i][j] + Math.max(maxVal(arr, i + 1, j), maxVal(arr, i, j + 1));

    }

    // Returns the value of the maximal path in the given 2D array, efficiently.
    public static int effMaxVal(int[][] arr) {
        // Creates a 2D array named memo, of the same dimensions as arr.
        // This array will be used for memorizing maxVal computations.
        // After creating the memo array, initializes all its elements to -1.
        // Next, initializes the value of the bottom-right cell of memo to the
        // value of the bottom-right (destination) cell of arr.
        // Put the array creation and initialization code here:
        // Computes the value of the maximal path, using the memo array:
        int[][] memo = new int[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                memo[i][j] = -1;
            }
        }
        memo[arr.length - 1][arr[0].length - 1] = arr[arr.length - 1][arr[0].length - 1];
        effMaxVal(arr, 0, 0, memo);

        // The maximal value is now stored in memo[0][0]:
        return memo[0][0];
    }

    // Returns the value of the maximal path in the given 2D array, starting
    // at location (i,j), efficiently. By "efficiently" we mean as follows:
    // If the value was already computed, returns the value using memo.
    // Otherwise, computes the value, stores the value in memo,
    // and returns the value.
    // SIDE EFFECT: This function changes the contents of the given memo array.
    private static int effMaxVal(int[][] arr, int i, int j, int[][] memo) {
        steps++;
        if (i == arr.length || j == arr[0].length) {
            return 0;
        }
        if (i == arr.length - 1 && j == arr[0].length)
            return arr[i][j];
        if (memo[i][j] != -1)
            return memo[i][j];
        memo[i][j] = arr[i][j] + Math.max(effMaxVal(arr, i + 1, j, memo), effMaxVal(arr, i, j + 1, memo));
        return memo[i][j];
    }
}