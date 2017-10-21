package com.google.tagmanager;

import android.os.Build;
import android.support.v4.os.EnvironmentCompat;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.analytics.containertag.common.FunctionType;
import com.google.analytics.midtier.proto.containertag.TypeSystem.Value;
import java.util.Map;

class DeviceNameMacro extends FunctionCallImplementation {
    private static final String ID = FunctionType.DEVICE_NAME.toString();

    public DeviceNameMacro() {
        super(ID, new String[0]);
    }

    public static String getFunctionId() {
        return ID;
    }

    public Value evaluate(Map<String, Value> map) {
        String str = Build.MANUFACTURER;
        Object obj = Build.MODEL;
        if (!(obj.startsWith(str) || str.equals(EnvironmentCompat.MEDIA_UNKNOWN))) {
            obj = str + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + obj;
        }
        return Types.objectToValue(obj);
    }

    public boolean isCacheable() {
        return true;
    }
}
