package android.support.v8.renderscript;

public class Matrix2f {
    final float[] mMat;

    public Matrix2f() {
        this.mMat = new float[4];
        loadIdentity();
    }

    public Matrix2f(float[] fArr) {
        this.mMat = new float[4];
        System.arraycopy(fArr, 0, this.mMat, 0, this.mMat.length);
    }

    public float get(int i, int i2) {
        return this.mMat[(i * 2) + i2];
    }

    public float[] getArray() {
        return this.mMat;
    }

    public void load(Matrix2f matrix2f) {
        System.arraycopy(matrix2f.getArray(), 0, this.mMat, 0, this.mMat.length);
    }

    public void loadIdentity() {
        this.mMat[0] = 1.0f;
        this.mMat[1] = 0.0f;
        this.mMat[2] = 0.0f;
        this.mMat[3] = 1.0f;
    }

    public void loadMultiply(Matrix2f matrix2f, Matrix2f matrix2f2) {
        for (int i = 0; i < 2; i++) {
            float f = 0.0f;
            float f2 = 0.0f;
            for (int i2 = 0; i2 < 2; i2++) {
                float f3 = matrix2f2.get(i, i2);
                f += matrix2f.get(i2, 0) * f3;
                f2 += f3 * matrix2f.get(i2, 1);
            }
            set(i, 0, f);
            set(i, 1, f2);
        }
    }

    public void loadRotate(float f) {
        float f2 = 0.017453292f * f;
        float cos = (float) Math.cos((double) f2);
        f2 = (float) Math.sin((double) f2);
        this.mMat[0] = cos;
        this.mMat[1] = -f2;
        this.mMat[2] = f2;
        this.mMat[3] = cos;
    }

    public void loadScale(float f, float f2) {
        loadIdentity();
        this.mMat[0] = f;
        this.mMat[3] = f2;
    }

    public void multiply(Matrix2f matrix2f) {
        Matrix2f matrix2f2 = new Matrix2f();
        matrix2f2.loadMultiply(this, matrix2f);
        load(matrix2f2);
    }

    public void rotate(float f) {
        Matrix2f matrix2f = new Matrix2f();
        matrix2f.loadRotate(f);
        multiply(matrix2f);
    }

    public void scale(float f, float f2) {
        Matrix2f matrix2f = new Matrix2f();
        matrix2f.loadScale(f, f2);
        multiply(matrix2f);
    }

    public void set(int i, int i2, float f) {
        this.mMat[(i * 2) + i2] = f;
    }

    public void transpose() {
        float f = this.mMat[1];
        this.mMat[1] = this.mMat[2];
        this.mMat[2] = f;
    }
}
