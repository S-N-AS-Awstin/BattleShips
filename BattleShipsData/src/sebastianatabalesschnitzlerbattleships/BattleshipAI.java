package sebastianatabalesschnitzlerbattleships;

import static sebastianatabalesschnitzlerbattleships.Coordinates.acceptedChars;

/**
 * This class holds methods that determine the AI opponents actions.
 * From ship placement to attack patterns, most are randomly generated, but has guidance and game logic put in.
 * @author SEBASTIAN ATABALES-SCHNITZLER
 */
public class BattleshipAI {

    public static int count = 0; // acts as an index (In AiGuess)
    static Coordinates[] shotCount = new Coordinates[49]; // How many Shots the AI CAN take in a game (In AiGuess)
            
   /** aiFleetFormation - For forming the AI's fleet. It takes the initialized ship objects
    * and gives them all values. It does not create new objects, it only provides values for the existing arrays.
    * Note: All Parameters are sent from BattleShipsBattke.java
    * @param sub1 - The empty array Coordinates of the First Submarine object
    * @param sub2 - The empty array Coordinates of the Second Submarine object
    * @param dest - The empty array Coordinates of the Destroyer object
    * @param batt - The empty array Coordinates of the BattleShip object
    * @param airC - The empty array Coordinates of the Aircraft Carrier object
    * @return - Doesn't directly return anything, rather gives all the array parameters values
    */
    public static void aiFleetFormation(Coordinates[] sub1, Coordinates[] sub2, Coordinates[] dest, Coordinates[] batt,Coordinates[] airC)
    {
        // Aircraft Carrier is made first, because it is the largest one and will have the least trouble in finding a spot.
        aiShipFormation(airC, 0);
        
        // The Battleship is Made 2nd
        aiShipFormation(batt, 1);
        // Ensures no coordinates overlap eachother.
        while(isColliding(batt, airC, null, null, null, 0) == true)
        {
            aiShipFormation(batt, 1);
        }
        
        // The Destroyer is made 3rd
        aiShipFormation(dest, 2);
        while(isColliding(dest, airC, batt, null, null, 1) == true)
        {
            aiShipFormation(dest, 2);
        }
        
        // The Second Submarine is made 4th
        aiShipFormation(sub2, 3);
        while(isColliding(sub2, airC, batt, dest, null, 2) == true)
        {
            aiShipFormation(sub2, 3);
        }
        
        // The First Submarine is made 5th
        aiShipFormation(sub1, 3);
        while(isColliding(sub1, airC, batt, dest, sub2, 3) == true)
        {
            aiShipFormation(sub1, 3);
        }

        // theWholeFleetDEBUG(sub1, sub2, dest, batt, airC); - DEBUG COMMAND
    }
    // METHOD IS FOR DEBUGGING PURPOSES: CHECKS VALUES OF ALL OBJECTS TO SEE FOR ERROR
    /*public static void theWholeFleetDEBUG(Coordinates[] sub1, Coordinates[] sub2, Coordinates[] dest, Coordinates[] batt,Coordinates[] airC)
    {
        System.out.println("AIRCRAFT CARRIER: ");
        toString(airC, 0);
        System.out.println("");
        System.out.println("BATTLESHIP: ");
        toString(batt, 0);
        System.out.println("");
        System.out.println("DESTROYER: ");
        toString(dest, 0);
        System.out.println("");
        System.out.println("SUBMARINE 2: ");
        toString(sub2, 0);
        System.out.println("");
        System.out.println("SUBMARINE 1: ");
        toString(sub1, 0);
        System.out.println("");
    }
    
    // DEBUGGING/TESTING METHOD
    public static void toString(Coordinates[] ship, int i)
    {
        if(i == ship.length)
        {
            return;
        }
        else{
            System.out.print("(");
            System.out.print(ship[i].letterCord + "" + ship[i].numCord);
            System.out.print(") ");
            toString(ship, i+1);
        }
    }*/
    
    
   /**
    * findChar - Finds the element value of the character parameter in the "acceptedChars" array
    * It does this by comparing the character itself to all of the elements in the acceptedChars array
    * until it finds the element it matches with. It then puts the element number into 'i'
    * @param c - Submitted Character, is used for comparison
    * @param i - acts as index (allowing for recursion), and stores the element number
    * @return returns the element position number.
    */
    public static int findChar(char c, int i)
    {
        if (c == acceptedChars[i])
        {
            return i;
        }
        else
        {
           return findChar(c, i+1);
        }
    }
   /**
    * isColliding - Determines if multiple ship's coordinates overlaps by comparing a ship of interest to the rest of the fleet.
    * @param fS - focus Ship - This ship is the one being compared to
    * @param s1 - ship 1 - Should be the ship that came prior to fS for comparison sake.
    * @param s2 - ship 2 - Same general purpose as ship 1, only one prior.
    * @param s3 - ship 3 - Same general purpose as ship 1, only two prior.
    * @param s4 - ship 4 - Same general purpose as ship 1, only three prior.
    * @param select - Navigates the switch statements to determine how many comparisons are happening
    * @return - True or false statement depending of if fS and s1-s4 have overlapping coordinates
    */
    public static boolean isColliding(Coordinates[] fS, Coordinates[] s1, Coordinates[] s2, Coordinates[] s3, Coordinates[] s4, int select)
    {
        switch (select)
        {
            case 0: // One comparison
                if(Coordinates.isValidTwo(fS, s1, null, null, null) == false)
                {
                    return true;
                }
                break;
            
            case 1: // Two comparisons
                if(Coordinates.isValidTwo(fS, s1, s2, null, null) == false)
                {
                    return true;
                }
                break;
            
            case 2: // Three comparisons
                if(Coordinates.isValidTwo(fS, s1, s2, s3, null) == false)
                {
                    return true;
                }
                break;
            
            case 3: // Four comparisons
                if(Coordinates.isValidTwo(fS, s1, s2, s3, s4) == false)
                {
                    return true;
                }
                break;
        }
        return false;
    }
   /**
    * aiShipConstruction - Through a series of recursion loops this method does the repetitive task of
    * defining or redefining Coordinate arrays. In other words it fills in the coordinates depending on ship size
    * as well as ship orientation.
    * @param s - ship - The coordinates that needs to either be defined or replaced
    * @param i - index - Allows for recursion, allowing for all elements in the array 's' to be defined.
    * @param c - character - The index of the character selected at random within the array 'acceptedChars'. Also is the Letter Coordinate (X AXIS)
    * @param n - number - the number coordinate. (Y AXIS)
    * @param choice - navigates the switch statement, for any of the constructors needs.
    * @return - Either newly defined coordinates or replacements of previous coordinates
    */
    public static Coordinates[] aiShipConstruction(Coordinates[] s, int i, int c, int n, int choice)
    {
        switch (choice)
        {
           case 0: // Horizontal
            if (i == s.length)
            {
            }
            else
            {
                s[i] = new Coordinates(acceptedChars[c + i], n);
                aiShipConstruction(s, i + 1, c, n, choice);
            }
            return s;
            
           case 1: // Vertical
               if (i == s.length)
            {
            }
            else
            {
                s[i] = new Coordinates(acceptedChars[c], n + i);
                aiShipConstruction(s, i + 1, c, n, choice);
            }
            return s;
            
           case 2: // Redefining Horizontal
            if (i == s.length)
            {
            }
            else
            {
                s[i].letterCord = acceptedChars[c + i];
                s[i].numCord = n;
                aiShipConstruction(s, i + 1, c, n, choice);
            }
            return s;
            
            case 3: // Redefining Vertically
            if (i == s.length)
            {
            }
            else
            {
                s[i].letterCord = acceptedChars[c];
                s[i].numCord = n + i;
                aiShipConstruction(s, i + 1, c, n, choice);
            }
            return s;
        }
        return s;
    }
    
