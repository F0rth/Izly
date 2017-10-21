package android.support.v8.renderscript;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.SystemProperties;
import android.util.Log;
import java.io.File;
import java.lang.reflect.Method;

public class RenderScript {
    private static final String CACHE_PATH = "com.android.renderscript.cache";
    static final boolean DEBUG = false;
    static final boolean LOG_ENABLED = false;
    static final String LOG_TAG = "RenderScript_jni";
    static boolean isNative = false;
    static Object lock = new Object();
    static String mCachePath;
    static Method registerNativeAllocation;
    static Method registerNativeFree;
    static boolean sInitialized;
    static Object sRuntime;
    static boolean sUseGCHooks;
    private static int thunk = 0;
    private Context mApplicationContext;
    int mContext;
    int mDev;
    Element mElement_ALLOCATION;
    Element mElement_A_8;
    Element mElement_BOOLEAN;
    Element mElement_CHAR_2;
    Element mElement_CHAR_3;
    Element mElement_CHAR_4;
    Element mElement_DOUBLE_2;
    Element mElement_DOUBLE_3;
    Element mElement_DOUBLE_4;
    Element mElement_ELEMENT;
    Element mElement_F32;
    Element mElement_F64;
    Element mElement_FLOAT_2;
    Element mElement_FLOAT_3;
    Element mElement_FLOAT_4;
    Element mElement_I16;
    Element mElement_I32;
    Element mElement_I64;
    Element mElement_I8;
    Element mElement_INT_2;
    Element mElement_INT_3;
    Element mElement_INT_4;
    Element mElement_LONG_2;
    Element mElement_LONG_3;
    Element mElement_LONG_4;
    Element mElement_MATRIX_2X2;
    Element mElement_MATRIX_3X3;
    Element mElement_MATRIX_4X4;
    Element mElement_RGBA_4444;
    Element mElement_RGBA_5551;
    Element mElement_RGBA_8888;
    Element mElement_RGB_565;
    Element mElement_RGB_888;
    Element mElement_SAMPLER;
    Element mElement_SCRIPT;
    Element mElement_SHORT_2;
    Element mElement_SHORT_3;
    Element mElement_SHORT_4;
    Element mElement_TYPE;
    Element mElement_U16;
    Element mElement_U32;
    Element mElement_U64;
    Element mElement_U8;
    Element mElement_UCHAR_2;
    Element mElement_UCHAR_3;
    Element mElement_UCHAR_4;
    Element mElement_UINT_2;
    Element mElement_UINT_3;
    Element mElement_UINT_4;
    Element mElement_ULONG_2;
    Element mElement_ULONG_3;
    Element mElement_ULONG_4;
    Element mElement_USHORT_2;
    Element mElement_USHORT_3;
    Element mElement_USHORT_4;
    RSErrorHandler mErrorCallback = null;
    RSMessageHandler mMessageCallback = null;
    MessageThread mMessageThread;
    Sampler mSampler_CLAMP_LINEAR;
    Sampler mSampler_CLAMP_LINEAR_MIP_LINEAR;
    Sampler mSampler_CLAMP_NEAREST;
    Sampler mSampler_MIRRORED_REPEAT_LINEAR;
    Sampler mSampler_MIRRORED_REPEAT_LINEAR_MIP_LINEAR;
    Sampler mSampler_MIRRORED_REPEAT_NEAREST;
    Sampler mSampler_WRAP_LINEAR;
    Sampler mSampler_WRAP_LINEAR_MIP_LINEAR;
    Sampler mSampler_WRAP_NEAREST;

    public enum ContextType {
        NORMAL(0),
        DEBUG(1),
        PROFILE(2);
        
        int mID;

        private ContextType(int i) {
            this.mID = i;
        }
    }

    static class MessageThread extends Thread {
        static final int RS_ERROR_FATAL_UNKNOWN = 4096;
        static final int RS_MESSAGE_TO_CLIENT_ERROR = 3;
        static final int RS_MESSAGE_TO_CLIENT_EXCEPTION = 1;
        static final int RS_MESSAGE_TO_CLIENT_NONE = 0;
        static final int RS_MESSAGE_TO_CLIENT_RESIZE = 2;
        static final int RS_MESSAGE_TO_CLIENT_USER = 4;
        int[] mAuxData = new int[2];
        RenderScript mRS;
        boolean mRun = true;

