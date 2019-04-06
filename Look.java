public class Look implements Move {

    private Place place;
    private Character character;

    /**
     * Creates a Look object for a character that is trying to see what is available in the current place
     *
     * @param place     The place in where the character is currently in
     * @param character The character who is trying to see what is available
     */
    public Look(Place place, Character character) {
        this.place = place;
        this.character = character;
    }

    /**
     * Executes look object and displays what is available in current place
     */
    @Override
    public void execute() {

        if (place.checkIllumination()) {
            //Notify user of event
            System.out.println("================================================");
            System.out.println("* PLAYER: " + character.name() + " looked around");
            System.out.println("================================================");
            System.out.println("Current location: " + this.place.name());

            System.out.println();

            //Print out any artifacts available if there is any
            if (this.place.getAvailableArtifacts().size() > 0) {
                System.out.println("Artifacts Available: ");
                for (Artifact artifact : this.place.getAvailableArtifacts().values()) {
                    System.out.println(">" + artifact.name() + ": " + artifact.description());
                }
                System.out.println();
            }

            //Prints out any characters available if there is any
            if (this.place.getCharacters().size() > 1) {
                System.out.println("Characters Present: ");
                for (Character character : this.place.getCharacters().values()) {
                    //Print out all the other characters in the current place not including the current character
                    if (this.character.name() != character.name()) {
                        character.display();
                    }
                }
            }
        }

        //If the place is dark, then let the player know they are not allowed to look around
        else
        {
            System.out.println("=====================================================================================");
            System.out.println("* PLAYER: " + character.name() + " tried looking around, but the place is pitch black");
            System.out.println("=====================================================================================");
        }

        System.out.println();
    }

}
