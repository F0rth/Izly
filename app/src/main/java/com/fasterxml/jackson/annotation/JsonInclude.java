package com.fasterxml.jackson.annotation;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@JacksonAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonInclude {

    public enum Include {
        ALWAYS,
        NON_NULL,
        NON_ABSENT,
        NON_EMPTY,
        NON_DEFAULT,
        USE_DEFAULTS
    }

    public static class Value implements JacksonAnnotationValue<JsonInclude>, Serializable {
        protected static final Value EMPTY = new Value(Include.USE_DEFAULTS, Include.USE_DEFAULTS);
        private static final long serialVersionUID = 1;
        protected final Include _contentInclusion;
        protected final Include _valueInclusion;

        protected Value(Include include, Include include2) {
            if (include == null) {
                include = Include.USE_DEFAULTS;
            }
            this._valueInclusion = include;
            if (include2 == null) {
                include2 = Include.USE_DEFAULTS;
            }
            this._contentInclusion = include2;
        }

        public Value(JsonInclude jsonInclude) {
            this(jsonInclude.value(), jsonInclude.content());
        }

        public static Value construct(Include include, Include include2) {
            return ((include == Include.USE_DEFAULTS || include == null) && (include2 == Include.USE_DEFAULTS || include2 == null)) ? EMPTY : new Value(include, include2);
        }

        public static Value empty() {
            return EMPTY;
        }

        public static Value from(JsonInclude jsonInclude) {
            if (jsonInclude == null) {
                return null;
            }
            Include value = jsonInclude.value();
            Include content = jsonInclude.content();
            return (value == Include.USE_DEFAULTS && content == Include.USE_DEFAULTS) ? EMPTY : new Value(value, content);
        }

        public boolean equals(Object obj) {
            if (obj != this) {
                if (obj == null || obj.getClass() != getClass()) {
                    return false;
                }
                Value value = (Value) obj;
                if (value._valueInclusion != this._valueInclusion) {
                    return false;
                }
                if (value._contentInclusion != this._contentInclusion) {
                    return false;
                }
            }
            return true;
        }

        public Include getContentInclusion() {
            return this._contentInclusion;
        }

        public Include getValueInclusion() {
            return this._valueInclusion;
        }

        public int hashCode() {
            return (this._valueInclusion.hashCode() << 2) + this._contentInclusion.hashCode();
        }

        protected Object readResolve() {
            return (this._valueInclusion == Include.USE_DEFAULTS && this._contentInclusion == Include.USE_DEFAULTS) ? EMPTY : this;
        }

        public String toString() {
            return String.format("[value=%s,content=%s]", new Object[]{this._valueInclusion, this._contentInclusion});
        }

        public Class<JsonInclude> valueFor() {
            return JsonInclude.class;
        }

        public Value withContentInclusion(Include include) {
            return include == this._contentInclusion ? this : new Value(this._valueInclusion, include);
        }

        public Value withOverrides(Value value) {
            Object obj = 1;
            if (value == null || value == EMPTY) {
                return this;
            }
            Include include = value._valueInclusion;
            Include include2 = value._contentInclusion;
            Object obj2 = (include == this._valueInclusion || include == Include.USE_DEFAULTS) ? null : 1;
            if (include2 == this._contentInclusion || include2 == Include.USE_DEFAULTS) {
                obj = null;
            }
            return obj2 != null ? obj != null ? new Value(include, include2) : new Value(include, this._contentInclusion) : obj != null ? new Value(this._valueInclusion, include2) : this;
        }

        public Value withValueInclusion(Include include) {
            return include == this._valueInclusion ? this : new Value(include, this._contentInclusion);
        }
    }

    Include content() default Include.ALWAYS;

    Include value() default Include.ALWAYS;
}
