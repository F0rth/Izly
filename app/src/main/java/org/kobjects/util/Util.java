package org.kobjects.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class Util {
    public static String buildUrl(String str, String str2) {
        int indexOf = str2.indexOf(58);
        if (str2.startsWith("/") || indexOf == 1) {
            return "file:///" + str2;
        }
        if (indexOf > 2 && indexOf < 6) {
            return str2;
        }
        if (str == null) {
            str = "file:///";
        } else {
            if (str.indexOf(58) == -1) {
                str = "file:///" + str;
            }
            if (!str.endsWith("/")) {
                str = str + "/";
            }
        }
        return str + str2;
    }

    public static int indexOf(Object[] objArr, Object obj) {
        for (int i = 0; i < objArr.length; i++) {
            if (objArr[i].equals(obj)) {
                return i;
            }
        }
        return -1;
    }

    public static void sort(Object[] objArr, int i, int i2) {
        while (i2 - i > 2) {
            if (i2 - i == 3) {
                sort(objArr, i, i + 2);
                sort(objArr, i + 1, i + 3);
                i2 = i + 2;
            } else {
                int i3 = (i + i2) / 2;
                sort(objArr, i, i3);
                sort(objArr, i3, i2);
                Object obj = new Object[(i2 - i)];
                int i4 = i3;
                int i5 = i;
                for (int i6 = 0; i6 < obj.length; i6++) {
                    if (i5 == i3 || (i4 != i2 && objArr[i5].toString().compareTo(objArr[i4].toString()) >= 0)) {
                        obj[i6] = objArr[i4];
                        i4++;
                    } else {
                        obj[i6] = objArr[i5];
                        i5++;
                    }
                }
                System.arraycopy(obj, 0, objArr, i, obj.length);
                return;
            }
        }
        if (i2 - i == 2 && objArr[i].toString().compareTo(objArr[i + 1].toString()) > 0) {
            Object obj2 = objArr[i];
            objArr[i] = objArr[i + 1];
            objArr[i + 1] = obj2;
        }
    }

    public static OutputStream streamcopy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[(Runtime.getRuntime().freeMemory() >= 1048576 ? 16384 : 128)];
        while (true) {
            int read = inputStream.read(bArr, 0, bArr.length);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                inputStream.close();
                return outputStream;
            }
        }
    }
}
