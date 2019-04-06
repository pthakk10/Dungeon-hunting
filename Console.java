/**
 * Prints out numerous new lines to the terminal to give the illusion of the screen clearing
 */
public class Console {
    public static void clearScreen() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
}
