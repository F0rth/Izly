package org.spongycastle.crypto.engines;

import java.util.Hashtable;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithSBox;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class GOST28147Engine implements BlockCipher {
    protected static final int BLOCK_SIZE = 8;
    private static byte[] DSbox_A = new byte[]{(byte) 10, (byte) 4, (byte) 5, (byte) 6, (byte) 8, (byte) 1, (byte) 3, (byte) 7, (byte) 13, (byte) 12, (byte) 14, (byte) 0, (byte) 9, (byte) 2, (byte) 11, (byte) 15, (byte) 5, (byte) 15, (byte) 4, (byte) 0, (byte) 2, (byte) 13, (byte) 11, (byte) 9, (byte) 1, (byte) 7, (byte) 6, (byte) 3, (byte) 12, (byte) 14, (byte) 10, (byte) 8, (byte) 7, (byte) 15, (byte) 12, (byte) 14, (byte) 9, (byte) 4, (byte) 1, (byte) 0, (byte) 3, (byte) 11, (byte) 5, (byte) 2, (byte) 6, (byte) 10, (byte) 8, (byte) 13, (byte) 4, (byte) 10, (byte) 7, (byte) 12, (byte) 0, (byte) 15, (byte) 2, (byte) 8, (byte) 14, (byte) 1, (byte) 6, (byte) 5, (byte) 13, (byte) 11, (byte) 9, (byte) 3, (byte) 7, (byte) 6, (byte) 4, (byte) 11, (byte) 9, (byte) 12, (byte) 2, (byte) 10, (byte) 1, (byte) 8, (byte) 0, (byte) 14, (byte) 15, (byte) 13, (byte) 3, (byte) 5, (byte) 7, (byte) 6, (byte) 2, (byte) 4, (byte) 13, (byte) 9, (byte) 15, (byte) 0, (byte) 10, (byte) 1, (byte) 5, (byte) 11, (byte) 8, (byte) 14, (byte) 12, (byte) 3, (byte) 13, (byte) 14, (byte) 4, (byte) 1, (byte) 7, (byte) 0, (byte) 5, (byte) 10, (byte) 3, (byte) 12, (byte) 8, (byte) 15, (byte) 6, (byte) 2, (byte) 9, (byte) 11, (byte) 1, (byte) 3, (byte) 10, (byte) 9, (byte) 5, (byte) 11, (byte) 4, (byte) 15, (byte) 8, (byte) 6, (byte) 7, (byte) 14, (byte) 13, (byte) 0, (byte) 2, (byte) 12};
    private static byte[] DSbox_Test = new byte[]{(byte) 4, (byte) 10, (byte) 9, (byte) 2, (byte) 13, (byte) 8, (byte) 0, (byte) 14, (byte) 6, (byte) 11, (byte) 1, (byte) 12, (byte) 7, (byte) 15, (byte) 5, (byte) 3, (byte) 14, (byte) 11, (byte) 4, (byte) 12, (byte) 6, (byte) 13, (byte) 15, (byte) 10, (byte) 2, (byte) 3, (byte) 8, (byte) 1, (byte) 0, (byte) 7, (byte) 5, (byte) 9, (byte) 5, (byte) 8, (byte) 1, (byte) 13, (byte) 10, (byte) 3, (byte) 4, (byte) 2, (byte) 14, (byte) 15, (byte) 12, (byte) 7, (byte) 6, (byte) 0, (byte) 9, (byte) 11, (byte) 7, (byte) 13, (byte) 10, (byte) 1, (byte) 0, (byte) 8, (byte) 9, (byte) 15, (byte) 14, (byte) 4, (byte) 6, (byte) 12, (byte) 11, (byte) 2, (byte) 5, (byte) 3, (byte) 6, (byte) 12, (byte) 7, (byte) 1, (byte) 5, (byte) 15, (byte) 13, (byte) 8, (byte) 4, (byte) 10, (byte) 9, (byte) 14, (byte) 0, (byte) 3, (byte) 11, (byte) 2, (byte) 4, (byte) 11, (byte) 10, (byte) 0, (byte) 7, (byte) 2, (byte) 1, (byte) 13, (byte) 3, (byte) 6, (byte) 8, (byte) 5, (byte) 9, (byte) 12, (byte) 15, (byte) 14, (byte) 13, (byte) 11, (byte) 4, (byte) 1, (byte) 3, (byte) 15, (byte) 5, (byte) 9, (byte) 0, (byte) 10, (byte) 14, (byte) 7, (byte) 6, (byte) 8, (byte) 2, (byte) 12, (byte) 1, (byte) 15, (byte) 13, (byte) 0, (byte) 5, (byte) 7, (byte) 10, (byte) 4, (byte) 9, (byte) 2, (byte) 3, (byte) 14, (byte) 6, (byte) 11, (byte) 8, (byte) 12};
    private static byte[] ESbox_A = new byte[]{(byte) 9, (byte) 6, (byte) 3, (byte) 2, (byte) 8, (byte) 11, (byte) 1, (byte) 7, (byte) 10, (byte) 4, (byte) 14, (byte) 15, (byte) 12, (byte) 0, (byte) 13, (byte) 5, (byte) 3, (byte) 7, (byte) 14, (byte) 9, (byte) 8, (byte) 10, (byte) 15, (byte) 0, (byte) 5, (byte) 2, (byte) 6, (byte) 12, (byte) 11, (byte) 4, (byte) 13, (byte) 1, (byte) 14, (byte) 4, (byte) 6, (byte) 2, (byte) 11, (byte) 3, (byte) 13, (byte) 8, (byte) 12, (byte) 15, (byte) 5, (byte) 10, (byte) 0, (byte) 7, (byte) 1, (byte) 9, (byte) 14, (byte) 7, (byte) 10, (byte) 12, (byte) 13, (byte) 1, (byte) 3, (byte) 9, (byte) 0, (byte) 2, (byte) 11, (byte) 4, (byte) 15, (byte) 8, (byte) 5, (byte) 6, (byte) 11, (byte) 5, (byte) 1, (byte) 9, (byte) 8, (byte) 13, (byte) 15, (byte) 0, (byte) 14, (byte) 4, (byte) 2, (byte) 3, (byte) 12, (byte) 7, (byte) 10, (byte) 6, (byte) 3, (byte) 10, (byte) 13, (byte) 12, (byte) 1, (byte) 2, (byte) 0, (byte) 11, (byte) 7, (byte) 5, (byte) 9, (byte) 4, (byte) 8, (byte) 15, (byte) 14, (byte) 6, (byte) 1, (byte) 13, (byte) 2, (byte) 9, (byte) 7, (byte) 10, (byte) 6, (byte) 0, (byte) 8, (byte) 12, (byte) 4, (byte) 5, (byte) 15, (byte) 3, (byte) 11, (byte) 14, (byte) 11, (byte) 10, (byte) 15, (byte) 5, (byte) 0, (byte) 12, (byte) 14, (byte) 8, (byte) 6, (byte) 2, (byte) 3, (byte) 9, (byte) 1, (byte) 7, (byte) 13, (byte) 4};
    private static byte[] ESbox_B = new byte[]{(byte) 8, (byte) 4, (byte) 11, (byte) 1, (byte) 3, (byte) 5, (byte) 0, (byte) 9, (byte) 2, (byte) 14, (byte) 10, (byte) 12, (byte) 13, (byte) 6, (byte) 7, (byte) 15, (byte) 0, (byte) 1, (byte) 2, (byte) 10, (byte) 4, (byte) 13, (byte) 5, (byte) 12, (byte) 9, (byte) 7, (byte) 3, (byte) 15, (byte) 11, (byte) 8, (byte) 6, (byte) 14, (byte) 14, (byte) 12, (byte) 0, (byte) 10, (byte) 9, (byte) 2, (byte) 13, (byte) 11, (byte) 7, (byte) 5, (byte) 8, (byte) 15, (byte) 3, (byte) 6, (byte) 1, (byte) 4, (byte) 7, (byte) 5, (byte) 0, (byte) 13, (byte) 11, (byte) 6, (byte) 1, (byte) 2, (byte) 3, (byte) 10, (byte) 12, (byte) 15, (byte) 4, (byte) 14, (byte) 9, (byte) 8, (byte) 2, (byte) 7, (byte) 12, (byte) 15, (byte) 9, (byte) 5, (byte) 10, (byte) 11, (byte) 1, (byte) 4, (byte) 0, (byte) 13, (byte) 6, (byte) 8, (byte) 14, (byte) 3, (byte) 8, (byte) 3, (byte) 2, (byte) 6, (byte) 4, (byte) 13, (byte) 14, (byte) 11, (byte) 12, (byte) 1, (byte) 7, (byte) 15, (byte) 10, (byte) 0, (byte) 9, (byte) 5, (byte) 5, (byte) 2, (byte) 10, (byte) 11, (byte) 9, (byte) 1, (byte) 12, (byte) 3, (byte) 7, (byte) 4, (byte) 13, (byte) 0, (byte) 6, (byte) 15, (byte) 8, (byte) 14, (byte) 0, (byte) 4, (byte) 11, (byte) 14, (byte) 8, (byte) 3, (byte) 7, (byte) 1, (byte) 10, (byte) 2, (byte) 9, (byte) 6, (byte) 15, (byte) 13, (byte) 5, (byte) 12};
    private static byte[] ESbox_C = new byte[]{(byte) 1, (byte) 11, (byte) 12, (byte) 2, (byte) 9, (byte) 13, (byte) 0, (byte) 15, (byte) 4, (byte) 5, (byte) 8, (byte) 14, (byte) 10, (byte) 7, (byte) 6, (byte) 3, (byte) 0, (byte) 1, (byte) 7, (byte) 13, (byte) 11, (byte) 4, (byte) 5, (byte) 2, (byte) 8, (byte) 14, (byte) 15, (byte) 12, (byte) 9, (byte) 10, (byte) 6, (byte) 3, (byte) 8, (byte) 2, (byte) 5, (byte) 0, (byte) 4, (byte) 9, (byte) 15, (byte) 10, (byte) 3, (byte) 7, (byte) 12, (byte) 13, (byte) 6, (byte) 14, (byte) 1, (byte) 11, (byte) 3, (byte) 6, (byte) 0, (byte) 1, (byte) 5, (byte) 13, (byte) 10, (byte) 8, (byte) 11, (byte) 2, (byte) 9, (byte) 7, (byte) 14, (byte) 15, (byte) 12, (byte) 4, (byte) 8, (byte) 13, (byte) 11, (byte) 0, (byte) 4, (byte) 5, (byte) 1, (byte) 2, (byte) 9, (byte) 3, (byte) 12, (byte) 14, (byte) 6, (byte) 15, (byte) 10, (byte) 7, (byte) 12, (byte) 9, (byte) 11, (byte) 1, (byte) 8, (byte) 14, (byte) 2, (byte) 4, (byte) 7, (byte) 3, (byte) 6, (byte) 5, (byte) 10, (byte) 0, (byte) 15, (byte) 13, (byte) 10, (byte) 9, (byte) 6, (byte) 8, (byte) 13, (byte) 14, (byte) 2, (byte) 0, (byte) 15, (byte) 3, (byte) 5, (byte) 11, (byte) 4, (byte) 1, (byte) 12, (byte) 7, (byte) 7, (byte) 4, (byte) 0, (byte) 5, (byte) 10, (byte) 2, (byte) 15, (byte) 14, (byte) 12, (byte) 6, (byte) 1, (byte) 11, (byte) 13, (byte) 9, (byte) 3, (byte) 8};
    private static byte[] ESbox_D = new byte[]{(byte) 15, (byte) 12, (byte) 2, (byte) 10, (byte) 6, (byte) 4, (byte) 5, (byte) 0, (byte) 7, (byte) 9, (byte) 14, (byte) 13, (byte) 1, (byte) 11, (byte) 8, (byte) 3, (byte) 11, (byte) 6, (byte) 3, (byte) 4, (byte) 12, (byte) 15, (byte) 14, (byte) 2, (byte) 7, (byte) 13, (byte) 8, (byte) 0, (byte) 5, (byte) 10, (byte) 9, (byte) 1, (byte) 1, (byte) 12, (byte) 11, (byte) 0, (byte) 15, (byte) 14, (byte) 6, (byte) 5, (byte) 10, (byte) 13, (byte) 4, (byte) 8, (byte) 9, (byte) 3, (byte) 7, (byte) 2, (byte) 1, (byte) 5, (byte) 14, (byte) 12, (byte) 10, (byte) 7, (byte) 0, (byte) 13, (byte) 6, (byte) 2, (byte) 11, (byte) 4, (byte) 9, (byte) 3, (byte) 15, (byte) 8, (byte) 0, (byte) 12, (byte) 8, (byte) 9, (byte) 13, (byte) 2, (byte) 10, (byte) 11, (byte) 7, (byte) 3, (byte) 6, (byte) 5, (byte) 4, (byte) 14, (byte) 15, (byte) 1, (byte) 8, (byte) 0, (byte) 15, (byte) 3, (byte) 2, (byte) 5, (byte) 14, (byte) 11, (byte) 1, (byte) 10, (byte) 4, (byte) 7, (byte) 12, (byte) 9, (byte) 13, (byte) 6, (byte) 3, (byte) 0, (byte) 6, (byte) 15, (byte) 1, (byte) 14, (byte) 9, (byte) 2, (byte) 13, (byte) 8, (byte) 12, (byte) 4, (byte) 11, (byte) 10, (byte) 5, (byte) 7, (byte) 1, (byte) 10, (byte) 6, (byte) 8, (byte) 15, (byte) 11, (byte) 0, (byte) 4, (byte) 12, (byte) 3, (byte) 5, (byte) 9, (byte) 7, (byte) 13, (byte) 2, (byte) 14};
    private static byte[] ESbox_Test = new byte[]{(byte) 4, (byte) 2, (byte) 15, (byte) 5, (byte) 9, (byte) 1, (byte) 0, (byte) 8, (byte) 14, (byte) 3, (byte) 11, (byte) 12, (byte) 13, (byte) 7, (byte) 10, (byte) 6, (byte) 12, (byte) 9, (byte) 15, (byte) 14, (byte) 8, (byte) 1, (byte) 3, (byte) 10, (byte) 2, (byte) 7, (byte) 4, (byte) 13, (byte) 6, (byte) 0, (byte) 11, (byte) 5, (byte) 13, (byte) 8, (byte) 14, (byte) 12, (byte) 7, (byte) 3, (byte) 9, (byte) 10, (byte) 1, (byte) 5, (byte) 2, (byte) 4, (byte) 6, (byte) 15, (byte) 0, (byte) 11, (byte) 14, (byte) 9, (byte) 11, (byte) 2, (byte) 5, (byte) 15, (byte) 7, (byte) 1, (byte) 0, (byte) 13, (byte) 12, (byte) 6, (byte) 10, (byte) 4, (byte) 3, (byte) 8, (byte) 3, (byte) 14, (byte) 5, (byte) 9, (byte) 6, (byte) 8, (byte) 0, (byte) 13, (byte) 10, (byte) 11, (byte) 7, (byte) 12, (byte) 2, (byte) 1, (byte) 15, (byte) 4, (byte) 8, (byte) 15, (byte) 6, (byte) 11, (byte) 1, (byte) 9, (byte) 12, (byte) 5, (byte) 13, (byte) 3, (byte) 7, (byte) 10, (byte) 0, (byte) 14, (byte) 2, (byte) 4, (byte) 9, (byte) 11, (byte) 12, (byte) 0, (byte) 3, (byte) 6, (byte) 7, (byte) 5, (byte) 4, (byte) 8, (byte) 14, (byte) 15, (byte) 1, (byte) 10, (byte) 2, (byte) 13, (byte) 12, (byte) 6, (byte) 5, (byte) 2, (byte) 11, (byte) 0, (byte) 9, (byte) 13, (byte) 3, (byte) 14, (byte) 7, (byte) 10, (byte) 15, (byte) 4, (byte) 1, (byte) 8};
    private static byte[] Sbox_Default = new byte[]{(byte) 4, (byte) 10, (byte) 9, (byte) 2, (byte) 13, (byte) 8, (byte) 0, (byte) 14, (byte) 6, (byte) 11, (byte) 1, (byte) 12, (byte) 7, (byte) 15, (byte) 5, (byte) 3, (byte) 14, (byte) 11, (byte) 4, (byte) 12, (byte) 6, (byte) 13, (byte) 15, (byte) 10, (byte) 2, (byte) 3, (byte) 8, (byte) 1, (byte) 0, (byte) 7, (byte) 5, (byte) 9, (byte) 5, (byte) 8, (byte) 1, (byte) 13, (byte) 10, (byte) 3, (byte) 4, (byte) 2, (byte) 14, (byte) 15, (byte) 12, (byte) 7, (byte) 6, (byte) 0, (byte) 9, (byte) 11, (byte) 7, (byte) 13, (byte) 10, (byte) 1, (byte) 0, (byte) 8, (byte) 9, (byte) 15, (byte) 14, (byte) 4, (byte) 6, (byte) 12, (byte) 11, (byte) 2, (byte) 5, (byte) 3, (byte) 6, (byte) 12, (byte) 7, (byte) 1, (byte) 5, (byte) 15, (byte) 13, (byte) 8, (byte) 4, (byte) 10, (byte) 9, (byte) 14, (byte) 0, (byte) 3, (byte) 11, (byte) 2, (byte) 4, (byte) 11, (byte) 10, (byte) 0, (byte) 7, (byte) 2, (byte) 1, (byte) 13, (byte) 3, (byte) 6, (byte) 8, (byte) 5, (byte) 9, (byte) 12, (byte) 15, (byte) 14, (byte) 13, (byte) 11, (byte) 4, (byte) 1, (byte) 3, (byte) 15, (byte) 5, (byte) 9, (byte) 0, (byte) 10, (byte) 14, (byte) 7, (byte) 6, (byte) 8, (byte) 2, (byte) 12, (byte) 1, (byte) 15, (byte) 13, (byte) 0, (byte) 5, (byte) 7, (byte) 10, (byte) 4, (byte) 9, (byte) 2, (byte) 3, (byte) 14, (byte) 6, (byte) 11, (byte) 8, (byte) 12};
    private static Hashtable sBoxes = new Hashtable();
    private byte[] S = Sbox_Default;
    private boolean forEncryption;
    private int[] workingKey = null;

    static {
        addSBox("Default", Sbox_Default);
        addSBox("E-TEST", ESbox_Test);
        addSBox("E-A", ESbox_A);
        addSBox("E-B", ESbox_B);
        addSBox("E-C", ESbox_C);
        addSBox("E-D", ESbox_D);
        addSBox("D-TEST", DSbox_Test);
        addSBox("D-A", DSbox_A);
    }

    private void GOST28147Func(int[] iArr, byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3 = 7;
        int bytesToint = bytesToint(bArr, i);
        int bytesToint2 = bytesToint(bArr, i + 4);
        int i4;
        int i5;
        int i6;
        if (this.forEncryption) {
            i4 = 0;
            while (i4 < 3) {
                i5 = bytesToint;
                bytesToint = bytesToint2;
                bytesToint2 = 0;
                while (bytesToint2 < 8) {
                    bytesToint2++;
                    i6 = i5;
                    i5 = bytesToint ^ GOST28147_mainStep(i5, iArr[bytesToint2]);
                    bytesToint = i6;
                }
                i4++;
                bytesToint2 = bytesToint;
                bytesToint = i5;
            }
            while (i3 > 0) {
                i3--;
                i6 = bytesToint;
                bytesToint = bytesToint2 ^ GOST28147_mainStep(bytesToint, iArr[i3]);
                bytesToint2 = i6;
            }
        } else {
            i5 = 0;
            while (i5 < 8) {
                i5++;
                i6 = bytesToint;
                bytesToint = bytesToint2 ^ GOST28147_mainStep(bytesToint, iArr[i5]);
                bytesToint2 = i6;
            }
            i4 = 0;
            while (i4 < 3) {
                i5 = bytesToint;
                bytesToint = bytesToint2;
                bytesToint2 = 7;
                while (bytesToint2 >= 0 && (i4 != 2 || bytesToint2 != 0)) {
                    bytesToint2--;
                    i6 = i5;
                    i5 = bytesToint ^ GOST28147_mainStep(i5, iArr[bytesToint2]);
                    bytesToint = i6;
                }
                i4++;
                bytesToint2 = bytesToint;
                bytesToint = i5;
            }
        }
        i3 = GOST28147_mainStep(bytesToint, iArr[0]);
        intTobytes(bytesToint, bArr2, i2);
        intTobytes(bytesToint2 ^ i3, bArr2, i2 + 4);
    }

    private int GOST28147_mainStep(int i, int i2) {
        int i3 = i2 + i;
        i3 = (this.S[((i3 >> 28) & 15) + 112] << 28) + (((((((this.S[((i3 >> 0) & 15) + 0] << 0) + (this.S[((i3 >> 4) & 15) + 16] << 4)) + (this.S[((i3 >> 8) & 15) + 32] << 8)) + (this.S[((i3 >> 12) & 15) + 48] << 12)) + (this.S[((i3 >> 16) & 15) + 64] << 16)) + (this.S[((i3 >> 20) & 15) + 80] << 20)) + (this.S[((i3 >> 24) & 15) + 96] << 24));
        return (i3 << 11) | (i3 >>> 21);
    }

    private static void addSBox(String str, byte[] bArr) {
        sBoxes.put(Strings.toUpperCase(str), bArr);
    }

    private int bytesToint(byte[] bArr, int i) {
        return ((((bArr[i + 3] << 24) & -16777216) + ((bArr[i + 2] << 16) & 16711680)) + ((bArr[i + 1] << 8) & 65280)) + (bArr[i] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
    }

    private int[] generateWorkingKey(boolean z, byte[] bArr) {
        this.forEncryption = z;
        if (bArr.length != 32) {
            throw new IllegalArgumentException("Key length invalid. Key needs to be 32 byte - 256 bit!!!");
        }
        int[] iArr = new int[8];
        for (int i = 0; i != 8; i++) {
            iArr[i] = bytesToint(bArr, i * 4);
        }
        return iArr;
    }

    public static byte[] getSBox(String str) {
        byte[] bArr = (byte[]) sBoxes.get(Strings.toUpperCase(str));
        if (bArr != null) {
            return Arrays.clone(bArr);
        }
        throw new IllegalArgumentException("Unknown S-Box - possible types: \"Default\", \"E-Test\", \"E-A\", \"E-B\", \"E-C\", \"E-D\", \"D-Test\", \"D-A\".");
    }

    private void intTobytes(int i, byte[] bArr, int i2) {
        bArr[i2 + 3] = (byte) (i >>> 24);
        bArr[i2 + 2] = (byte) (i >>> 16);
        bArr[i2 + 1] = (byte) (i >>> 8);
        bArr[i2] = (byte) i;
    }

    public String getAlgorithmName() {
        return "GOST28147";
    }

    public int getBlockSize() {
        return 8;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof ParametersWithSBox) {
            ParametersWithSBox parametersWithSBox = (ParametersWithSBox) cipherParameters;
            byte[] sBox = parametersWithSBox.getSBox();
            if (sBox.length != Sbox_Default.length) {
                throw new IllegalArgumentException("invalid S-box passed to GOST28147 init");
            }
            this.S = Arrays.clone(sBox);
            if (parametersWithSBox.getParameters() != null) {
                this.workingKey = generateWorkingKey(z, ((KeyParameter) parametersWithSBox.getParameters()).getKey());
            }
        } else if (cipherParameters instanceof KeyParameter) {
            this.workingKey = generateWorkingKey(z, ((KeyParameter) cipherParameters).getKey());
        } else if (cipherParameters != null) {
            throw new IllegalArgumentException("invalid parameter passed to GOST28147 init - " + cipherParameters.getClass().getName());
        }
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.workingKey == null) {
            throw new IllegalStateException("GOST28147 engine not initialised");
        } else if (i + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 8 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else {
            GOST28147Func(this.workingKey, bArr, i, bArr2, i2);
            return 8;
        }
    }

    public void reset() {
    }
}
