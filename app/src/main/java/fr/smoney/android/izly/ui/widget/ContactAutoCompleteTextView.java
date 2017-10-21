package fr.smoney.android.izly.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.TextView;

import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactAutoCompleteTextView extends AutoCompleteTextView {
    private b a;
    private c b;
    private LayoutInflater c;

    public interface c {
        void a(Contact contact);
    }

    final class a implements OnItemClickListener {
        final /* synthetic */ ContactAutoCompleteTextView a;
        private ContactAutoCompleteTextView b;

        public a(ContactAutoCompleteTextView contactAutoCompleteTextView, ContactAutoCompleteTextView contactAutoCompleteTextView2) {
            this.a = contactAutoCompleteTextView;
            this.b = contactAutoCompleteTextView2;
        }

        public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            Contact a = this.a.a.a(i);
            if (a.c.length() > 0) {
                CharSequence spannableString = new SpannableString(a.c);
                Drawable a2 = ContactAutoCompleteTextView.a(this.a, a.b);
                a2.setBounds(0, 0, a2.getIntrinsicWidth(), a2.getIntrinsicHeight());
                spannableString.setSpan(new ImageSpan(a2, a.c), 0, a.c.length(), 33);
                this.b.setText(spannableString);
            }
            if (this.a.b != null) {
                this.a.b.a(a);
            }
        }
    }

    final class b extends BaseAdapter implements Filterable {
        final /* synthetic */ ContactAutoCompleteTextView a;
        private List<Contact> b;
        private List<Contact> c;
        private LayoutInflater d;
        private Filter e = new a(this);

        final class a extends Filter {
            final /* synthetic */ b a;

            public a(b bVar) {
                this.a = bVar;
            }

            protected final FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                List arrayList = new ArrayList();
                if (charSequence != null) {
                    String charSequence2 = charSequence.toString();
                    int length = charSequence2.length();
                    if (this.a.c != null && this.a.c.size() > 0) {
                        for (Contact contact : this.a.c) {
                            if (contact.b.regionMatches(true, 0, charSequence2, 0, length)) {
                                arrayList.add(contact);
                            } else if (contact.c != null && contact.c.regionMatches(true, 0, charSequence2, 0, length)) {
                                arrayList.add(contact);
                            } else if (contact.a != null && contact.a.regionMatches(true, 0, charSequence2, 0, length)) {
                                arrayList.add(contact);
                            }
                        }
                    }
                    Collections.sort(arrayList);
                    filterResults.values = arrayList;
                    filterResults.count = arrayList.size();
                }
                return filterResults;
            }

            protected final void publishResults(CharSequence charSequence, FilterResults filterResults) {
                this.a.b = (List) filterResults.values;
                this.a.notifyDataSetChanged();
            }
        }

        final class b {
            TextView a;
            TextView b;
            View c;
            final /* synthetic */ b d;

            public b(b bVar, View view) {
                this.d = bVar;
                this.a = (TextView) view.findViewById(R.id.contact_name);
                this.b = (TextView) view.findViewById(R.id.contact_info);
                this.c = view.findViewById(R.id.contact_smoney);
            }
        }

        public b(ContactAutoCompleteTextView contactAutoCompleteTextView, List<Contact> list, LayoutInflater layoutInflater) {
            this.a = contactAutoCompleteTextView;
            this.b = list;
            this.c = list;
            this.d = layoutInflater;
        }

        static /* synthetic */ void a(b bVar, List list) {
            bVar.c.addAll(list);
            bVar.e.filter(bVar.a.getText());
        }

        public final Contact a(int i) {
            return (Contact) this.b.get(i);
        }

        public final int getCount() {
            return this.b != null ? this.b.size() : 0;
        }

        public final Filter getFilter() {
            return this.e;
        }

        public final /* synthetic */ Object getItem(int i) {
            return a(i);
        }

        public final long getItemId(int i) {
            return (long) i;
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            b bVar;
            Contact a = a(i);
            if (view == null) {
                view = this.d.inflate(R.layout.autocompletion_contact_item, null);
                bVar = new b(this, view);
                view.setTag(bVar);
            } else {
                bVar = (b) view.getTag();
            }
            bVar.a.setText(a.b);
            bVar.b.setText(a.c);
            bVar.c.setVisibility(a.g == fr.smoney.android.izly.data.model.Contact.a.a ? 0 : 8);
            return view;
        }
    }

    public ContactAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = new b(this, new ArrayList(), (LayoutInflater) context.getSystemService("layout_inflater"));
        setAdapter(this.a);
        setOnItemClickListener(new a(this, this));
    }

    static /* synthetic */ BitmapDrawable a(ContactAutoCompleteTextView contactAutoCompleteTextView, String str) {
        if (contactAutoCompleteTextView.c == null) {
            contactAutoCompleteTextView.c = (LayoutInflater) contactAutoCompleteTextView.getContext().getSystemService("layout_inflater");
        }
        TextView textView = (TextView) contactAutoCompleteTextView.c.inflate(R.layout.autocompletion_contact_textview, null);
        textView.setText(str);
        textView.measure(MeasureSpec.makeMeasureSpec(contactAutoCompleteTextView.getWidth(), 0), MeasureSpec.makeMeasureSpec(contactAutoCompleteTextView.getHeight(), 0));
        textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
        textView.setDrawingCacheEnabled(true);
        textView.buildDrawingCache(true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(contactAutoCompleteTextView.getResources(), Bitmap.createBitmap(textView.getDrawingCache()));
        textView.setDrawingCacheEnabled(false);
        return bitmapDrawable;
    }

    public final void a(List<Contact> list) {
        b.a(this.a, list);
    }

    public void setOnContactClickListener(c cVar) {
        this.b = cVar;
    }
}
