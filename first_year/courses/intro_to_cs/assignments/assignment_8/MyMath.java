/**
 * A library of commonly used mathematical operations.
 */
public class MyMath {

    // For each one of the three method names listed below, 
    // write the method signature, document the method,
    // and write its implementation.
    // The documentation should be done in JavaDoc.
    // Use the same documentation style we used in Fraction.java. 

    /**
     * Returns the greatest common divisor of two positive numbers.
     * Uses Euclid's algorithm.
     */
    public static int gcd(int x, int y) {
        int rem;
        while (y != 0) {
            rem = x % y;
            x = y;
            y = rem;
        }
        return x;
    }

    /**
     * Returns the common denominator of too fractions
     *
     * @param frac1+frac2
     * @return the common denominator of too fractions
     */
    public static int commonDenominator(Fraction frac1, Fraction frac2) {
        int d1 = frac1.getDenominator();
        int d2 = frac2.getDenominator();
        if (d1 % d2 == 0) {
            return d1;
        }
        if (d2 % d1 == 0) {
            return d2;
        }
        return d1 * d2;
    }


    /**
    * returen the max of to fractions for example frac1 = 1/2 and frac2 = 1/4. return frac1
     * @param frac1 and frac2
     * @return the max of to fractions
    */
    public static Fraction max(Fraction frac1, Fraction frac2) {
        Fraction temp = frac1.subtract(frac2);
        if(temp.signum() == 1){
            return frac1;
        }
        else{
            return frac2;
        }
    }
}