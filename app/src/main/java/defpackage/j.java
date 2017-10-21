package defpackage;

import android.util.Base64;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;

public final class j extends SSLSocketFactory {
    private SSLContext a = null;

    public j() {
        super(null);
    }

    private void a() {
        try {
            InputStream byteArrayInputStream = new ByteArrayInputStream(Base64.decode("AAAAAQAAABSRpS/uBJcpCbG//eGxHQIh26mm2AAABE4BAAVteWtleQAAAT9ByG2QAAAAAAAFWC41MDkAAATbMIIE1zCCA7+gAwIBAgIRAPpnykcxm1/+y9Uu1EH28zQwDQYJKoZIhvcNAQEFBQAwQTELMAkGA1UEBhMCRlIxEjAQBgNVBAoTCUdBTkRJIFNBUzEeMBwGA1UEAxMVR2FuZGkgU3RhbmRhcmQgU1NMIENBMB4XDTEzMDIxMTAwMDAwMFoXDTE0MDIxMTIzNTk1OVowVTEhMB8GA1UECxMYRG9tYWluIENvbnRyb2wgVmFsaWRhdGVkMRswGQYDVQQLExJHYW5kaSBTdGFuZGFyZCBTU0wxEzARBgNVBAMTCnNjYW5wYXkuaXQwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC+Z/UI9U/vEyuDK/lvXid3bgW4/gIGoy2btIQfvIJZIVgmxtkCWg6SbD+iqJmH9Syo9P30ORdPgq+NtAwGvwoV1svvgbHxg6UrZ2YpqWw2Cp89Q3i7Z+FD4obKZlbPLTztOeYgzKao74VoPKyqQD9vIc2dyK0lOAbiWM6Er2ysFQObMikMUyYuY3A9u/mC/860Ln3MM8DgGCFErhNnBzJKCz9fjXnmOCzPmftSKT6mQlHJkqPnwKVtotS2150SwiIJdlkRMED1gLSTN4zkcoDtfmqMEbQYaTzT18QiAGNh4cwfFwYBsGRuMPuhPCpcQSZMCfig96W59/q9AWamLg7XAgMBAAGjggG0MIIBsDAfBgNVHSMEGDAWgBS2qP+iqC/Qps1LsWjz51AQMad5ITAdBgNVHQ4EFgQUIpuWwHhQQZS1qQDJgM8nmaviPoMwDgYDVR0PAQH/BAQDAgWgMAwGA1UdEwEB/wQCMAAwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMGAGA1UdIARZMFcwSwYLKwYBBAGyMQECAhowPDA6BggrBgEFBQcCARYuaHR0cDovL3d3dy5nYW5kaS5uZXQvY29udHJhY3RzL2ZyL3NzbC9jcHMvcGRmLzAIBgZngQwBAgEwPAYDVR0fBDUwMzAxoC+gLYYraHR0cDovL2NybC5nYW5kaS5uZXQvR2FuZGlTdGFuZGFyZFNTTENBLmNybDBqBggrBgEFBQcBAQReMFwwNwYIKwYBBQUHMAKGK2h0dHA6Ly9jcnQuZ2FuZGkubmV0L0dhbmRpU3RhbmRhcmRTU0xDQS5jcnQwIQYIKwYBBQUHMAGGFWh0dHA6Ly9vY3NwLmdhbmRpLm5ldDAlBgNVHREEHjAcggpzY2FucGF5Lml0gg53d3cuc2NhbnBheS5pdDANBgkqhkiG9w0BAQUFAAOCAQEAopNGkkihKhagArhvwmRz+Ij+4DICPkbMMXRKFYr2e+aVEjBQ3S+tMs2IhyU66GIy8eyWsxvjsrQCu7Uq2+g/R4tZGwUOBSuDBL/Cm7fq3XpKMSDljoLx+UyV4YgAOJfR1P5xToM79iUQU1AmWnJwBvdL+0IdWjHPV7RbgISQbQOkADuglhC31Vq+9oVoV9VSrlxb6EzV/K6nVOQ7E6XIuFCCqRCYTPRkSAhdgexYDvJ7L4bULiFmMX8CS8jKNA5KtgBpaVSrdRLlm3455rajLnp7vdQctCIgA+FonmAAR7N2daLHk8IhI7LLV7WWHtIvYZipaEIm13YUH68IkNI2GQD3qHw/uvBDOA3DHoKW6EEIeU2Sxw==", 0));
            KeyStore instance = KeyStore.getInstance("BKS");
            instance.load(byteArrayInputStream, "vitamind".toCharArray());
            byteArrayInputStream.close();
            TrustManagerFactory instance2 = TrustManagerFactory.getInstance("X509");
            instance2.init(instance);
            this.a = SSLContext.getInstance("TLS");
            this.a.init(null, instance2.getTrustManagers(), null);
        } catch (Exception e) {
            Log.e("it.scanpay", e.getStackTrace().toString());
            throw new IOException("Could not create SSL Context");
        }
    }

    public final Socket createSocket() {
        if (this.a == null) {
            a();
        }
        return this.a.getSocketFactory().createSocket();
    }

    public final Socket createSocket(Socket socket, String str, int i, boolean z) {
        if (this.a == null) {
            a();
        }
        return this.a.getSocketFactory().createSocket(socket, str, i, z);
    }
}
