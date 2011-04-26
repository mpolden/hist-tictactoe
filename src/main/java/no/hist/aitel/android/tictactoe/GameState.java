package no.hist.aitel.android.tictactoe;

public enum GameState {
    WIN(1),
    DRAW(2),
    NEUTRAL(3),
    VALID_MOVE(4),
    INVALID_MOVE(5);
    private int value;

    GameState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
