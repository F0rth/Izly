package com.ezeeworld.b4s.android.sdk.ibeacon;

import com.ezeeworld.b4s.android.sdk.CalibrationConstants;

import java.util.Date;
import java.util.Locale;

public class Distance {
    private CalibrationConstants a;
    private double b;
    private long c;
    private double d = 0.0d;

    private Distance(CalibrationConstants calibrationConstants, double d) {
        this.a = calibrationConstants;
        this.c = System.currentTimeMillis();
        this.b = d;
    }

    private double a(double d) {
        double d2 = this.d + 0.25d;
        double d3 = (1.0d / (3.5d + d2)) * d2;
        this.b += (d - this.b) * d3;
        this.d = d2 * (1.0d - d3);
        return this.b;
    }

    public static Distance build(CalibrationConstants calibrationConstants, double d) {
        return new Distance(calibrationConstants, d);
    }

    public static double calculateDistance(CalibrationConstants calibrationConstants, double d) {
        return Math.pow(10.0d, (calibrationConstants.a - d) / (calibrationConstants.n * 10.0d));
    }

    public double getDistanceEstimate() {
        return calculateDistance(this.a, this.b);
    }

    public double getRssiEstimate() {
        return this.b;
    }

    public Date getTimeUpdated() {
        return new Date(this.c);
    }

    public boolean isFresh() {
        return this.c > System.currentTimeMillis() - 15000;
    }

    public double smooth(double d) {
        this.c = System.currentTimeMillis();
        this.b = a(d);
        return this.b;
    }

    public String toString() {
        return String.format(Locale.getDefault(), "%.2f", new Object[]{Double.valueOf(this.b)});
    }
}
