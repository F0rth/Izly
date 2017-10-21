package defpackage;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import org.spongycastle.asn1.cmp.PKIFailureInfo;

public final class mg {
    private static final String[] b = new String[0];
    private static mg$b c = mg$b.a;
    public final URL a;
    private HttpURLConnection d = null;
    private final String e;
    private mg$e f;
    private boolean g;
    private boolean h = true;
    private boolean i = false;
    private int j = PKIFailureInfo.certRevoked;
    private String k;
    private int l;

    private mg(CharSequence charSequence, String str) throws mg$c {
        try {
            this.a = new URL(charSequence.toString());
            this.e = str;
        } catch (IOException e) {
            throw new mg$c(e);
        }
    }

    private static String a(CharSequence charSequence, Map<?, ?> map) {
        String charSequence2 = charSequence.toString();
        if (map == null || map.isEmpty()) {
            return charSequence2;
        }
        StringBuilder stringBuilder = new StringBuilder(charSequence2);
        if (charSequence2.indexOf(58) + 2 == charSequence2.lastIndexOf(47)) {
            stringBuilder.append('/');
        }
        int indexOf = charSequence2.indexOf(63);
        int length = stringBuilder.length() - 1;
        if (indexOf == -1) {
            stringBuilder.append('?');
        } else if (indexOf < length && charSequence2.charAt(length) != '&') {
            stringBuilder.append('&');
        }
        Iterator it = map.entrySet().iterator();
        Entry entry = (Entry) it.next();
        stringBuilder.append(entry.getKey().toString());
        stringBuilder.append('=');
        Object value = entry.getValue();
        if (value != null) {
            stringBuilder.append(value);
        }
        while (it.hasNext()) {
            stringBuilder.append('&');
            entry = (Entry) it.next();
            stringBuilder.append(entry.getKey().toString());
            stringBuilder.append('=');
            value = entry.getValue();
            if (value != null) {
                stringBuilder.append(value);
            }
        }
        return stringBuilder.toString();
    }

    private mg a(InputStream inputStream, OutputStream outputStream) throws IOException {
        return (mg) new mg$1(this, inputStream, this.h, inputStream, outputStream).call();
    }

    public static mg a(CharSequence charSequence) throws mg$c {
        return new mg(charSequence, "PUT");
    }

    public static mg a(CharSequence charSequence, Map<?, ?> map, boolean z) {
        return new mg(mg.c(mg.a(charSequence, (Map) map)), "GET");
    }

    private mg a(String str, String str2, String str3, String str4) throws mg$c {
        try {
            i();
            b(str, str2, null);
            this.f.a(str4);
            return this;
        } catch (IOException e) {
            throw new mg$c(e);
        }
    }

    private static String b(String str, String str2) {
        String str3;
        if (str == null || str.length() == 0) {
            str3 = null;
        } else {
            int length = str.length();
            int indexOf = str.indexOf(59) + 1;
            if (indexOf == 0 || indexOf == length) {
                return null;
            }
            int i;
            int indexOf2 = str.indexOf(59, indexOf);
            if (indexOf2 == -1) {
                indexOf2 = indexOf;
                indexOf = length;
            } else {
                i = indexOf2;
                indexOf2 = indexOf;
                indexOf = i;
            }
            while (indexOf2 < indexOf) {
                int indexOf3 = str.indexOf(61, indexOf2);
                if (indexOf3 != -1 && indexOf3 < indexOf && str2.equals(str.substring(indexOf2, indexOf3).trim())) {
                    str3 = str.substring(indexOf3 + 1, indexOf).trim();
                    indexOf3 = str3.length();
                    if (indexOf3 != 0) {
                        if (indexOf3 > 2 && '\"' == str3.charAt(0) && '\"' == str3.charAt(indexOf3 - 1)) {
                            return str3.substring(1, indexOf3 - 1);
                        }
                    }
                }
                indexOf++;
                indexOf2 = str.indexOf(59, indexOf);
                if (indexOf2 == -1) {
                    indexOf2 = length;
                }
                i = indexOf2;
                indexOf2 = indexOf;
                indexOf = i;
            }
            return null;
        }
        return str3;
    }

    public static mg b(CharSequence charSequence) throws mg$c {
        return new mg(charSequence, "DELETE");
    }

    public static mg b(CharSequence charSequence, Map<?, ?> map, boolean z) {
        return new mg(mg.c(mg.a(charSequence, (Map) map)), "POST");
    }

