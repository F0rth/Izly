package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.crypto.util.Pack;

public abstract class LongDigest implements ExtendedDigest {
    private static final int BYTE_LENGTH = 128;
    static final long[] K = new long[]{4794697086780616226L, 8158064640168781261L, -5349999486874862801L, -1606136188198331460L, 4131703408338449720L, 6480981068601479193L, -7908458776815382629L, -6116909921290321640L, -2880145864133508542L, 1334009975649890238L, 2608012711638119052L, 6128411473006802146L, 8268148722764581231L, -9160688886553864527L, -7215885187991268811L, -4495734319001033068L, -1973867731355612462L, -1171420211273849373L, 1135362057144423861L, 2597628984639134821L, 3308224258029322869L, 5365058923640841347L, 6679025012923562964L, 8573033837759648693L, -7476448914759557205L, -6327057829258317296L, -5763719355590565569L, -4658551843659510044L, -4116276920077217854L, -3051310485924567259L, 489312712824947311L, 1452737877330783856L, 2861767655752347644L, 3322285676063803686L, 5560940570517711597L, 5996557281743188959L, 7280758554555802590L, 8532644243296465576L, -9096487096722542874L, -7894198246740708037L, -6719396339535248540L, -6333637450476146687L, -4446306890439682159L, -4076793802049405392L, -3345356375505022440L, -2983346525034927856L, -860691631967231958L, 1182934255886127544L, 1847814050463011016L, 2177327727835720531L, 2830643537854262169L, 3796741975233480872L, 4115178125766777443L, 5681478168544905931L, 6601373596472566643L, 7507060721942968483L, 8399075790359081724L, 8693463985226723168L, -8878714635349349518L, -8302665154208450068L, -8016688836872298968L, -6606660893046293015L, -4685533653050689259L, -4147400797238176981L, -3880063495543823972L, -3348786107499101689L, -1523767162380948706L, -757361751448694408L, 500013540394364858L, 748580250866718886L, 1242879168328830382L, 1977374033974150939L, 2944078676154940804L, 3659926193048069267L, 4368137639120453308L, 4836135668995329356L, 5532061633213252278L, 6448918945643986474L, 6902733635092675308L, 7801388544844847127L};
    protected long H1;
    protected long H2;
    protected long H3;
    protected long H4;
    protected long H5;
    protected long H6;
    protected long H7;
    protected long H8;
    private long[] W;
    private long byteCount1;
    private long byteCount2;
    private int wOff;
    private byte[] xBuf;
    private int xBufOff;

    protected LongDigest() {
        this.W = new long[80];
        this.xBuf = new byte[8];
        this.xBufOff = 0;
        reset();
    }

    protected LongDigest(LongDigest longDigest) {
        this.W = new long[80];
        this.xBuf = new byte[longDigest.xBuf.length];
        System.arraycopy(longDigest.xBuf, 0, this.xBuf, 0, longDigest.xBuf.length);
        this.xBufOff = longDigest.xBufOff;
        this.byteCount1 = longDigest.byteCount1;
        this.byteCount2 = longDigest.byteCount2;
        this.H1 = longDigest.H1;
        this.H2 = longDigest.H2;
        this.H3 = longDigest.H3;
        this.H4 = longDigest.H4;
        this.H5 = longDigest.H5;
        this.H6 = longDigest.H6;
        this.H7 = longDigest.H7;
        this.H8 = longDigest.H8;
        System.arraycopy(longDigest.W, 0, this.W, 0, longDigest.W.length);
        this.wOff = longDigest.wOff;
    }

    private long Ch(long j, long j2, long j3) {
        return (j & j2) ^ ((-1 ^ j) & j3);
    }

    private long Maj(long j, long j2, long j3) {
        return ((j & j2) ^ (j & j3)) ^ (j2 & j3);
    }

    private long Sigma0(long j) {
        return (((j << 63) | (j >>> 1)) ^ ((j << 56) | (j >>> 8))) ^ (j >>> 7);
    }

    private long Sigma1(long j) {
        return (((j << 45) | (j >>> 19)) ^ ((j << 3) | (j >>> 61))) ^ (j >>> 6);
    }

    private long Sum0(long j) {
        return (((j << 36) | (j >>> 28)) ^ ((j << 30) | (j >>> 34))) ^ ((j << 25) | (j >>> 39));
    }

    private long Sum1(long j) {
        return (((j << 50) | (j >>> 14)) ^ ((j << 46) | (j >>> 18))) ^ ((j << 23) | (j >>> 41));
    }

    private void adjustByteCounts() {
        if (this.byteCount1 > 2305843009213693951L) {
            this.byteCount2 += this.byteCount1 >>> 61;
            this.byteCount1 &= 2305843009213693951L;
        }
    }

    public void finish() {
        adjustByteCounts();
        long j = this.byteCount1;
        long j2 = this.byteCount2;
        update(Byte.MIN_VALUE);
        while (this.xBufOff != 0) {
            update((byte) 0);
        }
        processLength(j << 3, j2);
        processBlock();
    }

    public int getByteLength() {
        return 128;
    }

