package okhttp3;

import defpackage.nw;
import defpackage.nx;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.internal.Util;

public final class FormBody extends RequestBody {
    private static final MediaType CONTENT_TYPE = MediaType.parse("application/x-www-form-urlencoded");
    private final List<String> encodedNames;
    private final List<String> encodedValues;

    public static final class Builder {
        private final List<String> names = new ArrayList();
        private final List<String> values = new ArrayList();

        public final Builder add(String str, String str2) {
            this.names.add(HttpUrl.canonicalize(str, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, true));
            this.values.add(HttpUrl.canonicalize(str2, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, true));
            return this;
        }

        public final Builder addEncoded(String str, String str2) {
            this.names.add(HttpUrl.canonicalize(str, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, true));
            this.values.add(HttpUrl.canonicalize(str2, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, true));
            return this;
        }

        public final FormBody build() {
            return new FormBody(this.names, this.values);
        }
    }

    private FormBody(List<String> list, List<String> list2) {
        this.encodedNames = Util.immutableList((List) list);
        this.encodedValues = Util.immutableList((List) list2);
    }

    private long writeOrCountBytes(nx nxVar, boolean z) {
        long j = 0;
        nw nwVar = z ? new nw() : nxVar.a();
        int size = this.encodedNames.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                nwVar.b(38);
            }
            nwVar.a((String) this.encodedNames.get(i));
            nwVar.b(61);
            nwVar.a((String) this.encodedValues.get(i));
        }
        if (z) {
            j = nwVar.b;
            nwVar.q();
        }
        return j;
    }

    public final long contentLength() {
        return writeOrCountBytes(null, true);
    }

    public final MediaType contentType() {
        return CONTENT_TYPE;
    }

    public final String encodedName(int i) {
        return (String) this.encodedNames.get(i);
    }

    public final String encodedValue(int i) {
        return (String) this.encodedValues.get(i);
    }

    public final String name(int i) {
        return HttpUrl.percentDecode(encodedName(i), true);
    }

    public final int size() {
        return this.encodedNames.size();
    }

    public final String value(int i) {
        return HttpUrl.percentDecode(encodedValue(i), true);
    }

    public final void writeTo(nx nxVar) throws IOException {
        writeOrCountBytes(nxVar, false);
    }
}
