package defpackage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.CrousData;
import fr.smoney.android.izly.data.model.GetCrousContactListData;
import fr.smoney.android.izly.data.model.LoginData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import java.util.ArrayList;
import java.util.List;

public class hd extends aa implements TextWatcher, OnClickListener, SmoneyRequestManager$a {
    private TextView e;
    private TextView f;
    private TextView g;
    private TextView h;
    private EditText i;
    private EditText j;
    private EditText k;
    private Spinner l;
    private ArrayAdapter m;
    private Button n;
    private List<CrousData> o;
    private cl p;
    private LoginData q;

    private void a(GetCrousContactListData getCrousContactListData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getCrousContactListData == null || getCrousContactListData.d.isEmpty()) {
            a(hw.a(this.d, this));
        } else {
            Object<CrousData> obj = getCrousContactListData.d;
            this.o.clear();
            this.o.addAll(obj);
            this.m.notifyDataSetChanged();
            for (CrousData crousData : obj) {
                if (crousData.c) {
                    this.l.setSelection(this.m.getPosition(crousData));
                    break;
                }
            }
            if (getCrousContactListData.b != null) {
                this.i.setText(getCrousContactListData.b);
            }
            if (getCrousContactListData.c != null) {
                this.j.setText(getCrousContactListData.c);
            }
        }
    }

    private void b(ServerError serverError) {
        this.n.setEnabled(true);
        if (serverError != null) {
            a(serverError);
        } else {
            a(hv.a(getString(R.string.contact_us_confirmation_title), getString(R.string.contact_us_confirmation_message), getString(17039370), this, ie.ContactCrousConfirmationType));
        }
    }

    public final void a(Parcelable parcelable, ServerError serverError) {
        a((GetCrousContactListData) parcelable, serverError);
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            switch (i2) {
                case 268:
                    a((GetCrousContactListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetCrousContactListData"), serverError);
                    return;
                case 269:
                    b(serverError);
                    return;
                default:
                    return;
            }
        }
    }

    public void afterTextChanged(Editable editable) {
        Log.e("TAG", "afterTextChanged");
        if (jh.d(this.i.getText().toString()) || this.l.getSelectedItem() == null || jh.d(((CrousData) this.l.getSelectedItem()).a) || jh.d(this.k.getText().toString())) {
            this.n.setEnabled(false);
        } else {
            this.n.setEnabled(true);
        }
    }

    public final void b_(int i) {
        switch (i) {
            case 268:
                a(this.p.ca, this.p.cb);
                return;
            case 269:
                b(this.p.cc);
                return;
            default:
                return;
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public final void c(ie ieVar) {
        switch (hd$1.a[ieVar.ordinal()]) {
            case 1:
                getActivity().finish();
                return;
            default:
                super.c(ieVar);
                return;
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.p = i();
        if (this.p != null) {
            this.q = this.p.b;
        }
    }

    public void onClick(View view) {
        CharSequence obj = this.i.getText().toString();
        String obj2 = this.j.getText().toString();
        String str = ((CrousData) this.l.getSelectedItem()).a;
        String obj3 = this.k.getText().toString();
        if (jh.b(obj)) {
            int keyAt;
            this.n.setEnabled(false);
            SmoneyRequestManager j = j();
            String str2 = this.q.a;
            String str3 = this.q.c;
            int size = j.b.size();
            for (int i = 0; i < size; i++) {
                Intent intent = (Intent) j.b.valueAt(i);
                if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 269 && intent.getStringExtra("fr.smoney.android.izly.extras.contactCrousSupportUserId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.contactCrousSupportSessionId").equals(str3) && intent.getStringExtra("fr.smoney.android.izly.extras.contactCrousSupportUserEmail").equals(obj) && intent.getStringExtra("fr.smoney.android.izly.extras.contactCrousSupportUserPhone").equals(obj2) && intent.getStringExtra("fr.smoney.android.izly.extras.contactCrousSupportCrousNumber").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.contactCrousSupportMessage").equals(obj3)) {
                    keyAt = j.b.keyAt(i);
                    break;
                }
            }
            keyAt = SmoneyRequestManager.a.nextInt(1000000);
            Intent intent2 = new Intent(j.c, SmoneyService.class);
            intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 269);
            intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
            intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
            intent2.putExtra("fr.smoney.android.izly.extras.contactCrousSupportUserId", str2);
            intent2.putExtra("fr.smoney.android.izly.extras.contactCrousSupportSessionId", str3);
            intent2.putExtra("fr.smoney.android.izly.extras.contactCrousSupportUserEmail", obj);
            intent2.putExtra("fr.smoney.android.izly.extras.contactCrousSupportUserPhone", obj2);
            intent2.putExtra("fr.smoney.android.izly.extras.contactCrousSupportCrousNumber", str);
            intent2.putExtra("fr.smoney.android.izly.extras.contactCrousSupportMessage", obj3);
            j.c.startService(intent2);
            j.b.append(keyAt, intent2);
            j.f.cc = null;
            super.a(keyAt, 269, false);
            return;
        }
        a(hu.a(getString(R.string.dialog_error_title), getString(R.string.invalid_mail), getString(R.string.button_ok)));
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.contact_us_fragment, viewGroup, false);
        this.e = (TextView) inflate.findViewById(R.id.contact_us_for_question);
        this.f = (TextView) inflate.findViewById(R.id.contact_us_personal_info);
        this.i = (EditText) inflate.findViewById(R.id.contact_us_mail_input);
        this.j = (EditText) inflate.findViewById(R.id.contact_us_mobile_number_input);
        this.k = (EditText) inflate.findViewById(R.id.contact_us_demand_input);
        this.g = (TextView) inflate.findViewById(R.id.contact_us_crous);
        this.h = (TextView) inflate.findViewById(R.id.contact_us_demand);
        this.l = (Spinner) inflate.findViewById(R.id.contact_us_crous_spinner);
        this.n = (Button) inflate.findViewById(R.id.contact_us_send_btn);
        this.n.setOnClickListener(this);
        this.e.setMovementMethod(LinkMovementMethod.getInstance());
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getActivity().getResources().getColor(R.color.izly_blue));
        this.f.setText(jh.a(this.f.getText(), 2, foregroundColorSpan));
        this.g.setText(jh.a(this.g.getText(), 2, foregroundColorSpan));
        this.h.setText(jh.a(this.h.getText(), 2, foregroundColorSpan));
        this.o = new ArrayList();
        this.m = new gu(getActivity(), this.o);
        this.m.setDropDownViewResource(R.layout.spinner_item_crous);
        this.l.setAdapter(this.m);
        this.i.addTextChangedListener(this);
        this.k.addTextChangedListener(this);
        return inflate;
    }

    public void onResume() {
        super.onResume();
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
