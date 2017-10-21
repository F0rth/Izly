package rx.exceptions;

import java.io.PrintWriter;

class CompositeException$WrappedPrintWriter extends CompositeException$PrintStreamOrWriter {
    private final PrintWriter printWriter;

    CompositeException$WrappedPrintWriter(PrintWriter printWriter) {
        super();
        this.printWriter = printWriter;
    }

    Object lock() {
        return this.printWriter;
    }

    void println(Object obj) {
        this.printWriter.println(obj);
    }
}
