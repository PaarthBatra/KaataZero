package kaatazero;

public class KaataZeroLogicTest {

    public static void main(String[] args) {
        testPositionMapping();
        testWinningLineDetection();
        testDrawDetection();
        testInvalidPositionsAreSafe();
        System.out.println("KaataZeroLogicTest passed");
    }

    private static void testPositionMapping() {
        assertEquals(0, KaataZero.rowFromPosition(0), "position 0 row");
        assertEquals(0, KaataZero.colFromPosition(0), "position 0 col");
        assertEquals(1, KaataZero.rowFromPosition(4), "position 4 row");
        assertEquals(1, KaataZero.colFromPosition(4), "position 4 col");
        assertEquals(2, KaataZero.rowFromPosition(8), "position 8 row");
        assertEquals(2, KaataZero.colFromPosition(8), "position 8 col");
    }

    private static void testWinningLineDetection() {
        KaataZero game = new KaataZero();
        KaataZero.setPosition(game.matrix, 0, KaataZero.PLAYER_X);
        KaataZero.setPosition(game.matrix, 1, KaataZero.PLAYER_X);
        KaataZero.setPosition(game.matrix, 2, KaataZero.PLAYER_X);

        assertEquals(KaataZero.PLAYER_X, KaataZero.checkGameStatus(game.matrix), "row win status");
        assertArrayEquals(new int[] {0, 1, 2}, KaataZero.findWinningLine(game.matrix), "row winning line");
    }

    private static void testDrawDetection() {
        KaataZero game = new KaataZero();
        int[] values = {
            KaataZero.PLAYER_X, KaataZero.PLAYER_O, KaataZero.PLAYER_X,
            KaataZero.PLAYER_X, KaataZero.PLAYER_O, KaataZero.PLAYER_O,
            KaataZero.PLAYER_O, KaataZero.PLAYER_X, KaataZero.PLAYER_X
        };

        for (int position = 0; position < values.length; position++) {
            KaataZero.setPosition(game.matrix, position, values[position]);
        }

        assertEquals(KaataZero.GAME_DRAW, KaataZero.checkGameStatus(game.matrix), "draw status");
    }

    private static void testInvalidPositionsAreSafe() {
        KaataZero game = new KaataZero();
        KaataZero.setPosition(game.matrix, -1, KaataZero.PLAYER_X);
        KaataZero.setPosition(game.matrix, 9, KaataZero.PLAYER_X);

        assertEquals(KaataZero.EMPTY_CELL, KaataZero.getPosition(game.matrix, -1), "negative position");
        assertEquals(KaataZero.EMPTY_CELL, KaataZero.getPosition(game.matrix, 9), "outside position");
        assertEquals(KaataZero.GAME_CONTINUE, KaataZero.checkGameStatus(game.matrix), "invalid writes ignored");
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + ": expected " + expected + " but got " + actual);
        }
    }

    private static void assertArrayEquals(int[] expected, int[] actual, String message) {
        if (actual == null || expected.length != actual.length) {
            throw new AssertionError(message + ": array length mismatch");
        }

        for (int i = 0; i < expected.length; i++) {
            if (expected[i] != actual[i]) {
                throw new AssertionError(message + ": expected " + expected[i] + " at index " + i + " but got " + actual[i]);
            }
        }
    }
}
