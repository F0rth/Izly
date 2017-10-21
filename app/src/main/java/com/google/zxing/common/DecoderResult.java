package com.google.zxing.common;

import java.util.List;

public final class DecoderResult {
    private final List<byte[]> byteSegments;
    private final String ecLevel;
    private Integer erasures;
    private Integer errorsCorrected;
    private Object other;
    private final byte[] rawBytes;
    private final String text;

    public DecoderResult(byte[] bArr, String str, List<byte[]> list, String str2) {
        this.rawBytes = bArr;
        this.text = str;
        this.byteSegments = list;
        this.ecLevel = str2;
    }

    public final List<byte[]> getByteSegments() {
        return this.byteSegments;
    }

    public final String getECLevel() {
        return this.ecLevel;
    }

    public final Integer getErasures() {
        return this.erasures;
    }

    public final Integer getErrorsCorrected() {
        return this.errorsCorrected;
    }

    public final Object getOther() {
        return this.other;
    }

    public final byte[] getRawBytes() {
        return this.rawBytes;
    }

    public final String getText() {
        return this.text;
    }

    public final void setErasures(Integer num) {
        this.erasures = num;
    }

    public final void setErrorsCorrected(Integer num) {
        this.errorsCorrected = num;
    }

    public final void setOther(Object obj) {
        this.other = obj;
    }
}