        MessageThread(RenderScript renderScript) {
            super("RSMessageThread");
            this.mRS = renderScript;
        }

        public void run() {
            int[] iArr = new int[16];
            this.mRS.nContextInitToClient(this.mRS.mContext);
            while (this.mRun) {
                iArr[0] = 0;
                int nContextPeekMessage = this.mRS.nContextPeekMessage(this.mRS.mContext, this.mAuxData);
                int i = this.mAuxData[1];
                int i2 = this.mAuxData[0];
                if (nContextPeekMessage == 4) {
                    if ((i >> 2) >= iArr.length) {
                        iArr = new int[((i + 3) >> 2)];
                    }
                    if (this.mRS.nContextGetUserMessage(this.mRS.mContext, iArr) != 4) {
                        throw new RSDriverException("Error processing message from RenderScript.");
                    } else if (this.mRS.mMessageCallback != null) {
                        this.mRS.mMessageCallback.mData = iArr;
                        this.mRS.mMessageCallback.mID = i2;
                        this.mRS.mMessageCallback.mLength = i;
                        this.mRS.mMessageCallback.run();
                    } else {
                        throw new RSInvalidStateException("Received a message from the script with no message handler installed.");
                    }
                } else if (nContextPeekMessage == 3) {
                    String nContextGetErrorMessage = this.mRS.nContextGetErrorMessage(this.mRS.mContext);
                    if (i2 >= 4096) {
                        throw new RSRuntimeException("Fatal error " + i2 + ", details: " + nContextGetErrorMessage);
                    } else if (this.mRS.mErrorCallback != null) {
                        this.mRS.mErrorCallback.mErrorMessage = nContextGetErrorMessage;
                        this.mRS.mErrorCallback.mErrorNum = i2;
                        this.mRS.mErrorCallback.run();
                    } else {
                        Log.e(RenderScript.LOG_TAG, "non fatal RS error, " + nContextGetErrorMessage);
                    }
                } else {
                    try {
                        sleep(1, 0);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    public enum Priority {
        LOW(15),
        NORMAL(-4);
        
        int mID;

        private Priority(int i) {
            this.mID = i;
        }
    }

    public static class RSErrorHandler implements Runnable {
        protected String mErrorMessage;
        protected int mErrorNum;

        public void run() {
        }
    }

    public static class RSMessageHandler implements Runnable {
        protected int[] mData;
        protected int mID;
        protected int mLength;

        public void run() {
        }
    }

    RenderScript(Context context) {
        if (context != null) {
            this.mApplicationContext = context.getApplicationContext();
        }
    }

    public static RenderScript create(Context context) {
        return create(context, ContextType.NORMAL);
    }

    public static RenderScript create(Context context, int i) {
        return create(context, i, ContextType.NORMAL);
    }

    public static RenderScript create(Context context, int i, ContextType contextType) {
        RenderScript renderScript = new RenderScript(context);
        if (shouldThunk()) {
            Log.v(LOG_TAG, "RS native mode");
            return RenderScriptThunker.create(context, i);
        }
        synchronized (lock) {
            if (!sInitialized) {
                try {
                    Class cls = Class.forName("dalvik.system.VMRuntime");
                    sRuntime = cls.getDeclaredMethod("getRuntime", new Class[0]).invoke(null, new Object[0]);
                    registerNativeAllocation = cls.getDeclaredMethod("registerNativeAllocation", new Class[]{Integer.TYPE});
                    registerNativeFree = cls.getDeclaredMethod("registerNativeFree", new Class[]{Integer.TYPE});
                    sUseGCHooks = true;
                } catch (UnsatisfiedLinkError e) {
                    Log.e(LOG_TAG, "Error loading RS jni library: " + e);
                    throw new RSRuntimeException("Error loading RS jni library: " + e);
                } catch (Exception e2) {
                    Log.e(LOG_TAG, "No GC methods");
                    sUseGCHooks = false;
                }
                System.loadLibrary("RSSupport");
                System.loadLibrary("rsjni");
                sInitialized = true;
            }
        }
        Log.v(LOG_TAG, "RS compat mode");
        renderScript.mDev = renderScript.nDeviceCreate();
        renderScript.mContext = renderScript.nContextCreate(renderScript.mDev, 0, i, contextType.mID);
        if (renderScript.mContext == 0) {
            throw new RSDriverException("Failed to create RS context.");
        }
        renderScript.mMessageThread = new MessageThread(renderScript);
        renderScript.mMessageThread.start();
        return renderScript;
    }

    public static RenderScript create(Context context, ContextType contextType) {
        return create(context, context.getApplicationInfo().targetSdkVersion, contextType);
    }

    public static void setupDiskCache(File file) {
        File file2 = new File(file, CACHE_PATH);
        mCachePath = file2.getAbsolutePath();
        file2.mkdirs();
    }

    static boolean shouldThunk() {
        if (thunk == 0) {
            if (VERSION.SDK_INT < 18 || SystemProperties.getInt("debug.rs.forcecompat", 0) != 0) {
                thunk = -1;
            } else {
                thunk = 1;
            }
        }
        return thunk == 1;
    }

    public void contextDump() {
        validate();
        nContextDump(0);
    }

    public void destroy() {
        validate();
        nContextDeinitToClient(this.mContext);
        this.mMessageThread.mRun = false;
        try {
            this.mMessageThread.join();
        } catch (InterruptedException e) {
        }
        nContextDestroy();
        this.mContext = 0;
        nDeviceDestroy(this.mDev);
        this.mDev = 0;
    }

    public void finish() {
        nContextFinish();
    }

    public final Context getApplicationContext() {
        return this.mApplicationContext;
    }

    public RSErrorHandler getErrorHandler() {
        return this.mErrorCallback;
    }

    public RSMessageHandler getMessageHandler() {
        return this.mMessageCallback;
    }

    boolean isAlive() {
        return this.mContext != 0;
    }

    void nAllocationCopyFromBitmap(int i, Bitmap bitmap) {
        synchronized (this) {
            validate();
            rsnAllocationCopyFromBitmap(this.mContext, i, bitmap);
        }
    }

    void nAllocationCopyToBitmap(int i, Bitmap bitmap) {
        synchronized (this) {
            validate();
            rsnAllocationCopyToBitmap(this.mContext, i, bitmap);
        }
    }

    int nAllocationCreateBitmapBackedAllocation(int i, int i2, Bitmap bitmap, int i3) {
        int rsnAllocationCreateBitmapBackedAllocation;
        synchronized (this) {
            validate();
            rsnAllocationCreateBitmapBackedAllocation = rsnAllocationCreateBitmapBackedAllocation(this.mContext, i, i2, bitmap, i3);
        }
        return rsnAllocationCreateBitmapBackedAllocation;
    }

    int nAllocationCreateBitmapRef(int i, Bitmap bitmap) {
        int rsnAllocationCreateBitmapRef;
        synchronized (this) {
            validate();
            rsnAllocationCreateBitmapRef = rsnAllocationCreateBitmapRef(this.mContext, i, bitmap);
        }
        return rsnAllocationCreateBitmapRef;
    }

    int nAllocationCreateFromAssetStream(int i, int i2, int i3) {
        int rsnAllocationCreateFromAssetStream;
        synchronized (this) {
            validate();
            rsnAllocationCreateFromAssetStream = rsnAllocationCreateFromAssetStream(this.mContext, i, i2, i3);
        }
        return rsnAllocationCreateFromAssetStream;
    }

    int nAllocationCreateFromBitmap(int i, int i2, Bitmap bitmap, int i3) {
        int rsnAllocationCreateFromBitmap;
        synchronized (this) {
            validate();
            rsnAllocationCreateFromBitmap = rsnAllocationCreateFromBitmap(this.mContext, i, i2, bitmap, i3);
        }
        return rsnAllocationCreateFromBitmap;
    }

    int nAllocationCreateTyped(int i, int i2, int i3, int i4) {
        int rsnAllocationCreateTyped;
        synchronized (this) {
            validate();
            rsnAllocationCreateTyped = rsnAllocationCreateTyped(this.mContext, i, i2, i3, i4);
        }
        return rsnAllocationCreateTyped;
    }

    int nAllocationCubeCreateFromBitmap(int i, int i2, Bitmap bitmap, int i3) {
        int rsnAllocationCubeCreateFromBitmap;
        synchronized (this) {
            validate();
            rsnAllocationCubeCreateFromBitmap = rsnAllocationCubeCreateFromBitmap(this.mContext, i, i2, bitmap, i3);
        }
        return rsnAllocationCubeCreateFromBitmap;
    }

    void nAllocationData1D(int i, int i2, int i3, int i4, byte[] bArr, int i5) {
        synchronized (this) {
            validate();
            rsnAllocationData1D(this.mContext, i, i2, i3, i4, bArr, i5);
        }
    }

    void nAllocationData1D(int i, int i2, int i3, int i4, float[] fArr, int i5) {
        synchronized (this) {
            validate();
            rsnAllocationData1D(this.mContext, i, i2, i3, i4, fArr, i5);
        }
    }

    void nAllocationData1D(int i, int i2, int i3, int i4, int[] iArr, int i5) {
        synchronized (this) {
            validate();
            rsnAllocationData1D(this.mContext, i, i2, i3, i4, iArr, i5);
        }
    }

    void nAllocationData1D(int i, int i2, int i3, int i4, short[] sArr, int i5) {
        synchronized (this) {
            validate();
            rsnAllocationData1D(this.mContext, i, i2, i3, i4, sArr, i5);
        }
    }

    void nAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
        synchronized (this) {
            validate();
            rsnAllocationData2D(this.mContext, i, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12);
        }
    }

    void nAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, byte[] bArr, int i8) {
        synchronized (this) {
            validate();
            rsnAllocationData2D(this.mContext, i, i2, i3, i4, i5, i6, i7, bArr, i8);
        }
    }

    void nAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, float[] fArr, int i8) {
        synchronized (this) {
            validate();
            rsnAllocationData2D(this.mContext, i, i2, i3, i4, i5, i6, i7, fArr, i8);
        }
    }

    void nAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int[] iArr, int i8) {
        synchronized (this) {
            validate();
            rsnAllocationData2D(this.mContext, i, i2, i3, i4, i5, i6, i7, iArr, i8);
        }
    }

