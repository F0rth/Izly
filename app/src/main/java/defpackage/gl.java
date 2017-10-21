package defpackage;

import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import com.google.zxing.BarcodeFormat;
import java.util.Collection;
import java.util.HashSet;
import org.spongycastle.asn1.cmp.PKIFailureInfo;

public final class gl {
    public int a = PKIFailureInfo.systemUnavail;
    public String b = null;
    public BarcodeFormat c = null;
    public boolean d = false;
    private String e = null;
    private String f = null;

    public gl(String str, Bundle bundle, String str2, String str3, int i) {
        this.a = i;
        this.d = a(str, null, str2, str3);
    }

    private static String a(String str) {
        if (str != null) {
            String trim = str.trim();
            if (trim.length() != 0) {
                return trim;
            }
        }
        return null;
    }

    private boolean a(String str, Bundle bundle, String str2, String str3) {
        this.c = null;
        if (str3 != null) {
            try {
                this.c = BarcodeFormat.valueOf(str3);
            } catch (IllegalArgumentException e) {
            }
        }
        if (this.c == null || this.c == BarcodeFormat.QR_CODE) {
            this.c = BarcodeFormat.QR_CODE;
            if (str2.equals("TEXT_TYPE")) {
                if (str != null && str.length() > 0) {
                    this.b = str;
                    this.e = str;
                    this.f = "Text";
                }
            } else if (str2.equals("EMAIL_TYPE")) {
                r0 = gl.a(str);
                if (r0 != null) {
                    this.b = "mailto:" + r0;
                    this.e = r0;
                    this.f = "E-Mail";
                }
            } else if (str2.equals("PHONE_TYPE")) {
                r0 = gl.a(str);
                if (r0 != null) {
                    this.b = "tel:" + r0;
                    this.e = PhoneNumberUtils.formatNumber(r0);
                    this.f = "Phone";
                }
            } else if (str2.equals("SMS_TYPE")) {
                r0 = gl.a(str);
                if (r0 != null) {
                    this.b = "sms:" + r0;
                    this.e = PhoneNumberUtils.formatNumber(r0);
                    this.f = "SMS";
                }
            } else if (str2.equals("CONTACT_TYPE")) {
                if (bundle != null) {
                    String a;
                    StringBuilder stringBuilder = new StringBuilder(100);
                    StringBuilder stringBuilder2 = new StringBuilder(100);
                    stringBuilder.append("MECARD:");
                    r0 = gl.a(bundle.getString("name"));
                    if (r0 != null) {
                        stringBuilder.append("N:").append(gl.b(r0)).append(';');
                        stringBuilder2.append(r0);
                    }
                    r0 = gl.a(bundle.getString("postal"));
                    if (r0 != null) {
                        stringBuilder.append("ADR:").append(gl.b(r0)).append(';');
                        stringBuilder2.append('\n').append(r0);
                    }
                    Collection<String> hashSet = new HashSet(gj.a.length);
                    for (String a2 : gj.a) {
                        a2 = gl.a(bundle.getString(a2));
                        if (a2 != null) {
                            hashSet.add(a2);
                        }
                    }
                    for (String str4 : hashSet) {
                        stringBuilder.append("TEL:").append(gl.b(str4)).append(';');
                        stringBuilder2.append('\n').append(PhoneNumberUtils.formatNumber(str4));
                    }
                    hashSet = new HashSet(gj.c.length);
                    for (String a22 : gj.c) {
                        a22 = gl.a(bundle.getString(a22));
                        if (a22 != null) {
                            hashSet.add(a22);
                        }
                    }
                    for (String str42 : hashSet) {
                        stringBuilder.append("EMAIL:").append(gl.b(str42)).append(';');
                        stringBuilder2.append('\n').append(str42);
                    }
                    str42 = gl.a(bundle.getString("URL_KEY"));
                    if (str42 != null) {
                        stringBuilder.append("URL:").append(str42).append(';');
                        stringBuilder2.append('\n').append(str42);
                    }
                    str42 = gl.a(bundle.getString("NOTE_KEY"));
                    if (str42 != null) {
                        stringBuilder.append("NOTE:").append(gl.b(str42)).append(';');
                        stringBuilder2.append('\n').append(str42);
                    }
                    if (stringBuilder2.length() > 0) {
                        stringBuilder.append(';');
                        this.b = stringBuilder.toString();
                        this.e = stringBuilder2.toString();
                        this.f = "Contact";
                    } else {
                        this.b = null;
                        this.e = null;
                    }
                }
            } else if (str2.equals("LOCATION_TYPE") && bundle != null) {
                float f = bundle.getFloat("LAT", Float.MAX_VALUE);
                float f2 = bundle.getFloat("LONG", Float.MAX_VALUE);
                if (!(f == Float.MAX_VALUE || f2 == Float.MAX_VALUE)) {
                    this.b = "geo:" + f + ',' + f2;
                    this.e = f + "," + f2;
                    this.f = "Location";
                }
            }
        } else if (str != null && str.length() > 0) {
            this.b = str;
            this.e = str;
            this.f = "Text";
        }
        return this.b != null && this.b.length() > 0;
    }

    private static String b(String str) {
        if (str == null) {
            return str;
        }
        if (str.indexOf(58) < 0 && str.indexOf(59) < 0) {
            return str;
        }
        int length = str.length();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt == ':' || charAt == ';') {
                stringBuilder.append('\\');
            }
            stringBuilder.append(charAt);
        }
        return stringBuilder.toString();
    }
}
