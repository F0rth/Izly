package defpackage;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import fr.smoney.android.izly.R;

public final class ic extends if {
    private static final ie a = ie.ProCategorySelectionType;
    private String b;
    private int c;
    private ListView d;

    public static ic a(String str, if$a if_a, int i) {
        ic icVar = new ic();
        Bundle a = icVar.a(if_a, a);
        a.putString("Argument.DialogTitle", str);
        icVar.setArguments(a);
        icVar.c = i;
        return icVar;
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.b = getArguments().getString("Argument.DialogTitle");
    }

    public final Dialog onCreateDialog(Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.fragment_select_list_item_dialog, null);
        this.d = (ListView) inflate.findViewById(R.id.lv_category_selection_dialog);
        this.d.setAdapter(new ic$a(this, getActivity()));
        this.d.setOnItemClickListener(new ic$1(this));
        return new Builder(getActivity()).setTitle(this.b).setView(inflate).create();
    }

    public final void onStart() {
        super.onStart();
    }
}
