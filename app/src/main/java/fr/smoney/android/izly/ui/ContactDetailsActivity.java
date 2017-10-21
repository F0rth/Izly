package fr.smoney.android.izly.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import defpackage.hc;
import defpackage.ht;
import defpackage.hu;
import defpackage.hw;
import defpackage.ie;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.Bookmark;
import fr.smoney.android.izly.data.model.Contact;
import fr.smoney.android.izly.data.model.GetContactDetailsData;
import fr.smoney.android.izly.data.model.NearPro;
import fr.smoney.android.izly.data.model.NewsFeedContactLight;
import fr.smoney.android.izly.data.model.P2PPayRequest;
import fr.smoney.android.izly.data.model.PromotionalOffer;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.Transaction;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.fragment.ContactDetailsPartFragment;

public class ContactDetailsActivity extends SmoneyABSActivity implements SmoneyRequestManager$a {
    NearPro b;
    private String c;
    private int d;
    private GetContactDetailsData e;
    private MenuItem f;
    private MenuItem g;
    private Fragment h;
    private b i;

    final class a extends AsyncTask<Void, Void, Void> {
        final /* synthetic */ ContactDetailsActivity a;
        private Context b;

        public a(ContactDetailsActivity contactDetailsActivity, Context context) {
            this.a = contactDetailsActivity;
            this.b = context;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected final /* synthetic */ java.lang.Object doInBackground(java.lang.Object[] r13) {
            /*
            r12 = this;
            r11 = 1;
            r10 = 2;
            r2 = 0;
            r9 = 0;
            r0 = r12.a;
            r1 = new fr.smoney.android.izly.data.model.GetContactDetailsData;
            r1.<init>();
            r0.e = r1;
            r0 = r12.a;
            r0 = r0.e;
            r0.o = r9;
            r0 = r12.a;
            r0 = r0.e;
            r0.p = r9;
            r0 = r12.b;
            r1 = r12.a;
            r1 = r1.c;
            r3 = "^(0|(\\+|00)33)[67][0-9]{8}$";
            r3 = java.util.regex.Pattern.compile(r3);
            r4 = defpackage.gh.b(r0, r1);
            if (r4 == 0) goto L_0x0195;
        L_0x0032:
            r0 = r4.moveToFirst();
            if (r0 == 0) goto L_0x0195;
        L_0x0038:
            r0 = "data1";
            r5 = r4.getColumnIndex(r0);
            r0 = "data2";
            r6 = r4.getColumnIndex(r0);
            r1 = r2;
        L_0x0045:
            r0 = r4.getString(r5);
            r7 = " ";
            r8 = "";
            r0 = r0.replace(r7, r8);
            r7 = r3.matcher(r0);
            r7 = r7.matches();
            if (r7 == 0) goto L_0x0192;
        L_0x005b:
            r7 = r4.getInt(r6);
            if (r7 != r10) goto L_0x00f2;
        L_0x0061:
            r4.close();
        L_0x0064:
            r1 = r12.a;
            r1 = r1.e;
            r1.e = r0;
            r1 = r12.a;
            r1 = r1.e;
            r1.b = r0;
            r0 = r12.a;
            r1 = r0.e;
            r0 = r12.b;
            r3 = r12.a;
            r3 = r3.c;
            r0 = defpackage.iy.b(r0, r3);
            r3 = r0.size();
            if (r3 <= 0) goto L_0x00fd;
        L_0x008c:
            r0 = r0.get(r9);
            r0 = (java.lang.String) r0;
        L_0x0092:
            r1.w = r0;
            r0 = r12.b;
            r1 = r12.a;
            r1 = r1.c;
            r4 = new java.util.ArrayList;
            r4.<init>();
            r5 = defpackage.gh.e(r0, r1);
            if (r5 == 0) goto L_0x0102;
        L_0x00a7:
            r0 = r5.moveToNext();
            if (r0 == 0) goto L_0x00ff;
        L_0x00ad:
            r0 = "data2";
            r0 = r5.getColumnIndex(r0);
            r3 = r5.getString(r0);
            r0 = "data3";
            r0 = r5.getColumnIndex(r0);
            r1 = r5.getString(r0);
            r0 = "data1";
            r0 = r5.getColumnIndex(r0);
            r0 = r5.getString(r0);
            if (r3 != 0) goto L_0x00cf;
        L_0x00cd:
            r3 = "";
        L_0x00cf:
            if (r1 != 0) goto L_0x00d3;
        L_0x00d1:
            r1 = "";
        L_0x00d3:
            if (r0 != 0) goto L_0x00d7;
        L_0x00d5:
            r0 = "";
        L_0x00d7:
            r6 = "";
            r6 = r3.equals(r6);
            if (r6 == 0) goto L_0x00e8;
        L_0x00df:
            r6 = "";
            r6 = r1.equals(r6);
            if (r6 == 0) goto L_0x00e8;
        L_0x00e7:
            r1 = r0;
        L_0x00e8:
            r4.add(r3);
            r4.add(r1);
            r4.add(r0);
            goto L_0x00a7;
        L_0x00f2:
            if (r1 != 0) goto L_0x0192;
        L_0x00f4:
            r1 = r4.moveToNext();
            if (r1 == 0) goto L_0x0061;
        L_0x00fa:
            r1 = r0;
            goto L_0x0045;
        L_0x00fd:
            r0 = r2;
            goto L_0x0092;
        L_0x00ff:
            r5.close();
        L_0x0102:
            r0 = r4.size();
            if (r0 < r10) goto L_0x0154;
        L_0x0108:
            r0 = r12.a;
            r1 = r0.e;
            r0 = r4.get(r9);
            r0 = (java.lang.String) r0;
            r1.x = r0;
            r0 = r12.a;
            r0 = r0.e;
            r0 = r0.x;
            r0 = r0.length();
            if (r0 != 0) goto L_0x012e;
        L_0x0124:
            r0 = r12.a;
            r0 = r0.e;
            r1 = " ";
            r0.x = r1;
        L_0x012e:
            r0 = r12.a;
            r1 = r0.e;
            r0 = r4.get(r11);
            r0 = (java.lang.String) r0;
            r1.y = r0;
            r0 = r12.a;
            r0 = r0.e;
            r0 = r0.y;
            r0 = r0.length();
            if (r0 != 0) goto L_0x0154;
        L_0x014a:
            r0 = r12.a;
            r0 = r0.e;
            r1 = " ";
            r0.y = r1;
        L_0x0154:
            r0 = r12.b;
            r1 = r12.a;
            r1 = r1.c;
            r1 = defpackage.iy.c(r0, r1);
            r0 = r1.size();
            r3 = 3;
            if (r0 < r3) goto L_0x0191;
        L_0x0167:
            r0 = r12.a;
            r3 = r0.e;
            r0 = r1.get(r9);
            r0 = (java.lang.String) r0;
            r3.q = r0;
            r0 = r12.a;
            r3 = r0.e;
            r0 = r1.get(r11);
            r0 = (java.lang.String) r0;
            r3.s = r0;
            r0 = r12.a;
            r3 = r0.e;
            r0 = r1.get(r10);
            r0 = (java.lang.String) r0;
            r3.r = r0;
        L_0x0191:
            return r2;
        L_0x0192:
            r0 = r1;
            goto L_0x00f4;
        L_0x0195:
            r0 = r2;
            goto L_0x0064;
            */
            throw new UnsupportedOperationException("Method not decompiled: fr.smoney.android.izly.ui.ContactDetailsActivity.a.doInBackground(java.lang.Object[]):java.lang.Object");
        }

        protected final /* synthetic */ void onPostExecute(Object obj) {
            this.a.k();
            this.a.a(this.a.e);
        }
    }

