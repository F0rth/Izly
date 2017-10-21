package com.ezeeworld.b4s.android.sdk.positioning;

import android.util.Pair;

import java.util.Date;
import java.util.Map;

public final class PositioningUpdate {
    final double acceleration;
    final Map<String, Pair<Double, Long>> beaconDistances;
    final boolean highReliability;
    public final Position position;
    final float reliability;
    public final Date time = new Date();
    final boolean walking;
    public Zone zone;

    public PositioningUpdate(Position position, Map<String, Pair<Double, Long>> map, float f, boolean z, boolean z2, double d) {
        this.position = position;
        this.beaconDistances = map;
        this.reliability = f;
        this.highReliability = z;
        this.walking = z2;
        this.acceleration = d;
    }

    public final String toString() {
        return this.position.toString() + (this.highReliability ? " R" : "") + (this.walking ? " W" : "");
    }
}
