import java.util.HashMap;
import java.util.Random;

public class AttackingAI implements DecisionMaker {

    @Override
    public Move getMove(Character character, Place place) {

        //Used to debug what the status of the NPC characters are currently. If you want to check what
        //move the NPC is doing then we would just uncomment the System.out.println() in each move
        //character.print();

        Random rand = new Random();

        //Generate random number to decide what move to make
        int randomMove = rand.nextInt(3);


        //Used to debug the attacker to make sure the attacker is fetching an attack
//        System.out.println("Fetching attack for : " + character.name());

        //Get all the available characters from current place to see who to attack
        HashMap<String, Character> availableCharacters = place.getCharacters();

        //Only pick a character if there is at least one character to pick from
        if (availableCharacters.size() > 0) {
            int randomCharacter = rand.nextInt(availableCharacters.size());
            int counter = 0;

            for (Character availableCharacter : availableCharacters.values()) {
                //Check to see when the counter equals the randomly selected number, check to make sure the character we attack is only a player, check the player is still playing
                if (counter == randomCharacter && availableCharacter instanceof Player && availableCharacter.isPlaying()) {


                    //Notify the player that they have been attacked by this attacker
                    System.out.println("================================================");
                    System.out.println("* " + availableCharacter.name() + " got attacked by " + character.name());
                    System.out.println("================================================");

                    //Create attack object on selected character
                    return new Attack(place, availableCharacter);
                }

                counter++;
            }
        }


        //If there is no characters to attack then just make the Attacker wait
        return new Wait();
    }
}
