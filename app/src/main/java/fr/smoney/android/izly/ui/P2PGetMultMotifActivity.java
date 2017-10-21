package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.P2PGetMultMotif;

public class P2PGetMultMotifActivity extends SmoneyABSActivity {
    private ListView b;
    private LayoutInflater c;
    private View d;
    private a e;
    private EditText f;
    private P2PGetMultMotif g;

    final class a extends BaseAdapter implements OnItemClickListener {
        cg[] a = cg.values();
        final /* synthetic */ P2PGetMultMotifActivity b;

        public a(P2PGetMultMotifActivity p2PGetMultMotifActivity) {
            this.b = p2PGetMultMotifActivity;
        }

        public final int getCount() {
            return this.a.length;
        }

        public final /* bridge */ /* synthetic */ Object getItem(int i) {
            return this.a[i];
        }

        public final long getItemId(int i) {
            return (long) i;
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            View inflate = view == null ? this.b.c.inflate(R.layout.listitem_motif, null) : view;
            cg cgVar = this.a[i];
            TextView textView = (TextView) inflate;
            textView.setText(cgVar.k);
            textView.setCompoundDrawablesWithIntrinsicBounds(cgVar.j, 0, 0, 0);
            if (this.b.g.b == i) {
                textView.setTextColor(this.b.getResources().getColor(R.color.nearpro_purple));
                textView.setPaintFlags(32);
            } else {
                textView.setTextColor(this.b.getResources().getColorStateList(R.color.text_color_purple));
                textView.setPaintFlags(64);
            }
            return inflate;
        }

        public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            if (i == this.b.g.b) {
                this.b.g.b = -1;
                this.b.f.setText("");
            } else {
                this.b.g.b = i;
            }
            notifyDataSetChanged();
            P2PGetMultMotifActivity.b(this.b);
        }
    }

    static /* synthetic */ void b(P2PGetMultMotifActivity p2PGetMultMotifActivity) {
        Intent intent = new Intent();
        a aVar = p2PGetMultMotifActivity.e;
        cg cgVar = aVar.b.g.b != -1 ? aVar.a[aVar.b.g.b] : null;
        p2PGetMultMotifActivity.g.a = cgVar != null ? p2PGetMultMotifActivity.getString(cgVar.k) : p2PGetMultMotifActivity.f.getText().toString();
        intent.putExtra("fr.smoney.android.izly.motif.ExtraMotif", p2PGetMultMotifActivity.g);
        p2PGetMultMotifActivity.setResult(-1, intent);
        p2PGetMultMotifActivity.finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.p2p_get_mult_motif);
        this.g = (P2PGetMultMotif) getIntent().getParcelableExtra("fr.smoney.android.izly.motif.ExtraMotif");
        this.b = (ListView) findViewById(R.id.listview_motif);
        this.c = (LayoutInflater) getSystemService("layout_inflater");
        this.d = this.c.inflate(R.layout.footer_motif, null);
        this.f = (EditText) this.d.findViewById(R.id.et_motif_other);
        this.f.setOnEditorActionListener(new OnEditorActionListener(this) {
            final /* synthetic */ P2PGetMultMotifActivity a;

            {
                this.a = r1;
            }

            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                this.a.g.b = -1;
                P2PGetMultMotifActivity.b(this.a);
                return false;
            }
        });
        this.e = new a(this);
        if (this.g.b == -1) {
            this.f.setText(this.g.a);
        }
        this.b.addFooterView(this.d, null, false);
        this.b.setAdapter(this.e);
        this.b.setOnItemClickListener(this.e);
    }
}
