package sebastianatabalesschnitzlerbattleships;
/**
 * Coordinates Class - Contains the values of objects called 'Coordinates'
 * An object containing 1 char and 1 int. Furthermore there are methods in this class
 * that assist in checking whether or the created Coordinates are valid for different circumstances
 * @author Seb
 */
public class Coordinates {
    // letterCord(X axis) and numCord(Y axis) allows for universal values for coordinates.
    char letterCord;
    int numCord;
    // acceptedChars - The boundaries for the grids X axis
    static char[] acceptedChars = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
    
    // Simple constructor, allowing for easy construction of Coordinates
    public Coordinates(char letterC, int numC)
    {
        this.letterCord = letterC;
        this.numCord = numC;
    }
    
    // Default value for Coordinates objects.(This value is the center of the grid)
    // (Relevent for BattleshipAI.java)
    public Coordinates()
    {
        this('D', 4);
    }
    /**
     * isValid - Checks the two submitted X and Y coordinate for if they are
     * within the boundaries of the grid.
     * @param c - X axis coordinate
     * @param n - Y axis coordinate
     * @param i - index (allows for recursion)
     * @return - True or false. True if its within the grid, false if not.
     */
    public static boolean isValid(char c, int n, int i)
    {
        if(n > 7 || n <= 0) // if the Y axis is bigger than 7 or smaller than 1
        {
            return false;
        }
        if (i < acceptedChars.length)
        {
          if (c == acceptedChars[i]) // if the X axis is in the range of acceptedChars
            {
              return true;
            }
          else
          {
            return isValid(c, n, i+1); // Recursion if index is still within acceptedChars
          }
        }
        return false;
    }
    /**
     * isValid - This method separates itself as it takes an implicit Coordinate object
     * and checks the whole thing if its valid. (useful in ship building in BattleShipsPlacement)
     * @param i - index (allows for recursion)
     * @return - True or false. True if its within the grid, false if not.
     */
    public boolean isValid(int i)
    {
        if(this.numCord > 7 || this.numCord < 1) // Y Coordinate (int)
        {
            return false;
        }
        if (i < acceptedChars.length ) // X Coordinate (char)
        {
          if (this.letterCord == acceptedChars[i])
            {
              return true;
            }
          else
          {
            return this.isValid(i+1);
          }
        }
        return false;
    }
    /**
     * isValidTwo - Takes one central ship and checks it with the coordinates of all the other ships.
     * @param focusShip - This ship is compared to every other ship.
     * @param ship1 - Is a array of Coordinates that compares itself to focusShip
     * @param ship2 - see ship1
     * @param ship3 - see ship1
     * @param ship4 - see ship1
     * @return True or false. False if focus ship overlaps with any other ship, True if it doesn't
     */
    public static boolean isValidTwo(Coordinates[] focusShip, Coordinates[] ship1, Coordinates[] ship2, Coordinates[] ship3, Coordinates[] ship4)
    {
        for (int i = 0; i < focusShip.length; i++){// Checks every element of the focus ship
            if (ship1 != null){
                for(int index = 0; index < ship1.length; index++){ // will check every element of ship1
                    if (focusShip[i].numCord == ship1[index].numCord && focusShip[i].letterCord == ship1[index].letterCord)
                    {
                    return false;
                    }
                }
            }else{break;}
        }
        for (int i = 0; i < focusShip.length; i++){// Checks every element of the focus ship
            if (ship2 != null){
                for(int index = 0; index < ship2.length; index++){ // will check every element of ship2
                    if (focusShip[i].numCord == ship2[index].numCord && focusShip[i].letterCord == ship2[index].letterCord)
                    {
                    return false;
                    }
                }
            }else{break;}
        }
        for (int i = 0; i < focusShip.length; i++){// Checks every element of the focus ship
            if (ship3 != null){
                for(int index = 0; index < ship3.length; index++){ // will check every element of ship3
                    if (focusShip[i].numCord == ship3[index].numCord && focusShip[i].letterCord == ship3[index].letterCord)
                    {
                    return false;
                    }
                }
            }else{break;}
        }
        for (int i = 0; i < focusShip.length; i++){// Checks every element of the focus ship
            if (ship4 != null){
                for(int index = 0; index < ship4.length; index++){ // will check every element of ship4
                    if (focusShip[i].numCord == ship4[index].numCord && focusShip[i].letterCord == ship4[index].letterCord)
                    {
                    return false;
                    }
                }
            }else{break;}
        }
        return true;
    }
    /**
     * isValid - same purpose as isValid two, however used in different context. Due to the treatment of
     * null values in the if statements, these two nearly identical methods had to be separated.
     * @param focusShip - This ship is compared to every other ship.
     * @param ship1 - Is a array of Coordinates that compares itself to focusShip
     * @param ship2 - see ship1
     * @param ship3 - see ship1
     * @param ship4 - see ship1
     * @return True or false. False if focus ship overlaps with any other ship, True if it doesn't
     */
    public static boolean isValid(Coordinates[] focusShip, Coordinates[] ship1, Coordinates[] ship2, Coordinates[] ship3, Coordinates[] ship4)
    {
        for (int i = 0; i < focusShip.length; i++){ // Checks every element of the focus ship
            if (ship1[0] != null){
                for(int index = 0; index < ship1.length; index++){ // will check every element of ship1
                    if (focusShip[i].numCord == ship1[index].numCord && focusShip[i].letterCord == ship1[index].letterCord)
                    {
                    return false;
                    }
                }
            }else{break;}
        }
        for (int i = 0; i < focusShip.length; i++){ // Checks every element of the focus ship
            if (ship2[0] != null){
                for(int index = 0; index < ship2.length; index++){ // will check every element of ship2
                    if (focusShip[i].numCord == ship2[index].numCord && focusShip[i].letterCord == ship2[index].letterCord)
                    {
                    return false;
                    }
                }
            }else{break;}
        }
        for (int i = 0; i < focusShip.length; i++){ // Checks every element of the focus ship
            if (ship3[0] != null){
                for(int index = 0; index < ship3.length; index++){ // will check every element of ship3
                    if (focusShip[i].numCord == ship3[index].numCord && focusShip[i].letterCord == ship3[index].letterCord)
                    {
                    return false;
                    }
                }
            }else{break;}
        }
        for (int i = 0; i < focusShip.length; i++){ // Checks every element of the focus ship
            if (ship4[0] != null){
                for(int index = 0; index < ship4.length; index++){ // will check every element of ship4
                    if (focusShip[i].numCord == ship4[index].numCord && focusShip[i].letterCord == ship4[index].letterCord)
                    {
                    return false;
                    }
                }
            }else{break;}
        }
        return true;
    }
}
