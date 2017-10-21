package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.gq;
import defpackage.hw;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.CounterData;
import fr.smoney.android.izly.data.model.LoginLightData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

import java.util.List;

public class CounterDetailActivity extends SmoneyABSActivity implements SmoneyRequestManager$a {
    private long b;
    private String c;
    private gq d;
    @Bind({2131755299})
    ListView detailCounterList;
    @Bind({2131755470})
    TextView headerCounterDetail;
    @Bind({2131755471})
    View placeHolderCounter;

    private void a(long j) {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j2 = j();
        String str = i.b.a;
        String str2 = i.b.c;
        int size = j2.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j2.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 262 && intent.getStringExtra("fr.smoney.android.izly.extras.GetMyCounterDetailUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.GetMyCounterDetailSessionId").equals(str2)) {
                keyAt = j2.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j2.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 262);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j2.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.GetMyCounterDetailUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.GetMyCounterDetailSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.GetMyCounterDetailId", j);
        j2.c.startService(intent2);
        j2.b.append(keyAt, intent2);
        j2.f.bD = null;
        j2.f.bE = null;
        super.a(keyAt, 262, true);
    }

    private void a(CounterData counterData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (counterData == null) {
            a(hw.a(this, this));
        } else {
            List list = counterData.a;
            if (list == null || list.size() <= 0) {
                this.placeHolderCounter.setVisibility(0);
                this.detailCounterList.setVisibility(8);
                return;
            }
            this.d = new gq(this, list);
            this.detailCounterList.setAdapter(this.d);
            this.placeHolderCounter.setVisibility(8);
            this.detailCounterList.setVisibility(0);
        }
    }

    private void a(LoginLightData loginLightData, ServerError serverError) {
        if (serverError != null) {
            a(serverError, true);
        } else if (loginLightData == null) {
            a(hw.a(this, this));
        } else {
            a(this.b);
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        super.a(i, i2, i3, bundle);
        cl i4 = i();
        ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
        switch (i2) {
            case 232:
                a(i4.bJ, i4.bK);
                return;
            case 262:
                a((CounterData) bundle.getParcelable("fr.smoney.android.izly.extras.GetMyCounterDetail"), serverError);
                return;
            default:
                return;
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        switch (i) {
            case 232:
                a(i2.bJ, i2.bK);
                return;
            case 262:
                a(i2.bD, i2.bE);
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.counter_detail_activity);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            this.b = getIntent().getLongExtra("extra.counter.id", -1);
            this.c = getIntent().getStringExtra("extra.counter.name");
            if (this.b == -1 || this.c == null) {
                finish();
            }
            this.headerCounterDetail.setText(this.c);
            a(this.b);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }
}
