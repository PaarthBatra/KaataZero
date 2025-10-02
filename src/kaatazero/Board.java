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

import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Board extends JPanel
        implements Runnable,MouseListener {

    private final int B_WIDTH = 350;
    private final int B_HEIGHT = 350;
    private final int INITIAL_X = -40;
    private final int INITIAL_Y = -40;
    private final int DELAY = 2;
    private final int STROKESIZE =10;
    private final Color KAATAZEROCOLOUR = Color.WHITE;
    private final Color BACKGROUNDCOLOUR = Color.gray;
    //private int[][] CenterOfBoxes= new int [9][2];
    int CenterOfBoxes[][]={{B_WIDTH/6,B_HEIGHT/6},{B_WIDTH/6,B_HEIGHT/2},{B_WIDTH/6,B_HEIGHT*5/6},
                           {B_WIDTH/2,B_HEIGHT/6},{B_WIDTH/2,B_HEIGHT/2},{B_WIDTH/2,B_HEIGHT*5/6},
                           {B_WIDTH*5/6,B_HEIGHT/6},{B_WIDTH*5/6,B_HEIGHT/2},{B_WIDTH*5/6,B_HEIGHT*5/6}};
    
    private Graphics graphicsForDrawing;  // A graphics context for the panel
    protected Thread animator;
    private int x, y,yReverse,xReverse;
    public KaataZero M ;
    public callGUI gui;
    protected int CurrentValueAtMatrix;
    
    public Board(callGUI gui1) {
        gui = gui1;
        M = new KaataZero(gui);
        System.out.println("Board Class Initiated");
        initBoard(gui);
        
    }

    
    
    private void initBoard(callGUI gui) {
        
        System.out.println("initBoard method : Board Class");
        //setting background colout to JPanel
        this.setBackground(BACKGROUNDCOLOUR);
        //adding mouseListener Events
        this.addMouseListener(this);
        this.setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        this.setDoubleBuffered(true);
        
        


        x = INITIAL_X;
        y = INITIAL_Y;
        yReverse = B_HEIGHT;
        xReverse = B_WIDTH;
        
        
        /*int[][] Matrix = new int[3][3];
        //initializing matrix
        for (int row = 0; row < 3; row ++)
            for (int col = 0; col < 3; col++)
                Matrix[row][col] = 9;
                */
        
        M.PrintMatrix(M.Matrix);
        
    }

    private void DrawRandomZero(Graphics g,int CenterX , int CenterY){
        System.out.println("DrawRandomZero method : Board Class");
        g.setColor(KAATAZEROCOLOUR);
        g.drawOval(CenterX - 20 , CenterY - 20  , 40, 40);
        
    }
    
    
    
    private void drawInitialCross(Graphics g,callGUI gui){
        
        System.out.println("drawInitialCross method : Board Class");
        g.drawLine(B_WIDTH/3,-10,B_WIDTH/3,y);
        g.drawLine(B_WIDTH*2/3,B_HEIGHT + 10 ,B_WIDTH*2/3,yReverse +10 );
        g.drawLine(-10,B_HEIGHT/3,x,B_HEIGHT/3);
        g.drawLine(B_WIDTH+10,B_HEIGHT*2/3,xReverse+10,B_HEIGHT*2/3);
        
        //drawing initial 0 and x in all boxes with default couour as same as of JPanel
        if (y == B_HEIGHT){
            Random ran = new Random();
            int x = ran.nextInt(9);
            System.out.println("Random Value is : " + x);
            System.out.println("CenterOfBoxes[x][0] ,CenterOfBoxes[x][1] is  : " + CenterOfBoxes[x][0] + " , " + CenterOfBoxes[x][1]);
            DrawRandomZero(g,CenterOfBoxes[x][0] ,CenterOfBoxes[x][1]);
            M.MSetPosition(M.Matrix,x,0);
            M.PrintMatrix(M.Matrix);
            gui.Move="Player";
            gui.moveLabel.setText("Move : " + gui.Move);
            
        }
        
        Toolkit.getDefaultToolkit().sync();
    }
    
    public void PlayersMove(Graphics graphicsForDrawing2D,int position){
        switch (position){
        case 0 : 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,0,0);
            break;
        case 1: 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,1,0);
            break;
        case 2: 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,2,0);
            break;
        case 3: 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,0,1);
            break;
        case 4: 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,1,1);
            break;
        case 5: 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,2,1);
            break;
        case 6: 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,0,2);
            break;
        case 7: 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,1,2);
            break;
        case 8: 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,2,2);
            break;
        }
        
        System.out.println("CurrentValueAtMatrix is " + CurrentValueAtMatrix);
        M.PrintMatrix(M.Matrix);
            
            if (CurrentValueAtMatrix == 9 && gui.Move == "Player"){
                int mx=CenterOfBoxes[position][0];
                int my=CenterOfBoxes[position][1];
                graphicsForDrawing2D.drawLine(mx - 20 , my + 20  , mx + 20 , my - 20);
                graphicsForDrawing2D.drawLine(mx - 20 , my - 20  , mx + 20 , my + 20 );
                System.out.println("Setting Value at position " + position + " as : 1");
                M.MSetPosition(M.Matrix,position,1);

                System.out.println("Printing Matrix after Players Move");
                M.PrintMatrix(M.Matrix);
                
            int Success = M.MSuccess(M.Matrix);
            if (Success == 0){
                gui.statusbar.setText("Computer Won");
                //.GameRunning=false;
                gui.Score = 0 ;
                
                gui.levelLabel.setText("Easy");
                gui.scoreLabel.setText("Score : " + gui.Score);
                int result = JOptionPane.showConfirmDialog(
                null,
                "Oops ! Computer Won \n Your Score becomes : " + gui.Score + " \n Try hard buddy."
                        + "\nReady to Play Again?",
                "Match Result !!",
                JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    System.out.println("OK is clicked");
                    repaint();
                    M.MReinitialize();
                    } else {
                    System.exit(0);
                
                    }
                
                }
            else if (Success == 1){
                gui.statusbar.setText("Player Won");
                //gui.GameRunning=false;
                gui.Score = gui.Score + 100 ;
                gui.scoreLabel.setText("Score : " + gui.Score);
                /*
                if (gui.Score >= 1000)
                gui.levelLabel.setText("Medium");
                */
                int result = JOptionPane.showConfirmDialog(
                null,
                "Congratulations ! You Won \n Your Score is : " + gui.Score + " \n You are doing great !!."
                        + "\nReady to Play Again ?",
                "Match Result !!",
                JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    System.out.println("OK is clicked");
                    repaint();
                    M.MReinitialize();
                    } else {
                    System.exit(0);
                
                    }      
                }
            else if (Success == 999){
                gui.statusbar.setText("Match Drawn");
                gui.Score = gui.Score - 100 ;
                gui.scoreLabel.setText("Score : " + gui.Score);
                /*
                if (gui.Score >= 1000)
                gui.levelLabel.setText("Medium");
                */
                int result = JOptionPane.showConfirmDialog(
                null,
                "Well ! Match Drawn \n Your Score becomes : " + gui.Score + " \nDraw is same as Losing , Isnt it ?"
                        + "\nWant to Win ? Play Again ?",
                "Match Result !!",
                JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    System.out.println("OK is clicked");
                    repaint();
                    M.MReinitialize();
                    } else {
                    System.exit(0);
                
                    }  
                //gui.GameRunning=false;
                }
            else{
                gui.Move="Computer";
                gui.moveLabel.setText("Move : " + gui.Move);
                ComputersMove(graphicsForDrawing2D);
            }
                
                
            }
    }
    public void ComputersMove(Graphics g){
        System.out.println("ComputersMove method : Board Class");
        int Success;
        
        Random ran = new Random();
        int x = ran.nextInt(9);
        System.out.println("Random Value is : " + x);
        switch (x){
        case 0 : 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,0,0);
            break;
        case 1: 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,1,0);
            break;
        case 2: 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,2,0);
            break;
        case 3: 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,0,1);
            break;
        case 4: 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,1,1);
            break;
        case 5: 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,2,1);
            break;
        case 6: 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,0,2);
            break;
        case 7: 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,1,2);
            break;
        case 8: 
            CurrentValueAtMatrix=M.MFindValue(M.Matrix,2,2);
            break;
        }
        
        System.out.println("CurrentValueAtMatrix is " + CurrentValueAtMatrix);
        M.PrintMatrix(M.Matrix);
        if (CurrentValueAtMatrix == 9 && gui.Move == "Computer"){
            DrawRandomZero(g,CenterOfBoxes[x][0] ,CenterOfBoxes[x][1]);
            System.out.println("Setting Value at position " + x + " as : 0");
            M.MSetPosition(M.Matrix,x,0);
            System.out.println("Printing Matrix after Computers Move");
            M.PrintMatrix(M.Matrix);
            
            Success = M.MSuccess(M.Matrix);
            if (Success == 0){
                gui.statusbar.setText("Computer Won");
                //.GameRunning=false;
                gui.Score = 0 ;
                gui.scoreLabel.setText("Score : " + gui.Score);
                int result = JOptionPane.showConfirmDialog(
                null,
                "Oops ! Computer Won \n Your Score becomes : " + gui.Score + " \n Try hard buddy."
                        + "\nReady to Play Again?",
                "Match Result !!",
                JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    System.out.println("OK is clicked");
                    repaint();
                    M.MReinitialize();
                    } else {
                    System.exit(0);
                
                    }
                
                }
            else if (Success == 1){
                gui.statusbar.setText("Player Won");
                //gui.GameRunning=false;
                gui.Score = gui.Score + 100 ;
                gui.scoreLabel.setText("Score : " + gui.Score);
                
                int result = JOptionPane.showConfirmDialog(
                null,
                "Congratulations ! You Won \n Your Score is : " + gui.Score + " \n You are doing great !!."
                        + "\nReady to Play Again ?",
                "Match Result !!",
                JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    System.out.println("OK is clicked");
                    repaint();
                    M.MReinitialize();
                    } else {
                    System.exit(0);
                
                    }      
                }
            else if (Success == 999){
                gui.statusbar.setText("Match Drawn");
                gui.Score = gui.Score - 100 ;
                gui.scoreLabel.setText("Score : " + gui.Score);
                int result = JOptionPane.showConfirmDialog(
                null,
                "Well ! Match Drawn \n Your Score becomes : " + gui.Score + " \nDraw is same as Losing , Isnt it ?"
                        + "\nWant to Win ? Play Again ?",
                "Match Result !!",
                JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    System.out.println("OK is clicked");
                    repaint();
                    M.MReinitialize();
                    } else {
                    System.exit(0);
                
                    }  
                //gui.GameRunning=false;
                }
     
            gui.Move="Player";
            gui.moveLabel.setText("Move : " + gui.Move);
        }
        else{
            System.out.println("Else Statement in Computers Move");
            ComputersMove(g);
        }
            
            
        
    }
    private void cycle() {

        x += 1;
        y += 1;
        xReverse -= 1;
        yReverse -= 1;

    }
    //Start of Program by calling the Thread
    @Override
    public void addNotify() {
        super.addNotify();
        System.out.println("addNotify method : Board Class");

        animator = new Thread(this);
        //animator.start();
    }
    
    //Paint Component being Called
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        System.out.println("paintComponent method : Board Class");
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(STROKESIZE));
        
        drawInitialCross(g2d,gui);
        
    }
    
    //run method of the thread called in addNotify()
    @Override
    public void run() {
        System.out.println("run method : Board Class");
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (y < B_HEIGHT) {

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
        
        graphicsForDrawing = getGraphics();
        Graphics2D graphicsForDrawing2D = (Graphics2D) graphicsForDrawing;
        graphicsForDrawing2D.setColor(KAATAZEROCOLOUR);
        graphicsForDrawing2D.setStroke(new BasicStroke(STROKESIZE));
        
        /*
        
        graphicsForDrawing2D.drawOval(mx - 20 , my - 20  , 40, 40);
        //graphicsForDrawing.fillOval(mx - 20 , my - 20  , 40, 40);
        
        graphicsForDrawing2D.drawLine(mx - 10 , my + 10  , mx + 10 , my - 10);
        graphicsForDrawing2D.drawLine(mx - 10 , my - 10  , mx + 10 , my + 10 );
        */
        System.out.println("Value of B_WIDTH and B_HEIGHT are " + B_WIDTH + " :  " + B_HEIGHT);
        if (mx > 0  && mx < (B_WIDTH/3) && my > 0 && my < (B_HEIGHT/3)){
            System.out.println("Kaata to be drawn in 0 Box");
            //graphicsForDrawing2D.drawOval(CenterOfBoxes[0][0]  - 20 , CenterOfBoxes[0][1] - 20  , 40, 40);
            //check if at position 0 something is already done at matrix 
            PlayersMove(graphicsForDrawing2D,0);          
        }
        else if (mx > 0  && mx < (B_WIDTH/3) && my > (B_HEIGHT/3) && my < (2*B_HEIGHT/3)){
            System.out.println("Kaata to be drawn in 1 Box");
            PlayersMove(graphicsForDrawing2D,1);
        }
        else if (mx > 0  && mx < (B_WIDTH/3) && my > (2*B_HEIGHT/3) && my < (B_HEIGHT)){
            System.out.println("Kaata to be drawn in 2 Box");
            PlayersMove(graphicsForDrawing2D,2);
        }
        else if (mx > (B_WIDTH/3)  && mx < (2*B_WIDTH/3) && my > 0 && my < (B_HEIGHT/3)){
            System.out.println("Kaata to be drawn in 3 Box");
            PlayersMove(graphicsForDrawing2D,3);
        }
        else if (mx > (B_WIDTH/3)  && mx < (2*B_WIDTH/3) && my > (B_HEIGHT/3) && my < (2*B_HEIGHT/3)){
            System.out.println("Kaata to be drawn in 4 Box");
            PlayersMove(graphicsForDrawing2D,4);
        }
        else if (mx > (B_WIDTH/3)  && mx < (2*B_WIDTH/3) && my > (2*B_HEIGHT/3) && my < (B_HEIGHT)){
            System.out.println("Kaata to be drawn in 5 Box");
            PlayersMove(graphicsForDrawing2D,5);
        }
        else if (mx > (2*B_WIDTH/3)  && mx < (B_WIDTH) && my > 0 && my < (B_HEIGHT/3)){
            System.out.println("Kaata to be drawn in 6 Box");
            PlayersMove(graphicsForDrawing2D,6);
        }
        else if (mx > (2*B_WIDTH/3)  && mx < (B_WIDTH) && my > (B_HEIGHT/3) && my < (2*B_HEIGHT/3)){
            System.out.println("Kaata to be drawn in 7 Box");
            PlayersMove(graphicsForDrawing2D,7);
        }
        else if (mx > (2*B_WIDTH/3)  && mx < (B_WIDTH) && my > (2*B_HEIGHT/3) && my < (B_HEIGHT)){
            System.out.println("Kaata to be drawn in 8 Box");
            PlayersMove(graphicsForDrawing2D,8);
        }
        
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

