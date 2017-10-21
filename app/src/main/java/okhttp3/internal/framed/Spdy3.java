package okhttp3.internal.framed;

import defpackage.nw;
import defpackage.nx;
import defpackage.ny;
import defpackage.nz;
import defpackage.oa;
import defpackage.og;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.util.List;
import java.util.zip.Deflater;
import okhttp3.Protocol;
import okhttp3.internal.Util;
import okhttp3.internal.framed.FrameReader.Handler;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.crypto.tls.CipherSuite;

public final class Spdy3 implements Variant {
    static final byte[] DICTIONARY;
    static final int FLAG_FIN = 1;
    static final int FLAG_UNIDIRECTIONAL = 2;
    static final int TYPE_DATA = 0;
    static final int TYPE_GOAWAY = 7;
    static final int TYPE_HEADERS = 8;
    static final int TYPE_PING = 6;
    static final int TYPE_RST_STREAM = 3;
    static final int TYPE_SETTINGS = 4;
    static final int TYPE_SYN_REPLY = 2;
    static final int TYPE_SYN_STREAM = 1;
    static final int TYPE_WINDOW_UPDATE = 9;
    static final int VERSION = 3;

    static final class Reader implements FrameReader {
        private final boolean client;
        private final NameValueBlockReader headerBlockReader = new NameValueBlockReader(this.source);
        private final ny source;

        Reader(ny nyVar, boolean z) {
            this.source = nyVar;
            this.client = z;
        }

        private static IOException ioException(String str, Object... objArr) throws IOException {
            throw new IOException(Util.format(str, objArr));
        }

