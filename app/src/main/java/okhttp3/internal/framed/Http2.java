package okhttp3.internal.framed;

import defpackage.nw;
import defpackage.nx;
import defpackage.ny;
import defpackage.nz;
import defpackage.on;
import defpackage.oo;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.Protocol;
import okhttp3.internal.Util;
import okhttp3.internal.framed.FrameReader.Handler;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.crypto.tls.CipherSuite;

public final class Http2 implements Variant {
    private static final nz CONNECTION_PREFACE = nz.a("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
    static final byte FLAG_ACK = (byte) 1;
    static final byte FLAG_COMPRESSED = (byte) 32;
    static final byte FLAG_END_HEADERS = (byte) 4;
    static final byte FLAG_END_PUSH_PROMISE = (byte) 4;
    static final byte FLAG_END_STREAM = (byte) 1;
    static final byte FLAG_NONE = (byte) 0;
    static final byte FLAG_PADDED = (byte) 8;
    static final byte FLAG_PRIORITY = (byte) 32;
    static final int INITIAL_MAX_FRAME_SIZE = 16384;
    static final byte TYPE_CONTINUATION = (byte) 9;
    static final byte TYPE_DATA = (byte) 0;
    static final byte TYPE_GOAWAY = (byte) 7;
    static final byte TYPE_HEADERS = (byte) 1;
    static final byte TYPE_PING = (byte) 6;
    static final byte TYPE_PRIORITY = (byte) 2;
    static final byte TYPE_PUSH_PROMISE = (byte) 5;
    static final byte TYPE_RST_STREAM = (byte) 3;
    static final byte TYPE_SETTINGS = (byte) 4;
    static final byte TYPE_WINDOW_UPDATE = (byte) 8;
    private static final Logger logger = Logger.getLogger(FrameLogger.class.getName());

    static final class ContinuationSource implements on {
        byte flags;
        int left;
        int length;
        short padding;
        private final ny source;
        int streamId;

        public ContinuationSource(ny nyVar) {
            this.source = nyVar;
        }

        private void readContinuationHeader() throws IOException {
            int i = this.streamId;
            int access$300 = Http2.readMedium(this.source);
            this.left = access$300;
            this.length = access$300;
            byte f = (byte) (this.source.f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
            this.flags = (byte) (this.source.f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
            if (Http2.logger.isLoggable(Level.FINE)) {
                Http2.logger.fine(FrameLogger.formatHeader(true, this.streamId, this.length, f, this.flags));
            }
            this.streamId = this.source.h() & Integer.MAX_VALUE;
            if (f != Http2.TYPE_CONTINUATION) {
                throw Http2.ioException("%s != TYPE_CONTINUATION", Byte.valueOf(f));
            } else if (this.streamId != i) {
                throw Http2.ioException("TYPE_CONTINUATION streamId changed", new Object[0]);
            }
        }

        public final void close() throws IOException {
        }

        public final long read(nw nwVar, long j) throws IOException {
            while (this.left == 0) {
                this.source.g((long) this.padding);
                this.padding = (short) 0;
                if ((this.flags & 4) != 0) {
                    return -1;
                }
                readContinuationHeader();
            }
            long read = this.source.read(nwVar, Math.min(j, (long) this.left));
            if (read == -1) {
                return -1;
            }
            this.left = (int) (((long) this.left) - read);
            return read;
        }

        public final oo timeout() {
            return this.source.timeout();
        }
    }

    static final class FrameLogger {
        private static final String[] BINARY = new String[256];
        private static final String[] FLAGS = new String[64];
        private static final String[] TYPES = new String[]{"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};

        static {
            int i;
            int i2 = 0;
            for (i = 0; i < BINARY.length; i++) {
                BINARY[i] = Util.format("%8s", Integer.toBinaryString(i)).replace(' ', '0');
            }
            FLAGS[0] = "";
            FLAGS[1] = "END_STREAM";
            int[] iArr = new int[]{1};
            FLAGS[8] = "PADDED";
            for (i = 0; i <= 0; i++) {
                int i3 = iArr[i];
                FLAGS[i3 | 8] = FLAGS[i3] + "|PADDED";
            }
            FLAGS[4] = "END_HEADERS";
            FLAGS[32] = "PRIORITY";
            FLAGS[36] = "END_HEADERS|PRIORITY";
            for (i3 = 0; i3 < 3; i3++) {
                int i4 = new int[]{4, 32, 36}[i3];
                for (i = 0; i <= 0; i++) {
                    int i5 = iArr[i];
                    FLAGS[i5 | i4] = FLAGS[i5] + '|' + FLAGS[i4];
                    FLAGS[(i5 | i4) | 8] = FLAGS[i5] + '|' + FLAGS[i4] + "|PADDED";
                }
            }
            while (i2 < FLAGS.length) {
                if (FLAGS[i2] == null) {
                    FLAGS[i2] = BINARY[i2];
                }
                i2++;
            }
        }

        FrameLogger() {
        }

        static String formatFlags(byte b, byte b2) {
            if (b2 == (byte) 0) {
                return "";
            }
            switch (b) {
                case (byte) 2:
                case (byte) 3:
                case (byte) 7:
                case (byte) 8:
                    return BINARY[b2];
                case (byte) 4:
                case (byte) 6:
                    return b2 == (byte) 1 ? "ACK" : BINARY[b2];
                default:
                    String str = b2 < FLAGS.length ? FLAGS[b2] : BINARY[b2];
                    return (b != Http2.TYPE_PUSH_PROMISE || (b2 & 4) == 0) ? (b != (byte) 0 || (b2 & 32) == 0) ? str : str.replace("PRIORITY", "COMPRESSED") : str.replace("HEADERS", "PUSH_PROMISE");
            }
        }

        static String formatHeader(boolean z, int i, int i2, byte b, byte b2) {
            String format = b < TYPES.length ? TYPES[b] : Util.format("0x%02x", Byte.valueOf(b));
            String formatFlags = formatFlags(b, b2);
            String str = z ? "<<" : ">>";
            return Util.format("%s 0x%08x %5d %-13s %s", str, Integer.valueOf(i), Integer.valueOf(i2), format, formatFlags);
        }
    }

    static final class Reader implements FrameReader {
        private final boolean client;
        private final ContinuationSource continuation = new ContinuationSource(this.source);
        final Reader hpackReader;
        private final ny source;

        Reader(ny nyVar, int i, boolean z) {
            this.source = nyVar;
            this.client = z;
            this.hpackReader = new Reader(i, this.continuation);
        }

        private void readData(Handler handler, int i, byte b, int i2) throws IOException {
            short s = (short) 1;
            short s2 = (short) 0;
            boolean z = (b & 1) != 0;
            if ((b & 32) == 0) {
                s = (short) 0;
            }
            if (s != (short) 0) {
                throw Http2.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
            }
            if ((b & 8) != 0) {
                s2 = (short) (this.source.f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
            }
            handler.data(z, i2, this.source, Http2.lengthWithoutPadding(i, b, s2));
            this.source.g((long) s2);
        }

        private void readGoAway(Handler handler, int i, byte b, int i2) throws IOException {
            if (i < 8) {
                throw Http2.ioException("TYPE_GOAWAY length < 8: %s", Integer.valueOf(i));
            } else if (i2 != 0) {
                throw Http2.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
            } else {
                int h = this.source.h();
                int i3 = i - 8;
                ErrorCode fromHttp2 = ErrorCode.fromHttp2(this.source.h());
                if (fromHttp2 == null) {
                    throw Http2.ioException("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(r0));
                }
                nz nzVar = nz.b;
                if (i3 > 0) {
                    nzVar = this.source.d((long) i3);
                }
                handler.goAway(h, fromHttp2, nzVar);
            }
        }

        private List<Header> readHeaderBlock(int i, short s, byte b, int i2) throws IOException {
            ContinuationSource continuationSource = this.continuation;
            this.continuation.left = i;
            continuationSource.length = i;
            this.continuation.padding = s;
            this.continuation.flags = b;
            this.continuation.streamId = i2;
            this.hpackReader.readHeaders();
            return this.hpackReader.getAndResetHeaderList();
        }

        private void readHeaders(Handler handler, int i, byte b, int i2) throws IOException {
            if (i2 == 0) {
                throw Http2.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
            }
            boolean z = (b & 1) != 0;
            short f = (b & 8) != 0 ? (short) (this.source.f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) : (short) 0;
            if ((b & 32) != 0) {
                readPriority(handler, i2);
                i -= 5;
            }
            handler.headers(false, z, i2, -1, readHeaderBlock(Http2.lengthWithoutPadding(i, b, f), f, b, i2), HeadersMode.HTTP_20_HEADERS);
        }

        private void readPing(Handler handler, int i, byte b, int i2) throws IOException {
            boolean z = false;
            if (i != 8) {
                throw Http2.ioException("TYPE_PING length != 8: %s", Integer.valueOf(i));
            } else if (i2 != 0) {
                throw Http2.ioException("TYPE_PING streamId != 0", new Object[0]);
            } else {
                int h = this.source.h();
                int h2 = this.source.h();
                if ((b & 1) != 0) {
                    z = true;
                }
                handler.ping(z, h, h2);
            }
        }

        private void readPriority(Handler handler, int i) throws IOException {
            int h = this.source.h();
            handler.priority(i, h & Integer.MAX_VALUE, (this.source.f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + 1, (PKIFailureInfo.systemUnavail & h) != 0);
        }

        private void readPriority(Handler handler, int i, byte b, int i2) throws IOException {
            if (i != 5) {
                throw Http2.ioException("TYPE_PRIORITY length: %d != 5", Integer.valueOf(i));
            } else if (i2 == 0) {
                throw Http2.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
            } else {
                readPriority(handler, i2);
            }
        }

        private void readPushPromise(Handler handler, int i, byte b, int i2) throws IOException {
            short s = (short) 0;
            if (i2 == 0) {
                throw Http2.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
            }
            if ((b & 8) != 0) {
                s = (short) (this.source.f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
            }
            handler.pushPromise(i2, this.source.h() & Integer.MAX_VALUE, readHeaderBlock(Http2.lengthWithoutPadding(i - 4, b, s), s, b, i2));
        }

        private void readRstStream(Handler handler, int i, byte b, int i2) throws IOException {
            if (i != 4) {
                throw Http2.ioException("TYPE_RST_STREAM length: %d != 4", Integer.valueOf(i));
            } else if (i2 == 0) {
                throw Http2.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
            } else {
                ErrorCode fromHttp2 = ErrorCode.fromHttp2(this.source.h());
                if (fromHttp2 == null) {
                    throw Http2.ioException("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(r0));
                } else {
                    handler.rstStream(i2, fromHttp2);
                }
            }
        }

        private void readSettings(Handler handler, int i, byte b, int i2) throws IOException {
            if (i2 != 0) {
                throw Http2.ioException("TYPE_SETTINGS streamId != 0", new Object[0]);
            } else if ((b & 1) != 0) {
                if (i != 0) {
                    throw Http2.ioException("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
                }
                handler.ackSettings();
            } else if (i % 6 != 0) {
                throw Http2.ioException("TYPE_SETTINGS length %% 6 != 0: %s", Integer.valueOf(i));
            } else {
                Settings settings = new Settings();
                for (int i3 = 0; i3 < i; i3 += 6) {
                    int g = this.source.g();
                    int h = this.source.h();
                    switch (g) {
                        case 2:
                            if (!(h == 0 || h == 1)) {
                                throw Http2.ioException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                            }
                        case 3:
                            g = 4;
                            break;
                        case 4:
                            g = 7;
                            if (h >= 0) {
                                break;
                            }
                            throw Http2.ioException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                        case 5:
                            if (h >= 16384 && h <= 16777215) {
                                break;
                            }
                            throw Http2.ioException("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", Integer.valueOf(h));
                        default:
                            break;
                    }
                    settings.set(g, 0, h);
                }
                handler.settings(false, settings);
                if (settings.getHeaderTableSize() >= 0) {
                    this.hpackReader.headerTableSizeSetting(settings.getHeaderTableSize());
                }
            }
        }

        private void readWindowUpdate(Handler handler, int i, byte b, int i2) throws IOException {
            if (i != 4) {
                throw Http2.ioException("TYPE_WINDOW_UPDATE length !=4: %s", Integer.valueOf(i));
            }
            long h = ((long) this.source.h()) & 2147483647L;
            if (h == 0) {
                throw Http2.ioException("windowSizeIncrement was 0", Long.valueOf(h));
            } else {
                handler.windowUpdate(i2, h);
            }
        }

        public final void close() throws IOException {
            this.source.close();
        }

        public final boolean nextFrame(Handler handler) throws IOException {
            try {
                this.source.a(9);
                int access$300 = Http2.readMedium(this.source);
                if (access$300 < 0 || access$300 > 16384) {
                    throw Http2.ioException("FRAME_SIZE_ERROR: %s", Integer.valueOf(access$300));
                }
                byte f = (byte) (this.source.f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
                byte f2 = (byte) (this.source.f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
                int h = this.source.h() & Integer.MAX_VALUE;
                if (Http2.logger.isLoggable(Level.FINE)) {
                    Http2.logger.fine(FrameLogger.formatHeader(true, h, access$300, f, f2));
                }
                switch (f) {
                    case (byte) 0:
                        readData(handler, access$300, f2, h);
                        return true;
                    case (byte) 1:
                        readHeaders(handler, access$300, f2, h);
                        return true;
                    case (byte) 2:
                        readPriority(handler, access$300, f2, h);
                        return true;
                    case (byte) 3:
                        readRstStream(handler, access$300, f2, h);
                        return true;
                    case (byte) 4:
                        readSettings(handler, access$300, f2, h);
                        return true;
                    case (byte) 5:
                        readPushPromise(handler, access$300, f2, h);
                        return true;
                    case (byte) 6:
                        readPing(handler, access$300, f2, h);
                        return true;
                    case (byte) 7:
                        readGoAway(handler, access$300, f2, h);
                        return true;
                    case (byte) 8:
                        readWindowUpdate(handler, access$300, f2, h);
                        return true;
                    default:
                        this.source.g((long) access$300);
                        return true;
                }
            } catch (IOException e) {
                return false;
            }
        }

        public final void readConnectionPreface() throws IOException {
            if (!this.client) {
                nz d = this.source.d((long) Http2.CONNECTION_PREFACE.e());
                if (Http2.logger.isLoggable(Level.FINE)) {
                    Http2.logger.fine(Util.format("<< CONNECTION %s", d.c()));
                }
                if (!Http2.CONNECTION_PREFACE.equals(d)) {
                    throw Http2.ioException("Expected a connection header but was %s", d.a());
                }
            }
        }
    }

    static final class Writer implements FrameWriter {
        private final boolean client;
        private boolean closed;
        private final nw hpackBuffer = new nw();
        private final Writer hpackWriter = new Writer(this.hpackBuffer);
        private int maxFrameSize = 16384;
        private final nx sink;

        Writer(nx nxVar, boolean z) {
            this.sink = nxVar;
            this.client = z;
        }

        private void writeContinuationFrames(int i, long j) throws IOException {
            while (j > 0) {
                int min = (int) Math.min((long) this.maxFrameSize, j);
                j -= (long) min;
                frameHeader(i, min, Http2.TYPE_CONTINUATION, j == 0 ? (byte) 4 : (byte) 0);
                this.sink.write(this.hpackBuffer, (long) min);
            }
        }

        public final void ackSettings(Settings settings) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                this.maxFrameSize = settings.getMaxFrameSize(this.maxFrameSize);
                frameHeader(0, 0, (byte) 4, (byte) 1);
                this.sink.flush();
            }
        }

        public final void close() throws IOException {
            synchronized (this) {
                this.closed = true;
                this.sink.close();
            }
        }

        public final void connectionPreface() throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (this.client) {
                    if (Http2.logger.isLoggable(Level.FINE)) {
                        Http2.logger.fine(Util.format(">> CONNECTION %s", Http2.CONNECTION_PREFACE.c()));
                    }
                    this.sink.b(Http2.CONNECTION_PREFACE.f());
                    this.sink.flush();
                }
            }
        }

        public final void data(boolean z, int i, nw nwVar, int i2) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                byte b = (byte) 0;
                if (z) {
                    b = (byte) 1;
                }
                dataFrame(i, b, nwVar, i2);
            }
        }

        final void dataFrame(int i, byte b, nw nwVar, int i2) throws IOException {
            frameHeader(i, i2, (byte) 0, b);
            if (i2 > 0) {
                this.sink.write(nwVar, (long) i2);
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

        final void frameHeader(int i, int i2, byte b, byte b2) throws IOException {
            if (Http2.logger.isLoggable(Level.FINE)) {
                Http2.logger.fine(FrameLogger.formatHeader(false, i, i2, b, b2));
            }
            if (i2 > this.maxFrameSize) {
                throw Http2.illegalArgument("FRAME_SIZE_ERROR length > %d: %d", Integer.valueOf(this.maxFrameSize), Integer.valueOf(i2));
            } else if ((PKIFailureInfo.systemUnavail & i) != 0) {
                throw Http2.illegalArgument("reserved bit set: %s", Integer.valueOf(i));
            } else {
                Http2.writeMedium(this.sink, i2);
                this.sink.h(b & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
                this.sink.h(b2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
                this.sink.f(Integer.MAX_VALUE & i);
            }
        }

        public final void goAway(int i, ErrorCode errorCode, byte[] bArr) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                } else if (errorCode.httpCode == -1) {
                    throw Http2.illegalArgument("errorCode.httpCode == -1", new Object[0]);
                } else {
                    frameHeader(0, bArr.length + 8, Http2.TYPE_GOAWAY, (byte) 0);
                    this.sink.f(i);
                    this.sink.f(errorCode.httpCode);
                    if (bArr.length > 0) {
                        this.sink.b(bArr);
                    }
                    this.sink.flush();
                }
            }
        }

        public final void headers(int i, List<Header> list) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                headers(false, i, list);
            }
        }

        final void headers(boolean z, int i, List<Header> list) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.hpackWriter.writeHeaders(list);
            long j = this.hpackBuffer.b;
            int min = (int) Math.min((long) this.maxFrameSize, j);
            byte b = j == ((long) min) ? (byte) 4 : (byte) 0;
            if (z) {
                b = (byte) (b | 1);
            }
            frameHeader(i, min, (byte) 1, b);
            this.sink.write(this.hpackBuffer, (long) min);
            if (j > ((long) min)) {
                writeContinuationFrames(i, j - ((long) min));
            }
        }

        public final int maxDataLength() {
            return this.maxFrameSize;
        }

        public final void ping(boolean z, int i, int i2) throws IOException {
            byte b = (byte) 0;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                if (z) {
                    b = (byte) 1;
                }
                frameHeader(0, 8, Http2.TYPE_PING, b);
                this.sink.f(i);
                this.sink.f(i2);
                this.sink.flush();
            }
        }

        public final void pushPromise(int i, int i2, List<Header> list) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                this.hpackWriter.writeHeaders(list);
                long j = this.hpackBuffer.b;
                int min = (int) Math.min((long) (this.maxFrameSize - 4), j);
                frameHeader(i, min + 4, Http2.TYPE_PUSH_PROMISE, j == ((long) min) ? (byte) 4 : (byte) 0);
                this.sink.f(Integer.MAX_VALUE & i2);
                this.sink.write(this.hpackBuffer, (long) min);
                if (j > ((long) min)) {
                    writeContinuationFrames(i, j - ((long) min));
                }
            }
        }

        public final void rstStream(int i, ErrorCode errorCode) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                } else if (errorCode.httpCode == -1) {
                    throw new IllegalArgumentException();
                } else {
                    frameHeader(i, 4, Http2.TYPE_RST_STREAM, (byte) 0);
                    this.sink.f(errorCode.httpCode);
                    this.sink.flush();
                }
            }
        }

        public final void settings(Settings settings) throws IOException {
            int i = 0;
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                frameHeader(0, settings.size() * 6, (byte) 4, (byte) 0);
                while (i < 10) {
                    if (settings.isSet(i)) {
                        int i2 = i == 4 ? 3 : i == 7 ? 4 : i;
                        this.sink.g(i2);
                        this.sink.f(settings.get(i));
                    }
                    i++;
                }
                this.sink.flush();
            }
        }

        public final void synReply(boolean z, int i, List<Header> list) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                }
                headers(z, i, list);
            }
        }

        public final void synStream(boolean z, boolean z2, int i, int i2, List<Header> list) throws IOException {
            synchronized (this) {
                if (z2) {
                    throw new UnsupportedOperationException();
                } else if (this.closed) {
                    throw new IOException("closed");
                } else {
                    headers(z, i, list);
                }
            }
        }

        public final void windowUpdate(int i, long j) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    throw new IOException("closed");
                } else if (j == 0 || j > 2147483647L) {
                    throw Http2.illegalArgument("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", Long.valueOf(j));
                } else {
                    frameHeader(i, 4, (byte) 8, (byte) 0);
                    this.sink.f((int) j);
                    this.sink.flush();
                }
            }
        }
    }

    private static IllegalArgumentException illegalArgument(String str, Object... objArr) {
        throw new IllegalArgumentException(Util.format(str, objArr));
    }

    private static IOException ioException(String str, Object... objArr) throws IOException {
        throw new IOException(Util.format(str, objArr));
    }

    private static int lengthWithoutPadding(int i, byte b, short s) throws IOException {
        if ((b & 8) != 0) {
            short s2 = i - 1;
        }
        if (s <= s2) {
            return (short) (s2 - s);
        }
        throw ioException("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(s), Integer.valueOf(s2));
    }

    private static int readMedium(ny nyVar) throws IOException {
        return (((nyVar.f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16) | ((nyVar.f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) | (nyVar.f() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
    }

    private static void writeMedium(nx nxVar, int i) throws IOException {
        nxVar.h((i >>> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        nxVar.h((i >>> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        nxVar.h(i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
    }

    public final Protocol getProtocol() {
        return Protocol.HTTP_2;
    }

    public final FrameReader newReader(ny nyVar, boolean z) {
        return new Reader(nyVar, PKIFailureInfo.certConfirmed, z);
    }

    public final FrameWriter newWriter(nx nxVar, boolean z) {
        return new Writer(nxVar, z);
    }
}
