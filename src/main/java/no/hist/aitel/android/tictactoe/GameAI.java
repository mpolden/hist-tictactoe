package no.hist.aitel.android.tictactoe;


import java.util.Random;

public class GameAI {
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;

    private Random random = new Random();
    private GameBoard gameBoard;
    private int difficulty;
    private int boardSize;
    private int lengthToWin;

    public GameAI(GameBoard gameBoard, int difficulty) {
        this.gameBoard = gameBoard;
        this.difficulty = difficulty;
        boardSize = gameBoard.getBoard().length;
        lengthToWin = gameBoard.getLengthToWin();
    }

    public int[] getMove() {
        int[] move = new int[2];
        switch (difficulty) {
            case EASY: {
                
            }
        }
        return move;
    }



}
