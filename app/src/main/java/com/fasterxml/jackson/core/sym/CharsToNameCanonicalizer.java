package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.JsonFactory.Feature;
import com.fasterxml.jackson.core.util.InternCache;
import java.util.Arrays;
import java.util.BitSet;

public final class CharsToNameCanonicalizer {
    protected static final int DEFAULT_T_SIZE = 64;
    public static final int HASH_MULT = 33;
    static final int MAX_COLL_CHAIN_LENGTH = 100;
    static final int MAX_ENTRIES_FOR_REUSE = 12000;
    protected static final int MAX_T_SIZE = 65536;
    static final CharsToNameCanonicalizer sBootstrapSymbolTable = new CharsToNameCanonicalizer();
    protected Bucket[] _buckets;
    protected boolean _canonicalize;
    protected boolean _dirty;
    protected final int _flags;
    private final int _hashSeed;
    protected int _indexMask;
    protected int _longestCollisionList;
    protected BitSet _overflows;
    protected CharsToNameCanonicalizer _parent;
    protected int _size;
    protected int _sizeThreshold;
    protected String[] _symbols;

    static final class Bucket {
        public final int length;
        public final Bucket next;
        public final String symbol;

        public Bucket(String str, Bucket bucket) {
            this.symbol = str;
            this.next = bucket;
            this.length = bucket == null ? 1 : bucket.length + 1;
        }

        public final String has(char[] cArr, int i, int i2) {
            if (this.symbol.length() != i2) {
                return null;
            }
            int i3 = 0;
            while (this.symbol.charAt(i3) == cArr[i + i3]) {
                i3++;
                if (i3 >= i2) {
                    return this.symbol;
                }
            }
            return null;
        }
    }

    private CharsToNameCanonicalizer() {
        this._canonicalize = true;
        this._flags = -1;
        this._dirty = true;
        this._hashSeed = 0;
        this._longestCollisionList = 0;
        initTables(64);
    }

    private CharsToNameCanonicalizer(CharsToNameCanonicalizer charsToNameCanonicalizer, int i, String[] strArr, Bucket[] bucketArr, int i2, int i3, int i4) {
        this._parent = charsToNameCanonicalizer;
        this._flags = i;
        this._canonicalize = Feature.CANONICALIZE_FIELD_NAMES.enabledIn(i);
        this._symbols = strArr;
        this._buckets = bucketArr;
        this._size = i2;
        this._hashSeed = i3;
        int length = strArr.length;
        this._sizeThreshold = _thresholdSize(length);
        this._indexMask = length - 1;
        this._longestCollisionList = i4;
        this._dirty = false;
    }

    private String _addSymbol(char[] cArr, int i, int i2, int i3, int i4) {
        if (!this._dirty) {
            copyArrays();
            this._dirty = true;
        } else if (this._size >= this._sizeThreshold) {
            rehash();
            i4 = _hashToIndex(calcHash(cArr, i, i2));
        }
        String str = new String(cArr, i, i2);
        if (Feature.INTERN_FIELD_NAMES.enabledIn(this._flags)) {
            str = InternCache.instance.intern(str);
        }
        this._size++;
        if (this._symbols[i4] == null) {
            this._symbols[i4] = str;
        } else {
            int i5 = i4 >> 1;
            Bucket bucket = new Bucket(str, this._buckets[i5]);
            int i6 = bucket.length;
            if (i6 > 100) {
                _handleSpillOverflow(i5, bucket);
            } else {
                this._buckets[i5] = bucket;
                this._longestCollisionList = Math.max(i6, this._longestCollisionList);
            }
        }
        return str;
    }

    private String _findSymbol2(char[] cArr, int i, int i2, Bucket bucket) {
        while (bucket != null) {
            String has = bucket.has(cArr, i, i2);
            if (has != null) {
                return has;
            }
            bucket = bucket.next;
        }
        return null;
    }

