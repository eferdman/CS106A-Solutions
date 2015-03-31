import acm.program.*;
import acm.util.*;

public class Hangman extends ConsoleProgram {

    /**
     * 
     * @return dashes a string of dashes for each letter
     */
    private String getDashes() {
            dashes = "";
            for (int i=0; i<word.length(); i++) {
                dashes += "-";
            }
            return dashes;
    }
    
    /**
     * @param guess the character the user guessed
     * @return the updated word clue for each round
     */
    private String getClue(char guess) {
        /*iterate through each letter in the word; replacing each dash with the
         * correct letter that the user guessed.
         */
        for (int i=0;i<word.length();i++) {
            char letter = word.charAt(i);
            if (letter==guess) {
                //string replacement idiom
                wordClue = wordClue.substring(0,i) + guess + wordClue.substring(i+1);
            }
        }
        return wordClue;
    }
    
    public void init() {
        canvas = new HangmanCanvas();
        add(canvas);
    }
    
    public void run() {
        setFont("Monospaced-14");
        
        lexicon = new HangmanLexicon();
        
        while (true) {
            setUpGame();
            playGame();
        }
        
    }
    
    private void setUpGame() {
        
        incorrectLetters = "";
        guessCounter = 8;
        
        //retrieves a random secret word from the HangmanLexicon class
        RandomGenerator rgen = RandomGenerator.getInstance();
        int randomNumber = rgen.nextInt(lexicon.getWordCount() - 1);
        word = lexicon.getWord(randomNumber);
        wordClue = getDashes();
        
        //Console and Graphics Intro
        println("Welcome to Hangman!");
        println("The word now looks like this: " + wordClue);
        canvas.reset();
        canvas.displayWord(wordClue);
    }
    
    private void playGame() {
        while (guessCounter > 0) {
            
            if (guessCounter > 1) 
                println("You have " + guessCounter + " guesses left.");
            else println("You only have one guess left.");
            
            //Reads guess from user
            String guess = readLine("Your guess: ");
            
            //User can only enter a single alphabetic letter at a time.
            while (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
                guess = readLine("Please enter a single letter. Your guess: ");
            }
            //Allows upper or lower-case guess
            char ch = Character.toUpperCase(guess.charAt(0));
            
            wordClue = getClue(ch);
            
            canvas.displayWord(wordClue);
            
            if (word.indexOf(ch) == -1) {
                guessCounter--;
                println("There are no " + ch + "'s in the word.");
                incorrectLetters = incorrectLetters + ch;
                canvas.noteIncorrectGuess(incorrectLetters);
                
            } else {
                println("That guess is correct.");
                if (word.equals(wordClue)) {
                    println("You guessed the word: " + word);
                    println("You win!");
                    pause(1000);
                    break;
                }
            }
            
            if (guessCounter > 0) {
                println("The word now looks like this: " + wordClue);
            }  else if (guessCounter == 0) {
                println("You're completely hung.");
                println("The word was: " + word);
                println("You lose.");
            }
        }
    }
    
    /*private instance variables*/
    private String word;            //random word from the list
    private String wordClue;         //the wordClue consisting of letters and/or dashes
    private int guessCounter;
    private String dashes;    //first clue is a string of dashes for each letter in the word
    private HangmanCanvas canvas;
    private HangmanLexicon lexicon;
    private String incorrectLetters;   
}
