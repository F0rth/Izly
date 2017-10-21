package defpackage;

import android.graphics.Bitmap;
import android.text.Editable;

public final class pc extends pf {
    public pc() {
        this.b = 141.0f;
        this.a = 110.0f;
    }

    public final Bitmap a() {
        return os.a("basic_card");
    }

    public final Boolean a(String str) {
        return Boolean.valueOf(true);
    }

    public final void a(Editable editable) {
        if (editable.length() > 4) {
            editable.delete(4, editable.length());
        }
    }

    public final int b() {
        return 3;
    }

    public final Boolean b(String str) {
        return (str.length() == 22 && pf.d(str.toString().replace(" ", ""))) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    }

    public final Boolean c(String str) {
        return str.length() == 4 ? Boolean.valueOf(true) : Boolean.valueOf(false);
    }
}
