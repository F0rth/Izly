package okhttp3.internal.http;

import defpackage.om;
import java.io.IOException;

public interface CacheRequest {
    void abort();

    om body() throws IOException;
}
