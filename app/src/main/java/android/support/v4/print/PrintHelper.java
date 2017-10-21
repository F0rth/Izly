package android.support.v4.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import java.io.FileNotFoundException;

public final class PrintHelper {
    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    PrintHelperVersionImpl mImpl;

    public interface OnPrintFinishCallback {
        void onFinish();
    }

    interface PrintHelperVersionImpl {
        int getColorMode();

        int getOrientation();

        int getScaleMode();

        void printBitmap(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback);

        void printBitmap(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException;

        void setColorMode(int i);

        void setOrientation(int i);

        void setScaleMode(int i);
    }

    static final class PrintHelperKitkatImpl implements PrintHelperVersionImpl {
        private final PrintHelperKitkat mPrintHelper;

        PrintHelperKitkatImpl(Context context) {
            this.mPrintHelper = new PrintHelperKitkat(context);
        }

        public final int getColorMode() {
            return this.mPrintHelper.getColorMode();
        }

        public final int getOrientation() {
            return this.mPrintHelper.getOrientation();
        }

        public final int getScaleMode() {
            return this.mPrintHelper.getScaleMode();
        }

        public final void printBitmap(String str, Bitmap bitmap, final OnPrintFinishCallback onPrintFinishCallback) {
            android.support.v4.print.PrintHelperKitkat.OnPrintFinishCallback onPrintFinishCallback2 = null;
            if (onPrintFinishCallback != null) {
                onPrintFinishCallback2 = new android.support.v4.print.PrintHelperKitkat.OnPrintFinishCallback() {
                    public void onFinish() {
                        onPrintFinishCallback.onFinish();
                    }
                };
            }
            this.mPrintHelper.printBitmap(str, bitmap, onPrintFinishCallback2);
        }

        public final void printBitmap(String str, Uri uri, final OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
            android.support.v4.print.PrintHelperKitkat.OnPrintFinishCallback onPrintFinishCallback2 = null;
            if (onPrintFinishCallback != null) {
                onPrintFinishCallback2 = new android.support.v4.print.PrintHelperKitkat.OnPrintFinishCallback() {
                    public void onFinish() {
                        onPrintFinishCallback.onFinish();
                    }
                };
            }
            this.mPrintHelper.printBitmap(str, uri, onPrintFinishCallback2);
        }

        public final void setColorMode(int i) {
            this.mPrintHelper.setColorMode(i);
        }

        public final void setOrientation(int i) {
            this.mPrintHelper.setOrientation(i);
        }

        public final void setScaleMode(int i) {
            this.mPrintHelper.setScaleMode(i);
        }
    }

    static final class PrintHelperStubImpl implements PrintHelperVersionImpl {
        int mColorMode;
        int mOrientation;
        int mScaleMode;

        private PrintHelperStubImpl() {
            this.mScaleMode = 2;
            this.mColorMode = 2;
            this.mOrientation = 1;
        }

        public final int getColorMode() {
            return this.mColorMode;
        }

        public final int getOrientation() {
            return this.mOrientation;
        }

        public final int getScaleMode() {
            return this.mScaleMode;
        }

        public final void printBitmap(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
        }

        public final void printBitmap(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) {
        }

        public final void setColorMode(int i) {
            this.mColorMode = i;
        }

        public final void setOrientation(int i) {
            this.mOrientation = i;
        }

        public final void setScaleMode(int i) {
            this.mScaleMode = i;
        }
    }

    public PrintHelper(Context context) {
        if (systemSupportsPrint()) {
            this.mImpl = new PrintHelperKitkatImpl(context);
        } else {
            this.mImpl = new PrintHelperStubImpl();
        }
    }

    public static boolean systemSupportsPrint() {
        return VERSION.SDK_INT >= 19;
    }

    public final int getColorMode() {
        return this.mImpl.getColorMode();
    }

    public final int getOrientation() {
        return this.mImpl.getOrientation();
    }

    public final int getScaleMode() {
        return this.mImpl.getScaleMode();
    }

    public final void printBitmap(String str, Bitmap bitmap) {
        this.mImpl.printBitmap(str, bitmap, null);
    }

    public final void printBitmap(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
        this.mImpl.printBitmap(str, bitmap, onPrintFinishCallback);
    }

    public final void printBitmap(String str, Uri uri) throws FileNotFoundException {
        this.mImpl.printBitmap(str, uri, null);
    }

    public final void printBitmap(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
        this.mImpl.printBitmap(str, uri, onPrintFinishCallback);
    }

    public final void setColorMode(int i) {
        this.mImpl.setColorMode(i);
    }

    public final void setOrientation(int i) {
        this.mImpl.setOrientation(i);
    }

    public final void setScaleMode(int i) {
        this.mImpl.setScaleMode(i);
    }
}
