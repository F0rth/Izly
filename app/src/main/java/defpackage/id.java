package defpackage;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;

public final class id extends if {
    private String a;
    private CharSequence[] b;
    private int c;

    public static id a(String str, CharSequence[] charSequenceArr, int i, if$a if_a, ie ieVar) {
        id idVar = new id();
        Bundle a = idVar.a(if_a, ieVar);
        a.putString("Argument.DialogTitle", str);
        a.putCharSequenceArray("Argument.DialogItems", charSequenceArr);
        a.putInt("Argument.DialogCheckItem", i);
        idVar.setArguments(a);
        return idVar;
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        this.a = arguments.getString("Argument.DialogTitle");
        this.b = arguments.getCharSequenceArray("Argument.DialogItems");
        this.c = arguments.getInt("Argument.DialogCheckItem");
    }

    public final Dialog onCreateDialog(Bundle bundle) {
        return new Builder(getActivity()).setTitle(this.a).setSingleChoiceItems(this.b, this.c, new id$1(this)).create();
    }
}
