package defpackage;

import android.graphics.Bitmap;
import android.text.Editable;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

public final class b extends pf {
    private boolean c;

    public b() {
        this.b = 141.0f;
        this.a = -141.0f;
        this.c = false;
    }

    public final Bitmap a() {
        return os.a("visa");
    }

    public final Boolean a(String str) {
        return (str.length() <= 0 || str.charAt(0) != '4') ? Boolean.valueOf(false) : Boolean.valueOf(true);
    }

    public final void a(Editable editable) {
        this.c = false;
        String obj = editable.toString();
        if (editable.length() > 22) {
            editable.delete(22, editable.length());
        }
        if (editable.length() == 22 && !b.d(editable.toString().replace(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, ""))) {
            editable.delete(editable.length() - 1, editable.length());
            this.c = true;
        }
        if (obj.length() == 4 || obj.length() == 10 || obj.length() == 16) {
            editable.append("  ");
        } else if (obj.length() == 5 || obj.length() == 11 || obj.length() == 17) {
            editable.delete(obj.length() - 2, obj.length());
        }
    }

    public final int b() {
        return 3;
    }

    public final Boolean b(String str) {
        return (str.length() == 22 && b.d(str.toString().replace(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, ""))) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    }

    public final Boolean c(String str) {
        return Boolean.valueOf(this.c);
    }
}
