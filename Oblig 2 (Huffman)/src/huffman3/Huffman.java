/*
 * Copyright (C) 2018 Tønnes Tobias Pedersen Røren
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package huffman3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
    
/**
 *
 * @author Tønnes Tobias Pedersen Røren
 * @version 3.2 (24.02.2018)
 * 
 */
public class Huffman {
    
    public static HuffmanNode root;
    static PriorityQueue<HuffmanNode> proque;
    
    static HashMap<Character, Integer> freqMap;
    static HashMap<Character, String>  codeTable;
    
    static FileInputStream fileInput;
    static File f = new File("HuffmanTest.txt"); //Input file for encoding. Located in the base project folder.
    
    String userinput; //Input text for encoding
    String th; //Encoded Output
    String thd; //Decoded Output
    static String fileInputs; //Input file for encoding
    
    static int keyCounter = 0;
    
    public Huffman() {   
    }
    
    /**
     * Main
     * 
     * @param args
     * @throws IOException
     * @throws FileNotFoundException
     */
    
    public static void main(String[] args) throws IOException, FileNotFoundException { 
        //File or String printout *Swap comments to use file instead of string*.
        String userinput = getEncodeString(); 
        //String userinput = getEncodeFile(); //<-null bug    
        
        //Huffman
        HashMap<Character, Integer> freqMap = getFreqs(userinput);
        HuffmanNode root = createTree (freqMap);    
        HashMap<Character, String> codeTable = codeEncoding(root);
        
        //Printouts
        System.out.println("\nHashMaps: ");
        System.out.println(freqMap);
        System.out.println(codeTable); 
        
        //Encoding
        String th = encode(codeTable, userinput);
        System.out.println("\nText Input: " + userinput);
        System.out.println("Encoded Output: " + th);   
        
        //Decoding
        String thd = decode(root, th);
        System.out.println("\nEncoded Output Input: " + th);
        System.out.println("Decoded Output: " + thd + "\n");
               
        //Tree printout ----Uncomment to print tree.
        //printTree(root);
    }

    /**
     * Fetches the string input
     * 
     * @return Returns the input that the user wrote from scanner.
     */
    
    public static String getEncodeString() {       
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Write to encode (Some special characters are not supported): "); 
        String sc = scanner.nextLine();
        
        String userinput = sc;
        return userinput;
    }
    
    /**
     * Fetches the file input for when you want to use a file instead of a string.
     * Need to uncomment getEncodeFile line.
     * 
     * @return Returns a file in the form of a String
     */
    public static String getEncodeFile() {
        try {
            fileInput = new FileInputStream(f);
            int fileContent;
            while ((fileContent = fileInput.read()) != -1) {
                fileInputs +=((char) fileContent);          
            }
        } catch(IOException e) {
            System.out.println("Error: " + e);
        }
        return fileInputs;
    }
    
    /**
     * Gets frequence of characters from the input string.
     * 
     * @param userinput String to create Huffman Tree
     * @return A HashMap of character with number of occurrences.
     */
    public static HashMap<Character, Integer> getFreqs(String userinput) {
        freqMap = new HashMap<>();
        
        for (int i=0; i< userinput.length(); i++) {
            char ca = userinput.charAt(i);
            if (freqMap.containsKey(ca)) {
                freqMap.put(ca, freqMap.get(ca)+1);
            } else {
                freqMap.put(ca,1);
            }       
        }
        return freqMap;
    }
    
    /**
     * Adds priority queue
     * 
     * @param freqMap HashMap of frequencies
     * @return priorityQueue
     */
    public static HuffmanNode createTree(HashMap<Character, Integer> freqMap) {
        proque = new PriorityQueue<>();
        
        freqMap.keySet().forEach((ca) -> {
            proque.add(new HuffmanNode(ca, freqMap.get(ca), null, null));
        }); 
        while (proque.size() > 1) {
            HuffmanNode zero = proque.poll();
            HuffmanNode one = proque.poll();
            proque.add(new HuffmanNode(' ', zero.freq + one.freq, zero, one));
        }
        return proque.poll();
    }
    
