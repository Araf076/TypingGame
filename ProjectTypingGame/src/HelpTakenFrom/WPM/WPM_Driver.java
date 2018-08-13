package HelpTakenFrom.WPM;
/*****
 * @Copyright (C) 2016 Ernesto D Martinez - All Rights Reserved
 *****/

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.Border;

public class WPM_Driver extends JFrame implements WindowListener{
	JPanel topPanel, bottomPanel;
	JTextPane screen;
	JFrame resultsMessage;
	JButton button,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,space,backspace,enter;
	ButtonKeyListener buttonKeyListener;
    HashMap buttonMap;
    StringBuilder wordString;
    WordList wordList;
    String tempWord = "";
	Border lineBorder;
	int listSize = 0, lineLength, counter = 0, minutesSelected, renderTrigger = 0, menuOption = 1, timerTrigger = 0;
	long startTime = 0, endTime = 0;
	
	public WPM_Driver(int minutesSelect){
		setTitle("WPM");
		setSize(1000,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		minutesSelected = minutesSelect;
		
		wordList = new WordList();

		wordString = new StringBuilder();
		
		resultsMessage = new JFrame();
		
		/********** TOP PANEL **********/
		
		topPanel = new JPanel();
		screen = new JTextPane();
		screen.setSize(950,950);
		screen.setEditable(false);
		Font screenFont = new Font("Serif", 0, 50);
		screen.setFont(screenFont);
		Color screenBorderColor = new Color(102,0,204);
		lineBorder = BorderFactory.createLineBorder(screenBorderColor);
		screen.setBorder(lineBorder);
		topPanel.add(screen);
		
		/********** BOTTOM PANEL **********/
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		GridBagConstraints bottomPanelConstraints = new GridBagConstraints();
		bottomPanelConstraints.insets = new Insets(10,10,10,10);
		bottomPanelConstraints.ipadx = 30;
		bottomPanelConstraints.ipady = 40;
		bottomPanelConstraints.weightx = 0.5;
		
		buttonKeyListener = new ButtonKeyListener(this, minutesSelected);
		
		q = new JButton("Q");
		w = new JButton("W");
		e = new JButton("E");
		r = new JButton("R");
		t = new JButton("T");
		y = new JButton("Y");
		u = new JButton("U");
		i = new JButton("I");
		o = new JButton("O");
		p = new JButton("P");
		
		a = new JButton("A");
		s = new JButton("S");
		d = new JButton("D");
		f = new JButton("F");
		g = new JButton("G");
		h = new JButton("H");
		j = new JButton("J");
		k = new JButton("K");
		l = new JButton("L");
		
		z = new JButton("Z");
		x = new JButton("X");
		c = new JButton("C");
		v = new JButton("V");
		b = new JButton("B");
		n = new JButton("N");
		m = new JButton("M");
		
		space = new JButton("Space Bar");
		backspace = new JButton("Backspace");
		enter = new JButton("Enter");
		
		button = new JButton();
		
		buttonMap = new HashMap<Integer, String>();
		buttonMap.put(81, q);
		buttonMap.put(87, w);
		buttonMap.put(69, e);
		buttonMap.put(82, r);
		buttonMap.put(84, t);
		buttonMap.put(89, y);
		buttonMap.put(85, u);
		buttonMap.put(73, i);
		buttonMap.put(79, o);
		buttonMap.put(80, p);
		buttonMap.put(65, a);
		buttonMap.put(83, s);
		buttonMap.put(68, d);
		buttonMap.put(70, f);
		buttonMap.put(71, g);
		buttonMap.put(72, h);
		buttonMap.put(74, j);
		buttonMap.put(75, k);
		buttonMap.put(76, l);
		buttonMap.put(90, z);
		buttonMap.put(88, x);
		buttonMap.put(67, c);
		buttonMap.put(86, v);
		buttonMap.put(66, b);
		buttonMap.put(78, n);
		buttonMap.put(77, m);
		buttonMap.put(32, space);
		buttonMap.put(8, backspace);
		buttonMap.put(10, enter);
		
		bottomPanelConstraints.gridy = 0;
		bottomPanelConstraints.gridx = 0;
		bottomPanel.add(q, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 1;
		bottomPanel.add(w, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 2;
		bottomPanel.add(e, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 3;
		bottomPanel.add(r, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 4;
		bottomPanel.add(t, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 5;
		bottomPanel.add(y, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 6;
		bottomPanel.add(u, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 7;
		bottomPanel.add(i, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 8;
		bottomPanel.add(o, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 9;
		bottomPanel.add(p, bottomPanelConstraints);
		bottomPanelConstraints.gridy = 1;
		bottomPanelConstraints.gridx = 1;
		bottomPanel.add(a, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 2;
		bottomPanel.add(s, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 3;
		bottomPanel.add(d, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 4;
		bottomPanel.add(f, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 5;
		bottomPanel.add(g, bottomPanelConstraints);	
		bottomPanelConstraints.gridx = 6;
		bottomPanel.add(h, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 7;
		bottomPanel.add(j, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 8;
		bottomPanel.add(k, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 9;
		bottomPanel.add(l, bottomPanelConstraints);
		bottomPanelConstraints.gridy = 2;
		bottomPanelConstraints.gridx = 2;
		bottomPanel.add(z, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 3;
		bottomPanel.add(x, bottomPanelConstraints);	
		bottomPanelConstraints.gridx = 4;
		bottomPanel.add(c, bottomPanelConstraints);	
		bottomPanelConstraints.gridx = 5;
		bottomPanel.add(v, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 6;
		bottomPanel.add(b, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 7;
		bottomPanel.add(n, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 8;
		bottomPanel.add(m, bottomPanelConstraints);
		bottomPanelConstraints.gridy = 3;
		bottomPanelConstraints.gridx = 4;
		
		/**********  FINALIZATION **********/
		
		addKeyListener(buttonKeyListener);
		add(bottomPanel, BorderLayout.PAGE_END);
		add(topPanel, BorderLayout.PAGE_START);
		addWindowListener(this);
		setVisible(true);
		
		requestFocus(); //request focus only after component has been rendered (setVisible()).
	}
	
	/**********
	 * Starts the program by calling the screen to be populated with words (populateScreen).
	 **********/
	public void startProgram(){	
		populateScreen(); //populates the screen with words
	}
	
	/**********
	 * Creates a StringBuilder string that is used to populate the screen with words.
	 * Default number of words is 35.
	 * Adds newline character at the end of each 30 character line.
	 **********/
	public void populateScreen(){
		listSize = wordList.getSize();
		wordString.delete(0, wordString.length());
		lineLength = 0;
		counter = 0;
		
		while(counter < 35){
			tempWord = wordList.getRandomWord();
			lineLength += tempWord.length();
			
			if(counter == 0){
				wordString.append(tempWord);
				counter++;
			}
			else if(counter != 0 && lineLength <= 30){
				wordString.append(" ");
				wordString.append(tempWord);
				counter++;
			}
			else if(counter != 0 && lineLength > 30){
				wordString.append("\n");
				wordString.append(tempWord);
				counter++;
				lineLength = 0;
			}
		}

		screen.setText(wordString.toString());
	}
	
	/**********
	 * Method to retrieve JTextPane screen.
	 **********/
	public JTextPane getScreen(){
		return screen;
	}
	
	/**********
	 * Method to retrieve StringBuilder wordString.
	 **********/
	public StringBuilder getWordString(){
	    return wordString;
	}
	
	
	/**********
	 * Method to retrieve a button from the button hash map (buttonMap).
	 **********/
	public JButton getButton(int key){
		button = (JButton)buttonMap.get(key);
		return button;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
























/*import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class WPM_Driver extends JFrame implements WindowListener{
	static JPanel topPanel, bottomPanel;
	static JTextPane screen;
	static JFrame resultsMessage;
	static JButton button,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,space;
	ButtonKeyListener buttonKeyListener;
    static HashMap buttonMap;
    static WordList wordList;
    static int listSize = 0, lineLength = 0, counter = 0, renderTrigger = 0, menuOption = 1;
    static StringBuilder wordString;
    static String tempWord = "";
    static long startTime = 0, endTime = 0;
	static volatile int timerTrigger = 0; //volatile; the variable is accessed by multiple classes (ButtonKeyListener calls a method to change it's value) and the program was becoming unsynchronized at the startProgram() loop.
    
	/*public static void main(String[] args){
		while(menuOption != 0){
			switch(menuOption){
				case 1: startProgram();
				case 0: System.exit(0);
			}
		}
	}
	
	public WPM_Driver(){
		this.setSize(1000,800);
		this.setTitle("WPM");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		
		wordList = new WordList(); 
		wordString = new StringBuilder();
		
		resultsMessage = new JFrame();
		
		topPanel = new JPanel();
		screen = new JTextPane();
		screen.setSize(950,950);
		screen.setEditable(false);
		Font screenFont = new Font("Serif", 0, 50);
		screen.setFont(screenFont);
		topPanel.add(screen);
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		GridBagConstraints bottomPanelConstraints = new GridBagConstraints();
		bottomPanelConstraints.insets = new Insets(10,10,10,10);
		bottomPanelConstraints.ipadx = 30;
		bottomPanelConstraints.ipady = 40;
		bottomPanelConstraints.weightx = 0.5;
		
		buttonKeyListener = new ButtonKeyListener();
		
		q = new JButton("Q");
		//q.setEnabled(false);
		w = new JButton("W");
		//q.doClick();
		e = new JButton("E");
		r = new JButton("R");
		t = new JButton("T");
		y = new JButton("Y");
		u = new JButton("U");
		i = new JButton("I");
		o = new JButton("O");
		p = new JButton("P");
		
		a = new JButton("A");
		s = new JButton("S");
		d = new JButton("D");
		f = new JButton("F");
		g = new JButton("G");
		h = new JButton("H");
		j = new JButton("J");
		k = new JButton("K");
		l = new JButton("L");
		
		z = new JButton("Z");
		x = new JButton("X");
		c = new JButton("C");
		v = new JButton("V");
		b = new JButton("B");
		n = new JButton("N");
		m = new JButton("M");
		
		space = new JButton("Space Bar");
		
		button = new JButton();
		
		buttonMap = new HashMap<Integer, String>();
		buttonMap.put(81, q);
		buttonMap.put(87, w);
		buttonMap.put(69, e);
		buttonMap.put(82, r);
		buttonMap.put(84, t);
		buttonMap.put(89, y);
		buttonMap.put(85, u);
		buttonMap.put(73, i);
		buttonMap.put(79, o);
		buttonMap.put(80, p);
		buttonMap.put(65, a);
		buttonMap.put(83, s);
		buttonMap.put(68, d);
		buttonMap.put(70, f);
		buttonMap.put(71, g);
		buttonMap.put(72, h);
		buttonMap.put(74, j);
		buttonMap.put(75, k);
		buttonMap.put(76, l);
		buttonMap.put(90, z);
		buttonMap.put(88, x);
		buttonMap.put(67, c);
		buttonMap.put(86, v);
		buttonMap.put(66, b);
		buttonMap.put(78, n);
		buttonMap.put(77, m);
		buttonMap.put(32, space);
		
		bottomPanelConstraints.gridy = 0;
		bottomPanelConstraints.gridx = 0;
		bottomPanel.add(q, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 1;
		bottomPanel.add(w, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 2;
		bottomPanel.add(e, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 3;
		bottomPanel.add(r, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 4;
		bottomPanel.add(t, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 5;
		bottomPanel.add(y, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 6;
		bottomPanel.add(u, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 7;
		bottomPanel.add(i, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 8;
		bottomPanel.add(o, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 9;
		bottomPanel.add(p, bottomPanelConstraints);
		bottomPanelConstraints.gridy = 1;
		bottomPanelConstraints.gridx = 1;
		bottomPanel.add(a, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 2;
		bottomPanel.add(s, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 3;
		bottomPanel.add(d, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 4;
		bottomPanel.add(f, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 5;
		bottomPanel.add(g, bottomPanelConstraints);	
		bottomPanelConstraints.gridx = 6;
		bottomPanel.add(h, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 7;
		bottomPanel.add(j, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 8;
		bottomPanel.add(k, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 9;
		bottomPanel.add(l, bottomPanelConstraints);
		bottomPanelConstraints.gridy = 2;
		bottomPanelConstraints.gridx = 2;
		bottomPanel.add(z, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 3;
		bottomPanel.add(x, bottomPanelConstraints);	
		bottomPanelConstraints.gridx = 4;
		bottomPanel.add(c, bottomPanelConstraints);	
		bottomPanelConstraints.gridx = 5;
		bottomPanel.add(v, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 6;
		bottomPanel.add(b, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 7;
		bottomPanel.add(n, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 8;
		bottomPanel.add(m, bottomPanelConstraints);
		bottomPanelConstraints.gridy = 3;
		bottomPanelConstraints.gridx = 4;
		//bottomPanel.add(space, bottomPanelConstraints);
		
		//this.setFocusable(true);
		this.requestFocus(); //should probably delete
		this.addKeyListener(buttonKeyListener);
		this.add(bottomPanel, BorderLayout.PAGE_END);
		this.add(topPanel, BorderLayout.PAGE_START);
		this.addWindowListener(this);
		this.setVisible(true);
		
		this.requestFocus(); //request focus only after component has been rendered (setVisible()). 
		
		//System.out.println(buttonMap.keySet());
	}
	
	public static void startProgram(){
		while(endTime < 5 ){
			if(timerTrigger == 1){
				if(startTime == 0){
					startTime = System.nanoTime();
				}
				endTime = (System.nanoTime() - startTime)/1000000000;
			}
			
			//System.out.println("\n" + endTime + "\n");
			//System.out.println("");
			
			if(renderTrigger == 0){
				new WPM_Driver();
				populateScreen(); //populates the screen with words
			}
			renderTrigger = 1;
		}
		
		JOptionPane.showMessageDialog(resultsMessage, "Your score is: " 
				+ ButtonKeyListener.getWordCount() + " WPM", "Your Results", JOptionPane.PLAIN_MESSAGE);
		
		//System.exit(0);
	}
	
	public static JButton getButton(int key){
		button = (JButton)buttonMap.get(key);
		return button;
	}
	
	public static void populateScreen(){
		listSize = wordList.getSize();
		wordString.delete(0, wordString.length());
		counter = 0;
		
		while(counter < 35){
			tempWord = wordList.getRandomWord();
			lineLength += tempWord.length();
			
			if(counter == 0){
				wordString.append(tempWord);
				counter++;
			}
			else if(counter != 0 && lineLength <= 30){
				wordString.append(" ");
				wordString.append(tempWord);
				counter++;
			}
			else if(counter != 0 && lineLength > 30){
				wordString.append("\n");
				wordString.append(tempWord);
				counter++;
				lineLength = 0;
			}
		}
		
		screen.setDisplayText(wordString.toString());
	}
	
	public static StringBuilder getWordString(){
		return wordString;
	}
	
	public static JTextPane getScreen(){
		return screen;
	}
	
	public static void startTimer(){
		timerTrigger = 1;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		new WPM_Menu();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}*/
