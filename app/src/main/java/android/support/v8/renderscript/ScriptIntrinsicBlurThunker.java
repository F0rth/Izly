package android.support.v8.renderscript;

import android.renderscript.ScriptIntrinsicBlur;
import android.support.v8.renderscript.Script.FieldID;
import android.support.v8.renderscript.Script.KernelID;

class ScriptIntrinsicBlurThunker extends ScriptIntrinsicBlur {
    ScriptIntrinsicBlur mN;

    protected ScriptIntrinsicBlurThunker(int i, RenderScript renderScript) {
        super(i, renderScript);
    }

    public static ScriptIntrinsicBlurThunker create(RenderScript renderScript, Element element) {
        RenderScriptThunker renderScriptThunker = (RenderScriptThunker) renderScript;
        ElementThunker elementThunker = (ElementThunker) element;
        ScriptIntrinsicBlurThunker scriptIntrinsicBlurThunker = new ScriptIntrinsicBlurThunker(0, renderScript);
        scriptIntrinsicBlurThunker.mN = ScriptIntrinsicBlur.create(renderScriptThunker.mN, elementThunker.getNObj());
        return scriptIntrinsicBlurThunker;
    }

    public void forEach(Allocation allocation) {
        AllocationThunker allocationThunker = (AllocationThunker) allocation;
        if (allocationThunker != null) {
            this.mN.forEach(allocationThunker.getNObj());
        }
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

    ScriptIntrinsicBlur getNObj() {
        return this.mN;
    }

    public void setInput(Allocation allocation) {
        this.mN.setInput(((AllocationThunker) allocation).getNObj());
    }

    public void setRadius(float f) {
        this.mN.setRadius(f);
    }
}
