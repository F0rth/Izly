package com.ad4screen.sdk.provider;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.RemoteException;

import com.ad4screen.sdk.contract.A4SContract;
import com.ad4screen.sdk.provider.c.a;

import java.util.ArrayList;

public class d implements a {
    protected ContentResolver a;
    protected Context b;

    public d(Context context) {
        this.a = context.getContentResolver();
        this.b = context;
    }

    public int a(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return this.a.update(uri, contentValues, str, strArr);
    }

    public int a(Uri uri, String str, String[] strArr) {
        return this.a.delete(uri, str, strArr);
    }

    public int a(Uri uri, ContentValues[] contentValuesArr) {
        return this.a.bulkInsert(uri, contentValuesArr);
    }

    public Cursor a(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return this.a.query(uri, strArr, str, strArr2, str2);
    }

    public Uri a(Uri uri, ContentValues contentValues) {
        return this.a.insert(uri, contentValues);
    }

    public ContentProviderResult[] a(ArrayList<ContentProviderOperation> arrayList) throws OperationApplicationException {
        if (VERSION.SDK_INT >= 5) {
            try {
                return this.a.applyBatch(A4SContract.getAuthority(this.b), arrayList);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
