package defpackage;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

public final class pa extends AsyncTask {
    public Boolean a = Boolean.valueOf(false);
    public HttpUriRequest b;
    public jo c;
    private final String d;
    private final HttpClient e = pa.a(new DefaultHttpClient());

    public pa(String str) {
        LinkedList linkedList = new LinkedList();
        this.d = str;
        HttpConnectionParams.setConnectionTimeout(new BasicHttpParams(), 60000);
        this.b = null;
        this.c = null;
    }

    private static HttpClient a(HttpClient httpClient) {
        try {
            SocketFactory jVar = new j();
            ClientConnectionManager connectionManager = httpClient.getConnectionManager();
            connectionManager.getSchemeRegistry().register(new Scheme("https", jVar, 443));
            return new DefaultHttpClient(connectionManager, httpClient.getParams());
        } catch (Exception e) {
            Log.e("it.scanpay", e.getMessage());
            return null;
        }
    }

    private oy a() {
        try {
            if (this.e == null) {
                return new oy(oz.CONNECTION_ERROR, "SLL problem");
            }
            return new oy(oz.NONE_ERROR, EntityUtils.toString(this.e.execute(this.b).getEntity()));
        } catch (ConnectTimeoutException e) {
            ConnectTimeoutException connectTimeoutException = e;
            connectTimeoutException.printStackTrace();
            return new oy(oz.CONNECTION_TIMEOUT, connectTimeoutException.toString());
        } catch (ClientProtocolException e2) {
            ClientProtocolException clientProtocolException = e2;
            clientProtocolException.printStackTrace();
            return new oy(oz.CONNECTION_ERROR, clientProtocolException.toString());
        } catch (IOException e3) {
            IOException iOException = e3;
            iOException.printStackTrace();
            return new oy(oz.CONNECTION_ERROR, iOException.toString());
        }
    }

    public final void a(String str) {
        this.a = Boolean.valueOf(true);
        HttpUriRequest httpPost = new HttpPost(this.d);
        httpPost.setHeader("Content-type", kh.ACCEPT_JSON_VALUE);
        try {
            httpPost.setEntity(new StringEntity(str));
        } catch (UnsupportedEncodingException e) {
            Log.e("it.scanpay", "UnsupportedEncodingException exeption launch");
        }
        this.b = httpPost;
        execute(new Void[0]);
    }

    protected final /* synthetic */ Object doInBackground(Object[] objArr) {
        return a();
    }

    protected final /* synthetic */ void onPostExecute(Object obj) {
        oy oyVar = (oy) obj;
        if (this.c != null) {
            this.a = Boolean.valueOf(false);
            if (oyVar.b.equals(oz.NONE_ERROR)) {
                this.c.a(oyVar.a);
            }
        }
    }
}
