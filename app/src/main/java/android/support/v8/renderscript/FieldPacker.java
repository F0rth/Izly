package android.support.v8.renderscript;

import android.renderscript.Byte2;
import android.renderscript.Byte3;
import android.renderscript.Byte4;
import android.renderscript.Double2;
import android.renderscript.Double3;
import android.renderscript.Double4;
import android.renderscript.Float2;
import android.renderscript.Float3;
import android.renderscript.Float4;
import android.renderscript.Int2;
import android.renderscript.Int3;
import android.renderscript.Int4;
import android.renderscript.Long2;
import android.renderscript.Long3;
import android.renderscript.Long4;
import android.renderscript.Matrix2f;
import android.renderscript.Matrix3f;
import android.renderscript.Matrix4f;
import android.renderscript.Short2;
import android.renderscript.Short3;
import android.renderscript.Short4;
import android.support.v4.internal.view.SupportMenu;
import android.util.Log;

public class FieldPacker {
    private final byte[] mData;
    private int mLen;
    private android.renderscript.FieldPacker mN;
    private int mPos = 0;

    public FieldPacker(int i) {
        this.mLen = i;
        this.mData = new byte[i];
        if (RenderScript.shouldThunk()) {
            this.mN = new android.renderscript.FieldPacker(i);
        }
    }

    public void addBoolean(boolean z) {
        if (RenderScript.shouldThunk()) {
            this.mN.addBoolean(z);
        } else {
            addI8((byte) (z ? 1 : 0));
        }
    }

    public void addF32(float f) {
        if (RenderScript.shouldThunk()) {
            this.mN.addF32(f);
        } else {
            addI32(Float.floatToRawIntBits(f));
        }
    }

    public void addF32(Float2 float2) {
        if (RenderScript.shouldThunk()) {
            this.mN.addF32(new Float2(float2.x, float2.y));
            return;
        }
        addF32(float2.x);
        addF32(float2.y);
    }

    public void addF32(Float3 float3) {
        if (RenderScript.shouldThunk()) {
            this.mN.addF32(new Float3(float3.x, float3.y, float3.z));
            return;
        }
        addF32(float3.x);
        addF32(float3.y);
        addF32(float3.z);
    }

    public void addF32(Float4 float4) {
        if (RenderScript.shouldThunk()) {
            this.mN.addF32(new Float4(float4.x, float4.y, float4.z, float4.w));
            return;
        }
        addF32(float4.x);
        addF32(float4.y);
        addF32(float4.z);
        addF32(float4.w);
    }

    public void addF64(double d) {
        if (RenderScript.shouldThunk()) {
            this.mN.addF64(d);
        } else {
            addI64(Double.doubleToRawLongBits(d));
        }
    }

    public void addF64(Double2 double2) {
        if (RenderScript.shouldThunk()) {
            this.mN.addF64(new Double2(double2.x, double2.y));
            return;
        }
        addF64(double2.x);
        addF64(double2.y);
    }

    public void addF64(Double3 double3) {
        if (RenderScript.shouldThunk()) {
            this.mN.addF64(new Double3(double3.x, double3.y, double3.z));
            return;
        }
        addF64(double3.x);
        addF64(double3.y);
        addF64(double3.z);
    }

    public void addF64(Double4 double4) {
        if (RenderScript.shouldThunk()) {
            this.mN.addF64(new Double4(double4.x, double4.y, double4.z, double4.w));
            return;
        }
        addF64(double4.x);
        addF64(double4.y);
        addF64(double4.z);
        addF64(double4.w);
    }

    public void addI16(Short2 short2) {
        if (RenderScript.shouldThunk()) {
            this.mN.addI16(new Short2(short2.x, short2.y));
            return;
        }
        addI16(short2.x);
        addI16(short2.y);
    }

    public void addI16(Short3 short3) {
        if (RenderScript.shouldThunk()) {
            this.mN.addI16(new Short3(short3.x, short3.y, short3.z));
            return;
        }
        addI16(short3.x);
        addI16(short3.y);
        addI16(short3.z);
    }

    public void addI16(Short4 short4) {
        if (RenderScript.shouldThunk()) {
            this.mN.addI16(new Short4(short4.x, short4.y, short4.z, short4.w));
            return;
        }
        addI16(short4.x);
        addI16(short4.y);
        addI16(short4.z);
        addI16(short4.w);
    }

