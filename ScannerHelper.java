import java.util.Scanner;

/**
 * Scanner Helper class was designed to help retrieve either clean lines or the next available integer in the file
 */
public class ScannerHelper {

    static public String getEmptyLine(Scanner file) {
        String fileLine = file.nextLine();
        while (fileLine.isEmpty() || fileLine.startsWith("//")) {
            fileLine = file.nextLine();
        }

        return fileLine;
    }


    static public void getNextInt(Scanner file) {
        while (!file.hasNextInt()) {
            file.nextLine();
        }
    }
}
