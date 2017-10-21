package com.google.zxing.pdf417.decoder.ec;

import com.google.zxing.ChecksumException;

public final class ErrorCorrection {
    private final ModulusGF field = ModulusGF.PDF417_GF;

    private int[] findErrorLocations(ModulusPoly modulusPoly) throws ChecksumException {
        int degree = modulusPoly.getDegree();
        int[] iArr = new int[degree];
        int i = 0;
        for (int i2 = 1; i2 < this.field.getSize() && i < degree; i2++) {
            if (modulusPoly.evaluateAt(i2) == 0) {
                iArr[i] = this.field.inverse(i2);
                i++;
            }
        }
        if (i == degree) {
            return iArr;
        }
        throw ChecksumException.getChecksumInstance();
    }

    private int[] findErrorMagnitudes(ModulusPoly modulusPoly, ModulusPoly modulusPoly2, int[] iArr) {
        int i;
        int degree = modulusPoly2.getDegree();
        int[] iArr2 = new int[degree];
        for (i = 1; i <= degree; i++) {
            iArr2[degree - i] = this.field.multiply(i, modulusPoly2.getCoefficient(i));
        }
        ModulusPoly modulusPoly3 = new ModulusPoly(this.field, iArr2);
        int length = iArr.length;
        int[] iArr3 = new int[length];
        for (i = 0; i < length; i++) {
            int inverse = this.field.inverse(iArr[i]);
            iArr3[i] = this.field.multiply(this.field.subtract(0, modulusPoly.evaluateAt(inverse)), this.field.inverse(modulusPoly3.evaluateAt(inverse)));
        }
        return iArr3;
    }

    private ModulusPoly[] runEuclideanAlgorithm(ModulusPoly modulusPoly, ModulusPoly modulusPoly2, int i) throws ChecksumException {
        if (modulusPoly.getDegree() < modulusPoly2.getDegree()) {
            ModulusPoly modulusPoly3 = modulusPoly2;
            modulusPoly2 = modulusPoly;
            modulusPoly = modulusPoly3;
        }
        ModulusPoly zero = this.field.getZero();
        ModulusPoly one = this.field.getOne();
        ModulusPoly modulusPoly4 = modulusPoly2;
        while (modulusPoly4.getDegree() >= i / 2) {
            if (modulusPoly4.isZero()) {
                throw ChecksumException.getChecksumInstance();
            }
            ModulusPoly zero2 = this.field.getZero();
            int inverse = this.field.inverse(modulusPoly4.getCoefficient(modulusPoly4.getDegree()));
            modulusPoly2 = modulusPoly;
            while (modulusPoly2.getDegree() >= modulusPoly4.getDegree() && !modulusPoly2.isZero()) {
                int degree = modulusPoly2.getDegree() - modulusPoly4.getDegree();
                int multiply = this.field.multiply(modulusPoly2.getCoefficient(modulusPoly2.getDegree()), inverse);
                zero2 = zero2.add(this.field.buildMonomial(degree, multiply));
                modulusPoly2 = modulusPoly2.subtract(modulusPoly4.multiplyByMonomial(degree, multiply));
            }
            modulusPoly = modulusPoly4;
            modulusPoly4 = modulusPoly2;
            modulusPoly3 = one;
            one = zero2.multiply(one).subtract(zero).negative();
            zero = modulusPoly3;
        }
        int coefficient = one.getCoefficient(0);
        if (coefficient == 0) {
            throw ChecksumException.getChecksumInstance();
        }
        coefficient = this.field.inverse(coefficient);
        return new ModulusPoly[]{one.multiply(coefficient), modulusPoly4.multiply(coefficient)};
    }

    public final int decode(int[] iArr, int i, int[] iArr2) throws ChecksumException {
        ModulusPoly modulusPoly = new ModulusPoly(this.field, iArr);
        int[] iArr3 = new int[i];
        int i2 = 0;
        for (int i3 = i; i3 > 0; i3--) {
            int evaluateAt = modulusPoly.evaluateAt(this.field.exp(i3));
            iArr3[i - i3] = evaluateAt;
            if (evaluateAt != 0) {
                i2 = 1;
            }
        }
        if (i2 == 0) {
            return 0;
        }
        ModulusPoly one = this.field.getOne();
        for (int evaluateAt2 : iArr2) {
            evaluateAt2 = this.field.exp((iArr.length - 1) - evaluateAt2);
            one = one.multiply(new ModulusPoly(this.field, new int[]{this.field.subtract(0, evaluateAt2), 1}));
        }
        ModulusPoly[] runEuclideanAlgorithm = runEuclideanAlgorithm(this.field.buildMonomial(i, 1), new ModulusPoly(this.field, iArr3), i);
        ModulusPoly modulusPoly2 = runEuclideanAlgorithm[0];
        one = runEuclideanAlgorithm[1];
        int[] findErrorLocations = findErrorLocations(modulusPoly2);
        int[] findErrorMagnitudes = findErrorMagnitudes(one, modulusPoly2, findErrorLocations);
        for (i2 = 0; i2 < findErrorLocations.length; i2++) {
            int length = (iArr.length - 1) - this.field.log(findErrorLocations[i2]);
            if (length < 0) {
                throw ChecksumException.getChecksumInstance();
            }
            iArr[length] = this.field.subtract(iArr[length], findErrorMagnitudes[i2]);
        }
        return findErrorLocations.length;
    }
}
