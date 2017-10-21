package android.support.v8.renderscript;

import android.renderscript.ScriptGroup;
import android.support.v8.renderscript.Script.FieldID;
import android.support.v8.renderscript.Script.KernelID;

class ScriptGroupThunker extends ScriptGroup {
    ScriptGroup mN;

    public static final class Builder {
        android.renderscript.ScriptGroup.Builder bN;
        RenderScript mRS;

        Builder(RenderScript renderScript) {
            RenderScriptThunker renderScriptThunker = (RenderScriptThunker) renderScript;
            this.mRS = renderScript;
            this.bN = new android.renderscript.ScriptGroup.Builder(renderScriptThunker.mN);
        }

        public final Builder addConnection(Type type, KernelID kernelID, FieldID fieldID) {
            this.bN.addConnection(((TypeThunker) type).getNObj(), kernelID.mN, fieldID.mN);
            return this;
        }

        public final Builder addConnection(Type type, KernelID kernelID, KernelID kernelID2) {
            this.bN.addConnection(((TypeThunker) type).getNObj(), kernelID.mN, kernelID2.mN);
            return this;
        }

        public final Builder addKernel(KernelID kernelID) {
            this.bN.addKernel(kernelID.mN);
            return this;
        }

        public final ScriptGroupThunker create() {
            ScriptGroupThunker scriptGroupThunker = new ScriptGroupThunker(0, this.mRS);
            scriptGroupThunker.mN = this.bN.create();
            return scriptGroupThunker;
        }
    }

    ScriptGroupThunker(int i, RenderScript renderScript) {
        super(i, renderScript);
    }

    public void execute() {
        this.mN.execute();
    }

    ScriptGroup getNObj() {
        return this.mN;
    }

    public void setInput(KernelID kernelID, Allocation allocation) {
        this.mN.setInput(kernelID.mN, ((AllocationThunker) allocation).getNObj());
    }

    public void setOutput(KernelID kernelID, Allocation allocation) {
        this.mN.setOutput(kernelID.mN, ((AllocationThunker) allocation).getNObj());
    }
}
