package scanpay.it;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import defpackage.jn;
import defpackage.jo;
import defpackage.jp;
import defpackage.k;
import defpackage.l;
import defpackage.np;
import defpackage.nq;
import defpackage.ov;
import defpackage.ow;
import defpackage.pa;
import defpackage.pb;
import defpackage.t;
import defpackage.u;
import defpackage.v;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;
import org.spongycastle.asn1.x509.DisplayText;

public class ScanPayActivity extends Activity implements OnClickListener, jn, jo, jp, v {
    public ow a;
    private SPCreditCard b;
    private np c;
    private O d;
    private Bitmap e;
    private u f;
    private final String[] g = new String[]{"GT-S5839i", "GT-S5830i", "GT-S5831i", "GT-S5832i"};
    private boolean h = false;

    private void b() {
        if (getIntent().getExtras().getBoolean("confirmation_view", true)) {
            Intent intent = new Intent(this, SPValidationActivity.class);
            intent.putExtra("creditcard", this.b);
            intent.putExtra("cardImage", this.e);
            startActivityForResult(intent, 1);
            return;
        }
        if (!(this.c == null || this.c.b == null)) {
            this.c.b.b();
            ov.j();
        }
        intent = new Intent();
        intent.putExtra("creditcard", this.b);
        if (this.b == null || this.b.a == null) {
            setResult(2, intent);
        } else {
            setResult(1, intent);
        }
        finish();
    }

    public final void a() {
        if (this.c != null && this.a != null) {
            np npVar = this.c;
            npVar.a.runOnUiThread(new nq(npVar));
            this.a.a = this.c.b;
            this.a.enable();
            ov.a(640, 480, 300, DisplayText.DISPLAY_TEXT_MAXIMUM_SIZE, 1.33f);
        }
    }

    public final void a(Bitmap bitmap, float f) {
        this.a.disable();
        this.e = bitmap;
        this.f.a = this;
        u uVar = this.f;
        String f2 = ov.f();
        String g = ov.g();
        if (!(g == null || g.length() == 4)) {
            g = "";
        }
        uVar.b.a(f2);
        if (g.length() == 4) {
            uVar.b.c = g.substring(0, 2);
            uVar.b.d = g.substring(2, 4);
        } else {
            uVar.b.c = "";
            uVar.b.d = "";
        }
        c cVar = new c();
        cVar.c = "2.1";
        cVar.b = String.format("%.2f", new Object[]{Float.valueOf(f)});
        cVar.a = g.length() == 4 ? "true" : "false";
        cVar.d = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        l lVar = uVar.c;
        try {
            SQLiteDatabase writableDatabase = lVar.getWritableDatabase();
            writableDatabase.execSQL("INSERT INTO REQUESTS  (scan_time, founddate, scan_date) VALUES('" + cVar.b + "', '" + cVar.a + "', '" + cVar.d + "')");
            writableDatabase.close();
        } catch (Exception e) {
        }
        lVar.b.add(cVar);
        if (!(lVar.a == null || lVar.a.a.booleanValue()) || lVar.a == null) {
            lVar.a();
        }
        if (uVar.a != null) {
            uVar.a.a(uVar.b);
        }
    }

    public final void a(String str) {
        try {
            String string = new JSONObject(str).getString("scan");
            SharedPreferences sharedPreferences = getSharedPreferences("scanpay", 0);
            if (string.equals("success")) {
                sharedPreferences.edit().putBoolean("isBanFromScanpay", true).commit();
            } else if (string.equals("fail")) {
                sharedPreferences.edit().putBoolean("isBanFromScanpay", false).commit();
            }
        } catch (Exception e) {
        }
    }

