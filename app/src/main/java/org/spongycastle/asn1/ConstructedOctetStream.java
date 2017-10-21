package org.spongycastle.asn1;

import java.io.IOException;
import java.io.InputStream;

class ConstructedOctetStream extends InputStream {
    private InputStream _currentStream;
    private boolean _first = true;
    private final ASN1StreamParser _parser;

    ConstructedOctetStream(ASN1StreamParser aSN1StreamParser) {
        this._parser = aSN1StreamParser;
    }

    public int read() throws IOException {
        ASN1OctetStringParser aSN1OctetStringParser;
        int i;
        if (this._currentStream == null) {
            if (this._first) {
                aSN1OctetStringParser = (ASN1OctetStringParser) this._parser.readObject();
                if (aSN1OctetStringParser == null) {
                    return -1;
                }
                this._first = false;
                this._currentStream = aSN1OctetStringParser.getOctetStream();
            } else {
                i = -1;
                return i;
            }
        }
        while (true) {
            i = this._currentStream.read();
            if (i >= 0) {
                break;
            }
            aSN1OctetStringParser = (ASN1OctetStringParser) this._parser.readObject();
            if (aSN1OctetStringParser == null) {
                this._currentStream = null;
                return -1;
            }
            this._currentStream = aSN1OctetStringParser.getOctetStream();
        }
        return i;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(byte[] r6, int r7, int r8) throws java.io.IOException {
        /*
        r5 = this;
        r2 = 0;
        r1 = -1;
        r0 = r5._currentStream;
        if (r0 != 0) goto L_0x001f;
    L_0x0006:
        r0 = r5._first;
        if (r0 != 0) goto L_0x000d;
    L_0x000a:
        r0 = r1;
    L_0x000b:
        r1 = r0;
    L_0x000c:
        return r1;
    L_0x000d:
        r0 = r5._parser;
        r0 = r0.readObject();
        r0 = (org.spongycastle.asn1.ASN1OctetStringParser) r0;
        if (r0 == 0) goto L_0x000c;
    L_0x0017:
        r5._first = r2;
        r0 = r0.getOctetStream();
        r5._currentStream = r0;
    L_0x001f:
        r0 = r5._currentStream;
        r3 = r7 + r2;
        r4 = r8 - r2;
        r0 = r0.read(r6, r3, r4);
        if (r0 < 0) goto L_0x0030;
    L_0x002b:
        r0 = r0 + r2;
        if (r0 == r8) goto L_0x000b;
    L_0x002e:
        r2 = r0;
        goto L_0x001f;
    L_0x0030:
        r0 = r5._parser;
        r0 = r0.readObject();
        r0 = (org.spongycastle.asn1.ASN1OctetStringParser) r0;
        if (r0 != 0) goto L_0x0041;
    L_0x003a:
        r0 = 0;
        r5._currentStream = r0;
        if (r2 <= 0) goto L_0x000c;
    L_0x003f:
        r1 = r2;
        goto L_0x000c;
    L_0x0041:
        r0 = r0.getOctetStream();
        r5._currentStream = r0;
        r0 = r2;
        goto L_0x002e;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.asn1.ConstructedOctetStream.read(byte[], int, int):int");
    }
}
