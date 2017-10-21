package defpackage;

import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class kh {
    public static final String ACCEPT_JSON_VALUE = "application/json";
    public static final String ANDROID_CLIENT_TYPE = "android";
    public static final String CLS_ANDROID_SDK_DEVELOPER_TOKEN = "470fa2b4ae81cd56ecbcda9735803434cec591fa";
    public static final String CRASHLYTICS_USER_AGENT = "Crashlytics Android SDK/";
    public static final int DEFAULT_TIMEOUT = 10000;
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_API_KEY = "X-CRASHLYTICS-API-KEY";
    public static final String HEADER_CLIENT_TYPE = "X-CRASHLYTICS-API-CLIENT-TYPE";
    public static final String HEADER_CLIENT_VERSION = "X-CRASHLYTICS-API-CLIENT-VERSION";
    public static final String HEADER_DEVELOPER_TOKEN = "X-CRASHLYTICS-DEVELOPER-TOKEN";
    public static final String HEADER_REQUEST_ID = "X-REQUEST-ID";
    public static final String HEADER_USER_AGENT = "User-Agent";
    private static final Pattern PROTOCOL_AND_HOST_PATTERN = Pattern.compile("http(s?)://[^\\/]+", 2);
    protected final jy kit;
    private final int method$6bc89afe;
    private final String protocolAndHostOverride;
    private final mh requestFactory;
    private final String url;

    public kh(jy jyVar, String str, String str2, mh mhVar, int i) {
        if (str2 == null) {
            throw new IllegalArgumentException("url must not be null.");
        } else if (mhVar == null) {
            throw new IllegalArgumentException("requestFactory must not be null.");
        } else {
            this.kit = jyVar;
            this.protocolAndHostOverride = str;
            this.url = overrideProtocolAndHost(str2);
            this.requestFactory = mhVar;
            this.method$6bc89afe = i;
        }
    }

    private String overrideProtocolAndHost(String str) {
        return !kp.d(this.protocolAndHostOverride) ? PROTOCOL_AND_HOST_PATTERN.matcher(str).replaceFirst(this.protocolAndHostOverride) : str;
    }

    protected mg getHttpRequest() {
        return getHttpRequest(Collections.emptyMap());
    }

    protected mg getHttpRequest(Map<String, String> map) {
        mg a = this.requestFactory.a(this.method$6bc89afe, getUrl(), map);
        a.a().setUseCaches(false);
        a.a().setConnectTimeout(DEFAULT_TIMEOUT);
        return a.a(HEADER_USER_AGENT, new StringBuilder(CRASHLYTICS_USER_AGENT).append(this.kit.getVersion()).toString()).a(HEADER_DEVELOPER_TOKEN, CLS_ANDROID_SDK_DEVELOPER_TOKEN);
    }

    protected String getUrl() {
        return this.url;
    }
}
