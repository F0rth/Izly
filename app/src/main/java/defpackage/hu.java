package defpackage;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import java.io.Serializable;

public class hu extends if {
    private static final ie a = ie.AlertType;
    private String b;
    private String c;
    private String d;

    public static hu a(String str, String str2, String str3) {
        hu huVar = new hu();
        Serializable serializable = a;
        Bundle bundle = new Bundle();
        bundle.putSerializable("Argument.DialogFragmentType", serializable);
        bundle.putBoolean("Argument.DialogCallbackDefined", false);
        bundle.putString("Argument.DialogTitle", str);
        bundle.putString("Argument.DialogMsg", str2);
        bundle.putString("Argument.DialogBtnText", str3);
        huVar.setArguments(bundle);
        return huVar;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        this.b = arguments.getString("Argument.DialogTitle");
        this.c = arguments.getString("Argument.DialogMsg");
        this.d = arguments.getString("Argument.DialogBtnText");
    }

    public Dialog onCreateDialog(Bundle bundle) {
        return new Builder(getActivity()).setTitle(this.b).setMessage(this.c).setNeutralButton(this.d, new hu$1(this)).create();
    }
}
