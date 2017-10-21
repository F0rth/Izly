package defpackage;

import java.io.IOException;

public interface lu<T> {
    byte[] toBytes(T t) throws IOException;
}
