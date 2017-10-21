package com.ezeeworld.b4s.android.sdk.positioning;

import java.util.Date;

public final class HistoricPosition extends Position {
    private final Date a;
    private final Zone b;

    public HistoricPosition(double d, double d2, Date date, Zone zone) {
        super(d, d2);
        this.a = date;
        this.b = zone;
    }
}
