/**
 * Creates a NotFound object in where a player is notified if a typed in command was invalid
 */
public class NotFound implements Move {

    private Character character;

    public NotFound(Character character) {
        this.character = character;
    }

    @Override
    public void execute() {
        System.out.println("================================================");
        System.out.println("PLAYER: " + character.name() + " did nothing");
        System.out.println("================================================\n");
    }
}
