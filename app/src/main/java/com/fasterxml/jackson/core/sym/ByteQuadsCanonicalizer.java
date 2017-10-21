package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.JsonFactory.Feature;
import com.fasterxml.jackson.core.util.InternCache;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public final class ByteQuadsCanonicalizer {
    private static final int DEFAULT_T_SIZE = 64;
    static final int MAX_ENTRIES_FOR_REUSE = 6000;
    private static final int MAX_T_SIZE = 65536;
    static final int MIN_HASH_SIZE = 16;
    private static final int MULT = 33;
    private static final int MULT2 = 65599;
    private static final int MULT3 = 31;
    protected int _count;
    protected final boolean _failOnDoS;
    protected int[] _hashArea;
    private boolean _hashShared;
    protected int _hashSize;
    protected boolean _intern;
    protected int _longNameOffset;
    protected String[] _names;
    private transient boolean _needRehash;
    protected final ByteQuadsCanonicalizer _parent;
    protected int _secondaryStart;
    private final int _seed;
    protected int _spilloverEnd;
    protected final AtomicReference<TableInfo> _tableInfo;
    protected int _tertiaryShift;
    protected int _tertiaryStart;

    static final class TableInfo {
        public final int count;
        public final int longNameOffset;
        public final int[] mainHash;
        public final String[] names;
        public final int size;
        public final int spilloverEnd;
        public final int tertiaryShift;

        public TableInfo(int i, int i2, int i3, int[] iArr, String[] strArr, int i4, int i5) {
            this.size = i;
            this.count = i2;
            this.tertiaryShift = i3;
            this.mainHash = iArr;
            this.names = strArr;
            this.spilloverEnd = i4;
            this.longNameOffset = i5;
        }

        public TableInfo(ByteQuadsCanonicalizer byteQuadsCanonicalizer) {
            this.size = byteQuadsCanonicalizer._hashSize;
            this.count = byteQuadsCanonicalizer._count;
            this.tertiaryShift = byteQuadsCanonicalizer._tertiaryShift;
            this.mainHash = byteQuadsCanonicalizer._hashArea;
            this.names = byteQuadsCanonicalizer._names;
            this.spilloverEnd = byteQuadsCanonicalizer._spilloverEnd;
            this.longNameOffset = byteQuadsCanonicalizer._longNameOffset;
        }

        public static TableInfo createInitial(int i) {
            int i2 = i << 3;
            return new TableInfo(i, 0, ByteQuadsCanonicalizer._calcTertiaryShift(i), new int[i2], new String[(i << 1)], i2 - i, i2);
        }
    }

    private ByteQuadsCanonicalizer(int i, boolean z, int i2, boolean z2) {
        int i3 = 16;
        this._parent = null;
        this._seed = i2;
        this._intern = z;
        this._failOnDoS = z2;
        if (i < 16) {
            i = 16;
        } else if (((i - 1) & i) != 0) {
            while (i3 < i) {
                i3 += i3;
            }
            i = i3;
        }
        this._tableInfo = new AtomicReference(TableInfo.createInitial(i));
    }

    private ByteQuadsCanonicalizer(ByteQuadsCanonicalizer byteQuadsCanonicalizer, boolean z, int i, boolean z2, TableInfo tableInfo) {
        this._parent = byteQuadsCanonicalizer;
        this._seed = i;
        this._intern = z;
        this._failOnDoS = z2;
        this._tableInfo = null;
        this._count = tableInfo.count;
        this._hashSize = tableInfo.size;
        this._secondaryStart = this._hashSize << 2;
        this._tertiaryStart = this._secondaryStart + (this._secondaryStart >> 1);
        this._tertiaryShift = tableInfo.tertiaryShift;
        this._hashArea = tableInfo.mainHash;
        this._names = tableInfo.names;
        this._spilloverEnd = tableInfo.spilloverEnd;
        this._longNameOffset = tableInfo.longNameOffset;
        this._needRehash = false;
        this._hashShared = true;
    }

    private int _appendLongName(int[] iArr, int i) {
        int i2 = this._longNameOffset;
        if (i2 + i > this._hashArea.length) {
            int length = this._hashArea.length;
            int min = Math.min(4096, this._hashSize);
            int length2 = this._hashArea.length;
            this._hashArea = Arrays.copyOf(this._hashArea, Math.max((i2 + i) - length, min) + length2);
        }
        System.arraycopy(iArr, 0, this._hashArea, i2, i);
        this._longNameOffset += i;
        return i2;
    }

    private final int _calcOffset(int i) {
        return ((this._hashSize - 1) & i) << 2;
    }

    static int _calcTertiaryShift(int i) {
        int i2 = i >> 2;
        return i2 < 64 ? 4 : i2 <= 256 ? 5 : i2 <= 1024 ? 6 : 7;
    }

    private int _findOffsetForAdd(int i) {
        int _calcOffset = _calcOffset(i);
        int[] iArr = this._hashArea;
        if (iArr[_calcOffset + 3] == 0) {
            return _calcOffset;
        }
        int i2 = this._secondaryStart + ((_calcOffset >> 3) << 2);
        if (iArr[i2 + 3] == 0) {
            return i2;
        }
        i2 = this._tertiaryStart + ((_calcOffset >> (this._tertiaryShift + 2)) << this._tertiaryShift);
        int i3 = this._tertiaryShift;
        for (_calcOffset = i2; _calcOffset < (1 << i3) + i2; _calcOffset += 4) {
            if (iArr[_calcOffset + 3] == 0) {
                return _calcOffset;
            }
        }
        _calcOffset = this._spilloverEnd;
        this._spilloverEnd += 4;
        if (this._spilloverEnd < (this._hashSize << 3)) {
            return _calcOffset;
        }
        if (this._failOnDoS) {
            _reportTooManyCollisions();
        }
        this._needRehash = true;
        return _calcOffset;
    }

    private String _findSecondary(int i, int i2) {
        int i3;
        int i4 = ((i >> (this._tertiaryShift + 2)) << this._tertiaryShift) + this._tertiaryStart;
        int[] iArr = this._hashArea;
        int i5 = this._tertiaryShift;
        for (i3 = i4; i3 < (1 << i5) + i4; i3 += 4) {
            int i6 = iArr[i3 + 3];
            if (i2 == iArr[i3] && 1 == i6) {
                return this._names[i3 >> 2];
            }
            if (i6 == 0) {
                return null;
            }
        }
        i3 = _spilloverStart();
        while (i3 < this._spilloverEnd) {
            if (i2 == iArr[i3] && 1 == iArr[i3 + 3]) {
                return this._names[i3 >> 2];
            }
            i3 += 4;
        }
        return null;
    }

    private String _findSecondary(int i, int i2, int i3) {
        int i4 = ((i >> (this._tertiaryShift + 2)) << this._tertiaryShift) + this._tertiaryStart;
        int[] iArr = this._hashArea;
        int i5 = this._tertiaryShift;
        int i6 = i4;
        while (i6 < (1 << i5) + i4) {
            int i7 = iArr[i6 + 3];
            if (i2 == iArr[i6] && i3 == iArr[i6 + 1] && 2 == i7) {
                return this._names[i6 >> 2];
            }
            if (i7 == 0) {
                return null;
            }
            i6 += 4;
        }
        i6 = _spilloverStart();
        while (i6 < this._spilloverEnd) {
            if (i2 == iArr[i6] && i3 == iArr[i6 + 1] && 2 == iArr[i6 + 3]) {
                return this._names[i6 >> 2];
            }
            i6 += 4;
        }
        return null;
    }

    private String _findSecondary(int i, int i2, int i3, int i4) {
        int i5 = ((i >> (this._tertiaryShift + 2)) << this._tertiaryShift) + this._tertiaryStart;
        int[] iArr = this._hashArea;
        int i6 = this._tertiaryShift;
        int i7 = i5;
        while (i7 < (1 << i6) + i5) {
            int i8 = iArr[i7 + 3];
            if (i2 == iArr[i7] && i3 == iArr[i7 + 1] && i4 == iArr[i7 + 2] && 3 == i8) {
                return this._names[i7 >> 2];
            }
            if (i8 == 0) {
                return null;
            }
            i7 += 4;
        }
        i7 = _spilloverStart();
        while (i7 < this._spilloverEnd) {
            if (i2 == iArr[i7] && i3 == iArr[i7 + 1] && i4 == iArr[i7 + 2] && 3 == iArr[i7 + 3]) {
                return this._names[i7 >> 2];
            }
            i7 += 4;
        }
        return null;
    }

    private String _findSecondary(int i, int i2, int[] iArr, int i3) {
        int i4 = ((i >> (this._tertiaryShift + 2)) << this._tertiaryShift) + this._tertiaryStart;
        int[] iArr2 = this._hashArea;
        int i5 = this._tertiaryShift;
        int i6 = i4;
        while (i6 < (1 << i5) + i4) {
            int i7 = iArr2[i6 + 3];
            if (i2 == iArr2[i6] && i3 == i7 && _verifyLongName(iArr, i3, iArr2[i6 + 1])) {
                return this._names[i6 >> 2];
            }
            if (i7 == 0) {
                return null;
            }
            i6 += 4;
        }
        i6 = _spilloverStart();
        while (i6 < this._spilloverEnd) {
            if (i2 == iArr2[i6] && i3 == iArr2[i6 + 3] && _verifyLongName(iArr, i3, iArr2[i6 + 1])) {
                return this._names[i6 >> 2];
            }
            i6 += 4;
        }
        return null;
    }

    private final int _spilloverStart() {
        int i = this._hashSize;
        return (i << 3) - i;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean _verifyLongName(int[] r8, int r9, int r10) {
        /*
        r7 = this;
        r2 = 1;
        r0 = 0;
        r3 = r7._hashArea;
        switch(r9) {
            case 4: goto L_0x005f;
            case 5: goto L_0x005d;
            case 6: goto L_0x005b;
            case 7: goto L_0x0059;
            case 8: goto L_0x000c;
            default: goto L_0x0007;
        };
    L_0x0007:
        r0 = r7._verifyLongName2(r8, r9, r10);
    L_0x000b:
        return r0;
    L_0x000c:
        r1 = r8[r0];
        r4 = r3[r10];
        if (r1 != r4) goto L_0x000b;
    L_0x0012:
        r10 = r10 + 1;
        r1 = r2;
    L_0x0015:
        r4 = r8[r1];
        r5 = r3[r10];
        if (r4 != r5) goto L_0x000b;
    L_0x001b:
        r10 = r10 + 1;
        r1 = r1 + 1;
    L_0x001f:
        r4 = r8[r1];
        r5 = r3[r10];
        if (r4 != r5) goto L_0x000b;
    L_0x0025:
        r10 = r10 + 1;
        r1 = r1 + 1;
    L_0x0029:
        r4 = r8[r1];
        r5 = r3[r10];
        if (r4 != r5) goto L_0x000b;
    L_0x002f:
        r10 = r10 + 1;
        r1 = r1 + 1;
    L_0x0033:
        r4 = r1 + 1;
        r1 = r8[r1];
        r5 = r10 + 1;
        r6 = r3[r10];
        if (r1 != r6) goto L_0x000b;
    L_0x003d:
        r1 = r4 + 1;
        r4 = r8[r4];
        r6 = r5 + 1;
        r5 = r3[r5];
        if (r4 != r5) goto L_0x000b;
    L_0x0047:
        r4 = r8[r1];
        r5 = r3[r6];
        if (r4 != r5) goto L_0x000b;
    L_0x004d:
        r1 = r1 + 1;
        r1 = r8[r1];
        r4 = r6 + 1;
        r3 = r3[r4];
        if (r1 != r3) goto L_0x000b;
    L_0x0057:
        r0 = r2;
        goto L_0x000b;
    L_0x0059:
        r1 = r0;
        goto L_0x0015;
    L_0x005b:
        r1 = r0;
        goto L_0x001f;
    L_0x005d:
        r1 = r0;
        goto L_0x0029;
    L_0x005f:
        r1 = r0;
        goto L_0x0033;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer._verifyLongName(int[], int, int):boolean");
    }

    private boolean _verifyLongName2(int[] iArr, int i, int i2) {
        int i3 = 0;
        while (true) {
            int i4 = i3 + 1;
            if (iArr[i3] != this._hashArea[i2]) {
                return false;
            }
            if (i4 >= i) {
                return true;
            }
            i2++;
            i3 = i4;
        }
    }

    private void _verifyNeedForRehash() {
        if (this._count <= (this._hashSize >> 1)) {
            return;
        }
        if (((this._spilloverEnd - _spilloverStart()) >> 2) > ((this._count + 1) >> 7) || ((double) this._count) > ((double) this._hashSize) * 0.8d) {
            this._needRehash = true;
        }
    }

    private void _verifySharing() {
        if (this._hashShared) {
            this._hashArea = Arrays.copyOf(this._hashArea, this._hashArea.length);
            this._names = (String[]) Arrays.copyOf(this._names, this._names.length);
            this._hashShared = false;
            _verifyNeedForRehash();
        }
        if (this._needRehash) {
            rehash();
        }
    }

    public static ByteQuadsCanonicalizer createRoot() {
        long currentTimeMillis = System.currentTimeMillis();
        return createRoot((((int) (currentTimeMillis >>> 32)) + ((int) currentTimeMillis)) | 1);
    }

    protected static ByteQuadsCanonicalizer createRoot(int i) {
        return new ByteQuadsCanonicalizer(64, true, i, true);
    }

    private void mergeChild(TableInfo tableInfo) {
        int i = tableInfo.count;
        TableInfo tableInfo2 = (TableInfo) this._tableInfo.get();
        if (i != tableInfo2.count) {
            Object createInitial;
            if (i > MAX_ENTRIES_FOR_REUSE) {
                createInitial = TableInfo.createInitial(64);
            }
            this._tableInfo.compareAndSet(tableInfo2, createInitial);
        }
    }

    private void nukeSymbols(boolean z) {
        this._count = 0;
        this._spilloverEnd = _spilloverStart();
        this._longNameOffset = this._hashSize << 3;
        if (z) {
            Arrays.fill(this._hashArea, 0);
            Arrays.fill(this._names, null);
        }
    }

    private void rehash() {
        this._needRehash = false;
        this._hashShared = false;
        Object obj = this._hashArea;
        String[] strArr = this._names;
        int i = this._hashSize;
        int i2 = this._count;
        int i3 = i + i;
        int i4 = this._spilloverEnd;
        if (i3 > 65536) {
            nukeSymbols(true);
            return;
        }
        this._hashArea = new int[((i << 3) + obj.length)];
        this._hashSize = i3;
        this._secondaryStart = i3 << 2;
        this._tertiaryStart = this._secondaryStart + (this._secondaryStart >> 1);
        this._tertiaryShift = _calcTertiaryShift(i3);
        this._names = new String[(strArr.length << 1)];
        nukeSymbols(false);
        int[] iArr = new int[16];
        i3 = 0;
        for (int i5 = 0; i5 < i4; i5 += 4) {
            int i6 = obj[i5 + 3];
            if (i6 != 0) {
                i3++;
                String str = strArr[i5 >> 2];
                switch (i6) {
                    case 1:
                        iArr[0] = obj[i5];
                        addName(str, iArr, 1);
                        break;
                    case 2:
                        iArr[0] = obj[i5];
                        iArr[1] = obj[i5 + 1];
                        addName(str, iArr, 2);
                        break;
                    case 3:
                        iArr[0] = obj[i5];
                        iArr[1] = obj[i5 + 1];
                        iArr[2] = obj[i5 + 2];
                        addName(str, iArr, 3);
                        break;
                    default:
                        if (i6 > iArr.length) {
                            iArr = new int[i6];
                        }
                        System.arraycopy(obj, obj[i5 + 1], iArr, 0, i6);
                        addName(str, iArr, i6);
                        break;
                }
            }
        }
        if (i3 != i2) {
            throw new IllegalStateException("Failed rehash(): old count=" + i2 + ", copyCount=" + i3);
        }
    }

    protected final void _reportTooManyCollisions() {
        if (this._hashSize > 1024) {
            throw new IllegalStateException("Spill-over slots in symbol table with " + this._count + " entries, hash area of " + this._hashSize + " slots is now full (all " + (this._hashSize >> 3) + " slots -- suspect a DoS attack based on hash collisions. You can disable the check via `JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW`");
        }
    }

    public final String addName(String str, int i) {
        _verifySharing();
        if (this._intern) {
            str = InternCache.instance.intern(str);
        }
        int _findOffsetForAdd = _findOffsetForAdd(calcHash(i));
        this._hashArea[_findOffsetForAdd] = i;
        this._hashArea[_findOffsetForAdd + 3] = 1;
        this._names[_findOffsetForAdd >> 2] = str;
        this._count++;
        _verifyNeedForRehash();
        return str;
    }

    public final String addName(String str, int i, int i2) {
        _verifySharing();
        if (this._intern) {
            str = InternCache.instance.intern(str);
        }
        int _findOffsetForAdd = _findOffsetForAdd(i2 == 0 ? calcHash(i) : calcHash(i, i2));
        this._hashArea[_findOffsetForAdd] = i;
        this._hashArea[_findOffsetForAdd + 1] = i2;
        this._hashArea[_findOffsetForAdd + 3] = 2;
        this._names[_findOffsetForAdd >> 2] = str;
        this._count++;
        _verifyNeedForRehash();
        return str;
    }

    public final String addName(String str, int i, int i2, int i3) {
        _verifySharing();
        if (this._intern) {
            str = InternCache.instance.intern(str);
        }
        int _findOffsetForAdd = _findOffsetForAdd(calcHash(i, i2, i3));
        this._hashArea[_findOffsetForAdd] = i;
        this._hashArea[_findOffsetForAdd + 1] = i2;
        this._hashArea[_findOffsetForAdd + 2] = i3;
        this._hashArea[_findOffsetForAdd + 3] = 3;
        this._names[_findOffsetForAdd >> 2] = str;
        this._count++;
        _verifyNeedForRehash();
        return str;
    }

    public final String addName(String str, int[] iArr, int i) {
        int _findOffsetForAdd;
        _verifySharing();
        if (this._intern) {
            str = InternCache.instance.intern(str);
        }
        switch (i) {
            case 1:
                _findOffsetForAdd = _findOffsetForAdd(calcHash(iArr[0]));
                this._hashArea[_findOffsetForAdd] = iArr[0];
                this._hashArea[_findOffsetForAdd + 3] = 1;
                break;
            case 2:
                _findOffsetForAdd = _findOffsetForAdd(calcHash(iArr[0], iArr[1]));
                this._hashArea[_findOffsetForAdd] = iArr[0];
                this._hashArea[_findOffsetForAdd + 1] = iArr[1];
                this._hashArea[_findOffsetForAdd + 3] = 2;
                break;
            case 3:
                _findOffsetForAdd = _findOffsetForAdd(calcHash(iArr[0], iArr[1], iArr[2]));
                this._hashArea[_findOffsetForAdd] = iArr[0];
                this._hashArea[_findOffsetForAdd + 1] = iArr[1];
                this._hashArea[_findOffsetForAdd + 2] = iArr[2];
                this._hashArea[_findOffsetForAdd + 3] = 3;
                break;
            default:
                int calcHash = calcHash(iArr, i);
                _findOffsetForAdd = _findOffsetForAdd(calcHash);
                this._hashArea[_findOffsetForAdd] = calcHash;
                this._hashArea[_findOffsetForAdd + 1] = _appendLongName(iArr, i);
                this._hashArea[_findOffsetForAdd + 3] = i;
                break;
        }
        this._names[_findOffsetForAdd >> 2] = str;
        this._count++;
        _verifyNeedForRehash();
        return str;
    }

    public final int bucketCount() {
        return this._hashSize;
    }

    public final int calcHash(int i) {
        int i2 = this._seed ^ i;
        i2 += i2 >>> 16;
        i2 ^= i2 << 3;
        return i2 + (i2 >>> 12);
    }

    public final int calcHash(int i, int i2) {
        int i3 = (i >>> 15) + i;
        i3 = ((i3 ^ (i3 >>> 9)) + (i2 * 33)) ^ this._seed;
        i3 += i3 >>> 16;
        i3 ^= i3 >>> 4;
        return i3 + (i3 << 3);
    }

    public final int calcHash(int i, int i2, int i3) {
        int i4 = this._seed ^ i;
        i4 = (((i4 + (i4 >>> 9)) * 31) + i2) * 33;
        i4 = (i4 + (i4 >>> 15)) ^ i3;
        i4 += i4 >>> 4;
        i4 += i4 >>> 15;
        return i4 ^ (i4 << 9);
    }

    public final int calcHash(int[] iArr, int i) {
        if (i < 4) {
            throw new IllegalArgumentException();
        }
        int i2 = iArr[0] ^ this._seed;
        i2 = (i2 + (i2 >>> 9)) + iArr[1];
        i2 = ((i2 + (i2 >>> 15)) * 33) ^ iArr[2];
        i2 += i2 >>> 4;
        for (int i3 = 3; i3 < i; i3++) {
            int i4 = iArr[i3];
            i2 += i4 ^ (i4 >> 21);
        }
        i2 *= MULT2;
        i2 += i2 >>> 19;
        return i2 ^ (i2 << 5);
    }

    public final String findName(int i) {
        int _calcOffset = _calcOffset(calcHash(i));
        int[] iArr = this._hashArea;
        int i2 = iArr[_calcOffset + 3];
        if (i2 == 1) {
            if (iArr[_calcOffset] == i) {
                return this._names[_calcOffset >> 2];
            }
        } else if (i2 == 0) {
            return null;
        }
        i2 = this._secondaryStart + ((_calcOffset >> 3) << 2);
        int i3 = iArr[i2 + 3];
        if (i3 == 1) {
            if (iArr[i2] == i) {
                return this._names[i2 >> 2];
            }
        } else if (i3 == 0) {
            return null;
        }
        return _findSecondary(_calcOffset, i);
    }

    public final String findName(int i, int i2) {
        int _calcOffset = _calcOffset(calcHash(i, i2));
        int[] iArr = this._hashArea;
        int i3 = iArr[_calcOffset + 3];
        if (i3 == 2) {
            if (i == iArr[_calcOffset] && i2 == iArr[_calcOffset + 1]) {
                return this._names[_calcOffset >> 2];
            }
        } else if (i3 == 0) {
            return null;
        }
        i3 = this._secondaryStart + ((_calcOffset >> 3) << 2);
        int i4 = iArr[i3 + 3];
        if (i4 == 2) {
            if (i == iArr[i3] && i2 == iArr[i3 + 1]) {
                return this._names[i3 >> 2];
            }
        } else if (i4 == 0) {
            return null;
        }
        return _findSecondary(_calcOffset, i, i2);
    }

    public final String findName(int i, int i2, int i3) {
        int _calcOffset = _calcOffset(calcHash(i, i2, i3));
        int[] iArr = this._hashArea;
        int i4 = iArr[_calcOffset + 3];
        if (i4 == 3) {
            if (i == iArr[_calcOffset] && iArr[_calcOffset + 1] == i2 && iArr[_calcOffset + 2] == i3) {
                return this._names[_calcOffset >> 2];
            }
        } else if (i4 == 0) {
            return null;
        }
        i4 = this._secondaryStart + ((_calcOffset >> 3) << 2);
        int i5 = iArr[i4 + 3];
        if (i5 == 3) {
            if (i == iArr[i4] && iArr[i4 + 1] == i2 && iArr[i4 + 2] == i3) {
                return this._names[i4 >> 2];
            }
        } else if (i5 == 0) {
            return null;
        }
        return _findSecondary(_calcOffset, i, i2, i3);
    }

    public final String findName(int[] iArr, int i) {
        if (i < 4) {
            return i == 3 ? findName(iArr[0], iArr[1], iArr[2]) : i == 2 ? findName(iArr[0], iArr[1]) : findName(iArr[0]);
        } else {
            int calcHash = calcHash(iArr, i);
            int _calcOffset = _calcOffset(calcHash);
            int[] iArr2 = this._hashArea;
            int i2 = iArr2[_calcOffset + 3];
            if (calcHash == iArr2[_calcOffset] && i2 == i && _verifyLongName(iArr, i, iArr2[_calcOffset + 1])) {
                return this._names[_calcOffset >> 2];
            }
            if (i2 == 0) {
                return null;
            }
            int i3 = this._secondaryStart + ((_calcOffset >> 3) << 2);
            return (calcHash == iArr2[i3] && iArr2[i3 + 3] == i && _verifyLongName(iArr, i, iArr2[i3 + 1])) ? this._names[i3 >> 2] : i2 != 0 ? _findSecondary(_calcOffset, calcHash, iArr, i) : null;
        }
    }

    public final int hashSeed() {
        return this._seed;
    }

    public final ByteQuadsCanonicalizer makeChild(int i) {
        return new ByteQuadsCanonicalizer(this, Feature.INTERN_FIELD_NAMES.enabledIn(i), this._seed, Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(i), (TableInfo) this._tableInfo.get());
    }

    public final boolean maybeDirty() {
        return !this._hashShared;
    }

    public final int primaryCount() {
        int i = this._secondaryStart;
        int i2 = 0;
        for (int i3 = 3; i3 < i; i3 += 4) {
            if (this._hashArea[i3] != 0) {
                i2++;
            }
        }
        return i2;
    }

    public final void release() {
        if (this._parent != null && maybeDirty()) {
            this._parent.mergeChild(new TableInfo(this));
            this._hashShared = true;
        }
    }

    public final int secondaryCount() {
        int i = this._secondaryStart;
        int i2 = this._tertiaryStart;
        int i3 = 0;
        for (i += 3; i < i2; i += 4) {
            if (this._hashArea[i] != 0) {
                i3++;
            }
        }
        return i3;
    }

    public final int size() {
        return this._tableInfo != null ? ((TableInfo) this._tableInfo.get()).count : this._count;
    }

    public final int spilloverCount() {
        return (this._spilloverEnd - _spilloverStart()) >> 2;
    }

    public final int tertiaryCount() {
        int i = this._tertiaryStart + 3;
        int i2 = this._hashSize;
        int i3 = 0;
        for (int i4 = i; i4 < i2 + i; i4 += 4) {
            if (this._hashArea[i4] != 0) {
                i3++;
            }
        }
        return i3;
    }

    public final String toString() {
        int primaryCount = primaryCount();
        int secondaryCount = secondaryCount();
        int tertiaryCount = tertiaryCount();
        int spilloverCount = spilloverCount();
        int totalCount = totalCount();
        return String.format("[%s: size=%d, hashSize=%d, %d/%d/%d/%d pri/sec/ter/spill (=%s), total:%d]", new Object[]{getClass().getName(), Integer.valueOf(this._count), Integer.valueOf(this._hashSize), Integer.valueOf(primaryCount), Integer.valueOf(secondaryCount), Integer.valueOf(tertiaryCount), Integer.valueOf(spilloverCount), Integer.valueOf(totalCount), Integer.valueOf(((primaryCount + secondaryCount) + tertiaryCount) + spilloverCount), Integer.valueOf(totalCount)});
    }

    public final int totalCount() {
        int i = this._hashSize;
        int i2 = 0;
        for (int i3 = 3; i3 < (i << 3); i3 += 4) {
            if (this._hashArea[i3] != 0) {
                i2++;
            }
        }
        return i2;
    }
}
