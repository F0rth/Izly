package com.google.tagmanager;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.google.analytics.containertag.common.FunctionType;
import com.google.analytics.midtier.proto.containertag.TypeSystem.Value;
import java.util.Map;

class ResolutionMacro extends FunctionCallImplementation {
    private static final String ID = FunctionType.RESOLUTION.toString();
    private final Context mContext;

    public ResolutionMacro(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    public static String getFunctionId() {
        return ID;
    }

    public Value evaluate(Map<String, Value> map) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        return Types.objectToValue(i + "x" + displayMetrics.heightPixels);
    }

    public boolean isCacheable() {
        return true;
    }
}
