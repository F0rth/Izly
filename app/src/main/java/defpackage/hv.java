package defpackage;

import android.os.Bundle;

public final class hv extends hu {
    public static hv a(String str, String str2, String str3, if$a if_a, ie ieVar) {
        hv hvVar = new hv();
        Bundle a = hvVar.a(if_a, ieVar);
        a.putString("Argument.DialogTitle", str);
        a.putString("Argument.DialogMsg", str2);
        a.putString("Argument.DialogBtnText", str3);
        hvVar.setArguments(a);
        return hvVar;
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCancelable(false);
    }
}
