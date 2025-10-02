/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaatazero;

/**
 *
 * @author Paarth Batra
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;

public class callGUI extends JFrame {
    


    protected boolean GameRunning;
    public String Move = "Computer"; 
    protected JLabel moveLabel = new JLabel(); 
    protected JLabel statusbar = new JLabel("Click Start Button to Start Game");
    protected int Score = 0;
    protected JLabel scoreLabel = new JLabel();
    protected int final_height = 350;
    protected int final_width = 350;
    protected JLabel levelLabel = new JLabel("<html><a href='http://www.versionpb.com'>www.versionpb.com</a></html>");
    public callGUI() {
        
        GameRunning = false;
        initUI();
    }
    

  
    protected void goWebsite(JLabel levelLabel) {
        levelLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://www.versionpb.com"));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
    }


                
    private void initUI() {
       goWebsite(levelLabel);
       JMenuBar menubar = new JMenuBar();
       //ImageIcon icon = new ImageIcon("D:\\Paarth\\Google_Drive\\Google Drive\\Codes\\Java\\KaataZero\\images\\Icon.ico");
       
       //String imagePath = "D:\\Paarth\\Google_Drive\\Google Drive\\Codes\\Java\\KaataZero\\images\\Icon.png";
        //InputStream imgStream = Game.class.getResourceAsStream(imagePath );
        //BufferedImage myImg = ImageIO.read(imgStream);
        // ImageIcon icon = new ImageIcon(myImg);

        // use icon here
        //setIconImage(icon);
       Image image = new ImageIcon(this.getClass().getResource("Icon.png")).getImage();
       //setIconImage(Toolkit.getDefaultToolkit().getImage(imagePath));
       setIconImage(image);
       JMenu file = new JMenu("File");
       JMenu help = new JMenu("Help");
       file.setMnemonic(KeyEvent.VK_F);
       help.setMnemonic(KeyEvent.VK_H);
       JMenuItem eMenuItem = new JMenuItem("Exit");
       JMenuItem eMenuItemAboutUs = new JMenuItem("About Us");
       //MenuItem eMenuItem = new JMenuItem("Exit", icon);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItemAboutUs.setToolTipText("About Us");
        eMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        eMenuItemAboutUs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //String ImagePath="D:\\Paarth\\Google_Drive\\Google Drive\\Codes\\Java\\KaataZero\\images\\pbcabtdll";
                String ImagePath="pbcabtdll";
                ImageIcon icon = new ImageIcon(this.getClass().getResource(ImagePath));
                Image img = icon.getImage() ;  
                Image newimg = img.getScaledInstance( 100, 100,  java.awt.Image.SCALE_SMOOTH ) ;  
                //icon = new ImageIcon( newimg );
                
                
                JLabel lbl = new JLabel(new ImageIcon((newimg)));
                JLabel a = new JLabel("<html>Kaata Zero: <br>"
                        + "Kaata Zero is te best way to pass time <br><br> Author : Paarth Batra<br>Version : 1.0.0.0<br>Release Date : 7th Dec 2015<br><br>Contact:"
                        + "paarth_batra@yahoo.co.in<br>paarthh2@rediffmail.com<hr><hr>www.versionpb.com<br>Its all about what do you want ! <br> Sno: 201512070000007</html>");
                //GridLayout experimentLayout = new GridLayout(4,4,5,2);
                JPanel p = new JPanel();
                  JLabel b = new JLabel("<html>www.versionpb.com<br>Its all about what do you want !<br> Sno: 201512070000007</html>");
                //p.setLayout(experimentLayout);
                p.add(a,BorderLayout.EAST);
                p.add(lbl,BorderLayout.CENTER);
                //p.add(b,BorderLayout.SOUTH);
                JOptionPane.showMessageDialog(null, p, "AboutUs", 
                                 JOptionPane.PLAIN_MESSAGE, null);
            }
        });
        file.add(eMenuItem);
        help.add(eMenuItemAboutUs);
        menubar.add(file);
        menubar.add(help);
        setJMenuBar(menubar);

        
       JPanel panel = new JPanel();
       //panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
       //panel.setLayout(new GridLayout(3, 2, 5, 5));
        
       JButton playButton = new JButton("Play");
       //JButton startButton = new JButton("Start");
       panel.setLayout(null);
       //playButton.setLayout(null);
       
        String vpbicon ="Splash.png";
        ImageIcon icon = new ImageIcon(this.getClass().getResource(vpbicon));
        Image img = icon.getImage() ;  
        //Image newimg = img.getScaledInstance( 100, 100,  java.awt.Image.SCALE_SMOOTH ) ;  
                //icon = new ImageIcon( newimg );
                
                
        //JLabel thumb = new JLabel(new ImageIcon((img)));
        
        
       JLabel thumb = new JLabel();
       thumb.setIcon(icon);
       panel.add(playButton);
       panel.add(thumb);
       playButton.setBounds(125, 190, 100, 40);
       thumb.setBounds(0, -40, 350, 350);
       panel.setBackground(Color.BLACK);
       panel.setPreferredSize(new Dimension(final_width, final_height));
       
       moveLabel.setText("Move : Computer");
       scoreLabel.setText("Score : " + Score);
       JToolBar horizontal = new JToolBar(JToolBar.HORIZONTAL);
       horizontal.setFloatable(false);
       horizontal.setRollover(true);
       horizontal.setMargin(new Insets(10, 5, 5, 5));
       

       //JLabel statusbar = new JLabel("Click Start Button to Start Game");
       statusbar.setPreferredSize(new Dimension(-1, 22));
       statusbar.setBorder(LineBorder.createGrayLineBorder());
       add(statusbar, BorderLayout.NORTH);
       Board board = new Board(this);
       
       
       Dimension Seprator = new Dimension();
       Seprator.setSize(final_width/15,0);
       add(horizontal, BorderLayout.SOUTH);

       System.out.println("Dimension using getSize is " + final_width);
       //horizontal.add(startButton);
       //horizontal.addSeparator(Seprator);

       horizontal.add(moveLabel);
       horizontal.addSeparator(Seprator);
       horizontal.addSeparator(Seprator);
       
       horizontal.add(levelLabel);
       horizontal.addSeparator(Seprator);
       
       horizontal.add(scoreLabel);
       
       
       
       add(panel,BorderLayout.CENTER);
       //add(board,BorderLayout.CENTER);
       //add(panel); 
       
       playButton.addActionListener((ActionEvent e) -> {
           // display/center the jdialog when the button is pressed
           System.out.println("Play Button Clicked");
           statusbar.setText("Game is Running");
           if ( GameRunning == false ){
           panel.setVisible(false);
           add(board,BorderLayout.CENTER);
           board.animator.start();
           GameRunning=true;
           }
           else{
           repaint();
           board.M.MReinitialize();
           
           }
       });
       
       /*
       startButton.addActionListener((ActionEvent e) -> {
           // display/center the jdialog when the button is pressed
           System.out.println("Start Button Clicked");
           statusbar.setText("Game is Running");
           if ( GameRunning == false ){
           board.animator.start();
           GameRunning=true;
           }
           else{
           repaint();
           board.M.MReinitialize();
           
           }
       });
               */
       
        
        setResizable(false);
        pack();
        
        setTitle("Kaata Zero");    
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }

  
    public static void main(String[] args) {
        System.out.println("Main Method of callGUI Class Initiated");
        EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run() {     
                System.out.println("run method inside , Main Method : callGUI Class");
                JFrame ex = new callGUI();
                ex.setVisible(true);  
                System.out.println("run method inside , After ex.setVisible");
                //ex.moveLabel.setText("hh");
                
            }
        });
    }
}

