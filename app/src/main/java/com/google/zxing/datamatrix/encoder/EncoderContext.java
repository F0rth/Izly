package com.google.zxing.datamatrix.encoder;

import com.google.zxing.Dimension;
import java.nio.charset.Charset;

final class EncoderContext {
    StringBuilder codewords;
    private Dimension maxSize;
    private Dimension minSize;
    String msg;
    int newEncoding;
    int pos;
    private SymbolShapeHint shape;
    private int skipAtEnd;
    SymbolInfo symbolInfo;

    EncoderContext(String str) {
        byte[] bytes = str.getBytes(Charset.forName("ISO-8859-1"));
        StringBuilder stringBuilder = new StringBuilder(bytes.length);
        int i = 0;
        int length = bytes.length;
        while (i < length) {
            char c = (char) (bytes[i] & 255);
            if (c != '?' || str.charAt(i) == '?') {
                stringBuilder.append(c);
                i++;
            } else {
                throw new IllegalArgumentException("Message contains characters outside ISO-8859-1 encoding.");
            }
        }
        this.msg = stringBuilder.toString();
        this.shape = SymbolShapeHint.FORCE_NONE;
        this.codewords = new StringBuilder(str.length());
        this.newEncoding = -1;
    }

    private int getTotalMessageCharCount() {
        return this.msg.length() - this.skipAtEnd;
    }

    public final int getCodewordCount() {
        return this.codewords.length();
    }

    public final char getCurrent() {
        return this.msg.charAt(this.pos);
    }

    public final char getCurrentChar() {
        return this.msg.charAt(this.pos);
    }

    public final String getMessage() {
        return this.msg;
    }

    public final int getRemainingCharacters() {
        return getTotalMessageCharCount() - this.pos;
    }

    public final boolean hasMoreCharacters() {
        return this.pos < getTotalMessageCharCount();
    }

    public final void resetEncoderSignal() {
        this.newEncoding = -1;
    }

    public final void resetSymbolInfo() {
        this.symbolInfo = null;
    }

    public final void setSizeConstraints(Dimension dimension, Dimension dimension2) {
        this.minSize = dimension;
        this.maxSize = dimension2;
    }

    public final void setSkipAtEnd(int i) {
        this.skipAtEnd = i;
    }

    public final void setSymbolShape(SymbolShapeHint symbolShapeHint) {
        this.shape = symbolShapeHint;
    }

    public final void signalEncoderChange(int i) {
        this.newEncoding = i;
    }

    public final void updateSymbolInfo() {
        updateSymbolInfo(getCodewordCount());
    }

    public final void updateSymbolInfo(int i) {
        if (this.symbolInfo == null || i > this.symbolInfo.dataCapacity) {
            this.symbolInfo = SymbolInfo.lookup(i, this.shape, this.minSize, this.maxSize, true);
        }
    }

    public final void writeCodeword(char c) {
        this.codewords.append(c);
    }

    public final void writeCodewords(String str) {
        this.codewords.append(str);
    }
}
