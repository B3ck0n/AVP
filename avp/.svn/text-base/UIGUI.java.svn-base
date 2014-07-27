/**********************************
 * Assignment 5: AVP
 * UIGUI.java
 * Date: 12/4/2011
 *
 * Jon Hamlin: jwh244, 2400666
 * Brooks Hoffecker: bjh83, 2015719
 * Evan Long: erl43, 2300076
 * Richard McCaffrey: rpm77, 2494167
 **********************************/

package avp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

public class UIGUI extends JFrame {
        private GUI gui;
        private JButton enter, exitGame1, exitGame2, startGame;
        private JSlider slider;
        private JPanel infoPanel, controlPanel, aiPanel, sliderPanel, gamePanel, infoButtonsPanel, statusPanel, statusFormatBig, statusFormat1, statusFormat2;
        private JLabel sliderLabel, statusLabel, turnStatus, scannerStatus, alienSenseStatus, predatorSenseStatus, moveStatus, controlRoomStatus, winStatus;
        private JLayeredPane lpane;
        private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();;
        private Font buttonSize = new Font("Dialog", 1, 16);

        public UIGUI(GUI g) {

                gui = g;

                // initializes the game screens and action listeners
                createStartScreen();
                createGameScreen();
                createActionListeners();

                // creates a layered panel which can switch between
                // the start screen and the game screen
                lpane = new JLayeredPane();
                lpane.setOpaque(true);
                lpane.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
                lpane.add(infoPanel, new Integer(0));
                lpane.add(gamePanel, new Integer(-1));

                // adds all necessary elements and displays the JFrame
                add(lpane, BorderLayout.CENTER);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setUndecorated(true);
                setFocusable(true);
                pack();
                setVisible(true);
                infoPanel.requestFocus();

//		//***********************************************************
//		//Testing Mode - For Karma Points
//		// adds a manual control mode to the game for testing purposes
//		// activate by pressing F1 while on the start screen
//		infoPanel.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if (e.getKeyCode() == KeyEvent.VK_F1) {
//					while (lpane.getComponent(0) != gamePanel) {
//						lpane.setLayer(lpane.getComponent(0), -1);
//					}
//
//					// manual controls for the game
//					gui.requestFocus();
//					gui.addKeyListener(new KeyAdapter() {
//						@Override
//						public void keyPressed(KeyEvent e) {
//							switch (e.getKeyCode()) {
//							case 37: // leftarrow
//								Engine.move(0, e);
//								break;
//							case 38: // uparrow
//								Engine.move(1, e);
//								break;
//							case 39: // rightarrow
//								Engine.move(2, e);
//								break;
//							case 40: // downarrow
//								Engine.move(3, e);
//								break;
//							case KeyEvent.VK_F2:
//								Engine.engine.play();
//							}
//						}
//					});
//				}
//			}
//		});
	}

