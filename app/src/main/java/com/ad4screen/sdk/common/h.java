package com.ad4screen.sdk.common;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.b.m.f;
import com.ad4screen.sdk.d.b;
import com.ad4screen.sdk.service.modules.h.c;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public final class h {

    public enum a {
        DEFAULT,
        ISO8601
    }

    public static String a() {
        return new SimpleDateFormat(StdDateFormat.DATE_FORMAT_STR_ISO8601, Resources.getSystem().getConfiguration().locale).format(Calendar.getInstance().getTime());
    }

    public static String a(Context context, String str, boolean z, e... eVarArr) {
        if (str == null) {
            return null;
        }
        b a = b.a(context);
        c a2 = c.a(context);
        CharSequence charSequence = "portrait";
        if (f.a(context) == 2) {
            charSequence = "landscape";
        }
        String replace = str.replace("|id|", a.c() == null ? "" : a.c()).replace("|country|", Uri.encode(a.q())).replace("|lang|", Uri.encode(a.s())).replace("|pid|", a.l() == null ? "" : a.l()).replace("|o|", charSequence).replace("|uid|", Uri.encode(a2.b()));
        if (eVarArr != null) {
            int length = eVarArr.length;
            int i = 0;
            String str2 = replace;
            while (i < length) {
                e eVar = eVarArr[i];
                if (eVar.a != null) {
                    replace = str2.replace("|" + eVar.a + "|", eVar.b == null ? "" : eVar.b);
                } else {
                    replace = str2;
                }
                i++;
                str2 = replace;
            }
            replace = str2;
        }
        if (z) {
            replace = replace.replaceAll("\\|.*?\\|", "");
        }
        return replace.replaceAll("&amp;", "&");
    }

    public static String a(Context context, String str, e... eVarArr) {
        return a(context, str, true, eVarArr);
    }

    public static String a(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Throwable e) {
            Log.internal("Could not urlencode string \"" + str + "\"", e);
            return "";
        }
    }

    public static String a(String str, String... strArr) {
        if (strArr == null || strArr.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(strArr[0]);
        for (int i = 1; i < strArr.length; i++) {
            stringBuilder.append(str).append(strArr[i]);
        }
        return stringBuilder.toString();
    }

    public static String a(Date date, a aVar) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = aVar == a.ISO8601 ? new SimpleDateFormat(StdDateFormat.DATE_FORMAT_STR_ISO8601, Locale.US) : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(date);
    }

    private static String a(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bArr) {
            int i = (b >>> 4) & 15;
            int i2 = 0;
            while (true) {
                char c = (i < 0 || i > 9) ? (char) ((i - 10) + 97) : (char) (i + 48);
                stringBuilder.append(c);
                if (i2 > 0) {
                    break;
                }
                i = b & 15;
                i2++;
            }
        }
        return stringBuilder.toString();
    }

    public static String a(e... eVarArr) {
        StringBuilder stringBuilder = new StringBuilder();
        if (eVarArr != null && eVarArr.length > 0) {
            stringBuilder.append(eVarArr[0].a).append('=').append(a(eVarArr[0].b));
            for (int i = 1; i < eVarArr.length; i++) {
                stringBuilder.append('&').append(eVarArr[i].a).append('=').append(a(eVarArr[i].b));
            }
        }
        return stringBuilder.toString();
    }

    public static Date a(String str, a aVar) {
        Date date = null;
        if (str != null) {
            SimpleDateFormat simpleDateFormat;
            if (aVar == a.ISO8601) {
                if (str.endsWith("Z")) {
                    str = str.substring(0, str.length() - 1) + "+0000";
                }
                simpleDateFormat = new SimpleDateFormat(StdDateFormat.DATE_FORMAT_STR_ISO8601, Locale.US);
            } else {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US);
            }
            try {
                date = simpleDateFormat.parse(str);
            } catch (Throwable e) {
                if (aVar == a.ISO8601) {
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).parse(str);
                    } catch (ParseException e2) {
                        try {
                            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+", Locale.US).parse(str);
                        } catch (Throwable e3) {
                            Log.error("Could not parse date : " + str, e3);
                        }
                    }
                } else {
                    Log.error("Could not parse date : " + str, e3);
                }
            }
        }
        return date;
    }

    public static String b() {
        return UUID.randomUUID().toString();
    }

    public static String b(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            byte[] bytes = str.getBytes("UTF-8");
            instance.update(bytes, 0, bytes.length);
            return a(instance.digest());
        } catch (Throwable e) {
            Log.internal("No SHA-1 algorithm found", e);
            return null;
        } catch (Throwable e2) {
            Log.internal("Error while encoding string", e2);
            return null;
        }
    }
}
