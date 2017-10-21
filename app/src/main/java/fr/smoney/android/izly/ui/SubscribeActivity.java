package fr.smoney.android.izly.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import defpackage.hx;
import defpackage.ib;
import defpackage.ie;
import defpackage.ii;
import defpackage.ij;
import defpackage.is;
import defpackage.iw;
import defpackage.jc;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.UserSubscribingValues;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.FormEditText;

import java.io.File;
import java.util.regex.Pattern;

import org.spongycastle.crypto.tls.CipherSuite;

public class SubscribeActivity extends SmoneyABSActivity implements OnClickListener, OnItemSelectedListener, SmoneyRequestManager$a {
    private static final String b = SubscribeActivity.class.getSimpleName();
    private String A;
    private ii B;
    private ii C;
    private ii D;
    private UserSubscribingValues E;
    private ImageView c;
    private Button d;
    private EditText e;
    private EditText f;
    private Button g;
    private FormEditText h;
    private FormEditText i;
    private FormEditText j;
    private FormEditText k;
    private FormEditText l;
    private Spinner m;
    private ArrayAdapter<CharSequence> n;
    private EditText o;
    private EditText p;
    private CheckBox q;
    private CheckBox r;
    private Button s;
    private MenuItem t;
    private Time u;
    private int v = 0;
    private File w;
    private Bitmap x;
    private boolean y = false;
    private String z;

    final class a extends ArrayAdapter<CharSequence> {
        final /* synthetic */ SubscribeActivity a;

        public a(SubscribeActivity subscribeActivity, Context context, int i, CharSequence[] charSequenceArr) {
            this.a = subscribeActivity;
            super(context, R.layout.subscribe_simple_spinner_item, charSequenceArr);
        }

