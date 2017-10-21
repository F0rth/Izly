package defpackage;

import android.content.Context;
import fr.smoney.android.izly.R;

public final class jm {
    public static String a(Context context, double d) {
        if (d < 1000.0d) {
            return String.format("%.0f", new Object[]{Double.valueOf(d)}) + " " + context.getString(R.string.wingit_common_label_meter);
        }
        return String.format("%.0f", new Object[]{Double.valueOf(d / 1000.0d)}) + " " + context.getString(R.string.wingit_common_label_kilometer);
    }

    public static String b(Context context, double d) {
        if (d * 1.09361d < 1760.0d) {
            if (((double) ((int) Math.ceil(d * 1.09361d))) > 1.0d) {
                return String.format("%.0f", new Object[]{Double.valueOf(r0)}) + " " + context.getString(R.string.wingit_common_label_yards);
            }
            return String.format("%.0f", new Object[]{Double.valueOf(r0)}) + " " + context.getString(R.string.wingit_common_label_yard);
        }
        if (((float) (6.21371E-4d * d)) == 1.0f) {
            return String.format("%.0f", new Object[]{Float.valueOf(r0)}) + " " + context.getString(R.string.wingit_common_label_mile);
        }
        return String.format("%.0f", new Object[]{Float.valueOf(r0)}) + " " + context.getString(R.string.wingit_common_label_miles);
    }
}
