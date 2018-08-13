package HelpTakenFrom.WPM;
/*****
 * @Copyright (C) 2016 Ernesto D Martinez - All Rights Reserved
 *****/

import java.awt.Color;
import java.awt.event.*;
import java.net.URL;
import java.util.InputMismatchException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class ButtonKeyListener implements KeyListener{
	
	WPM_Driver wpm_Driver;
	JButton button;
	StringBuilder wordString, tempString, wrongChar;
	String stringA, stringB, stringC;
	JTextPane screen;
	Color green = new Color(102,200,51);;
	StyleContext sc;
	AttributeSet blackAttSet, redAttSet, greenAttSet;
	StyledDocument styledDoc;
	WPM_StopWatch stopWatch;
	URL keystroke;
	char currentChar;
	int currentParagraphIndex = 0, wordStringLength = 0, wordCount = 0, minutesSelected, redChars = 0;
	long startTime, endTime = 0;
	boolean done = false, listenTrigger = false;
	
	public ButtonKeyListener(WPM_Driver driver, int minutesSelect){
		wpm_Driver = driver;
		minutesSelected = minutesSelect;
		wordString = wpm_Driver.getWordString();
		screen = wpm_Driver.getScreen();
		tempString = new StringBuilder();
		wrongChar = new StringBuilder();
		button = new JButton();
		startTime = System.nanoTime();
        keystroke = ButtonKeyListener.class.getClassLoader().getResource("keystroke.wav");
		
		sc = StyleContext.getDefaultStyleContext();
		blackAttSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);
        redAttSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.RED);
        greenAttSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, green);
        styledDoc = screen.getStyledDocument();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//key code 8 = backspace
		//key code 32 = space bar
		
		button = wpm_Driver.getButton(e.getKeyCode());

		currentChar = wordString.charAt(currentParagraphIndex);
		
		//if newline character, treat it as space char.
		if(Character.isWhitespace(currentChar) && !Character.isSpaceChar(currentChar)){
			currentChar = ' ';
		}

		try{
			if(button != null && currentParagraphIndex < wordString.length()){
				button.doClick();
				playSound(keystroke);
				
				/********** CASE: There is one red marker on screen and user presses the corresponding button. **********/
				if(redChars == 1 && e.getKeyChar() == wordString.charAt(currentParagraphIndex-1)){
					stringA = wordString.substring(0, currentParagraphIndex);
					stringB = wordString.substring(currentParagraphIndex, wordString.length());
					
					screen.setCharacterAttributes(greenAttSet, false);
					screen.setText(stringA);

					try{
						styledDoc.insertString(currentParagraphIndex, stringB, null);
					}
					catch(Exception error){
						System.out.println(error);
					}
					
					redChars--;
				}
				
				/********** CASE: User presses correct key and there are no red characters on screen. **********/
				else if(e.getKeyChar() == currentChar && !Character.isSpaceChar(currentChar) && redChars == 0){
					if(redChars > 0){
						redChars--;
					}
					
					//starts timer one the first correct key is pressed 
					if(listenTrigger == false){
						stopWatch = new WPM_StopWatch(minutesSelected, this, wpm_Driver);
						stopWatch.execute();
						listenTrigger = true;
					}
					
					if(currentParagraphIndex == 0){
						stringA = wordString.substring(0, 1);
						stringB = wordString.substring(1, wordString.length());

						screen.setCharacterAttributes(greenAttSet, false);
						screen.setText(stringA);

						try{
							styledDoc.insertString(1, stringB, null);
						}
						catch(Exception error){
							System.out.println(error);
						}
					}
					else{
						stringA = wordString.substring(0, currentParagraphIndex + 1);
						stringB = wordString.substring(currentParagraphIndex + 1, wordString.length());
						
						screen.setCharacterAttributes(greenAttSet, false);
						screen.setText(stringA);
						screen.setCharacterAttributes(blackAttSet, false); //resets color to black for when populateScreen is called.

						try{
							styledDoc.insertString(currentParagraphIndex + 1, stringB, null);
						}
						catch(Exception error){
							System.out.println(error);
						}
					}
					
					currentParagraphIndex++;
				}
				
				/********** CASE: User presses the wrong key OR user presses the correct key while there are one or more red characters on screen. **********/
				else if((e.getKeyChar() != currentChar && !Character.isSpaceChar(currentChar) && e.getKeyCode() != 8) || 
						(e.getKeyChar() == currentChar && !Character.isSpaceChar(currentChar) && e.getKeyCode() != 8 && redChars > 0)){
					
					if(currentParagraphIndex == 0){
						stringA = wordString.substring(0, 1);
						stringB = wordString.substring(1, wordString.length());

						screen.setCharacterAttributes(redAttSet, false);
						screen.setText(stringA);

						try{
							styledDoc.insertString(1, stringB, null);
						}
						catch(Exception error){
							System.out.println(error);
						}
					}
					else{
						
						stringA = wordString.substring(0, currentParagraphIndex-redChars);
						stringB = wordString.substring(currentParagraphIndex + 1, wordString.length());
						stringC = wordString.substring(currentParagraphIndex-redChars, currentParagraphIndex+1);
						
						screen.setCharacterAttributes(greenAttSet, false);
						screen.setText(stringA);
						
						wrongChar.delete(0, 1);
						wrongChar.append(currentChar);
						try{
							styledDoc.insertString(currentParagraphIndex-redChars, stringC, redAttSet);
						}
						catch(Exception error){
							System.out.println(error);
						}

						try{
							styledDoc.insertString(currentParagraphIndex + 1, stringB, null);
						}
						catch(Exception error){
							System.out.println(error);
						}
					}
					
					redChars++;
					currentParagraphIndex++;
				}
				
				/********** CASE: The current character is a space bar. **********/
				else if(Character.isSpaceChar(currentChar) && e.getKeyCode() != 8){
						if(e.getKeyCode() == 32 && redChars == 0){
							currentParagraphIndex++;
							wordCount++;
						}
				}
				
				/********** CASE: The user presses the backspace key. **********/
				else if(e.getKeyCode() == 8 && redChars > 1){
					stringA = wordString.substring(0, (currentParagraphIndex-redChars));
					stringB = wordString.substring(currentParagraphIndex - 1, wordString.length());
					stringC = wordString.substring(currentParagraphIndex-redChars, currentParagraphIndex-1);
					
					screen.setCharacterAttributes(greenAttSet, false);
					screen.setText(stringA);
					
					wrongChar.delete(0, 1);
					wrongChar.append(currentChar);
					try{
						styledDoc.insertString(currentParagraphIndex-redChars, stringC, redAttSet);
					}
					catch(Exception error){
						System.out.println(error);
					}

					try{
						styledDoc.insertString(currentParagraphIndex - 1, stringB, null);
					}
					catch(Exception error){
						System.out.println(error);
					}
					
					if(redChars > 1){
						redChars--;
					}
					
					currentParagraphIndex--;
				}
			}
		}
		catch(InputMismatchException error){
			System.out.println("Error: " + error.getMessage());
		}
		
		/********** CASE: The end of the current wordString is reached. A new wordString is fetched. **********/
		if(currentParagraphIndex == wordString.length()){
			wpm_Driver.populateScreen();
			wordString = wpm_Driver.getWordString();
			currentParagraphIndex = 0;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**********
	 * Method to retrieve the word count.
	 **********/
	public int getWordCount(){
		return wordCount;
	}
	
	/**********
	 * Method that plays the sound.
	 **********/
	public void playSound(URL sound){
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}