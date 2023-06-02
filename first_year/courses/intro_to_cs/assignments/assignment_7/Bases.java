public class Bases {

    public static void main(String[] args) {
        // Put your tests here. For example:
        System.out.println(convert(26, 6));
    }

    // Returns the representation of the given decimal number (x) using the given base.
    // For example, convert(17, 2) returns "10001".
    // For example, convert(17, 2) returns "10001".
    // Assumes that x is nonnegative, and base is nonnegative and not greater than 10.

    public static String convert(int x, int base) {
        String numConverted = "";
        int dividing = x / base;
        if (dividing == 0) {
            return (x % base) + numConverted;
            //stop and return ans = numConverted
        } else {
            numConverted = (x % base) + numConverted;


        }
        return convert(dividing, base) + numConverted;
    }
}