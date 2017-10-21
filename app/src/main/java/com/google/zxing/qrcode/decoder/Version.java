package com.google.zxing.qrcode.decoder;

import android.support.v4.media.TransportMediator;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;

public final class Version {
    private static final Version[] VERSIONS = buildVersions();
    private static final int[] VERSION_DECODE_INFO = new int[]{31892, 34236, 39577, 42195, 48118, 51042, 55367, 58893, 63784, 68472, 70749, 76311, 79154, 84390, 87683, 92361, 96236, 102084, 102881, 110507, 110734, 117786, 119615, 126325, 127568, 133589, 136944, 141498, 145311, 150283, 152622, 158308, 161089, 167017};
    private final int[] alignmentPatternCenters;
    private final ECBlocks[] ecBlocks;
    private final int totalCodewords;
    private final int versionNumber;

    public static final class ECB {
        private final int count;
        private final int dataCodewords;

        ECB(int i, int i2) {
            this.count = i;
            this.dataCodewords = i2;
        }

        public final int getCount() {
            return this.count;
        }

        public final int getDataCodewords() {
            return this.dataCodewords;
        }
    }

    public static final class ECBlocks {
        private final ECB[] ecBlocks;
        private final int ecCodewordsPerBlock;

        ECBlocks(int i, ECB... ecbArr) {
            this.ecCodewordsPerBlock = i;
            this.ecBlocks = ecbArr;
        }

        public final ECB[] getECBlocks() {
            return this.ecBlocks;
        }

        public final int getECCodewordsPerBlock() {
            return this.ecCodewordsPerBlock;
        }

        public final int getNumBlocks() {
            int i = 0;
            for (ECB count : this.ecBlocks) {
                i += count.getCount();
            }
            return i;
        }

        public final int getTotalECCodewords() {
            return this.ecCodewordsPerBlock * getNumBlocks();
        }
    }

    private Version(int i, int[] iArr, ECBlocks... eCBlocksArr) {
        int i2 = 0;
        this.versionNumber = i;
        this.alignmentPatternCenters = iArr;
        this.ecBlocks = eCBlocksArr;
        int eCCodewordsPerBlock = eCBlocksArr[0].getECCodewordsPerBlock();
        for (ECB ecb : eCBlocksArr[0].getECBlocks()) {
            i2 += (ecb.getDataCodewords() + eCCodewordsPerBlock) * ecb.getCount();
        }
        this.totalCodewords = i2;
    }

