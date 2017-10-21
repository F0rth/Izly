package defpackage;

import android.os.AsyncTask;
import fr.smoney.android.izly.data.model.Contact;
import java.util.List;

final class he$a extends AsyncTask<Void, Void, List<Contact>> {
    final /* synthetic */ he a;

    private he$a(he heVar) {
        this.a = heVar;
    }

    protected final /* synthetic */ Object doInBackground(Object[] objArr) {
        List b = !this.a.m ? iy.b(this.a.d) : iy.c(this.a.d);
        iy.a(b);
        return b;
    }

    protected final /* synthetic */ void onPostExecute(Object obj) {
        List list = (List) obj;
        if (this.a.isAdded()) {
            for (int i = 0; i < list.size(); i++) {
                this.a.j.a(list);
            }
            list.size();
        }
    }
}
