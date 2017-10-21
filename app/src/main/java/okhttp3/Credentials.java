package okhttp3;

import defpackage.nz;
import java.io.UnsupportedEncodingException;
import org.spongycastle.i18n.LocalizedMessage;

public final class Credentials {
    private Credentials() {
    }

    public static String basic(String str, String str2) {
        try {
            return "Basic " + nz.a((str + ":" + str2).getBytes(LocalizedMessage.DEFAULT_ENCODING)).b();
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }
}
