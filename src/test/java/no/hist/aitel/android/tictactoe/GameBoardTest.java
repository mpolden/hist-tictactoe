package no.hist.aitel.android.tictactoe;

import org.junit.Test;

import static no.hist.aitel.android.tictactoe.GamePlayer.PLAYER1;
import static no.hist.aitel.android.tictactoe.GamePlayer.PLAYER2;
import static no.hist.aitel.android.tictactoe.GameState.*;
import static org.junit.Assert.assertEquals;

/**
 * Test case for GameBoard
 */
public class GameBoardTest {

    @Test
    public void testPut() {
        final GameBoard b = new GameBoard(3, 3);
        assertEquals(VALID_MOVE, b.put(0, 0, PLAYER1));
        assertEquals(PLAYER1, b.get(0, 0));
        assertEquals(INVALID_MOVE, b.put(0, 0, PLAYER2));
        assertEquals(PLAYER1, b.get(0, 0));
    }

    @Test
    public void testGame3x3() {
        final GameBoard b = new GameBoard(3, 3);
        assertEquals(VALID_MOVE, b.put(0, 0, PLAYER1));
        assertEquals(PLAYER1, b.get(0, 0));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(0, 1, PLAYER2));
        assertEquals(PLAYER2, b.get(0, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(1, 0, PLAYER1));
        assertEquals(PLAYER1, b.get(1, 0));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(1, 1, PLAYER2));
        assertEquals(PLAYER2, b.get(1, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(2, 0, PLAYER1));
        assertEquals(PLAYER1, b.get(2, 0));
        assertEquals(WIN, b.getState());
    }

    @Test
    public void testGame4x4() {
        final GameBoard b = new GameBoard(4, 4);
        assertEquals(VALID_MOVE, b.put(0, 1, PLAYER1));
        assertEquals(PLAYER1, b.get(0, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(2, 0, PLAYER2));
        assertEquals(PLAYER2, b.get(2, 0));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(1, 1, PLAYER1));
        assertEquals(PLAYER1, b.get(1, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(3, 0, PLAYER2));
        assertEquals(PLAYER2, b.get(3, 0));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(2, 1, PLAYER1));
        assertEquals(PLAYER1, b.get(2, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(3, 2, PLAYER2));
        assertEquals(PLAYER2, b.get(3, 2));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(3, 1, PLAYER1));
        assertEquals(PLAYER1, b.get(3, 1));
        assertEquals(WIN, b.getState());
    }

    @Test
    public void testGame5x5() {
        final GameBoard b = new GameBoard(5, 5);
        assertEquals(VALID_MOVE, b.put(4, 0, PLAYER2));
        assertEquals(PLAYER2, b.get(4, 0));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(2, 0, PLAYER1));
        assertEquals(PLAYER1, b.get(2, 0));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(1, 1, PLAYER2));
        assertEquals(PLAYER2, b.get(1, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(3, 0, PLAYER1));
        assertEquals(PLAYER1, b.get(3, 0));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(2, 1, PLAYER2));
        assertEquals(PLAYER2, b.get(2, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(3, 2, PLAYER1));
        assertEquals(PLAYER1, b.get(3, 2));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(3, 1, PLAYER2));
        assertEquals(PLAYER2, b.get(3, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(4, 2, PLAYER1));
        assertEquals(PLAYER1, b.get(4, 2));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(0, 1, PLAYER2));
        assertEquals(PLAYER2, b.get(0, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(2, 2, PLAYER1));
        assertEquals(PLAYER1, b.get(2, 2));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(4, 1, PLAYER2));
        assertEquals(PLAYER2, b.get(4, 1));
        assertEquals(WIN, b.getState());
    }

    @Test
    public void testGame6x6() {
        final GameBoard b = new GameBoard(6, 5);
        assertEquals(VALID_MOVE, b.put(3, 1, PLAYER2));
        assertEquals(PLAYER2, b.get(3, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(2, 0, PLAYER1));
        assertEquals(PLAYER1, b.get(2, 0));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(1, 1, PLAYER2));
        assertEquals(PLAYER2, b.get(1, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(3, 0, PLAYER1));
        assertEquals(PLAYER1, b.get(3, 0));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(2, 1, PLAYER2));
        assertEquals(PLAYER2, b.get(2, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(3, 2, PLAYER1));
        assertEquals(PLAYER1, b.get(3, 2));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(3, 3, PLAYER2));
        assertEquals(PLAYER2, b.get(3, 3));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(4, 2, PLAYER1));
        assertEquals(PLAYER1, b.get(4, 2));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(0, 1, PLAYER2));
        assertEquals(PLAYER2, b.get(0, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(2, 2, PLAYER1));
        assertEquals(PLAYER1, b.get(2, 2));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(1, 0, PLAYER2));
        assertEquals(PLAYER2, b.get(1, 0));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(5, 2, PLAYER1));
        assertEquals(PLAYER1, b.get(5, 2));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(0, 0, PLAYER2));
        assertEquals(PLAYER2, b.get(0, 0));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(1, 2, PLAYER1));
        assertEquals(PLAYER1, b.get(1, 2));
        assertEquals(WIN, b.getState());
    }

    @Test
    public void testGame7x7() {
        final GameBoard b = new GameBoard(7, 5);
        assertEquals(VALID_MOVE, b.put(3, 1, PLAYER2));
        assertEquals(PLAYER2, b.get(3, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(2, 0, PLAYER1));
        assertEquals(PLAYER1, b.get(2, 0));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(1, 1, PLAYER2));
        assertEquals(PLAYER2, b.get(1, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(3, 0, PLAYER1));
        assertEquals(PLAYER1, b.get(3, 0));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(2, 1, PLAYER2));
        assertEquals(PLAYER2, b.get(2, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(3, 2, PLAYER1));
        assertEquals(PLAYER1, b.get(3, 2));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(3, 3, PLAYER2));
        assertEquals(PLAYER2, b.get(3, 3));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(4, 2, PLAYER1));
        assertEquals(PLAYER1, b.get(4, 2));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(0, 1, PLAYER2));
        assertEquals(PLAYER2, b.get(0, 1));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(2, 2, PLAYER1));
        assertEquals(PLAYER1, b.get(2, 2));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(1, 0, PLAYER2));
        assertEquals(PLAYER2, b.get(1, 0));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(5, 2, PLAYER1));
        assertEquals(PLAYER1, b.get(5, 2));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(0, 0, PLAYER2));
        assertEquals(PLAYER2, b.get(0, 0));
        assertEquals(NEUTRAL, b.getState());
        assertEquals(VALID_MOVE, b.put(1, 2, PLAYER1));
        assertEquals(PLAYER1, b.get(1, 2));
        assertEquals(WIN, b.getState());
    }
}
