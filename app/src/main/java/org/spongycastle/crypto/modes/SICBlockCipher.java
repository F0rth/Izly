package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.tls.CipherSuite;

public class SICBlockCipher implements BlockCipher {
    private byte[] IV = new byte[this.blockSize];
    private final int blockSize = this.cipher.getBlockSize();
    private final BlockCipher cipher;
    private byte[] counter = new byte[this.blockSize];
    private byte[] counterOut = new byte[this.blockSize];

    public SICBlockCipher(BlockCipher blockCipher) {
        this.cipher = blockCipher;
    }

    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/SIC";
    }

    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            System.arraycopy(parametersWithIV.getIV(), 0, this.IV, 0, this.IV.length);
            reset();
            if (parametersWithIV.getParameters() != null) {
                this.cipher.init(true, parametersWithIV.getParameters());
                return;
            }
            return;
        }
        throw new IllegalArgumentException("SIC mode requires ParametersWithIV");
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) throws DataLengthException, IllegalStateException {
        int i3;
        this.cipher.processBlock(this.counter, 0, this.counterOut, 0);
        for (i3 = 0; i3 < this.counterOut.length; i3++) {
            bArr2[i2 + i3] = (byte) (this.counterOut[i3] ^ bArr[i + i3]);
        }
        i3 = 1;
        for (int length = this.counter.length - 1; length >= 0; length--) {
            int i4 = (this.counter[length] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + i3;
            i3 = i4 > CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV ? 1 : 0;
            this.counter[length] = (byte) i4;
        }
        return this.counter.length;
    }

    public void reset() {
        System.arraycopy(this.IV, 0, this.counter, 0, this.counter.length);
        this.cipher.reset();
    }
}
