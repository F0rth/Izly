package android.support.v8.renderscript;

import android.renderscript.Matrix3f;
import android.renderscript.Matrix4f;
import android.renderscript.ScriptIntrinsicColorMatrix;
import android.support.v8.renderscript.Script.KernelID;

class ScriptIntrinsicColorMatrixThunker extends ScriptIntrinsicColorMatrix {
    ScriptIntrinsicColorMatrix mN;

    private ScriptIntrinsicColorMatrixThunker(int i, RenderScript renderScript) {
        super(i, renderScript);
    }

    public static ScriptIntrinsicColorMatrixThunker create(RenderScript renderScript, Element element) {
        RenderScriptThunker renderScriptThunker = (RenderScriptThunker) renderScript;
        ElementThunker elementThunker = (ElementThunker) element;
        ScriptIntrinsicColorMatrixThunker scriptIntrinsicColorMatrixThunker = new ScriptIntrinsicColorMatrixThunker(0, renderScript);
        scriptIntrinsicColorMatrixThunker.mN = ScriptIntrinsicColorMatrix.create(renderScriptThunker.mN, elementThunker.getNObj());
        return scriptIntrinsicColorMatrixThunker;
    }

    public void forEach(Allocation allocation, Allocation allocation2) {
        this.mN.forEach(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public KernelID getKernelID() {
        KernelID createKernelID = createKernelID(0, 3, null, null);
        createKernelID.mN = this.mN.getKernelID();
        return createKernelID;
    }

    ScriptIntrinsicColorMatrix getNObj() {
        return this.mN;
    }

    public void setColorMatrix(Matrix3f matrix3f) {
        this.mN.setColorMatrix(new Matrix3f(matrix3f.getArray()));
    }

    public void setColorMatrix(Matrix4f matrix4f) {
        this.mN.setColorMatrix(new Matrix4f(matrix4f.getArray()));
    }

    public void setGreyscale() {
        this.mN.setGreyscale();
    }

    public void setRGBtoYUV() {
        this.mN.setRGBtoYUV();
    }

    public void setYUVtoRGB() {
        this.mN.setYUVtoRGB();
    }
}
