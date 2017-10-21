package okhttp3.internal.http;

import defpackage.kh;
import defpackage.nw;
import defpackage.nx;
import defpackage.ny;
import defpackage.oe;
import defpackage.og;
import defpackage.om;
import defpackage.on;
import defpackage.oo;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.Proxy.Type;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.Address;
import okhttp3.CertificatePinner;
import okhttp3.Connection;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
import okhttp3.internal.Internal;
import okhttp3.internal.InternalCache;
import okhttp3.internal.Util;
import okhttp3.internal.Version;
import okhttp3.internal.http.CacheStrategy.Factory;
import org.spongycastle.asn1.x509.DisplayText;

public final class HttpEngine {
    private static final ResponseBody EMPTY_BODY = new ResponseBody() {
        public final long contentLength() {
            return 0;
        }

        public final MediaType contentType() {
            return null;
        }

        public final ny source() {
            return new nw();
        }
    };
    public static final int MAX_FOLLOW_UPS = 20;
    public final boolean bufferRequestBody;
    private nx bufferedRequestBody;
    private Response cacheResponse;
    private CacheStrategy cacheStrategy;
    private final boolean callerWritesRequestBody;
    final OkHttpClient client;
    private final boolean forWebSocket;
    private HttpStream httpStream;
    private Request networkRequest;
    private final Response priorResponse;
    private om requestBodyOut;
    long sentRequestMillis = -1;
    private CacheRequest storeRequest;
    public final StreamAllocation streamAllocation;
    private boolean transparentGzip;
    private final Request userRequest;
    private Response userResponse;

    class NetworkInterceptorChain implements Chain {
        private int calls;
        private final Connection connection;
        private final int index;
        private final Request request;

        NetworkInterceptorChain(int i, Request request, Connection connection) {
            this.index = i;
            this.request = request;
            this.connection = connection;
        }

        public Connection connection() {
            return this.connection;
        }

        public Response proceed(Request request) throws IOException {
            Interceptor interceptor;
            this.calls++;
            if (this.index > 0) {
                interceptor = (Interceptor) HttpEngine.this.client.networkInterceptors().get(this.index - 1);
                Address address = connection().route().address();
                if (!request.url().host().equals(address.url().host()) || request.url().port() != address.url().port()) {
                    throw new IllegalStateException("network interceptor " + interceptor + " must retain the same host and port");
                } else if (this.calls > 1) {
                    throw new IllegalStateException("network interceptor " + interceptor + " must call proceed() exactly once");
                }
            }
            if (this.index < HttpEngine.this.client.networkInterceptors().size()) {
                Object networkInterceptorChain = new NetworkInterceptorChain(this.index + 1, request, this.connection);
                interceptor = (Interceptor) HttpEngine.this.client.networkInterceptors().get(this.index);
                Response intercept = interceptor.intercept(networkInterceptorChain);
                if (networkInterceptorChain.calls != 1) {
                    throw new IllegalStateException("network interceptor " + interceptor + " must call proceed() exactly once");
                } else if (intercept != null) {
                    return intercept;
                } else {
                    throw new NullPointerException("network interceptor " + interceptor + " returned null");
                }
            }
            HttpEngine.this.httpStream.writeRequestHeaders(request);
            HttpEngine.this.networkRequest = request;
            if (HttpEngine.this.permitsRequestBody(request) && request.body() != null) {
                nx a = og.a(HttpEngine.this.httpStream.createRequestBody(request, request.body().contentLength()));
                request.body().writeTo(a);
                a.close();
            }
            Response access$200 = HttpEngine.this.readNetworkResponse();
            int code = access$200.code();
            if ((code != 204 && code != 205) || access$200.body().contentLength() <= 0) {
                return access$200;
            }
            throw new ProtocolException("HTTP " + code + " had non-zero Content-Length: " + access$200.body().contentLength());
        }

        public Request request() {
            return this.request;
        }
    }

