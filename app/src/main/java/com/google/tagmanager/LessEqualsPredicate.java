package com.google.tagmanager;

import com.google.analytics.containertag.common.FunctionType;
import com.google.analytics.midtier.proto.containertag.TypeSystem.Value;
import java.util.Map;

class LessEqualsPredicate extends NumberPredicate {
    private static final String ID = FunctionType.LESS_EQUALS.toString();

    public LessEqualsPredicate() {
        super(ID);
    }

    public static String getFunctionId() {
        return ID;
    }

    protected boolean evaluateNumber(TypedNumber typedNumber, TypedNumber typedNumber2, Map<String, Value> map) {
        return typedNumber.compareTo(typedNumber2) <= 0;
    }
}
