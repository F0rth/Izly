package com.google.tagmanager;

import com.google.analytics.containertag.common.FunctionType;
import com.google.analytics.midtier.proto.containertag.TypeSystem.Value;
import java.util.Map;

class ContainsPredicate extends StringPredicate {
    private static final String ID = FunctionType.CONTAINS.toString();

    public ContainsPredicate() {
        super(ID);
    }

    public static String getFunctionId() {
        return ID;
    }

    protected boolean evaluateString(String str, String str2, Map<String, Value> map) {
        return str.contains(str2);
    }
}
