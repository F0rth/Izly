package defpackage;

import java.util.Collection;

public interface ld<T> {
    void addDependency(T t);

    boolean areDependenciesMet();

    Collection<T> getDependencies();
}
