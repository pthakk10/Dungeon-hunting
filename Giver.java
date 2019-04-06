import java.util.Scanner;

public class Giver extends NPC {

    public Giver(Scanner inputFile, double version) {
        super(inputFile, version, new GivingAI());
    }


}