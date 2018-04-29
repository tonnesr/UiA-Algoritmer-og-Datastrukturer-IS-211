/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablestructure;


import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;

/**
 *
 * @author Tonnes-Pc
 * 
 */
public class TableDataStructure {
    //Integers
    int scoreMath; //quick Maths
    int userInput; //user input
    int row = 6; //rows
    int col = 4;  //colums
    int line; //Find the user inputed line number.
    int lineCount; //Counts number of lines of saveFile.
    int width;
    int height;
    int lineOutput;
    int maths;
    int sortCol = 5;
    
    //Strings
    String loadedLine;
    String arrayLine; //String output from file.
    String saveFile = "saveFile.txt"; //saveFile location.
    
    //Booleans
    boolean isEmpty;
    
    //Arrays
    Integer[][] array; //"Main" integer-array. //Could have used 3d for a new 2d every regeta.
    String[] names; //Name string-array.
    String[] loadLine; //Loaded string from file.
    String[] loadCell; //Loaded string from file.
    Object[][] merged; //Merge between names, and array.
   
    //Other
    Scanner scan; 
    FileWriter fileW;
    BufferedWriter buffW;
    FileReader fileR;
    LineNumberReader lineNR;
   
    /**
     * Constructor
     */
    public TableDataStructure() {       
        scan = new Scanner(System.in);
        
        names = new String[] {"Boat", "Boaty MacBoatface", "Saily the boat", "Sully"};       
        merged = new Object[col][row + 1]; 
        array = new Integer[col][row];
    }
    
    /**
     * editArray
     * 
     * Edits one place of a spesific array index.
     */
    public void editArray()  {        
        int boat = whichBoat();
        int race = whichRace();
        
        System.out.println(race);
        
        if (race != 5) {
            array[boat][race]=scan.nextInt();
            System.out.println(Arrays.deepToString(array[boat])
                            .replace("[", "") //Shorten/Standarize
                            .replace("]", "")
                            .replace(",", "")
                            .replace("null", "0"));
            
            scoreMath = quickMaths(array, boat);
            array[boat][5] = scoreMath;
            
            System.out.println("Boat: " + names[boat] + " Number: " + boat + " " + " Race: " + race);
            System.out.println(Arrays.deepToString(array[boat])                           
                            .replace("[","")
                            .replace("]","")
                            .replace(","," ")
                            .replace("null", "0"));
        }
        else {
            System.out.println("Error: This space is reserved for maths.");
        }
    }
    
    /**
     * populateWholeArray
     * 
     * Edits the whole array with user information.
     */
    public void populateWholeArray() {       
        System.out.println("Now editing the whole regeta");   
        System.out.println(Arrays.deepToString(array[0])
                            .replace("[","")
                            .replace("]", "")
                            .replace(","," "));
        for (int i = 0; i < col; i++) {
            System.out.println("Boat: " + names[i] + "\n" + "Number: " +  i);
            for (int j = 0; j < row; j++) {
                if (j != 5) {
                    System.out.println("Race: " + j);
                    array[i][j] = scan.nextInt();
                    System.out.println(Arrays.deepToString(array[i])
                            .replace("[", "")
                            .replace("]", "")
                            .replace(",", "")
                            );      
                }
                if (j == 5) {
                    scoreMath = quickMaths(array, i);
                    array[i][5] = scoreMath; 
                    
                    System.out.println("Result: " + Arrays.deepToString(array[i])
                            .replace("[", "")
                            .replace("]", "")
                            .replace(",", "")
                            .replace("null", ""));
                }  
            } 
        }
    }
    
    /**
     * writeArrayToFile
     * 
     * Saves the current array to a saveFile.
     * uses isEmpty to check if there are any null values in the array.
     */
    public void writeArrayToFile() {	
        isEmpty = testArray(array);
        if (isEmpty == false) {
            System.out.println("Saving...");
            try {
                fileW = new FileWriter(saveFile, true);
                buffW = new BufferedWriter(fileW);

                buffW.write(Arrays.deepToString(array)
                            .replace("[", "")
                            .replace("]]", "")
                            .replace("],", ";")
                            .replace(" ", ""));
                buffW.newLine();
            } 
            catch (IOException e) {
                System.out.println(e);
            }
            finally {
                try {
                    if (buffW != null) {

                        buffW.close();
                    }
                    if (fileW != null) {

                        fileW.close();
                    }
                }
                catch (IOException er) {
                    System.out.println(er);
                }
            }
        }
        else {
            System.out.println("You need to fill out the scores.");
        }
    }

    /**
     * loadArrayFromFile
     * 
     * Loads the saved information from saveFile.
     * Uses a spesific format, where ',' is to separate values, and ';' is for new line.
     */
    public void loadArrayFromFile() {       
        line = getLine();
        try {
            loadedLine = Files.readAllLines(Paths.get(saveFile)).get(line);
            
            loadLine = loadedLine.split(";");
            width = loadLine.length;
            
            loadCell = loadLine[0].split(",");
            height = loadCell.length;
            
            array = new Integer[width][height];
            
            for (int i = 0; i < width; i++) {
                loadCell = loadLine[i].split(",");
                for (int j = 0; j < height; j++) {
                    array[i][j] = Integer.parseInt(loadCell[j]);
                }
            }
        }
        catch (IOException exe) {
            System.out.println(exe);
        }
        System.out.println(Arrays.deepToString(array));
    }
    
