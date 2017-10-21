package com.google.tagmanager;

import com.google.analytics.containertag.common.FunctionType;
import com.google.analytics.midtier.proto.containertag.TypeSystem.Value;
import java.util.Map;

class StartsWithPredicate extends StringPredicate {
    private static final String ID = FunctionType.STARTS_WITH.toString();

    public StartsWithPredicate() {
        super(ID);
    }

    public static String getFunctionId() {
        return ID;
    }

    protected boolean evaluateString(String str, String str2, Map<String, Value> map) {
        return str.startsWith(str2);
    }
}
