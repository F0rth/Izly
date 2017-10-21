package com.ezeeworld.b4s.android.sdk.positioning;

public final class Zone {
    public final double bottom;
    public final String id;
    public final double left;
    public final double right;
    public final double top;

    public Zone(String str, double d, double d2, double d3, double d4) {
        this.id = str;
        this.left = d;
        this.right = d2;
        this.top = d3;
        this.bottom = d4;
    }

    public final boolean contains(Position position) {
        return position.x >= Math.min(this.left, this.right) && position.x <= Math.max(this.left, this.right) && position.y >= Math.min(this.bottom, this.top) && position.y <= Math.max(this.bottom, this.top);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Zone) {
            Zone zone = (Zone) obj;
            if (((this.id == null && zone.id == null) || (this.id != null && this.id.equals(zone.id))) && this.left == zone.left && this.right == zone.right && this.top == zone.top && this.bottom == zone.bottom) {
                return true;
            }
        }
        return false;
    }

    public final String toString() {
        return this.id;
    }
}
