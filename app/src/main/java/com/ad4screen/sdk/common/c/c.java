package com.ad4screen.sdk.common.c;

import org.json.JSONException;

public interface c<T> {
    T fromJSON(String str) throws JSONException;

    String getClassKey();
}
