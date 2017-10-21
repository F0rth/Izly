package com.foxykeep.datadroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class MultiThreadService extends Service {
    private ArrayList<Future> mFutureList;
    private Handler mHandler;
    final Runnable mHasFinishedWorkingRunnable = new Runnable() {
        public void run() {
            ArrayList access$000 = MultiThreadService.this.mFutureList;
            int i = 0;
            while (i < access$000.size()) {
                if (((Future) access$000.get(i)).isDone()) {
                    access$000.remove(i);
                    i--;
                }
                i++;
            }
            if (access$000.isEmpty()) {
                MultiThreadService.this.stopSelf();
            }
        }
    };
    private int mMaxThreads;
    private ExecutorService mThreadPool;

    class IntentRunnable implements Runnable {
        private Intent mIntent;

        public IntentRunnable(Intent intent) {
            this.mIntent = intent;
        }

        public void run() {
            MultiThreadService.this.onHandleIntent(this.mIntent);
            MultiThreadService.this.mHandler.post(MultiThreadService.this.mHasFinishedWorkingRunnable);
        }
    }

    public MultiThreadService(int i) {
        this.mMaxThreads = i;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.mThreadPool = Executors.newFixedThreadPool(this.mMaxThreads);
        this.mHandler = new Handler();
        this.mFutureList = new ArrayList();
    }

    public abstract void onHandleIntent(Intent intent);

    public void onStart(Intent intent, int i) {
        this.mFutureList.add(this.mThreadPool.submit(new IntentRunnable(intent)));
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        onStart(intent, i2);
        return 1;
    }
}
