package com.ezeeworld.b4s.android.sdk.server;

import com.ad4screen.sdk.analytics.Lead;
import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.B4SUserProperty.Gender;
import com.ezeeworld.b4s.android.sdk.server.Properties.PropertyType;
import com.ezeeworld.b4s.android.sdk.server.Properties.UserProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

public final class UserPropertyDeserializer extends JsonDeserializer<UserProperty> {
    private Object a(PropertyType propertyType, JsonNode jsonNode) {
        Object obj = null;
        switch (propertyType) {
            case Array:
                ArrayList arrayList = new ArrayList();
                Iterator it = jsonNode.iterator();
                while (it.hasNext()) {
                    JsonNode jsonNode2 = (JsonNode) it.next();
                    arrayList.add(a(PropertyType.fromName(jsonNode2.get("type").asText()), jsonNode2.get(Lead.KEY_VALUE)));
                }
                return arrayList;
            case String:
                return jsonNode.asText();
            case Integer:
                return Integer.valueOf(jsonNode.asInt());
            case Float:
                return Double.valueOf(jsonNode.asDouble());
            case Gender:
                return Gender.values()[jsonNode.asInt()];
            case Date:
                try {
                    return Api2.RFC3339_FORMAT.parse(jsonNode.asText());
                } catch (ParseException e) {
                    B4SLog.e((Object) this, "Cannot parse RFC3339 date from string " + jsonNode.asText());
                    return obj;
                }
            default:
                B4SLog.w((Object) this, "User property value " + propertyType + " not supported!");
                return obj;
        }
    }

    public final UserProperty deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode jsonNode = (JsonNode) jsonParser.getCodec().readTree(jsonParser);
        UserProperty userProperty = new UserProperty();
        userProperty.key = jsonNode.get("key").asText();
        if (jsonNode.hasNonNull("type") && jsonNode.hasNonNull(Lead.KEY_VALUE)) {
            userProperty.type = PropertyType.fromName(jsonNode.get("type").asText());
            userProperty.value = a(userProperty.type, jsonNode.get(Lead.KEY_VALUE));
        }
        return userProperty;
    }
}
