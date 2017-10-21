package okhttp3.internal.framed;

import defpackage.nu;
import defpackage.nw;
import defpackage.ny;
import defpackage.om;
import defpackage.on;
import defpackage.oo;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import org.spongycastle.asn1.cmp.PKIFailureInfo;

public final class FramedStream {
    static final /* synthetic */ boolean $assertionsDisabled = (!FramedStream.class.desiredAssertionStatus());
    long bytesLeftInWriteWindow;
    private final FramedConnection connection;
    private ErrorCode errorCode = null;
    private final int id;
    private final StreamTimeout readTimeout = new StreamTimeout();
    private final List<Header> requestHeaders;
    private List<Header> responseHeaders;
    final FramedDataSink sink;
    private final FramedDataSource source;
    long unacknowledgedBytesRead = 0;
    private final StreamTimeout writeTimeout = new StreamTimeout();

    final class FramedDataSink implements om {
        static final /* synthetic */ boolean $assertionsDisabled = (!FramedStream.class.desiredAssertionStatus());
        private static final long EMIT_BUFFER_SIZE = 16384;
        private boolean closed;
        private boolean finished;
        private final nw sendBuffer = new nw();

        FramedDataSink() {
        }

        private void emitDataFrame(boolean z) throws IOException {
            synchronized (FramedStream.this) {
                FramedStream.this.writeTimeout.enter();
                while (FramedStream.this.bytesLeftInWriteWindow <= 0 && !this.finished && !this.closed && FramedStream.this.errorCode == null) {
                    try {
                        FramedStream.this.waitForIo();
                    } catch (Throwable th) {
                        FramedStream.this.writeTimeout.exitAndThrowIfTimedOut();
                    }
                }
                FramedStream.this.writeTimeout.exitAndThrowIfTimedOut();
                FramedStream.this.checkOutNotClosed();
                long min = Math.min(FramedStream.this.bytesLeftInWriteWindow, this.sendBuffer.b);
                FramedStream framedStream = FramedStream.this;
                framedStream.bytesLeftInWriteWindow -= min;
            }
            FramedStream.this.writeTimeout.enter();
            try {
                FramedConnection access$500 = FramedStream.this.connection;
                int access$600 = FramedStream.this.id;
                boolean z2 = z && min == this.sendBuffer.b;
                access$500.writeData(access$600, z2, this.sendBuffer, min);
            } finally {
                FramedStream.this.writeTimeout.exitAndThrowIfTimedOut();
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void close() throws java.io.IOException {
            /*
            r6 = this;
            r4 = 0;
            r2 = 1;
            r0 = $assertionsDisabled;
            if (r0 != 0) goto L_0x0015;
        L_0x0007:
            r0 = okhttp3.internal.framed.FramedStream.this;
            r0 = java.lang.Thread.holdsLock(r0);
            if (r0 == 0) goto L_0x0015;
        L_0x000f:
            r0 = new java.lang.AssertionError;
            r0.<init>();
            throw r0;
        L_0x0015:
            r1 = okhttp3.internal.framed.FramedStream.this;
            monitor-enter(r1);
            r0 = r6.closed;	 Catch:{ all -> 0x003b }
            if (r0 == 0) goto L_0x001e;
        L_0x001c:
            monitor-exit(r1);	 Catch:{ all -> 0x003b }
        L_0x001d:
            return;
        L_0x001e:
            monitor-exit(r1);	 Catch:{ all -> 0x003b }
            r0 = okhttp3.internal.framed.FramedStream.this;
            r0 = r0.sink;
            r0 = r0.finished;
            if (r0 != 0) goto L_0x004e;
        L_0x0027:
            r0 = r6.sendBuffer;
            r0 = r0.b;
            r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
            if (r0 <= 0) goto L_0x003e;
        L_0x002f:
            r0 = r6.sendBuffer;
            r0 = r0.b;
            r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
            if (r0 <= 0) goto L_0x004e;
        L_0x0037:
            r6.emitDataFrame(r2);
            goto L_0x002f;
        L_0x003b:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x003b }
            throw r0;
        L_0x003e:
            r0 = okhttp3.internal.framed.FramedStream.this;
            r0 = r0.connection;
            r1 = okhttp3.internal.framed.FramedStream.this;
            r1 = r1.id;
            r3 = 0;
            r0.writeData(r1, r2, r3, r4);
        L_0x004e:
            r1 = okhttp3.internal.framed.FramedStream.this;
            monitor-enter(r1);
            r0 = 1;
            r6.closed = r0;	 Catch:{ all -> 0x0064 }
            monitor-exit(r1);	 Catch:{ all -> 0x0064 }
            r0 = okhttp3.internal.framed.FramedStream.this;
            r0 = r0.connection;
            r0.flush();
            r0 = okhttp3.internal.framed.FramedStream.this;
            r0.cancelStreamIfNecessary();
            goto L_0x001d;
        L_0x0064:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0064 }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.framed.FramedStream.FramedDataSink.close():void");
        }

        public final void flush() throws IOException {
            if ($assertionsDisabled || !Thread.holdsLock(FramedStream.this)) {
                synchronized (FramedStream.this) {
                    FramedStream.this.checkOutNotClosed();
                }
                while (this.sendBuffer.b > 0) {
                    emitDataFrame(false);
                    FramedStream.this.connection.flush();
                }
                return;
            }
            throw new AssertionError();
        }

        public final oo timeout() {
            return FramedStream.this.writeTimeout;
        }

        public final void write(nw nwVar, long j) throws IOException {
            if ($assertionsDisabled || !Thread.holdsLock(FramedStream.this)) {
                this.sendBuffer.write(nwVar, j);
                while (this.sendBuffer.b >= EMIT_BUFFER_SIZE) {
                    emitDataFrame(false);
                }
                return;
            }
            throw new AssertionError();
        }
    }

    final class FramedDataSource implements on {
        static final /* synthetic */ boolean $assertionsDisabled = (!FramedStream.class.desiredAssertionStatus());
        private boolean closed;
        private boolean finished;
        private final long maxByteCount;
        private final nw readBuffer;
        private final nw receiveBuffer;

        private FramedDataSource(long j) {
            this.receiveBuffer = new nw();
            this.readBuffer = new nw();
            this.maxByteCount = j;
        }

        private void checkNotClosed() throws IOException {
            if (this.closed) {
                throw new IOException("stream closed");
            } else if (FramedStream.this.errorCode != null) {
                throw new StreamResetException(FramedStream.this.errorCode);
            }
        }

        private void waitUntilReadable() throws IOException {
            FramedStream.this.readTimeout.enter();
            while (this.readBuffer.b == 0 && !this.finished && !this.closed && FramedStream.this.errorCode == null) {
                try {
                    FramedStream.this.waitForIo();
                } catch (Throwable th) {
                    FramedStream.this.readTimeout.exitAndThrowIfTimedOut();
                }
            }
            FramedStream.this.readTimeout.exitAndThrowIfTimedOut();
        }

        public final void close() throws IOException {
            synchronized (FramedStream.this) {
                this.closed = true;
                this.readBuffer.q();
                FramedStream.this.notifyAll();
            }
            FramedStream.this.cancelStreamIfNecessary();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final long read(defpackage.nw r9, long r10) throws java.io.IOException {
            /*
            r8 = this;
            r4 = 0;
            r0 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1));
            if (r0 >= 0) goto L_0x001b;
        L_0x0006:
            r0 = new java.lang.IllegalArgumentException;
            r1 = new java.lang.StringBuilder;
            r2 = "byteCount < 0: ";
            r1.<init>(r2);
            r1 = r1.append(r10);
            r1 = r1.toString();
            r0.<init>(r1);
            throw r0;
        L_0x001b:
            r2 = okhttp3.internal.framed.FramedStream.this;
            monitor-enter(r2);
            r8.waitUntilReadable();	 Catch:{ all -> 0x00c9 }
            r8.checkNotClosed();	 Catch:{ all -> 0x00c9 }
            r0 = r8.readBuffer;	 Catch:{ all -> 0x00c9 }
            r0 = r0.b;	 Catch:{ all -> 0x00c9 }
            r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
            if (r0 != 0) goto L_0x0030;
        L_0x002c:
            monitor-exit(r2);	 Catch:{ all -> 0x00c9 }
            r0 = -1;
        L_0x002f:
            return r0;
        L_0x0030:
            r0 = r8.readBuffer;	 Catch:{ all -> 0x00c9 }
            r1 = r8.readBuffer;	 Catch:{ all -> 0x00c9 }
            r4 = r1.b;	 Catch:{ all -> 0x00c9 }
            r4 = java.lang.Math.min(r10, r4);	 Catch:{ all -> 0x00c9 }
            r0 = r0.read(r9, r4);	 Catch:{ all -> 0x00c9 }
            r3 = okhttp3.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00c9 }
            r4 = r3.unacknowledgedBytesRead;	 Catch:{ all -> 0x00c9 }
            r4 = r4 + r0;
            r3.unacknowledgedBytesRead = r4;	 Catch:{ all -> 0x00c9 }
            r3 = okhttp3.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00c9 }
            r4 = r3.unacknowledgedBytesRead;	 Catch:{ all -> 0x00c9 }
            r3 = okhttp3.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00c9 }
            r3 = r3.connection;	 Catch:{ all -> 0x00c9 }
            r3 = r3.okHttpSettings;	 Catch:{ all -> 0x00c9 }
            r6 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
            r3 = r3.getInitialWindowSize(r6);	 Catch:{ all -> 0x00c9 }
            r3 = r3 / 2;
            r6 = (long) r3;	 Catch:{ all -> 0x00c9 }
            r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
            if (r3 < 0) goto L_0x0077;
        L_0x005e:
            r3 = okhttp3.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00c9 }
            r3 = r3.connection;	 Catch:{ all -> 0x00c9 }
            r4 = okhttp3.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00c9 }
            r4 = r4.id;	 Catch:{ all -> 0x00c9 }
            r5 = okhttp3.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00c9 }
            r6 = r5.unacknowledgedBytesRead;	 Catch:{ all -> 0x00c9 }
            r3.writeWindowUpdateLater(r4, r6);	 Catch:{ all -> 0x00c9 }
            r3 = okhttp3.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00c9 }
            r4 = 0;
            r3.unacknowledgedBytesRead = r4;	 Catch:{ all -> 0x00c9 }
        L_0x0077:
            monitor-exit(r2);	 Catch:{ all -> 0x00c9 }
            r2 = okhttp3.internal.framed.FramedStream.this;
            r2 = r2.connection;
            monitor-enter(r2);
            r3 = okhttp3.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00c6 }
            r3 = r3.connection;	 Catch:{ all -> 0x00c6 }
            r4 = r3.unacknowledgedBytesRead;	 Catch:{ all -> 0x00c6 }
            r4 = r4 + r0;
            r3.unacknowledgedBytesRead = r4;	 Catch:{ all -> 0x00c6 }
            r3 = okhttp3.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00c6 }
            r3 = r3.connection;	 Catch:{ all -> 0x00c6 }
            r4 = r3.unacknowledgedBytesRead;	 Catch:{ all -> 0x00c6 }
            r3 = okhttp3.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00c6 }
            r3 = r3.connection;	 Catch:{ all -> 0x00c6 }
            r3 = r3.okHttpSettings;	 Catch:{ all -> 0x00c6 }
            r6 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
            r3 = r3.getInitialWindowSize(r6);	 Catch:{ all -> 0x00c6 }
            r3 = r3 / 2;
            r6 = (long) r3;	 Catch:{ all -> 0x00c6 }
            r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
            if (r3 < 0) goto L_0x00c3;
        L_0x00a7:
            r3 = okhttp3.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00c6 }
            r3 = r3.connection;	 Catch:{ all -> 0x00c6 }
            r4 = 0;
            r5 = okhttp3.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00c6 }
            r5 = r5.connection;	 Catch:{ all -> 0x00c6 }
            r6 = r5.unacknowledgedBytesRead;	 Catch:{ all -> 0x00c6 }
            r3.writeWindowUpdateLater(r4, r6);	 Catch:{ all -> 0x00c6 }
            r3 = okhttp3.internal.framed.FramedStream.this;	 Catch:{ all -> 0x00c6 }
            r3 = r3.connection;	 Catch:{ all -> 0x00c6 }
            r4 = 0;
            r3.unacknowledgedBytesRead = r4;	 Catch:{ all -> 0x00c6 }
        L_0x00c3:
            monitor-exit(r2);	 Catch:{ all -> 0x00c6 }
            goto L_0x002f;
        L_0x00c6:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x00c6 }
            throw r0;
        L_0x00c9:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x00c9 }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.framed.FramedStream.FramedDataSource.read(nw, long):long");
        }

        final void receive(ny nyVar, long j) throws IOException {
            if ($assertionsDisabled || !Thread.holdsLock(FramedStream.this)) {
                while (j > 0) {
                    boolean z;
                    Object obj;
                    synchronized (FramedStream.this) {
                        z = this.finished;
                        obj = this.readBuffer.b + j > this.maxByteCount ? 1 : null;
                    }
                    if (obj != null) {
                        nyVar.g(j);
                        FramedStream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                        return;
                    } else if (z) {
                        nyVar.g(j);
                        return;
                    } else {
                        long read = nyVar.read(this.receiveBuffer, j);
                        if (read == -1) {
                            throw new EOFException();
                        }
                        j -= read;
                        synchronized (FramedStream.this) {
                            obj = this.readBuffer.b == 0 ? 1 : null;
                            this.readBuffer.a(this.receiveBuffer);
                            if (obj != null) {
                                FramedStream.this.notifyAll();
                            }
                        }
                    }
                }
                return;
            }
            throw new AssertionError();
        }

        public final oo timeout() {
            return FramedStream.this.readTimeout;
        }
    }

    class StreamTimeout extends nu {
        StreamTimeout() {
        }

        public void exitAndThrowIfTimedOut() throws IOException {
            if (exit()) {
                throw newTimeoutException(null);
            }
        }

        protected IOException newTimeoutException(IOException iOException) {
            IOException socketTimeoutException = new SocketTimeoutException("timeout");
            if (iOException != null) {
                socketTimeoutException.initCause(iOException);
            }
            return socketTimeoutException;
        }

        protected void timedOut() {
            FramedStream.this.closeLater(ErrorCode.CANCEL);
        }
    }

    FramedStream(int i, FramedConnection framedConnection, boolean z, boolean z2, List<Header> list) {
        if (framedConnection == null) {
            throw new NullPointerException("connection == null");
        } else if (list == null) {
            throw new NullPointerException("requestHeaders == null");
        } else {
            this.id = i;
            this.connection = framedConnection;
            this.bytesLeftInWriteWindow = (long) framedConnection.peerSettings.getInitialWindowSize(PKIFailureInfo.notAuthorized);
            this.source = new FramedDataSource((long) framedConnection.okHttpSettings.getInitialWindowSize(PKIFailureInfo.notAuthorized));
            this.sink = new FramedDataSink();
            this.source.finished = z2;
            this.sink.finished = z;
            this.requestHeaders = list;
        }
    }

    private void cancelStreamIfNecessary() throws IOException {
        if ($assertionsDisabled || !Thread.holdsLock(this)) {
            Object obj;
            boolean isOpen;
            synchronized (this) {
                obj = (!this.source.finished && this.source.closed && (this.sink.finished || this.sink.closed)) ? 1 : null;
                isOpen = isOpen();
            }
            if (obj != null) {
                close(ErrorCode.CANCEL);
                return;
            } else if (!isOpen) {
                this.connection.removeStream(this.id);
                return;
            } else {
                return;
            }
        }
        throw new AssertionError();
    }

    private void checkOutNotClosed() throws IOException {
        if (this.sink.closed) {
            throw new IOException("stream closed");
        } else if (this.sink.finished) {
            throw new IOException("stream finished");
        } else if (this.errorCode != null) {
            throw new StreamResetException(this.errorCode);
        }
    }

    private boolean closeInternal(ErrorCode errorCode) {
        if ($assertionsDisabled || !Thread.holdsLock(this)) {
            synchronized (this) {
                if (this.errorCode != null) {
                    return false;
                } else if (this.source.finished && this.sink.finished) {
                    return false;
                } else {
                    this.errorCode = errorCode;
                    notifyAll();
                    this.connection.removeStream(this.id);
                    return true;
                }
            }
        }
        throw new AssertionError();
    }

    private void waitForIo() throws InterruptedIOException {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new InterruptedIOException();
        }
    }

    final void addBytesToWriteWindow(long j) {
        this.bytesLeftInWriteWindow += j;
        if (j > 0) {
            notifyAll();
        }
    }

    public final void close(ErrorCode errorCode) throws IOException {
        if (closeInternal(errorCode)) {
            this.connection.writeSynReset(this.id, errorCode);
        }
    }

    public final void closeLater(ErrorCode errorCode) {
        if (closeInternal(errorCode)) {
            this.connection.writeSynResetLater(this.id, errorCode);
        }
    }

    public final FramedConnection getConnection() {
        return this.connection;
    }

    public final ErrorCode getErrorCode() {
        ErrorCode errorCode;
        synchronized (this) {
            errorCode = this.errorCode;
        }
        return errorCode;
    }

    public final int getId() {
        return this.id;
    }

    public final List<Header> getRequestHeaders() {
        return this.requestHeaders;
    }

    public final List<Header> getResponseHeaders() throws IOException {
        List<Header> list;
        synchronized (this) {
            this.readTimeout.enter();
            while (this.responseHeaders == null && this.errorCode == null) {
                try {
                    waitForIo();
                } catch (Throwable th) {
                    this.readTimeout.exitAndThrowIfTimedOut();
                }
            }
            this.readTimeout.exitAndThrowIfTimedOut();
            if (this.responseHeaders != null) {
                list = this.responseHeaders;
            } else {
                throw new StreamResetException(this.errorCode);
            }
        }
        return list;
    }

    public final om getSink() {
        synchronized (this) {
            if (this.responseHeaders != null || isLocallyInitiated()) {
            } else {
                throw new IllegalStateException("reply before requesting the sink");
            }
        }
        return this.sink;
    }

    public final on getSource() {
        return this.source;
    }

    public final boolean isLocallyInitiated() {
        return this.connection.client == ((this.id & 1) == 1);
    }

    public final boolean isOpen() {
        boolean z = false;
        synchronized (this) {
            if (this.errorCode == null) {
                if (!(this.source.finished || this.source.closed) || (!(this.sink.finished || this.sink.closed) || this.responseHeaders == null)) {
                    z = true;
                }
            }
        }
        return z;
    }

    public final oo readTimeout() {
        return this.readTimeout;
    }

    final void receiveData(ny nyVar, int i) throws IOException {
        if ($assertionsDisabled || !Thread.holdsLock(this)) {
            this.source.receive(nyVar, (long) i);
            return;
        }
        throw new AssertionError();
    }

    final void receiveFin() {
        if ($assertionsDisabled || !Thread.holdsLock(this)) {
            boolean isOpen;
            synchronized (this) {
                this.source.finished = true;
                isOpen = isOpen();
                notifyAll();
            }
            if (!isOpen) {
                this.connection.removeStream(this.id);
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    final void receiveHeaders(List<Header> list, HeadersMode headersMode) {
        if ($assertionsDisabled || !Thread.holdsLock(this)) {
            ErrorCode errorCode = null;
            boolean z = true;
            synchronized (this) {
                if (this.responseHeaders == null) {
                    if (headersMode.failIfHeadersAbsent()) {
                        errorCode = ErrorCode.PROTOCOL_ERROR;
                    } else {
                        this.responseHeaders = list;
                        z = isOpen();
                        notifyAll();
                    }
                } else if (headersMode.failIfHeadersPresent()) {
                    errorCode = ErrorCode.STREAM_IN_USE;
                } else {
                    List arrayList = new ArrayList();
                    arrayList.addAll(this.responseHeaders);
                    arrayList.addAll(list);
                    this.responseHeaders = arrayList;
                }
            }
            if (errorCode != null) {
                closeLater(errorCode);
                return;
            } else if (!z) {
                this.connection.removeStream(this.id);
                return;
            } else {
                return;
            }
        }
        throw new AssertionError();
    }

    final void receiveRstStream(ErrorCode errorCode) {
        synchronized (this) {
            if (this.errorCode == null) {
                this.errorCode = errorCode;
                notifyAll();
            }
        }
    }

    public final void reply(List<Header> list, boolean z) throws IOException {
        boolean z2 = true;
        if ($assertionsDisabled || !Thread.holdsLock(this)) {
            synchronized (this) {
                if (list == null) {
                    throw new NullPointerException("responseHeaders == null");
                } else if (this.responseHeaders != null) {
                    throw new IllegalStateException("reply already sent");
                } else {
                    this.responseHeaders = list;
                    if (z) {
                        z2 = false;
                    } else {
                        this.sink.finished = true;
                    }
                }
            }
            this.connection.writeSynReply(this.id, z2, list);
            if (z2) {
                this.connection.flush();
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    public final oo writeTimeout() {
        return this.writeTimeout;
    }
}
