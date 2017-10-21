package org.ksoap2.transport;

import java.io.IOException;

class HttpsServiceConnectionSEIgnoringConnectionClose extends HttpsServiceConnectionSE {
    public HttpsServiceConnectionSEIgnoringConnectionClose(String str, int i, String str2, int i2) throws IOException {
        super(str, i, str2, i2);
    }

    public void setRequestProperty(String str, String str2) {
        if (!"Connection".equalsIgnoreCase(str) || !"close".equalsIgnoreCase(str2)) {
            super.setRequestProperty(str, str2);
        }
    }
}
