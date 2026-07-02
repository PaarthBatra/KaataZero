package kaatazero;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

public class CallGUI extends JFrame {

    private static final Preferences PREFERENCES = Preferences.userNodeForPackage(CallGUI.class);
    private static final String PREF_GAME_MODE = "gameMode";
    private static final String PREF_DIFFICULTY = "difficulty";
    private static final String PREF_STARTING_PLAYER = "startingPlayer";
    private static final String PREF_SOUND_ENABLED = "soundEnabled";
    private static final Color PANEL_BACKGROUND = new Color(238, 238, 238);
    private static final Color STATUS_BACKGROUND = new Color(245, 245, 245);

    protected boolean gameRunning;
    public String move = "Computer";
    public String gameMode = "Computer";
    public String difficulty = "Easy";
    public String startingPlayer = "Computer";
    public boolean soundEnabled = true;
    protected JLabel moveLabel = new JLabel();
    protected JLabel statusbar = new JLabel("Click Start Button to Start Game");
    protected JLabel historyLabel = new JLabel();
    protected JLabel difficultyInfoLabel = new JLabel();
    protected int score = 0;
    protected int playerWins = 0;
    protected int opponentWins = 0;
    protected int draws = 0;
    protected JLabel scoreLabel = new JLabel();
    protected int final_height = 350;
    protected int final_width = 350;
    protected JLabel levelLabel = new JLabel("<html><a href='http://www.versionpb.co.in'>www.versionpb.co.in</a></html>");

    public CallGUI() {
        gameRunning = false;
        loadSettings();
        initUI();
    }

