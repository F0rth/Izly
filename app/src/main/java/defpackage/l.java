package defpackage;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings.Secure;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class l extends SQLiteOpenHelper implements jo {
    public pa a;
    public List b;
    private String c;
    private Context d;

    public l(Context context, String str) {
        super(context, "request", null, 1);
        this.c = str;
        this.d = context;
    }

    private String c() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            int i = 0;
            while (i < this.b.size() && i < 15) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("scan_time", ((c) this.b.get(i)).b);
                jSONObject2.put("sdk_version", ((c) this.b.get(i)).c);
                jSONObject2.put("found_date", ((c) this.b.get(i)).a);
                jSONObject2.put("scan_date", ((c) this.b.get(i)).d);
                jSONArray.put(jSONObject2);
                i++;
            }
            jSONObject.put("app_name", d());
            jSONObject.put("package_name", this.d.getPackageName());
            jSONObject.put("token", this.c);
            jSONObject.put("os", kh.ANDROID_CLIENT_TYPE);
            jSONObject.put("udid", Secure.getString(this.d.getContentResolver(), "android_id"));
            jSONObject.put("request", jSONArray);
        } catch (Exception e) {
        }
        return jSONObject.toString();
    }

    private String d() {
        String str = this.d.getApplicationInfo().name;
        if (str == null) {
            PackageManager packageManager = this.d.getPackageManager();
            try {
                str = packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.d.getPackageName(), 0)).toString();
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public void a() {
        int i = pb.b;
        this.a = new pa("https://scanpay.it/scan_card");
        this.a.c = this;
        this.a.a(c());
    }

    public final void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("scan");
            if (string.equals("success")) {
                for (int i = jSONObject.getInt("nb_request"); i > 0 && this.b.size() > 0; i--) {
                    c cVar = (c) this.b.get(0);
                    try {
                        SQLiteDatabase writableDatabase = getWritableDatabase();
                        writableDatabase.execSQL("DELETE FROM REQUESTS WHERE scan_date ='" + cVar.d + "'");
                        writableDatabase.close();
                    } catch (Exception e) {
                    }
                    this.b.remove(0);
                }
                b();
            } else if (string.equals("fail")) {
                this.d.getSharedPreferences("scanpay", 0).edit().putBoolean("isBanFromScanpay", false).commit();
            }
        } catch (JSONException e2) {
        }
    }

    public final void b() {
        this.b = new ArrayList();
        try {
            SQLiteDatabase readableDatabase = getReadableDatabase();
            Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM REQUESTS LIMIT 15", null);
            if (rawQuery == null || !rawQuery.moveToFirst()) {
                rawQuery.close();
                readableDatabase.close();
                if (((this.a == null && !this.a.a.booleanValue()) || this.a == null) && this.b.size() > 0) {
                    a();
                    return;
                }
                return;
            }
            do {
                c cVar = new c();
                cVar.b = rawQuery.getString(rawQuery.getColumnIndex("scan_time"));
                cVar.a = rawQuery.getString(rawQuery.getColumnIndex("founddate"));
                cVar.d = rawQuery.getString(rawQuery.getColumnIndex("scan_date"));
                cVar.c = "2.1";
                this.b.add(cVar);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            readableDatabase.close();
            if (this.a == null) {
            }
        } catch (Exception e) {
        }
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS REQUESTS (ID INTEGER PRIMARY KEY AUTOINCREMENT, scan_time TEXT, founddate TEXT, scan_date TEXT)");
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
