package org.spongycastle.util.io.pem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.spongycastle.util.encoders.Base64;

public class PemReader extends BufferedReader {
    private static final String BEGIN = "-----BEGIN ";
    private static final String END = "-----END ";

    public PemReader(Reader reader) {
        super(reader);
    }

    private PemObject loadObject(String str) throws IOException {
        String stringBuilder = new StringBuilder(END).append(str).toString();
        StringBuffer stringBuffer = new StringBuffer();
        List arrayList = new ArrayList();
        while (true) {
            String readLine = readLine();
            if (readLine != null) {
                if (readLine.indexOf(":") < 0) {
                    if (readLine.indexOf(stringBuilder) != -1) {
                        break;
                    }
                    stringBuffer.append(readLine.trim());
                } else {
                    int indexOf = readLine.indexOf(58);
                    arrayList.add(new PemHeader(readLine.substring(0, indexOf), readLine.substring(indexOf + 1).trim()));
                }
            } else {
                break;
            }
        }
        if (readLine != null) {
            return new PemObject(str, arrayList, Base64.decode(stringBuffer.toString()));
        }
        throw new IOException(stringBuilder + " not found");
    }

    public PemObject readPemObject() throws IOException {
        String readLine = readLine();
        while (readLine != null && !readLine.startsWith(BEGIN)) {
            readLine = readLine();
        }
        if (readLine != null) {
            readLine = readLine.substring(11);
            int indexOf = readLine.indexOf(45);
            readLine = readLine.substring(0, indexOf);
            if (indexOf > 0) {
                return loadObject(readLine);
            }
        }
        return null;
    }
}
