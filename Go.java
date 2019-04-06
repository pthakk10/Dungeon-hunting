public class Go implements Move {

    private Place place;
    private String direction;
    private Character character;

    /**
     * Creates a Go object for a character trying to move in a certain direction
     *
     * @param place     Current place the character is in
     * @param character The Character who is trying to move
     * @param direction The direction in where the character is trying to go
     * @return Go object
     */
    public Go(Place place, Character character, String direction) {
        this.place = place;
        this.direction = direction;
        this.character = character;
    }

    /**
     * Will execute Go object to check if a character is able to move in a specified direction
     */
    @Override
    public void execute() {

        //Remove the character from the current place since it will move to a new location
        this.place.removeCharacter(character.name());

        //Try to follow the specified direction to see if it is available
        Place attemptedPlace = this.place.followDirection(this.direction);

        //If it is a player and a new place is returned, then the player has moved successfully
        if (!attemptedPlace.equals(this.place) && this.character instanceof Player) {
            System.out.println("================================================");
            System.out.println("PLAYER: " + character.name() + " went " + this.direction);
            System.out.println("================================================");
            this.place = attemptedPlace;
        }

        //If the same place is returned in where the player was in, then that means the direction was locked or non existant
        else if (attemptedPlace.equals(this.place) && this.character instanceof Player) {
            System.out.println("================================================");
            System.out.println("PLAYER: " + character.name() + " tried going " + this.direction + ", but either the door was locked or you cannot head in that direction");
            System.out.println("================================================");
        }

        //Add the character to the new location
        this.place.addCharacter(this.character);

        //Update the characters current location based on attempted direction
        this.character.updateLocation(this.place);
    }
}
