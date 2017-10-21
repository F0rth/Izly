package defpackage;

import android.text.TextUtils;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.Transaction;
import fr.smoney.android.izly.data.model.Transaction.a;
import fr.smoney.android.izly.data.model.TransactionListData;
import fr.smoney.android.izly.data.model.TransactionMessage;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class bz extends DefaultHandler {
    public ServerError a = null;
    public TransactionListData b = null;
    public int c = -1;
    private StringBuilder d = new StringBuilder();
    private Transaction e = null;
    private TransactionMessage f = null;

    public final void characters(char[] cArr, int i, int i2) throws SAXException {
        super.characters(cArr, i, i2);
        this.d.append(cArr, i, i2);
    }

    public final void endElement(String str, String str2, String str3) throws SAXException {
        boolean z = true;
        boolean z2 = false;
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
        } else if (str2.equals("RPL")) {
            TransactionListData transactionListData = this.b;
            if (this.c > this.b.b.size()) {
                z2 = true;
            }
            transactionListData.a = z2;
        } else if (str2.equals("P")) {
            if (this.e.v == 0) {
                this.e.l = this.e.k;
            } else {
                this.e.l = this.e.v;
            }
            this.b.b.add(this.e);
        } else if (str2.equals("ID")) {
            this.e.b = Long.parseLong(this.d.toString());
        } else if (str2.equals("RECCLI")) {
            r2 = this.e;
            if (!this.d.toString().equals("1")) {
                z = false;
            }
            r2.c = z;
        } else if (str2.equals("ISREAD")) {
            r2 = this.e;
            if (!this.d.toString().equals("1")) {
                z = false;
            }
            r2.d = z;
        } else if (str2.equals("DEB")) {
            this.e.e = Double.parseDouble(iu.c(this.d.toString()));
        } else if (str2.equals("DEBHT")) {
            this.e.f = Double.parseDouble(iu.c(this.d.toString()));
        } else if (str2.equals("TAX")) {
            this.e.j = this.d.toString();
        } else if (str2.equals("CRED")) {
            this.e.g = Double.parseDouble(iu.c(this.d.toString()));
        } else if (str2.equals("CREDHT")) {
            this.e.h = Double.parseDouble(iu.c(this.d.toString()));
        } else if (str2.equals("COM")) {
            this.e.i = Double.parseDouble(iu.c(this.d.toString()));
        } else if (str2.equals("DATE")) {
            try {
                this.e.k = ag.a.parse(this.d.toString()).getTime();
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if (str2.equals("MSG")) {
            this.e.m = this.d.toString();
        } else if (str2.equals("TYPE")) {
            this.e.n = Integer.parseInt(this.d.toString());
        } else if (str2.equals("TYPEDIRECTION")) {
            this.e.o = Integer.parseInt(this.d.toString());
        } else if (str2.equals("USERFNAME")) {
            this.e.q = this.d.toString();
        } else if (str2.equals("USERLNAME")) {
            this.e.r = this.d.toString();
        } else if (str2.equals("USERID")) {
            this.e.p = this.d.toString();
        } else if (str2.equals("USERTYPE")) {
            this.e.z = Integer.parseInt(this.d.toString());
        } else if (str2.equals("STATUS")) {
            this.e.s = Integer.parseInt(this.d.toString());
        } else if (str2.equals("DURATIONTYPE")) {
            Transaction transaction = this.e;
            transaction.t = (a) Transaction.a.get(Integer.parseInt(this.d.toString()));
            if (transaction.t == null) {
                transaction.t = a.a;
            }
        } else if (str2.equals("RESPMSG")) {
            this.e.u = this.d.toString();
        } else if (str2.equals("RESPDATE")) {
            try {
                if (TextUtils.isEmpty(this.d.toString())) {
                    this.e.v = -1;
                    return;
                }
                this.e.v = ag.a.parse(this.d.toString()).getTime();
            } catch (Exception e2) {
                throw new SAXException(e2);
            }
        } else if (str2.equals("INFOMSG")) {
            this.e.w = this.d.toString();
        } else if (str2.equals("ATTACHMENTNAME")) {
            this.e.x = this.d.toString();
        } else if (str2.equals("ATTACHMENTID")) {
            this.e.y = this.d.toString();
        } else if (str2.equals("BAL")) {
            this.b.c.a = Double.parseDouble(iu.c(this.d.toString()));
        } else if (str2.equals("CASHBAL")) {
            this.b.c.b = Double.parseDouble(iu.c(this.d.toString()));
        } else if (str2.equals("LUD")) {
            try {
                this.b.c.c = ag.a.parse(this.d.toString()).getTime();
            } catch (Exception e22) {
                throw new SAXException(e22);
            }
        } else if (str2.equals("MSGID")) {
            this.f.a = Long.parseLong(this.d.toString());
        } else if (str2.equals("MSGDATE")) {
            try {
                this.f.b = ag.a.parse(this.d.toString()).getTime();
            } catch (Exception e222) {
                throw new SAXException(e222);
            }
        } else if (str2.equals("MSGUSER")) {
            this.f.c = this.d.toString();
        } else if (str2.equals("MSGTEXT")) {
            this.f.d = this.d.toString();
        } else if (str2.equals("CHATMSG")) {
            this.e.A.add(this.f);
            this.f = null;
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        this.d.setLength(0);
        if (str2.equals("E")) {
            this.a = new ServerError();
        } else if (str2.equals("RPL")) {
            this.b = new TransactionListData();
        } else if (str2.equals("PL")) {
            this.b.b = new ArrayList();
            this.c = Integer.parseInt(attributes.getValue("NB"));
        } else if (str2.equals("P")) {
            this.e = new Transaction();
        } else if (str2.equals("CHATMSG")) {
            this.f = new TransactionMessage();
        }
    }
}
