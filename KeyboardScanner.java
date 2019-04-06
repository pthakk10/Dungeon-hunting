import java.util.Scanner;

/**
 * Creates a single scanner using the singleton design for taking in input from keyboard
 */
public class KeyboardScanner {

    private static Scanner keyboardScanner = null;

    private KeyboardScanner() {
        keyboardScanner = new Scanner(System.in);
    }

    public static Scanner getKeyboardScanner() {
        if (keyboardScanner == null) {
            new KeyboardScanner();
        }

        return keyboardScanner;
    }
}
