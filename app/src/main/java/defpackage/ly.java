package defpackage;

import java.io.IOException;

public interface ly {
    void cancelTimeBasedFileRollOver();

    boolean rollFileOver() throws IOException;
}
