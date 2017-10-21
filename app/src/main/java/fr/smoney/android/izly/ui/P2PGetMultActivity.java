package fr.smoney.android.izly.ui;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.IconTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import defpackage.gd;
import defpackage.gi;
import defpackage.gw;
import defpackage.hu;
import defpackage.hw;
import defpackage.hy;
import defpackage.ib;
import defpackage.ie;
import defpackage.io;
import defpackage.ip;
import defpackage.iq;
import defpackage.iq$a;
import defpackage.is;
import defpackage.iu;
import defpackage.iw;
import defpackage.iy;
import defpackage.ja;
import defpackage.jb;
import defpackage.je;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.Bookmark;
import fr.smoney.android.izly.data.model.Contact;
import fr.smoney.android.izly.data.model.GetBookmarksData;
import fr.smoney.android.izly.data.model.P2PGetMultData;
import fr.smoney.android.izly.data.model.P2PGetMultMotif;
import fr.smoney.android.izly.data.model.P2PGetMultOptions;
import fr.smoney.android.izly.data.model.RecipientItem;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.ui.widget.ContactAutoCompleteTextView;
import fr.smoney.android.izly.ui.widget.ExpandableGridView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class P2PGetMultActivity extends SmoneyABSActivity implements OnClickListener, OnItemClickListener, SmoneyRequestManager$a, fr.smoney.android.izly.ui.widget.ContactAutoCompleteTextView.c, iq$a {
    public static final String b = P2PGetMultActivity.class.getSimpleName();
    private static final File c = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    private static final String d = null;
    private P2PGetMultOptions A;
    private P2PGetMultMotif B;
    private boolean C = false;
    private boolean D = false;
    private OnEditorActionListener E = new OnEditorActionListener(this) {
        final /* synthetic */ P2PGetMultActivity a;

        {
            this.a = r1;
        }

        public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            String charSequence = textView.getText().toString();
            if (charSequence.length() <= 0) {
                return false;
            }
            this.a.a(charSequence);
            return true;
        }
    };
    private TextWatcher F = new TextWatcher(this) {
        final /* synthetic */ P2PGetMultActivity a;

        {
            this.a = r1;
        }

        public final void afterTextChanged(Editable editable) {
            if (editable.toString().length() == 0) {
                this.a.y.setVisibility(8);
                this.a.r = true;
                this.a.n.a(0.0d);
                return;
            }
            this.a.y.setVisibility(0);
            this.a.r = false;
            try {
                double a = iu.a(editable.toString());
                this.a.A.a = a;
                this.a.n.a(a);
            } catch (gd e) {
            }
        }

        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    };
    @SuppressLint({"NewApi"})
    private OnGlobalLayoutListener G = new OnGlobalLayoutListener(this) {
        final /* synthetic */ P2PGetMultActivity a;

        {
            this.a = r1;
        }

        public final void onGlobalLayout() {
            this.a.h.setPadding(this.a.h.getPaddingLeft(), this.a.h.getPaddingTop(), this.a.z.getWidth(), this.a.h.getPaddingBottom());
            if (VERSION.SDK_INT < 16) {
                this.a.z.getViewTreeObserver().removeGlobalOnLayoutListener(this.a.G);
            } else {
                this.a.z.getViewTreeObserver().removeOnGlobalLayoutListener(this.a.G);
            }
        }
    };
    @SuppressLint({"NewApi"})
    private OnGlobalLayoutListener H = new OnGlobalLayoutListener(this) {
        final /* synthetic */ P2PGetMultActivity a;

        {
            this.a = r1;
        }

        public final void onGlobalLayout() {
            this.a.h.setPadding(this.a.k.getPaddingLeft(), this.a.k.getPaddingTop(), this.a.l.getWidth(), this.a.k.getPaddingBottom());
            if (VERSION.SDK_INT < 16) {
                this.a.z.getViewTreeObserver().removeGlobalOnLayoutListener(this.a.H);
            } else {
                this.a.z.getViewTreeObserver().removeOnGlobalLayoutListener(this.a.H);
            }
        }
    };
    private EditText e;
    private TextView f;
    private TextView g;
    private ContactAutoCompleteTextView h;
    private IconTextView i;
    private ExpandableGridView j;
    private EditText k;
    private IconTextView l;
    private Button m;
    private c n;
    private ip o;
    private File p;
    private int q = -1;
    private boolean r = true;
    private String s;
    private String t;
    private cl u;
    private Button v;
    private Spinner w;
    private gw x;
    private Button y;
    private View z;

    final class a extends AsyncTask<Void, Void, List<Contact>> {
        final /* synthetic */ P2PGetMultActivity a;
        private List<Bookmark> b;

        public a(P2PGetMultActivity p2PGetMultActivity, List<Bookmark> list) {
            this.a = p2PGetMultActivity;
            this.b = list;
        }

        protected final /* synthetic */ Object doInBackground(Object[] objArr) {
            return iy.b(this.b);
        }

        protected final /* synthetic */ void onPostExecute(Object obj) {
            this.a.h.a((List) obj);
        }
    }

    final class b extends AsyncTask<Void, Void, List<Contact>> {
        final /* synthetic */ P2PGetMultActivity a;

        private b(P2PGetMultActivity p2PGetMultActivity) {
            this.a = p2PGetMultActivity;
        }

        protected final /* synthetic */ Object doInBackground(Object[] objArr) {
            return iy.a(this.a);
        }

        protected final /* synthetic */ void onPostExecute(Object obj) {
            this.a.h.a((List) obj);
        }
    }

    final class c extends BaseAdapter {
        ArrayList<RecipientItem> a;
        double b;
        boolean c = false;
        final /* synthetic */ P2PGetMultActivity d;
        private LayoutInflater e;
        private double f;

        public c(P2PGetMultActivity p2PGetMultActivity) {
            this.d = p2PGetMultActivity;
            this.e = p2PGetMultActivity.getLayoutInflater();
            this.a = new ArrayList();
        }

        public final RecipientItem a(int i) {
            return (RecipientItem) this.a.get(i);
        }

        public final String a() {
            String str = "";
            int i = 0;
            while (i < this.a.size()) {
                String str2;
                RecipientItem recipientItem = (RecipientItem) this.a.get(i);
                if (recipientItem.e) {
                    str2 = str;
                } else {
                    str = str + recipientItem.a;
                    if (recipientItem.a() > 0.0d) {
                        str2 = (str + ":") + recipientItem.a();
                    } else {
                        str2 = str;
                    }
                    str2 = str2 + ";";
                }
                i++;
                str = str2;
            }
            return str.substring(0, str.length() - 1);
        }

        public final void a(double d) {
            this.b = d;
            b();
        }

        public final boolean a(RecipientItem recipientItem) {
            this.a.add(recipientItem);
            b();
            return true;
        }

        public final void b() {
            int i;
            double d;
            this.d.A.d = this.a.size();
            boolean z = this.d.A.c;
            double d2 = 0.0d;
            int i2 = 0;
            for (i = 0; i < this.a.size(); i++) {
                RecipientItem recipientItem = (RecipientItem) this.a.get(i);
                if (recipientItem.d != -1.0d) {
                    d2 += recipientItem.d;
                    i2++;
                }
            }
            int size = this.c ? this.a.size() + 1 : this.a.size();
            if (this.b == 0.0d || size <= i2 || this.b <= d2) {
                if (this.d.r) {
                    for (i = 0; i < this.a.size(); i++) {
                        ((RecipientItem) this.a.get(i)).c = -1.0d;
                    }
                }
                d = -1.0d;
            } else {
                if (z) {
                    double round = ((double) Math.round(((this.b - d2) / ((double) (size - i2))) * 100.0d)) / 100.0d;
                    d = round > 0.0d ? round : -1.0d;
                } else {
                    d = this.b;
                }
                this.d.A.e = d;
                this.f = this.c ? d : -1.0d;
                for (i = 0; i < this.a.size(); i++) {
                    ((RecipientItem) this.a.get(i)).c = d;
                }
            }
            P2PGetMultActivity.a(this.d, this.c, d);
            notifyDataSetChanged();
        }

        public final int getCount() {
            return Math.max(1, this.a.size());
        }

        public final /* synthetic */ Object getItem(int i) {
            return a(i);
        }

        public final long getItemId(int i) {
            return 0;
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            e eVar;
            if (view == null) {
                view = this.e.inflate(R.layout.p2p_get_mult_recipient_item, null);
                e eVar2 = new e(this.d, view);
                view.setTag(eVar2);
                eVar = eVar2;
            } else {
                eVar = (e) view.getTag();
            }
            if (i < this.a.size()) {
                RecipientItem recipientItem = (RecipientItem) this.a.get(i);
                eVar.a.setImageResource(R.drawable.list_aiv_avatar_placeholder);
                eVar.d.a.a(jl.a(recipientItem.b), eVar.a);
                eVar.b.setText(recipientItem.a);
                if (recipientItem.a() != -1.0d) {
                    eVar.c.setText(eVar.d.a(recipientItem.a()));
                } else {
                    eVar.c.setText("?");
                }
            } else {
                eVar.a.setImageURI(null);
                eVar.a.setImageResource(R.drawable.icon_home_placeholder);
                eVar.d.a.a(R.drawable.icon_home_placeholder, eVar.a);
                eVar.c.setVisibility(0);
                eVar.b.setText("");
                eVar.c.setText("?");
            }
            return view;
        }

        public final boolean isEnabled(int i) {
            return i < this.a.size();
        }
    }

    static final class d extends io {
        public d(Context context, int i, int i2) {
            super(context.getResources().getDrawable(R.drawable.quick_actions_cancel), context.getResources().getString(R.string.p2p_get_mult_qa_delete));
        }
    }

    final class e {
        ImageView a;
        TextView b;
        TextView c;
        final /* synthetic */ P2PGetMultActivity d;

        public e(P2PGetMultActivity p2PGetMultActivity, View view) {
            this.d = p2PGetMultActivity;
            this.a = (ImageView) view.findViewById(R.id.iv_recipient_item_avatar);
            this.b = (TextView) view.findViewById(R.id.tv_recipient_item_name);
            this.c = (TextView) view.findViewById(R.id.tv_recipient_item_amount);
        }
    }

    private String a(double d) {
        return String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(d), Currency.getInstance(this.u.b.j).getSymbol()});
    }

    private void a(GetBookmarksData getBookmarksData, ServerError serverError) {
        findViewById(R.id.pb_recipient).setVisibility(8);
        if (serverError == null && getBookmarksData != null) {
            new a(this, getBookmarksData.b).execute(new Void[0]);
        }
    }

    private void a(P2PGetMultData p2PGetMultData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (p2PGetMultData == null) {
            a(hw.a(this, this));
        } else {
            Intent a = is.a(this, P2PGetMultConfirmActivity.class);
            p2PGetMultData.g = this.A.c;
            p2PGetMultData.d = this.A.e;
            a.putExtra("fr.smoney.android.izly.extras.p2pGetMultData", p2PGetMultData);
            if (m()) {
                a.putExtra("fr.smoney.android.izly.extras.attachmentName", this.s);
                a.putExtra("fr.smoney.android.izly.extras.attachmentPath", this.t);
            }
            startActivity(a);
        }
    }

    static /* synthetic */ void a(P2PGetMultActivity p2PGetMultActivity, boolean z, double d) {
        if (z) {
            p2PGetMultActivity.g.setVisibility(0);
            p2PGetMultActivity.g.setText(Html.fromHtml(p2PGetMultActivity.getString(R.string.p2p_get_mult_tv_add_me, new Object[]{p2PGetMultActivity.a(d)})));
            return;
        }
        p2PGetMultActivity.g.setVisibility(8);
    }

    private void a(String str) {
        this.n.a(new RecipientItem(str, false));
        this.h.setText("");
    }

    private void a(String str, String str2) {
        this.s = str;
        this.t = str2;
        iw.b(iw.a(new File(this.t), this.l.getWidth() + 10, this.l.getHeight() + 10));
    }

    static /* synthetic */ boolean a(P2PGetMultActivity p2PGetMultActivity, View view, View view2, MotionEvent motionEvent) {
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        view2.getGlobalVisibleRect(rect);
        view.getGlobalVisibleRect(rect2);
        return rect.contains(((int) motionEvent.getX()) + rect2.left, rect2.top + ((int) motionEvent.getY()));
    }

    static /* synthetic */ void e(P2PGetMultActivity p2PGetMultActivity) {
        if (ContextCompat.checkSelfPermission(p2PGetMultActivity, "android.permission.READ_CONTACTS") != 0) {
            ActivityCompat.requestPermissions(p2PGetMultActivity, new String[]{"android.permission.READ_CONTACTS"}, 119);
            return;
        }
        Intent a = is.a(p2PGetMultActivity, ContactsActivity.class);
        a.putExtra("fr.smoney.android.izly.intentExtrasModeContactPicker", 1);
        p2PGetMultActivity.startActivityForResult(a, 1);
    }

    private void k() {
        if (this.A.b) {
            this.n.c = true;
        } else {
            this.n.c = false;
        }
        this.n.b();
    }

    private void l() {
        this.s = null;
        this.t = null;
    }

    private boolean m() {
        return this.s != null;
    }

    private void n() {
        int a = j().a(this.u.b.a, this.u.b.c);
        findViewById(R.id.pb_recipient).setVisibility(0);
        super.a(a, 105, false);
    }

    private void o() {
        int i = 0;
        Object obj = this.e.getText().toString();
        if (!this.r && TextUtils.isEmpty(obj)) {
            a(hu.a(getString(R.string.dialog_error_title), getString(R.string.p2p_get_mult_dialog_error_no_amount), getString(17039370)));
        } else if (!this.r && !iu.b(obj)) {
            a(hu.a(getString(R.string.p2p_get_mult_dialog_error_invalid_amount_title), getString(R.string.p2p_get_mult_dialog_error_invalid_amount_message), getString(17039370)));
        } else if (this.n.b > this.u.b.e) {
            a(hu.a(getString(R.string.p2p_get_mult_dialog_error_max_reached_title), getString(R.string.p2p_get_mult_dialog_error_max_reached_message), getString(17039370)));
        } else {
            c cVar = this.n;
            if (cVar.a.size() != 0) {
                i = ((RecipientItem) cVar.a.get(0)).e ? cVar.a.size() - 1 : cVar.a.size();
            }
            if (i == 0) {
                a(hu.a(getString(R.string.dialog_error_title), getString(R.string.p2p_get_mult_dialog_error_no_recipients), getString(17039370)));
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            String charSequence = this.v.getText().toString();
            String obj2 = this.k.getText().toString();
            if (charSequence.length() > 0) {
                stringBuilder.append(charSequence);
                if (obj2.length() > 0) {
                    stringBuilder.append(" - ");
                }
            }
            stringBuilder.append(obj2);
            super.a(j().a(this.u.b.a, this.u.b.c, this.n.a(), stringBuilder.toString()), 66, true);
        }
    }

    public final void a(Contact contact) {
        a(contact.c);
        je.a(this, this.h);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 66) {
                    o();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case InputAmountType:
                this.n.a(this.q).d = bundle.getDouble("Data.Amount");
                this.n.b();
                return;
            case ChooseAttachmentType:
                switch (bundle.getInt("Data.SelectItem")) {
                    case 0:
                        try {
                            c.mkdirs();
                            Date date = new Date(System.currentTimeMillis());
                            this.p = new File(c, new SimpleDateFormat("'IMG_Smoney'_yyyyMMdd_HHmmss", Locale.getDefault()).format(date) + ".jpg");
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE", null);
                            intent.putExtra("output", Uri.fromFile(this.p));
                            startActivityForResult(intent, 3);
                            return;
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(this, R.string.subscribe_toast_photo_picker_not_found_message, 1).show();
                            return;
                        }
                    case 1:
                        startActivityForResult(new Intent("android.intent.action.PICK", gi.a), 2);
                        return;
                    default:
                        return;
                }
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a(iq iqVar, int i) {
        if (iqVar == this.o) {
            switch (i) {
                case 0:
                    c cVar = this.n;
                    int i2 = this.q;
                    if (!((RecipientItem) cVar.a.get(i2)).e) {
                        cVar.a.remove(i2);
                        cVar.b();
                        return;
                    }
                    return;
                case 1:
                    double a = this.n.a(i).a();
                    if (a == -1.0d) {
                        a(hy.a(getString(R.string.p2p_get_mult_dialog_configure_amount_title), null, null, this));
                        return;
                    } else {
                        a(hy.a(getString(R.string.p2p_get_mult_dialog_configure_amount_title), null, String.valueOf(a), this));
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 66) {
                a((P2PGetMultData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pGetMultData"), serverError);
            } else if (i2 == 105) {
                a((GetBookmarksData) bundle.getParcelable("fr.smoney.android.izly.extras.getBookmarksData"), serverError);
            }
        }
    }

    public final void b_(int i) {
        if (i == 66) {
            a(this.u.M, this.u.N);
        } else if (i == 105) {
            a(this.u.ao, this.u.ap);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        switch (i) {
            case 1:
                if (i2 == 1) {
                    this.n.a(new RecipientItem(intent.getStringExtra("resultIntentExtrasContactId"), false));
                    return;
                }
                return;
            case 2:
                if (i2 == -1) {
                    Cursor query = getContentResolver().query(intent.getData(), gi.b, null, null, null);
                    query.moveToFirst();
                    String string = query.getString(0);
                    String string2 = query.getString(1);
                    query.close();
                    a(string2, string);
                    return;
                }
                return;
            case 3:
                if (i2 == -1 && this.p != null) {
                    a(this.p.getName(), this.p.getAbsolutePath());
                    return;
                }
                return;
            case 513:
                if (i2 == -1) {
                    this.B = (P2PGetMultMotif) intent.getParcelableExtra("fr.smoney.android.izly.motif.ExtraMotif");
                    this.v.setText(this.B.a);
                    return;
                }
                return;
            case 514:
                if (i2 == -1) {
                    this.A = (P2PGetMultOptions) intent.getParcelableExtra("fr.smoney.android.izly.P2PGET_OPTIONS");
                    k();
                    return;
                }
                return;
            default:
                super.onActivityResult(i, i2, intent);
                return;
        }
    }

    public void onClick(View view) {
        if (view == this.m) {
            o();
        } else if (view == this.v) {
            r0 = new Intent(this, P2PGetMultMotifActivity.class);
            r0.putExtra("fr.smoney.android.izly.motif.ExtraMotif", this.B);
            a(r0, 513, false);
        } else if (view == this.y) {
            r0 = new Intent(this, P2PGetMultOptionActivity.class);
            r0.putExtra("fr.smoney.android.izly.P2PGET_OPTIONS", this.A);
            a(r0, 514, false);
        }
    }

    protected void onCreate(Bundle bundle) {
        CharSequence string;
        super.onCreate(bundle);
        setContentView((int) R.layout.p2p_get_mult);
        this.u = i();
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.v = (Button) findViewById(R.id.motif_spinner);
        this.w = (Spinner) findViewById(R.id.motif_spinnerf);
        this.e = (EditText) findViewById(R.id.et_amount);
        findViewById(R.id.p2p_get_info).setVisibility(0);
        findViewById(R.id.p2p_pict_info).setVisibility(0);
        this.f = (TextView) findViewById(R.id.tv_amount_symbol);
        this.g = (TextView) findViewById(R.id.p2p_get_mult_add_me);
        this.y = (Button) findViewById(R.id.bt_option);
        this.h = (ContactAutoCompleteTextView) findViewById(R.id.atv_recipient);
        this.i = (IconTextView) findViewById(R.id.iv_choose_contact);
        this.j = (ExpandableGridView) findViewById(R.id.gv_recipients);
        this.k = (EditText) findViewById(R.id.et_comment);
        this.l = (IconTextView) findViewById(R.id.icon_attachement);
        this.m = (Button) findViewById(R.id.b_submit);
        this.z = findViewById(R.id.atv_recipient);
        this.o = new ip(this);
        iq iqVar = this.o;
        iqVar.k.add(new d(this, R.drawable.quick_actions_cancel, R.string.p2p_get_mult_qa_delete));
        iqVar.i = true;
        this.o.j = this;
        this.m.setEnabled(true);
        this.v.setOnClickListener(this);
        this.y.setOnClickListener(this);
        this.e.setKeyListener(new ja(true, 3, 2));
        this.e.addTextChangedListener(this.F);
        EditText editText = this.e;
        if (ad.b) {
            string = getString(R.string.dialog_amount_entry_et_amount_hint_no_sign, new Object[]{Double.valueOf(this.u.b.d), Double.valueOf(this.u.b.e)});
        } else {
            string = getString(R.string.p2p_get_simple_tv_amount_hint);
        }
        editText.setHint(string);
        this.z.getViewTreeObserver().addOnGlobalLayoutListener(this.G);
        this.l.getViewTreeObserver().addOnGlobalLayoutListener(this.H);
        this.f.setText(Currency.getInstance(this.u.b.j).getSymbol());
        this.h.setOnEditorActionListener(this.E);
        this.j.setExpanded(true);
        this.n = new c(this);
        this.j.setAdapter(this.n);
        this.j.setOnItemClickListener(this);
        this.m.setOnClickListener(this);
        this.h.setOnContactClickListener(this);
        this.h.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ P2PGetMultActivity a;

            {
                this.a = r1;
            }

            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 1 || !P2PGetMultActivity.a(this.a, this.a.h, this.a.i, motionEvent)) {
                    return false;
                }
                P2PGetMultActivity.e(this.a);
                view.setPressed(false);
                view.clearFocus();
                view.invalidate();
                return true;
            }
        });
        this.k.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ P2PGetMultActivity a;

            {
                this.a = r1;
            }

            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 1 || !P2PGetMultActivity.a(this.a, this.a.k, this.a.l, motionEvent)) {
                    return false;
                }
                if (this.a.m()) {
                    this.a.l();
                } else {
                    this.a.a(ib.a(this.a.getString(R.string.p2p_get_mult_dialog_attachment_title), this.a.getResources().getTextArray(R.array.p2p_get_mult_choose_image), this.a, ie.ChooseAttachmentType));
                }
                view.setPressed(false);
                view.clearFocus();
                view.invalidate();
                return true;
            }
        });
        this.A = new P2PGetMultOptions();
        this.B = new P2PGetMultMotif();
        this.x = new gw(this, R.layout.izly_spinner, cg.values());
        this.w.setAdapter(this.x);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String string2 = extras.getString("defaultRecipientId");
            if (string2 != null) {
                this.n.a(new RecipientItem(string2, false));
            }
        }
        if (bundle != null) {
            String string3 = bundle.getString("savedStateAttachmentPath");
            CharSequence string4 = bundle.getString("savedStateRecipientText");
            CharSequence string5 = bundle.getString("savedStateMessageText");
            CharSequence string6 = bundle.getString("savedStateAmountText");
            ArrayList parcelableArrayList = bundle.getParcelableArrayList("savedStateRecipientsList");
            double d = bundle.getDouble("savedStateRecipientsAmountValue", 0.0d);
            this.r = bundle.getBoolean("savedStateAmountSetting", false);
            this.B = (P2PGetMultMotif) bundle.getParcelable("savedStateMotif");
            this.v.setText(this.B.a);
            this.A = (P2PGetMultOptions) bundle.getParcelable(d);
            k();
            if (string3 != null) {
                this.p = new File(string3);
                a(this.p.getName(), this.p.getAbsolutePath());
            } else {
                l();
            }
            if (string4 != null) {
                this.h.setText(string4);
            }
            if (string5 != null) {
                this.k.setText(string5);
            }
            if (string6 != null) {
                this.e.setText(string6);
            }
            if (parcelableArrayList != null && parcelableArrayList.size() > 0) {
                this.n.a = parcelableArrayList;
                this.n.a(d);
                this.n.notifyDataSetChanged();
            }
        } else {
            l();
        }
        this.h.addTextChangedListener(new TextWatcher(this) {
            final /* synthetic */ P2PGetMultActivity a;

            {
                this.a = r1;
            }

            public final void afterTextChanged(Editable editable) {
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (!this.a.C && !this.a.D) {
                    this.a.D = true;
                    if (ContextCompat.checkSelfPermission(this.a, "android.permission.READ_CONTACTS") != 0) {
                        ActivityCompat.requestPermissions(this.a, new String[]{"android.permission.READ_CONTACTS"}, 118);
                        return;
                    }
                    new b().execute(new Void[0]);
                }
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        if (this.u.ao == null) {
            n();
        } else {
            new a(this, this.u.ao.b).execute(new Void[0]);
            findViewById(R.id.pb_recipient).setVisibility(8);
        }
        jb.a(getApplicationContext(), R.string.screen_name_ask_money_collect_activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (adapterView == this.j && !this.n.a(i).e) {
            this.q = i;
            this.o.a(view);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                break;
        }
        return true;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        switch (i) {
            case 118:
                if (iArr.length > 0 && iArr[0] == 0) {
                    new b().execute(new Void[0]);
                    return;
                }
                return;
            case 119:
                if (iArr.length > 0 && iArr[0] == 0) {
                    Intent a = is.a(this, ContactsActivity.class);
                    a.putExtra("fr.smoney.android.izly.intentExtrasModeContactPicker", 1);
                    startActivityForResult(a, 1);
                    return;
                }
                return;
            default:
                return;
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.u.cw && !c(105)) {
            n();
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("savedStateAmountSetting", this.r);
        if (this.h.getText() != null && this.h.getText().length() > 0) {
            bundle.putString("savedStateRecipientText", this.h.getText().toString());
        }
        if (this.k.getText() != null && this.k.getText().length() > 0) {
            bundle.putString("savedStateMessageText", this.k.getText().toString());
        }
        if (this.e.getText() != null && this.e.getText().length() > 0) {
            bundle.putString("savedStateAmountText", this.e.getText().toString());
        }
        if (this.n != null && this.n.getCount() > 0) {
            bundle.putSerializable("savedStateRecipientsList", this.n.a);
            bundle.putDouble("savedStateRecipientsAmountValue", this.n.b);
        }
        if (this.p != null) {
            bundle.putString("savedStateAttachmentPath", this.p.getAbsolutePath());
        }
        if (this.B != null) {
            bundle.putParcelable("savedStateMotif", this.B);
        }
        if (this.A != null) {
            bundle.putParcelable(d, this.A);
        }
    }
}
