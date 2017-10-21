package okhttp3;

import defpackage.nw;
import defpackage.nx;
import defpackage.ny;
import defpackage.nz;
import defpackage.ob;
import defpackage.oc;
import defpackage.og;
import defpackage.om;
import defpackage.on;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import okhttp3.Headers.Builder;
import okhttp3.internal.DiskLruCache;
import okhttp3.internal.DiskLruCache.Editor;
import okhttp3.internal.DiskLruCache.Snapshot;
import okhttp3.internal.InternalCache;
import okhttp3.internal.Util;
import okhttp3.internal.http.CacheRequest;
import okhttp3.internal.http.CacheStrategy;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.OkHeaders;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.io.FileSystem;

public final class Cache implements Closeable, Flushable {
    private static final int ENTRY_BODY = 1;
    private static final int ENTRY_COUNT = 2;
    private static final int ENTRY_METADATA = 0;
    private static final int VERSION = 201105;
    private final DiskLruCache cache;
    private int hitCount;
    final InternalCache internalCache;
    private int networkCount;
    private int requestCount;
    private int writeAbortCount;
    private int writeSuccessCount;

    final class CacheRequestImpl implements CacheRequest {
        private om body;
        private om cacheOut;
        private boolean done;
        private final Editor editor;

        public CacheRequestImpl(final Editor editor) throws IOException {
            this.editor = editor;
            this.cacheOut = editor.newSink(1);
            this.body = new ob(this.cacheOut, Cache.this) {
                public void close() throws IOException {
                    synchronized (Cache.this) {
                        if (CacheRequestImpl.this.done) {
                            return;
                        }
                        CacheRequestImpl.this.done = true;
                        Cache.this.writeSuccessCount = Cache.this.writeSuccessCount + 1;
                        super.close();
                        editor.commit();
                    }
                }
            };
        }

        public final void abort() {
            synchronized (Cache.this) {
                if (this.done) {
                    return;
                }
                this.done = true;
                Cache.this.writeAbortCount = Cache.this.writeAbortCount + 1;
                Util.closeQuietly(this.cacheOut);
                try {
                    this.editor.abort();
                } catch (IOException e) {
                }
            }
        }

        public final om body() {
            return this.body;
        }
    }

    static class CacheResponseBody extends ResponseBody {
        private final ny bodySource;
        private final String contentLength;
        private final String contentType;
        private final Snapshot snapshot;

        public CacheResponseBody(final Snapshot snapshot, String str, String str2) {
            this.snapshot = snapshot;
            this.contentType = str;
            this.contentLength = str2;
            this.bodySource = og.a(new oc(snapshot.getSource(1)) {
                public void close() throws IOException {
                    snapshot.close();
                    super.close();
                }
            });
        }

        public long contentLength() {
            long j = -1;
            try {
                if (this.contentLength != null) {
                    j = Long.parseLong(this.contentLength);
                }
            } catch (NumberFormatException e) {
            }
            return j;
        }

        public MediaType contentType() {
            return this.contentType != null ? MediaType.parse(this.contentType) : null;
        }

        public ny source() {
            return this.bodySource;
        }
    }

    static final class Entry {
        private final int code;
        private final Handshake handshake;
        private final String message;
        private final Protocol protocol;
        private final long receivedResponseMillis;
        private final String requestMethod;
        private final Headers responseHeaders;
        private final long sentRequestMillis;
        private final String url;
        private final Headers varyHeaders;

        public Entry(Response response) {
            this.url = response.request().url().toString();
            this.varyHeaders = OkHeaders.varyHeaders(response);
            this.requestMethod = response.request().method();
            this.protocol = response.protocol();
            this.code = response.code();
            this.message = response.message();
            this.responseHeaders = response.headers();
            this.handshake = response.handshake();
            this.sentRequestMillis = response.sentRequestAtMillis();
            this.receivedResponseMillis = response.receivedResponseAtMillis();
        }

        public Entry(on onVar) throws IOException {
            long j = 0;
            TlsVersion tlsVersion = null;
            int i = 0;
            try {
                int i2;
                ny a = og.a(onVar);
                this.url = a.o();
                this.requestMethod = a.o();
                Builder builder = new Builder();
                int access$1000 = Cache.readInt(a);
                for (i2 = 0; i2 < access$1000; i2++) {
                    builder.addLenient(a.o());
                }
                this.varyHeaders = builder.build();
                StatusLine parse = StatusLine.parse(a.o());
                this.protocol = parse.protocol;
                this.code = parse.code;
                this.message = parse.message;
                Builder builder2 = new Builder();
                i2 = Cache.readInt(a);
                while (i < i2) {
                    builder2.addLenient(a.o());
                    i++;
                }
                String str = builder2.get(OkHeaders.SENT_MILLIS);
                String str2 = builder2.get(OkHeaders.RECEIVED_MILLIS);
                builder2.removeAll(OkHeaders.SENT_MILLIS);
                builder2.removeAll(OkHeaders.RECEIVED_MILLIS);
                this.sentRequestMillis = str != null ? Long.parseLong(str) : 0;
                if (str2 != null) {
                    j = Long.parseLong(str2);
                }
                this.receivedResponseMillis = j;
                this.responseHeaders = builder2.build();
                if (isHttps()) {
                    str = a.o();
                    if (str.length() > 0) {
                        throw new IOException("expected \"\" but was \"" + str + "\"");
                    }
                    CipherSuite forJavaName = CipherSuite.forJavaName(a.o());
                    List readCertificateList = readCertificateList(a);
                    List readCertificateList2 = readCertificateList(a);
                    if (!a.c()) {
                        tlsVersion = TlsVersion.forJavaName(a.o());
                    }
                    this.handshake = Handshake.get(tlsVersion, forJavaName, readCertificateList, readCertificateList2);
                } else {
                    this.handshake = null;
                }
                onVar.close();
            } catch (Throwable th) {
                onVar.close();
            }
        }

