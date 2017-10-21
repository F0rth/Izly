package defpackage;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;

public final class ib extends if {
    private String a;
    private CharSequence[] b;

    public static ib a(String str, CharSequence[] charSequenceArr, if$a if_a, ie ieVar) {
        ib ibVar = new ib();
        Bundle a = ibVar.a(if_a, ieVar);
        a.putString("Argument.DialogTitle", str);
        a.putCharSequenceArray("Argument.DialogItems", charSequenceArr);
        ibVar.setArguments(a);
        return ibVar;
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        this.a = arguments.getString("Argument.DialogTitle");
        this.b = arguments.getCharSequenceArray("Argument.DialogItems");
    }

    public final Dialog onCreateDialog(Bundle bundle) {
        return new Builder(getActivity()).setTitle(this.a).setItems(this.b, new ib$1(this)).create();
    }
}
