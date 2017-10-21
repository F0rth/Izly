package org.ksoap2.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ksoap2.HeaderProperty;

public class ServiceConnectionSE implements ServiceConnection {
    private HttpURLConnection connection;

    public ServiceConnectionSE(String str) throws IOException {
        this(null, str, 20000);
    }

    public ServiceConnectionSE(String str, int i) throws IOException {
        this(null, str, i);
    }

    public ServiceConnectionSE(Proxy proxy, String str) throws IOException {
        this(proxy, str, 20000);
    }

    public ServiceConnectionSE(Proxy proxy, String str, int i) throws IOException {
        this.connection = proxy == null ? (HttpURLConnection) new URL(str).openConnection() : (HttpURLConnection) new URL(str).openConnection(proxy);
        this.connection.setUseCaches(false);
        this.connection.setDoOutput(true);
        this.connection.setDoInput(true);
        this.connection.setConnectTimeout(i);
        this.connection.setReadTimeout(i);
    }

    public void connect() throws IOException {
        this.connection.connect();
    }

    public void disconnect() {
        this.connection.disconnect();
    }

    public InputStream getErrorStream() {
        return this.connection.getErrorStream();
    }

    public String getHost() {
        return this.connection.getURL().getHost();
    }

    public String getPath() {
        return this.connection.getURL().getPath();
    }

    public int getPort() {
        return this.connection.getURL().getPort();
    }

    public List getResponseProperties() throws IOException {
        List linkedList = new LinkedList();
        Map headerFields = this.connection.getHeaderFields();
        if (headerFields != null) {
            for (String str : headerFields.keySet()) {
                List list = (List) headerFields.get(str);
                for (int i = 0; i < list.size(); i++) {
                    linkedList.add(new HeaderProperty(str, (String) list.get(i)));
                }
            }
        }
        return linkedList;
    }

    public InputStream openInputStream() throws IOException {
        return this.connection.getInputStream();
    }

    public OutputStream openOutputStream() throws IOException {
        return this.connection.getOutputStream();
    }

    public void setFixedLengthStreamingMode(int i) {
        this.connection.setFixedLengthStreamingMode(i);
    }

    public void setRequestMethod(String str) throws IOException {
        this.connection.setRequestMethod(str);
    }

    public void setRequestProperty(String str, String str2) {
        this.connection.setRequestProperty(str, str2);
    }
}
