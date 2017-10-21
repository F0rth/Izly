package defpackage;

import android.util.SparseIntArray;
import fr.smoney.android.izly.data.model.MoneyInCbCb;
import fr.smoney.android.izly.data.model.MoneyInCbCbListData;
import fr.smoney.android.izly.data.model.ServerError;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class bd extends DefaultHandler {
    public ServerError a = null;
    public MoneyInCbCbListData b = null;
    private StringBuilder c = new StringBuilder();
    private MoneyInCbCb d = null;
    private SparseIntArray e = new SparseIntArray();
    private int f = 0;

    public final void characters(char[] cArr, int i, int i2) throws SAXException {
        super.characters(cArr, i, i2);
        this.c.append(cArr, i, i2);
    }

    public final void endElement(String str, String str2, String str3) throws SAXException {
        int i = 0;
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
        } else if (str2.equals("CB")) {
            this.b.a.add(this.d);
        } else if (str2.equals("ID")) {
            this.d.a = this.c.toString();
        } else if (str2.equals("DEFAULT")) {
            boolean z;
            MoneyInCbCb moneyInCbCb = this.d;
            if (this.c.toString().equals("True")) {
                z = true;
            }
            moneyInCbCb.e = z;
        } else if (str2.equals("ALIAS")) {
            this.d.b = this.c.toString();
        } else if (str2.equals("NWK")) {
            this.d.c = Integer.parseInt(this.c.toString());
        } else if (str2.equals("HNT")) {
            this.d.d = this.c.toString();
        } else if (str2.equals("STEPS")) {
            r1 = this.e.size();
            this.b.b = new int[r1];
            while (i < r1) {
                this.b.b[i] = this.e.get(i);
                i++;
            }
        } else if (str2.equals("STEP")) {
            SparseIntArray sparseIntArray = this.e;
            r1 = this.f;
            this.f = r1 + 1;
            sparseIntArray.put(r1, Integer.parseInt(this.c.toString()));
        } else if (str2.equals("BAL")) {
            this.b.c.a = Double.parseDouble(this.c.toString());
        } else if (str2.equals("CASHBAL")) {
            this.b.c.b = Double.parseDouble(this.c.toString());
        } else if (str2.equals("LUD")) {
            try {
                this.b.c.c = ag.a.parse(this.c.toString()).getTime();
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        this.c.setLength(0);
        if (str2.equals("E")) {
            this.a = new ServerError();
        } else if (str2.equals("RCBL")) {
            this.b = new MoneyInCbCbListData();
        } else if (str2.equals("CB")) {
            this.d = new MoneyInCbCb();
        }
    }
}
