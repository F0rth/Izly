package fr.smoney.android.izly.ui;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
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
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.IconTextView;
import android.widget.TextView;
import android.widget.Toast;

import defpackage.gd;
import defpackage.gi;
import defpackage.hu;
import defpackage.hw;
import defpackage.ib;
import defpackage.ie;
import defpackage.if$a;
import defpackage.is;
import defpackage.iu;
import defpackage.iw;
import defpackage.iy;
import defpackage.ja;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.Bookmark;
import fr.smoney.android.izly.data.model.Contact;
import fr.smoney.android.izly.data.model.GetBookmarksData;
import fr.smoney.android.izly.data.model.P2PGetMultData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.ui.widget.ContactAutoCompleteTextView;
import fr.smoney.android.izly.ui.widget.ContactAutoCompleteTextView.c;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class P2PGetSimpleActivity extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a, c, if$a {
    private static final File b = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    private ContactAutoCompleteTextView c;
    private EditText d;
    private EditText e;
    private TextView f;
    private IconTextView g;
    private IconTextView h;
    private View i;
    private Button j;
    private boolean k = true;
    private String l;
    private String m;
    private File n;
    private ge o;
    private cl p;
    private boolean q = false;
    private boolean r = false;
    @SuppressLint({"NewApi"})
    private OnGlobalLayoutListener s = new OnGlobalLayoutListener(this) {
        final /* synthetic */ P2PGetSimpleActivity a;

        {
            this.a = r1;
        }

        public final void onGlobalLayout() {
            this.a.c.setPadding(this.a.c.getPaddingLeft(), this.a.c.getPaddingTop(), this.a.i.getWidth(), this.a.c.getPaddingBottom());
            if (VERSION.SDK_INT < 16) {
                this.a.i.getViewTreeObserver().removeGlobalOnLayoutListener(this.a.s);
            } else {
                this.a.i.getViewTreeObserver().removeOnGlobalLayoutListener(this.a.s);
            }
        }
    };
    @SuppressLint({"NewApi"})
    private OnGlobalLayoutListener t = new OnGlobalLayoutListener(this) {
        final /* synthetic */ P2PGetSimpleActivity a;

        {
            this.a = r1;
        }

        public final void onGlobalLayout() {
            this.a.c.setPadding(this.a.e.getPaddingLeft(), this.a.e.getPaddingTop(), this.a.h.getWidth(), this.a.e.getPaddingBottom());
            if (VERSION.SDK_INT < 16) {
                this.a.i.getViewTreeObserver().removeGlobalOnLayoutListener(this.a.t);
            } else {
                this.a.i.getViewTreeObserver().removeOnGlobalLayoutListener(this.a.t);
            }
        }
    };
    private TextWatcher u = new TextWatcher(this) {
        final /* synthetic */ P2PGetSimpleActivity a;

        {
            this.a = r1;
        }

        public final void afterTextChanged(Editable editable) {
            ImageSpan[] imageSpanArr = (ImageSpan[]) editable.getSpans(0, editable.length(), ImageSpan.class);
            if (imageSpanArr.length > 0 && imageSpanArr[0].getSource().length() != editable.length()) {
                this.a.c.setText(imageSpanArr[0].getSource());
            }
            this.a.m();
        }

        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    };
    private TextWatcher v = new TextWatcher(this) {
        final /* synthetic */ P2PGetSimpleActivity a;

        {
            this.a = r1;
        }

        public final void afterTextChanged(Editable editable) {
            this.a.k = editable.toString().length() == 0;
            this.a.m();
        }

        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    };

    final class a extends AsyncTask<Void, Void, List<Contact>> {
        final /* synthetic */ P2PGetSimpleActivity a;
        private List<Bookmark> b;

        public a(P2PGetSimpleActivity p2PGetSimpleActivity, List<Bookmark> list) {
            this.a = p2PGetSimpleActivity;
            this.b = list;
        }

        protected final /* synthetic */ Object doInBackground(Object[] objArr) {
            return iy.b(this.b);
        }

        protected final /* synthetic */ void onPostExecute(Object obj) {
            this.a.c.a((List) obj);
        }
    }

    final class b extends AsyncTask<Void, Void, List<Contact>> {
        final /* synthetic */ P2PGetSimpleActivity a;

        private b(P2PGetSimpleActivity p2PGetSimpleActivity) {
            this.a = p2PGetSimpleActivity;
        }

        protected final /* synthetic */ Object doInBackground(Object[] objArr) {
            return iy.a(this.a);
        }

        protected final /* synthetic */ void onPostExecute(Object obj) {
            this.a.c.a((List) obj);
            this.a.q = true;
        }
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
            Intent a = is.a(this, P2PGetSimpleConfirmActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.p2pGetMultData", p2PGetMultData);
            if (l()) {
                a.putExtra("fr.smoney.android.izly.extras.attachmentName", this.l);
                a.putExtra("fr.smoney.android.izly.extras.attachmentPath", this.m);
            }
            startActivity(a);
        }
    }

    private void a(String str, String str2) {
        this.l = str;
        this.m = str2;
        iw.b(iw.a(new File(this.m), this.h.getWidth() + 10, this.h.getHeight() + 10));
    }

    static /* synthetic */ boolean a(P2PGetSimpleActivity p2PGetSimpleActivity, View view, View view2, MotionEvent motionEvent) {
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        view2.getGlobalVisibleRect(rect);
        view.getGlobalVisibleRect(rect2);
        return rect.contains(((int) motionEvent.getX()) + rect2.left, rect2.top + ((int) motionEvent.getY()));
    }

    static /* synthetic */ void e(P2PGetSimpleActivity p2PGetSimpleActivity) {
        if (ContextCompat.checkSelfPermission(p2PGetSimpleActivity, "android.permission.READ_CONTACTS") != 0) {
            ActivityCompat.requestPermissions(p2PGetSimpleActivity, new String[]{"android.permission.READ_CONTACTS"}, 119);
            return;
        }
        Intent a = is.a(p2PGetSimpleActivity, ContactsActivity.class);
        a.putExtra("fr.smoney.android.izly.intentExtrasModeContactPicker", 1);
        p2PGetSimpleActivity.startActivityForResult(a, 1);
    }

    private void k() {
        this.l = null;
        this.m = null;
    }

    private boolean l() {
        return this.l != null;
    }

    private void m() {
        this.j.setEnabled(!TextUtils.isEmpty(this.c.getText().toString()));
    }

    private void n() {
        findViewById(R.id.pb_recipient).setVisibility(0);
        super.a(j().a(this.p.b.a, this.p.b.c), 105, false);
    }

    private void o() {
        String obj = this.d.getText().toString();
        double d = 0.0d;
        try {
            d = iu.a(obj);
        } catch (gd e) {
        }
        if (!this.k && !iu.b(obj)) {
            a(hu.a(getString(R.string.p2p_get_mult_dialog_error_invalid_amount_title), getString(R.string.p2p_get_mult_dialog_error_invalid_amount_message), getString(17039370)));
        } else if (d > this.p.b.e) {
            a(hu.a(getString(R.string.p2p_get_mult_dialog_error_max_reached_title), getString(R.string.p2p_get_mult_dialog_error_max_reached_message), getString(17039370)));
        } else if (this.c.getText().length() == 0) {
            a(hu.a(getString(R.string.dialog_error_title), getString(R.string.p2p_get_simple_dialog_error_no_recipient), getString(17039370)));
        } else {
            SmoneyRequestManager j = j();
            obj = this.p.b.a;
            String str = this.p.b.c;
            String str2 = "" + this.c.getText().toString();
            if (!this.k) {
                str2 = (str2 + ":") + this.d.getText().toString();
            }
            super.a(j.a(obj, str, str2, this.e.getText().toString()), 66, true);
        }
    }

    public final void a(Contact contact) {
        if (contact.g == fr.smoney.android.izly.data.model.Contact.a.a) {
            String str = contact.c;
        }
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
            case ChooseAttachmentType:
                switch (bundle.getInt("Data.SelectItem")) {
                    case 0:
                        try {
                            b.mkdirs();
                            Date date = new Date(System.currentTimeMillis());
                            this.n = new File(b, new SimpleDateFormat("'IMG_Smoney'_yyyyMMdd_HHmmss", Locale.getDefault()).format(date) + ".jpg");
                            if (this.n.exists()) {
                                this.n.delete();
                            }
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE", null);
                            intent.putExtra("output", Uri.fromFile(this.n));
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
            a(this.p.M, this.p.N);
        } else if (i == 105) {
            a(this.p.ao, this.p.ap);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        switch (i) {
            case 1:
                if (i2 == 1) {
                    this.c.setText(intent.getStringExtra("resultIntentExtrasContactId"));
                    intent.getStringExtra("resultIntentExtrasContactId");
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
                if (i2 == -1 && this.n != null) {
                    a(this.n.getName(), this.n.getAbsolutePath());
                    return;
                }
                return;
            default:
                super.onActivityResult(i, i2, intent);
                return;
        }
    }

    public void onClick(View view) {
        if (view == this.j) {
            o();
        }
    }

    protected void onCreate(Bundle bundle) {
        CharSequence string;
        super.onCreate(bundle);
        setContentView((int) R.layout.p2p_get_simple);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.o = new ge(this);
        this.p = i();
        this.c = (ContactAutoCompleteTextView) findViewById(R.id.atv_recipient);
        this.c.setImeOptions(5);
        this.d = (EditText) findViewById(R.id.et_amount);
        this.e = (EditText) findViewById(R.id.et_comment);
        findViewById(R.id.p2p_get_info).setVisibility(0);
        this.f = (TextView) findViewById(R.id.tv_amount_symbol);
        this.g = (IconTextView) findViewById(R.id.iv_choose_contact);
        this.h = (IconTextView) findViewById(R.id.icon_attachement);
        this.j = (Button) findViewById(R.id.b_submit);
        this.j.setEnabled(true);
        this.i = findViewById(R.id.atv_recipient);
        this.d.addTextChangedListener(this.v);
        this.d.setKeyListener(new ja(true, 3, 2));
        EditText editText = this.d;
        if (ad.b) {
            string = getString(R.string.dialog_amount_entry_et_amount_hint_no_sign, new Object[]{Double.valueOf(this.p.b.d), Double.valueOf(this.p.b.e)});
        } else {
            string = getString(R.string.p2p_get_simple_tv_amount_hint);
        }
        editText.setHint(string);
        this.c.addTextChangedListener(this.u);
        this.c.setOnContactClickListener(this);
        this.c.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ P2PGetSimpleActivity a;

            {
                this.a = r1;
            }

            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 1 || !P2PGetSimpleActivity.a(this.a, this.a.c, this.a.g, motionEvent)) {
                    return false;
                }
                P2PGetSimpleActivity.e(this.a);
                view.clearFocus();
                return true;
            }
        });
        this.e.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ P2PGetSimpleActivity a;

            {
                this.a = r1;
            }

            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 1 || !P2PGetSimpleActivity.a(this.a, this.a.e, this.a.h, motionEvent)) {
                    return false;
                }
                if (this.a.l()) {
                    this.a.k();
                } else {
                    this.a.a(ib.a(this.a.getString(R.string.p2p_get_mult_dialog_attachment_title), this.a.getResources().getTextArray(R.array.p2p_get_mult_choose_image), this.a, ie.ChooseAttachmentType));
                }
                view.clearFocus();
                return true;
            }
        });
        this.i.getViewTreeObserver().addOnGlobalLayoutListener(this.s);
        this.h.getViewTreeObserver().addOnGlobalLayoutListener(this.t);
        this.j.setOnClickListener(this);
        m();
        this.f.setText(Currency.getInstance(this.p.b.j).getSymbol());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            string = extras.getString("defaultRecipientId");
            if (string != null) {
                this.c.setText(string);
            }
        }
        if (bundle != null) {
            String string2 = bundle.getString("savedStateAttachmentPath");
            CharSequence string3 = bundle.getString("savedStateRecipientText");
            CharSequence string4 = bundle.getString("savedStateMessageText");
            CharSequence string5 = bundle.getString("savedStateAmountText");
            CharSequence string6 = bundle.getString("savedStateRecipientId");
            this.k = bundle.getBoolean("savedStateAmountSetting", false);
            if (string2 != null) {
                this.n = new File(string2);
                a(this.n.getName(), this.n.getAbsolutePath());
            } else {
                k();
            }
            if (string6 != null) {
                this.c.setText(string6);
            }
            if (string3 != null) {
                this.c.setText(string3);
            }
            if (string4 != null) {
                this.e.setText(string4);
            }
            if (string5 != null) {
                this.d.setText(string5);
            }
        } else {
            k();
        }
        this.c.addTextChangedListener(new TextWatcher(this) {
            final /* synthetic */ P2PGetSimpleActivity a;

            {
                this.a = r1;
            }

            public final void afterTextChanged(Editable editable) {
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (!this.a.q && !this.a.r) {
                    this.a.r = true;
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
        if (this.p.ao == null) {
            n();
        } else {
            new a(this, this.p.ao.b).execute(new Void[0]);
            findViewById(R.id.pb_recipient).setVisibility(8);
        }
        jb.a(getApplicationContext(), R.string.screen_name_ask_money_simple_activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
        if (this.p.cw && !c(105)) {
            n();
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("savedStateAmountSetting", this.k);
        if (this.e.getText() != null && this.e.getText().length() > 0) {
            bundle.putString("savedStateRecipientText", this.c.getText().toString());
        }
        if (this.e.getText() != null && this.e.getText().length() > 0) {
            bundle.putString("savedStateMessageText", this.e.getText().toString());
        }
        if (this.d.getText() != null && this.d.getText().length() > 0) {
            bundle.putString("savedStateAmountText", this.d.getText().toString());
        }
        if (this.c.getText() != null && this.c.getText().length() > 0) {
            bundle.putString("savedStateRecipientId", this.c.getText().toString());
        }
        if (this.n != null) {
            bundle.putString("savedStateAttachmentPath", this.n.getAbsolutePath());
        }
    }
}
