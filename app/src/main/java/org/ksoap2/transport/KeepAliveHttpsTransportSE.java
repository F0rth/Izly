package org.ksoap2.transport;

import java.io.IOException;

public class KeepAliveHttpsTransportSE extends HttpsTransportSE {
    private final String file;
    private final String host;
    private final int port;
    private ServiceConnection serviceConnection;
    private final int timeout;

    public KeepAliveHttpsTransportSE(String str, int i, String str2, int i2) {
        super(str, i, str2, i2);
        this.host = str;
        this.port = i;
        this.file = str2;
        this.timeout = i2;
    }

    public ServiceConnection getServiceConnection() throws IOException {
        if (this.serviceConnection == null) {
            this.serviceConnection = new HttpsServiceConnectionSEIgnoringConnectionClose(this.host, this.port, this.file, this.timeout);
            this.serviceConnection.setRequestProperty("Connection", "keep-alive");
        }
        return this.serviceConnection;
    }
}
