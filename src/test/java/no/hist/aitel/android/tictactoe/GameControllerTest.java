package no.hist.aitel.android.tictactoe;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameControllerTest {

    private GameController controller;

    @Before
    public void setUp() {
        controller = new GameController();
    }

    private GameState[][] makeBoard(final int n, final int m) {
        final GameState[][] board = new GameState[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                board[i][j] = GameState.EMPTY;
            }
        }
        return board;
    }

    @Test
    public void testMove() {
        GameState[][] board = makeBoard(3, 3);
        controller.setBoard(board);
        controller.move(0, 0, GameState.PLAYER1);
        assertEquals(controller.getBoard()[0][0], GameState.PLAYER1);
    }
}
