package com.joanzapata.android.iconify;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import defpackage.lv;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class Utils {
    public static final String ICON_FONT_FOLDER = "icon_tmp";

    private Utils() {
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    static int convertDpToPx(Context context, float f) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }

    static boolean isEnabled(int[] iArr) {
        for (int i : iArr) {
            if (i == 16842910) {
                return true;
            }
        }
        return false;
    }

    public static StringBuilder replaceIcons(StringBuilder stringBuilder) {
        int indexOf = stringBuilder.indexOf("{fa");
        if (indexOf != -1) {
            int indexOf2 = (stringBuilder.substring(indexOf).indexOf("}") + indexOf) + 1;
            String replaceAll = stringBuilder.substring(indexOf + 1, indexOf2 - 1).replaceAll("-", lv.ROLL_OVER_FILE_NAME_SEPARATOR);
            try {
                stringBuilder = replaceIcons(stringBuilder.replace(indexOf, indexOf2, String.valueOf(Iconify$IconValue.valueOf(replaceAll).character)));
            } catch (IllegalArgumentException e) {
                Log.w(Iconify.TAG, "Wrong icon name: " + replaceAll);
            }
        }
        return stringBuilder;
    }

    static File resourceToFile(Context context, String str) throws IOException {
        Closeable bufferedOutputStream;
        Throwable th;
        Closeable closeable = null;
        File file = context.getFilesDir() == null ? new File(context.getCacheDir(), ICON_FONT_FOLDER) : new File(context.getFilesDir(), ICON_FONT_FOLDER);
        if (file.exists() || file.mkdirs()) {
            File file2 = new File(file, str);
            if (file2.exists()) {
                return file2;
            }
            try {
                Closeable resourceAsStream = Iconify.class.getClassLoader().getResourceAsStream(str);
                try {
                    byte[] bArr = new byte[resourceAsStream.available()];
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2));
                    while (true) {
                        try {
                            int read = resourceAsStream.read(bArr);
                            if (read > 0) {
                                bufferedOutputStream.write(bArr, 0, read);
                            } else {
                                closeQuietly(bufferedOutputStream);
                                closeQuietly(resourceAsStream);
                                return file2;
                            }
                        } catch (Throwable th2) {
                            Closeable closeable2 = resourceAsStream;
                            th = th2;
                            closeable = closeable2;
                        }
                    }
                } catch (Throwable th3) {
                    closeable = resourceAsStream;
                    th = th3;
                    bufferedOutputStream = null;
                    closeQuietly(bufferedOutputStream);
                    closeQuietly(closeable);
                    throw th;
                }
            } catch (Throwable th32) {
                th = th32;
                bufferedOutputStream = null;
                closeQuietly(bufferedOutputStream);
                closeQuietly(closeable);
                throw th;
            }
        }
        Log.e(Iconify.TAG, "Font folder creation failed");
        throw new IllegalStateException("Cannot create Iconify font destination folder");
    }
}
