package defpackage;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public abstract class if extends DialogFragment {
    public ie e;
    public if$a f;
    boolean g;

    public final Bundle a(if$a if_a, ie ieVar) {
        Bundle bundle = new Bundle();
        this.f = if_a;
        bundle.putSerializable("Argument.DialogFragmentType", ieVar);
        bundle.putBoolean("Argument.DialogCallbackDefined", true);
        return bundle;
    }

    protected final void a(Bundle bundle) {
        if (this.g && this.f != null) {
            this.f.a(this.e, bundle);
        }
    }

    public void onCancel(DialogInterface dialogInterface) {
        super.onCancel(dialogInterface);
        if (this.g && this.f != null) {
            this.f.d(this.e);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        this.e = (ie) arguments.getSerializable("Argument.DialogFragmentType");
        this.g = arguments.getBoolean("Argument.DialogCallbackDefined");
    }

    public void onDetach() {
        this.f = null;
        super.onDetach();
    }
}
