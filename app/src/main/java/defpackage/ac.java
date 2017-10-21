package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import fr.smoney.android.izly.data.model.ActivateUserData;
import fr.smoney.android.izly.data.model.OAuthData;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.crypto.DataLengthException;

public class ac {
    private static final String g = ac.class.getSimpleName();
    public OAuthData a = null;
    public boolean b = false;
    public String c = null;
    public boolean d = false;
    public ActivateUserData e = null;
    public Context f;
    private boolean h = false;

    public ac(Context context) {
        this.f = context;
    }

    public static String b(String str, String str2) {
        try {
            Mac instance = Mac.getInstance("HmacSHA1");
            instance.init(new SecretKeySpec(str.getBytes(), instance.getAlgorithm()));
            return new String(Base64.encode(instance.doFinal(str2.getBytes("UTF-8")), 8));
        } catch (NoSuchAlgorithmException e) {
        } catch (IllegalArgumentException e2) {
        } catch (InvalidKeyException e3) {
        } catch (IllegalStateException e4) {
        } catch (UnsupportedEncodingException e5) {
        }
        return "";
    }

    public final OAuthData a() {
        OAuthData oAuthData = null;
        if (this.a == null && !this.b) {
            SharedPreferences sharedPreferences = this.f.getSharedPreferences("fr.smoney.android.izly.config.sp", 0);
            String string = sharedPreferences.getString("OAuth.AccessToken", null);
            long j = sharedPreferences.getLong("OAuth.Expiration", -1);
            String string2 = sharedPreferences.getString("OAuth.RefreshToken", null);
            this.b = true;
            if (!(string == null || j == -1 || string2 == null)) {
                oAuthData = new OAuthData(string, string2, j);
            }
            this.a = oAuthData;
        }
        return this.a;
    }

    public final String a(String str, String str2) {
        if (str2 == null) {
            str2 = "";
        }
        String c = c(str);
        if (c != null && c.length() > 0) {
            c = c.substring(0, c.length() - 1);
        }
        return str2.concat(c);
    }

    public final void a(OAuthData oAuthData) {
        this.a = oAuthData;
        Editor edit = this.f.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).edit();
        edit.putString("OAuth.AccessToken", oAuthData.a);
        edit.putLong("OAuth.Expiration", oAuthData.c);
        edit.putString("OAuth.RefreshToken", oAuthData.b);
        edit.commit();
    }

    public final boolean a(String str) {
        String f = f();
        return f != null && f.equals(jf.c(str));
    }

    public final void b() {
        Editor edit = this.f.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).edit();
        edit.remove("OAuth.AccessToken");
        edit.remove("OAuth.Expiration");
        edit.remove("OAuth.RefreshToken");
        this.a = null;
        edit.commit();
    }

    public final void b(String str) {
        this.c = str;
        Editor edit = this.f.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).edit();
        edit.putString("sharedPrefHotpUserId", str);
        edit.remove("sharedPrefHotpCounter");
        edit.remove("sharedPrefHotpActivationCode");
        edit.commit();
    }

    public final ActivateUserData c() {
        ActivateUserData activateUserData = null;
        if (this.e == null && !this.h) {
            SharedPreferences sharedPreferences = this.f.getSharedPreferences("fr.smoney.android.izly.config.sp", 0);
            String string = sharedPreferences.getString("ActivationUserData.UserName", null);
            String string2 = sharedPreferences.getString("ActivationUserData.Token", null);
            int i = sharedPreferences.getInt("ActivationUserData.Status", -1);
            this.h = true;
            if (!(string == null || string2 == null || i == -1)) {
                activateUserData = new ActivateUserData(string, string2, i);
            }
            this.e = activateUserData;
        }
        return this.e;
    }

    public String c(String str) {
        if (!a(str)) {
            return null;
        }
        SharedPreferences sharedPreferences = this.f.getSharedPreferences("fr.smoney.android.izly.config.sp", 0);
        String str2 = "";
        try {
            Mac instance = Mac.getInstance("HmacSHA1");
            String string = sharedPreferences.getString("sharedPrefHotpActivationCode", "");
            String string2 = sharedPreferences.getString("sharedPrefHotpCounter", "");
            if (string.length() == 0 || string2.length() == 0) {
                throw new IllegalArgumentException("Activation code or Counter is empty");
            }
            instance.init(new SecretKeySpec(Base64.decode(string, 8), instance.getAlgorithm()));
            byte[] decode = Base64.decode(string2, 8);
            str2 = Base64.encodeToString(instance.doFinal(ix.b(ix.a(decode))), 8);
            Editor edit = sharedPreferences.edit();
            edit.putString("sharedPrefHotpCounter", Base64.encodeToString(ix.a(ix.a(decode) + 1), 8));
            edit.commit();
            return str2;
        } catch (InvalidKeyException e) {
            return str2;
        } catch (NoSuchAlgorithmException e2) {
            return str2;
        } catch (DataLengthException e3) {
            return str2;
        } catch (IllegalStateException e4) {
            return str2;
        } catch (IllegalArgumentException e5) {
            return "";
        }
    }

    public final void d() {
        Editor edit = this.f.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).edit();
        edit.remove("ActivationUserData.UserName");
        edit.remove("ActivationUserData.Token");
        edit.remove("ActivationUserData.Status");
        this.e = null;
        edit.commit();
    }

    public final boolean e() {
        return f() == null;
    }

    public final String f() {
        if (this.c == null && !this.d) {
            String string = this.f.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).getString("sharedPrefHotpUserId", null);
            this.d = true;
            this.c = string;
        }
        return this.c;
    }

    public final void g() {
        this.c = null;
        Editor edit = this.f.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).edit();
        edit.remove("sharedPrefHotpUserId");
        edit.remove("sharedPrefHotpCounter");
        edit.remove("sharedPrefHotpActivationCode");
        edit.commit();
    }

    public final void h() {
        Editor edit = this.f.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).edit();
        edit.remove("sharedPrefHotpCounter");
        edit.remove("sharedPrefHotpActivationCode");
        edit.commit();
    }
}
