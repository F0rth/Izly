package com.google.zxing.common.reedsolomon;

import java.util.ArrayList;
import java.util.List;

public final class ReedSolomonEncoder {
    private final List<GenericGFPoly> cachedGenerators = new ArrayList();
    private final GenericGF field;

    public ReedSolomonEncoder(GenericGF genericGF) {
        this.field = genericGF;
        this.cachedGenerators.add(new GenericGFPoly(genericGF, new int[]{1}));
    }

    private GenericGFPoly buildGenerator(int i) {
        if (i >= this.cachedGenerators.size()) {
            GenericGFPoly genericGFPoly = (GenericGFPoly) this.cachedGenerators.get(this.cachedGenerators.size() - 1);
            for (int size = this.cachedGenerators.size(); size <= i; size++) {
                genericGFPoly = genericGFPoly.multiply(new GenericGFPoly(this.field, new int[]{1, this.field.exp((size - 1) + this.field.getGeneratorBase())}));
                this.cachedGenerators.add(genericGFPoly);
            }
        }
        return (GenericGFPoly) this.cachedGenerators.get(i);
    }

    public final void encode(int[] iArr, int i) {
        if (i == 0) {
            throw new IllegalArgumentException("No error correction bytes");
        }
        int length = iArr.length - i;
        if (length <= 0) {
            throw new IllegalArgumentException("No data bytes provided");
        }
        GenericGFPoly buildGenerator = buildGenerator(i);
        Object obj = new int[length];
        System.arraycopy(iArr, 0, obj, 0, length);
        obj = new GenericGFPoly(this.field, obj).multiplyByMonomial(i, 1).divide(buildGenerator)[1].getCoefficients();
        int length2 = i - obj.length;
        for (int i2 = 0; i2 < length2; i2++) {
            iArr[length + i2] = 0;
        }
        System.arraycopy(obj, 0, iArr, length + length2, obj.length);
    }
}
