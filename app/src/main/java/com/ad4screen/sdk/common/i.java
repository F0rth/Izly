package com.ad4screen.sdk.common;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.b.m.c;
import com.ad4screen.sdk.common.b.m.j;
import com.ad4screen.sdk.common.b.m.k;
import com.ad4screen.sdk.common.h.a;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public final class i {
    private static double a(double d) {
        return (3.141592653589793d * d) / 180.0d;
    }

    public static int a(double d, double d2, double d3, double d4) {
        double sin = Math.sin(a(d));
        double sin2 = Math.sin(a(d3));
        return (int) ((((b(Math.acos((sin * sin2) + ((Math.cos(a(d)) * Math.cos(a(d3))) * Math.cos(a(d2 - d4))))) * 60.0d) * 1.1515d) * 1.609344d) * 1000.0d);
    }

    public static int a(Context context, int i) {
        return (int) ((context.getResources().getDisplayMetrics().density * ((float) i)) + 0.5f);
    }

    public static <T> T a(Class<T> cls, Bundle bundle, String str, T t) {
        Object obj = bundle.get(str);
        return cls.isInstance(obj) ? cls.cast(obj) : t;
    }

    public static String a(Context context, String str, Class<? extends Service> cls) {
        String str2 = null;
        if (context != null) {
            Context applicationContext = context.getApplicationContext();
            try {
                ServiceInfo serviceInfo = applicationContext.getPackageManager().getServiceInfo(new ComponentName(applicationContext, cls), 128);
                if (serviceInfo.metaData != null && serviceInfo.metaData.containsKey(str)) {
                    str2 = serviceInfo.metaData.get(str).toString();
                }
            } catch (Throwable e) {
                Log.internal("Could not load service metadata", e);
            }
        }
        return str2;
    }

    public static String a(PackageInfo packageInfo, ApplicationInfo applicationInfo) {
        long a = k.a(packageInfo);
        if (a == 0) {
            File file = new File(applicationInfo.dataDir);
            Log.internal("Fetching install time from app data dir : " + applicationInfo.packageName + " => " + applicationInfo.dataDir + " modified :" + file.lastModified());
            a = file.lastModified();
        }
        if (a == 0) {
            file = new File(applicationInfo.sourceDir);
            Log.internal("Fetching install time from app source dir : " + applicationInfo.packageName + " => " + applicationInfo.sourceDir + " modified :" + file.lastModified());
            a = file.lastModified();
        }
        if (a != 0) {
            String a2 = h.a(new Date(a), a.ISO8601);
            if (a2 != null) {
                return a2;
            }
        }
        return "";
    }

    public static String a(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.reset();
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                stringBuilder.append(String.format("%1$02x", new Object[]{Integer.valueOf(digest[i] & 255)}));
            }
            return stringBuilder.toString();
        } catch (Throwable e) {
            Log.error("Could not use md5 digest algorithm ", e);
            return "";
        }
    }

    public static String a(Cipher cipher, String str) {
        try {
            return c.a(cipher.doFinal(str.getBytes()), 0);
        } catch (Throwable e) {
            Log.error("Illegal Block Size ", e);
            return null;
        } catch (Throwable e2) {
            Log.error("Bad Padding ", e2);
            return null;
        }
    }

    public static String a(byte[] bArr, String str, SecretKey secretKey) {
        try {
            Cipher instance = Cipher.getInstance(b());
            instance.init(2, secretKey, new IvParameterSpec(bArr));
            return new String(instance.doFinal(c.a(str, 0)));
        } catch (Throwable e) {
            Log.error("Could not use AES algorithm ", e);
        } catch (Throwable e2) {
            Log.error("Padding problem ", e2);
        } catch (Throwable e22) {
            Log.error("Illegal Block Size ", e22);
        } catch (Throwable e222) {
            Log.error("Bad Padding ", e222);
        } catch (Throwable e2222) {
            Log.error("Invalid key ", e2222);
        } catch (Throwable e22222) {
            Log.error("Invalid Algorithm Parameter ", e22222);
        }
        return null;
    }

    public static Cipher a(SecretKey secretKey) {
        try {
            Cipher instance = Cipher.getInstance(b());
            instance.init(1, secretKey);
            return instance;
        } catch (Throwable e) {
            Log.error("Could not use AES algorithm ", e);
        } catch (Throwable e2) {
            Log.error("Padding problem ", e2);
        } catch (Throwable e22) {
            Log.error("Invalid key ", e22);
        }
        return null;
    }

    @SuppressLint({"TrulyRandom"})
    public static SecretKey a() {
        try {
            SecureRandom secureRandom = new SecureRandom();
            KeyGenerator instance = KeyGenerator.getInstance("AES");
            instance.init(256, secureRandom);
            return instance.generateKey();
        } catch (Throwable e) {
            Log.error("Could not use AES algorithm ", e);
            return null;
        }
    }

    public static void a(Context context, Intent intent) {
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent, context.getPackageName() + ".permission.A4S_SEND");
    }

    public static void a(String str, final Callback<Bitmap> callback, final boolean z) {
        new AsyncTask<String, String, Bitmap>() {
            protected final Bitmap a(String... strArr) {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(strArr[0]).openConnection();
                    httpURLConnection.setConnectTimeout(20000);
                    httpURLConnection.setReadTimeout(20000);
                    httpURLConnection.setInstanceFollowRedirects(true);
                    int responseCode = httpURLConnection.getResponseCode();
                    int contentLength = httpURLConnection.getContentLength();
                    if (responseCode / 100 == 3) {
                        String headerField = httpURLConnection.getHeaderField("Location");
                        httpURLConnection.disconnect();
                        return a(headerField);
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    for (int read = inputStream.read(); read != -1; read = inputStream.read()) {
                        byteArrayOutputStream.write(read);
                    }
                    inputStream.close();
                    byte[] toByteArray = byteArrayOutputStream.toByteArray();
                    if (contentLength != toByteArray.length) {
                        throw new SocketTimeoutException("Content-Length is not equal to final decoded bitmap bytes");
                    }
                    Bitmap decodeByteArray = BitmapFactory.decodeByteArray(toByteArray, 0, toByteArray.length);
                    if (!z) {
                        return decodeByteArray;
                    }
                    if (callback != null) {
                        if (decodeByteArray != null) {
                            callback.onResult(decodeByteArray);
                        } else {
                            callback.onError(0, "Can't download this bitmap");
                        }
                    }
                    return null;
                } catch (Throwable e) {
                    if (callback != null) {
                        callback.onError(0, "Can't download this bitmap");
                    }
                    Log.error("An error occured while downloading this image", e);
                }
            }

            protected final void a(Bitmap bitmap) {
                if (!z && callback != null) {
                    if (bitmap != null) {
                        callback.onResult(bitmap);
                    } else {
                        callback.onError(0, "Can't download this bitmap");
                    }
                }
            }

            protected final /* synthetic */ Object doInBackground(Object[] objArr) {
                return a((String[]) objArr);
            }

            protected final /* synthetic */ void onPostExecute(Object obj) {
                a((Bitmap) obj);
            }
        }.execute(new String[]{str});
    }

    @SuppressLint({"NewApi"})
    public static boolean a(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        boolean inKeyguardRestrictedInputMode = keyguardManager.inKeyguardRestrictedInputMode();
        if (VERSION.SDK_INT >= 7 && VERSION.SDK_INT < 20 && !inKeyguardRestrictedInputMode) {
            inKeyguardRestrictedInputMode = !powerManager.isScreenOn();
        }
        if (VERSION.SDK_INT >= 16 && !r2) {
            inKeyguardRestrictedInputMode = keyguardManager.isKeyguardLocked();
        }
        if (VERSION.SDK_INT >= 20 && !r2) {
            inKeyguardRestrictedInputMode = !powerManager.isInteractive();
        }
        return !inKeyguardRestrictedInputMode;
    }

    public static boolean a(Context context, String str) {
        return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }

    private static boolean a(Context context, int[] iArr) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        for (int networkInfo : iArr) {
            NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(networkInfo);
            if (networkInfo2 != null) {
                State state = networkInfo2.getState();
                if (state == State.CONNECTED || state == State.CONNECTING) {
                    return true;
                }
            }
        }
        return false;
    }

    private static double b(double d) {
        return (180.0d * d) / 3.141592653589793d;
    }

    public static String b() {
        return "AES/CBC/PKCS5Padding";
    }

    public static String b(Context context) {
        String string = Secure.getString(context.getContentResolver(), "android_id");
        if (string == null) {
            string = "";
        }
        return string.toLowerCase(Locale.US);
    }

    public static String c(Context context) {
        return context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).toUri(1);
    }

    public static int d(Context context) {
        return context.getApplicationInfo().icon;
    }

    public static String e(Context context) {
        return (String) context.getPackageManager().getApplicationLabel(context.getApplicationInfo());
    }

    public static String f(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return (telephonyManager.getNetworkOperatorName() == null || telephonyManager.getNetworkOperatorName().length() <= 0) ? null : telephonyManager.getNetworkOperatorName();
    }

    public static boolean g(Context context) {
        return a(context, new int[]{0});
    }

    @SuppressLint({"InlinedApi"})
    public static boolean h(Context context) {
        return a(context, new int[]{1, 6});
    }

    public static boolean i(Context context) {
        return j.a(context);
    }
}
