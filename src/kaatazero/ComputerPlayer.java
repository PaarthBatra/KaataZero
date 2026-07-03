package kaatazero;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Selects computer moves for Easy, Medium, and Hard difficulty levels.
 * Easy is random, Medium mixes tactical and random play, and Hard uses minimax.
 */
public class ComputerPlayer {

    private static final int MEDIUM_TACTICAL_PERCENT = 65;

    private final Random random = new Random();

    public int chooseMove(int[][] matrix, String difficulty) {
        if ("Hard".equals(difficulty)) {
            return chooseHardMove(matrix);
        }

        if ("Medium".equals(difficulty)) {
            return chooseMediumMove(matrix);
        }

        return chooseRandomMove(matrix);
    }

    private int chooseRandomMove(int[][] matrix) {
        List<Integer> emptyPositions = new ArrayList<Integer>();
        for (int position = 0; position < KaataZero.BOARD_SIZE * KaataZero.BOARD_SIZE; position++) {
            if (KaataZero.isEmptyPosition(matrix, position)) {
                emptyPositions.add(Integer.valueOf(position));
            }
        }

        if (emptyPositions.isEmpty()) {
            return -1;
        }

        return emptyPositions.get(random.nextInt(emptyPositions.size())).intValue();
    }

    private int chooseMediumMove(int[][] matrix) {
        if (random.nextInt(100) >= MEDIUM_TACTICAL_PERCENT) {
            return chooseRandomMove(matrix);
        }

        int winningMove = findWinningMove(matrix, KaataZero.PLAYER_O);
        if (winningMove != -1) {
            return winningMove;
        }

        int blockingMove = findWinningMove(matrix, KaataZero.PLAYER_X);
        if (blockingMove != -1) {
            return blockingMove;
        }

        return chooseRandomMove(matrix);
    }

    private int chooseHardMove(int[][] matrix) {
        int bestScore = Integer.MIN_VALUE;
        int bestPosition = -1;

        for (int position = 0; position < KaataZero.BOARD_SIZE * KaataZero.BOARD_SIZE; position++) {
            if (KaataZero.isEmptyPosition(matrix, position)) {
                setPositionValue(matrix, position, KaataZero.PLAYER_O);
                int score = minimax(matrix, false, 0);
                setPositionValue(matrix, position, KaataZero.EMPTY_CELL);

                if (score > bestScore) {
                    bestScore = score;
                    bestPosition = position;
                }
            }
        }

        return bestPosition == -1 ? chooseRandomMove(matrix) : bestPosition;
    }

    private int findWinningMove(int[][] matrix, int player) {
        for (int position = 0; position < KaataZero.BOARD_SIZE * KaataZero.BOARD_SIZE; position++) {
            if (KaataZero.isEmptyPosition(matrix, position)) {
                setPositionValue(matrix, position, player);
                int result = KaataZero.checkStatus(matrix);
                setPositionValue(matrix, position, KaataZero.EMPTY_CELL);

                if (result == player) {
                    return position;
                }
            }
        }

        return -1;
    }

    private int minimax(int[][] matrix, boolean computerTurn, int depth) {
        int result = KaataZero.checkStatus(matrix);

        if (result == KaataZero.PLAYER_O) {
            return 10 - depth;
        }

        if (result == KaataZero.PLAYER_X) {
            return depth - 10;
        }

        if (result == KaataZero.GAME_DRAW) {
            return 0;
        }

        if (computerTurn) {
            int bestScore = Integer.MIN_VALUE;
            for (int position = 0; position < KaataZero.BOARD_SIZE * KaataZero.BOARD_SIZE; position++) {
                if (KaataZero.isEmptyPosition(matrix, position)) {
                    setPositionValue(matrix, position, KaataZero.PLAYER_O);
                    bestScore = Math.max(bestScore, minimax(matrix, false, depth + 1));
                    setPositionValue(matrix, position, KaataZero.EMPTY_CELL);
                }
            }
            return bestScore;
        }

        int bestScore = Integer.MAX_VALUE;
        for (int position = 0; position < KaataZero.BOARD_SIZE * KaataZero.BOARD_SIZE; position++) {
            if (KaataZero.isEmptyPosition(matrix, position)) {
                setPositionValue(matrix, position, KaataZero.PLAYER_X);
                bestScore = Math.min(bestScore, minimax(matrix, true, depth + 1));
                setPositionValue(matrix, position, KaataZero.EMPTY_CELL);
            }
        }
        return bestScore;
    }

    private void setPositionValue(int[][] matrix, int position, int value) {
        int row = KaataZero.rowFromPosition(position);
        int col = KaataZero.colFromPosition(position);

        if (row >= 0 && col >= 0) {
            matrix[row][col] = value;
        }
    }
}
