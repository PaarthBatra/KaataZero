/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaatazero;

/**
 * Core Tic-Tac-Toe board model and game-state helpers.
 * Stores the 3x3 matrix, maps flat positions to rows/columns, and detects wins and draws.
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
    public int[][] matrix = new int[BOARD_SIZE][BOARD_SIZE];

    public KaataZero(){
        reset();
    }

    public void reset(){
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                matrix[row][col] = EMPTY_CELL;
            }
        }
    }

    
    public static void printMatrix(int[][] matrix){
        for (int row = 0; row < BOARD_SIZE; row ++){
            for (int col = 0; col < BOARD_SIZE; col++)
                System.out.print(matrix[row][col] + " ");
            System.out.println();
        }
    }

    public static int findValue(int[][] matrix,int r,int c){
        return matrix[r][c];
    }
    
    public static int[][] setValue(int[][] matrix,int r,int c,int value)
    {
        matrix[r][c] = value;
        return matrix;
    }

    public static int checkGameStatus(int[][] matrix){
       return checkStatus(matrix);
    }

    public static int checkStatus(int[][] matrix) {
       int[] winningLine = findWinningLine(matrix);

       if (winningLine != null) {
           return getPosition(matrix, winningLine[0]);
       }

       return isBoardFull(matrix) ? GAME_DRAW : GAME_CONTINUE;
    }

    public static int[] findWinningLine(int[][] matrix) {
       for (int i = 0; i < WINNING_LINES.length; i++) {
           int first = getPosition(matrix, WINNING_LINES[i][0]);
           int second = getPosition(matrix, WINNING_LINES[i][1]);
           int third = getPosition(matrix, WINNING_LINES[i][2]);

           if (first != EMPTY_CELL && first == second && second == third) {
               return new int[] {WINNING_LINES[i][0], WINNING_LINES[i][1], WINNING_LINES[i][2]};
           }
       }

       return null;
    }
    
    public static int[][] setPosition(int[][] matrix,int position,int value)
    {
        int row = rowFromPosition(position);
        int col = colFromPosition(position);

        if (row >= 0 && col >= 0) {
            matrix[row][col] = value;
        }
        
        return matrix;
    }

    public static int getPosition(int[][] matrix, int position) {
        int row = rowFromPosition(position);
        int col = colFromPosition(position);

        if (row < 0 || col < 0) {
            return EMPTY_CELL;
        }

        return matrix[row][col];
    }

    public static boolean isEmptyPosition(int[][] matrix, int position) {
        return getPosition(matrix, position) == EMPTY_CELL;
    }

    public static boolean isBoardFull(int[][] matrix) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (matrix[row][col] == EMPTY_CELL) {
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

        return position / BOARD_SIZE;
    }

    public static int colFromPosition(int position) {
        if (position < 0 || position >= BOARD_SIZE * BOARD_SIZE) {
            return -1;
        }

        return position % BOARD_SIZE;
    }
    
}
