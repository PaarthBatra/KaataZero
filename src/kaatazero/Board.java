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
    private final int[][] CenterOfBoxes={{B_WIDTH/6,B_HEIGHT/6},{B_WIDTH/2,B_HEIGHT/6},{B_WIDTH*5/6,B_HEIGHT/6},
                                         {B_WIDTH/6,B_HEIGHT/2},{B_WIDTH/2,B_HEIGHT/2},{B_WIDTH*5/6,B_HEIGHT/2},
                                         {B_WIDTH/6,B_HEIGHT*5/6},{B_WIDTH/2,B_HEIGHT*5/6},{B_WIDTH*5/6,B_HEIGHT*5/6}};
    
    protected Thread animator;
    private int x, y,yReverse,xReverse;
    public KaataZero game ;
    public CallGUI gui;
    private boolean gameActive;
    private boolean boardReady;
    private int currentPlayer;
    private final ComputerPlayer computerPlayer = new ComputerPlayer();
    
    public Board(CallGUI gui1) {
        gui = gui1;
        game = new KaataZero();
        initBoard(gui);
        
    }

    
    
    private void initBoard(CallGUI gui) {
        
        this.setBackground(BACKGROUNDCOLOUR);
        this.addMouseListener(this);
        this.setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        this.setDoubleBuffered(true);
        x = INITIAL_X;
        y = INITIAL_Y;
        yReverse = B_HEIGHT;
        xReverse = B_WIDTH;
        gameActive = false;
        boardReady = false;
        
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
        
        g.drawLine(B_WIDTH/3,-10,B_WIDTH/3,y);
        g.drawLine(B_WIDTH*2/3,B_HEIGHT + 10 ,B_WIDTH*2/3,yReverse +10 );
        g.drawLine(-10,B_HEIGHT/3,x,B_HEIGHT/3);
        g.drawLine(B_WIDTH+10,B_HEIGHT*2/3,xReverse+10,B_HEIGHT*2/3);

        drawMoves(g);
        
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawMoves(Graphics g) {
        for (int position = 0; position < BOARD_SIZE * BOARD_SIZE; position++) {
            int value = KaataZero.getPosition(game.matrix, position);
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
        if (!gameActive || !isHumanTurn() || !KaataZero.isEmptyPosition(game.matrix, position)) {
            return;
        }

        KaataZero.setPosition(game.matrix,position,currentPlayer);
        repaint();

        if (handleGameStatus()) {
            return;
        }

        if (isPlayerVsPlayerMode()) {
            currentPlayer = currentPlayer == PLAYER_X ? PLAYER_O : PLAYER_X;
            updateTurnLabel();
        } else {
            currentPlayer = PLAYER_O;
            gui.move="Computer";
            gui.moveLabel.setText("Move : " + gui.move);
            ComputersMove();
        }
    }

    public void ComputersMove(){
        if (!gameActive || isPlayerVsPlayerMode() || !"Computer".equals(gui.move)) {
            return;
        }

        int position = computerPlayer.chooseMove(game.matrix, gui.difficulty);

        if (position == -1) {
            handleGameStatus();
            return;
        }

        KaataZero.setPosition(game.matrix,position,PLAYER_O);
        repaint();

        if (!handleGameStatus()) {
            currentPlayer = PLAYER_X;
            gui.move="Player";
            gui.moveLabel.setText("Move : " + gui.move);
        }
    }

    private boolean handleGameStatus() {
        int success = KaataZero.checkGameStatus(game.matrix);

        if (success == GAME_CONTINUE) {
            return false;
        }

        gameActive = false;

        if (isPlayerVsPlayerMode()) {
            if (success == PLAYER_X) {
                gui.statusbar.setText("Player X Won");
                showPlayAgainDialog("Player X Won!\nReady to Play Again?");
            } else if (success == PLAYER_O) {
                gui.statusbar.setText("Player O Won");
                showPlayAgainDialog("Player O Won!\nReady to Play Again?");
            } else if (success == GAME_DRAW) {
                gui.statusbar.setText("Match Drawn");
                showPlayAgainDialog("Match Drawn!\nReady to Play Again?");
            }
        }
        else if (success == PLAYER_O){
            gui.statusbar.setText("Computer Won");
            gui.score = 0 ;
            gui.scoreLabel.setText("Score : " + gui.score);
            showPlayAgainDialog("Oops ! Computer Won \n Your Score becomes : " + gui.score + " \n Try hard buddy."
                    + "\nReady to Play Again?");
        }
        else if (success == PLAYER_X){
            gui.statusbar.setText("Player Won");
            gui.score = gui.score + 100 ;
            gui.scoreLabel.setText("Score : " + gui.score);
            showPlayAgainDialog("Congratulations ! You Won \n Your Score is : " + gui.score + " \n You are doing great !!."
                    + "\nReady to Play Again ?");
        }
        else if (success == GAME_DRAW){
            gui.statusbar.setText("Match Drawn");
            gui.score = gui.score - 100 ;
            gui.scoreLabel.setText("Score : " + gui.score);
            showPlayAgainDialog("Well ! Match Drawn \n Your Score becomes : " + gui.score + " \nDraw is same as Losing , Isnt it ?"
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

        if (animator == null || !animator.isAlive()) {
            animator = new Thread(this);
        }
    }
    
    //Paint Component being Called
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(STROKESIZE));
        g2d.setColor(KAATAZEROCOLOUR);
        
        drawBoard(g2d);
        
    }

    public void startGame() {
        game.reset();
        currentPlayer = PLAYER_X;
        gui.move = isPlayerVsPlayerMode() ? "Player X" : "Computer";
        gui.moveLabel.setText("Move : " + gui.move);
        gui.statusbar.setText("Game is Running");
        x = INITIAL_X;
        y = INITIAL_Y;
        yReverse = B_HEIGHT;
        xReverse = B_WIDTH;
        gameActive = true;
        boardReady = false;
        repaint();

        animator = new Thread(this);
        animator.start();
    }
    
    //run method of the thread called in addNotify()
    @Override
    public void run() {
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
        boardReady = true;
        repaint();

        if (gameActive) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (isPlayerVsPlayerMode()) {
                        updateTurnLabel();
                    } else {
                        ComputersMove();
                    }
                }
            });
        }
    }

    private boolean isPlayerVsPlayerMode() {
        return "2 Player".equals(gui.gameMode);
    }

    private boolean isHumanTurn() {
        return boardReady && (isPlayerVsPlayerMode() || "Player".equals(gui.move));
    }

    private void updateTurnLabel() {
        gui.move = currentPlayer == PLAYER_X ? "Player X" : "Player O";
        gui.moveLabel.setText("Move : " + gui.move);
    }
    
    
    
    
    //Mouse Events
    @Override
    public void mousePressed(MouseEvent evt) {
        
    }
    
    @Override
    public void mouseReleased(MouseEvent evt) {
        int mx = evt.getX();
        int my = evt.getY();

        if (mx < 0 || mx >= B_WIDTH || my < 0 || my >= B_HEIGHT) {
            return;
        }

        int col = mx / (B_WIDTH / BOARD_SIZE);
        int row = my / (B_HEIGHT / BOARD_SIZE);
        int position = (row * BOARD_SIZE) + col;

        PlayersMove(position);
      }
     
    @Override
    public void mouseExited(MouseEvent evt) {
    }
    
    @Override
    public void mouseEntered(MouseEvent evt) {
    }
          
    @Override
    public void mouseClicked(MouseEvent evt) {
    }
    
    
    

    

    
    
}

