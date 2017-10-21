package com.ezeeworld.b4s.android.sdk.server;

import android.text.TextUtils;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.B4SUserProperty.Gender;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Properties {
    public String sAppId;
    public String sClientId;
    public String sDeviceUdid;
    public List<UserProperty> userProperties;

    public enum PropertyType {
        String,
        Integer,
        Float,
        Gender,
        Date,
        Array;

        public static PropertyType fromName(String str) {
            return TextUtils.isEmpty(str) ? null : valueOf(str.substring(0, 1).toUpperCase(Locale.US) + str.substring(1).toLowerCase(Locale.US));
        }

        public static PropertyType fromValue(Object obj) {
            if (obj instanceof String) {
                return String;
            }
            if (obj instanceof Integer) {
                return Integer;
            }
            if (obj instanceof Float) {
                return Float;
            }
            if (obj instanceof Gender) {
                return Gender;
            }
            if (obj instanceof Date) {
                return Date;
            }
            if (obj instanceof List) {
                return Array;
            }
            B4SLog.e("PropertyType", "Unsupported user property of type " + obj.getClass().getSimpleName());
            return null;
        }

        public final String toName() {
            return name().toLowerCase(Locale.US);
        }
    }

    @JsonDeserialize(using = UserPropertyDeserializer.class)
    @JsonSerialize(using = UserPropertySerializer.class)
    public static class UserProperty {
        public String key;
        public PropertyType type;
        public Object value;
    }
}
