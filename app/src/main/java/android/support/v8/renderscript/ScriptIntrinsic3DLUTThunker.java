package android.support.v8.renderscript;

import android.renderscript.ScriptIntrinsic3DLUT;
import android.support.v8.renderscript.Script.KernelID;

class ScriptIntrinsic3DLUTThunker extends ScriptIntrinsic3DLUT {
    ScriptIntrinsic3DLUT mN;

    private ScriptIntrinsic3DLUTThunker(int i, RenderScript renderScript, Element element) {
        super(i, renderScript, element);
    }

    public static ScriptIntrinsic3DLUTThunker create(RenderScript renderScript, Element element) {
        RenderScriptThunker renderScriptThunker = (RenderScriptThunker) renderScript;
        ElementThunker elementThunker = (ElementThunker) element;
        ScriptIntrinsic3DLUTThunker scriptIntrinsic3DLUTThunker = new ScriptIntrinsic3DLUTThunker(0, renderScript, element);
        scriptIntrinsic3DLUTThunker.mN = ScriptIntrinsic3DLUT.create(renderScriptThunker.mN, elementThunker.getNObj());
        return scriptIntrinsic3DLUTThunker;
    }

    public void forEach(Allocation allocation, Allocation allocation2) {
        this.mN.forEach(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public KernelID getKernelID() {
        KernelID createKernelID = createKernelID(0, 3, null, null);
        createKernelID.mN = this.mN.getKernelID();
        return createKernelID;
    }

    ScriptIntrinsic3DLUT getNObj() {
        return this.mN;
    }

    public void setLUT(Allocation allocation) {
        this.mN.setLUT(((AllocationThunker) allocation).getNObj());
    }
}
