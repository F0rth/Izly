package com.ezeeworld.b4s.android.sdk.positioning;

public final class UserZoneChange {
    public final boolean isEnterChange;
    public final Position triggerPosition;
    public final Zone triggerZone;

    UserZoneChange(boolean z, Zone zone, Position position) {
        this.isEnterChange = z;
        this.triggerZone = zone;
        this.triggerPosition = position;
    }
}
