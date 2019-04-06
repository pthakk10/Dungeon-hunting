/*
Author: Giovanni Alanis
ACCC: galani3
UIN: 657681506
*/

import java.util.Scanner;

public class Direction {

    private enum DirType {
        NORTH("NORTH", "N"),
        SOUTH("SOUTH", "S"),
        EAST("EAST", "E"),
        WEST("WEST", "W"),
        UP("UP", "U"),
        DOWN("DOWN", "D"),
        NORTHEAST("NORTHEAST", "NE"),
        NORTHWEST("NORTHWEST", "NW"),
        SOUTHEAST("SOUTHEAST", "SE"),
        SOUTHWEST("SOUTHWEST", "SW"),
        NORTH_NORTHEAST("NORTH-NORTHEAST", "NNE"),
        NORTH_NORTHWEST("NORTH-NORTHWEST", "NNW"),
        EAST_NORTHEAST("EAST-NORTHEAST", "ENE"),
        WEST_NORTHWEST("WEST-NORTHWEST", "WNW"),
        EAST_SOUTHEAST("EAST-SOUTHEAST", "ESE"),
        WEST_SOUTHWEST("WEST-SOUTHWEST", "WSW"),
        SOUTH_SOUTHEAST("SOUTH-SOUTHEAST", "SSE"),
        SOUTH_SOUTHWEST("SOUTH-SOUTHWEST", "SSW");

        private String text;
        private String abbreviation;

        DirType(String text, String abbreviation) {
            this.text = text;
            this.abbreviation = abbreviation;
        }

        public String toString() {
            return this.text;
        }

        public boolean match(String direction) {
            if (direction.trim().equalsIgnoreCase(this.text) || direction.trim().equalsIgnoreCase(this.abbreviation)) {
                return true;
            } else {
                return false;
            }
        }


    }

    private int id;
    private Place from;
    private Place to;
    private boolean isLocked = false;
    private int lockPattern;
    private DirType directionType;

    /**
     * Creates a Direction object by passing in a file
     *
     * @param inputFile A file containing properties of a direction object
     * @return Direction Object
     */
    //Create a new direction object with its id, from, to, and direction
    Direction(Scanner inputFile) {
        String[] fileLine;
        String[] parsedFileLine;

        fileLine = ScannerHelper.getEmptyLine(inputFile).split("//");
        parsedFileLine = fileLine[0].split("\\s+");
        while (parsedFileLine[0].isEmpty()) {
            fileLine = inputFile.nextLine().split("//");
            parsedFileLine = fileLine[0].trim().split("\\s+");
        }

        //Set the id
        this.id = Integer.parseInt(parsedFileLine[0].trim());

        //Retrieve what the from place is going to be
        Place fromPlace = Place.getPlaceById(Integer.parseInt(parsedFileLine[1].trim()));
        this.from = fromPlace;

        //Determine what direction type this direction will be
        if (parsedFileLine[2].equals("N")) {
            directionType = DirType.NORTH;
        } else if (parsedFileLine[2].equals("E")) {
            directionType = DirType.EAST;
        } else if (parsedFileLine[2].equals("S")) {
            directionType = DirType.SOUTH;
        } else if (parsedFileLine[2].equals("W")) {
            directionType = DirType.WEST;
        } else if (parsedFileLine[2].equals("U")) {
            directionType = DirType.UP;
        } else if (parsedFileLine[2].equals("D")) {
            directionType = DirType.DOWN;
        } else if (parsedFileLine[2].equals("NE")) {
            directionType = DirType.NORTHEAST;
        } else if (parsedFileLine[2].equals("NW")) {
            directionType = DirType.NORTHWEST;
        } else if (parsedFileLine[2].equals("SE")) {
            directionType = DirType.SOUTHEAST;
        } else if (parsedFileLine[2].equals("SW")) {
            directionType = DirType.SOUTHWEST;
        } else if (parsedFileLine[2].equals("NNE")) {
            directionType = DirType.NORTH_NORTHEAST;
        } else if (parsedFileLine[2].equals("NNW")) {
            directionType = DirType.NORTH_NORTHWEST;
        } else if (parsedFileLine[2].equals("ENE")) {
            directionType = DirType.EAST_NORTHEAST;
        } else if (parsedFileLine[2].equals("WNW")) {
            directionType = DirType.WEST_NORTHWEST;
        } else if (parsedFileLine[2].equals("ESE")) {
            directionType = DirType.EAST_SOUTHEAST;
        } else if (parsedFileLine[2].equals("WSW")) {
            directionType = DirType.WEST_SOUTHWEST;
        } else if (parsedFileLine[2].equals("SSE")) {
            directionType = DirType.SOUTH_SOUTHEAST;
        } else if (parsedFileLine[2].equals("SSW")) {
            directionType = DirType.SOUTH_SOUTHWEST;
        } else {
            directionType = null;
        }

        //Check to see if the direction is locked or not
        if (parsedFileLine[3].trim().contains("-") || parsedFileLine[3].trim().matches("0")) {
            this.isLocked = true;

            //Remove the negative if it is locked to get the place or check it is an exit
            if (parsedFileLine[3].trim().contains("-")) {
                String[] split_to_id = parsedFileLine[3].split("-");
                this.to = Place.getPlaceById(Integer.parseInt(split_to_id[1]));
            } else {
                this.to = Place.getPlaceById(Integer.parseInt(parsedFileLine[3]));
            }
        } else {
            this.to = Place.getPlaceById(Integer.parseInt(parsedFileLine[3].trim()));
        }

        //Assign lock pattern
        this.lockPattern = Integer.parseInt(parsedFileLine[4]);

        //Add this direction to the necessary place
        fromPlace.addDirection(this);

    }


    //Check to see if this direction matches the given direction
    public boolean match(String s) {
        return this.directionType.match(s);
    }

    //Lock this place
    public void lock() {
        this.isLocked = true;
    }

    //Unlock this place
    public void unlock() {
        this.isLocked = false;
    }

    //Check to see if this place is locked
    public boolean isLocked() {
        return this.isLocked;
    }

    //Return the place this direction goes to if it is unlocked, else return the original place
    public Place follow() {
        if (!this.isLocked) {
            return this.to;
        } else {
            return this.from;
        }

    }

    //Print details about direction for debugging
    public void print() {
        System.out.println("====================================");
        System.out.println("    Direction: " + this.directionType.toString());
        System.out.println("====================================");
        System.out.println("ID: " + this.id);
        System.out.println("From: " + this.from.name());
        System.out.println("To: " + this.to.name());
        System.out.println("Locked? : " + this.isLocked);
        System.out.println("Lock Pattern: " + this.lockPattern + "\n");
    }

    //Attempt to use an artifact in this direction
    public boolean useKey(Artifact artifact) {
        if (artifact.use() > 0 && artifact.use() == this.lockPattern) {
            unlock();
            return true;
        } else {
            return false;
        }
    }

    //Return the type of direction this is
    public String directionType() {
        return this.directionType.toString();
    }
}