        private boolean isHttps() {
            return this.url.startsWith("https://");
        }

        private List<Certificate> readCertificateList(ny nyVar) throws IOException {
            int access$1000 = Cache.readInt(nyVar);
            if (access$1000 == -1) {
                return Collections.emptyList();
            }
            try {
                CertificateFactory instance = CertificateFactory.getInstance("X.509");
                List<Certificate> arrayList = new ArrayList(access$1000);
                for (int i = 0; i < access$1000; i++) {
                    String o = nyVar.o();
                    nw nwVar = new nw();
                    nwVar.a(nz.b(o));
                    arrayList.add(instance.generateCertificate(nwVar.d()));
                }
                return arrayList;
            } catch (CertificateException e) {
                throw new IOException(e.getMessage());
            }
        }

        private void writeCertList(nx nxVar, List<Certificate> list) throws IOException {
            try {
                nxVar.k((long) list.size()).h(10);
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    nxVar.b(nz.a(((Certificate) list.get(i)).getEncoded()).b()).h(10);
                }
            } catch (CertificateEncodingException e) {
                throw new IOException(e.getMessage());
            }
        }

        public final boolean matches(Request request, Response response) {
            return this.url.equals(request.url().toString()) && this.requestMethod.equals(request.method()) && OkHeaders.varyMatches(response, this.varyHeaders, request);
        }

        public final Response response(Snapshot snapshot) {
            String str = this.responseHeaders.get("Content-Type");
            String str2 = this.responseHeaders.get("Content-Length");
            return new Response.Builder().request(new Request.Builder().url(this.url).method(this.requestMethod, null).headers(this.varyHeaders).build()).protocol(this.protocol).code(this.code).message(this.message).headers(this.responseHeaders).body(new CacheResponseBody(snapshot, str, str2)).handshake(this.handshake).sentRequestAtMillis(this.sentRequestMillis).receivedResponseAtMillis(this.receivedResponseMillis).build();
        }

