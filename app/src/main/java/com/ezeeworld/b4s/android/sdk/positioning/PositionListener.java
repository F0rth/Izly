package com.ezeeworld.b4s.android.sdk.positioning;

public interface PositionListener {
    void onClearMap();

    void onPositionUpdate(PositioningUpdate positioningUpdate);

    void onZoneUpdated(UserZoneChange userZoneChange);
}
