package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.gy;
import defpackage.hw;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.GetMySupportListData;
import fr.smoney.android.izly.data.model.LoginLightData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

import java.util.List;

public class ListSupportActivity extends SmoneyABSActivity implements SmoneyRequestManager$a {
    private gy b;
    @Bind({2131755299})
    ListView mSupportsList;
    @Bind({2131755613})
    View noSupportsView;

    private void a(GetMySupportListData getMySupportListData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getMySupportListData == null) {
            a(hw.a(this, this));
        } else {
            List list = getMySupportListData.a;
            if (list != null && list.size() > 0) {
                this.mSupportsList.setVisibility(0);
                this.b = new gy(this, list);
                this.mSupportsList.setAdapter(this.b);
                this.mSupportsList.setOnItemClickListener(new OnItemClickListener(this) {
                    final /* synthetic */ ListSupportActivity a;

                    {
                        this.a = r1;
                    }

                    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                        Parcelable a = this.a.b.a(i);
                        Intent intent = new Intent(this.a, DetailSupportActivity.class);
                        intent.putExtra("support_extra", a);
                        this.a.startActivityForResult(intent, 10);
                        this.a.b.notifyDataSetChanged();
                        this.a.mSupportsList.invalidate();
                    }
                });
                this.noSupportsView.setVisibility(8);
            }
        }
    }

    private void a(LoginLightData loginLightData, ServerError serverError) {
        if (serverError != null) {
            a(serverError, true);
        } else if (loginLightData == null) {
            a(hw.a(this, this));
        } else {
            k();
        }
    }

    private void k() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 251 && intent.getStringExtra("fr.smoney.android.izly.extras.GetMySupportListUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.GetMySupportListSessionId").equals(str2)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 251);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.GetMySupportListUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.GetMySupportListSessionId", str2);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.bF = null;
        j.f.bG = null;
        super.a(keyAt, 251, true);
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        super.a(i, i2, i3, bundle);
        cl i4 = i();
        ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
        switch (i2) {
            case 232:
                a(i4.bJ, i4.bK);
                return;
            case 251:
                a((GetMySupportListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetMySupportList"), serverError);
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
            case 251:
                a(i2.bF, i2.bG);
                return;
            default:
                return;
        }
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i == 10 && i2 == -1) {
            k();
        }
        super.onActivityResult(i, i2, intent);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.list_supports_activity);
        ButterKnife.bind(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        k();
        jb.a(getApplicationContext(), R.string.screen_name_supports_activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }
}
