package android.support.v8.renderscript;

import android.renderscript.ScriptIntrinsicLUT;
import android.support.v8.renderscript.Script.KernelID;

class ScriptIntrinsicLUTThunker extends ScriptIntrinsicLUT {
    ScriptIntrinsicLUT mN;

    private ScriptIntrinsicLUTThunker(int i, RenderScript renderScript) {
        super(i, renderScript);
    }

    public static ScriptIntrinsicLUTThunker create(RenderScript renderScript, Element element) {
        RenderScriptThunker renderScriptThunker = (RenderScriptThunker) renderScript;
        ElementThunker elementThunker = (ElementThunker) element;
        ScriptIntrinsicLUTThunker scriptIntrinsicLUTThunker = new ScriptIntrinsicLUTThunker(0, renderScript);
        scriptIntrinsicLUTThunker.mN = ScriptIntrinsicLUT.create(renderScriptThunker.mN, elementThunker.getNObj());
        return scriptIntrinsicLUTThunker;
    }

    public void forEach(Allocation allocation, Allocation allocation2) {
        this.mN.forEach(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public KernelID getKernelID() {
        KernelID createKernelID = createKernelID(0, 3, null, null);
        createKernelID.mN = this.mN.getKernelID();
        return createKernelID;
    }

    ScriptIntrinsicLUT getNObj() {
        return this.mN;
    }

    public void setAlpha(int i, int i2) {
        this.mN.setAlpha(i, i2);
    }

    public void setBlue(int i, int i2) {
        this.mN.setBlue(i, i2);
    }

    public void setGreen(int i, int i2) {
        this.mN.setGreen(i, i2);
    }

    public void setRed(int i, int i2) {
        this.mN.setRed(i, i2);
    }
}
