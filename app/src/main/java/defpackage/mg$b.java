package defpackage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public interface mg$b {
    public static final mg$b a = new mg$b$1();

    HttpURLConnection a(URL url) throws IOException;

    HttpURLConnection a(URL url, Proxy proxy) throws IOException;
}
