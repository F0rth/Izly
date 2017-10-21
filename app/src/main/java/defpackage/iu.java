package defpackage;

import java.util.Locale;

public final class iu {
    public static double a(String str) throws gd {
        try {
            double parseDouble = Double.parseDouble(iu.c(str));
            if (parseDouble > 0.0d) {
                return parseDouble;
            }
            throw new gd();
        } catch (NumberFormatException e) {
            throw new gd();
        }
    }

    public static String a(double d, String str) {
        return String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(d), str});
    }

    private static boolean a(double d) {
        return d > 0.0d;
    }

    public static boolean a(String str, double d, double d2) {
        try {
            double parseDouble = Double.parseDouble(iu.c(str));
            return iu.a(parseDouble) && parseDouble >= d && parseDouble <= d2;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean b(String str) {
        try {
            return iu.a(Double.parseDouble(iu.c(str)));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String c(String str) {
        String replace = str.replace(",", ".");
        int indexOf = str.indexOf(".");
        if (indexOf == -1) {
            return replace;
        }
        indexOf = 3 - (replace.length() - indexOf);
        if (indexOf <= 0) {
            return replace;
        }
        StringBuilder stringBuilder = new StringBuilder(replace);
        for (int i = 0; i < indexOf; i++) {
            stringBuilder.append("0");
        }
        return stringBuilder.toString();
    }
}
