package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.Writer;

public final class SegmentedStringWriter extends Writer {
    protected final TextBuffer _buffer;

    public SegmentedStringWriter(BufferRecycler bufferRecycler) {
        this._buffer = new TextBuffer(bufferRecycler);
    }

    public final Writer append(char c) {
        write((int) c);
        return this;
    }

    public final Writer append(CharSequence charSequence) {
        String obj = charSequence.toString();
        this._buffer.append(obj, 0, obj.length());
        return this;
    }

    public final Writer append(CharSequence charSequence, int i, int i2) {
        String obj = charSequence.subSequence(i, i2).toString();
        this._buffer.append(obj, 0, obj.length());
        return this;
    }

    public final void close() {
    }

    public final void flush() {
    }

    public final String getAndClear() {
        String contentsAsString = this._buffer.contentsAsString();
        this._buffer.releaseBuffers();
        return contentsAsString;
    }

    public final void write(int i) {
        this._buffer.append((char) i);
    }

    public final void write(String str) {
        this._buffer.append(str, 0, str.length());
    }

    public final void write(String str, int i, int i2) {
        this._buffer.append(str, i, i2);
    }

    public final void write(char[] cArr) {
        this._buffer.append(cArr, 0, cArr.length);
    }

    public final void write(char[] cArr, int i, int i2) {
        this._buffer.append(cArr, i, i2);
    }
}
