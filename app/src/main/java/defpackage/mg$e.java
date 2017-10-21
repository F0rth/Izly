package defpackage;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public final class mg$e extends BufferedOutputStream {
    private final CharsetEncoder a;

    public mg$e(OutputStream outputStream, String str, int i) {
        super(outputStream, i);
        this.a = Charset.forName(mg.c(str)).newEncoder();
    }

    public final mg$e a(String str) throws IOException {
        ByteBuffer encode = this.a.encode(CharBuffer.wrap(str));
        super.write(encode.array(), 0, encode.limit());
        return this;
    }
}
