package com.ezeeworld.b4s.android.sdk.server;

import android.os.Build.VERSION;

import com.ezeeworld.b4s.android.sdk.B4SSettings;
import com.ezeeworld.b4s.android.sdk.BuildConfig;
import com.ezeeworld.b4s.android.sdk.Device;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Api2 {
    public static final String HEADER_APIKEY = "x-b4s-api-key";
    public static final String HEADER_APPID = "x-b4s-app-id";
    public static final String HEADER_CACHED = "cached";
    public static final String HEADER_CALLGROUP = "x-b4s-call-group";
    public static final String HEADER_CALLGROUP_CACHED = "x-b4s-call-group: cached";
    public static final String HEADER_CALLGROUP_LIVE = "x-b4s-call-group: live";
    public static final String HEADER_CALLGROUP_LOGGING = "x-b4s-call-group: logging";
    public static final String HEADER_CALLGROUP_SESSIONS = "x-b4s-call-group: sessions";
    public static final String HEADER_CALLGROUP_TRACKING = "x-b4s-call-group: tracking";
    public static final String HEADER_COUNTRY = "x-b4s-country";
    public static final String HEADER_LIVE = "live";
    public static final String HEADER_LOGGING = "logging";
    public static final String HEADER_SESSIONS = "sessions";
    public static final String HEADER_TRACKING = "tracking";
    public static final String HEADER_TZ = "x-b4s-tz";
    public static final String HEADER_UDID = "x-b4s-udid";
    public static final SimpleDateFormat RFC3339_FORMAT;
    private final OkHttpClient a;
    private final ObjectMapper b;
    private final b c;
    private String d;
    private Retrofit e;
    private Map<Class, Object> f;

    enum a {
        Live(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, Api2.HEADER_LIVE),
        Cached(10, Api2.HEADER_CACHED),
        Tracking(20, Api2.HEADER_TRACKING),
        Sessions(100, Api2.HEADER_SESSIONS),
        Logging(100, Api2.HEADER_LOGGING),
        Other(30, null);

        private final int g;
        private final String h;
        private long i;
        private int j;

        private a(int i, String str) {
            this.g = i;
            this.h = str;
            this.i = System.currentTimeMillis();
            this.j = 0;
        }

        public final String a() {
            return this.h;
        }

        public final boolean b() {
            if (this.h == Api2.HEADER_CACHED) {
                if (this.i >= System.currentTimeMillis() - 300000) {
                    return false;
                }
            } else if (this.i >= System.currentTimeMillis() - 60000) {
                return false;
            }
            return true;
        }

        public final void c() {
            this.i = System.currentTimeMillis();
            this.j = 0;
        }

        public final boolean d() {
            return this.j >= this.g;
        }

        public final void e() {
            this.j++;
        }

        public final void f() {
            this.j = 0;
        }
    }

    class b implements Interceptor {
        final /* synthetic */ Api2 a;
        private final Map<String, a> b = new HashMap();

        public b(Api2 api2) {
            this.a = api2;
            for (a aVar : a.values()) {
                this.b.put(aVar.a(), aVar);
            }
        }

        public void a() {
            for (a f : this.b.values()) {
                f.f();
            }
        }

        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            a aVar = (a) this.b.get(request.headers().get(Api2.HEADER_CALLGROUP));
            if (aVar.b()) {
                aVar.c();
            }
            if (aVar.d()) {
                return new Builder().request(request).protocol(Protocol.HTTP_1_1).code(500).body(ResponseBody.create(MediaType.parse("application/json"), "")).build();
            }
            aVar.e();
            return chain.proceed(request);
        }
    }

    static class c {
        static final Api2 a = new Api2();
    }

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        RFC3339_FORMAT = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private Api2() {
        this.b = new ObjectMapper();
        this.b.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.b.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        this.b.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final B4SSettings b4SSettings = B4SSettings.get();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(120, TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor(this) {
            final /* synthetic */ Api2 b;

            public Response intercept(Chain chain) throws IOException {
                String str;
                String str2;
                if (b4SSettings != null) {
                    str = b4SSettings.currentCountry;
                    str2 = b4SSettings.currentTimezone;
                } else {
                    str = "";
                    str2 = "";
                }
                Request.Builder newBuilder = chain.request().newBuilder();
                Locale locale = Locale.US;
                String str3 = VERSION.RELEASE;
                String deviceModel = Device.getDeviceModel();
                String appPackage = b4SSettings == null ? "" : b4SSettings.getAppPackage();
                String appVersion = b4SSettings == null ? "" : b4SSettings.getAppVersion();
                return chain.proceed(newBuilder.addHeader("User-Agent", String.format(locale, "%1$s;%2$s;%3$s;%4$s;%5$s;%6$s-%7$s", new Object[]{"Android", str3, deviceModel, appPackage, appVersion, "2.0.13", BuildConfig.GIT_BRANCH})).addHeader(Api2.HEADER_APIKEY, "a15d43fa826e92555d8a25808dc2f3c5").addHeader(Api2.HEADER_APPID, B4SSettings.get().getAppId()).addHeader(Api2.HEADER_UDID, Device.getDeviceId()).addHeader(Api2.HEADER_TZ, str2).addHeader(Api2.HEADER_COUNTRY, str).build());
            }
        });
        this.c = new b(this);
        builder.addInterceptor(this.c);
        this.a = builder.build();
        this.d = "https://api.beaconforstore.com/";
        this.f = new HashMap();
        a();
    }

    private void a() {
        this.e = new Retrofit.Builder().baseUrl(this.d).client(this.a).addConverterFactory(new a()).addConverterFactory(JacksonConverterFactory.create(this.b)).build();
        for (Class cls : this.f.keySet()) {
            this.f.put(cls, this.e.create(cls));
        }
    }

    public static Api2 get() {
        return c.a;
    }

    public static void setLogApi(boolean z) {
    }

    public static void switchEndpoint(boolean z, boolean z2) {
        String str = z ? "http://api.preprod.beaconforstore.com/" : z2 ? "http://api.dev.beaconforstore.com/" : "https://api.beaconforstore.com/";
        Api2 api2 = get();
        if (!api2.d.equals(str)) {
            api2.d = str;
            api2.a();
            if (B4SSettings.isInitialized()) {
                InteractionsApi.get().clearCache();
            }
            api2.c.a();
        }
    }

    public ObjectMapper getJackson() {
        return this.b;
    }

    public <T> T getRoutes(Class<T> cls) {
        if (this.f.containsKey(cls)) {
            return this.f.get(cls);
        }
        T create = this.e.create(cls);
        this.f.put(cls, create);
        return create;
    }
}
