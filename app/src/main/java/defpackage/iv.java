package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

public final class iv {
    public static Bitmap a(String str, int i, int i2, int i3, int i4) throws WriterException {
        BitMatrix encode = new Code128Writer().encode(str, BarcodeFormat.CODE_128, i, i2);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        for (int i5 = 0; i5 < encode.getWidth(); i5++) {
            for (int i6 = 0; i6 < encode.getHeight(); i6++) {
                createBitmap.setPixel(i5, i6, encode.get(i5, i6) ? -16777216 : -1);
            }
        }
        return createBitmap;
    }
}
