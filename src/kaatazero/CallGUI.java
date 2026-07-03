package kaatazero;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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

/**
 * Main application window for KaataZero.
 * Builds the splash screen, menus, controls, settings dialog, scoring UI, skins, and sounds.
 *
 * @author Paarth Batra
 * @version 2.0.0.0
 */
public class CallGUI extends JFrame {

    private static final Preferences PREFERENCES = Preferences.userNodeForPackage(CallGUI.class);
    private static final String PREF_GAME_MODE = "gameMode";
    private static final String PREF_DIFFICULTY = "difficulty";
    private static final String PREF_STARTING_PLAYER = "startingPlayer";
    private static final String PREF_SOUND_ENABLED = "soundEnabled";
    private static final String PREF_SKIN = "skin";
    private static final String PREF_PLAYER_WINS = "playerWins";
    private static final String PREF_OPPONENT_WINS = "opponentWins";
    private static final String PREF_DRAWS = "draws";
    private static final int COMPUTER_MOVE_DELAY_MS = 450;
    private static final String APP_VERSION = "2.0.0.0";
    private static final String RELEASE_DATE = "4th Jul 2026";
    private static final String WEBSITE_URL = "http://www.versionpb.co.in";
    private static final String WEBSITE_TEXT = "www.versionpb.co.in";
    private static final String[] SKIN_NAMES = {"Classic", "Midnight", "Ocean", "Forest", "Candy"};
    private static final Color[] PANEL_COLORS = {
        new Color(238, 238, 238), new Color(30, 34, 45), new Color(219, 237, 243), new Color(224, 238, 224), new Color(255, 228, 241)
    };
    private static final Color[] STATUS_COLORS = {
        new Color(245, 245, 245), new Color(42, 48, 64), new Color(238, 250, 255), new Color(239, 250, 238), new Color(255, 240, 249)
    };
    private static final Color[] TEXT_COLORS = {
        Color.BLACK, new Color(236, 240, 248), new Color(20, 60, 75), new Color(27, 77, 46), new Color(95, 40, 80)
    };
    private static final Color[] ACCENT_COLORS = {
        new Color(80, 80, 80), new Color(94, 210, 255), new Color(0, 148, 186), new Color(55, 145, 85), new Color(220, 78, 145)
    };
    private static final Color[] BOARD_BACKGROUND_COLORS = {
        Color.GRAY, new Color(18, 21, 30), new Color(28, 91, 115), new Color(38, 91, 58), new Color(121, 67, 106)
    };
    private static final Color[] GRID_COLORS = {
        Color.WHITE, new Color(115, 221, 255), new Color(185, 238, 255), new Color(195, 241, 195), new Color(255, 205, 232)
    };
    private static final Color[] PLAYER_X_COLORS = {
        Color.WHITE, new Color(255, 213, 96), new Color(255, 238, 170), new Color(255, 237, 157), new Color(255, 255, 255)
    };
    private static final Color[] PLAYER_O_COLORS = {
        Color.WHITE, new Color(255, 111, 145), new Color(117, 226, 255), new Color(158, 224, 115), new Color(255, 202, 74)
    };
    private static final Color[] WIN_LINE_COLORS = {
        Color.YELLOW, new Color(119, 255, 184), new Color(255, 213, 77), new Color(255, 245, 104), new Color(92, 255, 214)
    };

    protected boolean gameRunning;
    public String move = "Computer";
    public String gameMode = "Computer";
    public String difficulty = "Easy";
    public String startingPlayer = "Computer";
    public String skin = "Classic";
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
    protected JLabel websiteLabel = new JLabel("<html><u>" + WEBSITE_TEXT + "</u></html>");
    private final JLabel splashWebsiteLabel = new JLabel("<html><u>" + WEBSITE_TEXT + "</u></html>");

    public CallGUI() {
        gameRunning = false;
        loadSettings();
        initUI();
    }

