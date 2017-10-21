package defpackage;

import android.content.Context;
import android.os.AsyncTask;
import fr.smoney.android.izly.R;
import java.util.List;

final class hb$b extends AsyncTask<String, Void, List<String>> {
    final /* synthetic */ hb a;
    private Context b;

    public hb$b(hb hbVar, Context context) {
        this.a = hbVar;
        this.b = context;
    }

    protected final /* synthetic */ Object doInBackground(Object[] objArr) {
        String[] strArr = (String[]) objArr;
        List b = this.a.l ? iy.b(this.b, strArr[0]) : iy.a(this.b, strArr[0]);
        return b.size() > 0 ? b : iy.b(this.b, strArr[0]);
    }

    protected final /* synthetic */ void onPostExecute(Object obj) {
        List list = (List) obj;
        int size = list.size();
        if (size <= 0) {
            return;
        }
        if (size == 1) {
            this.a.a((String) list.get(0));
            return;
        }
        this.a.m = list;
        CharSequence[] charSequenceArr = new CharSequence[list.size()];
        for (int i = 0; i < size; i++) {
            charSequenceArr[i] = (CharSequence) list.get(i);
        }
        this.a.a(ib.a(this.a.getString(R.string.contacts_tv_choose_info), charSequenceArr, this.a, ie.ChooseContactInfoType));
    }
}
