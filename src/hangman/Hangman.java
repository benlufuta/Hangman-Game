/**
* CSCI-160: Project-2
* Description: Hangman    
* Author: Ben Lufuta
* Due Date: Sunday,February 25th,2022
*/

/**
 * Revision History - newest revisions first
 *******************************************************************************
 * 
 * 02/24 - Codes were added/changed in the main() method to meet the 
 * requirement of only one argument.(Ben Lufuta)
 * 
 *******************************************************************************
 * 
 * 02/21 - Finally, added some codes to properly keep track of wrong
 * guesses entered during a round.(Ben Lufuta)
 * 
 *******************************************************************************
 * 
 * 02/21 - Added some conditions to the run() method to see if the player
 * has won or not based on the number of wrong guesses and whether the word has 
 * been found or not.(Ben Lufuta)
 * 
 *******************************************************************************
 * 
 * 02/21 - Added code to the run() method to add guessed letters to the 
 * proper arrayList, and also manage to update and reveal the word guessed
 * so far if a letter was correctly guessed.(Ben Lufuta)
 * 
 *******************************************************************************
 * 
 * 02/19 - Added more methods (getGuess(), isWordGuessed(), and playAgain())
 * These methods respectively: Get a letter guess from the player, 
 * Check if the word has been guessed, and at the end of a round ask the player
 * if they want to continue or not.(Ben Lufuta)
 * 
 *******************************************************************************
 * 
 * 02/15 - Created run(), setupGame(), printInfo() methods. Added some code 
 *in each one of them to make sure things are working fine so far.(Ben Lufuta)
 * 
 *******************************************************************************
 * 
 * 02/15 - Created the hangman class,created all necessary variables for 
 * the game, and then added some codes to properly read a file and assign all 
 * its content into an arrayList.(Ben Lufuta)
 * 
 ******************************************************************************/

package hangman;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


/**
 * This is a hangman game.
 * @author Ben Lufuta Tshikoji
 */
public class Hangman {
    
    //Helps with collecting data from the player.
    Scanner input = new Scanner(System.in);
    
    //Used to process words from the list and pick a random word.
    Random random = new Random();
    
    ///ArrayList to hold all the words from the file.
    ArrayList<String> words = new ArrayList<String> ();
    
    //Stores previously guessed letters.
    ArrayList<Character> guessedLetters = new ArrayList<Character>();

    //Total number of guesses possible. Can not be changed.
    private final int MAX_ERRORS = 7;
    
    //Stores the word to guess in char format.
    private char [] wordToGuess;
    
    //Stores the correct guesses made so far.
    private char[] guessedSoFar;
    
    //Keeps track of the number of wrong guesses from the player.
    private int wrongGuesses = 0;
    
    //Number of attempt
    private int attempts = 0;
    
    
    /**
     * The getReadFile() method read the given file.
     * @param fileName the name of the file that needs to be read.
     * @return words: which is the list of words from the file.
     */
    
    private ArrayList<String> getReadFile(String fileName){

        //Use try and cathc clause to read data from the given file.
        //If file contains any data, store it in the arraylist, otherwise
        //print out the proper message to the screen.
        try {
            
             Scanner input = new Scanner(new FileReader(fileName));
             if (!input.hasNext()) {

                System.err.println("file " + fileName + " is empty");
                System.exit(2);
             }
             
            while (input.hasNext()) {

             words.add(input.next());
                
        }
            } catch (FileNotFoundException ex) {

            System.err.println("File not found.");
            System.exit(3);
            
        }
        
        //Pick a random index in the arrayList
        int randIndex = random.nextInt(words.size());
        //Get a word from the above index, and then convert it to a char array.
        this.wordToGuess = words.get(randIndex).toCharArray();
        //Create a char array that has the same size as the word to guess.
        this.guessedSoFar = new char[wordToGuess.length];
        
        //return all the words gotten from the file.
        return words;
        
    }

    /**
     * The setupGame() restart variables for the start of a new round.
     * Reinitialize the word to guess, the current letters guessed,
     * and the number of wrong guesses.
     */
    public void setupGame() {
        
        //Get a new index from the arrayList again.
        int randIndex = random.nextInt(words.size());
        //Get a new word from the above index, and assign it to a char array
        this.wordToGuess = words.get(randIndex).toCharArray();
        this.guessedSoFar = new char[wordToGuess.length];
        //Delete everything from the list of already guessed letters.
        this.guessedLetters.clear();
        //Remove the previous guessed word from the list
        words.remove(new String (wordToGuess));
        //Reinitialize the number of wrong guesses to zero.
        wrongGuesses  = 0;
        //Reinitialize the number of attmept to zero.
        attempts = 0;
        
    }
    

    /**
     * A method that prompts the user to enter a guessed letter. 
     * The method processes the guess into by just taking the first entered
     * letter and then turn it into a lower case character.
     * @return a single, lower-case char that the player guessed
     */
    public char getGuess() {
        
      //Get input the player, and only select the first letter if the player
      //has entered word instead of one letter only.
        String letterEntered = input.nextLine().toLowerCase().substring(0,1);
        attempts++;
        char currentLetterChar = letterEntered.charAt(0);

        return currentLetterChar; //return the letter
    }
    
    
    /**
     * The isWordGuessed() method check to see if the word guessed so far
     * matches with the word to guess, and then return true if that the case.
     * @return true if the word if fully guessed.
     */
    
