public class Died implements Move {
    private Character character;

    public Died(Character character)
    {
        this.character = character;
    }

    public void execute()
    {
        //Notify the user that the player has fainted
        System.out.println("==========================================");
        System.out.println("* " + character.name() + " has been slained!");
        System.out.println("==========================================");

        //Tell the character to leave the game
        character.leaveGame();
    }
}
