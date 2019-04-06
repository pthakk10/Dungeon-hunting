/**
 * Creates wait object for NPC when it tries to make a move that cannot be executed
 */
public class Wait implements Move {

    private enum Reason {

    }

    public Wait() {

    }

    @Override
    public void execute() {
        //Just make the AI wait
    }
}
