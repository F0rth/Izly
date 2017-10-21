package rx.exceptions;

abstract class CompositeException$PrintStreamOrWriter {
    private CompositeException$PrintStreamOrWriter() {
    }

    abstract Object lock();

    abstract void println(Object obj);
}