   /**
    * aiShipFormation - Will randomly generate the X and Y coordinates of a ship or Coordinate arrays.
    * The generation of the coordinates will follow game logic, thus requiring different rules depending on the ship type.
    * @param ship1 - The 'ship' (in reality Coordinate array object) that needs to be defined
    * @param select - Navigates the switch statement according to the programs need.
    */
    private static void aiShipFormation(Coordinates[] ship1, int select)
    {
        switch (select)
        {
            // -------------------------------- Type: Aircraft Carrier --------------------------------
            case 0: 
                char airCraftRandX = acceptedChars[(int)Math.floor((Math.random() * 6))];
                // HORIZONTAL -
                if (airCraftRandX != 'G' && airCraftRandX != 'F' && airCraftRandX != 'E' && airCraftRandX != 'D'){
                    int airRandomYH = (int)Math.floor((Math.random() * 7) + 1); // Random Y coordinate from 1-7
                    int airCharIndexH = findChar(airCraftRandX, 0); // Finds the letter
                    if (ship1[0] != null){ // If this is a newly defined
                        aiShipConstruction(ship1, 0, airCharIndexH, airRandomYH, 2);
                    }
                    else{ // If this has already been defined and needs to be replaced
                        aiShipConstruction(ship1, 0, airCharIndexH, airRandomYH, 0);
                    }
                }
                // VERTICAL |
                else{
                    int airRandomYV = (int)Math.floor((Math.random() * 3) + 1); // Prevents ship placing >3 on vertical. Otherwise it would be outside of the board.
                    int airCharIndexV = findChar(airCraftRandX, 0);
                    if (ship1[0] != null){
                        aiShipConstruction(ship1, 0, airCharIndexV, airRandomYV, 3);
                    }
                    else{
                        aiShipConstruction(ship1, 0, airCharIndexV, airRandomYV, 1);
                    }
                }
            break;
            
            // -------------------------------- Type: Battleship --------------------------------
            case 1: 
                char battleCraftRandX = acceptedChars[(int)Math.floor((Math.random() * 6))];
                // HORIZONTAL -
                if (battleCraftRandX != 'G' && battleCraftRandX != 'F' && battleCraftRandX != 'E'){
                    int battleRandomYH = (int)Math.floor((Math.random() * 7) + 1); // Random Y coordinate from 1-7
                    int battleCharIndexH = findChar(battleCraftRandX, 0); // Finds the letter
                    if (ship1[0] != null){ // If this is a newly defined
                        aiShipConstruction(ship1, 0, battleCharIndexH, battleRandomYH, 2);
                    }
                    else{ // If this has already been defined and needs to be replaced
                        aiShipConstruction(ship1, 0, battleCharIndexH, battleRandomYH, 0);
                    }
                }
                // VERTICAL |
                else{
                    int battleRandomYV = (int)Math.floor((Math.random() * 4) + 1); // Prevents ship placing >4 on vertical. Otherwise it would be outside of the board.
                    int battleCharIndexV = findChar(battleCraftRandX, 0);
                    if (ship1[0] != null){ // If this is a newly defined
                        aiShipConstruction(ship1, 0, battleCharIndexV, battleRandomYV, 3);
                    }
                    else{ // If this has already been defined and needs to be replaced
                        aiShipConstruction(ship1, 0, battleCharIndexV, battleRandomYV, 1);
                    }
                }
            break;
            
            // -------------------------------- Type: Destroyer --------------------------------
            case 2:
                char destroyCraftRandX = acceptedChars[(int)Math.floor((Math.random() * 6))];
                // HORIZONTAL -
                if (destroyCraftRandX != 'G' && destroyCraftRandX != 'F' && Math.random() * 1 > 0.25){ // HORIZONTAL -
                    int destroyRandomYH = (int)Math.floor((Math.random() * 7) + 1); // Random Y coordinate from 1-7
                    int destroyCharIndexH = findChar(destroyCraftRandX, 0); // Finds the letter
                    if (ship1[0] != null){ // If this is a newly defined
                        aiShipConstruction(ship1, 0, destroyCharIndexH, destroyRandomYH, 2);
                    }
                    else{ // If this has already been defined and needs to be replaced
                        aiShipConstruction(ship1, 0, destroyCharIndexH, destroyRandomYH, 0);
                    }
                }
                // VERTICAL |
                else{
                    int destroyRandomYV = (int)Math.floor((Math.random() * 5) + 1); // Prevents ship placing >5 on vertical. Otherwise it would be outside of the board.
                    int destroyCharIndexV = findChar(destroyCraftRandX, 0);
                    if (ship1[0] != null){ // If this is a newly defined
                        aiShipConstruction(ship1, 0, destroyCharIndexV, destroyRandomYV, 3);
                    }
                    else{ // If this has already been defined and needs to be replaced
                        aiShipConstruction(ship1, 0, destroyCharIndexV, destroyRandomYV, 1);
                    }
                }
            break;
            // -------------------------------- Type: Submarine --------------------------------
            case 3:
                char subCraftRandX = acceptedChars[(int)Math.floor((Math.random() * 6))];
                // HORIZONTAL -
                if (subCraftRandX != 'G' && Math.random() * 1 > 0.45){
                    int subRandomYH = (int)Math.floor((Math.random() * 7) + 1); // Random Y coordinate from 1-7
                    int subCharIndexH = findChar(subCraftRandX, 0); // Finds the letter
                    if (ship1[0] != null){ // If this is a newly defined
                        aiShipConstruction(ship1, 0, subCharIndexH, subRandomYH, 2);
                    }
                    else{ // If this has already been defined and needs to be replaced
                        aiShipConstruction(ship1, 0, subCharIndexH, subRandomYH, 0);
                    }
                }
                // VERTICAL |
                else{
                    int subRandomYV = (int)Math.floor((Math.random() * 6) + 1); // Prevents ship placing >6 on vertical. Otherwise it would be outside of the board.
                    int subCharIndexV = findChar(subCraftRandX, 0);
                    if (ship1[0] != null){ // If this is a newly defined
                        aiShipConstruction(ship1, 0, subCharIndexV, subRandomYV, 3);
                    }
                    else{ // If this has already been defined and needs to be replaced
                        aiShipConstruction(ship1, 0, subCharIndexV, subRandomYV, 1);
                    }
                }
            break;
        }
    }
   /**
    * AiGuess - shotCount is compared to check for nulls and to be put into the prevShot variable
    * The "Ai" randomly selects values that are within the limits by communicating with other methods.
    * It repeats the process of selecting coordinates until it lands on one that has not been shot yet.
    * @param prevShot - Previous Shot - Stores Previous shots to ensure there is no repeating.
    * @return 
    */
    public static Coordinates AiGuess(Coordinates prevShot)
    {
        // A new empty Coordinate variable is opened
        Coordinates newShot = new Coordinates();
        if(shotCount[count] == null){
            shotCount[count] = prevShot;
        }
        else
        {
            shotCount[count] = prevShot;
        }
        count++;
        if (prevShot.letterCord == 'Z')
        {
            return new Coordinates('D', 4);
        }
        else{
            isRepeating(newShot);
            while(isRepeating(newShot) == true){ 
                isRepeating(newShot);
            }
            return newShot;
        } 
    }
    /**
     * isRepeating - makes and checks a new pair of Coordinates to see if they are
     * the same values as previous shots.
     * @param newCords - The new set of Coordinates that will assign itself to
     * the previous method's 'newShot'.
     * @return - True or false - True if it is a repeat of a Coordinate 
     * in shotCount, false if its a new Coordinate
     */
    public static boolean isRepeating(Coordinates newCords)
    {
        // X and Y values are assigned
        newCords.letterCord = acceptedChars[(int)Math.floor((Math.random() * 7))];
        newCords.numCord = (int)Math.floor((Math.random() * 7) + 1);
        
        // Checks these new pair with the entire catalog of shotCount
         for (int i = 0; i < shotCount.length; i++){
          // if i reaches into an area of the catalog that is too far ahead it will
          // stop, thus concluding the loop
            if(shotCount[i] == null)
            {
                break;
            }
            else
            {
                if(newCords.letterCord == shotCount[i].letterCord && newCords.numCord == shotCount[i].numCord)
                {
                    return true;
                }
            }
        }
        return false;
    }
    }