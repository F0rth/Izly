package com.ezeeworld.b4s.android.sdk.server;

import com.ad4screen.sdk.analytics.Lead;
import com.ezeeworld.b4s.android.sdk.B4SUserProperty.Gender;
import com.ezeeworld.b4s.android.sdk.server.Properties.PropertyType;
import com.ezeeworld.b4s.android.sdk.server.Properties.UserProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public final class UserPropertySerializer extends JsonSerializer<UserProperty> {
    private String a(PropertyType propertyType, Object obj) throws JsonProcessingException {
        switch (propertyType) {
            case Gender:
                return Integer.toString(((Gender) obj).ordinal());
            case Date:
                return "\"" + Api2.RFC3339_FORMAT.format((Date) obj) + "\"";
            default:
                return Api2.get().getJackson().writeValueAsString(obj);
        }
    }

    public final void serialize(UserProperty userProperty, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("key", userProperty.key);
        PropertyType fromValue = PropertyType.fromValue(userProperty.value);
        if (fromValue != null && fromValue == PropertyType.Array) {
            jsonGenerator.writeStringField("type", PropertyType.Array.toName());
            jsonGenerator.writeArrayFieldStart(Lead.KEY_VALUE);
            for (Object next : (List) userProperty.value) {
                PropertyType fromValue2 = PropertyType.fromValue(next);
                if (fromValue2 != null) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeStringField("type", fromValue2.toName());
                    jsonGenerator.writeFieldName(Lead.KEY_VALUE);
                    jsonGenerator.writeRawValue(a(fromValue2, next));
                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
        } else if (fromValue != null) {
            jsonGenerator.writeStringField("type", fromValue.toName());
            jsonGenerator.writeFieldName(Lead.KEY_VALUE);
            jsonGenerator.writeRawValue(a(fromValue, userProperty.value));
        }
        jsonGenerator.writeEndObject();
    }
}
