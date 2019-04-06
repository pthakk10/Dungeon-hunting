public class Get implements Move {
    private Place place;
    private Character character;
    private String artifact;

    /**
     * Creates a Get object for a character trying to pickup a certain item
     *
     * @param place     Current place of the character
     * @param character Character who is trying to pickup an artifact
     * @param artifact  The artifact that the character is trying to pickup
     * @return Get Object
     */
    public Get(Place place, Character character, String artifact) {
        this.place = place;
        this.character = character;
        this.artifact = artifact;
    }

    /**
     * Will execute Get move to check if a character is able to pickup a certain artifact or not
     */
    @Override
    public void execute() {
        //If the character is a player, then print out event to notify the player.
        //Else if it is a NPC, then we can suppress the output.
        if (this.character instanceof Player) {

            //Check to see if the place is illuminated first to be able to get an artifact
            if (place.checkIllumination()) {

                //Check if the place contains the artifact the character is trying to pickup
                if (this.place.getAvailableArtifacts().containsKey(this.artifact)) {

                    //If found, then add artifact to character's inventory and then remove it from the place
                    this.character.addArtifact(this.place.getAvailableArtifacts().get(this.artifact));
                    this.place.removeArtifact(this.artifact);

                    //Notify the user of event
                    System.out.println("================================================");
                    System.out.println("* PLAYER: " + character.name() + " added " + this.artifact + " to their inventory");
                    System.out.println("================================================\n");
                } else {

                    //Notify user if artifact is not available
                    System.out.println("================================================");
                    System.out.println("* PLAYER: " + character.name() + " tried picking up " + this.artifact + ", but failed miserably");
                    System.out.println("================================================\n");
                }
            }
            else
            {
                System.out.println("================================================");
                System.out.println("* PLAYER: " + character.name() + " tried getting an item, but forgot it is completely dark");
                System.out.println("================================================\n");
            }
        } else {

            //For NPC
            //Check if the place contains the artifact the character is trying to pickup
            if (this.place.getAvailableArtifacts().containsKey(this.artifact)) {

                //If found, then add artifact to character's inventory and then remove it from the place
                this.character.addArtifact(this.place.getAvailableArtifacts().get(this.artifact));
                this.place.removeArtifact(this.artifact);

            } else {
                //For debugging, just in case somehow this event occurs
                System.out.println("Error: NPC trying to pickup item that does not exist");
            }
        }
    }
}