    protected void goWebsite(JLabel websiteLabel) {
        websiteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        websiteLabel.setToolTipText("Visit " + WEBSITE_TEXT);
        websiteLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(WEBSITE_URL));
                } catch (URISyntaxException | IOException ex) {
                    statusbar.setText("Unable to open website");
                }
            }
        });
    }

    private void initUI() {
       goWebsite(websiteLabel);
       goWebsite(splashWebsiteLabel);
       JMenuBar menubar = new JMenuBar();

       ImageIcon appIcon = loadImageIcon("Icon.png");
       if (appIcon != null) {
           setIconImage(appIcon.getImage());
       }
       JMenu file = new JMenu("File");
       JMenu settings = new JMenu("Settings");
       JMenu help = new JMenu("Help");
       file.setMnemonic(KeyEvent.VK_F);
       settings.setMnemonic(KeyEvent.VK_S);
       help.setMnemonic(KeyEvent.VK_H);
       JMenuItem exitMenuItem = new JMenuItem("Exit");
       final JMenuItem settingsMenuItem = new JMenuItem("Game Settings");
       JMenuItem howToPlayMenuItem = new JMenuItem("How to Play");
       JMenuItem aboutMenuItem = new JMenuItem("About Us");
       exitMenuItem.setMnemonic(KeyEvent.VK_E);
       exitMenuItem.setToolTipText("Exit application");
       settingsMenuItem.setToolTipText("Change mode, difficulty, starter, skin, and sound");
       howToPlayMenuItem.setToolTipText("Show controls and game rules");
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
       howToPlayMenuItem.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent event) {
               showHowToPlayDialog();
           }
       });
       file.add(exitMenuItem);
       settings.add(settingsMenuItem);
       help.add(howToPlayMenuItem);
       help.add(aboutMenuItem);
       menubar.add(file);
       menubar.add(settings);
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
       splashWebsiteLabel.setHorizontalAlignment(JLabel.CENTER);
       splashWebsiteLabel.setBounds(0, 310, final_width, 20);
       panel.add(splashWebsiteLabel);
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
       final JComboBox<String> skinCombo = new JComboBox<String>(SKIN_NAMES);
       skinCombo.setSelectedItem(skin);
       skinCombo.setPreferredSize(new Dimension(96, 24));
       skinCombo.setMaximumSize(new Dimension(96, 24));
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

       JToolBar actionToolbar = createControlToolbar();
       JToolBar statsToolbar = createControlToolbar();
       JToolBar infoToolbar = createControlToolbar();
       JPanel bottomPanel = new JPanel(new GridLayout(3, 1));
       bottomPanel.setPreferredSize(new Dimension(final_width, 87));
       bottomPanel.setMinimumSize(new Dimension(final_width, 87));
       bottomPanel.setMaximumSize(new Dimension(final_width, 87));

       statusbar.setPreferredSize(new Dimension(final_width, 22));
       statusbar.setOpaque(true);
       statusbar.setBorder(LineBorder.createGrayLineBorder());
       add(statusbar, BorderLayout.NORTH);
       final Board board = new Board(this);
       skinCombo.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               skin = (String) skinCombo.getSelectedItem();
               applySkinToUi(panel, thumb, bottomPanel,
                       new JToolBar[] {actionToolbar, statsToolbar, infoToolbar},
                       new JLabel[] {moveLabel, scoreLabel, historyLabel, difficultyInfoLabel, websiteLabel},
                       new JButton[] {playButton, restartButton, undoButton, resetButton},
                       new JComboBox<?>[] {modeCombo, difficultyCombo, starterCombo, skinCombo},
                       soundCheck);
               board.applySkin();
               statusbar.setText("Skin set to " + skin);
               saveSettings();
           }
       });

       Dimension separator = new Dimension();
       separator.setSize(4,0);
       add(bottomPanel, BorderLayout.SOUTH);

       moveLabel.setPreferredSize(new Dimension(84, 24));
       scoreLabel.setPreferredSize(new Dimension(64, 24));
       historyLabel.setPreferredSize(new Dimension(154, 24));
       difficultyInfoLabel.setPreferredSize(new Dimension(210, 24));
       websiteLabel.setPreferredSize(new Dimension(120, 24));
       websiteLabel.setHorizontalAlignment(JLabel.RIGHT);
       updateHistoryLabel();
       actionToolbar.add(moveLabel);
       actionToolbar.addSeparator(separator);
       actionToolbar.add(restartButton);
       actionToolbar.addSeparator(separator);
       actionToolbar.add(undoButton);
       statsToolbar.add(scoreLabel);
       statsToolbar.addSeparator(separator);
       statsToolbar.add(historyLabel);
       statsToolbar.addSeparator(separator);
       statsToolbar.add(resetButton);
       infoToolbar.add(difficultyInfoLabel);
       infoToolbar.addSeparator(separator);
       infoToolbar.add(websiteLabel);
       bottomPanel.add(actionToolbar);
       bottomPanel.add(statsToolbar);
       bottomPanel.add(infoToolbar);
       difficultyCombo.setEnabled("Computer".equals(gameMode));
       starterCombo.setEnabled("Computer".equals(gameMode));
       add(panel,BorderLayout.CENTER);
       installKeyboardShortcuts(board, restartButton, undoButton, resetButton);
       applySkinToUi(panel, thumb, bottomPanel,
               new JToolBar[] {actionToolbar, statsToolbar, infoToolbar},
               new JLabel[] {moveLabel, scoreLabel, historyLabel, difficultyInfoLabel, websiteLabel},
               new JButton[] {playButton, restartButton, undoButton, resetButton},
               new JComboBox<?>[] {modeCombo, difficultyCombo, starterCombo, skinCombo},
               soundCheck);
       settingsMenuItem.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               showSettingsDialog(board, panel, thumb, bottomPanel,
                       new JToolBar[] {actionToolbar, statsToolbar, infoToolbar},
                       new JLabel[] {moveLabel, scoreLabel, historyLabel, difficultyInfoLabel, websiteLabel},
                       new JButton[] {playButton, restartButton, undoButton, resetButton},
                       new JComboBox<?>[] {modeCombo, difficultyCombo, starterCombo, skinCombo},
                       soundCheck);
           }
       });

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
       toolbar.setBackground(getPanelColor());
       toolbar.setPreferredSize(new Dimension(final_width, 29));
       toolbar.setMinimumSize(new Dimension(final_width, 29));
       toolbar.setMaximumSize(new Dimension(final_width, 29));
       return toolbar;
    }

    private void loadSettings() {
        gameMode = PREFERENCES.get(PREF_GAME_MODE, gameMode);
        difficulty = PREFERENCES.get(PREF_DIFFICULTY, difficulty);
        startingPlayer = PREFERENCES.get(PREF_STARTING_PLAYER, startingPlayer);
        skin = PREFERENCES.get(PREF_SKIN, skin);
        if (!isValidSkin(skin)) {
            skin = "Classic";
        }
        soundEnabled = PREFERENCES.getBoolean(PREF_SOUND_ENABLED, soundEnabled);
        playerWins = PREFERENCES.getInt(PREF_PLAYER_WINS, playerWins);
        opponentWins = PREFERENCES.getInt(PREF_OPPONENT_WINS, opponentWins);
        draws = PREFERENCES.getInt(PREF_DRAWS, draws);
        move = "Computer".equals(startingPlayer) ? "Computer" : "Player";
    }

    private void saveSettings() {
        PREFERENCES.put(PREF_GAME_MODE, gameMode);
        PREFERENCES.put(PREF_DIFFICULTY, difficulty);
        PREFERENCES.put(PREF_STARTING_PLAYER, startingPlayer);
        PREFERENCES.put(PREF_SKIN, skin);
        PREFERENCES.putBoolean(PREF_SOUND_ENABLED, soundEnabled);
    }

    private void saveStats() {
        PREFERENCES.putInt(PREF_PLAYER_WINS, playerWins);
        PREFERENCES.putInt(PREF_OPPONENT_WINS, opponentWins);
        PREFERENCES.putInt(PREF_DRAWS, draws);
    }

    private boolean isValidSkin(String skinName) {
        for (int i = 0; i < SKIN_NAMES.length; i++) {
            if (SKIN_NAMES[i].equals(skinName)) {
                return true;
            }
        }

        return false;
    }

    private int skinIndex() {
        for (int i = 0; i < SKIN_NAMES.length; i++) {
            if (SKIN_NAMES[i].equals(skin)) {
                return i;
            }
        }

        return 0;
    }

    private Color getPanelColor() {
        return PANEL_COLORS[skinIndex()];
    }

    private Color getStatusColor() {
        return STATUS_COLORS[skinIndex()];
    }

    private Color getTextColor() {
        return TEXT_COLORS[skinIndex()];
    }

    private Color getAccentColor() {
        return ACCENT_COLORS[skinIndex()];
    }

    public Color getBoardBackgroundColor() {
        return BOARD_BACKGROUND_COLORS[skinIndex()];
    }

    public Color getGridColor() {
        return GRID_COLORS[skinIndex()];
    }

    public Color getPlayerXColor() {
        return PLAYER_X_COLORS[skinIndex()];
    }

    public Color getPlayerOColor() {
        return PLAYER_O_COLORS[skinIndex()];
    }

    public Color getWinLineColor() {
        return WIN_LINE_COLORS[skinIndex()];
    }

    private void applySkinToUi(JPanel splashPanel, JLabel thumb, JPanel bottomPanel,
            JToolBar[] toolbars, JLabel[] labels, JButton[] buttons, JComboBox<?>[] combos, JCheckBox soundCheck) {
        Color panelColor = getPanelColor();
        Color statusColor = getStatusColor();
        Color textColor = getTextColor();
        Color accentColor = getAccentColor();
        Color buttonTextColor = "Midnight".equals(skin) ? Color.BLACK : Color.WHITE;

        splashPanel.setBackground(getBoardBackgroundColor());
        thumb.setForeground(textColor);
        splashWebsiteLabel.setForeground(accentColor);
        bottomPanel.setBackground(panelColor);
        statusbar.setBackground(statusColor);
        statusbar.setForeground(textColor);

        for (int i = 0; i < toolbars.length; i++) {
            styleToolbar(toolbars[i], panelColor, textColor);
        }

        for (int i = 0; i < labels.length; i++) {
            if (labels[i] == websiteLabel) {
                labels[i].setForeground(accentColor);
            } else {
                labels[i].setForeground(textColor);
            }
        }

        for (int i = 0; i < buttons.length; i++) {
            styleButton(buttons[i], accentColor, buttonTextColor);
        }

        for (int i = 0; i < combos.length; i++) {
            combos[i].setBackground(statusColor);
            combos[i].setForeground(textColor);
        }

        soundCheck.setBackground(panelColor);
        soundCheck.setForeground(textColor);
    }

    private void styleToolbar(JToolBar toolbar, Color panelColor, Color textColor) {
        toolbar.setBackground(panelColor);
        Component[] components = toolbar.getComponents();

        for (int i = 0; i < components.length; i++) {
            components[i].setBackground(panelColor);
            components[i].setForeground(textColor);
        }
    }

    private void styleButton(JButton button, Color accentColor, Color textColor) {
        button.setBackground(accentColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

    public int getComputerMoveDelayMs() {
        return COMPUTER_MOVE_DELAY_MS;
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
        saveStats();
        statusbar.setText("Score and history reset");
    }

    public void recordPlayerWin() {
        playerWins++;
        updateHistoryLabel();
        saveStats();
    }

    public void recordOpponentWin() {
        opponentWins++;
        updateHistoryLabel();
        saveStats();
    }

    public void recordDraw() {
        draws++;
        updateHistoryLabel();
        saveStats();
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

    private void showSettingsDialog(final Board board, final JPanel splashPanel, final JLabel thumb,
            final JPanel bottomPanel, final JToolBar[] toolbars, final JLabel[] labels,
            final JButton[] buttons, final JComboBox<?>[] combos, final JCheckBox soundCheck) {
        final JComboBox<String> modeSettings = new JComboBox<String>(new String[] {"Computer", "2 Player"});
        final JComboBox<String> difficultySettings = new JComboBox<String>(new String[] {"Easy", "Medium", "Hard"});
        final JComboBox<String> starterSettings = new JComboBox<String>(new String[] {"Computer", "Player"});
        final JComboBox<String> skinSettings = new JComboBox<String>(SKIN_NAMES);
        final JCheckBox soundSettings = new JCheckBox("Enable move and game-over sounds", soundEnabled);

        modeSettings.setSelectedItem(gameMode);
        difficultySettings.setSelectedItem(difficulty);
        starterSettings.setSelectedItem(startingPlayer);
        skinSettings.setSelectedItem(skin);
        difficultySettings.setEnabled("Computer".equals(gameMode));
        starterSettings.setEnabled("Computer".equals(gameMode));

        modeSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean computerMode = "Computer".equals(modeSettings.getSelectedItem());
                difficultySettings.setEnabled(computerMode);
                starterSettings.setEnabled(computerMode);
            }
        });

        JPanel settingsPanel = new JPanel(new GridLayout(5, 2, 6, 6));
        settingsPanel.add(new JLabel("Mode"));
        settingsPanel.add(modeSettings);
        settingsPanel.add(new JLabel("Difficulty"));
        settingsPanel.add(difficultySettings);
        settingsPanel.add(new JLabel("First Move"));
        settingsPanel.add(starterSettings);
        settingsPanel.add(new JLabel("Skin"));
        settingsPanel.add(skinSettings);
        settingsPanel.add(new JLabel("Sound"));
        settingsPanel.add(soundSettings);

        int result = JOptionPane.showConfirmDialog(this, settingsPanel, "Game Settings",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        gameMode = (String) modeSettings.getSelectedItem();
        difficulty = (String) difficultySettings.getSelectedItem();
        startingPlayer = (String) starterSettings.getSelectedItem();
        skin = (String) skinSettings.getSelectedItem();
        soundEnabled = soundSettings.isSelected();
        move = "Computer".equals(startingPlayer) ? "Computer" : "Player";

        syncSettingsControls(combos, soundCheck);
        updateDifficultyInfo();
        applySkinToUi(splashPanel, thumb, bottomPanel, toolbars, labels, buttons, combos, soundCheck);
        board.applySkin();
        saveSettings();
        statusbar.setText("Settings updated. Restart to apply turn changes.");
    }

    private void syncSettingsControls(JComboBox<?>[] combos, JCheckBox soundCheck) {
        if (combos.length >= 4) {
            combos[0].setSelectedItem(gameMode);
            combos[1].setSelectedItem(difficulty);
            combos[2].setSelectedItem(startingPlayer);
            combos[3].setSelectedItem(skin);
            combos[1].setEnabled("Computer".equals(gameMode));
            combos[2].setEnabled("Computer".equals(gameMode));
        }

        soundCheck.setSelected(soundEnabled);
        moveLabel.setText("Move : " + move);
    }

    private void showAboutDialog() {
        ImageIcon icon = loadImageIcon("pbcabtdll");
        JLabel aboutText = new JLabel("<html>Kaata Zero: <br>"
                + "Kaata Zero is the best way to pass time <br><br> Author : Paarth Batra<br>Version : " + APP_VERSION + "<br>Release Date : " + RELEASE_DATE + "<br><br>Contact:"
                + "paarth.batra@gmail.com<hr><hr>" + WEBSITE_TEXT + "<br>Its all about what do you want ! <br> Sno: 201512070000007</html>");
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

    private void showHowToPlayDialog() {
        JLabel helpText = new JLabel("<html><b>How to Play</b><br><br>"
                + "Get three X or O marks in a row, column, or diagonal.<br><br>"
                + "<b>Controls</b><br>"
                + "R: Restart game<br>"
                + "U: Undo latest move<br>"
                + "S: Reset score and history<br>"
                + "1-9: Play a board cell from top-left to bottom-right<br>"
                + "Esc: Quit<br><br>"
                + "<b>Settings</b><br>"
                + "Use Settings > Game Settings to change mode, difficulty, first move, skin, and sound.<br><br>"
                + "<b>Difficulty</b><br>"
                + "Easy: random computer moves<br>"
                + "Medium: sometimes wins or blocks<br>"
                + "Hard: perfect minimax play<br><br>"
                + "Stats are saved across app launches.</html>");

        JOptionPane.showMessageDialog(this, helpText, "How to Play", JOptionPane.INFORMATION_MESSAGE);
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
