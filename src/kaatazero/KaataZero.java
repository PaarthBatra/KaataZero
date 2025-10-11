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
    private static final int BOARD_SIZE = 3;
    private static final int EMPTY_CELL = 9;
    private static final int PLAYER_O = 0;
    private static final int PLAYER_X = 1;
    private static final int GAME_CONTINUE = 9;
    private static final int GAME_DRAW = 999;

    /**
     * @param args the command line arguments
     */
    public int[][] Matrix = new int[BOARD_SIZE][BOARD_SIZE];
    public KaataZero(callGUI gui){
        //int[][] Matrix = new int[3][3];
        int[][] Result;
        //initializing matrix
        for (int row = 0; row < BOARD_SIZE; row ++)
            for (int col = 0; col < BOARD_SIZE; col++)
                Matrix[row][col] = EMPTY_CELL;
        System.out.println("KaataZero Class Initiated");
    }
    public void MReinitialize(){
        for (int row = 0; row < BOARD_SIZE; row ++)
            for (int col = 0; col < BOARD_SIZE; col++)
                Matrix[row][col] = EMPTY_CELL;
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
        int returnValue;
        int value;
        
        System.out.println("Class: KaataZero , MFindValue : Started");
        value = Matrix[r][c];
        if (value == EMPTY_CELL)
            returnValue=value;
        else
            returnValue=value;
        
        return returnValue;
    }
    
    public static int[][] MSet(int[][] Matrix,int r,int c,int value)
    {
        int[][] FinalM = new int[3][3];
        System.out.println("Class: KaataZero , Method : MSet :Hi I am MSet method");
        FinalM = Matrix;
        Matrix[r][c] = value;
        return FinalM;
    }
    public static int MSuccess(int[][] Matrix){
        //0 computer won
        //1 player 1
        //999 match drawn
        // 9 all ok
       System.out.println("Class: KaataZero , Method : MSuccess");
       int returnValue;
       if( Matrix[0][0] == PLAYER_O && Matrix[0][1] == PLAYER_O && Matrix[0][2] == PLAYER_O
               ||
            Matrix[1][0] == PLAYER_O && Matrix[1][1] == PLAYER_O && Matrix[1][2] == PLAYER_O   
              ||
            Matrix[2][0] == PLAYER_O && Matrix[2][1] == PLAYER_O && Matrix[2][2] == PLAYER_O  
              ||
            Matrix[0][0] == PLAYER_O && Matrix[1][0] == PLAYER_O && Matrix[2][0] == PLAYER_O  
               ||
            Matrix[0][1] == PLAYER_O && Matrix[1][1] == PLAYER_O && Matrix[2][1] == PLAYER_O  
              ||
            Matrix[0][2] == PLAYER_O && Matrix[1][2] == PLAYER_O && Matrix[2][2] == PLAYER_O  
               ||
            Matrix[0][0] == PLAYER_O && Matrix[1][1] == PLAYER_O && Matrix[2][2] == PLAYER_O  
              ||
            Matrix[0][2] == PLAYER_O && Matrix[1][1] == PLAYER_O && Matrix[2][0] == PLAYER_O    
              
              
          )
       {
       returnValue = PLAYER_O;    
       }
       else if( Matrix[0][0] == PLAYER_X && Matrix[0][1] == PLAYER_X && Matrix[0][2] == PLAYER_X
               ||
            Matrix[1][0] == PLAYER_X && Matrix[1][1] == PLAYER_X && Matrix[1][2] == PLAYER_X   
              ||
            Matrix[2][0] == PLAYER_X && Matrix[2][1] == PLAYER_X && Matrix[2][2] == PLAYER_X  
              ||
            Matrix[0][0] == PLAYER_X && Matrix[1][0] == PLAYER_X && Matrix[2][0] == PLAYER_X  
               ||
            Matrix[0][1] == PLAYER_X && Matrix[1][1] == PLAYER_X && Matrix[2][1] == PLAYER_X  
              ||
            Matrix[0][2] == PLAYER_X && Matrix[1][2] == PLAYER_X && Matrix[2][2] == PLAYER_X  
               ||
            Matrix[0][0] == PLAYER_X && Matrix[1][1] == PLAYER_X && Matrix[2][2] == PLAYER_X  
              ||
            Matrix[0][2] == PLAYER_X && Matrix[1][1] == PLAYER_X && Matrix[2][0] == PLAYER_X  
          )
       {
       returnValue = PLAYER_X;    
       }
       else if (Matrix[0][0] != EMPTY_CELL && Matrix[1][0] != EMPTY_CELL && Matrix[2][0] != EMPTY_CELL
                && Matrix[0][1] != EMPTY_CELL && Matrix[1][1] != EMPTY_CELL && Matrix[2][1] != EMPTY_CELL
                && Matrix[0][2] != EMPTY_CELL && Matrix[1][2] != EMPTY_CELL && Matrix[2][2] != EMPTY_CELL)
       {
           
           returnValue=GAME_DRAW;
           
       }
       else returnValue = GAME_CONTINUE;
       return returnValue;
    }
    
    public static int[][] MSetPosition(int[][] Matrix,int position,int value)
    {
        int[][] FinalM = new int[3][3];
        System.out.println("Class: KaataZero , Method : MSetPosition :Hi I am MSetPosition method");
        FinalM = Matrix;
        //position = 5;
        switch (position){
        case 0 : 
            Matrix[0][0] = value;
            break;
        case 1: 
            Matrix[1][0] = value;
            break;
        case 2: 
            Matrix[2][0] = value;
            break;
        case 3: 
            Matrix[0][1] = value;
            break;
        case 4: 
            Matrix[1][1] = value;
            break;
        case 5: 
            Matrix[2][1] = value;
            break;
        case 6: 
            Matrix[0][2] = value;
            break;
        case 7: 
            Matrix[1][2] = value;
            break;
        case 8: 
            Matrix[2][2] = value;
            break;
        }
        
        return FinalM;
    }
    
}
