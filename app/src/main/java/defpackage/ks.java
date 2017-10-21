package defpackage;

public enum ks {
    DEVELOPER(1),
    USER_SIDELOAD(2),
    TEST_DISTRIBUTION(3),
    APP_STORE(4);
    
    public final int e;

    private ks(int i) {
        this.e = i;
    }

    public static ks a(String str) {
        return "io.crash.air".equals(str) ? TEST_DISTRIBUTION : str != null ? APP_STORE : DEVELOPER;
    }

    public final String toString() {
        return Integer.toString(this.e);
    }
}
