package com.google.zxing.common.reedsolomon;

public final class ReedSolomonDecoder {
    private final GenericGF field;

    public ReedSolomonDecoder(GenericGF genericGF) {
        this.field = genericGF;
    }

    private int[] findErrorLocations(GenericGFPoly genericGFPoly) throws ReedSolomonException {
        int i = 0;
        int i2 = 1;
        int degree = genericGFPoly.getDegree();
        if (degree == 1) {
            return new int[]{genericGFPoly.getCoefficient(1)};
        }
        int[] iArr = new int[degree];
        while (i2 < this.field.getSize() && i < degree) {
            if (genericGFPoly.evaluateAt(i2) == 0) {
                iArr[i] = this.field.inverse(i2);
                i++;
            }
            i2++;
        }
        if (i == degree) {
            return iArr;
        }
        throw new ReedSolomonException("Error locator degree does not match number of roots");
    }

    private int[] findErrorMagnitudes(GenericGFPoly genericGFPoly, int[] iArr) {
        int length = iArr.length;
        int[] iArr2 = new int[length];
        for (int i = 0; i < length; i++) {
            int inverse = this.field.inverse(iArr[i]);
            int i2 = 1;
            int i3 = 0;
            while (i3 < length) {
                int multiply;
                if (i != i3) {
                    multiply = this.field.multiply(iArr[i3], inverse);
                    multiply = this.field.multiply(i2, (multiply & 1) == 0 ? multiply | 1 : multiply & -2);
                } else {
                    multiply = i2;
                }
                i3++;
                i2 = multiply;
            }
            iArr2[i] = this.field.multiply(genericGFPoly.evaluateAt(inverse), this.field.inverse(i2));
            if (this.field.getGeneratorBase() != 0) {
                iArr2[i] = this.field.multiply(iArr2[i], inverse);
            }
        }
        return iArr2;
    }

    public final void decode(int[] iArr, int i) throws ReedSolomonException {
        int i2;
        int i3 = 0;
        GenericGFPoly genericGFPoly = new GenericGFPoly(this.field, iArr);
        int[] iArr2 = new int[i];
        int i4 = 1;
        for (i2 = 0; i2 < i; i2++) {
            int evaluateAt = genericGFPoly.evaluateAt(this.field.exp(this.field.getGeneratorBase() + i2));
            iArr2[(i - 1) - i2] = evaluateAt;
            if (evaluateAt != 0) {
                i4 = 0;
            }
        }
        if (i4 == 0) {
            GenericGFPoly[] runEuclideanAlgorithm = runEuclideanAlgorithm(this.field.buildMonomial(i, 1), new GenericGFPoly(this.field, iArr2), i);
            GenericGFPoly genericGFPoly2 = runEuclideanAlgorithm[0];
            GenericGFPoly genericGFPoly3 = runEuclideanAlgorithm[1];
            int[] findErrorLocations = findErrorLocations(genericGFPoly2);
            int[] findErrorMagnitudes = findErrorMagnitudes(genericGFPoly3, findErrorLocations);
            while (i3 < findErrorLocations.length) {
                i2 = (iArr.length - 1) - this.field.log(findErrorLocations[i3]);
                if (i2 < 0) {
                    throw new ReedSolomonException("Bad error location");
                }
                iArr[i2] = GenericGF.addOrSubtract(iArr[i2], findErrorMagnitudes[i3]);
                i3++;
            }
        }
    }

    public final GenericGFPoly[] runEuclideanAlgorithm(GenericGFPoly genericGFPoly, GenericGFPoly genericGFPoly2, int i) throws ReedSolomonException {
        if (genericGFPoly.getDegree() < genericGFPoly2.getDegree()) {
            GenericGFPoly genericGFPoly3 = genericGFPoly2;
            genericGFPoly2 = genericGFPoly;
            genericGFPoly = genericGFPoly3;
        }
        GenericGFPoly zero = this.field.getZero();
        GenericGFPoly one = this.field.getOne();
        GenericGFPoly genericGFPoly4 = genericGFPoly2;
        while (genericGFPoly4.getDegree() >= i / 2) {
            if (genericGFPoly4.isZero()) {
                throw new ReedSolomonException("r_{i-1} was zero");
            }
            GenericGFPoly zero2 = this.field.getZero();
            int inverse = this.field.inverse(genericGFPoly4.getCoefficient(genericGFPoly4.getDegree()));
            genericGFPoly2 = genericGFPoly;
            while (genericGFPoly2.getDegree() >= genericGFPoly4.getDegree() && !genericGFPoly2.isZero()) {
                int degree = genericGFPoly2.getDegree() - genericGFPoly4.getDegree();
                int multiply = this.field.multiply(genericGFPoly2.getCoefficient(genericGFPoly2.getDegree()), inverse);
                zero2 = zero2.addOrSubtract(this.field.buildMonomial(degree, multiply));
                genericGFPoly2 = genericGFPoly2.addOrSubtract(genericGFPoly4.multiplyByMonomial(degree, multiply));
            }
            zero = zero2.multiply(one).addOrSubtract(zero);
            if (genericGFPoly2.getDegree() >= genericGFPoly4.getDegree()) {
                throw new IllegalStateException("Division algorithm failed to reduce polynomial?");
            }
            genericGFPoly = genericGFPoly4;
            genericGFPoly4 = genericGFPoly2;
            genericGFPoly3 = one;
            one = zero;
            zero = genericGFPoly3;
        }
        int coefficient = one.getCoefficient(0);
        if (coefficient == 0) {
            throw new ReedSolomonException("sigmaTilde(0) was zero");
        }
        coefficient = this.field.inverse(coefficient);
        return new GenericGFPoly[]{one.multiply(coefficient), genericGFPoly4.multiply(coefficient)};
    }
}
