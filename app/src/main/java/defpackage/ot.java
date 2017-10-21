package defpackage;

import android.graphics.Bitmap;
import android.text.Editable;

public final class ot extends pf {
    private boolean c;

    public ot() {
        this.b = 116.0f;
        this.a = -116.0f;
        this.c = false;
    }

    public final Bitmap a() {
        return os.a("american_express");
    }

    public final Boolean a(String str) {
        return (str.length() < 2 || str.charAt(0) != '3') ? Boolean.valueOf(false) : (str.charAt(1) == '4' || str.charAt(1) == '7') ? Boolean.valueOf(true) : Boolean.valueOf(false);
    }

    public final void a(Editable editable) {
        this.c = false;
        String obj = editable.toString();
        if (editable.length() > 19) {
            editable.delete(19, editable.length());
        }
        if (editable.length() == 19 && !pf.d(editable.toString().replace(" ", ""))) {
            editable.delete(editable.length() - 1, editable.length());
            this.c = true;
        }
        if (obj.length() == 4 || obj.length() == 12) {
            editable.append("  ");
        } else if (obj.length() == 5 || obj.length() == 13) {
            editable.delete(obj.length() - 2, obj.length());
        }
    }

    public final int b() {
        return 4;
    }

    public final Boolean b(String str) {
        return (str.length() == 19 && pf.d(str.toString().replace(" ", ""))) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    }

    public final Boolean c(String str) {
        return Boolean.valueOf(this.c);
    }
}
