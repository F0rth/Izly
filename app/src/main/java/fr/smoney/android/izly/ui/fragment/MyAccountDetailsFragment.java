package fr.smoney.android.izly.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.ad4screen.sdk.A4S;

import defpackage.gt;
import defpackage.ht;
import defpackage.hu;
import defpackage.hw;
import defpackage.hx;
import defpackage.ib;
import defpackage.ie;
import defpackage.ih;
import defpackage.ij;
import defpackage.jb;
import defpackage.jk;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.GetContactDetailsData;
import fr.smoney.android.izly.data.model.LoginData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.UpdateUserProfileData;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.FormEditText;

import java.lang.ref.WeakReference;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.regex.Pattern;

public class MyAccountDetailsFragment extends aa implements TextWatcher, OnClickListener, SmoneyRequestManager$a {
    private static final String e = MyAccountDetailsFragment.class.getName();
    private static int f = 2000;
    @Bind({2131755687})
    EditText etTwitter;
    private GetContactDetailsData g;
    private View h;
    private Time i;
    @Bind({2131755684})
    TextView izlyCard;
    @Bind({2131755686})
    EditText izlyEmail;
    private gt j;
    private int k = 0;
    private b l;
    private a m;
    @Bind({2131755206})
    Button mButtonBirthDate;
    @Bind({2131755200})
    Button mButtonCivility;
    @Bind({2131755325})
    Button mButtonModify;
    @Bind({2131755695})
    CheckBox mCheckBoxOptIn;
    @Bind({2131755696})
    CheckBox mCheckBoxOptInPartners;
    @Bind({2131755690})
    EditText mEditTextCity;
    @Bind({2131755189})
    FormEditText mEditTextEmail;
    @Bind({2131755694})
    EditText mEditTextEndDateCrous;
    @Bind({2131755682})
    EditText mEditTextFirstName;
    @Bind({2131755683})
    EditText mEditTextLastName;
    @Bind({2131755685})
    EditText mEditTextNickname;
    @Bind({2131755689})
    EditText mEditTextPostalCode;
    @Bind({2131755693})
    EditText mEditTextPriceCode;
    @Bind({2131755692})
    EditText mEditTextSocietyCode;
    @Bind({2131755688})
    EditText mEditTextStreet;
    @Bind({2131755359})
    Spinner mSpinnerCountry;
    @Bind({2131755396})
    FormEditText mTextViewPhone;
    @Bind({2131755681})
    View mTextViewUpdatedProfile;

    final class a extends BroadcastReceiver {
        final /* synthetic */ MyAccountDetailsFragment a;

        private a(MyAccountDetailsFragment myAccountDetailsFragment) {
            this.a = myAccountDetailsFragment;
        }

