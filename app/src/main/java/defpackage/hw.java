package defpackage;

import android.content.Context;
import android.os.Bundle;
import fr.smoney.android.izly.R;

public final class hw extends ht {
    private static final ie h = ie.ConnexionErrorType;

    public static hw a(Context context, if$a if_a) {
        hw hwVar = new hw();
        Bundle a = hwVar.a(if_a, h);
        a.putString("Argument.DialogTitle", context.getString(R.string.dialog_error_connexion_error_title));
        a.putString("Argument.DialogMsg", context.getString(R.string.dialog_error_connexion_error_message));
        a.putString("Argument.DialogBtnPositiveText", context.getString(R.string.dialog_button_retry));
        a.putString("Argument.DialogBtnNegativeText", context.getString(17039360));
        hwVar.setArguments(a);
        return hwVar;
    }

    public static hw a(Context context, if$a if_a, ie ieVar) {
        hw hwVar = new hw();
        Bundle a = hwVar.a(if_a, ieVar);
        a.putString("Argument.DialogTitle", context.getString(R.string.dialog_error_connexion_error_title));
        a.putString("Argument.DialogMsg", context.getString(R.string.dialog_error_connexion_error_message));
        a.putString("Argument.DialogBtnPositiveText", context.getString(R.string.dialog_button_retry));
        a.putString("Argument.DialogBtnNegativeText", context.getString(17039360));
        hwVar.setArguments(a);
        return hwVar;
    }
}