        // creates a start screen that is displayed when the game starts
        private void createStartScreen() {

                // creates and formats the start up screen
                infoPanel = new JPanel();
                infoPanel.setSize(new Dimension(screenSize.width, screenSize.height));
                infoPanel.setBackground(Color.BLACK);
                infoPanel.setOpaque(true);
                infoPanel.setFocusable(true);

                // creates and formats an image for the start up screen menu
                ImageIcon image = new ImageIcon("avp/images/Capture.JPG");
                JLabel label = new JLabel("", image, JLabel.CENTER);
                label.setMinimumSize(new Dimension(screenSize.width, screenSize.height - screenSize.height / 10));
                label.setMaximumSize(new Dimension(screenSize.width, screenSize.height - screenSize.height / 10));
                label.setPreferredSize(new Dimension(screenSize.width, screenSize.height - screenSize.height / 10));

                // creates a start button for the start up screen
                startGame = new JButton("Start");
                startGame.setFocusable(false);
                startGame.setAlignmentX(Component.CENTER_ALIGNMENT);
                startGame.setPreferredSize(new Dimension(screenSize.width / 5 - 100, screenSize.height / 20));
                startGame.setMaximumSize(new Dimension(screenSize.width / 5 - 100, screenSize.height / 20));
                startGame.setMinimumSize(new Dimension(screenSize.width / 5 - 100, screenSize.height / 20));
                startGame.setFont(buttonSize);
                startGame.setAlignmentX(Component.CENTER_ALIGNMENT);

                // creates an exit button for the start up screen
                exitGame1 = new JButton("Exit");
                exitGame1.setFocusable(false);
                exitGame1.setAlignmentX(Component.CENTER_ALIGNMENT);
                exitGame1.setPreferredSize(new Dimension(screenSize.width / 5 - 100, screenSize.height / 20));
                exitGame1.setMaximumSize(new Dimension(screenSize.width / 5 - 100, screenSize.height / 20));
                exitGame1.setMinimumSize(new Dimension(screenSize.width / 5 - 100, screenSize.height / 20));
                exitGame1.setFont(buttonSize);

                // creates a panel to provide formatting for the buttons on the
                // start up screen
                infoButtonsPanel = new JPanel();
                infoButtonsPanel.setBackground(Color.BLACK);
                infoButtonsPanel.setLayout(new BoxLayout(infoButtonsPanel, BoxLayout.LINE_AXIS));
                infoButtonsPanel.setPreferredSize(new Dimension(2 * screenSize.width / 5 - 100, screenSize.height / 20));
                infoButtonsPanel.setMinimumSize(new Dimension(2 * screenSize.width / 5 - 100, screenSize.height / 20));
                infoButtonsPanel.setMaximumSize(new Dimension(2 * screenSize.width / 5 - 100, screenSize.height / 20));

                // adds the buttons to the formatting panel
                infoButtonsPanel.add(startGame);
                infoButtonsPanel.add(Box.createRigidArea(new Dimension(100, screenSize.height / 20)));
                infoButtonsPanel.add(exitGame1);

                // adds all necessary elements to the start up screen panel
                infoPanel.add(label, BorderLayout.CENTER);
                infoPanel.add(infoButtonsPanel, BorderLayout.SOUTH);
        }

