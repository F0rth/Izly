package defpackage;

import android.os.Bundle;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public final class az {
    public static Bundle a(String str) throws ParserConfigurationException, SAXException, IOException {
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        ba baVar = new ba();
        xMLReader.setContentHandler(baVar);
        xMLReader.parse(new InputSource(new StringReader(str)));
        Bundle bundle = new Bundle();
        if (baVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", baVar.a);
        }
        if (baVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.moneyInCbAndPayData", baVar.b);
        }
        return bundle;
    }
}
