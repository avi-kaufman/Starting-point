

// Represents algebraic operations as functions.
public class Algebra {
    public static void main(String args[]) {
        // Some tests of the class functions
        System.out.println(plus(2, 3));           // 2 + 3
        System.out.println(minus(7, 2));          // 7 - 2
        System.out.println(times(3, 4));          // 3 * 4
        System.out.println(div(5, 3));            // 5 / 3
        System.out.println(div(3, 5));            // 3 / 5
        System.out.println(div(4, 4));            // 4 / 4
        System.out.println(div(14, 7));           // 14 / 7
        System.out.println(mod(9, 3));            // 5 % 3
        System.out.println(pow(5, 4));            // 5 ^ 4
        System.out.println(sqrt(10));            // sqrt(36)
        System.out.println(sqrt(76123));         // sqrt(76123)
        System.out.println(times(2, plus(4, 3)));  // 2 * (4 + 3)
        int b = 5, c = 3;
        System.out.println(minus(pow(b, 2), times(4, c))); // b * b - 4 * c*/
    }

    // Returns x1 + x2.
    // Assumption: x1 and x2 are nonnegative.
    public static int plus(int x1, int x2) {
        int sum = x1;
        // for loop to add 1 for for each iteration
        for (int i = 0; i < x2; i++) {
            sum++;
        }
        return sum;
    }

    // Returns x1 - x2.
    // Assumption: x1 and x2 are nonnegative, and x1 >= x2.
    public static int minus(int x1, int x2) {
        int sum = x1;
        // for loop to to decrease 1 for for each iteration
        for (int i = 0; i < x2; i++) {
            sum--;
        }
        return sum;
    }

    // Returns x1 * x2.
    // Assumption: x1 and x2 are nonnegative.
    public static int times(int x1, int x2) {
        //for loop to multiply x1 by x2
        int result = 0;
        for (int i = 0; i < x2; i++) {
            result = plus(x1, result);
        }
        return result;
    }

    // Returns x^n.
    // Assumption: x and n are nonnegative.
    public static int pow(int x, int n) {
        int sum = 1;
        //for loop to power x by n
        for (int i = 0; i < n; i++) {
            sum = times(x, sum);
        }

        return sum;
    }

    // Returns x1 / x2 (integer division).
    // Assumption: x1 is nonnegative, x2 is positive.
    public static int div(int x1, int x2) {
        int sum = 0;
        int n = 0;
        //for loop to divide x1 by x2
        for (int i = 0; sum <= x1; i++) {
            sum = times(x2, plus(i, 1));
            n = i;
        }
        return n--;
    }

    // Returns x1 % x2************************
    // Assumption: x1 is nonnegative, x2 is positive.
    public static int mod(int x1, int x2) {
        int sum = 0;
        int mod = 0;
        //for loop to divide x1 by x2
        for (int i = 0; sum <= x1; i++) {
            sum = times(x2, plus(i, 1));
        }
        mod = x1 - (sum - x2);
        return mod;
    }

    // Returns the integer part of sqrt(x)
    // Assumption: x >= 1.
    public static int sqrt(int x) {
        for (int i = 1; true; i++) {
            if ((times(i, i)) > x) {
                i--;

                return i;
            }

        }
    }
}