    private mg b(String str, String str2, String str3) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("form-data; name=\"").append(str);
        if (str2 != null) {
            stringBuilder.append("\"; filename=\"").append(str2);
        }
        stringBuilder.append('\"');
        c("Content-Disposition", stringBuilder.toString());
        if (str3 != null) {
            c("Content-Type", str3);
        }
        return d((CharSequence) "\r\n");
    }

    private static String c(CharSequence charSequence) throws mg$c {
        try {
            URL url = new URL(charSequence.toString());
            String host = url.getHost();
            int port = url.getPort();
            if (port != -1) {
                host = host + ':' + Integer.toString(port);
            }
            try {
                String toASCIIString = new URI(url.getProtocol(), host, url.getPath(), url.getQuery(), null).toASCIIString();
                int indexOf = toASCIIString.indexOf(63);
                if (indexOf > 0 && indexOf + 1 < toASCIIString.length()) {
                    toASCIIString = toASCIIString.substring(0, indexOf + 1) + toASCIIString.substring(indexOf + 1).replace("+", "%2B");
                }
                return toASCIIString;
            } catch (Throwable e) {
                IOException iOException = new IOException("Parsing URI failed");
                iOException.initCause(e);
                throw new mg$c(iOException);
            }
        } catch (IOException e2) {
            throw new mg$c(e2);
        }
    }

    private static String c(String str) {
        return (str == null || str.length() <= 0) ? "UTF-8" : str;
    }

    private mg c(String str, String str2) throws mg$c {
        return d((CharSequence) str).d((CharSequence) ": ").d((CharSequence) str2).d((CharSequence) "\r\n");
    }

    private String d(String str) throws mg$c {
        g();
        int headerFieldInt = a().getHeaderFieldInt("Content-Length", -1);
        OutputStream byteArrayOutputStream = headerFieldInt > 0 ? new ByteArrayOutputStream(headerFieldInt) : new ByteArrayOutputStream();
        try {
            a(new BufferedInputStream(e(), this.j), byteArrayOutputStream);
            return byteArrayOutputStream.toString(mg.c(str));
        } catch (IOException e) {
            throw new mg$c(e);
        }
    }

    private HttpURLConnection d() {
        try {
            HttpURLConnection a = this.k != null ? c.a(this.a, new Proxy(Type.HTTP, new InetSocketAddress(this.k, this.l))) : c.a(this.a);
            a.setRequestMethod(this.e);
            return a;
        } catch (IOException e) {
            throw new mg$c(e);
        }
    }

    private mg d(CharSequence charSequence) throws mg$c {
        try {
            h();
            this.f.a(charSequence.toString());
            return this;
        } catch (IOException e) {
            throw new mg$c(e);
        }
    }

    private InputStream e() throws mg$c {
        if (b() < 400) {
            try {
                InputStream inputStream = a().getInputStream();
            } catch (IOException e) {
                throw new mg$c(e);
            }
        }
        inputStream = a().getErrorStream();
        if (inputStream == null) {
            try {
                inputStream = a().getInputStream();
            } catch (IOException e2) {
                throw new mg$c(e2);
            }
        }
        if (!this.i || !"gzip".equals(a("Content-Encoding"))) {
            return inputStream;
        }
        try {
            return new GZIPInputStream(inputStream);
        } catch (IOException e22) {
            throw new mg$c(e22);
        }
    }

    private mg f() throws IOException {
        if (this.f != null) {
            if (this.g) {
                this.f.a("\r\n--00content0boundary00--\r\n");
            }
            if (this.h) {
                try {
                    this.f.close();
                } catch (IOException e) {
                }
            } else {
                this.f.close();
            }
            this.f = null;
        }
        return this;
    }

    private mg g() throws mg$c {
        try {
            return f();
        } catch (IOException e) {
            throw new mg$c(e);
        }
    }

    private mg h() throws IOException {
        if (this.f == null) {
            a().setDoOutput(true);
            this.f = new mg$e(a().getOutputStream(), mg.b(a().getRequestProperty("Content-Type"), "charset"), this.j);
        }
        return this;
    }

    private mg i() throws IOException {
        if (this.g) {
            this.f.a("\r\n--00content0boundary00\r\n");
        } else {
            this.g = true;
            a("Content-Type", "multipart/form-data; boundary=00content0boundary00").h();
            this.f.a("--00content0boundary00\r\n");
        }
        return this;
    }

    public final String a(String str) throws mg$c {
        g();
        return a().getHeaderField(str);
    }

    public final HttpURLConnection a() {
        if (this.d == null) {
            this.d = d();
        }
        return this.d;
    }

    public final mg a(String str, Number number) throws mg$c {
        return a(str, null, number != null ? number.toString() : null);
    }

    public final mg a(String str, String str2) {
        a().setRequestProperty(str, str2);
        return this;
    }

    public final mg a(String str, String str2, String str3) throws mg$c {
        return a(str, str2, null, str3);
    }

    public final mg a(String str, String str2, String str3, File file) throws mg$c {
        IOException e;
        Throwable th;
        InputStream bufferedInputStream;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            try {
                mg a = a(str, str2, str3, bufferedInputStream);
                try {
                    bufferedInputStream.close();
                } catch (IOException e2) {
                }
                return a;
            } catch (IOException e3) {
                e = e3;
                try {
                    throw new mg$c(e);
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e4) {
                        }
                    }
                    throw th;
                }
            }
        } catch (IOException e5) {
            e = e5;
            bufferedInputStream = null;
            throw new mg$c(e);
        } catch (Throwable th3) {
            th = th3;
            bufferedInputStream = null;
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            throw th;
        }
    }

    public final mg a(String str, String str2, String str3, InputStream inputStream) throws mg$c {
        try {
            i();
            b(str, str2, str3);
            a(inputStream, this.f);
            return this;
        } catch (IOException e) {
            throw new mg$c(e);
        }
    }

    public final int b() throws mg$c {
        try {
            f();
            return a().getResponseCode();
        } catch (IOException e) {
            throw new mg$c(e);
        }
    }

    public final String c() throws mg$c {
        return d(mg.b(a("Content-Type"), "charset"));
    }

    public final String toString() {
        return a().getRequestMethod() + ' ' + a().getURL();
    }
}