    public HttpEngine(OkHttpClient okHttpClient, Request request, boolean z, boolean z2, boolean z3, StreamAllocation streamAllocation, RetryableSink retryableSink, Response response) {
        this.client = okHttpClient;
        this.userRequest = request;
        this.bufferRequestBody = z;
        this.callerWritesRequestBody = z2;
        this.forWebSocket = z3;
        if (streamAllocation == null) {
            streamAllocation = new StreamAllocation(okHttpClient.connectionPool(), createAddress(okHttpClient, request));
        }
        this.streamAllocation = streamAllocation;
        this.requestBodyOut = retryableSink;
        this.priorResponse = response;
    }

    private Response cacheWritingResponse(final CacheRequest cacheRequest, Response response) throws IOException {
        if (cacheRequest == null) {
            return response;
        }
        om body = cacheRequest.body();
        if (body == null) {
            return response;
        }
        final ny source = response.body().source();
        final nx a = og.a(body);
        return response.newBuilder().body(new RealResponseBody(response.headers(), og.a(new on() {
            boolean cacheRequestClosed;

            public void close() throws IOException {
                if (!(this.cacheRequestClosed || Util.discard(this, 100, TimeUnit.MILLISECONDS))) {
                    this.cacheRequestClosed = true;
                    cacheRequest.abort();
                }
                source.close();
            }

            public long read(nw nwVar, long j) throws IOException {
                try {
                    long read = source.read(nwVar, j);
                    if (read == -1) {
                        if (!this.cacheRequestClosed) {
                            this.cacheRequestClosed = true;
                            a.close();
                        }
                        return -1;
                    }
                    nwVar.a(a.a(), nwVar.b - read, read);
                    a.s();
                    return read;
                } catch (IOException e) {
                    if (!this.cacheRequestClosed) {
                        this.cacheRequestClosed = true;
                        cacheRequest.abort();
                    }
                    throw e;
                }
            }

            public oo timeout() {
                return source.timeout();
            }
        }))).build();
    }

    private static Headers combine(Headers headers, Headers headers2) throws IOException {
        int i;
        int i2 = 0;
        Builder builder = new Builder();
        int size = headers.size();
        for (i = 0; i < size; i++) {
            String name = headers.name(i);
            String value = headers.value(i);
            if (!("Warning".equalsIgnoreCase(name) && value.startsWith("1")) && (!OkHeaders.isEndToEnd(name) || headers2.get(name) == null)) {
                Internal.instance.addLenient(builder, name, value);
            }
        }
        i = headers2.size();
        while (i2 < i) {
            String name2 = headers2.name(i2);
            if (!"Content-Length".equalsIgnoreCase(name2) && OkHeaders.isEndToEnd(name2)) {
                Internal.instance.addLenient(builder, name2, headers2.value(i2));
            }
            i2++;
        }
        return builder.build();
    }

    private HttpStream connect() throws RouteException, RequestException, IOException {
        return this.streamAllocation.newStream(this.client.connectTimeoutMillis(), this.client.readTimeoutMillis(), this.client.writeTimeoutMillis(), this.client.retryOnConnectionFailure(), !this.networkRequest.method().equals("GET"));
    }

