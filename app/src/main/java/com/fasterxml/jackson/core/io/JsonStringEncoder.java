package com.fasterxml.jackson.core.io;

import android.support.v4.media.TransportMediator;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.lang.ref.SoftReference;

public final class JsonStringEncoder {
    private static final byte[] HB = CharTypes.copyHexBytes();
    private static final char[] HC = CharTypes.copyHexChars();
    private static final int SURR1_FIRST = 55296;
    private static final int SURR1_LAST = 56319;
    private static final int SURR2_FIRST = 56320;
    private static final int SURR2_LAST = 57343;
    protected static final ThreadLocal<SoftReference<JsonStringEncoder>> _threadEncoder = new ThreadLocal();
    protected ByteArrayBuilder _bytes;
    protected final char[] _qbuf = new char[6];
    protected TextBuffer _text;

    public JsonStringEncoder() {
        this._qbuf[0] = '\\';
        this._qbuf[2] = '0';
        this._qbuf[3] = '0';
    }

    private int _appendByte(int i, int i2, ByteArrayBuilder byteArrayBuilder, int i3) {
        byteArrayBuilder.setCurrentSegmentLength(i3);
        byteArrayBuilder.append(92);
        if (i2 < 0) {
            byteArrayBuilder.append(117);
            if (i > 255) {
                int i4 = i >> 8;
                byteArrayBuilder.append(HB[i4 >> 4]);
                byteArrayBuilder.append(HB[i4 & 15]);
                i &= 255;
            } else {
                byteArrayBuilder.append(48);
                byteArrayBuilder.append(48);
            }
            byteArrayBuilder.append(HB[i >> 4]);
            byteArrayBuilder.append(HB[i & 15]);
        } else {
            byteArrayBuilder.append((byte) i2);
        }
        return byteArrayBuilder.getCurrentSegmentLength();
    }

    private int _appendNamed(int i, char[] cArr) {
        cArr[1] = (char) i;
        return 2;
    }

    private int _appendNumeric(int i, char[] cArr) {
        cArr[1] = 'u';
        cArr[4] = HC[i >> 4];
        cArr[5] = HC[i & 15];
        return 6;
    }

    private static int _convert(int i, int i2) {
        if (i2 >= 56320 && i2 <= 57343) {
            return (65536 + ((i - 55296) << 10)) + (i2 - 56320);
        }
        throw new IllegalArgumentException("Broken surrogate pair: first char 0x" + Integer.toHexString(i) + ", second 0x" + Integer.toHexString(i2) + "; illegal combination");
    }

    private static void _illegal(int i) {
        throw new IllegalArgumentException(UTF8Writer.illegalSurrogateDesc(i));
    }

    public static JsonStringEncoder getInstance() {
        SoftReference softReference = (SoftReference) _threadEncoder.get();
        JsonStringEncoder jsonStringEncoder = softReference == null ? null : (JsonStringEncoder) softReference.get();
        if (jsonStringEncoder != null) {
            return jsonStringEncoder;
        }
        jsonStringEncoder = new JsonStringEncoder();
        _threadEncoder.set(new SoftReference(jsonStringEncoder));
        return jsonStringEncoder;
    }

