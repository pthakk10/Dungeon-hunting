import java.util.Scanner;

/**
 * Creates an NPC object where it selects the decision maker to be from AI class
 */
public class NPC extends Character {

    public NPC(Scanner inputFile, double version) {
        super(inputFile, version);
        super.setDecisionMaker(new AI());
    }

    //Used to set the decision maker from a class that extends NPC
    public NPC(Scanner inputFile, double version, DecisionMaker decisionMaker)
    {
        super(inputFile, version);
        super.setDecisionMaker(decisionMaker);
    }
}
