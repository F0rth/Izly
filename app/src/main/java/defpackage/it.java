package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import fr.smoney.android.izly.R;

public final class it {
    public static int a(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static String a(String str, Context context) {
        if (str == null || str.equals("null")) {
            return "null";
        }
        String[] split = str.split("\\D");
        String str2 = "";
        int i = 0;
        while (i < split.length) {
            if (split[i].equals(context.getString(R.string.profilage_bancaire_1))) {
                str2 = str2 + context.getString(R.string.profilage_bancaire_BP);
            } else if (split[i].equals(context.getString(R.string.profilage_bancaire_2))) {
                str2 = str2 + context.getString(R.string.profilage_bancaire_CE);
            } else if (split[i].equals(context.getString(R.string.profilage_bancaire_3))) {
                str2 = str2 + context.getString(R.string.profilage_bancaire_OTHER);
            }
            if (i + 1 < split.length && i > 0 && (split[i].equals(context.getString(R.string.profilage_bancaire_1)) || split[i].equals(context.getString(R.string.profilage_bancaire_2)) || split[i].equals(context.getString(R.string.profilage_bancaire_3)))) {
                str2 = str2 + context.getString(R.string.profilage_bancaire_comma);
            }
            i++;
        }
        return str2;
    }

    public final int a(@NonNull Context context, int i) {
        int i2 = 1;
        int i3 = context.getResources().getDisplayMetrics().densityDpi;
        if (i3 >= 640) {
            i2 = 4;
        } else if (i3 >= 480) {
            i2 = 3;
        } else if (i3 >= 320) {
            i2 = 2;
        } else if (i3 >= 240) {
        }
        return i2 * i;
    }
}