    public final byte[] encodeAsUTF8(String str) {
        int i;
        ByteArrayBuilder byteArrayBuilder = this._bytes;
        if (byteArrayBuilder == null) {
            byteArrayBuilder = new ByteArrayBuilder(null);
            this._bytes = byteArrayBuilder;
        }
        int length = str.length();
        byte[] resetAndGetFirstSegment = byteArrayBuilder.resetAndGetFirstSegment();
        int length2 = resetAndGetFirstSegment.length;
        int i2 = 0;
        int i3 = 0;
        loop0:
        while (i2 < length) {
            int i4;
            int i5 = i2 + 1;
            i2 = str.charAt(i2);
            byte[] bArr = resetAndGetFirstSegment;
            int i6 = i3;
            byte[] bArr2 = bArr;
            while (i2 <= TransportMediator.KEYCODE_MEDIA_PAUSE) {
                if (i6 >= length2) {
                    bArr2 = byteArrayBuilder.finishCurrentSegment();
                    length2 = bArr2.length;
                    i6 = 0;
                }
                i4 = i6 + 1;
                bArr2[i6] = (byte) i2;
                if (i5 >= length) {
                    i = i4;
                    break loop0;
                }
                i2 = str.charAt(i5);
                i5++;
                i6 = i4;
            }
            if (i6 >= length2) {
                bArr2 = byteArrayBuilder.finishCurrentSegment();
                length2 = bArr2.length;
                i4 = 0;
            } else {
                i4 = i6;
            }
            if (i2 < 2048) {
                i6 = i4 + 1;
                bArr2[i4] = (byte) ((i2 >> 6) | 192);
                i4 = i2;
                i2 = i5;
            } else if (i2 < 55296 || i2 > 57343) {
                i6 = i4 + 1;
                bArr2[i4] = (byte) ((i2 >> 12) | 224);
                if (i6 >= length2) {
                    bArr2 = byteArrayBuilder.finishCurrentSegment();
                    length2 = bArr2.length;
                    i6 = 0;
                }
                bArr2[i6] = (byte) (((i2 >> 6) & 63) | 128);
                i6++;
                i4 = i2;
                i2 = i5;
            } else {
                if (i2 > 56319) {
                    _illegal(i2);
                }
                if (i5 >= length) {
                    _illegal(i2);
                }
                i2 = _convert(i2, str.charAt(i5));
                if (i2 > 1114111) {
                    _illegal(i2);
                }
                i6 = i4 + 1;
                bArr2[i4] = (byte) ((i2 >> 18) | 240);
                if (i6 >= length2) {
                    bArr2 = byteArrayBuilder.finishCurrentSegment();
                    length2 = bArr2.length;
                    i6 = 0;
                }
                i4 = i6 + 1;
                bArr2[i6] = (byte) (((i2 >> 12) & 63) | 128);
                if (i4 >= length2) {
                    bArr2 = byteArrayBuilder.finishCurrentSegment();
                    length2 = bArr2.length;
                    i6 = 0;
                } else {
                    i6 = i4;
                }
                bArr2[i6] = (byte) (((i2 >> 6) & 63) | 128);
                i6++;
                int i7 = i2;
                i2 = i5 + 1;
                i4 = i7;
            }
            if (i6 >= length2) {
                bArr2 = byteArrayBuilder.finishCurrentSegment();
                length2 = bArr2.length;
                i6 = 0;
            }
            bArr2[i6] = (byte) ((i4 & 63) | 128);
            bArr = bArr2;
            i3 = i6 + 1;
            resetAndGetFirstSegment = bArr;
        }
        i = i3;
        return this._bytes.completeAndCoalesce(i);
    }

