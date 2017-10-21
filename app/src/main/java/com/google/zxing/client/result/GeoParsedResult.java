package com.google.zxing.client.result;

public final class GeoParsedResult extends ParsedResult {
    private final double altitude;
    private final double latitude;
    private final double longitude;
    private final String query;

    GeoParsedResult(double d, double d2, double d3, String str) {
        super(ParsedResultType.GEO);
        this.latitude = d;
        this.longitude = d2;
        this.altitude = d3;
        this.query = str;
    }

    public final double getAltitude() {
        return this.altitude;
    }

    public final String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(20);
        stringBuilder.append(this.latitude);
        stringBuilder.append(", ");
        stringBuilder.append(this.longitude);
        if (this.altitude > 0.0d) {
            stringBuilder.append(", ");
            stringBuilder.append(this.altitude);
            stringBuilder.append('m');
        }
        if (this.query != null) {
            stringBuilder.append(" (");
            stringBuilder.append(this.query);
            stringBuilder.append(')');
        }
        return stringBuilder.toString();
    }

    public final String getGeoURI() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("geo:");
        stringBuilder.append(this.latitude);
        stringBuilder.append(',');
        stringBuilder.append(this.longitude);
        if (this.altitude > 0.0d) {
            stringBuilder.append(',');
            stringBuilder.append(this.altitude);
        }
        if (this.query != null) {
            stringBuilder.append('?');
            stringBuilder.append(this.query);
        }
        return stringBuilder.toString();
    }

    public final double getLatitude() {
        return this.latitude;
    }

    public final double getLongitude() {
        return this.longitude;
    }

    public final String getQuery() {
        return this.query;
    }
}
