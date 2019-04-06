/*
Author: Giovanni Alanis
ACCC: galani3
UIN: 657681506
*/

import java.util.*;

public class Place {

    private int id;
    private String name;
    private String description;
    protected boolean illuminated = true;
    private ArrayList<Direction> directions = new ArrayList<Direction>();
    private HashMap<String, Artifact> availableArtifacts = new HashMap<String, Artifact>();
    private HashMap<String, Character> characters = new HashMap<String, Character>();
    static private HashMap<Integer, Place> availablePlaces = new HashMap<Integer, Place>();

    Place(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        availablePlaces.put(id, this);
    }

    /**
     * Creates a Place object by passing in a gdf file
     *
     * @param inputFile A file containing properties of a Place object
     * @return Place Object
     */
    Place(Scanner inputFile) {

        String placeName;
        String description = "";
        int numOfDescriptionLines;
        int placeId;

        placeId = inputFile.nextInt();
        placeName = inputFile.nextLine().split("//")[0].trim();

        numOfDescriptionLines = inputFile.nextInt();
        inputFile.nextLine();

        for (int j = 0; j < numOfDescriptionLines; j++) {
            description += inputFile.nextLine() + " ";
        }

        this.id = placeId;
        this.name = placeName;
        this.description = description;

        //Store created place in collection of available places
        availablePlaces.put(placeId, this);

    }

    //Sets the name for the place
    public String name() {
        return this.name;
    }

    //Sets the description for the place
    public String description() {
        return this.description;
    }

    //Returns the id of the place
    public int id() {
        return this.id;
    }

    //Adds a new direction object to the list of current directions available
    public void addDirection(Direction newDirection) {
        this.directions.add(newDirection);
    }

    //Tries to follow the direction given by user and will return a new place if it is available
    public Place followDirection(String givenDirection) {
        for (Direction direction : this.directions) {
            if (direction.match(givenDirection)) {
                return direction.follow();
            }
        }
        return this;
    }

    //Print details about place for debugging
    public static void printAll() {
        System.out.println("Number of places: " + availablePlaces.size());
        for (Place place : availablePlaces.values()) {
            System.out.println("----------------- BEGIN ---------------------");
            place.print();

            System.out.println("Available Characters: ");
            for (Character character : place.characters.values()) {
                character.print();
            }

            System.out.println("Available Directions: ");
            for (Direction direction : place.directions) {
                direction.print();
            }
            System.out.println("Available Artifacts: ");
            for (Artifact artifact : place.availableArtifacts.values()) {
                artifact.print();
            }
            System.out.println("----------------- END -----------------------\n");
        }
    }

    //A more simplified version of printAll, but for only displaying the place
    public void print() {
        System.out.println("====================================");
        System.out.println("    Place: " + this.name);
        System.out.println("====================================");
        System.out.println("ID: " + this.id);
        System.out.println("Description: " + this.description + "\n");

    }

    //Used in game to display any places
    public void display() {
        System.out.println(">" + this.name);
        System.out.println("Description: " + this.description);
    }

    //Adds a specified artifact to the place
    public void addArtifact(Artifact artifact) {
        availableArtifacts.put(artifact.name(), artifact);
    }

    //Removes a specified artifact to the place
    public void removeArtifact(String artifactName) {
        if (availableArtifacts.containsKey(artifactName)) {
            availableArtifacts.remove(artifactName);
        }
    }

    //Checks to see if any direction in the current place matches the key patter of the given artifact
    public boolean useKey(Artifact artifact) {

        //Return either true or false to notify user if an artifact actually did something
        boolean eventHappened = false;
        for (Direction direction : this.directions) {
            if (direction.useKey(artifact)) {
                eventHappened = true;
            }
        }
        return eventHappened;
    }

    //Returns available artifacts from place
    public HashMap<String, Artifact> getAvailableArtifacts() {

        return this.availableArtifacts;
    }

    //Returns a place from specified id
    public static Place getPlaceById(int id) {
        if (availablePlaces.containsKey(id)) {
            return availablePlaces.get(id);
        } else {
            System.out.println("Could not find Place with Id: " + id);
            return null;
        }
    }

    //Returns a random place from collection of places
    public static Place getRandomPlace() {
        Random rand = new Random();

        int rand_int = 0;
        int counter = 0;

        //Select random number for place
        rand_int = rand.nextInt(availablePlaces.size());

        //TODO Might need to check the random place generator for a bug
        //Select a random place and make sure it is not an exit
        for (Place place : availablePlaces.values()) {
            if (counter == rand_int && (place.id != 0 || place.id != 1)) {
                return place;
            } else if (counter == rand_int && (place.id == 0 || place.id == 1)) {
                counter = 0;
                rand_int = rand.nextInt(availablePlaces.size());
            }

            counter++;
        }

        return null;
    }

    //Adds a character to the place
    public void addCharacter(Character character) {
        characters.put(character.name(), character);
    }

    //Removes a character from the place
    public void removeCharacter(String characterName) {

        //Check to see if the place even has the character
        if (characters.containsKey(characterName)) {

            //If so then remove the character
            characters.remove(characterName);
        } else {
            System.out.println("There doesn't seem to be a character at this location");
        }
    }

    //Get available directions in place
    public ArrayList<Direction> getDirections() {
        return this.directions;
    }

    //Get characters available in place
    public HashMap<String, Character> getCharacters() {
        return this.characters;
    }


    //Template for using a specific artifact in the place. This will be overriden by child classes
    public void use(Artifact artifact) {

    }

    //Check to the illumination of the current place
    public boolean checkIllumination() {
        return this.illuminated;
    }

}
