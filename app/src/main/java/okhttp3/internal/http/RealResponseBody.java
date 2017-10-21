package okhttp3.internal.http;

import defpackage.ny;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;

public final class RealResponseBody extends ResponseBody {
    private final Headers headers;
    private final ny source;

    public RealResponseBody(Headers headers, ny nyVar) {
        this.headers = headers;
        this.source = nyVar;
    }

    public final long contentLength() {
        return OkHeaders.contentLength(this.headers);
    }

    public final MediaType contentType() {
        String str = this.headers.get("Content-Type");
        return str != null ? MediaType.parse(str) : null;
    }

    public final ny source() {
        return this.source;
    }
}
