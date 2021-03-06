package cs.agh.flappy;

public class Position {
    private final int x;
    private final int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position() {
        this.x = 0;
        this.y = 0;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position other) {
        this.x = other.x;
        this.y = other.y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean smaller(Position other) {
        return x <= other.x && y <= other.y;
    }

    public boolean larger(Position other) {
        return x >= other.x && y >= other.y;
    }

    public Position upperRight(Position other) {
        return new Position(Math.max(other.x, x), Math.max(other.y, y));
    }

    public Position lowerLeft(Position other) {
        return new Position(Math.min(x, other.x), Math.min(y, other.y));
    }

    public Position add(Position other) {
        return add(other.getX(), other.getY());
    }

    public Position add(int x, int y) {
        return new Position(x + this.x, y + this.y);
    }

    public Position sub(Position other) {
        return sub(other.getX(), other.getY());
    }

    public Position sub(int x, int y) {
        return new Position(this.x - x, this.y - y);
    }

    public boolean equals(Object other) {
        if (!(other instanceof Position))
            return false;
        Position that = (Position) other;
        return x == that.x && y == that.y;
    }
}