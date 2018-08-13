package MainProject;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

//TypingGame class header - creates frame
public class TypingGame
{
    MasterPanel masterPanel;
    JFrame frame;

    //Constructor
    public TypingGame() {
        frame = null;
        masterPanel = null;
    }

    public static void main (String[] args)
    {
        //call run()
        TypingGame typingGame = new TypingGame();
        typingGame.run();
    }

    //run() method header - creates JFrame, calls MasterPanel()
    public void run()
    {
        //creates JFrame with default settings
        frame = new JFrame ("Typing Game");

        frame.setSize(1300, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(20, 20);
        frame.setResizable(false);

        //initializes instance of MasterPanel and adds it to frame
        masterPanel = new TypingGame.MasterPanel();
        frame.add(masterPanel);

        frame.setVisible(true);
    }

    //MasterPanel - contains HomePanel, LevelScorePanel, HighScoresPanel, GameplayPanel, FinalPanel
    public class MasterPanel extends JPanel
    {
        //field vars
        CardLayout cardLayout;
        Image backgroundImage;
        Analysis analysis;

        TypingGame.MasterPanel.HomePanel homePanel;
        TypingGame.MasterPanel.GameplayPanel game;
        TypingGame.MasterPanel.LevelScorePanel levelScorePanel;
        TypingGame.MasterPanel.HighScoresPanel highScoresPanel;
        TypingGame.MasterPanel.FinalPanel finalPanel;


        //constructor - add panels to CardLayout
        public MasterPanel()
        {
            //set layout to CardLayout
            cardLayout = new CardLayout();
            setLayout(cardLayout);

            //create and add instance of HomePanel to CardLayout
            homePanel = new TypingGame.MasterPanel.HomePanel();
            add(homePanel, "Home");

            //create and add instance of GameplayPanel to CardLayout
            game = new TypingGame.MasterPanel.GameplayPanel();
            add(game, "Gameplay");

            //create and add instance of LevelScorePanel
            levelScorePanel = new TypingGame.MasterPanel.LevelScorePanel();
            add(levelScorePanel, "LevelScore");

            //create and add instance of HighScoresPanel
            highScoresPanel = new HighScoresPanel();
            add(highScoresPanel, "HighScores");

            //create and add instance of FinalPanel
            finalPanel = new TypingGame.MasterPanel.FinalPanel();
            add(finalPanel, "Congrats");
        }

        //HomePanel - codes for the Home panel
        class HomePanel extends JPanel implements ActionListener {
            //declare field vars: backgroundImageName
            private String backgroundImageName;

            //constructor - prints backgroundImage to background, creates start and instructions button
            public HomePanel()
            {
                //backgroundImage name
                backgroundImageName = "TypingGame1.png";

                getImage();

                repaint();

                //set layout to FlowLayout
                setLayout(new FlowLayout(FlowLayout.CENTER, 100, 150));

                //set fonts
                Font homeTAfont = new Font("Serif", Font.BOLD, 80);
                Font buttonFont = new Font("Arial", Font.BOLD, 40);

                //topPanel
                JPanel topPanel = new JPanel();

                //creates JTextArea with heading
                JTextArea homeTextArea = new JTextArea("Welcome to Typing Game");
                homeTextArea.setForeground(Color.WHITE);
                homeTextArea.setOpaque(false);
                homeTextArea.setFont(homeTAfont);
                homeTextArea.setEditable(false);

                topPanel.add(homeTextArea);
                topPanel.setOpaque(false);

                add(topPanel);

                //bottomPanel
                JPanel bottomPanel = new JPanel();
                bottomPanel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10,10,10,10);

                //create start button
                gbc.gridx = 0;
                gbc.gridy = 0;
                JButton startButton = new JButton("Start");
                startButton.setOpaque(false);
                startButton.setFont(buttonFont);
                startButton.addActionListener(this);
                bottomPanel.add(startButton, gbc);

                //create HighScores button
                gbc.gridy = 1;
                JButton highScoresButton = new JButton("High Scores");
                highScoresButton.setOpaque(false);
                highScoresButton.setFont(buttonFont);
                highScoresButton.addActionListener(this);
                bottomPanel.add(highScoresButton, gbc);

                //create exit button
                gbc.gridy = 2;
                JButton exitButton = new JButton("Exit");
                exitButton.setOpaque(false);
                exitButton.setFont(buttonFont);
                exitButton.addActionListener(this);
                bottomPanel.add(exitButton, gbc);

                bottomPanel.setOpaque(false);
                add(bottomPanel);
            }

            //getImage() - loads backgroundImage file for background
            public void getImage()
            {
                File imageFile = new File("TypingGame1.png");
                try
                {
                    backgroundImage = ImageIO.read(imageFile);
                }
                catch (IOException e)
                {
                    System.err.println("\n" + backgroundImageName + " can't be found.\n");
                    e.printStackTrace();
                }
            }

            //paintComponent - sets background to the backgroundImage loaded
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, this);
            }

