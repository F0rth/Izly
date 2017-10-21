package org.kobjects.pim;

import java.io.IOException;
import java.io.Reader;
import java.util.Vector;
import org.kobjects.io.LookAheadReader;

public class PimParser {
    LookAheadReader reader;
    Class type;

    public PimParser(Reader reader, Class cls) {
        this.reader = new LookAheadReader(reader);
        this.type = cls;
    }

    String[] readArrayValue(int i) throws IOException {
        int i2 = 0;
        Vector vector = new Vector();
        StringBuffer stringBuffer = new StringBuffer();
        int i3 = 1;
        do {
            stringBuffer.append(this.reader.readTo(";\n\r"));
            switch (this.reader.read()) {
                case 10:
                    break;
                case 13:
                    if (this.reader.peek(0) == 10) {
                        this.reader.read();
                        break;
                    }
                    break;
                case 59:
                    vector.addElement(stringBuffer.toString());
                    stringBuffer.setLength(0);
                    continue;
                default:
                    break;
            }
            if (this.reader.peek(0) != 32) {
                i3 = 0;
                continue;
            } else {
                this.reader.read();
                continue;
            }
        } while (i3 != 0);
        if (stringBuffer.length() != 0) {
            vector.addElement(stringBuffer.toString());
        }
        String[] strArr = new String[i];
        while (i2 < Math.min(i, vector.size())) {
            strArr[i2] = (String) vector.elementAt(i2);
            i2++;
        }
        return strArr;
    }

    public PimItem readItem() throws IOException {
        String readName = readName();
        if (readName == null) {
            return null;
        }
        if (readName.equals("begin")) {
            try {
                PimItem pimItem = (PimItem) this.type.newInstance();
                this.reader.read();
                if (pimItem.getType().equals(readStringValue().toLowerCase())) {
                    while (true) {
                        String readName2 = readName();
                        if (readName2.equals("end")) {
                            this.reader.read();
                            System.out.println("end:" + readStringValue());
                            return pimItem;
                        }
                        Object readArrayValue;
                        PimField pimField = new PimField(readName2);
                        readProperties(pimField);
                        switch (pimItem.getType(readName2)) {
                            case 1:
                                readArrayValue = readArrayValue(pimItem.getArraySize(readName2));
                                break;
                            default:
                                readArrayValue = readStringValue();
                                break;
                        }
                        pimField.setValue(readArrayValue);
                        System.out.println("value:" + readArrayValue);
                        pimItem.addField(pimField);
                    }
                } else {
                    throw new RuntimeException("item types do not match!");
                }
            } catch (Exception e) {
                throw new RuntimeException(e.toString());
            }
        }
        throw new RuntimeException("'begin:' expected");
    }

    String readName() throws IOException {
        String toLowerCase = this.reader.readTo(":;").trim().toLowerCase();
        System.out.println("name:" + toLowerCase);
        return this.reader.peek(0) == -1 ? null : toLowerCase;
    }

    void readProperties(PimField pimField) throws IOException {
        int read = this.reader.read();
        while (read == 32) {
            read = this.reader.read();
        }
        while (read != 58) {
            String toLowerCase = this.reader.readTo(":;=").trim().toLowerCase();
            read = this.reader.read();
            if (read == 61) {
                pimField.setProperty(toLowerCase, this.reader.readTo(":;").trim().toLowerCase());
                read = this.reader.read();
            } else {
                pimField.setAttribute(toLowerCase, true);
            }
        }
    }

    String readStringValue() throws IOException {
        String readLine = this.reader.readLine();
        while (this.reader.peek(0) == 32) {
            this.reader.read();
            readLine = readLine + this.reader.readLine();
        }
        return readLine;
    }
}
