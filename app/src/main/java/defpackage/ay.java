package defpackage;

import fr.smoney.android.izly.data.model.MoneyInCbAndPayConfirmData;
import fr.smoney.android.izly.data.model.P2PPay;
import fr.smoney.android.izly.data.model.ServerError;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class ay extends DefaultHandler {
    public ServerError a = null;
    public MoneyInCbAndPayConfirmData b = null;
    private StringBuilder c = new StringBuilder();
    private boolean d = false;
    private boolean e = false;

    public final void characters(char[] cArr, int i, int i2) throws SAXException {
        super.characters(cArr, i, i2);
        this.c.append(cArr, i, i2);
    }

    public final void endElement(String str, String str2, String str3) throws SAXException {
        boolean z = false;
        if (str2.equals("Error")) {
            this.a.a = this.c.toString();
        } else if (str2.equals("Code")) {
            this.a.b = Integer.parseInt(this.c.toString());
        } else if (str2.equals("Msg")) {
            this.a.c = this.c.toString();
        } else if (str2.equals("Title")) {
            this.a.d = this.c.toString();
        } else if (str2.equals("Prio")) {
            this.a.e = Integer.parseInt(this.c.toString());
        } else if (str2.equals("MIN")) {
            this.d = false;
        } else if (str2.equals("ID")) {
            if (this.d) {
                this.b.b.a = Long.parseLong(this.c.toString());
            } else if (this.e) {
                this.b.a.a = Long.parseLong(this.c.toString());
            }
        } else if (str2.equals("CID")) {
            this.b.b.c.a = this.c.toString();
        } else if (str2.equals("CALIAS")) {
            this.b.b.c.b = this.c.toString();
        } else if (str2.equals("CNWK")) {
            this.b.b.c.c = Integer.parseInt(this.c.toString());
        } else if (str2.equals("CHNT")) {
            this.b.b.c.d = this.c.toString();
        } else if (str2.equals("DATE")) {
            try {
                if (this.d) {
                    this.b.b.b = ag.a.parse(this.c.toString()).getTime();
                } else if (this.e) {
                    this.b.a.b = ag.a.parse(this.c.toString()).getTime();
                }
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if (str2.equals("SEN")) {
            this.b.a.c = this.c.toString();
        } else if (str2.equals("REC")) {
            this.b.a.d = this.c.toString();
        } else if (str2.equals("RECCLI")) {
            this.b.a.f = this.c.toString().equals("1");
        } else if (str2.equals("RECFNAME")) {
            this.b.a.g = this.c.toString();
        } else if (str2.equals("RECLNAME")) {
            this.b.a.h = this.c.toString();
        } else if (str2.equals("ISPRO")) {
            P2PPay p2PPay = this.b.a;
            if (Integer.parseInt(this.c.toString()) == 1) {
                z = true;
            }
            p2PPay.o = z;
        } else if (str2.equals("DEB")) {
            if (this.d) {
                this.b.b.d = Double.parseDouble(iu.c(this.c.toString()));
            } else if (this.e) {
                this.b.a.i = Double.parseDouble(iu.c(this.c.toString()));
            }
        } else if (str2.equals("CRED")) {
            if (this.d) {
                this.b.b.e = Double.parseDouble(iu.c(this.c.toString()));
            } else if (this.e) {
                this.b.a.j = Double.parseDouble(iu.c(this.c.toString()));
            }
        } else if (str2.equals("COM")) {
            if (this.d) {
                try {
                    this.b.b.f = Double.parseDouble(iu.c(this.c.toString()));
                } catch (NumberFormatException e2) {
                    this.b.b.f = 0.0d;
                }
            } else if (this.e) {
                try {
                    this.b.a.k = Double.parseDouble(iu.c(this.c.toString()));
                } catch (NumberFormatException e3) {
                    this.b.b.f = 0.0d;
                }
            }
        } else if (str2.equals("MSG")) {
            this.b.a.m = this.c.toString();
        } else if (str2.equals("STATUS")) {
            this.b.a.n = Integer.parseInt(this.c.toString());
        } else if (str2.equals("BAL")) {
            this.b.g.a = Double.parseDouble(this.c.toString());
        } else if (str2.equals("LUD")) {
            try {
                this.b.g.c = ag.a.parse(this.c.toString()).getTime();
            } catch (Exception e4) {
                throw new SAXException(e4);
            }
        } else if (str2.equals("SID")) {
            this.b.f = this.c.toString();
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        this.c.setLength(0);
        if (str2.equals("E")) {
            this.a = new ServerError();
        } else if (str2.equals("MINPAY")) {
            this.b = new MoneyInCbAndPayConfirmData();
        } else if (str2.equals("MIN")) {
            this.d = true;
        } else if (str2.equals("HPAY")) {
            this.e = true;
        }
    }
}
