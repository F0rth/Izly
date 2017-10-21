package defpackage;

import org.spongycastle.asn1.x509.DisplayText;

public final class kz {
    public static int a(int i) {
        if (i < DisplayText.DISPLAY_TEXT_MAXIMUM_SIZE || i > 299) {
            if (i >= 300 && i <= 399) {
                return 1;
            }
            if (i < 400 || i > 499) {
                return i >= 500 ? 1 : 1;
            }
        }
        return 0;
    }
}
