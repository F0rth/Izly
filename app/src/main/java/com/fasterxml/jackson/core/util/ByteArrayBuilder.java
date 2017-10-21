package com.fasterxml.jackson.core.util;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;

public final class ByteArrayBuilder extends OutputStream {
    static final int DEFAULT_BLOCK_ARRAY_SIZE = 40;
    private static final int INITIAL_BLOCK_SIZE = 500;
    private static final int MAX_BLOCK_SIZE = 262144;
    public static final byte[] NO_BYTES = new byte[0];
    private final BufferRecycler _bufferRecycler;
    private byte[] _currBlock;
    private int _currBlockPtr;
    private final LinkedList<byte[]> _pastBlocks;
    private int _pastLen;

    public ByteArrayBuilder() {
        this(null);
    }

    public ByteArrayBuilder(int i) {
        this(null, i);
    }

    public ByteArrayBuilder(BufferRecycler bufferRecycler) {
        this(bufferRecycler, INITIAL_BLOCK_SIZE);
    }

    public ByteArrayBuilder(BufferRecycler bufferRecycler, int i) {
        this._pastBlocks = new LinkedList();
        this._bufferRecycler = bufferRecycler;
        this._currBlock = bufferRecycler == null ? new byte[i] : bufferRecycler.allocByteBuffer(2);
    }

    private void _allocMore() {
        this._pastLen += this._currBlock.length;
        int max = Math.max(this._pastLen >> 1, 1000);
        if (max > 262144) {
            max = 262144;
        }
        this._pastBlocks.add(this._currBlock);
        this._currBlock = new byte[max];
        this._currBlockPtr = 0;
    }

    public final void append(int i) {
        if (this._currBlockPtr >= this._currBlock.length) {
            _allocMore();
        }
        byte[] bArr = this._currBlock;
        int i2 = this._currBlockPtr;
        this._currBlockPtr = i2 + 1;
        bArr[i2] = (byte) i;
    }

    public final void appendThreeBytes(int i) {
        if (this._currBlockPtr + 2 < this._currBlock.length) {
            byte[] bArr = this._currBlock;
            int i2 = this._currBlockPtr;
            this._currBlockPtr = i2 + 1;
            bArr[i2] = (byte) (i >> 16);
            bArr = this._currBlock;
            i2 = this._currBlockPtr;
            this._currBlockPtr = i2 + 1;
            bArr[i2] = (byte) (i >> 8);
            bArr = this._currBlock;
            i2 = this._currBlockPtr;
            this._currBlockPtr = i2 + 1;
            bArr[i2] = (byte) i;
            return;
        }
        append(i >> 16);
        append(i >> 8);
        append(i);
    }

    public final void appendTwoBytes(int i) {
        if (this._currBlockPtr + 1 < this._currBlock.length) {
            byte[] bArr = this._currBlock;
            int i2 = this._currBlockPtr;
            this._currBlockPtr = i2 + 1;
            bArr[i2] = (byte) (i >> 8);
            bArr = this._currBlock;
            i2 = this._currBlockPtr;
            this._currBlockPtr = i2 + 1;
            bArr[i2] = (byte) i;
            return;
        }
        append(i >> 8);
        append(i);
    }

    public final void close() {
    }

    public final byte[] completeAndCoalesce(int i) {
        this._currBlockPtr = i;
        return toByteArray();
    }

    public final byte[] finishCurrentSegment() {
        _allocMore();
        return this._currBlock;
    }

    public final void flush() {
    }

    public final byte[] getCurrentSegment() {
        return this._currBlock;
    }

    public final int getCurrentSegmentLength() {
        return this._currBlockPtr;
    }

    public final void release() {
        reset();
        if (this._bufferRecycler != null && this._currBlock != null) {
            this._bufferRecycler.releaseByteBuffer(2, this._currBlock);
            this._currBlock = null;
        }
    }

    public final void reset() {
        this._pastLen = 0;
        this._currBlockPtr = 0;
        if (!this._pastBlocks.isEmpty()) {
            this._pastBlocks.clear();
        }
    }

    public final byte[] resetAndGetFirstSegment() {
        reset();
        return this._currBlock;
    }

    public final void setCurrentSegmentLength(int i) {
        this._currBlockPtr = i;
    }

    public final byte[] toByteArray() {
        int i = this._pastLen + this._currBlockPtr;
        if (i == 0) {
            return NO_BYTES;
        }
        Object obj = new byte[i];
        Iterator it = this._pastBlocks.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            byte[] bArr = (byte[]) it.next();
            int length = bArr.length;
            System.arraycopy(bArr, 0, obj, i2, length);
            i2 += length;
        }
        System.arraycopy(this._currBlock, 0, obj, i2, this._currBlockPtr);
        int i3 = this._currBlockPtr + i2;
        if (i3 != i) {
            throw new RuntimeException("Internal error: total len assumed to be " + i + ", copied " + i3 + " bytes");
        }
        if (!this._pastBlocks.isEmpty()) {
            reset();
        }
        return obj;
    }

    public final void write(int i) {
        append(i);
    }

    public final void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    public final void write(byte[] bArr, int i, int i2) {
        while (true) {
            int min = Math.min(this._currBlock.length - this._currBlockPtr, i2);
            if (min > 0) {
                System.arraycopy(bArr, i, this._currBlock, this._currBlockPtr, min);
                i += min;
                this._currBlockPtr += min;
                i2 -= min;
            }
            if (i2 > 0) {
                _allocMore();
            } else {
                return;
            }
        }
    }
}
