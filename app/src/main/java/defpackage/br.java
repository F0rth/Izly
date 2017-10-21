package defpackage;

import android.text.TextUtils;
import fr.smoney.android.izly.data.model.P2PPayRequest;
import fr.smoney.android.izly.data.model.P2PPayRequestConfirmData;
import fr.smoney.android.izly.data.model.ServerError;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class br extends DefaultHandler {
    public P2PPayRequestConfirmData a = null;
    public ServerError b = null;
    private StringBuilder c = new StringBuilder();

    public final void characters(char[] cArr, int i, int i2) throws SAXException {
        super.characters(cArr, i, i2);
        this.c.append(cArr, i, i2);
    }

    public final void endElement(String str, String str2, String str3) throws SAXException {
        boolean z = false;
        if (str2.equals("Error")) {
            this.b.a = this.c.toString();
        } else if (str2.equals("Code")) {
            this.b.b = Integer.parseInt(this.c.toString());
        } else if (str2.equals("Msg")) {
            this.b.c = this.c.toString();
        } else if (str2.equals("Title")) {
            this.b.d = this.c.toString();
        } else if (str2.equals("Prio")) {
            this.b.e = Integer.parseInt(this.c.toString());
        } else if (str2.equals("PRID")) {
            this.a.a.a = Long.parseLong(this.c.toString());
        } else if (str2.equals("DATE")) {
            try {
                this.a.a.c = ag.a.parse(this.c.toString()).getTime();
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if (str2.equals("SENID")) {
            this.a.a.d = this.c.toString();
        } else if (str2.equals("SENFNAME")) {
            this.a.a.e = this.c.toString();
        } else if (str2.equals("SENLNAME")) {
            this.a.a.f = this.c.toString();
        } else if (str2.equals("ISPRO")) {
            P2PPayRequest p2PPayRequest = this.a.a;
            if (Integer.parseInt(this.c.toString()) == 1) {
                z = true;
            }
            p2PPayRequest.r = z;
        } else if (str2.equals("AMT")) {
            if (TextUtils.isEmpty(this.c.toString())) {
                this.a.a.h = -1.0d;
                this.a.a.g = true;
                return;
            }
            this.a.a.h = Double.parseDouble(iu.c(this.c.toString()));
            this.a.a.g = false;
        } else if (str2.equals("COM")) {
            if (TextUtils.isEmpty(this.c.toString())) {
                this.a.a.i = -1.0d;
                return;
            }
            this.a.a.i = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("COMHT")) {
            if (TextUtils.isEmpty(this.c.toString())) {
                this.a.a.j = -1.0d;
                return;
            }
            this.a.a.j = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("MSG")) {
            this.a.a.k = this.c.toString();
        } else if (str2.equals("RESPMSG")) {
            this.a.a.l = this.c.toString();
        } else if (str2.equals("RESPDATE")) {
            try {
                if (TextUtils.isEmpty(this.c.toString())) {
                    this.a.a.m = -1;
                    return;
                }
                this.a.a.m = ag.a.parse(this.c.toString()).getTime();
            } catch (Exception e2) {
                throw new SAXException(e2);
            }
        } else if (str2.equals("STATUS")) {
            this.a.a.n = Integer.parseInt(this.c.toString());
        } else if (str2.equals("BAL")) {
            this.a.b.a = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("LUD")) {
            try {
                this.a.b.c = ag.a.parse(this.c.toString()).getTime();
            } catch (Exception e22) {
                throw new SAXException(e22);
            }
        } else if (str2.equals("SID")) {
            this.a.c = this.c.toString();
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        this.c.setLength(0);
        if (str2.equals("E")) {
            this.b = new ServerError();
        } else if (str2.equals("RPR")) {
            this.a = new P2PPayRequestConfirmData();
        }
    }
}
