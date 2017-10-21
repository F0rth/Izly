package defpackage;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;

public class ht extends if {
    protected String a;
    protected String b;
    protected String c;
    protected String d;

    public static ht a(String str, String str2, String str3, String str4, if$a if_a, ie ieVar) {
        ht htVar = new ht();
        Bundle a = htVar.a(if_a, ieVar);
        a.putString("Argument.DialogTitle", str);
        a.putString("Argument.DialogMsg", str2);
        a.putString("Argument.DialogBtnPositiveText", str3);
        a.putString("Argument.DialogBtnNegativeText", str4);
        htVar.setArguments(a);
        return htVar;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        setCancelable(false);
        this.a = arguments.getString("Argument.DialogTitle");
        this.b = arguments.getString("Argument.DialogMsg");
        this.c = arguments.getString("Argument.DialogBtnPositiveText");
        this.d = arguments.getString("Argument.DialogBtnNegativeText");
    }

    public Dialog onCreateDialog(Bundle bundle) {
        return new Builder(getActivity()).setTitle(this.a).setMessage(this.b).setPositiveButton(this.c, new ht$2(this)).setNegativeButton(this.d, new ht$1(this)).create();
    }
}
