package kaatazero;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Animated game board panel that handles drawing, mouse input, undo, and match flow.
 * Delegates AI turns to {@link ComputerPlayer} and game-state checks to {@link KaataZero}.
 *
 * @author Paarth Batra
 */
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
    private Color gridColour = Color.WHITE;
    private Color playerXColour = Color.WHITE;
    private Color playerOColour = Color.WHITE;
    private Color winLineColour = Color.YELLOW;
    private Color backgroundColour = Color.gray;
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
    private int[] winningLine;
    private int[] moveHistory = new int[BOARD_SIZE * BOARD_SIZE];
    private int moveHistoryCount;
    private boolean computerMovePending;
    private final ComputerPlayer computerPlayer = new ComputerPlayer();
    
    public Board(CallGUI gui1) {
        gui = gui1;
        game = new KaataZero();
        initBoard(gui);
        
    }

    
    
    private void initBoard(CallGUI gui) {
        
        applySkin();
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
        g.setColor(playerOColour);
        g.drawOval(CenterX - (MARK_SIZE / 2), CenterY - (MARK_SIZE / 2), MARK_SIZE, MARK_SIZE);
        
    }

    private void drawCross(Graphics g, int centerX, int centerY) {
        int halfMark = MARK_SIZE / 2;
        g.setColor(playerXColour);
        g.drawLine(centerX - halfMark, centerY + halfMark, centerX + halfMark, centerY - halfMark);
        g.drawLine(centerX - halfMark, centerY - halfMark, centerX + halfMark, centerY + halfMark);
    }

    private void drawBoard(Graphics g){
        
        g.setColor(gridColour);
        g.drawLine(B_WIDTH/3,-10,B_WIDTH/3,y);
        g.drawLine(B_WIDTH*2/3,B_HEIGHT + 10 ,B_WIDTH*2/3,yReverse +10 );
        g.drawLine(-10,B_HEIGHT/3,x,B_HEIGHT/3);
        g.drawLine(B_WIDTH+10,B_HEIGHT*2/3,xReverse+10,B_HEIGHT*2/3);

        drawMoves(g);
        drawWinningLine(g);
        
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

    private void drawWinningLine(Graphics g) {
        if (winningLine == null) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(winLineColour);
        g2d.setStroke(new BasicStroke(STROKESIZE + 2));

        int startPosition = winningLine[0];
        int endPosition = winningLine[2];
        g2d.drawLine(CenterOfBoxes[startPosition][0], CenterOfBoxes[startPosition][1],
                CenterOfBoxes[endPosition][0], CenterOfBoxes[endPosition][1]);
    }

    public void PlayersMove(int position){
        if (!gameActive || !isHumanTurn() || !KaataZero.isEmptyPosition(game.matrix, position)) {
            return;
        }

        KaataZero.setPosition(game.matrix,position,currentPlayer);
        rememberMove(position);
        gui.playMoveSound();
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
            scheduleComputerMove();
        }
    }

    public void ComputersMove(){
        computerMovePending = false;
        if (!gameActive || isPlayerVsPlayerMode() || !"Computer".equals(gui.move)) {
            return;
        }

        int position = computerPlayer.chooseMove(game.matrix, gui.difficulty);

        if (position == -1) {
            handleGameStatus();
            return;
        }

        KaataZero.setPosition(game.matrix,position,PLAYER_O);
        rememberMove(position);
        gui.playMoveSound();
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
        winningLine = KaataZero.findWinningLine(game.matrix);
        repaint();
        gui.playGameOverSound();

        if (isPlayerVsPlayerMode()) {
            if (success == PLAYER_X) {
                gui.recordPlayerWin();
                gui.showGameResult("Player X won.");
            } else if (success == PLAYER_O) {
                gui.recordOpponentWin();
                gui.showGameResult("Player O won.");
            } else if (success == GAME_DRAW) {
                gui.recordDraw();
                gui.showGameResult("Match drawn.");
            }
        }
        else if (success == PLAYER_O){
            gui.score = 0 ;
            gui.updateScoreLabel();
            gui.recordOpponentWin();
            gui.showGameResult("Computer won. Your score is " + gui.score + ".");
        }
        else if (success == PLAYER_X){
            gui.score = gui.score + gui.getWinScore() ;
            gui.updateScoreLabel();
            gui.recordPlayerWin();
            gui.showGameResult("You won. Your score is " + gui.score + ".");
        }
        else if (success == GAME_DRAW){
            gui.score = gui.score - 50 ;
            gui.updateScoreLabel();
            gui.recordDraw();
            gui.showGameResult("Match drawn. Your score is " + gui.score + ".");
        }

        return true;
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
        
        drawBoard(g2d);
        
    }

    public void applySkin() {
        backgroundColour = gui.getBoardBackgroundColor();
        gridColour = gui.getGridColor();
        playerXColour = gui.getPlayerXColor();
        playerOColour = gui.getPlayerOColor();
        winLineColour = gui.getWinLineColor();
        setBackground(backgroundColour);
        repaint();
    }

    public void startGame() {
        game.reset();
        winningLine = null;
        moveHistoryCount = 0;
        computerMovePending = false;
        currentPlayer = shouldComputerStart() ? PLAYER_O : PLAYER_X;
        gui.move = startingMoveText();
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
                        scheduleComputerMove();
                    }
                }
            });
        }
    }

    private void scheduleComputerMove() {
        if (computerMovePending || !gameActive || isPlayerVsPlayerMode()) {
            return;
        }

        computerMovePending = true;
        javax.swing.Timer timer = new javax.swing.Timer(gui.getComputerMoveDelayMs(), new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                ComputersMove();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private boolean isPlayerVsPlayerMode() {
        return "2 Player".equals(gui.gameMode);
    }

    private boolean shouldComputerStart() {
        return !isPlayerVsPlayerMode() && "Computer".equals(gui.startingPlayer);
    }

    private String startingMoveText() {
        if (isPlayerVsPlayerMode()) {
            return "Player X";
        }

        return shouldComputerStart() ? "Computer" : "Player";
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

    public void undoLastMove() {
        if (!boardReady || moveHistoryCount == 0 || winningLine != null) {
            return;
        }

        if (isPlayerVsPlayerMode()) {
            undoSingleMove();
            currentPlayer = currentPlayer == PLAYER_X ? PLAYER_O : PLAYER_X;
            updateTurnLabel();
        } else {
            undoSingleMove();

            if (moveHistoryCount > 0) {
                undoSingleMove();
            }

            computerMovePending = false;
            currentPlayer = PLAYER_X;
            gui.move = "Player";
            gui.moveLabel.setText("Move : " + gui.move);
        }

        gui.statusbar.setText("Last move undone");
        repaint();
    }

    private void rememberMove(int position) {
        if (moveHistoryCount < moveHistory.length) {
            moveHistory[moveHistoryCount] = position;
            moveHistoryCount++;
        }
    }

    private void undoSingleMove() {
        if (moveHistoryCount == 0) {
            return;
        }

        moveHistoryCount--;
        KaataZero.setPosition(game.matrix, moveHistory[moveHistoryCount], KaataZero.EMPTY_CELL);
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

