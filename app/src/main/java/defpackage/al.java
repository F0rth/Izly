package defpackage;

import fr.smoney.android.izly.data.model.Bookmark;
import fr.smoney.android.izly.data.model.GetBookmarksData;
import fr.smoney.android.izly.data.model.ServerError;
import java.util.Collections;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class al extends DefaultHandler {
    public ServerError a = null;
    public GetBookmarksData b = null;
    public Bookmark c = null;
    private StringBuilder d = new StringBuilder();

    public final void characters(char[] cArr, int i, int i2) throws SAXException {
        super.characters(cArr, i, i2);
        this.d.append(cArr, i, i2);
    }

    public final void endElement(String str, String str2, String str3) throws SAXException {
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
        } else if (str2.equals("BOOKMARK")) {
            this.b.b.add(this.c);
            this.c = null;
        } else if (str2.equals("BOOKMARKID")) {
            this.c.a = this.d.toString();
        } else if (str2.equals("BOOKMARKLNAME")) {
            this.c.b = this.d.toString();
        } else if (str2.equals("BOOKMARKFNAME")) {
            this.c.c = this.d.toString();
        } else if (str2.equals("ISBLOCKED")) {
            this.c.d = this.d.toString().equals("1");
        } else if (str2.equals("BAL")) {
            this.b.a.a = Double.parseDouble(iu.c(this.d.toString()));
        } else if (str2.equals("LUD")) {
            try {
                this.b.a.c = ag.a.parse(this.d.toString()).getTime();
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if (str2.equals("BOOKMARKLIST")) {
            Collections.sort(this.b.b);
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        this.d.setLength(0);
        if (str2.equals("E")) {
            this.a = new ServerError();
        } else if (str2.equals("RGETBOOKM")) {
            this.b = new GetBookmarksData();
        } else if (str2.equals("BOOKMARK")) {
            this.c = new Bookmark();
        }
    }
}
