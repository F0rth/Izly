package android.support.v8.renderscript;

import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ScriptC extends Script {
    private static final String TAG = "ScriptC";

    protected ScriptC(int i, RenderScript renderScript) {
        super(i, renderScript);
    }

    protected ScriptC(RenderScript renderScript, Resources resources, int i) {
        super(0, renderScript);
        if (RenderScript.isNative) {
            this.mT = new ScriptCThunker((RenderScriptThunker) renderScript, resources, i);
            return;
        }
        int internalCreate = internalCreate(renderScript, resources, i);
        if (internalCreate == 0) {
            throw new RSRuntimeException("Loading of ScriptC script failed.");
        }
        setID(internalCreate);
    }

    private static int internalCreate(RenderScript renderScript, Resources resources, int i) {
        synchronized (ScriptC.class) {
            String resourceEntryName;
            try {
                InputStream openRawResource = resources.openRawResource(i);
                try {
                    int length;
                    Object obj;
                    Object obj2 = new byte[1024];
                    int i2 = 0;
                    while (true) {
                        int length2;
                        length = obj2.length - i2;
                        if (length == 0) {
                            obj = new byte[(obj2.length * 2)];
                            System.arraycopy(obj2, 0, obj, 0, obj2.length);
                            length2 = obj.length - i2;
                        } else {
                            Object obj3 = obj2;
                            length2 = length;
                            obj = obj3;
                        }
                        length2 = openRawResource.read(obj, i2, length2);
                        if (length2 <= 0) {
                            break;
                        }
                        i2 = length2 + i2;
                        obj2 = obj;
                    }
                    openRawResource.close();
                    resourceEntryName = resources.getResourceEntryName(i);
                    length = renderScript.nScriptCCreate(resourceEntryName, renderScript.getApplicationContext().getCacheDir().toString(), obj, i2);
                    return length;
                } catch (IOException e) {
                    throw new NotFoundException();
                } catch (Throwable th) {
                    openRawResource.close();
                }
            } finally {
                resourceEntryName = ScriptC.class;
            }
        }
    }
}
