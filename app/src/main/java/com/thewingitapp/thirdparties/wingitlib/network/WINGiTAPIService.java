package com.thewingitapp.thirdparties.wingitlib.network;

import com.thewingitapp.thirdparties.wingitlib.BuildConfig;
import com.thewingitapp.thirdparties.wingitlib.network.api.IWINGiTAPI;
import com.thewingitapp.thirdparties.wingitlib.network.interceptor.HeaderInterceptor;
import com.thewingitapp.thirdparties.wingitlib.network.security.WINGiTTrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WINGiTAPIService {
    private static Retrofit S_RETROFIT = new Builder().baseUrl(BuildConfig.WINGiT_API_URL).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).addInterceptor(new HeaderInterceptor()).build()).addConverterFactory(GsonConverterFactory.create()).build();

    public static IWINGiTAPI getSSLService() {
        try {
            OkHttpClient.Builder addInterceptor = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).addInterceptor(new HeaderInterceptor());
            SSLContext instance = SSLContext.getInstance("SSL");
            WINGiTTrustManager instance2 = WINGiTTrustManager.getInstance();
            TrustManager[] trustManagerArr = new TrustManager[]{instance2};
            instance.init(null, trustManagerArr, new SecureRandom());
            addInterceptor.sslSocketFactory(instance.getSocketFactory(), WINGiTTrustManager.getInstance());
            return (IWINGiTAPI) new Builder().baseUrl(BuildConfig.WINGiT_API_URL).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(addInterceptor.build()).addConverterFactory(GsonConverterFactory.create()).build().create(IWINGiTAPI.class);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e2) {
            e2.printStackTrace();
        }
        return getService();
    }

    public static IWINGiTAPI getService() {
        return (IWINGiTAPI) S_RETROFIT.create(IWINGiTAPI.class);
    }
}
