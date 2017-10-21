package defpackage;

import fr.smoney.android.izly.data.model.MoneyInCbAndPayRequestData;
import fr.smoney.android.izly.data.model.P2PPayRequest;
import fr.smoney.android.izly.data.model.ServerError;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class bc extends DefaultHandler {
    public ServerError a = null;
    public MoneyInCbAndPayRequestData b = null;
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
            this.b.b.a = Long.parseLong(this.c.toString());
        } else if (str2.equals("PRID")) {
            this.b.a.a = Long.parseLong(this.c.toString());
        } else if (str2.equals("CID")) {
            this.b.b.c.a = this.c.toString();
        } else if (str2.equals("CALIAS")) {
            this.b.b.c.b = this.c.toString();
        } else if (str2.equals("CNWK")) {
            this.b.b.c.c = Integer.parseInt(this.c.toString());
        } else if (str2.equals("CHNT")) {
            this.b.b.c.d = this.c.toString();
        } else if (str2.equals("DEB")) {
            this.b.b.d = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("CRED")) {
            this.b.b.e = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("COM")) {
            if (this.d) {
                try {
                    this.b.b.f = Double.parseDouble(iu.c(this.c.toString()));
                } catch (NumberFormatException e) {
                    this.b.b.f = 0.0d;
                }
            } else if (this.e) {
                this.b.a.i = Double.parseDouble(this.c.toString().replace(",", "."));
            }
        } else if (str2.equals("PR")) {
            this.e = false;
        } else if (str2.equals("DATE")) {
            try {
                this.b.a.c = ag.a.parse(this.c.toString()).getTime();
            } catch (Exception e2) {
                throw new SAXException(e2);
            }
        } else if (str2.equals("SENID")) {
            this.b.a.d = this.c.toString();
        } else if (str2.equals("SENFNAME")) {
            this.b.a.e = this.c.toString();
        } else if (str2.equals("SENLNAME")) {
            this.b.a.f = this.c.toString();
        } else if (str2.equals("ISPRO")) {
            P2PPayRequest p2PPayRequest = this.b.a;
            if (Integer.parseInt(this.c.toString()) == 1) {
                z = true;
            }
            p2PPayRequest.r = z;
        } else if (str2.equals("AMT")) {
            try {
                this.b.a.h = Double.parseDouble(this.c.toString());
            } catch (NumberFormatException e3) {
                this.b.a.h = -1.0d;
            }
        } else if (str2.equals("MSG")) {
            this.b.a.k = this.c.toString();
        } else if (str2.equals("RESPMSG")) {
            this.b.a.l = this.c.toString();
        } else if (str2.equals("STATUS")) {
            this.b.a.n = Integer.parseInt(this.c.toString());
        } else if (str2.equals("BAL")) {
            this.b.d.a = Double.parseDouble(this.c.toString());
        } else if (str2.equals("LUD")) {
            try {
                this.b.d.c = ag.a.parse(this.c.toString()).getTime();
            } catch (Exception e22) {
                throw new SAXException(e22);
            }
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        this.c.setLength(0);
        if (str2.equals("E")) {
            this.a = new ServerError();
        } else if (str2.equals("RPR")) {
            this.b = new MoneyInCbAndPayRequestData();
        } else if (str2.equals("MIN")) {
            this.d = true;
        } else if (str2.equals("PR")) {
            this.e = true;
        }
    }
}
