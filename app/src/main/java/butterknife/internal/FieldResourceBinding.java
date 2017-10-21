package butterknife.internal;

final class FieldResourceBinding {
    private final int id;
    private final String method;
    private final String name;

    FieldResourceBinding(int i, String str, String str2) {
        this.id = i;
        this.name = str;
        this.method = str2;
    }

    public final int getId() {
        return this.id;
    }

    public final String getMethod() {
        return this.method;
    }

    public final String getName() {
        return this.name;
    }
}
