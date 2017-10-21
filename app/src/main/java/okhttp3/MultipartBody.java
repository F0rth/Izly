package okhttp3;

import defpackage.nw;
import defpackage.nx;
import defpackage.nz;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import okhttp3.internal.Util;

public final class MultipartBody extends RequestBody {
    public static final MediaType ALTERNATIVE = MediaType.parse("multipart/alternative");
    private static final byte[] COLONSPACE = new byte[]{(byte) 58, (byte) 32};
    private static final byte[] CRLF = new byte[]{(byte) 13, (byte) 10};
    private static final byte[] DASHDASH = new byte[]{(byte) 45, (byte) 45};
    public static final MediaType DIGEST = MediaType.parse("multipart/digest");
    public static final MediaType FORM = MediaType.parse("multipart/form-data");
    public static final MediaType MIXED = MediaType.parse("multipart/mixed");
    public static final MediaType PARALLEL = MediaType.parse("multipart/parallel");
    private final nz boundary;
    private long contentLength = -1;
    private final MediaType contentType;
    private final MediaType originalType;
    private final List<Part> parts;

    public static final class Builder {
        private final nz boundary;
        private final List<Part> parts;
        private MediaType type;

        public Builder() {
            this(UUID.randomUUID().toString());
        }

        public Builder(String str) {
            this.type = MultipartBody.MIXED;
            this.parts = new ArrayList();
            this.boundary = nz.a(str);
        }

        public final Builder addFormDataPart(String str, String str2) {
            return addPart(Part.createFormData(str, str2));
        }

        public final Builder addFormDataPart(String str, String str2, RequestBody requestBody) {
            return addPart(Part.createFormData(str, str2, requestBody));
        }

        public final Builder addPart(Headers headers, RequestBody requestBody) {
            return addPart(Part.create(headers, requestBody));
        }

        public final Builder addPart(Part part) {
            if (part == null) {
                throw new NullPointerException("part == null");
            }
            this.parts.add(part);
            return this;
        }

        public final Builder addPart(RequestBody requestBody) {
            return addPart(Part.create(requestBody));
        }

        public final MultipartBody build() {
            if (!this.parts.isEmpty()) {
                return new MultipartBody(this.boundary, this.type, this.parts);
            }
            throw new IllegalStateException("Multipart body must have at least one part.");
        }

        public final Builder setType(MediaType mediaType) {
            if (mediaType == null) {
                throw new NullPointerException("type == null");
            } else if (mediaType.type().equals("multipart")) {
                this.type = mediaType;
                return this;
            } else {
                throw new IllegalArgumentException("multipart != " + mediaType);
            }
        }
    }

    public static final class Part {
        private final RequestBody body;
        private final Headers headers;

        private Part(Headers headers, RequestBody requestBody) {
            this.headers = headers;
            this.body = requestBody;
        }

        public static Part create(Headers headers, RequestBody requestBody) {
            if (requestBody == null) {
                throw new NullPointerException("body == null");
            } else if (headers != null && headers.get("Content-Type") != null) {
                throw new IllegalArgumentException("Unexpected header: Content-Type");
            } else if (headers == null || headers.get("Content-Length") == null) {
                return new Part(headers, requestBody);
            } else {
                throw new IllegalArgumentException("Unexpected header: Content-Length");
            }
        }

        public static Part create(RequestBody requestBody) {
            return create(null, requestBody);
        }

        public static Part createFormData(String str, String str2) {
            return createFormData(str, null, RequestBody.create(null, str2));
        }

        public static Part createFormData(String str, String str2, RequestBody requestBody) {
            if (str == null) {
                throw new NullPointerException("name == null");
            }
            StringBuilder stringBuilder = new StringBuilder("form-data; name=");
            MultipartBody.appendQuotedString(stringBuilder, str);
            if (str2 != null) {
                stringBuilder.append("; filename=");
                MultipartBody.appendQuotedString(stringBuilder, str2);
            }
            return create(Headers.of("Content-Disposition", stringBuilder.toString()), requestBody);
        }
    }

    MultipartBody(nz nzVar, MediaType mediaType, List<Part> list) {
        this.boundary = nzVar;
        this.originalType = mediaType;
        this.contentType = MediaType.parse(mediaType + "; boundary=" + nzVar.a());
        this.parts = Util.immutableList((List) list);
    }

    static StringBuilder appendQuotedString(StringBuilder stringBuilder, String str) {
        stringBuilder.append('\"');
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            switch (charAt) {
                case '\n':
                    stringBuilder.append("%0A");
                    break;
                case '\r':
                    stringBuilder.append("%0D");
                    break;
                case '\"':
                    stringBuilder.append("%22");
                    break;
                default:
                    stringBuilder.append(charAt);
                    break;
            }
        }
        stringBuilder.append('\"');
        return stringBuilder;
    }

    private long writeOrCountBytes(nx nxVar, boolean z) throws IOException {
        nw nwVar;
        long j = 0;
        if (z) {
            nw nwVar2 = new nw();
            nwVar = nwVar2;
            nxVar = nwVar2;
        } else {
            nwVar = null;
        }
        int size = this.parts.size();
        for (int i = 0; i < size; i++) {
            Part part = (Part) this.parts.get(i);
            Headers access$000 = part.headers;
            RequestBody access$100 = part.body;
            nxVar.b(DASHDASH);
            nxVar.b(this.boundary);
            nxVar.b(CRLF);
            if (access$000 != null) {
                int size2 = access$000.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    nxVar.b(access$000.name(i2)).b(COLONSPACE).b(access$000.value(i2)).b(CRLF);
                }
            }
            MediaType contentType = access$100.contentType();
            if (contentType != null) {
                nxVar.b("Content-Type: ").b(contentType.toString()).b(CRLF);
            }
            long contentLength = access$100.contentLength();
            if (contentLength != -1) {
                nxVar.b("Content-Length: ").k(contentLength).b(CRLF);
            } else if (z) {
                nwVar.q();
                return -1;
            }
            nxVar.b(CRLF);
            if (z) {
                j += contentLength;
            } else {
                access$100.writeTo(nxVar);
            }
            nxVar.b(CRLF);
        }
        nxVar.b(DASHDASH);
        nxVar.b(this.boundary);
        nxVar.b(DASHDASH);
        nxVar.b(CRLF);
        if (!z) {
            return j;
        }
        long j2 = nwVar.b;
        nwVar.q();
        return j + j2;
    }

    public final String boundary() {
        return this.boundary.a();
    }

    public final long contentLength() throws IOException {
        long j = this.contentLength;
        if (j != -1) {
            return j;
        }
        j = writeOrCountBytes(null, true);
        this.contentLength = j;
        return j;
    }

    public final MediaType contentType() {
        return this.contentType;
    }

    public final Part part(int i) {
        return (Part) this.parts.get(i);
    }

    public final List<Part> parts() {
        return this.parts;
    }

    public final int size() {
        return this.parts.size();
    }

    public final MediaType type() {
        return this.originalType;
    }

    public final void writeTo(nx nxVar) throws IOException {
        writeOrCountBytes(nxVar, false);
    }
}