    public boolean isWordGuessed(){
        
        return Arrays.equals(wordToGuess, guessedSoFar);        
    }
    

    /**
     * This method checks to see if a player want to play another round of
     * the hangman game.
     * @return true if the player wants another round, otherwise return false.
     */
    public boolean playAgain(){
        
       boolean continueGame = true;
       
        //Ask the player if he/she wants to play another round or not.
        System.out.println("Would you like to play another round of Hangman?");
        System.out.println("Enter Y or y for YES, and N or n for NO.");
        String userAnswer = input.nextLine();

       if ( userAnswer.equals("Y") || userAnswer.equals("y")){
           
           continueGame = true;
           
       } else if ( userAnswer.equals("N") || userAnswer.equals("n")){
           
           continueGame = false;
       }
       
       return continueGame;
       
    }
    
    /**
     * The print() method serves to print some informations (such as rules) to 
     * the player before the initial round of the hangman game.
     * 
     */
    public void printInfo() {
        
        //Display the rules of the game
        System.out.println("Welcome!");
        System.out.println("You are playing the Hangman game.");
        System.out.println("The rules are as follows:"
                + "\nIf you guess the word correctly or make 7 wrong guesses,"
                + "\nthe game will end, "
                + "the word and the results will be displayed"
                + "\nEach letter is represented by a star (*) sign.");
        System.out.println();
        
        
        //Check to see if the arrayList is reading all the word form the file.
        //System.out.println("The dictionary file is " + words.size()); 
    }

    /**
     * The run() method is where everything happens. This is where we collect 
     * input from the user and proceed with checking all appropriate conditions 
     * of the game. Changes are made on values and messages are printed on the 
     * console accordingly.
     * @param fileName which is the name of the file that contains words. 
     */
    public void run (String fileName){
        
    getReadFile(fileName); //Read and load the file.
    printInfo();// Print some rules to the screen for the play.
    
        boolean playAgain = true;
        
        while (playAgain != false){
            
            setupGame();
            
            //Cover up each letter of the word with the star sign (*).
            for(int index = 0; index < wordToGuess.length; index++){
            
            guessedSoFar[index] = '*';

        }
            
        //Start looping the program as long as the conditions below are true.
        while (wrongGuesses < MAX_ERRORS && isWordGuessed() == false){
                
        System.out.print("Enter a letter in the word ");
       
       //Print out to console the word to guess as * (star signs) 
       for (int index = 0; index < guessedSoFar.length; index++){
          
         System.out.print(guessedSoFar [index]);
      }
      //Point where the player needs to type the letter.
        System.out.print( " > ");
        
        //Get a guess from the user, and store in a char variable.
                char guessedLetter = getGuess();
        
        //Check to if the letter entered by the player is already in the list 
        //of letters that have been guessed.
        //If it is in then let the player know, otherwise add it to the list.
                if(!guessedLetters.contains(guessedLetter)) {
                    
                guessedLetters.add(guessedLetter);

            } else {
                    
            System.out.println(guessedLetter + " has already been guessed");
            
            }  
        //Check if the word to guess contains the entered letter. If so, diplay 
        //it at that specifix index in the word guessed so far.
            for (int index = 0; index < guessedSoFar.length; index++){
           
            if (wordToGuess[index] == guessedLetter){

              guessedSoFar [index] = guessedLetter;
              
           }
       }
            //Convert word to guess into a new string, then it iterate through
            //it to see if the entered letter is found in, if it is not then 
            //increase wrong guesses by one.
            if (new String(wordToGuess).indexOf(guessedLetter) == -1){
                
                wrongGuesses++;
            }
    }
            
      //Call isWordGuessed() method to check if the word found so far matches
      //the actual word to guess. If so, print a message to let the player know
      //and if they do not match and the player has exceeded 7 attempts, then
      //let the player known that they lost and reveal the word to them.
      
      if (isWordGuessed() && wrongGuesses <= MAX_ERRORS){
           
        System.out.println("\nYou have successfully found the word in "
            + attempts + " attempts. The word is: " + new String (wordToGuess));
        System.out.println("You have had " + wrongGuesses + " wrong guesses.");
        
        playAgain = playAgain();
        
       } else if (!isWordGuessed() && wrongGuesses == MAX_ERRORS){
            
        System.out.println("\nSorry, You have lost. The word was " + 
                                new String (wordToGuess));
        
        System.out.println("You have had " + wrongGuesses + " wrong guesses, "
                + "for a total of " + attempts + " attempts.");
        
        playAgain = playAgain();
        
        }
      }
                
        System.out.println("Thanks for playing. See you next time!");
    }
    
    /**
     * The main () method that runs everything in this program.
     * As the only method main method, it is declared static.
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        
        //check and make sure that there is one argument prior to proceeding
        //with the program.
        if (args.length < 1) {
            System.err.println("Usage: progName dictionary");
            System.exit(1);
        }
        //Create an object of the class and then call the run() method to 
        //execute the program.
        Hangman gameDriver = new Hangman();
        gameDriver.run(args[0]);
    }   
}