package defpackage;

import com.ad4screen.sdk.external.shortcutbadger.impl.AdwHomeBadger;
import fr.smoney.android.izly.data.model.P2PGet;
import fr.smoney.android.izly.data.model.P2PGetMultConfirmData;
import fr.smoney.android.izly.data.model.ServerError;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class bn extends DefaultHandler {
    public ServerError a = null;
    public P2PGetMultConfirmData b = null;
    public boolean c = false;
    public P2PGet d = null;
    private StringBuilder e = new StringBuilder();

    public final void characters(char[] cArr, int i, int i2) throws SAXException {
        super.characters(cArr, i, i2);
        this.e.append(cArr, i, i2);
    }

    public final void endElement(String str, String str2, String str3) throws SAXException {
        boolean z = true;
        boolean z2 = false;
        if (this.c) {
            if (!this.c) {
                return;
            }
            if (str2.equals("GETS")) {
                this.c = false;
            } else if (str2.equals("GET")) {
                this.b.b.add(this.d);
                this.d = null;
            } else if (str2.equals("RECID")) {
                this.d.c = this.e.toString();
            } else if (str2.equals("RECCLI")) {
                r2 = this.d;
                if (this.e.toString().equals("1")) {
                    z2 = true;
                }
                r2.d = z2;
            } else if (str2.equals("RECFNAME")) {
                this.d.f = this.e.toString();
            } else if (str2.equals("RECLNAME")) {
                this.d.g = this.e.toString();
            } else if (str2.equals("ISPRO")) {
                r2 = this.d;
                if (Integer.parseInt(this.e.toString()) != 1) {
                    z = false;
                }
                r2.e = z;
            } else if (str2.equals("AMT")) {
                if (this.e.toString().length() > 0) {
                    this.d.h = Double.parseDouble(this.e.toString());
                    return;
                }
                this.d.h = -1.0d;
            } else if (str2.equals("COM")) {
                if (this.e.toString().length() > 0) {
                    this.d.i = Double.parseDouble(this.e.toString());
                    return;
                }
                this.d.i = -1.0d;
            } else if (str2.equals("Code")) {
                this.d.q = Integer.parseInt(this.e.toString());
            } else if (str2.equals("Msg")) {
                this.d.r = this.e.toString();
            } else if (str2.equals("Prio")) {
                this.d.s = Integer.parseInt(this.e.toString());
            } else if (str2.equals("SID")) {
                this.b.i = this.e.toString();
            }
        } else if (str2.equals("Error")) {
            this.a.a = this.e.toString();
        } else if (str2.equals("Code")) {
            this.a.b = Integer.parseInt(this.e.toString());
        } else if (str2.equals("Msg")) {
            this.a.c = this.e.toString();
        } else if (str2.equals("Title")) {
            this.a.d = this.e.toString();
        } else if (str2.equals("Prio")) {
            this.a.e = Integer.parseInt(this.e.toString());
        } else if (str2.equals("ID")) {
            this.b.c = Long.parseLong(this.e.toString());
        } else if (str2.equals("DATE")) {
            try {
                this.b.d = ag.a.parse(this.e.toString()).getTime();
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if (str2.equals("TOTAL")) {
            if (this.e.toString().length() > 0) {
                this.b.e = Double.parseDouble(this.e.toString());
                return;
            }
            this.b.e = -1.0d;
        } else if (str2.equals(AdwHomeBadger.COUNT)) {
            this.b.f = Integer.parseInt(this.e.toString());
        } else if (str2.equals("MSG")) {
            this.b.g = this.e.toString();
        } else if (str2.equals("BAL")) {
            this.b.a.a = Double.parseDouble(iu.c(this.e.toString()));
        } else if (str2.equals("LUD")) {
            try {
                this.b.a.c = ag.a.parse(this.e.toString()).getTime();
            } catch (Exception e2) {
                throw new SAXException(e2);
            }
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        this.e.setLength(0);
        if (this.c) {
            if (this.c && str2.equals("GET")) {
                this.d = new P2PGet();
            }
        } else if (str2.equals("E")) {
            this.a = new ServerError();
        } else if (str2.equals("RGETMULT")) {
            this.b = new P2PGetMultConfirmData();
        } else if (str2.equals("GETS")) {
            this.c = true;
        }
    }
}
