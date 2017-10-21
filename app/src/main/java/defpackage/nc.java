package defpackage;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public final class nc {
    public final String a;
    public final int b;
    public final int c;
    public final int d;

    private nc(String str, int i, int i2, int i3) {
        this.a = str;
        this.b = i;
        this.c = i2;
        this.d = i3;
    }

    public static nc a(Context context, String str) {
        if (str != null) {
            try {
                int j = kp.j(context);
                js.a().a("Fabric", "App icon resource ID is " + j);
                Options options = new Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(context.getResources(), j, options);
                return new nc(str, j, options.outWidth, options.outHeight);
            } catch (Throwable e) {
                js.a().c("Fabric", "Failed to load icon", e);
            }
        }
        return null;
    }
}