    protected void goWebsite(JLabel levelLabel) {
        levelLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://www.versionpb.co.in"));
                } catch (URISyntaxException | IOException ex) {
                    statusbar.setText("Unable to open website");
                }
            }
        });
    }

    private void initUI() {
       goWebsite(levelLabel);
       JMenuBar menubar = new JMenuBar();

       ImageIcon appIcon = loadImageIcon("Icon.png");
       if (appIcon != null) {
           setIconImage(appIcon.getImage());
       }
       JMenu file = new JMenu("File");
       JMenu help = new JMenu("Help");
       file.setMnemonic(KeyEvent.VK_F);
       help.setMnemonic(KeyEvent.VK_H);
       JMenuItem exitMenuItem = new JMenuItem("Exit");
       JMenuItem aboutMenuItem = new JMenuItem("About Us");
       exitMenuItem.setMnemonic(KeyEvent.VK_E);
       exitMenuItem.setToolTipText("Exit application");
       aboutMenuItem.setToolTipText("About Us");
       exitMenuItem.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent event) {
               System.exit(0);
           }
       });
       aboutMenuItem.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent event) {
               showAboutDialog();
           }
       });
       file.add(exitMenuItem);
       help.add(aboutMenuItem);
       menubar.add(file);
       menubar.add(help);
       setJMenuBar(menubar);

       final JPanel panel = new JPanel();

       JButton playButton = new JButton("Play");
       playButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
       panel.setLayout(null);

       ImageIcon icon = loadImageIcon("Splash.png");
       JLabel thumb = new JLabel();
       if (icon != null) {
           thumb.setIcon(icon);
       } else {
           thumb.setFont(new Font("SansSerif", Font.BOLD, 28));
           thumb.setForeground(Color.WHITE);
           thumb.setHorizontalAlignment(JLabel.CENTER);
           thumb.setText("<html><div style='text-align:center'>Kaata Zero<br><span style='font-size:12px'>Tic Tac Toe</span></div></html>");
       }
       panel.add(playButton);
       panel.add(thumb);
       playButton.setBounds(125, 190, 100, 40);
       thumb.setBounds(0, -40, 350, 350);
       panel.setBackground(Color.BLACK);
       panel.setPreferredSize(new Dimension(final_width, final_height));

       moveLabel.setText("Move : " + move);
       updateScoreLabel();
       final JComboBox<String> modeCombo = new JComboBox<String>(new String[] {"Computer", "2 Player"});
       modeCombo.setSelectedItem(gameMode);
       modeCombo.setPreferredSize(new Dimension(76, 24));
       modeCombo.setMaximumSize(new Dimension(76, 24));
       final JComboBox<String> difficultyCombo = new JComboBox<String>(new String[] {"Easy", "Medium", "Hard"});
       difficultyCombo.setSelectedItem(difficulty);
       difficultyCombo.setPreferredSize(new Dimension(64, 24));
       difficultyCombo.setMaximumSize(new Dimension(64, 24));
       final JComboBox<String> starterCombo = new JComboBox<String>(new String[] {"Computer", "Player"});
       starterCombo.setSelectedItem(startingPlayer);
       starterCombo.setPreferredSize(new Dimension(76, 24));
       starterCombo.setMaximumSize(new Dimension(76, 24));
       final JCheckBox soundCheck = new JCheckBox("Sound", soundEnabled);
       soundCheck.setFocusable(false);
       soundCheck.setMargin(new Insets(0, 0, 0, 0));
       updateDifficultyInfo();
       modeCombo.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               gameMode = (String) modeCombo.getSelectedItem();
               difficultyCombo.setEnabled("Computer".equals(gameMode));
               starterCombo.setEnabled("Computer".equals(gameMode));
               statusbar.setText("Mode set to " + gameMode);
               updateDifficultyInfo();
               saveSettings();
           }
       });
       difficultyCombo.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               difficulty = (String) difficultyCombo.getSelectedItem();
               statusbar.setText("Difficulty set to " + difficulty);
               updateDifficultyInfo();
               saveSettings();
           }
       });
       starterCombo.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               startingPlayer = (String) starterCombo.getSelectedItem();
               move = "Computer".equals(startingPlayer) ? "Computer" : "Player";
               moveLabel.setText("Move : " + move);
               statusbar.setText(startingPlayer + " will start next game");
               saveSettings();
           }
       });
       soundCheck.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               soundEnabled = soundCheck.isSelected();
               statusbar.setText(soundEnabled ? "Sound enabled" : "Sound muted");
               saveSettings();
           }
       });
       final JButton restartButton = new JButton("Restart");
       restartButton.setMargin(new Insets(2, 4, 2, 4));
       restartButton.setPreferredSize(new Dimension(72, 24));
       restartButton.setEnabled(false);
       final JButton undoButton = new JButton("Undo");
       undoButton.setMargin(new Insets(2, 4, 2, 4));
       undoButton.setPreferredSize(new Dimension(58, 24));
       undoButton.setEnabled(false);
       final JButton resetButton = new JButton("Reset");
       resetButton.setMargin(new Insets(2, 4, 2, 4));
       resetButton.setPreferredSize(new Dimension(58, 24));

       JToolBar primaryToolbar = createControlToolbar();
       JToolBar secondaryToolbar = createControlToolbar();
       JToolBar historyToolbar = createControlToolbar();
       JToolBar infoToolbar = createControlToolbar();
       JPanel bottomPanel = new JPanel(new GridLayout(4, 1));
       bottomPanel.setPreferredSize(new Dimension(final_width, 116));
       bottomPanel.setMinimumSize(new Dimension(final_width, 116));
       bottomPanel.setMaximumSize(new Dimension(final_width, 116));

       statusbar.setPreferredSize(new Dimension(final_width, 22));
       statusbar.setOpaque(true);
       statusbar.setBackground(STATUS_BACKGROUND);
       statusbar.setBorder(LineBorder.createGrayLineBorder());
       add(statusbar, BorderLayout.NORTH);
       final Board board = new Board(this);

       Dimension separator = new Dimension();
       separator.setSize(4,0);
       add(bottomPanel, BorderLayout.SOUTH);

       moveLabel.setPreferredSize(new Dimension(72, 24));
       scoreLabel.setPreferredSize(new Dimension(52, 24));
       historyLabel.setPreferredSize(new Dimension(148, 24));
       difficultyInfoLabel.setPreferredSize(new Dimension(final_width - 8, 24));
       updateHistoryLabel();
       primaryToolbar.add(moveLabel);
       primaryToolbar.addSeparator(separator);
       primaryToolbar.add(new JLabel("Mode"));
       primaryToolbar.add(modeCombo);
       primaryToolbar.addSeparator(separator);
       primaryToolbar.add(new JLabel("Level"));
       primaryToolbar.add(difficultyCombo);
       secondaryToolbar.add(new JLabel("First"));
       secondaryToolbar.add(starterCombo);
       secondaryToolbar.addSeparator(separator);
       secondaryToolbar.add(scoreLabel);
       secondaryToolbar.addSeparator(separator);
       secondaryToolbar.add(restartButton);
       secondaryToolbar.addSeparator(separator);
       secondaryToolbar.add(undoButton);
       historyToolbar.add(historyLabel);
       historyToolbar.addSeparator(separator);
       historyToolbar.add(soundCheck);
       historyToolbar.addSeparator(separator);
       historyToolbar.add(resetButton);
       infoToolbar.add(difficultyInfoLabel);
       bottomPanel.add(primaryToolbar);
       bottomPanel.add(secondaryToolbar);
       bottomPanel.add(historyToolbar);
       bottomPanel.add(infoToolbar);
       difficultyCombo.setEnabled("Computer".equals(gameMode));
       starterCombo.setEnabled("Computer".equals(gameMode));
       add(panel,BorderLayout.CENTER);
       installKeyboardShortcuts(board, restartButton, undoButton, resetButton);

       playButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               statusbar.setText("Game is Running");
               if (gameRunning == false){
                   panel.setVisible(false);
                   add(board,BorderLayout.CENTER);
                   board.startGame();
                   gameRunning=true;
                   restartButton.setEnabled(true);
                   undoButton.setEnabled(true);
               }
               else{
                   board.startGame();
                   undoButton.setEnabled(true);
               }
           }
       });
       restartButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               board.startGame();
               statusbar.setText("Game restarted");
           }
       });
       undoButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               board.undoLastMove();
           }
       });
       resetButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               resetScoreAndHistory();
           }
       });

        setResizable(false);
        pack();

        setTitle("Kaata Zero");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JToolBar createControlToolbar() {
       JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
       toolbar.setFloatable(false);
       toolbar.setRollover(true);
       toolbar.setMargin(new Insets(3, 2, 2, 2));
       toolbar.setBackground(PANEL_BACKGROUND);
       toolbar.setPreferredSize(new Dimension(final_width, 29));
       toolbar.setMinimumSize(new Dimension(final_width, 29));
       toolbar.setMaximumSize(new Dimension(final_width, 29));
       return toolbar;
    }

    private void loadSettings() {
        gameMode = PREFERENCES.get(PREF_GAME_MODE, gameMode);
        difficulty = PREFERENCES.get(PREF_DIFFICULTY, difficulty);
        startingPlayer = PREFERENCES.get(PREF_STARTING_PLAYER, startingPlayer);
        soundEnabled = PREFERENCES.getBoolean(PREF_SOUND_ENABLED, soundEnabled);
        move = "Computer".equals(startingPlayer) ? "Computer" : "Player";
    }

    private void saveSettings() {
        PREFERENCES.put(PREF_GAME_MODE, gameMode);
        PREFERENCES.put(PREF_DIFFICULTY, difficulty);
        PREFERENCES.put(PREF_STARTING_PLAYER, startingPlayer);
        PREFERENCES.putBoolean(PREF_SOUND_ENABLED, soundEnabled);
    }

    public int getWinScore() {
        if ("Hard".equals(difficulty)) {
            return 200;
        }

        if ("Medium".equals(difficulty)) {
            return 100;
        }

        return 50;
    }

    public void updateScoreLabel() {
        scoreLabel.setText("Score : " + score);
    }

    public void updateHistoryLabel() {
        historyLabel.setText("You:" + playerWins + " Opp:" + opponentWins + " Draw:" + draws);
    }

    public void updateDifficultyInfo() {
        String info;

        if ("2 Player".equals(gameMode)) {
            info = "2 Player: take turns locally. Keys: U undo, R restart.";
        } else if ("Hard".equals(difficulty)) {
            info = "Hard: perfect minimax AI. Keys: 1-9 cells, U undo.";
        } else if ("Medium".equals(difficulty)) {
            info = "Medium: sometimes wins or blocks. Keys: 1-9 cells.";
        } else {
            info = "Easy: random computer moves. Keys: R restart, Esc quit.";
        }

        difficultyInfoLabel.setText(info);
        difficultyInfoLabel.setFont(difficultyInfoLabel.getFont().deriveFont(Font.PLAIN, 11f));
    }

    public void resetScoreAndHistory() {
        score = 0;
        playerWins = 0;
        opponentWins = 0;
        draws = 0;
        updateScoreLabel();
        updateHistoryLabel();
        statusbar.setText("Score and history reset");
    }

    public void recordPlayerWin() {
        playerWins++;
        updateHistoryLabel();
    }

    public void recordOpponentWin() {
        opponentWins++;
        updateHistoryLabel();
    }

    public void recordDraw() {
        draws++;
        updateHistoryLabel();
    }

    public void showGameResult(String resultText) {
        statusbar.setText(resultText + " Press R to restart.");
    }

    public void playMoveSound() {
        if (!soundEnabled) {
            return;
        }

        Toolkit.getDefaultToolkit().beep();
    }

    public void playGameOverSound() {
        if (!soundEnabled) {
            return;
        }

        Toolkit.getDefaultToolkit().beep();
    }

    private void installKeyboardShortcuts(final Board board, final JButton restartButton,
            final JButton undoButton, final JButton resetButton) {
        JComponent rootPane = getRootPane();

        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "restartGame");
        rootPane.getActionMap().put("restartGame", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (restartButton.isEnabled()) {
                    board.startGame();
                    statusbar.setText("Game restarted");
                }
            }
        });

        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exitGame");
        rootPane.getActionMap().put("exitGame", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0), "undoMove");
        rootPane.getActionMap().put("undoMove", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoButton.isEnabled()) {
                    board.undoLastMove();
                }
            }
        });

        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "resetScore");
        rootPane.getActionMap().put("resetScore", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetButton.doClick();
            }
        });

        for (int position = 0; position < KaataZero.BOARD_SIZE * KaataZero.BOARD_SIZE; position++) {
            final int boardPosition = position;
            int keyCode = KeyEvent.VK_1 + position;
            rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0), "playPosition" + position);
            rootPane.getActionMap().put("playPosition" + position, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    board.PlayersMove(boardPosition);
                }
            });
        }
    }

    private void showAboutDialog() {
        ImageIcon icon = loadImageIcon("pbcabtdll");
        JLabel aboutText = new JLabel("<html>Kaata Zero: <br>"
                + "Kaata Zero is the best way to pass time <br><br> Author : Paarth Batra<br>Version : 1.0.0.0<br>Release Date : 7th Dec 2015<br><br>Contact:"
                + "paarth_batra@yahoo.co.in<br>paarthh2@rediffmail.com<hr><hr>www.versionpb.co.in<br>Its all about what do you want ! <br> Sno: 201512070000007</html>");
        JPanel panel = new JPanel();
        panel.add(aboutText,BorderLayout.EAST);

        if (icon != null) {
            Image img = icon.getImage();
            Image newimg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon((newimg)));
            panel.add(imageLabel,BorderLayout.CENTER);
        } else {
            JLabel fallbackImage = new JLabel("KZ");
            fallbackImage.setFont(new Font("SansSerif", Font.BOLD, 36));
            fallbackImage.setHorizontalAlignment(JLabel.CENTER);
            fallbackImage.setPreferredSize(new Dimension(100, 100));
            panel.add(fallbackImage,BorderLayout.CENTER);
        }

        JOptionPane.showMessageDialog(null, panel, "AboutUs", JOptionPane.PLAIN_MESSAGE, null);
    }

    private ImageIcon loadImageIcon(String resourceName) {
        URL resource = this.getClass().getResource(resourceName);

        if (resource == null) {
            return null;
        }

        return new ImageIcon(resource);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame ex = new CallGUI();
                ex.setVisible(true);
            }
        });
    }
}
