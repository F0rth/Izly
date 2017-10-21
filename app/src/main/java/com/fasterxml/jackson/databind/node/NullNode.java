package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public final class NullNode extends ValueNode {
    public static final NullNode instance = new NullNode();

    private NullNode() {
    }

    public static NullNode getInstance() {
        return instance;
    }

    public final String asText() {
        return "null";
    }

    public final String asText(String str) {
        return str;
    }

    public final JsonToken asToken() {
        return JsonToken.VALUE_NULL;
    }

    public final boolean equals(Object obj) {
        return obj == this;
    }

    public final JsonNodeType getNodeType() {
        return JsonNodeType.NULL;
    }

    public final int hashCode() {
        return JsonNodeType.NULL.ordinal();
    }

    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        serializerProvider.defaultSerializeNull(jsonGenerator);
    }
}
