import java.util.Scanner;

public class UI implements DecisionMaker {

    /**
     * Returns a move object that a Player will execute based on what the player has typed in
     *
     * @param character A character of type Player on which the selected move will be executed on
     * @param place     The current place of the Player that was passed.
     * @return Selected Move object
     */
    @Override
    public Move getMove(Character character, Place place) {
        Scanner scanner = KeyboardScanner.getKeyboardScanner();
        String userInput;

        //Check if the user is not in an exit place, if so then remove the character from game
        if (place.id() == 0 || place.id() == 1) {
            return new Exit(character);
        }

        //Also check to see if the player has no more health, if so then take out the player from the game
        if (character.checkHealth() <= 0) {
            return new Died(character);
        }

        //Display the player's name along with information about where they are
        System.out.println("==============================");
        System.out.println("PLAYER: " + character.name());
        System.out.println("Health: " + character.checkHealth() + "/100");
        System.out.println("==============================");

        if (!place.checkIllumination()) {
            //Notify user that the place is dark
            System.out.println("This place is pitch black. You can't even see your hands in front of you.");
        } else {
            //If the current place is not a dark place then the user can see the location and directions
            System.out.println("Current Location: " + place.name() + ": " + place.description());
        }


        //Checks to see if the current place has any available artifacts to display
        if (place.getAvailableArtifacts().size() > 0) {
            System.out.println("Artifacts Available: ");
            for (Artifact artifact : place.getAvailableArtifacts().values()) {
                System.out.println(">" + artifact.name() + ": " + artifact.description());
                System.out.println("Mobility: " + artifact.size());
                System.out.println("Value: " + artifact.value());
            }
            System.out.println();
        }

        //Checks to see if there are any other characters in the same place the player is in.
        //If so then display them
        if (place.getCharacters().size() > 1) {
            System.out.println("Characters Present: ");
            for (Character charactersPresent : place.getCharacters().values()) {
                //Print out all the other characters in the current place not including the current character
                if (charactersPresent.name() != character.name()) {
                    charactersPresent.display();
                }
            }
        }
        //Read in user command to determine what they want to do
        System.out.print("ENTER A COMMAND>");
        userInput = scanner.nextLine();
        System.out.println();

        //If user types in look then display the location name and the artifacts in the place
        if (userInput.toLowerCase().trim().startsWith("look")) {
            return new Look(place, character);
        }
        //If user types in go then try to move the character to specified direction
        else if (userInput.toLowerCase().trim().startsWith("go")) {
            String[] directions = userInput.toLowerCase().split("go");
            String direction = directions[1].trim();
            return new Go(place, character, direction);
        }
        //If user types in get then try to pick up artifact in current location
        else if (userInput.toLowerCase().trim().startsWith("get")) {

            String[] getArtifact = userInput.toLowerCase().split("get");
            String artifact = getArtifact[1].trim();
            return new Get(place, character, artifact);
        }
        //If user types in drop then drop an artifact in current location
        else if (userInput.toLowerCase().trim().startsWith("drop")) {
            String[] dropArtifact = userInput.toLowerCase().split("drop");
            String artifact = dropArtifact[1].trim();
            return new Drop(place, character, artifact);
        }
        //If user types in use, then try to use the specified artifact to see if it does anything based on the current location
        else if (userInput.toLowerCase().trim().startsWith("use")) {

            String[] useArtifact = userInput.toLowerCase().split("use");
            String artifact = useArtifact[1].trim();
            return new Use(place, character, artifact);
        }
        //If user types in inv or inventory then display the player's current inventory
        else if (userInput.toLowerCase().contains("inv") || userInput.toLowerCase().contains("inventory")) {
            return new Inventory(character);
        }
        //If user types in help, then display a list of commands available in the game
        else if (userInput.toLowerCase().contains("help")) {
            return new Help();
        }
        //If user types quit or exit, then remove character from the game
        else if (userInput.toLowerCase().contains("quit") || userInput.toLowerCase().contains("exit")) {
            return new Exit(character);
        }
        //If user types in random gibberish, then let them know
        else {
            return new NotFound(character);
        }

    }
}
