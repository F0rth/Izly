package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.IconTextView;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.gz;
import defpackage.hu;
import defpackage.hz;
import defpackage.ie;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.Support;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

public class DetailSupportActivity extends SmoneyABSActivity implements SmoneyRequestManager$a {
    @Bind({2131755506})
    IconTextView actionButton;
    private gz b;
    private Support c;
    @Bind({2131755498})
    DetailTwoText crous;
    @Bind({2131755500})
    DetailTwoText expDate;
    @Bind({2131755505})
    Spinner motifSpinner;
    @Bind({2131755504})
    View motifView;
    @Bind({2131755501})
    DetailTwoText oppositeDate;
    @Bind({2131755502})
    DetailTwoText oppositeMotif;
    @Bind({2131755503})
    TextView riseInstruction;
    @Bind({2131755499})
    DetailTwoText studentId;
    @Bind({2131755497})
    DetailTwoText type;

    private void a(String str) {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str2 = i.b.a;
        String str3 = i.b.c;
        long j2 = this.c.a;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 253 && intent.getStringExtra("fr.smoney.android.izly.extras.RiseOppositionUserId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.RiseOppositionSessionId").equals(str3)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 253);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.RiseOppositionUserId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.RiseOppositionSessionId", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.RiseOppositionSupportId", j2);
        intent2.putExtra("fr.smoney.android.izly.extras.RiseOppositionPassword", str);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.bH = null;
        super.a(keyAt, 253, true);
    }

    private void b(ServerError serverError) {
        if (serverError == null) {
            setResult(-1);
            finish();
        } else if (serverError.b == 120) {
            a(hu.a(serverError.d, serverError.c, getString(17039370)));
        } else {
            a(serverError);
        }
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case InputPasswordType:
                String string = bundle.getString("Data.Password");
                if (this.c.b() == null) {
                    int keyAt;
                    cl i = i();
                    SmoneyRequestManager j = j();
                    String str = i.b.a;
                    String str2 = i.b.c;
                    long j2 = this.c.a;
                    int i2 = ((ck) this.motifSpinner.getSelectedItem()).d;
                    int size = j.b.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        Intent intent = (Intent) j.b.valueAt(i3);
                        if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 252 && intent.getStringExtra("fr.smoney.android.izly.extras.PutOppositionUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.PutOppositionSessionId").equals(str2)) {
                            keyAt = j.b.keyAt(i3);
                            super.a(keyAt, 252, true);
                            return;
                        }
                    }
                    keyAt = SmoneyRequestManager.a.nextInt(1000000);
                    Intent intent2 = new Intent(j.c, SmoneyService.class);
                    intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 252);
                    intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
                    intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
                    intent2.putExtra("fr.smoney.android.izly.extras.PutOppositionUserId", str);
                    intent2.putExtra("fr.smoney.android.izly.extras.PutOppositionSessionId", str2);
                    intent2.putExtra("fr.smoney.android.izly.extras.PutOppositionSupportId", j2);
                    intent2.putExtra("fr.smoney.android.izly.extras.PutOppositionMotif", i2);
                    intent2.putExtra("fr.smoney.android.izly.extras.PutOppositionPassword", string);
                    j.c.startService(intent2);
                    j.b.append(keyAt, intent2);
                    j.f.bI = null;
                    super.a(keyAt, 252, true);
                    return;
                }
                a(string);
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        super.a(i, i2, i3, bundle);
        cl i4 = i();
        switch (i2) {
            case 252:
                b(i4.bI);
                return;
            case 253:
                b(i4.bH);
                return;
            default:
                return;
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        switch (i) {
            case 252:
                b(i2.bI);
                return;
            case 253:
                b(i2.bH);
                return;
            default:
                return;
        }
    }

    public final void c(ie ieVar) {
        super.c(ieVar);
        int[] iArr = AnonymousClass2.a;
        ieVar.ordinal();
        super.d(ieVar);
    }

    public final void d(ie ieVar) {
        int[] iArr = AnonymousClass2.a;
        ieVar.ordinal();
        super.d(ieVar);
    }

    public void onBackPressed() {
        super.onBackPressed();
        setResult(-1);
        finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.detail_supports_activity);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        if (!getIntent().hasExtra("support_extra")) {
            finish();
        }
        this.c = (Support) getIntent().getParcelableExtra("support_extra");
        getIntent().removeExtra("support_extra");
        this.type.setRightText(this.c.b.d);
        Log.d("SUPPORT", this.c.toString());
        if (this.c.c == null || this.c.c.length() <= 0) {
            this.crous.setVisibility(8);
        } else {
            this.crous.setRightText(this.c.c);
        }
        if (this.c.b == co.a) {
            this.studentId.setRightText(this.c.d);
        } else {
            this.studentId.setVisibility(8);
        }
        if (this.c.a() != null) {
            this.expDate.setRightText(this.c.a());
        } else {
            this.expDate.setVisibility(8);
        }
        if (this.c.c()) {
            this.oppositeDate.setRightText(this.c.b());
            if (this.c.e) {
                this.oppositeMotif.setRightText((int) R.string.permanente_opposition);
                this.actionButton.setVisibility(8);
                this.riseInstruction.setVisibility(0);
            } else {
                int i;
                this.actionButton.setText(R.string.rise_oppostion);
                DetailTwoText detailTwoText = this.oppositeMotif;
                switch (this.c.f) {
                    case 2:
                        i = R.string.stolen_support;
                        break;
                    case 3:
                        i = R.string.disabled;
                        break;
                    case 5:
                        i = R.string.lost_support;
                        break;
                    default:
                        i = R.string.na;
                        break;
                }
                detailTwoText.setRightText(i);
            }
        } else {
            this.b = new gz(this, R.layout.izly_spinner, ck.values());
            this.motifSpinner.setAdapter(this.b);
            this.oppositeMotif.setVisibility(8);
            this.oppositeDate.setVisibility(8);
            this.motifView.setVisibility(0);
            this.actionButton.setText(R.string.put_opposition);
        }
        this.actionButton.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ DetailSupportActivity a;

            {
                this.a = r1;
            }

            public final void onClick(View view) {
                this.a.a(hz.a(this.a, this.a));
            }
        });
        jb.a(getApplicationContext(), R.string.screen_name_supports_details_activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }
}
