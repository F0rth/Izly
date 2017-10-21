package org.spongycastle.crypto.prng;

import org.spongycastle.crypto.tls.CipherSuite;

public class ThreadedSeedGenerator {

    class SeedGenerator implements Runnable {
        private volatile int counter;
        private volatile boolean stop;

        private SeedGenerator() {
            this.counter = 0;
            this.stop = false;
        }

        public byte[] generateSeed(int i, boolean z) {
            boolean z2 = false;
            Thread thread = new Thread(this);
            byte[] bArr = new byte[i];
            this.counter = 0;
            this.stop = false;
            thread.start();
            if (!z) {
                i *= 8;
            }
            for (int i2 = 0; i2 < i; i2++) {
                while (this.counter == z2) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                    }
                }
                z2 = this.counter;
                if (z) {
                    bArr[i2] = (byte) (z2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
                } else {
                    int i3 = i2 / 8;
                    bArr[i3] = (byte) ((bArr[i3] << 1) | (z2 & 1));
                }
            }
            this.stop = true;
            return bArr;
        }

        public void run() {
            while (!this.stop) {
                this.counter++;
            }
        }
    }

    public byte[] generateSeed(int i, boolean z) {
        return new SeedGenerator().generateSeed(i, z);
    }
}
