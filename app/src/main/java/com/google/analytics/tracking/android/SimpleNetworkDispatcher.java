package com.google.analytics.tracking.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

class SimpleNetworkDispatcher implements Dispatcher {
    private static final String USER_AGENT_TEMPLATE = "%s/%s (Linux; U; Android %s; %s; %s Build/%s)";
    private final Context ctx;
    private GoogleAnalytics gaInstance;
    private final HttpClient httpClient;
    private URL mOverrideHostUrl;
    private final String userAgent;

    SimpleNetworkDispatcher(HttpClient httpClient, Context context) {
        this(httpClient, GoogleAnalytics.getInstance(context), context);
    }

    @VisibleForTesting
    SimpleNetworkDispatcher(HttpClient httpClient, GoogleAnalytics googleAnalytics, Context context) {
        this.ctx = context.getApplicationContext();
        this.userAgent = createUserAgentString("GoogleAnalytics", "3.0", VERSION.RELEASE, Utils.getLanguage(Locale.getDefault()), Build.MODEL, Build.ID);
        this.httpClient = httpClient;
        this.gaInstance = googleAnalytics;
    }

    private HttpEntityEnclosingRequest buildRequest(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            Log.w("Empty hit, discarding.");
            return null;
        }
        HttpEntityEnclosingRequest basicHttpEntityEnclosingRequest;
        String str3 = str2 + "?" + str;
        if (str3.length() < 2036) {
            basicHttpEntityEnclosingRequest = new BasicHttpEntityEnclosingRequest("GET", str3);
        } else {
            basicHttpEntityEnclosingRequest = new BasicHttpEntityEnclosingRequest("POST", str2);
            try {
                basicHttpEntityEnclosingRequest.setEntity(new StringEntity(str));
            } catch (UnsupportedEncodingException e) {
                Log.w("Encoding error, discarding hit");
                return null;
            }
        }
        basicHttpEntityEnclosingRequest.addHeader("User-Agent", this.userAgent);
        return basicHttpEntityEnclosingRequest;
    }

    private void logDebugInformation(HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Object obj : httpEntityEnclosingRequest.getAllHeaders()) {
            stringBuffer.append(obj.toString()).append("\n");
        }
        stringBuffer.append(httpEntityEnclosingRequest.getRequestLine().toString()).append("\n");
        if (httpEntityEnclosingRequest.getEntity() != null) {
            try {
                InputStream content = httpEntityEnclosingRequest.getEntity().getContent();
                if (content != null) {
                    int available = content.available();
                    if (available > 0) {
                        byte[] bArr = new byte[available];
                        content.read(bArr);
                        stringBuffer.append("POST:\n");
                        stringBuffer.append(new String(bArr)).append("\n");
                    }
                }
            } catch (IOException e) {
                Log.v("Error Writing hit to log...");
            }
        }
        Log.v(stringBuffer.toString());
    }

    public void close() {
        this.httpClient.getConnectionManager().shutdown();
    }

    String createUserAgentString(String str, String str2, String str3, String str4, String str5, String str6) {
        return String.format(USER_AGENT_TEMPLATE, new Object[]{str, str2, str3, str4, str5, str6});
    }

    public int dispatchHits(List<Hit> list) {
        int i;
        int min = Math.min(list.size(), 40);
        Object obj = 1;
        int i2 = 0;
        int i3 = 0;
        while (i3 < min) {
            Hit hit = (Hit) list.get(i3);
            URL url = getUrl(hit);
            if (url == null) {
                if (Log.isVerbose()) {
                    Log.w("No destination: discarding hit: " + hit.getHitParams());
                } else {
                    Log.w("No destination: discarding hit.");
                }
                i = i2 + 1;
            } else {
                HttpHost httpHost = new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
                String path = url.getPath();
                String postProcessHit = TextUtils.isEmpty(hit.getHitParams()) ? "" : HitBuilder.postProcessHit(hit, System.currentTimeMillis());
                HttpRequest buildRequest = buildRequest(postProcessHit, path);
                if (buildRequest == null) {
                    i = i2 + 1;
                } else {
                    buildRequest.addHeader("Host", httpHost.toHostString());
                    if (Log.isVerbose()) {
                        logDebugInformation(buildRequest);
                    }
                    if (postProcessHit.length() > 8192) {
                        Log.w("Hit too long (> 8192 bytes)--not sent");
                    } else if (this.gaInstance.isDryRunEnabled()) {
                        Log.i("Dry run enabled. Hit not actually sent.");
                    } else {
                        if (obj != null) {
                            try {
                                GANetworkReceiver.sendRadioPoweredBroadcast(this.ctx);
                                obj = null;
                            } catch (ClientProtocolException e) {
                                Log.w("ClientProtocolException sending hit; discarding hit...");
                                i = i2 + 1;
                                i3++;
                                i2 = i;
                            } catch (IOException e2) {
                                Log.w("Exception sending hit: " + e2.getClass().getSimpleName());
                                Log.w(e2.getMessage());
                                return i2;
                            }
                        }
                        try {
                            HttpResponse execute = this.httpClient.execute(httpHost, buildRequest);
                            int statusCode = execute.getStatusLine().getStatusCode();
                            HttpEntity entity = execute.getEntity();
                            if (entity != null) {
                                entity.consumeContent();
                            }
                            if (statusCode != 200) {
                                Log.w("Bad response: " + execute.getStatusLine().getStatusCode());
                            }
                        } catch (ClientProtocolException e3) {
                            Log.w("ClientProtocolException sending hit; discarding hit...");
                            i = i2 + 1;
                            i3++;
                            i2 = i;
                        } catch (IOException e22) {
                            Log.w("Exception sending hit: " + e22.getClass().getSimpleName());
                            Log.w(e22.getMessage());
                            return i2;
                        }
                    }
                    i = i2 + 1;
                }
            }
            i3++;
            i2 = i;
        }
        return i2;
    }

    @VisibleForTesting
    URL getUrl(Hit hit) {
        if (this.mOverrideHostUrl != null) {
            return this.mOverrideHostUrl;
        }
        try {
            return new URL("http:".equals(hit.getHitUrlScheme()) ? "http://www.google-analytics.com/collect" : "https://ssl.google-analytics.com/collect");
        } catch (MalformedURLException e) {
            Log.e("Error trying to parse the hardcoded host url. This really shouldn't happen.");
            return null;
        }
    }

    public boolean okToDispatch() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.ctx.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        Log.v("...no network connectivity");
        return false;
    }

    @VisibleForTesting
    public void overrideHostUrl(String str) {
        try {
            this.mOverrideHostUrl = new URL(str);
        } catch (MalformedURLException e) {
            this.mOverrideHostUrl = null;
        }
    }
}