        public final void writeTo(Editor editor) throws IOException {
            int i;
            int i2 = 0;
            nx a = og.a(editor.newSink(0));
            a.b(this.url).h(10);
            a.b(this.requestMethod).h(10);
            a.k((long) this.varyHeaders.size()).h(10);
            int size = this.varyHeaders.size();
            for (i = 0; i < size; i++) {
                a.b(this.varyHeaders.name(i)).b(": ").b(this.varyHeaders.value(i)).h(10);
            }
            a.b(new StatusLine(this.protocol, this.code, this.message).toString()).h(10);
            a.k((long) (this.responseHeaders.size() + 2)).h(10);
            i = this.responseHeaders.size();
            while (i2 < i) {
                a.b(this.responseHeaders.name(i2)).b(": ").b(this.responseHeaders.value(i2)).h(10);
                i2++;
            }
            a.b(OkHeaders.SENT_MILLIS).b(": ").k(this.sentRequestMillis).h(10);
            a.b(OkHeaders.RECEIVED_MILLIS).b(": ").k(this.receivedResponseMillis).h(10);
            if (isHttps()) {
                a.h(10);
                a.b(this.handshake.cipherSuite().javaName()).h(10);
                writeCertList(a, this.handshake.peerCertificates());
                writeCertList(a, this.handshake.localCertificates());
                if (this.handshake.tlsVersion() != null) {
                    a.b(this.handshake.tlsVersion().javaName()).h(10);
                }
            }
            a.close();
        }
    }

    public Cache(File file, long j) {
        this(file, j, FileSystem.SYSTEM);
    }

    Cache(File file, long j, FileSystem fileSystem) {
        this.internalCache = new InternalCache() {
            public Response get(Request request) throws IOException {
                return Cache.this.get(request);
            }

            public CacheRequest put(Response response) throws IOException {
                return Cache.this.put(response);
            }

            public void remove(Request request) throws IOException {
                Cache.this.remove(request);
            }

            public void trackConditionalCacheHit() {
                Cache.this.trackConditionalCacheHit();
            }

            public void trackResponse(CacheStrategy cacheStrategy) {
                Cache.this.trackResponse(cacheStrategy);
            }

            public void update(Response response, Response response2) throws IOException {
                Cache.this.update(response, response2);
            }
        };
        this.cache = DiskLruCache.create(fileSystem, file, VERSION, 2, j);
    }

    private void abortQuietly(Editor editor) {
        if (editor != null) {
            try {
                editor.abort();
            } catch (IOException e) {
            }
        }
    }

    private CacheRequest put(Response response) throws IOException {
        String method = response.request().method();
        if (HttpMethod.invalidatesCache(response.request().method())) {
            try {
                remove(response.request());
                return null;
            } catch (IOException e) {
                return null;
            }
        } else if (!method.equals("GET") || OkHeaders.hasVaryAll(response)) {
            return null;
        } else {
            Entry entry = new Entry(response);
            Editor edit;
            try {
                edit = this.cache.edit(urlToKey(response.request()));
                if (edit == null) {
                    return null;
                }
                try {
                    entry.writeTo(edit);
                    return new CacheRequestImpl(edit);
                } catch (IOException e2) {
                    abortQuietly(edit);
                    return null;
                }
            } catch (IOException e3) {
                edit = null;
                abortQuietly(edit);
                return null;
            }
        }
    }

    private static int readInt(ny nyVar) throws IOException {
        try {
            long k = nyVar.k();
            String o = nyVar.o();
            if (k >= 0 && k <= 2147483647L && o.isEmpty()) {
                return (int) k;
            }
            throw new IOException("expected an int but was \"" + k + o + "\"");
        } catch (NumberFormatException e) {
            throw new IOException(e.getMessage());
        }
    }

    private void remove(Request request) throws IOException {
        this.cache.remove(urlToKey(request));
    }

    private void trackConditionalCacheHit() {
        synchronized (this) {
            this.hitCount++;
        }
    }

    private void trackResponse(CacheStrategy cacheStrategy) {
        synchronized (this) {
            this.requestCount++;
            if (cacheStrategy.networkRequest != null) {
                this.networkCount++;
            } else if (cacheStrategy.cacheResponse != null) {
                this.hitCount++;
            }
        }
    }

    private void update(Response response, Response response2) {
        Entry entry = new Entry(response2);
        try {
            Editor edit = ((CacheResponseBody) response.body()).snapshot.edit();
            if (edit != null) {
                entry.writeTo(edit);
                edit.commit();
            }
        } catch (IOException e) {
            abortQuietly(null);
        }
    }

    private static String urlToKey(Request request) {
        return Util.md5Hex(request.url().toString());
    }

    public final void close() throws IOException {
        this.cache.close();
    }

    public final void delete() throws IOException {
        this.cache.delete();
    }

    public final File directory() {
        return this.cache.getDirectory();
    }

    public final void evictAll() throws IOException {
        this.cache.evictAll();
    }

    public final void flush() throws IOException {
        this.cache.flush();
    }

    final Response get(Request request) {
        try {
            Closeable closeable = this.cache.get(urlToKey(request));
            if (closeable == null) {
                return null;
            }
            try {
                Entry entry = new Entry(closeable.getSource(0));
                Response response = entry.response(closeable);
                if (entry.matches(request, response)) {
                    return response;
                }
                Util.closeQuietly(response.body());
                return null;
            } catch (IOException e) {
                Util.closeQuietly(closeable);
                return null;
            }
        } catch (IOException e2) {
            return null;
        }
    }

    public final int hitCount() {
        int i;
        synchronized (this) {
            i = this.hitCount;
        }
        return i;
    }

    public final void initialize() throws IOException {
        this.cache.initialize();
    }

    public final boolean isClosed() {
        return this.cache.isClosed();
    }

    public final long maxSize() {
        return this.cache.getMaxSize();
    }

    public final int networkCount() {
        int i;
        synchronized (this) {
            i = this.networkCount;
        }
        return i;
    }

    public final int requestCount() {
        int i;
        synchronized (this) {
            i = this.requestCount;
        }
        return i;
    }

    public final long size() throws IOException {
        return this.cache.size();
    }

    public final Iterator<String> urls() throws IOException {
        return new Iterator<String>() {
            boolean canRemove;
            final Iterator<Snapshot> delegate = Cache.this.cache.snapshots();
            String nextUrl;

            public boolean hasNext() {
                if (this.nextUrl != null) {
                    return true;
                }
                this.canRemove = false;
                while (this.delegate.hasNext()) {
                    Snapshot snapshot = (Snapshot) this.delegate.next();
                    try {
                        this.nextUrl = og.a(snapshot.getSource(0)).o();
                        snapshot.close();
                        return true;
                    } catch (IOException e) {
                        snapshot.close();
                    } catch (Throwable th) {
                        snapshot.close();
                    }
                }
                return false;
            }

            public String next() {
                if (hasNext()) {
                    String str = this.nextUrl;
                    this.nextUrl = null;
                    this.canRemove = true;
                    return str;
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                if (this.canRemove) {
                    this.delegate.remove();
                    return;
                }
                throw new IllegalStateException("remove() before next()");
            }
        };
    }

    public final int writeAbortCount() {
        int i;
        synchronized (this) {
            i = this.writeAbortCount;
        }
        return i;
    }

    public final int writeSuccessCount() {
        int i;
        synchronized (this) {
            i = this.writeSuccessCount;
        }
        return i;
    }
}
