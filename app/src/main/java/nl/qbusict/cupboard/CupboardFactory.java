package nl.qbusict.cupboard;

public final class CupboardFactory {
    private static Cupboard INSTANCE = new Cupboard();

    public static Cupboard cupboard() {
        return INSTANCE;
    }

    public static Cupboard getInstance() {
        return INSTANCE;
    }

    public static void setCupboard(Cupboard cupboard) {
        INSTANCE = cupboard;
    }
}
