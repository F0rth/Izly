package defpackage;

import android.util.SparseArray;
import fr.smoney.android.izly.data.model.MoneyInCbCb;
import fr.smoney.android.izly.data.model.P2PPay;
import fr.smoney.android.izly.data.model.P2PPayData;
import fr.smoney.android.izly.data.model.ServerError;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class bq extends DefaultHandler {
    public ServerError a = null;
    public P2PPayData b = null;
    private StringBuilder c = new StringBuilder();
    private MoneyInCbCb d = null;
    private SparseArray<Double> e = new SparseArray();
    private int f = 0;
    private boolean g = false;
    private boolean h = false;

    public final void characters(char[] cArr, int i, int i2) throws SAXException {
        super.characters(cArr, i, i2);
        this.c.append(cArr, i, i2);
    }

    public final void endElement(String str, String str2, String str3) throws SAXException {
        boolean z = false;
        boolean z2 = true;
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
            if (this.h) {
                this.b.a.a = Long.parseLong(this.c.toString());
            } else if (this.g) {
                this.d.a = this.c.toString();
            }
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
        } else if (str2.equals("ISCARITATIVE")) {
            r2 = this.b;
            if (Integer.parseInt(this.c.toString()) == 1) {
                z = true;
            }
            r2.f = z;
        } else if (str2.equals("ISPRO")) {
            P2PPay p2PPay = this.b.a;
            if (Integer.parseInt(this.c.toString()) != 1) {
                z2 = false;
            }
            p2PPay.o = z2;
        } else if (str2.equals("OPTINPARTNERS")) {
            r2 = this.b;
            if (Integer.parseInt(this.c.toString()) != 1) {
                z2 = false;
            }
            r2.e = z2;
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
        } else if (str2.equals("CB")) {
            this.b.c.add(this.d);
        } else if (str2.equals("ID")) {
            this.d.a = this.c.toString();
        } else if (str2.equals("ALIAS")) {
            this.d.b = this.c.toString();
        } else if (str2.equals("NWK")) {
            this.d.c = Integer.parseInt(this.c.toString());
        } else if (str2.equals("HNT")) {
            this.d.d = this.c.toString();
        } else if (str2.equals("DEFAULT")) {
            this.d.e = this.c.toString().equals("True");
        } else if (str2.equals("STEPS")) {
            int size = this.e.size();
            this.b.d = new double[size];
            for (r1 = 0; r1 < size; r1++) {
                this.b.d[r1] = ((Double) this.e.get(r1)).doubleValue();
            }
        } else if (str2.equals("STEP")) {
            SparseArray sparseArray = this.e;
            r1 = this.f;
            this.f = r1 + 1;
            sparseArray.put(r1, Double.valueOf(Double.parseDouble(iu.c(this.c.toString()))));
        } else if (str2.equals("BAL")) {
            this.b.h.a = Double.parseDouble(this.c.toString());
        } else if (str2.equals("LUD")) {
            try {
                this.b.h.c = ag.a.parse(this.c.toString()).getTime();
            } catch (Exception e2) {
                throw new SAXException(e2);
            }
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        this.c.setLength(0);
        if (str2.equals("E")) {
            this.a = new ServerError();
        } else if (str2.equals("PAY")) {
            this.b = new P2PPayData();
        } else if (str2.equals("RCBL")) {
            this.b.b = true;
        } else if (str2.equals("CB")) {
            this.d = new MoneyInCbCb();
            this.g = true;
        } else if (str2.equals("HPAY")) {
            this.h = true;
        }
    }
}
