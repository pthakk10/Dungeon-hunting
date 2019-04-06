public class Inventory implements Move {

    private Character character;

    /**
     * Creates a Inventory object in where it displays a character's current inventory
     *
     * @param character The character to check inventory from
     * @return Inventory object
     */
    public Inventory(Character character) {
        this.character = character;
    }

    /**
     * Executes the inventory object to display the characters' current inventory
     */
    @Override
    public void execute() {
        int totalValue = 0;
        int totalMobility = 0;

        //Display the character's inventory
        System.out.println("================================================");
        System.out.println("PLAYER: " + character.name() + " Inventory");
        System.out.println("================================================");
        for (Artifact userArtifact : this.character.retrieveInventory().values()) {
            System.out.println("Name: " + userArtifact.name());
            System.out.println("Value: " + userArtifact.value());
            System.out.println("Description: " + userArtifact.description());

            //Check to see if artifact weights more than one pound
            if (userArtifact.size() > 1) {
                System.out.println("Mobility: " + userArtifact.size() + " pounds");
            } else {
                System.out.println("Mobility: " + userArtifact.size() + " pound.");
            }

            //Calculate total value and total mobility in inventory
            totalValue += userArtifact.value();
            totalMobility += userArtifact.size();
            System.out.println();

        }

        //Print out statistics
        System.out.println("Total Value: " + totalValue);
        System.out.println("Total Mobility: " + totalMobility);
        System.out.println();
    }
}
