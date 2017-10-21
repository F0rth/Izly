package com.ad4screen.sdk.provider;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.contract.A4SContract.BeaconGeofenceGroups;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class c {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    protected a mAcc;
    protected Context mContext;

    public interface a {
        int a(Uri uri, ContentValues contentValues, String str, String[] strArr);

        int a(Uri uri, String str, String[] strArr);

        int a(Uri uri, ContentValues[] contentValuesArr);

        Cursor a(Uri uri, String[] strArr, String str, String[] strArr2, String str2);

        Uri a(Uri uri, ContentValues contentValues);

        ContentProviderResult[] a(ArrayList<ContentProviderOperation> arrayList) throws OperationApplicationException;
    }

    public c(a aVar, Context context) {
        this.mAcc = aVar;
        this.mContext = context;
    }

    public static String convertDateToString(Date date) {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(date);
    }

    public static Date convertStringToDate(String str) {
        try {
            return new SimpleDateFormat(DATE_TIME_FORMAT).parse(str);
        } catch (ParseException e) {
            Log.error("A4SBeaconResolver|convertStringToDate exception: " + e.toString());
            return new Date(0);
        }
    }

    public int deleteAllGroups() {
        return this.mAcc.a(BeaconGeofenceGroups.getContentUri(this.mContext), null, null);
    }
}
