package org.kobjects.rss;

import java.io.IOException;
import java.io.Reader;
import org.kobjects.xml.XmlReader;
import org.spongycastle.i18n.MessageBundle;

public class RssReader {
    public static final int AUTHOR = 4;
    public static final int DATE = 3;
    public static final int DESCRIPTION = 2;
    public static final int LINK = 1;
    public static final int TITLE = 0;
    XmlReader xr;

    public RssReader(Reader reader) throws IOException {
        this.xr = new XmlReader(reader);
    }

    public String[] next() throws IOException {
        String[] strArr = new String[5];
        while (this.xr.next() != 1) {
            if (this.xr.getType() == 2) {
                String toLowerCase = this.xr.getName().toLowerCase();
                if (toLowerCase.equals("item") || toLowerCase.endsWith(":item")) {
                    while (this.xr.next() != 3) {
                        if (this.xr.getType() == 2) {
                            toLowerCase = this.xr.getName().toLowerCase();
                            int indexOf = toLowerCase.indexOf(":");
                            if (indexOf != -1) {
                                toLowerCase = toLowerCase.substring(indexOf + 1);
                            }
                            StringBuffer stringBuffer = new StringBuffer();
                            readText(stringBuffer);
                            String stringBuffer2 = stringBuffer.toString();
                            if (toLowerCase.equals(MessageBundle.TITLE_ENTRY)) {
                                strArr[0] = stringBuffer2;
                            } else if (toLowerCase.equals("link")) {
                                strArr[1] = stringBuffer2;
                            } else if (toLowerCase.equals("description")) {
                                strArr[2] = stringBuffer2;
                            } else if (toLowerCase.equals("date")) {
                                strArr[3] = stringBuffer2;
                            } else if (toLowerCase.equals("author")) {
                                strArr[4] = stringBuffer2;
                            }
                        }
                    }
                    return strArr;
                }
            }
        }
        return null;
    }

    void readText(StringBuffer stringBuffer) throws IOException {
        while (this.xr.next() != 3) {
            switch (this.xr.getType()) {
                case 2:
                    readText(stringBuffer);
                    break;
                case 4:
                    stringBuffer.append(this.xr.getText());
                    break;
                default:
                    break;
            }
        }
    }
}
