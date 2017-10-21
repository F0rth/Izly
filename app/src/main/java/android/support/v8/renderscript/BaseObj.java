package android.support.v8.renderscript;

public class BaseObj {
    private boolean mDestroyed = false;
    private int mID;
    RenderScript mRS;

    BaseObj(int i, RenderScript renderScript) {
        renderScript.validate();
        this.mRS = renderScript;
        this.mID = i;
    }

    void checkValid() {
        if (this.mID == 0 && getNObj() == null) {
            throw new RSIllegalArgumentException("Invalid object.");
        }
    }

    public void destroy() {
        synchronized (this) {
            if (this.mDestroyed) {
                throw new RSInvalidStateException("Object already destroyed.");
            }
            this.mDestroyed = true;
            this.mRS.nObjDestroy(this.mID);
        }
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (getClass() != obj.getClass()) {
                return false;
            }
            if (this.mID != ((BaseObj) obj).mID) {
                return false;
            }
        }
        return true;
    }

    protected void finalize() throws Throwable {
        if (!this.mDestroyed) {
            if (this.mID != 0 && this.mRS.isAlive()) {
                this.mRS.nObjDestroy(this.mID);
            }
            this.mRS = null;
            this.mID = 0;
            this.mDestroyed = true;
        }
        super.finalize();
    }

    int getID(RenderScript renderScript) {
        this.mRS.validate();
        if (RenderScript.isNative && getNObj() != null) {
            return getNObj().hashCode();
        }
        if (this.mDestroyed) {
            throw new RSInvalidStateException("using a destroyed object.");
        } else if (this.mID == 0) {
            throw new RSRuntimeException("Internal error: Object id 0.");
        } else if (renderScript == null || renderScript == this.mRS) {
            return this.mID;
        } else {
            throw new RSInvalidStateException("using object with mismatched context.");
        }
    }

    android.renderscript.BaseObj getNObj() {
        return null;
    }

    public int hashCode() {
        return this.mID;
    }

    void setID(int i) {
        if (this.mID != 0) {
            throw new RSRuntimeException("Internal Error, reset of object ID.");
        }
        this.mID = i;
    }
}
