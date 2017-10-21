package defpackage;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import java.io.IOException;

public final class cb extends AsyncTask<Void, Void, String> {
    private Context a;
    private SmoneyRequestManager b;
    private cl c;

    public cb(Context context, SmoneyRequestManager smoneyRequestManager, cl clVar) {
        this.a = context;
        this.b = smoneyRequestManager;
        this.c = clVar;
    }

    private String a() {
        try {
            return GoogleCloudMessaging.getInstance(this.a).register("1074937585998");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected final /* synthetic */ Object doInBackground(Object[] objArr) {
        return a();
    }

    protected final /* synthetic */ void onPostExecute(Object obj) {
        String str = (String) obj;
        if (this.c.b != null) {
            String str2 = this.c.b.I;
            String a = jg.a(this.a);
            if (a == null) {
                Log.d("MyGCMAsyncTask", "GCM token from SharedPref is null so we ask one to GCM");
            } else {
                str = a;
            }
            Log.d("MyGCMAsyncTask", "GCM token : " + str + " and push token from server = " + str2);
            if (str == null) {
                Log.i("MyGCMAsyncTask", "device token from GCM is null so we remove it from the server");
                this.b.b(this.c.b.a, this.c.b.c);
            } else if (str2.equals(str)) {
                Log.i("Token", "GCM token : " + str);
            } else {
                Log.i("MyGCMAsyncTask", "device token from GCM is different from the one received from the server. Sending updated token");
                this.b.a(this.c.b.a, this.c.b.c, a.a);
            }
        }
    }
}
