package defpackage;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import fr.smoney.android.izly.R;

public final class hy extends if {
    private static final ie a = ie.InputAmountType;
    private String b;
    private String c;
    private String d;
    private EditText h;

    public static hy a(String str, String str2, String str3, if$a if_a) {
        hy hyVar = new hy();
        Bundle a = hyVar.a(if_a, a);
        a.putString("Argument.DialogTitle", str);
        a.putString("Argument.DialogAmountHint", str2);
        a.putString("Argument.DialogAmountText", str3);
        hyVar.setArguments(a);
        return hyVar;
    }

    static /* synthetic */ void a(hy hyVar) {
        FragmentActivity activity = hyVar.getActivity();
        String obj = hyVar.h.getText().toString();
        if (TextUtils.isEmpty(obj)) {
            Toast.makeText(activity, activity.getString(R.string.dialog_error_no_amount_message), 0).show();
            return;
        }
        try {
            double a = iu.a(obj);
            Bundle bundle = new Bundle();
            bundle.putDouble("Data.Amount", a);
            je.a(hyVar.getActivity(), hyVar.h);
            hyVar.a(bundle);
            hyVar.dismiss();
        } catch (gd e) {
            Toast.makeText(activity, activity.getString(R.string.dialog_error_error_amount_message), 0).show();
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        this.b = arguments.getString("Argument.DialogTitle");
        this.c = arguments.getString("Argument.DialogAmountHint");
        this.d = arguments.getString("Argument.DialogAmountText");
    }

    public final Dialog onCreateDialog(Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.dialog_amount_entry, null);
        this.h = (EditText) inflate.findViewById(R.id.et_amount);
        this.h.setOnFocusChangeListener(new hy$2(this));
        this.h.setOnEditorActionListener(new hy$3(this));
        this.h.setKeyListener(new ja(true, 3, 2));
        if (this.c != null) {
            this.h.setHint(this.c);
        }
        if (this.d != null) {
            this.h.setText(this.d);
        }
        return new Builder(getActivity()).setTitle(this.b).setView(inflate).setPositiveButton(17039370, null).create();
    }

    public final void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(-1).setOnClickListener(new hy$1(this));
        this.h.requestFocus();
    }
}
