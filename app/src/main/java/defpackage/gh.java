package defpackage;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;

public final class gh {
    public static final String[] a = new String[]{"contact_id", "display_name", "data1", "data2"};
    public static final String[] b = new String[]{"contact_id", "display_name", "data1"};
    public static final String[] c = new String[]{"contact_id", "data4", "data1"};
    public static final String[] d = new String[]{"contact_id", "display_name", "data1", "data1"};
    public static final String[] e = new String[]{"contact_id", "data1"};
    public static final String[] f = new String[]{"contact_id", "data1"};
    public static final String[] g = new String[]{"contact_id", "data1", "data2"};
    public static final String[] h = new String[]{"contact_id", "data4", "data7", "data9"};
    public static final String[] i = new String[]{"contact_id", "data2", "data3", "data1"};
    public static final String[] j = new String[]{"contact_id", "data1"};

    public static Cursor a(Context context) {
        return context.getContentResolver().query(Phone.CONTENT_URI, a, "data1 NOT NULL", null, "display_name COLLATE NOCASE ASC");
    }

    public static Cursor a(Context context, String str) {
        return context.getContentResolver().query(Phone.CONTENT_URI, f, "contact_id = ?AND (data1 NOT NULL)", new String[]{str}, null);
    }

    public static Cursor b(Context context) {
        return context.getContentResolver().query(Data.CONTENT_URI, b, "data1 NOT NULL", null, "contact_id ASC");
    }

    public static Cursor b(Context context, String str) {
        return context.getContentResolver().query(Phone.CONTENT_URI, g, "contact_id = ?AND (data1 NOT NULL)", new String[]{str}, null);
    }

    public static Cursor c(Context context) {
        return context.getContentResolver().query(Data.CONTENT_URI, d, "(data1 NOT NULL) OR data1 NOT NULL", null, "contact_id ASC");
    }

    public static Cursor c(Context context, String str) {
        return context.getContentResolver().query(Email.CONTENT_URI, e, "contact_id = ? AND data1 NOT NULL", new String[]{str}, null);
    }

    public static Cursor d(Context context, String str) {
        return context.getContentResolver().query(Data.CONTENT_URI, null, "mimetype = 'vnd.android.cursor.item/postal-address_v2' AND contact_id = ?", new String[]{str}, null);
    }

    public static Cursor e(Context context, String str) {
        return context.getContentResolver().query(Data.CONTENT_URI, i, "mimetype = 'vnd.android.cursor.item/name' AND contact_id = ?", new String[]{str}, "data2");
    }
}
