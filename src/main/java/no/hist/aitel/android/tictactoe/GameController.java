package no.hist.aitel.android.tictactoe;

public class GameController {

    private int n;
    private int moveCount;
    private GameState[][] board;

    public GameState[][] getBoard() {
        return board;
    }

    public void setBoard(GameState[][] board) {
        this.n = board.length;
        this.board = board;
    }

    public GameState move(int x, int y, GameState s) {
        if (board[x][y] == GameState.EMPTY) {
            board[x][y] = s;
        }
        moveCount++;
        //check end conditions
        //check col
        for (int i = 0; i < n; i++) {
            if (board[x][i] != s) {
                break;
            }
            if (i == n - 1) {
                //report win for s
                return board[x][i];
            }
        }
        //check row
        for (int i = 0; i < n; i++) {
            if (board[i][y] != s) {
                break;
            }
            if (i == n - 1) {
                //report win for s
                return board[i][y];
            }
        }
        //check diag
        if (x == y) {
            //we're on a diagonal
            for (int i = 0; i < n; i++) {
                if (board[i][i] != s) {
                    break;
                }
                if (i == n - 1) {
                    //report win for s
                    return board[i][i];
                }
            }
        }
        //check anti diag (thanks rampion)
        for (int i = 0; i < n; i++) {
            if (board[i][(n - 1) - i] != s) {
                break;
            }
            if (i == n - 1) {
                //report win for s
                return board[i][(n - 1) - i];
            }
        }
        //check draw
        if (moveCount == (n ^ 2 - 1)) {
            //report draw
            return GameState.DRAW;
        }
        return null;
    }

    public String toString() {
        return null;
    }
}

