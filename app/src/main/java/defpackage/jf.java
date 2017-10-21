package defpackage;

import android.text.TextUtils;
import java.io.File;

public final class jf {
    public static StringBuilder a = new StringBuilder();

    public static String a(String str) {
        return jf.a(str, true);
    }

    public static String a(String str, String str2, String str3) {
        a.setLength(0);
        if (jf.a(str, str2)) {
            if (!TextUtils.isEmpty(str)) {
                a.append(str);
                a.append(" ");
            }
            if (!TextUtils.isEmpty(str2)) {
                a.append(str2);
            }
        } else {
            a.append(jf.a(str3, true));
        }
        return a.toString();
    }

    public static String a(String str, boolean z) {
        if (str == null || !jf.b(str)) {
            return str;
        }
        r0 = str.length() == 11 ? "0" + str.substring(2) : str.length() > 11 ? "0" + str.substring(str.length() - 11) : "0" + str.substring(1);
        int length = r0.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i += 2) {
            stringBuilder.append(r0.charAt(i));
            if (i + 1 < length) {
                stringBuilder.append(r0.charAt(i + 1));
            }
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public static boolean a() {
        try {
            if (new File("/system/app/Superuser.apk").exists()) {
                return true;
            }
        } catch (Throwable th) {
        }
        return false;
    }

    public static boolean a(String str, String str2) {
        return (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) ? false : true;
    }

    public static boolean b(String str) {
        if (str.length() != 10 && str.length() != 11) {
            return false;
        }
        boolean z;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(Character.valueOf(str.charAt(i)).charValue())) {
                z = false;
                break;
            }
        }
        z = true;
        return z;
    }

    public static String c(String str) {
        return str.substring(0, 1).equals("0") ? "33" + str.substring(1) : str;
    }
}
