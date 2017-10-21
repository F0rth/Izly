package android.support.v8.renderscript;

import android.content.Context;
import android.renderscript.RenderScript;
import android.support.v8.renderscript.RenderScript.Priority;

class RenderScriptThunker extends RenderScript {
    RenderScript mN;

    RenderScriptThunker(Context context) {
        super(context);
        isNative = true;
    }

    public static RenderScript create(Context context, int i) {
        RenderScript renderScriptThunker = new RenderScriptThunker(context);
        renderScriptThunker.mN = RenderScript.create(context, i);
        return renderScriptThunker;
    }

    public void contextDump() {
        this.mN.contextDump();
    }

    public void destroy() {
        this.mN.destroy();
        this.mN = null;
    }

    public void finish() {
        this.mN.finish();
    }

    public void setPriority(Priority priority) {
        if (priority == Priority.LOW) {
            this.mN.setPriority(RenderScript.Priority.LOW);
        }
        if (priority == Priority.NORMAL) {
            this.mN.setPriority(RenderScript.Priority.NORMAL);
        }
    }

    void validate() {
        if (this.mN == null) {
            throw new RSInvalidStateException("Calling RS with no Context active.");
        }
    }
}