    private void _handleSpillOverflow(int i, Bucket bucket) {
        if (this._overflows == null) {
            this._overflows = new BitSet();
        } else if (this._overflows.get(i)) {
            if (Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(this._flags)) {
                reportTooManyCollisions(100);
            }
            this._canonicalize = false;
            this._symbols[i + i] = bucket.symbol;
            this._buckets[i] = null;
            this._size -= bucket.length;
            this._longestCollisionList = -1;
        }
        this._overflows.set(i);
        this._symbols[i + i] = bucket.symbol;
        this._buckets[i] = null;
        this._size -= bucket.length;
        this._longestCollisionList = -1;
    }

    private static int _thresholdSize(int i) {
        return i - (i >> 2);
    }

    private void copyArrays() {
        String[] strArr = this._symbols;
        this._symbols = (String[]) Arrays.copyOf(strArr, strArr.length);
        Bucket[] bucketArr = this._buckets;
        this._buckets = (Bucket[]) Arrays.copyOf(bucketArr, bucketArr.length);
    }

    public static CharsToNameCanonicalizer createRoot() {
        long currentTimeMillis = System.currentTimeMillis();
        return createRoot((((int) (currentTimeMillis >>> 32)) + ((int) currentTimeMillis)) | 1);
    }

    protected static CharsToNameCanonicalizer createRoot(int i) {
        return sBootstrapSymbolTable.makeOrphan(i);
    }

    private void initTables(int i) {
        this._symbols = new String[i];
        this._buckets = new Bucket[(i >> 1)];
        this._indexMask = i - 1;
        this._size = 0;
        this._longestCollisionList = 0;
        this._sizeThreshold = _thresholdSize(i);
    }

    private CharsToNameCanonicalizer makeOrphan(int i) {
        return new CharsToNameCanonicalizer(null, -1, this._symbols, this._buckets, this._size, i, this._longestCollisionList);
    }

    private void mergeChild(CharsToNameCanonicalizer charsToNameCanonicalizer) {
        if (charsToNameCanonicalizer.size() > MAX_ENTRIES_FOR_REUSE) {
            synchronized (this) {
                initTables(256);
                this._dirty = false;
            }
        } else if (charsToNameCanonicalizer.size() > size()) {
            synchronized (this) {
                this._symbols = charsToNameCanonicalizer._symbols;
                this._buckets = charsToNameCanonicalizer._buckets;
                this._size = charsToNameCanonicalizer._size;
                this._sizeThreshold = charsToNameCanonicalizer._sizeThreshold;
                this._indexMask = charsToNameCanonicalizer._indexMask;
                this._longestCollisionList = charsToNameCanonicalizer._longestCollisionList;
                this._dirty = false;
            }
        }
    }

