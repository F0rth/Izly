package defpackage;

import java.util.regex.Pattern;

public final class ij extends ik {
    private Pattern b;
    private boolean c;

    public ij(String str, Pattern pattern, boolean z) {
        super(str);
        if (pattern == null) {
            throw new IllegalArgumentException("pattern must not be null");
        }
        this.b = pattern;
        this.c = z;
    }

    public final boolean b(String str) {
        return this.c ? !this.b.matcher(str).matches() : this.b.matcher(str).matches();
    }
}
