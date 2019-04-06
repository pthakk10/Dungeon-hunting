import java.util.Random;

public class Attack implements Move {

    private Place place;
    private Character character;

    public Attack(Place place, Character character)
    {
        this.place = place;
        this.character = character;
    }

    public void execute()
    {
        Random rand = new Random();

        //Generate random damage to perform on selected character
        int randomDamage = rand.nextInt(20);

        //Deplete characters health
        this.character.depleteHealth(randomDamage);

    }
}
