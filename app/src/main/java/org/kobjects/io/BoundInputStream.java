package org.kobjects.io;

import java.io.IOException;
import java.io.InputStream;

public class BoundInputStream extends InputStream {
    InputStream is;
    int remaining;

    public BoundInputStream(InputStream inputStream, int i) {
        this.is = inputStream;
        this.remaining = i;
    }

    public int available() throws IOException {
        int available = this.is.available();
        return available < this.remaining ? available : this.remaining;
    }

    public void close() {
        try {
            this.is.close();
        } catch (IOException e) {
        }
    }

    public int read() throws IOException {
        if (this.remaining <= 0) {
            return -1;
        }
        this.remaining--;
        return this.is.read();
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i2 > this.remaining) {
            i2 = this.remaining;
        }
        int read = this.is.read(bArr, i, i2);
        if (read > 0) {
            this.remaining -= read;
        }
        return read;
    }
}
