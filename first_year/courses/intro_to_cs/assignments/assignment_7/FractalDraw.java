public class FractalDraw {

    public static void main(String[] args) {
        // Put your testing code here. For example:
        drawCarpet(5);

    }

    // Draws a Sierpinski Carpet fractal of depth n, in the unit square.
    // The initial square is centered at coordinates (0.5, 0.5),
    // and the length of its edge (size) is 1.0/3.0.
    // Assumes that n is nonnegative.
    public static void drawCarpet(int n) {
        // Write your code here:
        double x = 0.5;
        double y = 0.5;
        double size = 1.0 / 3.0;
        StdDraw.filledSquare(x, y, size / 2);
        drawCarpet(x, y, size, n);


    }

    private static void drawCarpet(double x, double y, double size, int n) {
        // Write your code here:
        if (n == 0) return;
        double leftX = x - size;
        double rightX = x + size;
        double upY = y + size;
        double downY = y - size;
        double newSize = size / 3;
        StdDraw.filledSquare(leftX, downY, newSize / 2);
        StdDraw.filledSquare(leftX, upY, newSize / 2);
        StdDraw.filledSquare(rightX, upY, newSize / 2);
        StdDraw.filledSquare(rightX, downY, newSize / 2);
        StdDraw.filledSquare(leftX, y, newSize / 2);
        StdDraw.filledSquare(x, upY, newSize / 2);
        StdDraw.filledSquare(rightX, y, newSize / 2);
        StdDraw.filledSquare(x, downY, newSize / 2);

        drawCarpet(leftX, downY, newSize, n - 1);
        drawCarpet(leftX, y, newSize, n - 1);
        drawCarpet(leftX, upY, newSize, n - 1);
        drawCarpet(x, upY, newSize, n - 1);
        drawCarpet(rightX, upY, newSize, n - 1);
        drawCarpet(rightX, y, newSize, n - 1);
        drawCarpet(rightX, downY, newSize, n - 1);
        drawCarpet(x, downY, newSize, n - 1);
        drawCarpet(leftX, downY, newSize, n - 1);


    }


}