    private String cookieHeader(List<Cookie> list) {
        StringBuilder stringBuilder = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                stringBuilder.append("; ");
            }
            Cookie cookie = (Cookie) list.get(i);
            stringBuilder.append(cookie.name()).append('=').append(cookie.value());
        }
        return stringBuilder.toString();
    }

    private static Address createAddress(OkHttpClient okHttpClient, Request request) {
        SSLSocketFactory sslSocketFactory;
        HostnameVerifier hostnameVerifier;
        CertificatePinner certificatePinner = null;
        if (request.isHttps()) {
            sslSocketFactory = okHttpClient.sslSocketFactory();
            hostnameVerifier = okHttpClient.hostnameVerifier();
            certificatePinner = okHttpClient.certificatePinner();
        } else {
            hostnameVerifier = null;
            sslSocketFactory = null;
        }
        return new Address(request.url().host(), request.url().port(), okHttpClient.dns(), okHttpClient.socketFactory(), sslSocketFactory, hostnameVerifier, certificatePinner, okHttpClient.proxyAuthenticator(), okHttpClient.proxy(), okHttpClient.protocols(), okHttpClient.connectionSpecs(), okHttpClient.proxySelector());
    }

    public static boolean hasBody(Response response) {
        if (!response.request().method().equals("HEAD")) {
            int code = response.code();
            if (((code < 100 || code >= DisplayText.DISPLAY_TEXT_MAXIMUM_SIZE) && code != 204 && code != 304) || OkHeaders.contentLength(response) != -1) {
                return true;
            }
            if ("chunked".equalsIgnoreCase(response.header("Transfer-Encoding"))) {
                return true;
            }
        }
        return false;
    }

    private boolean isRecoverable(IOException iOException, boolean z) {
        if (!(iOException instanceof ProtocolException)) {
            if (iOException instanceof InterruptedIOException) {
                if ((iOException instanceof SocketTimeoutException) && z) {
                    return true;
                }
            } else if (!(((iOException instanceof SSLHandshakeException) && (iOException.getCause() instanceof CertificateException)) || (iOException instanceof SSLPeerUnverifiedException))) {
                return true;
            }
        }
        return false;
    }

    private void maybeCache() throws IOException {
        InternalCache internalCache = Internal.instance.internalCache(this.client);
        if (internalCache != null) {
            if (CacheStrategy.isCacheable(this.userResponse, this.networkRequest)) {
                this.storeRequest = internalCache.put(this.userResponse);
            } else if (HttpMethod.invalidatesCache(this.networkRequest.method())) {
                try {
                    internalCache.remove(this.networkRequest);
                } catch (IOException e) {
                }
            }
        }
    }

    private Request networkRequest(Request request) throws IOException {
        Request.Builder newBuilder = request.newBuilder();
        if (request.header("Host") == null) {
            newBuilder.header("Host", Util.hostHeader(request.url(), false));
        }
        if (request.header("Connection") == null) {
            newBuilder.header("Connection", "Keep-Alive");
        }
        if (request.header("Accept-Encoding") == null) {
            this.transparentGzip = true;
            newBuilder.header("Accept-Encoding", "gzip");
        }
        List loadForRequest = this.client.cookieJar().loadForRequest(request.url());
        if (!loadForRequest.isEmpty()) {
            newBuilder.header("Cookie", cookieHeader(loadForRequest));
        }
        if (request.header(kh.HEADER_USER_AGENT) == null) {
            newBuilder.header(kh.HEADER_USER_AGENT, Version.userAgent());
        }
        return newBuilder.build();
    }

    private Response readNetworkResponse() throws IOException {
        this.httpStream.finishRequest();
        Response build = this.httpStream.readResponseHeaders().request(this.networkRequest).handshake(this.streamAllocation.connection().handshake()).sentRequestAtMillis(this.sentRequestMillis).receivedResponseAtMillis(System.currentTimeMillis()).build();
        if (!(this.forWebSocket && build.code() == 101)) {
            build = build.newBuilder().body(this.httpStream.openResponseBody(build)).build();
        }
        if ("close".equalsIgnoreCase(build.request().header("Connection")) || "close".equalsIgnoreCase(build.header("Connection"))) {
            this.streamAllocation.noNewStreams();
        }
        return build;
    }

    private static Response stripBody(Response response) {
        return (response == null || response.body() == null) ? response : response.newBuilder().body(null).build();
    }

    private Response unzip(Response response) throws IOException {
        if (!this.transparentGzip || !"gzip".equalsIgnoreCase(this.userResponse.header("Content-Encoding")) || response.body() == null) {
            return response;
        }
        on oeVar = new oe(response.body().source());
        Headers build = response.headers().newBuilder().removeAll("Content-Encoding").removeAll("Content-Length").build();
        return response.newBuilder().headers(build).body(new RealResponseBody(build, og.a(oeVar))).build();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean validate(okhttp3.Response r4, okhttp3.Response r5) {
        /*
        r0 = r5.code();
        r1 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
        if (r0 != r1) goto L_0x000a;
    L_0x0008:
        r0 = 1;
    L_0x0009:
        return r0;
    L_0x000a:
        r0 = r4.headers();
        r1 = "Last-Modified";
        r0 = r0.getDate(r1);
        if (r0 == 0) goto L_0x002e;
    L_0x0016:
        r1 = r5.headers();
        r2 = "Last-Modified";
        r1 = r1.getDate(r2);
        if (r1 == 0) goto L_0x002e;
    L_0x0022:
        r2 = r1.getTime();
        r0 = r0.getTime();
        r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1));
        if (r0 < 0) goto L_0x0008;
    L_0x002e:
        r0 = 0;
        goto L_0x0009;
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http.HttpEngine.validate(okhttp3.Response, okhttp3.Response):boolean");
    }

    private boolean writeRequestHeadersEagerly() {
        return this.callerWritesRequestBody && permitsRequestBody(this.networkRequest) && this.requestBodyOut == null;
    }

    public final void cancel() {
        this.streamAllocation.cancel();
    }

    public final StreamAllocation close() {
        if (this.bufferedRequestBody != null) {
            Util.closeQuietly(this.bufferedRequestBody);
        } else if (this.requestBodyOut != null) {
            Util.closeQuietly(this.requestBodyOut);
        }
        if (this.userResponse != null) {
            Util.closeQuietly(this.userResponse.body());
        } else {
            this.streamAllocation.streamFailed(null);
        }
        return this.streamAllocation;
    }

    public final Request followUpRequest() throws IOException {
        if (this.userResponse == null) {
            throw new IllegalStateException();
        }
        Connection connection = this.streamAllocation.connection();
        Route route = connection != null ? connection.route() : null;
        int code = this.userResponse.code();
        String method = this.userRequest.method();
        switch (code) {
            case 300:
            case 301:
            case 302:
            case 303:
                break;
            case StatusLine.HTTP_TEMP_REDIRECT /*307*/:
            case StatusLine.HTTP_PERM_REDIRECT /*308*/:
                if (!(method.equals("GET") || method.equals("HEAD"))) {
                    return null;
                }
            case 401:
                return this.client.authenticator().authenticate(route, this.userResponse);
            case 407:
                if ((route != null ? route.proxy() : this.client.proxy()).type() == Type.HTTP) {
                    return this.client.proxyAuthenticator().authenticate(route, this.userResponse);
                }
                throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
            case 408:
                Object obj = (this.requestBodyOut == null || (this.requestBodyOut instanceof RetryableSink)) ? 1 : null;
                return (this.callerWritesRequestBody && obj == null) ? null : this.userRequest;
            default:
                return null;
        }
        if (!this.client.followRedirects()) {
            return null;
        }
        String header = this.userResponse.header("Location");
        if (header == null) {
            return null;
        }
        HttpUrl resolve = this.userRequest.url().resolve(header);
        if (resolve == null) {
            return null;
        }
        if (!resolve.scheme().equals(this.userRequest.url().scheme()) && !this.client.followSslRedirects()) {
            return null;
        }
        Request.Builder newBuilder = this.userRequest.newBuilder();
        if (HttpMethod.permitsRequestBody(method)) {
            if (HttpMethod.redirectsToGet(method)) {
                newBuilder.method("GET", null);
            } else {
                newBuilder.method(method, null);
            }
            newBuilder.removeHeader("Transfer-Encoding");
            newBuilder.removeHeader("Content-Length");
            newBuilder.removeHeader("Content-Type");
        }
        if (!sameConnection(resolve)) {
            newBuilder.removeHeader("Authorization");
        }
        return newBuilder.url(resolve).build();
    }

    public final nx getBufferedRequestBody() {
        nx nxVar = this.bufferedRequestBody;
        if (nxVar != null) {
            return nxVar;
        }
        om requestBody = getRequestBody();
        if (requestBody == null) {
            return null;
        }
        nxVar = og.a(requestBody);
        this.bufferedRequestBody = nxVar;
        return nxVar;
    }

    public final Connection getConnection() {
        return this.streamAllocation.connection();
    }

    public final Request getRequest() {
        return this.userRequest;
    }

    public final om getRequestBody() {
        if (this.cacheStrategy != null) {
            return this.requestBodyOut;
        }
        throw new IllegalStateException();
    }

    public final Response getResponse() {
        if (this.userResponse != null) {
            return this.userResponse;
        }
        throw new IllegalStateException();
    }

    public final boolean hasResponse() {
        return this.userResponse != null;
    }

    final boolean permitsRequestBody(Request request) {
        return HttpMethod.permitsRequestBody(request.method());
    }

    public final void readResponse() throws IOException {
        if (this.userResponse == null) {
            if (this.networkRequest == null && this.cacheResponse == null) {
                throw new IllegalStateException("call sendRequest() first!");
            } else if (this.networkRequest != null) {
                Response proceed;
                InternalCache internalCache;
                if (this.forWebSocket) {
                    this.httpStream.writeRequestHeaders(this.networkRequest);
                } else if (this.callerWritesRequestBody) {
                    if (this.bufferedRequestBody != null && this.bufferedRequestBody.a().b > 0) {
                        this.bufferedRequestBody.b();
                    }
                    if (this.sentRequestMillis == -1) {
                        if (OkHeaders.contentLength(this.networkRequest) == -1 && (this.requestBodyOut instanceof RetryableSink)) {
                            this.networkRequest = this.networkRequest.newBuilder().header("Content-Length", Long.toString(((RetryableSink) this.requestBodyOut).contentLength())).build();
                        }
                        this.httpStream.writeRequestHeaders(this.networkRequest);
                    }
                    if (this.requestBodyOut != null) {
                        if (this.bufferedRequestBody != null) {
                            this.bufferedRequestBody.close();
                        } else {
                            this.requestBodyOut.close();
                        }
                        if (this.requestBodyOut instanceof RetryableSink) {
                            this.httpStream.writeRequestBody((RetryableSink) this.requestBodyOut);
                        }
                    }
                } else {
                    proceed = new NetworkInterceptorChain(0, this.networkRequest, this.streamAllocation.connection()).proceed(this.networkRequest);
                    receiveHeaders(proceed.headers());
                    if (this.cacheResponse != null) {
                        if (validate(this.cacheResponse, proceed)) {
                            Util.closeQuietly(this.cacheResponse.body());
                        } else {
                            this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).headers(combine(this.cacheResponse.headers(), proceed.headers())).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(proceed)).build();
                            proceed.body().close();
                            releaseStreamAllocation();
                            internalCache = Internal.instance.internalCache(this.client);
                            internalCache.trackConditionalCacheHit();
                            internalCache.update(this.cacheResponse, this.userResponse);
                            this.userResponse = unzip(this.userResponse);
                            return;
                        }
                    }
                    this.userResponse = proceed.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(proceed)).build();
                    if (hasBody(this.userResponse)) {
                        maybeCache();
                        this.userResponse = unzip(cacheWritingResponse(this.storeRequest, this.userResponse));
                    }
                }
                proceed = readNetworkResponse();
                receiveHeaders(proceed.headers());
                if (this.cacheResponse != null) {
                    if (validate(this.cacheResponse, proceed)) {
                        Util.closeQuietly(this.cacheResponse.body());
                    } else {
                        this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).headers(combine(this.cacheResponse.headers(), proceed.headers())).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(proceed)).build();
                        proceed.body().close();
                        releaseStreamAllocation();
                        internalCache = Internal.instance.internalCache(this.client);
                        internalCache.trackConditionalCacheHit();
                        internalCache.update(this.cacheResponse, this.userResponse);
                        this.userResponse = unzip(this.userResponse);
                        return;
                    }
                }
                this.userResponse = proceed.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(proceed)).build();
                if (hasBody(this.userResponse)) {
                    maybeCache();
                    this.userResponse = unzip(cacheWritingResponse(this.storeRequest, this.userResponse));
                }
            }
        }
    }

    public final void receiveHeaders(Headers headers) throws IOException {
        if (this.client.cookieJar() != CookieJar.NO_COOKIES) {
            List parseAll = Cookie.parseAll(this.userRequest.url(), headers);
            if (!parseAll.isEmpty()) {
                this.client.cookieJar().saveFromResponse(this.userRequest.url(), parseAll);
            }
        }
    }

    public final HttpEngine recover(IOException iOException, boolean z) {
        return recover(iOException, z, this.requestBodyOut);
    }

    public final HttpEngine recover(IOException iOException, boolean z, om omVar) {
        this.streamAllocation.streamFailed(iOException);
        if (!this.client.retryOnConnectionFailure() || ((omVar != null && !(omVar instanceof RetryableSink)) || !isRecoverable(iOException, z) || !this.streamAllocation.hasMoreRoutes())) {
            return null;
        }
        return new HttpEngine(this.client, this.userRequest, this.bufferRequestBody, this.callerWritesRequestBody, this.forWebSocket, close(), (RetryableSink) omVar, this.priorResponse);
    }

    public final void releaseStreamAllocation() throws IOException {
        this.streamAllocation.release();
    }

    public final boolean sameConnection(HttpUrl httpUrl) {
        HttpUrl url = this.userRequest.url();
        return url.host().equals(httpUrl.host()) && url.port() == httpUrl.port() && url.scheme().equals(httpUrl.scheme());
    }

    public final void sendRequest() throws RequestException, RouteException, IOException {
        if (this.cacheStrategy == null) {
            if (this.httpStream != null) {
                throw new IllegalStateException();
            }
            Request networkRequest = networkRequest(this.userRequest);
            InternalCache internalCache = Internal.instance.internalCache(this.client);
            Response response = internalCache != null ? internalCache.get(networkRequest) : null;
            this.cacheStrategy = new Factory(System.currentTimeMillis(), networkRequest, response).get();
            this.networkRequest = this.cacheStrategy.networkRequest;
            this.cacheResponse = this.cacheStrategy.cacheResponse;
            if (internalCache != null) {
                internalCache.trackResponse(this.cacheStrategy);
            }
            if (response != null && this.cacheResponse == null) {
                Util.closeQuietly(response.body());
            }
            if (this.networkRequest == null && this.cacheResponse == null) {
                this.userResponse = new Response.Builder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(EMPTY_BODY).sentRequestAtMillis(this.sentRequestMillis).receivedResponseAtMillis(System.currentTimeMillis()).build();
            } else if (this.networkRequest == null) {
                this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).build();
                this.userResponse = unzip(this.userResponse);
            } else {
                try {
                    this.httpStream = connect();
                    this.httpStream.setHttpEngine(this);
                    if (writeRequestHeadersEagerly()) {
                        long contentLength = OkHeaders.contentLength(networkRequest);
                        if (!this.bufferRequestBody) {
                            this.httpStream.writeRequestHeaders(this.networkRequest);
                            this.requestBodyOut = this.httpStream.createRequestBody(this.networkRequest, contentLength);
                        } else if (contentLength > 2147483647L) {
                            throw new IllegalStateException("Use setFixedLengthStreamingMode() or setChunkedStreamingMode() for requests larger than 2 GiB.");
                        } else if (contentLength != -1) {
                            this.httpStream.writeRequestHeaders(this.networkRequest);
                            this.requestBodyOut = new RetryableSink((int) contentLength);
                        } else {
                            this.requestBodyOut = new RetryableSink();
                        }
                    }
                } catch (Throwable th) {
                    if (response != null) {
                        Util.closeQuietly(response.body());
                    }
                }
            }
        }
    }

    public final void writingRequestHeaders() {
        if (this.sentRequestMillis != -1) {
            throw new IllegalStateException();
        }
        this.sentRequestMillis = System.currentTimeMillis();
    }
}