    public final char[] quoteAsString(String str) {
        TextBuffer textBuffer = this._text;
        if (textBuffer == null) {
            textBuffer = new TextBuffer(null);
            this._text = textBuffer;
        }
        Object emptyAndGetCurrentSegment = textBuffer.emptyAndGetCurrentSegment();
        int[] iArr = CharTypes.get7BitOutputEscapes();
        char length = iArr.length;
        int length2 = str.length();
        int i = 0;
        int i2 = 0;
        loop0:
        while (i < length2) {
            int i3;
            while (true) {
                char charAt = str.charAt(i);
                if (charAt < length && iArr[charAt] != 0) {
                    break;
                }
                if (i2 >= emptyAndGetCurrentSegment.length) {
                    emptyAndGetCurrentSegment = textBuffer.finishCurrentSegment();
                    i3 = 0;
                } else {
                    i3 = i2;
                }
                i2 = i3 + 1;
                emptyAndGetCurrentSegment[i3] = charAt;
                i++;
                if (i >= length2) {
                    break loop0;
                }
            }
            i3 = i + 1;
            char charAt2 = str.charAt(i);
            int i4 = iArr[charAt2];
            i = i4 < 0 ? _appendNumeric(charAt2, this._qbuf) : _appendNamed(i4, this._qbuf);
            if (i2 + i > emptyAndGetCurrentSegment.length) {
                i4 = emptyAndGetCurrentSegment.length - i2;
                if (i4 > 0) {
                    System.arraycopy(this._qbuf, 0, emptyAndGetCurrentSegment, i2, i4);
                }
                emptyAndGetCurrentSegment = textBuffer.finishCurrentSegment();
                i2 = i - i4;
                System.arraycopy(this._qbuf, i4, emptyAndGetCurrentSegment, 0, i2);
                i = i3;
            } else {
                System.arraycopy(this._qbuf, 0, emptyAndGetCurrentSegment, i2, i);
                i2 += i;
                i = i3;
            }
        }
        textBuffer.setCurrentLength(i2);
        return textBuffer.contentsAsArray();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final byte[] quoteAsUTF8(java.lang.String r12) {
        /*
        r11 = this;
        r9 = 127; // 0x7f float:1.78E-43 double:6.27E-322;
        r5 = 0;
        r0 = r11._bytes;
        if (r0 != 0) goto L_0x000f;
    L_0x0007:
        r0 = new com.fasterxml.jackson.core.util.ByteArrayBuilder;
        r1 = 0;
        r0.<init>(r1);
        r11._bytes = r0;
    L_0x000f:
        r7 = r12.length();
        r1 = r0.resetAndGetFirstSegment();
        r2 = r5;
        r3 = r5;
    L_0x0019:
        if (r2 >= r7) goto L_0x003a;
    L_0x001b:
        r6 = com.fasterxml.jackson.core.io.CharTypes.get7BitOutputEscapes();
    L_0x001f:
        r8 = r12.charAt(r2);
        if (r8 > r9) goto L_0x0041;
    L_0x0025:
        r4 = r6[r8];
        if (r4 != 0) goto L_0x0041;
    L_0x0029:
        r4 = r1.length;
        if (r3 < r4) goto L_0x0105;
    L_0x002c:
        r1 = r0.finishCurrentSegment();
        r4 = r5;
    L_0x0031:
        r3 = r4 + 1;
        r8 = (byte) r8;
        r1[r4] = r8;
        r2 = r2 + 1;
        if (r2 < r7) goto L_0x001f;
    L_0x003a:
        r0 = r11._bytes;
        r0 = r0.completeAndCoalesce(r3);
        return r0;
    L_0x0041:
        r4 = r1.length;
        if (r3 < r4) goto L_0x0049;
    L_0x0044:
        r1 = r0.finishCurrentSegment();
        r3 = r5;
    L_0x0049:
        r4 = r2 + 1;
        r8 = r12.charAt(r2);
        if (r8 > r9) goto L_0x005d;
    L_0x0051:
        r1 = r6[r8];
        r3 = r11._appendByte(r8, r1, r0, r3);
        r1 = r0.getCurrentSegment();
        r2 = r4;
        goto L_0x0019;
    L_0x005d:
        r2 = 2047; // 0x7ff float:2.868E-42 double:1.0114E-320;
        if (r8 > r2) goto L_0x007f;
    L_0x0061:
        r6 = r3 + 1;
        r2 = r8 >> 6;
        r2 = r2 | 192;
        r2 = (byte) r2;
        r1[r3] = r2;
        r2 = r8 & 63;
        r2 = r2 | 128;
        r3 = r2;
        r2 = r4;
        r4 = r6;
    L_0x0071:
        r6 = r1.length;
        if (r4 < r6) goto L_0x0079;
    L_0x0074:
        r1 = r0.finishCurrentSegment();
        r4 = r5;
    L_0x0079:
        r3 = (byte) r3;
        r1[r4] = r3;
        r3 = r4 + 1;
        goto L_0x0019;
    L_0x007f:
        r2 = 55296; // 0xd800 float:7.7486E-41 double:2.732E-319;
        if (r8 < r2) goto L_0x0089;
    L_0x0084:
        r2 = 57343; // 0xdfff float:8.0355E-41 double:2.8331E-319;
        if (r8 <= r2) goto L_0x00ae;
    L_0x0089:
        r2 = r3 + 1;
        r6 = r8 >> 12;
        r6 = r6 | 224;
        r6 = (byte) r6;
        r1[r3] = r6;
        r3 = r1.length;
        if (r2 < r3) goto L_0x009a;
    L_0x0095:
        r1 = r0.finishCurrentSegment();
        r2 = r5;
    L_0x009a:
        r3 = r2 + 1;
        r6 = r8 >> 6;
        r6 = r6 & 63;
        r6 = r6 | 128;
        r6 = (byte) r6;
        r1[r2] = r6;
        r2 = r8 & 63;
        r2 = r2 | 128;
        r10 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r10;
        goto L_0x0071;
    L_0x00ae:
        r2 = 56319; // 0xdbff float:7.892E-41 double:2.78253E-319;
        if (r8 <= r2) goto L_0x00b6;
    L_0x00b3:
        _illegal(r8);
    L_0x00b6:
        if (r4 < r7) goto L_0x00bb;
    L_0x00b8:
        _illegal(r8);
    L_0x00bb:
        r2 = r12.charAt(r4);
        r8 = _convert(r8, r2);
        r2 = 1114111; // 0x10ffff float:1.561202E-39 double:5.50444E-318;
        if (r8 <= r2) goto L_0x00cb;
    L_0x00c8:
        _illegal(r8);
    L_0x00cb:
        r2 = r3 + 1;
        r6 = r8 >> 18;
        r6 = r6 | 240;
        r6 = (byte) r6;
        r1[r3] = r6;
        r3 = r1.length;
        if (r2 < r3) goto L_0x00dc;
    L_0x00d7:
        r1 = r0.finishCurrentSegment();
        r2 = r5;
    L_0x00dc:
        r3 = r2 + 1;
        r6 = r8 >> 12;
        r6 = r6 & 63;
        r6 = r6 | 128;
        r6 = (byte) r6;
        r1[r2] = r6;
        r2 = r1.length;
        if (r3 < r2) goto L_0x0103;
    L_0x00ea:
        r1 = r0.finishCurrentSegment();
        r2 = r5;
    L_0x00ef:
        r6 = r2 + 1;
        r3 = r8 >> 6;
        r3 = r3 & 63;
        r3 = r3 | 128;
        r3 = (byte) r3;
        r1[r2] = r3;
        r2 = r4 + 1;
        r3 = r8 & 63;
        r3 = r3 | 128;
        r4 = r6;
        goto L_0x0071;
    L_0x0103:
        r2 = r3;
        goto L_0x00ef;
    L_0x0105:
        r4 = r3;
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.io.JsonStringEncoder.quoteAsUTF8(java.lang.String):byte[]");
    }
}
