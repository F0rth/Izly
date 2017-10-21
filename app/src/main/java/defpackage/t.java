package defpackage;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import scanpay.it.ScanPayActivity;

public final class t implements OnClickListener {
    private /* synthetic */ ScanPayActivity a;

    public t(ScanPayActivity scanPayActivity) {
        this.a = scanPayActivity;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.a.finish();
    }
}
