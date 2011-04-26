package no.hist.aitel.android.tictactoe;

public class GameController {

    private int inRow;
    private int moveCount;
    private GameState[][] board;

    public GameState[][] getBoard() {
        return board;
    }

    public void setBoard(GameState[][] board) {
        this.board = board;
    }

    public void setInRow(int inRow) {
        this.inRow = inRow;
    }

    public GameState move(int x, int y, GameState s) {
        if (board[x][y] == GameState.EMPTY) {
            board[x][y] = s;
        }
        moveCount++;
        //check end conditions
        //check col
        for (int i = 0; i < inRow; i++) {
            if (board[x][i] != s) {
                break;
            }
            if (i == inRow - 1) {
                //report win for s
                return board[x][i];
            }
        }
        //check row
        for (int i = 0; i < inRow; i++) {
            if (board[i][y] != s) {
                break;
            }
            if (i == inRow - 1) {
                //report win for s
                return board[i][y];
            }
        }
        //check diag
        if (x == y) {
            //we're on a diagonal
            for (int i = 0; i < inRow; i++) {
                if (board[i][i] != s) {
                    break;
                }
                if (i == inRow - 1) {
                    //report win for s
                    return board[i][i];
                }
            }
        }
        //check anti diag (thanks rampion)
        for (int i = 0; i < inRow; i++) {
            if (board[i][(inRow - 1) - i] != s) {
                break;
            }
            if (i == inRow - 1) {
                //report win for s
                return board[i][(inRow - 1) - i];
            }
        }
        //check draw
        if (moveCount == (inRow ^ 2 - 1)) {
            //report draw
            return GameState.DRAW;
        }
        return null;
    }

    public String toString() {
        return null;
    }
}

