package defpackage;

import android.os.Bundle;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public final class by {
    public static Bundle a(String str) throws ParserConfigurationException, SAXException, IOException {
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        bz bzVar = new bz();
        xMLReader.setContentHandler(bzVar);
        xMLReader.parse(new InputSource(new StringReader(str)));
        Bundle bundle = new Bundle();
        if (bzVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", bzVar.a);
        }
        if (bzVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.transactionListData", bzVar.b);
        }
        return bundle;
    }
}
