package com.google.tagmanager;

import com.google.analytics.containertag.common.FunctionType;
import com.google.analytics.midtier.proto.containertag.TypeSystem.Value;
import java.util.Map;

class TimeMacro extends FunctionCallImplementation {
    private static final String ID = FunctionType.TIME.toString();

    public TimeMacro() {
        super(ID, new String[0]);
    }

    public static String getFunctionId() {
        return ID;
    }

    public Value evaluate(Map<String, Value> map) {
        return Types.objectToValue(Long.valueOf(System.currentTimeMillis()));
    }

    public boolean isCacheable() {
        return false;
    }
}
