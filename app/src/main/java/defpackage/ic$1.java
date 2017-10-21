package defpackage;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

final class ic$1 implements OnItemClickListener {
    final /* synthetic */ ic a;

    ic$1(ic icVar) {
        this.a = icVar;
    }

    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Bundle bundle = new Bundle();
        bundle.putInt("Data.SelectedCategory", cn.b(i).p);
        bundle.putInt("Data.SelectedCategory.Position", i);
        this.a.a(bundle);
        this.a.dismiss();
    }
}
