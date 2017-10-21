package defpackage;

import android.util.SparseArray;
import fr.smoney.android.izly.data.model.MoneyOutTransferAccountData;
import fr.smoney.android.izly.data.model.ServerError;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class bg extends DefaultHandler {
    public ServerError a = null;
    public MoneyOutTransferAccountData b = null;
    private StringBuilder c = new StringBuilder();
    private SparseArray<Float> d = new SparseArray();
    private int e = 0;

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
        } else if (str2.equals("ALIAS")) {
            this.b.a = this.c.toString();
        } else if (str2.equals("HNTIBAN")) {
            this.b.b = this.c.toString();
        } else if (str2.equals("HNTBIC")) {
            this.b.c = this.c.toString();
        } else if (str2.equals("STEPS")) {
            int size = this.d.size();
            this.b.d = new float[size];
            for (r1 = 0; r1 < size; r1++) {
                this.b.d[r1] = ((Float) this.d.get(r1)).floatValue();
            }
        } else if (str2.equals("STEP")) {
            SparseArray sparseArray = this.d;
            r1 = this.e;
            this.e = r1 + 1;
            sparseArray.put(r1, Float.valueOf(Float.parseFloat(this.c.toString())));
        } else if (str2.equals("BAL")) {
            this.b.e.a = Double.parseDouble(iu.c(this.c.toString()));
        } else if (str2.equals("LUD")) {
            try {
                this.b.e.c = ag.a.parse(this.c.toString()).getTime();
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        this.c.setLength(0);
        if (str2.equals("E")) {
            this.a = new ServerError();
        } else if (str2.equals("RMOTA")) {
            this.b = new MoneyOutTransferAccountData();
        }
    }
}
