import java.util.HashMap;
import java.util.Scanner;

public class Character {

    private int id;
    private int placeId;
    private int health = 100;
    private String name;
    private String description;
    private Place current;
    private DecisionMaker decisionMaker;
    private static HashMap<Integer, Character> availableCharacters = new HashMap<Integer, Character>();
    private HashMap<String, Artifact> inventory = new HashMap<String, Artifact>();
    private boolean isPlaying = true;

    /**
     * Creates a character object by passing in a file
     *
     * @param inputFile A file containing properties of a character object
     * @return Character Object
     */
    public Character(Scanner inputFile, double version) {

        if (version < 5.0) {
            this.placeId = inputFile.nextInt();
            inputFile.nextLine();
        } else {
            this.placeId = Integer.parseInt(inputFile.nextLine().split("//")[0].trim());
        }

        //Set id and name
        this.id = inputFile.nextInt();
        this.name = inputFile.nextLine().trim();

        int numOfDescription = inputFile.nextInt();
        inputFile.nextLine();

        String description = " ";
        for (int i = 0; i < numOfDescription; i++) {
            description += inputFile.nextLine().trim() + " ";
        }
        this.description = description;

        //Retrieve the place or find a random place to put the character in
        if (this.placeId > 0) {
            this.current = Place.getPlaceById(this.placeId);
            Place place = Place.getPlaceById(this.placeId);
            place.addCharacter(this);
        } else {
            Place place = Place.getRandomPlace();
            this.current = place;
            place.addCharacter(this);
        }

        //Add character to the total characters collection in the game
        availableCharacters.put(this.id, this);
    }

    public Character(int id, int placeId, String name, String description) {
        this.id = id;
        this.placeId = placeId;
        this.name = name;
        this.description = description;

        if (this.placeId > 0) {
            this.current = Place.getPlaceById(this.placeId);
            Place place = Place.getPlaceById(this.placeId);
            place.addCharacter(this);
        } else {
            Place place = Place.getRandomPlace();
            this.current = place;
            place.addCharacter(this);
        }

    }

    //Returns a character based on ID
    public static Character getCharacterByID(int id) {
        if (availableCharacters.containsKey(id)) {
            return availableCharacters.get(id);
        } else {
            System.out.println("Unable to find character with id of " + id);
            return null;
        }
    }

    //Adds artifact to the character's inventory
    public void addArtifact(Artifact artifact) {
        this.inventory.put(artifact.name(), artifact);
    }

    //Removes artifact from the character's inventory
    public void removeArtifact(String artifactName) {
        if (this.inventory.containsKey(artifactName)) {
            this.inventory.remove(artifactName);
        }
    }

    //Used to display character information in game
    public void display() {
        System.out.println(">" + this.name());
        System.out.println("Description: " + this.description);
    }

    //Used to debug the character
    public void print() {
        System.out.println("====================================");
        System.out.println("Character: " + name());
        System.out.println("====================================");
        System.out.println("ID: " + this.id);
        System.out.println("Current location: " + this.current.name());
        System.out.println("Name: " + this.name);
        System.out.println("Description: " + this.description + "\n");
        System.out.println("Inventory: ");
        for (Artifact artifact : this.inventory.values()) {
            System.out.println(artifact.name());
        }
    }

    //Prints all the characters in the game
    public static void printAll() {
        System.out.println("Number of characters: " + availableCharacters.size());
        for (Character character : availableCharacters.values()) {
            character.print();
        }
    }

    //Return character name
    public String name() {
        return this.name;
    }

    //Assigns the Decision Maker for the character based on if it's a Player or NPC
    public void setDecisionMaker(DecisionMaker decisionMaker) {
        this.decisionMaker = decisionMaker;
    }

    //Generates a move to be executed on the character
    public Move makeMove() {
        return decisionMaker.getMove(this, this.current);
    }

    //Checks if character is carrying an artifact
    public boolean checkForArtifact(String artifactName) {
        return this.inventory.containsKey(artifactName);
    }

    //Returns artifact in character's inventory
    public Artifact retrieveArtifactFromInventory(String artifactName) {
        return this.inventory.get(artifactName);
    }

    //Returns entire inventory of character
    public HashMap<String, Artifact> retrieveInventory() {
        return this.inventory;
    }

    //Updates character's location when moving place to place
    public void updateLocation(Place place) {
        this.current = place;
    }

    //Update the character isPlaying if they decide to leave the game
    public void leaveGame() {
        this.isPlaying = false;
    }

    //Used to check if the current player is still in the game
    public boolean isPlaying() {
        return this.isPlaying;
    }

    //Display the character's current health
    public int checkHealth() {
        return this.health;
    }

    //Deplete the character's health
    public void depleteHealth(int damage) {
        this.health -= damage;
    }

    //Increase the character's health
    public void increaseHealth(int regeneration) {
        this.health += regeneration;
        if (this.health > 100) {
            this.health = 100;
        }
    }

}
