package android.support.v8.renderscript;

import android.renderscript.ScriptIntrinsicConvolve5x5;
import android.support.v8.renderscript.Script.FieldID;
import android.support.v8.renderscript.Script.KernelID;

class ScriptIntrinsicConvolve5x5Thunker extends ScriptIntrinsicConvolve5x5 {
    ScriptIntrinsicConvolve5x5 mN;

    ScriptIntrinsicConvolve5x5Thunker(int i, RenderScript renderScript) {
        super(i, renderScript);
    }

    public static ScriptIntrinsicConvolve5x5Thunker create(RenderScript renderScript, Element element) {
        RenderScriptThunker renderScriptThunker = (RenderScriptThunker) renderScript;
        ElementThunker elementThunker = (ElementThunker) element;
        ScriptIntrinsicConvolve5x5Thunker scriptIntrinsicConvolve5x5Thunker = new ScriptIntrinsicConvolve5x5Thunker(0, renderScript);
        scriptIntrinsicConvolve5x5Thunker.mN = ScriptIntrinsicConvolve5x5.create(renderScriptThunker.mN, elementThunker.getNObj());
        return scriptIntrinsicConvolve5x5Thunker;
    }

    public void forEach(Allocation allocation) {
        this.mN.forEach(((AllocationThunker) allocation).getNObj());
    }

    public FieldID getFieldID_Input() {
        FieldID createFieldID = createFieldID(1, null);
        createFieldID.mN = this.mN.getFieldID_Input();
        return createFieldID;
    }

    public KernelID getKernelID() {
        KernelID createKernelID = createKernelID(0, 2, null, null);
        createKernelID.mN = this.mN.getKernelID();
        return createKernelID;
    }

    ScriptIntrinsicConvolve5x5 getNObj() {
        return this.mN;
    }

    public void setCoefficients(float[] fArr) {
        this.mN.setCoefficients(fArr);
    }

    public void setInput(Allocation allocation) {
        this.mN.setInput(((AllocationThunker) allocation).getNObj());
    }
}
