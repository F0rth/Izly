package defpackage;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import fr.smoney.android.izly.data.model.NearPro;
import fr.smoney.android.izly.ui.P2PPayActivity;
import fr.smoney.android.izly.ui.PreAuthorizePaymentActivity;

final class hj$2 implements OnClickListener {
    final /* synthetic */ hj a;

    hj$2(hj hjVar) {
        this.a = hjVar;
    }

    public final void onClick(View view) {
        int positionForView = this.a.f.getPositionForView(view) - 1;
        if (positionForView != -1) {
            NearPro nearPro = (NearPro) this.a.k.getItem(positionForView);
            Intent a;
            if (nearPro.o != null) {
                a = is.a(this.a.getActivity(), PreAuthorizePaymentActivity.class);
                a.putExtra("INTENT_EXTRA_PRE_AUTHORIZATION", nearPro.o);
                this.a.startActivity(a);
                return;
            }
            a = is.a(this.a.getActivity(), P2PPayActivity.class);
            a.putExtra("defaultRecipientId", nearPro.b);
            a.putExtra("nearProData", nearPro);
            this.a.startActivity(a);
        }
    }
}