        public final void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("fr.smoney.android.izly.sessionState", -1) == 1) {
                this.a.u();
            }
        }
    }

    static final class b extends Handler {
        WeakReference<View> a;

        public b(View view) {
            this.a = new WeakReference(view);
        }

        public final void handleMessage(Message message) {
            if (message.what == 0) {
                ((View) this.a.get()).setVisibility(8);
            }
        }
    }

    private void a(TextView textView) {
        textView.setEnabled(false);
        textView.setTextColor(getResources().getColor(R.color.disable_tv));
    }

    private void a(GetContactDetailsData getContactDetailsData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getContactDetailsData == null) {
            a(hw.a(getActivity(), this));
        } else {
            this.g = getContactDetailsData;
            o();
        }
    }

    private void a(UpdateUserProfileData updateUserProfileData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (updateUserProfileData == null) {
            a(hw.a(getActivity(), this));
        } else {
            cl i = i();
            if (!i.b.b()) {
                i.b.p = this.mEditTextFirstName.getText().toString();
                i.b.q = this.mEditTextLastName.getText().toString();
                i.b.C = this.mCheckBoxOptIn.isChecked();
                i.b.D = this.mCheckBoxOptInPartners.isChecked();
            }
            this.mTextViewUpdatedProfile.setVisibility(0);
            ((ScrollView) this.h.findViewById(R.id.sv_my_account_details)).smoothScrollTo(0, 0);
            t();
            this.l.sendEmptyMessageDelayed(0, (long) f);
            LoginData loginData = i.b;
            String str = "";
            String str2 = "";
            if (loginData.C) {
                str = "Y";
            }
            if (loginData.D) {
                str2 = "Y";
            }
            Bundle bundle = new Bundle();
            bundle.putString("optin_smoney", str);
            bundle.putString("optin_partenaires", str2);
            A4S.get(getActivity()).updateDeviceInfo(bundle);
        }
    }

    public static MyAccountDetailsFragment n() {
        return new MyAccountDetailsFragment();
    }

    private void o() {
        cl i = i();
        this.i = new Time();
        this.i.set(jk.c(this.g.u));
        this.mEditTextFirstName.setText(this.g.x);
        this.mEditTextLastName.setText(this.g.y);
        this.mButtonBirthDate.setText(DateFormat.format("dd/MM/yyyy", this.i.toMillis(true)));
        this.mTextViewPhone.setText(this.g.e);
        this.mEditTextNickname.setText(this.g.b);
        this.mEditTextEmail.setText(this.g.w);
        this.mEditTextStreet.setText(this.g.q);
        this.mEditTextPostalCode.setText(this.g.r);
        this.mEditTextCity.setText(this.g.s);
        this.izlyCard.setText(this.g.B);
        this.etTwitter.setText(this.g.A);
        this.izlyEmail.setText(this.g.z);
        this.mEditTextPriceCode.setText(this.g.E);
        this.mEditTextEndDateCrous.setText(DateFormat.format("dd/MM/yyyy", jk.c(this.g.D)));
        this.mEditTextSocietyCode.setText(String.valueOf(this.g.C));
        if (i.b.b()) {
            a(this.mEditTextFirstName);
            a(this.mEditTextLastName);
            a(this.mEditTextStreet);
            a(this.mEditTextPostalCode);
            a(this.mEditTextCity);
            this.mButtonBirthDate.setEnabled(false);
            this.mSpinnerCountry.setEnabled(false);
            this.mButtonCivility.setEnabled(false);
        }
        this.mCheckBoxOptIn.setChecked(this.g.F);
        this.mCheckBoxOptInPartners.setChecked(this.g.G);
        this.k = this.g.v;
        fr.smoney.android.izly.data.model.AddressValues.a a = fr.smoney.android.izly.data.model.AddressValues.a.a(this.g.t);
        if (a != null) {
            this.mSpinnerCountry.setSelection(a.ordinal());
        }
        r();
        this.mEditTextFirstName.setSelection(this.mEditTextFirstName.getText().length());
    }

    private void p() {
        if (TextUtils.isEmpty(this.mEditTextFirstName.getText().toString()) || TextUtils.isEmpty(this.mEditTextLastName.getText().toString()) || !this.mEditTextEmail.c() || (this.g.q != null && (TextUtils.isEmpty(this.mEditTextStreet.getText()) || (this.g.s != null && (TextUtils.isEmpty(this.mEditTextCity.getText()) || (this.g.r != null && TextUtils.isEmpty(this.mEditTextPostalCode.getText()))))))) {
            this.mButtonModify.setEnabled(false);
        } else {
            this.mButtonModify.setEnabled(true);
        }
    }

    private int q() {
        return this.j.getCount() > 0 ? ((fr.smoney.android.izly.data.model.AddressValues.a) this.j.getItem(this.mSpinnerCountry.getSelectedItemPosition())).F : 0;
    }

    private void r() {
        this.mButtonCivility.setText(getResources().getStringArray(R.array.subscribe_civility)[this.k]);
    }

    private void s() {
        a(hu.a(getString(R.string.modif_user_profile_title), getString(R.string.modif_user_profile_message), getString(17039370)));
    }

    private void t() {
        if (this.l.hasMessages(0)) {
            this.l.removeMessages(0);
        }
    }

    private void u() {
        cl i = i();
        super.a(j().c(i.b.a, i.b.c, i.b.a, null, null), 224, true);
    }

    private void v() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        int i2 = this.k;
        String obj = this.mEditTextFirstName.getText().toString();
        String obj2 = this.mEditTextLastName.getText().toString();
        String a = jk.a(this.i);
        String obj3 = this.mTextViewPhone.getText().toString();
        String obj4 = this.mEditTextEmail.getText().toString();
        String obj5 = this.mEditTextStreet.getText().toString();
        String obj6 = this.mEditTextPostalCode.getText().toString();
        String obj7 = this.mEditTextCity.getText().toString();
        int q = q();
        int i3 = this.mCheckBoxOptIn.isChecked() ? 1 : 0;
        int i4 = this.mCheckBoxOptInPartners.isChecked() ? 1 : 0;
        String obj8 = this.etTwitter.getText().toString();
        int size = j.b.size();
        for (int i5 = 0; i5 < size; i5++) {
            Intent intent = (Intent) j.b.valueAt(i5);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 173 && intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileSessionId").equals(str2) && intent.getIntExtra("fr.smoney.android.izly.extras.updateUserProfileCivility", 0) == i2 && intent.getIntExtra("fr.smoney.android.izly.extras.updateUserProfileCountry", 0) == q && intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileFirstName").equals(obj) && intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileLastName").equals(obj2) && intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileBirthDate").equals(a) && intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileActivity").equals("") && intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileAlias").equals("") && intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfilePhone").equals(obj3) && intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileEmail").equals(obj4) && intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileWebsite").equals("") && intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileAddress").equals(obj5) && intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfilePostCode").equals(obj6) && intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileCity").equals(obj7) && intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileTwitter").equals(obj8) && intent.getStringExtra("fr.smoney.android.izly.extras.updateUserProfileCommercialMessage").equals("") && intent.getIntExtra("fr.smoney.android.izly.extras.updateUserProfileOptIn", -1) == i3 && intent.getIntExtra("fr.smoney.android.izly.extras.updateUserProfileOptInPartners", -1) == i4) {
                keyAt = j.b.keyAt(i5);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 173);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileCivility", i2);
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileFirstName", obj);
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileLastName", obj2);
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileBirthDate", a);
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileActivity", "");
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileAlias", "");
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfilePhone", obj3);
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileEmail", obj4);
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileWebsite", "");
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileAddress", obj5);
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfilePostCode", obj6);
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileCity", obj7);
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileCountry", q);
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileCommercialMessage", "");
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileOptIn", i3);
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileOptInPartners", i4);
        intent2.putExtra("fr.smoney.android.izly.extras.updateUserProfileTwitter", obj8);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.aM = null;
        j.f.aN = null;
        super.a(keyAt, 173, true);
        EditText editText = this.mEditTextPostalCode;
        Bundle bundle = new Bundle();
        bundle.putString("code_postal", editText.getText().toString());
        A4S.get(getActivity()).updateDeviceInfo(bundle);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                switch (h()) {
                    case 173:
                        v();
                        return;
                    case 224:
                        u();
                        return;
                    default:
                        super.a(ieVar, bundle);
                        return;
                }
            case SelectCivilityType:
                this.k = bundle.getInt("Data.SelectItem");
                r();
                return;
            case SelectBirthDate:
                this.i.set(Long.valueOf(bundle.getLong("Data.Date")).longValue());
                this.mButtonBirthDate.setText(DateFormat.format("dd/MM/yyyy", this.i.toMillis(false)));
                p();
                return;
            case OtherCountryChosenAlertType:
                v();
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final boolean a() {
        this.mTextViewPhone.a();
        this.mEditTextEmail.a();
        return super.a();
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            switch (i2) {
                case 173:
                    a((UpdateUserProfileData) bundle.getParcelable("fr.smoney.android.izly.extras.updateUserProfileData"), serverError);
                    return;
                case 224:
                    a((GetContactDetailsData) bundle.getParcelable("fr.smoney.android.izly.extras.GetContactDetails"), serverError);
                    return;
                default:
                    return;
            }
        }
    }

    public void afterTextChanged(Editable editable) {
        p();
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() != 224) {
                    super.b(ieVar);
                    return;
                } else if (this.d instanceof ih) {
                    ((ih) this.d).e();
                    return;
                } else {
                    return;
                }
            default:
                super.b(ieVar);
                return;
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        switch (i) {
            case 173:
                a(i2.aM, i2.aN);
                return;
            case 224:
                a(i2.bq, i2.br);
                return;
            default:
                return;
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public final void c(ie ieVar) {
        switch (ieVar) {
            case ErrorType:
                if (h() == 224 && (this.d instanceof ih)) {
                    ((ih) this.d).e();
                    return;
                }
                return;
            default:
                super.c(ieVar);
                return;
        }
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType && h() == 224) {
            g();
            if (this.d instanceof ih) {
                ((ih) this.d).e();
                return;
            }
            return;
        }
        super.d(ieVar);
    }

    protected final String k() {
        return getString(R.string.my_account_title);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        b(true);
        this.mEditTextFirstName.addTextChangedListener(this);
        this.mEditTextLastName.addTextChangedListener(this);
        this.mButtonBirthDate.setOnClickListener(this);
        this.mEditTextEmail.setNoIcon();
        this.mEditTextEmail.a(new ij(getString(R.string.subscribe_error_msg_for_mail), Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"), false));
        this.mEditTextEmail.addTextChangedListener(this);
        this.mEditTextStreet.addTextChangedListener(this);
        this.mEditTextPostalCode.addTextChangedListener(this);
        this.mEditTextCity.addTextChangedListener(this);
        this.mEditTextFirstName.setOnClickListener(this);
        this.mEditTextLastName.setOnClickListener(this);
        this.mEditTextStreet.setOnClickListener(this);
        this.mEditTextPostalCode.setOnClickListener(this);
        this.mEditTextCity.setOnClickListener(this);
        this.j = new gt(getActivity());
        Iterator it = EnumSet.allOf(fr.smoney.android.izly.data.model.AddressValues.a.class).iterator();
        while (it.hasNext()) {
            this.j.add((fr.smoney.android.izly.data.model.AddressValues.a) it.next());
        }
        this.mSpinnerCountry.setAdapter(this.j);
        this.mButtonCivility.setOnClickListener(this);
        this.mButtonModify.setOnClickListener(this);
        this.l = new b(this.mTextViewUpdatedProfile);
        if (bundle != null) {
            this.g = (GetContactDetailsData) bundle.getParcelable("savedStateContactDetailsData");
        }
        if (this.g == null) {
            u();
        } else {
            o();
        }
    }

    public void onClick(View view) {
        if (view == this.mButtonModify) {
            if (q() != fr.smoney.android.izly.data.model.AddressValues.a.Other.F) {
                v();
            } else {
                a(ht.a(getString(R.string.my_account_inscription), getString(R.string.my_account_other_country_error), getString(R.string.my_account_confirm), getString(17039360), this, ie.OtherCountryChosenAlertType));
            }
        } else if (view == this.mButtonBirthDate) {
            if (i().b.b()) {
                s();
            } else {
                a(hx.a(this.i, this, ie.SelectBirthDate));
            }
        } else if (view == this.mEditTextFirstName || view == this.mEditTextLastName || view == this.mEditTextCity || view == this.mEditTextPostalCode || view == this.mEditTextStreet || view == this.mButtonBirthDate) {
            if (i().b.b()) {
                s();
            }
        } else if (view != this.mButtonCivility) {
        } else {
            if (i().b.b()) {
                s();
            } else {
                a(ib.a(getString(R.string.subscribe_dialog_civility_title), getResources().getTextArray(R.array.subscribe_civility), this, ie.SelectCivilityType));
            }
        }
    }

    public void onCreate(Bundle bundle) {
        this.c = new ge(this.d);
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.h = layoutInflater.inflate(R.layout.my_account_details, viewGroup, false);
        ButterKnife.bind(this, this.h);
        jb.a(getActivity(), R.string.screen_name_profile_activity);
        return this.h;
    }

    public void onDestroy() {
        t();
        super.onDestroy();
    }

    public void onPause() {
        this.d.unregisterReceiver(this.m);
        this.m = null;
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("fr.smoney.android.izly.notifications.NOTIFICATION_SESSION_STATE_CHANGE_INTENT_URI");
        if (this.m == null) {
            this.m = new a();
        }
        this.d.registerReceiver(this.m, intentFilter);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable("savedStateContactDetailsData", this.g);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
