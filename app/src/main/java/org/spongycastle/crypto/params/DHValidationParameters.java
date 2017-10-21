package org.spongycastle.crypto.params;

import org.spongycastle.util.Arrays;

public class DHValidationParameters {
    private int counter;
    private byte[] seed;

    public DHValidationParameters(byte[] bArr, int i) {
        this.seed = bArr;
        this.counter = i;
    }

    public boolean equals(Object obj) {
        if (obj instanceof DHValidationParameters) {
            DHValidationParameters dHValidationParameters = (DHValidationParameters) obj;
            if (dHValidationParameters.counter == this.counter) {
                return Arrays.areEqual(this.seed, dHValidationParameters.seed);
            }
        }
        return false;
    }

    public int getCounter() {
        return this.counter;
    }

    public byte[] getSeed() {
        return this.seed;
    }

    public int hashCode() {
        return this.counter ^ Arrays.hashCode(this.seed);
    }
}
