package android.support.v8.renderscript;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;

public class Matrix4f {
    final float[] mMat;

    public Matrix4f() {
        this.mMat = new float[16];
        loadIdentity();
    }

    public Matrix4f(float[] fArr) {
        this.mMat = new float[16];
        System.arraycopy(fArr, 0, this.mMat, 0, this.mMat.length);
    }

    private float computeCofactor(int i, int i2) {
        int i3 = (i + 1) % 4;
        int i4 = (i + 2) % 4;
        int i5 = (i + 3) % 4;
        int i6 = (i2 + 1) % 4;
        int i7 = (i2 + 2) % 4;
        int i8 = (i2 + 3) % 4;
        float f = (this.mMat[i3 + (i8 * 4)] * ((this.mMat[(i6 * 4) + i4] * this.mMat[(i7 * 4) + i5]) - (this.mMat[i4 + (i7 * 4)] * this.mMat[i5 + (i6 * 4)]))) + ((((this.mMat[(i7 * 4) + i4] * this.mMat[(i8 * 4) + i5]) - (this.mMat[(i8 * 4) + i4] * this.mMat[(i7 * 4) + i5])) * this.mMat[(i6 * 4) + i3]) - (((this.mMat[(i6 * 4) + i4] * this.mMat[(i8 * 4) + i5]) - (this.mMat[(i8 * 4) + i4] * this.mMat[(i6 * 4) + i5])) * this.mMat[(i7 * 4) + i3]));
        return ((i + i2) & 1) != 0 ? -f : f;
    }

    public float get(int i, int i2) {
        return this.mMat[(i * 4) + i2];
    }

    public float[] getArray() {
        return this.mMat;
    }

    public boolean inverse() {
        int i = 0;
        Matrix4f matrix4f = new Matrix4f();
        for (int i2 = 0; i2 < 4; i2++) {
            for (int i3 = 0; i3 < 4; i3++) {
                matrix4f.mMat[(i2 * 4) + i3] = computeCofactor(i2, i3);
            }
        }
        float f = (((this.mMat[0] * matrix4f.mMat[0]) + (this.mMat[4] * matrix4f.mMat[1])) + (this.mMat[8] * matrix4f.mMat[2])) + (this.mMat[12] * matrix4f.mMat[3]);
        if (((double) Math.abs(f)) < 1.0E-6d) {
            return false;
        }
        f = 1.0f / f;
        while (i < 16) {
            this.mMat[i] = matrix4f.mMat[i] * f;
            i++;
        }
        return true;
    }

    public boolean inverseTranspose() {
        int i = 0;
        Matrix4f matrix4f = new Matrix4f();
        for (int i2 = 0; i2 < 4; i2++) {
            for (int i3 = 0; i3 < 4; i3++) {
                matrix4f.mMat[(i3 * 4) + i2] = computeCofactor(i2, i3);
            }
        }
        float f = (((this.mMat[0] * matrix4f.mMat[0]) + (this.mMat[4] * matrix4f.mMat[4])) + (this.mMat[8] * matrix4f.mMat[8])) + (this.mMat[12] * matrix4f.mMat[12]);
        if (((double) Math.abs(f)) < 1.0E-6d) {
            return false;
        }
        f = 1.0f / f;
        while (i < 16) {
            this.mMat[i] = matrix4f.mMat[i] * f;
            i++;
        }
        return true;
    }

    public void load(Matrix3f matrix3f) {
        this.mMat[0] = matrix3f.mMat[0];
        this.mMat[1] = matrix3f.mMat[1];
        this.mMat[2] = matrix3f.mMat[2];
        this.mMat[3] = 0.0f;
        this.mMat[4] = matrix3f.mMat[3];
        this.mMat[5] = matrix3f.mMat[4];
        this.mMat[6] = matrix3f.mMat[5];
        this.mMat[7] = 0.0f;
        this.mMat[8] = matrix3f.mMat[6];
        this.mMat[9] = matrix3f.mMat[7];
        this.mMat[10] = matrix3f.mMat[8];
        this.mMat[11] = 0.0f;
        this.mMat[12] = 0.0f;
        this.mMat[13] = 0.0f;
        this.mMat[14] = 0.0f;
        this.mMat[15] = 1.0f;
    }

