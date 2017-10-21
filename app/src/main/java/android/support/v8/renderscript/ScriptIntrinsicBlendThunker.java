package android.support.v8.renderscript;

import android.renderscript.ScriptIntrinsicBlend;
import android.support.v8.renderscript.Script.KernelID;

class ScriptIntrinsicBlendThunker extends ScriptIntrinsicBlend {
    ScriptIntrinsicBlend mN;

    ScriptIntrinsicBlendThunker(int i, RenderScript renderScript) {
        super(i, renderScript);
    }

    public static ScriptIntrinsicBlendThunker create(RenderScript renderScript, Element element) {
        RenderScriptThunker renderScriptThunker = (RenderScriptThunker) renderScript;
        ElementThunker elementThunker = (ElementThunker) element;
        ScriptIntrinsicBlendThunker scriptIntrinsicBlendThunker = new ScriptIntrinsicBlendThunker(0, renderScript);
        scriptIntrinsicBlendThunker.mN = ScriptIntrinsicBlend.create(renderScriptThunker.mN, elementThunker.getNObj());
        return scriptIntrinsicBlendThunker;
    }

    public void forEachAdd(Allocation allocation, Allocation allocation2) {
        this.mN.forEachAdd(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public void forEachClear(Allocation allocation, Allocation allocation2) {
        this.mN.forEachClear(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public void forEachDst(Allocation allocation, Allocation allocation2) {
        this.mN.forEachDst(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public void forEachDstAtop(Allocation allocation, Allocation allocation2) {
        this.mN.forEachDstAtop(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public void forEachDstIn(Allocation allocation, Allocation allocation2) {
        this.mN.forEachDstIn(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public void forEachDstOut(Allocation allocation, Allocation allocation2) {
        this.mN.forEachDstOut(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public void forEachDstOver(Allocation allocation, Allocation allocation2) {
        this.mN.forEachDstOver(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public void forEachMultiply(Allocation allocation, Allocation allocation2) {
        this.mN.forEachMultiply(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public void forEachSrc(Allocation allocation, Allocation allocation2) {
        this.mN.forEachSrc(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public void forEachSrcAtop(Allocation allocation, Allocation allocation2) {
        this.mN.forEachSrcAtop(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public void forEachSrcIn(Allocation allocation, Allocation allocation2) {
        this.mN.forEachSrcIn(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public void forEachSrcOut(Allocation allocation, Allocation allocation2) {
        this.mN.forEachSrcOut(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public void forEachSrcOver(Allocation allocation, Allocation allocation2) {
        this.mN.forEachSrcOver(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public void forEachSubtract(Allocation allocation, Allocation allocation2) {
        this.mN.forEachSubtract(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public void forEachXor(Allocation allocation, Allocation allocation2) {
        this.mN.forEachXor(((AllocationThunker) allocation).getNObj(), ((AllocationThunker) allocation2).getNObj());
    }

    public KernelID getKernelIDAdd() {
        KernelID createKernelID = createKernelID(34, 3, null, null);
        createKernelID.mN = this.mN.getKernelIDAdd();
        return createKernelID;
    }

    public KernelID getKernelIDClear() {
        KernelID createKernelID = createKernelID(0, 3, null, null);
        createKernelID.mN = this.mN.getKernelIDClear();
        return createKernelID;
    }

    public KernelID getKernelIDDst() {
        KernelID createKernelID = createKernelID(2, 3, null, null);
        createKernelID.mN = this.mN.getKernelIDDst();
        return createKernelID;
    }

    public KernelID getKernelIDDstAtop() {
        KernelID createKernelID = createKernelID(10, 3, null, null);
        createKernelID.mN = this.mN.getKernelIDDstAtop();
        return createKernelID;
    }

    public KernelID getKernelIDDstIn() {
        KernelID createKernelID = createKernelID(6, 3, null, null);
        createKernelID.mN = this.mN.getKernelIDDstIn();
        return createKernelID;
    }

    public KernelID getKernelIDDstOut() {
        KernelID createKernelID = createKernelID(8, 3, null, null);
        createKernelID.mN = this.mN.getKernelIDDstOut();
        return createKernelID;
    }

    public KernelID getKernelIDDstOver() {
        KernelID createKernelID = createKernelID(4, 3, null, null);
        createKernelID.mN = this.mN.getKernelIDDstOver();
        return createKernelID;
    }

    public KernelID getKernelIDMultiply() {
        KernelID createKernelID = createKernelID(14, 3, null, null);
        createKernelID.mN = this.mN.getKernelIDMultiply();
        return createKernelID;
    }

    public KernelID getKernelIDSrc() {
        KernelID createKernelID = createKernelID(1, 3, null, null);
        createKernelID.mN = this.mN.getKernelIDSrc();
        return createKernelID;
    }

    public KernelID getKernelIDSrcAtop() {
        KernelID createKernelID = createKernelID(9, 3, null, null);
        createKernelID.mN = this.mN.getKernelIDSrcAtop();
        return createKernelID;
    }

    public KernelID getKernelIDSrcIn() {
        KernelID createKernelID = createKernelID(5, 3, null, null);
        createKernelID.mN = this.mN.getKernelIDSrcIn();
        return createKernelID;
    }

    public KernelID getKernelIDSrcOut() {
        KernelID createKernelID = createKernelID(7, 3, null, null);
        createKernelID.mN = this.mN.getKernelIDSrcOut();
        return createKernelID;
    }

    public KernelID getKernelIDSrcOver() {
        KernelID createKernelID = createKernelID(3, 3, null, null);
        createKernelID.mN = this.mN.getKernelIDSrcOver();
        return createKernelID;
    }

    public KernelID getKernelIDSubtract() {
        KernelID createKernelID = createKernelID(35, 3, null, null);
        createKernelID.mN = this.mN.getKernelIDSubtract();
        return createKernelID;
    }

    public KernelID getKernelIDXor() {
        KernelID createKernelID = createKernelID(11, 3, null, null);
        createKernelID.mN = this.mN.getKernelIDXor();
        return createKernelID;
    }

    ScriptIntrinsicBlend getNObj() {
        return this.mN;
    }
}
