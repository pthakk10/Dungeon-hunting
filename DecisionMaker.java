public interface DecisionMaker {
    /**
     * Retrieves a move object based on whether a character is a Player or NPC
     *
     * @param character Character to decide what move to make on
     * @param place     Current place of the character
     * @return Move object
     */
    Move getMove(Character character, Place place);
}
