public class GCD {
    public static void main(String[] args) {
        // Put your tests here. For example:
        System.out.println(gcd(56, 42));
    }

    // Computes the Greatest Common Denominator of the two given integers.
    // Assumes that a is nonnegative and b is greater than 0.
    public static int gcd(int a, int b) {
        int greatestDem = 0;
        if (a == b) {
            return greatestDem = a;
        } else {
            if (a > b) {
                return gcd(a - b, b);
            } else {
                if (b > a) {
                    return gcd(a, b - a);
                }
            }

        }
        return greatestDem;
    }
}
