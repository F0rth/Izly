package android.support.v8.renderscript;

import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.v8.renderscript.Script.FieldID;
import android.support.v8.renderscript.Script.KernelID;

public class ScriptIntrinsicYuvToRGBThunker extends ScriptIntrinsicYuvToRGB {
    ScriptIntrinsicYuvToRGB mN;

    private ScriptIntrinsicYuvToRGBThunker(int i, RenderScript renderScript) {
        super(i, renderScript);
    }

    public static ScriptIntrinsicYuvToRGBThunker create(RenderScript renderScript, Element element) {
        RenderScriptThunker renderScriptThunker = (RenderScriptThunker) renderScript;
        ElementThunker elementThunker = (ElementThunker) element;
        ScriptIntrinsicYuvToRGBThunker scriptIntrinsicYuvToRGBThunker = new ScriptIntrinsicYuvToRGBThunker(0, renderScript);
        scriptIntrinsicYuvToRGBThunker.mN = ScriptIntrinsicYuvToRGB.create(renderScriptThunker.mN, elementThunker.getNObj());
        return scriptIntrinsicYuvToRGBThunker;
    }

    public void forEach(Allocation allocation) {
        this.mN.setInput(((AllocationThunker) allocation).getNObj());
    }

    public FieldID getFieldID_Input() {
        FieldID createFieldID = createFieldID(0, null);
        createFieldID.mN = this.mN.getFieldID_Input();
        return createFieldID;
    }

    public KernelID getKernelID() {
        KernelID createKernelID = createKernelID(0, 2, null, null);
        createKernelID.mN = this.mN.getKernelID();
        return createKernelID;
    }

    ScriptIntrinsicYuvToRGB getNObj() {
        return this.mN;
    }

    public void setInput(Allocation allocation) {
        this.mN.setInput(((AllocationThunker) allocation).getNObj());
    }
}
