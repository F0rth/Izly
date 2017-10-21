package com.thewingitapp.thirdparties.wingitlib.network.security;

import android.util.Base64;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class WINGiTTrustManager implements X509TrustManager {
    private static WINGiTTrustManager mInstance;
    private String[] COM_PINS = new String[0];

    public static WINGiTTrustManager getInstance() {
        if (mInstance == null) {
            mInstance = new WINGiTTrustManager();
        }
        return mInstance;
    }

    public static void updateInstance(String[] strArr) {
        getInstance().COM_PINS = strArr;
    }

    private boolean validateCertificatePin(X509Certificate x509Certificate) throws CertificateException {
        try {
            String encodeToString = Base64.encodeToString(MessageDigest.getInstance("SHA-256").digest(x509Certificate.getPublicKey().getEncoded()), 0);
            for (String trim : this.COM_PINS) {
                if (trim.trim().equalsIgnoreCase(encodeToString.trim())) {
                    return true;
                }
            }
            return false;
        } catch (Throwable e) {
            throw new CertificateException(e);
        }
    }

    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        if (x509CertificateArr == null) {
            throw new IllegalArgumentException("checkServerTrusted: X509Certificate array is null");
        } else if (x509CertificateArr.length <= 0) {
            throw new IllegalArgumentException("checkServerTrusted: X509Certificate is empty");
        } else {
            try {
                TrustManagerFactory instance = TrustManagerFactory.getInstance("X509");
                instance.init(null);
                for (TrustManager trustManager : instance.getTrustManagers()) {
                    ((X509TrustManager) trustManager).checkServerTrusted(x509CertificateArr, str);
                }
                int i = 0;
                while (i < x509CertificateArr.length && !validateCertificatePin(x509CertificateArr[i])) {
                    if (i >= x509CertificateArr.length - 1) {
                        throw new CertificateException("Could not find a valid SSL public key pin");
                    }
                    i++;
                }
            } catch (Throwable e) {
                throw new CertificateException(e);
            }
        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
