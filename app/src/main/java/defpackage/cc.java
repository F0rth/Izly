package defpackage;

public enum cc implements ce {
    MY_BANK_ACCOUNT(2131231292, 2131230816),
    MY_CARDS(2131231293, 2131230817),
    MY_SUPPORTS(2131231411, 2131230818);
    
    private int d;
    private int e;

    private cc(int i, int i2) {
        this.d = i;
        this.e = i2;
    }

    public final int a() {
        return this.d;
    }

    public final int b() {
        return this.e;
    }

    public final int c() {
        return 0;
    }

    public final int d() {
        return 0;
    }
}
