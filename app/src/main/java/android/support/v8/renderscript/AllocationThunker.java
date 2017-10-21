package android.support.v8.renderscript;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.renderscript.Allocation;
import android.renderscript.Allocation.MipmapControl;
import android.renderscript.BaseObj;
import android.renderscript.Element;
import android.renderscript.FieldPacker;

class AllocationThunker extends Allocation {
    static Options mBitmapOptions;
    Allocation mN;

    static {
        Options options = new Options();
        mBitmapOptions = options;
        options.inScaled = false;
    }

    AllocationThunker(RenderScript renderScript, Type type, int i, Allocation allocation) {
        super(0, renderScript, type, i);
        this.mType = type;
        this.mUsage = i;
        this.mN = allocation;
    }

    static MipmapControl convertMipmapControl(Allocation.MipmapControl mipmapControl) {
        switch (mipmapControl) {
            case MIPMAP_NONE:
                return MipmapControl.MIPMAP_NONE;
            case MIPMAP_FULL:
                return MipmapControl.MIPMAP_FULL;
            case MIPMAP_ON_SYNC_TO_TEXTURE:
                return MipmapControl.MIPMAP_ON_SYNC_TO_TEXTURE;
            default:
                return null;
        }
    }

    public static Allocation createCubemapFromBitmap(RenderScript renderScript, Bitmap bitmap, Allocation.MipmapControl mipmapControl, int i) {
        Allocation createCubemapFromBitmap = Allocation.createCubemapFromBitmap(((RenderScriptThunker) renderScript).mN, bitmap, convertMipmapControl(mipmapControl), i);
        return new AllocationThunker(renderScript, new TypeThunker(renderScript, createCubemapFromBitmap.getType()), i, createCubemapFromBitmap);
    }

    public static Allocation createCubemapFromCubeFaces(RenderScript renderScript, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6, Allocation.MipmapControl mipmapControl, int i) {
        Allocation createCubemapFromCubeFaces = Allocation.createCubemapFromCubeFaces(((RenderScriptThunker) renderScript).mN, bitmap, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6, convertMipmapControl(mipmapControl), i);
        return new AllocationThunker(renderScript, new TypeThunker(renderScript, createCubemapFromCubeFaces.getType()), i, createCubemapFromCubeFaces);
    }

    public static Allocation createFromBitmap(RenderScript renderScript, Bitmap bitmap, Allocation.MipmapControl mipmapControl, int i) {
        Allocation createFromBitmap = Allocation.createFromBitmap(((RenderScriptThunker) renderScript).mN, bitmap, convertMipmapControl(mipmapControl), i);
        return new AllocationThunker(renderScript, new TypeThunker(renderScript, createFromBitmap.getType()), i, createFromBitmap);
    }

    public static Allocation createFromBitmapResource(RenderScript renderScript, Resources resources, int i, Allocation.MipmapControl mipmapControl, int i2) {
        Allocation createFromBitmapResource = Allocation.createFromBitmapResource(((RenderScriptThunker) renderScript).mN, resources, i, convertMipmapControl(mipmapControl), i2);
        return new AllocationThunker(renderScript, new TypeThunker(renderScript, createFromBitmapResource.getType()), i2, createFromBitmapResource);
    }

    public static Allocation createFromString(RenderScript renderScript, String str, int i) {
        Allocation createFromString = Allocation.createFromString(((RenderScriptThunker) renderScript).mN, str, i);
        return new AllocationThunker(renderScript, new TypeThunker(renderScript, createFromString.getType()), i, createFromString);
    }

    public static Allocation createSized(RenderScript renderScript, Element element, int i, int i2) {
        Allocation createSized = Allocation.createSized(((RenderScriptThunker) renderScript).mN, (Element) element.getNObj(), i, i2);
        return new AllocationThunker(renderScript, new TypeThunker(renderScript, createSized.getType()), i2, createSized);
    }

    public static Allocation createTyped(RenderScript renderScript, Type type, Allocation.MipmapControl mipmapControl, int i) {
        return new AllocationThunker(renderScript, type, i, Allocation.createTyped(((RenderScriptThunker) renderScript).mN, ((TypeThunker) type).mN, convertMipmapControl(mipmapControl), i));
    }

    public void copy1DRangeFrom(int i, int i2, Allocation allocation, int i3) {
        this.mN.copy1DRangeFrom(i, i2, ((AllocationThunker) allocation).mN, i3);
    }

    public void copy1DRangeFrom(int i, int i2, byte[] bArr) {
        this.mN.copy1DRangeFrom(i, i2, bArr);
    }

    public void copy1DRangeFrom(int i, int i2, float[] fArr) {
        this.mN.copy1DRangeFrom(i, i2, fArr);
    }

    public void copy1DRangeFrom(int i, int i2, int[] iArr) {
        this.mN.copy1DRangeFrom(i, i2, iArr);
    }