        private void readGoAway(Handler handler, int i, int i2) throws IOException {
            if (i2 != 8) {
                throw ioException("TYPE_GOAWAY length: %d != 8", Integer.valueOf(i2));
            }
            int h = this.source.h();
            ErrorCode fromSpdyGoAway = ErrorCode.fromSpdyGoAway(this.source.h());
            if (fromSpdyGoAway == null) {
                throw ioException("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(r1));
            } else {
                handler.goAway(h & Integer.MAX_VALUE, fromSpdyGoAway, nz.b);
            }
        }

        private void readHeaders(Handler handler, int i, int i2) throws IOException {
            handler.headers(false, false, this.source.h() & Integer.MAX_VALUE, -1, this.headerBlockReader.readNameValueBlock(i2 - 4), HeadersMode.SPDY_HEADERS);
        }

        private void readPing(Handler handler, int i, int i2) throws IOException {
            boolean z = true;
            if (i2 != 4) {
                throw ioException("TYPE_PING length: %d != 4", Integer.valueOf(i2));
            }
            int h = this.source.h();
            if (this.client != ((h & 1) == 1)) {
                z = false;
            }
            handler.ping(z, h, 0);
        }

        private void readRstStream(Handler handler, int i, int i2) throws IOException {
            if (i2 != 8) {
                throw ioException("TYPE_RST_STREAM length: %d != 8", Integer.valueOf(i2));
            }
            int h = this.source.h();
            ErrorCode fromSpdy3Rst = ErrorCode.fromSpdy3Rst(this.source.h());
            if (fromSpdy3Rst == null) {
                throw ioException("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(r1));
            } else {
                handler.rstStream(h & Integer.MAX_VALUE, fromSpdy3Rst);
            }
        }

        private void readSettings(Handler handler, int i, int i2) throws IOException {
            boolean z = false;
            int h = this.source.h();
            if (i2 != (h * 8) + 4) {
                throw ioException("TYPE_SETTINGS length: %d != 4 + 8 * %d", Integer.valueOf(i2), Integer.valueOf(h));
            }
            Settings settings = new Settings();
            for (int i3 = 0; i3 < h; i3++) {
                int h2 = this.source.h();
                settings.set(16777215 & h2, (h2 & -16777216) >>> 24, this.source.h());
            }
            if ((i & 1) != 0) {
                z = true;
            }
            handler.settings(z, settings);
        }

        private void readSynReply(Handler handler, int i, int i2) throws IOException {
            int h = this.source.h();
            handler.headers(false, (i & 1) != 0, Integer.MAX_VALUE & h, -1, this.headerBlockReader.readNameValueBlock(i2 - 4), HeadersMode.SPDY_REPLY);
        }

        private void readSynStream(Handler handler, int i, int i2) throws IOException {
            boolean z = false;
            int h = this.source.h();
            int h2 = this.source.h();
            this.source.g();
            List readNameValueBlock = this.headerBlockReader.readNameValueBlock(i2 - 10);
            boolean z2 = (i & 1) != 0;
            if ((i & 2) != 0) {
                z = true;
            }
            handler.headers(z, z2, h & Integer.MAX_VALUE, h2 & Integer.MAX_VALUE, readNameValueBlock, HeadersMode.SPDY_SYN_STREAM);
        }

        private void readWindowUpdate(Handler handler, int i, int i2) throws IOException {
            if (i2 != 8) {
                throw ioException("TYPE_WINDOW_UPDATE length: %d != 8", Integer.valueOf(i2));
            }
            int h = this.source.h();
            long h2 = (long) (this.source.h() & Integer.MAX_VALUE);
            if (h2 == 0) {
                throw ioException("windowSizeIncrement was 0", Long.valueOf(h2));
            } else {
                handler.windowUpdate(h & Integer.MAX_VALUE, h2);
            }
        }

        public final void close() throws IOException {
            this.headerBlockReader.close();
        }

        public final boolean nextFrame(Handler handler) throws IOException {
            boolean z = false;
            try {
                int h = this.source.h();
                int h2 = this.source.h();
                int i = (-16777216 & h2) >>> 24;
                h2 &= 16777215;
                if ((PKIFailureInfo.systemUnavail & h) != 0) {
                    int i2 = (2147418112 & h) >>> 16;
                    if (i2 != 3) {
                        throw new ProtocolException("version != 3: " + i2);
                    }
                    switch (65535 & h) {
                        case 1:
                            readSynStream(handler, i, h2);
                            return true;
                        case 2:
                            readSynReply(handler, i, h2);
                            return true;
                        case 3:
                            readRstStream(handler, i, h2);
                            return true;
                        case 4:
                            readSettings(handler, i, h2);
                            return true;
                        case 6:
                            readPing(handler, i, h2);
                            return true;
                        case 7:
                            readGoAway(handler, i, h2);
                            return true;
                        case 8:
                            readHeaders(handler, i, h2);
                            return true;
                        case 9:
                            readWindowUpdate(handler, i, h2);
                            return true;
                        default:
                            this.source.g((long) h2);
                            return true;
                    }
                }
                if ((i & 1) != 0) {
                    z = true;
                }
                handler.data(z, Integer.MAX_VALUE & h, this.source, h2);
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        public final void readConnectionPreface() {
        }
    }

    static final class Writer implements FrameWriter {
        private final boolean client;
        private boolean closed;
        private final nw headerBlockBuffer = new nw();
        private final nx headerBlockOut;
        private final nx sink;

        Writer(nx nxVar, boolean z) {
            this.sink = nxVar;
            this.client = z;
            Deflater deflater = new Deflater();
            deflater.setDictionary(Spdy3.DICTIONARY);
            this.headerBlockOut = og.a(new oa(this.headerBlockBuffer, deflater));
        }

        private void writeNameValueBlockToBuffer(List<Header> list) throws IOException {
            this.headerBlockOut.f(list.size());
            int size = list.size();
            for (int i = 0; i < size; i++) {
                nz nzVar = ((Header) list.get(i)).name;
                this.headerBlockOut.f(nzVar.e());
                this.headerBlockOut.b(nzVar);
                nzVar = ((Header) list.get(i)).value;
                this.headerBlockOut.f(nzVar.e());
                this.headerBlockOut.b(nzVar);
            }
            this.headerBlockOut.flush();
        }

        public final void ackSettings(Settings settings) {
        }

        public final void close() throws IOException {
            synchronized (this) {
                this.closed = true;
                Util.closeAll(this.sink, this.headerBlockOut);
            }
        }

        public final void connectionPreface() {
            synchronized (this) {
            }
        }

        public final void data(boolean z, int i, nw nwVar, int i2) throws IOException {
            synchronized (this) {
                sendDataFrame(i, z ? 1 : 0, nwVar, i2);
            }
        }

        public final void flush() throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                this.sink.flush();
            }
        }

        public final void goAway(int i, ErrorCode errorCode, byte[] bArr) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                } else if (errorCode.spdyGoAwayCode == -1) {
                    throw new IllegalArgumentException("errorCode.spdyGoAwayCode == -1");
                } else {
                    this.sink.f(-2147287033);
                    this.sink.f(8);
                    this.sink.f(i);
                    this.sink.f(errorCode.spdyGoAwayCode);
                    this.sink.flush();
                }
            }
        }