        // creates a game screen which is displayed once the game is started
        private void createGameScreen() {

                // creates and formats the game screen
                gamePanel = new JPanel();
                gamePanel.setSize(screenSize.width, screenSize.height);
                gamePanel.setLayout(new BorderLayout());
                gamePanel.setBackground(Color.BLACK);
                gamePanel.setOpaque(true);

                // creates and formats a gui displaying the game board
                gui.setOpaque(true);
                gui.setPreferredSize(new Dimension(screenSize.width, screenSize.height - screenSize.height / 6));
                gui.setMaximumSize(new Dimension(screenSize.width, screenSize.height - screenSize.height / 6));
                gui.setMinimumSize(new Dimension(screenSize.width, screenSize.height - screenSize.height / 6));

                // creates and formats a control panel underneath the game board
                controlPanel = new JPanel();
                controlPanel.setOpaque(true);
                controlPanel.setLayout(new BorderLayout());
                controlPanel.setPreferredSize(new Dimension(screenSize.width, screenSize.height / 6));
                controlPanel.setMinimumSize(new Dimension(screenSize.width, screenSize.height / 6));
                controlPanel.setMaximumSize(new Dimension(screenSize.width, screenSize.height / 6));
                controlPanel.setBackground(Color.WHITE);

                // creates and formats an image for the splash screen displayed
                // on the
                // control bar
                ImageIcon image2 = new ImageIcon("avp/images/splash.jpg");
                JLabel label2 = new JLabel("", image2, JLabel.LEFT);
                label2.setMinimumSize(new Dimension(screenSize.width / 5, screenSize.height / 4));
                label2.setMaximumSize(new Dimension(screenSize.width / 5, screenSize.height / 4));
                label2.setPreferredSize(new Dimension(screenSize.width / 5, screenSize.height / 4));

                // creates a status display for the control center
                statusPanel = new JPanel();
                statusPanel.setLayout(new BorderLayout());
                statusPanel.setBackground(Color.WHITE);
                statusPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                statusPanel.setPreferredSize(new Dimension(screenSize.width - (2 * screenSize.width) / 5, screenSize.height / 4));
                statusPanel.setMaximumSize(new Dimension(screenSize.width - (2 * screenSize.width) / 5, screenSize.height / 4));
                statusPanel.setMinimumSize(new Dimension(screenSize.width - (2 * screenSize.width) / 5, screenSize.height / 4));

                // creates a title for the status display
                statusLabel = new JLabel("Status Terminal", SwingConstants.CENTER);
                statusLabel.setFont(new Font("Dialog", 1, 28));
                statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                statusLabel.setPreferredSize(new Dimension(screenSize.width / 5 - 100, screenSize.height / 20));
                statusLabel.setMaximumSize(new Dimension(screenSize.width / 5 - 100, screenSize.height / 20));
                statusLabel.setMinimumSize(new Dimension(screenSize.width / 5 - 100, screenSize.height / 20));

                // creates a format panel for the status display
                statusFormatBig = new JPanel();
                statusFormatBig.setBackground(Color.WHITE);
                statusFormatBig.setLayout(new BoxLayout(statusFormatBig, BoxLayout.LINE_AXIS));

                // creates a smaller format panel which goes inside of the first
                // format
                // panel on the left side
                statusFormat1 = new JPanel();
                statusFormat1.setBackground(Color.GRAY);
                statusFormat1.setLayout(new BoxLayout(statusFormat1, BoxLayout.PAGE_AXIS));
                statusFormat1.setPreferredSize(new Dimension(screenSize.width / 3, screenSize.height / 7));
                statusFormat1.setMinimumSize(new Dimension(screenSize.width / 3, screenSize.height / 7));
                statusFormat1.setMaximumSize(new Dimension(screenSize.width / 3, screenSize.height / 7));

                // creates fields which will display game status in the first
                // smaller
                // format panel
                turnStatus = new JLabel();
                turnStatus.setForeground(Color.WHITE);
                turnStatus.setFont(new Font("Dialog", 1, 16));
                turnStatus.setMinimumSize(new Dimension(screenSize.width / 5, screenSize.height / 40));
                turnStatus.setPreferredSize(new Dimension(screenSize.width / 5, screenSize.height / 40));
                turnStatus.setMaximumSize(new Dimension(screenSize.width / 5, screenSize.height / 40));

                moveStatus = new JLabel();
                moveStatus.setForeground(Color.WHITE);
                moveStatus.setFont(new Font("Dialog", 1, 16));
                moveStatus.setMinimumSize(new Dimension(screenSize.width / 3, screenSize.height / 40));
                moveStatus.setPreferredSize(new Dimension(screenSize.width / 3, screenSize.height / 40));
                moveStatus.setMaximumSize(new Dimension(screenSize.width / 3, screenSize.height / 40));

                alienSenseStatus = new JLabel();
                alienSenseStatus.setForeground(Color.WHITE);
                alienSenseStatus.setFont(new Font("Dialog", 1, 16));
                alienSenseStatus.setMinimumSize(new Dimension(screenSize.width / 5, screenSize.height / 40));
                alienSenseStatus.setPreferredSize(new Dimension(screenSize.width / 5, screenSize.height / 40));
                alienSenseStatus.setMaximumSize(new Dimension(screenSize.width / 5, screenSize.height / 40));

                predatorSenseStatus = new JLabel();
                predatorSenseStatus.setForeground(Color.WHITE);
                predatorSenseStatus.setFont(new Font("Dialog", 1, 16));
                predatorSenseStatus.setMinimumSize(new Dimension(screenSize.width / 5, screenSize.height / 40));
                predatorSenseStatus.setPreferredSize(new Dimension(screenSize.width / 5, screenSize.height / 40));
                predatorSenseStatus.setMaximumSize(new Dimension(screenSize.width / 5, screenSize.height / 40));

                // adds all necessary elements to the first small format panel
                statusFormat1.add(turnStatus);
                statusFormat1.add(moveStatus);
                statusFormat1.add(alienSenseStatus);
                statusFormat1.add(predatorSenseStatus);

                // creates a second small format panel which goes on the right
                // side
                // of the large format panel
                statusFormat2 = new JPanel();
                statusFormat2.setBackground(Color.GRAY);
                statusFormat2.setLayout(new BoxLayout(statusFormat2, BoxLayout.PAGE_AXIS));
                statusFormat2.setPreferredSize(new Dimension((screenSize.width - (2 * screenSize.width) / 5) / 2 - screenSize.width / 30, screenSize.height / 7));
                statusFormat2.setMinimumSize(new Dimension((screenSize.width - (2 * screenSize.width) / 5) / 2 - screenSize.width / 30, screenSize.height / 7));
                statusFormat2.setMaximumSize(new Dimension((screenSize.width - (2 * screenSize.width) / 5) / 2 - screenSize.width / 30, screenSize.height / 7));

                // creates fields for the second small format panel
                scannerStatus = new JLabel();
                scannerStatus.setForeground(Color.WHITE);
                scannerStatus.setFont(new Font("Dialog", 1, 16));
                scannerStatus.setMinimumSize(new Dimension(screenSize.width / 5, screenSize.height / 40));
                scannerStatus.setPreferredSize(new Dimension(screenSize.width / 5, screenSize.height / 40));
                scannerStatus.setMaximumSize(new Dimension(screenSize.width / 5, screenSize.height / 40));

                controlRoomStatus = new JLabel();
                controlRoomStatus.setForeground(Color.WHITE);
                controlRoomStatus.setFont(new Font("Dialog", 1, 16));
                controlRoomStatus.setMinimumSize(new Dimension(screenSize.width / 5, screenSize.height / 40));
                controlRoomStatus.setPreferredSize(new Dimension(screenSize.width / 5, screenSize.height / 40));
                controlRoomStatus.setMaximumSize(new Dimension(screenSize.width / 5, screenSize.height / 40));

                winStatus = new JLabel();
                winStatus.setForeground(Color.WHITE);
                winStatus.setFont(new Font("Dialog", 1, 16));
                winStatus.setMinimumSize(new Dimension(screenSize.width / 5, screenSize.height / 40));
                winStatus.setPreferredSize(new Dimension(screenSize.width / 5, screenSize.height / 40));
                winStatus.setMaximumSize(new Dimension(screenSize.width / 5, screenSize.height / 40));

                // adds all necessary elements to the second small format panel
                statusFormat2.add(controlRoomStatus);
                statusFormat2.add(winStatus);
                statusFormat2.add(scannerStatus);

                // adds all necessary elements to the big format panel
                statusFormatBig.add(statusFormat1);
                statusFormatBig.add(Box.createRigidArea(new Dimension(screenSize.width / 30, screenSize.height / 4)));
                statusFormatBig.add(statusFormat2);

                // adds all necessary elements to the status display
                statusPanel.add(statusLabel, BorderLayout.NORTH);
                statusPanel.add(statusFormatBig, BorderLayout.CENTER);

                // creates and formats an AI controls panel
                aiPanel = new JPanel();
                aiPanel.setBackground(Color.WHITE);
                aiPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                aiPanel.setPreferredSize(new Dimension(screenSize.width / 5, screenSize.height / 4));
                aiPanel.setMaximumSize(new Dimension(screenSize.width / 5, screenSize.height / 4));
                aiPanel.setMinimumSize(new Dimension(screenSize.width / 5, screenSize.height / 4));
                aiPanel.setLayout(new BoxLayout(aiPanel, BoxLayout.PAGE_AXIS));

                // creates a slider panel for formatting the speed slider
                sliderPanel = new JPanel();
                sliderPanel.setLayout(new BorderLayout());
                sliderPanel.setBackground(Color.WHITE);
                sliderPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                sliderPanel.setPreferredSize(new Dimension(screenSize.width / 5 - 100, screenSize.height / 13));
                sliderPanel.setMaximumSize(new Dimension(screenSize.width / 5 - 100, screenSize.height / 13));
                sliderPanel.setMinimumSize(new Dimension(screenSize.width / 5 - 100, screenSize.height / 13));

                // creates a speed slider for the AI controls panel
                slider = new JSlider(-100, 0, -80);
                slider.setBackground(Color.WHITE);
                slider.setMajorTickSpacing(20);
                slider.setMinorTickSpacing(10);
                slider.setPaintTicks(true);
                slider.setPaintLabels(true);
                slider.setSnapToTicks(true);

                // establishes the labels for the speed slider
                Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
                JLabel slower = new JLabel("Slower");
                JLabel faster = new JLabel("Faster");
                slower.setFont(new Font("Dialog", 1, 12));
                faster.setFont(new Font("Dialog", 1, 12));
                labelTable.put(new Integer(-100), slower);
                labelTable.put(new Integer(0), faster);
                slider.setLabelTable(labelTable);

                // creates a label for the speed slider
                sliderLabel = new JLabel("Set AI Speed", JLabel.CENTER);
                sliderLabel.setFont(buttonSize);

                // creates an enter button for the speed slider
                enter = new JButton("Enter");
                enter.setFocusable(false);
                enter.setFont(new Font("Dialog", 1, 12));

                // adds all necessary elements to the slider panel
                sliderPanel.add(slider, BorderLayout.CENTER);
                sliderPanel.add(enter, BorderLayout.EAST);
                sliderPanel.add(sliderLabel, BorderLayout.NORTH);

                // creates an "Exit" button to the AI controls panel
                exitGame2 = new JButton("Exit");
                exitGame2.setFocusable(false);
                exitGame2.setFont(buttonSize);
                exitGame2.setAlignmentX(CENTER_ALIGNMENT);
                exitGame2.setPreferredSize(new Dimension(screenSize.width / 5 - 100, screenSize.height / 20));
                exitGame2.setMaximumSize(new Dimension(screenSize.width / 5 - 100, screenSize.height / 20));
                exitGame2.setMinimumSize(new Dimension(screenSize.width / 5 - 100, screenSize.height / 20));

                // adds all necessary elements to the AI controls panel
                aiPanel.add(Box.createRigidArea(new Dimension(screenSize.width / 5 - 100, screenSize.height / 160)));
                aiPanel.add(sliderPanel);
                aiPanel.add(Box.createRigidArea(new Dimension(screenSize.width / 5 - 100, screenSize.height / 80)));
                aiPanel.add(exitGame2);

                // adds all necessary elements to the control panel
                controlPanel.add(aiPanel, BorderLayout.EAST);
                controlPanel.add(statusPanel, BorderLayout.CENTER);
                controlPanel.add(label2, BorderLayout.WEST);

                // adds all necessary elements to the game screen
                gamePanel.add(controlPanel, BorderLayout.SOUTH);
                gamePanel.add(gui, BorderLayout.CENTER);
        }

