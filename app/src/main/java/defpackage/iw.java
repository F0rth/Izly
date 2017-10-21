package defpackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import fr.smoney.android.izly.provider.FileContentProvider;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import org.spongycastle.asn1.cmp.PKIFailureInfo;

public class iw {
    private static final String a = iw.class.getSimpleName();

    public static Bitmap a(Context context, int i) {
        return BitmapFactory.decodeResource(context.getResources(), i);
    }

    public static Bitmap a(Bitmap bitmap, float f, float f2, Bitmap bitmap2, float f3, float f4) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        canvas.drawBitmap(bitmap2, f3, f4, null);
        return createBitmap;
    }

    private static Bitmap a(Bitmap bitmap, int i, int i2, boolean z, boolean z2) {
        int i3;
        Bitmap createScaledBitmap;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        float f = ((float) i) / ((float) width);
        float f2 = ((float) i2) / ((float) height);
        if (f2 > f) {
            i3 = (int) ((((float) height) * f2) + 0.5f);
            height = (int) ((((float) width) * f2) + 0.5f);
        } else {
            i3 = (int) ((((float) height) * f) + 0.5f);
            height = (int) ((((float) width) * f) + 0.5f);
        }
        try {
            createScaledBitmap = Bitmap.createScaledBitmap(bitmap, height, i3, true);
        } catch (Throwable th) {
            createScaledBitmap = null;
        }
        if (createScaledBitmap != bitmap) {
            try {
                bitmap.recycle();
            } catch (Exception e) {
            }
        }
        return createScaledBitmap;
    }

    public static Bitmap a(File file, int i, int i2) {
        Closeable fileInputStream;
        Throwable th;
        Bitmap bitmap = null;
        int i3 = 1;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                BitmapFactory.decodeStream(fileInputStream, null, options);
                int i4 = options.outHeight;
                int i5 = options.outWidth;
                if (i4 > i2 || i5 > i) {
                    i4 = Math.round(((float) i4) / ((float) i2));
                    i3 = Math.round(((float) i5) / ((float) i));
                    if (i4 < i3) {
                        i3 = i4;
                    }
                }
                options.inJustDecodeBounds = false;
                options.inSampleSize = i3;
                options.inTempStorage = new byte[PKIFailureInfo.notAuthorized];
                options.inPurgeable = true;
                if (fileInputStream.markSupported()) {
                    fileInputStream.reset();
                } else {
                    iw.a(fileInputStream);
                    Object fileInputStream2 = new FileInputStream(file);
                }
                try {
                    bitmap = iw.a(BitmapFactory.decodeStream(fileInputStream, null, options), i, i2, true, true);
                    iw.a(fileInputStream);
                } catch (Exception e) {
                    iw.a(fileInputStream);
                    return bitmap;
                } catch (Throwable th2) {
                    th = th2;
                    iw.a(fileInputStream);
                    throw th;
                }
            } catch (Exception e2) {
                iw.a(fileInputStream);
                return bitmap;
            } catch (Throwable th3) {
                th = th3;
                iw.a(fileInputStream);
                throw th;
            }
        } catch (Exception e3) {
            fileInputStream = bitmap;
            iw.a(fileInputStream);
            return bitmap;
        } catch (Throwable th4) {
            Bitmap bitmap2 = bitmap;
            th = th4;
            fileInputStream = bitmap2;
            iw.a(fileInputStream);
            throw th;
        }
        return bitmap;
    }

    public static Bitmap a(byte[] bArr) {
        return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
    }

    public static String a(Context context) {
        return context.getFilesDir().getAbsolutePath() + "/attachment.jpg";
    }

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }

    public static byte[] a(Bitmap bitmap) {
        bitmap.compress(CompressFormat.PNG, 100, new ByteArrayOutputStream());
        CompressFormat compressFormat = CompressFormat.PNG;
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(compressFormat, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap b(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, 5.0f, 5.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();
        return createBitmap;
    }

    public static void b(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(FileContentProvider.a(iw.a(context)), "image/*");
        context.startActivity(intent);
    }
}
