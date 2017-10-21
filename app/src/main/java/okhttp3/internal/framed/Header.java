package okhttp3.internal.framed;

import defpackage.nz;
import okhttp3.internal.Util;

public final class Header {
    public static final nz RESPONSE_STATUS = nz.a(":status");
    public static final nz TARGET_AUTHORITY = nz.a(":authority");
    public static final nz TARGET_HOST = nz.a(":host");
    public static final nz TARGET_METHOD = nz.a(":method");
    public static final nz TARGET_PATH = nz.a(":path");
    public static final nz TARGET_SCHEME = nz.a(":scheme");
    public static final nz VERSION = nz.a(":version");
    final int hpackSize;
    public final nz name;
    public final nz value;

    public Header(String str, String str2) {
        this(nz.a(str), nz.a(str2));
    }

    public Header(nz nzVar, String str) {
        this(nzVar, nz.a(str));
    }

    public Header(nz nzVar, nz nzVar2) {
        this.name = nzVar;
        this.value = nzVar2;
        this.hpackSize = (nzVar.e() + 32) + nzVar2.e();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Header)) {
            return false;
        }
        Header header = (Header) obj;
        return this.name.equals(header.name) && this.value.equals(header.value);
    }

    public final int hashCode() {
        return ((this.name.hashCode() + 527) * 31) + this.value.hashCode();
    }

    public final String toString() {
        return Util.format("%s: %s", this.name.a(), this.value.a());
    }
}