        // creates the Action Listeners which give functionality to the game
        private void createActionListeners() {

                // action listeners for the "Start" button on the start screen
                startGame.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                while (lpane.getComponent(0) != gamePanel) {
                                        lpane.setLayer(lpane.getComponent(0), -1);
                                }

                                final class Worker implements Runnable {
                                        public void run() {
                                                Engine.engine.play();
                                        }
                                }
                                Thread thread = new Thread(new Worker());
                                thread.setDaemon(true);
                                thread.start();
                                gui.paint(gui.getGraphics());
                                controlPanel.paint(controlPanel.getGraphics());
                        }
                });

                // action listeners for the "Exit" button on the start screen
                exitGame1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                System.exit(0);
                        }
                });

                // action listeners for the "Enter" button on the game screen
                enter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                double minv = Math.log(50);
                                double maxv = Math.log(750);
                                double scale = (maxv - minv) / (100);
                                Engine.speed = (int) Math.exp(minv + scale * (Math.abs(slider.getValue())));
                        }
                });

                // action listeners for the "Exit" button on the game screen
                exitGame2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                System.exit(0);
                        }
                });

                // action listeners that allow the enter button to start the
                // game
                infoPanel.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                        while (lpane.getComponent(0) != gamePanel) {
                                                lpane.setLayer(lpane.getComponent(0), -1);
                                        }

                                        final class Worker implements Runnable {
                                                public void run() {
                                                        Engine.engine.play();
                                                }
                                        }
                                        Thread thread = new Thread(new Worker());
                                        thread.setDaemon(true);
                                        thread.start();
                                        gui.paint(gui.getGraphics());
                                        controlPanel.paint(controlPanel.getGraphics());
                                }
                        }
                });
        }

        public GUI getGUI() {
                return gui;
        }

        public void changeASenseStatus(String s) {
                alienSenseStatus.setText(s);
        }

        public void changePSenseStatus(String s) {
                predatorSenseStatus.setText(s);
        }

        public void changeScannerStatus(String s) {
                scannerStatus.setText(s);
        }

        public void changeTurnStatus(String s) {
                turnStatus.setText(s);
        }

        public void changeWinStatus(String s) {
                winStatus.setText(s);
        }

        public void changeCRStatus(String s) {
                controlRoomStatus.setText(s);
        }

        public void changeMoveStatus(String s) {
                moveStatus.setText(s);
        }
}
