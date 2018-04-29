/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablestructure;

import java.util.Scanner;

/**
 *
 * @author Tønnes-Skole-Pc2
 * 
 */
public class menu {
    
    Scanner scan = new Scanner(System.in);
    TableDataStructure tds = new TableDataStructure();
    
    int menuChoo;
    
    public menu() {  
        menuChoo = 0;
    }
    /**
     * Menu.
     * Handles the menu part.
     */
    public void menuHandeling() {
        System.out.println("==========Menu============");
        System.out.println("0 -  Register Finished Boat");
        System.out.println("1 -  Register every race");
        System.out.println("2 -  Print and Sort results");
        System.out.println("3 -  Save results to file");        
        System.out.println("4 -  Load results from file");
        System.out.println("5 -  Exit");
        menuChoo = scan.nextInt();        
        
        switch (menuChoo) {
            case 0: register();
            case 1: whole();
            case 2: print();
            case 3: save();
            case 4: load();
            case 5: exit(); 
        }
    }
    
    /**
     * Back to the Menu
     * 
     * Loops back to the menu.
     */
    public void backToMenu() {
        System.out.println("Action Completed!\nEnter any key to go back to the menu...");
        scan.next();
        menuHandeling();
    }
    
    /**
     * Register
     * 
     * Goes to editArray() in TableDataStructure.
     * Where you edit a single part of the array.
     */
    public void register() {
        tds.editArray();
        backToMenu();
    }
    
    /**
     * Whole
     * 
     * Goes to populateWholeArray() in TableDataStructure.
     * Where you edit the whole array.
     */
    public void whole() {
        tds.populateWholeArray();
        backToMenu();
    }
    
    /**
     * Save
     * 
     * Goes to writeArrayToFile() in TableDataStructure.
     * Saves array to a file.
     */
    public void save() {
        tds.writeArrayToFile();
        backToMenu();
    }
    
    /**
     * Print
     * 
     * Goes to printSailResults() in TableDataStructure.
     * Prints and sorts the arrays.
     */
    public void print() {
        tds.printSailResults();       
        backToMenu();
    }
    
    /**
     * Load
     * 
     * Goes to loadArrayFromFile() in TableDataStructure.
     * Loads a line from the saveFile.
     */
    public void load() {
        tds.loadArrayFromFile();
        backToMenu();
    }
    
    /**
     * Exit
     * 
     * Exits the program.
     * Just exits the program.
     */
    public void exit() {
        System.out.println("The program has been closed.");
    }
}