    public void addI16(short s) {
        if (RenderScript.shouldThunk()) {
            this.mN.addI16(s);
            return;
        }
        align(2);
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) (s & 255);
        bArr = this.mData;
        i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) (s >> 8);
    }

    public void addI32(int i) {
        if (RenderScript.shouldThunk()) {
            this.mN.addI32(i);
            return;
        }
        align(4);
        byte[] bArr = this.mData;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr[i2] = (byte) (i & 255);
        bArr = this.mData;
        i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr[i2] = (byte) ((i >> 8) & 255);
        bArr = this.mData;
        i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr[i2] = (byte) ((i >> 16) & 255);
        bArr = this.mData;
        i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr[i2] = (byte) ((i >> 24) & 255);
    }

    public void addI32(Int2 int2) {
        if (RenderScript.shouldThunk()) {
            this.mN.addI32(new Int2(int2.x, int2.y));
            return;
        }
        addI32(int2.x);
        addI32(int2.y);
    }

    public void addI32(Int3 int3) {
        if (RenderScript.shouldThunk()) {
            this.mN.addI32(new Int3(int3.x, int3.y, int3.z));
            return;
        }
        addI32(int3.x);
        addI32(int3.y);
        addI32(int3.z);
    }

    public void addI32(Int4 int4) {
        if (RenderScript.shouldThunk()) {
            this.mN.addI32(new Int4(int4.x, int4.y, int4.z, int4.w));
            return;
        }
        addI32(int4.x);
        addI32(int4.y);
        addI32(int4.z);
        addI32(int4.w);
    }

    public void addI64(long j) {
        if (RenderScript.shouldThunk()) {
            this.mN.addI64(j);
            return;
        }
        align(8);
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) ((int) (j & 255));
        bArr = this.mData;
        i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) ((int) ((j >> 8) & 255));
        bArr = this.mData;
        i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) ((int) ((j >> 16) & 255));
        bArr = this.mData;
        i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) ((int) ((j >> 24) & 255));
        bArr = this.mData;
        i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) ((int) ((j >> 32) & 255));
        bArr = this.mData;
        i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) ((int) ((j >> 40) & 255));
        bArr = this.mData;
        i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) ((int) ((j >> 48) & 255));
        bArr = this.mData;
        i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) ((int) ((j >> 56) & 255));
    }

    public void addI64(Long2 long2) {
        if (RenderScript.shouldThunk()) {
            this.mN.addI64(new Long2(long2.x, long2.y));
            return;
        }
        addI64(long2.x);
        addI64(long2.y);
    }

    public void addI64(Long3 long3) {
        if (RenderScript.shouldThunk()) {
            this.mN.addI64(new Long3(long3.x, long3.y, long3.z));
            return;
        }
        addI64(long3.x);
        addI64(long3.y);
        addI64(long3.z);
    }

    public void addI64(Long4 long4) {
        if (RenderScript.shouldThunk()) {
            this.mN.addI64(new Long4(long4.x, long4.y, long4.z, long4.w));
            return;
        }
        addI64(long4.x);
        addI64(long4.y);
        addI64(long4.z);
        addI64(long4.w);
    }

    public void addI8(byte b) {
        if (RenderScript.shouldThunk()) {
            this.mN.addI8(b);
            return;
        }
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = b;
    }

    public void addI8(Byte2 byte2) {
        if (RenderScript.shouldThunk()) {
            this.mN.addI8(new Byte2(byte2.x, byte2.y));
            return;
        }
        addI8(byte2.x);
        addI8(byte2.y);
    }

    public void addI8(Byte3 byte3) {
        if (RenderScript.shouldThunk()) {
            this.mN.addI8(new Byte3(byte3.x, byte3.y, byte3.z));
            return;
        }
        addI8(byte3.x);
        addI8(byte3.y);
        addI8(byte3.z);
    }

    public void addI8(Byte4 byte4) {
        if (RenderScript.shouldThunk()) {
            this.mN.addI8(new Byte4(byte4.x, byte4.y, byte4.z, byte4.w));
            return;
        }
        addI8(byte4.x);
        addI8(byte4.y);
        addI8(byte4.z);
        addI8(byte4.w);
    }

    public void addMatrix(Matrix2f matrix2f) {
        if (RenderScript.shouldThunk()) {
            this.mN.addMatrix(new Matrix2f(matrix2f.getArray()));
            return;
        }
        for (float addF32 : matrix2f.mMat) {
            addF32(addF32);
        }
    }

    public void addMatrix(Matrix3f matrix3f) {
        if (RenderScript.shouldThunk()) {
            this.mN.addMatrix(new Matrix3f(matrix3f.getArray()));
            return;
        }
        for (float addF32 : matrix3f.mMat) {
            addF32(addF32);
        }
    }

    public void addMatrix(Matrix4f matrix4f) {
        if (RenderScript.shouldThunk()) {
            this.mN.addMatrix(new Matrix4f(matrix4f.getArray()));
            return;
        }
        for (float addF32 : matrix4f.mMat) {
            addF32(addF32);
        }
    }

    public void addObj(BaseObj baseObj) {
        if (RenderScript.shouldThunk()) {
            if (baseObj != null) {
                this.mN.addObj(baseObj.getNObj());
            } else {
                this.mN.addObj(null);
            }
        } else if (baseObj != null) {
            addI32(baseObj.getID(null));
        } else {
            addI32(0);
        }
    }

    public void addU16(int i) {
        if (RenderScript.shouldThunk()) {
            this.mN.addU16(i);
        } else if (i < 0 || i > SupportMenu.USER_MASK) {
            Log.e("rs", "FieldPacker.addU16( " + i + " )");
            throw new IllegalArgumentException("Saving value out of range for type");
        } else {
            align(2);
            byte[] bArr = this.mData;
            int i2 = this.mPos;
            this.mPos = i2 + 1;
            bArr[i2] = (byte) (i & 255);
            bArr = this.mData;
            i2 = this.mPos;
            this.mPos = i2 + 1;
            bArr[i2] = (byte) (i >> 8);
        }
    }

    public void addU16(Int2 int2) {
        if (RenderScript.shouldThunk()) {
            this.mN.addU16(new Int2(int2.x, int2.y));
            return;
        }
        addU16(int2.x);
        addU16(int2.y);
    }

    public void addU16(Int3 int3) {
        if (RenderScript.shouldThunk()) {
            this.mN.addU16(new Int3(int3.x, int3.y, int3.z));
            return;
        }
        addU16(int3.x);
        addU16(int3.y);
        addU16(int3.z);
    }

    public void addU16(Int4 int4) {
        if (RenderScript.shouldThunk()) {
            this.mN.addU16(new Int4(int4.x, int4.y, int4.z, int4.w));
            return;
        }
        addU16(int4.x);
        addU16(int4.y);
        addU16(int4.z);
        addU16(int4.w);
    }

    public void addU32(long j) {
        if (RenderScript.shouldThunk()) {
            this.mN.addU32(j);
        } else if (j < 0 || j > 4294967295L) {
            Log.e("rs", "FieldPacker.addU32( " + j + " )");
            throw new IllegalArgumentException("Saving value out of range for type");
        } else {
            align(4);
            byte[] bArr = this.mData;
            int i = this.mPos;
            this.mPos = i + 1;
            bArr[i] = (byte) ((int) (j & 255));
            bArr = this.mData;
            i = this.mPos;
            this.mPos = i + 1;
            bArr[i] = (byte) ((int) ((j >> 8) & 255));
            bArr = this.mData;
            i = this.mPos;
            this.mPos = i + 1;
            bArr[i] = (byte) ((int) ((j >> 16) & 255));
            bArr = this.mData;
            i = this.mPos;
            this.mPos = i + 1;
            bArr[i] = (byte) ((int) ((j >> 24) & 255));
        }
    }

    public void addU32(Long2 long2) {
        if (RenderScript.shouldThunk()) {
            this.mN.addU32(new Long2(long2.x, long2.y));
            return;
        }
        addU32(long2.x);
        addU32(long2.y);
    }

    public void addU32(Long3 long3) {
        if (RenderScript.shouldThunk()) {
            this.mN.addU32(new Long3(long3.x, long3.y, long3.z));
            return;
        }
        addU32(long3.x);
        addU32(long3.y);
        addU32(long3.z);
    }

    public void addU32(Long4 long4) {
        if (RenderScript.shouldThunk()) {
            this.mN.addU32(new Long4(long4.x, long4.y, long4.z, long4.w));
            return;
        }
        addU32(long4.x);
        addU32(long4.y);
        addU32(long4.z);
        addU32(long4.w);
    }

    public void addU64(long j) {
        if (RenderScript.shouldThunk()) {
            this.mN.addU64(j);
        } else if (j < 0) {
            Log.e("rs", "FieldPacker.addU64( " + j + " )");
            throw new IllegalArgumentException("Saving value out of range for type");
        } else {
            align(8);
            byte[] bArr = this.mData;
            int i = this.mPos;
            this.mPos = i + 1;
            bArr[i] = (byte) ((int) (j & 255));
            bArr = this.mData;
            i = this.mPos;
            this.mPos = i + 1;
            bArr[i] = (byte) ((int) ((j >> 8) & 255));
            bArr = this.mData;
            i = this.mPos;
            this.mPos = i + 1;
            bArr[i] = (byte) ((int) ((j >> 16) & 255));
            bArr = this.mData;
            i = this.mPos;
            this.mPos = i + 1;
            bArr[i] = (byte) ((int) ((j >> 24) & 255));
            bArr = this.mData;
            i = this.mPos;
            this.mPos = i + 1;
            bArr[i] = (byte) ((int) ((j >> 32) & 255));
            bArr = this.mData;
            i = this.mPos;
            this.mPos = i + 1;
            bArr[i] = (byte) ((int) ((j >> 40) & 255));
            bArr = this.mData;
            i = this.mPos;
            this.mPos = i + 1;
            bArr[i] = (byte) ((int) ((j >> 48) & 255));
            bArr = this.mData;
            i = this.mPos;
            this.mPos = i + 1;
            bArr[i] = (byte) ((int) ((j >> 56) & 255));
        }
    }

    public void addU64(Long2 long2) {
        if (RenderScript.shouldThunk()) {
            this.mN.addU64(new Long2(long2.x, long2.y));
            return;
        }
        addU64(long2.x);
        addU64(long2.y);
    }

    public void addU64(Long3 long3) {
        if (RenderScript.shouldThunk()) {
            this.mN.addU64(new Long3(long3.x, long3.y, long3.z));
            return;
        }
        addU64(long3.x);
        addU64(long3.y);
        addU64(long3.z);
    }

    public void addU64(Long4 long4) {
        if (RenderScript.shouldThunk()) {
            this.mN.addU64(new Long4(long4.x, long4.y, long4.z, long4.w));
            return;
        }
        addU64(long4.x);
        addU64(long4.y);
        addU64(long4.z);
        addU64(long4.w);
    }

    public void addU8(Short2 short2) {
        if (RenderScript.shouldThunk()) {
            this.mN.addU8(new Short2(short2.x, short2.y));
            return;
        }
        addU8(short2.x);
        addU8(short2.y);
    }

    public void addU8(Short3 short3) {
        if (RenderScript.shouldThunk()) {
            this.mN.addU8(new Short3(short3.x, short3.y, short3.z));
            return;
        }
        addU8(short3.x);
        addU8(short3.y);
        addU8(short3.z);
    }

    public void addU8(Short4 short4) {
        if (RenderScript.shouldThunk()) {
            this.mN.addU8(new Short4(short4.x, short4.y, short4.z, short4.w));
            return;
        }
        addU8(short4.x);
        addU8(short4.y);
        addU8(short4.z);
        addU8(short4.w);
    }

    public void addU8(short s) {
        if (RenderScript.shouldThunk()) {
            this.mN.addU8(s);
        } else if (s < (short) 0 || s > (short) 255) {
            throw new IllegalArgumentException("Saving value out of range for type");
        } else {
            byte[] bArr = this.mData;
            int i = this.mPos;
            this.mPos = i + 1;
            bArr[i] = (byte) s;
        }
    }

    public void align(int i) {
        if (RenderScript.shouldThunk()) {
            this.mN.align(i);
        } else if (i <= 0 || ((i - 1) & i) != 0) {
            throw new RSIllegalArgumentException("argument must be a non-negative non-zero power of 2: " + i);
        } else {
            while ((this.mPos & (i - 1)) != 0) {
                byte[] bArr = this.mData;
                int i2 = this.mPos;
                this.mPos = i2 + 1;
                bArr[i2] = (byte) 0;
            }
        }
    }

    public final byte[] getData() {
        return RenderScript.shouldThunk() ? this.mN.getData() : this.mData;
    }

    public void reset() {
        if (RenderScript.shouldThunk()) {
            this.mN.reset();
        } else {
            this.mPos = 0;
        }
    }

    public void reset(int i) {
        if (RenderScript.shouldThunk()) {
            this.mN.reset(i);
        } else if (i < 0 || i >= this.mLen) {
            throw new RSIllegalArgumentException("out of range argument: " + i);
        } else {
            this.mPos = i;
        }
    }

    public void skip(int i) {
        if (RenderScript.shouldThunk()) {
            this.mN.skip(i);
            return;
        }
        int i2 = this.mPos + i;
        if (i2 < 0 || i2 > this.mLen) {
            throw new RSIllegalArgumentException("out of range argument: " + i);
        }
        this.mPos = i2;
    }
}
