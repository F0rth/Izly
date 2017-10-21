package com.ezeeworld.b4s.android.sdk.positioning;

public class Position {
    public double x;
    public double y;

    public Position(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public static boolean areColinear(Position position, Position position2, Position position3) {
        Position minus = position2.minus(position);
        Position minus2 = position3.minus(position);
        return (minus.x * minus2.y) - (minus2.x * minus.y) == 0.0d;
    }

    public double distanceTo(Position position) {
        return Math.sqrt(Math.pow(this.x - position.x, 2.0d) + Math.pow(this.y - position.y, 2.0d));
    }

    public double dot(Position position) {
        return (this.x * position.x) + (this.y * position.y);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position position = (Position) obj;
            if (this.x == position.x && this.y == position.y) {
                return true;
            }
        }
        return false;
    }

    public Position minus(Position position) {
        return new Position(this.x - position.x, this.y - position.y);
    }

    public Position plus(Position position) {
        return new Position(this.x + position.x, this.y + position.y);
    }

    public Position times(double d) {
        return new Position(this.x * d, this.y * d);
    }

    public Position times(Position position) {
        return new Position(this.x * position.x, this.y * position.y);
    }

    public String toString() {
        return this.x + "," + this.y;
    }
}
