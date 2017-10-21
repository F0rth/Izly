package android.support.v8.renderscript;

public class Type extends BaseObj {
    boolean mDimFaces;
    boolean mDimMipmaps;
    int mDimX;
    int mDimY;
    int mDimYuv;
    int mDimZ;
    Element mElement;
    int mElementCount;

    public static class Builder {
        boolean mDimFaces;
        boolean mDimMipmaps;
        int mDimX = 1;
        int mDimY;
        int mDimZ;
        Element mElement;
        RenderScript mRS;
        int mYuv;

        public Builder(RenderScript renderScript, Element element) {
            element.checkValid();
            this.mRS = renderScript;
            this.mElement = element;
        }

        public Type create() {
            if (this.mDimZ > 0) {
                if (this.mDimX <= 0 || this.mDimY <= 0) {
                    throw new RSInvalidStateException("Both X and Y dimension required when Z is present.");
                } else if (this.mDimFaces) {
                    throw new RSInvalidStateException("Cube maps not supported with 3D types.");
                }
            }
            if (this.mDimY > 0 && this.mDimX <= 0) {
                throw new RSInvalidStateException("X dimension required when Y is present.");
            } else if (this.mDimFaces && this.mDimY <= 0) {
                throw new RSInvalidStateException("Cube maps require 2D Types.");
            } else if (this.mYuv == 0 || !(this.mDimZ != 0 || this.mDimFaces || this.mDimMipmaps)) {
                Type create = RenderScript.isNative ? TypeThunker.create((RenderScriptThunker) this.mRS, this.mElement, this.mDimX, this.mDimY, this.mDimZ, this.mDimMipmaps, this.mDimFaces, this.mYuv) : new Type(this.mRS.nTypeCreate(this.mElement.getID(this.mRS), this.mDimX, this.mDimY, this.mDimZ, this.mDimMipmaps, this.mDimFaces, this.mYuv), this.mRS);
                create.mElement = this.mElement;
                create.mDimX = this.mDimX;
                create.mDimY = this.mDimY;
                create.mDimZ = this.mDimZ;
                create.mDimMipmaps = this.mDimMipmaps;
                create.mDimFaces = this.mDimFaces;
                create.mDimYuv = this.mYuv;
                create.calcElementCount();
                return create;
            } else {
                throw new RSInvalidStateException("YUV only supports basic 2D.");
            }
        }

        public Builder setFaces(boolean z) {
            this.mDimFaces = z;
            return this;
        }

        public Builder setMipmaps(boolean z) {
            this.mDimMipmaps = z;
            return this;
        }

        public Builder setX(int i) {
            if (i <= 0) {
                throw new RSIllegalArgumentException("Values of less than 1 for Dimension X are not valid.");
            }
            this.mDimX = i;
            return this;
        }

        public Builder setY(int i) {
            if (i <= 0) {
                throw new RSIllegalArgumentException("Values of less than 1 for Dimension Y are not valid.");
            }
            this.mDimY = i;
            return this;
        }

        public Builder setYuvFormat(int i) {
            switch (i) {
                case 17:
                case 842094169:
                    this.mYuv = i;
                    return this;
                default:
                    throw new RSIllegalArgumentException("Only NV21 and YV12 are supported..");
            }
        }

        public Builder setZ(int i) {
            if (i <= 0) {
                throw new RSIllegalArgumentException("Values of less than 1 for Dimension Z are not valid.");
            }
            this.mDimZ = i;
            return this;
        }
    }

    public enum CubemapFace {
        POSITIVE_X(0),
        NEGATIVE_X(1),
        POSITIVE_Y(2),
        NEGATIVE_Y(3),
        POSITIVE_Z(4),
        NEGATIVE_Z(5);
        
        int mID;

        private CubemapFace(int i) {
            this.mID = i;
        }
    }

    Type(int i, RenderScript renderScript) {
        super(i, renderScript);
    }

    void calcElementCount() {
        boolean hasMipmaps = hasMipmaps();
        int x = getX();
        int y = getY();
        int z = getZ();
        int i = hasFaces() ? 6 : 1;
        if (x == 0) {
            x = 1;
        }
        if (y == 0) {
            y = 1;
        }
        if (z == 0) {
            z = 1;
        }
        int i2 = ((x * y) * z) * i;
        while (hasMipmaps && (x > 1 || y > 1 || z > 1)) {
            if (x > 1) {
                x >>= 1;
            }
            if (y > 1) {
                y >>= 1;
            }
            if (z > 1) {
                z >>= 1;
            }
            i2 += ((x * y) * z) * i;
        }
        this.mElementCount = i2;
    }

    public int getCount() {
        return this.mElementCount;
    }

    public Element getElement() {
        return this.mElement;
    }

    public int getX() {
        return this.mDimX;
    }

    public int getY() {
        return this.mDimY;
    }

    public int getYuv() {
        return this.mDimYuv;
    }

    public int getZ() {
        return this.mDimZ;
    }

    public boolean hasFaces() {
        return this.mDimFaces;
    }

    public boolean hasMipmaps() {
        return this.mDimMipmaps;
    }
}
