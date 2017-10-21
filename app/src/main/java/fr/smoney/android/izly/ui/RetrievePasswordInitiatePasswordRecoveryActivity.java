package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import defpackage.ie;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

public class RetrievePasswordInitiatePasswordRecoveryActivity extends SmoneyABSActivity implements SmoneyRequestManager$a {
    private int b = -1;
    private String c;

    private void b(ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else {
            setContentView((int) R.layout.retrieve_password_initiate_password_recovery);
        }
    }

    private void k() {
        int keyAt;
        SmoneyRequestManager j = j();
        String str = this.c;
        boolean z = this.b == 1;
        int size = j.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) j.b.valueAt(i);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 191 && intent.getStringExtra("fr.smoney.android.izly.extras.initiatePasswordRecoveryUserId").equals(str) && intent.getBooleanExtra("fr.smoney.android.izly.extras.initiatePasswordRecoveryUnlockAccount", false) != z) {
                keyAt = j.b.keyAt(i);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 191);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.initiatePasswordRecoveryUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.initiatePasswordRecoveryUnlockAccount", z);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.aS = null;
        j.f.aT = null;
        super.a(keyAt, 191, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 191) {
                    k();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 191) {
                b(serverError);
            }
        }
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 191) {
                    finish();
                    return;
                } else {
                    super.b(ieVar);
                    return;
                }
            default:
                super.b(ieVar);
                return;
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 191) {
            b(i2.aT);
        }
    }

    public final void c(ie ieVar) {
        switch (ieVar) {
            case ErrorType:
                finish();
                return;
            default:
                super.c(ieVar);
                return;
        }
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType && h() == 191) {
            g();
            finish();
            return;
        }
        super.d(ieVar);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.b = extras.getInt("fr.smoney.android.izly.ui.intentExtraMode");
            this.c = extras.getString("fr.smoney.android.izly.ui.intentUserId");
        }
        k();
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        switch (i) {
            case 4:
                finish();
                return true;
            default:
                return super.onKeyUp(i, keyEvent);
        }
    }
}
