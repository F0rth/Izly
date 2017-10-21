package org.ksoap2.transport;

import defpackage.kh;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.xmlpull.v1.XmlPullParserException;

public class HttpTransportSE extends Transport {
    private ServiceConnection serviceConnection;

    public HttpTransportSE(String str) {
        super(null, str);
    }

    public HttpTransportSE(String str, int i) {
        super(str, i);
    }

    public HttpTransportSE(String str, int i, int i2) {
        super(str, i);
    }

    public HttpTransportSE(Proxy proxy, String str) {
        super(proxy, str);
    }

    public HttpTransportSE(Proxy proxy, String str, int i) {
        super(proxy, str, i);
    }

    public HttpTransportSE(Proxy proxy, String str, int i, int i2) {
        super(proxy, str, i);
    }

    private InputStream getUnZippedInputStream(InputStream inputStream) throws IOException {
        try {
            return (GZIPInputStream) inputStream;
        } catch (ClassCastException e) {
            return new GZIPInputStream(inputStream);
        }
    }

    public List call(String str, SoapEnvelope soapEnvelope, List list) throws IOException, XmlPullParserException {
        int i;
        IOException iOException;
        List list2 = null;
        if (str == null) {
            str = "\"\"";
        }
        byte[] createRequestData = createRequestData(soapEnvelope, "UTF-8");
        this.requestDump = this.debug ? new String(createRequestData) : null;
        this.responseDump = null;
        ServiceConnection serviceConnection = getServiceConnection();
        serviceConnection.setRequestProperty(kh.HEADER_USER_AGENT, "ksoap2-android/2.6.0+");
        if (soapEnvelope.version != 120) {
            serviceConnection.setRequestProperty("SOAPAction", str);
        }
        if (soapEnvelope.version == 120) {
            serviceConnection.setRequestProperty("Content-Type", "application/soap+xml;charset=utf-8");
        } else {
            serviceConnection.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
        }
        serviceConnection.setRequestProperty("Connection", "close");
        serviceConnection.setRequestProperty("Accept-Encoding", "gzip");
        serviceConnection.setRequestProperty("Content-Length", new StringBuffer().append(createRequestData.length).toString());
        serviceConnection.setFixedLengthStreamingMode(createRequestData.length);
        if (list != null) {
            for (i = 0; i < list.size(); i++) {
                HeaderProperty headerProperty = (HeaderProperty) list.get(i);
                serviceConnection.setRequestProperty(headerProperty.getKey(), headerProperty.getValue());
            }
        }
        serviceConnection.setRequestMethod("POST");
        OutputStream openOutputStream = serviceConnection.openOutputStream();
        openOutputStream.write(createRequestData, 0, createRequestData.length);
        openOutputStream.flush();
        openOutputStream.close();
        int i2;
        InputStream unZippedInputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] bArr;
        int read;
        try {
            List responseProperties = serviceConnection.getResponseProperties();
            int i3 = PKIFailureInfo.certRevoked;
            for (i = 0; i < responseProperties.size(); i++) {
                headerProperty = (HeaderProperty) responseProperties.get(i);
                if (headerProperty.getKey() != null) {
                    if (headerProperty.getKey().equalsIgnoreCase("content-length") && headerProperty.getValue() != null) {
                        try {
                            i3 = Integer.parseInt(headerProperty.getValue());
                        } catch (NumberFormatException e) {
                            i3 = PKIFailureInfo.certRevoked;
                        }
                    }
                    try {
                        if (headerProperty.getKey().equalsIgnoreCase("Content-Encoding") && headerProperty.getValue().equalsIgnoreCase("gzip")) {
                            i = 1;
                            i2 = i3;
                            break;
                        }
                    } catch (IOException e2) {
                        iOException = e2;
                        i = 0;
                        i2 = i3;
                        list2 = responseProperties;
                    }
                }
            }
            i = 0;
            i2 = i3;
            if (i != 0) {
                try {
                    unZippedInputStream = getUnZippedInputStream(new BufferedInputStream(serviceConnection.openInputStream(), i2));
                    list2 = responseProperties;
                } catch (IOException e3) {
                    iOException = e3;
                    list2 = responseProperties;
                    unZippedInputStream = i == 0 ? new BufferedInputStream(serviceConnection.getErrorStream(), i2) : getUnZippedInputStream(new BufferedInputStream(serviceConnection.getErrorStream(), i2));
                    if (unZippedInputStream == null) {
                        serviceConnection.disconnect();
                        throw iOException;
                    }
                    if (this.debug) {
                        if (i2 <= 0) {
                            i2 = 262144;
                        }
                        byteArrayOutputStream = new ByteArrayOutputStream(i2);
                        bArr = new byte[256];
                        while (true) {
                            read = unZippedInputStream.read(bArr, 0, 256);
                            if (read == -1) {
                                break;
                            }
                            byteArrayOutputStream.write(bArr, 0, read);
                        }
                        byteArrayOutputStream.flush();
                        bArr = byteArrayOutputStream.toByteArray();
                        this.responseDump = new String(bArr);
                        unZippedInputStream.close();
                        unZippedInputStream = new ByteArrayInputStream(bArr);
                    }
                    parseResponse(soapEnvelope, unZippedInputStream);
                    return list2;
                }
                if (this.debug) {
                    if (i2 <= 0) {
                        i2 = 262144;
                    }
                    byteArrayOutputStream = new ByteArrayOutputStream(i2);
                    bArr = new byte[256];
                    while (true) {
                        read = unZippedInputStream.read(bArr, 0, 256);
                        if (read == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, read);
                    }
                    byteArrayOutputStream.flush();
                    bArr = byteArrayOutputStream.toByteArray();
                    this.responseDump = new String(bArr);
                    unZippedInputStream.close();
                    unZippedInputStream = new ByteArrayInputStream(bArr);
                }
                parseResponse(soapEnvelope, unZippedInputStream);
                return list2;
            }
            unZippedInputStream = new BufferedInputStream(serviceConnection.openInputStream(), i2);
            list2 = responseProperties;
            if (this.debug) {
                if (i2 <= 0) {
                    i2 = 262144;
                }
                byteArrayOutputStream = new ByteArrayOutputStream(i2);
                bArr = new byte[256];
                while (true) {
                    read = unZippedInputStream.read(bArr, 0, 256);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                byteArrayOutputStream.flush();
                bArr = byteArrayOutputStream.toByteArray();
                this.responseDump = new String(bArr);
                unZippedInputStream.close();
                unZippedInputStream = new ByteArrayInputStream(bArr);
            }
            parseResponse(soapEnvelope, unZippedInputStream);
            return list2;
        } catch (IOException e22) {
            i = 0;
            IOException iOException2 = e22;
            i2 = PKIFailureInfo.certRevoked;
            iOException = iOException2;
            if (i == 0) {
            }
            if (unZippedInputStream == null) {
                serviceConnection.disconnect();
                throw iOException;
            }
            if (this.debug) {
                if (i2 <= 0) {
                    i2 = 262144;
                }
                byteArrayOutputStream = new ByteArrayOutputStream(i2);
                bArr = new byte[256];
                while (true) {
                    read = unZippedInputStream.read(bArr, 0, 256);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                byteArrayOutputStream.flush();
                bArr = byteArrayOutputStream.toByteArray();
                this.responseDump = new String(bArr);
                unZippedInputStream.close();
                unZippedInputStream = new ByteArrayInputStream(bArr);
            }
            parseResponse(soapEnvelope, unZippedInputStream);
            return list2;
        }
    }

    public void call(String str, SoapEnvelope soapEnvelope) throws IOException, XmlPullParserException {
        call(str, soapEnvelope, null);
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
            this.serviceConnection = new ServiceConnectionSE(this.proxy, this.url, this.timeout);
        }
        return this.serviceConnection;
    }
}
