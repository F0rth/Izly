package defpackage;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import fr.smoney.android.izly.R;

public final class hz extends if {
    private static final ie a = ie.InputPasswordType;
    private EditText b;
    private String c;
    private String d;
    private String h;

    public static hz a(Context context, if$a if_a) {
        hz hzVar = new hz();
        Bundle a = hzVar.a(if_a, a);
        a.putString("Argument.DialogTitle", context.getString(R.string.dialog_session_error_title));
        a.putString("Argument.DialogMsg", context.getString(R.string.dialog_session_error_message));
        a.putString("Argument.DialogBtnText", context.getString(R.string.dialog_session_error_btn));
        hzVar.setArguments(a);
        return hzVar;
    }

    static /* synthetic */ void a(hz hzVar) {
        String obj = hzVar.b.getText().toString();
        if (TextUtils.isEmpty(obj) || obj.length() != 6) {
            FragmentActivity activity = hzVar.getActivity();
            Toast.makeText(activity, activity.getString(R.string.dialog_error_no_password_message, new Object[]{"6"}), 0).show();
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("Data.Password", obj);
        je.a(hzVar.getActivity(), hzVar.b);
        hzVar.a(bundle);
        hzVar.dismiss();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        this.c = arguments.getString("Argument.DialogTitle");
        this.d = arguments.getString("Argument.DialogMsg");
        this.h = arguments.getString("Argument.DialogBtnText");
    }

    public final Dialog onCreateDialog(Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.dialog_password_entry, null);
        ((TextView) inflate.findViewById(R.id.tv_password)).setText(this.d);
        this.b = (EditText) inflate.findViewById(R.id.et_password);
        this.b.setOnFocusChangeListener(new hz$2(this));
        this.b.setOnEditorActionListener(new hz$3(this));
        return new Builder(getActivity()).setTitle(this.c).setView(inflate).setPositiveButton(this.h, null).create();
    }

    public final void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(-1).setOnClickListener(new hz$1(this));
        this.b.requestFocus();
    }
}
