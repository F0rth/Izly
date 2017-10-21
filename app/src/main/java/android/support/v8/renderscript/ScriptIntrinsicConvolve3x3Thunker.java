package android.support.v8.renderscript;

import android.renderscript.ScriptIntrinsicConvolve3x3;
import android.support.v8.renderscript.Script.FieldID;
import android.support.v8.renderscript.Script.KernelID;

class ScriptIntrinsicConvolve3x3Thunker extends ScriptIntrinsicConvolve3x3 {
    ScriptIntrinsicConvolve3x3 mN;

    ScriptIntrinsicConvolve3x3Thunker(int i, RenderScript renderScript) {
        super(i, renderScript);
    }

    public static ScriptIntrinsicConvolve3x3Thunker create(RenderScript renderScript, Element element) {
        RenderScriptThunker renderScriptThunker = (RenderScriptThunker) renderScript;
        ElementThunker elementThunker = (ElementThunker) element;
        ScriptIntrinsicConvolve3x3Thunker scriptIntrinsicConvolve3x3Thunker = new ScriptIntrinsicConvolve3x3Thunker(0, renderScript);
        scriptIntrinsicConvolve3x3Thunker.mN = ScriptIntrinsicConvolve3x3.create(renderScriptThunker.mN, elementThunker.getNObj());
        return scriptIntrinsicConvolve3x3Thunker;
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

    ScriptIntrinsicConvolve3x3 getNObj() {
        return this.mN;
    }

    public void setCoefficients(float[] fArr) {
        this.mN.setCoefficients(fArr);
    }

    public void setInput(Allocation allocation) {
        this.mN.setInput(((AllocationThunker) allocation).getNObj());
    }
}
