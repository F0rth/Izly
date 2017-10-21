package org.spongycastle.math.ec;

import java.math.BigInteger;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.util.Arrays;

class IntArray {
    private int[] m_ints;

    public IntArray(int i) {
        this.m_ints = new int[i];
    }

    public IntArray(BigInteger bigInteger) {
        this(bigInteger, 0);
    }

    public IntArray(BigInteger bigInteger, int i) {
        int i2 = 1;
        if (bigInteger.signum() == -1) {
            throw new IllegalArgumentException("Only positive Integers allowed");
        } else if (bigInteger.equals(ECConstants.ZERO)) {
            this.m_ints = new int[]{0};
        } else {
            int i3;
            byte[] toByteArray = bigInteger.toByteArray();
            int length = toByteArray.length;
            if (toByteArray[0] == (byte) 0) {
                length--;
            } else {
                i2 = 0;
            }
            int i4 = (length + 3) / 4;
            if (i4 < i) {
                this.m_ints = new int[i];
            } else {
                this.m_ints = new int[i4];
            }
            i4--;
            int i5 = (length % 4) + i2;
            if (i2 < i5) {
                i3 = 0;
                while (i2 < i5) {
                    length = toByteArray[i2];
                    if (length < 0) {
                        length += 256;
                    }
                    i2++;
                    i3 = length | (i3 << 8);
                }
                this.m_ints[i4] = i3;
                i5 = i4 - 1;
                length = i2;
            } else {
                length = i2;
                i5 = i4;
            }
            while (i5 >= 0) {
                i2 = 0;
                i4 = 0;
                i3 = length;
                while (i2 < 4) {
                    length = toByteArray[i3];
                    if (length < 0) {
                        length += 256;
                    }
                    i2++;
                    i3++;
                    i4 = length | (i4 << 8);
                }
                this.m_ints[i5] = i4;
                i5--;
                length = i3;
            }
        }
    }

    public IntArray(int[] iArr) {
        this.m_ints = iArr;
    }

    private int[] resizedInts(int i) {
        Object obj = new int[i];
        int length = this.m_ints.length;
        if (length < i) {
            i = length;
        }
        System.arraycopy(this.m_ints, 0, obj, 0, i);
        return obj;
    }

    public void addShifted(IntArray intArray, int i) {
        int usedLength = intArray.getUsedLength();
        int i2 = usedLength + i;
        if (i2 > this.m_ints.length) {
            this.m_ints = resizedInts(i2);
        }
        for (i2 = 0; i2 < usedLength; i2++) {
            int[] iArr = this.m_ints;
            int i3 = i2 + i;
            iArr[i3] = iArr[i3] ^ intArray.m_ints[i2];
        }
    }

    public int bitLength() {
        int usedLength = getUsedLength();
        if (usedLength == 0) {
            return 0;
        }
        int i = usedLength - 1;
        usedLength = this.m_ints[i];
        i = (i << 5) + 1;
        int i2;
        if ((-65536 & usedLength) != 0) {
            if ((-16777216 & usedLength) != 0) {
                i2 = usedLength >>> 24;
                usedLength = i + 24;
                i = i2;
            } else {
                i2 = usedLength >>> 16;
                usedLength = i + 16;
                i = i2;
            }
        } else if (usedLength > CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) {
            i2 = usedLength >>> 8;
            usedLength = i + 8;
            i = i2;
        } else {
            i2 = usedLength;
            usedLength = i;
            i = i2;
        }
        while (i != 1) {
            i >>>= 1;
            usedLength++;
        }
        return usedLength;
    }