        public final void headers(int i, List<Header> list) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                writeNameValueBlockToBuffer(list);
                int i2 = (int) (this.headerBlockBuffer.b + 4);
                this.sink.f(-2147287032);
                this.sink.f((i2 & 16777215) | 0);
                this.sink.f(Integer.MAX_VALUE & i);
                this.sink.a(this.headerBlockBuffer);
            }
        }

        public final int maxDataLength() {
            return 16383;
        }

        public final void ping(boolean z, int i, int i2) throws IOException {
            boolean z2 = true;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (this.client == ((i & 1) == 1)) {
                    z2 = false;
                }
                if (z != z2) {
                    throw new IllegalArgumentException("payload != reply");
                }
                this.sink.f(-2147287034);
                this.sink.f(4);
                this.sink.f(i);
                this.sink.flush();
            }
        }

        public final void pushPromise(int i, int i2, List<Header> list) throws IOException {
        }

        public final void rstStream(int i, ErrorCode errorCode) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                } else if (errorCode.spdyRstCode == -1) {
                    throw new IllegalArgumentException();
                } else {
                    this.sink.f(-2147287037);
                    this.sink.f(8);
                    this.sink.f(Integer.MAX_VALUE & i);
                    this.sink.f(errorCode.spdyRstCode);
                    this.sink.flush();
                }
            }
        }

        final void sendDataFrame(int i, int i2, nw nwVar, int i3) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            } else if (((long) i3) > 16777215) {
                throw new IllegalArgumentException("FRAME_TOO_LARGE max size is 16Mib: " + i3);
            } else {
                this.sink.f(Integer.MAX_VALUE & i);
                this.sink.f(((i2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 24) | (16777215 & i3));
                if (i3 > 0) {
                    this.sink.write(nwVar, (long) i3);
                }
            }
        }

        public final void settings(Settings settings) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                int size = settings.size();
                this.sink.f(-2147287036);
                this.sink.f((((size * 8) + 4) & 16777215) | 0);
                this.sink.f(size);
                for (size = 0; size <= 10; size++) {
                    if (settings.isSet(size)) {
                        this.sink.f(((settings.flags(size) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 24) | (size & 16777215));
                        this.sink.f(settings.get(size));
                    }
                }
                this.sink.flush();
            }
        }

        public final void synReply(boolean z, int i, List<Header> list) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                writeNameValueBlockToBuffer(list);
                int i2 = z ? 1 : 0;
                int i3 = (int) (this.headerBlockBuffer.b + 4);
                this.sink.f(-2147287038);
                this.sink.f(((i2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 24) | (i3 & 16777215));
                this.sink.f(Integer.MAX_VALUE & i);
                this.sink.a(this.headerBlockBuffer);
                this.sink.flush();
            }
        }

        public final void synStream(boolean z, boolean z2, int i, int i2, List<Header> list) throws IOException {
            int i3 = 0;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                writeNameValueBlockToBuffer(list);
                int i4 = (int) (10 + this.headerBlockBuffer.b);
                int i5 = z ? 1 : 0;
                if (z2) {
                    i3 = 2;
                }
                this.sink.f(-2147287039);
                this.sink.f((((i3 | i5) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 24) | (16777215 & i4));
                this.sink.f(i & Integer.MAX_VALUE);
                this.sink.f(i2 & Integer.MAX_VALUE);
                this.sink.g(0);
                this.sink.a(this.headerBlockBuffer);
                this.sink.flush();
            }
        }

        public final void windowUpdate(int i, long j) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                } else if (j == 0 || j > 2147483647L) {
                    throw new IllegalArgumentException("windowSizeIncrement must be between 1 and 0x7fffffff: " + j);
                } else {
                    this.sink.f(-2147287031);
                    this.sink.f(8);
                    this.sink.f(i);
                    this.sink.f((int) j);
                    this.sink.flush();
                }
            }
        }
    }

    static {
        try {
            DICTIONARY = "\u0000\u0000\u0000\u0007options\u0000\u0000\u0000\u0004head\u0000\u0000\u0000\u0004post\u0000\u0000\u0000\u0003put\u0000\u0000\u0000\u0006delete\u0000\u0000\u0000\u0005trace\u0000\u0000\u0000\u0006accept\u0000\u0000\u0000\u000eaccept-charset\u0000\u0000\u0000\u000faccept-encoding\u0000\u0000\u0000\u000faccept-language\u0000\u0000\u0000\raccept-ranges\u0000\u0000\u0000\u0003age\u0000\u0000\u0000\u0005allow\u0000\u0000\u0000\rauthorization\u0000\u0000\u0000\rcache-control\u0000\u0000\u0000\nconnection\u0000\u0000\u0000\fcontent-base\u0000\u0000\u0000\u0010content-encoding\u0000\u0000\u0000\u0010content-language\u0000\u0000\u0000\u000econtent-length\u0000\u0000\u0000\u0010content-location\u0000\u0000\u0000\u000bcontent-md5\u0000\u0000\u0000\rcontent-range\u0000\u0000\u0000\fcontent-type\u0000\u0000\u0000\u0004date\u0000\u0000\u0000\u0004etag\u0000\u0000\u0000\u0006expect\u0000\u0000\u0000\u0007expires\u0000\u0000\u0000\u0004from\u0000\u0000\u0000\u0004host\u0000\u0000\u0000\bif-match\u0000\u0000\u0000\u0011if-modified-since\u0000\u0000\u0000\rif-none-match\u0000\u0000\u0000\bif-range\u0000\u0000\u0000\u0013if-unmodified-since\u0000\u0000\u0000\rlast-modified\u0000\u0000\u0000\blocation\u0000\u0000\u0000\fmax-forwards\u0000\u0000\u0000\u0006pragma\u0000\u0000\u0000\u0012proxy-authenticate\u0000\u0000\u0000\u0013proxy-authorization\u0000\u0000\u0000\u0005range\u0000\u0000\u0000\u0007referer\u0000\u0000\u0000\u000bretry-after\u0000\u0000\u0000\u0006server\u0000\u0000\u0000\u0002te\u0000\u0000\u0000\u0007trailer\u0000\u0000\u0000\u0011transfer-encoding\u0000\u0000\u0000\u0007upgrade\u0000\u0000\u0000\nuser-agent\u0000\u0000\u0000\u0004vary\u0000\u0000\u0000\u0003via\u0000\u0000\u0000\u0007warning\u0000\u0000\u0000\u0010www-authenticate\u0000\u0000\u0000\u0006method\u0000\u0000\u0000\u0003get\u0000\u0000\u0000\u0006status\u0000\u0000\u0000\u0006200 OK\u0000\u0000\u0000\u0007version\u0000\u0000\u0000\bHTTP/1.1\u0000\u0000\u0000\u0003url\u0000\u0000\u0000\u0006public\u0000\u0000\u0000\nset-cookie\u0000\u0000\u0000\nkeep-alive\u0000\u0000\u0000\u0006origin100101201202205206300302303304305306307402405406407408409410411412413414415416417502504505203 Non-Authoritative Information204 No Content301 Moved Permanently400 Bad Request401 Unauthorized403 Forbidden404 Not Found500 Internal Server Error501 Not Implemented503 Service UnavailableJan Feb Mar Apr May Jun Jul Aug Sept Oct Nov Dec 00:00:00 Mon, Tue, Wed, Thu, Fri, Sat, Sun, GMTchunked,text/html,image/png,image/jpg,image/gif,application/xml,application/xhtml+xml,text/plain,text/javascript,publicprivatemax-age=gzip,deflate,sdchcharset=utf-8charset=iso-8859-1,utf-,*,enq=0.".getBytes(Util.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }

    public final Protocol getProtocol() {
        return Protocol.SPDY_3;
    }

    public final FrameReader newReader(ny nyVar, boolean z) {
        return new Reader(nyVar, z);
    }

    public final FrameWriter newWriter(nx nxVar, boolean z) {
        return new Writer(nxVar, z);
    }
}
