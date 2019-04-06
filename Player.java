import java.util.Scanner;

/**
 * Creates a Player object where it selects the decision maker to be from UI class
 */
public class Player extends Character {

    public Player(Scanner inputFile, double version) {
        super(inputFile, version);
        super.setDecisionMaker(new UI());
    }

    //Used to manually create players if none are present in gdf file
    public Player(int id, int startingPlace, String name, String description) {
        super(id, startingPlace, name, description);
        super.setDecisionMaker(new UI());
    }
}
