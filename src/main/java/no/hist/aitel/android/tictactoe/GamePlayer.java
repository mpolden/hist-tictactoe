package no.hist.aitel.android.tictactoe;

public enum GamePlayer {
    UNKNOWN(-1),
    EMPTY(0),
    PLAYER1(1),
    PLAYER2(2);
    private final int value;

    GamePlayer(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
