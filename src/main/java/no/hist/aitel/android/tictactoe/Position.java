package no.hist.aitel.android.tictactoe;

public class Position {

    private int x;
    private int y;

    /**
     * Keeps track of x and y coordinates of a position
     * @param x
     * @param y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x coordinate
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        if (y != position.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
