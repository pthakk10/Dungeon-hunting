import java.util.Scanner;

public class Attacker extends NPC {

    public Attacker(Scanner inputFile, double version) {
        super(inputFile, version, new AttackingAI());
    }


}
