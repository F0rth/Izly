package org.ksoap2.transport;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

public class HttpsTransportSE extends HttpTransportSE {
    static final String PROTOCOL = "https";
    private static final String PROTOCOL_FULL = "https://";
    private final String file;
    private final String host;
    private final int port;
    private ServiceConnection serviceConnection = null;
    private final int timeout;

    public HttpsTransportSE(String str, int i, String str2, int i2) {
        super(new StringBuffer(PROTOCOL_FULL).append(str).append(":").append(i).append(str2).toString());
        this.host = str;
        this.port = i;
        this.file = str2;
        this.timeout = i2;
    }

    public HttpsTransportSE(Proxy proxy, String str, int i, String str2, int i2) {
        super(proxy, new StringBuffer(PROTOCOL_FULL).append(str).append(":").append(i).append(str2).toString());
        this.host = str;
        this.port = i;
        this.file = str2;
        this.timeout = i2;
    }

    public String getHost() {
        try {
            return new URL(this.url).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPath() {
        try {
            return new URL(this.url).getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getPort() {
        try {
            return new URL(this.url).getPort();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ServiceConnection getServiceConnection() throws IOException {
        if (this.serviceConnection == null) {
            this.serviceConnection = new HttpsServiceConnectionSE(this.proxy, this.host, this.port, this.file, this.timeout);
        }
        return this.serviceConnection;
    }
}
