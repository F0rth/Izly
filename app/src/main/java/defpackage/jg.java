package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.gson.Gson;
import com.thewingitapp.thirdparties.wingitlib.model.WGCity;
import org.spongycastle.asn1.cmp.PKIFailureInfo;

public final class jg {
    private static final String a = null;

    public static String a(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("fr.smoney.android.izly.config.sp", 0);
        return sharedPreferences.getInt("sharedPrefElemDeviceAppVersion", PKIFailureInfo.systemUnavail) != jg.c(context) ? null : sharedPreferences.getString("sharedPrefElemDeviceRegistrationId", null);
    }

    public static void a(Context context, WGCity wGCity) {
        Editor edit = context.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).edit();
        edit.putString("sharedPrefWingitCity", wGCity == null ? null : new Gson().toJson(wGCity));
        edit.commit();
    }

    public static void a(Context context, String str) {
        int c = jg.c(context);
        Editor edit = context.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).edit();
        edit.putString("sharedPrefElemDeviceRegistrationId", str);
        edit.putInt("sharedPrefElemDeviceAppVersion", c);
        edit.commit();
    }

    public static WGCity b(Context context) {
        String string = context.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).getString("sharedPrefWingitCity", null);
        return string == null ? null : (WGCity) new Gson().fromJson(string, WGCity.class);
    }

    private static int c(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
}
