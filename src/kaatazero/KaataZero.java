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
public class KaataZero {
    
    // Game constants
    public static final int BOARD_SIZE = 3;
    public static final int EMPTY_CELL = 9;
    public static final int PLAYER_O = 0;
    public static final int PLAYER_X = 1;
    public static final int GAME_CONTINUE = 9;
    public static final int GAME_DRAW = 999;

    private static final int[][] WINNING_LINES = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},
        {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8},
        {0, 4, 8},
        {2, 4, 6}
    };

    /**
     * @param args the command line arguments
     */
    public int[][] Matrix = new int[BOARD_SIZE][BOARD_SIZE];
    public KaataZero(callGUI gui){
        MReinitialize();
        System.out.println("KaataZero Class Initiated");
    }
    public void MReinitialize(){
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Matrix[row][col] = EMPTY_CELL;
            }
        }
        System.out.println("KaataZero Class : Method MReinitialize");
    }
    
    
    
    /*
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Hi from Main function !");
        
        int[][] Matrix = new int[3][3];
        int[][] Result;
        //initializing matrix
        for (int row = 0; row < BOARD_SIZE; row ++)
            for (int col = 0; col < BOARD_SIZE; col++)
                Matrix[row][col] = EMPTY_CELL;
        
        //printing matrix
        System.out.println("Main : Prinitng Matrix");
        for (int row = 0; row < BOARD_SIZE; row ++){
            for (int col = 0; col < BOARD_SIZE; col++)
                System.out.print(Matrix[row][col] + " ");
            System.out.println();
        }
        
        //Result=MSetPosition(Matrix,5,0);
        //PrintMatrix(Result);
        
        //Result=MSet(Matrix,1,1,0);
       
        Result=MSetPosition(Matrix,5,0);
        
        System.out.println("Main : Prinitng Result Matrix");
        for (int row = 0; row < 3; row ++){
            for (int col = 0; col < 3; col++)
                System.out.print(Result[row][col] + " ");
            System.out.println();
        }
                
        //System.out.println("Result from collegecomparison function is " + Result);
    }*/
    
    public static void PrintMatrix(int[][]Matrix){
        System.out.println("Class: KaataZero , Method : PrintMatrix : Prinitng Result Matrix");
        for (int row = 0; row < BOARD_SIZE; row ++){
            for (int col = 0; col < BOARD_SIZE; col++)
                System.out.print(Matrix[row][col] + " ");
            System.out.println();
        }
    }
    public static int MFindValue(int[][] Matrix,int r,int c){
        System.out.println("Class: KaataZero , MFindValue : Started");
        return Matrix[r][c];
    }
    
    public static int[][] MSet(int[][] Matrix,int r,int c,int value)
    {
        System.out.println("Class: KaataZero , Method : MSet :Hi I am MSet method");
        Matrix[r][c] = value;
        return Matrix;
    }
    public static int MSuccess(int[][] Matrix){
        //0 computer won
        //1 player 1
        //999 match drawn
        // 9 all ok
       System.out.println("Class: KaataZero , Method : MSuccess");
       for (int i = 0; i < WINNING_LINES.length; i++) {
           int first = getPosition(Matrix, WINNING_LINES[i][0]);
           int second = getPosition(Matrix, WINNING_LINES[i][1]);
           int third = getPosition(Matrix, WINNING_LINES[i][2]);

           if (first != EMPTY_CELL && first == second && second == third) {
               return first;
           }
       }

       return isBoardFull(Matrix) ? GAME_DRAW : GAME_CONTINUE;
    }
    
    public static int[][] MSetPosition(int[][] Matrix,int position,int value)
    {
        System.out.println("Class: KaataZero , Method : MSetPosition :Hi I am MSetPosition method");
        int row = rowFromPosition(position);
        int col = colFromPosition(position);

        if (row >= 0 && col >= 0) {
            Matrix[row][col] = value;
        }
        
        return Matrix;
    }

    public static int getPosition(int[][] Matrix, int position) {
        int row = rowFromPosition(position);
        int col = colFromPosition(position);

        if (row < 0 || col < 0) {
            return EMPTY_CELL;
        }

        return Matrix[row][col];
    }

    public static boolean isEmptyPosition(int[][] Matrix, int position) {
        return getPosition(Matrix, position) == EMPTY_CELL;
    }

    public static boolean isBoardFull(int[][] Matrix) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (Matrix[row][col] == EMPTY_CELL) {
                    return false;
                }
            }
        }

        return true;
    }

    public static int rowFromPosition(int position) {
        if (position < 0 || position >= BOARD_SIZE * BOARD_SIZE) {
            return -1;
        }

        return position % BOARD_SIZE;
    }

    public static int colFromPosition(int position) {
        if (position < 0 || position >= BOARD_SIZE * BOARD_SIZE) {
            return -1;
        }

        return position / BOARD_SIZE;
    }
    
}
