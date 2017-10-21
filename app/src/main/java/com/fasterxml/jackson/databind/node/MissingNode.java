package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

public final class MissingNode extends ValueNode {
    private static final MissingNode instance = new MissingNode();

    private MissingNode() {
    }

    public static MissingNode getInstance() {
        return instance;
    }

    public final String asText() {
        return "";
    }

    public final String asText(String str) {
        return str;
    }

    public final JsonToken asToken() {
        return JsonToken.NOT_AVAILABLE;
    }

    public final <T extends JsonNode> T deepCopy() {
        return this;
    }

    public final boolean equals(Object obj) {
        return obj == this;
    }

    public final JsonNodeType getNodeType() {
        return JsonNodeType.MISSING;
    }

    public final int hashCode() {
        return JsonNodeType.MISSING.ordinal();
    }

    public final void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNull();
    }

    public final void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonProcessingException {
        jsonGenerator.writeNull();
    }

    public final String toString() {
        return "";
    }
}
