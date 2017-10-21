package nl.qbusict.cupboard.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Index {
    CompositeIndex[] indexNames() default {};

    boolean unique() default false;

    CompositeIndex[] uniqueNames() default {};
}
