package com.foxykeep.datadroid.requestmanager;

public abstract class RequestManager {
    public static final String RECEIVER_EXTRA_ERROR_TYPE = "com.foxykeep.datadroid.extras.error";
    public static final String RECEIVER_EXTRA_PAYLOAD = "com.foxykeep.datadroid.extras.payload";
    public static final String RECEIVER_EXTRA_REQUEST_ID = "com.foxykeep.datadroid.extras.requestId";
    public static final String RECEIVER_EXTRA_RESULT_CODE = "com.foxykeep.datadroid.extras.code";
    public static final int RECEIVER_EXTRA_VALUE_ERROR_TYPE_CONNEXION = 1;
    public static final int RECEIVER_EXTRA_VALUE_ERROR_TYPE_DATA = 2;
}
