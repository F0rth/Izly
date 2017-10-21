package com.crashlytics.android;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import defpackage.js;

public class CrashlyticsInitProvider extends ContentProvider {
    private static final String TAG = "CrashlyticsInitProvider";

    interface EnabledCheckStrategy {
        boolean isCrashlyticsEnabled(Context context);
    }

    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public boolean onCreate() {
        Context context = getContext();
        if (shouldInitializeFabric(context, new kv(), new ManifestEnabledCheckStrategy())) {
            try {
                js.a(context, new Crashlytics());
                js.a().c(TAG, "CrashlyticsInitProvider initialization successful");
            } catch (IllegalStateException e) {
                js.a().c(TAG, "CrashlyticsInitProvider initialization unsuccessful");
                return false;
            }
        }
        return true;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    boolean shouldInitializeFabric(Context context, kv kvVar, EnabledCheckStrategy enabledCheckStrategy) {
        return kv.b(context) && enabledCheckStrategy.isCrashlyticsEnabled(context);
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
