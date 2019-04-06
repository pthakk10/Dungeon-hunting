public class Help implements Move {

    /**
     * Creates a help object in where a user can see all the available commands in the game
     */
    public Help() {

    }

    @Override
    public void execute() {
        System.out.println("=================================");
        System.out.println("|       Available Commands      |");
        System.out.println("=================================");
        System.out.println("LOOK                ;Display the name of the current location and any artifacts found");
        System.out.println("GO XXX              ;Attempt to go in the direction XXX where XXX is any direction on the compass");
        System.out.println("GET XXX             ;Attempt to pick up artifact XXX from current location");
        System.out.println("DROP XXX            ;Drop an artifact XXX from the inventory in the current location");
        System.out.println("USE XXX             ;Use artifact XXX from the inventory in the current location");
        System.out.println("INV or INVENTORY    ;Display the player's inventory");
        System.out.println("HELP                ;Displays this help menu\n");
    }
}
