import acm.graphics.*;

/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */
public class HangmanCanvas extends GCanvas {
              

/** Resets the display so that only the scaffold appears */
    public void reset() {
        removeAll();
        
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        int bodyY = centerY-BODY_LENGTH/2 - 80;
        int headY = bodyY-HEAD_DIAM;
        int ropeY = headY-ROPE_LENGTH;
        int beamX = centerX-BEAM_LENGTH;
             
        GLine scaffold = new GLine(beamX,ropeY,beamX,ropeY+SCAFFOLD_HEIGHT);
        add(scaffold);
        GLine beam = new GLine(beamX,ropeY,centerX, ropeY);
        add(beam);
        GLine rope = new GLine(centerX,ropeY,centerX,headY);
        add(rope);
    }

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 * 
 * @param word the word consisting of dashes and/or guessed letters
 */
	public void displayWord(String word) {
            GLabel display = new GLabel(word);
            display.setFont("Monospaced-26");
            double x = getWidth()/4;
            double y = getHeight()-HEAD_DIAM;
            if(getElementAt(x,y)!= null) remove(getElementAt(x,y));
            add(display,x,y);
	}

    /**
     *  Updates the display to correspond to an incorrect guess by the
     *  user. Calling this method causes the next body part to appear
     *  on the scaffold and adds the letter to the list of incorrect
     *  guesses that appears at the bottom of the window.
     *  
     * @param wrongLetters the string made up of incorrect guesses
     */
    public void noteIncorrectGuess(String wrongLetters) {
        /*display a string of the user's incorrect guesses*/
        double x = getWidth()/4;
        double y = getHeight()-HEAD_DIAM/2;
        GLabel wrongGuesses = new GLabel(wrongLetters,x,y);
        if(getElementAt(x,y)!= null) remove(getElementAt(x,y)); 
        add(wrongGuesses);
        
        /*for each successive error, add the next body part to the hangman*/
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        int bodyY = centerY-BODY_LENGTH/2 - 80;
        int bodyY2 = bodyY + BODY_LENGTH;
        int headX = (getWidth()-HEAD_DIAM)/2;
        int headY = bodyY-HEAD_DIAM;
        int armY = bodyY + ARM_OFFSET_FROM_HEAD;
        int armY2 = armY + LOWER_ARM_LENGTH;
        int leftArmX = centerX-UPPER_ARM_LENGTH;
        int rightArmX2 = centerX+UPPER_ARM_LENGTH;
        int leftHipX = centerX-HIP_WIDTH;  
         
        int index = wrongLetters.length();               
        switch (index) {
            case 1: GOval head = new GOval(headX,headY,HEAD_DIAM,HEAD_DIAM);
                    add(head);
                    break;
            case 2: GLine body = new GLine(centerX,bodyY,centerX,bodyY2);
                    add(body);
                    break;
            case 3: GLine leftUpperArm = new GLine(leftArmX, armY,centerX,armY);
                    GLine leftLowerArm = new GLine(leftArmX, armY, leftArmX,armY2);
                    add(leftUpperArm);
                    add(leftLowerArm);
                    break;
            case 4: GLine rightUpperArm = new GLine(centerX,armY,rightArmX2,armY);
                    GLine rightLowerArm = new GLine(rightArmX2,armY,rightArmX2,armY2);
                    add(rightUpperArm); 
                    add(rightLowerArm);
                    break;
            case 5: GLine leftHip = new GLine(leftHipX,bodyY2,centerX,bodyY2);
                    GLine leftLeg = new GLine(leftHipX,bodyY2,leftHipX,bodyY2+LEG_LENGTH);
                    add(leftHip);
                    add(leftLeg);
                    break;
            case 6: GLine rightHip = new GLine(centerX,bodyY2,centerX+HIP_WIDTH,bodyY2);
                    GLine rightLeg = new GLine(centerX+HIP_WIDTH,bodyY2,
                    centerX+HIP_WIDTH,bodyY2+LEG_LENGTH);
                    add(rightHip);
                    add(rightLeg);
                    break;
            case 7: GLine leftFoot = new GLine(leftHipX-FOOT_LENGTH,bodyY2+LEG_LENGTH,
                    leftHipX,bodyY2+LEG_LENGTH);
                    add(leftFoot);
                    break;
            case 8: GLine rightFoot = new GLine(centerX+HIP_WIDTH,bodyY2+LEG_LENGTH,
                    centerX+HIP_WIDTH+FOOT_LENGTH,bodyY2+LEG_LENGTH);
                    add(rightFoot);
                    break;
        }
 
}

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_DIAM = 72;
	private static final int BODY_LENGTH = 144; 
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
}
