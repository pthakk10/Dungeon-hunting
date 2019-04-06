/*
Author: Giovanni Alanis
ACCC: galani3
UIN: 657681506
*/

import java.util.HashMap;
import java.util.Scanner;

public class Game {

    private String gameName;
    private static HashMap<String, Character> characters = new HashMap<String, Character>();


    /**
     * Creates a game object that will read in a gdf file and create place, character, artifact, direction objects
     * to setup the game
     *
     * @param inputFile A gdf file containing data on how the game should be setupt
     * @return Game Object
     */
    //Creates a new game object with it's name
    Game(Scanner inputFile) {

        String[] fileLine;
        String[] parsedFileLine;
        int startingLocation = 0;
        double version = 3.0;
        int players = 0;

        while (inputFile.hasNextLine()) {

            //Read in a line from the file
            fileLine = inputFile.nextLine().split("//");
            parsedFileLine = fileLine[0].split("\\s+");

            //Check if the line begins with one of the magic words
            if (parsedFileLine[0].equals("GDF")) {

                //Print out game version
                version = Double.parseDouble(parsedFileLine[1].trim());
                System.out.println("Version: " + version);

                //Print out game name
                String gameName = "";
                for (int i = 2; i < parsedFileLine.length; i++) {
                    gameName += parsedFileLine[i] + " ";
                }
                System.out.println("Game Name: " + gameName);
                this.gameName = gameName;
            } else if (parsedFileLine[0].equals("PLACES")) {

                //Read in the number of places in the game
                int numOfPlaces = Integer.parseInt(parsedFileLine[1].trim());

                for (int i = 0; i < numOfPlaces; i++) {

                    //Use scanner helper to find the next valid file line
                    String illumination = ScannerHelper.getEmptyLine(inputFile).trim();

                    if (illumination.equals("LIGHT")) {
                        //Pass input file to place constructor and build out the places
                        Place place = new Place(inputFile);

                        //Store the initial starting place just in case no players are found in data file
                        if (i == 0) {
                            startingLocation = place.id();
                        }
                    } else {
                        DarkPlace darkPlace = new DarkPlace(inputFile);

                        //Store the initial starting place just in case no players are found in data file
                        if (i == 0) {
                            startingLocation = darkPlace.id();
                        }
                    }

                    //If the gdf file is version 3.0 then we have to manually create a player object for the player and use the first place as the starting location
                    if (i == 0 && version >= 3.0 && version < 4.0) {
                        Player player = new Player(1, startingLocation, "1", "A character who is easily controlled by a keyboard");
                        characters.put(player.name(), player);
                    }
                }

                //Hardcode the nowhere and exit place for now
                Place nowhere = new Place(0, "Nowhere", "Leads to nowhere");
                Place exit = new Place(1, "exit", "Lead to exit");

            } else if (parsedFileLine[0].equals("DIRECTIONS")) {

                //Read in the number of directions in the game
                int numOfDirections = Integer.parseInt(parsedFileLine[1].trim());

                for (int i = 0; i < numOfDirections; i++) {

                    //Pass input file to direction constructor and build out the directions
                    Direction direction = new Direction(inputFile);
                }

            } else if (parsedFileLine[0].equals("ARTIFACTS")) {

                //Read in the number of artifacts
                int numOfArtifacts = Integer.parseInt(parsedFileLine[1]);

                for (int i = 0; i < numOfArtifacts; i++) {

                    //Check what type of artifact it is
                    parsedFileLine = ScannerHelper.getEmptyLine(inputFile).split("\\s+");

                    if (parsedFileLine[0].trim().equals("NORMAL")) {
                        //Pass input file to artifact constructor and build out the artifacts
                        Artifact artifact = new Artifact(inputFile);
                    } else if (parsedFileLine[0].trim().equals("LIGHT")) {
                        LightArtifact lightArtifact = new LightArtifact(inputFile);
                    } else {
                        int healthRegeneration = Integer.parseInt(parsedFileLine[1].trim());
                        HealthArtifact healthArtifact = new HealthArtifact(inputFile, healthRegeneration);
                    }


                }
            } else if (parsedFileLine[0].equals("CHARACTERS")) {
                //Read in the number of characters
                int numOfCharacters = Integer.parseInt(parsedFileLine[1]);

                for (int i = 0; i < numOfCharacters; i++) {


                    //Split line that contains either PLAYER or NPC <NORMAL OR ATTACKER>
                    fileLine = ScannerHelper.getEmptyLine(inputFile).split("//");
                    parsedFileLine = fileLine[0].split("\\s+");


                    //If its a player then create a player object, else create an NPC object and store it in
                    //the collection in game
                    if (parsedFileLine[0].trim().equals("PLAYER")) {
                        Player player = new Player(inputFile, version);
                        characters.put(player.name(), player);
                        players++;
                    } else {

                        //Check to see if the NPC should be a regular NPC or Attacking NPC
                        if (parsedFileLine[1].trim().equals("NORMAL")) {
                            NPC npc = new NPC(inputFile, version);
                            characters.put(npc.name(), npc);
                        } else if (parsedFileLine[1].trim().equals("GIVER")) {
                            Giver giver = new Giver(inputFile, version);
                            characters.put(giver.name(), giver);
                        } else {
                            Attacker attacker = new Attacker(inputFile, version);
                            characters.put(attacker.name(), attacker);
                        }
                    }

                }

            }

        }

        //If the gdf version is 4.0 or greater and no players are specified in the gdf file, then ask
        //user for players
        if (players <= 0 && version >= 4.0) {
            System.out.print("How many players are playing?: ");
            Scanner input = KeyboardScanner.getKeyboardScanner();
            int numOfPlayers = Integer.parseInt(input.nextLine());

            for (int i = 1; i < numOfPlayers + 1; i++) {

                System.out.print("[Player " + i + "] Enter your name: ");
                String name = input.nextLine();
                System.out.print("[Player " + i + "] Enter a description of yourself: ");
                String description = input.nextLine();
                System.out.println();

                Player player = new Player(i, startingLocation, name, description);
                characters.put(player.name(), player);

            }

        }

    }

    //Print game details for debugging
    public void print() {
        System.out.println("Name of the game: " + this.gameName);
    }

    /**
     * Goes through collection of characters in the game and asks them to make a move.
     * Once everyone has made a move, the game executes the moves and then repeats the cycle.
     */
    public void play() {

        //Get keyboard scanner
        Scanner input = KeyboardScanner.getKeyboardScanner();

        //Print out welcome title
        System.out.println("------------------------------------");
        System.out.println("| Welcome to " + this.gameName + " |");
        System.out.println("------------------------------------");


        boolean endGame = false;

        //If the number of players is greater than 0, then keep the game playing. Else, exit the game
        while (!endGame) {

            //Attempt to end the game, the game will not end if there is still a playing player to prevent this
            endGame = true;

            //Go through each character and check if they are still playing, if so then ask them to make a move depending on whether they are a PLAYER or NPC
            for (Character character : characters.values()) {
                if (character.isPlaying() && character instanceof Player) {
                    Move move = character.makeMove();
                    move.execute();

                    endGame = false;
                } else if (character.isPlaying() && character instanceof NPC) {
                    Move move = character.makeMove();
                    move.execute();
                }
            }

        }

        System.out.println("Thanks for playing!");
    }
}
