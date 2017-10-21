package defpackage;

import org.ksoap2.serialization.SoapObject;

public final class cp extends SoapObject {
    public String a;

    public cp(String str, String str2, String str3, String str4) {
        super(str, str2);
        this.a = str3;
        addProperty("version", str4);
        addProperty("channel", "AIZ");
        addProperty("format", "T");
        addProperty("model", "A");
    }
}
