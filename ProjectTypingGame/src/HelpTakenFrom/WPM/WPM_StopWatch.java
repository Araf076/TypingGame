package HelpTakenFrom.WPM;
/*****
 * @Copyright (C) 2016 Ernesto D Martinez - All Rights Reserved
 *****/

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

public class WPM_StopWatch extends SwingWorker implements ActionListener{
	
	JFrame scoreFrame;
	JPanel scorePanel;
	JLabel scoreLabel;
	JButton closeButton;
	Font labelFont, buttonFont;
	ButtonKeyListener buttonKeyListener;
	GridBagConstraints gbc;
	WPM_Driver wpm_Driver;
	boolean done = false;
	long endTime = 0, startTime = 0, timeInterval;
	
	public WPM_StopWatch(int time, ButtonKeyListener bkl, WPM_Driver driver){
		timeInterval = time;
		buttonKeyListener = bkl;
		wpm_Driver = driver;
		scoreFrame = new JFrame();
		scorePanel = new JPanel();
		scoreLabel = new JLabel();
		closeButton = new JButton("Close");
		labelFont = new Font("Serif", 3, 35);
		buttonFont = new Font("Serif", 3, 15);
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(10,10,10,10);
		gbc.ipadx = 30;
		gbc.ipady = 40;
		gbc.weightx = 0.5;
		
		/********** Code that prevents the space bar from closing the score window. **********/
		InputMap im = (InputMap)UIManager.get("Button.focusInputMap");
		im.put(KeyStroke.getKeyStroke("pressed SPACE"), "none");
		im.put(KeyStroke.getKeyStroke("released SPACE"), "none");
	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
		while(endTime < timeInterval ){
				if(startTime == 0){
					startTime = System.nanoTime();
				}
				endTime = (System.nanoTime() - startTime)/1000000000;
				
				//System.out.println(endTime);
		}
		
		scoreFrame.setTitle("Your Score");
		scoreFrame.setSize(500, 300);
		scoreFrame.setLocationRelativeTo(null);
		scoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scoreFrame.setLayout(new GridBagLayout());
		
		
		scoreLabel.setText("Your score is: " + buttonKeyListener.getWordCount() + " WPM");
		scoreLabel.setFont(labelFont);
		
		scorePanel.add(scoreLabel);
		
		closeButton.addActionListener(this);
		closeButton.setFont(buttonFont);
		
		gbc.gridy = 0;
		gbc.gridx = 0;
		scoreFrame.add(scorePanel, gbc);
		gbc.gridy = 1;
		gbc.gridx = 0;
		scoreFrame.add(closeButton, gbc);
		scoreFrame.setVisible(true);
		
		return null;
	}

	/**********
	 * Called when Close button on the score window is pressed.
	 * Starts a new program instance and calls it's main.
	 * Closes wpm_driver and scoreFrame windows.
	 **********/
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getActionCommand() == "Close"){
			WPM_StartProgram newProgram = new WPM_StartProgram();
			newProgram.main(null);
			wpm_Driver.dispose();
			scoreFrame.dispose();
		}
	}
}