    public void copy1DRangeFrom(int i, int i2, short[] sArr) {
        this.mN.copy1DRangeFrom(i, i2, sArr);
    }

    public void copy1DRangeFromUnchecked(int i, int i2, byte[] bArr) {
        this.mN.copy1DRangeFromUnchecked(i, i2, bArr);
    }

    public void copy1DRangeFromUnchecked(int i, int i2, float[] fArr) {
        this.mN.copy1DRangeFromUnchecked(i, i2, fArr);
    }

    public void copy1DRangeFromUnchecked(int i, int i2, int[] iArr) {
        this.mN.copy1DRangeFromUnchecked(i, i2, iArr);
    }

    public void copy1DRangeFromUnchecked(int i, int i2, short[] sArr) {
        this.mN.copy1DRangeFromUnchecked(i, i2, sArr);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, Allocation allocation, int i5, int i6) {
        int i7 = i;
        int i8 = i2;
        int i9 = i3;
        int i10 = i4;
        this.mN.copy2DRangeFrom(i7, i8, i9, i10, ((AllocationThunker) allocation).mN, i5, i6);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, byte[] bArr) {
        this.mN.copy2DRangeFrom(i, i2, i3, i4, bArr);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, float[] fArr) {
        this.mN.copy2DRangeFrom(i, i2, i3, i4, fArr);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, int[] iArr) {
        this.mN.copy2DRangeFrom(i, i2, i3, i4, iArr);
    }

    public void copy2DRangeFrom(int i, int i2, int i3, int i4, short[] sArr) {
        this.mN.copy2DRangeFrom(i, i2, i3, i4, sArr);
    }

    public void copy2DRangeFrom(int i, int i2, Bitmap bitmap) {
        this.mN.copy2DRangeFrom(i, i2, bitmap);
    }

    public void copyFrom(Bitmap bitmap) {
        this.mN.copyFrom(bitmap);
    }

    public void copyFrom(Allocation allocation) {
        this.mN.copyFrom(((AllocationThunker) allocation).mN);
    }

    public void copyFrom(byte[] bArr) {
        this.mN.copyFrom(bArr);
    }

    public void copyFrom(float[] fArr) {
        this.mN.copyFrom(fArr);
    }

    public void copyFrom(int[] iArr) {
        this.mN.copyFrom(iArr);
    }

    public void copyFrom(BaseObj[] baseObjArr) {
        if (baseObjArr != null) {
            BaseObj[] baseObjArr2 = new BaseObj[baseObjArr.length];
            for (int i = 0; i < baseObjArr.length; i++) {
                baseObjArr2[i] = baseObjArr[i].getNObj();
            }
            this.mN.copyFrom(baseObjArr2);
        }
    }

    public void copyFrom(short[] sArr) {
        this.mN.copyFrom(sArr);
    }

    public void copyFromUnchecked(byte[] bArr) {
        this.mN.copyFromUnchecked(bArr);
    }

    public void copyFromUnchecked(float[] fArr) {
        this.mN.copyFromUnchecked(fArr);
    }

    public void copyFromUnchecked(int[] iArr) {
        this.mN.copyFromUnchecked(iArr);
    }

    public void copyFromUnchecked(short[] sArr) {
        this.mN.copyFromUnchecked(sArr);
    }

    public void copyTo(Bitmap bitmap) {
        this.mN.copyTo(bitmap);
    }

    public void copyTo(byte[] bArr) {
        this.mN.copyTo(bArr);
    }

    public void copyTo(float[] fArr) {
        this.mN.copyTo(fArr);
    }

    public void copyTo(int[] iArr) {
        this.mN.copyTo(iArr);
    }

    public void copyTo(short[] sArr) {
        this.mN.copyTo(sArr);
    }

    public void generateMipmaps() {
        this.mN.generateMipmaps();
    }

    public int getBytesSize() {
        return this.mN.getBytesSize();
    }

    public Element getElement() {
        return getType().getElement();
    }

    Allocation getNObj() {
        return this.mN;
    }

    public Type getType() {
        return TypeThunker.find(this.mN.getType());
    }

    public int getUsage() {
        return this.mN.getUsage();
    }

    public void ioReceive() {
        this.mN.ioReceive();
    }

    public void ioSend() {
        this.mN.ioSend();
    }

    public void setFromFieldPacker(int i, int i2, FieldPacker fieldPacker) {
        this.mN.setFromFieldPacker(i, i2, new FieldPacker(fieldPacker.getData()));
    }

    public void setFromFieldPacker(int i, FieldPacker fieldPacker) {
        this.mN.setFromFieldPacker(i, new FieldPacker(fieldPacker.getData()));
    }

    public void syncAll(int i) {
        this.mN.syncAll(i);
    }
}