    private void rehash() {
        int i = 0;
        int length = this._symbols.length;
        int i2 = length + length;
        if (i2 > 65536) {
            this._size = 0;
            this._canonicalize = false;
            this._symbols = new String[64];
            this._buckets = new Bucket[32];
            this._indexMask = 63;
            this._dirty = true;
            return;
        }
        String[] strArr = this._symbols;
        Bucket[] bucketArr = this._buckets;
        this._symbols = new String[i2];
        this._buckets = new Bucket[(i2 >> 1)];
        this._indexMask = i2 - 1;
        this._sizeThreshold = _thresholdSize(i2);
        i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            String str = strArr[i4];
            if (str != null) {
                i2++;
                int _hashToIndex = _hashToIndex(calcHash(str));
                if (this._symbols[_hashToIndex] == null) {
                    this._symbols[_hashToIndex] = str;
                } else {
                    _hashToIndex >>= 1;
                    Bucket bucket = new Bucket(str, this._buckets[_hashToIndex]);
                    this._buckets[_hashToIndex] = bucket;
                    i3 = Math.max(i3, bucket.length);
                }
            }
        }
        while (i < (length >> 1)) {
            Bucket bucket2 = bucketArr[i];
            int i5 = i3;
            i3 = i2;
            i2 = i5;
            while (bucket2 != null) {
                String str2 = bucket2.symbol;
                int _hashToIndex2 = _hashToIndex(calcHash(str2));
                if (this._symbols[_hashToIndex2] == null) {
                    this._symbols[_hashToIndex2] = str2;
                } else {
                    _hashToIndex2 >>= 1;
                    Bucket bucket3 = new Bucket(str2, this._buckets[_hashToIndex2]);
                    this._buckets[_hashToIndex2] = bucket3;
                    i2 = Math.max(i2, bucket3.length);
                }
                bucket2 = bucket2.next;
                i3++;
            }
            i++;
            i5 = i3;
            i3 = i2;
            i2 = i5;
        }
        this._longestCollisionList = i3;
        this._overflows = null;
        if (i2 != this._size) {
            throw new Error("Internal error on SymbolTable.rehash(): had " + this._size + " entries; now have " + i2 + ".");
        }
    }

    public final int _hashToIndex(int i) {
        int i2 = (i >>> 15) + i;
        i2 ^= i2 << 7;
        return (i2 + (i2 >>> 3)) & this._indexMask;
    }

    public final int bucketCount() {
        return this._symbols.length;
    }

    public final int calcHash(String str) {
        int length = str.length();
        int i = this._hashSeed;
        int i2 = 0;
        while (i2 < length) {
            char charAt = str.charAt(i2);
            i2++;
            i = (i * 33) + charAt;
        }
        return i == 0 ? 1 : i;
    }

    public final int calcHash(char[] cArr, int i, int i2) {
        int i3 = this._hashSeed;
        int i4 = i;
        while (i4 < i + i2) {
            int i5 = cArr[i4] + (i3 * 33);
            i4++;
            i3 = i5;
        }
        return i3 == 0 ? 1 : i3;
    }

    public final int collisionCount() {
        int i = 0;
        for (Bucket bucket : this._buckets) {
            if (bucket != null) {
                i += bucket.length;
            }
        }
        return i;
    }

    public final String findSymbol(char[] cArr, int i, int i2, int i3) {
        if (i2 <= 0) {
            return "";
        }
        if (!this._canonicalize) {
            return new String(cArr, i, i2);
        }
        int _hashToIndex = _hashToIndex(i3);
        String str = this._symbols[_hashToIndex];
        if (str != null) {
            if (str.length() == i2) {
                int i4 = 0;
                while (str.charAt(i4) == cArr[i + i4]) {
                    i4++;
                    if (i4 == i2) {
                        return str;
                    }
                }
            }
            Bucket bucket = this._buckets[_hashToIndex >> 1];
            if (bucket != null) {
                String has = bucket.has(cArr, i, i2);
                if (has != null) {
                    return has;
                }
                has = _findSymbol2(cArr, i, i2, bucket.next);
                if (has != null) {
                    return has;
                }
            }
        }
        return _addSymbol(cArr, i, i2, i3, _hashToIndex);
    }

    public final int hashSeed() {
        return this._hashSeed;
    }

    public final CharsToNameCanonicalizer makeChild(int i) {
        String[] strArr;
        Bucket[] bucketArr;
        int i2;
        int i3;
        int i4;
        synchronized (this) {
            strArr = this._symbols;
            bucketArr = this._buckets;
            i2 = this._size;
            i3 = this._hashSeed;
            i4 = this._longestCollisionList;
        }
        return new CharsToNameCanonicalizer(this, i, strArr, bucketArr, i2, i3, i4);
    }

    public final int maxCollisionLength() {
        return this._longestCollisionList;
    }

    public final boolean maybeDirty() {
        return this._dirty;
    }

    public final void release() {
        if (maybeDirty() && this._parent != null && this._canonicalize) {
            this._parent.mergeChild(this);
            this._dirty = false;
        }
    }

    protected final void reportTooManyCollisions(int i) {
        throw new IllegalStateException("Longest collision chain in symbol table (of size " + this._size + ") now exceeds maximum, " + i + " -- suspect a DoS attack based on hash collisions");
    }

    public final int size() {
        return this._size;
    }
}
