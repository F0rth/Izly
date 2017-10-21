package defpackage;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import fr.smoney.android.izly.data.model.NewsFeedItem;
import fr.smoney.android.izly.data.model.NewsFeedItem.a;
import fr.smoney.android.izly.ui.ContactDetailsActivity;

final class hi$11 implements OnClickListener {
    final /* synthetic */ hi a;

    hi$11(hi hiVar) {
        this.a = hiVar;
    }

    public final void onClick(View view) {
        int positionForView = this.a.k.getPositionForView(view) - this.a.k.getHeaderViewsCount();
        if (positionForView != -1) {
            NewsFeedItem a = this.a.C.a(positionForView);
            if (a.e != null && a.d != a.PaymentDistributor) {
                Intent a2 = is.a(this.a.d, ContactDetailsActivity.class);
                a2.putExtra("fr.smoney.android.izly.extras.contactId", a.e);
                this.a.startActivity(a2);
            }
        }
    }
}