    public void load(Matrix4f matrix4f) {
        System.arraycopy(matrix4f.getArray(), 0, this.mMat, 0, this.mMat.length);
    }

    public void loadFrustum(float f, float f2, float f3, float f4, float f5, float f6) {
        loadIdentity();
        this.mMat[0] = (2.0f * f5) / (f2 - f);
        this.mMat[5] = (2.0f * f5) / (f4 - f3);
        this.mMat[8] = (f2 + f) / (f2 - f);
        this.mMat[9] = (f4 + f3) / (f4 - f3);
        this.mMat[10] = (-(f6 + f5)) / (f6 - f5);
        this.mMat[11] = GroundOverlayOptions.NO_DIMENSION;
        this.mMat[14] = ((-2.0f * f6) * f5) / (f6 - f5);
        this.mMat[15] = 0.0f;
    }

    public void loadIdentity() {
        this.mMat[0] = 1.0f;
        this.mMat[1] = 0.0f;
        this.mMat[2] = 0.0f;
        this.mMat[3] = 0.0f;
        this.mMat[4] = 0.0f;
        this.mMat[5] = 1.0f;
        this.mMat[6] = 0.0f;
        this.mMat[7] = 0.0f;
        this.mMat[8] = 0.0f;
        this.mMat[9] = 0.0f;
        this.mMat[10] = 1.0f;
        this.mMat[11] = 0.0f;
        this.mMat[12] = 0.0f;
        this.mMat[13] = 0.0f;
        this.mMat[14] = 0.0f;
        this.mMat[15] = 1.0f;
    }

    public void loadMultiply(Matrix4f matrix4f, Matrix4f matrix4f2) {
        for (int i = 0; i < 4; i++) {
            float f = 0.0f;
            float f2 = 0.0f;
            float f3 = 0.0f;
            float f4 = 0.0f;
            for (int i2 = 0; i2 < 4; i2++) {
                float f5 = matrix4f2.get(i, i2);
                f += matrix4f.get(i2, 0) * f5;
                f2 += matrix4f.get(i2, 1) * f5;
                f3 += matrix4f.get(i2, 2) * f5;
                f4 += f5 * matrix4f.get(i2, 3);
            }
            set(i, 0, f);
            set(i, 1, f2);
            set(i, 2, f3);
            set(i, 3, f4);
        }
    }

    public void loadOrtho(float f, float f2, float f3, float f4, float f5, float f6) {
        loadIdentity();
        this.mMat[0] = 2.0f / (f2 - f);
        this.mMat[5] = 2.0f / (f4 - f3);
        this.mMat[10] = -2.0f / (f6 - f5);
        this.mMat[12] = (-(f2 + f)) / (f2 - f);
        this.mMat[13] = (-(f4 + f3)) / (f4 - f3);
        this.mMat[14] = (-(f6 + f5)) / (f6 - f5);
    }

    public void loadOrthoWindow(int i, int i2) {
        loadOrtho(0.0f, (float) i, (float) i2, 0.0f, GroundOverlayOptions.NO_DIMENSION, 1.0f);
    }

    public void loadPerspective(float f, float f2, float f3, float f4) {
        float tan = f3 * ((float) Math.tan((double) ((float) ((((double) f) * 3.141592653589793d) / 360.0d))));
        float f5 = -tan;
        loadFrustum(f5 * f2, tan * f2, f5, tan, f3, f4);
    }