    public final void a(SPCreditCard sPCreditCard) {
        if (getSharedPreferences("scanpay", 0).getBoolean("isBanFromScanpay", false)) {
            this.b.a(sPCreditCard.a);
            this.b.c = sPCreditCard.c;
            this.b.d = sPCreditCard.d;
            try {
                ((Vibrator) getSystemService("vibrator")).vibrate(200);
            } catch (Exception e) {
                Log.e("it.scanpay", "Can't vibrate : <uses-permission android:name=\"android.permission.VIBRATE\" />  is missing in your Android manifest");
            }
            b();
            return;
        }
        Builder builder = new Builder(this);
        builder.setMessage(k.a().a("Wrong Token"));
        builder.setPositiveButton("Ok", new t(this));
        builder.show();
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1 && i2 == 1) {
            this.b = (SPCreditCard) intent.getParcelableExtra("creditcard");
            setResult(1, intent);
            finish();
        } else if (i2 == 2 && !this.h) {
            this.e = null;
            this.b = new SPCreditCard();
            findViewById(13).setVisibility(4);
            findViewById(14).setVisibility(4);
            findViewById(16).setVisibility(4);
            findViewById(15).setVisibility(4);
            findViewById(33).setVisibility(4);
            findViewById(34).setVisibility(4);
            findViewById(36).setVisibility(4);
            findViewById(35).setVisibility(4);
            findViewById(4).setVisibility(0);
            findViewById(39).setVisibility(0);
            findViewById(11).setVisibility(0);
            findViewById(5).setVisibility(0);
            findViewById(12).setVisibility(0);
            findViewById(37).setVisibility(0);
            findViewById(42).setVisibility(0);
            findViewById(8).setBackgroundColor(0);
            findViewById(43).setBackgroundColor(0);
            findViewById(44).setVisibility(0);
            if (!getIntent().getBooleanExtra("manual_entry_button", true)) {
                findViewById(42).setVisibility(4);
            }
        } else if (i2 == 2 && this.h) {
            setResult(2);
            finish();
        }
    }

    public void onBackPressed() {
        if (this.f != null) {
            l lVar = this.f.c;
            if (lVar.a != null) {
                pa paVar = lVar.a;
                if (paVar.b != null) {
                    paVar.a = Boolean.valueOf(false);
                    paVar.b.abort();
                }
            }
        }
        setResult(2);
        super.onBackPressed();
    }

    public void onClick(View view) {
        b();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.b = new SPCreditCard();
        try {
            System.loadLibrary("ScanPay");
            this.d = new O(this);
            setContentView(this.d);
            Bundle extras = getIntent().getExtras();
            String str = "";
            if (extras != null) {
                str = extras.getString("sptoken");
                if (!extras.getBoolean("manual_entry_button", true)) {
                    findViewById(42).setVisibility(4);
                }
            }
            if (ov.i() && ((getPackageManager().hasSystemFeature("android.hardware.camera") || getPackageManager().hasSystemFeature("android.hardware.camera.front")) && !Build.PRODUCT.contains("sdk"))) {
                int i;
                Boolean valueOf;
                for (String compareTo : this.g) {
                    if (Build.DEVICE.compareTo(compareTo) == 0) {
                        valueOf = Boolean.valueOf(true);
                        break;
                    }
                }
                valueOf = Boolean.valueOf(false);
                if (!valueOf.booleanValue()) {
                    this.a = new ow(this);
                    this.c = new np(this);
                    setRequestedOrientation(1);
                    if (!getSharedPreferences("scanpay", 0).getBoolean("isBanFromScanpay", false)) {
                        try {
                            i = pb.b;
                            pa paVar = new pa("https://scanpay.it/check_token");
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("token", str);
                            jSONObject.put("package_name", getPackageName());
                            paVar.c = this;
                            paVar.a(jSONObject.toString());
                        } catch (Exception e) {
                        }
                    }
                    this.f = new u(str, this);
                    return;
                }
            }
            this.h = true;
            b();
        } catch (UnsatisfiedLinkError e2) {
            this.h = true;
            b();
        }
    }

    protected void onDestroy() {
        if (!(this.c == null || this.a == null)) {
            this.c.a();
            this.a.disable();
            this.c = null;
        }
        super.onDestroy();
    }

    protected void onPause() {
        if (!(this.c == null || this.a == null)) {
            this.c.a();
            this.a.disable();
        }
        super.onPause();
    }
}
