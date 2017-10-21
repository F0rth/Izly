package defpackage;

public enum ci implements ce {
    SIMPLE_SEND(2130837972, 2131231711, 2131231712),
    SIMPLE_ASK(2130837968, 2131230842, 2131230843),
    COLLECT(2130837965, 2131230915, 2131230916);
    
    private int d;
    private int e;
    private int f;

    private ci(int i, int i2, int i3) {
        this.d = i;
        this.e = i2;
        this.f = i3;
    }

    public final int a() {
        return this.e;
    }

    public final int b() {
        return this.f;
    }

    public final int c() {
        return this.d;
    }

    public final int d() {
        return 2130903334;
    }
}
