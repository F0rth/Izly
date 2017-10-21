package defpackage;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import fr.smoney.android.izly.data.model.Contact;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

final class he$b extends AsyncTask<Void, Void, List<Contact>> {
    final /* synthetic */ he a;

    private he$b(he heVar) {
        this.a = heVar;
    }

    protected final /* synthetic */ Object doInBackground(Object[] objArr) {
        AppCompatActivity a = this.a.d;
        Pattern compile = Pattern.compile("^(0|(\\+|00)33)[67][0-9]{8}$");
        Cursor b = gh.b(a);
        List arrayList = new ArrayList();
        String str = null;
        if (b != null) {
            if (b.moveToFirst()) {
                int columnIndex = b.getColumnIndex("contact_id");
                int columnIndex2 = b.getColumnIndex("display_name");
                int columnIndex3 = b.getColumnIndex("data1");
                while (true) {
                    String string = b.getString(columnIndex);
                    String string2 = b.getString(columnIndex2);
                    String replace = b.getString(columnIndex3).replace(" ", "");
                    if (string.equals(str) || !compile.matcher(replace).matches()) {
                        string2 = str;
                    } else {
                        Contact contact = new Contact();
                        contact.a = string;
                        if (string2 == null) {
                            string2 = replace;
                        }
                        contact.b = string2;
                        contact.c = replace;
                        arrayList.add(contact);
                        string2 = string;
                    }
                    if (!b.moveToNext()) {
                        break;
                    }
                    str = string2;
                }
            }
            b.close();
        }
        Collections.sort(arrayList);
        iy.a(arrayList);
        return arrayList;
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
