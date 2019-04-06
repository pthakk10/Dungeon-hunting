import java.util.HashMap;

public class Exit implements Move {
    private Character character;

    /**
     * Creates a exit object that will remove a character from the game
     *
     * @param character Character that will be removed from the game
     * @return Exit object
     */
    public Exit(Character character) {
        this.character = character;
    }

    /**
     * Removes character from game
     *
     * @return Execute object
     */
    @Override
    public void execute() {

        //Notify user of event
        System.out.println("================================================");
        System.out.println("PLAYER: " + character.name() + " has left the game");
        System.out.println("================================================\n");

        //Tell the character to leave the game
        character.leaveGame();
    }
}
