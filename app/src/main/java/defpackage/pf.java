package defpackage;

import android.graphics.Bitmap;
import android.text.Editable;

public abstract class pf {
    public float a;
    public float b;

    protected static boolean d(String str) {
        int i = 0;
        boolean z = false;
        int length = str.length() - 1;
        while (length >= 0) {
            int parseInt = Integer.parseInt(str.substring(length, length + 1));
            if (z) {
                parseInt <<= 1;
                if (parseInt > 9) {
                    parseInt = (parseInt % 10) + 1;
                }
            }
            i += parseInt;
            length--;
            z = !z;
        }
        return i % 10 == 0;
    }

    public abstract Bitmap a();

    public abstract Boolean a(String str);

    public abstract void a(Editable editable);

    public abstract int b();

    public abstract Boolean b(String str);

    public abstract Boolean c(String str);
}
