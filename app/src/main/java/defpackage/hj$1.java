package defpackage;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

final class hj$1 implements OnCancelListener {
    final /* synthetic */ hj a;

    hj$1(hj hjVar) {
        this.a = hjVar;
    }

    public final void onCancel(DialogInterface dialogInterface) {
        this.a.getActivity().finish();
    }
}
