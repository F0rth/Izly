package com.ezeeworld.b4s.android.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.ezeeworld.b4s.android.sdk.AsyncExecutor.RunnableEx;
import com.ezeeworld.b4s.android.sdk.playservices.AdvertisingServices;
import com.ezeeworld.b4s.android.sdk.server.Api2;
import com.ezeeworld.b4s.android.sdk.server.AppInfo;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;
import com.ezeeworld.b4s.android.sdk.server.Properties;
import com.ezeeworld.b4s.android.sdk.server.Properties.UserProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

import de.greenrobot.event.util.AsyncExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public final class B4SUserProperty {
    public static final String USER_ADDRESS = "user.address";
    public static final String USER_AGE = "user.age";
    public static final String USER_CITY = "user.city";
    public static final String USER_COUNTRY = "user.country";
    public static final String USER_CUSTOMER_REF = "user.customerRef";
    public static final String USER_EMAIL = "user.email";
    public static final String USER_FIRST_NAME = "user.firstName";
    public static final String USER_GENDER = "user.gender";
    public static final String USER_ID = "user.id";
    public static final String USER_LAST_NAME = "user.lastName";
    public static final String USER_PHONE = "user.phone";
    public static final String USER_ZIPCODE = "user.zipcode";
    private final Context a;
    private final SharedPreferences b;
    private Map<String, Object> c;
    private Map<String, Object> d;
    private final Object e;
    private boolean f;

    public enum Gender {
        Undefined,
        Female,
        Male
    }

    static class a {
        static final B4SUserProperty a = new B4SUserProperty();
    }

    private B4SUserProperty() {
        this.e = new Object();
        this.f = false;
        this.b = B4SSettings.get().getPreferences();
        this.a = B4SSettings.get().getApplicationContext();
    }

    private void a(Context context) {
        Object obj;
        int i = 0;
        String identifier = AdvertisingServices.get().getIdentifier();
        Boolean trackingEnabled = AdvertisingServices.get().getTrackingEnabled();
        int i2 = Device.areNotificationEnabled(context) ? 1 : 0;
        a("application.sdk.remoteNotificationsEnabled", Integer.valueOf(i2), true);
        a("application.sdk.localNotificationsEnabled", Integer.valueOf(i2), true);
        if (identifier == null) {
            obj = "0";
        } else {
            String str = identifier;
        }
        a("device.advertisingTracking.value", obj, true);
        i2 = trackingEnabled == null ? 2 : trackingEnabled.booleanValue() ? 0 : 1;
        a("device.advertisingTracking.enabled", Integer.valueOf(i2), true);
        store("application.sdk.version", "2.0.13-242");
        a("device.model", Device.getDeviceModel(), true);
        a("device.os.name", Device.a(), true);
        a("device.os.version", Device.b(), true);
        a("device.carrier", Device.c(), true);
        a("device.storage.total", Integer.valueOf(Device.d()), true);
        a("device.storage.free", Integer.valueOf(Device.e()), true);
        WifiManager wifiManager = (WifiManager) this.a.getSystemService("wifi");
        if (wifiManager != null) {
            if (wifiManager.isWifiEnabled()) {
                i = 1;
            }
            a("device.wifi.enabled", Integer.valueOf(i), true);
        }
    }

    private void a(Boolean bool) {
        try {
            Properties properties = new Properties();
            if (bool.booleanValue()) {
                properties.userProperties = b();
                this.b.edit().putString("b4s_system_properties", Api2.get().getJackson().writeValueAsString(properties)).apply();
                return;
            }
            properties.userProperties = c();
            this.b.edit().putString("b4s_user_properties", Api2.get().getJackson().writeValueAsString(properties)).apply();
        } catch (JsonProcessingException e) {
            B4SLog.e((Object) this, "Cannot persist user properties to JSON: " + e.toString());
        }
    }

    private void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                for (UserProperty userProperty : ((Properties) Api2.get().getJackson().readValue(str, Properties.class)).userProperties) {
                    this.c.put(userProperty.key, userProperty.value);
                }
            } catch (IOException e) {
                B4SLog.e((Object) this, "Cannot read user properties from JSON: " + e.toString());
            }
        }
    }

    private boolean a() {
        B4SLog.i((Object) this, "requiresUpload dirty=" + this.f + " lastupdated=" + this.b.getLong("b4s_userprop_lastupdated", 0) + " delta=" + (System.currentTimeMillis() - 21600000));
        return this.f || this.b.getLong("b4s_userprop_lastupdated", 0) < System.currentTimeMillis() - 21600000;
    }

    private boolean a(final String str, final Object obj, boolean z) {
        AsyncExecutor asyncExecutor = AsyncExecutor.get();
        final Boolean valueOf = Boolean.valueOf(z);
        asyncExecutor.execute(new RunnableEx(this) {
            final /* synthetic */ B4SUserProperty d;

            public void run() throws Exception {
                this.d.ensureInit();
                synchronized (this.d.e) {
                    Object obj;
                    if (valueOf.booleanValue()) {
                        if (obj == null && this.d.d.containsKey(str)) {
                            this.d.d.remove(str);
                            this.d.f = true;
                            this.d.a(Boolean.valueOf(true));
                        } else if (obj != null) {
                            obj = this.d.d.get(str);
                            if (!(obj != null && obj.getClass() == obj.getClass() && obj.equals(obj))) {
                                this.d.d.put(str, obj);
                                this.d.f = true;
                                this.d.a(Boolean.valueOf(true));
                            }
                        }
                    } else if (obj == null && this.d.c.containsKey(str)) {
                        this.d.c.remove(str);
                        this.d.f = true;
                        this.d.a(Boolean.valueOf(false));
                    } else if (obj != null) {
                        obj = this.d.c.get(str);
                        if (!(obj != null && obj.getClass() == obj.getClass() && obj.equals(obj))) {
                            this.d.c.put(str, obj);
                            this.d.f = true;
                            this.d.a(Boolean.valueOf(false));
                        }
                    }
                }
            }
        });
        return true;
    }

    private List<UserProperty> b() {
        List arrayList = new ArrayList();
        for (Entry entry : this.d.entrySet()) {
            UserProperty userProperty = new UserProperty();
            userProperty.key = (String) entry.getKey();
            userProperty.value = entry.getValue();
            arrayList.add(userProperty);
        }
        return arrayList;
    }

    private void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                for (UserProperty userProperty : ((Properties) Api2.get().getJackson().readValue(str, Properties.class)).userProperties) {
                    this.d.put(userProperty.key, userProperty.value);
                }
            } catch (IOException e) {
                B4SLog.e((Object) this, "Cannot read user properties from JSON: " + e.toString());
            }
        }
    }

    private List<UserProperty> c() {
        AppInfo cachedOnlyAppInfo = InteractionsApi.get().getCachedOnlyAppInfo();
        int i = (cachedOnlyAppInfo == null || cachedOnlyAppInfo.nMaxUserPropertiesCount < 0) ? 20 : cachedOnlyAppInfo.nMaxUserPropertiesCount;
        List arrayList = new ArrayList();
        int i2 = 0;
        for (Entry entry : this.c.entrySet()) {
            if (i2 >= i) {
                B4SLog.w((Object) this, "You attempt to override the maximum amount of allowed user properties (" + i + ") ! So key:" + ((String) entry.getKey()) + " is rejected");
            } else {
                UserProperty userProperty = new UserProperty();
                userProperty.key = (String) entry.getKey();
                userProperty.value = entry.getValue();
                arrayList.add(userProperty);
                i2++;
            }
        }
        return arrayList;
    }

    private List<UserProperty> d() {
        AppInfo cachedOnlyAppInfo = InteractionsApi.get().getCachedOnlyAppInfo();
        int i = (cachedOnlyAppInfo == null || cachedOnlyAppInfo.nMaxUserPropertiesCount < 0) ? 20 : cachedOnlyAppInfo.nMaxUserPropertiesCount;
        List arrayList = new ArrayList();
        int i2 = 0;
        for (Entry entry : this.c.entrySet()) {
            if (i2 >= i) {
                B4SLog.w((Object) this, "You attempt to override the maximum amount of allowed user properties (" + i + ") ! So key:" + ((String) entry.getKey()) + " is rejected");
            } else {
                UserProperty userProperty = new UserProperty();
                userProperty.key = (String) entry.getKey();
                userProperty.value = entry.getValue();
                arrayList.add(userProperty);
                i2++;
            }
        }
        for (Entry entry2 : this.d.entrySet()) {
            UserProperty userProperty2 = new UserProperty();
            userProperty2.key = (String) entry2.getKey();
            userProperty2.value = entry2.getValue();
            arrayList.add(userProperty2);
        }
        return arrayList;
    }

    public static B4SUserProperty get() {
        return a.a;
    }

    public final boolean delete(String str) {
        ensureInit();
        synchronized (this.e) {
            if (this.c.containsKey(str)) {
                this.c.remove(str);
                this.f = true;
                a(Boolean.valueOf(false));
                return true;
            }
            return false;
        }
    }

    public final void ensureInit() {
        if (this.c == null) {
            synchronized (this.e) {
                if (this.c == null) {
                    this.c = new ConcurrentHashMap();
                    a(this.b.getString("b4s_user_properties", null));
                }
            }
        }
        if (this.d == null) {
            synchronized (this.e) {
                if (this.d == null) {
                    this.d = new ConcurrentHashMap();
                    b(this.b.getString("b4s_system_properties", null));
                }
            }
        }
    }

    public final <T> T get(String str) {
        ensureInit();
        T t = this.d.get(str);
        return t == null ? this.c.get(str) : t;
    }

    public final int getCount() {
        int size;
        ensureInit();
        synchronized (this.e) {
            size = this.c.size();
        }
        return size;
    }

    public final boolean store(String str, Gender gender) {
        return a(str, gender, false);
    }

    public final boolean store(String str, Float f) {
        return a(str, f, false);
    }

    public final boolean store(String str, Integer num) {
        return a(str, num, false);
    }

    public final boolean store(String str, String str2) {
        return a(str, str2, false);
    }

    public final boolean store(String str, Date date) {
        return a(str, date, false);
    }

    public final boolean store(String str, List list) {
        Object obj = null;
        for (Object next : list) {
            if (obj == null) {
                obj = next;
            } else if (obj.getClass() != next.getClass()) {
                return false;
            }
        }
        return a(str, list, false);
    }

    public final boolean uploadIfNeeded(Context context) {
        if (a()) {
            if (context == null) {
                context = this.a;
            }
            B4SLog.i((Object) this, "Property upload");
            a(context);
            Properties properties = new Properties();
            properties.userProperties = get().d();
            boolean registerProperties = InteractionsApi.get().registerProperties(properties);
            if (!registerProperties) {
                return registerProperties;
            }
            this.b.edit().putLong("b4s_userprop_lastupdated", System.currentTimeMillis()).apply();
            return registerProperties;
        }
        B4SLog.i((Object) this, "Property do not require upload");
        return false;
    }
}