            //actionPerformed() header - changes panel in CardLayout if button is pressed
            public void actionPerformed(ActionEvent evt)
            {
                //use getActionCommand to find which button was pressed
                String optionPicked;
                optionPicked = evt.getActionCommand();

                //flip to the correct panel
                if (optionPicked.equals("Start"))
                {
                    cardLayout.show(masterPanel, "Gameplay");
                }
                else if(optionPicked.equals("High Scores")){
                    highScoresPanel.setHighScores();
                    cardLayout.show(masterPanel, "HighScores");
                }
                else if (optionPicked.equals("Exit"))
                {
                    System.exit(1);
                }
            }
        }

        //GameplayPanel - panel where the actual game is played
        class GameplayPanel extends JPanel implements ActionListener {

            JPanel topPanel, centerPanel, bottomPanel, upperBottomPanel, lowerBottomPanel;

//            JTextPane feedbackTextPane;
            JTextField feedbackTextField;
            JTextArea displayTextArea;

            JTextField inputTextField;
            JButton restartButton, homeButton;
            JTextArea levelArea;
            boolean levelPassed = false;

            int level;
            long time, startTIme, endTime;
            double timeInMinutes;

            Border lineBorder, matteBorder;

// for class CustomKeyListener
            String thisLine;
            String[] words;
            char character[], currentChar;
            String stringA, stringB, stringC;

            StyleContext styleContext;
            StyledDocument styledDoc;
            Color backgroundColor, foregroundColor;
            Color redBackgroundColor, redForegroundColor, blueBackgroundColor, blueForegroundColor;
            Style blueStyle, yellowStyle, redStyle;

            int currentCharacterIndex = 0, correctCharacterCount = 0, wrongCharacterCount = 0;

            //for setDisplayText
            String thisLine2, textLevel1, textLevel2, textLevel3, textLevel4;

            public GameplayPanel() {

                level = 1;
                //colors
                Color backgroundColor = new Color(255, 255, 255);
                Color textAreaColor = new Color(18, 88, 131);

                setBackground(backgroundColor);

                //set layout
                setLayout(new BorderLayout());
//                setLayout(new GridBagLayout());
//                GridBagConstraints gbc = new GridBagConstraints();

                //Top PANEL
                //create restartLevel button and area for level
                topPanel = new JPanel();
                topPanel.setLayout(new BorderLayout());
//                topPanel.setLayout(new GridBagLayout());
//                GridBagConstraints gbcTop = new GridBagConstraints();
                topPanel.setBackground(backgroundColor);

                //font
                Font buttonfont = new Font("Serif", Font.BOLD, 25);
                Font levelAreaFont = new Font("Serif", Font.BOLD, 40);

                levelArea = new JTextArea("Level 1");
                levelArea.setOpaque(false);
                levelArea.setForeground(textAreaColor);
                levelArea.setFont(levelAreaFont);
                levelArea.setEditable(false);

                homeButton = new JButton("Home");
                homeButton.setFont(buttonfont);
                homeButton.addActionListener(this);

                restartButton = new JButton("Restart");
                restartButton.setFont(buttonfont);
                restartButton.addActionListener(this);

                matteBorder = BorderFactory.createMatteBorder(0,0,1,0, Color.darkGray);
                topPanel.setBorder(matteBorder);

                topPanel.add(homeButton, BorderLayout.WEST);
                topPanel.add(levelArea, BorderLayout.CENTER);
                topPanel.add(restartButton, BorderLayout.LINE_END);
//                gbcTop.gridx = 0;
//                topPanel.add(levelArea, gbcTop);
//                gbcTop.gridx = 4;
//                topPanel.add(restartButton, gbcTop);

//                gbc.fill = GridBagConstraints.HORIZONTAL;
////                gbc.ipady = 10;      //make this component tall
//                gbc.weightx = 0.0;
//                gbc.gridwidth = 5;
//                gbc.gridx = 0;
//                gbc.gridy = 0;
//                add(topPanel, gbc);
                add(topPanel, BorderLayout.PAGE_START);


                //Center PANEL
                centerPanel = new JPanel();
//                centerPanel.setLayout(new BorderLayout());
                centerPanel.setOpaque(false);


                displayTextArea = new JTextArea(8,30);
                displayTextArea.setLineWrap(true);
                displayTextArea.setWrapStyleWord(true);

                displayTextArea.setEditable(false);
                Font displayTextAreaFont = new Font("Serif", Font.PLAIN, 45);
                displayTextArea.setFont(displayTextAreaFont);

                Color displayBorderColor = new Color(72, 134, 94);
                matteBorder = BorderFactory.createMatteBorder(0,0,1,0, displayBorderColor);
                displayTextArea.setBorder(matteBorder);
                //setting text in displayArea
                setDisplayText();

                centerPanel.add(displayTextArea);

//                feedbackTextPane = new JTextPane();
//                feedbackTextPane.setSize(100, 100);
//                feedbackTextPane.setEditable(false);
//                feedbackTextPane.setFont(displayTextAreaFont);

//                gbc.fill = GridBagConstraints.HORIZONTAL;
//                gbc.ipady = 50;      //make this component tall
//                gbc.weightx = 0.0;
//                gbc.gridwidth = 5;
//                gbc.gridx = 0;
//                gbc.gridy = 1;
//                add(centerPanel, gbc);
                add(centerPanel, BorderLayout.CENTER);

                //Bottom Panel
                bottomPanel = new JPanel();
                bottomPanel.setLayout(new BorderLayout());
                bottomPanel.setOpaque(false);

                //Upper Bottom Panel
                upperBottomPanel = new JPanel();
                upperBottomPanel.setOpaque(false);

                feedbackTextField = new JTextField(20);
                feedbackTextField.setHorizontalAlignment(SwingConstants.CENTER);
                feedbackTextField.setEditable(false);
                Font feedbackFont = new Font("Serif", Font.PLAIN, 40);
                feedbackTextField.setFont(feedbackFont);
                feedbackTextField.setText("Type Status");
                feedbackTextField.setBackground(Color.WHITE);
                feedbackTextField.setForeground(Color.BLACK);

                lineBorder = BorderFactory.createLineBorder(Color.black);
                feedbackTextField.setBorder(lineBorder);

                upperBottomPanel.add(feedbackTextField);

                //Lower Bottom Panel
                lowerBottomPanel = new JPanel();
                lowerBottomPanel.setOpaque(false);

                inputTextField = new JTextField(30);
                inputTextField.setEditable(true);
                Font inputFont = new Font("Serif", Font.PLAIN, 40);
                inputTextField.setFont(inputFont);

                Color inputBorderColor = new Color(89, 115, 147);
                lineBorder = BorderFactory.createLineBorder(inputBorderColor);
                inputTextField.setBorder(lineBorder);

                lowerBottomPanel.add(inputTextField);
                bottomPanel.add(upperBottomPanel, BorderLayout.NORTH);
                bottomPanel.add(lowerBottomPanel, BorderLayout.SOUTH);

                add(bottomPanel, BorderLayout.PAGE_END);

                inputTextField.setText("Click here to Start");
                inputTextField.addMouseListener(new MouseListener()
                {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        inputTextField.setText(null);
                        startTIme = System.currentTimeMillis();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                inputTextField.addKeyListener(new CustomKeyListener());
            }

            //handles keyListener
            class CustomKeyListener implements KeyListener {

                public CustomKeyListener() {
//                    styleContext = StyleContext.getDefaultStyleContext();
//
//                    // Create and add the styles
//                    backgroundColor = new Color(214, 230, 249);
//                    foregroundColor = new Color(39, 85, 129);
//                    blueStyle = styleContext.addStyle("Blue", null);
//                    blueStyle.addAttribute(StyleConstants.Foreground, foregroundColor);
//                    blueStyle.addAttribute(StyleConstants.Background, backgroundColor);
//
//                    backgroundColor = new Color(255, 233, 184);
//                    foregroundColor = new Color(42, 121, 26);
//                    yellowStyle = styleContext.addStyle("Yellow", null);
//                    yellowStyle.addAttribute(StyleConstants.Foreground, foregroundColor);
//                    yellowStyle.addAttribute(StyleConstants.Background, backgroundColor);
//
//                    backgroundColor = new Color(246, 196, 204);
//                    foregroundColor = new Color(138, 39, 36);
//                    redStyle = styleContext.addStyle("Red", null);
//                    redStyle.addAttribute(StyleConstants.Foreground, foregroundColor);
//                    redStyle.addAttribute(StyleConstants.Background, backgroundColor);
//
//                    styledDoc = feedbackTextPane.getStyledDocument();

                    //feedbackColor
                    redBackgroundColor = new Color(246, 196, 204);
                    redForegroundColor = new Color(138, 39, 36);

                    blueBackgroundColor = new Color(214, 230, 249);
                    blueForegroundColor = new Color(39, 85, 129);
                }

                public void keyTyped(KeyEvent e) {
                    thisLine = displayTextArea.getText();
                    character = thisLine.toCharArray();

                    if (currentCharacterIndex < character.length) {
                        currentChar = character[currentCharacterIndex];
//                        stringA = String.valueOf(currentChar);

                        try {

                            //CASE: User presses same key
                            if (e.getKeyChar() == currentChar) {
                                System.out.println("Same Character: Pressed: " + e.getKeyChar() +
                                        " Matched with: " + currentChar);

//                                //user presses space
//                                if (Character.isSpaceChar(currentChar)) {
//
//                                }

                                //coloring
//                              styledDoc.insertString(currentCharacterIndex, stringA, blueStyle);
                                feedbackTextField.setText("Same Key Typed");
                                feedbackTextField.setForeground(blueForegroundColor);
                                feedbackTextField.setBackground(blueBackgroundColor);

                                currentCharacterIndex++;
                                correctCharacterCount++;
                            }

//                            //CASE: User presses the backspace key
//                            else if (e.getKeyCode() == 8 && currentCharacterIndex > 0) {
//                                currentCharacterIndex--;
//
////                               correctCharacterCount--;
////                               wrongCharacterCount--;
//
//                                //empty formatting
////                              styledDoc.insertString(currentCharacterIndex, stringA, null);
//                            }

                            //CASE: User presses different key
                            else if (e.getKeyChar() != currentChar) {
                                System.out.println("Different Character: Pressed: " + e.getKeyChar() +
                                        " Matched with: " + currentChar);

                                //coloring
//                              styledDoc.insertString(currentCharacterIndex, stringA, redStyle);
                                feedbackTextField.setText("Different Key Typed");
                                feedbackTextField.setForeground(redForegroundColor);
                                feedbackTextField.setBackground(redBackgroundColor);

                                currentCharacterIndex++;
                                wrongCharacterCount++;
                            }

                            //if all words are typed
                            if (currentCharacterIndex == character.length) {
                                endTime = System.currentTimeMillis();
                                time = endTime - startTIme;

                                //converting miliseconds to minute
                                timeInMinutes = (double) time;
                                timeInMinutes = timeInMinutes/(1000 * 60);

                                //comment out
                                //making analysis & printing on console
                                System.out.println("");
                                System.out.println("Level Finished");
                                System.out.println("");
                                System.out.println(time + " miliSeconds, " + timeInMinutes + " minutes");

//                                analysis = new Analysis(timeInMinutes, character.length, correctCharacterCount,wrongCharacterCount,level);
                                analysis = new Analysis();

//                                System.out.println("Before setting values manually,");
//                                System.out.println("Calculation of Accuracy: " + ((analysis.correctCharacters/analysis.totalCharacters) * 100));
//                                System.out.println("Calculation of Gross WPM: " + ( (analysis.totalCharacters/5) / timeInMinutes ) );
//                                System.out.println("Calculation of Net WPM: " + ( analysis.grossWPM - (analysis.wrongCharacters/ timeInMinutes) ));
//
//                                System.out.println("Total Characters: " + analysis.totalCharacters + " Correct: " + analysis.correctCharacters +
//                                        " Wrong: " + analysis.wrongCharacters);
//                                System.out.println("Time in minutes: " + analysis.timeInMinutes + " Time in seconds: " + analysis.timeInSeconds);
//                                System.out.println("Gross WPM: " + analysis.grossWPM + " Net WPM: " + analysis.netWPM);
//                                System.out.println("Accuracy: " + analysis.accuracy + " %" + " Score: " + analysis.score + " Time: " + analysis.timeInSeconds);
//                                System.out.println("Level: " + analysis.level);
//                                System.out.println("Required Accuracy: " + analysis.requiredAccuracy + " Required Net WPM: " + analysis.requiredNetWPM +
//                                        " Target Net WPM: " +analysis.targetNetWPM);
//
//                                System.out.println("");
//                                System.out.println("After setting values manually,");
                                analysis.setCorrectCharacters(correctCharacterCount);
                                analysis.setWrongCharacters(wrongCharacterCount);
                                analysis.setTotalCharacters(character.length);
                                analysis.setTimeInMinutes(timeInMinutes);
//                                analysis.setTimeInSeconds();

                                analysis.setLevel(level);
//                                analysis.setRequiredAccuracy();
//                                analysis.setRequiredNetWPM();
//                                analysis.setTargetNetWPM();

                                analysis.setGrossWPM((analysis.totalCharacters/5) / timeInMinutes);
                                analysis.setNetWPM(Math.ceil( ( analysis.grossWPM - (analysis.wrongCharacters/ timeInMinutes) ) ));
                                analysis.setAccuracy(Math.ceil( ( (analysis.correctCharacters/analysis.totalCharacters) * 100 ) ));
//                                analysis.setScore();

                                System.out.println("Calculation of Accuracy: " + ((analysis.correctCharacters/analysis.totalCharacters) * 100));
                                System.out.println("Calculation of Gross WPM: " + ( (analysis.totalCharacters/5) / timeInMinutes ) );
                                System.out.println("Calculation of Net WPM: " + ( analysis.grossWPM - (analysis.wrongCharacters/ timeInMinutes) ));

                                System.out.println("Total Characters: " + analysis.totalCharacters + " Correct: " + analysis.correctCharacters +
                                        " Wrong: " + analysis.wrongCharacters);
                                System.out.println("Time in minutes: " + analysis.timeInMinutes + " Time in seconds: " + analysis.timeInSeconds);
                                System.out.println("Gross WPM: " + analysis.grossWPM + " Net WPM: " + analysis.netWPM);
                                System.out.println("Accuracy: " + analysis.accuracy + " %" + " Score: " + analysis.score + " Time: " + analysis.timeInSeconds);
                                System.out.println("Level: " + analysis.level);
                                System.out.println("Required Accuracy: " + analysis.requiredAccuracy + " Required Net WPM: " + analysis.requiredNetWPM +
                                        " Target Net WPM: " +analysis.targetNetWPM);


                                //displaying score & changing level
                                //add requirements for level pass
//                                if(level > 4)
//                                    game.restartGame();

                                //if all levels are completed
                                if(level == 4){
                                    if(analysis.score > 0){
                                        levelScorePanel.calculateScore();
                                        cardLayout.show(masterPanel, "Congrats");

                                        //store scores to file
                                        try{
                                            FileWriter fl = new FileWriter("savedscores.txt",true);
                                            BufferedWriter bw = new BufferedWriter(fl);
                                            bw.write(analysis.accuracy + " " + analysis.netWPM + " " + analysis.score + " " + analysis.timeInSeconds + System.lineSeparator());
                                            bw.close();
                                        }
                                        catch(Exception exception){
                                            exception.printStackTrace();
                                        }
                                    }
                                    else {
                                        levelScorePanel.calculateScore();
                                        cardLayout.show(masterPanel, "LevelScore");

                                        //store scores to file
                                        try{
                                            FileWriter fl = new FileWriter("savedscores.txt",true);
                                            BufferedWriter bw = new BufferedWriter(fl);
                                            bw.write(analysis.accuracy + " " + analysis.netWPM + " " + analysis.score + " " + analysis.timeInSeconds + System.lineSeparator());
                                            bw.close();
                                        }
                                        catch(Exception exception){
                                            exception.printStackTrace();
                                        }
                                    }
                                }

                                if (level < 4)
                                {
                                    levelScorePanel.calculateScore();
                                    cardLayout.show(masterPanel, "LevelScore");

                                    //store scores to file
                                    try{
                                        FileWriter fl = new FileWriter("savedscores.txt",true);
                                        BufferedWriter bw = new BufferedWriter(fl);
                                        bw.write(analysis.accuracy + " " + analysis.netWPM + " " + analysis.score + " " + analysis.timeInSeconds + System.lineSeparator());
                                        bw.close();
                                    }
                                    catch(Exception exception){
                                        exception.printStackTrace();
                                    }

                                    //if level passed, update level
                                    if(analysis.score > 0){
                                        level++;
                                        levelArea.setText("Level " + level);

                                        currentCharacterIndex = 0;
                                        correctCharacterCount = 0;
                                        wrongCharacterCount = 0;

                                        setDisplayText();

                                        feedbackTextField.setText("Type Status");
                                        feedbackTextField.setBackground(Color.WHITE);
                                        feedbackTextField.setForeground(Color.BLACK);

                                        inputTextField.setText("Click here to Start");
                                        inputTextField.addMouseListener(new MouseListener() {
                                            @Override
                                            public void mouseClicked(MouseEvent e) {
                                                inputTextField.setText(null);
                                                startTIme = System.currentTimeMillis();
                                            }

                                            @Override
                                            public void mousePressed(MouseEvent e) {

                                            }

                                            @Override
                                            public void mouseReleased(MouseEvent e) {

                                            }

                                            @Override
                                            public void mouseEntered(MouseEvent e) {

                                            }

                                            @Override
                                            public void mouseExited(MouseEvent e) {

                                            }
                                        });
                                    }
                                }
                            }

                        } catch (Exception error) {
                            System.err.println("Error: " + error.getMessage());
                            error.printStackTrace();
                        }
                    }
                }

                public void keyPressed(KeyEvent e) {
                }

                public void keyReleased(KeyEvent e) {
                }
            }

            //sets text in displayTextArea
            public void setDisplayText() {
                try{
                    FileReader fileReader = new FileReader("TypingTexts.txt");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    textLevel1 = bufferedReader.readLine();
                    textLevel2 = bufferedReader.readLine();
                    textLevel3 = bufferedReader.readLine();
                    textLevel4 = bufferedReader.readLine();

                    if(level == 1){
                        displayTextArea.setText(textLevel1);
                    }
                    if(level == 2){
                        displayTextArea.setText(textLevel2);
                    }
                    if(level == 3){
                        displayTextArea.setText(textLevel3);
                    }
                    if(level == 4){
                        displayTextArea.setText(textLevel4);
                    }
                }
                catch (Exception e){
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void actionPerformed(ActionEvent evt) {
                //use getActionCommand to find which button was pressed
                String optionPicked;
                optionPicked = evt.getActionCommand();

                //flip to the correct panel
                if (optionPicked.equals("Restart"))
                {
                    game.restartLevel();
                }
                if (optionPicked.equals("Home"))
                {
                    game.restartGame();
                }
            }

            //restarts the game
            void restartGame(){
                frame.dispose();
                TypingGame typingGame = new TypingGame();
                typingGame.run();
            }

//            polymorphism example - overloading
            //restarts the level from gamepanel
            void restartLevel() {
                //sets all values to default if game is restarted
                //if level is restarted,
                //if score isn't initialized
                levelArea.setText("Level " + level);
                System.out.println("");
                System.out.println("Restarting level " + level);
                feedbackTextField.setText("Type Status");
                feedbackTextField.setBackground(Color.WHITE);
                feedbackTextField.setForeground(Color.BLACK);
                setDisplayText();
                currentCharacterIndex = 0;
                correctCharacterCount = 0;
                wrongCharacterCount = 0;
                inputTextField.setText("Click here to Start");
                inputTextField.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        inputTextField.setText(null);
                        startTIme = System.currentTimeMillis();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
            }

            //restarts the level from Level Score panel
            void restartLevel(int l) {
                //sets all values to default if game is restarted
                //if level is restarted,
                //if score isn't initialized
                level = l;
                levelArea.setText("Level " + level);
                System.out.println("");
                System.out.println("Restarting level " + level);

                feedbackTextField.setText("Type Status");
                feedbackTextField.setBackground(Color.WHITE);
                feedbackTextField.setForeground(Color.BLACK);

                setDisplayText();

                currentCharacterIndex = 0;
                correctCharacterCount = 0;
                wrongCharacterCount = 0;

                inputTextField.setText("Click here to Start");
                inputTextField.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        inputTextField.setText(null);
                        startTIme = System.currentTimeMillis();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
            }
        }

        //LevelScorePanel - panel displaying level score analysis
        class LevelScorePanel extends JPanel implements ActionListener{
            JPanel topPanel, centerPanel, centerEastPanel, centerWestPanel, bottomPanel;
            JLabel feedbackLabel, accuracyLabel, speedLabel, durationLabel,
                    accuracyRequirementLabel, speedRequirementLabel, speedGoalLabel,
                    levelScoreLabel, requirementLabel, ScoreLabel,
                    levelLabel;
            JTextField accuracyLevelTF, speedLevelTF, durationLevelTF,
                    accuracyRequiredTF, speedRequiredTF, speedGoalTF,
                    scoreTF;
            JButton restartButton, homeButton, nextLevelButton;

            Border lineBorder, matteBorder;
            //constructor - adds button
            public LevelScorePanel(){
//                repaint();

                //colors
                Color backgroundColor = new Color(25, 77, 123);
                Color labelColor = new Color(142,174,208);
                Color textFieldFGColor = new Color(255,255,255);

                //font

                Font buttonfont = new Font("Serif", Font.BOLD, 30);
                Font labelFont = new Font("Serif", Font.PLAIN, 25);
                Font bigLabelFont = new Font("Serif", Font.BOLD, 35);
                Font textFieldFont = new Font("Serif", Font.BOLD, 35);
                Font bigTextFieldFont = new Font("Serif", Font.BOLD, 50);

                setBackground(backgroundColor);

                //set layout
                setLayout(new BorderLayout());
//                setLayout(new GridBagLayout());
//                GridBagConstraints gbc = new GridBagConstraints();

                //Top PANEL
                topPanel = new JPanel();
//                topPanel.setLayout(new BorderLayout());
                topPanel.setLayout(new GridBagLayout());

                GridBagConstraints gbcTop = new GridBagConstraints();
                gbcTop.insets = new Insets(10, 10,10,10);

                ScoreLabel = new JLabel("Score");
                ScoreLabel.setForeground(labelColor);
                ScoreLabel.setFont(bigLabelFont);

                scoreTF = new JTextField(30);
                scoreTF.setEditable(false);
                scoreTF.setOpaque(false);
                scoreTF.setForeground(textFieldFGColor);
                scoreTF.setFont(bigTextFieldFont);
                scoreTF.setHorizontalAlignment(SwingConstants.CENTER);

                feedbackLabel = new JLabel("Good Performance.");
                feedbackLabel.setForeground(labelColor);
                feedbackLabel.setFont(labelFont);

                matteBorder = BorderFactory.createMatteBorder(0,0,5,0, Color.white);
                scoreTF.setBorder(matteBorder);

                gbcTop.gridy = 0;
                topPanel.add(ScoreLabel, gbcTop);
                gbcTop.gridy = 1;
                topPanel.add(scoreTF, gbcTop);
                gbcTop.gridy = 2;
                topPanel.add(feedbackLabel, gbcTop);

                add(topPanel, BorderLayout.NORTH);


                //Center PANEL
                centerPanel = new JPanel();
                centerPanel.setLayout(new GridLayout(1,2));
                centerPanel.setOpaque(false);
//                centerPanel.setBackground(backgroundColor);

                centerEastPanel = new JPanel();
                centerEastPanel.setOpaque(false);
                centerWestPanel = new JPanel();
                centerWestPanel.setOpaque(false);

                //labels
                levelScoreLabel = new JLabel("Your Score:");
                levelScoreLabel.setForeground(labelColor);
                levelScoreLabel.setFont(labelFont);

                requirementLabel = new JLabel("Requirements:");
                requirementLabel.setForeground(labelColor);
                requirementLabel.setFont(labelFont);

                accuracyLabel = new JLabel("accuracy");
                accuracyLabel.setForeground(labelColor);
                accuracyLabel.setFont(labelFont);

                accuracyRequirementLabel = new JLabel("accuracy");
                accuracyRequirementLabel.setForeground(labelColor);
                accuracyRequirementLabel.setFont(labelFont);

                speedLabel = new JLabel("net speed");
                speedLabel.setForeground(labelColor);
                speedLabel.setFont(labelFont);

                speedRequirementLabel = new JLabel("net speed");
                speedRequirementLabel.setForeground(labelColor);
                speedRequirementLabel.setFont(labelFont);

                durationLabel = new JLabel("duration");
                durationLabel.setForeground(labelColor);
                durationLabel.setFont(labelFont);

                speedGoalLabel = new JLabel("speed goal");
                speedGoalLabel.setForeground(labelColor);
                speedGoalLabel.setFont(labelFont);

                //textFields
                accuracyLevelTF = new JTextField(10);
                accuracyLevelTF.setEditable(false);
                accuracyLevelTF.setOpaque(false);
//                accuracyLevelTF.setForeground(textFieldFGColor);
                accuracyLevelTF.setHorizontalAlignment(SwingConstants.CENTER);
                accuracyLevelTF.setFont(textFieldFont);

                speedLevelTF = new JTextField(10);
                speedLevelTF.setEditable(false);
                speedLevelTF.setOpaque(false);
                speedLevelTF.setHorizontalAlignment(SwingConstants.CENTER);
                speedLevelTF.setFont(textFieldFont);


                durationLevelTF = new JTextField(10);
                durationLevelTF.setEditable(false);
                durationLevelTF.setOpaque(false);
                durationLevelTF.setHorizontalAlignment(SwingConstants.CENTER);
                durationLevelTF.setFont(textFieldFont);


                accuracyRequiredTF = new JTextField(10);
                accuracyRequiredTF.setEditable(false);
                accuracyRequiredTF.setOpaque(false);
                accuracyRequiredTF.setHorizontalAlignment(SwingConstants.CENTER);
                accuracyRequiredTF.setFont(textFieldFont);

                speedRequiredTF = new JTextField(10);
                speedRequiredTF.setEditable(false);
                speedRequiredTF.setOpaque(false);
                speedRequiredTF.setHorizontalAlignment(SwingConstants.CENTER);
                speedRequiredTF.setFont(textFieldFont);

                speedGoalTF  = new JTextField(10);
                speedGoalTF.setEditable(false);
                speedGoalTF.setOpaque(false);
                speedGoalTF.setHorizontalAlignment(SwingConstants.CENTER);
                speedGoalTF.setFont(textFieldFont);


                //centerWestPanel
                centerWestPanel.setLayout(new GridBagLayout());
                GridBagConstraints gbcCWP = new GridBagConstraints();
                gbcCWP.insets = new Insets(10,10,10,10);
                gbcCWP.gridy = 0;
                centerWestPanel.add(levelScoreLabel, gbcCWP);

                gbcCWP.gridy = 1;
                gbcCWP.gridx = 0;
                centerWestPanel.add(accuracyLevelTF, gbcCWP);
                gbcCWP.gridx = 1;
                centerWestPanel.add(accuracyLabel, gbcCWP);
                gbcCWP.gridy = 2;
                gbcCWP.gridx = 0;
                centerWestPanel.add(speedLevelTF, gbcCWP);
                gbcCWP.gridx = 1;
                centerWestPanel.add(speedLabel, gbcCWP);
                gbcCWP.gridy = 3;
                gbcCWP.gridx = 0;
                centerWestPanel.add(durationLevelTF, gbcCWP);
                gbcCWP.gridx = 1;
                centerWestPanel.add(durationLabel, gbcCWP);


                //centerEastPanel
                centerEastPanel.setLayout(new GridBagLayout());
                GridBagConstraints gbcCEP = new GridBagConstraints();
                gbcCEP.insets = new Insets(10,10,10,10);
                gbcCEP.gridy = 0;

                centerEastPanel.add(requirementLabel, gbcCEP);

                gbcCEP.gridy = 1;
                gbcCEP.gridx = 0;
                centerEastPanel.add(accuracyRequiredTF, gbcCEP);
                gbcCEP.gridx = 1;
                centerEastPanel.add(accuracyRequirementLabel, gbcCEP);
                gbcCEP.gridy = 2;
                gbcCEP.gridx = 0;
                centerEastPanel.add(speedRequiredTF, gbcCEP);
                gbcCEP.gridx = 1;
                centerEastPanel.add(speedRequirementLabel, gbcCEP);
                gbcCEP.gridy = 3;
                gbcCEP.gridx = 0;
                centerEastPanel.add(speedGoalTF, gbcCEP);
                gbcCEP.gridx = 1;
                centerEastPanel.add(speedGoalLabel, gbcCEP);

                centerPanel.add(centerWestPanel);
                centerPanel.add(centerEastPanel);
                add(centerPanel, BorderLayout.CENTER);

                //bottomPanel
                bottomPanel = new JPanel();
                backgroundColor = new Color(255,255,255);
                bottomPanel.setBackground(backgroundColor);
                bottomPanel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10,10,10,10);

                //add home button
                gbc.gridy = 0;
                gbc.gridx = 0;
                homeButton = new JButton("Home");
                homeButton.setFont(buttonfont);
                homeButton.addActionListener(this);
                bottomPanel.add(homeButton, gbc);

                //restart button
                gbc.gridx = 1;
                restartButton = new JButton("Restart");
                restartButton.setFont(buttonfont);
                restartButton.addActionListener(this);
                bottomPanel.add(restartButton, gbc);

                //next level button
                gbc.gridx = 2;
                nextLevelButton = new JButton("Next Level");
                nextLevelButton.setOpaque(false);
                nextLevelButton.setFont(buttonfont);
                nextLevelButton.addActionListener(this);
                bottomPanel.add(nextLevelButton, gbc);

                //add label level
                gbc.gridy = 1;
                gbc.gridx = 0;
                gbc.gridwidth = 3;
                levelLabel = new JLabel("Level ");
                levelLabel.setForeground(Color.black);
                levelLabel.setFont(bigLabelFont);
                bottomPanel.add(levelLabel, gbc);

                add(bottomPanel, BorderLayout.SOUTH);
            }

            void calculateScore(){
                accuracyLevelTF.setText(String.valueOf(analysis.accuracy + " %"));
                speedLevelTF.setText(String.valueOf(analysis.netWPM + " WPM"));
                durationLevelTF.setText(String.valueOf(analysis.timeInSeconds + " seconds"));
                accuracyRequiredTF.setText(String.valueOf(analysis.requiredAccuracy + " %"));
                speedRequiredTF.setText(String.valueOf(analysis.requiredNetWPM + " WPM"));
                speedGoalTF.setText(String.valueOf(analysis.targetNetWPM + " WPM"));

                scoreTF.setText(String.valueOf(analysis.score));

                levelLabel.setText(String.valueOf("Level: " + game.level));

//                update feedback based on score & level
                if(analysis.score == 0)
                    feedbackLabel.setText("You can do better! Try Again.");
                else if(analysis.score > 0 && analysis.score < 700)
                    feedbackLabel.setText("Good Performance!");
                else if(analysis.score >= 700  && analysis.score < 1000)
                    feedbackLabel.setText("Excellent Performance!");
                else if(analysis.score >= 1000 && analysis.level < 4)
                    feedbackLabel.setText("Outstanding Performance. Time to move on to next Level.");
                else if(analysis.score >= 1000 && analysis.level >= 4)
                    feedbackLabel.setText("Outstanding Performance. You have completed all level.");
            }

            //actionPerformed header - goes back to HomePanel if back button pressed
            public void actionPerformed(ActionEvent e)
            {
                //use getActionCommand to find which button was pressed
                String optionPicked;
                optionPicked = e.getActionCommand();

                //flip to the correct panel
                if(optionPicked.equals("Home")){
                    game.restartGame();
//                    cardLayout.show(masterPanel, "HighScores");
                }

                else if (optionPicked.equals("Restart"))
                {
                    //level passed and restart from scores
                    //level not passed and restart from scores
                    if(analysis.score > 0 && analysis.level < 4)
                        game.restartLevel(game.level - 1);
                    else
                        game.restartLevel();
                    cardLayout.show(masterPanel, "Gameplay");
                }

                else if (optionPicked.equals("Next Level"))
                {
                    if(analysis.level < 4) {
                        if (analysis.score > 0) {
                            cardLayout.show(masterPanel, "Gameplay");
                            game.inputTextField.setText("Click here to Start");
                        }
                        else
                            JOptionPane.showMessageDialog(null, "Finish the requirements of current level first!");
                    }
                    else
                        JOptionPane.showMessageDialog(null, "There is no next level!");
                }
            }
            //draws backgroundImage as background
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, this);
            }
        }

        //HighScoresPanel - panel containing scores
        class HighScoresPanel extends JPanel implements ActionListener{
            JPanel topPanel,
                    centerPanel, centerPanel1, centerPanel2, centerPanel3, centerPanel4,
                    bottomPanel;
            JLabel accuracyLabel, accuracyLabel1, accuracyLabel2, accuracyLabel3,
                    scoreLabel, scoreLabel1, scoreLabel2, scoreLabel3,
                    durationLabel, durationLabel1, durationLabel2, durationLabel3,
                    speedLabel, speedLabel1, speedLabel2, speedLabel3,
                    titleLabel;
            JButton backButton;

            //constructor - adds Scores and back button
            public HighScoresPanel(){

                repaint();
//                setBackground(backgroundColor);

                //colors
                Color backgroundColor = new Color(25, 77, 123);
                Color labelColor = new Color(142,174,208);
                Color textFieldFGColor = new Color(255,255,255);

                //fonts
                Font buttonfont = new Font("Serif", Font.PLAIN, 25);
                Font labelFont = new Font("Serif", Font.PLAIN, 25);
                Font bigLabelFont = new Font("Serif", Font.BOLD, 35);
                Font largeFont = new Font("Serif", Font.BOLD, 45);

                //set layout
                setLayout(new BorderLayout());

                //top Panel
                topPanel = new JPanel();
                topPanel.setOpaque(false);

                titleLabel = new JLabel();
                titleLabel.setText("High Scores");
                titleLabel.setForeground(labelColor);
                titleLabel.setFont(largeFont);

                topPanel.add(titleLabel);
                Border matteBorder = BorderFactory.createMatteBorder(0,0,3,0, Color.white);
                topPanel.setBorder(matteBorder);
                add(topPanel, BorderLayout.PAGE_START);

                //Center PANEL
                centerPanel = new JPanel();
                centerPanel.setLayout(new GridLayout(2,2,10,10));
                centerPanel.setOpaque(false);

                centerPanel1 = new JPanel();
                centerPanel1.setLayout(new GridLayout(4,1, 0,10));
                centerPanel1.setOpaque(false);

                centerPanel2 = new JPanel();
                centerPanel2.setLayout(new GridLayout(4,1, 0,10));
                centerPanel2.setOpaque(false);

                centerPanel3 = new JPanel();
                centerPanel3.setLayout(new GridLayout(4,1, 0,10));
                centerPanel3.setOpaque(false);

                centerPanel4 = new JPanel();
                centerPanel4.setLayout(new GridLayout(4,1, 0,10));
                centerPanel4.setOpaque(false);

                //labels
                accuracyLabel = new JLabel("   Accuracy:");
                accuracyLabel.setForeground(labelColor);
                accuracyLabel.setFont(bigLabelFont);

                accuracyLabel1 = new JLabel();
                accuracyLabel1.setForeground(labelColor);
                accuracyLabel1.setFont(labelFont);

                accuracyLabel2 = new JLabel();
                accuracyLabel2.setForeground(labelColor);
                accuracyLabel2.setFont(labelFont);

                accuracyLabel3 = new JLabel();
                accuracyLabel3.setForeground(labelColor);
                accuracyLabel3.setFont(labelFont);

                durationLabel = new JLabel("   Duration:");
                durationLabel.setForeground(labelColor);
                durationLabel.setFont(bigLabelFont);

                durationLabel1 = new JLabel();
                durationLabel1.setForeground(labelColor);
                durationLabel1.setFont(labelFont);

                durationLabel2 = new JLabel();
                durationLabel2.setForeground(labelColor);
                durationLabel2.setFont(labelFont);

                durationLabel3 = new JLabel();
                durationLabel3.setForeground(labelColor);
                durationLabel3.setFont(labelFont);

                speedLabel = new JLabel("   Speed:");
                speedLabel.setForeground(labelColor);
                speedLabel.setFont(bigLabelFont);

                speedLabel1 = new JLabel();
                speedLabel1.setForeground(labelColor);
                speedLabel1.setFont(labelFont);

                speedLabel2 = new JLabel();
                speedLabel2.setForeground(labelColor);
                speedLabel2.setFont(labelFont);

                speedLabel3 = new JLabel();
                speedLabel3.setForeground(labelColor);
                speedLabel3.setFont(labelFont);

                scoreLabel = new JLabel("   Score:");
                scoreLabel.setForeground(labelColor);
                scoreLabel.setFont(bigLabelFont);

                scoreLabel1 = new JLabel();
                scoreLabel1.setForeground(labelColor);
                scoreLabel1.setFont(labelFont);

                scoreLabel2 = new JLabel();
                scoreLabel2.setForeground(labelColor);
                scoreLabel2.setFont(labelFont);

                scoreLabel3 = new JLabel();
                scoreLabel3.setForeground(labelColor);
                scoreLabel3.setFont(labelFont);

                //adding labels & panels
                centerPanel1.add(scoreLabel);
                centerPanel1.add(scoreLabel1);
                centerPanel1.add(scoreLabel2);
                centerPanel1.add(scoreLabel3);

                centerPanel2.add(durationLabel);
                centerPanel2.add(durationLabel1);
                centerPanel2.add(durationLabel2);
                centerPanel2.add(durationLabel3);

                centerPanel3.add(accuracyLabel);
                centerPanel3.add(accuracyLabel1);
                centerPanel3.add(accuracyLabel2);
                centerPanel3.add(accuracyLabel3);

                centerPanel4.add(speedLabel);
                centerPanel4.add(speedLabel1);
                centerPanel4.add(speedLabel2);
                centerPanel4.add(speedLabel3);

                centerPanel.add(centerPanel1);
                centerPanel.add(centerPanel2);
                centerPanel.add(centerPanel3);
                centerPanel.add(centerPanel4);
                add(centerPanel, BorderLayout.CENTER);

                //bottomPanel
                //create button to go back to HomePanel
                bottomPanel = new JPanel();
                backButton = new JButton("Back");
                backButton.setFont(buttonfont);
                backButton.setHorizontalAlignment(SwingConstants.CENTER);
                backButton.setVerticalAlignment(SwingConstants.CENTER);
                backButton.addActionListener(this);
                bottomPanel.add(backButton);
                add(bottomPanel, BorderLayout.PAGE_END);
            }

            void setHighScores(){
                try{
                    String thisLine, str[];
                    ArrayList<Double> accuracyAL = new ArrayList<Double>();
                    ArrayList<Double> speedAL = new ArrayList<Double>();
                    ArrayList<Double> scoreAL = new ArrayList<Double>();
                    ArrayList<Double> durationAL = new ArrayList<Double>();

                    FileReader in = new FileReader("savedscores.txt");
                    BufferedReader br = new BufferedReader(in);

                    while ( (thisLine = br.readLine()) != null ) {
                        str = thisLine.split(" ");
                        System.out.println("Accuracy: " + str[0] + ", Speed: " + str[1] + ", Score: " + str[2] + ", Duration: " + str[3]);

//                        double accuracyTemp = Integer.parseInt(str[0]);
//                        double speedTemp = Integer.parseInt(str[1]);
//                        double scoreTemp = Integer.parseInt(str[2]);
//                        double durationTemp = Integer.parseInt(str[3]);

                        double accuracyTemp = Double.parseDouble(str[0]);
                        double speedTemp = Double.parseDouble(str[1]);
                        double scoreTemp = Double.parseDouble(str[2]);
                        double durationTemp = Double.parseDouble(str[3]);

                        accuracyAL.add(accuracyTemp);
                        speedAL.add(speedTemp);
                        scoreAL.add(scoreTemp);
                        durationAL.add(durationTemp);
                    }

                    Collections.sort(accuracyAL);
                    Collections.reverse(accuracyAL);
                    Collections.sort(speedAL);
                    Collections.reverse(speedAL);
                    Collections.sort(scoreAL);
                    Collections.reverse(scoreAL);
                    Collections.sort(durationAL);

                    accuracyLabel1.setText("    1. " + String.valueOf(accuracyAL.get(0)) + " %");
                    accuracyLabel2.setText("    2. " + String.valueOf(accuracyAL.get(1)) + " %");
                    accuracyLabel3.setText("    3. " + String.valueOf(accuracyAL.get(2)) + " %");

                    speedLabel1.setText("   1. " + String.valueOf(speedAL.get(0)) + " WPM");
                    speedLabel2.setText("   2. " + String.valueOf(speedAL.get(1)) + " WPM");
                    speedLabel3.setText("   3. " + String.valueOf(speedAL.get(2)) + " WPM");

                    scoreLabel1.setText("    1. " + String.valueOf(scoreAL.get(0)));
                    scoreLabel2.setText("    2. " + String.valueOf(scoreAL.get(1)));
                    scoreLabel3.setText("    3. " + String.valueOf(scoreAL.get(2)));

                    durationLabel1.setText("   1. " + String.valueOf(durationAL.get(0)) + " seconds");
                    durationLabel2.setText("   2. " + String.valueOf(durationAL.get(1)) + " seconds");
                    durationLabel3.setText("   3. " + String.valueOf(durationAL.get(2)) + " seconds");
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }

            //actionPerformed header - goes back to HomePanel if back button pressed
            public void actionPerformed(ActionEvent evt)
            {
                //flip card to HomePanel
                cardLayout.show(masterPanel, "Home");
            }

            //draws backgroundImage as background
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, this);
            }
        }

        //FinalPanel - creates the final slide with text area and restartLevel button
        class FinalPanel extends JPanel implements ActionListener
        {
            public FinalPanel()
            {
                repaint();

                setLayout(new BorderLayout());

                //create congrats sign
                String congratsText = "\n\n                            Congratulations!\n\n   You have completed all levels of the game!";
                JTextArea congratulations = new JTextArea(10, 23);
                congratulations.setForeground(Color.WHITE);
                congratulations.setText(congratsText);
                Font congratsFont = new Font("Serif", Font.BOLD, 60);
                congratulations.setFont(congratsFont);
                congratulations.setEditable(false);
                congratulations.setLineWrap(true);
                congratulations.setWrapStyleWord(true);
                congratulations.setOpaque(false);
                add(congratulations, BorderLayout.CENTER);

                //create restartLevel button to go back to HomePanel
                JButton hide = new JButton("Hide");
                Font restartFont = new Font("Arial", Font.BOLD, 30);
                hide.setFont(restartFont);
                hide.addActionListener(this);
                add(hide, BorderLayout.PAGE_END);
            }

            //for restarting the game
            public void actionPerformed(ActionEvent evt)
            {
                cardLayout.show(masterPanel, "LevelScore");
            }

            //draws backgroundImage into background
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, this);
            }
        }
    }
}