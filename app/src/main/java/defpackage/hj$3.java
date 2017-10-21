package defpackage;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import fr.smoney.android.izly.data.model.GetNearProListData;
import fr.smoney.android.izly.ui.NearProMapActivity;
import java.util.ArrayList;

final class hj$3 implements OnClickListener {
    final /* synthetic */ hj a;

    hj$3(hj hjVar) {
        this.a = hjVar;
    }

    public final void onClick(View view) {
        int positionForView = this.a.f.getPositionForView(view) - 1;
        if (positionForView != -1) {
            Parcelable getNearProListData = new GetNearProListData();
            getNearProListData.a = this.a.h.a;
            getNearProListData.b = new ArrayList(this.a.h.b.subList(positionForView, Math.min(this.a.h.b.size(), positionForView + 1)));
            Intent a = is.a(this.a.getActivity(), NearProMapActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.nearProMapData", getNearProListData);
            this.a.startActivityForResult(a, 2);
        }
    }
}
