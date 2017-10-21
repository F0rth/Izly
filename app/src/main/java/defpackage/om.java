package defpackage;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

public interface om extends Closeable, Flushable {
    void close() throws IOException;

    void flush() throws IOException;

    oo timeout();

    void write(nw nwVar, long j) throws IOException;
}
