package defpackage;

public enum lc$d {
    ;

    static {
        a = 1;
        b = 2;
        c = 3;
        d = new int[]{a, b, c};
    }

    public static int[] a() {
        return (int[]) d.clone();
    }
}
