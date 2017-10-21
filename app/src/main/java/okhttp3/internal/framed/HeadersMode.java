package okhttp3.internal.framed;

public enum HeadersMode {
    SPDY_SYN_STREAM,
    SPDY_REPLY,
    SPDY_HEADERS,
    HTTP_20_HEADERS;

    public final boolean failIfHeadersAbsent() {
        return this == SPDY_HEADERS;
    }

    public final boolean failIfHeadersPresent() {
        return this == SPDY_REPLY;
    }

    public final boolean failIfStreamAbsent() {
        return this == SPDY_REPLY || this == SPDY_HEADERS;
    }

    public final boolean failIfStreamPresent() {
        return this == SPDY_SYN_STREAM;
    }
}
