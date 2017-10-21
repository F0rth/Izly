package com.ad4screen.sdk.common;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.b.m.i;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public final class d {
    public static String a(String str) {
        return a(str, "GET", null);
    }

    private static String a(String str, String str2, byte[] bArr) {
        Throwable th;
        String str3;
        Throwable th2;
        i.a(17986);
        try {
            URL url = new URL(str);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(str2);
            httpURLConnection.setDoInput(true);
            if (bArr != null) {
                httpURLConnection.setDoOutput(true);
                OutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                bufferedOutputStream.write(bArr);
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
            String a = a(url, httpURLConnection);
            try {
                httpURLConnection.disconnect();
                i.a();
                return a;
            } catch (Throwable e) {
                th = e;
                str3 = a;
                th2 = th;
                try {
                    Log.error("HTTP|Malformed url : " + str, th2);
                    return str3;
                } finally {
                    i.a();
                }
            } catch (Throwable e2) {
                th = e2;
                str3 = a;
                th2 = th;
                Log.error("HTTP|Error while performing request @ " + str, th2);
                i.a();
                return str3;
            }
        } catch (Throwable e22) {
            th = e22;
            str3 = null;
            th2 = th;
            Log.error("HTTP|Malformed url : " + str, th2);
            return str3;
        } catch (Throwable e222) {
            th = e222;
            str3 = null;
            th2 = th;
            Log.error("HTTP|Error while performing request @ " + str, th2);
            i.a();
            return str3;
        }
    }

    public static String a(String str, byte[] bArr) {
        return a(str, "POST", bArr);
    }

    private static String a(URL url, HttpURLConnection httpURLConnection) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream(), 8192);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[8192];
            while (true) {
                int read = bufferedInputStream.read(bArr, 0, 8192);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    bufferedInputStream.close();
                    bArr = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return new String(bArr);
                }
            }
        } catch (Throwable e) {
            Log.error("HTTP|Could not read response from url : " + url, e);
            return null;
        }
    }
}
