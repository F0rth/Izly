package android.support.v8.renderscript;

public class Matrix3f {
    final float[] mMat;

    public Matrix3f() {
        this.mMat = new float[9];
        loadIdentity();
    }

    public Matrix3f(float[] fArr) {
        this.mMat = new float[9];
        System.arraycopy(fArr, 0, this.mMat, 0, this.mMat.length);
    }

    public float get(int i, int i2) {
        return this.mMat[(i * 3) + i2];
    }

    public float[] getArray() {
        return this.mMat;
    }

    public void load(Matrix3f matrix3f) {
        System.arraycopy(matrix3f.getArray(), 0, this.mMat, 0, this.mMat.length);
    }

    public void loadIdentity() {
        this.mMat[0] = 1.0f;
        this.mMat[1] = 0.0f;
        this.mMat[2] = 0.0f;
        this.mMat[3] = 0.0f;
        this.mMat[4] = 1.0f;
        this.mMat[5] = 0.0f;
        this.mMat[6] = 0.0f;
        this.mMat[7] = 0.0f;
        this.mMat[8] = 1.0f;
    }

    public void loadMultiply(Matrix3f matrix3f, Matrix3f matrix3f2) {
        for (int i = 0; i < 3; i++) {
            float f = 0.0f;
            float f2 = 0.0f;
            float f3 = 0.0f;
            for (int i2 = 0; i2 < 3; i2++) {
                float f4 = matrix3f2.get(i, i2);
                f += matrix3f.get(i2, 0) * f4;
                f2 += matrix3f.get(i2, 1) * f4;
                f3 += f4 * matrix3f.get(i2, 2);
            }
            set(i, 0, f);
            set(i, 1, f2);
            set(i, 2, f3);
        }
    }

    public void loadRotate(float f) {
        loadIdentity();
        float f2 = 0.017453292f * f;
        float cos = (float) Math.cos((double) f2);
        f2 = (float) Math.sin((double) f2);
        this.mMat[0] = cos;
        this.mMat[1] = -f2;
        this.mMat[3] = f2;
        this.mMat[4] = cos;
    }

    public void loadRotate(float f, float f2, float f3, float f4) {
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
        this.mMat[3] = (f6 * sqrt) - f5;
        this.mMat[6] = (f8 * sqrt) + f10;
        this.mMat[1] = f5 + (f6 * sqrt);
        this.mMat[4] = ((f3 * f3) * sqrt) + cos;
        this.mMat[7] = (f7 * sqrt) - f9;
        this.mMat[2] = (f8 * sqrt) - f10;
        this.mMat[5] = (f7 * sqrt) + f9;
        this.mMat[8] = cos + (sqrt * (f4 * f4));
    }

    public void loadScale(float f, float f2) {
        loadIdentity();
        this.mMat[0] = f;
        this.mMat[4] = f2;
    }

    public void loadScale(float f, float f2, float f3) {
        loadIdentity();
        this.mMat[0] = f;
        this.mMat[4] = f2;
        this.mMat[8] = f3;
    }

    public void loadTranslate(float f, float f2) {
        loadIdentity();
        this.mMat[6] = f;
        this.mMat[7] = f2;
    }

    public void multiply(Matrix3f matrix3f) {
        Matrix3f matrix3f2 = new Matrix3f();
        matrix3f2.loadMultiply(this, matrix3f);
        load(matrix3f2);
    }

    public void rotate(float f) {
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.loadRotate(f);
        multiply(matrix3f);
    }

    public void rotate(float f, float f2, float f3, float f4) {
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.loadRotate(f, f2, f3, f4);
        multiply(matrix3f);
    }

    public void scale(float f, float f2) {
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.loadScale(f, f2);
        multiply(matrix3f);
    }

    public void scale(float f, float f2, float f3) {
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.loadScale(f, f2, f3);
        multiply(matrix3f);
    }

    public void set(int i, int i2, float f) {
        this.mMat[(i * 3) + i2] = f;
    }

    public void translate(float f, float f2) {
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.loadTranslate(f, f2);
        multiply(matrix3f);
    }

    public void transpose() {
        for (int i = 0; i < 2; i++) {
            for (int i2 = i + 1; i2 < 3; i2++) {
                float f = this.mMat[(i * 3) + i2];
                this.mMat[(i * 3) + i2] = this.mMat[(i2 * 3) + i];
                this.mMat[(i2 * 3) + i] = f;
            }
        }
    }
}