    /**
     * mergeArray
     * 
     * Merges the arrays: Integer[][] array, and String[] names.
     * Was originally meant to retain the name of the boats after sorting. 
     * But sorting a Object array is harder than i though. 
     */
    public void mergeArrays() {
        for (int i = 0; i < col; i++) {             
            for (int j = 0; j < row; j++) {
                merged[i][6] = names[i];    
                merged[i][j] = array[i][j];
            }
        }
    } 
    
    /**
     * sortByColumn
     * 
     * Sorts Integer[][] arrays.
     * Cannot sort Object arrays, so the names of the boats are lost at the result printout.
     */
    public void sortByColumn() {        
        Arrays.sort(array, new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] ent1, Integer[] ent2) {                
                if (ent1[sortCol] > ent2[sortCol]) {
                    return 1;
                }
                else {
                    return -1;
                }
            } 
        });
    }

    /**
     * printSailResults
     * 
     * Uses the merge method to merge array.
     * Uses the sort method to sort Integer array.
     * 
     * Prints the results in theory, jus without names. So, you don't really know who won...
     */
    public void printSailResults() {   
        mergeArrays();
        System.out.println("===Results====================");
        
        for (int i = 0; i < col; i++) {
            System.out.println(Arrays.deepToString(merged[i])
                            .replace("[","")
                            .replace("]","")
                            .replace(",",""));   
        }
        
        System.out.println("===Sorted=====================");
        sortByColumn();
        
        for (int j = 0; j < col; j++) {
            int place = j +1;
            System.out.println(place + ". " + Arrays.toString(array[j])
                            .replace("[", "")
                            .replace("]","")
                            .replace(",",""));
        }
        
        System.out.println("==============================");
    }

    /**
     * quickMaths
     * 
     * @param array "main array" input
     * @param i index input
     * @return returns math
     * 
     * Used to get the result of each boats 6 races.
     * Math: x+x+x+x+x+x = x
     *  while ignoring null values.
     */
    public int quickMaths(Integer[][] array, int i) {
        maths = 0; //reset maths
        array[i][5] = 0; //reset result
        for (Object obje: array[i]) {
            if (obje instanceof Integer) {
                int v = (Integer) obje;
                maths += v;
            }
        }
        
        return maths;
    }
    
    /**
     * countLines
     * 
     * @return returns a count of lines in a file.
     * 
     * Used to get the number of lines in a file.
     */
    public int countLines() {
        try {
            if (saveFile != null) {
                fileR = new FileReader(saveFile);
                lineNR = new LineNumberReader(fileR);
                
                lineCount = 0;
                while (lineNR.readLine() != null) {
                    lineCount++;
                }              
                
                lineNR.close();
            }
        }
        catch (IOException error) {
            System.out.println(error);
        }
            
        return lineCount;
    }
    
    /**
     * testArray
     * 
     * @param array
     * @return if there are any null values in the array.
     * 
     * Used to test if there are any null values in the whole array.
     * Used when saving to file.
     */
    public boolean testArray(Integer[][] array) {
        isEmpty = false;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] == null) {
                    isEmpty = true;
                    break;
                }
            }
        }
        return isEmpty;
    } 
    
    /**
     * getLine
     * 
     * @return returns a user inputed line number.
     * 
     * Used to find what part of the array, the user want to edit.
     */
    public int getLine() {
        lineCount = countLines() -1;
        System.out.println("Choose a voyage (oldest-newest): \n0-" + lineCount);
        line = scan.nextInt();
        return line;
    }     
    
    /**
     * whichBoat
     * 
     * @return The boat, the user wants to edit.
     * 
     * Should have merged these last methods. They are practically identical.
     */
    public int whichBoat() {
        System.out.println("Which Boat: ");
        for (int i = 0; i < names.length; i++)  {
            System.out.println(i + " - " + names[i]);
        }
        
        userInput = scan.nextInt();
        
        System.out.println("Now editing: " + names[userInput]);
        
        return userInput;
    }
    
    /**
     * whichRace
     * 
     * @return The race, the user wants to edit.
     * 
     * Should have merged these last methods. They are practically identical.
     */
    public int whichRace() {
        System.out.println("Which Race: "
                         + "\n0 - Race 1"
                         + "\n1 - Race 2"
                         + "\n2 - Race 3"
                         + "\n3 - Race 4"
                         + "\n4 - Race 5"
                         + "\n5 - Race 6");
        userInput = scan.nextInt();
        int var = userInput + 1;
        
        System.out.println("Now editing: Race " + var);
        
        return userInput;
    }       
}
