import java.util.Random;
import java.util.Scanner;

public class Give implements Move {

    private Place place;
    private Character character;

    //constructor
    public Give(Place place, Character character) {
        this.place = place;
        this.character = character;
    }

    public void execute() {

        //Message that the Leprechaun says before giving away health
        System.out.printf("-------------------------------------------------------\n\n");
        System.out.println("I am the Leprechaun and I am feeling very lucky today!");
        System.out.println("You, my friend, seem a bit down! Here are some health points\n\n");
        System.out.printf("-------------------------------------------------------");
        System.out.printf("\n");

        Random rand = new Random();

        //Generate random health to give to the selected character
        int randomReplenish = rand.nextInt(20) + 1;

        //Increase the characters health
        this.character.increaseHealth(randomReplenish);

        //Used for debugging purposes
        //System.out.println(character.name() + " gained " + randomReplenish + " points of health");
    }

}
