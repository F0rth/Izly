package fr.smoney.android.izly.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;

public class FileContentProvider extends ContentProvider {
    public static Uri a(String str) {
        Uri parse = Uri.parse(str);
        return parse.isAbsolute() ? parse : Uri.parse("content://fr.smoney.android.izly.provider.FileContentProvider/" + str);
    }

    public int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("Delete:Not supported by this provider");
    }

    public String getType(Uri uri) {
        throw new UnsupportedOperationException("GetType:Not supported by this provider");
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("Insert:Not supported by this provider");
    }

    public boolean onCreate() {
        return true;
    }

    public ParcelFileDescriptor openFile(Uri uri, String str) throws FileNotFoundException {
        if (uri != null) {
            String str2 = "content://fr.smoney.android.izly.provider.FileContentProvider/" + getContext().getFilesDir().getAbsolutePath();
            Object obj = (uri.toString().equals(new StringBuilder().append(str2).append("/attachment.jpg").toString()) || uri.toString().equals(str2 + "/attachment.png") || uri.toString().equals(str2 + "/attachment.tiff")) ? null : 1;
            if (obj == null) {
                return ParcelFileDescriptor.open(new File(uri.getPath()), 268435456);
            }
        }
        return null;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("Update:Not supported by this provider");
    }
}
