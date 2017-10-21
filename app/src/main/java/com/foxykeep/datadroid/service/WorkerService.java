package com.foxykeep.datadroid.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import com.foxykeep.datadroid.requestmanager.RequestManager;

public abstract class WorkerService extends MultiThreadService {
    public static final int ERROR_CODE = -1;
    public static final String INTENT_EXTRA_RECEIVER = "com.foxykeep.datadroid.extras.receiver";
    public static final String INTENT_EXTRA_REQUEST_ID = "com.foxykeep.datadroid.extras.requestId";
    public static final String INTENT_EXTRA_WORKER_TYPE = "com.foxykeep.datadroid.extras.workerType";
    public static final String LOG_TAG = WorkerService.class.getSimpleName();
    public static final int SUCCESS_CODE = 0;

    public WorkerService(int i) {
        super(i);
    }

    protected void sendConnexionFailure(Intent intent, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt(RequestManager.RECEIVER_EXTRA_ERROR_TYPE, 1);
        sendResult(intent, bundle, -1);
    }

    protected void sendDataFailure(Intent intent, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt(RequestManager.RECEIVER_EXTRA_ERROR_TYPE, 2);
        sendResult(intent, bundle, -1);
    }

    protected void sendFailure(Intent intent, Bundle bundle) {
        sendResult(intent, bundle, -1);
    }

    public void sendResult(Intent intent, Bundle bundle, int i) {
        Log.d(LOG_TAG, "sendResult : " + (i == 0 ? "Success" : "Failure"));
        ResultReceiver resultReceiver = (ResultReceiver) intent.getParcelableExtra(INTENT_EXTRA_RECEIVER);
        if (resultReceiver != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putInt("com.foxykeep.datadroid.extras.requestId", intent.getIntExtra("com.foxykeep.datadroid.extras.requestId", -1));
            resultReceiver.send(i, bundle);
        }
    }

    protected void sendSuccess(Intent intent, Bundle bundle) {
        sendResult(intent, bundle, 0);
    }
}
