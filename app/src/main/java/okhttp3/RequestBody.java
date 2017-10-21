package okhttp3;

import defpackage.nx;
import defpackage.nz;
import defpackage.og;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import okhttp3.internal.Util;

public abstract class RequestBody {
    public static RequestBody create(final MediaType mediaType, final File file) {
        if (file != null) {
            return new RequestBody() {
                public final long contentLength() {
                    return file.length();
                }

                public final MediaType contentType() {
                    return mediaType;
                }

                public final void writeTo(nx nxVar) throws IOException {
                    Closeable closeable = null;
                    try {
                        closeable = og.a(file);
                        nxVar.a(closeable);
                    } finally {
                        Util.closeQuietly(closeable);
                    }
                }
            };
        }
        throw new NullPointerException("content == null");
    }

    public static RequestBody create(MediaType mediaType, String str) {
        Charset charset = Util.UTF_8;
        if (mediaType != null) {
            charset = mediaType.charset();
            if (charset == null) {
                charset = Util.UTF_8;
                mediaType = MediaType.parse(mediaType + "; charset=utf-8");
            }
        }
        return create(mediaType, str.getBytes(charset));
    }

    public static RequestBody create(final MediaType mediaType, final nz nzVar) {
        return new RequestBody() {
            public final long contentLength() throws IOException {
                return (long) nzVar.e();
            }

            public final MediaType contentType() {
                return mediaType;
            }

            public final void writeTo(nx nxVar) throws IOException {
                nxVar.b(nzVar);
            }
        };
    }

    public static RequestBody create(MediaType mediaType, byte[] bArr) {
        return create(mediaType, bArr, 0, bArr.length);
    }

    public static RequestBody create(final MediaType mediaType, final byte[] bArr, final int i, final int i2) {
        if (bArr == null) {
            throw new NullPointerException("content == null");
        }
        Util.checkOffsetAndCount((long) bArr.length, (long) i, (long) i2);
        return new RequestBody() {
            public final long contentLength() {
                return (long) i2;
            }

            public final MediaType contentType() {
                return mediaType;
            }

            public final void writeTo(nx nxVar) throws IOException {
                nxVar.c(bArr, i, i2);
            }
        };
    }

    public long contentLength() throws IOException {
        return -1;
    }

    public abstract MediaType contentType();

    public abstract void writeTo(nx nxVar) throws IOException;
}