    void nAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, short[] sArr, int i8) {
        synchronized (this) {
            validate();
            rsnAllocationData2D(this.mContext, i, i2, i3, i4, i5, i6, i7, sArr, i8);
        }
    }

    void nAllocationData2D(int i, int i2, int i3, int i4, int i5, Bitmap bitmap) {
        synchronized (this) {
            validate();
            rsnAllocationData2D(this.mContext, i, i2, i3, i4, i5, bitmap);
        }
    }

    void nAllocationData3D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13) {
        synchronized (this) {
            validate();
            rsnAllocationData3D(this.mContext, i, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13);
        }
    }

    void nAllocationData3D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, byte[] bArr, int i9) {
        synchronized (this) {
            validate();
            rsnAllocationData3D(this.mContext, i, i2, i3, i4, i5, i6, i7, i8, bArr, i9);
        }
    }

    void nAllocationData3D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, float[] fArr, int i9) {
        synchronized (this) {
            validate();
            rsnAllocationData3D(this.mContext, i, i2, i3, i4, i5, i6, i7, i8, fArr, i9);
        }
    }

    void nAllocationData3D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int[] iArr, int i9) {
        synchronized (this) {
            validate();
            rsnAllocationData3D(this.mContext, i, i2, i3, i4, i5, i6, i7, i8, iArr, i9);
        }
    }

    void nAllocationData3D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, short[] sArr, int i9) {
        synchronized (this) {
            validate();
            rsnAllocationData3D(this.mContext, i, i2, i3, i4, i5, i6, i7, i8, sArr, i9);
        }
    }

    void nAllocationElementData1D(int i, int i2, int i3, int i4, byte[] bArr, int i5) {
        synchronized (this) {
            validate();
            rsnAllocationElementData1D(this.mContext, i, i2, i3, i4, bArr, i5);
        }
    }

    void nAllocationGenerateMipmaps(int i) {
        synchronized (this) {
            validate();
            rsnAllocationGenerateMipmaps(this.mContext, i);
        }
    }

    int nAllocationGetType(int i) {
        int rsnAllocationGetType;
        synchronized (this) {
            validate();
            rsnAllocationGetType = rsnAllocationGetType(this.mContext, i);
        }
        return rsnAllocationGetType;
    }

    void nAllocationIoReceive(int i) {
        synchronized (this) {
            validate();
            rsnAllocationIoReceive(this.mContext, i);
        }
    }

    void nAllocationIoSend(int i) {
        synchronized (this) {
            validate();
            rsnAllocationIoSend(this.mContext, i);
        }
    }

    void nAllocationRead(int i, byte[] bArr) {
        synchronized (this) {
            validate();
            rsnAllocationRead(this.mContext, i, bArr);
        }
    }

    void nAllocationRead(int i, float[] fArr) {
        synchronized (this) {
            validate();
            rsnAllocationRead(this.mContext, i, fArr);
        }
    }

    void nAllocationRead(int i, int[] iArr) {
        synchronized (this) {
            validate();
            rsnAllocationRead(this.mContext, i, iArr);
        }
    }

    void nAllocationRead(int i, short[] sArr) {
        synchronized (this) {
            validate();
            rsnAllocationRead(this.mContext, i, sArr);
        }
    }

    void nAllocationResize1D(int i, int i2) {
        synchronized (this) {
            validate();
            rsnAllocationResize1D(this.mContext, i, i2);
        }
    }

    void nAllocationResize2D(int i, int i2, int i3) {
        synchronized (this) {
            validate();
            rsnAllocationResize2D(this.mContext, i, i2, i3);
        }
    }

    void nAllocationSyncAll(int i, int i2) {
        synchronized (this) {
            validate();
            rsnAllocationSyncAll(this.mContext, i, i2);
        }
    }

    int nContextCreate(int i, int i2, int i3, int i4) {
        int rsnContextCreate;
        synchronized (this) {
            rsnContextCreate = rsnContextCreate(i, i2, i3, i4);
        }
        return rsnContextCreate;
    }

    native void nContextDeinitToClient(int i);

    void nContextDestroy() {
        synchronized (this) {
            validate();
            rsnContextDestroy(this.mContext);
        }
    }

    void nContextDump(int i) {
        synchronized (this) {
            validate();
            rsnContextDump(this.mContext, i);
        }
    }

    void nContextFinish() {
        synchronized (this) {
            validate();
            rsnContextFinish(this.mContext);
        }
    }

    native String nContextGetErrorMessage(int i);

    native int nContextGetUserMessage(int i, int[] iArr);

    native void nContextInitToClient(int i);

    native int nContextPeekMessage(int i, int[] iArr);

    void nContextSendMessage(int i, int[] iArr) {
        synchronized (this) {
            validate();
            rsnContextSendMessage(this.mContext, i, iArr);
        }
    }

    void nContextSetPriority(int i) {
        synchronized (this) {
            validate();
            rsnContextSetPriority(this.mContext, i);
        }
    }

    native int nDeviceCreate();

    native void nDeviceDestroy(int i);

    native void nDeviceSetConfig(int i, int i2, int i3);

    int nElementCreate(int i, int i2, boolean z, int i3) {
        int rsnElementCreate;
        synchronized (this) {
            validate();
            rsnElementCreate = rsnElementCreate(this.mContext, i, i2, z, i3);
        }
        return rsnElementCreate;
    }

    int nElementCreate2(int[] iArr, String[] strArr, int[] iArr2) {
        int rsnElementCreate2;
        synchronized (this) {
            validate();
            rsnElementCreate2 = rsnElementCreate2(this.mContext, iArr, strArr, iArr2);
        }
        return rsnElementCreate2;
    }

    void nElementGetNativeData(int i, int[] iArr) {
        synchronized (this) {
            validate();
            rsnElementGetNativeData(this.mContext, i, iArr);
        }
    }

    void nElementGetSubElements(int i, int[] iArr, String[] strArr, int[] iArr2) {
        synchronized (this) {
            validate();
            rsnElementGetSubElements(this.mContext, i, iArr, strArr, iArr2);
        }
    }

    void nObjDestroy(int i) {
        synchronized (this) {
            if (this.mContext != 0) {
                rsnObjDestroy(this.mContext, i);
            }
        }
    }

    int nSamplerCreate(int i, int i2, int i3, int i4, int i5, float f) {
        int rsnSamplerCreate;
        synchronized (this) {
            validate();
            rsnSamplerCreate = rsnSamplerCreate(this.mContext, i, i2, i3, i4, i5, f);
        }
        return rsnSamplerCreate;
    }

    void nScriptBindAllocation(int i, int i2, int i3) {
        synchronized (this) {
            validate();
            rsnScriptBindAllocation(this.mContext, i, i2, i3);
        }
    }

    int nScriptCCreate(String str, String str2, byte[] bArr, int i) {
        int rsnScriptCCreate;
        synchronized (this) {
            validate();
            rsnScriptCCreate = rsnScriptCCreate(this.mContext, str, str2, bArr, i);
        }
        return rsnScriptCCreate;
    }

    int nScriptFieldIDCreate(int i, int i2) {
        int rsnScriptFieldIDCreate;
        synchronized (this) {
            validate();
            rsnScriptFieldIDCreate = rsnScriptFieldIDCreate(this.mContext, i, i2);
        }
        return rsnScriptFieldIDCreate;
    }

    void nScriptForEach(int i, int i2, int i3, int i4, byte[] bArr) {
        synchronized (this) {
            validate();
            if (bArr == null) {
                rsnScriptForEach(this.mContext, i, i2, i3, i4);
            } else {
                rsnScriptForEach(this.mContext, i, i2, i3, i4, bArr);
            }
        }
    }

    void nScriptForEachClipped(int i, int i2, int i3, int i4, byte[] bArr, int i5, int i6, int i7, int i8, int i9, int i10) {
        synchronized (this) {
            validate();
            if (bArr == null) {
                rsnScriptForEachClipped(this.mContext, i, i2, i3, i4, i5, i6, i7, i8, i9, i10);
            } else {
                rsnScriptForEachClipped(this.mContext, i, i2, i3, i4, bArr, i5, i6, i7, i8, i9, i10);
            }
        }
    }

    int nScriptGroupCreate(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5) {
        int rsnScriptGroupCreate;
        synchronized (this) {
            validate();
            rsnScriptGroupCreate = rsnScriptGroupCreate(this.mContext, iArr, iArr2, iArr3, iArr4, iArr5);
        }
        return rsnScriptGroupCreate;
    }

    void nScriptGroupExecute(int i) {
        synchronized (this) {
            validate();
            rsnScriptGroupExecute(this.mContext, i);
        }
    }

    void nScriptGroupSetInput(int i, int i2, int i3) {
        synchronized (this) {
            validate();
            rsnScriptGroupSetInput(this.mContext, i, i2, i3);
        }
    }

    void nScriptGroupSetOutput(int i, int i2, int i3) {
        synchronized (this) {
            validate();
            rsnScriptGroupSetOutput(this.mContext, i, i2, i3);
        }
    }

    int nScriptIntrinsicCreate(int i, int i2) {
        int rsnScriptIntrinsicCreate;
        synchronized (this) {
            validate();
            rsnScriptIntrinsicCreate = rsnScriptIntrinsicCreate(this.mContext, i, i2);
        }
        return rsnScriptIntrinsicCreate;
    }

    void nScriptInvoke(int i, int i2) {
        synchronized (this) {
            validate();
            rsnScriptInvoke(this.mContext, i, i2);
        }
    }

    void nScriptInvokeV(int i, int i2, byte[] bArr) {
        synchronized (this) {
            validate();
            rsnScriptInvokeV(this.mContext, i, i2, bArr);
        }
    }

    int nScriptKernelIDCreate(int i, int i2, int i3) {
        int rsnScriptKernelIDCreate;
        synchronized (this) {
            validate();
            rsnScriptKernelIDCreate = rsnScriptKernelIDCreate(this.mContext, i, i2, i3);
        }
        return rsnScriptKernelIDCreate;
    }

    void nScriptSetTimeZone(int i, byte[] bArr) {
        synchronized (this) {
            validate();
            rsnScriptSetTimeZone(this.mContext, i, bArr);
        }
    }

    void nScriptSetVarD(int i, int i2, double d) {
        synchronized (this) {
            validate();
            rsnScriptSetVarD(this.mContext, i, i2, d);
        }
    }

    void nScriptSetVarF(int i, int i2, float f) {
        synchronized (this) {
            validate();
            rsnScriptSetVarF(this.mContext, i, i2, f);
        }
    }

    void nScriptSetVarI(int i, int i2, int i3) {
        synchronized (this) {
            validate();
            rsnScriptSetVarI(this.mContext, i, i2, i3);
        }
    }

    void nScriptSetVarJ(int i, int i2, long j) {
        synchronized (this) {
            validate();
            rsnScriptSetVarJ(this.mContext, i, i2, j);
        }
    }

    void nScriptSetVarObj(int i, int i2, int i3) {
        synchronized (this) {
            validate();
            rsnScriptSetVarObj(this.mContext, i, i2, i3);
        }
    }

    void nScriptSetVarV(int i, int i2, byte[] bArr) {
        synchronized (this) {
            validate();
            rsnScriptSetVarV(this.mContext, i, i2, bArr);
        }
    }

    void nScriptSetVarVE(int i, int i2, byte[] bArr, int i3, int[] iArr) {
        synchronized (this) {
            validate();
            rsnScriptSetVarVE(this.mContext, i, i2, bArr, i3, iArr);
        }
    }

    int nTypeCreate(int i, int i2, int i3, int i4, boolean z, boolean z2, int i5) {
        int rsnTypeCreate;
        synchronized (this) {
            validate();
            rsnTypeCreate = rsnTypeCreate(this.mContext, i, i2, i3, i4, z, z2, i5);
        }
        return rsnTypeCreate;
    }

    void nTypeGetNativeData(int i, int[] iArr) {
        synchronized (this) {
            validate();
            rsnTypeGetNativeData(this.mContext, i, iArr);
        }
    }

    native void rsnAllocationCopyFromBitmap(int i, int i2, Bitmap bitmap);

    native void rsnAllocationCopyToBitmap(int i, int i2, Bitmap bitmap);

    native int rsnAllocationCreateBitmapBackedAllocation(int i, int i2, int i3, Bitmap bitmap, int i4);

    native int rsnAllocationCreateBitmapRef(int i, int i2, Bitmap bitmap);

    native int rsnAllocationCreateFromAssetStream(int i, int i2, int i3, int i4);

    native int rsnAllocationCreateFromBitmap(int i, int i2, int i3, Bitmap bitmap, int i4);

    native int rsnAllocationCreateTyped(int i, int i2, int i3, int i4, int i5);

    native int rsnAllocationCubeCreateFromBitmap(int i, int i2, int i3, Bitmap bitmap, int i4);

    native void rsnAllocationData1D(int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6);

    native void rsnAllocationData1D(int i, int i2, int i3, int i4, int i5, float[] fArr, int i6);

    native void rsnAllocationData1D(int i, int i2, int i3, int i4, int i5, int[] iArr, int i6);

    native void rsnAllocationData1D(int i, int i2, int i3, int i4, int i5, short[] sArr, int i6);

    native void rsnAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13);

    native void rsnAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, byte[] bArr, int i9);

    native void rsnAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, float[] fArr, int i9);

    native void rsnAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int[] iArr, int i9);

    native void rsnAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, short[] sArr, int i9);

    native void rsnAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, Bitmap bitmap);

    native void rsnAllocationData3D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14);

    native void rsnAllocationData3D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, byte[] bArr, int i10);

    native void rsnAllocationData3D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float[] fArr, int i10);

    native void rsnAllocationData3D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int[] iArr, int i10);

    native void rsnAllocationData3D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, short[] sArr, int i10);

    native void rsnAllocationElementData1D(int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6);

    native void rsnAllocationGenerateMipmaps(int i, int i2);

    native int rsnAllocationGetType(int i, int i2);

    native void rsnAllocationIoReceive(int i, int i2);

    native void rsnAllocationIoSend(int i, int i2);

    native void rsnAllocationRead(int i, int i2, byte[] bArr);

    native void rsnAllocationRead(int i, int i2, float[] fArr);

    native void rsnAllocationRead(int i, int i2, int[] iArr);

    native void rsnAllocationRead(int i, int i2, short[] sArr);

    native void rsnAllocationResize1D(int i, int i2, int i3);

    native void rsnAllocationResize2D(int i, int i2, int i3, int i4);

    native void rsnAllocationSyncAll(int i, int i2, int i3);

    native int rsnContextCreate(int i, int i2, int i3, int i4);

    native void rsnContextDestroy(int i);

    native void rsnContextDump(int i, int i2);

    native void rsnContextFinish(int i);

    native void rsnContextSendMessage(int i, int i2, int[] iArr);

    native void rsnContextSetPriority(int i, int i2);

    native int rsnElementCreate(int i, int i2, int i3, boolean z, int i4);

    native int rsnElementCreate2(int i, int[] iArr, String[] strArr, int[] iArr2);

    native void rsnElementGetNativeData(int i, int i2, int[] iArr);

    native void rsnElementGetSubElements(int i, int i2, int[] iArr, String[] strArr, int[] iArr2);

    native void rsnObjDestroy(int i, int i2);

    native int rsnSamplerCreate(int i, int i2, int i3, int i4, int i5, int i6, float f);

    native void rsnScriptBindAllocation(int i, int i2, int i3, int i4);

    native int rsnScriptCCreate(int i, String str, String str2, byte[] bArr, int i2);

    native int rsnScriptFieldIDCreate(int i, int i2, int i3);

    native void rsnScriptForEach(int i, int i2, int i3, int i4, int i5);

    native void rsnScriptForEach(int i, int i2, int i3, int i4, int i5, byte[] bArr);

    native void rsnScriptForEachClipped(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11);

    native void rsnScriptForEachClipped(int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6, int i7, int i8, int i9, int i10, int i11);

    native int rsnScriptGroupCreate(int i, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5);

    native void rsnScriptGroupExecute(int i, int i2);

    native void rsnScriptGroupSetInput(int i, int i2, int i3, int i4);

    native void rsnScriptGroupSetOutput(int i, int i2, int i3, int i4);

    native int rsnScriptIntrinsicCreate(int i, int i2, int i3);

    native void rsnScriptInvoke(int i, int i2, int i3);

    native void rsnScriptInvokeV(int i, int i2, int i3, byte[] bArr);

    native int rsnScriptKernelIDCreate(int i, int i2, int i3, int i4);

    native void rsnScriptSetTimeZone(int i, int i2, byte[] bArr);

    native void rsnScriptSetVarD(int i, int i2, int i3, double d);

    native void rsnScriptSetVarF(int i, int i2, int i3, float f);

    native void rsnScriptSetVarI(int i, int i2, int i3, int i4);

    native void rsnScriptSetVarJ(int i, int i2, int i3, long j);

    native void rsnScriptSetVarObj(int i, int i2, int i3, int i4);

    native void rsnScriptSetVarV(int i, int i2, int i3, byte[] bArr);

    native void rsnScriptSetVarVE(int i, int i2, int i3, byte[] bArr, int i4, int[] iArr);

    native int rsnTypeCreate(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2, int i6);

    native void rsnTypeGetNativeData(int i, int i2, int[] iArr);

    int safeID(BaseObj baseObj) {
        return baseObj != null ? baseObj.getID(this) : 0;
    }

    public void sendMessage(int i, int[] iArr) {
        nContextSendMessage(i, iArr);
    }

    public void setErrorHandler(RSErrorHandler rSErrorHandler) {
        this.mErrorCallback = rSErrorHandler;
        if (isNative) {
            RenderScriptThunker renderScriptThunker = (RenderScriptThunker) this;
            renderScriptThunker.mN.setErrorHandler(new android.renderscript.RenderScript.RSErrorHandler() {
                public void run() {
                    RenderScript.this.mErrorCallback.mErrorMessage = this.mErrorMessage;
                    RenderScript.this.mErrorCallback.mErrorNum = this.mErrorNum;
                    RenderScript.this.mErrorCallback.run();
                }
            });
        }
    }

    public void setMessageHandler(RSMessageHandler rSMessageHandler) {
        this.mMessageCallback = rSMessageHandler;
        if (isNative) {
            RenderScriptThunker renderScriptThunker = (RenderScriptThunker) this;
            renderScriptThunker.mN.setMessageHandler(new android.renderscript.RenderScript.RSMessageHandler() {
                public void run() {
                    RenderScript.this.mMessageCallback.mData = this.mData;
                    RenderScript.this.mMessageCallback.mID = this.mID;
                    RenderScript.this.mMessageCallback.mLength = this.mLength;
                    RenderScript.this.mMessageCallback.run();
                }
            });
        }
    }

    public void setPriority(Priority priority) {
        validate();
        nContextSetPriority(priority.mID);
    }

    void validate() {
        if (this.mContext == 0) {
            throw new RSInvalidStateException("Calling RS with no Context active.");
        }
    }
}