    public void loadProjectionNormalized(int i, int i2) {
        Matrix4f matrix4f = new Matrix4f();
        Matrix4f matrix4f2 = new Matrix4f();
        if (i > i2) {
            float f = ((float) i) / ((float) i2);
            matrix4f.loadFrustum(-f, f, GroundOverlayOptions.NO_DIMENSION, 1.0f, 1.0f, 100.0f);
        } else {
            float f2 = ((float) i2) / ((float) i);
            matrix4f.loadFrustum(GroundOverlayOptions.NO_DIMENSION, 1.0f, -f2, f2, 1.0f, 100.0f);
        }
        matrix4f2.loadRotate(BitmapDescriptorFactory.HUE_CYAN, 0.0f, 1.0f, 0.0f);
        matrix4f.loadMultiply(matrix4f, matrix4f2);
        matrix4f2.loadScale(-2.0f, 2.0f, 1.0f);
        matrix4f.loadMultiply(matrix4f, matrix4f2);
        matrix4f2.loadTranslate(0.0f, 0.0f, 2.0f);
        matrix4f.loadMultiply(matrix4f, matrix4f2);
        load(matrix4f);
    }

    public void loadRotate(float f, float f2, float f3, float f4) {
        this.mMat[3] = 0.0f;
        this.mMat[7] = 0.0f;
        this.mMat[11] = 0.0f;
        this.mMat[12] = 0.0f;
        this.mMat[13] = 0.0f;
        this.mMat[14] = 0.0f;
        this.mMat[15] = 1.0f;
        float f5 = 0.017453292f * f;
        float cos = (float) Math.cos((double) f5);
        f5 = (float) Math.sin((double) f5);
        float sqrt = (float) Math.sqrt((double) (((f2 * f2) + (f3 * f3)) + (f4 * f4)));
        if (sqrt == 1.0f) {
            sqrt = 1.0f / sqrt;
            f2 *= sqrt;
            f3 *= sqrt;
            f4 *= sqrt;
        }
        sqrt = 1.0f - cos;
        float f6 = f2 * f3;
        float f7 = f3 * f4;
        float f8 = f4 * f2;
        float f9 = f2 * f5;
        float f10 = f3 * f5;
        f5 *= f4;
        this.mMat[0] = ((f2 * f2) * sqrt) + cos;
        this.mMat[4] = (f6 * sqrt) - f5;
        this.mMat[8] = (f8 * sqrt) + f10;
        this.mMat[1] = f5 + (f6 * sqrt);
        this.mMat[5] = ((f3 * f3) * sqrt) + cos;
        this.mMat[9] = (f7 * sqrt) - f9;
        this.mMat[2] = (f8 * sqrt) - f10;
        this.mMat[6] = (f7 * sqrt) + f9;
        this.mMat[10] = cos + (sqrt * (f4 * f4));
    }

    public void loadScale(float f, float f2, float f3) {
        loadIdentity();
        this.mMat[0] = f;
        this.mMat[5] = f2;
        this.mMat[10] = f3;
    }

    public void loadTranslate(float f, float f2, float f3) {
        loadIdentity();
        this.mMat[12] = f;
        this.mMat[13] = f2;
        this.mMat[14] = f3;
    }

    public void multiply(Matrix4f matrix4f) {
        Matrix4f matrix4f2 = new Matrix4f();
        matrix4f2.loadMultiply(this, matrix4f);
        load(matrix4f2);
    }

    public void rotate(float f, float f2, float f3, float f4) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.loadRotate(f, f2, f3, f4);
        multiply(matrix4f);
    }

    public void scale(float f, float f2, float f3) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.loadScale(f, f2, f3);
        multiply(matrix4f);
    }

    public void set(int i, int i2, float f) {
        this.mMat[(i * 4) + i2] = f;
    }

    public void translate(float f, float f2, float f3) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.loadTranslate(f, f2, f3);
        multiply(matrix4f);
    }

    public void transpose() {
        for (int i = 0; i < 3; i++) {
            for (int i2 = i + 1; i2 < 4; i2++) {
                float f = this.mMat[(i * 4) + i2];
                this.mMat[(i * 4) + i2] = this.mMat[(i2 * 4) + i];
                this.mMat[(i2 * 4) + i] = f;
            }
        }
    }
}
