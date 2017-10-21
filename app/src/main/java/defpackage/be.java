package defpackage;

import fr.smoney.android.izly.data.model.MoneyInCbConfirmData;
import fr.smoney.android.izly.data.model.ServerError;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class be extends DefaultHandler {
    public ServerError a = null;
    public MoneyInCbConfirmData b = null;
    private StringBuilder c = new StringBuilder();

    public final void characters(char[] cArr, int i, int i2) throws SAXException {
        super.characters(cArr, i, i2);
        this.c.append(cArr, i, i2);
    }

    public final void endElement(String str, String str2, String str3) throws SAXException {
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
        } else if (str2.equals("ID")) {
            this.b.a.a = Long.parseLong(this.c.toString());
        } else if (str2.equals("DATE")) {
            try {
                this.b.a.b = ag.a.parse(this.c.toString()).getTime();
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if (str2.equals("CID")) {
            this.b.a.c.a = this.c.toString();
        } else if (str2.equals("CALIAS")) {
            this.b.a.c.b = this.c.toString();
        } else if (str2.equals("CNWK")) {
            this.b.a.c.c = Integer.parseInt(this.c.toString());
        } else if (str2.equals("CHNT")) {
            this.b.a.c.d = this.c.toString();
        } else if (str2.equals("DEB")) {
            this.b.a.d = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("CRED")) {
            this.b.a.e = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("COM")) {
            if (this.c.toString().length() > 0) {
                this.b.a.f = Double.parseDouble(iu.c(this.c.toString()));
            }
        } else if (str2.equals("BAL")) {
            this.b.e.a = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("LUD")) {
            try {
                this.b.e.c = ag.a.parse(this.c.toString()).getTime();
            } catch (Exception e2) {
                throw new SAXException(e2);
            }
        } else if (str2.equals("SID")) {
            this.b.f = this.c.toString();
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        this.c.setLength(0);
        if (str2.equals("E")) {
            this.a = new ServerError();
        } else if (str2.equals("RMINC")) {
            this.b = new MoneyInCbConfirmData();
        }
    }
}
