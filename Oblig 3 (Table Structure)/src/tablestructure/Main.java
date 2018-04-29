/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablestructure;

/**
 *
 * @author Tonnes-Pc
 * @version 1.5 (25.03.2018)
 * 
 * Practical information:
 * 
 *   Problems with the program:
 *    1. Cannot sort object array that contains names and results.
 *    2. The file saving, sometimes puts multiple saves on the same line.
 *    3. Does not test for boats with the same placing in a race. 
 * 
 *   Information: 
 *    1. Race = One of the 6 races that each boat partake in.
 *    2. Boat = a boat with a name, and number.
 *    3. Voyage = one regatta. Including all races that year.
 * 
 */
public class Main {
    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        menu Menu = new menu();
        Menu.menuHandeling();
    }    
}