        public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
            View inflate = view == null ? ((LayoutInflater) this.a.getSystemService("layout_inflater")).inflate(R.layout.subscribe_spinner_secret_question_dropdown_item, viewGroup, false) : view;
            ((TextView) inflate).setText((CharSequence) getItem(i));
            return inflate;
        }
    }

    final class b implements TextWatcher {
        final /* synthetic */ SubscribeActivity a;
        private View b;

        public b(SubscribeActivity subscribeActivity, View view) {
            this.a = subscribeActivity;
            this.b = view;
        }

        public final void afterTextChanged(Editable editable) {
            if (this.b.equals(this.a.k)) {
                this.a.D.a(this.a.k.getText().toString());
            } else if (this.b.equals(this.a.g)) {
                String charSequence = DateFormat.format("ddMMyy", this.a.u.toMillis(false)).toString();
                this.a.B.a(charSequence);
                this.a.C.a(charSequence);
            } else if (this.b.equals(this.a.o)) {
                if (this.a.o.getText().length() > 0) {
                    this.a.p.setEnabled(true);
                } else {
                    this.a.p.setEnabled(false);
                }
            }
            this.a.k();
        }

        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    private void a(UserSubscribingValues userSubscribingValues) {
        int keyAt;
        SmoneyRequestManager j = j();
        int size = j.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) j.b.valueAt(i);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 42 && intent.getParcelableExtra("fr.smoney.android.izly.extras.userSubscribingValuesCheck").equals(userSubscribingValues)) {
                keyAt = j.b.keyAt(i);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 42);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.userSubscribingValuesCheck", userSubscribingValues);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.bs = null;
        super.a(keyAt, 42, true);
    }

    private void b(ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (this.y) {
            r0 = this.E;
            r1 = is.a(this, SubscribeConfirmActivity.class);
            r1.putExtra("fr.smoney.android.izly.extras.userSubscribingValues", r0);
            startActivity(r1);
        } else {
            r0 = this.E;
            r1 = is.a(this, CGUActivity.class);
            r1.putExtra("fr.smoney.android.izly.extras.userSubscribingValues", r0);
            r1.putExtra("fr.smoney.android.izly.extras.displayCase", fr.smoney.android.izly.ui.CGUActivity.a.CGU_FOR_SUBSCRIBE);
            startActivityForResult(r1, 1339);
        }
    }

    private void k() {
        int selectedItemPosition = this.m.getSelectedItemPosition();
        Button button = this.s;
        boolean z = (this.v == -1 || TextUtils.isEmpty(this.e.getText().toString()) || TextUtils.isEmpty(this.f.getText().toString()) || selectedItemPosition == 0 || ((selectedItemPosition == 6 && (selectedItemPosition != 6 || TextUtils.isEmpty(this.o.getText().toString()))) || TextUtils.isEmpty(this.p.getText().toString()) || this.u == null || !this.h.c() || !this.i.c() || !this.j.c() || !this.k.c() || !this.l.c())) ? false : true;
        button.setEnabled(z);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 42) {
                    a(this.E);
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case ChoosePhotoType:
                switch (bundle.getInt("Data.SelectItem")) {
                    case 0:
                        this.w = jc.a(this, 1337);
                        return;
                    case 1:
                        jc.b(this, 1338);
                        return;
                    default:
                        return;
                }
            case ChangeOrRemovePhotoType:
                switch (bundle.getInt("Data.SelectItem")) {
                    case 0:
                        a(ib.a(getString(R.string.subscribe_dialog_photo_title), getResources().getTextArray(R.array.subscribe_choose_photo), this, ie.ChoosePhotoType));
                        return;
                    case 1:
                        this.x = null;
                        this.c.setImageResource(R.drawable.subscribe_iv_photo_placeholder);
                        return;
                    default:
                        return;
                }
            case SelectCivilityType:
                this.v = bundle.getInt("Data.SelectItem");
                this.d.setText(getResources().getStringArray(R.array.subscribe_civility)[this.v]);
                return;
            case SelectBirthDate:
                Long valueOf = Long.valueOf(bundle.getLong("Data.Date"));
                Time time = new Time();
                time.set(valueOf.longValue());
                this.u = time;
                this.g.setText(DateFormat.format("dd MMMM yyyy", valueOf.longValue()));
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 42) {
                b(serverError);
            }
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 42) {
            b(i2.bs);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1) {
            switch (i) {
                case 1337:
                    try {
                        MediaScannerConnection.scanFile(this, new String[]{this.w.getAbsolutePath()}, new String[]{null}, null);
                        Intent intent2 = new Intent("com.android.camera.action.CROP");
                        intent2.setDataAndType(Uri.fromFile(this.w), "image/*");
                        intent2.putExtra("scale", true);
                        intent2.putExtra("aspectX", 1);
                        intent2.putExtra("aspectY", 1);
                        intent2.putExtra("outputX", CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA);
                        intent2.putExtra("outputY", 160);
                        intent2.putExtra("return-data", true);
                        startActivityForResult(intent2, 1338);
                        return;
                    } catch (Throwable e) {
                        Log.e(b, "Cannot crop image", e);
                        Toast.makeText(this, R.string.subscribe_toast_photo_picker_not_found_message, 1).show();
                        return;
                    }
                case 1338:
                    Bitmap bitmap = (Bitmap) intent.getParcelableExtra("data");
                    if (bitmap == null) {
                        Uri data = intent.getData();
                        if (data != null) {
                            Intent intent3 = new Intent("com.android.camera.action.CROP");
                            intent3.setDataAndType(data, "image/*");
                            intent3.putExtra("scale", true);
                            intent3.putExtra("aspectX", 1);
                            intent3.putExtra("aspectY", 1);
                            intent3.putExtra("outputX", CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA);
                            intent3.putExtra("outputY", 160);
                            intent3.putExtra("return-data", true);
                            startActivityForResult(intent3, 1338);
                            return;
                        }
                        return;
                    }
                    this.x = bitmap;
                    this.c.setImageBitmap(this.x);
                    return;
                case 1339:
                    this.y = true;
                    return;
                default:
                    return;
            }
        }
    }

    public void onClick(View view) {
        if (view == this.d) {
            a(ib.a(getString(R.string.subscribe_dialog_civility_title), getResources().getTextArray(R.array.subscribe_civility), this, ie.SelectCivilityType));
        } else if (view == this.g) {
            a(hx.a(this.u, this, ie.SelectBirthDate));
        } else if (view == this.s) {
            this.z = this.k.getText().toString();
            int i = this.v;
            String obj = this.e.getText().toString();
            String obj2 = this.f.getText().toString();
            String obj3 = this.j.getText().toString();
            String charSequence = DateFormat.format("dd MMMM yyyy", this.u.toMillis(false)).toString();
            String obj4 = this.k.getText().toString();
            String obj5 = this.h.getText().toString();
            String obj6 = this.i.getText().toString();
            int selectedItemPosition = this.m.getSelectedItemPosition();
            if (selectedItemPosition == 6) {
                this.A = this.o.getText().toString();
            } else {
                this.A = getResources().getStringArray(R.array.subscribe_secret_question)[selectedItemPosition];
            }
            this.E = new UserSubscribingValues(i, obj, obj2, obj3, charSequence, obj4, obj5, obj6, this.A, this.p.getText().toString(), this.q.isChecked(), this.r.isChecked(), this.x != null ? iw.a(this.x) : null);
            a(this.E);
        } else if (view != this.c) {
        } else {
            if (this.x == null) {
                a(ib.a(getString(R.string.subscribe_dialog_photo_title), getResources().getTextArray(R.array.subscribe_choose_photo), this, ie.ChoosePhotoType));
            } else {
                a(ib.a(getString(R.string.subscribe_dialog_photo_title), getResources().getTextArray(R.array.subscribe_change_remove_photo), this, ie.ChangeOrRemovePhotoType));
            }
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.subscribe);
        getSupportActionBar().setHomeButtonEnabled(false);
        setTitle(R.string.subscribe_title);
        this.c = (ImageView) findViewById(R.id.iv_photo);
        this.c.setOnClickListener(this);
        this.d = (Button) findViewById(R.id.b_civility);
        this.d.setOnClickListener(this);
        this.d.addTextChangedListener(new b(this, this.d));
        this.e = (EditText) findViewById(R.id.et_first_name);
        this.e.addTextChangedListener(new b(this, this.e));
        this.f = (EditText) findViewById(R.id.et_last_name);
        this.f.addTextChangedListener(new b(this, this.f));
        this.g = (Button) findViewById(R.id.b_birth_date);
        this.g.setOnClickListener(this);
        this.g.addTextChangedListener(new b(this, this.g));
        this.h = (FormEditText) findViewById(R.id.et_phone);
        this.h.a(new ij(getString(R.string.subscribe_error_msg_for_phone), Pattern.compile("^(0|(\\+|00)33)[67][0-9]{8}$"), false));
        this.h.addTextChangedListener(new b(this, this.h));
        this.i = (FormEditText) findViewById(R.id.et_email);
        this.i.a(new ij(getString(R.string.subscribe_error_msg_for_mail), Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"), false));
        this.i.addTextChangedListener(new b(this, this.i));
        this.j = (FormEditText) findViewById(R.id.et_nickname);
        this.j.a(new ij(getString(R.string.subscribe_error_msg_for_alias), Pattern.compile("^([a-zA-Z])([a-zA-Z0-9_\\-\\.\\#]+)$"), false));
        this.j.addTextChangedListener(new b(this, this.j));
        this.k = (FormEditText) findViewById(R.id.et_password);
        this.k.a(new ij(getString(R.string.subscribe_error_msg_for_password_less_than_six_numbers), Pattern.compile("[0-9]{6}$"), false));
        this.k.a(new ij(getString(R.string.subscribe_error_msg_for_password_identical_numbers_are_not_allowed), Pattern.compile("0{6}|1{6}|2{6}|3{6}|4{6}|5{6}|6{6}|7{6}|8{6}|9{6}"), true));
        this.k.a(new ij(getString(R.string.subscribe_error_msg_for_password_series_are_not_allowed), Pattern.compile("012345|123456|234567|345678|456789|567890|678901|789012|890123|901234|987654|876543|765432|654321|543210|432109|321098|210987|109876|098765"), true));
        this.B = new ii(this.k, getString(R.string.subscribe_error_msg_for_password_birthday_date_not_allowed), null, true);
        this.k.a(this.B);
        this.k.addTextChangedListener(new b(this, this.k));
        this.l = (FormEditText) findViewById(R.id.et_password_confirm);
        this.D = new ii(this.l, getString(R.string.subscribe_error_msg_for_password_not_equal_to_password), this.k.getText().toString(), false);
        this.l.a(this.D);
        this.l.a(new ij(getString(R.string.subscribe_error_msg_for_password_less_than_six_numbers), Pattern.compile("[0-9]{6}$"), false));
        this.l.a(new ij(getString(R.string.subscribe_error_msg_for_password_identical_numbers_are_not_allowed), Pattern.compile("0{6}|1{6}|2{6}|3{6}|4{6}|5{6}|6{6}|7{6}|8{6}|9{6}"), true));
        this.l.a(new ij(getString(R.string.subscribe_error_msg_for_password_series_are_not_allowed), Pattern.compile("012345|123456|234567|345678|456789|567890|678901|789012|890123|901234|987654|876543|765432|654321|543210|432109|321098|210987|109876|098765"), true));
        this.C = new ii(this.l, getString(R.string.subscribe_error_msg_for_password_birthday_date_not_allowed), null, true);
        this.l.a(this.C);
        this.l.addTextChangedListener(new b(this, this.l));
        this.m = (Spinner) findViewById(R.id.sp_secret_question);
        this.n = new a(this, this, R.layout.subscribe_simple_spinner_item, getResources().getTextArray(R.array.subscribe_secret_question));
        this.m.setAdapter(this.n);
        this.m.setOnItemSelectedListener(this);
        this.o = (EditText) findViewById(R.id.et_secret_question_custom);
        this.o.addTextChangedListener(new b(this, this.o));
        this.p = (EditText) findViewById(R.id.et_secret_answer);
        this.p.addTextChangedListener(new b(this, this.p));
        this.q = (CheckBox) findViewById(R.id.cb_smoney_opt_in);
        this.r = (CheckBox) findViewById(R.id.cb_partner_opt_in);
        this.s = (Button) findViewById(R.id.b_submit);
        this.s.setOnClickListener(this);
        if (bundle != null) {
            long j = bundle.getLong("savedStateBirthDateTime", -1);
            if (j != -1) {
                this.u = new Time();
                this.u.set(j);
            }
            this.v = bundle.getInt("savedStateCivility", 0);
            this.A = bundle.getString("savedSecretQuestion");
            this.y = bundle.getBoolean("savedStateCGUValidated", false);
            this.z = bundle.getString("savedCurrentPassword");
        }
        this.d.setText(getResources().getStringArray(R.array.subscribe_civility)[this.v]);
        if (this.u != null) {
            this.g.setText(DateFormat.format("dd MMMM yyyy", this.u.toMillis(false)));
        }
        if (this.A != null) {
            this.o.setText(this.A);
        }
        k();
        findViewById(R.id.v_focus).requestFocus();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.t = menu.add(R.string.menu_item_help);
        this.t.setIcon(R.drawable.pict_ab_help);
        this.t.setShowAsAction(2);
        return super.onCreateOptionsMenu(menu);
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        this.o.setVisibility(i == 6 ? 0 : 8);
        this.p.setEnabled(i != 0);
        if (i == 6) {
            this.p.setEnabled(false);
        }
        k();
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem == this.t) {
            startActivity(is.a(this, WebViewHelperActivity.class));
        }
        return super.onOptionsItemSelected(menuItem);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("savedStateCGUValidated", this.y);
        if (this.u != null) {
            bundle.putLong("savedStateBirthDateTime", this.u.toMillis(false));
        }
        bundle.putInt("savedStateCivility", this.v);
        if (this.A != null) {
            bundle.putString("savedSecretQuestion", this.A);
        }
        bundle.putString("savedCurrentPassword", this.z);
    }
}
