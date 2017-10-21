package defpackage;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

public final class ia extends if {
    private static final ie a = ie.ProgressType;
    private String b;
    private String c;

    public static ia a(String str, String str2, if$a if_a) {
        ia iaVar = new ia();
        Bundle a = iaVar.a(if_a, a);
        a.putString("Argument.DialogTitle", str);
        a.putString("Argument.DialogMsg", str2);
        iaVar.setArguments(a);
        return iaVar;
    }

    public static ia a(String str, String str2, if$a if_a, ie ieVar) {
        ia iaVar = new ia();
        Bundle a = iaVar.a(if_a, ieVar);
        a.putString("Argument.DialogTitle", str);
        a.putString("Argument.DialogMsg", str2);
        iaVar.setArguments(a);
        return iaVar;
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        this.b = arguments.getString("Argument.DialogTitle");
        this.c = arguments.getString("Argument.DialogMsg");
    }

    public final Dialog onCreateDialog(Bundle bundle) {
        Dialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(this.b);
        progressDialog.setMessage(this.c);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
}
