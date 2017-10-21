package okhttp3.internal.http;

import defpackage.nw;
import defpackage.om;
import defpackage.oo;
import java.io.IOException;
import java.net.ProtocolException;
import okhttp3.internal.Util;

public final class RetryableSink implements om {
    private boolean closed;
    private final nw content;
    private final int limit;

    public RetryableSink() {
        this(-1);
    }

    public RetryableSink(int i) {
        this.content = new nw();
        this.limit = i;
    }

    public final void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            if (this.content.b < ((long) this.limit)) {
                throw new ProtocolException("content-length promised " + this.limit + " bytes, but received " + this.content.b);
            }
        }
    }

    public final long contentLength() throws IOException {
        return this.content.b;
    }

    public final void flush() throws IOException {
    }

    public final oo timeout() {
        return oo.NONE;
    }

    public final void write(nw nwVar, long j) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        Util.checkOffsetAndCount(nwVar.b, 0, j);
        if (this.limit == -1 || this.content.b <= ((long) this.limit) - j) {
            this.content.write(nwVar, j);
            return;
        }
        throw new ProtocolException("exceeded content-length limit of " + this.limit + " bytes");
    }

    public final void writeToSocket(om omVar) throws IOException {
        nw nwVar = new nw();
        this.content.a(nwVar, 0, this.content.b);
        omVar.write(nwVar, nwVar.b);
    }
}
