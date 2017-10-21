package android.support.v8.renderscript;

import android.content.res.Resources;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.FieldPacker;
import android.renderscript.Script;
import android.renderscript.Script.FieldID;
import android.renderscript.Script.KernelID;
import android.renderscript.ScriptC;
import android.support.v8.renderscript.Script.LaunchOptions;

class ScriptCThunker extends ScriptC {
    private static final String TAG = "ScriptC";

    protected ScriptCThunker(RenderScriptThunker renderScriptThunker, Resources resources, int i) {
        super(renderScriptThunker.mN, resources, i);
    }

    void thunkBindAllocation(Allocation allocation, int i) {
        Allocation allocation2 = null;
        if (allocation != null) {
            allocation2 = ((AllocationThunker) allocation).mN;
        }
        bindAllocation(allocation2, i);
    }

    FieldID thunkCreateFieldID(int i, Element element) {
        return createFieldID(i, ((ElementThunker) element).getNObj());
    }

    KernelID thunkCreateKernelID(int i, int i2, Element element, Element element2) {
        Element element3 = null;
        Element element4 = element != null ? ((ElementThunker) element).mN : null;
        if (element2 != null) {
            element3 = ((ElementThunker) element2).mN;
        }
        return createKernelID(i, i2, element4, element3);
    }

    void thunkForEach(int i, Allocation allocation, Allocation allocation2, FieldPacker fieldPacker) {
        FieldPacker fieldPacker2 = null;
        Allocation allocation3 = allocation != null ? ((AllocationThunker) allocation).mN : null;
        Allocation allocation4 = allocation2 != null ? ((AllocationThunker) allocation2).mN : null;
        if (fieldPacker != null) {
            fieldPacker2 = new FieldPacker(fieldPacker.getData());
        }
        forEach(i, allocation3, allocation4, fieldPacker2);
    }

    void thunkForEach(int i, Allocation allocation, Allocation allocation2, FieldPacker fieldPacker, LaunchOptions launchOptions) {
        Script.LaunchOptions launchOptions2;
        FieldPacker fieldPacker2 = null;
        if (launchOptions != null) {
            launchOptions2 = new Script.LaunchOptions();
            if (launchOptions.getXEnd() > 0) {
                launchOptions2.setX(launchOptions.getXStart(), launchOptions.getXEnd());
            }
            if (launchOptions.getYEnd() > 0) {
                launchOptions2.setY(launchOptions.getYStart(), launchOptions.getYEnd());
            }
            if (launchOptions.getZEnd() > 0) {
                launchOptions2.setZ(launchOptions.getZStart(), launchOptions.getZEnd());
            }
        } else {
            launchOptions2 = null;
        }
        Allocation allocation3 = allocation != null ? ((AllocationThunker) allocation).mN : null;
        Allocation allocation4 = allocation2 != null ? ((AllocationThunker) allocation2).mN : null;
        if (fieldPacker != null) {
            fieldPacker2 = new FieldPacker(fieldPacker.getData());
        }
        forEach(i, allocation3, allocation4, fieldPacker2, launchOptions2);
    }

    void thunkInvoke(int i) {
        invoke(i);
    }

    void thunkInvoke(int i, FieldPacker fieldPacker) {
        invoke(i, new FieldPacker(fieldPacker.getData()));
    }

    void thunkSetTimeZone(String str) {
        setTimeZone(str);
    }

    void thunkSetVar(int i, double d) {
        setVar(i, d);
    }

    void thunkSetVar(int i, float f) {
        setVar(i, f);
    }

    void thunkSetVar(int i, int i2) {
        setVar(i, i2);
    }

    void thunkSetVar(int i, long j) {
        setVar(i, j);
    }

    void thunkSetVar(int i, BaseObj baseObj) {
        if (baseObj == null) {
            setVar(i, 0);
        } else {
            setVar(i, baseObj.getNObj());
        }
    }

    void thunkSetVar(int i, FieldPacker fieldPacker) {
        setVar(i, new FieldPacker(fieldPacker.getData()));
    }

    void thunkSetVar(int i, FieldPacker fieldPacker, Element element, int[] iArr) {
        setVar(i, new FieldPacker(fieldPacker.getData()), ((ElementThunker) element).mN, iArr);
    }

    void thunkSetVar(int i, boolean z) {
        setVar(i, z);
    }
}
