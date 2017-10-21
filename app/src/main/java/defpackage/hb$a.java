package defpackage;

import android.os.AsyncTask;
import fr.smoney.android.izly.data.model.Contact;
import java.util.List;

final class hb$a extends AsyncTask<Void, Void, List<Contact>> {
    final /* synthetic */ hb a;

    private hb$a(hb hbVar) {
        this.a = hbVar;
    }

    protected final /* synthetic */ Object doInBackground(Object[] objArr) {
        List c = this.a.l ? iy.c(this.a.getActivity()) : iy.b(this.a.getActivity());
        iy.a(c);
        return c;
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
