package android.support.v8.renderscript;

import android.renderscript.Element;
import android.renderscript.Element.Builder;
import android.renderscript.Element.DataKind;
import android.renderscript.Element.DataType;

class ElementThunker extends Element {
    Element mN;

    static class BuilderThunker {
        Builder mN;

        public BuilderThunker(RenderScript renderScript) {
            this.mN = new Builder(((RenderScriptThunker) renderScript).mN);
        }

        public void add(Element element, String str, int i) {
            this.mN.add(((ElementThunker) element).mN, str, i);
        }

        public Element create(RenderScript renderScript) {
            return new ElementThunker(renderScript, this.mN.create());
        }
    }

    ElementThunker(RenderScript renderScript, Element element) {
        super(0, renderScript);
        this.mN = element;
    }

    static DataKind convertKind(Element.DataKind dataKind) {
        switch (dataKind) {
            case USER:
                return DataKind.USER;
            case PIXEL_L:
                return DataKind.PIXEL_L;
            case PIXEL_A:
                return DataKind.PIXEL_A;
            case PIXEL_LA:
                return DataKind.PIXEL_LA;
            case PIXEL_RGB:
                return DataKind.PIXEL_RGB;
            case PIXEL_RGBA:
                return DataKind.PIXEL_RGBA;
            default:
                return null;
        }
    }

    static DataType convertType(Element.DataType dataType) {
        switch (dataType) {
            case NONE:
                return DataType.NONE;
            case FLOAT_32:
                return DataType.FLOAT_32;
            case FLOAT_64:
                return DataType.FLOAT_64;
            case SIGNED_8:
                return DataType.SIGNED_8;
            case SIGNED_16:
                return DataType.SIGNED_16;
            case SIGNED_32:
                return DataType.SIGNED_32;
            case SIGNED_64:
                return DataType.SIGNED_64;
            case UNSIGNED_8:
                return DataType.UNSIGNED_8;
            case UNSIGNED_16:
                return DataType.UNSIGNED_16;
            case UNSIGNED_32:
                return DataType.UNSIGNED_32;
            case UNSIGNED_64:
                return DataType.UNSIGNED_64;
            case BOOLEAN:
                return DataType.BOOLEAN;
            case MATRIX_4X4:
                return DataType.MATRIX_4X4;
            case MATRIX_3X3:
                return DataType.MATRIX_3X3;
            case MATRIX_2X2:
                return DataType.MATRIX_2X2;
            case RS_ELEMENT:
                return DataType.RS_ELEMENT;
            case RS_TYPE:
                return DataType.RS_TYPE;
            case RS_ALLOCATION:
                return DataType.RS_ALLOCATION;
            case RS_SAMPLER:
                return DataType.RS_SAMPLER;
            case RS_SCRIPT:
                return DataType.RS_SCRIPT;
            default:
                return null;
        }
    }

    static Element create(RenderScript renderScript, Element.DataType dataType) {
        Element F32;
        RenderScriptThunker renderScriptThunker = (RenderScriptThunker) renderScript;
        switch (dataType) {
            case FLOAT_32:
                F32 = Element.F32(renderScriptThunker.mN);
                break;
            case FLOAT_64:
                F32 = Element.F64(renderScriptThunker.mN);
                break;
            case SIGNED_8:
                F32 = Element.I8(renderScriptThunker.mN);
                break;
            case SIGNED_16:
                F32 = Element.I16(renderScriptThunker.mN);
                break;
            case SIGNED_32:
                F32 = Element.I32(renderScriptThunker.mN);
                break;
            case SIGNED_64:
                F32 = Element.I64(renderScriptThunker.mN);
                break;
            case UNSIGNED_8:
                F32 = Element.U8(renderScriptThunker.mN);
                break;
            case UNSIGNED_16:
                F32 = Element.U16(renderScriptThunker.mN);
                break;
            case UNSIGNED_32:
                F32 = Element.U32(renderScriptThunker.mN);
                break;
            case UNSIGNED_64:
                F32 = Element.U64(renderScriptThunker.mN);
                break;
            case BOOLEAN:
                F32 = Element.BOOLEAN(renderScriptThunker.mN);
                break;
            case MATRIX_4X4:
                F32 = Element.MATRIX_4X4(renderScriptThunker.mN);
                break;
            case MATRIX_3X3:
                F32 = Element.MATRIX_3X3(renderScriptThunker.mN);
                break;
            case MATRIX_2X2:
                F32 = Element.MATRIX_2X2(renderScriptThunker.mN);
                break;
            case RS_ELEMENT:
                F32 = Element.ELEMENT(renderScriptThunker.mN);
                break;
            case RS_TYPE:
                F32 = Element.TYPE(renderScriptThunker.mN);
                break;
            case RS_ALLOCATION:
                F32 = Element.ALLOCATION(renderScriptThunker.mN);
                break;
            case RS_SAMPLER:
                F32 = Element.SAMPLER(renderScriptThunker.mN);
                break;
            case RS_SCRIPT:
                F32 = Element.SCRIPT(renderScriptThunker.mN);
                break;
            default:
                F32 = null;
                break;
        }
        return new ElementThunker(renderScript, F32);
    }

    public static Element createPixel(RenderScript renderScript, Element.DataType dataType, Element.DataKind dataKind) {
        return new ElementThunker(renderScript, Element.createPixel(((RenderScriptThunker) renderScript).mN, convertType(dataType), convertKind(dataKind)));
    }

    public static Element createVector(RenderScript renderScript, Element.DataType dataType, int i) {
        return new ElementThunker(renderScript, Element.createVector(((RenderScriptThunker) renderScript).mN, convertType(dataType), i));
    }

    public int getBytesSize() {
        return this.mN.getBytesSize();
    }

    public Element.DataKind getDataKind() {
        return this.mKind;
    }

    public Element.DataType getDataType() {
        return this.mType;
    }

    Element getNObj() {
        return this.mN;
    }

    public Element getSubElement(int i) {
        return new ElementThunker(this.mRS, this.mN.getSubElement(i));
    }

    public int getSubElementArraySize(int i) {
        return this.mN.getSubElementArraySize(i);
    }

    public int getSubElementCount() {
        return this.mN.getSubElementCount();
    }

    public String getSubElementName(int i) {
        return this.mN.getSubElementName(i);
    }

    public int getSubElementOffsetBytes(int i) {
        return this.mN.getSubElementOffsetBytes(i);
    }

    public int getVectorSize() {
        return this.mN.getVectorSize();
    }

    public boolean isCompatible(Element element) {
        return ((ElementThunker) element).mN.isCompatible(this.mN);
    }

    public boolean isComplex() {
        return this.mN.isComplex();
    }
}
