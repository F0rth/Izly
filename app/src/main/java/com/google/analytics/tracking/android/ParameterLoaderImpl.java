package com.google.analytics.tracking.android;

import android.content.Context;
import android.text.TextUtils;

class ParameterLoaderImpl implements ParameterLoader {
    private final Context mContext;
    private String mOverrideResourcePackageName;

    public ParameterLoaderImpl(Context context) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }
        this.mContext = context.getApplicationContext();
    }

    private int getResourceIdForType(String str, String str2) {
        if (this.mContext == null) {
            return 0;
        }
        return this.mContext.getResources().getIdentifier(str, str2, this.mOverrideResourcePackageName != null ? this.mOverrideResourcePackageName : this.mContext.getPackageName());
    }

    public boolean getBoolean(String str) {
        int resourceIdForType = getResourceIdForType(str, "bool");
        return resourceIdForType == 0 ? false : "true".equalsIgnoreCase(this.mContext.getString(resourceIdForType));
    }

    public Double getDoubleFromString(String str) {
        Double d = null;
        String string = getString(str);
        if (TextUtils.isEmpty(string)) {
            return d;
        }
        try {
            return Double.valueOf(Double.parseDouble(string));
        } catch (NumberFormatException e) {
            Log.w("NumberFormatException parsing " + string);
            return d;
        }
    }

    public int getInt(String str, int i) {
        int resourceIdForType = getResourceIdForType(str, "integer");
        if (resourceIdForType != 0) {
            try {
                i = Integer.parseInt(this.mContext.getString(resourceIdForType));
            } catch (NumberFormatException e) {
                Log.w("NumberFormatException parsing " + this.mContext.getString(resourceIdForType));
            }
        }
        return i;
    }

    public String getString(String str) {
        int resourceIdForType = getResourceIdForType(str, "string");
        return resourceIdForType == 0 ? null : this.mContext.getString(resourceIdForType);
    }

    public boolean isBooleanKeyPresent(String str) {
        return getResourceIdForType(str, "bool") != 0;
    }

    public void setResourcePackageName(String str) {
        this.mOverrideResourcePackageName = str;
    }
}
