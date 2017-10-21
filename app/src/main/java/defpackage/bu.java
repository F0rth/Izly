package defpackage;

import android.text.TextUtils;
import fr.smoney.android.izly.data.model.P2PPayRequest;
import fr.smoney.android.izly.data.model.P2PPayRequestListData;
import fr.smoney.android.izly.data.model.ServerError;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class bu extends DefaultHandler {
    public ServerError a = null;
    public P2PPayRequestListData b = null;
    public int c = -1;
    private StringBuilder d = new StringBuilder();
    private P2PPayRequest e = null;

    public final void characters(char[] cArr, int i, int i2) throws SAXException {
        super.characters(cArr, i, i2);
        this.d.append(cArr, i, i2);
    }

    public final void endElement(String str, String str2, String str3) throws SAXException {
        boolean z = false;
        boolean z2 = true;
        if (str2.equals("Error")) {
            this.a.a = this.d.toString();
        } else if (str2.equals("Code")) {
            this.a.b = Integer.parseInt(this.d.toString());
        } else if (str2.equals("Msg")) {
            this.a.c = this.d.toString();
        } else if (str2.equals("Title")) {
            this.a.d = this.d.toString();
        } else if (str2.equals("Prio")) {
            this.a.e = Integer.parseInt(this.d.toString());
        } else if (str2.equals("RPRLIST")) {
            P2PPayRequestListData p2PPayRequestListData = this.b;
            if (this.c > this.b.a.size()) {
                z = true;
            }
            p2PPayRequestListData.b = z;
        } else if (str2.equals("PR")) {
            this.b.a.add(this.e);
        } else if (str2.equals("PRID")) {
            this.e.a = Long.parseLong(this.d.toString());
        } else if (str2.equals("ISREAD")) {
            r2 = this.e;
            if (!this.d.toString().equals("1")) {
                z2 = false;
            }
            r2.b = z2;
        } else if (str2.equals("DATE")) {
            try {
                this.e.c = ag.a.parse(this.d.toString()).getTime();
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if (str2.equals("ISCARITATIVE")) {
            r2 = this.e;
            if (!this.d.toString().equals("1")) {
                z2 = false;
            }
            r2.u = z2;
        } else if (str2.equals("SENID")) {
            this.e.d = this.d.toString();
        } else if (str2.equals("SENFNAME")) {
            this.e.e = this.d.toString();
        } else if (str2.equals("SENLNAME")) {
            this.e.f = this.d.toString();
        } else if (str2.equals("ISPRO")) {
            r2 = this.e;
            if (Integer.parseInt(this.d.toString()) != 1) {
                z2 = false;
            }
            r2.r = z2;
        } else if (str2.equals("AMT")) {
            if (TextUtils.isEmpty(this.d.toString())) {
                this.e.h = -1.0d;
                this.e.g = true;
                return;
            }
            this.e.h = Double.parseDouble(iu.c(this.d.toString()));
            this.e.g = false;
        } else if (str2.equals("COM")) {
            if (TextUtils.isEmpty(this.d.toString())) {
                this.e.i = -1.0d;
                return;
            }
            this.e.i = Double.parseDouble(iu.c(this.d.toString()));
        } else if (str2.equals("MSG")) {
            this.e.k = this.d.toString();
        } else if (str2.equals("RESPMSG")) {
            this.e.l = this.d.toString();
        } else if (str2.equals("RESPDATE")) {
            try {
                if (TextUtils.isEmpty(this.d.toString())) {
                    this.e.m = -1;
                    return;
                }
                this.e.m = ag.a.parse(this.d.toString()).getTime();
            } catch (Exception e2) {
                throw new SAXException(e2);
            }
        } else if (str2.equals("STATUS")) {
            this.e.n = Integer.parseInt(this.d.toString());
        } else if (str2.equals("ATTACHMENTID")) {
            this.e.p = this.d.toString();
        } else if (str2.equals("ATTACHMENTNAME")) {
            this.e.o = this.d.toString();
        } else if (str2.equals("BAL")) {
            this.b.e.a = Double.parseDouble(iu.c(this.d.toString()));
        } else if (str2.equals("LUD")) {
            try {
                this.b.e.c = ag.a.parse(this.d.toString()).getTime();
            } catch (Exception e22) {
                throw new SAXException(e22);
            }
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        boolean z = false;
        this.d.setLength(0);
        if (str2.equals("E")) {
            this.a = new ServerError();
        } else if (str2.equals("RPRLIST")) {
            this.b = new P2PPayRequestListData();
        } else if (str2.equals("PRLIST")) {
            this.b.a = new ArrayList();
            this.c = Integer.parseInt(attributes.getValue("NB"));
            P2PPayRequestListData p2PPayRequestListData = this.b;
            if (Integer.parseInt(attributes.getValue("NEW")) == 1) {
                z = true;
            }
            p2PPayRequestListData.d = z;
            this.b.c = Integer.parseInt(attributes.getValue("TOTAL"));
        } else if (str2.equals("PR")) {
            this.e = new P2PPayRequest();
        }
    }
}
