public class Drop implements Move {
    private Place place;
    private Character character;
    private String artifact;

    /**
     * Creates a drop object that will try to drop an artifact from a character's inventory
     *
     * @param character Character that will try to drop something from inventory
     * @param artifact  Artifact that the character will try to drop
     * @return Drop object
     */
    public Drop(Place place, Character character, String artifact) {
        this.place = place;
        this.character = character;
        this.artifact = artifact;
    }

    /**
     * Tries to drop an object from character's inventory if the specified artifact exists
     *
     * @return Execute Object
     */
    @Override
    public void execute() {
        //Check to see if the character is a player, if so then we can print out to the terminal
        if (this.character instanceof Player) {

            //Check if character contains artifact in inventory
            if (this.character.checkForArtifact(this.artifact)) {

                //If so, then add artifact to current place and remove it from character
                this.place.addArtifact(this.character.retrieveArtifactFromInventory(this.artifact));
                this.character.removeArtifact(this.artifact);

                //Notify user of event
                System.out.println("================================================");
                System.out.println("PLAYER: " + character.name() + " dropped " + this.artifact);
                System.out.println("================================================\n");
            } else {
                System.out.println("You are not carrying that item");
            }
        } else {
            //If character is NPC then we suppress the output
            if (this.character.checkForArtifact(this.artifact)) {
                this.place.addArtifact(this.character.retrieveArtifactFromInventory(this.artifact));
                this.character.removeArtifact(this.artifact);
            } else {
                System.out.println("Error: NPC is trying to drop item that it does not have");
            }
        }
    }
}
