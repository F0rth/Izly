package defpackage;

import fr.smoney.android.izly.data.model.MoneyOutTransferData;
import fr.smoney.android.izly.data.model.ServerError;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class bi extends DefaultHandler {
    public ServerError a = null;
    public MoneyOutTransferData b = null;
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
        } else if (str2.equals("AALIAS")) {
            this.b.b = this.c.toString();
        } else if (str2.equals("AHNT")) {
            this.b.c = this.c.toString();
        } else if (str2.equals("DEB")) {
            this.b.d = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("CRED")) {
            this.b.e = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("COM")) {
            this.b.f = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("COMHT")) {
            this.b.g = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("BAL")) {
            this.b.h.a = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("LUD")) {
            try {
                this.b.h.c = ag.a.parse(this.c.toString()).getTime();
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        this.c.setLength(0);
        if (str2.equals("E")) {
            this.a = new ServerError();
        } else if (str2.equals("RMOT")) {
            this.b = new MoneyOutTransferData();
        }
    }
}
