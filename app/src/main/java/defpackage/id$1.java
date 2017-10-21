package defpackage;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

final class id$1 implements OnClickListener {
    final /* synthetic */ id a;

    id$1(id idVar) {
        this.a = idVar;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("Data.SelectItem", i);
        this.a.dismiss();
        this.a.a(bundle);
    }
}
