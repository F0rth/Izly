package defpackage;

import android.text.TextUtils;
import android.util.SparseArray;
import fr.smoney.android.izly.data.model.MoneyInCbCb;
import fr.smoney.android.izly.data.model.P2PPayRequestData;
import fr.smoney.android.izly.data.model.ServerError;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class bs extends DefaultHandler {
    public P2PPayRequestData a = null;
    public ServerError b = null;
    private StringBuilder c = new StringBuilder();
    private MoneyInCbCb d = null;
    private SparseArray<Double> e = new SparseArray();
    private int f = 0;
    private boolean g = false;

    public final void characters(char[] cArr, int i, int i2) throws SAXException {
        super.characters(cArr, i, i2);
        this.c.append(cArr, i, i2);
    }

    public final void endElement(String str, String str2, String str3) throws SAXException {
        boolean z = false;
        boolean z2 = true;
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
        } else if (str2.equals("RCBL")) {
            this.g = false;
        } else if (str2.equals("PRID")) {
            this.a.a.a = Long.parseLong(this.c.toString());
        } else if (str2.equals("SENID")) {
            this.a.a.d = this.c.toString();
        } else if (str2.equals("SENFNAME")) {
            this.a.a.e = this.c.toString();
        } else if (str2.equals("SENLNAME")) {
            this.a.a.f = this.c.toString();
        } else if (str2.equals("ISPRO")) {
            r2 = this.a.a;
            if (Integer.parseInt(this.c.toString()) == 1) {
                z = true;
            }
            r2.r = z;
        } else if (str2.equals("AMT")) {
            if (TextUtils.isEmpty(this.c.toString())) {
                this.a.a.h = -1.0d;
                this.a.a.g = true;
                return;
            }
            this.a.a.h = Double.parseDouble(iu.c(this.c.toString()));
            this.a.a.g = false;
        } else if (str2.equals("DATE")) {
            try {
                this.a.a.c = ag.a.parse(this.c.toString()).getTime();
            } catch (Exception e) {
                throw new SAXException(e);
            }
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
        } else if (str2.equals("STATUS")) {
            this.a.a.n = Integer.parseInt(this.c.toString());
        } else if (str2.equals("CB")) {
            this.a.c.add(this.d);
        } else if (str2.equals("ID") && this.g) {
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
            this.a.d = new double[size];
            for (r1 = 0; r1 < size; r1++) {
                this.a.d[r1] = ((Double) this.e.get(r1)).doubleValue();
            }
        } else if (str2.equals("STEP")) {
            SparseArray sparseArray = this.e;
            r1 = this.f;
            this.f = r1 + 1;
            sparseArray.put(r1, Double.valueOf(Double.parseDouble(iu.c(this.c.toString()))));
        } else if (str2.equals("BAL")) {
            this.a.g.a = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("LUD")) {
            try {
                this.a.g.c = ag.a.parse(this.c.toString()).getTime();
            } catch (Exception e2) {
                throw new SAXException(e2);
            }
        } else if (str2.equals("ISCARITATIVE")) {
            P2PPayRequestData p2PPayRequestData = this.a;
            if (Integer.parseInt(this.c.toString()) != 1) {
                z2 = false;
            }
            p2PPayRequestData.e = z2;
        } else if (str2.equals("ISPRO")) {
            r2 = this.a.a;
            if (Integer.parseInt(this.c.toString()) != 1) {
                z2 = false;
            }
            r2.t = z2;
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        this.c.setLength(0);
        if (str2.equals("E")) {
            this.b = new ServerError();
        } else if (str2.equals("RPR")) {
            this.a = new P2PPayRequestData();
        } else if (str2.equals("RCBL")) {
            this.g = true;
            this.a.b = true;
        } else if (str2.equals("CB")) {
            this.d = new MoneyInCbCb();
        }
    }
}
