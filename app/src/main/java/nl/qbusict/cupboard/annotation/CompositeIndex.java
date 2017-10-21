package nl.qbusict.cupboard.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CompositeIndex {
    public static final boolean DEFAULT_ASCENDING = true;
    public static final String DEFAULT_INDEX_NAME = "";
    public static final int DEFAULT_ORDER = 0;

    boolean ascending() default true;

    String indexName();

    int order() default 0;
}
