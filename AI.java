import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class AI implements DecisionMaker {

    /**
     * Returns a move object that a NPC will execute based on which randomly generated number is picked.
     *
     * @param character A character of type NPC on which the random move will be picked
     * @param place     The current place of the NPC that was passed.
     * @return Random Move Object
     */
    @Override
    public Move getMove(Character character, Place place) {

        //Used to debug what the status of the NPC characters are currently. If you want to check what
        //move the NPC is doing then we would just uncomment the System.out.println() in each move
        //character.print();

        Random rand = new Random();

        //Generate random number to decide what move to make
        int randomMove = rand.nextInt(4);

        //AI Go
        if (randomMove == 0) {
            //System.out.println(character.name() + " is performing Go operation");

            //Get available directions from current place
            ArrayList<Direction> availableDirections = place.getDirections();

            //Select a random direction
            int rand_int = rand.nextInt(availableDirections.size());
            Direction randomDirectionSelected = availableDirections.get(rand_int);

            //Return a new Go object containing randomly selected direction
            return new Go(place, character, randomDirectionSelected.directionType());

        }
        //AI Get
        else if (randomMove == 1) {
            //System.out.println(character.name() + " is performing Get operation");

            //Get available artifacts from current place
            HashMap<String, Artifact> availableArtifacts = place.getAvailableArtifacts();

            //Check to see if there's even any AI to pickup in the first place, if not then make the NPC wait
            if (availableArtifacts.size() > 0) {
                int counter = 0;
                int randomCounter = rand.nextInt(availableArtifacts.size());

                //Pick a random artifact from available artifacts and return a new Get object with it
                for (Artifact artifact : availableArtifacts.values()) {
                    if (counter == randomCounter) {
                        return new Get(place, character, artifact.name());
                    }
                    counter++;
                }
            } else {
                return new Wait();
            }

        }
        //AI Drop
        else if (randomMove == 2) {
            //System.out.println(character.name() + " is performing Drop operation");

            //Get all the inventory from the NPC
            HashMap<String, Artifact> characterInventory = character.retrieveInventory();

            //Check to see first if NPC has anything to drop
            if (characterInventory.size() > 0) {
                int counter = 0;
                int randomCounter = rand.nextInt(characterInventory.size());

                //Pick a random artifact from the inventory and return a drop object
                for (Artifact artifact : characterInventory.values()) {
                    if (counter == randomCounter) {
                        return new Drop(place, character, artifact.name());
                    }
                    counter++;
                }
            } else {
                return new Wait();
            }

        }
        //AI Use
        else if (randomMove == 3) {
            //System.out.println(character.name() + " is performing Use operation");

            //Get the inventory from the NPC
            HashMap<String, Artifact> characterInventory = character.retrieveInventory();

            //Check to see if there's anything to use
            if (characterInventory.size() > 0) {
                int counter = 0;
                int randomCounter = rand.nextInt(characterInventory.size());

                //Pick a random artifact and see if the NPC can use it
                for (Artifact artifact : characterInventory.values()) {
                    if (counter == randomCounter) {
                        return new Use(place, character, artifact.name());
                    }
                }
            } else {
                return new Wait();
            }
        }
        return new Wait();
    }
}