    final class b extends BroadcastReceiver {
        final /* synthetic */ ContactDetailsActivity a;

        private b(ContactDetailsActivity contactDetailsActivity) {
            this.a = contactDetailsActivity;
        }

        public final void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("fr.smoney.android.izly.sessionState", -1) == 1) {
                this.a.l();
            }
        }
    }

    public interface c {
        void a(boolean z);
    }

    private void a(GetContactDetailsData getContactDetailsData) {
        if (getContactDetailsData.o) {
            this.h = hc.a(getContactDetailsData);
        } else {
            this.h = ContactDetailsPartFragment.a(getContactDetailsData);
        }
        a((int) R.id.content, this.h);
    }

    private void a(GetContactDetailsData getContactDetailsData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getContactDetailsData == null) {
            a(hw.a(this, this));
        } else {
            this.e = getContactDetailsData;
            supportInvalidateOptionsMenu();
            a(getContactDetailsData);
        }
    }

    private void b(GetContactDetailsData getContactDetailsData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getContactDetailsData == null) {
            a(hw.a(this, this));
        } else {
            this.e.j = !this.e.j;
            if (this.e.j) {
                Toast.makeText(this, getString(R.string.my_account_contact_details_toast_block), 1).show();
            } else {
                Toast.makeText(this, getString(R.string.my_account_contact_details_toast_unblock), 1).show();
            }
            if (this.e.j) {
                this.f.setIcon(R.drawable.pict_bloc);
                this.f.setTitle(R.string.menu_item_deblock);
            } else {
                this.f.setIcon(R.drawable.pict_bloc_off);
                this.f.setTitle(R.string.menu_item_block);
            }
            boolean z = this.e.j;
            if (this.h instanceof c) {
                ((c) this.h).a(z);
            }
        }
    }

    private void b(boolean z) {
        if (this.f != null) {
            this.f.setVisible(z);
        }
        if (this.g != null) {
            this.g.setVisible(z);
        }
    }

    private void c(GetContactDetailsData getContactDetailsData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getContactDetailsData == null) {
            a(hw.a(this, this));
        } else {
            this.e.l = !this.e.l;
            if (this.e.l) {
                Toast.makeText(this, getString(R.string.my_account_contact_details_toast_bookmark), 1).show();
            } else {
                Toast.makeText(this, getString(R.string.my_account_contact_details_toast_unbookmark), 1).show();
            }
            if (this.e.l) {
                this.g.setIcon(R.drawable.pict_favoris);
                this.g.setTitle(R.string.menu_item_unfavorite);
                return;
            }
            this.g.setIcon(R.drawable.pict_favoris_off);
            this.g.setTitle(R.string.menu_item_favorite);
        }
    }

    private void c(boolean z) {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        String str3 = this.c;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 103 && intent.getStringExtra("fr.smoney.android.izly.extras.blockContactUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.blockContactSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.blockContactContactId").equals(str3) && intent.getBooleanExtra("fr.smoney.android.izly.extras.blockContactBlocksContact", false) != z) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 103);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.blockContactUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.blockContactSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.blockContactContactId", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.blockContactBlocksContact", z);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.ak = null;
        j.f.al = null;
        super.a(keyAt, 103, true);
    }

    private void d(boolean z) {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        String str3 = this.c;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 102 && intent.getStringExtra("fr.smoney.android.izly.extras.bookmarkContactUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.bookmarkContactSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.bookmarkContactContactId").equals(str3) && intent.getBooleanExtra("fr.smoney.android.izly.extras.bookmarkContactBookmarksContact", false) != z) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 102);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.bookmarkContactUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.bookmarkContactSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.bookmarkContactContactId", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.bookmarkContactBookmarksContact", z);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.ai = null;
        j.f.aj = null;
        super.a(keyAt, 102, true);
    }

    private void k() {
        if (this.e != null) {
            if (this.e.p || this.e.o) {
                b(true);
            } else {
                b(false);
            }
            if (this.g != null) {
                if (this.e.l) {
                    this.g.setIcon(R.drawable.pict_favoris);
                    this.g.setTitle(R.string.menu_item_unfavorite);
                } else {
                    this.g.setIcon(R.drawable.pict_favoris_off);
                    this.g.setTitle(R.string.menu_item_favorite);
                }
            }
            if (this.f == null) {
                return;
            }
            if (this.e.j) {
                this.f.setIcon(R.drawable.pict_bloc);
                this.f.setTitle(R.string.menu_item_deblock);
                return;
            }
            this.f.setIcon(R.drawable.pict_bloc_off);
            this.f.setTitle(R.string.menu_item_block);
            return;
        }
        b(false);
    }

    private void l() {
        cl i = i();
        super.a(j().c(i.b.a, i.b.c, this.c, null, null), 224, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        boolean z = true;
        boolean z2 = false;
        switch (ieVar) {
            case BlockContactType:
                if (!this.e.j) {
                    z2 = true;
                }
                c(z2);
                return;
            case BookmarkContactType:
                if (this.e.l) {
                    z = false;
                }
                d(z);
                return;
            case ConnexionErrorType:
                int h = h();
                if (h == 224) {
                    l();
                    return;
                } else if (h == 103) {
                    if (this.e.j) {
                        z = false;
                    }
                    c(z);
                    return;
                } else if (h == 102) {
                    if (this.e.l) {
                        z = false;
                    }
                    d(z);
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
            switch (i2) {
                case 102:
                    c((GetContactDetailsData) bundle.getParcelable("fr.smoney.android.izly.extras.bookmarkContactData"), serverError);
                    return;
                case 103:
                    b((GetContactDetailsData) bundle.getParcelable("fr.smoney.android.izly.extras.blockContactData"), serverError);
                    return;
                case 224:
                    a((GetContactDetailsData) bundle.getParcelable("fr.smoney.android.izly.extras.GetContactDetails"), serverError);
                    return;
                default:
                    return;
            }
        }
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 224) {
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
        switch (i) {
            case 102:
                c(i2.ai, i2.aj);
                return;
            case 103:
                b(i2.ak, i2.al);
                return;
            case 224:
                a(i2.bq, i2.br);
                return;
            default:
                return;
        }
    }

    public final void c(ie ieVar) {
        switch (ieVar) {
            case ErrorType:
                if (h() == 224) {
                    finish();
                    return;
                }
                return;
            default:
                super.c(ieVar);
                return;
        }
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType && h() == 224) {
            g();
            finish();
            return;
        }
        super.d(ieVar);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.contact_details);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.contact_details_root_view);
        viewGroup.requestTransparentRegion(viewGroup);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportInvalidateOptionsMenu();
        Intent intent = getIntent();
        if (intent != null) {
            Parcelable parcelableExtra = intent.getParcelableExtra("fr.smoney.android.izly.extras.contactId");
            if (parcelableExtra instanceof NearPro) {
                this.b = (NearPro) parcelableExtra;
                this.c = this.b.b;
            } else if (parcelableExtra instanceof NewsFeedContactLight) {
                this.c = ((NewsFeedContactLight) parcelableExtra).a;
            } else if (parcelableExtra instanceof Transaction) {
                this.c = ((Transaction) parcelableExtra).p;
            } else if (parcelableExtra instanceof Bookmark) {
                this.c = ((Bookmark) parcelableExtra).a;
            } else if (parcelableExtra instanceof P2PPayRequest) {
                this.c = ((P2PPayRequest) parcelableExtra).d;
            } else if (parcelableExtra instanceof PromotionalOffer) {
                this.c = String.valueOf(((PromotionalOffer) parcelableExtra).a);
            } else {
                this.c = ((Contact) parcelableExtra).a;
            }
            this.d = intent.getIntExtra("fr.smoney.android.izly.extras.mode", 0);
        }
        if (bundle != null) {
            this.e = (GetContactDetailsData) bundle.getParcelable("getContactDetailsData");
            k();
        } else if (this.d == 0) {
            l();
        } else {
            new a(this, this).execute(new Void[0]);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.f = menu.add(R.string.menu_item_block);
        this.f.setIcon(R.drawable.pict_bloc_off);
        this.f.setShowAsAction(2);
        this.g = menu.add(R.string.menu_item_favorite);
        this.g.setIcon(R.drawable.pict_favoris_off);
        this.g.setShowAsAction(2);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem == this.g) {
            if (this.e.m) {
                if (!this.e.l) {
                    a(ht.a(getString(R.string.my_account_contact_details_dialog_action_title), getString(R.string.my_account_contact_details_dialog_confirm_bookmark), getString(17039379), getString(17039369), this, ie.BookmarkContactType));
                } else {
                    a(ht.a(getString(R.string.my_account_contact_details_dialog_action_title), getString(R.string.my_account_contact_details_dialog_confirm_unbookmark), getString(17039379), getString(17039369), this, ie.BookmarkContactType));
                }
            } else {
                a(hu.a(getString(R.string.dialog_error_title), getString(R.string.my_account_contact_details_dialog_unable_to_bookmark), getString(17039370)));
            }
        } else if (menuItem != this.f) {
            finish();
        } else if (this.e.k) {
            boolean z = this.e.j;
            if (this.e.j) {
                a(ht.a(getString(R.string.my_account_contact_details_dialog_action_title), getString(R.string.my_account_contact_details_dialog_confirm_unblock), getString(17039379), getString(17039369), this, ie.BlockContactType));
            } else {
                a(ht.a(getString(R.string.my_account_contact_details_dialog_action_title), getString(R.string.my_account_contact_details_dialog_confirm_block), getString(17039379), getString(17039369), this, ie.BlockContactType));
            }
        } else {
            a(hu.a(getString(R.string.dialog_error_title), getString(R.string.my_account_contact_details_dialog_unable_to_block), getString(17039370)));
        }
        return true;
    }

    public void onPause() {
        unregisterReceiver(this.i);
        this.i = null;
        super.onPause();
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        k();
        return super.onPrepareOptionsMenu(menu);
    }

    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("fr.smoney.android.izly.notifications.NOTIFICATION_SESSION_STATE_CHANGE_INTENT_URI");
        if (this.i == null) {
            this.i = new b();
        }
        registerReceiver(this.i, intentFilter);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.e != null) {
            bundle.putParcelable("getContactDetailsData", this.e);
        }
    }
}
