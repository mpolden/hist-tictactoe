package no.hist.aitel.android.tictactoe;

public enum GameState {
    UNKNOWN(-3),
    WIN(-2),
    EMPTY(0),
    PLAYER1(1),
    PLAYER2(2),
    DRAW(3);
    private int value;

    GameState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GameState fromInt(int i) {
        for (GameState s : values()) {
            if (s.getValue() == i) {
                return s;
            }
        }
        return EMPTY;
    }
}