    protected void processBlock() {
        int i;
        adjustByteCounts();
        for (i = 16; i <= 79; i++) {
            this.W[i] = ((Sigma1(this.W[i - 2]) + this.W[i - 7]) + Sigma0(this.W[i - 15])) + this.W[i - 16];
        }
        long j = this.H1;
        long j2 = this.H2;
        long j3 = this.H3;
        long j4 = this.H4;
        long j5 = this.H5;
        long j6 = this.H6;
        long j7 = this.H7;
        int i2 = 0;
        long j8 = j;
        i = 0;
        long j9 = this.H8;
        long j10 = j8;
        while (i2 < 10) {
            long Sum1 = Sum1(j5);
            long Ch = Ch(j5, j6, j7);
            int i3 = i + 1;
            j = (this.W[i] + ((Sum1 + Ch) + K[i])) + j9;
            j4 += j;
            j9 = (Maj(j10, j2, j3) + Sum0(j10)) + j;
            j = Sum1(j4);
            Sum1 = Ch(j4, j5, j6);
            int i4 = i3 + 1;
            j = (((j + Sum1) + K[i3]) + this.W[i3]) + j7;
            Sum1 = j3 + j;
            j7 = (Maj(j9, j10, j2) + Sum0(j9)) + j;
            j = Sum1(Sum1);
            j3 = Ch(Sum1, j4, j5);
            i3 = i4 + 1;
            j = (((j + j3) + K[i4]) + this.W[i4]) + j6;
            j3 = j2 + j;
            j6 = (Maj(j7, j9, j10) + Sum0(j7)) + j;
            j = Sum1(j3);
            j2 = Ch(j3, Sum1, j4);
            i4 = i3 + 1;
            j = (((j + j2) + K[i3]) + this.W[i3]) + j5;
            j2 = j10 + j;
            j5 = (Maj(j6, j7, j9) + Sum0(j6)) + j;
            j = Sum1(j2);
            j10 = Ch(j2, j3, Sum1);
            i3 = i4 + 1;
            j4 += ((j + j10) + K[i4]) + this.W[i4];
            j10 = j9 + j4;
            j4 += Maj(j5, j6, j7) + Sum0(j5);
            j = Sum1(j10);
            j9 = Ch(j10, j2, j3);
            int i5 = i3 + 1;
            j = (((j + j9) + K[i3]) + this.W[i3]) + Sum1;
            j9 = j7 + j;
            Sum1 = j + (Sum0(j4) + Maj(j4, j5, j6));
            j = Sum1(j9);
            j7 = Ch(j9, j10, j2);
            i4 = i5 + 1;
            j = (((j + j7) + K[i5]) + this.W[i5]) + j3;
            j7 = j6 + j;
            j3 = j + (Sum0(Sum1) + Maj(Sum1, j4, j5));
            j = (((Sum1(j7) + Ch(j7, j9, j10)) + K[i4]) + this.W[i4]) + j2;
            j5 += j;
            j += Maj(j3, Sum1, j4) + Sum0(j3);
            j2 = j3;
            i2++;
            j3 = Sum1;
            j8 = j7;
            j7 = j9;
            j9 = j10;
            j10 = j;
            i = i4 + 1;
            j6 = j8;
        }
        this.H1 += j10;
        this.H2 += j2;
        this.H3 += j3;
        this.H4 += j4;
        this.H5 += j5;
        this.H6 += j6;
        this.H7 += j7;
        this.H8 += j9;
        this.wOff = 0;
        for (i = 0; i < 16; i++) {
            this.W[i] = 0;
        }
    }

    protected void processLength(long j, long j2) {
        if (this.wOff > 14) {
            processBlock();
        }
        this.W[14] = j2;
        this.W[15] = j;
    }

    protected void processWord(byte[] bArr, int i) {
        this.W[this.wOff] = Pack.bigEndianToLong(bArr, i);
        int i2 = this.wOff + 1;
        this.wOff = i2;
        if (i2 == 16) {
            processBlock();
        }
    }

    public void reset() {
        int i = 0;
        this.byteCount1 = 0;
        this.byteCount2 = 0;
        this.xBufOff = 0;
        for (int i2 = 0; i2 < this.xBuf.length; i2++) {
            this.xBuf[i2] = (byte) 0;
        }
        this.wOff = 0;
        while (i != this.W.length) {
            this.W[i] = 0;
            i++;
        }
    }

    public void update(byte b) {
        byte[] bArr = this.xBuf;
        int i = this.xBufOff;
        this.xBufOff = i + 1;
        bArr[i] = b;
        if (this.xBufOff == this.xBuf.length) {
            processWord(this.xBuf, 0);
            this.xBufOff = 0;
        }
        this.byteCount1++;
    }

    public void update(byte[] bArr, int i, int i2) {
        while (this.xBufOff != 0 && i2 > 0) {
            update(bArr[i]);
            i++;
            i2--;
        }
        while (i2 > this.xBuf.length) {
            processWord(bArr, i);
            i += this.xBuf.length;
            i2 -= this.xBuf.length;
            this.byteCount1 += (long) this.xBuf.length;
        }
        while (i2 > 0) {
            update(bArr[i]);
            i++;
            i2--;
        }
    }
}
