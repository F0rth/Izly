package okhttp3;

import defpackage.nw;
import defpackage.ny;
import defpackage.op;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import okhttp3.internal.Util;

public abstract class ResponseBody implements Closeable {
    private Reader reader;

    private Charset charset() {
        MediaType contentType = contentType();
        return contentType != null ? contentType.charset(Util.UTF_8) : Util.UTF_8;
    }

    public static ResponseBody create(final MediaType mediaType, final long j, final ny nyVar) {
        if (nyVar != null) {
            return new ResponseBody() {
                public final long contentLength() {
                    return j;
                }

                public final MediaType contentType() {
                    return mediaType;
                }

                public final ny source() {
                    return nyVar;
                }
            };
        }
        throw new NullPointerException("source == null");
    }

    public static ResponseBody create(MediaType mediaType, String str) {
        Charset charset = Util.UTF_8;
        if (mediaType != null) {
            charset = mediaType.charset();
            if (charset == null) {
                charset = Util.UTF_8;
                mediaType = MediaType.parse(mediaType + "; charset=utf-8");
            }
        }
        nw nwVar = new nw();
        int length = str.length();
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        } else if (length < 0) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + length + " < 0");
        } else if (length > str.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + length + " > " + str.length());
        } else if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else {
            ny a;
            if (charset.equals(op.a)) {
                a = nwVar.a(str);
            } else {
                byte[] bytes = str.substring(0, length).getBytes(charset);
                a = nwVar.b(bytes, 0, bytes.length);
            }
            return create(mediaType, a.b, a);
        }
    }

    public static ResponseBody create(MediaType mediaType, byte[] bArr) {
        return create(mediaType, (long) bArr.length, new nw().a(bArr));
    }

    public final InputStream byteStream() {
        return source().d();
    }

    public final byte[] bytes() throws IOException {
        long contentLength = contentLength();
        if (contentLength > 2147483647L) {
            throw new IOException("Cannot buffer entire body for content length: " + contentLength);
        }
        Closeable source = source();
        try {
            byte[] p = source.p();
            if (contentLength == -1 || contentLength == ((long) p.length)) {
                return p;
            }
            throw new IOException("Content-Length and stream length disagree");
        } finally {
            Util.closeQuietly(source);
        }
    }

    public final Reader charStream() {
        Reader reader = this.reader;
        if (reader != null) {
            return reader;
        }
        reader = new InputStreamReader(byteStream(), charset());
        this.reader = reader;
        return reader;
    }

    public void close() {
        Util.closeQuietly(source());
    }

    public abstract long contentLength();

    public abstract MediaType contentType();

    public abstract ny source();

    public final String string() throws IOException {
        return new String(bytes(), charset().name());
    }
}
