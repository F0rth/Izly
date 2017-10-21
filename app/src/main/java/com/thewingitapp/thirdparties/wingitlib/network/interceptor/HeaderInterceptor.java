package com.thewingitapp.thirdparties.wingitlib.network.interceptor;

import android.os.Build;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.util.Base64;
import com.thewingitapp.thirdparties.wingitlib.BuildConfig;
import com.thewingitapp.thirdparties.wingitlib.WINGiTConstant;
import defpackage.nw;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Request;
import okhttp3.Response;
import org.spongycastle.crypto.tls.CipherSuite;

public final class HeaderInterceptor implements Interceptor {
    private static String generateOneTimeRequestNonce() {
        byte[] bArr = new byte[32];
        new SecureRandom().nextBytes(bArr);
        return new String(Base64.encode(bArr, 2));
    }

    @Nullable
    private String getTokenHash(String str) {
        try {
            Mac instance = Mac.getInstance("HmacSHA512");
            instance.init(new SecretKeySpec(Base64.decode(WINGiTConstant.WINGiT_APP_SECRET, 2), "HmacSHA512"));
            return new String(Base64.encode(instance.doFinal(str.getBytes("UTF-8")), 2));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    public static String md5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : digest) {
                String toHexString = Integer.toHexString(b & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
                while (toHexString.length() < 2) {
                    toHexString = "0" + toHexString;
                }
                stringBuilder.append(toHexString);
            }
            return stringBuilder.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public final Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long currentTimeMillis = System.currentTimeMillis();
        String generateOneTimeRequestNonce = generateOneTimeRequestNonce();
        String str = "";
        Object nwVar = new nw();
        if (request.body() != null) {
            request.body().writeTo(nwVar);
            str = nwVar.n();
        }
        return chain.proceed(request.newBuilder().addHeader("Authorization", " WGT " + (WINGiTConstant.WINGiT_APP_ID + ":" + getTokenHash(request.method() + "\n" + request.url() + "\n" + md5(str) + "\n" + generateOneTimeRequestNonce + "\n" + currentTimeMillis) + ":" + generateOneTimeRequestNonce + ":" + currentTimeMillis)).addHeader("X-WGT-UserId", WINGiTConstant.WINGiT_USER_ID).addHeader("Accept-Language", Locale.getDefault().getLanguage()).addHeader("X-WGT-Platform", "Android-" + VERSION.RELEASE).addHeader("X-WGT-Version", BuildConfig.VERSION_NAME).addHeader("X-WGT-Model", Build.MODEL).build());
    }
}
