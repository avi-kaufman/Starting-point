//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class StdIn {
    private static final String CHARSET_NAME = "UTF-8";
    private static final Locale LOCALE;
    private static final Pattern WHITESPACE_PATTERN;
    private static final Pattern EMPTY_PATTERN;
    private static final Pattern EVERYTHING_PATTERN;
    private static Scanner scanner;
    private static InputStream keyboard;

    private StdIn() {
    }

    public static boolean isEmpty() {
        return !scanner.hasNext();
    }

    public static boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public static boolean hasNextChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        boolean var0 = scanner.hasNext();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return var0;
    }

    public static String readLine() {
        String var0;
        try {
            var0 = scanner.nextLine();
        } catch (NoSuchElementException var2) {
            var0 = null;
        }

        return var0;
    }

    public static char readChar() {
        try {
            scanner.useDelimiter(EMPTY_PATTERN);
            String var0 = scanner.next();

            assert var0.length() == 1 : "Internal (Std)In.readChar() error! Please contact the authors.";

            scanner.useDelimiter(WHITESPACE_PATTERN);
            return var0.charAt(0);
        } catch (NoSuchElementException var1) {
            throw new NoSuchElementException("attempts to read a 'char' value from standard input, but no more tokens are available");
        }
    }

    public static String readAll() {
        if (!scanner.hasNextLine()) {
            return "";
        } else {
            String var0 = scanner.useDelimiter(EVERYTHING_PATTERN).next();
            scanner.useDelimiter(WHITESPACE_PATTERN);
            return var0;
        }
    }

    public static String readString() {
        try {
            return scanner.next();
        } catch (NoSuchElementException var1) {
            throw new NoSuchElementException("attempts to read a 'String' value from standard input, but no more tokens are available");
        }
    }

    public static int readInt() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException var2) {
            String var1 = scanner.next();
            throw new InputMismatchException("attempts to read an 'int' value from standard input, but the next token is \"" + var1 + "\"");
        } catch (NoSuchElementException var3) {
            throw new NoSuchElementException("attemps to read an 'int' value from standard input, but no more tokens are available");
        }
    }

    public static double readDouble() {
        try {
            return scanner.nextDouble();
        } catch (InputMismatchException var2) {
            String var1 = scanner.next();
            throw new InputMismatchException("attempts to read a 'double' value from standard input, but the next token is \"" + var1 + "\"");
        } catch (NoSuchElementException var3) {
            throw new NoSuchElementException("attempts to read a 'double' value from standard input, but no more tokens are available");
        }
    }

    public static float readFloat() {
        try {
            return scanner.nextFloat();
        } catch (InputMismatchException var2) {
            String var1 = scanner.next();
            throw new InputMismatchException("attempts to read a 'float' value from standard input, but the next token is \"" + var1 + "\"");
        } catch (NoSuchElementException var3) {
            throw new NoSuchElementException("attempts to read a 'float' value from standard input, but there no more tokens are available");
        }
    }

    public static long readLong() {
        try {
            return scanner.nextLong();
        } catch (InputMismatchException var2) {
            String var1 = scanner.next();
            throw new InputMismatchException("attempts to read a 'long' value from standard input, but the next token is \"" + var1 + "\"");
        } catch (NoSuchElementException var3) {
            throw new NoSuchElementException("attempts to read a 'long' value from standard input, but no more tokens are available");
        }
    }

    public static short readShort() {
        try {
            return scanner.nextShort();
        } catch (InputMismatchException var2) {
            String var1 = scanner.next();
            throw new InputMismatchException("attempts to read a 'short' value from standard input, but the next token is \"" + var1 + "\"");
        } catch (NoSuchElementException var3) {
            throw new NoSuchElementException("attempts to read a 'short' value from standard input, but no more tokens are available");
        }
    }

    public static byte readByte() {
        try {
            return scanner.nextByte();
        } catch (InputMismatchException var2) {
            String var1 = scanner.next();
            throw new InputMismatchException("attempts to read a 'byte' value from standard input, but the next token is \"" + var1 + "\"");
        } catch (NoSuchElementException var3) {
            throw new NoSuchElementException("attempts to read a 'byte' value from standard input, but no more tokens are available");
        }
    }

    public static boolean readBoolean() {
        try {
            String var0 = readString();
            if ("true".equalsIgnoreCase(var0)) {
                return true;
            } else if ("false".equalsIgnoreCase(var0)) {
                return false;
            } else if ("1".equals(var0)) {
                return true;
            } else if ("0".equals(var0)) {
                return false;
            } else {
                throw new InputMismatchException("attempts to read a 'boolean' value from standard input, but the next token is \"" + var0 + "\"");
            }
        } catch (NoSuchElementException var1) {
            throw new NoSuchElementException("attempts to read a 'boolean' value from standard input, but no more tokens are available");
        }
    }

    public static String[] readAllStrings() {
        String[] var0 = WHITESPACE_PATTERN.split(readAll());
        if (var0.length != 0 && var0[0].length() <= 0) {
            String[] var1 = new String[var0.length - 1];

            for(int var2 = 0; var2 < var0.length - 1; ++var2) {
                var1[var2] = var0[var2 + 1];
            }

            return var1;
        } else {
            return var0;
        }
    }

    public static String[] readAllLines() {
        ArrayList var0 = new ArrayList();

        while(hasNextLine()) {
            var0.add(readLine());
        }

        return (String[])var0.toArray(new String[var0.size()]);
    }

    public static int[] readAllInts() {
        String[] var0 = readAllStrings();
        int[] var1 = new int[var0.length];

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = Integer.parseInt(var0[var2]);
        }

        return var1;
    }

    public static long[] readAllLongs() {
        String[] var0 = readAllStrings();
        long[] var1 = new long[var0.length];

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = Long.parseLong(var0[var2]);
        }

        return var1;
    }

    public static double[] readAllDoubles() {
        String[] var0 = readAllStrings();
        double[] var1 = new double[var0.length];

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = Double.parseDouble(var0[var2]);
        }

        return var1;
    }

    private static void resync() {
        setScanner(new Scanner(new BufferedInputStream(System.in), "UTF-8"));
    }

    private static void setScanner(Scanner var0) {
        scanner = var0;
        scanner.useLocale(LOCALE);
    }

    /** @deprecated */
    @Deprecated
    public static int[] readInts() {
        return readAllInts();
    }

    /** @deprecated */
    @Deprecated
    public static double[] readDoubles() {
        return readAllDoubles();
    }

    public static void setInput(String var0) {
        try {
            if (var0.equals("keyboard")) {
                System.setIn(keyboard);
            } else {
                System.setIn(new FileInputStream(var0));
            }
        } catch (FileNotFoundException var2) {
            var2.printStackTrace();
        }

        resync();
    }

    /** @deprecated */
    @Deprecated
    public static String[] readStrings() {
        return readAllStrings();
    }

    static {
        LOCALE = Locale.US;
        WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");
        EMPTY_PATTERN = Pattern.compile("");
        EVERYTHING_PATTERN = Pattern.compile("\\A");
        keyboard = System.in;
        resync();
    }
}
