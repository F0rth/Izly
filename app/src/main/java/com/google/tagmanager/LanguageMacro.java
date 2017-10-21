package com.google.tagmanager;

import com.google.analytics.containertag.common.FunctionType;
import com.google.analytics.midtier.proto.containertag.TypeSystem.Value;
import java.util.Locale;
import java.util.Map;

class LanguageMacro extends FunctionCallImplementation {
    private static final String ID = FunctionType.LANGUAGE.toString();

    public LanguageMacro() {
        super(ID, new String[0]);
    }

    public static String getFunctionId() {
        return ID;
    }

    public Value evaluate(Map<String, Value> map) {
        Locale locale = Locale.getDefault();
        if (locale == null) {
            return Types.getDefaultValue();
        }
        String language = locale.getLanguage();
        return language == null ? Types.getDefaultValue() : Types.objectToValue(language.toLowerCase());
    }

    public boolean isCacheable() {
        return false;
    }
}
