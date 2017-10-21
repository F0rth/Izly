package defpackage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.Contact;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class gm extends BaseAdapter implements SectionIndexer {
    private LayoutInflater a;
    private List<Contact> b;
    private Map<String, Integer> c;
    private String[] d;

    public gm(Context context) {
        this.a = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public final Contact a(int i) {
        return (Contact) this.b.get(i);
    }

    public final void a(List<Contact> list) {
        this.b = list;
        this.c = new HashMap();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Contact contact = (Contact) list.get(i);
            if (contact.i) {
                this.c.put(contact.j, Integer.valueOf(i));
            }
        }
        ArrayList arrayList = new ArrayList(this.c.keySet());
        Collections.sort(arrayList);
        this.d = new String[arrayList.size()];
        arrayList.toArray(this.d);
        notifyDataSetChanged();
    }

    public final int getCount() {
        return this.b != null ? this.b.size() : 0;
    }

    public final /* synthetic */ Object getItem(int i) {
        return a(i);
    }

    public final long getItemId(int i) {
        return (long) i;
    }

    public final int getPositionForSection(int i) {
        int intValue = ((Integer) this.c.get(this.d[i])).intValue();
        Log.d("CONTACT ADAPTER", "pos : " + String.valueOf(intValue) + " for section : " + String.valueOf(i) + " with index : " + this.d[i]);
        return intValue;
    }

    public final int getSectionForPosition(int i) {
        return 0;
    }

    public final /* bridge */ /* synthetic */ Object[] getSections() {
        return this.d;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        gp gpVar;
        if (view == null) {
            view = this.a.inflate(R.layout.contacts_item, null);
            gpVar = new gp(view);
            view.setTag(gpVar);
        } else {
            gpVar = (gp) view.getTag();
        }
        Contact a = a(i);
        gpVar.a.setText(a.b);
        gpVar.b.setVisibility(8);
        if (a.i) {
            gpVar.c.setVisibility(0);
            if (a.i) {
                gpVar.c.setText(a.j);
            }
        } else {
            gpVar.c.setVisibility(8);
        }
        return view;
    }
}
