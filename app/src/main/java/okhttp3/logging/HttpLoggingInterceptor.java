package okhttp3.logging;

import defpackage.nw;
import defpackage.ny;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;
import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Platform;
import okhttp3.internal.http.HttpEngine;
import org.spongycastle.asn1.eac.CertificateBody;

public final class HttpLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private volatile Level level;
    private final Logger logger;

    public enum Level {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    public interface Logger {
        public static final Logger DEFAULT = new Logger() {
            public final void log(String str) {
                Platform.get().log(4, str, null);
            }
        };

        void log(String str);
    }

    public HttpLoggingInterceptor() {
        this(Logger.DEFAULT);
    }

    public HttpLoggingInterceptor(Logger logger) {
        this.level = Level.NONE;
        this.logger = logger;
    }

    private boolean bodyEncoded(Headers headers) {
        String str = headers.get("Content-Encoding");
        return (str == null || str.equalsIgnoreCase("identity")) ? false : true;
    }

    static boolean isPlaintext(nw nwVar) throws EOFException {
        try {
            nw nwVar2 = new nw();
            nwVar.a(nwVar2, 0, nwVar.b < 64 ? nwVar.b : 64);
            for (int i = 0; i < 16 && !nwVar2.c(); i++) {
                if (nwVar2.b == 0) {
                    throw new EOFException();
                }
                int i2;
                int i3;
                int i4;
                byte c = nwVar2.c(0);
                if ((c & 128) == 0) {
                    i2 = 0;
                    i3 = c & CertificateBody.profileType;
                    i4 = 1;
                } else if ((c & 224) == 192) {
                    i2 = 128;
                    i3 = c & 31;
                    i4 = 2;
                } else if ((c & 240) == 224) {
                    i2 = 2048;
                    i3 = c & 15;
                    i4 = 3;
                } else if ((c & 248) == 240) {
                    i2 = 65536;
                    i3 = c & 7;
                    i4 = 4;
                } else {
                    nwVar2.g(1);
                    i3 = 65533;
                    if (!Character.isISOControl(i3) && !Character.isWhitespace(i3)) {
                        return false;
                    }
                }
                if (nwVar2.b < ((long) i4)) {
                    throw new EOFException("size < " + i4 + ": " + nwVar2.b + " (to read code point prefixed 0x" + Integer.toHexString(c) + ")");
                }
                for (int i5 = 1; i5 < i4; i5++) {
                    c = nwVar2.c((long) i5);
                    if ((c & 192) != 128) {
                        nwVar2.g((long) i5);
                        i3 = 65533;
                        break;
                    }
                    i3 = (i3 << 6) | (c & 63);
                }
                nwVar2.g((long) i4);
                if (i3 > 1114111) {
                    i3 = 65533;
                } else if (i3 >= 55296 && i3 <= 57343) {
                    i3 = 65533;
                } else if (i3 < i2) {
                    i3 = 65533;
                }
                if (!Character.isISOControl(i3)) {
                }
            }
            return true;
        } catch (EOFException e) {
            return false;
        }
    }

    public final Level getLevel() {
        return this.level;
    }

    public final Response intercept(Chain chain) throws IOException {
        Level level = this.level;
        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }
        Object obj = level == Level.BODY ? 1 : null;
        Object obj2 = (obj != null || level == Level.HEADERS) ? 1 : null;
        RequestBody body = request.body();
        Object obj3 = body != null ? 1 : null;
        Connection connection = chain.connection();
        String str = "--> " + request.method() + ' ' + request.url() + ' ' + (connection != null ? connection.protocol() : Protocol.HTTP_1_1);
        if (obj2 == null && obj3 != null) {
            str = str + " (" + body.contentLength() + "-byte body)";
        }
        this.logger.log(str);
        if (obj2 != null) {
            if (obj3 != null) {
                if (body.contentType() != null) {
                    this.logger.log("Content-Type: " + body.contentType());
                }
                if (body.contentLength() != -1) {
                    this.logger.log("Content-Length: " + body.contentLength());
                }
            }
            Headers headers = request.headers();
            int size = headers.size();
            for (int i = 0; i < size; i++) {
                String name = headers.name(i);
                if (!("Content-Type".equalsIgnoreCase(name) || "Content-Length".equalsIgnoreCase(name))) {
                    this.logger.log(name + ": " + headers.value(i));
                }
            }
            if (obj == null || obj3 == null) {
                this.logger.log("--> END " + request.method());
            } else if (bodyEncoded(request.headers())) {
                this.logger.log("--> END " + request.method() + " (encoded body omitted)");
            } else {
                nw nwVar = new nw();
                body.writeTo(nwVar);
                Charset charset = UTF8;
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                this.logger.log("");
                if (isPlaintext(nwVar)) {
                    this.logger.log(nwVar.a(charset));
                    this.logger.log("--> END " + request.method() + " (" + body.contentLength() + "-byte body)");
                } else {
                    this.logger.log("--> END " + request.method() + " (binary " + body.contentLength() + "-byte body omitted)");
                }
            }
        }
        long nanoTime = System.nanoTime();
        try {
            Response proceed = chain.proceed(request);
            long toMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime);
            ResponseBody body2 = proceed.body();
            long contentLength = body2.contentLength();
            this.logger.log("<-- " + proceed.code() + ' ' + proceed.message() + ' ' + proceed.request().url() + " (" + toMillis + "ms" + (obj2 == null ? ", " + (contentLength != -1 ? contentLength + "-byte" : "unknown-length") + " body" : "") + ')');
            if (obj2 != null) {
                Headers headers2 = proceed.headers();
                int size2 = headers2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    this.logger.log(headers2.name(i2) + ": " + headers2.value(i2));
                }
                if (obj == null || !HttpEngine.hasBody(proceed)) {
                    this.logger.log("<-- END HTTP");
                } else if (bodyEncoded(proceed.headers())) {
                    this.logger.log("<-- END HTTP (encoded body omitted)");
                } else {
                    ny source = body2.source();
                    source.b(Long.MAX_VALUE);
                    nw a = source.a();
                    Charset charset2 = UTF8;
                    MediaType contentType2 = body2.contentType();
                    if (contentType2 != null) {
                        try {
                            charset2 = contentType2.charset(UTF8);
                        } catch (UnsupportedCharsetException e) {
                            this.logger.log("");
                            this.logger.log("Couldn't decode the response body; charset is likely malformed.");
                            this.logger.log("<-- END HTTP");
                            return proceed;
                        }
                    }
                    if (isPlaintext(a)) {
                        if (contentLength != 0) {
                            this.logger.log("");
                            this.logger.log(a.r().a(charset2));
                        }
                        this.logger.log("<-- END HTTP (" + a.b + "-byte body)");
                    } else {
                        this.logger.log("");
                        this.logger.log("<-- END HTTP (binary " + a.b + "-byte body omitted)");
                        return proceed;
                    }
                }
            }
            return proceed;
        } catch (Exception e2) {
            this.logger.log("<-- HTTP FAILED: " + e2);
            throw e2;
        }
    }

    public final HttpLoggingInterceptor setLevel(Level level) {
        if (level == null) {
            throw new NullPointerException("level == null. Use Level.NONE instead.");
        }
        this.level = level;
        return this;
    }
}
