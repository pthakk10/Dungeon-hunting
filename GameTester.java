/*
Author: Giovanni Alanis
ACCC: galani3
UIN: 657681506
*/

import java.io.File;
import java.util.Scanner;

public class GameTester {

    public static void main(String[] args) {

        //Print out student information
        System.out.println("Giovanni Alanis ACCC: galani3");
        System.out.println("Harsheta Kumar  ACCC: hkumar7");
        System.out.println("Prachi Thakkar  ACCC: pthakk23\n");

        //Ask user for gdf file to read from
        System.out.print("Enter the game file name: ");
        Scanner input = KeyboardScanner.getKeyboardScanner();
        File file = new File(input.nextLine());

        //If unable to find file, then keep asking user until a valid file is entered
        while (!file.exists()) {
            System.out.print("Could not find game file. Please try again: ");
            file = new File(input.nextLine());
        }

        //Try creating scanner file with valid file name
        try {
            input = new Scanner(file);
        } catch (Exception e) {
            System.out.println("Could not find file.");
        }

        //Pass gdf file to game constructor
        Game myGame = new Game(input);

        //Close scanner for reading file
        input.close();

        //Print out game information for debugging
        //Character.printAll();
        //myGame.print();
        //Place.printAll();

        //Begin playing the game
        myGame.play();

    }
}
