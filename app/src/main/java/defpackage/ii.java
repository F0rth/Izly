package defpackage;

import fr.smoney.android.izly.ui.widget.FormEditText;

public final class ii extends ik {
    private String b;
    private FormEditText c;
    private boolean d;

    public ii(FormEditText formEditText, String str, String str2, boolean z) {
        super(str);
        this.c = formEditText;
        this.b = str2;
        this.d = z;
    }

    public final void a(String str) {
        this.b = str;
        this.c.b();
    }

    public final boolean b(String str) {
        return this.d ? !str.equalsIgnoreCase(this.b) : str.equalsIgnoreCase(this.b);
    }
}