    /**
     * Creates a code table with necessary data to decode.
     * 
     * @param root Huffman Tree start (the root of the tree).
     * @return A HashMap of encoded codes.
     */
    public static HashMap<Character, String> codeEncoding(HuffmanNode root) {
        codeTable = new HashMap<>();
        
        assign(root, "", codeTable);
        return codeTable;
    }
    
    /**
     * Assigns values to tree nodes.
     * 
     * @param node A node in the Huffman Tree.
     * @param code Codes
     * @param encodeTable Codes
     */
    public static void assign(HuffmanNode node, String code, HashMap<Character, String> encodeTable) {
        if (node.testLeaf()) {
            encodeTable.put(node.character, code);
        } else {
            if (node.zero != null) {
                assign(node.zero, code+"0", encodeTable);
            }
            if (node.one != null) {
                assign(node.one, code+"1", encodeTable);
            }
        } 
    }
    
    /**
     * Prints out the whole Tree.
     * Must uncomment a line in "Main()" to use.
     * 
     * @param node A node in the Huffman Tree.
     */   
    private static void printTree(HuffmanNode node) {	
        if (node.testLeaf()) {
            System.out.println(String.format("Key: %d    Char: %s    Freq: %d  <--LEAF %n", node.key, node.character, node.freq));
            return;
        }
        if (node.zero != null) {
            System.out.println(String.format("Key: %d          %s    Freq: %d", node.key, node.character, node.freq));
            System.out.println(String.format("Key: %d    Path: %d    0", node.key, node.zero.key));
            printTree(node.zero); //Left
        }
        if (node.one != null) {
            System.out.println(String.format("Key: %s          %s    Freq: %d", node.key, node.character, node.freq));
            System.out.println(String.format("Key: %s    Path: %s    1", node.key, node.one.key));
            printTree(node.one); //Right
        }
    }
    
    /**
     * Encodes the text input from either a file or a string.
     * 
     * @param codeTable HashMap of encoded 0's and 1's.
     * @param userinput Text of what the user inputs to be encoded.
     * @return Returns a string in the form of 0's and 1's. The encoded output.
     */
    public static String encode(HashMap<Character, String> codeTable, String userinput) {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < userinput.length(); i++) {
            sb.append(codeTable.get(userinput.charAt(i)));
        }
        return sb.toString();
    }
    
    /**
     * Decodes the binary output of encode.
     * 
     * @param node A node in the Huffman Tree.
     * @param th The encoded input.
     * @return The decoded output.
     */
    public static String decode(HuffmanNode node, String th) {
        String decoded = "";
        
        for (int i = 0; i < th.length();) {
            HuffmanNode tmpNode = node;
            while (tmpNode.zero != null && tmpNode.one != null && i < th.length()) {            
                if (th.charAt(i) == '1') {
                    tmpNode = tmpNode.one; //Right
                } else if (th.charAt(i) == '0') {
                    tmpNode = tmpNode.zero; //Left
                }
                i++;
            }
            decoded += tmpNode.character;           
        }
        return decoded;
    }
    
    
    /**
     * Handles the tree structure
     * 
     * HuffmanNode Class
     */
    public static class HuffmanNode implements Comparable {
        public char character;
	public int key;	
	public int freq;
	public HuffmanNode zero; //left
	public HuffmanNode one; //right	
        
        /**
         * Tree values
         * 
         * @param cr Characters
         * @param fq Frequencies
         * @param right One
         * @param left  Zero
         */
        public HuffmanNode(char cr, int fq, HuffmanNode right, HuffmanNode left) {
            key = keyCounter++;
            character = cr; 
            freq = fq;
            zero = left; //Left
            one = right; //Right
        }
        
        /**
         * Test for end node(Leaf).
         * 
         * @return Returns true of false. If it is a end node or not.
         */
        public boolean testLeaf() {
            return zero==null && one==null; //left, right
        }
        
        /**
         * Compares
         * 
         * @param o An object
         * @return Frequence
         */
        @Override
        public int compareTo(Object o) {
            return freq - ((HuffmanNode)o).freq;
        }
    }
}