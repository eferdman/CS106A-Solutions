/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import java.io.*;
import java.util.*;

public class HangmanLexicon {
    private final String LexiconPath = "/resources/HangmanLexicon.txt";
    
    // This is the HangmanLexicon constructor
    public HangmanLexicon() {
        
        wordList = new ArrayList<String>();
        
        try {
            InputStream stream = this.getClass().getResourceAsStream(LexiconPath);
            BufferedReader rd = new BufferedReader(new InputStreamReader(stream)); 
                while (true) {
                    String line = rd.readLine();
                    if (line == null) break;
                    wordList.add(line);
                }
            rd.close();
        } catch (IOException ex) {
            throw new ErrorException(ex);
        }
    }

/**
 * 
 * @return the number of words in the lexicon. 
 */
	public int getWordCount() {
		return wordList.size();
	}

/**
 * 
 * @param index a random index in the wordList
 * @return the word at the specified index
 */
	public String getWord(int index) {
		return wordList.get(index);
	};
        
        private ArrayList<String> wordList;
}
