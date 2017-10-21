package defpackage;

import fr.smoney.android.izly.data.model.P2PPay;
import fr.smoney.android.izly.data.model.P2PPayConfirmData;
import fr.smoney.android.izly.data.model.ServerError;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class bp extends DefaultHandler {
    public ServerError a = null;
    public P2PPayConfirmData b = null;
    private StringBuilder c = new StringBuilder();

    public final void characters(char[] cArr, int i, int i2) throws SAXException {
        super.characters(cArr, i, i2);
        this.c.append(cArr, i, i2);
    }

    public final void endElement(String str, String str2, String str3) throws SAXException {
        boolean z = true;
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
        } else if (str2.equals("DEB")) {
            this.b.a.i = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("CRED")) {
            this.b.a.j = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("COM")) {
            this.b.a.k = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("COMHT")) {
            this.b.a.l = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("MSG")) {
            this.b.a.m = this.c.toString();
        } else if (str2.equals("STATUS")) {
            this.b.a.n = Integer.parseInt(this.c.toString());
        } else if (str2.equals("BAL")) {
            this.b.b.a = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("LUD")) {
            try {
                this.b.b.c = ag.a.parse(this.c.toString()).getTime();
            } catch (Exception e2) {
                throw new SAXException(e2);
            }
        } else if (str2.equals("SID")) {
            this.b.c = this.c.toString();
        } else if (str2.equals("ISPRO")) {
            P2PPay p2PPay = this.b.a;
            if (Integer.parseInt(this.c.toString()) != 1) {
                z = false;
            }
            p2PPay.o = z;
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        this.c.setLength(0);
        if (str2.equals("E")) {
            this.a = new ServerError();
        } else if (str2.equals("PAY")) {
            this.b = new P2PPayConfirmData();
        }
    }
}