    public Object clone() {
        return new IntArray(Arrays.clone(this.m_ints));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof IntArray)) {
            return false;
        }
        IntArray intArray = (IntArray) obj;
        int usedLength = getUsedLength();
        if (intArray.getUsedLength() != usedLength) {
            return false;
        }
        for (int i = 0; i < usedLength; i++) {
            if (this.m_ints[i] != intArray.m_ints[i]) {
                return false;
            }
        }
        return true;
    }

    public void flipBit(int i) {
        int i2 = i >> 5;
        int[] iArr = this.m_ints;
        iArr[i2] = (1 << (i & 31)) ^ iArr[i2];
    }

    public int getLength() {
        return this.m_ints.length;
    }

    public int getUsedLength() {
        int length = this.m_ints.length;
        if (length <= 0) {
            return 0;
        }
        if (this.m_ints[0] != 0) {
            do {
                length--;
            } while (this.m_ints[length] == 0);
            return length + 1;
        }
        do {
            length--;
            if (this.m_ints[length] != 0) {
                return length + 1;
            }
        } while (length > 0);
        return 0;
    }

    public int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < getUsedLength(); i2++) {
            i = (i * 31) + this.m_ints[i2];
        }
        return i;
    }

    public boolean isZero() {
        return this.m_ints.length == 0 || (this.m_ints[0] == 0 && getUsedLength() == 0);
    }

    public IntArray multiply(IntArray intArray, int i) {
        int i2 = (i + 31) >> 5;
        if (this.m_ints.length < i2) {
            this.m_ints = resizedInts(i2);
        }
        IntArray intArray2 = new IntArray(intArray.resizedInts(intArray.getLength() + 1));
        IntArray intArray3 = new IntArray(((i + i) + 31) >> 5);
        int i3 = 1;
        int i4 = 0;
        while (i4 < 32) {
            int i5;
            for (i5 = 0; i5 < i2; i5++) {
                if ((this.m_ints[i5] & i3) != 0) {
                    intArray3.addShifted(intArray2, i5);
                }
            }
            i5 = i3 << 1;
            intArray2.shiftLeft();
            i4++;
            i3 = i5;
        }
        return intArray3;
    }

    public void reduce(int i, int[] iArr) {
        for (int i2 = (i + i) - 2; i2 >= i; i2--) {
            if (testBit(i2)) {
                int i3 = i2 - i;
                flipBit(i3);
                flipBit(i2);
                int length = iArr.length;
                while (true) {
                    length--;
                    if (length < 0) {
                        break;
                    }
                    flipBit(iArr[length] + i3);
                }
            }
        }
        this.m_ints = resizedInts((i + 31) >> 5);
    }

    public void setBit(int i) {
        int i2 = i >> 5;
        int[] iArr = this.m_ints;
        iArr[i2] = (1 << (i & 31)) | iArr[i2];
    }

    public IntArray shiftLeft(int i) {
        int usedLength = getUsedLength();
        if (usedLength == 0 || i == 0) {
            return this;
        }
        if (i > 31) {
            throw new IllegalArgumentException("shiftLeft() for max 31 bits , " + i + "bit shift is not possible");
        }
        int[] iArr = new int[(usedLength + 1)];
        int i2 = 32 - i;
        iArr[0] = this.m_ints[0] << i;
        for (int i3 = 1; i3 < usedLength; i3++) {
            iArr[i3] = (this.m_ints[i3] << i) | (this.m_ints[i3 - 1] >>> i2);
        }
        iArr[usedLength] = this.m_ints[usedLength - 1] >>> i2;
        return new IntArray(iArr);
    }

    public void shiftLeft() {
        int usedLength = getUsedLength();
        if (usedLength != 0) {
            if (this.m_ints[usedLength - 1] < 0) {
                usedLength++;
                if (usedLength > this.m_ints.length) {
                    this.m_ints = resizedInts(this.m_ints.length + 1);
                }
            }
            Object obj = null;
            int i = 0;
            while (i < usedLength) {
                Object obj2 = this.m_ints[i] < 0 ? 1 : null;
                int[] iArr = this.m_ints;
                iArr[i] = iArr[i] << 1;
                if (obj != null) {
                    int[] iArr2 = this.m_ints;
                    iArr2[i] = iArr2[i] | 1;
                }
                i++;
                obj = obj2;
            }
        }
    }

    public IntArray square(int i) {
        int[] iArr = new int[]{0, 1, 4, 5, 16, 17, 20, 21, 64, 65, 68, 69, 80, 81, 84, 85};
        int i2 = (i + 31) >> 5;
        if (this.m_ints.length < i2) {
            this.m_ints = resizedInts(i2);
        }
        IntArray intArray = new IntArray(i2 + i2);
        for (int i3 = 0; i3 < i2; i3++) {
            int i4;
            int i5 = 0;
            for (i4 = 0; i4 < 4; i4++) {
                i5 = (i5 >>> 8) | (iArr[(this.m_ints[i3] >>> (i4 * 4)) & 15] << 24);
            }
            intArray.m_ints[i3 + i3] = i5;
            int i6 = this.m_ints[i3];
            i5 = 0;
            for (i4 = 0; i4 < 4; i4++) {
                i5 = (i5 >>> 8) | (iArr[((i6 >>> 16) >>> (i4 * 4)) & 15] << 24);
            }
            intArray.m_ints[(i3 + i3) + 1] = i5;
        }
        return intArray;
    }

    public boolean testBit(int i) {
        return (this.m_ints[i >> 5] & (1 << (i & 31))) != 0;
    }

    public BigInteger toBigInteger() {
        int usedLength = getUsedLength();
        if (usedLength == 0) {
            return ECConstants.ZERO;
        }
        int i = this.m_ints[usedLength - 1];
        byte[] bArr = new byte[4];
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 3; i4 >= 0; i4--) {
            byte b = (byte) (i >>> (i4 * 8));
            if (i2 != 0 || b != (byte) 0) {
                bArr[i3] = b;
                i3++;
                i2 = 1;
            }
        }
        byte[] bArr2 = new byte[(((usedLength - 1) * 4) + i3)];
        for (i2 = 0; i2 < i3; i2++) {
            bArr2[i2] = bArr[i2];
        }
        int i5 = usedLength - 2;
        i2 = i3;
        while (i5 >= 0) {
            i3 = i2;
            i2 = 3;
            while (i2 >= 0) {
                bArr2[i3] = (byte) (this.m_ints[i5] >>> (i2 * 8));
                i2--;
                i3++;
            }
            i5--;
            i2 = i3;
        }
        return new BigInteger(1, bArr2);
    }

    public String toString() {
        int usedLength = getUsedLength();
        if (usedLength == 0) {
            return "0";
        }
        StringBuffer stringBuffer = new StringBuffer(Integer.toBinaryString(this.m_ints[usedLength - 1]));
        for (int i = usedLength - 2; i >= 0; i--) {
            String toBinaryString = Integer.toBinaryString(this.m_ints[i]);
            for (int length = toBinaryString.length(); length < 8; length++) {
                toBinaryString = "0" + toBinaryString;
            }
            stringBuffer.append(toBinaryString);
        }
        return stringBuffer.toString();
    }
}
