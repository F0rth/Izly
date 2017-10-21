package com.ad4screen.sdk.service.modules.k.f.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.common.c;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.d.b;

import java.util.Iterator;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a implements com.ad4screen.sdk.service.modules.k.b.a {
    private static int a;
    private static long b;
    private static String c;
    private com.ad4screen.sdk.A4SService.a d;
    private com.ad4screen.sdk.common.a e = g.e();

    public static class a {
        private String a;
        private Uri b;
        private JSONObject c;
        private Bundle d;

        private a() {
        }

        private static Bundle a(JSONObject jSONObject) throws JSONException {
            Bundle bundle = new Bundle();
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                Object obj = jSONObject.get(str);
                if (obj instanceof JSONObject) {
                    bundle.putBundle(str, a((JSONObject) obj));
                } else if (obj instanceof JSONArray) {
                    JSONArray jSONArray = (JSONArray) obj;
                    if (jSONArray.length() == 0) {
                        bundle.putStringArray(str, new String[0]);
                    } else {
                        Object obj2 = jSONArray.get(0);
                        int i;
                        if (obj2 instanceof JSONObject) {
                            Parcelable[] parcelableArr = new Bundle[jSONArray.length()];
                            for (i = 0; i < jSONArray.length(); i++) {
                                parcelableArr[i] = a(jSONArray.getJSONObject(i));
                            }
                            bundle.putParcelableArray(str, parcelableArr);
                        } else if (obj2 instanceof JSONArray) {
                            throw new UnsupportedOperationException("Nested arrays are not supported.");
                        } else {
                            String[] strArr = new String[jSONArray.length()];
                            for (i = 0; i < jSONArray.length(); i++) {
                                strArr[i] = jSONArray.get(i).toString();
                            }
                            bundle.putStringArray(str, strArr);
                        }
                    }
                } else {
                    bundle.putString(str, obj.toString());
                }
            }
            return bundle;
        }

        public static a a(String str) {
            a aVar = null;
            if (str != null) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    Object optString = jSONObject.optString("applink_args");
                    long optLong = jSONObject.optLong("click_time", -1);
                    String optString2 = jSONObject.optString("applink_class");
                    String optString3 = jSONObject.optString("applink_url");
                    if (!TextUtils.isEmpty(optString)) {
                        aVar = b(optString);
                        if (optLong != -1) {
                            try {
                                if (aVar.c != null) {
                                    aVar.c.put("com.facebook.platform.APPLINK_TAP_TIME_UTC", optLong);
                                }
                                if (aVar.d != null) {
                                    aVar.d.putString("com.facebook.platform.APPLINK_TAP_TIME_UTC", Long.toString(optLong));
                                }
                            } catch (JSONException e) {
                                Log.debug("Facebook.AppLinkData|Unable to put tap time in AppLinkData.arguments");
                            }
                        }
                        if (optString2 != null) {
                            try {
                                if (aVar.c != null) {
                                    aVar.c.put("com.facebook.platform.APPLINK_NATIVE_CLASS", optString2);
                                }
                                if (aVar.d != null) {
                                    aVar.d.putString("com.facebook.platform.APPLINK_NATIVE_CLASS", optString2);
                                }
                            } catch (JSONException e2) {
                                Log.debug("Facebook.AppLinkData|Unable to put tap time in AppLinkData.arguments");
                            }
                        }
                        if (optString3 != null) {
                            try {
                                if (aVar.c != null) {
                                    aVar.c.put("com.facebook.platform.APPLINK_NATIVE_URL", optString3);
                                }
                                if (aVar.d != null) {
                                    aVar.d.putString("com.facebook.platform.APPLINK_NATIVE_URL", optString3);
                                }
                            } catch (JSONException e3) {
                                Log.debug("Facebook.AppLinkData|Unable to put tap time in AppLinkData.arguments");
                            }
                        }
                    }
                } catch (Exception e4) {
                    Log.debug("Facebook.AppLinkData|Unable to fetch deferred applink from server");
                }
            }
            return aVar;
        }

        public static void a(Context context) {
            com.ad4screen.sdk.common.a.a.a(context).a(new b(context));
        }

        public static a b(String str) {
            if (str != null) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    String string = jSONObject.getString("version");
                    if ("applink".equals(jSONObject.getJSONObject("bridge_args").getString("method")) && "2".equals(string)) {
                        a aVar = new a();
                        aVar.c = jSONObject.getJSONObject("method_args");
                        if (aVar.c.has("ref")) {
                            aVar.a = aVar.c.getString("ref");
                        } else if (aVar.c.has("referer_data")) {
                            jSONObject = aVar.c.getJSONObject("referer_data");
                            if (jSONObject.has("fb_ref")) {
                                aVar.a = jSONObject.getString("fb_ref");
                            }
                        }
                        if (aVar.c.has("target_url")) {
                            aVar.b = Uri.parse(aVar.c.getString("target_url"));
                        }
                        aVar.d = a(aVar.c);
                        return aVar;
                    }
                } catch (Throwable e) {
                    Log.debug("Facebook.AppLinkData|Unable to parse AppLink JSON", e);
                    return null;
                }
            }
            return null;
        }

        public Uri a() {
            return this.b;
        }

        public Bundle b() {
            return this.d;
        }
    }

    public a(com.ad4screen.sdk.A4SService.a aVar) {
        this.d = aVar;
    }

    private static int a(long j) {
        int i = 0;
        while (i < c.a.length && c.a[i] < j) {
            i++;
        }
        return i;
    }

    private JSONArray a(Cart cart) {
        JSONObject a = a(this.d.a(), "fb_mobile_add_to_cart", cart.getItem().getCategory() + " - " + cart.getItem().getLabel(), cart.getItem().getCurrency(), cart.getItem().getId(), cart.getItem().getPrice() * ((double) cart.getItem().getQuantity()));
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(a);
        return jSONArray;
    }

    private JSONArray a(Lead lead) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        JSONObject a = a(this.d.a(), "fb_mobile_complete_registration", null, null, null, 0.0d);
        a.put("fb_registration_method", lead.getLabel() + " - " + lead.getValue());
        jSONArray.put(a);
        return jSONArray;
    }

    private JSONArray a(Purchase purchase) {
        return purchase.getItems() != null ? b(purchase) : c(purchase);
    }

    private static JSONObject a(Context context, String str, String str2, String str3, String str4, double d) {
        JSONObject jSONObject = new JSONObject();
        if (str2 != null) {
            try {
                jSONObject.put("fb_content_type", str2);
            } catch (Throwable e) {
                Log.error("Facebook|Error while creating event", e);
            } catch (Throwable e2) {
                Log.error("Facebook|Error while creating event", e2);
            } catch (RuntimeException e3) {
            }
        }
        if (str3 != null) {
            jSONObject.put("fb_currency", str3);
        }
        if (str4 != null) {
            jSONObject.put("fb_content_id", str4);
        }
        if (str != null) {
            jSONObject.put("_eventName", str);
        }
        if (d != 0.0d) {
            jSONObject.put("_valueToSum", d);
        }
        jSONObject.put("_logTime", System.currentTimeMillis());
        jSONObject.put("_appVersion", context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
        return jSONObject;
    }

    protected static boolean a(Context context) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        JSONObject a = a(context, "fb_mobile_activate_app", null, null, null, 0.0d);
        a.put("fb_mobile_launch_source", c);
        jSONArray.put(a);
        return a(context, jSONArray, new Bundle());
    }

    protected static boolean a(Context context, long j, long j2) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        JSONObject a = a(context, "fb_mobile_deactivate_app", null, null, null, (double) j2);
        a.put("fb_mobile_launch_source", c);
        a.put("fb_mobile_app_interruptions", a);
        a.put("fb_mobile_time_between_sessions", String.format(Locale.US, "session_quanta_%d", new Object[]{Integer.valueOf(a(j))}));
        jSONArray.put(a);
        a = 0;
        return a(context, jSONArray, new Bundle());
    }

    private static boolean a(Context context, JSONArray jSONArray, Bundle bundle) {
        com.ad4screen.sdk.common.a.a.a(context).a(new d(context, jSONArray, bundle));
        return true;
    }

    private JSONArray b(Purchase purchase) {
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < purchase.getItems().length; i++) {
            jSONArray.put(a(this.d.a(), "fb_mobile_purchase", purchase.getItems()[i].getCategory() + " - " + purchase.getItems()[i].getLabel(), purchase.getItems()[i].getCurrency(), purchase.getItems()[i].getId(), purchase.getItems()[i].getPrice() * ((double) purchase.getItems()[i].getQuantity())));
        }
        return jSONArray;
    }

    private JSONArray c(Purchase purchase) {
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(a(this.d.a(), "fb_mobile_purchase", null, purchase.getCurrency(), purchase.getId(), purchase.getTotalPrice()));
        return jSONArray;
    }

    public String a() {
        return "Facebook";
    }

    public void a(long j, Bundle bundle, String... strArr) {
        Log.debug("Facebook|This event can't be dispatched to Facebook (not supported)");
    }

    public void a(Cart cart, Bundle bundle) {
        a(this.d.a(), a(cart), bundle);
    }

    public void a(Lead lead, Bundle bundle) {
        try {
            a(this.d.a(), a(lead), bundle);
        } catch (Throwable e) {
            Log.internal("Fail to create JSONArray from Lead", e);
        }
    }

    public void a(Purchase purchase, Bundle bundle) {
        a(this.d.a(), a(purchase), bundle);
    }

    public void a(String str) {
        if (b.a(this.d.a()).L()) {
            Log.debug("Facebook|Now retrieving any Deferred AppLink to trigger");
            a.a(this.d.a());
            return;
        }
        Log.debug("Facebook|Deferred AppLink is disabled. Please see our doc if you want to activate it");
    }

    public int b() {
        return 4;
    }

    public void c() {
    }

    public void d() {
        SharedPreferences sharedPreferences = this.d.a().getSharedPreferences("FacebookSession", 0);
        a = sharedPreferences.getInt("interruptions", 0);
        long j = sharedPreferences.getLong("time", 0);
        b = this.e.b();
        long j2 = sharedPreferences.getLong("millisecondsSpentInSession", 0) <= 0 ? 1000 : sharedPreferences.getLong("millisecondsSpentInSession", 0);
        long b = this.e.b() - j;
        if (b > 60000) {
            if (j > 0) {
                try {
                    a(this.d.a(), b, j2 / 1000);
                    sharedPreferences.edit().putLong("millisecondsSpentInSession", 0).commit();
                    j2 = 0;
                } catch (Throwable e) {
                    Log.internal("Failed to create JSON for deactivateApp event", e);
                }
            } else {
                j2 = j;
            }
            j = j2;
        } else if (b > 1000) {
            a++;
            sharedPreferences.edit().putInt("interruptions", a).commit();
        }
        if (a == 0) {
            c = c.e(this.d.a());
        }
        int i = this.e.b() - sharedPreferences.getLong("activateTime", 0) > 300000 ? 1 : 0;
        if (j == 0 || i != 0) {
            com.ad4screen.sdk.common.a.a.a(this.d.a()).a(new e(this.d.a()));
            sharedPreferences.edit().putLong("activateTime", this.e.b()).commit();
        }
    }

    public void e() {
        SharedPreferences sharedPreferences = this.d.a().getSharedPreferences("FacebookSession", 0);
        long b = this.e.b() - b;
        if (b < 0) {
            b = 0;
        }
        sharedPreferences.edit().putLong("time", this.e.b()).putLong("millisecondsSpentInSession", b + sharedPreferences.getLong("millisecondsSpentInSession", 0)).commit();
    }
}
