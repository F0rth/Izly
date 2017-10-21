package butterknife.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class MethodViewBinding implements ViewBinding {
    private final String name;
    private final List<Parameter> parameters;
    private final boolean required;

    MethodViewBinding(String str, List<Parameter> list, boolean z) {
        this.name = str;
        this.parameters = Collections.unmodifiableList(new ArrayList(list));
        this.required = z;
    }

    public final String getDescription() {
        return "method '" + this.name + "'";
    }

    public final String getName() {
        return this.name;
    }

    public final List<Parameter> getParameters() {
        return this.parameters;
    }

    public final boolean isRequired() {
        return this.required;
    }
}
