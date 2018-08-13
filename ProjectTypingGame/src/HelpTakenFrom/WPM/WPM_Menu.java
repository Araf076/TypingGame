package HelpTakenFrom.WPM;
/*****
 * @Copyright (C) 2016 Ernesto D Martinez - All Rights Reserved
 *****/

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class WPM_Menu extends JFrame implements ActionListener{
	
	WPM_Driver wpm_Driver;
	JPanel topPanel, middlePanel, bottomPanel;
	JLabel intro;
	JRadioButton oneMinute, twoMinute, threeMinute;
	ButtonGroup buttonGroup;
	JButton startButton, exitButton;
	int minutesSelected = 60;
	volatile boolean start = false;
	boolean exit = false;
	JFrame messageFrame;
	GridBagConstraints menuConstraints, bottomPanelConstraints;
	Border raisedBevelBorder, lineBorder, matteBorder;
	Font labelFont, radialFont, buttonFont;
	
	public WPM_Menu(){
		
		/********** INITIAL SETTINGS **********/
		
		setTitle("Word Per Minute");
		setSize(400,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		
		labelFont = new Font("Serif", 3, 25);
		radialFont = new Font("Serif", 1, 20);
		buttonFont = new Font("Serif", 1, 25);
		
		menuConstraints = new GridBagConstraints();
		menuConstraints.insets = new Insets(10,10,10,10);
		menuConstraints.ipadx = 30;
		menuConstraints.ipady = 40;
		menuConstraints.weightx = 0.5;
		
		
		/********** TOP PANEL **********/
		
		topPanel = new JPanel();
		
		lineBorder = BorderFactory.createLineBorder(Color.BLACK);
		topPanel.setBorder(lineBorder);
		
		intro = new JLabel("<html><font color='#cc3300'>"
				+ "Welcome to WPM! Please choose a time interval and click \"Start\"</font></html>");
		intro.setFont(labelFont);
		
		topPanel.add(intro);
		
		
		/********** MIDDLE PANEL **********/
		
		middlePanel = new JPanel();
		
		matteBorder = BorderFactory.createMatteBorder(0, 100, 0, 100, Color.BLACK);
		middlePanel.setBorder(matteBorder);
		
		oneMinute = new JRadioButton("1 minute");
		oneMinute.setFont(radialFont);
		oneMinute.addActionListener(this);
		oneMinute.setSelected(true);
		
		twoMinute = new JRadioButton("2 minutes");
		twoMinute.setFont(radialFont);
		twoMinute.addActionListener(this);
		
		threeMinute = new JRadioButton("3 minutes");
		threeMinute.setFont(radialFont);
		threeMinute.addActionListener(this);
		
		buttonGroup = new ButtonGroup();
		
		buttonGroup.add(oneMinute);
		buttonGroup.add(twoMinute);
		buttonGroup.add(threeMinute);
		
		middlePanel.add(oneMinute);
		middlePanel.add(twoMinute);
		middlePanel.add(threeMinute);
		
		
		/********** BOTTOM PANEL **********/
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		bottomPanelConstraints = new GridBagConstraints();
		bottomPanelConstraints.insets = new Insets(10,10,10,10);
		bottomPanelConstraints.ipadx = 60;
		bottomPanelConstraints.ipady = 20;
		bottomPanelConstraints.weightx = 0.5;
		
		raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
		
		startButton = new JButton("Start");
		startButton.setBorder(raisedBevelBorder);
		startButton.setFont(buttonFont);
		Color startButtonColor = new Color(0,153,51);
		startButton.setBackground(startButtonColor);
		startButton.setForeground(Color.WHITE);
		startButton.addActionListener(this);
		
		exitButton = new JButton("Exit");
		exitButton.setBorder(raisedBevelBorder);
		exitButton.setFont(buttonFont);
		Color exitButtonColor = new Color(0,52,102);
		exitButton.setBackground(exitButtonColor);
		exitButton.setForeground(Color.WHITE);
		exitButton.addActionListener(this);
		
		bottomPanelConstraints.gridy = 0;
		bottomPanelConstraints.gridx = 0;
		bottomPanel.add(startButton, bottomPanelConstraints);
		bottomPanelConstraints.gridx = 3;
		bottomPanel.add(exitButton, bottomPanelConstraints);
		
		/********** ADDING PANELS TO FRAME **********/
		
		menuConstraints.gridy = 0;
		menuConstraints.gridx = 3;
		add(topPanel, menuConstraints);
		menuConstraints.gridy = 1;
		add(middlePanel, menuConstraints);
		menuConstraints.gridy = 2;
		add(bottomPanel, menuConstraints);
		
		pack();
		
		setVisible(true);
	}
	
	/**********
	 * Initializes the WPM_Driver class (the typing GUI) once the menu's Start button is pressed.
	 * Exits when Exit is pressed.
	 * The variable minutesSelected controls typing time interval. It is adjusted here per the user's selection.
	 **********/
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand() == "1 minute"){
			minutesSelected = 60;
		}
		else if(e.getActionCommand() == "2 minutes"){
			minutesSelected = 120;
		}
		else if(e.getActionCommand() == "3 minutes"){
			minutesSelected = 180;
		}
		
		if(e.getActionCommand() == "Start"){
			wpm_Driver = new WPM_Driver(minutesSelected);
			wpm_Driver.startProgram();
			dispose();
		}
		
		if(e.getActionCommand() == "Exit"){
			dispose();
		}
	}
}





























































/*import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class WPM_Menu extends JFrame implements ActionListener{
	
	JLabel intro;
	JRadioButton oneMinute, twoMinute, threeMinute;
	ButtonGroup buttonGroup;
	JPanel radialPanel;
	JButton startButton, exitButton;
	int minutesSelected = 1;
	static volatile boolean start = false;
	static boolean exit = false;
	static JFrame messageFrame;
	
	public static void main(String[] args){
		new WPM_Menu();
		messageFrame = new JFrame();
		
		while(exit == false){
			if(start == true){
				WPM_Driver.startProgram();
				start = false;
			}
		}
	}
	
	public WPM_Menu(){
		this.setTitle("Word Per Minute");
		this.setSize(400,400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		JLabel intro = new JLabel("Welcome to WPM. Please choose a time interval and click \"Start\".");
		
		oneMinute = new JRadioButton("1 minute");
		oneMinute.addActionListener(this);
		oneMinute.setSelected(true);
		
		twoMinute = new JRadioButton("2 minutes");
		twoMinute.addActionListener(this);
		
		threeMinute = new JRadioButton("3 minutes");
		threeMinute.addActionListener(this);
		
		buttonGroup = new ButtonGroup();
		
		buttonGroup.add(oneMinute);
		buttonGroup.add(twoMinute);
		buttonGroup.add(threeMinute);
		
		radialPanel = new JPanel();
		
		radialPanel.add(oneMinute);
		radialPanel.add(twoMinute);
		radialPanel.add(threeMinute);
		
		startButton = new JButton("Start");
		startButton.addActionListener(this);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		
		this.add(intro, BorderLayout.PAGE_START);
		this.add(radialPanel, BorderLayout.CENTER);
		this.add(startButton, BorderLayout.PAGE_END);
		this.add(exitButton, BorderLayout.LINE_END);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand() == "1 minute"){
			minutesSelected = 1;
		}
		else if(e.getActionCommand() == "2 minutes"){
			minutesSelected = 2;
		}
		else if(e.getActionCommand() == "3 minutes"){
			minutesSelected = 3;
		}
		
		if(e.getActionCommand() == "Start"){
			this.setVisible(false);
			start = true;
		}
		
		//System.out.println(minutesSelected);
	}
}*/
