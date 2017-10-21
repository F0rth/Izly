package org.kobjects.pim;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;

public class PimWriter {
    Writer writer;

    public PimWriter(Writer writer) {
        this.writer = writer;
    }

    public void writeEntry(PimItem pimItem) throws IOException {
        this.writer.write("begin:");
        this.writer.write(pimItem.getType());
        this.writer.write("\r\n");
        Enumeration fieldNames = pimItem.fieldNames();
        while (fieldNames.hasMoreElements()) {
            String str = (String) fieldNames.nextElement();
            for (int i = 0; i < pimItem.getFieldCount(str); i++) {
                PimField field = pimItem.getField(str, i);
                this.writer.write(str);
                this.writer.write(58);
                this.writer.write(field.getValue().toString());
                this.writer.write("\r\n");
            }
        }
        this.writer.write("end:");
        this.writer.write(pimItem.getType());
        this.writer.write("\r\n\r\n");
    }
}
