package defpackage;

import android.content.Context;

public final class kx {
    private final kg<String> a = new kx$1(this);
    private final ke<String> b = new ke();

    public final String a(Context context) {
        try {
            String str = (String) this.b.a(context, this.a);
            return "".equals(str) ? null : str;
        } catch (Throwable e) {
            js.a().c("Fabric", "Failed to determine installer package name", e);
            return null;
        }
    }
}
