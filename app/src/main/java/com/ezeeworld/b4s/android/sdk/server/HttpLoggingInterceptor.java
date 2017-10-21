package com.ezeeworld.b4s.android.sdk.server;

import java.io.IOException;
import java.nio.charset.Charset;
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

public final class HttpLoggingInterceptor implements Interceptor {
    private static final Charset a = Charset.forName("UTF-8");
    private final Logger b;
    private volatile Level c;

    public enum Level {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    public interface Logger {
        public static final Logger DEFAULT = new Logger() {
            public final void log(String str) {
                Platform.get().logW(str);
            }
        };

        void log(String str);
    }

    public HttpLoggingInterceptor() {
        this(Logger.DEFAULT);
    }

    public HttpLoggingInterceptor(Logger logger) {
        this.c = Level.NONE;
        this.b = logger;
    }

    private static String a(Protocol protocol) {
        return protocol == Protocol.HTTP_1_0 ? "HTTP/1.0" : "HTTP/1.1";
    }

    private boolean a(Headers headers) {
        String str = headers.get("Content-Encoding");
        return (str == null || str.equalsIgnoreCase("identity")) ? false : true;
    }

    public final Level getLevel() {
        return this.c;
    }

    public final Response intercept(Chain chain) throws IOException {
        int i = 1;
        int i2 = 0;
        Level level = this.c;
        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }
        int i3 = level == Level.BODY ? 1 : 0;
        int i4 = (i3 != 0 || level == Level.HEADERS) ? 1 : 0;
        RequestBody body = request.body();
        if (body == null) {
            i = 0;
        }
        Connection connection = chain.connection();
        String str = "--> " + request.method() + ' ' + request.url() + ' ' + a(connection != null ? connection.protocol() : Protocol.HTTP_1_1);
        if (i4 == 0 && i != 0) {
            str = str + " (" + body.contentLength() + "-byte body)";
        }
        this.b.log(str);
        if (i4 != 0) {
            if (i != 0) {
                if (body.contentType() != null) {
                    this.b.log("Content-Type: " + body.contentType());
                }
                if (body.contentLength() != -1) {
                    this.b.log("Content-Length: " + body.contentLength());
                }
            }
            Headers headers = request.headers();
            int size = headers.size();
            for (int i5 = 0; i5 < size; i5++) {
                String name = headers.name(i5);
                if (!("Content-Type".equalsIgnoreCase(name) || "Content-Length".equalsIgnoreCase(name))) {
                    this.b.log(name + ": " + headers.value(i5));
                }
            }
            if (i3 == 0 || i == 0) {
                this.b.log("--> END " + request.method());
            } else if (a(request.headers())) {
                this.b.log("--> END " + request.method() + " (encoded body omitted)");
            } else {
                nw nwVar = new nw();
                body.writeTo(nwVar);
                Charset charset = a;
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    contentType.charset(a);
                }
                this.b.log("");
                this.b.log(nwVar.a(charset));
                this.b.log("--> END " + request.method() + " (" + body.contentLength() + "-byte body)");
            }
        }
        long nanoTime = System.nanoTime();
        Response proceed = chain.proceed(request);
        nanoTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime);
        ResponseBody body2 = proceed.body();
        this.b.log("<-- " + proceed.code() + ' ' + proceed.message() + ' ' + proceed.request().url() + " (" + nanoTime + "ms" + (i4 == 0 ? ", " + body2.contentLength() + "-byte body" : "") + ')');
        if (i4 != 0) {
            Headers headers2 = proceed.headers();
            i = headers2.size();
            while (i2 < i) {
                this.b.log(headers2.name(i2) + ": " + headers2.value(i2));
                i2++;
            }
            if (i3 == 0 || !HttpEngine.hasBody(proceed)) {
                this.b.log("<-- END HTTP");
            } else if (a(proceed.headers())) {
                this.b.log("<-- END HTTP (encoded body omitted)");
            } else {
                ny source = body2.source();
                source.b(Long.MAX_VALUE);
                nw a = source.a();
                Charset charset2 = a;
                MediaType contentType2 = body2.contentType();
                if (contentType2 != null) {
                    charset2 = contentType2.charset(a);
                }
                if (body2.contentLength() != 0) {
                    this.b.log("");
                    this.b.log(a.r().a(charset2));
                }
                this.b.log("<-- END HTTP (" + a.b + "-byte body)");
            }
        }
        return proceed;
    }

    public final HttpLoggingInterceptor setLevel(Level level) {
        if (level == null) {
            throw new NullPointerException("level == null. Use Level.NONE instead.");
        }
        this.c = level;
        return this;
    }
}
