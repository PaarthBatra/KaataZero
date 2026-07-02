package kaatazero;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;

public class CallGUI extends JFrame {

    protected boolean gameRunning;
    public String move = "Computer";
    public String difficulty = "Easy";
    protected JLabel moveLabel = new JLabel();
    protected JLabel statusbar = new JLabel("Click Start Button to Start Game");
    protected int score = 0;
    protected JLabel scoreLabel = new JLabel();
    protected int final_height = 350;
    protected int final_width = 350;
    protected JLabel levelLabel = new JLabel("<html><a href='http://www.versionpb.co.in'>www.versionpb.co.in</a></html>");

    public CallGUI() {
        gameRunning = false;
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
       panel.setLayout(null);

       ImageIcon icon = loadImageIcon("Splash.png");
       JLabel thumb = new JLabel();
       if (icon != null) {
           thumb.setIcon(icon);
       } else {
           thumb.setForeground(Color.WHITE);
           thumb.setHorizontalAlignment(JLabel.CENTER);
           thumb.setText("Kaata Zero");
       }
       panel.add(playButton);
       panel.add(thumb);
       playButton.setBounds(125, 190, 100, 40);
       thumb.setBounds(0, -40, 350, 350);
       panel.setBackground(Color.BLACK);
       panel.setPreferredSize(new Dimension(final_width, final_height));

       moveLabel.setText("Move : Computer");
       scoreLabel.setText("Score : " + score);
       final JComboBox<String> difficultyCombo = new JComboBox<String>(new String[] {"Easy", "Medium", "Hard"});
       difficultyCombo.setSelectedItem(difficulty);
       difficultyCombo.setPreferredSize(new Dimension(70, 24));
       difficultyCombo.setMaximumSize(new Dimension(70, 24));
       difficultyCombo.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               difficulty = (String) difficultyCombo.getSelectedItem();
               statusbar.setText("Difficulty set to " + difficulty);
           }
       });
       JToolBar horizontal = new JToolBar(JToolBar.HORIZONTAL);
       horizontal.setFloatable(false);
       horizontal.setRollover(true);
       horizontal.setMargin(new Insets(4, 2, 2, 2));
       horizontal.setPreferredSize(new Dimension(final_width, 30));
       horizontal.setMinimumSize(new Dimension(final_width, 30));
       horizontal.setMaximumSize(new Dimension(final_width, 30));

       statusbar.setPreferredSize(new Dimension(final_width, 22));
       statusbar.setBorder(LineBorder.createGrayLineBorder());
       add(statusbar, BorderLayout.NORTH);
       final Board board = new Board(this);

       Dimension separator = new Dimension();
       separator.setSize(4,0);
       add(horizontal, BorderLayout.SOUTH);

       moveLabel.setPreferredSize(new Dimension(105, 24));
       scoreLabel.setPreferredSize(new Dimension(58, 24));
       horizontal.add(moveLabel);
       horizontal.addSeparator(separator);
       horizontal.add(new JLabel("Diff: "));
       horizontal.add(difficultyCombo);
       horizontal.addSeparator(separator);
       horizontal.add(scoreLabel);
       add(panel,BorderLayout.CENTER);

       playButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               statusbar.setText("Game is Running");
               if (gameRunning == false){
                   panel.setVisible(false);
                   add(board,BorderLayout.CENTER);
                   board.startGame();
                   gameRunning=true;
               }
               else{
                   board.startGame();
               }
           }
       });

        setResizable(false);
        pack();

        setTitle("Kaata Zero");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
