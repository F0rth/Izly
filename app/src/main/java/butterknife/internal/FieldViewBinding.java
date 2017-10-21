package butterknife.internal;

final class FieldViewBinding implements ViewBinding {
    private final String name;
    private final boolean required;
    private final String type;

    FieldViewBinding(String str, String str2, boolean z) {
        this.name = str;
        this.type = str2;
        this.required = z;
    }

    public final String getDescription() {
        return "field '" + this.name + "'";
    }

    public final String getName() {
        return this.name;
    }

    public final String getType() {
        return this.type;
    }

    public final boolean isRequired() {
        return this.required;
    }

    public final boolean requiresCast() {
        return !"android.view.View".equals(this.type);
    }
}
