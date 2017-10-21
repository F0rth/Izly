package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import defpackage.hw;
import defpackage.ie;
import defpackage.is;
import defpackage.iw;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.UserData;
import fr.smoney.android.izly.data.model.UserSubscribingValues;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

public class SubscribeConfirmActivity extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a {
    private UserSubscribingValues b;
    private Button c;
    private ImageView d;

    private void a(UserData userData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (userData == null) {
            a(hw.a(this, this));
        } else {
            SmoneyApplication.c.b(userData.a);
            startActivity(is.a(this, SubscribeResultActivity.class));
        }
    }

    private void a(UserSubscribingValues userSubscribingValues) {
        int keyAt;
        SmoneyRequestManager j = j();
        int size = j.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) j.b.valueAt(i);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 41 && intent.getParcelableExtra("fr.smoney.android.izly.extras.userSubscribingValues").equals(userSubscribingValues)) {
                keyAt = j.b.keyAt(i);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 41);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.userSubscribingValues", userSubscribingValues);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.y = null;
        j.f.z = null;
        super.a(keyAt, 41, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 41) {
                    a(this.b);
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
            if (i2 == 41) {
                a((UserData) bundle.getParcelable("fr.smoney.android.izly.extras.userData"), serverError);
            }
        }
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 41) {
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
        if (i == 41) {
            a(i2.y, i2.z);
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

    public void onClick(View view) {
        if (this.c == view) {
            a(this.b);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.subscribe_confirm);
        getSupportActionBar().setHomeButtonEnabled(false);
        this.c = (Button) findViewById(R.id.b_confirm_subscribe);
        this.c.setOnClickListener(this);
        this.d = (ImageView) findViewById(R.id.iv_user_photo);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.b = (UserSubscribingValues) extras.getParcelable("fr.smoney.android.izly.extras.userSubscribingValues");
        }
        if (this.b != null) {
            if (this.b.a == 0) {
                ((TextView) findViewById(R.id.tv_birth_date_label)).setText(getString(R.string.subscribe_confirm_label_birth_date_mister));
            } else if (this.b.a == 1) {
                ((TextView) findViewById(R.id.tv_birth_date_label)).setText(getString(R.string.subscribe_confirm_label_birth_date_miss));
            }
            if (!(this.b.b == null || this.b.c == null)) {
                StringBuilder stringBuilder = new StringBuilder(this.b.b);
                stringBuilder.append(" ");
                stringBuilder.append(this.b.c);
                ((TextView) findViewById(R.id.tv_names)).setText(stringBuilder.toString());
            }
            if (this.b.d != null) {
                ((TextView) findViewById(R.id.tv_nickname)).setText(this.b.d);
            } else {
                ((TextView) findViewById(R.id.tv_nickname)).setText(getString(R.string.subscribe_confirm_nickname_not_specified));
            }
            if (this.b.e != null) {
                ((TextView) findViewById(R.id.tv_birth_date)).setText(this.b.e);
            }
            if (this.b.g != null) {
                ((TextView) findViewById(R.id.tv_phone_number)).setText(this.b.g);
            }
            if (this.b.h != null) {
                ((TextView) findViewById(R.id.tv_mail_address)).setText(this.b.h);
            }
            if (this.b.i != null) {
                ((TextView) findViewById(R.id.tv_subscribe_confirm_secret_question)).setText(this.b.i);
            }
            if (this.b.j != null) {
                ((TextView) findViewById(R.id.tv_subscribe_confirm_secret_answer)).setText(this.b.j);
            }
        }
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z && this.b.m != null) {
            this.d.setImageBitmap(iw.a(this.b.m));
        }
    }
}
