package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import defpackage.ht;
import defpackage.hv;
import defpackage.hw;
import defpackage.ie;
import defpackage.is;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyActivityWrapper;
import fr.smoney.android.izly.data.model.ClientUserStatus;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

public class CompleteAccountWrapperActivity extends SmoneyActivityWrapper implements SmoneyRequestManager$a {
    private int a;
    private Bundle b;
    private boolean c;
    private boolean d;

    private void a(int i, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (i == -1) {
            a(hw.a(this, this));
        } else if (this.d) {
            if (i == 1) {
                setResult(-1);
                finish();
                return;
            }
            d();
        } else if (i == 1) {
            f();
        } else if (i == 2) {
            a(hv.a(getString(R.string.dialog_complete_subscription_success_title), getString(R.string.dialog_complete_subscription_success_msg), getString(17039370), this, ie.CompleteSubscriptionSuccessType));
        } else {
            d();
        }
    }

    private void b(int i) {
        if (i == 0) {
            e();
        } else {
            f();
        }
    }

    private void d() {
        a(hv.a(getString(R.string.dialog_complete_subscription_error_title), getString(R.string.dialog_complete_subscription_error_msg), getString(17039370), this, ie.CompleteSubscriptionErrorType));
    }

    private void e() {
        Intent a = is.a(this, CompleteMyAddressActivity.class);
        a.putExtra("fr.smoney.android.izly.extras.isADirectCallForAddress", this.d);
        a(a, 1, false);
    }

    private void f() {
        Intent a = is.a(this, CbAddActivity.class);
        a.putExtra("fr.smoney.android.izly.intentExtraMode", 1);
        startActivityForResult(a, 2);
    }

    private void g() {
        int keyAt;
        cl b = b();
        SmoneyRequestManager c = c();
        String str = b.b.a;
        String str2 = b.b.c;
        int size = c.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) c.b.valueAt(i);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 221 && intent.getStringExtra("fr.smoney.android.izly.extras.getUserStatusUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.getUserStatusSessionId").equals(str2)) {
                keyAt = c.b.keyAt(i);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(c.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 221);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", c.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.getUserStatusUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.getUserStatusSessionId", str2);
        c.c.startService(intent2);
        c.b.append(keyAt, intent2);
        c.f.bn = null;
        super.a(keyAt, 221, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (a() == 221) {
                    g();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case CompleteSubscriptionInfoType:
                b(b().b.E);
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 221) {
                ClientUserStatus clientUserStatus = (ClientUserStatus) bundle.getParcelable("fr.smoney.android.izly.extras.ClientUserStatus");
                a(clientUserStatus != null ? clientUserStatus.a : -1, serverError);
            }
        }
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (a() == 221) {
                    finish();
                    return;
                } else {
                    super.b(ieVar);
                    return;
                }
            case CompleteSubscriptionInfoType:
                finish();
                return;
            default:
                super.b(ieVar);
                return;
        }
    }

    public final void b_(int i) {
        cl b = b();
        if (i == 221) {
            a(b.b.E, b.bn);
        }
    }

    public final void c(ie ieVar) {
        switch (ieVar) {
            case CompleteSubscriptionSuccessType:
                Intent intent = null;
                switch (this.a) {
                    case 1:
                        intent = is.a(this, MoneyInActivity.class);
                        break;
                    case 2:
                        intent = is.a(this, MoneyInCbAndPayActivity.class);
                        if (this.b != null) {
                            intent.putExtra("fr.smoney.android.izly.extras.p2pPayData", this.b.getParcelable("fr.smoney.android.izly.extras.p2pPayData"));
                            intent.putExtra("fr.smoney.android.izly.extras.forceReloadP2PPay", true);
                            break;
                        }
                        break;
                    case 3:
                        intent = is.a(this, MoneyInCbAndPayActivity.class);
                        if (this.b != null) {
                            intent.putExtra("fr.smoney.android.izly.extras.p2pPayRequestData", this.b.getParcelable("fr.smoney.android.izly.extras.p2pPayRequestData"));
                            intent.putExtra("fr.smoney.android.izly.extras.forceReloadP2PPay", true);
                            break;
                        }
                        break;
                    case 4:
                        intent = is.a(this, MoneyOutTransferActivity.class);
                        break;
                    case 8:
                        intent = is.a(this, HomeActivity.class);
                        break;
                }
                if (intent != null) {
                    startActivity(intent);
                }
                finish();
                finish();
                return;
            case CompleteSubscriptionErrorType:
                setResult(0);
                finish();
                return;
            default:
                super.c(ieVar);
                return;
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case 1:
                if (i2 == -1) {
                    g();
                    return;
                } else {
                    d();
                    return;
                }
            case 2:
                if (i2 == -1) {
                    g();
                    return;
                } else {
                    d();
                    return;
                }
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        cl b = b();
        if (b.b.a()) {
            Intent intent = getIntent();
            this.a = intent.getIntExtra("fr.smoney.android.izly.extras.activityToStart", -1);
            this.b = intent.getBundleExtra("fr.smoney.android.izly.extras.activityToStartDataBundle");
            this.c = intent.getBooleanExtra("fr.smoney.android.izly.extras.isADirectCall", false);
            this.d = intent.getBooleanExtra("fr.smoney.android.izly.extras.isADirectCallForAddress", false);
            if (this.d) {
                e();
                return;
            } else if (this.c) {
                b(b.b.E);
                return;
            } else {
                a(ht.a(getString(R.string.dialog_complete_subscription_info_title), getString(R.string.dialog_complete_subscription_info_msg), getString(R.string.dialog_complete_subscription_info_complete_btn), getString(17039360), this, ie.CompleteSubscriptionInfoType));
                return;
            }
        }
        Toast.makeText(this, getString(R.string.toast_complete_subscription_not_necessary), 0).show();
        finish();
    }
}
