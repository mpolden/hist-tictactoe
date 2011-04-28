package no.hist.aitel.android.tictactoe;

import com.google.common.base.Strings;

/**
 * This class handles board logic
 */
public class GameBoard {

    private static final int MIN_BOARD_SIZE = 3;
    private static final int MIN_IN_ROW = 3;
    private int inRow;
    private int moveCount;
    private int x;
    private int y;
    private GamePlayer[][] board;
    private GamePlayer previousPlayer;
    private GamePlayer currentPlayer;

    /**
     * Create an empty board of the given size
     *
     * @param size  Board size
     * @param inRow Number of required squares in row
     */
    public GameBoard(final int size, final int inRow) {
        if (size < MIN_BOARD_SIZE) {
            throw new IllegalArgumentException(
                    String.format("Minimum board size is %d", MIN_BOARD_SIZE));
        }
        if (inRow < MIN_IN_ROW) {
            throw new IllegalArgumentException(
                    String.format("Minimum in row is %d", MIN_IN_ROW));
        }
        if (inRow > size) {
            throw new IllegalArgumentException("Number of in row can't be larger than board size");
        }
        this.board = new GamePlayer[size][size];
        this.inRow = inRow;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = GamePlayer.EMPTY;
            }
        }
    }

    public GamePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(GamePlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Set the position to the given previousPlayer
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param p Player to set
     * @return Game state
     */
    public GameState put(int x, int y, GamePlayer p) {
        if (x < 0 || x >= board.length) {
            throw new IllegalArgumentException(
                    String.format("x must in range(0,%d)", board.length - 1));
        }
        if (y < 0 || y >= board[x].length) {
            throw new IllegalArgumentException(
                    String.format("y must be in range(0,%d)", board[x].length - 1));
        }
        if (p == previousPlayer) {
            throw new IllegalArgumentException(
                    String.format("Player %s had previous move, can't move again", p));
        }
        if (board[x][y] == GamePlayer.EMPTY) {
            board[x][y] = p;
            this.x = x;
            this.y = y;
            this.previousPlayer = p;
            moveCount++;
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
        // Column
        for (int i = 0, n = 0; i < board.length; i++) {
            if (board[x][i] == previousPlayer) {
                n++;
            } else if (board[x][i] == GamePlayer.EMPTY) {
                n = 0;
            } else {
                break;
            }
            if (n == inRow) {
                return GameState.WIN;
            }
        }
        // Row
        for (int i = 0, n = 0; i < board.length; i++) {
            if (board[i][y] == previousPlayer) {
                n++;
            } else if (board[i][y] == GamePlayer.EMPTY) {
                n = 0;
            } else {
                break;
            }
            if (n == inRow) {
                return GameState.WIN;
            }
        }
        // Diagonal
        if (x == y) {
            for (int i = 0, n = 0; i < board.length; i++) {
                if (board[i][i] == previousPlayer) {
                    n++;
                } else if (board[i][i] == GamePlayer.EMPTY) {
                    n = 0;
                } else {
                    break;
                }
                if (n == inRow) {
                    return GameState.WIN;
                }
            }
        }
        // Reverse diagonal
        for (int i = 0, n = 0; i < board.length; i++) {
            if (board[i][(board.length - 1) - i] == previousPlayer) {
                n++;
            } else if (board[i][(board.length - 1) - i] == GamePlayer.EMPTY) {
                n = 0;
            }
            if (n == inRow) {
                return GameState.WIN;
            }
        }
        // Draw
        if (moveCount == Math.pow(board.length, 2) - 1) {
            return GameState.DRAW;
        }
        return GameState.NEUTRAL;
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

