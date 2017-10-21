package defpackage;

import fr.smoney.android.izly.data.model.MoneyOutTransferConfirmData;
import fr.smoney.android.izly.data.model.ServerError;
import java.util.Date;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class bh extends DefaultHandler {
    public ServerError a = null;
    public MoneyOutTransferConfirmData b = null;
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
            this.b.a = Long.parseLong(this.c.toString());
        } else if (str2.equals("DATE")) {
            this.b.b = new Date().getTime();
        } else if (str2.equals("AALIAS")) {
            this.b.c = this.c.toString();
        } else if (str2.equals("AHNT")) {
            this.b.d = this.c.toString();
        } else if (str2.equals("DEB")) {
            this.b.e = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("CRED")) {
            this.b.f = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("COM")) {
            this.b.g = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("COMHT")) {
            this.b.h = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("BAL")) {
            this.b.i.a = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("LUD")) {
            try {
                this.b.i.c = ag.a.parse(this.c.toString()).getTime();
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if (str2.equals("SID")) {
            this.b.j = this.c.toString();
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        this.c.setLength(0);
        if (str2.equals("E")) {
            this.a = new ServerError();
        } else if (str2.equals("RMOTC")) {
            this.b = new MoneyOutTransferConfirmData();
        }
    }
}
