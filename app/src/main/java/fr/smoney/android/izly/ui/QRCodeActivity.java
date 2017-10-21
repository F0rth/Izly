package fr.smoney.android.izly.ui;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import defpackage.gk;
import defpackage.gl;
import defpackage.hu;
import defpackage.hw;
import defpackage.ie;
import defpackage.jb;
import defpackage.jf;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.LoginData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.UserData;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class QRCodeActivity extends SmoneyABSActivity implements SmoneyRequestManager$a {
    private ac b;
    private String c;
    private String d = "UTC";
    @Bind({2131755284})
    ImageView qrImage;

    enum a {
        IZLY("AIZ"),
        SMONEY("A");

        String c;

        private a(String str) {
            this.c = str;
        }
    }

    private Bitmap a(String str) throws WriterException {
        gl glVar = new gl(str, null, "TEXT_TYPE", BarcodeFormat.QR_CODE.toString(), (int) getResources().getDimension(R.dimen.qr_size));
        if (!glVar.d) {
            return null;
        }
        Object obj;
        Map enumMap;
        CharSequence charSequence = glVar.b;
        for (int i = 0; i < charSequence.length(); i++) {
            if (charSequence.charAt(i) > 'ÿ') {
                obj = "UTF-8";
                break;
            }
        }
        obj = null;
        if (obj != null) {
            enumMap = new EnumMap(EncodeHintType.class);
            enumMap.put(EncodeHintType.CHARACTER_SET, obj);
        } else {
            enumMap = null;
        }
        BitMatrix encode = new MultiFormatWriter().encode(glVar.b, glVar.c, glVar.a, glVar.a, enumMap);
        int width = encode.getWidth();
        int height = encode.getHeight();
        int[] iArr = new int[(width * height)];
        for (int i2 = 0; i2 < height; i2++) {
            for (int i3 = 0; i3 < width; i3++) {
                iArr[(i2 * width) + i3] = encode.get(i3, i2) ? -12632257 : -1;
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return createBitmap;
    }

    private String a(a aVar) {
        String c = this.b.c(this.c);
        c = (c == null || c.length() <= 0) ? null : c.substring(0, c.length() - 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(this.d));
        try {
            String str = aVar.c + ";" + this.c + ";" + simpleDateFormat.format(new Date()) + ";2";
            StringBuilder append = new StringBuilder().append(str).append(";");
            Key secretKeySpec = new SecretKeySpec(c.getBytes(), "HmacSHA1");
            Mac instance = Mac.getInstance("HmacSHA1");
            instance.init(secretKeySpec);
            return append.append(gk.a(instance.doFinal(str.getBytes()))).toString();
        } catch (SignatureException e) {
            e.printStackTrace();
            return "";
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return "";
        } catch (InvalidKeyException e3) {
            e3.printStackTrace();
            return "";
        }
    }

    private void a(LoginData loginData, UserData userData, ServerError serverError) {
        if (serverError != null) {
            if (serverError.b == 120) {
                a(hu.a(serverError.d, serverError.c, getString(17039370)));
            } else {
                a(serverError);
            }
        } else if (loginData == null && userData == null) {
            a(hw.a(this, this));
        } else {
            try {
                this.qrImage.setImageBitmap(a(a(a.SMONEY)));
            } catch (WriterException e) {
                e.printStackTrace();
            }
            jb.a(getApplicationContext(), R.string.screen_name_pay_qrcode_smoney_activity);
        }
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case InputPasswordType:
                super.a(j().a(i().a, bundle.getString("Data.Password"), true, jf.a(), false), 1, true);
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            switch (i2) {
                case 1:
                    a((LoginData) bundle.getParcelable("fr.smoney.android.izly.extras.loginData"), (UserData) bundle.getParcelable("fr.smoney.android.izly.extras.userData"), serverError);
                    return;
                default:
                    return;
            }
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        switch (i) {
            case 1:
                a(i2.b, i2.c, i2.d);
                return;
            default:
                return;
        }
    }

    public final void c(ie ieVar) {
        super.c(ieVar);
        int[] iArr = AnonymousClass1.a;
        ieVar.ordinal();
        super.d(ieVar);
    }

    public final void d(ie ieVar) {
        int[] iArr = AnonymousClass1.a;
        ieVar.ordinal();
        super.d(ieVar);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_qr_code);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        this.b = new ac(this);
        this.c = this.b.f();
        LayoutParams layoutParams = new LayoutParams(0, -2);
        layoutParams.setMargins(0, 0, 0, 0);
        layoutParams.weight = 1.0f;
        try {
            this.qrImage.setImageBitmap(a(a(a.IZLY)));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.screenBrightness = 1.0f;
        getWindow().setAttributes(attributes);
        jb.a(getApplicationContext(), R.string.screen_name_pay_qrcode_izly_activity);
    }
}
