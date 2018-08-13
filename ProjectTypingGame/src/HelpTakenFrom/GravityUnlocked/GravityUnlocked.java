package HelpTakenFrom.GravityUnlocked;

//TypingGame.java

//import needed libraries
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.lang.Math;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.Scanner;

//TypingGame class header - creates frame
public class GravityUnlocked
{
    //field vars
    MasterPanel mp;

    public GravityUnlocked()
    {
        mp = null;
    }

    public static void main (String[] args)
    {
        //call run()
        GravityUnlocked gu = new GravityUnlocked();
        gu.run();
    }

    //run() method header - creates JFrame, calls MasterPanel()
    public void run()
    {
        //creates JFrame with default settings
        JFrame frame = new JFrame ("Gravity Unlocked");

        frame.setSize(1300, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(20, 20);
        frame.setResizable(false);

        //initializes instance of MasterPanel and adds it to frame
        mp = new MasterPanel();
        frame.add(mp);

        frame.setVisible(true);
    }

    //MasterPanel - contains HomePanel, InstructionsPanel, and GameplayPanel
    class MasterPanel extends JPanel
    {
        //declare field vars

        private CardLayout cl;
        private Image image;
        private GameplayPanel game;

        //constructor - add the three panels to CardLayout
        public MasterPanel()
        {
            //set layout to CardLayout
            cl = new CardLayout();
            setLayout(cl);

            //create and add instance of HomePanel to CardLayout
            OpeningPanel opening = new OpeningPanel();
            add(opening, "Opening");

            //create and add instance of InstructionsPanel to CardLayout
            InstructionsPanel instructions = new InstructionsPanel();
            add(instructions, "Instructions");

            //create and add instance of GameplayPanel to CardLayout
            game = new GameplayPanel();
            add(game, "Gameplay");

            //create and add instance of FinalPanel
            FinalPanel finalp = new FinalPanel();
            add(finalp, "Congrats");
        }

        //HomePanel - codes for the opening/title panel
        class OpeningPanel extends JPanel implements ActionListener
        {
            //declare field vars: imageName

            private String imageName;

            //constructor - prints image to background, creates start and instructions button
            public OpeningPanel()
            {
                //image name
                imageName = "HelpTakenFrom/GravityUnlocked/Solar System.png";

                getImage();

                repaint();

                //set layout to FlowLayout
                setLayout(new FlowLayout(FlowLayout.CENTER, 200, 225));

                //creates JTextArea with heading
                JTextArea ta = new JTextArea("Welcome to Gravity Unlocked");
                ta.setForeground(Color.WHITE);
                ta.setOpaque(false);
                Font font = new Font("Arial", Font.BOLD, 60);
                ta.setFont(font);
                ta.setEditable(false);
                add(ta);

                //create start button
                font = new Font("Arial", Font.BOLD, 50);
                JButton start = new JButton("Start");
                //start.setForeground(Color.WHITE);
                start.setOpaque(false);
                start.setFont(font);
                start.addActionListener(this);
                add(start);

                //create instructions button
                font = new Font("Arial", Font.BOLD, 50);
                JButton instruc = new JButton("Instructions");
                //instruc.setForeground(Color.WHITE);
                instruc.setOpaque(false);
                instruc.setFont(font);
                instruc.addActionListener(this);
                add(instruc);
            }

            //getImage() - loads image file for background
            public void getImage()
            {
                File imageFile = new File("/Users/abdullah/IdeaProjects/ProjectTypingGame/src/HelpTakenFrom/GravityUnlocked/Solar System.png");
                try
                {
                    image = ImageIO.read(imageFile);
                }
                catch (IOException e)
                {
                    System.err.println("\n\n" + imageName + " can't be found.\n\n");
                    e.printStackTrace();
                }
            }

            //paintComponent - sets background to the image loaded
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this);
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
                    cl.show(mp, "Gameplay");
                }
                else if (optionPicked.equals("Instructions"))
                {
                    cl.show(mp, "Instructions");
                }
            }
        }

        //InstructionsPanel - panel containing the instructions
        class InstructionsPanel extends JPanel implements ActionListener
        {
            //constructor - adds instructions and back button
            public InstructionsPanel()
            {
                repaint();

                setLayout(new FlowLayout());

                //create uneditable JTextArea with instructions and add it
                String instructions = "\t\t\t  Instructions"
                        + "\n\n- In each level, you will be shown a set of boards. If you are on level 5 or under, one board will be shown. If you are above level 5, you will be shown two boards."
                        + "\n\n- During each turn, look at the right of the screen. You can change the planet (which affects gravity), velocity, and angle. When you are done adjusting these parameters, click the FIRE button. The goal of each level is to hit the green board(s) with the projectile, which will be launched from the center of the blue board."
                        + "\n\n- The gravity and velocity are always multiplied by 6 in order to increase the the curve of the projectile by a factor of 6."
                        + "\n\n- You are given an unlimited amount of attempts per level. If you are successful in your attempt, you will be promoted to the next level. If you are unsuccessful, read the feedback given. It will provide you with the math behind your shot and why you missed. Then, you may adjust the parameters and take another attempt."
                        + "\n\n- The goal of the game is to pass all 10 levels."
                        + "\n\nGood luck!";
                JTextArea instructa = new JTextArea(10, 55);
                instructa.setForeground(Color.WHITE);
                instructa.setText(instructions);
                Font font2 = new Font("Arial", Font.BOLD, 25);
                instructa.setFont(font2);
                instructa.setEditable(false);
                instructa.setLineWrap(true);
                instructa.setWrapStyleWord(true);
                instructa.setCaretPosition(instructions.length());
                instructa.setOpaque(false);
                add(instructa);

                //create button to go back to HomePanel
                JButton back = new JButton("Back");
                font2 = new Font("Arial", Font.BOLD, 25);
                back.setFont(font2);
                back.addActionListener(this);
                add(back);
            }
            //actionPerformed header - goes back to HomePanel is back button pressed
            public void actionPerformed(ActionEvent evt)
            {
                //flip card to HomePanel
                cl.show(mp, "Opening");
            }

            //draws image as background
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this);
            }
        }

        //GameplayPanel - panel where the actual game is played
        class GameplayPanel extends JPanel implements ActionListener, ChangeListener
        {
            //declare field vars

            private JTextArea levelArea;
            private JTextArea southArea;
            private JTextArea gravityArea;
            private JPanel centerPanel;
            private int level;
            private String planet;
            private JSlider velocity;
            private JSlider angle;
            private int curVelocity;
            private int curAngle;
            private double time;
            private double xCoef;
            private double yCoef1;
            private double yCoef2;
            private double yCoef3;
            private Scanner input;
            private String fileString;
            private double currGravity;
            private BorderLayout bl;
            private boolean isPressed;
            private String numberString;
            private boolean isMoved;
            private JTextArea velArea;
            private JTextArea ang;
            private boolean paintBall;
            private double height;
            private JTextArea planetArea;
            private boolean setBoard1;
            private boolean setBoard2;
            private int boardWidth;
            private int board1X;
            private int board1Y;
            private int board2X;
            private int board2Y;
            private boolean hitBoard1;
            private boolean hitBoard2;
            private boolean hitBoardFinal;
            private int finalVel;
            private int finalAng;
            private double finalGravity;
            private double xVel;
            private double yVel;
            private boolean overShot;
            private boolean underShot;
            private boolean overShot2;
            private boolean underShot2;
            private double time1;
            private double time2;
            private double time3;
            private double time4;
            private double time5;
            private Image image2;

            //constructor - constructs the basic framework of the panel using BorderLayout
            //adds the level and restart button to the North Panel
            //adds all of the adjustments to the east panel
            //adds feedback to South Panel
            public GameplayPanel()
            {
                repaint();
                //read PlanetGravity.txt and find the respective gravity, using try-catch
                //and save gravity into new var
                File inFile = new File("/Users/abdullah/IdeaProjects/ProjectTypingGame/src/HelpTakenFrom/GravityUnlocked/PlanetGravity.txt");
                input = null;

                try
                {
                    input = new Scanner(inFile);
                }
                catch (FileNotFoundException e)
                {
                    System.err.println("Cannot find PlanetGravity.txt");
                    System.exit(1);
                }

                fileString = "";
                while (input.hasNext())
                {
                    fileString += input.nextLine();
                }

                getMyImage2();

                //initialize vars
                level = 1;
                planet = "";
                height = 0;
                overShot = false;
                underShot = false;
                underShot2 = false;
                overShot2 = false;
                isMoved = false;
                paintBall = false;
                setBoard1 = false;
                setBoard2 = false;
                boardWidth = 0;
                hitBoard1 = false;
                hitBoard2 = false;
                hitBoardFinal = false;
                finalAng = 0;
                finalVel = 0;
                finalGravity = 0;
                xVel = 0;
                yVel = 0;
                time1 = 0;
                time2 = 0;
                time3 = 0;
                time4 = 0;
                time5 = 0;

                curVelocity = 0;
                curAngle = 0;
                time = 0;
                currGravity = 9.8;

                //set layout to BorderLayout
                bl = new BorderLayout(0, 0);
                setLayout(bl);

                //create restart button and area for level, add to North
                JPanel panelNorth = new JPanel();
                panelNorth.setLayout(new BorderLayout(0, 0));
                Font font = new Font("Arial", Font.BOLD, 25);
                JButton restart = new JButton("Restart");
                restart.setFont(font);
                restart.addActionListener(this);
                panelNorth.add(restart, BorderLayout.EAST);
                Font font2 = new Font("Arial", Font.BOLD, 40);
                levelArea = new JTextArea("Level 1");
                levelArea.setOpaque(false);
                levelArea.setForeground(Color.WHITE);
                levelArea.setFont(font2);
                levelArea.setEditable(false);
                panelNorth.add(levelArea, BorderLayout.CENTER);
                panelNorth.setBackground(Color.BLACK);
                add(panelNorth, BorderLayout.NORTH);

                //create south Area with feedback
                southArea = new JTextArea(7, 10);
                southArea.setOpaque(true);
                southArea.setText("Here is where your feedback will be displayed");
                Font southFont = new Font("Arial", Font.BOLD, 25);
                southArea.setFont(southFont);
                southArea.setEditable(false);
                southArea.setLineWrap(true);
                southArea.setWrapStyleWord(true);
                southArea.setForeground(Color.BLACK);
                JScrollPane scroll = new JScrollPane(southArea);
                scroll.getViewport().setOpaque(false);
                scroll.setOpaque(false);
                add(scroll, BorderLayout.SOUTH);

                JPanel eastPanel = new JPanel();
                eastPanel.setOpaque(false);
                eastPanel.setLayout(new GridLayout(5, 1, 0, 0));

                //create JMenuBar

                JPanel panel2 = new JPanel();
                panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
                Font mfont = new Font("Arial", Font.BOLD, 15);
                JMenuItem mercury = new JMenuItem("Mercury");
                JMenuItem venus = new JMenuItem("Venus");
                JMenuItem earth = new JMenuItem("Earth");
                JMenuItem mars = new JMenuItem("Mars");
                JMenuItem jupiter = new JMenuItem("Jupiter");
                JMenuItem saturn = new JMenuItem("Saturn");
                JMenuItem uranus = new JMenuItem("Uranus");
                JMenuItem neptune = new JMenuItem("Neptune");
                JMenuItem pluto = new JMenuItem("Pluto");


                mercury.setFont(mfont);
                venus.setFont(mfont);
                earth.setFont(mfont);
                mars.setFont(mfont);
                jupiter.setFont(mfont);
                saturn.setFont(mfont);
                uranus.setFont(mfont);
                neptune.setFont(mfont);
                pluto.setFont(mfont);

                mercury.addActionListener(this);
                venus.addActionListener(this);
                earth.addActionListener(this);
                mars.addActionListener(this);
                jupiter.addActionListener(this);
                saturn.addActionListener(this);
                uranus.addActionListener(this);
                neptune.addActionListener(this);
                pluto.addActionListener(this);

                //add these to WPM_Menu
                Font menuFont = new Font("Arial", Font.BOLD, 15);
                JMenu menu = new JMenu("Pick Planet");
                menu.setFont(menuFont);

                menu.add(mercury);
                menu.add(venus);
                menu.add(earth);
                menu.add(mars);
                menu.add(jupiter);
                menu.add(saturn);
                menu.add(uranus);
                menu.add(neptune);
                menu.add(pluto);

                JMenuBar mb = new JMenuBar();
                mb.add(menu);
                panel2.add(mb);


                //create JTextArea with contents "Planet: "
                numberString = "";
                isPressed = false;
                planetArea = new JTextArea("Planet: ");
                Font planetFont = new Font("Arial", Font.BOLD, 25);
                planetArea.setFont(planetFont);
                planetArea.setEditable(false);
                planetArea.setForeground(Color.WHITE);
                planetArea.setOpaque(false);
                panel2.add(planetArea);

                panel2.setOpaque(false);
                eastPanel.add(panel2);

                //create JTextArea with contents "Gravity: "
                gravityArea = new JTextArea("Gravity: ");
                Font gravFont = new Font("Arial", Font.BOLD, 25);
                gravityArea.setFont(gravFont);
                gravityArea.setEditable(false);
                gravityArea.setForeground(Color.WHITE);
                gravityArea.setOpaque(false);
                eastPanel.add(gravityArea);

                JPanel panel3 = new JPanel();
                panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

                //create JTextArea with contents "Velocity:" and velocity slider
                Font newFont = new Font("Arial", Font.BOLD, 25);
                velArea = new JTextArea("Velocity:");
                velArea.setFont(newFont);
                velArea.setEditable(false);
                velArea.setForeground(Color.WHITE);
                velArea.setOpaque(false);
                panel3.add(velArea);
                velocity = new JSlider(JSlider.HORIZONTAL, 0, 90, 0);
                velocity.setMajorTickSpacing(30);
                velocity.setPaintTicks(true);
                velocity.setPaintLabels(true);
                velocity.setForeground(Color.WHITE);
                velocity.setOpaque(false);
                velocity.addChangeListener(this);
                panel3.setOpaque(false);
                panel3.add(velocity);

                JPanel panel4 = new JPanel();
                panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

                //create JTextArea with contents "Angle:" and JSlider
                ang = new JTextArea("Angle:");
                ang.setFont(newFont);
                ang.setEditable(false);
                ang.setForeground(Color.WHITE);
                ang.setOpaque(false);
                panel4.add(ang);
                angle = new JSlider(JSlider.HORIZONTAL, 0, 90, 0);
                angle.setMajorTickSpacing(30);
                angle.setPaintTicks(true);
                angle.setPaintLabels(true);
                angle.setForeground(Color.WHITE);
                angle.setOpaque(false);
                angle.addChangeListener(this);
                panel4.setBackground(Color.BLACK);
                panel4.add(angle);
                panel4.setOpaque(false);

                //add JPanel 3 and 4 to JPanel
                eastPanel.add(panel3);
                eastPanel.add(panel4);

                //create JButton called FIRE
                Font fireFont = new Font("Arial", Font.BOLD, 30);
                JButton fire = new JButton("FIRE");
                fire.setFont(fireFont);
                fire.addActionListener(this);
                eastPanel.add(fire);

                //add JPanel to East
                add(eastPanel, BorderLayout.EAST);

                //centerPanel
                centerPanel = new JPanel();
                centerPanel.setOpaque(false);
                add(centerPanel, BorderLayout.CENTER);

                setBoards();
            }

            //loads second image of Mars
            public void getMyImage2()
            {
                File imageFile = new File("/Users/abdullah/IdeaProjects/ProjectTypingGame/src/HelpTakenFrom/GravityUnlocked/Mars.jpg");
                try
                {
                    image2 = ImageIO.read(imageFile);
                }
                catch (IOException e)
                {
                    System.err.println("\n\nMars.jpg can't be found.\n\n");
                    e.printStackTrace();
                }
            }

            //sets all values to default if game is restarted
            public void restart()
            {
                southArea.setText("Here is where your feedback will be displayed");
                levelArea.setText("Level 1");
                planetArea.setText("Planet: ");
                gravityArea.setText("Gravity: ");
                velocity.setValue(0);
                angle.setValue(0);
                velArea.setText("Velocity :");
                ang.setText("Angle: ");
                level = 1;
                boardWidth = 80;
                setBoards();
            }

            //actionPerformed header - goes back to opening if restart is pressed, call fired() if FIRE is pressed, finds gravity of planet otherwise
            public void actionPerformed(ActionEvent evt)
            {
                String pressed = evt.getActionCommand();
                if (pressed.equals("Restart"))
                {
                    restart();
                    cl.show(mp, "Opening");
                }

                else if (pressed.equals("FIRE"))
                {
                    fired();
                }
                else
                {
                    //set planet to pressed
                    planet = pressed;

                    int indexOfPlanet = 0;
                    indexOfPlanet = fileString.indexOf(planet);

                    int indexOfColon = 0;
                    indexOfColon = fileString.indexOf(":", indexOfPlanet);

                    int indexOfM = 0;
                    indexOfM = fileString.indexOf("m", indexOfColon);

                    numberString = fileString.substring(indexOfColon + 1, indexOfM);
                    currGravity = Double.parseDouble(numberString);

                    isPressed = true;
                    repaint();
                }
            }

            //stateChanged header - adjusts value of velocity and angle if sliders are moved
            public void stateChanged(ChangeEvent evt)
            {
                curVelocity = velocity.getValue();
                curAngle = angle.getValue();

                isMoved = true;
                repaint();
            }

            //fired() header - calculates equations of path of projectile, calls paintComponent()
            public void fired()
            {

                //save curVelocity, curAngle, and planet into new vars
                finalVel = curVelocity;
                finalAng = curAngle;
                finalGravity = currGravity;

                //use values to determine coefficients of equation using trig

                xVel = finalVel * Math.cos((finalAng * Math.PI)/180);
                yVel = finalVel * Math.sin((finalAng * Math.PI)/180);


                xCoef = xVel * 6;
                yCoef1 = -0.5 * finalGravity * 6;
                yCoef2 = yVel * 6;
                yCoef3 = 1;


                //call paintComponent()
                height = 0;
                time = 0;

                paintBall = true;
                repaint();
            }

            public void paintComponent(Graphics g) //displays current planet, gravity, velocity, and angle, and draw projectile path
            {
                super.paintComponent(g);

                //draw background image and starting board
                g.drawImage(image2, 0, 0, 1300, 700, this);

                g.setColor(Color.BLUE);
                g.fillRect(0, 454, 91, 12);

                if (setBoard1) //set one board randomly
                {
                    setBoard1 = false;

                    g.setColor(Color.GREEN);

                    board1X = (int)(Math.random()*501 + 200);
                    board1Y = (int)(Math.random()*331 + 70);

                    g.fillRect(board1X, board1Y, boardWidth, 10);
                }
                if (setBoard2) //set two boards randomly
                {
                    setBoard2 = false;

                    g.setColor(Color.GREEN);

                    board1X = 48;
                    board1Y = 443;
                    board2X = 500;
                    board2Y = 10;

                    while ((((double)(board1Y + 10 - board2Y))/(board2X - (board1X + boardWidth))) >= (((double)(452 - (board1Y + 10)))/(board1X + boardWidth - 47)))
                    {
                        System.out.println("(" + board1X + ", " + board1Y + "), (" + board2X + ", " + board2Y + ") " + boardWidth);
                        System.out.println((((double)(board1Y + 10 - board2Y))/(board2X - (board1X + boardWidth)) + " " + (((double)(452 - (board1Y + 10)))/(board1X + boardWidth - 47))));
                        board1X = (int)(Math.random()*201 + 200);
                        board1Y = (int)(Math.random()*331 + 70);
                        board2X = (int)(Math.random()*201 + 500);
                        board2Y = (int)(Math.random()*331 + 70);
                    }

                    g.fillRect(board1X, board1Y, boardWidth, 10);
                    g.fillRect(board2X, board2Y, boardWidth, 10);
                }

                if (isPressed) //update planet and gravity
                {
                    planetArea.setText("Planet: " + planet);
                    gravityArea.setText("Gravity: " + numberString + " m/s^2");
                    isPressed = false;
                }
                if (isMoved) //update velocity and angle
                {
                    velArea.setText("Velocity: " + curVelocity + " m/s");
                    ang.setText("Angle: " + curAngle);
                    isMoved = false;
                }
                if (paintBall) //use while loop to calculate x and y coordinate
                {
                    paintBall = false;
                    hitBoard1 = false;
                    hitBoard2 = false;
                    hitBoardFinal = false;
                    underShot = false;
                    underShot2 = false;
                    overShot = false;
                    overShot2 = false;

                    while (height >= -12)
                    {
                        //calculate x and y position
                        double xPos = xCoef * time;
                        double yPos = yCoef1 * time * time;
                        yPos += yCoef2 * time;
                        yPos += yCoef3;
                        height = yPos;

                        g.setColor(Color.RED);
                        int xPosition = 42 + (int)(xPos - 5);
                        int yPosition = 445 - (int)(yPos - 5);

                        if (level <= 5) //checks if projectile hit, was undershot, or was overshot
                        {
                            if (xPosition + 5 >= board1X && xPosition <= board1X + boardWidth && yPosition + 5 >= board1Y && yPosition <= board1Y + 10)
                            {
                                hitBoardFinal = true;
                            }
                            if (xPosition == board1X)
                            {
                                time1 = time;
                                if (yPosition < board1Y)
                                {
                                    overShot = true;
                                }
                                else
                                {
                                    underShot = true;
                                }
                            }
                            if (xPosition == board1X + boardWidth)
                            {
                                time2 = time;
                            }
                            if (height <= 0)
                            {
                                time3 = time;
                            }
                        }
                        else //checks if projectile hit, was undershot, or was overshot for 2 boards
                        {
                            if (xPosition + 5 >= board1X && xPosition <= board1X + boardWidth && yPosition + 5 >= board1Y && yPosition <= board1Y + 10)
                            {
                                hitBoard1 = true;
                            }
                            if (xPosition + 5 >= board2X && xPosition <= board2X + boardWidth && yPosition + 5 >= board2Y && yPosition <= board2Y + 10)
                            {
                                hitBoard2 = true;
                                if (hitBoard1 == true) {
                                    hitBoardFinal = true;
                                }
                            }
                            if (xPosition == board1X)
                            {
                                time1 = time;
                                if (yPosition < board1Y)
                                {
                                    overShot = true;
                                }
                                else
                                {
                                    underShot = true;
                                }
                            }
                            if (xPosition == board2X)
                            {
                                time4 = time;
                                if (yPosition < board2Y)
                                {
                                    overShot2 = true;
                                }
                                else
                                {
                                    underShot2 = true;
                                }
                            }
                            if (xPosition == board1X + boardWidth)
                            {
                                time2 = time;
                            }
                            if (xPosition == board2X + boardWidth)
                            {
                                time5 = time;
                            }
                            if (height == 0)
                            {
                                time3 = time;
                            }
                        }
                        g.fillOval( xPosition, yPosition, 5, 5);

                        time += 0.0005;
                    }
                    //update based on hit or no hit
                    if (hitBoardFinal == true) {
                        if (level == 10)
                        {
                            cl.show(mp, "Congrats");
                        }
                        else
                        {
                            level++;
                            levelArea.setText("Level " + level);
                            setBoards();
                        }
                    }
                    feedback(hitBoardFinal);
                }


                g.setColor(Color.GREEN);
                g.fillRect(board1X, board1Y, boardWidth, 10);
                g.fillRect(board2X, board2Y, boardWidth, 10);
            }

            //setBoards() - draws the boards on the projectile path panel
            public void setBoards()
            {
                if (level == 1)
                {
                    boardWidth = 80;
                    setBoard1 = true;
                    repaint();
                }
                else
                {
                    ActionListener al = new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            //set board width and numberOfBoards based on level

                            int numberOfBoards;

                            if (level <= 5)
                            {
                                numberOfBoards = 1;
                            }
                            else
                            {
                                numberOfBoards = 2;
                            }

                            if (level % 5 == 1)
                            {
                                boardWidth = 80 ;
                            }
                            else if (level % 5 == 2)
                            {
                                boardWidth = 60;
                            }
                            else if (level % 5 == 3)
                            {
                                boardWidth = 40;
                            }
                            else if (level % 5 == 4)
                            {
                                boardWidth = 20;
                            }
                            else
                            {
                                boardWidth = 10;
                            }

                            if (numberOfBoards == 1)
                            {
                                setBoard1 = true;
                            }
                            else
                            {
                                setBoard2 = true;
                            }
                            repaint();
                        }
                    };

                    //set timer to cause delay

                    Timer timer = new Timer (5000, al);
                    timer.setInitialDelay(3000);
                    timer.setRepeats(false);
                    timer.start();
                }

            }

            //feedback() - provides user with feedback based on their most recent projectile launch attempt
            public void feedback(boolean hit)
            {
                if (hit) //say good job
                {
                    southArea.setText("Good job! You have been promoted to Level " + level + "!");
                }
                else //create String based on level and whether hit, overshot, or undershot
                {
                    String southText = "";
                    southText = "You had a gravity of " + (Math.round(finalGravity * 100.0) / 100.0) + ", a velocity of " + finalVel + ", and an angle of " + finalAng + ". ";
                    southText += "This gives a horizontal velocity of " + finalVel + "*cos(" + finalAng + ") = " + (Math.round(xVel * 100.0) / 100.0) + ", and a vertical velocity of " + finalVel + "*sin(" + finalAng + ") = " + (Math.round(yVel * 100.0) / 100.0) + ". ";
                    southText += "We can now form parametric equations of x = 6*" + (Math.round(xVel * 100.0) / 100.0) + "*t = " + (Math.round(xCoef * 100.0) / 100.0) + "t, and y = 6*(-0.5)*(" + (Math.round(finalGravity * 100.0) / 100.0) + ")*t^2 + 6*" + (Math.round(yVel * 100.0) / 100.0) + "*t = " + (Math.round(yCoef1 * 100.0) / 100.0) + "t^2 + " + (Math.round(yCoef2 * 100.0) / 100.0) + "t";
                    southText += " (remember, all coefficients are multiplied by 6). ";

                    if (level <= 5) //if one board
                    {
                        southText += "\nThe board covers the x-coordinates " + (board1X - 37) + "-" + (board1X + boardWidth - 37) + ", and y-coordinates " + (452 - board1Y - 10) + "-" + (452 - board1Y) + ". ";
                        if (underShot)
                        {
                            southText += "At time t = " + (Math.round(time2 * 10000.0) / 10000.0) + ", our parametric show us that x = " + (Math.round(xCoef * 100.0) / 100.0) + "*" + (Math.round(time2 * 10000.0) / 10000.0) + " = " + Math.round(xCoef*time2) + ", and y = " + (Math.round(yCoef1 * 100.0) / 100.0) + "*(" + (Math.round(time2 * 10000.0) / 10000.0) + ")^2 + " + (Math.round(yCoef2 * 100.0) / 100.0) + "*" + (Math.round(time2 * 10000.0) / 10000.0) + " = " + Math.round(yCoef1*time2*time2 + yCoef2*time2) + ". ";
                            southText += "As we can see, when the x-coordinate is that of the right edge of the board, the y-coordinate of the projectile is less than the y-coordinate of the board. Therefore, the projectile was undershot.";
                            southText += " In order to solve this problem, try increasing the velocity or angle of the projectile.";
                        }
                        else if (overShot)
                        {
                            southText += "At time t = " + (Math.round(time1 * 10000.0) / 10000.0) + ", our parametric show us that x = " + (Math.round(xCoef * 100.0) / 100.0) + "*" + (Math.round(time1 * 10000.0) / 10000.0) + " = " + Math.round(xCoef*time1) + ", and y = " + (Math.round(yCoef1 * 100.0) / 100.0) + "*(" + (Math.round(time1 * 10000.0) / 10000.0) + ")^2 + " + (Math.round(yCoef2 * 100.0) / 100.0) + "*" + (Math.round(time1 * 10000.0) / 10000.0) + " = " + Math.round(yCoef1*time1*time1 + yCoef2*time1) + ". ";
                            southText += "As we can see, when the x-coordinate is that of the left edge of the board, the y-coordinate of the projectile is greater than the y-coordinate of the board. Therefore, the projectile was overshot.";
                            southText += " In order to solve this problem, try decreasing the velocity or angle of the projectile.";
                        }
                        else
                        {
                            southText += "At time t = " + (Math.round(time3 * 10000.0) / 10000.0) + ", our parametric show us that x = " + (Math.round(xCoef * 100.0) / 100.0) + "*" + (Math.round(time3 * 10000.0) / 10000.0) + " = " + Math.round(xCoef*time3) + ", and y = " + (Math.round(yCoef1 * 100.0) / 100.0) + "*(" + (Math.round(time3 * 10000.0) / 10000.0) + ")^2 + " + (Math.round(yCoef2 * 100.0) / 100.0) + "*" + (Math.round(time3 * 10000.0) / 10000.0) + " = " + Math.round(yCoef1*time3*time3 + yCoef2*time3) + ". ";
                            southText += "As we can see, the y-coordinate of the projectile decreases below 0 when its x-coordinate is still less than x-coordinate of the left edge of the board. Therefore, the projectile was undershot.";
                            southText += " In order to solve this problem, try increasing the velocity or decreasing the angle of the projectile.";
                        }
                    }
                    else //if two boards, consider each board separately for overshot vs undershot
                    {
                        southText += "\nThe first board covers the x-coordinates " + (board1X - 37) + "-" + (board1X + boardWidth - 37) + ", and y-coordinates " + (452 - board1Y - 10) + "-" + (452 - board1Y) + ". ";
                        if (hitBoard1)
                        {
                            southText += "Congrats on hitting this board!";
                            underShot = false;
                            overShot = false;
                        }
                        else
                        {
                            if (underShot)
                            {
                                southText += "At time t = " + (Math.round(time2 * 10000.0) / 10000.0) + ", our parametric show us that x = " + (Math.round(xCoef * 100.0) / 100.0) + "*" + (Math.round(time2 * 10000.0) / 10000.0) + " = " + Math.round(xCoef*time2) + ", and y = " + (Math.round(yCoef1 * 100.0) / 100.0) + "*(" + (Math.round(time2 * 10000.0) / 10000.0) + ")^2 + " + (Math.round(yCoef2 * 100.0) / 100.0) + "*" + (Math.round(time2 * 10000.0) / 10000.0) + " = " + Math.round(yCoef1*time2*time2 + yCoef2*time2) + ". ";
                                southText += "As we can see, when the x-coordinate is that of the right edge of the board, the y-coordinate of the projectile is less than the y-coordinate of the board. Therefore, the projectile was undershot.";
                            }
                            else if (overShot)
                            {
                                southText += "At time t = " + (Math.round(time1 * 10000.0) / 10000.0) + ", our parametric show us that x = " + (Math.round(xCoef * 100.0) / 100.0) + "*" + (Math.round(time1 * 10000.0) / 10000.0) + " = " + Math.round(xCoef*time1) + ", and y = " + (Math.round(yCoef1 * 100.0) / 100.0) + "*(" + (Math.round(time1 * 10000.0) / 10000.0) + ")^2 + " + (Math.round(yCoef2 * 100.0) / 100.0) + "*" + (Math.round(time1 * 10000.0) / 10000.0) + " = " + Math.round(yCoef1*time1*time1 + yCoef2*time1) + ". ";
                                southText += "As we can see, when the x-coordinate is that of the left edge of the board, the y-coordinate of the projectile is greater than the y-coordinate of the board. Therefore, the projectile was overshot.";
                            }
                            else
                            {
                                southText += "At time t = " + (Math.round(time3 * 10000.0) / 10000.0) + ", our parametric show us that x = " + (Math.round(xCoef * 100.0) / 100.0) + "*" + (Math.round(time3 * 10000.0) / 10000.0) + " = " + Math.round(xCoef*time3) + ", and y = " + (Math.round(yCoef1 * 100.0) / 100.0) + "*(" + (Math.round(time3 * 10000.0) / 10000.0) + ")^2 + " + (Math.round(yCoef2 * 100.0) / 100.0) + "*" + (Math.round(time3 * 10000.0) / 10000.0) + " = " + Math.round(yCoef1*time3*time3 + yCoef2*time3) + ". ";
                                southText += "As we can see, the y-coordinate of the projectile decreases below 0 when its x-coordinate is still less than x-coordinate of the left edge of the board. Therefore, the projectile was undershot.";
                            }
                        }

                        southText += "\nThe second board covers the x-coordinates " + (board2X - 37) + "-" + (board2X + boardWidth - 37) + ", and y-coordinates " + (452 - board2Y - 10) + "-" + (452 - board2Y) + ". ";
                        if (hitBoard2)
                        {
                            southText += "Congrats on hitting this board!";
                            underShot2 = false;
                            overShot2 = false;
                        }
                        else
                        {
                            if (underShot2)
                            {
                                southText += "At time t = " + (Math.round(time5 * 10000.0) / 10000.0) + ", our parametric show us that x = " + (Math.round(xCoef * 100.0) / 100.0) + "*" + (Math.round(time5 * 10000.0) / 10000.0) + " = " + Math.round(xCoef*time2) + ", and y = " + (Math.round(yCoef1 * 100.0) / 100.0) + "*(" + (Math.round(time5 * 10000.0) / 10000.0) + ")^2 + " + (Math.round(yCoef2 * 100.0) / 100.0) + "*" + (Math.round(time5 * 10000.0) / 10000.0) + " = " + Math.round(yCoef1*time5*time5 + yCoef2*time5) + ". ";
                                southText += "As we can see, when the x-coordinate is that of the right edge of the board, the y-coordinate of the projectile is less than the y-coordinate of the board. Therefore, the projectile was undershot.";
                            }
                            else if (overShot2)
                            {
                                southText += "At time t = " + (Math.round(time1 * 10000.0) / 10000.0) + ", our parametric show us that x = " + (Math.round(xCoef * 100.0) / 100.0) + "*" + (Math.round(time4 * 10000.0) / 10000.0) + " = " + Math.round(xCoef*time4) + ", and y = " + (Math.round(yCoef1 * 100.0) / 100.0) + "*(" + (Math.round(time4 * 10000.0) / 10000.0) + ")^2 + " + (Math.round(yCoef2 * 100.0) / 100.0) + "*" + (Math.round(time4 * 10000.0) / 10000.0) + " = " + Math.round(yCoef1*time4*time4 + yCoef2*time4) + ". ";
                                southText += "As we can see, when the x-coordinate is that of the left edge of the board, the y-coordinate of the projectile is greater than the y-coordinate of the board. Therefore, the projectile was overshot.";
                            }
                            else
                            {
                                southText += "At time t = " + (Math.round(time3 * 10000.0) / 10000.0) + ", our parametric show us that x = " + (Math.round(xCoef * 100.0) / 100.0) + "*" + (Math.round(time3 * 10000.0) / 10000.0) + " = " + Math.round(xCoef*time3) + ", and y = " + (Math.round(yCoef1 * 100.0) / 100.0) + "*(" + (Math.round(time3 * 10000.0) / 10000.0) + ")^2 + " + (Math.round(yCoef2 * 100.0) / 100.0) + "*" + (Math.round(time3 * 10000.0) / 10000.0) + " = " + Math.round(yCoef1*time3*time3 + yCoef2*time3) + ". ";
                                southText += "As we can see, the y-coordinate of the projectile decreases below 0 when its x-coordinate is still less than x-coordinate of the left edge of the board. Therefore, the projectile was undershot.";
                            }
                        }

                        //gives user feedback on how to improve the shot

                        southText += "\nIn order to solve this problem, try ";

                        if ((hitBoard1 && underShot2) || (underShot && underShot2))
                        {
                            southText += "increasing the angle or velocity.";
                        }
                        if ((hitBoard1 && overShot2) || (overShot && overShot2))
                        {
                            southText += "decreasing the angle or velocity.";
                        }
                        if ((hitBoard1 && !(overShot2 || underShot2 || hitBoard2)) || (!(hitBoard1 || underShot || overShot) && !(overShot2 || underShot2 || hitBoard2)))
                        {
                            southText += "increasing the velocity or decreasing the angle.";
                        }
                        if ((underShot && hitBoard2) || (underShot && overShot2))
                        {
                            southText += "increasing the angle.";
                        }
                        if (underShot && !(overShot2 || underShot2 || hitBoard2))
                        {
                            southText += "increasing the angle and velocity.";
                        }
                        if (overShot && !(overShot2 || underShot2 || hitBoard2))
                        {
                            southText += "decreasing the angle and increasing the velocity.";
                        }
                        if ((overShot && hitBoard2) || (overShot && underShot2))
                        {
                            southText += "decreasing the angle.";
                        }
                    }


                    southArea.setText(southText);
                }
            }
        }

        //FinalPanel - creates the final slide with text area and restart button
        class FinalPanel extends JPanel implements ActionListener
        {
            public FinalPanel()
            {
                repaint();

                setLayout(new FlowLayout());

                //create congrats sign
                String congratsText = "                       Congratulations!\n\nYou have passed all 10 levels of the game!";
                JTextArea congratulations = new JTextArea(8, 23);
                congratulations.setForeground(Color.WHITE);
                congratulations.setText(congratsText);
                Font congratsFont = new Font("Arial", Font.BOLD, 60);
                congratulations.setFont(congratsFont);
                congratulations.setEditable(false);
                congratulations.setLineWrap(true);
                congratulations.setWrapStyleWord(true);
                congratulations.setCaretPosition(congratsText.length());
                congratulations.setOpaque(false);
                add(congratulations);

                //create restart button to go back to HomePanel
                JButton restart = new JButton("Restart");
                Font restartFont = new Font("Arial", Font.BOLD, 60);
                restart.setFont(restartFont);
                restart.addActionListener(this);
                add(restart);
            }

            //for restarting the game
            public void actionPerformed(ActionEvent evt)
            {
                game.restart();
                cl.show(mp, "Opening");
            }

            //draws image into background
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this);
            }
        }
    }
}