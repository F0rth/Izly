package com.google.zxing.qrcode.encoder;

import java.lang.reflect.Array;

public final class ByteMatrix {
    private final byte[][] bytes;
    private final int height;
    private final int width;

    public ByteMatrix(int i, int i2) {
        this.bytes = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{i2, i});
        this.width = i;
        this.height = i2;
    }

    public final void clear(byte b) {
        for (int i = 0; i < this.height; i++) {
            for (int i2 = 0; i2 < this.width; i2++) {
                this.bytes[i][i2] = b;
            }
        }
    }

    public final byte get(int i, int i2) {
        return this.bytes[i2][i];
    }

    public final byte[][] getArray() {
        return this.bytes;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getWidth() {
        return this.width;
    }

    public final void set(int i, int i2, byte b) {
        this.bytes[i2][i] = b;
    }

    public final void set(int i, int i2, int i3) {
        this.bytes[i2][i] = (byte) i3;
    }

    public final void set(int i, int i2, boolean z) {
        this.bytes[i2][i] = (byte) (z ? 1 : 0);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder(((this.width * 2) * this.height) + 2);
        for (int i = 0; i < this.height; i++) {
            for (int i2 = 0; i2 < this.width; i2++) {
                switch (this.bytes[i][i2]) {
                    case (byte) 0:
                        stringBuilder.append(" 0");
                        break;
                    case (byte) 1:
                        stringBuilder.append(" 1");
                        break;
                    default:
                        stringBuilder.append("  ");
                        break;
                }
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
