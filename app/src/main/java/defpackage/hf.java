package defpackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.GetCrousContactListData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.HelpActivity;

public class hf extends aa implements SmoneyRequestManager$a {
    private WebView e;
    private ProgressBar f;
    private cl g;

    private void a(GetCrousContactListData getCrousContactListData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getCrousContactListData == null) {
            a(hw.a(this.d, this));
        } else {
            HelpActivity helpActivity = (HelpActivity) getActivity();
            ((aa) helpActivity.mViewPager.getAdapter().instantiateItem(helpActivity.mViewPager, helpActivity.b)).a(getCrousContactListData, serverError);
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            switch (i2) {
                case 268:
                    a((GetCrousContactListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetCrousContactListData"), serverError);
                    return;
                default:
                    return;
            }
        }
    }

    public final void b_(int i) {
        switch (i) {
            case 268:
                a(this.g.ca, this.g.cb);
                return;
            default:
                return;
        }
    }

    public void onActivityCreated(Bundle bundle) {
        int keyAt;
        super.onActivityCreated(bundle);
        this.g = i();
        SmoneyRequestManager j = j();
        String str = this.g.b.a;
        String str2 = this.g.b.c;
        int size = j.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) j.b.valueAt(i);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 268 && intent.getStringExtra("fr.smoney.android.izly.extras.getCrousContactListUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.getCrousContactListSessionId").equals(str2)) {
                keyAt = j.b.keyAt(i);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 268);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.getCrousContactListUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.getCrousContactListSessionId", str2);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.cb = null;
        super.a(keyAt, 268, true);
        this.f = (ProgressBar) getView().findViewById(R.id.pb_about_content_faq);
        this.e = (WebView) getView().findViewById(R.id.wv_about_content_faq);
        this.e.getSettings().setJavaScriptEnabled(true);
        this.e.setWebViewClient(new hf$a());
        this.e.setDrawingCacheEnabled(true);
        this.e.loadUrl(af.a);
        this.e.setBackgroundColor(0);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.faq, viewGroup, false);
    }

    public void onResume() {
        super.onResume();
    }
}
