package cs.agh.flappy;

public class Position {
    private final double x;
    private final double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Position() {
        this.x = 0;
        this.y = 0;
    }

    public Position(double x, double y) {
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
        return new Position(x + other.x, y + other.y);
    }

    public Position add(double x, double y) {
        return this.add(new Position(x, y));
    }

    public Position sub(double x, double y) {
        return this.sub(new Position(x, y));
    }

    public Position sub(Position other) {
        return new Position(x - other.x, y - other.y);
    }

    public boolean equals(Object other) {
        if (!(other instanceof Position))
            return false;
        Position that = (Position) other;
        if (x == that.x && y == that.y)
            return true;
        return false;
    }
}