    private static Version[] buildVersions() {
        ECBlocks eCBlocks = new ECBlocks(7, new ECB(1, 19));
        ECBlocks eCBlocks2 = new ECBlocks(10, new ECB(1, 16));
        ECBlocks eCBlocks3 = new ECBlocks(13, new ECB(1, 13));
        ECBlocks eCBlocks4 = new ECBlocks(17, new ECB(1, 9));
        Version version = new Version(1, new int[0], eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(10, new ECB(1, 34));
        eCBlocks2 = new ECBlocks(16, new ECB(1, 28));
        eCBlocks3 = new ECBlocks(22, new ECB(1, 22));
        eCBlocks4 = new ECBlocks(28, new ECB(1, 16));
        Version version2 = new Version(2, new int[]{6, 18}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(15, new ECB(1, 55));
        eCBlocks2 = new ECBlocks(26, new ECB(1, 44));
        eCBlocks3 = new ECBlocks(18, new ECB(2, 17));
        eCBlocks4 = new ECBlocks(22, new ECB(2, 13));
        Version version3 = new Version(3, new int[]{6, 22}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(20, new ECB(1, 80));
        eCBlocks2 = new ECBlocks(18, new ECB(2, 32));
        eCBlocks3 = new ECBlocks(26, new ECB(2, 24));
        eCBlocks4 = new ECBlocks(16, new ECB(4, 9));
        Version version4 = new Version(4, new int[]{6, 26}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(26, new ECB(1, 108));
        eCBlocks2 = new ECBlocks(24, new ECB(2, 43));
        eCBlocks3 = new ECBlocks(18, new ECB(2, 15), new ECB(2, 16));
        eCBlocks4 = new ECBlocks(22, new ECB(2, 11), new ECB(2, 12));
        Version version5 = new Version(5, new int[]{6, 30}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(18, new ECB(2, 68));
        eCBlocks2 = new ECBlocks(16, new ECB(4, 27));
        eCBlocks3 = new ECBlocks(24, new ECB(4, 19));
        eCBlocks4 = new ECBlocks(28, new ECB(4, 15));
        Version version6 = new Version(6, new int[]{6, 34}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(20, new ECB(2, 78));
        eCBlocks2 = new ECBlocks(18, new ECB(4, 31));
        eCBlocks3 = new ECBlocks(18, new ECB(2, 14), new ECB(4, 15));
        eCBlocks4 = new ECBlocks(26, new ECB(4, 13), new ECB(1, 14));
        Version version7 = new Version(7, new int[]{6, 22, 38}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(24, new ECB(2, 97));
        eCBlocks2 = new ECBlocks(22, new ECB(2, 38), new ECB(2, 39));
        eCBlocks3 = new ECBlocks(22, new ECB(4, 18), new ECB(2, 19));
        eCBlocks4 = new ECBlocks(26, new ECB(4, 14), new ECB(2, 15));
        Version version8 = new Version(8, new int[]{6, 24, 42}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(2, 116));
        eCBlocks2 = new ECBlocks(22, new ECB(3, 36), new ECB(2, 37));
        eCBlocks3 = new ECBlocks(20, new ECB(4, 16), new ECB(4, 17));
        eCBlocks4 = new ECBlocks(24, new ECB(4, 12), new ECB(4, 13));
        r16 = new int[3];
        Version version9 = new Version(9, new int[]{6, 26, 46}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(18, new ECB(2, 68), new ECB(2, 69));
        eCBlocks2 = new ECBlocks(26, new ECB(4, 43), new ECB(1, 44));
        eCBlocks3 = new ECBlocks(24, new ECB(6, 19), new ECB(2, 20));
        eCBlocks4 = new ECBlocks(28, new ECB(6, 15), new ECB(2, 16));
        r17 = new int[3];
        Version version10 = new Version(10, new int[]{6, 28, 50}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(20, new ECB(4, 81));
        eCBlocks2 = new ECBlocks(30, new ECB(1, 50), new ECB(4, 51));
        eCBlocks3 = new ECBlocks(28, new ECB(4, 22), new ECB(4, 23));
        eCBlocks4 = new ECBlocks(24, new ECB(3, 12), new ECB(8, 13));
        r18 = new int[3];
        Version version11 = new Version(11, new int[]{6, 30, 54}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(24, new ECB(2, 92), new ECB(2, 93));
        eCBlocks2 = new ECBlocks(22, new ECB(6, 36), new ECB(2, 37));
        eCBlocks3 = new ECBlocks(26, new ECB(4, 20), new ECB(6, 21));
        eCBlocks4 = new ECBlocks(28, new ECB(7, 14), new ECB(4, 15));
        r19 = new int[3];
        Version version12 = new Version(12, new int[]{6, 32, 58}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(26, new ECB(4, 107));
        eCBlocks2 = new ECBlocks(22, new ECB(8, 37), new ECB(1, 38));
        eCBlocks3 = new ECBlocks(24, new ECB(8, 20), new ECB(4, 21));
        eCBlocks4 = new ECBlocks(22, new ECB(12, 11), new ECB(4, 12));
        r20 = new int[3];
        Version version13 = new Version(13, new int[]{6, 34, 62}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(3, 115), new ECB(1, 116));
        eCBlocks2 = new ECBlocks(24, new ECB(4, 40), new ECB(5, 41));
        eCBlocks3 = new ECBlocks(20, new ECB(11, 16), new ECB(5, 17));
        eCBlocks4 = new ECBlocks(24, new ECB(11, 12), new ECB(5, 13));
        r21 = new int[4];
        Version version14 = new Version(14, new int[]{6, 26, 46, 66}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(22, new ECB(5, 87), new ECB(1, 88));
        eCBlocks2 = new ECBlocks(24, new ECB(5, 41), new ECB(5, 42));
        eCBlocks3 = new ECBlocks(30, new ECB(5, 24), new ECB(7, 25));
        eCBlocks4 = new ECBlocks(24, new ECB(11, 12), new ECB(7, 13));
        r22 = new int[4];
        Version version15 = new Version(15, new int[]{6, 26, 48, 70}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(24, new ECB(5, 98), new ECB(1, 99));
        eCBlocks2 = new ECBlocks(28, new ECB(7, 45), new ECB(3, 46));
        eCBlocks3 = new ECBlocks(24, new ECB(15, 19), new ECB(2, 20));
        eCBlocks4 = new ECBlocks(30, new ECB(3, 15), new ECB(13, 16));
        r23 = new int[4];
        Version version16 = new Version(16, new int[]{6, 26, 50, 74}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(28, new ECB(1, 107), new ECB(5, 108));
        eCBlocks2 = new ECBlocks(28, new ECB(10, 46), new ECB(1, 47));
        eCBlocks3 = new ECBlocks(28, new ECB(1, 22), new ECB(15, 23));
        eCBlocks4 = new ECBlocks(28, new ECB(2, 14), new ECB(17, 15));
        r24 = new int[4];
        Version version17 = new Version(17, new int[]{6, 30, 54, 78}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(5, 120), new ECB(1, 121));
        eCBlocks2 = new ECBlocks(26, new ECB(9, 43), new ECB(4, 44));
        eCBlocks3 = new ECBlocks(28, new ECB(17, 22), new ECB(1, 23));
        eCBlocks4 = new ECBlocks(28, new ECB(2, 14), new ECB(19, 15));
        r25 = new int[4];
        Version version18 = new Version(18, new int[]{6, 30, 56, 82}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(28, new ECB(3, 113), new ECB(4, 114));
        eCBlocks2 = new ECBlocks(26, new ECB(3, 44), new ECB(11, 45));
        eCBlocks3 = new ECBlocks(26, new ECB(17, 21), new ECB(4, 22));
        eCBlocks4 = new ECBlocks(26, new ECB(9, 13), new ECB(16, 14));
        r26 = new int[4];
        Version version19 = new Version(19, new int[]{6, 30, 58, 86}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(28, new ECB(3, 107), new ECB(5, 108));
        eCBlocks2 = new ECBlocks(26, new ECB(3, 41), new ECB(13, 42));
        eCBlocks3 = new ECBlocks(30, new ECB(15, 24), new ECB(5, 25));
        eCBlocks4 = new ECBlocks(28, new ECB(15, 15), new ECB(10, 16));
        r27 = new int[4];
        Version version20 = new Version(20, new int[]{6, 34, 62, 90}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(28, new ECB(4, 116), new ECB(4, 117));
        eCBlocks2 = new ECBlocks(26, new ECB(17, 42));
        eCBlocks3 = new ECBlocks(28, new ECB(17, 22), new ECB(6, 23));
        eCBlocks4 = new ECBlocks(30, new ECB(19, 16), new ECB(6, 17));
        r28 = new int[5];
        Version version21 = new Version(21, new int[]{6, 28, 50, 72, 94}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(28, new ECB(2, 111), new ECB(7, 112));
        eCBlocks2 = new ECBlocks(28, new ECB(17, 46));
        eCBlocks3 = new ECBlocks(30, new ECB(7, 24), new ECB(16, 25));
        eCBlocks4 = new ECBlocks(24, new ECB(34, 13));
        r29 = new int[5];
        Version version22 = new Version(22, new int[]{6, 26, 50, 74, 98}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(4, 121), new ECB(5, 122));
        eCBlocks2 = new ECBlocks(28, new ECB(4, 47), new ECB(14, 48));
        eCBlocks3 = new ECBlocks(30, new ECB(11, 24), new ECB(14, 25));
        eCBlocks4 = new ECBlocks(30, new ECB(16, 15), new ECB(14, 16));
        r30 = new int[5];
        Version version23 = new Version(23, new int[]{6, 30, 54, 78, 102}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(6, 117), new ECB(4, 118));
        eCBlocks2 = new ECBlocks(28, new ECB(6, 45), new ECB(14, 46));
        eCBlocks3 = new ECBlocks(30, new ECB(11, 24), new ECB(16, 25));
        eCBlocks4 = new ECBlocks(30, new ECB(30, 16), new ECB(2, 17));
        r31 = new int[5];
        Version version24 = new Version(24, new int[]{6, 28, 54, 80, 106}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(26, new ECB(8, 106), new ECB(4, 107));
        eCBlocks2 = new ECBlocks(28, new ECB(8, 47), new ECB(13, 48));
        eCBlocks3 = new ECBlocks(30, new ECB(7, 24), new ECB(22, 25));
        eCBlocks4 = new ECBlocks(30, new ECB(22, 15), new ECB(13, 16));
        r32 = new int[5];
        Version version25 = new Version(25, new int[]{6, 32, 58, 84, 110}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(28, new ECB(10, 114), new ECB(2, 115));
        eCBlocks2 = new ECBlocks(28, new ECB(19, 46), new ECB(4, 47));
        eCBlocks3 = new ECBlocks(28, new ECB(28, 22), new ECB(6, 23));
        eCBlocks4 = new ECBlocks(30, new ECB(33, 16), new ECB(4, 17));
        r33 = new int[5];
        Version version26 = new Version(26, new int[]{6, 30, 58, 86, 114}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(8, 122), new ECB(4, 123));
        eCBlocks2 = new ECBlocks(28, new ECB(22, 45), new ECB(3, 46));
        eCBlocks3 = new ECBlocks(30, new ECB(8, 23), new ECB(26, 24));
        eCBlocks4 = new ECBlocks(30, new ECB(12, 15), new ECB(28, 16));
        r34 = new int[5];
        Version version27 = new Version(27, new int[]{6, 34, 62, 90, 118}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(3, 117), new ECB(10, 118));
        eCBlocks2 = new ECBlocks(28, new ECB(3, 45), new ECB(23, 46));
        eCBlocks3 = new ECBlocks(30, new ECB(4, 24), new ECB(31, 25));
        eCBlocks4 = new ECBlocks(30, new ECB(11, 15), new ECB(31, 16));
        r35 = new int[6];
        Version version28 = new Version(28, new int[]{6, 26, 50, 74, 98, 122}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(7, 116), new ECB(7, 117));
        eCBlocks2 = new ECBlocks(28, new ECB(21, 45), new ECB(7, 46));
        eCBlocks3 = new ECBlocks(30, new ECB(1, 23), new ECB(37, 24));
        eCBlocks4 = new ECBlocks(30, new ECB(19, 15), new ECB(26, 16));
        r36 = new int[6];
        Version version29 = new Version(29, new int[]{6, 30, 54, 78, 102, TransportMediator.KEYCODE_MEDIA_PLAY}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(5, 115), new ECB(10, 116));
        eCBlocks2 = new ECBlocks(28, new ECB(19, 47), new ECB(10, 48));
        eCBlocks3 = new ECBlocks(30, new ECB(15, 24), new ECB(25, 25));
        eCBlocks4 = new ECBlocks(30, new ECB(23, 15), new ECB(25, 16));
        r37 = new int[6];
        Version version30 = new Version(30, new int[]{6, 26, 52, 78, 104, TransportMediator.KEYCODE_MEDIA_RECORD}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(13, 115), new ECB(3, 116));
        eCBlocks2 = new ECBlocks(28, new ECB(2, 46), new ECB(29, 47));
        eCBlocks3 = new ECBlocks(30, new ECB(42, 24), new ECB(1, 25));
        eCBlocks4 = new ECBlocks(30, new ECB(23, 15), new ECB(28, 16));
        r38 = new int[6];
        Version version31 = new Version(31, new int[]{6, 30, 56, 82, 108, 134}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(17, 115));
        eCBlocks2 = new ECBlocks(28, new ECB(10, 46), new ECB(23, 47));
        eCBlocks3 = new ECBlocks(30, new ECB(10, 24), new ECB(35, 25));
        eCBlocks4 = new ECBlocks(30, new ECB(19, 15), new ECB(35, 16));
        r39 = new int[6];
        Version version32 = new Version(32, new int[]{6, 34, 60, 86, 112, 138}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(17, 115), new ECB(1, 116));
        eCBlocks2 = new ECBlocks(28, new ECB(14, 46), new ECB(21, 47));
        eCBlocks3 = new ECBlocks(30, new ECB(29, 24), new ECB(19, 25));
        eCBlocks4 = new ECBlocks(30, new ECB(11, 15), new ECB(46, 16));
        r40 = new int[6];
        Version version33 = new Version(33, new int[]{6, 30, 58, 86, 114, 142}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(13, 115), new ECB(6, 116));
        eCBlocks2 = new ECBlocks(28, new ECB(14, 46), new ECB(23, 47));
        eCBlocks3 = new ECBlocks(30, new ECB(44, 24), new ECB(7, 25));
        eCBlocks4 = new ECBlocks(30, new ECB(59, 16), new ECB(1, 17));
        r41 = new int[6];
        Version version34 = new Version(34, new int[]{6, 34, 62, 90, 118, 146}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(12, 121), new ECB(7, 122));
        eCBlocks2 = new ECBlocks(28, new ECB(12, 47), new ECB(26, 48));
        eCBlocks3 = new ECBlocks(30, new ECB(39, 24), new ECB(14, 25));
        eCBlocks4 = new ECBlocks(30, new ECB(22, 15), new ECB(41, 16));
        int[] iArr = new int[7];
        Version version35 = new Version(35, new int[]{6, 30, 54, 78, 102, TransportMediator.KEYCODE_MEDIA_PLAY, 150}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(6, 121), new ECB(14, 122));
        eCBlocks2 = new ECBlocks(28, new ECB(6, 47), new ECB(34, 48));
        eCBlocks3 = new ECBlocks(30, new ECB(46, 24), new ECB(10, 25));
        eCBlocks4 = new ECBlocks(30, new ECB(2, 15), new ECB(64, 16));
        int[] iArr2 = new int[7];
        Version version36 = new Version(36, new int[]{6, 24, 50, 76, 102, 128, 154}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(17, 122), new ECB(4, 123));
        eCBlocks2 = new ECBlocks(28, new ECB(29, 46), new ECB(14, 47));
        eCBlocks3 = new ECBlocks(30, new ECB(49, 24), new ECB(10, 25));
        eCBlocks4 = new ECBlocks(30, new ECB(24, 15), new ECB(46, 16));
        int[] iArr3 = new int[7];
        Version version37 = new Version(37, new int[]{6, 28, 54, 80, 106, 132, 158}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(4, 122), new ECB(18, 123));
        eCBlocks2 = new ECBlocks(28, new ECB(13, 46), new ECB(32, 47));
        eCBlocks3 = new ECBlocks(30, new ECB(48, 24), new ECB(14, 25));
        eCBlocks4 = new ECBlocks(30, new ECB(42, 15), new ECB(32, 16));
        int[] iArr4 = new int[7];
        Version version38 = new Version(38, new int[]{6, 32, 58, 84, 110, 136, 162}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(20, 117), new ECB(4, 118));
        eCBlocks2 = new ECBlocks(28, new ECB(40, 47), new ECB(7, 48));
        eCBlocks3 = new ECBlocks(30, new ECB(43, 24), new ECB(22, 25));
        eCBlocks4 = new ECBlocks(30, new ECB(10, 15), new ECB(67, 16));
        int[] iArr5 = new int[7];
        Version version39 = new Version(39, new int[]{6, 26, 54, 82, 110, 138, 166}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        eCBlocks = new ECBlocks(30, new ECB(19, 118), new ECB(6, 119));
        eCBlocks2 = new ECBlocks(28, new ECB(18, 47), new ECB(31, 48));
        eCBlocks3 = new ECBlocks(30, new ECB(34, 24), new ECB(34, 25));
        eCBlocks4 = new ECBlocks(30, new ECB(20, 15), new ECB(61, 16));
        Version[] versionArr = new Version[40];
        versionArr[0] = version;
        versionArr[1] = version2;
        versionArr[2] = version3;
        versionArr[3] = version4;
        versionArr[4] = version5;
        versionArr[5] = version6;
        versionArr[6] = version7;
        versionArr[7] = version8;
        versionArr[8] = version9;
        versionArr[9] = version10;
        versionArr[10] = version11;
        versionArr[11] = version12;
        versionArr[12] = version13;
        versionArr[13] = version14;
        versionArr[14] = version15;
        versionArr[15] = version16;
        versionArr[16] = version17;
        versionArr[17] = version18;
        versionArr[18] = version19;
        versionArr[19] = version20;
        versionArr[20] = version21;
        versionArr[21] = version22;
        versionArr[22] = version23;
        versionArr[23] = version24;
        versionArr[24] = version25;
        versionArr[25] = version26;
        versionArr[26] = version27;
        versionArr[27] = version28;
        versionArr[28] = version29;
        versionArr[29] = version30;
        versionArr[30] = version31;
        versionArr[31] = version32;
        versionArr[32] = version33;
        versionArr[33] = version34;
        versionArr[34] = version35;
        versionArr[35] = version36;
        versionArr[36] = version37;
        versionArr[37] = version38;
        versionArr[38] = version39;
        versionArr[39] = new Version(40, new int[]{6, 30, 58, 86, 114, 142, 170}, eCBlocks, eCBlocks2, eCBlocks3, eCBlocks4);
        return versionArr;
    }

    static Version decodeVersionInformation(int i) {
        int i2 = 0;
        int i3 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        for (int i4 = 0; i4 < VERSION_DECODE_INFO.length; i4++) {
            int i5 = VERSION_DECODE_INFO[i4];
            if (i5 == i) {
                return getVersionForNumber(i4 + 7);
            }
            i5 = FormatInformation.numBitsDiffering(i, i5);
            if (i5 < i3) {
                i2 = i4 + 7;
                i3 = i5;
            }
        }
        return i3 <= 3 ? getVersionForNumber(i2) : null;
    }

    public static Version getProvisionalVersionForDimension(int i) throws FormatException {
        if (i % 4 != 1) {
            throw FormatException.getFormatInstance();
        }
        try {
            return getVersionForNumber((i - 17) >> 2);
        } catch (IllegalArgumentException e) {
            throw FormatException.getFormatInstance();
        }
    }

    public static Version getVersionForNumber(int i) {
        if (i > 0 && i <= 40) {
            return VERSIONS[i - 1];
        }
        throw new IllegalArgumentException();
    }

    final BitMatrix buildFunctionPattern() {
        int dimensionForVersion = getDimensionForVersion();
        BitMatrix bitMatrix = new BitMatrix(dimensionForVersion);
        bitMatrix.setRegion(0, 0, 9, 9);
        bitMatrix.setRegion(dimensionForVersion - 8, 0, 8, 9);
        bitMatrix.setRegion(0, dimensionForVersion - 8, 9, 8);
        int length = this.alignmentPatternCenters.length;
        int i = 0;
        while (i < length) {
            int i2 = this.alignmentPatternCenters[i];
            int i3 = 0;
            while (i3 < length) {
                if (!((i == 0 && (i3 == 0 || i3 == length - 1)) || (i == length - 1 && i3 == 0))) {
                    bitMatrix.setRegion(this.alignmentPatternCenters[i3] - 2, i2 - 2, 5, 5);
                }
                i3++;
            }
            i++;
        }
        bitMatrix.setRegion(6, 9, 1, dimensionForVersion - 17);
        bitMatrix.setRegion(9, 6, dimensionForVersion - 17, 1);
        if (this.versionNumber > 6) {
            bitMatrix.setRegion(dimensionForVersion - 11, 0, 3, 6);
            bitMatrix.setRegion(0, dimensionForVersion - 11, 6, 3);
        }
        return bitMatrix;
    }

    public final int[] getAlignmentPatternCenters() {
        return this.alignmentPatternCenters;
    }

    public final int getDimensionForVersion() {
        return (this.versionNumber * 4) + 17;
    }

    public final ECBlocks getECBlocksForLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        return this.ecBlocks[errorCorrectionLevel.ordinal()];
    }

    public final int getTotalCodewords() {
        return this.totalCodewords;
    }

    public final int getVersionNumber() {
        return this.versionNumber;
    }

    public final String toString() {
        return String.valueOf(this.versionNumber);
    }
}
