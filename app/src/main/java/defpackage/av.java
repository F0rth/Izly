package defpackage;

import fr.smoney.android.izly.data.model.LoginData;
import fr.smoney.android.izly.data.model.OAuthData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.ServiceData;
import fr.smoney.android.izly.data.model.UserData;
import java.util.Currency;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class av extends DefaultHandler {
    public ServerError a = null;
    public LoginData b = null;
    public UserData c = null;
    public boolean d = false;
    public boolean e = false;
    public OAuthData f = null;
    public ServiceData g = null;
    private StringBuilder h = new StringBuilder();

    public final void characters(char[] cArr, int i, int i2) throws SAXException {
        super.characters(cArr, i, i2);
        this.h.append(cArr, i, i2);
    }

    public final void endElement(String str, String str2, String str3) throws SAXException {
        boolean z = false;
        boolean z2 = true;
        if (str2.equals("Error")) {
            this.a.a = this.h.toString();
        } else if (str2.equals("Code")) {
            this.a.b = Integer.parseInt(this.h.toString());
        } else if (str2.equals("Msg")) {
            this.a.c = this.h.toString();
        } else if (str2.equals("Title")) {
            this.a.d = this.h.toString();
        } else if (str2.equals("Prio")) {
            this.a.e = Integer.parseInt(this.h.toString());
        } else if (this.d && str2.equals("UID")) {
            this.b.a = this.h.toString();
        } else if (str2.equals("USER_ID")) {
            this.b.b = this.h.toString();
        } else if (str2.equals("SID")) {
            this.b.c = this.h.toString();
        } else if (str2.equals("P2PPAYMIN")) {
            this.b.d = Double.parseDouble(iu.c(this.h.toString()));
        } else if (str2.equals("P2PPAYMAX")) {
            this.b.e = Double.parseDouble(iu.c(this.h.toString()));
        } else if (str2.equals("MONEYINMIN")) {
            this.b.f = Double.parseDouble(iu.c(this.h.toString()));
        } else if (str2.equals("MONEYINMAX")) {
            this.b.g = Double.parseDouble(iu.c(this.h.toString()));
        } else if (str2.equals("MONEYOUTMIN")) {
            this.b.h = Double.parseDouble(iu.c(this.h.toString()));
        } else if (str2.equals("MONEYOUTMAX")) {
            this.b.i = Double.parseDouble(iu.c(this.h.toString()));
        } else if (str2.equals("CUR")) {
            this.b.j = this.h.toString();
            try {
                Currency.getInstance(this.b.j);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if (str2.equals("STATUS")) {
            this.b.k = Integer.parseInt(this.h.toString());
        } else if (str2.equals("BLOCKED")) {
            this.b.l = Integer.parseInt(this.h.toString());
        } else if (str2.equals("NBP2PGET")) {
            this.b.m = Integer.parseInt(this.h.toString());
        } else if (str2.equals("NBP2PREQUEST")) {
            this.b.n = Integer.parseInt(this.h.toString());
        } else if (str2.equals("NBTRANSAC")) {
            this.b.o = Integer.parseInt(this.h.toString());
        } else if (str2.equals("TOKEN")) {
            this.b.I = this.h.toString();
        } else if (str2.equals("FNAME")) {
            this.b.p = this.h.toString();
        } else if (str2.equals("LNAME")) {
            this.b.q = this.h.toString();
        } else if (str2.equals("EMAIL")) {
            this.b.r = this.h.toString();
        } else if (str2.equals("AGE")) {
            this.b.s = Integer.parseInt(this.h.toString());
        } else if (str2.equals("ZIP_CODE")) {
            this.b.t = this.h.toString();
        } else if (str2.equals("CROUS")) {
            this.b.u = this.h.toString();
        } else if (str2.equals("CATEGORY_USERID")) {
            this.b.v = Integer.parseInt(this.h.toString());
        } else if (str2.equals("ALIAS")) {
            this.b.w = this.h.toString();
        } else if (str2.equals("OPTIN")) {
            try {
                r2 = this.b;
                if (Integer.parseInt(this.h.toString().trim()) == 1) {
                    z = true;
                }
                r2.C = z;
            } catch (NumberFormatException e2) {
            }
        } else if (str2.equals("OPTINPARTNERS")) {
            try {
                r2 = this.b;
                if (Integer.parseInt(this.h.toString().trim()) != 1) {
                    z2 = false;
                }
                r2.D = z2;
            } catch (NumberFormatException e3) {
            }
        } else if (str2.equals("CROUS_NAME")) {
            this.b.x = this.h.toString();
        } else if (str2.equals("SUBSCRIPTION_DATE")) {
            this.b.z = this.h.toString();
        } else if (str2.equals("TERMS_CONDITIONS_AGREEMENT_DATE")) {
            this.b.y = this.h.toString();
        } else if (str2.equals("SERVICE")) {
            this.b.J.add(this.g);
            this.g = null;
        } else if (str2.equals("ID")) {
            this.g.a = Integer.parseInt(this.h.toString());
        } else if (str2.equals("CGU_EXPIRED") || str2.equals("CGU_EXPIRED")) {
            try {
                if (Integer.parseInt(this.h.toString()) != 1) {
                    z2 = false;
                }
            } catch (NumberFormatException e4) {
                z2 = false;
            }
            if (this.g != null) {
                this.g.b = z2;
            } else {
                this.b.H = z2;
            }
        } else if (str2.equals("BAL")) {
            this.b.B.a = Double.parseDouble(iu.c(this.h.toString()));
        } else if (str2.equals("CASHBAL")) {
            this.b.B.b = Double.parseDouble(iu.c(this.h.toString()));
        } else if (str2.equals("LUD")) {
            try {
                this.b.B.c = ag.a.parse(this.h.toString()).getTime();
            } catch (Exception e5) {
                throw new SAXException(e5);
            }
        } else if (this.e && str2.equals("UID")) {
            this.c.a = this.h.toString();
        } else if (str2.equals("SALT")) {
            this.c.b = this.h.toString();
        } else if (str2.equals("USERSTATUS")) {
            try {
                this.b.E = Integer.parseInt(this.h.toString().trim());
            } catch (Exception e52) {
                throw new SAXException(e52);
            }
        } else if (str2.equals("PROCASHIER")) {
            try {
                r2 = this.b;
                if (Integer.parseInt(this.h.toString().trim()) != 1) {
                    z2 = false;
                }
                r2.F = z2;
            } catch (NumberFormatException e6) {
            }
        } else if (str2.equals("ACCESS_TOKEN")) {
            this.f.a = this.h.toString();
        } else if (str2.equals("EXPIRES_IN")) {
            this.f.c = Long.parseLong(this.h.toString());
        } else if (str2.equals("REFRESH_TOKEN")) {
            this.f.b = this.h.toString();
        } else if (str2.equals("NEWVERSION")) {
            r2 = this.b;
            if (Integer.parseInt(this.h.toString()) != 1) {
                z2 = false;
            }
            r2.G = z2;
        } else if (str2.equals("BankCode")) {
            this.b.A += "," + this.h.toString();
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        this.h.setLength(0);
        if (str2.equals("E")) {
            this.a = new ServerError();
        } else if (str2.equals("Logon")) {
            this.b = new LoginData();
            this.d = true;
        } else if (str2.equals("UserData")) {
            this.c = new UserData();
            this.e = true;
        } else if (str2.equals("OAUTH")) {
            this.f = new OAuthData();
        } else if (str2.equals("SERVICE")) {
            this.g = new ServiceData();
        }
    }
}
