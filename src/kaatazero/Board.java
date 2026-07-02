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
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Board extends JPanel
        implements Runnable,MouseListener {

    // Game constants
    private static final int BOARD_SIZE = KaataZero.BOARD_SIZE;
    private static final int PLAYER_O = KaataZero.PLAYER_O;
    private static final int PLAYER_X = KaataZero.PLAYER_X;
    private static final int GAME_CONTINUE = KaataZero.GAME_CONTINUE;
    private static final int GAME_DRAW = KaataZero.GAME_DRAW;
    
    // UI constants
    private final int B_WIDTH = 350;
    private final int B_HEIGHT = 350;
    private final int INITIAL_X = -40;
    private final int INITIAL_Y = -40;
    private final int DELAY = 2;
    private final int STROKESIZE =10;
    private final Color KAATAZEROCOLOUR = Color.WHITE;
    private final Color BACKGROUNDCOLOUR = Color.gray;
    private final int MARK_SIZE = 40;
    private final int[][] CenterOfBoxes={{B_WIDTH/6,B_HEIGHT/6},{B_WIDTH/6,B_HEIGHT/2},{B_WIDTH/6,B_HEIGHT*5/6},
                                         {B_WIDTH/2,B_HEIGHT/6},{B_WIDTH/2,B_HEIGHT/2},{B_WIDTH/2,B_HEIGHT*5/6},
                                         {B_WIDTH*5/6,B_HEIGHT/6},{B_WIDTH*5/6,B_HEIGHT/2},{B_WIDTH*5/6,B_HEIGHT*5/6}};
    
    protected Thread animator;
    private int x, y,yReverse,xReverse;
    public KaataZero M ;
    public callGUI gui;
    protected int CurrentValueAtMatrix;
    private boolean gameActive;
    private final Random random = new Random();
    
    public Board(callGUI gui1) {
        gui = gui1;
        M = new KaataZero(gui);
        System.out.println("Board Class Initiated");
        initBoard(gui);
        
    }

    
    
    private void initBoard(callGUI gui) {
        
        System.out.println("initBoard method : Board Class");
        this.setBackground(BACKGROUNDCOLOUR);
        this.addMouseListener(this);
        this.setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        this.setDoubleBuffered(true);
        x = INITIAL_X;
        y = INITIAL_Y;
        yReverse = B_HEIGHT;
        xReverse = B_WIDTH;
        gameActive = false;
        KaataZero.PrintMatrix(M.Matrix);
        
    }

    private void drawZero(Graphics g,int CenterX , int CenterY){
        g.setColor(KAATAZEROCOLOUR);
        g.drawOval(CenterX - (MARK_SIZE / 2), CenterY - (MARK_SIZE / 2), MARK_SIZE, MARK_SIZE);
        
    }

    private void drawCross(Graphics g, int centerX, int centerY) {
        int halfMark = MARK_SIZE / 2;
        g.setColor(KAATAZEROCOLOUR);
        g.drawLine(centerX - halfMark, centerY + halfMark, centerX + halfMark, centerY - halfMark);
        g.drawLine(centerX - halfMark, centerY - halfMark, centerX + halfMark, centerY + halfMark);
    }

    private void drawBoard(Graphics g){
        
        System.out.println("drawInitialCross method : Board Class");
        g.drawLine(B_WIDTH/3,-10,B_WIDTH/3,y);
        g.drawLine(B_WIDTH*2/3,B_HEIGHT + 10 ,B_WIDTH*2/3,yReverse +10 );
        g.drawLine(-10,B_HEIGHT/3,x,B_HEIGHT/3);
        g.drawLine(B_WIDTH+10,B_HEIGHT*2/3,xReverse+10,B_HEIGHT*2/3);

        drawMoves(g);
        
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawMoves(Graphics g) {
        for (int position = 0; position < BOARD_SIZE * BOARD_SIZE; position++) {
            int value = KaataZero.getPosition(M.Matrix, position);
            int centerX = CenterOfBoxes[position][0];
            int centerY = CenterOfBoxes[position][1];

            if (value == PLAYER_O) {
                drawZero(g, centerX, centerY);
            } else if (value == PLAYER_X) {
                drawCross(g, centerX, centerY);
            }
        }
    }

    public void PlayersMove(int position){
        if (!gameActive || !"Player".equals(gui.Move) || !KaataZero.isEmptyPosition(M.Matrix, position)) {
            return;
        }

        System.out.println("Setting Value at position " + position + " as : 1");
        KaataZero.MSetPosition(M.Matrix,position,PLAYER_X);
        repaint();
        KaataZero.PrintMatrix(M.Matrix);

        if (handleGameStatus()) {
            return;
        }

        gui.Move="Computer";
        gui.moveLabel.setText("Move : " + gui.Move);
        ComputersMove();
    }

    public void ComputersMove(){
        System.out.println("ComputersMove method : Board Class");

        if (!gameActive || !"Computer".equals(gui.Move)) {
            return;
        }

        int position = chooseComputerPosition();

        if (position == -1) {
            handleGameStatus();
            return;
        }

        System.out.println("Setting Value at position " + position + " as : 0");
        KaataZero.MSetPosition(M.Matrix,position,PLAYER_O);
        repaint();
        KaataZero.PrintMatrix(M.Matrix);

        if (!handleGameStatus()) {
            gui.Move="Player";
            gui.moveLabel.setText("Move : " + gui.Move);
        }
    }

    private int chooseComputerPosition() {
        List<Integer> emptyPositions = new ArrayList<Integer>();
        for (int position = 0; position < BOARD_SIZE * BOARD_SIZE; position++) {
            if (KaataZero.isEmptyPosition(M.Matrix, position)) {
                emptyPositions.add(Integer.valueOf(position));
            }
        }

        if (emptyPositions.isEmpty()) {
            return -1;
        }

        return emptyPositions.get(random.nextInt(emptyPositions.size())).intValue();
    }

    private boolean handleGameStatus() {
        int success = KaataZero.MSuccess(M.Matrix);

        if (success == GAME_CONTINUE) {
            return false;
        }

        gameActive = false;

        if (success == PLAYER_O){
            gui.statusbar.setText("Computer Won");
            gui.Score = 0 ;
            gui.scoreLabel.setText("Score : " + gui.Score);
            showPlayAgainDialog("Oops ! Computer Won \n Your Score becomes : " + gui.Score + " \n Try hard buddy."
                    + "\nReady to Play Again?");
        }
        else if (success == PLAYER_X){
            gui.statusbar.setText("Player Won");
            gui.Score = gui.Score + 100 ;
            gui.scoreLabel.setText("Score : " + gui.Score);
            showPlayAgainDialog("Congratulations ! You Won \n Your Score is : " + gui.Score + " \n You are doing great !!."
                    + "\nReady to Play Again ?");
        }
        else if (success == GAME_DRAW){
            gui.statusbar.setText("Match Drawn");
            gui.Score = gui.Score - 100 ;
            gui.scoreLabel.setText("Score : " + gui.Score);
            showPlayAgainDialog("Well ! Match Drawn \n Your Score becomes : " + gui.Score + " \nDraw is same as Losing , Isnt it ?"
                    + "\nWant to Win ? Play Again ?");
        }

        return true;
    }

    private void showPlayAgainDialog(String message) {
        int result = JOptionPane.showConfirmDialog(
        null,
        message,
        "Match Result !!",
        JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.out.println("YES is clicked");
            startGame();
        } else {
            System.exit(0);
        }
    }

    private void cycle() {

        x = Math.min(B_WIDTH, x + 1);
        y = Math.min(B_HEIGHT, y + 1);
        xReverse = Math.max(0, xReverse - 1);
        yReverse = Math.max(0, yReverse - 1);

    }
    //Start of Program by calling the Thread
    @Override
    public void addNotify() {
        super.addNotify();
        System.out.println("addNotify method : Board Class");

        if (animator == null || !animator.isAlive()) {
            animator = new Thread(this);
        }
    }
    
    //Paint Component being Called
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        System.out.println("paintComponent method : Board Class");
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(STROKESIZE));
        g2d.setColor(KAATAZEROCOLOUR);
        
        drawBoard(g2d);
        
    }

    public void startGame() {
        M.MReinitialize();
        gui.Move = "Computer";
        gui.moveLabel.setText("Move : " + gui.Move);
        gui.statusbar.setText("Game is Running");
        x = INITIAL_X;
        y = INITIAL_Y;
        yReverse = B_HEIGHT;
        xReverse = B_WIDTH;
        gameActive = true;
        repaint();

        animator = new Thread(this);
        animator.start();
    }
    
    //run method of the thread called in addNotify()
    @Override
    public void run() {
        System.out.println("run method : Board Class");
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (gameActive && y < B_HEIGHT) {

            cycle();
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }

            beforeTime = System.currentTimeMillis();
        }

        x = B_WIDTH;
        y = B_HEIGHT;
        xReverse = 0;
        yReverse = 0;
        repaint();

        if (gameActive) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ComputersMove();
                }
            });
        }
    }
    
    
    
    
    //Mouse Events
    @Override
    public void mousePressed(MouseEvent evt) {
        System.out.println("Mouse is Pressed");
        
    }
    
    @Override
    public void mouseReleased(MouseEvent evt) {
        System.out.println("Mouse is Released");
        int mx = evt.getX();
        int my = evt.getY();
        System.out.println("Value of B_WIDTH and B_HEIGHT are " + B_WIDTH + " :  " + B_HEIGHT);

        if (mx < 0 || mx >= B_WIDTH || my < 0 || my >= B_HEIGHT) {
            return;
        }

        int col = mx / (B_WIDTH / BOARD_SIZE);
        int row = my / (B_HEIGHT / BOARD_SIZE);
        int position = (col * BOARD_SIZE) + row;

        System.out.println("Kaata to be drawn in " + position + " Box");
        PlayersMove(position);
        
        System.out.println("Circle drawn on mx , my  as center: " + mx  + "  " + my);
      }
     
    @Override
    public void mouseExited(MouseEvent evt) {
        System.out.println("Mouse is Exited");
    }
    
    @Override
    public void mouseEntered(MouseEvent evt) {
        System.out.println("Mouse is mouseEntered");
    }
          
    @Override
    public void mouseClicked(MouseEvent evt) {
        System.out.println("Mouse is mouseClicked");
    }
    
    
    

    

    
    
}

