package defpackage;

import android.content.Context;
import android.os.AsyncTask;
import fr.smoney.android.izly.R;
import java.util.List;

final class he$e extends AsyncTask<String, Void, List<String>> {
    final /* synthetic */ he a;
    private Context b;

    public he$e(he heVar, Context context) {
        this.a = heVar;
        this.b = context;
    }

    protected final /* synthetic */ Object doInBackground(Object[] objArr) {
        String[] strArr = (String[]) objArr;
        return this.a.m ? iy.b(this.b, strArr[0]) : iy.a(this.b, strArr[0]);
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
        this.a.n = list;
        CharSequence[] charSequenceArr = new CharSequence[list.size()];
        for (int i = 0; i < size; i++) {
            charSequenceArr[i] = (CharSequence) list.get(i);
        }
        this.a.a(ib.a(!this.a.m ? this.a.getString(R.string.contacts_tv_choose_info) : this.a.getString(R.string.contacts_tv_choose_info_mail), charSequenceArr, this.a, ie.ChooseContactInfoType));
    }
}
