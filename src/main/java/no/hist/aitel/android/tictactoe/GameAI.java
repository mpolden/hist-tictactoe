package no.hist.aitel.android.tictactoe;

import java.util.List;
import java.util.Random;

public class GameAI {

    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;

    private final Random random;
    private final GameBoard gameBoard;
    private final int difficulty;

    /**
     * An artificial intelligence that handles the phone moves in single player
     * @param gameBoard
     * @param difficulty
     */
    public GameAI(GameBoard gameBoard, int difficulty) {
        this.random = new Random();
        this.gameBoard = gameBoard;
        this.difficulty = difficulty;
    }

    /**
     * Gets the AI's next move as a Position object
     * @return AI's next move
     */
    public Position getMove() {
        switch (difficulty) {
            case EASY: {
                final List<Position> emptyPositions = gameBoard.findEmpty();
                return emptyPositions.get(random.nextInt(emptyPositions.size()));
            }
            default: {
                return null;
            }
        }
    }

}
