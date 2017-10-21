package defpackage;

import java.io.IOException;
import java.io.InputStream;

final class ky$1 implements ky$c {
    boolean a = true;
    final /* synthetic */ StringBuilder b;
    final /* synthetic */ ky c;

    ky$1(ky kyVar, StringBuilder stringBuilder) {
        this.c = kyVar;
        this.b = stringBuilder;
    }

    public final void read(InputStream inputStream, int i) throws IOException {
        if (this.a) {
            this.a = false;
        } else {
            this.b.append(", ");
        }
        this.b.append(i);
    }
}
