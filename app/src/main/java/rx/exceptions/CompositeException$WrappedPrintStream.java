package rx.exceptions;

import java.io.PrintStream;

class CompositeException$WrappedPrintStream extends CompositeException$PrintStreamOrWriter {
    private final PrintStream printStream;

    CompositeException$WrappedPrintStream(PrintStream printStream) {
        super();
        this.printStream = printStream;
    }

    Object lock() {
        return this.printStream;
    }

    void println(Object obj) {
        this.printStream.println(obj);
    }
}
