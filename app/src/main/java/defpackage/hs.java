package defpackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fr.smoney.android.izly.R;

public final class hs extends Fragment {
    private int a = 0;

    public static hs a(int i) {
        hs hsVar = new hs();
        hsVar.a = i;
        return hsVar;
    }

    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        switch (this.a) {
            case 0:
                return layoutInflater.inflate(R.layout.welcome_page_1, viewGroup, false);
            case 1:
                return layoutInflater.inflate(R.layout.welcome_page_2, viewGroup, false);
            case 2:
                return layoutInflater.inflate(R.layout.welcome_page_3, viewGroup, false);
            default:
                return null;
        }
    }
}
