package no.hist.aitel.android.tictactoe;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles board logic
 */
public class GameBoard {

    private static final int MIN_BOARD_SIZE = 3;
    private static final int MIN_IN_ROW = 3;
    private int moveCount;
    private final int lengthToWin;
    private final GamePlayer[][] board;
    private GamePlayer previousPlayer;
    private GamePlayer currentPlayer;

    /**
     * Create an empty board of the given size
     *
     * @param size        Board size
     * @param lengthToWin Number of required squares in row
     */
    public GameBoard(final int size, final int lengthToWin) {
        if (size < MIN_BOARD_SIZE) {
            throw new IllegalArgumentException(
                    String.format("Minimum board size is %d", MIN_BOARD_SIZE));
        }
        if (lengthToWin < MIN_IN_ROW) {
            throw new IllegalArgumentException(
                    String.format("Minimum length to win is %d", MIN_IN_ROW));
        }
        if (lengthToWin > size) {
            throw new IllegalArgumentException("Length to win can't be larger than board size");
        }
        this.board = new GamePlayer[size][size];
        this.lengthToWin = lengthToWin;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = GamePlayer.EMPTY;
            }
        }
    }

    /**
     * Get current player
     *
     * @return Current player
     */
    public GamePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Set current player
     *
     * @param currentPlayer Current player
     */
    public void setCurrentPlayer(GamePlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Get board
     *
     * @return Board
     */
    public GamePlayer[][] getBoard() {
        return board;
    }

    /**
     * Get the length required to win
     *
     * @return lengthToWin
     */
    public int getLengthToWin() {
        return lengthToWin;
    }

    /**
     * Set the position to the given previousPlayer
     *
     * @param x      X coordinate
     * @param y      Y coordinate
     * @param player Player to set
     * @return Game state
     */
    public GameState put(int x, int y, GamePlayer player) {
        if (x < 0 || x >= board.length) {
            throw new IllegalArgumentException(
                    String.format("x must in range(0,%d)", board.length - 1));
        }
        if (y < 0 || y >= board[x].length) {
            throw new IllegalArgumentException(
                    String.format("y must be in range(0,%d)", board[x].length - 1));
        }
        if (player == previousPlayer) {
            throw new IllegalArgumentException(
                    String.format("Player %s had previous move, can't move again", player));
        }
        if (board[x][y] == GamePlayer.EMPTY) {
            board[x][y] = player;
            moveCount++;
            this.previousPlayer = player;
            return GameState.VALID_MOVE;
        } else {
            return GameState.INVALID_MOVE;
        }
    }

    /**
     * Get previousPlayer of the current position
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return Player in the given position
     */
    public GamePlayer get(int x, int y) {
        if (x < 0 || x >= board.length) {
            throw new IllegalArgumentException(
                    String.format("x must be in range(0,%d)", board.length - 1));
        }
        if (y < 0 || y >= board[x].length) {
            throw new IllegalArgumentException(
                    String.format("y must be in range(0,%d)", board[x].length - 1));
        }
        return board[x][y];
    }

    /**
     * Get the current game state
     *
     * @return State of the game
     */
    public GameState getState() {
        for (int top = 0; top <= board.length - lengthToWin; ++top) {
            int bottom = top + lengthToWin - 1;
            for (int left = 0; left <= board.length - lengthToWin; ++left) {
                int right = left + lengthToWin - 1;
                // Check each row.
                nextRow:
                for (int row = top; row <= bottom; ++row) {
                    if (board[row][left] == GamePlayer.EMPTY) {
                        continue;
                    }
                    for (int col = left; col <= right; ++col) {
                        if (board[row][col] != board[row][left]) {
                            continue nextRow;
                        }
                    }
                    return GameState.WIN;
                }
                // Check each column.
                nextCol:
                for (int col = left; col <= right; ++col) {
                    if (board[top][col] == GamePlayer.EMPTY) {
                        continue;
                    }
                    for (int row = top; row <= bottom; ++row) {
                        if (board[row][col] != board[top][col]) {
                            continue nextCol;
                        }
                    }
                    return GameState.WIN;
                }
                // Check top-left to bottom-right diagonal.
                diag1:
                if (board[top][left] != GamePlayer.EMPTY) {
                    for (int i = 1; i < lengthToWin; ++i) {
                        if (board[top + i][left + i] != board[top][left]) {
                            break diag1;
                        }
                    }
                    return GameState.WIN;
                }
                // Check top-right to bottom-left diagonal.
                diag2:
                if (board[top][right] != GamePlayer.EMPTY) {
                    for (int i = 1; i < lengthToWin; ++i) {
                        if (board[top + i][right - i] != board[top][right]) {
                            break diag2;
                        }
                    }
                    return GameState.WIN;
                }
            }
        }
        if (moveCount == Math.pow(board.length, 2)) {
            return GameState.DRAW;
        } else {
            return GameState.NEUTRAL;
        }
    }

    /**
     * Find empty positions
     *
     * @return List of empty positions
     */
    public List<Position> findEmpty() {
        final List<Position> empty = new ArrayList<Position>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == GamePlayer.EMPTY) {
                    empty.add(new Position(i, j));
                }
            }
        }
        return empty;
    }

    /**
     * Format enums as traditional Tic-tac-toe symbols
     *
     * @param player Player to convert
     * @return Symbol representing previousPlayer 1, previousPlayer 2 or empty
     */
    private char playerToSymbol(GamePlayer player) {
        switch (player) {
            case PLAYER1: {
                return 'X';
            }
            case PLAYER2: {
                return 'O';
            }
            default: {
                return ' ';
            }
        }
    }

    /**
     * String representation of the board
     *
     * @return The board
     */
    @Override
    public String toString() {
        final int length = board.length;
        final int fieldWidth = 3;
        final String separator = Strings.repeat("+" + Strings.repeat("-", fieldWidth), length);
        final StringBuilder out = new StringBuilder();
        for (int i = 0; i < length; i++) {
            out.append(separator);
            out.append("+\n| ");
            for (int j = 0; j < length; j++) {
                final int last = length - 1;
                out.append(playerToSymbol(board[j][i]));
                if (j != last) {
                    out.append(" | ");
                } else {
                    out.append(" |\n");
                }
            }
        }
        out.append(separator);
        out.append('+');
        return out.toString();
    }
}

