package defpackage;

import java.util.Formatter;

public final class gk {
    public static String a(byte[] bArr) {
        Formatter formatter = new Formatter();
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            formatter.format("%02x", new Object[]{Byte.valueOf(bArr[i])});
        }
        return formatter.toString();
    }
}
