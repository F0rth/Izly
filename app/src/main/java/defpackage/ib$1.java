package defpackage;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

final class ib$1 implements OnClickListener {
    final /* synthetic */ ib a;

    ib$1(ib ibVar) {
        this.a = ibVar;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("Data.SelectItem", i);
        this.a.a(bundle);
    }
}
